package com.skyline.c2c.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.skyline.c2c.business.TransactiontypeBusiness;
import com.skyline.c2c.exception.BusinessException;
import com.skyline.c2c.mapper.TransactiontypeMapper;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.C2cConstant;
import com.skyline.common.constant.C2cStatusCode;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Transactiontype;
import com.skyline.common.util.DateUtil;


@Service
@Transactional
public class TransactionTypeBusinessImpl extends BaseBusinessImpl<Transactiontype, Integer> implements TransactiontypeBusiness{
	
	@Autowired
	TransactiontypeMapper transactiontypeMapper;
	/**
	 * 
	* @Title: saveTransactiontype
	* @Description: TODO(新增c2c交易类型)
	* @param @param transactiontype
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<?> saveTransactiontype(Transactiontype transactiontype) {
		
		Transactiontype params=new Transactiontype();
		params.setCoinId(transactiontype.getCoinId());
		params.setType(transactiontype.getType());
		if(transactiontypeMapper.selectOne(params)!=null){//存在
			throw	new BusinessException(C2cStatusCode.E00216)	;	
		}
		transactiontype.setStatus(C2cConstant.TRANSACTIONTYPE_STATUS_0);
		transactiontype.setCreateTime(DateUtil.getCurTimeString());
		if(transactiontypeMapper.insertSelective(transactiontype)==1){
			return Result.successResult();
		}
		  throw	new BusinessException(StatusCode.SAVEERROR)	;	
	}
	/**
	 * 
	* @Title: updateTransactiontypeStatus
	* @Description: TODO(更改状态，启用，禁用)
	* @param @param status
	* @param @param transactiontypeId
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<?> updateTransactiontypeStatus(Integer status,Integer transactiontypeId) {
		Transactiontype  transactiontype =transactiontypeMapper.selectByPrimaryKey(transactiontypeId);
		if(transactiontype==null){
			throw	new BusinessException(C2cStatusCode.E00213)	;	
		}
		transactiontype.setStatus(status);
		transactiontype.setUpdateTime(DateUtil.getCurTimeString());
		if(transactiontypeMapper.updateByPrimaryKeySelective(transactiontype)==1){
			return Result.successResult();
		}
		throw	new BusinessException(StatusCode.UPDATEERROR)	;
	}
	/**
	 * 
	* @Title: updateTransactiontype
	* @Description: TODO(更改基本信息)
	* @param @param transactiontype
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<?> updateTransactiontype(Transactiontype transactiontype) {
		Transactiontype  item  =transactiontypeMapper.selectByPrimaryKey(transactiontype.getId());
		item.setBuyMax(transactiontype.getBuyMax());
		item.setBuyMin(transactiontype.getBuyMin());
		item.setSellMax(transactiontype.getSellMax());
		item.setSellMin(transactiontype.getSellMax());
		item.setUpdateTime(DateUtil.getCurTimeString());
		if( transactiontypeMapper.updateByPrimaryKeySelective(transactiontype)==1){
			return Result.successResult();
		}
		throw	new BusinessException(StatusCode.UPDATEERROR)	;
	}
	/**
	 * 
	* @Title: deleteTransactiontype
	* @Description: TODO(删除)
	* @param @param transactiontypeId
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<?> deleteTransactiontype(Integer transactiontypeId) {
		if(	transactiontypeMapper.deleteByPrimaryKey(transactiontypeId)==1){
			return Result.successResult();
		}
		throw	new BusinessException(StatusCode.DELETEERROR)	;
	}
	/**
	 * 
	* @Title: deleteTransactiontype
	* @Description: TODO(删除)
	* @param @param transactiontypeId
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@Override
	public Result<List<Transactiontype>> getTransactiontypeList(Integer type) {
		Transactiontype transactiontype=new Transactiontype();
		transactiontype.setType(type);
		List<Transactiontype> list=transactiontypeMapper.select(transactiontype);
		return Result.successResult(list);
	}




}
