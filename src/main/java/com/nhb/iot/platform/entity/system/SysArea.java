package com.nhb.iot.platform.entity.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sys_area")
public class SysArea {

	@Id
	private String id;

	/**
	 * 区域层级结构
	 */
	@Field("parent_id")
	private String parentId;

	/**
	 * 名称
	 */
	@Field("name")
	private String name;

	@Field("area_type")
	private String areaType;

	/**
	 * 创建时间
	 */
	@Field("create_time")
	private Date createTime;

	/**
	 * 管理员的账号
	 */
	@Field("tenant_id")
	private String tenantId;

	/**
	 * 站点类型
	 */
	@Field("site_type")
	private String siteType;

	/**
	 * 基本参数 json 格式
	 */
	@Field("params")
	private List<Map<String, String>> params;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public List<Map<String, String>> getParams() {
		return params;
	}

	public void setParams(List<Map<String, String>> params) {
		this.params = params;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
