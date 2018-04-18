package com.nhb.iot.platform.entity.data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The persistent class for the data_switch database table.
 * 
 */
@Document(collection = "data_switch")
public class DataSwitch {

	@Id
	private String id;

	@Field("device_id")
	private String deviceId;

	@Field("read_time")
	private Date readTime;

	@Field("status")
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getReadTime() {
		return this.readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}