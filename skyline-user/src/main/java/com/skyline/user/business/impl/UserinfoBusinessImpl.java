package com.skyline.user.business.impl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aliyuncs.exceptions.ClientException;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.AdditionalFunctionStatusCode;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.RedisConstant;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.UserStatusCode;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.CryptUtil;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.JSONUtil;
import com.skyline.common.util.MailUtil;
import com.skyline.common.util.ObjectTool;
import com.skyline.common.util.RadomCode;
import com.skyline.common.util.RandomUtil;
import com.skyline.common.util.SmsUtil;
import com.skyline.common.util.StringTool;
import com.skyline.common.util.VerifyCodeUtil;
import com.skyline.user.business.UserinfoBusiness;
import com.skyline.user.exception.BusinessException;
import com.skyline.user.mapper.UserinfoMapper;
import com.skyline.user.redis.RedisUtil;
import com.skyline.user.util.RequestUtil;
import com.skyline.user.async.InviteAsyncTask;


@Service
@Transactional
public class UserinfoBusinessImpl extends BaseBusinessImpl<Userinfo,Integer> implements UserinfoBusiness {

	@Autowired
	private UserinfoMapper userinfoMapper;
	@Autowired
	private RedisUtil redisUtil;//工具类对象
	
	@Autowired
	private InviteAsyncTask InviteAsyncTask;
	
	
	/**
	 * 
	 * 图形验认码
	 * @throws IOException 
	 */
	 public Result<?> imgCode() throws IOException {
		 	//为图像创建文本
			String capText =VerifyCodeUtil.createText();
			//将文本存储在redis
			String codeId=StringTool.getUUID();
			redisUtil.set(RedisConstant.VERIFYCODE+codeId, capText, Constants.IMGCODE_EXPIRES_TIME);
			System.out.println(RedisConstant.VERIFYCODE+codeId+":"+redisUtil.getString(RedisConstant.VERIFYCODE+codeId));
			//用文本创建图像
			BufferedImage bi = VerifyCodeUtil.createImage(capText);
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			 ImageIO.write(bi, "jpg", outputStream);
			Base64 base64 = new Base64();
			String base64Img = base64.encodeToString(outputStream.toByteArray());
		     Map<String,String> data=new HashMap<String,String>();
		     data.put("img", "data:image/jpg;base64,"+base64Img);
			 data.put("codeId",codeId);
		    return Result.successResult(data);
	 }
	 /**
	  * 注册短信验证码
	  * @param mobile
	  * @return
	 * @throws ClientException 
	  */
	 public Result<?> sendSmsCode(String phone,Integer type) throws ClientException {
		 if(Constants.SEND_TYPE_0==type) {//注册
			  //判断手机号是否存在
			  if(userinfoMapper.isRegister(phone, null)>0) {//用户已经注册
						throw new BusinessException(UserStatusCode.E00301.getCode(), UserStatusCode.E00301.getMsg());
			  }
			  String code=RandomUtil.getRandom();
			  SmsUtil.sendIdentifyingCode(phone, code);
			  System.out.println("验证码===============================>"+code);
			  redisUtil.set(RedisConstant.REGISTERCODE+phone, code, Constants.CODE_EXPIRES_TIME);
		 }else if(Constants.SEND_TYPE_1==type) {//找回密码
			  String code=RandomUtil.getRandom();
			  SmsUtil.sendIdentifyingCode(phone, code);
			  System.out.println("验证码===============================>"+code);
			  redisUtil.set(RedisConstant.REGISTERCODE+phone, code, Constants.CODE_EXPIRES_TIME);
		 }else if(Constants.SEND_TYPE_2==type){
			  //判断手机号是否存在
			  if(userinfoMapper.isRegister(phone, null)>0) {//用户已经注册
						throw new BusinessException(UserStatusCode.E00312.getCode(), UserStatusCode.E00312.getMsg());
			  }
			  String code=RandomUtil.getRandom();
			  System.out.println("绑定手机===============================>"+code);
			  SmsUtil.sendIdentifyingCode(phone, code);
			  redisUtil.set(RedisConstant.REGISTERCODE+phone, code, Constants.CODE_EXPIRES_TIME);
		 }else if(Constants.SEND_TYPE_3==type){
			 if(StringUtils.equals(RequestUtil.getCurrentUser().getPhone(), phone)){
				  String code=RandomUtil.getRandom();
				  SmsUtil.sendIdentifyingCode(phone, code);
				  System.out.println("找回资金密码手机验证码===============================>"+code);
				  redisUtil.set(RedisConstant.REGISTERCODE+phone, code, Constants.CODE_EXPIRES_TIME);
			 }else{
					throw new BusinessException(UserStatusCode.E00313.getCode(), UserStatusCode.E00313.getMsg());
			 }
		 }
		 
		  return Result.successResult();
	 }
	 /**
	  * 注册发送邮箱验证码
	  * @param email
	  * @return
	 * @throws MessagingException 
	 * @throws AddressException 
	  */
	 public Result<?> sendEmail(String email,Integer type) throws AddressException, MessagingException {
		 if(Constants.SEND_TYPE_0==type) {//注册
				 if(userinfoMapper.isRegister(null, email)>0) {//用户已经注册
						throw new BusinessException(UserStatusCode.E00301.getCode(), UserStatusCode.E00301.getMsg());
				 }
				  String code=RandomUtil.getRandom();
				  MailUtil.registerSendMail(email, code);
				  redisUtil.set(RedisConstant.REGISTERCODE+email, code,  Constants.CODE_EXPIRES_TIME);
		 }else if(Constants.SEND_TYPE_1==type) {//找回密码
			 String code=RandomUtil.getRandom();
			  MailUtil.registerSendMail(email, code);
			  redisUtil.set(RedisConstant.REGISTERCODE+email, code,  Constants.CODE_EXPIRES_TIME);
		 }else if(Constants.SEND_TYPE_2==type){
			 if(userinfoMapper.isRegister(null, email)>0) {//用户已经注册
					throw new BusinessException(UserStatusCode.E00312.getCode(), UserStatusCode.E00312.getMsg());
			 }
			  String code=RandomUtil.getRandom();
			  MailUtil.registerSendMail(email, code);
			  System.out.println("验证码===============================>"+code);
			  redisUtil.set(RedisConstant.REGISTERCODE+email, code,  Constants.CODE_EXPIRES_TIME);
		 }else if(Constants.SEND_TYPE_3==type){
			 if(StringUtils.equals(RequestUtil.getCurrentUser().getEmail(), email)){//找回密码
				 String code=RandomUtil.getRandom();
				  MailUtil.registerSendMail(email, code);
				  redisUtil.set(RedisConstant.REGISTERCODE+email, code,  Constants.CODE_EXPIRES_TIME);
				  System.out.println("修改资金密码邮箱验证码===============================>"+code);
			 }else{
					throw new BusinessException(UserStatusCode.E00314.getCode(), UserStatusCode.E00314.getMsg());
			 }
		 }
		  return Result.successResult();
	 }
	

