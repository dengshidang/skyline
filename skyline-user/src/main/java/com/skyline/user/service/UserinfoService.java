package com.skyline.user.service;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aliyuncs.exceptions.ClientException;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.UserStatusCode;
import com.skyline.common.entity.Useridentitie;
import com.skyline.common.entity.Userinfo;
import com.skyline.user.business.UserinfoBusiness;
import com.skyline.user.util.RequestUtil;
/**
 * @author chenzilong
 * @Description TODO
 * @date 2018年7月12日下午5:39:31
 */
@RestController
@RequestMapping(value="userinfo")
public class UserinfoService {
	private final static Logger log = LoggerFactory.getLogger(UserinfoService.class); 
	@Autowired
	private UserinfoBusiness userinfoBusiness;
	@GetMapping(value="/imgCode")
    public Result<?> imgCode() throws IOException{
		return userinfoBusiness.imgCode();
	 }
	@PostMapping(value="sendSmsCode")
	 public Result<?> registerSendSmsCode(String phone,Integer type) throws ClientException {
	    return userinfoBusiness.sendSmsCode(phone,type);
	 }
	    
	 @PostMapping(value="sendMailCode")
	 public Result<?> sendMail(String email,Integer type) throws AddressException, MessagingException  {	    
		 return userinfoBusiness.sendEmail(email,type);
	  }
	/**
	 * @Title: saveUserinfo 
	 * @Description TODO(用户注册操作)
	 * @param userinfo
	 * @return
	 * @date 2018年7月11日下午5:15:16
	 */
	@PostMapping(value="/register")
	public Result register(@RequestBody Userinfo userinfo){
			return	userinfoBusiness.register(userinfo);
	}	
	
	/**
	 * @Title: findUserinfoByUP 
	 * @Description TODO(用户登录操作)
	 * @param phone
	 * @param loginPwd
	 * @return
	 * @date 2018年7月11日下午5:16:07
	 */
	@PostMapping(value="/login")
	public Result<Userinfo> login(String account,String loginPwd,String ipAddress){
			return  userinfoBusiness.login(account,loginPwd,ipAddress);
	}
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
	@PostMapping(value="/checkPayPwd")	
	public Result<?> checkPayPwd(String payPwd){
		 return userinfoBusiness.checkPayPwd(payPwd);
	}

	@PostMapping(value="/bingEmail")
	public Result<?> bingEmail(@RequestParam("id") String id,@RequestParam("email") String email,@RequestParam("token") String token){
		return userinfoBusiness.bingEmail(id, email,token);
	};
	
	/**
	 * 
	 * <p>Title: bingPhone</p>  
	 * <p>Description:绑定手机号码 </p>  
	 * @param id
	 * @param phone 手机号码
	 * @param phoneCode  手机短信验证码
	 * @return
	 */
	@PostMapping(value="/bingPhone")
	public Result<?> bingPhone(@RequestParam("id") String id,@RequestParam("phone") String phone,@RequestParam("token") String token){
		return userinfoBusiness.bingPhone(id, phone,token);
	};

	/**
	 * 
	 * <p>Title: setPayPwd</p>  
	 * <p>Description:设置交易密码 </p>  
	 * @param id
	 * @param payPwd 
	 * @param token
	 * @return
	 */
	@PostMapping(value="/setPayPwd")
	public Result<?> setPayPwd(@RequestParam("payPwd") String payPwd){
		return userinfoBusiness.setPayPwd(payPwd);
	};
	
	
	/**
	 * 
	 * <p>Title: updatePayPwd</p>  
	 * <p>Description: 修改资金密码</p>  
	 * @param id
	 * @param oldPayPwd
	 * @param newPayPwd
	 * @return
	 */
	@PostMapping(value="/updatePayPwd")
	public Result<?> updatePayPwd(@RequestParam("id") String id,@RequestParam("oldPayPwd") String oldPayPwd,
			@RequestParam("newPayPwd")String newPayPwd){
		return userinfoBusiness.updatePayPwd(id, oldPayPwd, newPayPwd);
	};
	/**
	 * 
	 * <p>Title: updateLoginPwd</p>  
	 * <p>Description: 修改登录密码</p>  
	 * @param id
	 * @param oldLoginPwd
	 * @param newLoginPwd
	 * @return
	 */
	@PostMapping(value="/updateLoginPwd")
	public Result<?> updateLoginPwd(@RequestParam("id") String id,@RequestParam("oldLoginPwd") String oldLoginPwd,
			@RequestParam("newLoginPwd")String newLoginPwd){
		return userinfoBusiness.updateLoginPwd(id, oldLoginPwd, newLoginPwd);
	};
	
	/**
	 * 
	* @Title: forgetLoginPwd
	* @Description: TODO(找回登录密码)
	* @author xzj
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	@PostMapping(value="/forgetLoginPwd")
	public Result<?> forgetLoginPwd(String phone,String email,String newLoginPwd){
		return userinfoBusiness.forgetLoginPwd(phone,email,newLoginPwd);
	}
	
	/**
	 * 
	 * <p>Title: findPayPwd</p>  
	 * <p>Description: 找回资金密码</p>  
	 * @param phone
	 * @param email
	 * @param newPayPwd
	 * @return
	 */
	@PostMapping(value="/findPayPwd")
	public  Result<?> findPayPwd(String phone,String email, String newPayPwd){
		
		return userinfoBusiness.findPayPwd(phone, email, newPayPwd);
	}

	/**
	 * 
	 * <p>Title: checkPayPwd</p>  
	 * <p>Description:校验当前用户是有设定交易密码 </p>  
	 * @return
	 */
	@PostMapping("/isSetPayPwd")
	public Result<?> isSetPayPwd(){
		return userinfoBusiness.isSetPayPwd();
	}
	
	
	
	/**
	 * 
	 * <p>Title: updateNickName</p>  
	 * <p>Description:修改当前用户昵称 </p>  
	 * @return
	 */
	@PostMapping("/updateNickName")
	public Result<?> updateNickName(String nickName){
		Userinfo userInfo = RequestUtil.getCurrentUser();
		if(userInfo==null){
			return Result.errorResult(UserStatusCode.E00304.getCode(), UserStatusCode.E00304.getMsg());
		}
		return userinfoBusiness.updateNickName(userInfo,nickName);
	}

}
