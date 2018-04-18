// package com.nhb.iot.platform.api.alarm;
//
// import java.util.Date;
// import java.util.List;
// import java.util.Map;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RestController;
// import com.google.common.collect.Lists;
// import com.google.common.collect.Maps;
// import com.nhb.iot.platform.api.inface.SystemLog;
// import com.nhb.iot.platform.dataaccess.service.alarm.AlarmRuleService;
// import com.nhb.iot.platform.dataaccess.service.alarm.AlarmService;
// import
// com.nhb.iot.platform.dataaccess.service.device.ReceiptCollectorService;
// import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
// import com.nhb.iot.platform.dataaccess.service.device.ReceiptMeterService;
// import
// com.nhb.iot.platform.dataaccess.service.system.SysUserCollectorService;
// import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
// import com.nhb.iot.platform.entity.alarm.Alarm;
// import com.nhb.iot.platform.entity.alarm.AlarmRule;
// import com.nhb.iot.platform.entity.device.ReceiptCollector;
// import com.nhb.iot.platform.entity.device.ReceiptDevice;
// import com.nhb.iot.platform.entity.device.ReceiptMeter;
// import com.nhb.iot.platform.entity.system.SysUser;
// import com.nhb.iot.platform.entity.system.SysUserCollector;
// import com.nhb.iot.platform.enums.AlarmTypeEnum;
// import com.nhb.iot.platform.enums.SysUserRoleEnum;
// import com.nhb.iot.platform.request.alarm.AlarmRequest;
// import com.nhb.iot.platform.util.JsonMapper;
// import com.nhb.utils.nhb_utils.common.DateUtil;
// import com.nhb.utils.nhb_utils.common.RestResultDto;
// import com.nhb.utils.nhb_utils.common.StringUtil;
// import io.swagger.annotations.ApiOperation;
//
/// **
// * Created by Administrator on 2017/11/2.
// */
// @RestController
// @RequestMapping("api/v1/alarm")
// public class AlarmController {
//
// @Autowired
// private AlarmService alarmService;
//
// @Autowired
// private ReceiptCollectorService receiptCollectorService;
//
// @Autowired
// private ReceiptMeterService receiptMeterService;
//
// @Autowired
// private ReceiptDeviceService receiptDeviceService;
//
// @Autowired
// private SysUserService sysUserService;
//
// @Autowired
// private SysUserCollectorService sysUserCollectorService;
//
// @Autowired
// private AlarmRuleService alarmRuleService;
//
// /**
// * @return RestResultDto
// * @Title:findAlarms
// * @Description: 查询告警信息
// */
// @SuppressWarnings({"rawtypes", "unchecked"})
// @ApiOperation(value = "查询告警信息", notes = "查询告警信息")
// @RequestMapping(value = "findAlarmCounts", method = {RequestMethod.POST})
// public RestResultDto findAlarmCounts(@RequestBody AlarmRequest request) {
// RestResultDto resultDto = new RestResultDto();
// Integer result = RestResultDto.RESULT_SUCC;
// String msg = null;
// Object data = null;
// String exception = null;
// try {/*
//
// String userId = request.getUserId();
// if (StringUtil.isNullOrEmpty(userId)) {
// throw new Exception("用户id不能为空！");
// }
// Date startTime = DateUtil.parse(request.getStartTime());
// Date endTime = DateUtil.parse(request.getEndTime());
//
// List<String> deviceIds = getDeviceIdsByUserId(userId);
//
// long unhandleAlarmCounts = alarmService
// .countByDeviceIdInAndDealStatusAndAlarmTimeBetween(deviceIds, 0, startTime,
// endTime);
// long alarmCounts =
// alarmService.countByDeviceIdInAndAlarmTimeBetween(deviceIds, startTime,
// endTime);
// Map<String, Object> returnValue = Maps.newHashMap();
//
// returnValue.put("alarmCounts", alarmCounts);
// returnValue.put("unhandleAlarmCounts", unhandleAlarmCounts);
//
// data = returnValue;
// msg = "告警数据查询成功！";
//
// */} catch (Exception e) {
// result = RestResultDto.RESULT_FAIL;
// exception = e.getMessage();
// data = null;
// msg = "查询告警信息失败！";
// } finally {
// resultDto.setData(data);
// resultDto.setException(exception);
// resultDto.setMsg(msg);
// resultDto.setResult(result);
// }
// return resultDto;
// }
//
// /**
// * 根据userId 获取下属的deviceIds列表
// *
// * @param userId
// * @return
// */
//// public List<String> getDeviceIdsByUserId(String userId) {
//// SysUser sysUser = sysUserService.findById(userId);
//// List<String> collectorIds = Lists.newArrayList();
//// if (sysUser.getRole().equals(SysUserRoleEnum.ADMIN.getKey())) {
//// List<ReceiptCollector> collectors =
//// receiptCollectorService.findAllByManagerId(sysUser.getId());
//// for (ReceiptCollector collector : collectors) {
//// collectorIds.add(collector.getId());
//// }
//// } else {
//// List<SysUserCollector> sysUserCollectors =
// sysUserCollectorService.findByUserId(userId);
//// for (SysUserCollector collector : sysUserCollectors) {
//// collectorIds.add(collector.getCollectorId());
//// }
//// }
////
//// List<ReceiptMeter> meters =
// receiptMeterService.findByCollectorIdIn(collectorIds);
//// List<String> meterIds = Lists.newArrayList();
//// for (ReceiptMeter meter : meters) {
//// meterIds.add(meter.getId());
//// }
////
//// List<ReceiptDevice> devices =
// receiptDeviceService.findByMeterIdIn(meterIds);
//// List<String> deviceIds = Lists.newArrayList();
//// for (ReceiptDevice device : devices) {
//// deviceIds.add(device.getId());
//// }
//// return deviceIds;
//// }
//
// /**
// * @return RestResultDto
// * @Title:findAlarms
// * @Description: 查询告警信息
// */
// @SuppressWarnings({"rawtypes", "unchecked"})
// @ApiOperation(value = "查询告警信息", notes = "查询告警信息")
// @RequestMapping(value = "findAlarms", method = {RequestMethod.POST})
// public RestResultDto findAlarms(@RequestBody AlarmRequest request) {
// RestResultDto resultDto = new RestResultDto();
// Integer result = RestResultDto.RESULT_SUCC;
// String msg = null;
// Object data = null;
// String exception = null;
// try {/*
//
// String userId = request.getUserId();
// if (StringUtil.isNullOrEmpty(userId)) {
// throw new Exception("用户id不能为空！");
// }
// Integer dealStatus = request.getDealStatus();
// if (null == dealStatus) {
// throw new Exception("处理状态不能为空！");
// }
// Date startTime = DateUtil.parse(request.getStartTime());
// Date endTime = DateUtil.parse(request.getEndTime());
// Sort sort = new Sort(Sort.Direction.DESC, "alarmTime");
// Pageable pageable = PageRequest.of(request.getPage() - 1, request.getRows(),
// sort);
//
// List<String> deviceIds = getDeviceIdsByUserId(userId);
// Map<String, Object> returnValue = Maps.newHashMap();
// Long total = 0L;
// List<Object> list = Lists.newArrayList();
// Integer totalPage = 0;
//
// Page<Alarm> pageList =
// alarmService.findByDeviceIdInAndDealStatusAndAlarmTimeBetween(
// deviceIds, dealStatus, startTime, endTime, pageable);
// if (null != pageList) {
// total = pageList.getTotalElements();
// totalPage = pageList.getTotalPages();
//
// Map<String, Object> mapValue = null;
// for (Object obj : pageList.getContent()) {
// mapValue = Maps.newHashMap();
// mapValue = JsonMapper.fromJsonString(JsonMapper.toJsonString(obj),
// Map.class);
// mapValue.put("alarmTime",
// DateUtil.format(new
// Date(Long.valueOf(String.valueOf(mapValue.get("alarmTime"))))));
// mapValue.put("alarmTypeName",
// AlarmTypeEnum.getValueByKey(String.valueOf(mapValue.get("alarmType"))));
// list.add(mapValue);
// }
// }
//
// returnValue.put("total", total);
// returnValue.put("totalPage", totalPage);
// returnValue.put("currPage", request.getPage());
// returnValue.put("rows", list);
//
// data = returnValue;
// msg = "告警数据查询成功！";
//
// */} catch (Exception e) {
// result = RestResultDto.RESULT_FAIL;
// exception = e.getMessage();
// data = null;
// msg = "查询告警信息失败！";
// } finally {
// resultDto.setData(data);
// resultDto.setException(exception);
// resultDto.setMsg(msg);
// resultDto.setResult(result);
// }
// return resultDto;
// }
//
// /**
// * @return RestResultDto
// * @Title: handleAlarms
// * @Description: 处理告警
// */
// @SuppressWarnings({"rawtypes", "unchecked"})
// @ApiOperation(value = "处理告警", notes = "处理告警")
// @RequestMapping(value = "handleAlarms", method = {RequestMethod.POST})
// @SystemLog(module = "ALARM", methods = "ALARM_HANDLE")
// public RestResultDto handleAlarms(@RequestBody AlarmRequest request) {
// RestResultDto resultDto = new RestResultDto();
// Integer result = RestResultDto.RESULT_SUCC;
// String msg = null;
// Object data = null;
// String exception = null;
// try {
// List<String> alarmIds = request.getIds();
// String handleReason = request.getHandleReason();
// Alarm alarm = null;
// for (String alarmId : alarmIds) {
// alarm = alarmService.findById(alarmId);
// alarm.setHandleReason(handleReason);
// alarm.setDealStatus(1);
// alarmService.save(alarm);
// }
//
// data = true;
// msg = "处理成功！";
//
// } catch (Exception e) {
// result = RestResultDto.RESULT_FAIL;
// exception = e.getMessage();
// data = null;
// msg = "处理失败！";
// } finally {
// resultDto.setData(data);
// resultDto.setException(exception);
// resultDto.setMsg(msg);
// resultDto.setResult(result);
// }
// return resultDto;
// }
//
// /**
// * @return RestResultDto
// * @Title: saveAlarmRule
// * @Description: 设置告警规则
// */
// @SuppressWarnings({"rawtypes", "unchecked"})
// @ApiOperation(value = "设置告警规则", notes = "设置告警规则")
// @RequestMapping(value = "saveAlarmRule", method = {RequestMethod.POST})
// @SystemLog(module = "ALARM", methods = "ALARM_SETRULE")
// public RestResultDto saveAlarmRule(@RequestBody AlarmRule request) {
// RestResultDto resultDto = new RestResultDto();
// Integer result = RestResultDto.RESULT_SUCC;
// String msg = null;
// Object data = null;
// String exception = null;
// try {
// request = alarmRuleService.save(request);
// data = request;
// msg = "告警规则设置成功！";
// } catch (Exception e) {
// result = RestResultDto.RESULT_FAIL;
// exception = e.getMessage();
// data = null;
// msg = "告警规则设置失败！";
// } finally {
// resultDto.setData(data);
// resultDto.setException(exception);
// resultDto.setMsg(msg);
// resultDto.setResult(result);
// }
// return resultDto;
// }
//
// /**
// * @return RestResultDto
// * @Title: findAlarmRuleByDeviceId
// * @Description: 根据deviceId查询告警规则
// */
// @SuppressWarnings({"rawtypes", "unchecked"})
// @ApiOperation(value = "根据deviceId查询告警规则", notes = "根据deviceId查询告警规则")
// @RequestMapping(value = "findAlarmRuleByDeviceId", method =
// {RequestMethod.POST})
// public RestResultDto findAlarmRuleByDeviceId(@RequestBody AlarmRule request)
// {
// RestResultDto resultDto = new RestResultDto();
// Integer result = RestResultDto.RESULT_SUCC;
// String msg = null;
// Object data = null;
// String exception = null;
// try {
//
// AlarmRule rule = alarmRuleService.findById(request.getDeviceId());
// data = rule;
// msg = "查询告警规则成功！";
//
// } catch (Exception e) {
// result = RestResultDto.RESULT_FAIL;
// exception = e.getMessage();
// data = null;
// msg = "查询告警规则失败！";
// } finally {
// resultDto.setData(data);
// resultDto.setException(exception);
// resultDto.setMsg(msg);
// resultDto.setResult(result);
// }
// return resultDto;
// }
//
// }
