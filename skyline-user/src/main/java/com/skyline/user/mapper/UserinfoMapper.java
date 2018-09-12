package com.skyline.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Useridentitie;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.MyMapper;

public interface UserinfoMapper extends MyMapper<Userinfo> {
	
	/**
	 * 用户登录操作
	 * @param userinfo 
	 * @return
	 */
	Userinfo getUserinfo(@Param("account") String account,@Param("loginPwd") String loginPwd);
	
	/**
	 * 校验用户信息
	 * @param param 传的参数类型(手机号码，邮箱)
	 * @param cloumn 具体的参数(phone,email)
	 * @return 用户信息
	 */
	int findCheckUserinfo(@Param("param") String param, @Param("cloumn") String cloumn);
	/**
	 *  是不注册
	 * @param phone
	 * @param email
	 * @return
	 */
	int isRegister(@Param("phone") String phone,@Param("email") String email);
	/**
	 * 
	* @Title: checkPayPwd
	* @Description: TODO(检查交易密码是否正确)
	* @author xzj
	* @param @param userId
	* @param @param checkPayPwd
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	public Userinfo  checkPayPwd(@Param("userId")Integer userId,@Param("payPwd")String payPwd);

	/**
	 *  查询是否已经设置交易密码
	 * @return
	 */
	String isSetPayPwd(@Param("userId") String userId);
	
	/**
	 *  通过用户id查询实名认证信息
	 * @return
	 */
	String queryUserinfoByUerId(@Param("userId")  String userId);
	
	/**
	 *  实名认证后更新用户信息表
	 * @return
	 */
	void updateIdSign(@Param("userId") String userId ,@Param("idSign") String idSign);

	
	/**
	 *  实名认证后更新用户信息表(商户认证)
	 * @return
	 */
	void updateMctSign(@Param("userId") String userId ,@Param("mctSign") String mctSign);

	/**
	 *  根据邀请码 查询用户id
	 * @return
	 */
	String  queryUserIdByInvite(@Param("parentInvite")String parentInvite);
	
	/**
	 * 根据用户ID 查询用户信息表
	 */
	Userinfo queryUserInfoById(@Param("userId") Integer userId);
	
	/**
	 * 绑定邮箱
	 */
	void  bingEmail(@Param("id") String id ,@Param("email") String email);
	
	/**
	 * 绑定手机号码
	 */
	void  bingPhone(@Param("id") String id ,@Param("phone") String phone);
	
	/**
	 * 设置交易密码
	 */
	int  setPayPwd(@Param("id") Integer  id ,@Param("payPwd") String payPwd);
	
	/**
	 * 修改交易密码
	 */
	int updatePayPwd(@Param("id") String id ,@Param("oldPayPwd") String oldPayPwd,@Param("newPayPwd")String newPayPwd);
	
	/**
	 * 修改登录密码
	 */
	int updateLoginPwd(@Param("id") String id ,@Param("oldLoginPwd") String oldLoginPwd,@Param("newLoginPwd") String newLoginPwd);
	/**
	 * 
	* @Title: forgetLoginPwd
	* @Description: TODO(找回密码)
	* @author xzj
	* @param @param phone
	* @param @param email
	* @param @param newLoginPwd
	* @param @return    参数
	* @return int    返回类型
	* @throws
	 */
	int forgetLoginPwd(@Param("phone") String phone ,@Param("email") String email,@Param("newLoginPwd") String newLoginPwd);
	
	/**
	 * 找回资金密码
	 */
	
	int  findPayPwd(@Param("phone") String phone ,@Param("email") String email,@Param("newPayPwd") String newPayPwd);
}
