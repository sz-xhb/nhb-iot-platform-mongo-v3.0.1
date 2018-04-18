package com.nhb.iot.platform.request.data;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class DataAnalyzeRequest {

  @XmlElement(name = "deviceId")
  @ApiModelProperty(value = "deviceId", required = true, example = "201709210111")
  private String deviceId;

  @XmlElement(name = "areaId")
  @ApiModelProperty(value = "areaId", required = true, example = "201709210111")
  private String areaId;

  @XmlElement(name = "deviceIds")
  @ApiModelProperty(value = "deviceIds", required = true, example = "[201709210111,201709210112]")
  private List<String> deviceIds;

  @XmlElement(name = "areaIds")
  @ApiModelProperty(value = "areaIds", required = true, example = "[201709210111,201709210112]")
  private List<String> areaIds;

  @XmlElement(name = "startDate")
  @ApiModelProperty(value = "startDate", required = true, example = "2017-09-21")
  private String startDate;

  @XmlElement(name = "endDate")
  @ApiModelProperty(value = "endDate", required = true, example = "2017-09-27")
  private String endDate;

  @XmlElement(name = "startMonth")
  @ApiModelProperty(value = "startMonth", required = true, example = "2017-08")
  private String startMonth;

  @XmlElement(name = "endMonth")
  @ApiModelProperty(value = "endMonth", required = true, example = "2017-09")
  private String endMonth;

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getAreaId() {
    return areaId;
  }

  public void setAreaId(String areaId) {
    this.areaId = areaId;
  }

  public List<String> getAreaIds() {
    return areaIds;
  }

  public void setAreaIds(List<String> areaIds) {
    this.areaIds = areaIds;
  }

  public List<String> getDeviceIds() {
    return deviceIds;
  }

  public void setDeviceIds(List<String> deviceIds) {
    this.deviceIds = deviceIds;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getStartMonth() {
    return startMonth;
  }

  public void setStartMonth(String startMonth) {
    this.startMonth = startMonth;
  }

  public String getEndMonth() {
    return endMonth;
  }

  public void setEndMonth(String endMonth) {
    this.endMonth = endMonth;
  }

}