	//用户注册操作
	//封装userinfo入库数据，需要对密码加密处理String
	@Override
    public Result<?> register(Userinfo userinfo) {
		//判断是否存在
		if(userinfoMapper.isRegister(userinfo.getPhone(), userinfo.getEmail())>0) {//用户已经注册
			throw new BusinessException(UserStatusCode.E00301.getCode(), UserStatusCode.E00301.getMsg());
		}
		String md5LoginPwd =CryptUtil.crypt(userinfo.getLoginPwd(),"MD5");
		userinfo.setLoginPwd(md5LoginPwd);
		userinfo.setCreateTime(DateUtil.getCurTimeString());
		userinfo.setIdSign(0);
		userinfo.setMctSign(0);
		userinfo.setCharge(0);
		userinfo.setStatus(0);
		userinfo.setDeleteSign(0);
		String parentInvit =userinfo.getInvit(); 
		//随机生成专属邀请码
		String  pusineInvite =RadomCode.generateRandomStr(6); 
		if(!ObjectTool.isBlank(parentInvit)){
			String parentId  = userinfoMapper.queryUserIdByInvite(parentInvit);
			if(ObjectTool.isBlank(parentId)){
				throw new BusinessException(AdditionalFunctionStatusCode.E321.getCode(),
						AdditionalFunctionStatusCode.E321.getMsg());
			}
		}
		userinfo.setInvit(pusineInvite);
		if(userinfoMapper.insert(userinfo)==1) {
			InviteAsyncTask.save(pusineInvite, parentInvit);
			return Result.successResult();
		}
		throw new BusinessException(UserStatusCode.E00302.getCode(), UserStatusCode.E00302.getMsg());
	}
	//用户登录操作
	/**
	 * 1.根据用户名和密码查询信息
	 * 2.如果查询的数据不为null，表示用户名和密码正确
	 * 3.生成一个用户token
	 * 4.将用户信息转化为JSON串
	 * 5.将token:json存入redis中
	 * 6.设置session过期时间
	 */
	@Override
	public Result<Userinfo> login(String account,String loginPwd,String ipAddress) {
		//1.将登录密码，交易密码加密处理
		loginPwd=CryptUtil.crypt(loginPwd,"MD5");
		//2.根据用户名和密码查询用户信息
		Userinfo userinfo=userinfoMapper.getUserinfo(account, loginPwd);
		//3.校验用户名和密码
		if(userinfo==null){
			throw new BusinessException(UserStatusCode.E00303.getCode(), UserStatusCode.E00303.getMsg());
		}
		//4.用户名和密码正确  生成秘钥
		String token = StringTool.getUUID();
		if(!StringTool.isBlank(userinfo.getLoginSession())) {//不为空
			redisUtil.del(RedisConstant.USERTOKEN+userinfo.getLoginSession());
		}
		Userinfo user=new Userinfo();
		user.setId(userinfo.getId());
		user.setLoginSession(token);
		userinfoMapper.updateByPrimaryKeySelective(user);//更新token
		//保存
		Userinfo redisUser=new Userinfo();
		redisUser.setId(userinfo.getId());
		redisUser.setPhone(userinfo.getPhone());
		redisUser.setEmail(userinfo.getEmail());
		redisUser.setMctSign(userinfo.getMctSign());
		redisUser.setInvit(userinfo.getInvit());
		redisUser.setStatus(userinfo.getStatus());
		redisUser.setSex(userinfo.getSex());
		redisUser.setPortrait(userinfo.getPortrait());
		redisUser.setNickName(userinfo.getNickName());
		redisUser.setLoginSession(token);
		redisUser.setRegisterType(userinfo.getRegisterType());
		redisUser.setLoginPwd("*********");
		if(!ObjectTool.isBlank(userinfo.getPayPwd())){
			redisUser.setPayPwd("*********");
		}
		redisUtil.set(RedisConstant.USERTOKEN+token, JSONUtil.toJson(redisUser), Constants.TOKEN_EXPIRES_TIME);
		InviteAsyncTask.setLoginHistory(ipAddress,userinfo.getId());
		return Result.successResult(redisUser);
	}

	
	
	

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
	public Result<?> checkPayPwd(String payPwd) {
		Userinfo user=RequestUtil.getCurrentUser();
		payPwd=CryptUtil.crypt(payPwd, "MD5");
		if(userinfoMapper.checkPayPwd(user.getId(), payPwd)!=null) {
			return Result.successResult();
		}
		return Result.errorResult(UserStatusCode.E00309.getCode(), UserStatusCode.E00309.getMsg());
	}
	
	
	@Override
	public Result<?> bingEmail(String id, String email,String token) {
	  userinfoMapper.bingEmail(id, email);
	  Userinfo  userinfo = userinfoMapper.queryUserInfoById(Integer.parseInt(id));
	  userinfo.setLoginPwd("*********");
	  if(userinfo.getPayPwd()!=null){
		  userinfo.setPayPwd("*********");
	  }
	  redisUtil.set(RedisConstant.USERTOKEN+token, JSONUtil.toJson(userinfo), 30*60);
	  return Result.successResult();
	}
	@Override
	public Result<?> bingPhone(String id, String phone,String token)  {
		userinfoMapper.bingPhone(id, phone);
		Userinfo  userinfo = userinfoMapper.queryUserInfoById(Integer.parseInt(id));
		 userinfo.setLoginPwd("*********");
		  if(userinfo.getPayPwd()!=null){
			  userinfo.setPayPwd("*********");
		  }
		redisUtil.set(RedisConstant.USERTOKEN+token, JSONUtil.toJson(userinfo), 30*60);
		return Result.successResult();
	}
	
