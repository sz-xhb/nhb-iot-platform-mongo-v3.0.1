package com.nhb.iot.platform.request.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.nhb.iot.platform.base.page.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class SysAreaRequest extends PageRequest {

	@XmlElement(name = "id")
	@ApiModelProperty(value = "id", required = true, example = "11")
	private String id;

	@XmlElement(name = "deviceId")
	@ApiModelProperty(value = "deviceId", required = true, example = "201709200111")
	private String deviceId;

	@XmlElement(name = "tenantId")
	@ApiModelProperty(value = "tenantId", required = true, example = "6d70b512-3dc4-40f1-b4bb-e35e78e4921a")
	private String tenantId;

	@XmlElement(name = "siteType")
	@ApiModelProperty(value = "siteType", required = true, example = "TXJ")
	private String siteType;

	@XmlElement(name = "parentId")
	@ApiModelProperty(value = "parentId", required = true, example = "TXJ")
	private String parentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
