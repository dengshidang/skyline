package com.skyline.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhangwei
 * @version 2018年8月13日 上午9:19:15
 * 
 */
@Entity
@Table(name = "se_t_roleinfo")
public class Roleinfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键(自增) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** 角色名称 */
    @Column(name = "name")
    private String name;

    /** 备注 */
    @Column(name = "note")
    private String note;
    /** 创建时间 */
    @Column(name = "createTime")
    private String createTime;

    /** 更新时间 */
    @Column(name = "updateTime")
    private String updateTime;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }

    public String getCreateTime() {
	return createTime;
    }

    public void setCreateTime(String createTime) {
	this.createTime = createTime;
    }

    public String getUpdateTime() {
	return updateTime;
    }

    public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
    }

}
