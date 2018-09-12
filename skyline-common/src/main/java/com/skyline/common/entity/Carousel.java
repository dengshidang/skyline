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
 * 创建时间：2018-07-19 14:33:02
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_carousel")
public class Carousel implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** */
	@Column(name = "url")
	private String url;

	/** */
	@Column(name = "sequence")
	private Integer sequence;

	/** */
	@Column(name = "title1")
	private String title1;

	/** */
	@Column(name = "title2")
	private String title2;

	/** */
	@Column(name = "title3")
	private String title3;

	/** */
	@Column(name = "createTime")
	private String createTime;

	/** */
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

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}

	public Integer getSequence(){
		return sequence;
	}

	public void setTitle1(String title1){
		this.title1=title1;
	}

	public String getTitle1(){
		return title1;
	}

	public void setTitle2(String title2){
		this.title2=title2;
	}

	public String getTitle2(){
		return title2;
	}

	public void setTitle3(String title3){
		this.title3=title3;
	}

	public String getTitle3(){
		return title3;
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
