//package com.nhb.iot.platform.api.system;
//
//import java.util.Date;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import com.google.common.collect.Lists;
//import com.nhb.iot.platform.dataaccess.service.data.DataElectricityService;
//import com.nhb.iot.platform.dataaccess.service.data.EnergyCusumeService;
//import com.nhb.iot.platform.dataaccess.service.device.ReceiptCollectorService;
//import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
//import com.nhb.iot.platform.dataaccess.service.device.ReceiptMeterService;
//import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
//import com.nhb.iot.platform.entity.device.ReceiptCollector;
//import com.nhb.iot.platform.entity.device.ReceiptDevice;
//import com.nhb.iot.platform.entity.device.ReceiptMeter;
//import com.nhb.iot.platform.entity.system.SysUser;
//import com.nhb.iot.platform.enums.SysUserRoleEnum;
//import com.nhb.iot.platform.job.EnergyCusumeDay;
//import com.nhb.iot.platform.request.data.EnergyDataRequest;
//import com.nhb.utils.nhb_utils.common.DateUtil;
//import com.nhb.utils.nhb_utils.common.RestResultDto;
//import com.nhb.utils.nhb_utils.common.StringUtil;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("api/v1/handle")
//public class HandleCalcaEnergyController {
//
//  @Autowired
//  private EnergyCusumeDay energyCusumeDay;
//
//  @Autowired
//  private EnergyCusumeService energyCusumeService;
//
//  @Autowired
//  private SysUserService sysUserService;
//
//  @Autowired
//  private ReceiptDeviceService receiptDeviceService;
//
//  @Autowired
//  private ReceiptCollectorService receiptCollectorService;
//
//  @Autowired
//  private ReceiptMeterService receiptMeterService;
//
//  @Autowired
//  private DataElectricityService dataElectricityService;
//
//  /**
//   * @return RestResultDto
//   * @Title: handleEnergy
//   * @Description: 获取日能耗数据
//   */
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  @ApiOperation(value = "手动计算日能耗", notes = "手动计算日能耗，日期，和日期的开始和结束时间")
//  @RequestMapping(value = "handleEnergy", method = {RequestMethod.POST})
//  public RestResultDto handleEnergy(@RequestBody EnergyDataRequest request) {
//    RestResultDto resultDto = new RestResultDto();
//    Integer result = RestResultDto.RESULT_SUCC;
//    String msg = null;
//    Object data = null;
//    String exception = null;
//    try {
//
//      String userId = request.getUserId();
//      String dates = request.getDates();
//
//      SysUser sysUser = sysUserService.findById(userId);
//      List<String> deviceIds = Lists.newArrayList();
//      if (sysUser.getRole().equals(SysUserRoleEnum.SUPERADMIN.getKey())) {// 超级管理员
//        List<ReceiptDevice> devices = receiptDeviceService.findAll();
//        if (!CollectionUtils.isEmpty(devices)) {
//          for (ReceiptDevice device : devices) {
//            deviceIds.add(device.getId());
//          }
//        }
//      } else if (sysUser.getRole().equals(SysUserRoleEnum.ADMIN.getKey())) {/* // 管理员
//        List<ReceiptCollector> collectors = receiptCollectorService.findAllByManagerId(userId);
//        List<String> collectorIds = Lists.newArrayList();
//        for (ReceiptCollector collector : collectors) {
//          collectorIds.add(collector.getId());
//        }
//        List<ReceiptMeter> meters = receiptMeterService.findByCollectorIdIn(collectorIds);
//        List<String> meterIds = Lists.newArrayList();
//        for (ReceiptMeter meter : meters) {
//          meterIds.add(meter.getId());
//        }
//        List<ReceiptDevice> devices = receiptDeviceService.findByMeterIdIn(meterIds);
//        for (ReceiptDevice device : devices) {
//          deviceIds.add(device.getId());
//        }
//      */}
//
//      String[] days = StringUtil.splitComma(dates);
//      for (int i = 0; i < days.length; i++) {
//        String start = days[i] + " 00:00:00";
//        String end = days[i] + " 23:59:59";
//        Date startTime = DateUtil.parse(days[i] + " 00:00:00", DateUtil.DATETIME_FORMAT);
//        Date endTime = DateUtil.parse(days[i] + " 23:59:59", DateUtil.DATETIME_FORMAT);
//        // 先删除当天已经计算好的数据的数据
//        energyCusumeService.deleteByDateBetweenAndDeviceIdIn(start, end, deviceIds);
//
//        // 重新计算
//        energyCusumeDay.calcEnergyCusumeDay(startTime, endTime, days[i], deviceIds);
//      }
//
//      data = true;
//      msg = "计算成功！";
//
//    } catch (Exception e) {
//      result = RestResultDto.RESULT_FAIL;
//      exception = e.getMessage();
//      data = null;
//      msg = "计算失败！";
//    } finally {
//      resultDto.setData(data);
//      resultDto.setException(exception);
//      resultDto.setMsg(msg);
//      resultDto.setResult(result);
//    }
//    return resultDto;
//  }
//
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  @ApiOperation(value = "删除数据", notes = "删除数据")
//  @RequestMapping(value = "deleteData", method = {RequestMethod.POST})
//  public RestResultDto deleteData() {
//    RestResultDto resultDto = new RestResultDto();
//    Integer result = RestResultDto.RESULT_SUCC;
//    String msg = null;
//    Object data = null;
//    String exception = null;
//    try {
//
//      String date = "2017-11-15 00:00:00";
//      Date dateTime = DateUtil.parse(date);
//
//      dataElectricityService.deleteByReadTimeLessThan(dateTime);
//
//    } catch (Exception e) {
//      result = RestResultDto.RESULT_FAIL;
//      exception = e.getMessage();
//      data = null;
//      msg = "计算失败！";
//    } finally {
//      resultDto.setData(data);
//      resultDto.setException(exception);
//      resultDto.setMsg(msg);
//      resultDto.setResult(result);
//    }
//    return resultDto;
//  }
//
//}
