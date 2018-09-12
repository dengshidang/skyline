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
 * @version 2018年8月7日 下午2:23:06
 * 
 */
@Entity
@Table(name = "se_t_adminlog")
public class Adminlog implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键(自增) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** 管理员ID */
    @Column(name = "adminId")
    private Integer adminId;

    /** 管理员昵称 */
    @Column(name = "adminName")
    private String adminName;

    /** 操作 */
    @Column(name = "operaction")
    private String operaction;

    /** 内容 */
    @Column(name = "content")
    private String content;

    /** 创建时间 */
    @Column(name = "createTime")
    private String createTime;

    /** 更新时间 */
    @Column(name = "updateTime")
    private String updateTime;

    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getId() {
	return id;
    }

    public void setAdminId(Integer adminId) {
	this.adminId = adminId;
    }

    public Integer getAdminId() {
	return adminId;
    }

    public void setAdminName(String adminName) {
	this.adminName = adminName;
    }

    public String getAdminName() {
	return adminName;
    }

    public void setOperaction(String operaction) {
	this.operaction = operaction;
    }

    public String getOperaction() {
	return operaction;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getContent() {
	return content;
    }

    public void setCreateTime(String createTime) {
	this.createTime = createTime;
    }

    public String getCreateTime() {
	return createTime;
    }

    public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
    }

    public String getUpdateTime() {
	return updateTime;
    }

}
