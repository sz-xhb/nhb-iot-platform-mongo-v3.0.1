package com.nhb.iot.platform.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.dataaccess.service.common.CommonService;
import com.nhb.iot.platform.dataaccess.service.data.EnergyCusumeService;
import com.nhb.iot.platform.dataaccess.service.data.HistoryDataService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
import com.nhb.iot.platform.entity.data.EnergyCusume;
import com.nhb.iot.platform.entity.data.HistoryData;
import com.nhb.iot.platform.entity.device.ReceiptDevice;
import com.nhb.iot.platform.enums.DeviceModelEnum;
import com.nhb.iot.platform.request.report.ReportRequest;
import com.nhb.iot.platform.util.DateTimeUtils;
import com.nhb.iot.platform.util.JsonUtil;
import com.nhb.utils.nhb_utils.common.DateUtil;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;

import io.swagger.annotations.ApiOperation;

@Component
@RestController
@RequestMapping("api/v1/data/energy")
public class EnergyCusumeDay {

	@Autowired
	private HistoryDataService historyDataService;

	@Autowired
	private EnergyCusumeService energyCusumeService;

	// @Autowired
	// private UnitRateService unitRateService;

	@Autowired
	private ReceiptDeviceService receiptDeviceService;

	@Autowired
	private CommonService commonService;

	/**
	 * @return void
	 * @throws Exception
	 * @Title: calcEnergyCusumeDay
	 * @Description: 计算日能耗数据
	 */
	@Scheduled(cron = "0 10 0 * * ?")
	public void calcEnergyCusumeDayLast() throws Exception {
		// 获取前一天时间（yyyy-mm-dd 00:00:00 - yyyy-mm-dd 23:59:59）
		Map<String, String> dateTimeRange = DateTimeUtils.getDateRange();
		// 昨天的日期
		String beforeDate = DateTimeUtils.getBeforeDate();
		// 开始结束时间
		Date startTime = DateUtil.parse(dateTimeRange.get("beginTime"));
		Date endTime = DateUtil.parse(dateTimeRange.get("endTime"));
		List<ReceiptDevice> devices = receiptDeviceService.findAll();
		List<String> deviceIds = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(devices)) {
			for (ReceiptDevice device : devices) {
				deviceIds.add(device.getId());
			}
		}
		energyCusumeService.deleteByDateBetweenAndDeviceIdIn(DateUtil.format(startTime), DateUtil.format(endTime),
				deviceIds);
		calcEnergyCusumeDay(startTime, endTime, beforeDate, deviceIds, devices);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "手动计算能耗数据", notes = "计算的给定日期当天的能耗,参数是deviceIds,day(2018-04-02)")
	@RequestMapping(value = "handlerCalcEnergy", method = { RequestMethod.POST })
	public RestResultDto handlerCalcEnergy(@RequestBody ReportRequest request) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String day = request.getDay();
			List<String> deviceIds = request.getDeviceIds();

			if (StringUtil.isNullOrEmpty(day) || CollectionUtils.isEmpty(deviceIds)) {
				throw new Exception("必要参数不能为空！");
			}

			Date startTime = DateUtil.parse(day + " 00:00:00");
			Date endTime = DateUtil.parse(day + " 23:59:59");

			List<ReceiptDevice> devices = receiptDeviceService.findByIds(deviceIds);

			// 计算能耗之前，先删除原来的能耗!
			energyCusumeService.deleteByDateAndDeviceIdIn(day, deviceIds);
			calcEnergyCusumeDay(startTime, endTime, day, deviceIds, devices);
			msg = "能耗数据计算完成！";
			data = "true";

		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			msg = "能耗数据计算失败!";
			data = "false";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	public void calcEnergyCusumeDay(Date startTime, Date endTime, String beforeDate, List<String> deviceIds,
			List<ReceiptDevice> deviceList) throws Exception {
		// 查询昨天所有历史数据
		List<HistoryData> dataHistoryList = historyDataService
				.findByMeterTimeBetweenAndDeviceIdInOrderByMeterTimeDesc(startTime, endTime, deviceIds);

		// 对历史数据进行分组
		Map<String, List<HistoryData>> dataMap = commonService.cacheDataHistory(dataHistoryList);

		// 遍历key,获取所有的deviceId
		List<String> deviceIdsForHisData = Lists.newArrayList();
		if (!MapUtils.isEmpty(dataMap)) {
			Set<String> keySet = dataMap.keySet();
			for (String key : keySet) {
				deviceIdsForHisData.add(key);
			}
		}

		// 获取所有区域信息 用于获取资源类型
		// List<SysArea> sysAreas = sysAreaService.findAll();
		Map<String, ReceiptDevice> mapVlaue = Maps.newHashMap();
		if (!CollectionUtils.isEmpty(deviceList)) {
			for (ReceiptDevice receiptDevice : deviceList) {
				mapVlaue.put(receiptDevice.getId(), receiptDevice);
			}
		}

		// List<UnitRate> rates =
		// unitRateService.findByStartDateLessThanEqualAndEndDateGreaterThan(beforeDate,
		// beforeDate);
		// Map<String, UnitRate> rateMap = Maps.newHashMap();
		// for (UnitRate rate : rates) {
		// rateMap.put(rate.getAreaId(), rate);
		// }

		// 数据保存
		List<EnergyCusume> cusumes = Lists.newArrayList();
		EnergyCusume cusume = null;
		Double cusumeDay = (double) 0;
		List<HistoryData> list = null;
		Double rate = (double) 0;

		for (String deviceId : deviceIdsForHisData) {
			cusume = new EnergyCusume();
			if (mapVlaue.containsKey(deviceId)) {
				list = dataMap.get(deviceId);
				if (DeviceModelEnum.AC.getKey().equals(mapVlaue.get(deviceId).getModel())
						|| DeviceModelEnum.DC.getKey().equals(mapVlaue.get(deviceId).getModel())) {// 电
					cusumeDay = Double.parseDouble(
							JsonUtil.convertValue(list.get(0).getData(), HashMap.class).get("kwh").toString())
							- Double.parseDouble(
									JsonUtil.convertValue(list.get(list.size() - 1).getData(), HashMap.class).get("kwh")
											.toString());
				} else if (mapVlaue.get(deviceId).getDeviceType().equals(DeviceModelEnum.WATER.getKey())) {// 水
					cusumeDay = Double
							.parseDouble(JsonUtil.convertValue(list.get(0).getData(), HashMap.class).get("consumption")
									.toString())
							- Double.parseDouble(
									JsonUtil.convertValue(list.get(list.size() - 1).getData(), HashMap.class)
											.get("consumption").toString());
				}
				cusume.setCusume((double) Math.round(cusumeDay * 100) / 100);
				cusume.setDeviceId(deviceId);
				cusume.setDate(beforeDate);
				cusume.setMoneySpent(cusume.getCusume() * rate);
				cusumes.add(cusume);
			}
		}

		if (!CollectionUtils.isEmpty(cusumes)) {
			energyCusumeService.saveList(cusumes);
		}

	}

}
