package com.skyline.c2c.business.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.c2c.async.BillinfoAsyncTask;
import com.skyline.c2c.async.MessageAsyncTask;
import com.skyline.c2c.business.TransactionorderBusiness;
import com.skyline.c2c.exception.BusinessException;
import com.skyline.c2c.filter.RequestUtil;
import com.skyline.c2c.mapper.BankinfoMapper;
import com.skyline.c2c.mapper.TransactionMapper;
import com.skyline.c2c.mapper.TransactionorderMapper;
import com.skyline.c2c.mapper.TransactiontypeMapper;
import com.skyline.c2c.mapper.UserbalanceMapper;
import com.skyline.c2c.mapper.UserinfoMapper;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.C2cConstant;
import com.skyline.common.constant.C2cStatusCode;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.TransactionMessageConstant;
import com.skyline.common.constant.WebSocketConstant;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Billinfo;
import com.skyline.common.entity.TransactionMessage;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.entity.Transactiontype;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.DoubleUtil;
import com.skyline.common.util.OrderNoUtil;
import com.skyline.common.vo.TransactionorderVO;
import com.skyline.common.vo.WebSocketVO;

@Service
@Transactional
public class TransactionorderBusinessImpl extends BaseBusinessImpl<Transactionorder, Integer> implements TransactionorderBusiness{
	
	@Autowired
	TransactiontypeMapper transactiontypeMapper;
	@Autowired
	TransactionMapper transactionMapper;
	@Autowired
	TransactionorderMapper transactionorderMapper;
	@Autowired
	UserbalanceMapper  userbalanceMapper;
	
	@Autowired
	BankinfoMapper bankinfoMapper;
	@Autowired
	UserinfoMapper userinfoMapper;
	@Autowired
	BillinfoAsyncTask billinfoAsyncTask;
	@Autowired
	MessageAsyncTask messageAsyncTask;
	
