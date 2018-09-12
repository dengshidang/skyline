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
 * 创建时间：2018-07-24 15:42:45
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_inviteinfo")
public class Inviteinfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 邀请用户ID*/
	@Column(name = "parentId")
	private Integer parentId;

	/** 邀请用户的邀请码*/
	@Column(name = "parentInvite")
	private String parentInvite;

	/** 受邀用户ID*/
	@Column(name = "puisneId")
	private Integer puisneId;

	/** 受请用户的邀请码*/
	@Column(name = "puisneInvite")
	private String puisneInvite;

	/** 状态（预留）*/
	@Column(name = "status")
	private Integer status;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;

	private String nickName;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setParentId(Integer parentId){
		this.parentId=parentId;
	}

	public Integer getParentId(){
		return parentId;
	}

	public void setParentInvite(String parentInvite){
		this.parentInvite=parentInvite;
	}

	public String getParentInvite(){
		return parentInvite;
	}

	public void setPuisneId(Integer puisneId){
		this.puisneId=puisneId;
	}

	public Integer getPuisneId(){
		return puisneId;
	}

	public void setPuisneInvite(String puisneInvite){
		this.puisneInvite=puisneInvite;
	}

	public String getPuisneInvite(){
		return puisneInvite;
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
