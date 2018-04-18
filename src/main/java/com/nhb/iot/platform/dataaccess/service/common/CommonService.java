package com.nhb.iot.platform.dataaccess.service.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptCollectorService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptMeterService;
import com.nhb.iot.platform.dataaccess.service.system.SysAreaService;
import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
import com.nhb.iot.platform.entity.data.EnergyCusume;
import com.nhb.iot.platform.entity.data.HistoryData;
import com.nhb.iot.platform.entity.device.ReceiptCollector;
import com.nhb.iot.platform.entity.device.ReceiptDevice;
import com.nhb.iot.platform.entity.device.ReceiptMeter;
import com.nhb.iot.platform.entity.system.SysArea;
import com.nhb.iot.platform.entity.system.SysUser;
import com.nhb.iot.platform.enums.AreaTypeEnum;
import com.nhb.iot.platform.enums.SysUserRoleEnum;

/**
 * Created by Administrator on 2017/11/22.
 */
@Service
public class CommonService {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysAreaService sysAreaService;

	@Autowired
	private ReceiptCollectorService receiptCollectorService;

	@Autowired
	private ReceiptMeterService receiptMeterService;

	@Autowired
	private ReceiptDeviceService receiptDeviceService;

	/**
	 * 根据租户id查询下属包括自己在内的所有用户id
	 * 
	 * @param tenantId
	 * @return
	 */
	public List<String> findSubIdsByTenantId(String tenantId) {
		List<String> tenantIds = Lists.newArrayList();
		tenantIds.add(tenantId);
		SysUser sysUser = sysUserService.findById(tenantId);
		if(null == sysUser){
			return tenantIds;
		}
		if (sysUser.getRole().equals(SysUserRoleEnum.SUPERADMIN.getKey())) { // 超级管理员
			// 下属管理员账号
			List<SysUser> childUsers = sysUserService.findByParentId(tenantId);
			List<String> childTenantIds = Lists.newArrayList();
			if (!CollectionUtils.isEmpty(childUsers)) {
				for (SysUser user : childUsers) {
					childTenantIds.add(user.getId());
					tenantIds.add(user.getId());
				}
			}
			// 下属普通用户账号
			List<SysUser> grandChildUsers = sysUserService.findByParentIdIn(childTenantIds);
			if (!CollectionUtils.isEmpty(grandChildUsers)) {
				for (SysUser user : grandChildUsers) {
					tenantIds.add(user.getId());
				}
			}
		} else if (sysUser.getRole().equals(SysUserRoleEnum.ADMIN.getKey())) {// 管理员
			// 下属所有用户
			List<SysUser> childUsers = sysUserService.findByParentId(tenantId);
			if (!CollectionUtils.isEmpty(childUsers)) {
				for (SysUser user : childUsers) {
					tenantIds.add(user.getId());
				}
			}
		}

		return tenantIds;
	}

	/**
	 * 根据租户的ids 查询下属所有的 设备 id
	 * 
	 * @param tenantIds
	 * @return
	 */
	public List<String> findDeviceIdsIdsByByTenantIds(List<String> tenantIds) {
		List<String> deviceIds = Lists.newArrayList();
		List<ReceiptCollector> collectors = receiptCollectorService.findByTenantIds(tenantIds);
		if (CollectionUtils.isEmpty(collectors)) {
			return deviceIds;
		}
		List<String> collectorIds = Lists.newArrayList();
		for (ReceiptCollector collector : collectors) {
			collectorIds.add(collector.getId());
		}
		List<ReceiptMeter> meters = receiptMeterService.findByCollectorIdIn(collectorIds);
		if (CollectionUtils.isEmpty(meters)) {
			return deviceIds;
		}
		List<String> meterIds = Lists.newArrayList();
		for (ReceiptMeter meter : meters) {
			meterIds.add(meter.getId());
		}
		List<ReceiptDevice> devices = receiptDeviceService.findByMeterIdIn(meterIds);
		if (CollectionUtils.isEmpty(devices)) {
			return deviceIds;
		}
		for (ReceiptDevice device : devices) {
			deviceIds.add(device.getId());
		}
		return deviceIds;
	}

