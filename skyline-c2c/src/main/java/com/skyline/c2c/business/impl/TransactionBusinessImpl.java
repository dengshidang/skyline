package com.skyline.c2c.business.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.c2c.async.BillinfoAsyncTask;
import com.skyline.c2c.business.TransactionBusiness;
import com.skyline.c2c.exception.BusinessException;
import com.skyline.c2c.filter.RequestUtil;
import com.skyline.c2c.mapper.BankinfoMapper;
import com.skyline.c2c.mapper.TransactionMapper;
import com.skyline.c2c.mapper.TransactionorderMapper;
import com.skyline.c2c.mapper.TransactiontypeMapper;
import com.skyline.c2c.mapper.UserbalanceMapper;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.UserConstant;
import com.skyline.common.constant.C2cConstant;
import com.skyline.common.constant.C2cStatusCode;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Billinfo;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.Transactiontype;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.DoubleUtil;
import com.skyline.common.util.OrderNoUtil;
import com.skyline.common.vo.TransactionVO;

@Service
@Transactional
public class TransactionBusinessImpl extends BaseBusinessImpl<Transaction, Integer> implements TransactionBusiness{
	
	@Autowired
	TransactionMapper  transactionMapper;
	@Autowired
	TransactiontypeMapper transactiontypeMapper;
	@Autowired
	TransactionorderMapper transactionorderMapper;
	@Autowired
	UserbalanceMapper userbalanceMapper;
	@Autowired 
	BillinfoAsyncTask billinfoAsyncTask;
	@Autowired
	BankinfoMapper bankinfoMapper;
	
