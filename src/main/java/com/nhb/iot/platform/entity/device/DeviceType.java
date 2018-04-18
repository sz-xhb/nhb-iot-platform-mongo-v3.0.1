package com.nhb.iot.platform.entity.device;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "device_type")
public class DeviceType {

  @Id
  private String id;

  @Field("device_type_code")
  private String deviceTypeCode;

  @Field("device_type_name")
  private String deviceTypeName;

  @Field("user_id")
  private String userId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDeviceTypeCode() {
    return deviceTypeCode;
  }

  public void setDeviceTypeCode(String deviceTypeCode) {
    this.deviceTypeCode = deviceTypeCode;
  }

  public String getDeviceTypeName() {
    return deviceTypeName;
  }

  public void setDeviceTypeName(String deviceTypeName) {
    this.deviceTypeName = deviceTypeName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
