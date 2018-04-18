/**
 * Project Name:nhb-platform File Name:DataElectricity.java Package
 * Name:nhb.system.platform.entity.data Date:2017年9月21日下午1:46:20 Copyright (c) 2017,
 * lorisun@live.com All Rights Reserved.
 */

package com.nhb.iot.platform.dto;

import java.util.Date;
import java.util.UUID;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author xuyahui
 * @ClassName:DataElectricity
 * @Function: ADD FUNCTION.
 * @Reason: ADD REASON.
 * @Date: 2017年9月21日 下午1:46:20
 * @see
 * @since JDK 1.7
 */

public class DataElectricityDTO {

  @Excel(name = "标识")
  private UUID id;

  @Excel(name = "设备Id", orderNum = "0")
  private String deviceId;

  @Excel(name = "时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
  private Date readTime;

  @Excel(name = "设备Id")
  private String electricityType;

  @Excel(name = "设备Id")
  private Double frequency;

  @Excel(name = "设备Id")
  private Double voltage;

  @Excel(name = "设备Id")
  private Double voltageA;

  @Excel(name = "设备Id")
  private Double voltageB;

  @Excel(name = "设备Id")
  private Double voltageC;

  @Excel(name = "设备Id")
  private Double voltageAB;

  @Excel(name = "设备Id")
  private Double voltageBC;

  @Excel(name = "设备Id")
  private Double voltageCA;

  @Excel(name = "设备Id")
  private Double current;

  @Excel(name = "设备Id")
  private Double currentA;
  @Excel(name = "设备Id")
  private Double currentB;
  @Excel(name = "设备Id")
  private Double currentC;
  @Excel(name = "设备Id")
  private Double kw;
  @Excel(name = "设备Id")
  private Double kwA;
  @Excel(name = "设备Id")
  private Double kwB;
  @Excel(name = "设备Id")
  private Double kwC;
  @Excel(name = "设备Id")
  private Double kvar;
  @Excel(name = "设备Id")
  private Double kvarA;

  @Excel(name = "设备Id")
  private Double kvarB;

  @Excel(name = "设备Id")
  private Double kvarC;

  @Excel(name = "设备Id")
  private Double kva;

  @Excel(name = "设备Id")
  private Double kvaA;

  @Excel(name = "设备Id")
  private Double kvaB;

  @Excel(name = "设备Id")
  private Double kvaC;

  @Excel(name = "设备Id")
  private Double kwh;

  @Excel(name = "设备Id")
  private Double kwhForward;

  @Excel(name = "设备Id")
  private Double kwhReverse;

  @Excel(name = "设备Id")
  private Double kvarh1;

  @Excel(name = "设备Id")
  private Double kvarh2;

  @Excel(name = "设备Id")
  private Double powerFactor;

  @Excel(name = "设备Id")
  private Double powerFactorA;

  @Excel(name = "设备Id")
  private Double powerFactorB;

  @Excel(name = "设备Id")
  private Double powerFactorC;

  @Excel(name = "设备Id")
  private Double restMoney;

  public DataElectricityDTO(UUID id, String deviceId, Date readTime, String electricityType,
      Double frequency, Double voltage, Double voltageA, Double voltageB, Double voltageC,
      Double voltageAB, Double voltageBC, Double voltageCA, Double current, Double currentA,
      Double currentB, Double currentC, Double kw, Double kwA, Double kwB, Double kwC, Double kvar,
      Double kvarA, Double kvarB, Double kvarC, Double kva, Double kvaA, Double kvaB, Double kvaC,
      Double kwh, Double kwhForward, Double kwhReverse, Double kvarh1, Double kvarh2,
      Double powerFactor, Double powerFactorA, Double powerFactorB, Double powerFactorC,
      Double restMoney) {
    super();
    this.id = id;
    this.deviceId = deviceId;
    this.readTime = readTime;
    this.electricityType = electricityType;
    this.frequency = frequency;
    this.voltage = voltage;
    this.voltageA = voltageA;
    this.voltageB = voltageB;
    this.voltageC = voltageC;
    this.voltageAB = voltageAB;
    this.voltageBC = voltageBC;
    this.voltageCA = voltageCA;
    this.current = current;
    this.currentA = currentA;
    this.currentB = currentB;
    this.currentC = currentC;
    this.kw = kw;
    this.kwA = kwA;
    this.kwB = kwB;
    this.kwC = kwC;
    this.kvar = kvar;
    this.kvarA = kvarA;
    this.kvarB = kvarB;
    this.kvarC = kvarC;
    this.kva = kva;
    this.kvaA = kvaA;
    this.kvaB = kvaB;
    this.kvaC = kvaC;
    this.kwh = kwh;
    this.kwhForward = kwhForward;
    this.kwhReverse = kwhReverse;
    this.kvarh1 = kvarh1;
    this.kvarh2 = kvarh2;
    this.powerFactor = powerFactor;
    this.powerFactorA = powerFactorA;
    this.powerFactorB = powerFactorB;
    this.powerFactorC = powerFactorC;
    this.restMoney = restMoney;
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
