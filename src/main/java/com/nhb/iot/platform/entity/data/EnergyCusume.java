package com.nhb.iot.platform.entity.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "energy_cusume")
public class EnergyCusume {

	@Id
	private String id;

	@Field("device_id")
	private String deviceId;

	@Field("cusume")
	private Double cusume;

	@Field("money_spent")
	private Double moneySpent;

	@Field("date")
	private String date;

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

	public Double getCusume() {
		return cusume;
	}

	public void setCusume(Double cusume) {
		this.cusume = cusume;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(Double moneySpent) {
		this.moneySpent = moneySpent;
	}

}
