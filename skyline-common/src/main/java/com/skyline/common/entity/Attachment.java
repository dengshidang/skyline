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
 * 创建时间：2018-07-20 15:34:56
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_attachment")
public class Attachment implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 物理路径*/
	@Column(name = "url")
	private String url;

	/** 图片宽度*/
	@Column(name = "imgWidth")
	private Integer imgWidth;

	/** 图片高度*/
	@Column(name = "imgHeight")
	private Integer imgHeight;

	/** 图片类型*/
	@Column(name = "imgType")
	private String imgType;

	/** 图片帧数*/
	@Column(name = "imgFrames")
	private Integer imgFrames;

	/** 图片大小*/
	@Column(name = "filesize")
	private Integer filesize;

	/** Mime类型*/
	@Column(name = "mimeType")
	private String mimeType;

	/** 透传数据*/
	@Column(name = "extparam")
	private String extparam;

	/** 文件存储位置*/
	@Column(name = "storage")
	private String storage;

	/** 文件编码*/
	@Column(name = "sha")
	private String sha;

	/** 上传时间*/
	@Column(name = "uploadTime")
	private String uploadTime;

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

	public void setUrl(String url){
		this.url=url;
	}

	public String getUrl(){
		return url;
	}

	public void setImgWidth(Integer imgWidth){
		this.imgWidth=imgWidth;
	}

	public Integer getImgWidth(){
		return imgWidth;
	}

	public void setImgHeight(Integer imgHeight){
		this.imgHeight=imgHeight;
	}

	public Integer getImgHeight(){
		return imgHeight;
	}

	public void setImgType(String imgType){
		this.imgType=imgType;
	}

	public String getImgType(){
		return imgType;
	}

	public void setImgFrames(Integer imgFrames){
		this.imgFrames=imgFrames;
	}

	public Integer getImgFrames(){
		return imgFrames;
	}

	public void setFilesize(Integer filesize){
		this.filesize=filesize;
	}

	public Integer getFilesize(){
		return filesize;
	}

	public void setMimeType(String mimeType){
		this.mimeType=mimeType;
	}

	public String getMimeType(){
		return mimeType;
	}

	public void setExtparam(String extparam){
		this.extparam=extparam;
	}

	public String getExtparam(){
		return extparam;
	}

	public void setStorage(String storage){
		this.storage=storage;
	}

	public String getStorage(){
		return storage;
	}

	public void setSha(String sha){
		this.sha=sha;
	}

	public String getSha(){
		return sha;
	}

	public void setUploadTime(String uploadTime){
		this.uploadTime=uploadTime;
	}

	public String getUploadTime(){
		return uploadTime;
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
