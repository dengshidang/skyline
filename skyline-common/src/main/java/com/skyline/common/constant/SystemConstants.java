package com.skyline.common.constant;

public class SystemConstants {
	
	
	/**充值审核状态 |待审核:0*/
	public static final int COINTRANSACTION_STATUS_0=0;
	/**充值审核状态 |审核成功:1*/
	public static final int COINTRANSACTION_STATUS_1=1;
	/**充值审核状态 |审核失败:2*/
	public static final int COINTRANSACTION_STATUS_2=2;
	/**充值审核状态 |充值退回:3*/
	public static final int COINTRANSACTION_STATUS_3=3;
	/**充值审核状态 |异常打款:4*/
	public static final int COINTRANSACTION_STATUS_4=4;
	
	
	/**提币审核状态 |待审核*/
	public static final int COINEXTRACT_STATUS_0=0;
	/**提币审核状态 |通过*/
	public static final int COINEXTRACT_STATUS_1=1;
	/**提币审核状态 |驳回*/
	public static final int COINEXTRACT_STATUS_2=2;
	
	
	/**绑卡类型 | 0 微信*/
	public static final String BIND_BANK_TYPE_0="0";
	/**绑卡类型 | 1 支付宝*/
	public static final String BIND_BANK_TYPE_1="1";
	/**绑卡类型 | 2 银行卡*/
	public static final String BIND_BANK_TYPE_2="2";
	
	/**实名认证审核 |  0 未审核*/
	public static final int   CERTIFIEDCATION_TYPE_0 =0;
	/**实名认证审核 |  1 审核通过*/
	public static final int   CERTIFIEDCATION_TYPE_1 =1;
	/**实名认证审核 |  2 审核不通过*/
	public static final int   CERTIFIEDCATION_TYPE_2 =2;
	
	/**实名认证标志|  1 未认证（包含认证未通过）*/
	public static final int   USERINFO_ISSIGN_0 =0;
	/**实名认证标志|  2 已认证*/
	public static final int   USERINFO_ISSIGN_1 =1;
	
	/**受邀级别|  一级*/
	public static final int   INVATATION_RECORD_CLASS_1 =1;
	/**受邀级别|  二级*/
	public static final int   INVATATION_RECORD_CLASS_2 =2;
	/**受邀级别|  三级*/
	public static final int   INVATATION_RECORD_CLASS_3 =3;
	
	
	
	/**信息是否有效|有效*/
	public static final int IS_VALID_0=0;
	/**信息是否有效|无效*/
	public static final int IS_VALID_1=1;
	
	/**是否为默认|是*/
	public static final int IS_DEFAULT_0=0;
	/**是否为默认|否*/
	public static final int IS_DEFAULT_1=1;
	
}
