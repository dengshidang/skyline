package com.skyline.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Inviteinfo;
import com.skyline.common.util.ObjectTool;
import com.skyline.user.business.InvitationRecordBusiness;

import lombok.Value;
/**
 * 
* <p>Title: InvitationRecordService</p>  
* <p>Description:受邀记录 </p>  
* @author kuangwenqiang  
* @date 2018年7月24日
 */
@RestController
@RequestMapping(value="/invitationRecord")
public class InvitationRecordService {
	@Autowired
	private InvitationRecordBusiness invitationRecordBusiness;
	/**
	 * 
	 * <p>Title: queryInvitationRecord</p>  
	 * <p>Description: 查询受邀记录</p>  
	 * @param userId
	 * @param grade
	 * @return
	 */
	@PostMapping(value="/queryInvitationRecord")
	public Result queryInvitationRecord(@RequestParam("userId")String userId,@RequestParam("grade")Integer grade){
		return invitationRecordBusiness.queryInvitationRecord(userId, grade);
	}

	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description:添加受邀记录 </p>  
	 * @param inviteinfo
	 * @return
	 */
	@PostMapping(value="/addInvitationRecord")
	public Result addInvitationRecord(@RequestBody Inviteinfo inviteinfo){
		return invitationRecordBusiness.addInvitationRecord(inviteinfo);
	};
	
	
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 商家申请</p>  
	 * @param inviteinfo
	 * @return
	 */
	@GetMapping(value="/businessApplication")
	public Result invitationRecord(@RequestParam("userId")String userId){
		return invitationRecordBusiness.invitationRecord(userId);
	};
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 查询商家申请</p>  
	 * @param inviteinfo
	 * @return
	 */
	/*@PostMapping(value="/queryBusinessInvit")
	public Result queryBusinessInvit(@RequestParam("userId")String userId){
	
		return null;
	};*/
	
	/**
	 * 
	 * <p>Title: queryInvitCount</p>  
	 * <p>Description: 查询受邀记录数</p>  
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/queryInvitCount")
	public Result<?> queryInvitCount(@RequestParam("userId")String userId){
		return invitationRecordBusiness.queryInvitCount(userId);
	}
}
