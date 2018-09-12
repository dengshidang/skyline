package com.skyline.user.business;


import com.skyline.common.constant.Result;
/**
 * 
* <p>Title: LoginHistoryBusiness</p>  
* <p>Description:登录历史记录 </p>  
* @author kuangwenqiang  
* @date 2018年8月2日
 */
public interface LoginHistoryBusiness {

	/**
	 * 
	 * <p>Title: queryLoginHistory</p>  
	 * <p>Description: 查询登录历史</p>  
	 * @param userId
	 * @return
	 */
	public Result queryLoginHistory( String userId);
}
