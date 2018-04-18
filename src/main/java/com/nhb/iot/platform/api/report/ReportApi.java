package com.nhb.iot.platform.api.report;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.nhb.iot.platform.dataaccess.service.device.DeviceTypeService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
import com.nhb.iot.platform.dataaccess.service.system.SysAreaService;
import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
import com.nhb.iot.platform.entity.data.EnergyCusume;
import com.nhb.iot.platform.entity.device.DeviceType;
import com.nhb.iot.platform.entity.device.ReceiptDevice;
import com.nhb.iot.platform.entity.system.SysArea;
import com.nhb.iot.platform.entity.system.SysUser;
import com.nhb.iot.platform.enums.SysUserRoleEnum;
import com.nhb.iot.platform.request.report.ReportRequest;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/report")
public class ReportApi {

  @Autowired
  private SysAreaService sysAreaService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private ReceiptDeviceService receiptDeviceService;

  @Autowired
  private DeviceTypeService deviceTypeService;

  @Autowired
  private EnergyCusumeService energyCusumeService;

  @Autowired
  private CommonService commonService;

  /**
   * @return RestResultDto
   * @Title: findEnergyByDeviceType
   * @Description: 获取基站各设备类型耗电量
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "获取基站各设备类型耗电量", notes = "参数：areaId，userId，日期格式为 2017-10-26")
  @RequestMapping(value = "findEnergyByDeviceType", method = {RequestMethod.POST})
  public RestResultDto findEnergyByDeviceType(@RequestBody ReportRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      // 只有站址区域id
      List<String> areaIds = request.getAreaIds();
      String userId = request.getUserId();
      // 开始结束时间
      String startDate = request.getStartTime();
      String endDate = request.getEndTime();
      // 查询所有要显示的区域信息
      List<SysArea> sysAreas = sysAreaService.findByIds(areaIds);
      // 用于存储需要查询的DeviceId
      List<String> deviceIds = Lists.newArrayList();
      // 每一个区域所对应的deviceIds
      Map<String, List<String>> mapValue = Maps.newHashMap();
      // 在区域下的deviceId
      List<String> deviceIdInArea = null;
      // 保存 id 和 name 对应的map
      Map<String, String> areIdForName = Maps.newHashMap();
      List<String> deviceIdsChild = null;
      List<String> childDeviceIds = null;
      for (SysArea area : sysAreas) {
        deviceIdInArea = Lists.newArrayList();
        // 如果是区域信息
        deviceIdsChild = Lists.newArrayList();
        childDeviceIds = commonService.getDeviceNode(area.getId(), deviceIdsChild);
        if (!CollectionUtils.isEmpty(childDeviceIds)) {
          deviceIds.addAll(childDeviceIds);
          deviceIdInArea.addAll(childDeviceIds);
        }
        mapValue.put(area.getId(), deviceIdInArea);
        areIdForName.put(area.getId(), area.getName());
      }

      // 对应设备id和设备类型
      List<ReceiptDevice> devices = receiptDeviceService.findByIds(deviceIds);
      Map<String, String> deviceTypeMap = Maps.newHashMap();
      if (!CollectionUtils.isEmpty(devices)) {
        for (ReceiptDevice device : devices) {
          deviceTypeMap.put(device.getId(), device.getDeviceType());
        }
      }

      // 用户信息，如果是 用户，则查询 父节点 下属的设备类型
      SysUser sysUser = sysUserService.findById(userId);
      if (sysUser.getRole().equals(SysUserRoleEnum.SUPERADMIN.getKey())
          || sysUser.getRole().equals(SysUserRoleEnum.ADMIN.getKey())
          || sysUser.getRole().equals(SysUserRoleEnum.USER.getKey())) {
        userId = sysUser.getParentId();
      }

      // 查询下属 设备类型
      List<DeviceType> deviceTypeList = deviceTypeService.findByUserId(userId);
      List<String> deviceTypes = Lists.newArrayList();
      // 缓存 编号和 名称
      Map<String, String> deviceTypeForName = Maps.newHashMap();
      if (!CollectionUtils.isEmpty(deviceTypeList)) {
        for (DeviceType type : deviceTypeList) {
          deviceTypes.add(type.getDeviceTypeCode());
          deviceTypeForName.put(type.getDeviceTypeCode(), type.getDeviceTypeName());
        }
      }

      // 查询 电能汇总表
      List<EnergyCusume> cusumesList =
          energyCusumeService.findByDeviceIdInAndDateBetween(deviceIds, startDate, endDate);

      // 所有电的的历史数据
      // 对历史数据进行分组 key-deviceId, value-list
      Map<String, List<EnergyCusume>> dataMap = commonService.cacheEnergy(cusumesList);

      // 遍历所有 要显示的 区域信息key
      Set<String> areaIdsKey = mapValue.keySet();
      List<String> areaIdsA = Lists.newArrayList();
      for (String key : areaIdsKey) {
        areaIdsA.add(key);
      }

      Map<String, Object> dtMap = null;
      List<Object> dtList = null;
      Map<String, Object> areaMap = null;
      List<Object> returnValue = Lists.newArrayList();
      // 同一 类型设备 的总值
      Double typeElec = (double) 0;
      // 同一 区域下总值
      Double totalElec = (double) 0;
      // 同一 类型设备 的总值
      Double typeMoney = (double) 0;
      // 同一 区域下电费
      Double totalMoney = (double) 0;

      List<String> devlist = null;
      // 遍历区域,区域里根据 类型匹配
      for (String key : areaIdsA) {
        // 区域下所有id的列表
        devlist = mapValue.get(key);
        areaMap = Maps.newHashMap();
        dtList = Lists.newArrayList();
        totalElec = (double) 0;
        totalMoney = (double) 0;

        for (String devicetype : deviceTypes) {
          dtMap = Maps.newHashMap();
          typeElec = (double) 0;
          typeMoney = (double) 0;
          for (String deviceId : devlist) {
            if (deviceTypeMap.get(deviceId).equals(devicetype)) {
              List<EnergyCusume> list = dataMap.get(deviceId);
              if (!CollectionUtils.isEmpty(list)) {
                for (EnergyCusume cusume : list) {
                  typeElec = typeElec + cusume.getCusume();
                  typeMoney = typeMoney + cusume.getMoneySpent();
                }
              }
            }
          }
          dtMap.put("deviceType", devicetype);
          dtMap.put("deviceTypeName", deviceTypeForName.get(devicetype));
          dtMap.put("typeElec", typeElec);
          dtMap.put("typeMoney", typeMoney);
          dtList.add(dtMap);
          totalElec = totalElec + typeElec;
          totalMoney = totalMoney + typeMoney;
        }

        areaMap.put("data", dtList);
        areaMap.put("totalElec", totalElec);
        areaMap.put("totalMoney", totalMoney);
        areaMap.put("areaId", key);
        areaMap.put("name", areIdForName.get(key));
        returnValue.add(areaMap);
      }
      data = returnValue;
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
   * @return RestResultDto
   * @Title: findAvaMonthEnergy
   * @Description: 根据区域获取月平均电价、能耗等信息
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "根据区域获取月平均电价、能耗等信息", notes = "参数：areaId，userId，日期格式为 2017-10-26")
  @RequestMapping(value = "findAvaMonthEnergy", method = {RequestMethod.POST})
  public RestResultDto findAvaMonthEnergy(@RequestBody ReportRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {

      // String userId = request.getUserId();
      List<String> areaIds = request.getAreaIds();
      String startTime = request.getStartTime();
      String endTime = request.getEndTime();

      // 查询所有要显示的区域信息
      List<SysArea> sysAreas = sysAreaService.findByIds(areaIds);
      // 用于存储需要查询的DeviceId
      List<String> deviceIds = Lists.newArrayList();
      // 每一个区域所对应的deviceIds
      Map<String, List<String>> mapValue = Maps.newHashMap();
      // 在区域下的deviceId
      List<String> deviceIdInArea = null;
      // 保存 id 和 name 对应的map
      Map<String, String> areIdForName = Maps.newHashMap();
      for (SysArea area : sysAreas) {
        deviceIdInArea = Lists.newArrayList();
        // 如果是区域信息
        List<String> deviceIdsChild = Lists.newArrayList();
        List<String> childDeviceIds = commonService.getDeviceNode(area.getId(), deviceIdsChild);
        if (!CollectionUtils.isEmpty(childDeviceIds)) {
          deviceIds.addAll(childDeviceIds);
          deviceIdInArea.addAll(childDeviceIds);
        }
        mapValue.put(area.getId(), deviceIdInArea);
        areIdForName.put(area.getId(), area.getName());
      }

      // 查询 电能汇总表
      List<EnergyCusume> cusumesList =
          energyCusumeService.findByDeviceIdInAndDateBetween(deviceIds, startTime, endTime);

      // 能耗记录到缓存中
      Map<String, List<EnergyCusume>> energyMap = commonService.cacheEnergy(cusumesList);

      List<Object> returnValue = Lists.newArrayList();
      Map<String, Double> dateEnergyMap;
      Map<String, Double> dateMoneyMap;
      Double energy = (double) 0;
      Double money = (double) 0;
      List<Object> energylistMap;
      List<Object> moneyListMap;
      Map<String, Object> map = null;
      // 遍历区域id
      for (String areaId : mapValue.keySet()) {
        map = Maps.newHashMap();
        energylistMap = Lists.newArrayList();
        moneyListMap = Lists.newArrayList();
        energy = (double) 0;
        money = (double) 0;
        // 遍历能耗记录deviceId
        dateMoneyMap = Maps.newHashMap();
        dateEnergyMap = Maps.newHashMap();
        for (String deviceId : energyMap.keySet()) {
          if (mapValue.get(areaId).contains(deviceId)) {
            for (EnergyCusume entity : energyMap.get(deviceId)) {
              String month = entity.getDate().substring(0, 7);
              if (dateEnergyMap.containsKey(month)) {
                energy = dateEnergyMap.get(month);
                money = dateMoneyMap.get(month);
              } else {
                energy = (double) 0;
                money = (double) 0;
              }
              energy += entity.getCusume();
              money += entity.getMoneySpent();
              dateEnergyMap.put(month, energy);
              dateMoneyMap.put(month, money);
            }
          }
        }
        energylistMap.add(dateEnergyMap);
        moneyListMap.add(dateMoneyMap);
        map.put("areaId", areaId);
        map.put("name", areIdForName.get(areaId));
        map.put("energy", energylistMap);
        map.put("money", moneyListMap);
        returnValue.add(map);
      }

      data = returnValue;

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

}
