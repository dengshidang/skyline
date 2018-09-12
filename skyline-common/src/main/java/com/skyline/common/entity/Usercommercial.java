package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释：  <br/>
 * 类描述：  <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-08-06 10:14:38
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_usercommercial")
public class Usercommercial implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 商户录制视频*/
	@Column(name = "videoUrl")
	private String videoUrl;

	/** 商户资料文档*/
	@Column(name = "dcoUrl")
	private String dcoUrl;

	/** 状态（0:未审核，1:通过，2:拒绝）*/
	@Column(name = "status")
	private Integer status;

	/** 描述*/
	@Column(name = "remark")
	private String remark;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;


	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setVideoUrl(String videoUrl){
		this.videoUrl=videoUrl;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public void setDcoUrl(String dcoUrl){
		this.dcoUrl=dcoUrl;
	}

	public String getDcoUrl(){
		return dcoUrl;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return remark;
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

}
