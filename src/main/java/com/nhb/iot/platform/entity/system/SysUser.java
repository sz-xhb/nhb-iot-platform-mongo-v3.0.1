package com.nhb.iot.platform.entity.system;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
 * @ClassName: SysUser
 * @Description: 系统用户实体类
 * @author XS guo
 * @date 2017年9月15日 下午4:24:50
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Document(collection = "sys_user")
public class SysUser {

	@Id
	private String id;

	/**
	 * 登录名
	 */
	@Field("login_name")
	private String loginName;

	@Field("password")
	private String password;

	@Field("name")
	private String name;

	@Field("address")
	private String address;

	@Field("email")
	private String email;

	@Field("phone")
	private String phone;

	@Field("login_ip")
	private String loginIp;

	/**
	 * 最近登录时间
	 */
	@Field("last_login_time")
	private Date lastLoginTime;

	@Field("create_time")
	private Date createTime;

	@Field("update_time")
	private Date updateTime;

	/**
	 * 父节点id
	 */
	@Field("parent_id")
	private String parentId;

	/**
	 * 角色： 超管，管理员，用户
	 */
	@Field("role")
	private String role;

	@Field("image")
	private String image;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
