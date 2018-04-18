package com.nhb.iot.platform.enums;

public enum AreaTypeEnum {

	VirtualiNode("VirtualiNode", "虚拟节点"), DeviceNode("DeviceNode", "设备节点");

	String key;

	String value;

	private AreaTypeEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

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
