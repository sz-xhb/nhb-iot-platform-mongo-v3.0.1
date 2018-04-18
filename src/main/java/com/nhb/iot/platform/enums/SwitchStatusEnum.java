package com.nhb.iot.platform.enums;

import com.nhb.utils.nhb_utils.common.StringUtil;

/**
 * 
 * @ClassName: SwitchStatus
 * @Description: 开关
 * @author XS guo
 * @date 2017年7月20日 下午3:09:17
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum SwitchStatusEnum {

  ON("ON", "开机"), OFF("OFF", "关闭"), NONE("NONE", "未知"), BROKEN("BROKEN", "故障");

  private SwitchStatusEnum(String key, String value) {
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
   * @Title: getValueByKey
   * @Description: (根据key获取value)
   * @return String
   * @author XS guo
   * @date 2017-9-17 上午10:32:59
   */
  public static String getValueByKey(String key) {
    if (!StringUtil.isNullOrEmpty(key)) {
      for (SwitchStatusEnum e : SwitchStatusEnum.values()) {
        if (e.getKey().equals(key)) {
          return e.getValue();
        }
      }
    }
    return null;
  }

  /**
   * @Title: getKeyByValue
   * @Description: (根据value获取key)
   * @return String author XS guo
   * @date 2017-9-17 上午10:32:59
   */
  public static String getKeyByValue(String value) {
    if (!StringUtil.isNullOrEmpty(value)) {
      for (SwitchStatusEnum e : SwitchStatusEnum.values()) {
        if (e.getValue().equals(value)) {
          return e.getKey();
        }
      }
    }
    return null;
  }
}