	/**
	 * 
	* @Title: saveTransactionorder
	* @Description: TODO(生成法币交易订单)
	* @param @param transactionorder
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<?> saveTransactionorder(Transactionorder transactionorder) {
		Userinfo user =RequestUtil.getCurrentUser();
		Billinfo billinfo=null;
		/*判断是否还有没有结束的订单*/
		if(transactionorderMapper.getTransactionorderInCountByUser(user.getId())>0) {//已经有正在处理的订单
			throw	new BusinessException(C2cStatusCode.E00224)	;
		}
		Transaction transaction=new Transaction();
		transaction.setTransactionNo(transactionorder.getTransactionNo());
		transaction=transactionMapper.selectOne(transaction);
		if(user.getId().equals(transaction.getMerchantId())) {
			throw	new BusinessException(C2cStatusCode.E00225)	;
		}
		String[] payWayArr=transaction.getPayWay().split(",");
		List<Bankinfo> bankinfoList = bankinfoMapper.getBankinfoByUser(user.getId());
		if(bankinfoList==null ||bankinfoList.size()==0 ) {
			throw new BusinessException(C2cStatusCode.E00235);
		}
		int t=0;
		for (Bankinfo bankinfo : bankinfoList) {
			if(Arrays.asList(payWayArr).contains(String.valueOf(bankinfo.getType()))) {
				t++;
				break;
			}
		}
		if(t==0) {
			throw new BusinessException(C2cStatusCode.E00235);
		}
		Double  alreadynumber=transactionorderMapper.getCountOrderNumber(transaction.getTransactionNo());
		Double number=transactionorder.getNumber();
		alreadynumber=alreadynumber==null?0d:alreadynumber;
		if(number>(transaction.getSurplusNum()-alreadynumber)){
			throw	new BusinessException(C2cStatusCode.E00217)	;
		}
		if(number<DoubleUtil.divide(transaction.getMin(), transaction.getPrice(), 8)){
			throw	new BusinessException(C2cStatusCode.E00218)	;
		}
		if(number>DoubleUtil.divide(transaction.getMax(), transaction.getPrice(), 8)){
			throw	new BusinessException(C2cStatusCode.E00219)	;
		}
		Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(user.getId(),transaction.getCoinId());
		if(userbalance==null) {
			userbalance=new Userbalance();
			userbalance.setCoinId(transaction.getCoinId());
			userbalance.setFrozenNum(0d);
			userbalance.setTotalNum(0d);
			userbalance.setValidNum(0d);
			userbalance.setUserId(user.getId());
			userbalance.setCreateTime(DateUtil.getCurTimeString());
			userbalance.setStatus(0);
			userbalanceMapper.insert(userbalance);
			return Result.errorResult(C2cStatusCode.E00236);
		}
		Transactiontype transactiontype=transactiontypeMapper.selectByPrimaryKey(transaction.getTransactiontypeId());
		if(transactiontype.getType()==C2cConstant.TRANSACTIONTYPE_TYPE_1) {//出售 冻结用户资金
			billinfo=new Billinfo();
			
			billinfo.setForNum(userbalance.getTotalNum());
			billinfo.setForFrozenNum(userbalance.getFrozenNum());
			billinfo.setForValidNum(userbalance.getValidNum());
			if(userbalance.getValidNum()<transactionorder.getNumber()) {//余额不足
				throw	new BusinessException(C2cStatusCode.E00236)	;
			}
			userbalance.setFrozenNum(DoubleUtil.add(userbalance.getFrozenNum(),transactionorder.getNumber() ));
			userbalance.setValidNum(DoubleUtil.sub(userbalance.getValidNum(), transactionorder.getNumber()));
			userbalance.setUpdateTime(DateUtil.getCurTimeString());
			if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1) {
				throw new BusinessException(C2cStatusCode.E00238);
			}
			billinfo.setOrderNo(transactionorder.getOrderNo());
			billinfo.setUserId(user.getId());
			billinfo.setUserName(user.getNickName());
			billinfo.setCoinId(transaction.getCoinId());
			billinfo.setCoinName(transaction.getCoinName());
			billinfo.setType(Constants.BILLINFO_TYPE_2);
			billinfo.setNumber(transactionorder.getNumber());
			billinfo.setFee(0d);
			billinfo.setNowNum(userbalance.getTotalNum());
			billinfo.setNowFrozenNum(userbalance.getFrozenNum());
			billinfo.setNowValidNum(userbalance.getValidNum());
			billinfo.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
			billinfo.setCreateTime(DateUtil.getCurTimeString());
		}
		transactionorder.setPrice(transaction.getPrice());
		transactionorder.setTotalPrice(DoubleUtil.mul(transactionorder.getNumber(), transaction.getPrice()));
		transactionorder.setUserId(user.getId());
		transactionorder.setMerchantId(transaction.getMerchantId());
		transactionorder.setOrderNo(OrderNoUtil.getTorderOdrderNo());
		transactionorder.setRemarkCode(OrderNoUtil.getRemarkCode());
		transactionorder.setCreateTime(DateUtil.getCurTimeString());
		transactionorder.setStatus(C2cConstant.TRANSACTIONORDER_STATUS_0);
		transactionorder.setIsAppeal(C2cConstant.TRANSACTIONORDER_ISAPPEAL_0);
		if(transactionorderMapper.insert(transactionorder)==1){
			if(billinfo!=null) {
				billinfoAsyncTask.save(billinfo);
			}
			TransactionMessage userMessage =new TransactionMessage();
			TransactionMessage merchantMessage =new TransactionMessage();
			userMessage.setSenderId(-1);
			merchantMessage.setSenderId(-1);
			userMessage.setRecipientId(user.getId());
			merchantMessage.setRecipientId(transaction.getMerchantId());
			userMessage.setOrderId(transactionorder.getId());
			merchantMessage.setOrderId(transactionorder.getId());
			if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
				userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_START_1);
				merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_START_2);
			}else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){
				userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_START_3);
				merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_START_4);
			}
			messageAsyncTask.send(userMessage,WebSocketConstant.WEBSOCKET_TYPE_0);
			messageAsyncTask.send(merchantMessage,WebSocketConstant.WEBSOCKET_TYPE_0);
			return Result.successResult(transactionorder.getId());
		}
		throw	new BusinessException(StatusCode.SAVEERROR)	;
	}
	/**
 	 * 
 	* @Title: makeMoney
 	* @Description: TODO(打款)
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@Override
	public Result<?> makeMoney(Integer transactionorderId,String imgUrl,Integer payWay) {
		Userinfo user =RequestUtil.getCurrentUser();
		Transactionorder  transactionorder = transactionorderMapper.selectByPrimaryKey(transactionorderId);
		 Transactiontype transactiontype = transactiontypeMapper.selectTransactiontype(transactionorderId);
		 	TransactionMessage userMessage =new TransactionMessage();
			TransactionMessage merchantMessage =new TransactionMessage();
		   if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
			   if(!user.getId().equals(transactionorder.getUserId())) {
					throw	new BusinessException(StatusCode.USERERROR)	;
			   }
			   userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_MAKE_1);
			   merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_MAKE_2);
			}else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){
				 if(!user.getId().equals(transactionorder.getMerchantId())) {
						throw	new BusinessException(StatusCode.USERERROR)	;
				 }
				 userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_MAKE_3);
				 merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_MAKE_4);
			}
		if( transactionorderMapper.updateStatus(transactionorderId, C2cConstant.TRANSACTION_STATUS_0,
				C2cConstant.TRANSACTIONORDER_STATUS_1,DateUtil.getCurTimeString(),imgUrl,payWay)==1){
			userMessage.setSenderId(-1);
			merchantMessage.setSenderId(-1);
			userMessage.setRecipientId(transactionorder.getUserId());
			merchantMessage.setRecipientId(transactionorder.getMerchantId());
			userMessage.setOrderId(transactionorder.getId());
			merchantMessage.setOrderId(transactionorder.getId());
			messageAsyncTask.send(userMessage,WebSocketConstant.WEBSOCKET_TYPE_1);
			messageAsyncTask.send(merchantMessage,WebSocketConstant.WEBSOCKET_TYPE_1);
			return Result.successResult();
		}
		throw	new BusinessException(StatusCode.UPDATEERROR);
	}
	
	/**
 	 * 
 	* @Title: makeMoney
 	* @Description: TODO(放行)
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@Override
	public Result<?> confirmMakeMoney(Integer transactionorderId) {
			 Userinfo user =RequestUtil.getCurrentUser();
			 //查询是出售还购买（购买用户加币，商户减币，出售的话用用户减币 商户加币）
			Transactionorder transactionorder =	transactionorderMapper.selectByPrimaryKey(transactionorderId);
			 if(transactionorder==null){
						throw	new BusinessException(C2cStatusCode.E00211);
			 }
			 Transactiontype transactiontype=	transactiontypeMapper.selectTransactiontype(transactionorderId);
			 if(transactiontype==null){
					   throw 	new BusinessException(C2cStatusCode.E00212);
			 }
			 if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
				   if(!user.getId().equals(transactionorder.getMerchantId())) {
						throw	new BusinessException(StatusCode.USERERROR)	;
				   }
				}else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){
					 if(!user.getId().equals(transactionorder.getUserId())) {
							throw	new BusinessException(StatusCode.USERERROR)	;
					 }
				}
			Billinfo billinfoUser=new Billinfo();
			Billinfo billinfoMerchant=new Billinfo();
			//查询 transactionorder信息
		   Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(transactionorder.getUserId(),transactiontype.getCoinId());
	       billinfoUser.setForNum(userbalance.getTotalNum());
	       billinfoUser.setForFrozenNum(userbalance.getFrozenNum());
	       billinfoUser.setForValidNum(userbalance.getValidNum());
	       Userbalance  merchantUserbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(transactionorder.getMerchantId(),transactiontype.getCoinId());
	       billinfoMerchant.setForNum(merchantUserbalance.getTotalNum());
	       billinfoMerchant.setForFrozenNum(merchantUserbalance.getFrozenNum());
	       billinfoMerchant.setForValidNum(merchantUserbalance.getValidNum());
		if(C2cConstant.TRANSACTIONTYPE_STATUS_0==transactiontype.getType()){//购买
			billinfoUser.setType(Constants.BILLINFO_TYPE_0);
			billinfoMerchant.setType(Constants.BILLINFO_TYPE_1);
			//用户添加币数
			userbalance.setValidNum(DoubleUtil.add(userbalance.getValidNum(), transactionorder.getNumber()));
			userbalance.setTotalNum(DoubleUtil.add(userbalance.getTotalNum(), transactionorder.getNumber()));
			userbalance.setUpdateTime(DateUtil.getCurTimeString());
			if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1) {
				throw	new BusinessException(C2cStatusCode.E00238);
			}
		    billinfoUser.setNowNum(userbalance.getTotalNum());
		    billinfoUser.setNowFrozenNum(userbalance.getFrozenNum());
		    billinfoUser.setNowValidNum(userbalance.getValidNum());
			//商户扣除冻结币数
		    merchantUserbalance.setFrozenNum(DoubleUtil.sub(merchantUserbalance.getFrozenNum(), transactionorder.getNumber()));
		    merchantUserbalance.setTotalNum(DoubleUtil.sub(merchantUserbalance.getTotalNum(), transactionorder.getNumber()));
		    merchantUserbalance.setUpdateTime(DateUtil.getCurTimeString());
		    if(userbalanceMapper.updateByPrimaryKeySelective(merchantUserbalance)!=1) {
				throw	new BusinessException(C2cStatusCode.E00238);
			}
		    billinfoMerchant.setNowNum(merchantUserbalance.getTotalNum());
		    billinfoMerchant.setNowFrozenNum(merchantUserbalance.getFrozenNum());
		    billinfoMerchant.setNowValidNum(merchantUserbalance.getValidNum());
		}else if(C2cConstant.TRANSACTIONTYPE_STATUS_1==transactiontype.getType()){//出售
			billinfoUser.setType(Constants.BILLINFO_TYPE_1);
			billinfoMerchant.setType(Constants.BILLINFO_TYPE_0);
			//用户扣除冻结币数
			userbalance.setFrozenNum(DoubleUtil.sub(userbalance.getFrozenNum(), transactionorder.getNumber()));
			userbalance.setTotalNum(DoubleUtil.sub(userbalance.getTotalNum(), transactionorder.getNumber()));
			userbalance.setUpdateTime(DateUtil.getCurTimeString());
			 if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1) {
					throw	new BusinessException(C2cStatusCode.E00238);
			 }
			 billinfoUser.setNowNum(userbalance.getTotalNum());
			 billinfoUser.setNowFrozenNum(userbalance.getFrozenNum());
			 billinfoUser.setNowValidNum(userbalance.getValidNum());
			//商户添加币数
			 merchantUserbalance.setValidNum(DoubleUtil.add(merchantUserbalance.getValidNum(),transactionorder.getNumber() ));
			 merchantUserbalance.setTotalNum(DoubleUtil.add(merchantUserbalance.getTotalNum(), transactionorder.getNumber()));
			 merchantUserbalance.setUpdateTime(DateUtil.getCurTimeString());
			 if(userbalanceMapper.updateByPrimaryKeySelective(merchantUserbalance)!=1) {
					throw	new BusinessException(C2cStatusCode.E00238);
			 }
			billinfoMerchant.setNowNum(merchantUserbalance.getTotalNum());
		    billinfoMerchant.setNowFrozenNum(merchantUserbalance.getFrozenNum());
		    billinfoMerchant.setNowValidNum(merchantUserbalance.getValidNum());
		}else{
			throw	new BusinessException(C2cStatusCode.E00213);
		}
		
		//更改成交数
		Transaction transaction=new Transaction();
		transaction.setTransactionNo(transactionorder.getTransactionNo());
		transaction=transactionMapper.selectOne(transaction);
		if(transactionMapper.addTransactionFinishNum(transaction.getId(),transactionorder.getNumber(),DateUtil.getCurTimeString())<=0){
			throw	new BusinessException(StatusCode.SAVEERROR)	;
		}
		 transaction=new Transaction();
		transaction.setTransactionNo(transactionorder.getTransactionNo());
		transaction=transactionMapper.selectOne(transaction);
		
		if(transaction.getSurplusNum().equals(new Double(0))) {
			transaction.setStatus(C2cConstant.TRANSACTION_STATUS_2);
		}
		transactionMapper.updateByPrimaryKeySelective(transaction);	
		
		billinfoUser.setOrderNo(transactionorder.getOrderNo());
		billinfoUser.setUserId(transactionorder.getUserId());
		billinfoUser.setCoinId(transaction.getCoinId());
		billinfoUser.setCoinName(transaction.getCoinName());
		billinfoUser.setNumber(transactionorder.getNumber());
		billinfoUser.setFee(0d);
	    billinfoUser.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
	    billinfoUser.setCreateTime(DateUtil.getCurTimeString());
	    billinfoAsyncTask.save(billinfoUser);
		
		billinfoMerchant.setOrderNo(transactionorder.getOrderNo());
		billinfoMerchant.setUserId(transactionorder.getMerchantId());
		billinfoMerchant.setCoinId(transaction.getCoinId());
		billinfoMerchant.setCoinName(transaction.getCoinName());
		billinfoMerchant.setNumber(transactionorder.getNumber());
		billinfoMerchant.setFee(0d);
	    billinfoMerchant.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
	    billinfoMerchant.setCreateTime(DateUtil.getCurTimeString());
		billinfoAsyncTask.save(billinfoMerchant);
		//更改状态
		if(transactionorderMapper.updateStatus(transactionorderId, C2cConstant.TRANSACTION_STATUS_1, 
				C2cConstant.TRANSACTION_STATUS_2,DateUtil.getCurTimeString(),null,null)==1){
			TransactionMessage userMessage =new TransactionMessage();
			TransactionMessage merchantMessage =new TransactionMessage();
		   if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
			   userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_CONFIRM_1);
			   merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_CONFIRM_2);
			}else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){
				userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_CONFIRM_3);
				merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_CONFIRM_4);
			}
		   userMessage.setSenderId(-1);
		   merchantMessage.setSenderId(-1);
			userMessage.setRecipientId(transactionorder.getUserId());
			merchantMessage.setRecipientId(transaction.getMerchantId());
			userMessage.setOrderId(transactionorder.getId());
			merchantMessage.setOrderId(transactionorder.getId());
			messageAsyncTask.send(userMessage,WebSocketConstant.WEBSOCKET_TYPE_2);
			messageAsyncTask.send(merchantMessage,WebSocketConstant.WEBSOCKET_TYPE_2);
			return Result.successResult();
		}
		throw	new BusinessException(StatusCode.UPDATEERROR);
	}
	/**
 	 * 
 	* @Title: cancelOrder
 	* @Description: TODO(取消订单)
 	* @param @param transactionorderId
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@Override
	public Result<?> cancelOrder(Integer transactionorderId) {
		 Userinfo user =RequestUtil.getCurrentUser();
		 Billinfo billinfo=null;
		 //查询是出售还购买
		Transactionorder transactionorder =	transactionorderMapper.selectByPrimaryKey(transactionorderId);
		 if(transactionorder==null){
					throw	new BusinessException(C2cStatusCode.E00211);
		 }
		 Transactiontype transactiontype=	transactiontypeMapper.selectTransactiontype(transactionorderId);
		 if(transactiontype==null){
				   throw 	new BusinessException(C2cStatusCode.E00212);
		 }
		 if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
			   if(!user.getId().equals(transactionorder.getUserId())) {
					throw	new BusinessException(StatusCode.USERERROR)	;
			   }
			}else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){//出售 取消订单 用户恢复冻结
					 if(!user.getId().equals(transactionorder.getMerchantId())) {
							throw	new BusinessException(StatusCode.USERERROR)	;
					 }
				 	billinfo=new Billinfo();
					Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(user.getId(),transactiontype.getCoinId());
					billinfo.setForNum(userbalance.getTotalNum());
					billinfo.setForFrozenNum(userbalance.getFrozenNum());
					billinfo.setForValidNum(userbalance.getValidNum());
					
					userbalance.setValidNum(DoubleUtil.add(userbalance.getValidNum(), transactionorder.getNumber()));
					userbalance.setFrozenNum(DoubleUtil.sub(userbalance.getFrozenNum(),transactionorder.getNumber()));
					userbalance.setUpdateTime(DateUtil.getCurTimeString());
					 if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1) {
							throw	new BusinessException(C2cStatusCode.E00238);
					 }
					billinfo.setOrderNo(transactionorder.getOrderNo());
					billinfo.setUserId(user.getId());
					billinfo.setUserName(user.getNickName());
					billinfo.setCoinId(transactiontype.getCoinId());
					billinfo.setCoinName(transactiontype.getCoinName());
					billinfo.setType(Constants.BILLINFO_TYPE_2);
					billinfo.setNumber(transactionorder.getNumber());
					billinfo.setFee(0d);
					billinfo.setNowNum(userbalance.getTotalNum());
					billinfo.setNowFrozenNum(DoubleUtil.sub(userbalance.getFrozenNum(), transactionorder.getNumber()));
					billinfo.setNowValidNum(DoubleUtil.add(userbalance.getValidNum(), transactionorder.getNumber()));
					billinfo.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
					billinfo.setCreateTime(DateUtil.getCurTimeString());
			}
		if( transactionorderMapper.updateStatus(transactionorderId, C2cConstant.TRANSACTIONORDER_STATUS_0,
				C2cConstant.TRANSACTIONORDER_STATUS_3,DateUtil.getCurTimeString(),null,null)==1){
			if(billinfo!=null) {
				billinfoAsyncTask.save(billinfo);
			}
			TransactionMessage userMessage =new TransactionMessage();
			TransactionMessage merchantMessage =new TransactionMessage();
			 if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
				 userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_CANCEL_1);
				 merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_CANCEL_2);
			}else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){
				 userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_CANCEL_2);
				 merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_CANCEL_1);
			}
			 userMessage.setSenderId(-1);
			 merchantMessage.setSenderId(-1);
			 userMessage.setRecipientId(transactionorder.getUserId());
			 merchantMessage.setRecipientId(transactionorder.getMerchantId());
			userMessage.setOrderId(transactionorder.getId());
			merchantMessage.setOrderId(transactionorder.getId());
			 messageAsyncTask.send(userMessage,WebSocketConstant.WEBSOCKET_TYPE_3);
			 messageAsyncTask.send(merchantMessage,WebSocketConstant.WEBSOCKET_TYPE_3);
			return Result.successResult();
		}
		throw	new BusinessException(StatusCode.UPDATEERROR.getCode(), StatusCode.UPDATEERROR.getMsg())	;
	}
	


	/**
 	 * 
 	* @Title: getTransactionorder
 	* @Description: TODO(获取订单信息)
 	* @param @param transactionorderId
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
 	public Result<TransactionorderVO> getTransactionorder(Integer transactionorderId){
 		TransactionorderVO item=transactionorderMapper.getTransactionorderById(transactionorderId);
 		if(C2cConstant.TRANSACTIONORDER_STATUS_0==item.getStatus()) {
 			int time=C2cConstant.AUTO_CANCEL_TIME-DateUtil.calLastedTime(item.getCreateTime());
 			item.setTime(time>0?time:0);
 		}else if(C2cConstant.TRANSACTIONORDER_STATUS_1==item.getStatus()) {
 			int time=C2cConstant.AUTO_ISAPPEAL_TIME-DateUtil.calLastedTime(item.getMakeTime());
 			item.setTime(time>0?time:0);
 		}else if(C2cConstant.TRANSACTIONORDER_ISAPPEAL_1==item.getIsAppeal()) {
 			int time=C2cConstant.AUTO_STATUS_9_TIME-DateUtil.calLastedTime(item.getMakeTime());
 			item.setTime(time>0?time:0);
 		}else {
 			item.setTime(0);
 		}
 		return Result.successResult(item);
 	}
 	
	/**
 	 * 
 	* @Title: getTransactionorderList
 	* @Description: TODO(查个人c2c订单)
 	* @author xzj
 	* @param @param tansactiontypeId
 	* @param @param pageNum
 	* @param @param pageSize
 	* @param @return    参数
 	* @return Result    返回类型
 	* @throws
 	 */
 	public Result<PageInfo<TransactionorderVO>> getTransactionorderList(Integer pageNum,Integer pageSize,Integer status,Integer type,Integer coinId,String startTime,String endTime) {
 		Userinfo user=RequestUtil.getCurrentUser();
 		PageHelper.startPage(pageNum, pageSize);
 		List<TransactionorderVO>	 transactionorderList= transactionorderMapper.getTransactionorderList(user.getId(), status, type, coinId, startTime, endTime);
        PageInfo<TransactionorderVO> page=new PageInfo<TransactionorderVO>(transactionorderList);
 		return Result.successResult(page);
 	}
 	
 	/**
 	 * 
 	* @Title: getTransactionorderListByMerchantId
 	* @Description: TODO(商户查询广告下面的订单列表)
 	* @author xzj
 	* @param @return    参数
 	* @return Result<PageInfo<Transactionorder>>    返回类型
 	* @throws
 	 */
 	public Result<PageInfo<TransactionorderVO>> getTransactionorderListByTransaction(Integer transactionId,Integer pageNum,Integer pageSize){
 		Transaction tran =transactionMapper.selectByPrimaryKey(transactionId);
 		if(tran==null) {
 			throw	new BusinessException(C2cStatusCode.E00213);
 		}
 		Userinfo user=RequestUtil.getCurrentUser();
 		if(!tran.getMerchantId().equals(user.getId())) {
 			throw	new BusinessException(C2cStatusCode.E00227);
 		}
 		PageHelper.startPage(pageNum, pageSize);
 		List<TransactionorderVO>	 transactionorderList=transactionorderMapper.getTransactionorderListByTransaction(transactionId);
 		 PageInfo<TransactionorderVO> page=new PageInfo<TransactionorderVO>(transactionorderList);
 	 	return Result.successResult(page);
 	}
 	/**
 	 * 
 	* @Title: autoCancelOrder
 	* @Description: TODO(自动取消订单。定时器来调用)
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@Override
	public void autoCancelOrder() {
		List<Transactionorder> list=transactionorderMapper.getAutoCancelOrder(C2cConstant.TRANSACTIONORDER_STATUS_0,C2cConstant.AUTO_CANCEL_TIME);
		for (Transactionorder transactionorder : list) {
			if( transactionorderMapper.updateStatus(transactionorder.getId(), C2cConstant.TRANSACTIONORDER_STATUS_0,
						C2cConstant.TRANSACTIONORDER_STATUS_4,DateUtil.getCurTimeString(),null,null)==1){
				 		Transactiontype transactiontype=	transactiontypeMapper.selectTransactiontype(transactionorder.getId());
						 if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){//出售 取消订单 用户恢复冻结
						 	Billinfo billinfo=new Billinfo();
							Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(transactionorder.getUserId(),transactiontype.getCoinId());
							billinfo.setForNum(userbalance.getTotalNum());
							billinfo.setForFrozenNum(userbalance.getFrozenNum());
							billinfo.setForValidNum(userbalance.getValidNum());
							userbalance.setValidNum(DoubleUtil.add(userbalance.getValidNum(), transactionorder.getNumber()));
							userbalance.setFrozenNum(DoubleUtil.sub(userbalance.getFrozenNum(),transactionorder.getNumber()));
							userbalance.setUpdateTime(DateUtil.getCurTimeString());
							 if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1) {
									throw	new BusinessException(C2cStatusCode.E00238);
							 }
							billinfo.setOrderNo(transactionorder.getOrderNo());
							Userinfo user=userinfoMapper.selectByPrimaryKey(transactionorder.getUserId());
							billinfo.setUserId(user.getId());
							billinfo.setUserName(user.getNickName());
							billinfo.setCoinId(transactiontype.getCoinId());
							billinfo.setCoinName(transactiontype.getCoinName());
							billinfo.setType(Constants.BILLINFO_TYPE_2);
							billinfo.setNumber(transactionorder.getNumber());
							billinfo.setFee(0d);
							billinfo.setNowNum(userbalance.getTotalNum());
							billinfo.setNowFrozenNum(DoubleUtil.sub(userbalance.getFrozenNum(), transactionorder.getNumber()));
							billinfo.setNowValidNum(DoubleUtil.add(userbalance.getValidNum(), transactionorder.getNumber()));
							billinfo.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
							billinfo.setCreateTime(DateUtil.getCurTimeString());
							billinfoAsyncTask.save(billinfo);
						 }		
						TransactionMessage userMessage =new TransactionMessage();
						TransactionMessage merchantMessage =new TransactionMessage();
					 	 if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
					 		userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_CANCEL_1);
					 		merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_CANCEL_2);
					 	 }else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){
					 		userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_CANCEL_3);
					 		merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_CANCEL_4);
						}
					 	userMessage.setSenderId(-1);
					 	merchantMessage.setSenderId(-1);
						userMessage.setRecipientId(transactionorder.getUserId());
						merchantMessage.setRecipientId(transactionorder.getMerchantId());
						userMessage.setOrderId(transactionorder.getId());
						merchantMessage.setOrderId(transactionorder.getId());
						messageAsyncTask.send(userMessage,WebSocketConstant.WEBSOCKET_TYPE_4);
						messageAsyncTask.send(merchantMessage,WebSocketConstant.WEBSOCKET_TYPE_4);
			}
		}
	
	}
 	
 	/**
 	 * 
 	* @Title: autoUpdateOrderStatusStayState
 	* @Description: TODO(自动更改订单为待审诉)
 	* @author xzj
 	* @param     参数
 	* @return void    返回类型
 	* @throws
 	 */
 	public void autoUpdateOrderStatusStayState() {
 		List<Transactionorder> list=transactionorderMapper.autoUpdateOrderStatusStayState(C2cConstant.TRANSACTIONORDER_STATUS_1,C2cConstant.AUTO_ISAPPEAL_TIME);
		for (Transactionorder transactionorder : list) {
			if( transactionorderMapper.updateIsAppeal(transactionorder.getId(),C2cConstant.TRANSACTIONORDER_ISAPPEAL_0, 
					C2cConstant.TRANSACTIONORDER_ISAPPEAL_1, DateUtil.getCurTimeString())==1){
						TransactionMessage userMessage =new TransactionMessage();
						TransactionMessage merchantMessage =new TransactionMessage();
					 	userMessage.setRecipientId(transactionorder.getUserId());
					 	merchantMessage.setRecipientId(transactionorder.getMerchantId());
					 	userMessage.setSenderId(-1);
					 	merchantMessage.setSenderId(-1);
				 	 	userMessage.setOrderId(transactionorder.getId());
				 	 	merchantMessage.setOrderId(transactionorder.getId());
				 		Transactiontype transactiontype = transactiontypeMapper.selectTransactiontype(transactionorder.getId());
				 	 	if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
						 		userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STAY_STATUS_1);
						 		merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STAY_STATUS_2);
						 }else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){
						 		userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STAY_STATUS_3);
						 		merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STAY_STATUS_4);
						}
						messageAsyncTask.send(userMessage,WebSocketConstant.WEBSOCKET_TYPE_5);
						messageAsyncTask.send(merchantMessage,WebSocketConstant.WEBSOCKET_TYPE_5);
			}
		}
	
 	}
 	/**
 	 * 
 	* @Title: auotUpdateOrderStatusExceptionClose
 	* @Description: TODO(自动更改订单为异常关闭)
 	* @author xzj
 	* @param     参数
 	* @return void    返回类型
 	* @throws
 	 */
 	public void auotUpdateOrderStatusExceptionClose() {
 		List<Transactionorder> list=transactionorderMapper.auotUpdateOrderStatusExceptionClose(C2cConstant.TRANSACTIONORDER_STATUS_5,C2cConstant.AUTO_STATUS_9_TIME);
		for (Transactionorder transactionorder : list) {
			
			if( transactionorderMapper.updateStatus(transactionorder.getId(), C2cConstant.TRANSACTIONORDER_STATUS_5,
						C2cConstant.TRANSACTIONORDER_STATUS_9,DateUtil.getCurTimeString(),null,null)==1){
		 		Transactiontype transactiontype=	transactiontypeMapper.selectTransactiontype(transactionorder.getId());
						if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){//出售 取消订单 用户恢复冻结
						 	Billinfo billinfo=new Billinfo();
							Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(transactionorder.getUserId(),transactiontype.getCoinId());
							billinfo.setForNum(userbalance.getTotalNum());
							billinfo.setForFrozenNum(userbalance.getFrozenNum());
							billinfo.setForValidNum(userbalance.getValidNum());
							userbalance.setValidNum(DoubleUtil.add(userbalance.getValidNum(), transactionorder.getNumber()));
							userbalance.setFrozenNum(DoubleUtil.sub(userbalance.getFrozenNum(),transactionorder.getNumber()));
							userbalance.setUpdateTime(DateUtil.getCurTimeString());
							 if(userbalanceMapper.updateByPrimaryKeySelective(userbalance)!=1) {
									throw	new BusinessException(C2cStatusCode.E00238);
							 }
							billinfo.setOrderNo(transactionorder.getOrderNo());
							Userinfo user=userinfoMapper.selectByPrimaryKey(transactionorder.getUserId());
							billinfo.setUserId(user.getId());
							billinfo.setUserName(user.getNickName());
							billinfo.setCoinId(transactiontype.getCoinId());
							billinfo.setCoinName(transactiontype.getCoinName());
							billinfo.setType(Constants.BILLINFO_TYPE_2);
							billinfo.setNumber(transactionorder.getNumber());
							billinfo.setFee(0d);
							billinfo.setNowNum(userbalance.getTotalNum());
							billinfo.setNowFrozenNum(DoubleUtil.sub(userbalance.getFrozenNum(), transactionorder.getNumber()));
							billinfo.setNowValidNum(DoubleUtil.add(userbalance.getValidNum(), transactionorder.getNumber()));
							billinfo.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
							billinfo.setCreateTime(DateUtil.getCurTimeString());
							billinfoAsyncTask.save(billinfo);
						 }	
						TransactionMessage userMessage =new TransactionMessage();
						TransactionMessage merchantMessage=new TransactionMessage();
						userMessage.setRecipientId(transactionorder.getUserId());
					 	merchantMessage.setRecipientId(transactionorder.getMerchantId());
					 	userMessage.setSenderId(-1);
					 	merchantMessage.setSenderId(-1);
					 	userMessage.setOrderId(transactionorder.getId());
					 	merchantMessage.setOrderId(transactionorder.getId());
						userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_EXCEPTION_STATUS);
						merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_EXCEPTION_STATUS);
						messageAsyncTask.send(userMessage,WebSocketConstant.WEBSOCKET_TYPE_9);
						messageAsyncTask.send(merchantMessage,WebSocketConstant.WEBSOCKET_TYPE_9);
			}
		}
 	}
 	/**
  	  * 
  	 * @Title: getBankinfoByUser
  	 * @Description: TODO(查询订单的可支付类型)
  	 * @author xzj
  	 * @param @return    参数
  	 * @return Result<Bankinfo>    返回类型
  	 * @throws
  	  */
  	 public Result<List<Bankinfo>>  getBankinfoByOrder(Integer id){
 		Transactiontype transactiontype=	transactiontypeMapper.selectTransactiontype(id);
 		Transactionorder order=transactionorderMapper.selectByPrimaryKey(id);
 		if(transactiontype.getType()==C2cConstant.TRANSACTIONTYPE_TYPE_0) {
 			 List<Bankinfo> list=bankinfoMapper.getBankinfoByUser(order.getMerchantId());
 			 return Result.successResult(list);
 		}else if(transactiontype.getType()==C2cConstant.TRANSACTIONTYPE_TYPE_1) {
 			List<Bankinfo> list=bankinfoMapper.getBankinfoByUser(order.getUserId());
			 return Result.successResult(list);
 		}else {
 			 return Result.successResult();
 		}
  	 }
  	 
 	/**
		 * 
		* @Title: getIdsByUserId
		* @Description: TODO(正在处理的订单id集合)
		* @author xzj
		* @param @param userId
		* @param @return    参数
		* @return List<Integer>    返回类型
		* @throws
		 */
  	   public Result<List<WebSocketVO>> getIdsByUserId(){
  		   		Userinfo user=RequestUtil.getCurrentUser();
  		   		List<WebSocketVO> list=new ArrayList<WebSocketVO>();
  		   		List<Integer> ids=  transactionorderMapper.getIdsByUserId(user.getId());
  		   		for (Integer id : ids) {
				  WebSocketVO ws=new WebSocketVO();
				  ws.setOrderId(id);
				  ws.setSenderId(-1);
				  ws.setRecipientId(user.getId());
				  ws.setType(0);
				  list.add(ws);
  		   		} 
  		   		return Result.successResult(list);
	   }
}
