package com.nhb.iot.platform.base.page;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class PageRequest {
  @XmlElement(name = "page")
  @ApiModelProperty(value = "page", required = true, example = "1")
  private Integer page;

  @XmlElement(name = "rows")
  @ApiModelProperty(value = "rows", required = true, example = "10")
  private Integer rows;

  @XmlElement(name = "startTime")
  @ApiModelProperty(value = "startTime", required = true, example = "2017-01-01 00:00:00")
  private String startTime;

  @XmlElement(name = "endTime")
  @ApiModelProperty(value = "endTime", required = true, example = "2017-07-19 00:00:00")
  private String endTime;

  public PageRequest() {
    super();
  }

  public PageRequest(Integer page, Integer rows) {
    super();
    this.page = page;
    this.rows = rows;
  }

  public Integer getRows() {
    return rows;
  }

  public void setRows(Integer rows) {
    this.rows = rows;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

}
