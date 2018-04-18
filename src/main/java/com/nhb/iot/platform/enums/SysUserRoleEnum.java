package com.nhb.iot.platform.enums;

import com.nhb.utils.nhb_utils.common.StringUtil;

public enum SysUserRoleEnum {

	SUPERADMIN("SUPERADMIN", "超级管理员"), ADMIN("ADMIN", "管理员"), USER("USER", "普通用户");

	private SysUserRoleEnum(String key, String value) {
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
			for (SysUserRoleEnum e : SysUserRoleEnum.values()) {
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
			for (SysUserRoleEnum e : SysUserRoleEnum.values()) {
				if (e.getValue().equals(value)) {
					return e.getKey();
				}
			}
		}
		return null;
	}

}
