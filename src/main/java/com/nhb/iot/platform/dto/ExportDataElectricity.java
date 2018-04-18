package com.nhb.iot.platform.dto;

import java.util.Date;
import java.util.UUID;
import cn.afterturn.easypoi.excel.annotation.Excel;

public class ExportDataElectricity {

  @Excel(name = "地市")
  private String city;

  @Excel(name = "站点")
  private String baseName;

  @Excel(name = "设备")
  private String deviceName;

  // @Excel(name = "标识")
  private UUID id;

  // @Excel(name = "设备Id", orderNum = "0")
  private String deviceId;

  @Excel(name = "采集时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
  private Date readTime;

  // @Excel(name = "类型")
  private String electricityType;

  @Excel(name = "频率(Hz)")
  private Double frequency;

  @Excel(name = "电压(V)")
  private Double voltage;

  @Excel(name = "A相电压(V)")
  private Double voltageA;

  @Excel(name = "B相电压(V)")
  private Double voltageB;

  @Excel(name = "C相电压(V)")
  private Double voltageC;

  @Excel(name = "AB相电压(V)")
  private Double voltageAB;

  @Excel(name = "BC相电压(V)")
  private Double voltageBC;

  @Excel(name = "CA相电压(V)")
  private Double voltageCA;

  @Excel(name = "电流(A)")
  private Double current;

  @Excel(name = "A相电流(A)")
  private Double currentA;

  @Excel(name = "B相电流(A)")
  private Double currentB;

  @Excel(name = "C相电流(A)")
  private Double currentC;

  @Excel(name = "总有功功率(W)")
  private Double kw;

  @Excel(name = "A相有功功率(W)")
  private Double kwA;

  @Excel(name = "B相有功功率(W)")
  private Double kwB;

  @Excel(name = "C相有功功率(W)")
  private Double kwC;

  @Excel(name = "总无功功率(W)")
  private Double kvar;

  @Excel(name = "A相无功功率(W)")
  private Double kvarA;

  @Excel(name = "B相无功功率(W)")
  private Double kvarB;

  @Excel(name = "C相无功功率(W)")
  private Double kvarC;

  @Excel(name = "总视在功率(W)")
  private Double kva;

  @Excel(name = "A相视在功率(W)")
  private Double kvaA;

  @Excel(name = "B相视在功率(W)")
  private Double kvaB;

  @Excel(name = "C相视在功率(W)")
  private Double kvaC;

  @Excel(name = "有功总电能(KWh)")
  private Double kwh;

  @Excel(name = "正向有功总电能(KWh)")
  private Double kwhForward;

  @Excel(name = "反向有功总电能(KWh)")
  private Double kwhReverse;

  @Excel(name = "组合无功1总电能(KWh)")
  private Double kvarh1;

  @Excel(name = "组合无功2总电能(KWh)")
  private Double kvarh2;

  @Excel(name = "总功率因数")
  private Double powerFactor;

  @Excel(name = "A相功率因数")
  private Double powerFactorA;

  @Excel(name = "B相功率因数")
  private Double powerFactorB;

  @Excel(name = "C相功率因数")
  private Double powerFactorC;

  @Excel(name = "余额")
  private Double restMoney;

  public String getBaseName() {
    return baseName;
  }

  public void setBaseName(String baseName) {
    this.baseName = baseName;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Date getReadTime() {
    return readTime;
  }

  public void setReadTime(Date readTime) {
    this.readTime = readTime;
  }

  public String getElectricityType() {
    return electricityType;
  }

  public void setElectricityType(String electricityType) {
    this.electricityType = electricityType;
  }

  public Double getFrequency() {
    return frequency;
  }

  public void setFrequency(Double frequency) {
    this.frequency = frequency;
  }

  public Double getVoltage() {
    return voltage;
  }

  public void setVoltage(Double voltage) {
    this.voltage = voltage;
  }

  public Double getVoltageA() {
    return voltageA;
  }

  public void setVoltageA(Double voltageA) {
    this.voltageA = voltageA;
  }

  public Double getVoltageB() {
    return voltageB;
  }

  public void setVoltageB(Double voltageB) {
    this.voltageB = voltageB;
  }

  public Double getVoltageC() {
    return voltageC;
  }

  public void setVoltageC(Double voltageC) {
    this.voltageC = voltageC;
  }

  public Double getVoltageAB() {
    return voltageAB;
  }

  public void setVoltageAB(Double voltageAB) {
    this.voltageAB = voltageAB;
  }

  public Double getVoltageBC() {
    return voltageBC;
  }

  public void setVoltageBC(Double voltageBC) {
    this.voltageBC = voltageBC;
  }

  public Double getVoltageCA() {
    return voltageCA;
  }

  public void setVoltageCA(Double voltageCA) {
    this.voltageCA = voltageCA;
  }

  public Double getCurrent() {
    return current;
  }

  public void setCurrent(Double current) {
    this.current = current;
  }

  public Double getCurrentA() {
    return currentA;
  }

  public void setCurrentA(Double currentA) {
    this.currentA = currentA;
  }

  public Double getCurrentB() {
    return currentB;
  }

  public void setCurrentB(Double currentB) {
    this.currentB = currentB;
  }

  public Double getCurrentC() {
    return currentC;
  }

  public void setCurrentC(Double currentC) {
    this.currentC = currentC;
  }

  public Double getKw() {
    return kw;
  }

  public void setKw(Double kw) {
    this.kw = kw;
  }

  public Double getKwA() {
    return kwA;
  }

  public void setKwA(Double kwA) {
    this.kwA = kwA;
  }

  public Double getKwB() {
    return kwB;
  }

  public void setKwB(Double kwB) {
    this.kwB = kwB;
  }

  public Double getKwC() {
    return kwC;
  }

  public void setKwC(Double kwC) {
    this.kwC = kwC;
  }

  public Double getKvar() {
    return kvar;
  }

  public void setKvar(Double kvar) {
    this.kvar = kvar;
  }

  public Double getKvarA() {
    return kvarA;
  }

  public void setKvarA(Double kvarA) {
    this.kvarA = kvarA;
  }

  public Double getKvarB() {
    return kvarB;
  }

  public void setKvarB(Double kvarB) {
    this.kvarB = kvarB;
  }

  public Double getKvarC() {
    return kvarC;
  }

  public void setKvarC(Double kvarC) {
    this.kvarC = kvarC;
  }

  public Double getKva() {
    return kva;
  }

  public void setKva(Double kva) {
    this.kva = kva;
  }

  public Double getKvaA() {
    return kvaA;
  }

  public void setKvaA(Double kvaA) {
    this.kvaA = kvaA;
  }

  public Double getKvaB() {
    return kvaB;
  }

  public void setKvaB(Double kvaB) {
    this.kvaB = kvaB;
  }

  public Double getKvaC() {
    return kvaC;
  }

  public void setKvaC(Double kvaC) {
    this.kvaC = kvaC;
  }

  public Double getKwh() {
    return kwh;
  }

  public void setKwh(Double kwh) {
    this.kwh = kwh;
  }

  public Double getKwhForward() {
    return kwhForward;
  }

  public void setKwhForward(Double kwhForward) {
    this.kwhForward = kwhForward;
  }

  public Double getKwhReverse() {
    return kwhReverse;
  }

  public void setKwhReverse(Double kwhReverse) {
    this.kwhReverse = kwhReverse;
  }

  public Double getKvarh1() {
    return kvarh1;
  }

  public void setKvarh1(Double kvarh1) {
    this.kvarh1 = kvarh1;
  }

  public Double getKvarh2() {
    return kvarh2;
  }

  public void setKvarh2(Double kvarh2) {
    this.kvarh2 = kvarh2;
  }

  public Double getPowerFactor() {
    return powerFactor;
  }

  public void setPowerFactor(Double powerFactor) {
    this.powerFactor = powerFactor;
  }

  public Double getPowerFactorA() {
    return powerFactorA;
  }

  public void setPowerFactorA(Double powerFactorA) {
    this.powerFactorA = powerFactorA;
  }

  public Double getPowerFactorB() {
    return powerFactorB;
  }

  public void setPowerFactorB(Double powerFactorB) {
    this.powerFactorB = powerFactorB;
  }

  public Double getPowerFactorC() {
    return powerFactorC;
  }

  public void setPowerFactorC(Double powerFactorC) {
    this.powerFactorC = powerFactorC;
  }

  public Double getRestMoney() {
    return restMoney;
  }

  public void setRestMoney(Double restMoney) {
    this.restMoney = restMoney;
  }



}
