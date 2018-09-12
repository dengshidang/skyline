package com.skyline.common.constant;

/**
 * 常量
 * @author lilin
 *
 */
public class Constants {
	
	/**当前用户*/
	public static final String CURRENT_USER="user";
	public static final String CURRENT_USER_JSON="userJson";
	public static final String CURRENT_TOKEN="token";
	/**
	 *   token失效时间
	 */
	public static final Integer  TOKEN_EXPIRES_TIME=30*60;
	/**
	 * 图形验证码失效时间
	 */
	public static final Integer IMGCODE_EXPIRES_TIME=5*60;
	/**
	 * 验证码失效时间
	 */
	public static final Integer CODE_EXPIRES_TIME=5*60;
	
	/** 默认的起始页 **/
	public static final int DEFAULT_PAGE_NO = 1;

	/** iso字符编码 **/
	public static final String ISO8859_1_CODE = "ISO8859-1";

	/** UTF-8字符编码 **/
	public static final String UTF_8_CODE = "UTF-8";

	/** 默认的分页显示数量 **/
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 个人账单信息记录 类型
	 */
	/**0:收入*/
	public static final int  BILLINFO_TYPE_0=0;
	/**1:支出*/
	public static final int  BILLINFO_TYPE_1=1;
	/**2:冻结*/
	public static final int  BILLINFO_TYPE_2=2;
	
	/**
	 * 个人账单信息记录 行为
	 */
	/** C2C挂单*/
	public static final int  BILLINFO_BEHAVIOR_0=0;
	/**C2C交易 */
	public static final int  BILLINFO_BEHAVIOR_1=1;
	/**C2C挂单撤销*/
	public static final int  BILLINFO_BEHAVIOR_2=2;
	/**充值*/
	public static final int  BILLINFO_BEHAVIOR_3=3;
	/**提现*/
	public static final int  BILLINFO_BEHAVIOR_4=4;
	/**币币交易委托*/
	public static final int  BILLINFO_BEHAVIOR_5=5;
	/**币币交易成交*/
	public static final int  BILLINFO_BEHAVIOR_6=6;
	/**币币交易撤销*/
	public static final int  BILLINFO_BEHAVIOR_7=7;
	
	/**
	 * 个人账单信息记录 状态
	 */
	/**默认状态*/
	public static final int  BILLINFO_STATUS_0=0;
	
	/**
	 *  发送短信或邮箱类型
	 */
	/**注册*/
	public static final int SEND_TYPE_0=0;
	/**找回密码*/
	public static final int SEND_TYPE_1=1;
	/**绑定邮箱或者绑定手机号码*/
	public static final int SEND_TYPE_2=2;
	/**找回资金密码**/
	public static final int SEND_TYPE_3=3;
	
	
	/**
	 * 支付类型
	 */
	/**微信*/
	public static final int BANK_INFO_TYPE_0=0;
	/**支付宝*/
	public static final int BANK_INFO_TYPE_1=1;
	/**银行卡*/
	public static final int BANK_INFO_TYPE_2=2;
	
	
}
