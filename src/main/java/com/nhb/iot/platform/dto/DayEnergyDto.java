package com.nhb.iot.platform.dto;

public class DayEnergyDto {

  private String date;

  private String resoutceType;

  private Double cusume;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getResoutceType() {
    return resoutceType;
  }

  public void setResoutceType(String resoutceType) {
    this.resoutceType = resoutceType;
  }

  public Double getCusume() {
    return cusume;
  }

  public void setCusume(Double cusume) {
    this.cusume = cusume;
  }

}
