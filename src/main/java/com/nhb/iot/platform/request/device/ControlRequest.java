package com.nhb.iot.platform.request.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class ControlRequest {

  @XmlElement(name = "userId")
  @ApiModelProperty(value = "userId", required = true,
      example = "6d70b512-3dc4-40f1-b4bb-e35e78e4921a")
  private String userId;

  @XmlElement(name = "deviceId")
  @ApiModelProperty(value = "deviceId", required = true, example = "201709210111")
  private String deviceId;

  @XmlElement(name = "type")
  @ApiModelProperty(value = "type", required = true, example = "1")
  private String type;

  @XmlElement(name = "name")
  @ApiModelProperty(value = "name", required = true, example = "采集器")
  private String name;

  @XmlElement(name = "result")
  @ApiModelProperty(value = "result", required = true, example = "true")
  private boolean result;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

}
