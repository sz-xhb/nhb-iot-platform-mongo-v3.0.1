package com.nhb.iot.platform.enums;

import com.nhb.utils.nhb_utils.common.StringUtil;

public enum SystemLogMethodsTypeEnum {

  SYSTEM_LOGIN("SYSTEM_LOGIN", "登录系统"), SYSTEM_RESET_PASSWORD("SYSTEM_RESET_PASSWORD",
      "重置密码"), SYSTEM_ADD_USER("SYSTEM_ADD_USER", "新增用户"), SYSTEM_MODIFY_PASSWORD(
          "SYSTEM_MODIFY_PASSWORD",
          "修改密码"), SYSTEM_MODIFY_USER("SYSTEM_MODIFY_USER", "修改用户信息"), SYSTEM_DELETE_USER(
              "SYSTEM_DELETE_USER", "删除用户"), SYSTEM_LOGOUT("SYSTEM_LOGOUT", "退出系统"),

  SYSTEM_ADD_AREA("SYSTEM_ADD_AREA", "新增区域"), SYSTEM_MODIFY_AREA("SYSTEM_MODIFY_AREA",
      "修改区域信息"), SYSTEM_DELETE_AREA("SYSTEM_DELETE_AREA",
          "删除区域"), SYSTEM_BIND_AREADEVICE("SYSTEM_BIND_AREADEVICE", "绑定区域设备"),

  SYSTEM_BIND_USERAREA("SYSTEM_BIND_USERAREA",
      "绑定用户区域"), SYSTEM_BIND_USERCOLLECTOR("SYSTEM_BIND_USERCOLLECTOR", "绑定用户采集器"),

  SYSTEM_UPDATE_UNITRATE("SYSTEM_UPDATE_UNITRATE", "费率修改"),

  DEVICE_ADD_COLLECTOR("DEVICE_ADD_COLLECTOR", "新增采集器"), DEVICE_UPDATE_COLLECTOR(
      "DEVICE_UPDATE_COLLECTOR", "修改采集器"), DEVICE_DELETE_COLLECTOR("DEVICE_DELETE_COLLECTOR",
          "删除采集器"), DEVICE_BIND_MANAGERCOLLECTOR("DEVICE_BIND_MANAGERCOLLECTOR", "绑定采集器管理员"),

  DEVICE_ADD_METER("DEVICE_ADD_METER", "新增电表"), DEVICE_UPDATE_METER("DEVICE_UPDATE_METER",
      "修改电表"), DEVICE_DELETE_METER("DEVICE_DELETE_METER", "删除电表"),

  DEVICE_ADD_DEVICE("DEVICE_ADD_DEVICE", "新增设备"), DEVICE_UPDATE_DEVICE("DEVICE_UPDATE_DEVICE",
      "修改设备"), DEVICE_DELETE_DEVICE("DEVICE_DELETE_DEVICE",
          "删除设备"), DEVICE_REMOTE_CONTROL("DEVICE_REMOTE_CONTROL", "远程控制"),

  ALARM_HANDLE("ALARM_HANDLE", "告警处理"), ALARM_SETRULE("ALARM_SETRULE", "设置告警规则");

  private SystemLogMethodsTypeEnum(String key, String value) {
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

  /**
   * @return String
   * @Title: getValueByKey
   * @Description: (根据key获取value)
   * @author XS guo
   * @date 2017-9-17 上午10:32:59
   */
  public static String getValueByKey(String key) {
    if (!StringUtil.isNullOrEmpty(key)) {
      for (SystemLogMethodsTypeEnum e : SystemLogMethodsTypeEnum.values()) {
        if (e.getKey().equals(key)) {
          return e.getValue();
        }
      }
    }
    return null;
  }

  /**
   * @return String author XS guo
   * @Title: getKeyByValue
   * @Description: (根据value获取key)
   * @date 2017-9-17 上午10:32:59
   */
  public static String getKeyByValue(String value) {
    if (!StringUtil.isNullOrEmpty(value)) {
      for (SystemLogMethodsTypeEnum e : SystemLogMethodsTypeEnum.values()) {
        if (e.getValue().equals(value)) {
          return e.getKey();
        }
      }
    }
    return null;
  }
}