	@Override
	public Result setPayPwd(String payPwd) {
	   if(userinfoMapper.setPayPwd(RequestUtil.getCurrentUser().getId(), CryptUtil.crypt(payPwd,"MD5"))==1){
			Userinfo  userinfo = userinfoMapper.selectByPrimaryKey(RequestUtil.getCurrentUser().getId());
			userinfo.setLoginPwd("*********");
			if(userinfo.getPayPwd()!=null){
				  userinfo.setPayPwd("*********");
			}
			redisUtil.set(RedisConstant.USERTOKEN+RequestUtil.getCurrentToken(), JSONUtil.toJson(userinfo), Constants.TOKEN_EXPIRES_TIME);
			return Result.successResult();
	   }else {
		   throw new BusinessException(UserStatusCode.E00320.getCode(), UserStatusCode.E00320.getMsg());
	   }
	}
	@Override
	public Result updatePayPwd(String id, String oldPayPwd, String newPayPwd) {
		oldPayPwd = CryptUtil.crypt(oldPayPwd,"MD5");
		newPayPwd = CryptUtil.crypt(newPayPwd,"MD5");
		int resultNum = userinfoMapper.updatePayPwd(id, oldPayPwd, newPayPwd);
		if(resultNum>0){
			return Result.successResult();
		}
		return  Result.errorResult(UserStatusCode.E00310.getCode(), UserStatusCode.E00310.getMsg());
	}
	@Override
	public Result updateLoginPwd(String id, String oldLoginPwd, String newLoginPwd) {
		oldLoginPwd = CryptUtil.crypt(oldLoginPwd,"MD5");
		newLoginPwd = CryptUtil.crypt(newLoginPwd,"MD5");
		int resultNum = userinfoMapper.updateLoginPwd(id, oldLoginPwd, newLoginPwd);
		if(resultNum>0){
			return Result.successResult();
		}
		return  Result.errorResult(UserStatusCode.E00310.getCode(), UserStatusCode.E00310.getMsg());
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
	public Result<?> forgetLoginPwd(String phone,String email,String newLoginPwd){
		if(userinfoMapper.forgetLoginPwd(phone, email,CryptUtil.crypt(newLoginPwd, "MD5"))==1) {
			return Result.successResult();
		}
		throw new BusinessException(UserStatusCode.E00311.getCode(), UserStatusCode.E00311.getMsg());
	}
	
	
	@Override
	public Result<?> findPayPwd(String phone, String email, String newPayPwd) {
		
		if(userinfoMapper.findPayPwd(phone, email,CryptUtil.crypt(newPayPwd, "MD5"))==1) {
			return Result.successResult();
		}
		
		throw new BusinessException(UserStatusCode.E00311.getCode(), UserStatusCode.E00311.getMsg());
	}
	@Override
	public Result<?> isSetPayPwd() {
		// TODO Auto-generated method stub
		Userinfo currUser  =RequestUtil.getCurrentUser();
		Userinfo user = userinfoMapper.selectByPrimaryKey(currUser.getId());
		if(user==null){
			throw new BusinessException(UserStatusCode.E00317.getCode(), UserStatusCode.E00317.getMsg());
		}
		if(!StringTool.isBlank(user.getPayPwd())){
			return	Result.successResult();
		}else{
			return	Result.errorResult(UserStatusCode.E00319.getCode(),UserStatusCode.E00319.getMsg());
		}
	}
	
	
	@Override
	public Result<?> updateNickName(Userinfo userInfo,String nickName) {
		// TODO Auto-generated method stub
		Userinfo info  = new  Userinfo();
		if(userInfo.getId()!=null){
			info.setId(userInfo.getId());
		}else{
			throw new BusinessException(UserStatusCode.E00304.getCode(), UserStatusCode.E00304.getMsg());
		}
		Userinfo user = userinfoMapper.selectOne(info);
		if(user==null){
			throw new BusinessException(UserStatusCode.E00321.getCode(), UserStatusCode.E00321.getMsg());
		}
		user.setNickName(nickName);
		if(userinfoMapper.updateByPrimaryKey(user)==1){
			userInfo.setNickName(nickName);
			String token = RequestUtil.getCurrentToken();
			redisUtil.set(RedisConstant.USERTOKEN+token, JSONUtil.toJson(userInfo), Constants.TOKEN_EXPIRES_TIME);
			return Result.successResult();
		}
		throw new BusinessException(UserStatusCode.E00320.getCode(), UserStatusCode.E00320.getMsg());
	}

}

















