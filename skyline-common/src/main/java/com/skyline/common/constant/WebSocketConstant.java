package com.skyline.common.constant;

public class WebSocketConstant {
	/**
	 * 消息类型
	 */
	/**0 C2C订单等待支付*/
	public static final int WEBSOCKET_TYPE_0=0;
	/**1  C2C订单已支付*/
	public static final int WEBSOCKET_TYPE_1=1;
	/**2  C2C订单已完成*/
	public static final int WEBSOCKET_TYPE_2=2;
	/**3  C2C订单撤消*/
	public static final int WEBSOCKET_TYPE_3=3;
	/**4 C2C订单自动取消*/
	public static final int WEBSOCKET_TYPE_4=4;
	/**5  C2C订单待审诉*/
	public static final int WEBSOCKET_TYPE_5=5;
	/**6  C2C订单正在审诉*/
	public static final int WEBSOCKET_TYPE_6=6;
	/**7  C2C订单审诉完成*/
	public static final int WEBSOCKET_TYPE_7=7;
	/**9  C2C订单异常关闭*/
	public static final int WEBSOCKET_TYPE_9=9;
	/**20  C2C订单对话*/
	public static final int WEBSOCKET_TYPE_20=20;
}
