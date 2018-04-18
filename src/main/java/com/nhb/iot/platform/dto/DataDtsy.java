package com.nhb.iot.platform.dto;

/**
 * 
 * @ClassName: DataDtsy
 * @Description: 三相预付费 导轨表 实体
 * @author XS guo
 * @date 2017年7月12日 上午9:03:32
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DataDtsy {
	// 一级报警电量
	private Integer alarmPowerLevel1;
	// 二级报警电量
	private Integer alarmPowerLevel2;
	// 过负荷门限
	private double loadThreshold;
	// 售电次数
	private Integer sellNum;
	// 累计电量
	private double cumulativePower;
	// 剩余电量
	private double surplusPower;
	// 总累计电量
	private double totalCumulativePower;
	// 透支电量
	private Integer overdrawPower;
	// 囤积电量
	private Integer cornerPower;
	// 最近一次购电量
	private double lastPurchasePower;
	// 过零电量
	private double zeroPower;
	// 非法卡使用次数
	private Integer illegalTimes;
	// 过负荷次数
	private Integer loadThresholdTimes;
	// 恶性负载门限
	private Integer malignantLoadThreshold;
	// 恶性负载次数
	private Integer malignantLoadTimes;
	// 电表状态字2
	private String statusWord2;

	private Double kwh;

	public Integer getAlarmPowerLevel1() {
		return alarmPowerLevel1;
	}

	public void setAlarmPowerLevel1(Integer alarmPowerLevel1) {
		this.alarmPowerLevel1 = alarmPowerLevel1;
	}

	public Integer getAlarmPowerLevel2() {
		return alarmPowerLevel2;
	}

	public void setAlarmPowerLevel2(Integer alarmPowerLevel2) {
		this.alarmPowerLevel2 = alarmPowerLevel2;
	}

	public double getLoadThreshold() {
		return loadThreshold;
	}

	public void setLoadThreshold(double loadThreshold) {
		this.loadThreshold = loadThreshold;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public double getCumulativePower() {
		return cumulativePower;
	}

	public void setCumulativePower(double cumulativePower) {
		this.cumulativePower = cumulativePower;
	}

	public double getSurplusPower() {
		return surplusPower;
	}

	public void setSurplusPower(double surplusPower) {
		this.surplusPower = surplusPower;
	}

	public double getTotalCumulativePower() {
		return totalCumulativePower;
	}

	public void setTotalCumulativePower(double totalCumulativePower) {
		this.totalCumulativePower = totalCumulativePower;
	}

	public Integer getOverdrawPower() {
		return overdrawPower;
	}

	public void setOverdrawPower(Integer overdrawPower) {
		this.overdrawPower = overdrawPower;
	}

	public Integer getCornerPower() {
		return cornerPower;
	}

	public void setCornerPower(Integer cornerPower) {
		this.cornerPower = cornerPower;
	}

	public double getLastPurchasePower() {
		return lastPurchasePower;
	}

	public void setLastPurchasePower(double lastPurchasePower) {
		this.lastPurchasePower = lastPurchasePower;
	}

	public double getZeroPower() {
		return zeroPower;
	}

	public void setZeroPower(double zeroPower) {
		this.zeroPower = zeroPower;
	}

	public Integer getIllegalTimes() {
		return illegalTimes;
	}

	public void setIllegalTimes(Integer illegalTimes) {
		this.illegalTimes = illegalTimes;
	}

	public Integer getLoadThresholdTimes() {
		return loadThresholdTimes;
	}

	public void setLoadThresholdTimes(Integer loadThresholdTimes) {
		this.loadThresholdTimes = loadThresholdTimes;
	}

	public Integer getMalignantLoadThreshold() {
		return malignantLoadThreshold;
	}

	public void setMalignantLoadThreshold(Integer malignantLoadThreshold) {
		this.malignantLoadThreshold = malignantLoadThreshold;
	}

	public Integer getMalignantLoadTimes() {
		return malignantLoadTimes;
	}

	public void setMalignantLoadTimes(Integer malignantLoadTimes) {
		this.malignantLoadTimes = malignantLoadTimes;
	}

	public String getStatusWord2() {
		return statusWord2;
	}

	public void setStatusWord2(String statusWord2) {
		this.statusWord2 = statusWord2;
	}

	public Double getKwh() {
		return kwh;
	}

	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}

}
