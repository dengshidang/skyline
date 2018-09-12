package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 币种信息表 <br/>
 * 类描述： 币种信息表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-08-06 16:41:23
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_coininfo")
public class Coininfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 币种简称*/
	@Column(name = "name")
	private String name;

	/** 类型*/
	@Column(name = "type")
	private String type;

	/** 钱包服务器ID*/
	@Column(name = "walletId")
	private Integer walletId;

	/** 中文名称*/
	@Column(name = "title")
	private String title;

	/** 英文名称*/
	@Column(name = "englishTitle")
	private String englishTitle;

	/** 币种图片URL*/
	@Column(name = "img")
	private String img;

	/** 排序*/
	@Column(name = "sort")
	private Integer sort;

	/** 挂单比例*/
	@Column(name = "ratio")
	private String ratio;

	/** 每日转出限额比例*/
	@Column(name = "meitian")
	private String meitian;

	/** 确认次数*/
	@Column(name = "time")
	private Integer time;

	/** 转入说明*/
	@Column(name = "shifttoRule")
	private String shifttoRule;

	/** 转出说明*/
	@Column(name = "outRule")
	private String outRule;

	/** 手续费*/
	@Column(name = "fee")
	private Double fee;

	/** 官方收取手续费用户*/
	@Column(name = "feeUser")
	private Integer feeUser;

	/** 最小转出金额*/
	@Column(name = "outMin")
	private Double outMin;

	/** 最大转出金额*/
	@Column(name = "outMax")
	private Double outMax;

	/** 转出状态0:正常1:禁止*/
	@Column(name = "outStatus")
	private Integer outStatus;

	/** 详情介绍*/
	@Column(name = "detail")
	private String detail;

	/** 转出自动(0禁止1允许)*/
	@Column(name = "outSelf")
	private Integer outSelf;

	/** 钱包下载*/
	@Column(name = "dowWallet")
	private String dowWallet;

	/** 源码下载*/
	@Column(name = "dowCode")
	private String dowCode;

	/** 官方链接*/
	@Column(name = "officialUrl")
	private String officialUrl;

	/** 官方论坛*/
	@Column(name = "officialForum")
	private String officialForum;

	/** 研发者*/
	@Column(name = "author")
	private String author;

	/** 核心算法*/
	@Column(name = "alg")
	private String alg;

	/** 发布时间*/
	@Column(name = "birthTime")
	private String birthTime;

	/** 发行的总量*/
	@Column(name = "gross")
	private String gross;

	/** 存量*/
	@Column(name = "storageNum")
	private String storageNum;

	/** 证明方式*/
	@Column(name = "prove")
	private String prove;

	/** 冷钱包地址*/
	@Column(name = "cold")
	private String cold;

	/** 公网转账手续费*/
	@Column(name = "netWork")
	private Double netWork;

	/** 协议类型:ERC20=ERC-20标准代币*/
	@Column(name = "protocol")
	private String protocol;

	/** 智能合约ABI*/
	@Column(name = "scAbi")
	private String scAbi;

	/** 智能合约地址*/
	@Column(name = "scAddress")
	private String scAddress;

	/** 状态（0有效，1无效）*/
	@Column(name = "status")
	private Integer status;

	/** 最后登入时间*/
	@Column(name = "logTime")
	private String logTime;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;

	/** 是否删除(Y/N)*/
	@Column(name = "deleteSign")
	private String deleteSign;


	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setType(String type){
		this.type=type;
	}

	public String getType(){
		return type;
	}

	public void setWalletId(Integer walletId){
		this.walletId=walletId;
	}

	public Integer getWalletId(){
		return walletId;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public String getTitle(){
		return title;
	}

	public void setEnglishTitle(String englishTitle){
		this.englishTitle=englishTitle;
	}

	public String getEnglishTitle(){
		return englishTitle;
	}

	public void setImg(String img){
		this.img=img;
	}

	public String getImg(){
		return img;
	}

	public void setSort(Integer sort){
		this.sort=sort;
	}

	public Integer getSort(){
		return sort;
	}

	public void setRatio(String ratio){
		this.ratio=ratio;
	}

	public String getRatio(){
		return ratio;
	}

	public void setMeitian(String meitian){
		this.meitian=meitian;
	}

	public String getMeitian(){
		return meitian;
	}

	public void setTime(Integer time){
		this.time=time;
	}

	public Integer getTime(){
		return time;
	}

	public void setShifttoRule(String shifttoRule){
		this.shifttoRule=shifttoRule;
	}

	public String getShifttoRule(){
		return shifttoRule;
	}

	public void setOutRule(String outRule){
		this.outRule=outRule;
	}

	public String getOutRule(){
		return outRule;
	}

	public void setFee(Double fee){
		this.fee=fee;
	}

	public Double getFee(){
		return fee;
	}

	public void setFeeUser(Integer feeUser){
		this.feeUser=feeUser;
	}

	public Integer getFeeUser(){
		return feeUser;
	}

	public void setOutMin(Double outMin){
		this.outMin=outMin;
	}

	public Double getOutMin(){
		return outMin;
	}

	public void setOutMax(Double outMax){
		this.outMax=outMax;
	}

	public Double getOutMax(){
		return outMax;
	}

	public void setOutStatus(Integer outStatus){
		this.outStatus=outStatus;
	}

	public Integer getOutStatus(){
		return outStatus;
	}

	public void setDetail(String detail){
		this.detail=detail;
	}

	public String getDetail(){
		return detail;
	}

	public void setOutSelf(Integer outSelf){
		this.outSelf=outSelf;
	}

	public Integer getOutSelf(){
		return outSelf;
	}

	public void setDowWallet(String dowWallet){
		this.dowWallet=dowWallet;
	}

	public String getDowWallet(){
		return dowWallet;
	}

	public void setDowCode(String dowCode){
		this.dowCode=dowCode;
	}

	public String getDowCode(){
		return dowCode;
	}

	public void setOfficialUrl(String officialUrl){
		this.officialUrl=officialUrl;
	}

	public String getOfficialUrl(){
		return officialUrl;
	}

	public void setOfficialForum(String officialForum){
		this.officialForum=officialForum;
	}

	public String getOfficialForum(){
		return officialForum;
	}

	public void setAuthor(String author){
		this.author=author;
	}

	public String getAuthor(){
		return author;
	}

	public void setAlg(String alg){
		this.alg=alg;
	}

	public String getAlg(){
		return alg;
	}

	public void setBirthTime(String birthTime){
		this.birthTime=birthTime;
	}

	public String getBirthTime(){
		return birthTime;
	}

	public void setGross(String gross){
		this.gross=gross;
	}

	public String getGross(){
		return gross;
	}

	public void setStorageNum(String storageNum){
		this.storageNum=storageNum;
	}

	public String getStorageNum(){
		return storageNum;
	}

	public void setProve(String prove){
		this.prove=prove;
	}

	public String getProve(){
		return prove;
	}

	public void setCold(String cold){
		this.cold=cold;
	}

	public String getCold(){
		return cold;
	}

	public void setNetWork(Double netWork){
		this.netWork=netWork;
	}

	public Double getNetWork(){
		return netWork;
	}

	public void setProtocol(String protocol){
		this.protocol=protocol;
	}

	public String getProtocol(){
		return protocol;
	}

	public void setScAbi(String scAbi){
		this.scAbi=scAbi;
	}

	public String getScAbi(){
		return scAbi;
	}

	public void setScAddress(String scAddress){
		this.scAddress=scAddress;
	}

	public String getScAddress(){
		return scAddress;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setLogTime(String logTime){
		this.logTime=logTime;
	}

	public String getLogTime(){
		return logTime;
	}

	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}

	public String getCreateTime(){
		return createTime;
	}

	public void setUpdateTime(String updateTime){
		this.updateTime=updateTime;
	}

	public String getUpdateTime(){
		return updateTime;
	}

	public void setDeleteSign(String deleteSign){
		this.deleteSign=deleteSign;
	}

	public String getDeleteSign(){
		return deleteSign;
	}

}
