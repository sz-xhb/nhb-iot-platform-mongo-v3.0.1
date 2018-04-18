package com.nhb.iot.platform.entity.system;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
 * @ClassName: LogEntity
 * @Description: 系统操作日志
 * @author XS guo
 * @date 2017年9月26日 下午5:32:31
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Document(collection = "log_entity")
public class LogEntity {

  @Id
  private String id;

  @Field("user_id")
  private String userId;

  @Field("module")
  private String module;

  @Field("method")
  private String method;

  @Field("response_time")
  private String responseTime;

  @Field("ip")
  private String ip;

  @Field("date")
  private Date date;

  @Field("commit")
  private String commit;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(String responseTime) {
    this.responseTime = responseTime;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getCommit() {
    return commit;
  }

  public void setCommit(String commit) {
    this.commit = commit;
  }

}
