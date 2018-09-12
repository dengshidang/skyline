package com.skyline.wallet.util;

import com.skyline.common.constant.StatusCode;
import com.skyline.common.constant.TradeStatusCode;

import net.sf.json.JSONObject;

public class WalletResponse {

	public static String get(StatusCode statusCode,String data){
		JSONObject result = JSONObject.fromObject(statusCode.toString());
		result.put("data", data);
		return result.toString();
	}
}
