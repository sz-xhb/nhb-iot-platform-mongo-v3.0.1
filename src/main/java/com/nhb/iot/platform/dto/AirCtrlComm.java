package com.nhb.iot.platform.dto;

public class AirCtrlComm {

	private String status;
	// 制冷状态异常告警
	private String refrigerationEx;
	// 制热状态异常告警
	private String heatingEx;
	// 电源告警
	private String powerEx;
	// 电流传感器故障
	private String currsensorEx;
	// 回风温度传感器故障
	private String returnairEx;
	// 出风温度传感器故障
	private String deliveryairEx;
	// A相电压
	private Integer powerA;
	// A相电流
	private Double currentA;
	// A相电压
	private Integer powerB;
	// A相电流
	private Double currentB;
	// A相电压
	private Integer powerC;
	// A相电流
	private Double currentC;
	// 送风温度
	private Integer supplyTemp;
	// 回风温度
	private Integer returnTemp;

	private Double kwh;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefrigerationEx() {
		return refrigerationEx;
	}

	public void setRefrigerationEx(String refrigerationEx) {
		this.refrigerationEx = refrigerationEx;
	}

	public String getHeatingEx() {
		return heatingEx;
	}

	public void setHeatingEx(String heatingEx) {
		this.heatingEx = heatingEx;
	}

	public String getPowerEx() {
		return powerEx;
	}

	public void setPowerEx(String powerEx) {
		this.powerEx = powerEx;
	}

	public String getCurrsensorEx() {
		return currsensorEx;
	}

	public void setCurrsensorEx(String currsensorEx) {
		this.currsensorEx = currsensorEx;
	}

	public String getReturnairEx() {
		return returnairEx;
	}

	public void setReturnairEx(String returnairEx) {
		this.returnairEx = returnairEx;
	}

	public String getDeliveryairEx() {
		return deliveryairEx;
	}

	public void setDeliveryairEx(String deliveryairEx) {
		this.deliveryairEx = deliveryairEx;
	}

	public Integer getPowerA() {
		return powerA;
	}

	public void setPowerA(Integer powerA) {
		this.powerA = powerA;
	}

	public Double getCurrentA() {
		return currentA;
	}

	public void setCurrentA(Double currentA) {
		this.currentA = currentA;
	}

	public Integer getPowerB() {
		return powerB;
	}

	public void setPowerB(Integer powerB) {
		this.powerB = powerB;
	}

	public Double getCurrentB() {
		return currentB;
	}

	public void setCurrentB(Double currentB) {
		this.currentB = currentB;
	}

	public Integer getPowerC() {
		return powerC;
	}

	public void setPowerC(Integer powerC) {
		this.powerC = powerC;
	}

	public Double getCurrentC() {
		return currentC;
	}

	public void setCurrentC(Double currentC) {
		this.currentC = currentC;
	}

	public Integer getSupplyTemp() {
		return supplyTemp;
	}

	public void setSupplyTemp(Integer supplyTemp) {
		this.supplyTemp = supplyTemp;
	}

	public Integer getReturnTemp() {
		return returnTemp;
	}

	public void setReturnTemp(Integer returnTemp) {
		this.returnTemp = returnTemp;
	}

	public Double getKwh() {
		return kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

}
