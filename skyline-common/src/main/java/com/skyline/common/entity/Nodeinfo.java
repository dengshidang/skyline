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
 * 创建时间：2018-07-09 10:42:07
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_nodeinfo")
public class Nodeinfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键(自增)*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 节点标题*/
	@Column(name = "title")
	private String title;

	/** 节点链接*/
	@Column(name = "url")
	private String url;

	/** 级别(0:父级,1:子级)*/
	@Column(name = "level")
	private Integer level;

	/** 父级ID*/
	@Column(name = "upLevel")
	private Integer upLevel;

	/** 状态:(0:正常,1:禁用)*/
	@Column(name = "status")
	private Integer status;

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

	public void setUrl(String url){
		this.url=url;
	}

	public String getUrl(){
		return url;
	}

	public void setLevel(Integer level){
		this.level=level;
	}

	public Integer getLevel(){
		return level;
	}

	public void setUpLevel(Integer upLevel){
		this.upLevel=upLevel;
	}

	public Integer getUpLevel(){
		return upLevel;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
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
