package com.nhb.iot.platform.api.device;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.api.inface.SystemLog;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptCollectorService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptMeterService;
import com.nhb.iot.platform.entity.device.ReceiptCollector;
import com.nhb.iot.platform.entity.device.ReceiptDevice;
import com.nhb.iot.platform.entity.device.ReceiptMeter;
import com.nhb.iot.platform.request.device.ReceiptDeviceRequest;
import com.nhb.iot.platform.util.JsonMapper;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/device")
public class DeviceController {

	@Autowired
	private ReceiptDeviceService deviceService;

	@Autowired
	private ReceiptCollectorService receiptCollectorService;

	@Autowired
	private ReceiptMeterService receiptMeterService;

	/**
	 * 新建支路信息
	 * 
	 * @param device
	 * @return
	 */
	@ApiOperation(value = "保存支路", notes = "保存支路")
	@RequestMapping(value = "save", method = { RequestMethod.POST })
	@SystemLog(module = "DEVICE", methods = "DEVICE_ADD_DEVICE")
	public RestResultDto<Object> save(@RequestBody ReceiptDeviceRequest request) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String collectorNo = request.getCollectorNo();
			String meterNo = request.getMeterNo();
			String circuitNo = request.getCircuitNo();
			
			if(StringUtil.isNullOrEmpty(collectorNo) 
					|| StringUtil.isNullOrEmpty(meterNo)
					|| StringUtil.isNullOrEmpty(circuitNo)){
				throw new Exception("采集器编号、电表编号、回路编号不能为空！");
			}
			
			ReceiptDevice receiptDevice = new ReceiptDevice();
			receiptDevice.setId(collectorNo+meterNo+circuitNo);
			transferReceiptDevice(request, receiptDevice);
			deviceService.saveOrUpdate(receiptDevice);
			data = receiptDevice;
			msg = "保存支路成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "保存支路失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * 修改支路信息
	 * 
	 * @param device
	 * @return
	 */
	@ApiOperation(value = "修改支路信息 ", notes = "修改支路信息")
	@RequestMapping(value = "update", method = { RequestMethod.POST })
	@SystemLog(module = "DEVICE", methods = "DEVICE_UPDATE_DEVICE")
	public RestResultDto<Object> update(@RequestBody ReceiptDeviceRequest request) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			if(StringUtil.isNullOrEmpty(request.getId())){
				throw new Exception("设备Id不能为空！");
			}
			ReceiptDevice receiptDevice = deviceService.findById(request.getId());
			transferReceiptDevice(request, receiptDevice);
			deviceService.saveOrUpdate(receiptDevice);
			data = receiptDevice;
			msg = "支路修改成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "支路修改失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * 查询支路信息
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据电表编号查询下属支路", notes = "根据电表编号查询下属支路")
	@RequestMapping(value = "findListByMeterId", method = { RequestMethod.POST })
	public RestResultDto<Object> findListByMeterId(@RequestBody ReceiptDeviceRequest request) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			Map<String, Object> returnValue = Maps.newHashMap();
			List<ReceiptDevice> devices = Lists.newArrayList();
			long total = 0;
			int totalPage = 0;
			Sort sort = new Sort(Direction.DESC, "collectorNo");
			Pageable pageable = PageRequest.of(request.getPage() - 1, request.getRows(), sort);
			Page<ReceiptDevice> pageList = deviceService.findByMeterId(request.getMeterId(), pageable);
			if (pageList != null) {
				devices = pageList.getContent();
				total = pageList.getTotalElements();
				totalPage = pageList.getTotalPages();
			}
			returnValue.put("total", total);
			returnValue.put("totalPage", totalPage);
			returnValue.put("currPage", request.getPage());
			returnValue.put("rows", devices);
			data = returnValue;
			msg = "支路查询成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "支路查询失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "根据区域编号查询下属支路", notes = "根据区域编号查询下属支路")
	@RequestMapping(value = "findListDeviceByAreaId", method = { RequestMethod.POST })
	public RestResultDto<Object> findListDeviceByAreaId(@RequestBody Map<String, String> areaId) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			List<ReceiptDevice> devices;
			List<String> ids = Lists.newArrayList();
			List<ReceiptMeter> meterList;
			List<Map<String, Object>> deviceMapList = Lists.newArrayList();
			// 根据区域ID查找下属采集器
			List<ReceiptCollector> receiptCollectorList = receiptCollectorService.findByAreaId(areaId);
			if (!CollectionUtils.isEmpty(receiptCollectorList)) {
				for (ReceiptCollector receiptCollector : receiptCollectorList) {
					ids.add(receiptCollector.getId());
				}
				// 根据采集器ID查找下属电表
				meterList = receiptMeterService.findByCollectorIdIn(ids);
				ids.clear();
				if (!CollectionUtils.isEmpty(meterList)) {
					for (ReceiptMeter meter : meterList) {
						ids.add(meter.getId());
					}
					// 根据电表ID查找下属支路
					Map<String, Object> mapValue = null;
					devices = deviceService.findByMeterIdIn(ids);
					if (!CollectionUtils.isEmpty(devices)) {
						for (ReceiptDevice device : devices) {
							mapValue = JsonMapper.fromJsonString(JsonMapper.toJsonString(device), Map.class);
							if (StringUtil.isNullOrEmpty(String.valueOf(device.getActiveTime()))) {
								mapValue.put("status", "OFFLINE");
							} else {
								mapValue.put("status",
										System.currentTimeMillis() - device.getActiveTime().getTime() > 5 * 60 * 1000
												? "OFFLINE" : "ONLINE");
							}
							deviceMapList.add(mapValue);
						}
					}
				}
			}
			data = deviceMapList;
			msg = "支路查询成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "支路查询失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * 删除支路信息
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "删除支路", notes = "删除支路")
	@RequestMapping(value = "delete", method = { RequestMethod.POST })
	@SystemLog(module = "DEVICE", methods = "DEVICE_DELETE_DEVICE")
	public RestResultDto<Object> delete(@RequestBody ReceiptDeviceRequest request) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String deviceId = request.getId();
			deviceService.delete(deviceId);
			data = true;
			msg = "支路删除成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = false;
			msg = "支路删除失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}
	
	private void transferReceiptDevice(ReceiptDeviceRequest request, ReceiptDevice receiptDevice) {
///		receiptDevice.setId(request.getCollectorNo() + request.getMeterNo() + request.getCircuitNo());
		receiptDevice.setActiveTime(request.getActiveTime());
		receiptDevice.setCircuitNo(request.getCircuitNo());
		receiptDevice.setCollectorNo(request.getCollectorNo());
		receiptDevice.setMeterId(request.getMeterId());
		receiptDevice.setMeterNo(request.getMeterNo());
		receiptDevice.setName(request.getName());
		receiptDevice.setCtrl(request.isCtrl());
		receiptDevice.setModel(request.getModel());
		receiptDevice.setDeviceType(request.getDeviceType());
		receiptDevice.setCurrentRatio(request.getCurrentRatio());
		receiptDevice.setVoltageRatio(request.getVoltageRatio());
		receiptDevice.setGpsLat(request.getGpsLat());
		receiptDevice.setGpsLng(request.getGpsLng());
		receiptDevice.setLatitude(request.getLatitude());
		receiptDevice.setLongitude(request.getLongitude());
		receiptDevice.setLocation(request.getLocation());
	}

}
