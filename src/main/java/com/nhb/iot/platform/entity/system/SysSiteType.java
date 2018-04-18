package com.nhb.iot.platform.entity.system;

import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sys_site_type")
public class SysSiteType {

  @Id
  private String id;

  @Field("site_code")
  private String siteCode;

  @Field("site_name")
  private String siteName;

  @Field("param")
  private List<Map<String, String>> param;

  @Field("user_id")
  private String userId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSiteCode() {
    return siteCode;
  }

  public void setSiteCode(String siteCode) {
    this.siteCode = siteCode;
  }

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public List<Map<String, String>> getParam() {
    return param;
  }

  public void setParam(List<Map<String, String>> param) {
    this.param = param;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
