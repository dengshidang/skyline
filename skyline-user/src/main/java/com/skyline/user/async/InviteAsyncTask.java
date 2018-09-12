package com.skyline.user.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.skyline.common.constant.AdditionalFunctionStatusCode;
import com.skyline.common.entity.Inviteinfo;
import com.skyline.common.entity.LoginHistory;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.ObjectTool;
import com.skyline.user.exception.BusinessException;
import com.skyline.user.mapper.InviteinfoMapper;
import com.skyline.user.mapper.LoginHistoryMapper;
import com.skyline.user.mapper.UserinfoMapper;

@Component
public class InviteAsyncTask {
	
	@Autowired
	private UserinfoMapper userinfoMapper;
	
	@Autowired
	 private  InviteinfoMapper inviteinfoMapper;
	
	@Autowired
	 private LoginHistoryMapper loginHistoryMapper;
	
	@Async
    public void save(String pusineInvite,String parentInvit ){
		String pusineId = userinfoMapper.queryUserIdByInvite(pusineInvite);
		String parentId  = userinfoMapper.queryUserIdByInvite(parentInvit);
		if(ObjectTool.isBlank(parentId)){
			throw new BusinessException(AdditionalFunctionStatusCode.E313.getCode(),
					AdditionalFunctionStatusCode.E313.getMsg());
		}
		Inviteinfo invite = new Inviteinfo();
		if(ObjectTool.isBlank(pusineId)){
			throw new BusinessException(AdditionalFunctionStatusCode.E313.getCode(),
					AdditionalFunctionStatusCode.E313.getMsg());
		}
		invite.setParentId(Integer.parseInt(parentId));
		invite.setParentInvite(parentInvit);
		invite.setPuisneInvite(pusineInvite);
		invite.setPuisneId(Integer.parseInt(pusineId));
		invite.setCreateTime(DateUtil.getNowDate());
		if(inviteinfoMapper.addInvitationRecord(invite)!=1){
			throw new BusinessException(AdditionalFunctionStatusCode.E317.getCode(),
					AdditionalFunctionStatusCode.E317.getMsg());
		}
    }
	@Async
	public void setLoginHistory(String ipAddress,Integer userId){
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setIp(ipAddress);
		loginHistory.setLoginWay(0);
		loginHistory.setLoginTime(DateUtil.getNowDate());
		loginHistory.setUserId(userId);
		loginHistory.setStatus(0);
		loginHistoryMapper.insert(loginHistory);
	}
	

}
