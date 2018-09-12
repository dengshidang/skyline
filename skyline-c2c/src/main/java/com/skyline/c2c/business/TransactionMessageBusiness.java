package com.skyline.c2c.business;

import java.util.List;

import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.TransactionMessage;

public interface TransactionMessageBusiness  extends  BaseBusiness<TransactionMessage, Integer> {
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
	public Result<?> saveMessage(TransactionMessage message,Integer  type);
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
	public Result<TransactionMessage> saveMessage(Integer transactionorderId,String msg);
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
	public Result<?> saveMessage(List<TransactionMessage> messageList);
	
	/**
	 * 
	* @Title: getMessageList
	* @Description: TODO(获取通讯信息)
	* @param @param orderId  订单id
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public Result<List<TransactionMessage>> getMessageList(Integer orderId);
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
	public Result<?> updateIsRead(Integer messageId);
	
	
}
