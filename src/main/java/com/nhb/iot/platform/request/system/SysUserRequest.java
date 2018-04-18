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
public class SysUserRequest extends PageRequest {

  @XmlElement(name = "id")
  @ApiModelProperty(value = "id", required = true, example = "0b5c1062-8a34-4tt5-9715-1232e200348a")
  private String id;

  @XmlElement(name = "name")
  @ApiModelProperty(value = "name", required = true, example = "小明")
  private String name;

  @XmlElement(name = "role")
  @ApiModelProperty(value = "role", required = true, example = "ADMIN")
  private String role;

  @XmlElement(name = "parentId")
  @ApiModelProperty(value = "parentId", required = true, example = "0b5c1062-8a34-4ac5-9715-1232e200348a")
  private String parentId;

  @XmlElement(name = "oldPass")
  @ApiModelProperty(value = "oldPass", required = true, example = "123456")
  private String oldPass;

  @XmlElement(name = "newPass")
  @ApiModelProperty(value = "newPass", required = true, example = "123456")
  private String newPass;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getOldPass() {
    return oldPass;
  }

  public void setOldPass(String oldPass) {
    this.oldPass = oldPass;
  }

  public String getNewPass() {
    return newPass;
  }

  public void setNewPass(String newPass) {
    this.newPass = newPass;
  }

}
