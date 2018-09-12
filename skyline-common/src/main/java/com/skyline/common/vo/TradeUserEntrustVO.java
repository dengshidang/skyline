package com.skyline.common.vo;
/**
 * 用户委托信息实体
 * @author dengshidang
 *
 */
public class TradeUserEntrustVO {
	/**委托信息id**/
	private Integer id;
	/**行情id**/
	private Integer marketId;
	/**委托方向**/
	private Integer type;
	/**交易币id**/
	private Integer precoinId;
	/**交易币名称**/
	private String precoinName;
	/**买卖币id**/
	private Integer sufcoinId;
	/**买卖币名称**/
	private String sufcoinName;
	/**委托总量**/
	private Double totalNum;
	/**成交量**/
	private Double dealNum;
	/**委托总额**/
	private Double number;
	/**委托价**/
	private Double price;
	/**委托时间**/
	private String createTime;
	/**为托行情名称，交易对**/
	private String marketName;
	/**开始时间**/
	private String startTime;
	/**结束时间**/
	private String endTime;
	/**成交均价**/	
	private Double  avgPrice;
	/**委托状态 1 当前委托，2 历史委托**/
	private Integer isCurrent;
	/**手续费**/
	private Double fee;
	/**状态**/
	private Integer status;
	
	

	public Integer getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(Integer isCurrent) {
		this.isCurrent = isCurrent;
	}

	public Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}


	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPrecoinId() {
		return precoinId;
	}

	public void setPrecoinId(Integer precoinId) {
		this.precoinId = precoinId;
	}

	public String getPrecoinName() {
		return precoinName;
	}

	public void setPrecoinName(String precoinName) {
		this.precoinName = precoinName;
	}

	public Integer getSufcoinId() {
		return sufcoinId;
	}

	public void setSufcoinId(Integer sufcoinId) {
		this.sufcoinId = sufcoinId;
	}

	public String getSufcoinName() {
		return sufcoinName;
	}

	public void setSufcoinName(String sufcoinName) {
		this.sufcoinName = sufcoinName;
	}

	public Double getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}

	public Double getDealNum() {
		return dealNum;
	}

	public void setDealNum(Double dealNum) {
		this.dealNum = dealNum;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


}
