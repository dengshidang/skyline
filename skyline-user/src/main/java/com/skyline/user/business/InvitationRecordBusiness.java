package com.skyline.user.business;


import com.skyline.common.constant.Result;
import com.skyline.common.entity.Inviteinfo;

public interface InvitationRecordBusiness {
	/**
	 * 
	 * <p>Title: queryInvitationRecord</p>  
	 * <p>Description:查询当前用户一级或二级或三级所邀用户 </p>  
	 * @param userId
	 * @param grade
	 * @return
	 */
	public Result queryInvitationRecord(String userId,Integer grade);
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description:添加受邀记录 </p>  
	 * @param inviteinfo
	 * @return
	 */
	public Result addInvitationRecord(Inviteinfo inviteinfo);
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 商家申请</p>  
	 * @param inviteinfo
	 * @return
	 */
	public Result invitationRecord(String userId);
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description: 查询商家申请</p>  
	 * @param inviteinfo
	 * @return
	 */
	public Result queryBusinessInvit(String userId);
	
	
	/**
	 * 
	 * <p>Title: queryInvitCount</p>  
	 * <p>Description: 查询邀请记录数</p>  
	 * @param userId
	 * @return
	 */
	public Result<?> queryInvitCount(String userId);
}
