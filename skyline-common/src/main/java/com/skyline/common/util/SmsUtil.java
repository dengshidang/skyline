package com.skyline.common.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @author chenzilong
 * @Description TODO(发送短信工具类)
 * @date 2018年7月11日下午5:19:01
 */
public class SmsUtil {

	// 产品名称:云通信短信API产品,开发者无需替换
	private static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	private static final String domain = "dysmsapi.aliyuncs.com";

	// 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	/**AccessKeyId*/
	private static String accessKeyId = "LTAInobXgicITHqn";
	/**accessKeySecret*/
	private static String accessKeySecret = "gHTWo4YZNAbaBaVxc4v8m8sSB09ks3";
	 /**短信签名*/
	private static String signName ="传奇C2C";
	
	/**短信模板CODE*/
	public  static String TempleteCode = "SMS_136386383";
	
	/**
	 * @Title: init 
	 * @Description TODO(初始化)
	 * @param accessKeyId 秘钥Id
	 * @param accessKeySecret  秘钥
	 * @param signName   短信签名
	 * @param identifyingTempleteCode  辨别的短信模板ID
	 * @param registTempleteCode  注册的短信模板ID
	 * @date 2018年7月11日下午5:38:59
	 */
	public static void init(String accessKeyId, String accessKeySecret, String signName, String TempleteCode) {
		SmsUtil.accessKeyId = accessKeyId;
		SmsUtil.accessKeySecret = accessKeySecret;
		SmsUtil.signName = signName;
		SmsUtil.TempleteCode = TempleteCode;
	}
	
	/**
	 * @Title: sendSms 
	 * @Description TODO(短信发送操作的封装)
	 * @param phone 手机号码
	 * @param templateParam  短信模板内容参数
	 * @param templateCode  短信模板ID
	 * @return
	 * @throws ClientException
	 * @date 2018年7月11日下午5:19:26
	 */
	public static SendSmsResponse sendSms(String phone, String templateParam, String templateCode)
			throws ClientException {

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		//使用post提交
		request.setMethod(MethodType.POST);
		// 必填:待发送手机号
		request.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);
		// 必填:短信模板-可在短信控制台中找到                 
		request.setTemplateCode(templateCode);//SMS_136386383

		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		//templateParam = "{\"code\":\"123456\"}";
		request.setTemplateParam(templateParam);

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");

		// hint请求失败这里会抛ClientException异常,注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		//解析短信响应结果
		 if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			 
        	//请求成功
			 
        }
		return sendSmsResponse;
	}

	//向用户手机发起验证短信
	public static SendSmsResponse sendIdentifyingCode(String phone, String code) throws ClientException {
				return sendSms(phone, "{\"code\":\"" + code + "\"}", TempleteCode);
	}
}