	/**
	 * 根据区域ids 查询下属所有 deviceIds
	 * 
	 * @param areaIds
	 * @return
	 */
	public List<String> findDeviceIdsByAreaIds(List<String> areaIds) {
		List<String> deviceIds = Lists.newArrayList();
		List<ReceiptCollector> collectors = receiptCollectorService.findByAreaIdIn(areaIds);
		if (CollectionUtils.isEmpty(collectors)) {
			return deviceIds;
		}
		List<String> collectorIds = Lists.newArrayList();
		for (ReceiptCollector collector : collectors) {
			collectorIds.add(collector.getId());
		}
		List<ReceiptMeter> meters = receiptMeterService.findByCollectorIdIn(collectorIds);
		if (CollectionUtils.isEmpty(meters)) {
			return deviceIds;
		}
		List<String> meterIds = Lists.newArrayList();
		for (ReceiptMeter meter : meters) {
			meterIds.add(meter.getId());
		}
		List<ReceiptDevice> devices = receiptDeviceService.findByMeterIdIn(meterIds);
		if (CollectionUtils.isEmpty(devices)) {
			return deviceIds;
		}
		for (ReceiptDevice device : devices) {
			deviceIds.add(device.getId());
		}
		return deviceIds;
	}

	/**
	 * 根据区域id 返回deviceIds的映射关系
	 * 
	 * @param areaIds
	 * @return
	 */
	public Map<String, List<String>> findAreaIdToDeviceIds(List<String> areaIds) {

		Map<String, List<String>> mapValue = Maps.newHashMap();

		List<SysArea> sysAreas = sysAreaService.findByIds(areaIds);
		List<String> subAreaIds = null;
		List<String> deviceNodeIds = null;
		for (SysArea sysArea : sysAreas) {
			deviceNodeIds = Lists.newArrayList();
			subAreaIds = Lists.newArrayList();
			if (sysArea.getAreaType().equals(AreaTypeEnum.VirtualiNode.getKey())) {
				subAreaIds = getDeviceNode(sysArea.getId(), subAreaIds);
				deviceNodeIds.addAll(subAreaIds);
			} else if (sysArea.getAreaType().equals(AreaTypeEnum.DeviceNode.getKey())) {
				deviceNodeIds.add(sysArea.getId());
			}

			List<ReceiptCollector> collectors = receiptCollectorService.findByAreaIdIn(areaIds);
			if (CollectionUtils.isEmpty(collectors)) {
				return mapValue;
			}
			List<String> collectorIds = Lists.newArrayList();
			for (ReceiptCollector collector : collectors) {
				collectorIds.add(collector.getId());
			}
			List<ReceiptMeter> meters = receiptMeterService.findByCollectorIdIn(collectorIds);
			if (CollectionUtils.isEmpty(meters)) {
				return mapValue;
			}
			List<String> meterIds = Lists.newArrayList();
			for (ReceiptMeter meter : meters) {
				meterIds.add(meter.getId());
			}
			List<ReceiptDevice> devices = receiptDeviceService.findByMeterIdIn(meterIds);
			if (CollectionUtils.isEmpty(devices)) {
				return mapValue;
			}
			List<String> deviceIds = Lists.newArrayList();
			for (ReceiptDevice device : devices) {
				deviceIds.add(device.getId());
			}
			mapValue.put(sysArea.getId(), deviceIds);
		}

		return mapValue;
	}

	/**
	 * 根据虚拟节点的区域id查询下属所有的设备节点id
	 * 
	 * @param areaId
	 * @param deviceNodeIds
	 * @return
	 */
	public List<String> getDeviceNode(String areaId, List<String> deviceNodeIds) {
		List<SysArea> sysAreas = sysAreaService.findByParentId(areaId);
		if (!CollectionUtils.isEmpty(sysAreas)) {
			for (SysArea sysArea : sysAreas) {
				if (sysArea.getAreaType().equals(AreaTypeEnum.DeviceNode.getKey())) {
					deviceNodeIds.add(sysArea.getId());
				} else {
					getDeviceNode(sysArea.getId(), deviceNodeIds);
				}
			}
		}
		return deviceNodeIds;
	}
	
