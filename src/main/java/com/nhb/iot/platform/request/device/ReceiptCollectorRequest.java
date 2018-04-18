package com.nhb.iot.platform.request.device;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.nhb.iot.platform.base.page.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class ReceiptCollectorRequest extends PageRequest {

  @XmlElement(name = "id")
  @ApiModelProperty(value = "id", required = true, example = "1")
  private String id;

  @XmlElement(name = "tenantId")
  @ApiModelProperty(value = "tenantId", required = true,
      example = "6d70b512-3dc4-40f1-b4bb-e35e78e4921a")
  private String tenantId;

  @XmlElement(name = "collectorType")
  @ApiModelProperty(value = "collectorType", required = true, example = "DTU")
  private String collectorType;

  @XmlElement(name = "ids")
  @ApiModelProperty(value = "ids", required = true, example = "[1,2,3]")
  private List<String> ids;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCollectorType() {
    return collectorType;
  }

  public void setCollectorType(String collectorType) {
    this.collectorType = collectorType;
  }

  public List<String> getIds() {
    return ids;
  }

  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }
}
