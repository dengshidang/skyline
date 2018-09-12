package com.skyline.user.business;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.web.bind.annotation.PostMapping;

import com.aliyuncs.exceptions.ClientException;
import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.UserStatusCode;
import com.skyline.common.entity.Userinfo;
import com.skyline.user.util.RequestUtil;

public interface UserinfoBusiness  extends BaseBusiness<Userinfo,Integer> {
	/**
	 * 
	 * 图形验认码
	 */
	 public Result<?> imgCode() throws IOException;
	 /**
	  * 注册短信验证码
	  * @param mobile
	  * @return
	  */
	 public Result<?> sendSmsCode(String phone,Integer type)throws ClientException ;
	 /**
	  * 注册发送邮箱验证码
	  * @param email
	  * @return
	  */
	 public Result<?> sendEmail(String email,Integer type) throws AddressException, MessagingException ;
	 
	 
	/**
	 * 用户注册操作
	 * @param userinfo 
	 */
    public Result<?> register(Userinfo userinfo);
    
	/**
	 * 用户登录操作
	 * @param userinfo
	 * @return
	 */
	public Result<Userinfo> login(String account,String password,String ipAddress);
	

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
	public Result<?> checkPayPwd(String payPwd);

	/**
	 * 
	 * <p>Title: bingEmail</p>  
	 * <p>Description:绑定邮箱 </p>  
	 * @param id
	 * @param email 邮箱地址
	 * @param emailCode 邮箱验证码
	 * @return
	 */
	public Result<?> bingEmail(String id, String email,String token);
	
	/**
	 * 
	 * <p>Title: bingPhone</p>  
	 * <p>Description:绑定手机号码 </p>  
	 * @param id
	 * @param phone 手机号码
	 * @param phoneCode  手机短信验证码
	 * @return
	 */
	public Result<?> bingPhone(String id,String phone,String token) ;
	
	/**
	 * 
	 * <p>Title: setPayPwd</p>  
	 * <p>Description:设置交易密码 </p>  
	 * @param id
	 * @param payPwd 
	 * @param token
	 * @return
	 */
	public Result<?> setPayPwd(String payPwd);

	 
	
	/**
	 * 
	 * <p>Title: updatePayPwd</p>  
	 * <p>Description: 修改资金密码</p>  
	 * @param id
	 * @param oldPayPwd
	 * @param newPayPwd
	 * @return
	 */
	public Result<?> updatePayPwd(String id,String oldPayPwd,String newPayPwd);
	/**
	 * 
	 * <p>Title: updateLoginPwd</p>  
	 * <p>Description: 修改登录密码</p>  
	 * @param id
	 * @param oldLoginPwd
	 * @param newLoginPwd
	 * @return
	 */
	public Result<?> updateLoginPwd(String id,String oldLoginPwd,String newLoginPwd);
	/**
	 * 
	* @Title: forgetLoginPwd
	* @Description: TODO(找回登录密码)
	* @author xzj
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	public Result<?> forgetLoginPwd(String phone,String email,String newLoginPwd);
	
	/**
	 * 
	 * <p>Title: findPayPwd</p>  
	 * <p>Description: 找回资金密码</p>  
	 * @param phone
	 * @param email
	 * @param newPayPwd
	 * @return
	 */
	public  Result<?> findPayPwd(String phone,String email, String newPayPwd);
	
	
	/**
	 * 
	 * <p>Title: checkPayPwd</p>  
	 * <p>Description:校验当前用户是有设定交易密码 </p>  
	 * @return
	 */
	public Result<?> isSetPayPwd();
	
	

	/**
	 * 
	 * <p>Title: updateNickName</p>  
	 * <p>Description:修改当前用户昵称 </p>  
	 * @return
	 */
	public Result<?> updateNickName(Userinfo userInfo,String nickName);
	
}
