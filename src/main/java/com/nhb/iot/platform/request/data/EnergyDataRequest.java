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
public class EnergyDataRequest {

  @XmlElement(name = "userId")
  @ApiModelProperty(value = "userId", required = true,
      example = "267dce1a-6d63-4bbc-a289-1c3a7acf6e2f")
  private String userId;

  @XmlElement(name = "dates")
  @ApiModelProperty(value = "dates", required = true, example = "2017-11-09,2017-11-10")
  private String dates;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDates() {
    return dates;
  }

  public void setDates(String dates) {
    this.dates = dates;
  }

}
