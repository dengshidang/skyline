package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Usercommercial;
import com.skyline.common.entity.Useridentitie;
import com.skyline.common.util.ObjectTool;

@FeignClient(value = "skyline-user")
public interface UserCertifiedService {
	/**
	 * 
	 * <p>Title: certification</p>  
	 * <p>Description: 实名认证</p>  
	 * @return
	 */
	@PostMapping(value="/userCertified/realNameCertification")
	public  Result realNameCertification(@RequestBody Useridentitie useridentitie);
	
	/**
	 * 
	 * <p>Title: certification</p>  
	 * <p>Description: 认证信息查询</p>  
	 * @return
	 */
	@PostMapping(value="/userCertified/queryCertifiedcation")
	public  Result queryCertifiedcation(@RequestParam("userId") String userId,@RequestParam("status") String status,@RequestParam("pageSize") int  pageSize,@RequestParam("pageNum") int pageNum);
	
	/**
	 * 
	 * <p>Title: certification</p>  
	 * <p>Description: 认证审核</p>  
	 * @return
	 */
	@PostMapping(value="/userCertified/updateCertifiedcation")
	public  Result updateCertifiedcation(@RequestParam("id") String id,@RequestParam("status") String status,@RequestParam("remark")String remark);
	
	/**
	 * 
	 * <p>Title: queryBindBankInfo</p>  
	 * <p>Description:查询用户关联的银行卡类型信息</p>  
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/userCertified/queryBindBankInfo")
	public Result queryBindBankInfo(@RequestParam("userId") String userId);
	
	
	/**
	 * 
	 * <p>Title: bindBank</p>  
	 * <p>Description: 绑定银行卡信息</p>  
	 * @param bankinfo
	 * @param payPwd
	 * @return
	 */
	@PostMapping(value="/userCertified/bindBank")
	public Result  bindBank(@RequestBody Bankinfo bankinfo,@RequestParam("payPwd") String payPwd);
	
	
	
	/**
	 * 
	 * <p>Title: QueryUsercommercial</p>  
	 * <p>Description:查询商家申请记录 </p>  
	 * @param usercommercial
	 * @return
	 */
	@PostMapping(value="/userCertified/queryUsercommercial")
	public Result queryUsercommercial(@RequestParam("startTime")String startTime,@RequestParam("endTime")String endTime,
			@RequestParam("userId")Integer userId,@RequestParam("status")Integer status,
			@RequestParam("pageSize")Integer pageSize,@RequestParam("pageNum")Integer pageNum);
	
	/**
	 * 
	 * <p>Title: QueryUsercommercial</p>  
	 * <p>Description:修改商家审核状态 </p>  
	 * @param usercommercial
	 * @return
	 */
	@PostMapping(value="/userCertified/updateBusinessStatus")
	public Result updateBusinessStatus(@RequestParam("id") Integer id,@RequestParam("status") String status,@RequestParam("remark") String remark);
	/**
	 * 
	 * <p>Title: businessApplication</p>  
	 * <p>Description:商家申请 </p>  
	 * @param usercommercial
	 * @return
	 */
	@PostMapping(value="/userCertified/businessApplication")
	public Result businessApplication(@RequestBody Usercommercial usercommercial);
	
	
	/**
	 * 
	 * <p>Title: updatePayWay</p>  
	 * <p>Description:修改微信或 支付宝绑定信息 </p>  
	 * @param id
	 * @param account
	 * @param imgUrl
	 * @param payPwd
	 * @param type
	 * @return
	 */
	@PostMapping(value="/userCertified/updatePayWay")
	public Result updatePayWay(@RequestParam("id")String id,@RequestParam("account")String account,@RequestParam("imgUrl")String imgUrl,
			@RequestParam("payPwd")String payPwd,@RequestParam("type") String type);
	
	
	/**
	 * 
	 * <p>Title: updateBankPayWay</p>  
	 * <p>Description:修改银行卡绑定信息 </p>  
	 * @param bankinfo
	 * @param payPwd
	 * @return
	 */
	@PostMapping(value="/userCertified/updateBankPayWay")
	public Result updateBankPayWay(@RequestBody Bankinfo  bankinfo, @RequestParam("payPwd")String payPwd);
}
