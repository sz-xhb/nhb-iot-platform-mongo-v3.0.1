//package com.nhb.iot.platform.api.data;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.nhb.iot.platform.dataaccess.service.data.EnergyCusumeService;
//import com.nhb.iot.platform.dataaccess.service.system.SysAreaService;
//import com.nhb.iot.platform.dataaccess.service.system.SysUserAreaService;
//import com.nhb.iot.platform.entity.data.EnergyCusume;
//import com.nhb.iot.platform.entity.system.SysArea;
//import com.nhb.iot.platform.entity.system.SysUserArea;
//import com.nhb.iot.platform.enums.SysAreaTypeEnum;
//import com.nhb.iot.platform.request.data.HomeDataRequest;
//import com.nhb.utils.nhb_utils.common.RestResultDto;
//import com.nhb.utils.nhb_utils.common.StringUtil;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("api/v1/data/home")
//public class DataHomeController {
//
//  @Autowired
//  private SysAreaService sysAreaService;
//
//  @Autowired
//  private EnergyCusumeService energyCusumeService;
//
//  @Autowired
//  private SysUserAreaService sysUserAreaService;
//
//  /**
//   * @return RestResultDto
//   * @Title: monthEnergy
//   * @Description: 获取月能耗数据
//   */
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  @ApiOperation(value = "根据userId和时间查询按区域月能耗", notes = "根据userId和时间查询按区域月能耗")
//  @RequestMapping(value = "monthEnergy", method = {RequestMethod.POST})
//  public RestResultDto monthEnergy(@RequestBody HomeDataRequest request) {
//    RestResultDto resultDto = new RestResultDto();
//    Integer result = RestResultDto.RESULT_SUCC;
//    String msg = null;
//    Object data = null;
//    String exception = null;
//    try {
//      String startDate = request.getStartDate();
//      String endDate = request.getEndDate();
//      String userId = request.getUserId();
//      if (StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(startDate)
//          || StringUtil.isNullOrEmpty(endDate)) {
//        throw new Exception("必要参数不能为空！");
//      }
//
//      List<SysArea> allSysAreas = sysAreaService.findByManagerId(userId);
//      // 所有顶级节点区域
//      List<SysArea> topAreas = Lists.newArrayList();
//      // 所有设备节点
//      List<SysArea> deviceAreas = Lists.newArrayList();
//      if (!CollectionUtils.isEmpty(allSysAreas)) {
//        for (SysArea area : allSysAreas) {
//          if (area.getResourceType().equals(SysAreaTypeEnum.AREA.getKey())
//              && area.getParentId().equals("0")) {
//            topAreas.add(area);
//          } else if (!area.getResourceType().equals(SysAreaTypeEnum.AREA.getKey())) {
//            deviceAreas.add(area);
//          }
//        }
//      }
//
//      // 根据设备Ids获取所有时间段内的 能耗数据
//      List<String> deviceIds = Lists.newArrayList();
//      if (!CollectionUtils.isEmpty(deviceAreas)) {
//        for (SysArea area : deviceAreas) {
//          deviceIds.add(area.getDeviceId());
//        }
//      }
//
//      // 当前 管理员下属 所有设备 在区间内的能耗数据
//      List<EnergyCusume> cusumeList =
//          energyCusumeService.findByDeviceIdInAndDateBetween(deviceIds, startDate, endDate);
//
//      Map<String, Object> mapValue = null;
//      List<Object> returnValue = Lists.newArrayList();
//      // key-类型，value-能耗值
//      Map<String, Double> totalByType = null;
//      Map<String, Double> totalMoneyByType = null;
//      Double total = (double) 0;
//      Double totalMoney = (double) 0;
//      for (SysArea top : topAreas) {
//        mapValue = Maps.newHashMap();
//        totalByType = Maps.newHashMap();
//        totalMoneyByType = Maps.newHashMap();
//        // 设备节点
//        for (EnergyCusume entity : cusumeList) {
//          // 不再该顶级节点下不进行计算
//          if (!entity.getTopAreaId().equals(top.getId())) {
//            continue;
//          }
//          if (totalByType.containsKey(entity.getResourceType())) {
//            total = totalByType.get(entity.getResourceType());
//            totalMoney = totalMoneyByType.get(entity.getResourceType());
//          } else {
//            total = (double) 0;
//            totalMoney = (double) 0;
//          }
//          total = total + entity.getCusume();
//          totalMoney = totalMoney + entity.getMoneySpent();
//          totalByType.put(entity.getResourceType(), total);
//          totalMoneyByType.put(entity.getResourceType(), totalMoney);
//        }
//
//        mapValue.put("areaId", top.getId());
//        mapValue.put("name", top.getName());
//        mapValue.put("cusume", totalByType);
//        mapValue.put("cusumeMoney", totalMoneyByType);
//        returnValue.add(mapValue);
//      }
//      data = returnValue;
//      msg = "获取月能耗数据成功！";
//    } catch (Exception e) {
//      result = RestResultDto.RESULT_FAIL;
//      exception = e.getMessage();
//      data = null;
//      msg = "获取月能耗数据失败！";
//    } finally {
//      resultDto.setData(data);
//      resultDto.setException(exception);
//      resultDto.setMsg(msg);
//      resultDto.setResult(result);
//    }
//    return resultDto;
//  }
//
//  /**
//   * @return RestResultDto
//   * @Title: dayEnergy
//   * @Description: 获取日能耗数据
//   */
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  @ApiOperation(value = "根据userId和时间查询按区域日能耗", notes = "根据userId和时间查询按区域日能耗")
//  @RequestMapping(value = "dayEnergy", method = {RequestMethod.POST})
//  public RestResultDto dayEnergy(@RequestBody HomeDataRequest request) {
//    RestResultDto resultDto = new RestResultDto();
//    Integer result = RestResultDto.RESULT_SUCC;
//    String msg = null;
//    Object data = null;
//    String exception = null;
//    try {
//      String startDate = request.getStartDate();
//      String endDate = request.getEndDate();
//      String userId = request.getUserId();
//      if (StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(startDate)
//          || StringUtil.isNullOrEmpty(endDate)) {
//        throw new Exception("必要参数不能为空！");
//      }
//
//      List<SysArea> allSysAreas = sysAreaService.findByManagerId(userId);
//      // 所有顶级节点区域
//      List<SysArea> topAreas = Lists.newArrayList();
//      // 所有设备节点
//      List<SysArea> deviceAreas = Lists.newArrayList();
//      if (!CollectionUtils.isEmpty(allSysAreas)) {
//        for (SysArea area : allSysAreas) {
//          if (area.getResourceType().equals(SysAreaTypeEnum.AREA.getKey())
//              && area.getParentId().equals("0")) {
//            topAreas.add(area);
//          } else if (!area.getResourceType().equals(SysAreaTypeEnum.AREA.getKey())) {
//            deviceAreas.add(area);
//          }
//        }
//      }
//
//      // 根据设备Ids获取所有时间段内的 能耗数据
//      List<String> deviceIds = Lists.newArrayList();
//      if (!CollectionUtils.isEmpty(deviceAreas)) {
//        for (SysArea area : deviceAreas) {
//          deviceIds.add(area.getDeviceId());
//        }
//      }
//
//      // 根据id查询所有能耗数据
//      List<EnergyCusume> cusumeList =
//          energyCusumeService.findByDeviceIdInAndDateBetween(deviceIds, startDate, endDate);
//
//      List<Object> returnValue = Lists.newArrayList();
//      Map<String, Object> map = null;
//
//      // 存储按照类型划分 key- 类型，value 是能耗list
//      Map<String, List<EnergyCusume>> mapByType = null;
//      List<EnergyCusume> listByType = null;
//      // 遍历所有的顶级节点
//      for (SysArea area : topAreas) {
//        mapByType = Maps.newHashMap();
//        listByType = Lists.newArrayList();
//        map = Maps.newHashMap();
//        for (EnergyCusume cusume : cusumeList) {
//          // 不再顶级节点下不进行计算
//          if (!cusume.getTopAreaId().equals(area.getId())) {
//            continue;
//          }
//          if (mapByType.containsKey(cusume.getResourceType())) {
//            listByType = mapByType.get(cusume.getResourceType());
//          } else {
//            listByType = Lists.newArrayList();
//          }
//          listByType.add(cusume);
//          mapByType.put(cusume.getResourceType(), listByType);
//        }
//
//        Set<String> keySet = mapByType.keySet();
//        List<String> keys = Lists.newArrayList();
//        for (String key : keySet) {
//          keys.add(key);
//        }
//
//        Map<String, Double> dateMap = null;
//        List<EnergyCusume> cusumes = null;
//        Map<String, Object> mapValue = Maps.newHashMap();
//        Map<String, Object> value = Maps.newHashMap();
//        for (String key : keys) {
//          dateMap = Maps.newHashMap();
//          Double dayTotal = (double) 0;
//          cusumes = mapByType.get(key);
//          for (EnergyCusume cusume : cusumes) {
//            if (dateMap.containsKey(cusume.getDate())) {
//              dayTotal = dateMap.get(cusume.getDate());
//            } else {
//              dayTotal = (double) 0;
//            }
//            dayTotal = dayTotal + cusume.getCusume();
//            dateMap.put(cusume.getDate(), dayTotal);
//          }
//          mapValue.put(key, dateMap);
//          value.put(key, dateMap);
//        }
//        map.put("areaId", area.getId());
//        map.put("name", area.getName());
//        map.put("cusume", mapValue);
//        returnValue.add(map);
//      }
//
//      data = returnValue;
//      msg = "获取日能耗数据成功！";
//    } catch (Exception e) {
//      result = RestResultDto.RESULT_FAIL;
//      exception = e.getMessage();
//      data = null;
//      msg = "获取日能耗数据失败！";
//    } finally {
//      resultDto.setData(data);
//      resultDto.setException(exception);
//      resultDto.setMsg(msg);
//      resultDto.setResult(result);
//    }
//    return resultDto;
//  }
//
//  /**
//   * @return RestResultDto
//   * @Title: dayEnergy
//   * @Description: 获取日能耗数据
//   */
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  @ApiOperation(value = "根据userId和时间查询按区域日能耗", notes = "根据userId和时间查询按区域日能耗")
//  @RequestMapping(value = "userMonthEnergy", method = {RequestMethod.POST})
//  public RestResultDto userMonthEnergy(@RequestBody HomeDataRequest request) {
//    RestResultDto resultDto = new RestResultDto();
//    Integer result = RestResultDto.RESULT_SUCC;
//    String msg = null;
//    Object data = null;
//    String exception = null;
//    try {
//      String startDate = request.getStartDate();
//      String endDate = request.getEndDate();
//      String userId = request.getUserId();
//      if (StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(startDate)
//          || StringUtil.isNullOrEmpty(endDate)) {
//        throw new Exception("必要参数不能为空！");
//      }
//
//      // 查询当前 用户下属所有区域
//      List<SysUserArea> userAreas = sysUserAreaService.findByUserId(userId);
//      List<String> areaIds = Lists.newArrayList();
//      if (!CollectionUtils.isEmpty(userAreas)) {
//        for (SysUserArea userArea : userAreas) {
//          areaIds.add(userArea.getAreaId());
//        }
//      }
//
//      List<SysArea> areas = Lists.newArrayList();
//      if (!CollectionUtils.isEmpty(areaIds)) {
//        areas = sysAreaService.findByIds(areaIds);
//      }
//
//      if (!CollectionUtils.isEmpty(areas)) {
//
//      }
//
//      msg = "获取日能耗数据成功！";
//    } catch (Exception e) {
//      result = RestResultDto.RESULT_FAIL;
//      exception = e.getMessage();
//      data = null;
//      msg = "获取日能耗数据失败！";
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
