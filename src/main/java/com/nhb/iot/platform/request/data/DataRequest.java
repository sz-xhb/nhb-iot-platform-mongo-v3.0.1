/**
 * Project Name:nhb-platform File Name:DataRequest.java Package
 * Name:nhb.system.platform.request.data Date:2017年9月21日下午4:03:59 Copyright (c) 2017,
 * lorisun@live.com All Rights Reserved.
 * 
 */

package com.nhb.iot.platform.request.data;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.nhb.iot.platform.base.page.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName:DataRequest
 * @Function: ADD FUNCTION.
 * @Reason: ADD REASON.
 * @Date: 2017年9月21日 下午4:03:59
 * @author xuyahui
 * @version
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class DataRequest extends PageRequest {

	@XmlElement(name = "deviceId")
	@ApiModelProperty(value = "deviceId", required = true, example = "228")
	private String deviceId;

	@XmlElement(name = "deviceIds")
	@ApiModelProperty(value = "deviceIds", required = true, example = "[226,228]")
	private List<String> deviceIds;

	@XmlElement(name = "deviceModel")
	@ApiModelProperty(value = "deviceModel", required = true, example = "ELECTRICITY")
	private String deviceModel;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public List<String> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<String> deviceIds) {
		this.deviceIds = deviceIds;
	}

}
