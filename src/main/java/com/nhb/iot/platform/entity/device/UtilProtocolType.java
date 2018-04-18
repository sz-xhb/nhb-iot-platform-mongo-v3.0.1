package com.nhb.iot.platform.entity.device;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "util_protocol_type")
public class UtilProtocolType {

  @Id
  private Integer id;

  @Field("meter_type_code")
  private String meterTypeCode;

  @Field("meter_type_name")
  private String meterTypeName;

  @Field("protocol_type_code")
  private String procotolTypeCode;

  @Field("protocol_type_name")
  private String procotolTypeName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMeterTypeCode() {
    return meterTypeCode;
  }

  public void setMeterTypeCode(String meterTypeCode) {
    this.meterTypeCode = meterTypeCode;
  }

  public String getMeterTypeName() {
    return meterTypeName;
  }

  public void setMeterTypeName(String meterTypeName) {
    this.meterTypeName = meterTypeName;
  }

  public String getProcotolTypeCode() {
    return procotolTypeCode;
  }

  public void setProcotolTypeCode(String procotolTypeCode) {
    this.procotolTypeCode = procotolTypeCode;
  }

  public String getProcotolTypeName() {
    return procotolTypeName;
  }

  public void setProcotolTypeName(String procotolTypeName) {
    this.procotolTypeName = procotolTypeName;
  }

}
