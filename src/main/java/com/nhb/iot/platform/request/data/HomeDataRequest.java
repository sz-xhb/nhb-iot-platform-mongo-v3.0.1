package com.nhb.iot.platform.request.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class HomeDataRequest {

  @XmlElement(name = "userId")
  @ApiModelProperty(value = "userId", required = true,
      example = "6d70b512-3dc4-40f1-b4bb-e35e78e4921a")
  private String userId;

  @XmlElement(name = "startDate")
  @ApiModelProperty(value = "startDate", required = true, example = "2017-09-01")
  private String startDate;

  @XmlElement(name = "endDate")
  @ApiModelProperty(value = "endDate", required = true, example = "2017-09-30")
  private String endDate;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

}
