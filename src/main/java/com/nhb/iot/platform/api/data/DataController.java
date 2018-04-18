/**
 * Project Name:nhb-platform File Name:DataController.java Package Name:nhb.system.platform.api.data
 * Date:2017��1日下�:45:18 Copyright (c) 2017, lorisun@live.com All Rights Reserved.
 */

package com.nhb.iot.platform.api.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.dataaccess.service.data.DataSwitchService;
import com.nhb.iot.platform.dataaccess.service.data.HistoryDataService;
import com.nhb.iot.platform.dataaccess.service.data.RealtimeDataService;
import com.nhb.iot.platform.entity.data.DataSwitch;
import com.nhb.iot.platform.entity.data.HistoryData;
import com.nhb.iot.platform.enums.DeviceModelEnum;
import com.nhb.iot.platform.request.data.DataRequest;
import com.nhb.utils.nhb_utils.common.DateUtil;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/data")
public class DataController {

	@Autowired
	private HistoryDataService historyDataService;

	@Autowired
	private DataSwitchService dataSwitchService;

	@Autowired
	private RealtimeDataService realtimeDataService;
	

	@SuppressWarnings({ "rawtypes", "unchecked" }) // 单点查询、多点查询、电表明细
	@ApiOperation(value = "实时数据, notes = 传 deviceId,不分页")
	@RequestMapping(value = "findRealtimeDataByDeviceId", method = { RequestMethod.POST })
	public RestResultDto findRealtimeDataByDeviceId(@RequestBody DataRequest dataRequest) {
		RestResultDto resultDto = new RestResultDto();
		List<?> list = Lists.newArrayList();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			List<String> deviceIds = dataRequest.getDeviceIds();
			String deviceModel = dataRequest.getDeviceModel();

			if (CollectionUtils.isEmpty(deviceIds)) {
				throw new Exception("设备Id不能为空");
			}
			if (StringUtil.isNullOrEmpty(deviceModel)) {
				throw new Exception("设备类型不能为空");
			}

			if (DeviceModelEnum.AC.getKey().equals(deviceModel) || DeviceModelEnum.DC.getKey().equals(deviceModel)
					|| DeviceModelEnum.NHB_DEVICE.getKey().equals(deviceModel)) {
				list = realtimeDataService.findByDeviceIdIn(deviceIds);
			}

			data = list;
			msg = "查询成功";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			msg = "查询失败";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" }) // 单点查询、多点查询、电表明细
	@ApiOperation(value = "历史数据, notes = 传 deviceId,")
	@RequestMapping(value = "findHistoryDataByDeviceId", method = { RequestMethod.POST })
	public RestResultDto findHistoryDataByDeviceId(@RequestBody DataRequest dataRequest) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			if (StringUtil.isNullOrEmpty(dataRequest.getDeviceModel())
					|| StringUtil.isNullOrEmpty(dataRequest.getStartTime())
					|| StringUtil.isNullOrEmpty(dataRequest.getEndTime())
					|| (StringUtil.isNullOrEmpty(dataRequest.getDeviceId()))) {
				throw new Exception("必要参数不能为空");
			}
			String deviceId = dataRequest.getDeviceId();

			Map<String, Object> returnValue = Maps.newHashMap();
			Long total = 0L;
			Integer totalPage = 0;
			Sort sort = new Sort(Direction.ASC, "meterTime");
			Pageable pageable = PageRequest.of(dataRequest.getPage() - 1, dataRequest.getRows(), sort);

			Date startDate = DateUtil.parse(dataRequest.getStartTime(), DateUtil.DATETIME_FORMAT);
			Date endDate = DateUtil.parse(dataRequest.getEndTime(), DateUtil.DATETIME_FORMAT);
			Page<HistoryData> pageList = historyDataService.findByDeviceIdAndMeterTimeBetween(deviceId, startDate,
					endDate, pageable);

			total = pageList.getTotalElements();
			totalPage = pageList.getTotalPages();
			returnValue.put("total", total);
			returnValue.put("totalPage", totalPage);
			returnValue.put("currPage", dataRequest.getPage());
			returnValue.put("rows", pageList.getContent());
			data = returnValue;
			msg = "查询成功";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			msg = "查询失败";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" }) // 单点查询、多点查询、电表明细
	@ApiOperation(value = "查询开关设备的历史读取状态, notes = 传 deviceId")
	@RequestMapping(value = "findSwitchStatus", method = { RequestMethod.POST })
	public RestResultDto findSwitchStatus(@RequestBody DataRequest dataRequest) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			if (StringUtil.isNullOrEmpty(dataRequest.getDeviceModel())
					|| StringUtil.isNullOrEmpty(dataRequest.getStartTime())
					|| StringUtil.isNullOrEmpty(dataRequest.getEndTime())
					|| (StringUtil.isNullOrEmpty(dataRequest.getDeviceId()))) {
				throw new Exception("必要参数不能为空");
			}
			String deviceId = dataRequest.getDeviceId();

			Map<String, Object> returnValue = Maps.newHashMap();
			Long total = 0L;
			Integer totalPage = 0;
			Sort sort = new Sort(Direction.DESC, "readTime");
			Pageable pageable = PageRequest.of(dataRequest.getPage() - 1, dataRequest.getRows(), sort);

			Date startDate = DateUtil.parse(dataRequest.getStartTime(), DateUtil.DATETIME_FORMAT);
			Date endDate = DateUtil.parse(dataRequest.getEndTime(), DateUtil.DATETIME_FORMAT);

			Page<DataSwitch> pageList = dataSwitchService.findByDeviceIdAndReadTimeBetween(deviceId, startDate, endDate,
					pageable);

			returnValue.put("total", total);
			returnValue.put("totalPage", totalPage);
			returnValue.put("currPage", dataRequest.getPage());
			returnValue.put("rows", pageList.getContent());
			data = returnValue;
			msg = "查询成功";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			msg = "查询失败";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

}
