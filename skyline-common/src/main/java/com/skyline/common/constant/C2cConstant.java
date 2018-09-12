package com.skyline.common.constant;

public class C2cConstant {
	
	/**自动取消订单时间(s)*/
	public static final int  AUTO_CANCEL_TIME=30*60;
	/**自动更改订单为待审诉(s)*/
	public static final int  AUTO_ISAPPEAL_TIME=15*60;
	/**自动更改订单为异常关闭*/
	public static final int  AUTO_STATUS_9_TIME=6*60*60;
	
	/**法币交易类型状态*/
	/**启用*/
	public static final int TRANSACTIONTYPE_STATUS_0=0;
	/**禁用*/
	public static final int TRANSACTIONTYPE_STATUS_1=1;
	/**法币交易类型-类型*/
	/**购买*/
	public static final int TRANSACTIONTYPE_TYPE_0=0;
	/**出售*/
	public static final int TRANSACTIONTYPE_TYPE_1=1;
	/**法币交易信息状态**/
	/**挂起**/
	public static final int TRANSACTION_STATUS_0=0;
	/**处理中**/
	public static final int TRANSACTION_STATUS_1=1;
	/**完成**/
	public static final int TRANSACTION_STATUS_2=2;
	/**撤销**/
	public static final int TRANSACTION_STATUS_3=3;
	/** 法币交易订单状态 **/
	/**待支付*/
	public static final int   TRANSACTIONORDER_STATUS_0=0;
	/**已支付*/
	public static final int   TRANSACTIONORDER_STATUS_1=1;
	/**完成*/
	public static final int   TRANSACTIONORDER_STATUS_2=2;
	/**撤销（取消）*/
	public static final int   TRANSACTIONORDER_STATUS_3=3;
	/**自动取消*/
	public static final int   TRANSACTIONORDER_STATUS_4=4;
	/**申诉完成*/
	public static final int   TRANSACTIONORDER_STATUS_5=5;
	/**异常关闭*/
	public static final int   TRANSACTIONORDER_STATUS_9=9;
	
	/**是否可以申诉*/
	/** 不能*/
	public static final int   TRANSACTIONORDER_ISAPPEAL_0=0;
	/** 能*/
	public static final int   TRANSACTIONORDER_ISAPPEAL_1=1;
	
	
	/**审诉状态*/
	/**审核中*/
	public static final int TRANSACTIONSTATE_STATUS_0=0;
	/**审核成功*/
	public static final int TRANSACTIONSTATE_STATUS_1=1;
	/**审核驳回*/
	public static final int TRANSACTIONSTATE_STATUS_2=2;
	/**撤消*/
	public static final int TRANSACTIONSTATE_STATUS_3=3;
	
}
