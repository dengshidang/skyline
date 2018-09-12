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
 * 创建时间：2018-06-19 15:42:57
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "demo")
public class DemoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 文本*/
	@Column(name = "text")
	private String text;


	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setText(String text){
		this.text=text;
	}

	public String getText(){
		return text;
	}

}
