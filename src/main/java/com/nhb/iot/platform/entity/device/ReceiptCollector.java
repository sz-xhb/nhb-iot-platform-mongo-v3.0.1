package com.nhb.iot.platform.entity.device;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "receipt_collector")
public class ReceiptCollector {

	@Id
	private String id;

	@Field("collector_no")
	private String collectorNo;

	@Field("collector_type")
	private String collectorType;

	@Field("name")
	private String name;

	@Field("collector_frequency")
	private Integer collectionFrequency;

	@Field("ip_address")
	private String ipAddress;

	@Field("manufacturer")
	private String manufacturer;

	@Field("installation")
	private String installation;

	@Field("area_id")
	private String areaId;

	@Field("tenant_id")
	private String tenantId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectorNo() {
		return collectorNo;
	}

	public void setCollectorNo(String collectorNo) {
		this.collectorNo = collectorNo;
	}

	public String getCollectorType() {
		return collectorType;
	}

	public void setCollectorType(String collectorType) {
		this.collectorType = collectorType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCollectionFrequency() {
		return collectionFrequency;
	}

	public void setCollectionFrequency(Integer collectionFrequency) {
		this.collectionFrequency = collectionFrequency;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getInstallation() {
		return installation;
	}

	public void setInstallation(String installation) {
		this.installation = installation;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
