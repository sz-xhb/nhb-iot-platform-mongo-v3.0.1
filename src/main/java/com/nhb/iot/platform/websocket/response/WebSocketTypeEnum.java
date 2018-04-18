package com.nhb.iot.platform.websocket.response;

/**
 * Created by Administrator on 2017/11/22.
 */
public enum WebSocketTypeEnum {

  ALARM("ALARM", "告警"), 
  REMOTE_CONTROL("REMOTE_CONTROL", "远程控制");

  WebSocketTypeEnum(String key, String value) {
    this.key = key;
    this.value = value;
  }

  private String key;

  private String value;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
