package com.nhb.iot.platform.request.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/11/7.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class EconomyRequest {

  @XmlElement(name = "economyDeviceId")
  @ApiModelProperty(value = "economyDeviceId", required = true, example = "201709210111")
  private String economyDeviceId;

  @XmlElement(name = "economyDeviceId")
  @ApiModelProperty(value = "economyDeviceId", required = true, example = "201709210121")
  private String uneconomyDeviceId;

  @XmlElement(name = "economyStartDate")
  @ApiModelProperty(value = "economyStartDate", required = true, example = "2017-10-01")
  private String economyStartDate;

  @XmlElement(name = "economyEndDate")
  @ApiModelProperty(value = "economyEndDate", required = true, example = "2017-10-10")
  private String economyEndDate;

  @XmlElement(name = "uneconomyStartDate")
  @ApiModelProperty(value = "uneconomyStartDate", required = true, example = "2017-10-11")
  private String uneconomyStartDate;

  @XmlElement(name = "uneconomyEndDate")
  @ApiModelProperty(value = "uneconomyEndDate", required = true, example = "2017-10-30")
  private String uneconomyEndDate;

  public String getEconomyDeviceId() {
    return economyDeviceId;
  }

  public void setEconomyDeviceId(String economyDeviceId) {
    this.economyDeviceId = economyDeviceId;
  }

  public String getUneconomyDeviceId() {
    return uneconomyDeviceId;
  }

  public void setUneconomyDeviceId(String uneconomyDeviceId) {
    this.uneconomyDeviceId = uneconomyDeviceId;
  }

  public String getEconomyStartDate() {
    return economyStartDate;
  }

  public void setEconomyStartDate(String economyStartDate) {
    this.economyStartDate = economyStartDate;
  }

  public String getEconomyEndDate() {
    return economyEndDate;
  }

  public void setEconomyEndDate(String economyEndDate) {
    this.economyEndDate = economyEndDate;
  }

  public String getUneconomyStartDate() {
    return uneconomyStartDate;
  }

  public void setUneconomyStartDate(String uneconomyStartDate) {
    this.uneconomyStartDate = uneconomyStartDate;
  }

  public String getUneconomyEndDate() {
    return uneconomyEndDate;
  }

  public void setUneconomyEndDate(String uneconomyEndDate) {
    this.uneconomyEndDate = uneconomyEndDate;
  }
}