	/**
	 * 获取区域下面的所有deviceId
	 * @param areaId
	 * @param sysArea
	 * @param deviceIds
	 * @param deviceNodeIds
	 */
	public List<String> getDeviceIdsOfArea(SysArea sysArea) {
		List<String> deviceNodeIds = Lists.newArrayList();
		List<String> deviceIds = Lists.newArrayList();
		if(null != sysArea){
			if (sysArea.getAreaType().equals(AreaTypeEnum.DeviceNode.getKey())) {// 设备节点
				deviceNodeIds.add(sysArea.getId());
			} else { // 虚拟节点
				getDeviceNode(sysArea.getId(), deviceNodeIds);
			}
		}
		
		// 获取区域下面的采集器
		List<ReceiptCollector> receiptCollectors = receiptCollectorService.findByAreaIdIn(deviceNodeIds);
		
		// 查询区域采集器下面的设备
		List<String> collectorNos = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(receiptCollectors)){
			for(ReceiptCollector receiptCollector : receiptCollectors){
				collectorNos.add(receiptCollector.getCollectorNo());
			}
			
			List<ReceiptDevice> receiptDevices = receiptDeviceService.findByCollectorNoIn(collectorNos);
			for(ReceiptDevice receiptDevice : receiptDevices){
				deviceIds.add(receiptDevice.getId());
			}
		}
		return deviceIds;
	}

	/**
	 * 电历史数据按deviceId分组, key-deviceId, value-list
	 * @param historyDataList
	 * @return
	 */
	public Map<String, List<HistoryData>> cacheDataHistory(List<HistoryData> historyDataList) {
		Map<String, List<HistoryData>> dataMap = Maps.newHashMap();
		List<String> deviceIds = Lists.newArrayList();
		List<HistoryData> historyDataValues = null;
		
		if(CollectionUtils.isNotEmpty(historyDataList)){
			// 拿到所有的DeviceId
			for(HistoryData historyData : historyDataList){
				deviceIds.add(historyData.getDeviceId());
			}
			
			for(String deviceId : deviceIds){
				historyDataValues = Lists.newArrayList();
				for(HistoryData historyData : historyDataList){
					if(deviceId.equals(historyData.getDeviceId())){
						historyDataValues.add(historyData);
					}
				}
				dataMap.put(deviceId, historyDataValues);
			}
		}
		return dataMap;
	}

	/**
	 * 将能耗数据按deviceId分组
	 * @param cusumesList
	 * @return
	 */
	public Map<String, List<EnergyCusume>> cacheEnergy(List<EnergyCusume> cusumesList) {
		Map<String, List<EnergyCusume>> dataMap = Maps.newHashMap();
		List<String> deviceIds = Lists.newArrayList();
		List<EnergyCusume> energyDataValues = null;
		
		if(CollectionUtils.isNotEmpty(cusumesList)){
			// 拿到所有的DeviceId
			for(EnergyCusume energyCusume : cusumesList){
				deviceIds.add(energyCusume.getDeviceId());
			}
			
			for(String deviceId : deviceIds){
				energyDataValues = Lists.newArrayList();
				for(EnergyCusume energyCusume : cusumesList){
					if(deviceId.equals(energyCusume.getDeviceId())){
						energyDataValues.add(energyCusume);
					}
				}
				dataMap.put(deviceId, energyDataValues);
			}
		}
		return dataMap;
	}
	
	/**
	 * 计算能耗，按日期分组，日期单位是天（比如2018-03-30）
	 * @param cusumesList
	 * @return
	 */
	public Map<String, Object> cacheDayEnergy(List<EnergyCusume> cusumesList) {
		Map<String, Object> dataMap = Maps.newHashMap();
		List<String> days = Lists.newArrayList();
		double energy = 0.0;
		
		if(CollectionUtils.isNotEmpty(cusumesList)){
			// 拿到所有的DeviceId
			for(EnergyCusume energyCusume : cusumesList){
				days.add(energyCusume.getDate());
			}
			
			for(String day : days){
				energy = 0.0;
				for(EnergyCusume energyCusume : cusumesList){
					if(day.equals(energyCusume.getDate())){
						energy += energyCusume.getCusume();
					}
				}
				dataMap.put(day, energy);
			}
		}
		return dataMap;
	}
	
	/**
	 * 计算能耗，按deviceId分组
	 * @param cusumesList
	 * @return
	 */
	public Map<String, Double> cacheDeviceIdEnergy(List<EnergyCusume> cusumesList) {
		Map<String, Double> dataMap = Maps.newHashMap();
		List<String> deviceIds = Lists.newArrayList();
		double energy = 0.0;
		
		if(CollectionUtils.isNotEmpty(cusumesList)){
			// 拿到所有的DeviceId
			for(EnergyCusume energyCusume : cusumesList){
				deviceIds.add(energyCusume.getDeviceId());
			}
			
			for(String deviceId : deviceIds){
				energy = 0.0;
				for(EnergyCusume energyCusume : cusumesList){
					if(deviceId.equals(energyCusume.getDeviceId())){
						energy += energyCusume.getCusume();
					}
				}
				dataMap.put(deviceId, energy);
			}
		}
		return dataMap;
	}


}
