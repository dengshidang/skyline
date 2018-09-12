package com.skyline.user.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skyline.common.constant.AdditionalFunctionStatusCode;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.SystemConstants;
import com.skyline.common.entity.Inviteinfo;
import com.skyline.common.entity.Usercommercial;
import com.skyline.common.entity.Useridentitie;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.ObjectTool;
import com.skyline.user.business.InvitationRecordBusiness;
import com.skyline.user.exception.BusinessException;
import com.skyline.user.mapper.InviteinfoMapper;
import com.skyline.user.mapper.UsercommercialMapper;
import com.skyline.user.mapper.UseridentitieMapper;
import com.skyline.user.mapper.UserinfoMapper;
@Service
@Transactional
public class InvitationRecordBusinessImpl implements InvitationRecordBusiness {

	@Autowired
	 private  InviteinfoMapper inviteinfoMapper;
	@Autowired
	private UserinfoMapper userinfoMapper;
	@Autowired
	private  UseridentitieMapper useridentitieMapper;
	@Autowired 
	private UsercommercialMapper usercommercialMapper;
	@Override
	public Result queryInvitationRecord(String userId, Integer grade) {
		//查询当前用户所邀用户 一级
		if(grade==SystemConstants.INVATATION_RECORD_CLASS_1){
			List<Inviteinfo> list_Inviteinfo = inviteinfoMapper.queryInvitationRecordClassOne(userId);
			return Result.successResult(list_Inviteinfo);
		//查询当前用户所邀用户下所有邀请的用户  二级
		}else if(grade==SystemConstants.INVATATION_RECORD_CLASS_2){
			List<Inviteinfo> list_Inviteinfo = inviteinfoMapper.queryInvitationRecordClassTwo(userId);
			return Result.successResult(list_Inviteinfo);
			//查询当前用户所邀用户下所有邀请的用户下的所有的邀请用户 三级	
		}else if(grade==SystemConstants.INVATATION_RECORD_CLASS_3){
			List<Inviteinfo> list_Inviteinfo = inviteinfoMapper.queryInvitationRecordClassThree(userId);
			return Result.successResult(list_Inviteinfo);
		}else {
			throw new BusinessException(AdditionalFunctionStatusCode.E312.getCode(),
					AdditionalFunctionStatusCode.E312.getMsg());
		}
	}
	@Override
	public Result addInvitationRecord(Inviteinfo inviteinfo) {
		/*String  parentInvite = inviteinfo.getParentInvite();
		//根据邀请码  查询用户id
		Userinfo  userinfo = userinfoMapper.queryUserIdByInvite(parentInvite);
		if(userinfo==null||ObjectTool.isBlank(userinfo.getId())){
			throw new BusinessException(AdditionalFunctionStatusCode.E313.getCode(),
					AdditionalFunctionStatusCode.E313.getMsg());
		}
		inviteinfo.setParentId(userinfo.getId());
		//添加到邀请记录表里面
		int rs = inviteinfoMapper.addInvitationRecord(inviteinfo);
		if(rs<1){
			throw new BusinessException(AdditionalFunctionStatusCode.E301.getCode(),
					AdditionalFunctionStatusCode.E301.getMsg());
		}
		return Result.successResult();*/
		return null;
	}
	@Override
	public Result invitationRecord(String userId) {
		Useridentitie  record = new Useridentitie();
		record.setUserId(Integer.parseInt(userId));
		record.setStatus(SystemConstants.CERTIFIEDCATION_TYPE_1);
		Useridentitie useridentitie = useridentitieMapper.selectOne(record);
		if(useridentitie==null){
			throw new BusinessException(AdditionalFunctionStatusCode.E314.getCode(),
					AdditionalFunctionStatusCode.E314.getMsg());
		}
		Usercommercial usercommercial = new Usercommercial();
		usercommercial.setUserId(useridentitie.getUserId());
		usercommercial.setStatus(SystemConstants.CERTIFIEDCATION_TYPE_0);
		usercommercial.setCreateTime(DateUtil.getNowDate());
		// TODO Auto-generated method stub
		int res = usercommercialMapper.insert(usercommercial);
		if(res<1){
			throw new BusinessException(AdditionalFunctionStatusCode.E301.getCode(),
					AdditionalFunctionStatusCode.E301.getMsg());
		}
		return Result.successResult();
	}
	@Override
	public Result queryBusinessInvit(String userId) {
		// TODO Auto-generated method stub
		Usercommercial record = new Usercommercial();
		record.setUserId(Integer.parseInt(userId));
		Usercommercial usercommercial  = 	usercommercialMapper.selectOne(record);
		 return Result.successResult(usercommercial);
	}
	@Override
	public Result<?> queryInvitCount(String userId) {
		Inviteinfo  record = new Inviteinfo();
		record.setParentId(Integer.parseInt(userId));
		int count = inviteinfoMapper.selectCount(record);
		return Result.successResult(count);
	}
}
