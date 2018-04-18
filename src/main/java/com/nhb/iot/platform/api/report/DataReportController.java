package com.nhb.iot.platform.api.report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.dataaccess.service.common.CommonService;
import com.nhb.iot.platform.dataaccess.service.data.EnergyCusumeService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptCollectorService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
import com.nhb.iot.platform.dataaccess.service.system.SysAreaService;
import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
import com.nhb.iot.platform.dataaccess.service.system.UnitRateService;
import com.nhb.iot.platform.entity.data.EnergyCusume;
import com.nhb.iot.platform.entity.device.ReceiptCollector;
import com.nhb.iot.platform.entity.device.ReceiptDevice;
import com.nhb.iot.platform.entity.system.SysArea;
import com.nhb.iot.platform.entity.system.SysUser;
import com.nhb.iot.platform.entity.system.UnitRate;
import com.nhb.iot.platform.enums.AreaTypeEnum;
import com.nhb.iot.platform.enums.DeviceModelEnum;
import com.nhb.iot.platform.enums.SysUserRoleEnum;
import com.nhb.iot.platform.request.report.ReportRequest;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/data/report")
public class DataReportController {

	@Autowired
	private SysAreaService sysAreaService;

//	@Autowired
//	private HistoryDataService historyDataService;

	@Autowired
	private UnitRateService unitRateService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ReceiptCollectorService receiptCollectorService;

	@Autowired
	private ReceiptDeviceService receiptDeviceService;

	@Autowired
	private EnergyCusumeService energyCusumeService;

