package com.skyline.c2c.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.c2c.async.BillinfoAsyncTask;
import com.skyline.c2c.async.MessageAsyncTask;
import com.skyline.c2c.business.TransactionstateBusiness;
import com.skyline.c2c.exception.BusinessException;
import com.skyline.c2c.filter.RequestUtil;
import com.skyline.c2c.mapper.TransactionMapper;
import com.skyline.c2c.mapper.TransactionorderMapper;
import com.skyline.c2c.mapper.TransactionstateMapper;
import com.skyline.c2c.mapper.TransactiontypeMapper;
import com.skyline.c2c.mapper.UserbalanceMapper;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.C2cConstant;
import com.skyline.common.constant.C2cStatusCode;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.constant.TransactionMessageConstant;
import com.skyline.common.constant.WebSocketConstant;
import com.skyline.common.entity.Billinfo;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.TransactionMessage;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.entity.Transactionstate;
import com.skyline.common.entity.Transactiontype;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.DoubleUtil;
import com.skyline.common.vo.TransactionVO;
import com.skyline.common.vo.TransactionstateVO;

@Service
@Transactional
public class TransactionstateBusinessImpl extends BaseBusinessImpl<Transactionstate, Integer> implements TransactionstateBusiness{

	@Autowired
	TransactiontypeMapper transactiontypeMapper;
	@Autowired
	TransactionMapper transactionMapper;
	@Autowired
	TransactionorderMapper transactionorderMapper;
	@Autowired
	TransactionstateMapper transactionstateMapper;
	@Autowired
	UserbalanceMapper  userbalanceMapper;
	@Autowired
	BillinfoAsyncTask billinfoAsyncTask;
	@Autowired
	MessageAsyncTask messageAsyncTask;
	@Override
	public Result<?> addTransactionstate(String orderNo,String proveUrl,String content) {
		  	Userinfo user=RequestUtil.getCurrentUser();
		  	if(transactionstateMapper.getCountTransactionstateByOrderId(orderNo,user.getId())>0) {
				throw new BusinessException(C2cStatusCode.E00234);
		  	}
		    Transactionorder order=new Transactionorder();
			order.setOrderNo(orderNo);
			order=transactionorderMapper.selectOne(order);
			Transaction transaction =transactionMapper.getTransactionByOrderId(order.getId());
			 Transactionstate transactionstate=new Transactionstate();
			 transactionstate.setOrderNo(orderNo);
			 transactionstate.setProveUrl(proveUrl);
			 transactionstate.setContent(content);
			//订单为待申诉才能够进行申诉
			if(order.getIsAppeal()!=C2cConstant.TRANSACTIONORDER_ISAPPEAL_1) {
				throw new BusinessException(C2cStatusCode.E00220);
			}
			  transactionstate.setStateUser(user.getId());
			if(user.getId().equals(order.getUserId())) {
				  transactionstate.setTakeUser(order.getMerchantId());
			}else if(user.getId().equals(order.getMerchantId())) {
				  transactionstate.setTakeUser(order.getUserId());
			}else {//异常
				throw new BusinessException(C2cStatusCode.E00221);
			}
			transactionstate.setCreateTime(DateUtil.getCurTimeString());
			transactionstate.setCoinId(transaction.getCoinId());
			transactionstate.setCoinName(transaction.getCoinName());
			transactionstate.setStatus(0);
			transactionstate.setNumber(order.getNumber());
			transactionstate.setPayWay(order.getPayWay());
			transactionstate.setPrice(order.getPrice());
			transactionstate.setTotalPrice(order.getTotalPrice());
			if(transactionstateMapper.insert(transactionstate)!=1) {
				throw new BusinessException(StatusCode.SAVEERROR);
			}
			//订单改为审核中
			if(transactionorderMapper.updateByPrimaryKeySelective(order)==1) {
				Transactiontype transactiontype=transactiontypeMapper.selectByPrimaryKey(transaction.getTransactiontypeId());
				TransactionMessage userMessage =new TransactionMessage();
				TransactionMessage merchantMessage =new TransactionMessage();
				userMessage.setSenderId(-1);
				merchantMessage.setSenderId(-1);
				userMessage.setRecipientId(user.getId());
				merchantMessage.setRecipientId(transaction.getMerchantId());
				userMessage.setOrderId(order.getId());
				merchantMessage.setOrderId(order.getId());
				if(C2cConstant.TRANSACTIONTYPE_TYPE_0==transactiontype.getType()){
					if(user.getId().equals(order.getUserId())) {
						userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STATE_STATUS_1);
						merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STATE_STATUS_2);
					}else {
						userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STATE_STATUS_3);
						merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STATE_STATUS_4);
					}
				}else if(C2cConstant.TRANSACTIONTYPE_TYPE_1==transactiontype.getType()){
					if(user.getId().equals(order.getMerchantId())) {
						userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STATE_STATUS_1);
						merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STATE_STATUS_2);
					}else {
						userMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STATE_STATUS_3);
						merchantMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_STATE_STATUS_4);
					}
				}
				messageAsyncTask.send(userMessage,WebSocketConstant.WEBSOCKET_TYPE_6);
				messageAsyncTask.send(merchantMessage,WebSocketConstant.WEBSOCKET_TYPE_6);
				return Result.successResult();
			}
			
			throw new  BusinessException(StatusCode.SAVEERROR);
	}
	/**
	 * 
	* @Title: examineTransactionstate
	* @Description: TODO(进行审批)
	* @author xzj
	* @param @param transactionstateId
	*  @param @param status
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	public Result<?> examineTransactionstate(Integer transactionstateId,Integer status,String remark) {
		Transactionorder transactionorder=transactionorderMapper.getTransactionorderByStateId(transactionstateId);
		if(transactionorder==null){
			throw	new BusinessException(C2cStatusCode.E00211);
		}
		Transactiontype transactiontype =transactiontypeMapper.selectTransactiontype(transactionorder.getId());
		 if(transactiontype==null){
			   throw 	new BusinessException(C2cStatusCode.E00212);
	    }
		 Transactionstate	 transactionstate = transactionstateMapper.selectByPrimaryKey(transactionstateId);
		 if(transactionstate==null){
			   throw 	new BusinessException(C2cStatusCode.E00213);
	    }
		 Integer type=transactiontype.getType();
		 Integer stateUser =transactionstate.getStateUser();
		 Integer userId=transactionorder.getUserId();
		 Integer merchantId=transactionorder.getMerchantId(); 
		 
		if(transactionorderMapper.updateStatus(transactionorder.getId(), C2cConstant.TRANSACTIONORDER_STATUS_1, 
					C2cConstant.TRANSACTIONORDER_STATUS_5,DateUtil.getCurTimeString(),null,null)!=1) {
		   			//状态说明订单已经结束了只更改审核状态
			   	if(transactionstateMapper.examineTransactionstate(transactionstateId, C2cConstant.TRANSACTIONSTATE_STATUS_0, status,DateUtil.getCurTimeString(),remark)==1) {
					return Result.successResult();
		    	 }
			    throw new BusinessException(C2cStatusCode.E00222);
		}else {
			// 购买类型 用户申诉成功  和 商家申诉失败
		 if((type==0 && stateUser==userId && status==C2cConstant.TRANSACTIONSTATE_STATUS_1) ||
				( type==0 && stateUser==merchantId && status==C2cConstant.TRANSACTIONSTATE_STATUS_2)) {
							    Billinfo billinfoUser=new Billinfo();
							    Billinfo billinfoMerchant=new Billinfo();
							    Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(transactionorder.getUserId(),transactiontype.getCoinId());
						        billinfoUser.setForNum(userbalance.getTotalNum());
						        billinfoUser.setForFrozenNum(userbalance.getFrozenNum());
						        billinfoUser.setForValidNum(userbalance.getValidNum());
						        Userbalance  merchantUserbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(transactionorder.getMerchantId(),transactiontype.getCoinId());
						        billinfoMerchant.setForNum(merchantUserbalance.getTotalNum());
						        billinfoMerchant.setForFrozenNum(merchantUserbalance.getFrozenNum());
						        billinfoMerchant.setForValidNum(merchantUserbalance.getValidNum());
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
								 billinfoMerchant.setNowFrozenNum(merchantUserbalance.getFrozenNum()-transactionorder.getNumber());
								 billinfoMerchant.setNowValidNum(merchantUserbalance.getValidNum());
								 billinfoUser.setOrderNo(transactionorder.getOrderNo());
								 billinfoUser.setUserId(transactionorder.getUserId());
								 billinfoUser.setCoinId(transactiontype.getCoinId());
								 billinfoUser.setCoinName(transactiontype.getCoinName());
								 billinfoUser.setNumber(transactionorder.getNumber());
								 billinfoUser.setFee(0d);
								 billinfoUser.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
								 billinfoUser.setCreateTime(DateUtil.getCurTimeString());
								 billinfoAsyncTask.save(billinfoUser);
							     billinfoMerchant.setOrderNo(transactionorder.getOrderNo());
								 billinfoMerchant.setUserId(transactionorder.getMerchantId());
								 billinfoMerchant.setCoinId(transactiontype.getCoinId());
								 billinfoMerchant.setCoinName(transactiontype.getCoinName());
								 billinfoMerchant.setNumber(transactionorder.getNumber());
								 billinfoMerchant.setFee(0d);
								 billinfoMerchant.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
								 billinfoMerchant.setCreateTime(DateUtil.getCurTimeString());
							     billinfoAsyncTask.save(billinfoMerchant);
							     if(transactionstateMapper.examineTransactionstate(transactionstateId, C2cConstant.TRANSACTIONSTATE_STATUS_0, status,DateUtil.getCurTimeString(),remark)==1) {
									return Result.successResult();
						    	 }
							    throw new BusinessException(C2cStatusCode.E00222);
		// 购买类型 用户申诉失败  和 商家申诉成功			    
		}else if((type==0 && stateUser==userId && status==C2cConstant.TRANSACTIONSTATE_STATUS_2) ||
								( type==0 && stateUser==merchantId && status==C2cConstant.TRANSACTIONSTATE_STATUS_1)) { 
								if(transactionstateMapper.examineTransactionstate(transactionstateId, C2cConstant.TRANSACTIONSTATE_STATUS_0, status,DateUtil.getCurTimeString(),remark)==1) {
													return Result.successResult();
								}
								throw new BusinessException(C2cStatusCode.E00222);
				
		// 出售类型 用户申诉成功  和 商家申诉失败	
	  }else if((type==1 && stateUser==userId && status==C2cConstant.TRANSACTIONSTATE_STATUS_1) ||
				( type==1 && stateUser==merchantId && status==C2cConstant.TRANSACTIONSTATE_STATUS_2)) {
						  	if(transactionstateMapper.examineTransactionstate(transactionstateId, C2cConstant.TRANSACTIONSTATE_STATUS_0, status,DateUtil.getCurTimeString(),remark)==1) {
								return Result.successResult();
						  	}		
						   throw new BusinessException(C2cStatusCode.E00222);
		// 出售类型 用户申诉失败  和 商家申诉成功	
	  }else if((type==1 && stateUser==userId && status==C2cConstant.TRANSACTIONSTATE_STATUS_1) ||
				( type==1 && stateUser==merchantId && status==C2cConstant.TRANSACTIONSTATE_STATUS_2)) {
						  	Billinfo billinfoUser=new Billinfo();
						    Billinfo billinfoMerchant=new Billinfo();
						    Userbalance  userbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(transactionorder.getUserId(),transactiontype.getCoinId());
					        billinfoUser.setForNum(userbalance.getTotalNum());
					        billinfoUser.setForFrozenNum(userbalance.getFrozenNum());
					        billinfoUser.setForValidNum(userbalance.getValidNum());
					        Userbalance  merchantUserbalance=userbalanceMapper.getUserbalanceByUserIdForUpdate(transactionorder.getMerchantId(),transactiontype.getCoinId());
					        billinfoMerchant.setForNum(merchantUserbalance.getTotalNum());
					        billinfoMerchant.setForFrozenNum(merchantUserbalance.getFrozenNum());
					        billinfoMerchant.setForValidNum(merchantUserbalance.getValidNum());
							billinfoUser.setType(Constants.BILLINFO_TYPE_1);
							billinfoMerchant.setType(Constants.BILLINFO_TYPE_2);
							//商家添加币数
							 merchantUserbalance.setValidNum(DoubleUtil.add(merchantUserbalance.getValidNum(),transactionorder.getNumber() ));
							 merchantUserbalance.setTotalNum(DoubleUtil.add(merchantUserbalance.getTotalNum(), transactionorder.getNumber()));
							 merchantUserbalance.setUpdateTime(DateUtil.getCurTimeString());
							 if(userbalanceMapper.updateByPrimaryKeySelective(merchantUserbalance)!=1) {
									throw	new BusinessException(C2cStatusCode.E00238);
							 }
							billinfoMerchant.setNowNum(merchantUserbalance.getTotalNum());
							billinfoMerchant.setNowFrozenNum(merchantUserbalance.getFrozenNum());
							billinfoMerchant.setNowValidNum(merchantUserbalance.getValidNum());
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
							 billinfoUser.setOrderNo(transactionorder.getOrderNo());
							 billinfoUser.setUserId(transactionorder.getUserId());
							 billinfoUser.setCoinId(transactiontype.getCoinId());
							 billinfoUser.setCoinName(transactiontype.getCoinName());
							 billinfoUser.setNumber(transactionorder.getNumber());
							 billinfoUser.setFee(0d);
							 billinfoUser.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
							 billinfoUser.setCreateTime(DateUtil.getCurTimeString());
							 billinfoAsyncTask.save(billinfoUser);
						     billinfoMerchant.setOrderNo(transactionorder.getOrderNo());
							 billinfoMerchant.setUserId(transactionorder.getMerchantId());
							 billinfoMerchant.setCoinId(transactiontype.getCoinId());
							 billinfoMerchant.setCoinName(transactiontype.getCoinName());
							 billinfoMerchant.setNumber(transactionorder.getNumber());
							 billinfoMerchant.setFee(0d);
							 billinfoMerchant.setBehavior(Constants.BILLINFO_BEHAVIOR_1);
							 billinfoMerchant.setCreateTime(DateUtil.getCurTimeString());
						     billinfoAsyncTask.save(billinfoMerchant);
							 if(transactionstateMapper.examineTransactionstate(transactionstateId, C2cConstant.TRANSACTIONSTATE_STATUS_0, status,DateUtil.getCurTimeString(),remark)==1) {
									return Result.successResult();
						    }
							 throw new BusinessException(C2cStatusCode.E00222);
	  }else {
		  					throw new BusinessException(C2cStatusCode.E00222);
	  }
	}
}
	@Override
	public Result<PageInfo<TransactionstateVO>> getListTransactionstate(Integer pageNum,Integer pageSize,String orderNo,String stateUserAccount,String takeUserAccount,Integer payWayType,
			Integer status,String startTime,String endTime) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<TransactionstateVO>	 list= transactionstateMapper.getListTransactionstate(orderNo, stateUserAccount, takeUserAccount, payWayType,
				 status, startTime, endTime);
	    PageInfo<TransactionstateVO> page=new PageInfo<TransactionstateVO>(list);
		return Result.successResult(page);
	}
	/**
	 * 
	* @Title: revokeTransactionstate
	* @Description: TODO(撤消审诉)
	* @author xzj
	* @param @param id
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	public Result<?> revokeTransactionstate(Integer id){
		Userinfo user=RequestUtil.getCurrentUser();
		Transactionstate state = transactionstateMapper.selectByPrimaryKey(id);
		if(state.getStatus()!=C2cConstant.TRANSACTIONSTATE_STATUS_0) {
			throw	new BusinessException(C2cStatusCode.E00232);
		}
		if(!user.getId().equals(state.getStateUser())) {
			throw	new BusinessException(C2cStatusCode.E00227);
		}
		Transactionorder transactionorder=transactionorderMapper.getTransactionorderByStateId(id);
		if(transactionorder==null){
			throw	new BusinessException(C2cStatusCode.E00211);
		}
		Transactiontype transactiontype =transactiontypeMapper.selectTransactiontype(transactionorder.getId());
		 if(transactiontype==null){
			   throw 	new BusinessException(C2cStatusCode.E00212);
	     }
		if(transactionstateMapper.examineTransactionstate(id, C2cConstant.TRANSACTIONSTATE_STATUS_0, C2cConstant.TRANSACTIONSTATE_STATUS_3,DateUtil.getCurTimeString(),null)==1) {
			TransactionMessage stateUserMessage =new TransactionMessage();
			TransactionMessage takeUserMessage =new TransactionMessage();
			if(transactionorder.getUserId()==user.getId()) {
				stateUserMessage.setRecipientId(transactionorder.getUserId());
				takeUserMessage.setRecipientId(transactionorder.getMerchantId());
			}else {
				stateUserMessage.setRecipientId(transactionorder.getMerchantId());
				takeUserMessage.setRecipientId(transactionorder.getUserId());
			}
		 	stateUserMessage.setSenderId(-1);
		 	takeUserMessage.setSenderId(-1);
		 	stateUserMessage.setOrderId(transactionorder.getId());
		 	takeUserMessage.setOrderId(transactionorder.getId());
			stateUserMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_REVOKE_STATUS_1);
			takeUserMessage.setMessage(TransactionMessageConstant.MESSAGE_C2C_AUTO_REVOKE_STATUS_2);
			messageAsyncTask.send(stateUserMessage,WebSocketConstant.WEBSOCKET_TYPE_5);
			messageAsyncTask.send(takeUserMessage,WebSocketConstant.WEBSOCKET_TYPE_5);
			return Result.successResult();
		}
		throw new BusinessException(C2cStatusCode.E00222);
		 
	}
	
	/**
	 * 
	* @Title: getTransactionstate
	* @Description: TODO(查询单条信息)
	* @author xzj
	* @param @param orderId
	* @param @param id
	* @param @return    参数
	* @return Result<Transactionstate>    返回类型
	* @throws
	 */
	public Result<Transactionstate> getTransactionstateByOrderNo(String  orderNo){
		Userinfo user =RequestUtil.getCurrentUser();
		Transactionstate	 state= transactionstateMapper.getTransactionstateByOrderNo(orderNo,user.getId(),C2cConstant.TRANSACTIONSTATE_STATUS_0);
		 return Result.successResult(state);
	}
	
	/**
	 * 
	* @Title: getTransactionstate
	* @Description: TODO(查询单条信息)
	* @author xzj
	* @param @param orderNo
	* @param @param id
	* @param @return    参数
	* @return Result<Transactionstate>    返回类型
	* @throws
	 */
	public Result<Transactionstate> getTransactionstate(Integer id){
	   return Result.successResult(transactionstateMapper.selectByPrimaryKey(id));
	}
}
