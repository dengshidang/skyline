package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Inviteinfo;
import com.skyline.common.util.ObjectTool;

@FeignClient(value="skyline-user")
public interface InvitationRecordService {

	
	/**
	 * 
	 * <p>Title: queryInvitationRecord</p>  
	 * <p>Description: </p>  
	 * @param userId（当前用户id）
	 * @param grade（层级  界面上选择 一 二 三  分别传 1，2，3  即可）
	 * @return
	 */
	@RequestMapping(value="/invitationRecord/queryInvitationRecord",method=RequestMethod.POST)
	public Result queryInvitationRecord(@RequestParam("userId")String userId,@RequestParam("grade")Integer grade);
	
	
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 添加受邀记录</p>  
	 * @param inviteinfo
	 * @return
	 */
	@PostMapping(value="/invitationRecord/addInvitationRecord")
	public Result addInvitationRecord(@RequestBody Inviteinfo inviteinfo);
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 商家申请</p>  
	 * @param inviteinfo
	 * @return
	 */
	@GetMapping(value="/invitationRecord/businessApplication")
	public Result invitationRecord(@RequestParam("userId")String userId);
	
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 查询商家申请</p>  
	 * @param inviteinfo
	 * @return
	 */
	@PostMapping(value="/invitationRecord/queryBusinessInvit")
	public Result queryBusinessInvit(@RequestParam("userId")String userId);
	
	
	/**
	 * 
	 * <p>Title: queryInvitCount</p>  
	 * <p>Description: 查询受邀记录数</p>  
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/invitationRecord/queryInvitCount")
	public Result<?> queryInvitCount(@RequestParam("userId")String userId);
	
}
