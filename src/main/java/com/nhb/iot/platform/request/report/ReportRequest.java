package com.nhb.iot.platform.request.report;

import java.util.List;
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
public class ReportRequest extends PageRequest {

	@XmlElement(name = "id") // 区域id，一个
	@ApiModelProperty(value = "id", required = true, example = "1")
	private String id;

	@XmlElement(name = "areaIds") // 区域id,一个或多个
	@ApiModelProperty(value = "areaIds", required = false, example = "[1,2,3]")
	private List<String> areaIds;

	@XmlElement(name = "areaId") // 区域id,一个或多个
	@ApiModelProperty(value = "areaId", required = false, example = "101")
	private String areaId;

	@XmlElement(name = "userId") // 区域id，一个
	@ApiModelProperty(value = "userId", required = true, example = "1")
	private String userId;

	@XmlElement(name = "deviceId")
	@ApiModelProperty(value = "deviceId", required = false, example = "C17051601M170502")
	private String deviceId;
	
	@XmlElement(name = "deviceIds")
	@ApiModelProperty(value = "deviceIds", required = false, example = "[C17051601M170502,21212,2323]")
	private List<String> deviceIds;

	@XmlElement(name = "resourceType")
	@ApiModelProperty(value = "resourceType", required = true, example = "area")
	private String resourceType;

	@XmlElement(name = "day")
	@ApiModelProperty(value = "day", required = true, example = "2018-04-02")
	private String day;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(List<String> areaIds) {
		this.areaIds = areaIds;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public List<String> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<String> deviceIds) {
		this.deviceIds = deviceIds;
	}

	

}
