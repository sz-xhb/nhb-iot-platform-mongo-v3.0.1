package com.nhb.iot.platform.entity.data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "history_data")
public class HistoryData {

	@Id
	private String id;

	@Field("device_id")
	private String deviceId;

//	@Field("read_time")
//	private Date readTime;
	
	@Field("meter_time")
	private Date meterTime;

	@Field("service_id")
	private String serviceId;

	@Field("data")
	private Object data;

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

//	public Date getReadTime() {
//		return readTime;
//	}
//
//	public void setReadTime(Date readTime) {
//		this.readTime = readTime;
//	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Date getMeterTime() {
		return meterTime;
	}

	public void setMeterTime(Date meterTime) {
		this.meterTime = meterTime;
	}

}
