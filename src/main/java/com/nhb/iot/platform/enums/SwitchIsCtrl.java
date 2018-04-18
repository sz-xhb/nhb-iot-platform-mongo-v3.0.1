package com.nhb.iot.platform.enums;

public enum SwitchIsCtrl {

  IsCtrl("IsCtrl", true), IsNotCtrl("IsNotCtrl", false);

  String key;

  boolean value;

  private SwitchIsCtrl(String key, boolean value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public boolean isValue() {
    return value;
  }

  public void setValue(boolean value) {
    this.value = value;
  }


}
