package com.nhb.iot.platform.entity.device;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "receipt_meter")
public class ReceiptMeter {

	@Id
	private String id;

	@Field("collector_id")
	private String collectorId;

	@Field("meter_no")
	private String meterNo;

	@Field("name")
	private String name;

	@Field("manufacturer")
	private String manufacturer;

	@Field("mete_type")
	private String meterType;

	@Field("protocol_type")
	private String protocolType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

}
