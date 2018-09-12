package com.skyline.webapi.controller;

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
import com.skyline.webapi.service.InvitationRecordService;
/**
 * 
* <p>Title: InvitationRecordController</p>  
* <p>Description: 邀请记录</p>  
* @author kuangwenqiang  
* @date 2018年7月24日
 */
@RestController
@RequestMapping(value ="/invitationRecord")
public class InvitationRecordController {
	
		@Autowired
		private InvitationRecordService invitationRecordService;
		
	
	/**
	 * 
	 * <p>Title: queryInvitationRecord</p>  
	 * <p>Description: </p>  
	 * @param userId（当前用户id）
	 * @param grade（层级  界面上选择 一 二 三  分别传 1，2，3  即可）
	 * @return
	 */
	@PostMapping(value = "/queryInvitationRecord")
	public Result  queryInvitationRecord(@RequestParam("userId")String userId,@RequestParam("grade")Integer grade){
		if(ObjectTool.isBlank(userId,grade)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return invitationRecordService.queryInvitationRecord(userId, grade);
	};
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 添加受邀记录</p>  
	 * @param inviteinfo
	 * @return
	 */
	@PostMapping(value="/addInvitationRecord")
	public Result addInvitationRecord(@RequestBody Inviteinfo inviteinfo){
		if(inviteinfo==null||ObjectTool.isBlank(inviteinfo.getParentInvite(),
				inviteinfo.getPuisneId(),inviteinfo.getPuisneInvite())){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return invitationRecordService.addInvitationRecord(inviteinfo);
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
		if(ObjectTool.isBlank(userId)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return invitationRecordService.invitationRecord(userId);
	};
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 查询商家申请</p>  
	 * @param inviteinfo
	 * @return
	 */
/*	@PostMapping(value="/queryBusinessInvit")
	public Result queryBusinessInvit(@RequestParam("userId")String userId){
		if(ObjectTool.isBlank(userId)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return invitationRecordService.queryBusinessInvit(userId);
	};
*/
	
	/**
	 * 
	 * <p>Title: queryInvitCount</p>  
	 * <p>Description: 查询受邀记录数</p>  
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/queryInvitCount")
	public Result<?> queryInvitCount(@RequestParam("userId")String userId){
		if(ObjectTool.isBlank(userId)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return invitationRecordService.queryInvitCount(userId);
	}
	
}
