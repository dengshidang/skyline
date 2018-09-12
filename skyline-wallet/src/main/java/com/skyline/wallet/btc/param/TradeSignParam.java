package com.skyline.wallet.btc.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeSignParam {

	/** 原始交易十六进制 **/
	private String hex;
	
	/** 有效交易信息 **/
	private List<Map<String, Object>> tradeArray;
	
	/** 签名私钥集合 **/
	private List<String> prvKeyArray;
	
	public TradeSignParam(String hex,TradeUnused tradeUnused,String prvKey){
		this.hex = hex;
		
		tradeArray = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("txid", tradeUnused.getTxid());
		map.put("vout", tradeUnused.getVout());
		map.put("amount", tradeUnused.getAmount());
		map.put("scriptPubKey", tradeUnused.getScriptPubKey());
		map.put("redeemScript", tradeUnused.getRedeemScript());
		tradeArray.add(map);
		
		prvKeyArray = new ArrayList<String>();
		prvKeyArray.add(prvKey);
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public List<Map<String, Object>> getTradeArray() {
		return tradeArray;
	}

	public void setTradeArray(List<Map<String, Object>> tradeArray) {
		this.tradeArray = tradeArray;
	}

	public List<String> getPrvKeyArray() {
		return prvKeyArray;
	}

	public void setPrvKeyArray(List<String> prvKeyArray) {
		this.prvKeyArray = prvKeyArray;
	}
	
	
}
