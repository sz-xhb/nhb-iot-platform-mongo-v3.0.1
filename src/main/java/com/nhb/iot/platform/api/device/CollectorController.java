package com.nhb.iot.platform.api.device;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.api.inface.SystemLog;
import com.nhb.iot.platform.dataaccess.service.common.CommonService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptCollectorService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptMeterService;
import com.nhb.iot.platform.dataaccess.service.system.SysAreaService;
import com.nhb.iot.platform.entity.device.ReceiptCollector;
import com.nhb.iot.platform.entity.device.ReceiptDevice;
import com.nhb.iot.platform.entity.device.ReceiptMeter;
import com.nhb.iot.platform.entity.system.SysArea;
import com.nhb.iot.platform.enums.CollectorTypeEnum;
import com.nhb.iot.platform.request.device.ReceiptCollectorRequest;
import com.nhb.iot.platform.util.JsonMapper;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/collector")
public class CollectorController {

	@Autowired
	private ReceiptCollectorService receiptCollectorService;

	@Autowired
	private ReceiptMeterService receiptMeterService;

	@Autowired
	private ReceiptDeviceService receiptDeviceService;

	@Autowired
	private SysAreaService sysAreaService;

	@Autowired
	private CommonService commonService;

	/**
	 * 新增采集器
	 */
	@ApiOperation(value = "新增采集器", notes = "新增采集器")
	@RequestMapping(value = "save", method = { RequestMethod.POST })
	@SystemLog(module = "DEVICE", methods = "DEVICE_ADD_COLLECTOR")
	public RestResultDto<Object> save(@RequestBody ReceiptCollector receiptCollector) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			if (StringUtil.isNullOrEmpty(receiptCollector.getTenantId())) {
				throw new Exception("用户id不能为空！");
			}
			String collectorNo = receiptCollector.getCollectorNo();
			if (StringUtil.isNullOrEmpty(collectorNo)) {
				throw new Exception("采集器编号不能为空！");
			}
			ReceiptCollector collector = receiptCollectorService.findByCollectorNo(collectorNo);
			if (null != collector) {
				throw new Exception("已存在编号为" + collectorNo + "的采集器！");
			}
			receiptCollector = receiptCollectorService.saveOrUpdate(receiptCollector);
			data = receiptCollector;
			msg = "保存采集器成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "保存采集器失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * 更新采集器信息
	 *
	 * @return RestResultDto
	 */
	@ApiOperation(value = "修改采集器", notes = "修改采集器")
	@RequestMapping(value = "update", method = { RequestMethod.POST })
	@SystemLog(module = "DEVICE", methods = "DEVICE_UPDATE_COLLECTOR")
	public RestResultDto<Object> update(@RequestBody ReceiptCollector receiptCollector) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			ReceiptCollector collector = receiptCollectorService.findById(receiptCollector.getId());
			if(null == collector){
				throw new Exception("采集器不存在!");
			}
			receiptCollector = receiptCollectorService.saveOrUpdate(receiptCollector);
			data = receiptCollector;
			msg = "采集器修改成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "采集器修改失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * 查询采集器列表
	 *
	 * @return RestResultDto
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询设备列表", notes = "用户id")
	@RequestMapping(value = "findListByUserId", method = { RequestMethod.POST })
	public RestResultDto<Object> findListByUserId(@RequestBody ReceiptCollectorRequest request) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			Map<String, Object> returnValue = Maps.newHashMap();
			List<Object> dataList = Lists.newArrayList();
			long total = 0;
			int totalPage = 0;
			String tenantId = request.getTenantId();
			if (StringUtil.isNullOrEmpty(tenantId)) {
				throw new Exception("用户id不能为空！");
			}

			Pageable pageable = null;
			if (request.getPage() != null && request.getRows() != null) {
				pageable = PageRequest.of(request.getPage() - 1, request.getRows());
			}
			// 查询该用户下 所有用户id
			List<String> tenantIds = commonService.findSubIdsByTenantId(tenantId);

			Page<ReceiptCollector> pageList = receiptCollectorService.findByTenantIds(tenantIds, pageable);
			if (null != pageList) {
				List<ReceiptCollector> receiptCollectors = pageList.getContent();
				List<String> areaIds = Lists.newArrayList();
				for (ReceiptCollector collector : receiptCollectors) {
					if (!areaIds.contains(collector.getAreaId())) {
						areaIds.add(collector.getAreaId());
					}
				}

				List<SysArea> sysAreas = sysAreaService.findByIds(areaIds);
				Map<String, Object> mapValue = null;

				for (ReceiptCollector collector : receiptCollectors) {
					mapValue = JsonMapper.fromJsonString(JsonMapper.toJsonString(collector), Map.class);
					for (SysArea sysArea : sysAreas) {
						if (sysArea.getId().equals(collector.getAreaId())) {
							mapValue.put("areaName", sysArea.getName());
						}
					}
					dataList.add(mapValue);
				}
				total = pageList.getTotalElements();
				totalPage = pageList.getTotalPages();
			}

			returnValue.put("currPage", request.getPage());
			returnValue.put("totalPage", totalPage);
			returnValue.put("total", total);
			returnValue.put("rows", dataList);

			data = returnValue;
			msg = "采集器查询成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "采集器查询失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * 删除采集器
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "删除采集器", notes = "删除采集器")
	@RequestMapping(value = "delete", method = { RequestMethod.POST })
	@SystemLog(module = "DEVICE", methods = "DEVICE_DELETE_COLLECTOR")
	public RestResultDto delete(@RequestBody ReceiptCollectorRequest request) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String collectorId = request.getId();
			if (null == collectorId) {
				throw new Exception("删除失败，未选中采集器！");
			}
			// 查找采集器下面的电表信息
			List<ReceiptMeter> meters = receiptMeterService.findByCollectorId(collectorId, null).getContent();
			// 删除采集器下属设备
			if (!CollectionUtils.isEmpty(meters)) {
				List<String> meterIds = Lists.newArrayList();
				for (ReceiptMeter meter : meters) {
					meterIds.add(meter.getId());
					receiptMeterService.delete(meter.getId());
				}
				// 查找电表下面的支路信息
				List<ReceiptDevice> deviceList = receiptDeviceService.findByMeterIdIn(meterIds);
				for (ReceiptDevice receiptDevice : deviceList) {
					receiptDeviceService.delete(receiptDevice.getId());
				}
			}

			// 删除采集器
			receiptCollectorService.delete(request.getId());
			data = true;
			msg = "采集器以及下属设备删除成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = false;
			msg = "采集器删除失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * 获取采集器类型
	 */
	@RequestMapping(value = "getCollectorType", method = { RequestMethod.POST })
	public RestResultDto<Object> getCollectorType() {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			List<Object> returnValue = Lists.newArrayList();
			Map<String, Object> mapValue;
			for (CollectorTypeEnum value : CollectorTypeEnum.values()) {
				mapValue = Maps.newHashMap();
				mapValue.put("key", value.getKey());
				mapValue.put("value", value.getValue());
				returnValue.add(mapValue);
			}
			data = returnValue;
			msg = "获取采集器类型成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = false;
			msg = "获取采集器类型失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

}
