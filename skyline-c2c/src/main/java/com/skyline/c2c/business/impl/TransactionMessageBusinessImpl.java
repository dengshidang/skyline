package com.skyline.c2c.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skyline.c2c.business.TransactionMessageBusiness;
import com.skyline.c2c.exception.BusinessException;
import com.skyline.c2c.filter.RequestUtil;
import com.skyline.c2c.mapper.TransactionMessageMapper;
import com.skyline.c2c.mapper.TransactionorderMapper;
import com.skyline.c2c.service.WebSocketService;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.constant.WebSocketConstant;
import com.skyline.common.entity.TransactionMessage;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.DateUtil;
import com.skyline.common.vo.WebSocketVO;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

@Service
@Transactional
public class TransactionMessageBusinessImpl extends BaseBusinessImpl<TransactionMessage,Integer> implements TransactionMessageBusiness {
		@Autowired
		TransactionMessageMapper transactionMessageMapper;
		@Autowired
		TransactionorderMapper transactionorderMapper;
		
		@Autowired
		WebSocketService webSocketService;
		/**
		 * 
		* @Title: saveMessage
		* @Description: TODO(保存通讯信息)
		* @param @param message
		* @param @return    参数
		* @return Result    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public Result<?> saveMessage(TransactionMessage message,Integer  type){
			String time=DateUtil.getCurTimeString();
			message.setCreateTime(time);
			if(transactionMessageMapper.insert(message)!=1){
				throw new BusinessException(StatusCode.SAVEERROR);
			}
			WebSocketVO webSocketVO=new WebSocketVO();
			webSocketVO.setRecipientId(message.getRecipientId());
			webSocketVO.setSenderId(message.getSenderId());
			webSocketVO.setType(type);
			webSocketVO.setMsg(message.getMessage());
			webSocketVO.setOrderId(message.getOrderId());
			webSocketVO.setCreateTime(time);
			webSocketService.send(webSocketVO);
			return Result.successResult();
		}
		/**
		 * 
		* @Title: saveMessage
		* @Description: TODO(保存通讯信息)
		* @param @param message
		* @param @return    参数
		* @return Result    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public Result<TransactionMessage> saveMessage(Integer transactionorderId,String msg){
			Userinfo user=RequestUtil.getCurrentUser();
			TransactionMessage message=new TransactionMessage();
			message.setSenderId(user.getId());
			message.setOrderId(transactionorderId);
			Transactionorder  transactionorder = transactionorderMapper.selectByPrimaryKey(transactionorderId);
			if(user.getId().equals(transactionorder.getUserId())) {
				message.setRecipientId(transactionorder.getMerchantId());
			}else if(user.getId().equals(transactionorder.getMerchantId())) {
				message.setRecipientId(transactionorder.getUserId());
			}
			message.setMessage(msg);
			String time=DateUtil.getCurTimeString();
			message.setCreateTime(time);
			if(transactionMessageMapper.insert(message)!=1){
				throw new BusinessException(StatusCode.SAVEERROR);
			}
			WebSocketVO webSocketVO=new WebSocketVO();
			webSocketVO.setRecipientId(message.getRecipientId());
			webSocketVO.setSenderId(message.getSenderId());
			webSocketVO.setType(WebSocketConstant.WEBSOCKET_TYPE_20);
			webSocketVO.setMsg(message.getMessage());
			webSocketVO.setOrderId(transactionorderId);
			webSocketVO.setCreateTime(time);
			webSocketService.send(webSocketVO);
			return Result.successResult(message);
		}
		/**
		 * 
		* @Title: saveMessage
		* @Description: TODO(批量保存)
		* @param @param messageList
		* @param @return    参数
		* @return Result    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public Result<?> saveMessage(List<TransactionMessage> messageList){
			for (TransactionMessage message : messageList) {
				message.setCreateTime(DateUtil.getCurTimeString());
			}
			if(transactionMessageMapper.insertList(messageList)<=0){
				throw new BusinessException(StatusCode.SAVEERROR);
			}
			return Result.successResult();
		}
		
		/**
		 * 
		* @Title: getMessageList
		* @Description: TODO(获取通讯信息)
		* @param @param recipientId  接收者
		* @param @param senderId  发送者
		* @param @param type
		* @param @param isRead
		* @param @return    参数
		* @return Result    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public Result<List<TransactionMessage>> getMessageList(Integer orderId){
			Userinfo user=RequestUtil.getCurrentUser();
			TransactionMessage message=new TransactionMessage();
			message.setOrderId(orderId);
			List<TransactionMessage> list=	transactionMessageMapper.getMessageList(user.getId(),orderId);
			return Result.successResult(list);
		}
		
		/**
		 * 
		* @Title: updateIsRead
		* @Description: TODO(更改为已读)
		* @param @param messageId
		* @param @return    参数
		* @return Result    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public Result<?> updateIsRead(Integer messageId){
			TransactionMessage message=new TransactionMessage();
			message.setId(messageId);
			if(transactionMessageMapper.updateByPrimaryKeySelective(message)==1){
				return Result.successResult();
			}
			throw new BusinessException(StatusCode.UPDATEERROR);
		}
	
}
