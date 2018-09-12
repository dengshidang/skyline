package com.skyline.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Inviteinfo;
import com.skyline.common.util.MyMapper;

public interface InviteinfoMapper extends MyMapper<Inviteinfo>{

	/**
	 * 
	 * <p>Title: queryInvitationRecord</p>  
	 * <p>Description:查询邀请记录  一级</p>  
	 * @return
	 */
	List<Inviteinfo> queryInvitationRecordClassOne(@Param("userId") String userId);
	
	/**
	 * 
	 * <p>Title: queryInvitationRecord</p>  
	 * <p>Description:查询邀请记录  二级</p>  
	 * @return
	 */
	List<Inviteinfo> queryInvitationRecordClassTwo(@Param("userId") String userId);
	
	/**
	 * 
	 * <p>Title: queryInvitationRecord</p>  
	 * <p>Description:查询邀请记录 三级 </p>  
	 * @return
	 */
	List<Inviteinfo> queryInvitationRecordClassThree(@Param("userId") String userId);
	
	/**
	 * 
	 * <p>Title: addInvitationRecord</p>  
	 * <p>Description:添加邀请记录 </p>  
	 * @param inviteinfo
	 * @return
	 */
	int addInvitationRecord(Inviteinfo inviteinfo);
}
