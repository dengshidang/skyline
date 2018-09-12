package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.constant.UserStatusCode;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.ObjectTool;
import com.skyline.common.util.RegexUtil;

@FeignClient(value = "skyline-user")
public interface UserinfoService {
	/**
	 * 
	* @Title: imgCode
	* @Description: TODO(获取图片验证码)
	* @author xzj
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@GetMapping(value="/userinfo/imgCode")
    public Result<?> imgCode();
	/**
	 * 
	* @Title: sendSmsCode
	* @Description: TODO(获取短信验证码)
	* @author xzj
	* @param @param mobile
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value="/userinfo/sendSmsCode")
	 public Result<?> sendSmsCode(@RequestParam("phone")String phone,@RequestParam("type")Integer type) ;
	/**
	 * 
	* @Title: sendMail
	* @Description: TODO(获取邮箱验证码)
	* @author xzj
	* @param @param email
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value="/userinfo/sendMailCode")
	 public Result<?> sendMail(@RequestParam("email")String email,@RequestParam("type")Integer type);
	/**
	 * 
	* @Title: register
	* @Description: TODO(注册)
	* @author xzj
	* @param @param userinfo
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value="/userinfo/register")
	public Result<?> register(@RequestBody Userinfo userinfo);
	/**
	 * 
	* @Title: login
	* @Description: TODO(登录)
	* @author xzj
	* @param @param account
	* @param @param loginPwd
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value="/userinfo/login")
	public Result<Userinfo> login(@RequestParam("account")String account,@RequestParam("loginPwd")String loginPwd,@RequestParam("ipAddress") String ipAddress);
	
	/**
	 * 
	* @Title: checkPayPwd
	* @Description: TODO(检查交易密码是否正确)
	* @author xzj
	* @param @param userId
	* @param @param payPwd
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value="/userinfo/checkPayPwd")	
	public Result<?> checkPayPwd(@RequestParam("payPwd")String payPwd);

	/**
	 * 
	 * <p>Title: bingEmail</p>  
	 * <p>Description:绑定邮箱 </p>  
	 * @param id
	 * @param email 邮箱地址
	 * @param emailCode 邮箱验证码
	 * @return
	 */
	@PostMapping(value="/userinfo/bingEmail")
	public Result<?> bingEmail(@RequestParam("id") String id,@RequestParam("email") String email, @RequestParam("emailCode")String emailCode
			,@RequestParam("token")String token);
	
	/**
	 * 
	 * <p>Title: bingPhone</p>  
	 * <p>Description:绑定手机号码 </p>  
	 * @param id
	 * @param phone 手机号码
	 * @param phoneCode  手机短信验证码
	 * @return
	 */
	@PostMapping(value="/userinfo/bingPhone")
	public Result<?> bingPhone(@RequestParam("id") String id,@RequestParam("phone") String phone,@RequestParam("phoneCode")String phoneCode
			,@RequestParam("token")String token);
	
	
	/**
	 * 
	 * <p>Title: setPayPwd</p>  
	 * <p>Description:设置交易密码 </p>  
	 * @param id
	 * @param payPwd 
	 * @param token
	 * @return
	 */
	@PostMapping(value="userinfo/setPayPwd")
	public Result<?> setPayPwd(@RequestParam("payPwd") String payPwd);
	/**
	 * 
	 * <p>Title: updatePayPwd</p>  
	 * <p>Description: 修改资金密码</p>  
	 * @param id
	 * @param oldPayPwd
	 * @param newPayPwd
	 * @return
	 */
	@PostMapping(value="userinfo/updatePayPwd")
	public Result<?> updatePayPwd(@RequestParam("id") String id,@RequestParam("oldPayPwd") String oldPayPwd,
			@RequestParam("newPayPwd")String newPayPwd);
	/**
	 * 
	 * <p>Title: updateLoginPwd</p>  
	 * <p>Description: 修改登录密码</p>  
	 * @param id
	 * @param oldLoginPwd
	 * @param newLoginPwd
	 * @return
	 */
	@PostMapping(value="userinfo/updateLoginPwd")
	public Result<?> updateLoginPwd(@RequestParam("id") String id,@RequestParam("oldLoginPwd") String oldLoginPwd,
			@RequestParam("newLoginPwd")String newLoginPwd);
	
	/**
	 * 
	* @Title: forgetLoginPwd
	* @Description: TODO(找回登录密码)
	* @author xzj
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	@PostMapping(value="userinfo/forgetLoginPwd")
	public Result<?> forgetLoginPwd(@RequestParam("phone")String phone ,@RequestParam("email")String email,@RequestParam("newLoginPwd")String newLoginPwd);
	
	/**
	 * 
	 * <p>Title: findPayPwd</p>  
	 * <p>Description: 找回资金密码</p>  
	 * @param phone
	 * @param email
	 * @param newPayPwd
	 * @return
	 */
	@PostMapping(value="userinfo/findPayPwd")
	public  Result<?> findPayPwd(@RequestParam("phone")String phone,@RequestParam("email")String email, @RequestParam("newPayPwd")String newPayPwd);
	
	/**
	 * 
	 * <p>Title: checkPayPwd</p>  
	 * <p>Description:校验当前用户是有设定交易密码 </p>  
	 * @return
	 */
	@PostMapping("userinfo/isSetPayPwd")
	public Result<?> isSetPayPwd();
	
	
	/**
	 * 
	 * <p>Title: updateNickName</p>  
	 * <p>Description:修改当前用户昵称 </p>  
	 * @return
	 */
	@PostMapping("userinfo/updateNickName")
	public Result<?> updateNickName(@RequestParam("nickName")String nickName);
	
}
