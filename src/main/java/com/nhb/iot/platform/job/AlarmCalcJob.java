//package com.nhb.iot.platform.job;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import org.apache.commons.collections.MapUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.nhb.iot.platform.dataaccess.service.alarm.AlarmRuleService;
//import com.nhb.iot.platform.dataaccess.service.alarm.AlarmService;
//import com.nhb.iot.platform.dataaccess.service.data.DataElectricityService;
//import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
//import com.nhb.iot.platform.dataaccess.service.system.SysAreaService;
//import com.nhb.iot.platform.dataaccess.service.system.SysUserAreaService;
//import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
//import com.nhb.iot.platform.entity.alarm.Alarm;
//import com.nhb.iot.platform.entity.alarm.AlarmRule;
//import com.nhb.iot.platform.entity.data.DataElectricity;
//import com.nhb.iot.platform.entity.device.ReceiptDevice;
//import com.nhb.iot.platform.entity.system.SysArea;
//import com.nhb.iot.platform.entity.system.SysUser;
//import com.nhb.iot.platform.entity.system.SysUserArea;
//import com.nhb.iot.platform.enums.AlarmTypeEnum;
//import com.nhb.iot.platform.websocket.WebSocketServer;
//import com.nhb.iot.platform.websocket.response.WebSockerResponse;
//import com.nhb.iot.platform.websocket.response.WebSocketTypeEnum;
//import com.nhb.utils.nhb_utils.common.DateUtil;
//import com.nhb.utils.nhb_utils.common.StringUtil;
//import com.nhb.utils.nhb_utils.json.JsonMapper;
//
///**
// * Created by Administrator on 2017/11/7.
// */
//@Component
//public class AlarmCalcJob {
//
//  @Autowired
//  private StringRedisTemplate stringRedisTemplate;
//
//  @Autowired
//  private AlarmRuleService alarmRuleService;
//
//  @Autowired
//  private AlarmService alarmService;
//
//  @Autowired
//  private SysAreaService sysAreaService;
//
//  @Autowired
//  private SysUserAreaService sysUserAreaService;
//
//  @Autowired
//  private SysUserService sysUserService;
//
//  @Autowired
//  private ReceiptDeviceService receiptDeviceService;
//
//  private Date lastCalcTime;
//
//  private String LAST_CALC_TIME = "LAST_CALC_TIME";
//
//  private String DEVICE_PHONE = "DEVICE_PHONE";
//
//  private Map<String, List<String>> deviceIdForUserId = Maps.newHashMap();
//
//  @Value(value = "${messageUrl}")
//  private String messageUrl;
//
//  @Value(value = "${messageContent}")
//  private String messageContent;
//
//  @Scheduled(cron = "0 */3 * * * ?")
//  public void alarmCalcJob() throws Exception {
//
//    // 查询出所有的告警规则
//    List<AlarmRule> alarmRules = alarmRuleService.findAll();
//    Map<String, AlarmRule> ruleMap = Maps.newHashMap();
//    if (!CollectionUtils.isEmpty(alarmRules)) {
//      for (AlarmRule rule : alarmRules) {
//        ruleMap.put(rule.getDeviceId(), rule);
//      }
//    }
//
//    List<ReceiptDevice> devices = receiptDeviceService.findAll();
//    Map<String, ReceiptDevice> mapValue = Maps.newHashMap();
//    if (!CollectionUtils.isEmpty(devices)) {
//      for (ReceiptDevice device : devices) {
//        mapValue.put(device.getId(), device);
//      }
//    }
//
//    // 查询历史数据
//    // 先从本地获取时间
//    if (null == lastCalcTime) {
//      // 如果本地时间为空，再从缓存中获取
//      String lastTimeStr = stringRedisTemplate.opsForValue().get(LAST_CALC_TIME);
//      if (null != lastTimeStr) {
//        lastCalcTime = DateUtil.parse(lastTimeStr);
//      }
//      // 如果依然为空，则使用是默认时间
//      if (null == lastCalcTime) {
//        lastCalcTime = DateUtil.parse("2017-01-01 00:00:00", DateUtil.DATETIME_FORMAT);
//      }
//    }
//
//    Date endTime = new Date();
//    List<DataElectricity> dataElectricities =
//        dataElectricityService.findByReadTimeBetweenOrderByReadTimeDesc(lastCalcTime, endTime);
//
//    Alarm alarm = null;
//
//    List<Alarm> alarms = Lists.newArrayList();
//    if (!CollectionUtils.isEmpty(dataElectricities)) {
//      for (DataElectricity data : dataElectricities) {
//        AlarmRule rule = ruleMap.get(data.getDeviceId());
//        if (null == rule) {
//          continue;
//        }
//        // 电流告警判断
//        if ((data.getCurrent() == null ? 0 : data.getCurrent()) > rule.getCurrHI()
//            || (data.getCurrentA() == null ? 0 : data.getCurrentA()) > rule.getCurrHI()
//            || (data.getCurrentB() == null ? 0 : data.getCurrentB()) > rule.getCurrHI()
//            || (data.getCurrentC() == null ? 0 : data.getCurrentC()) > rule.getCurrHI()) {
//          alarm = new Alarm();
//          alarm.setDealStatus(0);
//          alarm.setAlarmTime(data.getReadTime());
//          alarm.setDeviceId(data.getDeviceId());
//          if (!MapUtils.isEmpty(mapValue)) {
//            alarm.setDeviceName(mapValue.get(data.getDeviceId()).getName());
//            alarm.setDeviceType(mapValue.get(data.getDeviceId()).getDeviceType());
//          }
//          alarm.setAlarmType(AlarmTypeEnum.CURR_EXCEPTION.getKey());
//          alarmService.save(alarm);
//          alarms.add(alarm);
//        }
//        if ((data.getVoltage() == null ? 0 : data.getVoltage()) > rule.getVoltHI()
//            || (data.getVoltageA() == null ? 0 : data.getVoltageA()) > rule.getVoltHI()
//            || (data.getVoltageB() == null ? 0 : data.getVoltageB()) > rule.getVoltHI()
//            || (data.getVoltageC() == null ? 0 : data.getVoltageC()) > rule.getVoltHI()) {
//          alarm = new Alarm();
//          alarm.setDealStatus(0);
//          alarm.setAlarmTime(data.getReadTime());
//          alarm.setDeviceId(data.getDeviceId());
//          if (!MapUtils.isEmpty(mapValue)) {
//            alarm.setDeviceName(mapValue.get(data.getDeviceId()).getName());
//            alarm.setDeviceType(mapValue.get(data.getDeviceId()).getDeviceType());
//          }
//          alarm.setAlarmType(AlarmTypeEnum.VOLT_EXCEPTION.getKey());
//          alarmService.save(alarm);
//          alarms.add(alarm);
//        }
//      }
//    }
//
//    // 发送通知
//    if (!CollectionUtils.isEmpty(alarms)) {
//      String phoneNo = "";
//      String msgContent = "";
//      WebSockerResponse msg = null;
//      Map<String, String> map = null;
//      for (Alarm entity : alarms) {
//
//        // websocket 推送到页面
//        List<String> userIds = deviceIdForUserId.get(entity.getDeviceId());
//        if (!CollectionUtils.isEmpty(userIds)) {
//          for (String userId : userIds) {
//            msg = new WebSockerResponse();
//            map = Maps.newHashMap();
//            msg.setInfoType(WebSocketTypeEnum.ALARM.getKey());
//            map.put("deviceId", entity.getDeviceId());
//            map.put("deviceName", entity.getDeviceName());
//            map.put("alarmType", entity.getAlarmType());
//            map.put("alarmTypeName", AlarmTypeEnum.getValueByKey(entity.getAlarmType()));
//            map.put("alarmTime", DateUtil.format(entity.getAlarmTime()));
//            msg.setData(map);
//            WebSocketServer.pushMessageToUser(userId, msg);
//          }
//        }
//
//        // 再短信通知
//        if (!StringUtil.isNullOrEmpty(messageUrl)) {
//          // 请求调用Url
//          phoneNo = stringRedisTemplate.opsForValue().get(DEVICE_PHONE + entity.getDeviceId());
//          msgContent = entity.getDeviceName();
//          Map<String, Object> msgMap = Maps.newHashMap();
//          msgMap.put("node", msgContent);
//          String content = JsonMapper.toJsonString(msgMap);
//          String urlNameString = messageUrl + phoneNo + messageContent + content;
//          URL realUrl = new URL(urlNameString);
//          // 打开和URL之间的连接
//          URLConnection connection = realUrl.openConnection();
//          // 设置通用的请求属性
//          connection.setRequestProperty("accept", "*/*");
//          connection.setRequestProperty("connection", "Keep-Alive");
//          connection.setRequestProperty("user-agent",
//              "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//          // 建立实际的连接
//          connection.connect();
//          // 定义 BufferedReader输入流来读取URL的响应
//          BufferedReader in =
//              new BufferedReader(new InputStreamReader(connection.getInputStream()));
//          in.close();
//        }
//      }
//    }
//
//    // 更新上次时间
//    lastCalcTime = endTime;
//    // 保存到redis
//    stringRedisTemplate.opsForValue().set(LAST_CALC_TIME, DateUtil.format(lastCalcTime));
//
//  }
//
//  @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 2)
//  public void cachePhoneNoAForDeviceId() {
//    // 缓存areaId和List<userId>的map
//    List<SysUserArea> sysUserAreas = sysUserAreaService.findAll();
//    Map<String, List<String>> areaIdForUserIdMap = Maps.newHashMap();
//    List<String> userIds = null;
//    if (!CollectionUtils.isEmpty(sysUserAreas)) {
//      for (SysUserArea sysUserArea : sysUserAreas) {
//        if (areaIdForUserIdMap.containsKey(sysUserArea.getAreaId())) {
//          userIds = areaIdForUserIdMap.get(sysUserArea.getAreaId());
//        } else {
//          userIds = Lists.newArrayList();
//        }
//        userIds.add(sysUserArea.getUserId());
//        areaIdForUserIdMap.put(sysUserArea.getAreaId(), userIds);
//      }
//    }
//
//    // 缓存deviceId和areaId的map
//    List<String> userIdList = null;
//    List<SysArea> sysAreas = sysAreaService.findByDeviceIdIsNotNull();
//    Map<String, String> deviceIdForAreaIdMap = Maps.newHashMap();
//    if (!CollectionUtils.isEmpty(sysAreas)) {
//      for (SysArea sysArea : sysAreas) {
//        if (!StringUtil.isNullOrEmpty(sysArea.getDeviceId())) {
//          deviceIdForAreaIdMap.put(sysArea.getDeviceId(), sysArea.getId());
//        }
//        // 每个区域对应绑定管理员的id
//        userIdList = areaIdForUserIdMap.get(sysArea.getId());
//        if (CollectionUtils.isEmpty(userIdList)) {
//          userIdList = Lists.newArrayList();
//        }
//        userIdList.add(sysArea.getManagerId());
//        areaIdForUserIdMap.put(sysArea.getId(), userIdList);
//      }
//    }
//
//    // 缓存userId和phoneNo的map
//    List<SysUser> sysUsers = sysUserService.findAll();
//    Map<String, String> userIdForPhoneNoMap = Maps.newHashMap();
//    if (!CollectionUtils.isEmpty(sysUsers)) {
//      for (SysUser sysUser : sysUsers) {
//        if (!StringUtil.isNullOrEmpty(sysUser.getPhone())) {
//          userIdForPhoneNoMap.put(sysUser.getId(), sysUser.getPhone());
//        }
//      }
//    }
//
//    // deviceId对应的电话号码的集合
//    String areaId = null;
//    String phoneNo = null;
//    List<String> phones = null;
//    List<String> userIdlist = null;
//    for (String deviceId : deviceIdForAreaIdMap.keySet()) {
//      phones = Lists.newArrayList();
//      userIdlist = Lists.newArrayList();
//      areaId = deviceIdForAreaIdMap.get(deviceId);
//      userIdList = areaIdForUserIdMap.get(areaId);
//      for (String userId : userIdList) {
//        phoneNo = userIdForPhoneNoMap.get(userId);
//        phones.add(phoneNo);
//        userIdlist.add(userId);
//      }
//      StringBuilder phoneNos = new StringBuilder();
//      if (!CollectionUtils.isEmpty(phones)) {
//        for (int i = 0; i < phones.size(); i++) {
//          if (i == phones.size() - 1) {
//            phoneNos.append(phones.get(i));
//          } else {
//            phoneNos.append(phones.get(i));
//            phoneNos.append(",");
//          }
//        }
//      }
//      deviceIdForUserId.put(deviceId, userIdlist);
//      stringRedisTemplate.opsForValue().set(DEVICE_PHONE + deviceId, phoneNos.toString());
//    }
//
//  }
//
//}
