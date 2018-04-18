package com.nhb.iot.platform.request.system;

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
public class SysLogRequest extends PageRequest {

  @XmlElement(name = "userId")
  @ApiModelProperty(value = "userId", required = true,
      example = "6d70b512-3dc4-40f1-b4bb-e35e78e4921a")
  private String userId;

  @XmlElement(name = "opUserId")
  @ApiModelProperty(value = "opUserId", required = true,
      example = "6d70b512-3dc4-40f1-b4bb-e35e78e4921a")
  private String opUserId;

  @XmlElement(name = "module")
  @ApiModelProperty(value = "module", required = true, example = "SYSTEM")
  private String module;

  @XmlElement(name = "methods")
  @ApiModelProperty(value = "methods", required = true, example = "SYSTEM_UPDATE_UNITRATE")
  private String methods;

  @XmlElement(name = "commit")
  @ApiModelProperty(value = "commit", required = true, example = "SUCCESS")
  private String commit;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOpUserId() {
    return opUserId;
  }

  public void setOpUserId(String opUserId) {
    this.opUserId = opUserId;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getMethods() {
    return methods;
  }

  public void setMethods(String methods) {
    this.methods = methods;
  }

  public String getCommit() {
    return commit;
  }

  public void setCommit(String commit) {
    this.commit = commit;
  }

}
