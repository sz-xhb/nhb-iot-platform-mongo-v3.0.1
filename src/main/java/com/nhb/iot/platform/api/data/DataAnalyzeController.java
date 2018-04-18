package com.nhb.iot.platform.api.data;

import java.util.Calendar;
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
import com.nhb.iot.platform.dataaccess.service.data.EnergyCusumeService;
import com.nhb.iot.platform.dataaccess.service.system.SysAreaService;
import com.nhb.iot.platform.entity.data.EnergyCusume;
import com.nhb.iot.platform.entity.system.SysArea;
import com.nhb.iot.platform.enums.AreaTypeEnum;
import com.nhb.iot.platform.request.data.DataAnalyzeRequest;
import com.nhb.iot.platform.request.data.EconomyRequest;
import com.nhb.utils.nhb_utils.common.DateUtil;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/data/analyze")
public class DataAnalyzeController {

  @Autowired
  private EnergyCusumeService energyCusumeService;

  @Autowired
  private SysAreaService sysAreaService;

  // 用于保存区域节点下属所有的设备节点
  List<String> deviceIdsChild;

  /**
   * @return RestResultDto
   * @Title: analyzeTB
   * @Description: 同比分析
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "同比分析", notes = "传参：areaIds , startDate , endDate")
  @RequestMapping(value = "analyzeTB", method = {RequestMethod.POST})
  public RestResultDto analyzeTB(@RequestBody DataAnalyzeRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      List<String> areaIds = request.getAreaIds();
      String startDate = request.getStartDate();
      String endDate = request.getEndDate();
      if (CollectionUtils.isEmpty(areaIds) || StringUtil.isNullOrEmpty(startDate)
          || StringUtil.isNullOrEmpty(endDate)) {
        throw new Exception("必要参数不能为空！");
      }

      List<SysArea> sysAreas = sysAreaService.findByIds(areaIds);

      // 用于存储需要查询的DeviceId
      List<String> deviceIds = Lists.newArrayList();
      // 每一个区域所对应的deviceIds
      Map<String, List<String>> mapValue = Maps.newHashMap();
      List<String> deviceIdInArea = null;
      // 保存 id 和 name 对应的map
      Map<String, String> areIdForName = Maps.newHashMap();

      for (SysArea area : sysAreas) {
        deviceIdInArea = Lists.newArrayList();
        deviceIdsChild = Lists.newArrayList();
        // 当前区域下所有设备信息
        List<String> childDeviceIds = treeAreaList(area.getId());
        if (!CollectionUtils.isEmpty(childDeviceIds)) {
          deviceIds.addAll(childDeviceIds);
          deviceIdInArea.addAll(childDeviceIds);
        }
        mapValue.put(area.getId(), deviceIdInArea);
        areIdForName.put(area.getId(), area.getName());
      }

      // 查询 出所有的需要查询的deviceId
      List<EnergyCusume> cusumesList =
          energyCusumeService.findByDeviceIdInAndDateBetween(deviceIds, startDate, endDate);

      Map<String, List<EnergyCusume>> dataMap = Maps.newHashMap();
      List<EnergyCusume> listValue = null;
      for (SysArea area : sysAreas) {
        listValue = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(cusumesList)) {
          for (EnergyCusume cusume : cusumesList) {
            if (mapValue.get(area.getId()).contains(cusume.getDeviceId())) {
              listValue.add(cusume);
            }
          }
        }
        dataMap.put(area.getId(), listValue);
      }

      data = dataMap;
      msg = "获取同比分析数据成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "获取同比分析数据失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  // 递归获取当前节点 下属所有的设备节点
  public List<String> treeAreaList(String areaId) {
    List<SysArea> sysAreas = sysAreaService.findByParentId(areaId);
    if (!CollectionUtils.isEmpty(sysAreas)) {
      for (SysArea area : sysAreas) {
        if (area.getAreaType().equals(AreaTypeEnum.DeviceNode.getKey())) {
          deviceIdsChild.add(area.getId());
        } else {
          treeAreaList(area.getId());
        }
      }
    }
    return deviceIdsChild;
  }

  /**
   * @return RestResultDto
   * @Title: analyzeHB
   * @Description: 环比分析
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "环比分析", notes = "传参：deviceId , startMonth , endMonth")
  @RequestMapping(value = "analyzeHB", method = {RequestMethod.POST})
  public RestResultDto analyzeHB(@RequestBody DataAnalyzeRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String areaId = request.getAreaId();
      String startMonth = request.getStartMonth();
      String endMonth = request.getEndMonth();
      if (StringUtil.isNullOrEmpty(areaId) || StringUtil.isNullOrEmpty(startMonth)
          || StringUtil.isNullOrEmpty(endMonth)) {
        throw new Exception("必要参数不能为空！");
      }
      deviceIdsChild = Lists.newArrayList();
      List<String> deviceIds = treeAreaList(areaId);

      String startMonthFirstData = startMonth + "-01";
      // 最后一天
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(DateUtil.parse(startMonthFirstData + " 00:00:00"));
      // 设置Calendar月份数为下一个月
      calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
      // 设置Calendar日期为下一个月一号
      calendar.set(Calendar.DATE, 1);
      // 设置Calendar日期减一,为本月末
      calendar.add(Calendar.DATE, -1);
      // 默认结束到昨天为止的天数
      String startMonthEndData = DateUtil.format(calendar.getTime(), DateUtil.DATE_FORMAT);

      String endMonthFirstData = endMonth + "-01";
      Calendar calendar2 = Calendar.getInstance();
      calendar2.setTime(DateUtil.parse(endMonthFirstData + " 00:00:00"));
      calendar2.set(Calendar.MONTH, calendar2.get(Calendar.MONTH) + 1);
      calendar2.set(Calendar.DATE, 1);
      calendar2.add(Calendar.DATE, -1);
      String endMonthEndData = DateUtil.format(calendar2.getTime(), DateUtil.DATE_FORMAT);

      List<EnergyCusume> startMonthCusumes = energyCusumeService
          .findByDeviceIdInAndDateBetween(deviceIds, startMonthFirstData, startMonthEndData);

      List<EnergyCusume> endMonthCusumes = energyCusumeService
          .findByDeviceIdInAndDateBetween(deviceIds, endMonthFirstData, endMonthEndData);

      List<EnergyCusume> cusumes = Lists.newArrayList();
      if (!CollectionUtils.isEmpty(startMonthCusumes)) {
        cusumes.addAll(startMonthCusumes);
      }
      if (!CollectionUtils.isEmpty(endMonthCusumes)) {
        cusumes.addAll(endMonthCusumes);
      }
      data = cusumes;
      msg = "获取环比分析数据成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "获取环比分析数据失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  /**
   * @param request
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "同一设备节电信息",
      notes = "参数：economyDeviceId,economyStartDate,economyEndDate,uneconomyStartData,uneconomyEndData")
  @RequestMapping(value = "economyOne", method = {RequestMethod.POST})
  public RestResultDto economyOne(@RequestBody EconomyRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {

      String economyDeviceId = request.getEconomyDeviceId();
      // 开启节能时间段
      String economyStartDate = request.getEconomyStartDate();
      String economyEndDate = request.getEconomyEndDate();
      // 未开启节能时间段
      String uneconomyStartData = request.getUneconomyStartDate();
      String uneconomyEndData = request.getUneconomyEndDate();

      List<EnergyCusume> economyCusumes = energyCusumeService
          .findByDeviceIdAndDateBetween(economyDeviceId, economyStartDate, economyEndDate);

      List<EnergyCusume> uneconomyCusumes = energyCusumeService
          .findByDeviceIdAndDateBetween(economyDeviceId, uneconomyStartData, uneconomyEndData);

      Map<String, Object> mapValue = Maps.newHashMap();
      Double economyDeviceEnergy = (double) 0;
      Double economyDeviceMoney = (double) 0;
      if (!CollectionUtils.isEmpty(economyCusumes)) {
        for (EnergyCusume cusume : economyCusumes) {
          economyDeviceEnergy += cusume.getCusume();
          economyDeviceMoney += cusume.getMoneySpent();
        }
      }
      Double uneconomyDeviceEnergy = (double) 0;
      Double uneconomyDeviceMoney = (double) 0;
      if (!CollectionUtils.isEmpty(uneconomyCusumes)) {
        for (EnergyCusume cusume : uneconomyCusumes) {
          uneconomyDeviceEnergy += cusume.getCusume();
          uneconomyDeviceMoney += cusume.getMoneySpent();
        }
      }

      mapValue.put("economyDeviceEnergy", economyDeviceEnergy);
      mapValue.put("economyDeviceMoney", economyDeviceMoney);
      mapValue.put("uneconomyDeviceEnergy", uneconomyDeviceEnergy);
      mapValue.put("uneconomyDeviceMoney", uneconomyDeviceMoney);
      mapValue.put("economyEnergy", uneconomyDeviceEnergy - economyDeviceEnergy);
      mapValue.put("economyMoney", uneconomyDeviceMoney - economyDeviceMoney);
      mapValue.put("economyRate",
          (uneconomyDeviceEnergy - economyDeviceEnergy) / uneconomyDeviceEnergy);

      data = mapValue;
      msg = "查询成功！";

    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "查询失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  /**
   * @param request
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "不同设备节电信息",
      notes = "参数：economyDeviceId,uneconomyDeviceId,economyStartDate,economyEndDate")
  @RequestMapping(value = "economyTwo", method = {RequestMethod.POST})
  public RestResultDto economyTwo(@RequestBody EconomyRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {

      String economyDeviceId = request.getEconomyDeviceId();
      String uneconomyDeviceId = request.getUneconomyDeviceId();
      // 开启节能时间段
      String economyStartDate = request.getEconomyStartDate();
      String economyEndDate = request.getEconomyEndDate();

      List<EnergyCusume> economyCusumes = energyCusumeService
          .findByDeviceIdAndDateBetween(economyDeviceId, economyStartDate, economyEndDate);

      List<EnergyCusume> uneconomyCusumes = energyCusumeService
          .findByDeviceIdAndDateBetween(uneconomyDeviceId, economyStartDate, economyEndDate);

      Map<String, Object> mapValue = Maps.newHashMap();
      Double economyDeviceEnergy = (double) 0;
      Double economyDeviceMoney = (double) 0;
      if (!CollectionUtils.isEmpty(economyCusumes)) {
        for (EnergyCusume cusume : economyCusumes) {
          economyDeviceEnergy += cusume.getCusume();
          economyDeviceMoney += cusume.getMoneySpent();
        }
      }
      Double uneconomyDeviceEnergy = (double) 0;
      Double uneconomyDeviceMoney = (double) 0;
      if (!CollectionUtils.isEmpty(uneconomyCusumes)) {
        for (EnergyCusume cusume : uneconomyCusumes) {
          uneconomyDeviceEnergy += cusume.getCusume();
          uneconomyDeviceMoney += cusume.getMoneySpent();
        }
      }

      mapValue.put("economyDeviceEnergy", economyDeviceEnergy);
      mapValue.put("economyDeviceMoney", economyDeviceMoney);
      mapValue.put("uneconomyDeviceEnergy", uneconomyDeviceEnergy);
      mapValue.put("uneconomyDeviceMoney", uneconomyDeviceMoney);
      mapValue.put("economyEnergy", uneconomyDeviceEnergy - economyDeviceEnergy);
      mapValue.put("economyMoney", uneconomyDeviceMoney - economyDeviceMoney);
      mapValue.put("economyRate",
          (uneconomyDeviceEnergy - economyDeviceEnergy) / uneconomyDeviceEnergy);

      data = mapValue;
      msg = "查询成功！";

    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "查询失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }
}