	/**
	 * @return RestResultDto
	 * @Title: totalEnergy
	 * @Description: 能耗信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "能耗信息", notes = "根据区域查询能耗信息")
	@RequestMapping(value = "totalEnergy", method = { RequestMethod.POST })
	public RestResultDto totalEnergy(@RequestBody ReportRequest request) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			List<String> areaIds = request.getAreaIds();
			String startDate = request.getStartTime();
			String endDate = request.getEndTime();
			
			if(CollectionUtils.isEmpty(areaIds) 
					|| StringUtil.isNullOrEmpty(startDate)
					|| StringUtil.isNullOrEmpty(endDate)){
				throw new Exception("区域Id和时间条件不能为空!");
			}
			
			// 查询所有要显示的区域信息
			List<SysArea> sysAreas = sysAreaService.findByIds(areaIds);
			List<String> areaIdsShow = Lists.newArrayList();
			// 用于存储需要查询的DeviceId
			List<String> deviceIds = Lists.newArrayList();
			// 每一个区域所对应的deviceIds
			Map<String, List<String>> mapValue = Maps.newHashMap();
			List<String> deviceIdInArea = null;
			// 保存 id 和 name 对应的map
			Map<String, String> areIdForName = Maps.newHashMap();
			for (SysArea area : sysAreas) {
				deviceIdInArea = getDeviceIdsOfArea(area);
				deviceIds.addAll(deviceIdInArea);
				mapValue.put(area.getId(), deviceIdInArea);
				areIdForName.put(area.getId(), area.getName());
				areaIdsShow.add(area.getId());
			}

			// 所有设备的历史数据
			List<EnergyCusume> cusumeList = energyCusumeService.findByDeviceIdInAndDateBetween(deviceIds,startDate, endDate);
			Map<String, Double> deviceEnergyMap = commonService.cacheDeviceIdEnergy(cusumeList);
			
			// 对deviceId分组汇总,根据能源类型分组：水·电·气·热
			List<String> electricityDeviceIds = Lists.newArrayList();
			List<String> waterDeviceIds = Lists.newArrayList();
			groupByDeviceType(deviceIds,electricityDeviceIds,waterDeviceIds);
			
			Map<String, Object> map = null;
			List<Object> returnValue = Lists.newArrayList();
			Double totalElec = (double) 0;
			Double totalWater = (double) 0;
			List<String> areaDeviceIds = null;
			// 遍历区域
			for (String key : areaIdsShow) {
				map = Maps.newHashMap();
				totalElec = (double) 0;
				totalWater = (double) 0;
				areaDeviceIds = mapValue.get(key);
				if (!CollectionUtils.isEmpty(electricityDeviceIds) && !CollectionUtils.isEmpty(areaDeviceIds)) {
					for (String eleDeviceId : electricityDeviceIds) {
						if (areaDeviceIds.contains(eleDeviceId) && deviceEnergyMap.keySet().contains(eleDeviceId)) {
							totalElec += deviceEnergyMap.get(eleDeviceId);
						}
					}
				}
				if (!CollectionUtils.isEmpty(waterDeviceIds) && !CollectionUtils.isEmpty(areaDeviceIds)) {
					for (String waterDeviceId : waterDeviceIds) {
						if (areaDeviceIds.contains(waterDeviceId) && deviceEnergyMap.keySet().contains(waterDeviceId)) {
							totalWater += deviceEnergyMap.get(waterDeviceId);
						}
					}
				}
				map.put("areaId", key);
				map.put("name", areIdForName.get(key));
				map.put("totalElec", totalElec);
				map.put("totalWater", totalWater);
				returnValue.add(map);
			}
			data = returnValue;
			msg = "查询成功！";

		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "能耗信息失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto
	 * @Title: totalEnergyMoney
	 * @Description: 能耗信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "费用信息", notes = "根据区域查询能耗信息")
	@RequestMapping(value = "totalEnergyMoney", method = { RequestMethod.POST })
	public RestResultDto totalEnergyMoney(@RequestBody ReportRequest request) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String userId = request.getUserId();
			if (StringUtil.isNullOrEmpty(userId)) {
				throw new Exception("用户id不能为空！");
			}
			SysUser sysUser = sysUserService.findById(userId);
			if (null == sysUser) {
				throw new Exception("当前用户不存在！");
			}
			String unitId = null;
			if (sysUser.getRole().equals(SysUserRoleEnum.ADMIN.getKey())) {
				unitId = sysUser.getId();
			} else {
				unitId = sysUser.getParentId();
			}

			UnitRate unitRate = unitRateService.findById(unitId);
			if (null == unitRate) {
				throw new Exception("当前系统未设置费率！");
			}

			RestResultDto restDto = totalEnergy(request);
			List<Object> returnValue = Lists.newArrayList();
			Map<String, Object> mapValue = Maps.newHashMap();
			Map<String, Object> map = null;
			if (restDto.getResult() == 0) {
				List<Object> list = (List<Object>) restDto.getData();
				if (!CollectionUtils.isEmpty(list)) {
					for (Object obj : list) {
						mapValue = Maps.newHashMap();
						map = (Map<String, Object>) obj;
						mapValue.put("areaId", map.get("areaId"));
						mapValue.put("name", map.get("name"));
						mapValue.put("elecMoney", (double) Math.round(
								(Double.parseDouble(String.valueOf(map.get("totalElec"))) * unitRate.getElectricity())
										* 100)
								/ 100);
						mapValue.put("waterMoney", (double) Math.round(
								(Double.parseDouble(String.valueOf(map.get("totalWater"))) * unitRate.getWater()) * 100)
								/ 100);
						returnValue.add(mapValue);
					}
				}
				data = returnValue;
				msg = "费用报表查询成功！";
			} else {
				result = RestResultDto.RESULT_FAIL;
				exception = restDto.getException();
				data = null;
				msg = "费用报表查询失败！";
			}

		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "费用报表查询失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto
	 * @Title: totalEnergyMoney
	 * @Description: 能耗信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "单个区域能耗分析", notes = "查询单个区域下设备的能耗数据")
	@RequestMapping(value = "oneAreaEnergy", method = { RequestMethod.POST })
	public RestResultDto oneAreaEnergy(@RequestBody ReportRequest request) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String areaId = request.getAreaId();
			String startDate = request.getStartTime();
			String endDate = request.getEndTime();
			
			if(StringUtil.isNullOrEmpty(areaId)
					|| StringUtil.isNullOrEmpty(startDate) 
					|| StringUtil.isNullOrEmpty(endDate)){
				throw new Exception("查询参数不能为空！");
			}
			
			// 遍历获取区域下面所有的设备节点
			SysArea sysArea = sysAreaService.findById(areaId);
			
			// 获取区域下面的所有deviceId
			List<String> deviceIds = getDeviceIdsOfArea(sysArea);
			
			// 根据设备Id查询能耗数据
			Map<String, Object> deviceEnergyMap = null;
			if(!CollectionUtils.isEmpty(deviceIds)){
				List<EnergyCusume> cusumesList = energyCusumeService.findByDeviceIdInAndDateBetween(deviceIds, startDate, endDate);
				deviceEnergyMap = commonService.cacheDayEnergy(cusumesList);
				
			}
			data = deviceEnergyMap;
			
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "费用报表查询失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * 获取区域下面的所有deviceId
	 * @param areaId
	 * @param sysArea
	 * @param deviceIds
	 * @param deviceNodeIds
	 */
	private List<String> getDeviceIdsOfArea(SysArea sysArea) {
		List<String> deviceNodeIds = Lists.newArrayList();
		List<String> deviceIds = Lists.newArrayList();
		if(null != sysArea){
			if (sysArea.getAreaType().equals(AreaTypeEnum.DeviceNode.getKey())) {// 设备节点
				deviceNodeIds.add(sysArea.getId());
			} else { // 虚拟节点
				commonService.getDeviceNode(sysArea.getId(), deviceNodeIds);
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
	 * @description 根据deciceId查询设备，并且按照设备类型分组
	 * @param deviceIds
	 * @param electricityDevices
	 * @param waterDevices
	 */
	public void groupByDeviceType(List<String> deviceIds,
			List<String> electricityDeviceIds,List<String> waterDeviceIds){
		
		List<ReceiptDevice> receiptDevices = receiptDeviceService.findByIds(deviceIds);
		
		for(ReceiptDevice receiptDevice : receiptDevices){
			if(DeviceModelEnum.AC.getKey().equals(receiptDevice.getModel())
					|| DeviceModelEnum.DC.getKey().equals(receiptDevice.getModel())){
				electricityDeviceIds.add(receiptDevice.getId());
			}
			if(DeviceModelEnum.WATER.getKey().equals(receiptDevice.getModel())){
				waterDeviceIds.add(receiptDevice.getId());
			}
		}
	}
	
}
