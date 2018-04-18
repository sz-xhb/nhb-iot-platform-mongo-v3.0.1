package com.nhb.iot.platform.entity.device;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "receipt_device")
public class ReceiptDevice {

	@Id
	private String id;

	@Field("collector_no")
	private String collectorNo;

	@Field("meter_no")
	private String meterNo;

	@Field("meter_id")
	private String meterId;

	@Field("circuit_no")
	private String circuitNo;

	@Field("name")
	private String name;

	/**
	 * 设备型号
	 */
	@Field("model")
	private String model;

	/**
	 * 是否可控制
	 */
	@Field("is_ctrl")
	private boolean isCtrl;

	@Field("active_time")
	private Date activeTime;

	@Field("switch_status")
	private String switchStatus;

	@Field("device_type")
	private String deviceType;

	@Field("longitude")
	private Double longitude;

	@Field("latitude")
	private Double latitude;

	@Field("gps_lng")
	private Double gpsLng;

	@Field("gps_lat")
	private Double gpsLat;

	@Field("location")
	private String location;

	// 电压变比
	@Field("voltage_ratio")
	private Double voltageRatio;

	// 电流变比
	@Field("current_ratio")
	private Double currentRatio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectorNo() {
		return collectorNo;
	}

	public void setCollectorNo(String collectorNo) {
		this.collectorNo = collectorNo;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getCircuitNo() {
		return circuitNo;
	}

	public void setCircuitNo(String circuitNo) {
		this.circuitNo = circuitNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public String getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(String switchStatus) {
		this.switchStatus = switchStatus;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getGpsLng() {
		return gpsLng;
	}

	public void setGpsLng(Double gpsLng) {
		this.gpsLng = gpsLng;
	}

	public Double getGpsLat() {
		return gpsLat;
	}

	public void setGpsLat(Double gpsLat) {
		this.gpsLat = gpsLat;
	}

	public Double getVoltageRatio() {
		return voltageRatio;
	}

	public void setVoltageRatio(Double voltageRatio) {
		this.voltageRatio = voltageRatio;
	}

	public Double getCurrentRatio() {
		return currentRatio;
	}

	public void setCurrentRatio(Double currentRatio) {
		this.currentRatio = currentRatio;
	}

  public boolean isCtrl() {
    return isCtrl;
  }

  public void setCtrl(boolean isCtrl) {
    this.isCtrl = isCtrl;
  }

}
