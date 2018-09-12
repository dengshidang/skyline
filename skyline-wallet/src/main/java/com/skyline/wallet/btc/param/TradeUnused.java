package com.skyline.wallet.btc.param;

/**
 * bitcoin未被使用交易信息实体
 * @author Yjian
 *
 */
public class TradeUnused {

	/** 交易ID **/
	private String txid;
	
	/** 索引 **/
	private int vout;
	
	/** 交易地址 **/
	private String address;
	
	/** 赎回脚本 **/
	private String redeemScript;
	
	/** 锁定脚本 **/
	private String scriptPubKey;
	
	/** 金额 **/
	private double amount;
	
	/** 交易确认次数 **/
	private int confirmations;
	
	/** 是否可用 **/
	private boolean spendable;
	
	/** 是否可解决 **/
	private boolean solvable;
	
	/** 是否安全 **/
	private boolean safe;

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public int getVout() {
		return vout;
	}

	public void setVout(int vout) {
		this.vout = vout;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRedeemScript() {
		return redeemScript;
	}

	public void setRedeemScript(String redeemScript) {
		this.redeemScript = redeemScript;
	}

	public String getScriptPubKey() {
		return scriptPubKey;
	}

	public void setScriptPubKey(String scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	public boolean isSpendable() {
		return spendable;
	}

	public void setSpendable(boolean spendable) {
		this.spendable = spendable;
	}

	public boolean isSolvable() {
		return solvable;
	}

	public void setSolvable(boolean solvable) {
		this.solvable = solvable;
	}

	public boolean isSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}
	
	
	
}
