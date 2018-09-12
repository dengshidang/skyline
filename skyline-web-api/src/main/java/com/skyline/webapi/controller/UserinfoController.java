package com.skyline.webapi.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Constants;
import com.skyline.common.constant.RedisConstant;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.constant.UserStatusCode;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.JSONUtil;
import com.skyline.common.util.ObjectTool;
import com.skyline.common.util.RegexUtil;
import com.skyline.common.util.StringTool;
import com.skyline.webapi.annotation.NotLogin;
import com.skyline.webapi.filter.RequestUtil;
import com.skyline.webapi.from.RegisterFrom;
import com.skyline.webapi.redis.RedisUtil;
import com.skyline.webapi.service.UserinfoService;
import com.skyline.webapi.util.IpUtil;

@RestController
public class UserinfoController {
	@Autowired
	UserinfoService userinfoService;
	@Autowired
	RedisUtil redisUtil;
	
	
	/**
	 * 
	* @Title: imgCode
	* @Description: TODO(获取图片验证码)
	* @author xzj
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	@GetMapping(value="/imgCode")
	 */
	@NotLogin
	@GetMapping(value="/userinfo/imgCode")
    public Result<?> imgCode() {
		return userinfoService.imgCode();
	}
	/**
	 * 
	* @Title:sendSmsCode
	* @Description: TODO(获取短信验证码)
	* @author xzj
	* @param @param mobile
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@NotLogin
	@PostMapping(value="/userinfo/sendSmsCode")
	 public Result<?> sendSmsCode(String phone,Integer type,String imgCode,String imgCodeId) {
		if(ObjectTool.isBlank(phone)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		 if(!RegexUtil.checkMobile(phone)) {
			 return Result.errorResult(UserStatusCode.E00308.getCode(), UserStatusCode.E00308.getMsg());
		 }
		 if(type==0){
			 if(ObjectTool.isBlank(imgCode)) {
				 return Result.errorResult(UserStatusCode.E00322.getCode(), UserStatusCode.E00322.getMsg());
			 }
			//验证图形验证码
			 //System.out.println(redisUtil.getString(RedisConstant.VERIFYCODE+imgCodeId));
				if(!imgCode.equals(redisUtil.getString(RedisConstant.VERIFYCODE+imgCodeId))){
					return Result.errorResult(UserStatusCode.E00305.getCode(), UserStatusCode.E00305.getMsg());
				}
		  redisUtil.del(RedisConstant.VERIFYCODE+imgCodeId);
		 }
		return userinfoService.sendSmsCode(phone,type);
	}
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
	@NotLogin
	@PostMapping(value="/userinfo/sendMailCode")
	 public Result<?> sendMail(String email,Integer type) {
		if(ObjectTool.isBlank(email)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		if(!RegexUtil.checkEmail(email)) {
			 return Result.errorResult(UserStatusCode.E00307.getCode(), UserStatusCode.E00307.getMsg());
		 }
		return userinfoService.sendMail(email,type);
	}
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
	@NotLogin
	@PostMapping(value="/userinfo/register")
	public Result<?> register(RegisterFrom from) {
		if(ObjectTool.isBlank(from.getRegisterType(),from.getLoginPwd(),from.getImgCodeId(),
				from.getPhoneOrEmailCode())) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		String redisKey;
		Integer registerType;
		if(from.getRegisterType()==0) {//手机注册
			 if(ObjectTool.isBlank(from.getPhone())) {
				return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
			 }
			 if(!RegexUtil.checkMobile(from.getPhone())) {
				 return Result.errorResult(UserStatusCode.E00308.getCode(), UserStatusCode.E00308.getMsg());
			 }
			 redisKey=RedisConstant.REGISTERCODE+from.getPhone();
			 registerType=0;
		}else {//邮箱注册
			if(ObjectTool.isBlank(from.getEmail())) {
					return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
			}
			if(!RegexUtil.checkEmail(from.getEmail())) {
					 return Result.errorResult(UserStatusCode.E00307.getCode(), UserStatusCode.E00307.getMsg());
			}
			redisKey=RedisConstant.REGISTERCODE+from.getEmail();
			registerType=1;
		}
		//验证手机验证码或邮箱
		if(!from.getPhoneOrEmailCode().equals(redisUtil.getString(redisKey))) {
			return Result.errorResult(UserStatusCode.E00306.getCode(), UserStatusCode.E00306.getMsg());
		}
		//失效
		redisUtil.del(redisKey+from.getImgCodeId());
		Userinfo userinfo=new Userinfo();
		BeanUtils.copyProperties(from,userinfo);
		userinfo.setRegisterType(registerType);
		return userinfoService.register(userinfo);
	}
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
	@NotLogin
	@PostMapping(value="/userinfo/login")
	public Result<Userinfo> login(String account,String loginPwd,String imgCodeId,String imgCode ,HttpServletRequest request) {
		if(ObjectTool.isBlank(account,loginPwd,imgCodeId,imgCode)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		//验证图形验证码
		if(!imgCode.equals(redisUtil.getString(RedisConstant.VERIFYCODE+imgCodeId))){
			return Result.errorResult(UserStatusCode.E00305.getCode(), UserStatusCode.E00305.getMsg());
		}
		//失效
		redisUtil.del(RedisConstant.VERIFYCODE+imgCodeId);
        String ipAddress = IpUtil.getIpAddr(request);
		return userinfoService.login(account,loginPwd,ipAddress);
	}
	
	/**
	 * 
	* @Title: getUserByToken
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author xzj
	* @param @param token
	* @param @return    参数
	* @return Userinfo    返回类型
	* @throws
	 */
	@GetMapping(value="/userinfo/getUserByToken")	
	public Result<Userinfo> getUserByToken(String token) {
		Userinfo user=JSONUtil.toBean(redisUtil.getString(RedisConstant.USERTOKEN+token),Userinfo.class);
		if(user!=null) {
			redisUtil.expire(RedisConstant.USERTOKEN+token, Constants.TOKEN_EXPIRES_TIME);
			return Result.successResult(user);
		}
		return Result.errorResult(UserStatusCode.E00304.getCode(), UserStatusCode.E00304.getMsg());
	}
	

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
	public Result<?> bingEmail(@RequestParam("id") String id,@RequestParam("email") String email, 
			@RequestParam("emailCode")String emailCode,@RequestParam("token")String token){
		if(ObjectTool.isBlank(id,email)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		if(!RegexUtil.checkEmail(email)) {
			 return Result.errorResult(UserStatusCode.E00307.getCode(), UserStatusCode.E00307.getMsg());
		 }
		
		String redisKey=RedisConstant.REGISTERCODE+email;
		//验证手机验证码或邮箱
		if(!emailCode.equals(redisUtil.getString(redisKey))) {
			return Result.errorResult(UserStatusCode.E00306.getCode(), UserStatusCode.E00306.getMsg());
		}
		//失效
		redisUtil.del(redisKey);
		return userinfoService.bingEmail(id, email, emailCode,token);
	}
	
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
	public Result<?> bingPhone(@RequestParam("id") String id,@RequestParam("phone") String phone,
			@RequestParam("phoneCode")String phoneCode,@RequestParam("token")String token){
		if(ObjectTool.isBlank(id,phone)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		 if(!RegexUtil.checkMobile(phone)) {
			 return Result.errorResult(UserStatusCode.E00308.getCode(), UserStatusCode.E00308.getMsg());
		 }
		String  redisKey=RedisConstant.REGISTERCODE+phone;
		//验证手机验证码或邮箱
		if(!phoneCode.equals(redisUtil.getString(redisKey))) {
			return Result.errorResult(UserStatusCode.E00306.getCode(), UserStatusCode.E00306.getMsg());
		}
		//失效
	    redisUtil.del(redisKey);
		return userinfoService.bingPhone(id, phone, phoneCode,token);
	}
	
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
	public Result<?> setPayPwd(@RequestParam("payPwd") String payPwd){
		if(ObjectTool.isBlank(payPwd)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userinfoService.setPayPwd( payPwd);
	}
	
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
	public Result <?>updatePayPwd(@RequestParam("id") String id,@RequestParam("oldPayPwd") String oldPayPwd,
			@RequestParam("newPayPwd")String newPayPwd){
		if(ObjectTool.isBlank(id,oldPayPwd,newPayPwd)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userinfoService.updatePayPwd(id, oldPayPwd, newPayPwd);
	}
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
			@RequestParam("newLoginPwd")String newLoginPwd){
		if(ObjectTool.isBlank(id,oldLoginPwd,newLoginPwd)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userinfoService.updateLoginPwd(id, oldLoginPwd, newLoginPwd);
	}
	
	/**
	 * 
	* @Title: forgetLoginPwd
	* @Description: TODO(找回登录密码)
	* @author xzj
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	@NotLogin
	@PostMapping(value="userinfo/forgetLoginPwd")
	public Result<?> forgetLoginPwd(String phone,String email, String newLoginPwd,String code){
		if(StringTool.isBlank(phone)&& StringTool.isBlank(email)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());	
		}
		if(ObjectTool.isBlank(newLoginPwd,code)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}	
		String redisKey;
		if(!StringTool.isBlank(phone)) {//手机注册
			 if(!RegexUtil.checkMobile(phone)) {
				 return Result.errorResult(UserStatusCode.E00308.getCode(), UserStatusCode.E00308.getMsg());
			 }
			 redisKey=RedisConstant.REGISTERCODE+phone;
			 email=null;
		}else {//邮箱注册
			if(!RegexUtil.checkEmail(email)) {
					 return Result.errorResult(UserStatusCode.E00307.getCode(), UserStatusCode.E00307.getMsg());
			}
			redisKey=RedisConstant.REGISTERCODE+email;
			phone=null;
		}
		//验证手机验证码或邮箱
		if(!code.equals(redisUtil.getString(redisKey))) {
					return Result.errorResult(UserStatusCode.E00306.getCode(), UserStatusCode.E00306.getMsg());
	    }
		return userinfoService.forgetLoginPwd(phone, email,newLoginPwd);
	}
	/**
	 * 
	 * <p>Title: findPayPwd</p>  
	 * <p>Description: 找回交易密码</p>  
	 * @param phone
	 * @param email
	 * @param newPayPwd
	 * @param code
	 * @return
	 */
	@PostMapping(value="userinfo/findPayPwd")
	public  Result<?> findPayPwd(String phone,String email, String newPayPwd,String code){
		if(StringTool.isBlank(phone)&& StringTool.isBlank(email)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());	
		}
		if(ObjectTool.isBlank(newPayPwd,code)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}	
		String redisKey;
		Userinfo user=	RequestUtil.getCurrentUser();
		if(!StringTool.isBlank(phone)) {
			if(ObjectTool.isBlank(user.getPhone())){
				 return Result.errorResult(UserStatusCode.E00316.getCode(), UserStatusCode.E00316.getMsg());
			}
			 if(!RegexUtil.checkMobile(phone)) {
				 return Result.errorResult(UserStatusCode.E00308.getCode(), UserStatusCode.E00308.getMsg());
			 }
			 if(!StringUtils.equals(phone, user.getPhone())){
				 return Result.errorResult(UserStatusCode.E00313.getCode(), UserStatusCode.E00313.getMsg());
			 }
			 redisKey=RedisConstant.REGISTERCODE+phone;
			 email=null;
		}else {//邮箱注册
			if(ObjectTool.isBlank(user.getEmail())){
				 return Result.errorResult(UserStatusCode.E00315.getCode(), UserStatusCode.E00315.getMsg());
			}
			if(!RegexUtil.checkEmail(email)) {
					 return Result.errorResult(UserStatusCode.E00307.getCode(), UserStatusCode.E00307.getMsg());
			}
			 if(!StringUtils.equals(email, user.getEmail())){
				 return Result.errorResult(UserStatusCode.E00314.getCode(), UserStatusCode.E00314.getMsg());
			 }
			redisKey=RedisConstant.REGISTERCODE+email;
			phone=null;
		}
		//验证手机验证码或邮箱
		if(!code.equals(redisUtil.getString(redisKey))) {
					return Result.errorResult(UserStatusCode.E00306.getCode(), UserStatusCode.E00306.getMsg());
	    }
		return userinfoService.findPayPwd(phone, email, newPayPwd);
	}

	/**
	 * 
	 * <p>Title: checkPayPwd</p>  
	 * <p>Description:校验当前用户是有设定交易密码 </p>  
	 * @return
	 */
	@PostMapping("userinfo/isSetPayPwd")
	public Result<?> isSetPayPwd(){
		return userinfoService.isSetPayPwd();
	}
	/**
	 * 
	 * <p>Title: updateNickName</p>  
	 * <p>Description:修改当前用户昵称 </p>  
	 * @return
	 */
	@PostMapping("userinfo/updateNickName")
	public Result<?> updateNickName(String nickName){
		if(ObjectTool.isBlank(nickName)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userinfoService.updateNickName(nickName);
	}
	
	
}
