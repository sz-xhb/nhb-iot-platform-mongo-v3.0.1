package com.nhb.iot.platform.entity.alarm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Administrator on 2017/11/7.
 */
@Document(collection = "alarm_rule")
public class AlarmRule {

  @Id
  @Field("device_id")
  private String deviceId;

  // 电压上限
  @Field("volt_hi")
  private Double voltHI;

  // 电压下限
  @Field("volt_lo")
  private Double voltLO;

  // 电流上限
  @Field("curr_hi")
  private Double currHI;

  // 电流下限
  @Field("curr_lo")
  private Double currLO;

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Double getVoltHI() {
    return voltHI;
  }

  public void setVoltHI(Double voltHI) {
    this.voltHI = voltHI;
  }

  public Double getVoltLO() {
    return voltLO;
  }

  public void setVoltLO(Double voltLO) {
    this.voltLO = voltLO;
  }

  public Double getCurrHI() {
    return currHI;
  }

  public void setCurrHI(Double currHI) {
    this.currHI = currHI;
  }

  public Double getCurrLO() {
    return currLO;
  }

  public void setCurrLO(Double currLO) {
    this.currLO = currLO;
  }
}
