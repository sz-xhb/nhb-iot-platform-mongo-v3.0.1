package com.nhb.iot.platform.request.device;

import javax.xml.bind.annotation.XmlElement;
import com.nhb.iot.platform.base.page.PageRequest;
import io.swagger.annotations.ApiModelProperty;

public class ReceiptMeterRequest extends PageRequest {

  @XmlElement(name = "id")
  @ApiModelProperty(value = "id", required = true, example = "1")
  private String id;

  @XmlElement(name = "collectorId")
  @ApiModelProperty(value = "collectorId", required = true, example = "1")
  private String collectorId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCollectorId() {
    return collectorId;
  }

  public void setCollectorId(String collectorId) {
    this.collectorId = collectorId;
  }

}
