package com.nhb.iot.platform.request.system;

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
public class SysUserCollectorRequest {

  @XmlElement(name = "userId")
  @ApiModelProperty(value = "iuserIdd", required = true,
      example = "6d70b512-3dc4-40f1-b4bb-e35e78e4921a")
  private String userId;

  @XmlElement(name = "collectorIds")
  @ApiModelProperty(value = "collectorIds", required = true, example = "1,3,5")
  private List<String> collectorIds;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<String> getCollectorIds() {
    return collectorIds;
  }

  public void setCollectorIds(List<String> collectorIds) {
    this.collectorIds = collectorIds;
  }

}
