package com.nhb.iot.platform.entity.alarm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

@Document(collection = "alarm")
public class Alarm {

  @Id
  private String id;

  @Field("device_id")
  private String deviceId;

  @Field("device_name")
  private String deviceName;

  @Field("alarm_time")
  private Date alarmTime;

  @Field("device_type")
  private String deviceType;

  @Field("alarm_info")
  private String alarmInfo;

  @Field("alarm_type")
  private String alarmType;

  @Field("alarm_level")
  private String alarmLevel;

  /**
   * 处理状态，0-未处理-实时告警，1-已处理-历史告警
   */
  @Field("deal_status")
  private Integer dealStatus;

  @Field("handle_reason")
  private String handleReason;

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

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public Date getAlarmTime() {
    return alarmTime;
  }

  public void setAlarmTime(Date alarmTime) {
    this.alarmTime = alarmTime;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getAlarmInfo() {
    return alarmInfo;
  }

  public void setAlarmInfo(String alarmInfo) {
    this.alarmInfo = alarmInfo;
  }

  public String getAlarmType() {
    return alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }

  public String getAlarmLevel() {
    return alarmLevel;
  }

  public void setAlarmLevel(String alarmLevel) {
    this.alarmLevel = alarmLevel;
  }

  public Integer getDealStatus() {
    return dealStatus;
  }

  public void setDealStatus(Integer dealStatus) {
    this.dealStatus = dealStatus;
  }

  public String getHandleReason() {
    return handleReason;
  }

  public void setHandleReason(String handleReason) {
    this.handleReason = handleReason;
  }
}
