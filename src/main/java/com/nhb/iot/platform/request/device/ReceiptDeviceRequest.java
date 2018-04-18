package com.nhb.iot.platform.request.device;

import java.util.Date;

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
public class ReceiptDeviceRequest extends PageRequest {

	@XmlElement(name = "id")
	@ApiModelProperty(value = "id", required = true, example = "201708080811")
	private String id;

	@XmlElement(name = "collectorNo")
	@ApiModelProperty(value = "collectorNo", required = true, example = "201708080811")
	private String collectorNo;

	@XmlElement(name = "meterId")
	@ApiModelProperty(value = "meterId", required = true, example = "1")
	private String meterId;

	@XmlElement(name = "meterNo")
	@ApiModelProperty(value = "meterNo", required = true, example = "1")
	private String meterNo;

	@XmlElement(name = "circuitNo")
	@ApiModelProperty(value = "circuitNo", required = true, example = "1")
	private String circuitNo;

	@XmlElement(name = "name")
	@ApiModelProperty(value = "name", required = true, example = "1")
	private String name;

	@XmlElement(name = "model")
	@ApiModelProperty(value = "model", required = true, example = "1")
	private String model;

	@XmlElement(name = "isCtrl")
	@ApiModelProperty(value = "isCtrl", required = true, example = "1")
	private boolean isCtrl;

	@XmlElement(name = "activeTime")
	@ApiModelProperty(value = "activeTime", required = true, example = "1")
	private Date activeTime;

	@XmlElement(name = "switchStatus")
	@ApiModelProperty(value = "switchStatus", required = true, example = "1")
	private String switchStatus;

	@XmlElement(name = "deviceType")
	@ApiModelProperty(value = "deviceType", required = true, example = "1")
	private String deviceType;

	@XmlElement(name = "longitude")
	@ApiModelProperty(value = "longitude", required = true, example = "1")
	private Double longitude;

	@XmlElement(name = "latitude")
	@ApiModelProperty(value = "latitude", required = true, example = "1")
	private Double latitude;

	@XmlElement(name = "gpsLng")
	@ApiModelProperty(value = "gpsLng", required = true, example = "1")
	private Double gpsLng;

	@XmlElement(name = "gpsLat")
	@ApiModelProperty(value = "gpsLat", required = true, example = "1")
	private Double gpsLat;

	@XmlElement(name = "location")
	@ApiModelProperty(value = "location", required = true, example = "1")
	private String location;

	@XmlElement(name = "voltageRatio")
	@ApiModelProperty(value = "voltageRatio", required = true, example = "1")
	private Double voltageRatio;

	@XmlElement(name = "currentRatio")
	@ApiModelProperty(value = "currentRatio", required = true, example = "1")
	private Double currentRatio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
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


	public boolean isCtrl() {
    return isCtrl;
  }

  public void setCtrl(boolean isCtrl) {
    this.isCtrl = isCtrl;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
	
	

}