	/**
	 * 
	* @Title: release
	* @Description: TODO(发布c2c交易)
	* @param     参数
	* @return void    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<?> release(Transaction transaction) {
		Userinfo user=RequestUtil.getCurrentUser();
		Billinfo billinfo=new Billinfo();
		//1.查询用户权根（商户才有权限发布交易）
		if(!UserConstant.USERINFO_MCTSIGN_1.equals(user.getMctSign())){
			return Result.errorResult(C2cStatusCode.E00210);
		}
		transaction.setMerchantId(user.getId());//添加用户
		//2.检查交易信息合法性
		Transactiontype transactiontype=null;
		transactiontype=transactiontypeMapper.selectByPrimaryKey(transaction.getTransactiontypeId());//查询出币种交易类型
		if(transactiontype==null){//此币种不能交易
			throw new BusinessException(C2cStatusCode.E00201);
		}
		if(transactiontype.getStatus()==C2cConstant.TRANSACTIONTYPE_STATUS_1){//此币种禁用
			throw new BusinessException(C2cStatusCode.E00202);
		}
		if(transactiontype.getType()==C2cConstant.TRANSACTIONTYPE_TYPE_0){//购买
				if(transaction.getTotalNum()<transactiontype.getBuyMin()){//小于最小买入量
					throw new BusinessException(C2cStatusCode.E00203);
				}else if(transaction.getTotalNum()>transactiontype.getBuyMax()){//大于最大买入量
					throw new BusinessException(C2cStatusCode.E00204);
				}
		}else{//出售
				if(transaction.getTotalNum()<transactiontype.getSellMin()){//小于最小卖出量
					throw new BusinessException(C2cStatusCode.E00205);
				}else if(transaction.getTotalNum()>transactiontype.getSellMax()){//大于最大卖出量
					throw new BusinessException(C2cStatusCode.E00206);
				}
		}
		Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(user.getId(),transactiontype.getCoinId());
		if(userbalance==null) {
			userbalance=new Userbalance();
			userbalance.setCoinId(transactiontype.getCoinId());
			userbalance.setFrozenNum(0d);
			userbalance.setTotalNum(0d);
			userbalance.setValidNum(0d);
			userbalance.setUserId(user.getId());
			userbalance.setCreateTime(DateUtil.getCurTimeString());
			userbalance.setStatus(0);
			userbalanceMapper.insert(userbalance);
			return Result.errorResult(C2cStatusCode.E00236);
		}
		billinfo.setForNum(userbalance.getTotalNum());
		billinfo.setForFrozenNum(userbalance.getFrozenNum());
		billinfo.setForValidNum(userbalance.getValidNum());	
		//3.检查如果是购买 ，冻结交易余额
		if(transactiontype.getType()==C2cConstant.TRANSACTIONTYPE_TYPE_0){
				if(userbalance.getValidNum()<transaction.getTotalNum()) {
					throw new BusinessException(C2cStatusCode.E00236); 
				}
				userbalance.setFrozenNum(DoubleUtil.add(userbalance.getFrozenNum(),transaction.getTotalNum()));
				userbalance.setValidNum(DoubleUtil.sub(userbalance.getValidNum(), transaction.getTotalNum()));
				userbalance.setUpdateTime(DateUtil.getCurTimeString());
				if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1){
					throw new BusinessException(C2cStatusCode.E00238);
				}
		}	
		//4.保存交易
		transaction.setCoinId(transactiontype.getCoinId());
		transaction.setCoinName(transactiontype.getCoinName());
		transaction.setStatus(C2cConstant.TRANSACTION_STATUS_0);//挂起
		transaction.setFinishNum(0d);
		transaction.setSurplusNum(transaction.getTotalNum());
		transaction.setTransactionNo(OrderNoUtil.getTransaOdrderNo());
		transaction.setCreateTime(DateUtil.getCurTimeString());
		//5.保存日志
		billinfo.setOrderNo(transaction.getTransactionNo());
		billinfo.setUserId(user.getId());
		billinfo.setUserName(user.getNickName());
		billinfo.setCoinId(transaction.getCoinId());
		billinfo.setCoinName(transaction.getCoinName());
		billinfo.setType(Constants.BILLINFO_TYPE_2);
		billinfo.setNumber(transaction.getTotalNum());
		billinfo.setFee(0d);
		billinfo.setNowNum(userbalance.getTotalNum());
		billinfo.setNowFrozenNum(userbalance.getFrozenNum());
		billinfo.setNowValidNum(userbalance.getValidNum());
		billinfo.setBehavior(Constants.BILLINFO_BEHAVIOR_0);
		billinfo.setCreateTime(DateUtil.getCurTimeString());
		billinfoAsyncTask.save(billinfo);
		//6.添加
		if( transactionMapper.insert(transaction)==1){
		    return 	Result.successResult();
		}
		throw new BusinessException(C2cStatusCode.E00209);
	}
	/**
	 * 
	* @Title: uploadTransaction
	* @Description: TODO(更改c2c交易)
	* @author xzj
	* @param @param transaction
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	public Result<?>  updateTransaction(Transaction  transaction){
		Userinfo user=RequestUtil.getCurrentUser();
		Billinfo billinfo=new Billinfo();
		//1.查询用户权根
		Transaction tran=transactionMapper.getTransactionForUpdate(transaction.getId());//锁
		if(tran==null) {
			return Result.errorResult(C2cStatusCode.E00226);
		}
		if(!user.getId().equals(tran.getMerchantId())) {
			return Result.errorResult(C2cStatusCode.E00227);
		}
		//订单状态
		if(!(tran.getStatus().equals(C2cConstant.TRANSACTION_STATUS_0)||tran.getStatus().equals(C2cConstant.TRANSACTION_STATUS_1))) {
			return Result.errorResult(C2cStatusCode.E00230);
		}
		//2.检查交易信息合法性
		Transactiontype transactiontype=transactiontypeMapper.selectByPrimaryKey(tran.getTransactiontypeId());//查询出币种交易类型
		if(transactiontype==null){//此币种暂停
			throw new BusinessException(C2cStatusCode.E00201);
		}
		if(transactiontype.getStatus()==C2cConstant.TRANSACTIONTYPE_STATUS_1){//此币种禁用
			throw new BusinessException(C2cStatusCode.E00202);
		}
		if(transactiontype.getType()==C2cConstant.TRANSACTIONTYPE_TYPE_0){//买入
				if(transaction.getTotalNum()<transactiontype.getBuyMin()){//小于最小买入量
					throw new BusinessException(C2cStatusCode.E00203);
				}else if(transaction.getTotalNum()>transactiontype.getBuyMax()){//大于最大买入量
					throw new BusinessException(C2cStatusCode.E00204);
				}
		}else{//买出
			if(transaction.getTotalNum()<transactiontype.getSellMin()){//小于最小卖出量
				throw new BusinessException(C2cStatusCode.E00205);
			}else if(transaction.getTotalNum()>transactiontype.getSellMax()){//大于最大卖出量
				throw new BusinessException(C2cStatusCode.E00206);
			}
		}
		/*对卖出总数进行判断 1.查出该广告正在交易的币数，总数要大于等于该金额*/
		Double sum=	transactionorderMapper.getSumNumberByIn(tran.getTransactionNo());
		sum=(sum==null)?0:sum;
		if(transaction.getTotalNum()<sum) {
				throw new BusinessException(C2cStatusCode.E00228);
		}
		Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(user.getId(),tran.getCoinId());
		billinfo.setForNum(userbalance.getTotalNum());
		billinfo.setForFrozenNum(userbalance.getFrozenNum());
		billinfo.setForValidNum(userbalance.getValidNum());
		//3.检查如果是购买 ，冻结交易余额
		Double frozenNum= DoubleUtil.sub(transaction.getTotalNum(), tran.getTotalNum());
		if(transactiontype.getType()==C2cConstant.TRANSACTIONTYPE_TYPE_0){
			if(userbalance.getValidNum()<frozenNum) {
				throw new BusinessException(C2cStatusCode.E00236); 
			}
			userbalance.setFrozenNum(DoubleUtil.add(userbalance.getFrozenNum(),frozenNum));
			userbalance.setValidNum(DoubleUtil.sub(userbalance.getValidNum(), frozenNum));
			userbalance.setUpdateTime(DateUtil.getCurTimeString());
			if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1) {
				throw new BusinessException(C2cStatusCode.E00238);
			}
		}
		//4.更改交易
		tran.setMax(transaction.getMax());
		tran.setMin(transaction.getMin());
		tran.setTotalNum(transaction.getTotalNum());
		tran.setPayWay(transaction.getPayWay());
		transactionMapper.updateByPrimaryKeySelective(tran);
		//5.保存日志
		billinfo.setOrderNo(tran.getTransactionNo());
		billinfo.setUserId(user.getId());
		billinfo.setUserName(user.getNickName());
		billinfo.setCoinId(tran.getCoinId());
		billinfo.setCoinName(tran.getCoinName());
		billinfo.setType(Constants.BILLINFO_TYPE_2);
		billinfo.setNumber(frozenNum);
		billinfo.setFee(0d);
		billinfo.setNowNum(userbalance.getTotalNum());
		billinfo.setNowFrozenNum(	userbalance.getFrozenNum());
		billinfo.setNowValidNum(userbalance.getValidNum());
		billinfo.setBehavior(Constants.BILLINFO_BEHAVIOR_0);
		billinfo.setCreateTime(DateUtil.getCurTimeString());
		billinfoAsyncTask.save(billinfo);
		//6.更新
		tran.setUpdateTime(DateUtil.getCurTimeString());
		if(transactionMapper.updateByPrimaryKeySelective(tran)==1){
		    return 	Result.successResult();
		}
		throw new BusinessException(C2cStatusCode.E00209);
	}
	/**
	 * 
	* @Title: release
	* @Description: TODO(撤销c2c交易)
	* @param     参数
	* @return void    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<?> cancel(Integer id) {
		Userinfo user=RequestUtil.getCurrentUser();
		Transaction transaction=	transactionMapper.selectByPrimaryKey(id);
		Transactiontype transactiontype=transactiontypeMapper.selectByPrimaryKey(transaction.getTransactiontypeId());//查询出币种交易类型
		Double  number=  transactionorderMapper.getCountOrderNumber(transaction.getTransactionNo());
		if(number!=null && number>0) {//说明还有没有结束的订单
			throw new BusinessException(C2cStatusCode.E00239);
		}
		//更改为撤销
		if(transactionMapper.cancelTransaction(id,C2cConstant.TRANSACTION_STATUS_3,DateUtil.getCurTimeString())!=1){
			throw new BusinessException(C2cStatusCode.E00215);
		}
		if(transactiontype.getType()==C2cConstant.TRANSACTIONTYPE_TYPE_0) {//购买
			Billinfo billinfo=new Billinfo();
			Userbalance userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(user.getId(),transaction.getCoinId());
				billinfo.setForNum(userbalance.getTotalNum());
				billinfo.setForFrozenNum(userbalance.getFrozenNum());
				billinfo.setForValidNum(userbalance.getValidNum());
				userbalance.setFrozenNum(DoubleUtil.sub(userbalance.getFrozenNum(), transaction.getSurplusNum()));
				userbalance.setValidNum(DoubleUtil.add(userbalance.getValidNum(), transaction.getSurplusNum()));
				if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1) {
					throw new BusinessException(C2cStatusCode.E00238);
				}
				billinfo.setOrderNo(transaction.getTransactionNo());
				billinfo.setUserId(user.getId());
				billinfo.setUserName(user.getNickName());
				billinfo.setCoinId(transaction.getCoinId());
				billinfo.setCoinName(transaction.getCoinName());
				billinfo.setType(Constants.BILLINFO_TYPE_0);
				billinfo.setNumber(transaction.getSurplusNum());
				billinfo.setFee(0d);
				billinfo.setNowNum(userbalance.getTotalNum());
				billinfo.setNowFrozenNum(userbalance.getFrozenNum());
				billinfo.setNowValidNum(userbalance.getValidNum());
				billinfo.setBehavior(Constants.BILLINFO_BEHAVIOR_0);
				billinfo.setCreateTime(DateUtil.getCurTimeString());
				billinfoAsyncTask.save(billinfo);
		}
		return Result.successResult();	
	}

	/**
	 * 
	* @Title: release
	* @Description: TODO(查询c2c交易)
	* @param     参数
	* @return void    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Result<TransactionVO> findById(Integer id) {
		TransactionVO transactionVO = transactionMapper.findById(id);
		return transactionVO!=null?Result.successResult(transactionVO)
				:Result.errorResult(C2cStatusCode.E00208) ;
	}
	/**
	 * 
	* @Title: getList
	* @Description: TODO(查询)
	* @param @param tansactiontypeId
	* @param @param pageNum 当前页
	* @param @param pageSize 一页多少条
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<PageInfo<TransactionVO>> getTransactionList(Integer tansactiontypeId, 
			Integer pageNum, Integer pageSize,String  payWay,Double money) {
		PageHelper.startPage(pageNum, pageSize);
	   List<TransactionVO>	 transactionList= transactionMapper.selectTransactionList(tansactiontypeId,payWay,money);
       PageInfo<TransactionVO> page=new PageInfo<TransactionVO>(transactionList);
		return Result.successResult(page);
	}
	 /**
	  * 
	 * @Title: getTransactionListByUser
	 * @Description: TODO(查询各人发布的广告)
	 * @author xzj
	 * @param @return    参数
	 * @return Result    返回类型
	 * @throws
	  */
	 public Result<PageInfo<TransactionVO>> getTransactionListByUser(Integer pageNum, Integer pageSize,String startTime,String endTime,Integer coinId) {
		  Userinfo user=RequestUtil.getCurrentUser();
		  PageHelper.startPage(pageNum, pageSize);
		  List<TransactionVO>	 transactionList= transactionMapper.getTransactionListByUser(user.getId(), startTime, endTime,coinId);
		  PageInfo<TransactionVO> page=new PageInfo<TransactionVO>(transactionList);
		 return Result.successResult(page);
	 }
	 
	 
	 
	 
	 public Result<List<Bankinfo>>  getBankinfoByUser(){
		 Userinfo user =RequestUtil.getCurrentUser();
		return Result.successResult(bankinfoMapper.getBankinfoByUser(user.getId()));
	 }
	 /**
	  * 
	 * @Title: getBankinfoByUser
	 * @Description: TODO(查询广告可支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<Bankinfo>    返回类型
	 * @throws
	  */
	 public Result<List<Bankinfo>>  getBankinfoByTransactionNo(String transactionNo){
		 List<Bankinfo> list=bankinfoMapper.getBankinfoByTransactionNo(transactionNo);
		 return Result.successResult(list);
	 }
	 
	 /**
	  * 
	 * @Title: saveBankinfo
	 * @Description: TODO(添加支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<?>    返回类型
	 * @throws
	  */
	 public Result<?>  saveBankinfo(Bankinfo bank){
		 Userinfo user =RequestUtil.getCurrentUser();
		 Bankinfo bankinfo= bankinfoMapper.getBankinfoByType(user.getId(), bank.getType());
		 if(bankinfo!=null) {//存在，不能新增
			 throw new BusinessException(C2cStatusCode.E00240);
		 }
		 String time=DateUtil.getCurTimeString();
		 bank.setCreateTime(time);
		 bank.setUpdateTime(time);
		 bank.setUserId(user.getId());
		if(bankinfoMapper.insert(bank)==1){
			return Result.successResult();
		}
		throw new BusinessException(C2cStatusCode.E00241);
	 }

}
