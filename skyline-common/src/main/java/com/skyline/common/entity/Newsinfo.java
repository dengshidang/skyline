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
 * 创建时间：2018-07-20 10:14:42
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_newsinfo")
public class Newsinfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 标题*/
	@Column(name = "title")
	private String title;

	/** 内容*/
	@Column(name = "content")
	private String content;

	/** 类型(0:新闻,1:公告)*/
	@Column(name = "type")
	private String type;

	/** 状态:(0:展示,1:不展示)*/
	@Column(name = "status")
	private String status;

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

	public void setTitle(String title){
		this.title=title;
	}

	public String getTitle(){
		return title;
	}

	public void setContent(String content){
		this.content=content;
	}

	public String getContent(){
		return content;
	}

	public void setType(String type){
		this.type=type;
	}

	public String getType(){
		return type;
	}

	public void setStatus(String status){
		this.status=status;
	}

	public String getStatus(){
		return status;
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
