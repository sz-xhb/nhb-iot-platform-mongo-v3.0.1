package com.nhb.iot.platform.api.device;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.nhb.iot.platform.dataaccess.service.data.DataSwitchService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
import com.nhb.iot.platform.entity.data.DataSwitch;
import com.nhb.iot.platform.entity.device.ReceiptDevice;
import com.nhb.iot.platform.entity.system.SysUser;
import com.nhb.iot.platform.enums.SwitchIsCtrl;
import com.nhb.iot.platform.feign.RemoteService;
import com.nhb.iot.platform.request.device.ControlRequest;
import com.nhb.iot.platform.request.system.SysUserRequest;
import com.nhb.iot.platform.websocket.WebSocketServer;
import com.nhb.iot.platform.websocket.response.WebSockerResponse;
import com.nhb.iot.platform.websocket.response.WebSocketTypeEnum;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;
import io.swagger.annotations.ApiOperation;

/**
 * @author XS guo
 * @ClassName: RemoteDevice
 * @Description: 远程控制接口
 * @date 2017年9月21日 下午8:36:26
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping("api/v1/remote")
public class RemoteDevice {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private DataSwitchService dataSwitchService;

  @Autowired
  private RemoteService remoteService;

  @Autowired
  private CommonService commonService;

  @Autowired
  private ReceiptDeviceService receiptDeviceService;

  /**
   * 用于存储Device 和 user对应关系
   */
  Map<String, String> deviceUserMap = Maps.newHashMap();

  WebSockerResponse webSockerResponse;

  /**
   * @return RestResultDto
   * @Title: findRemoteDevices
   * @Description: 根据用户id查询其下属可进行远程控制的设备列表
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "远程控制列表", notes = "必传：id")
  @RequestMapping(value = "findRemoteDevices", method = {RequestMethod.POST})
  public RestResultDto findRemoteDevices(@RequestBody SysUserRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String userId = request.getId();
      if (StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("用户id不能为空！");
      }
      // 总条数
      Long total = 0L;
      // 页码总数
      Integer totalPage = 0;
      // 查询当前用户
      SysUser sysUser = sysUserService.findById(userId);
      if (null == sysUser) {
        throw new Exception("当前用户为空！");
      }
      // 用于存储查询到有效的设备列表
      List<String> tenantIds = Lists.newArrayList();
      Pageable pageable = PageRequest.of(request.getPage() - 1, request.getRows());
      tenantIds = commonService.findSubIdsByTenantId(userId);

      // 返回结果
      Map<String, Object> returnValue = Maps.newHashMap();
      List<ReceiptDevice> listValue = Lists.newArrayList();

      List<String> deviceIds = Lists.newArrayList();

      deviceIds = commonService.findDeviceIdsIdsByByTenantIds(tenantIds);
      // 根据设备Id，查询下属说有的可控制的设备列表
      Page<ReceiptDevice> ctrlDevices = receiptDeviceService
          .findByIsCtrlAndIdIn(SwitchIsCtrl.IsCtrl.isValue(), deviceIds, pageable);
      listValue = ctrlDevices.getContent();
      returnValue.put("total", total);
      returnValue.put("totalPage", totalPage);
      returnValue.put("currPage", request.getPage());
      returnValue.put("rows", listValue);
      data = returnValue;
      msg = "获取远程控制设备列表成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "获取远程控制设备列表失败！";
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
   * @Title: findRemoteDevices
   * @Description: 根据用户id查询其下属可进行远程控制的设备列表
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "远程控制", notes = "必传：deviceId，userId，type")
  @RequestMapping(value = "control", method = {RequestMethod.POST})
  @SystemLog(module = "DEVICE", methods = "DEVICE_REMOTE_CONTROL")
  public RestResultDto control(@RequestBody ControlRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String userId = request.getUserId();
      String deviceId = request.getDeviceId();
      String type = request.getType();
      if (StringUtil.isNullOrEmpty(type) || StringUtil.isNullOrEmpty(deviceId)
          || StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("必要参数不能为空！");
      }
      // 保存map
      deviceUserMap.put(deviceId, userId);

      // 调用采集软件接口
      Map<String, Object> param = Maps.newHashMap();
      param.put("deviceId", deviceId);
      param.put("type", type);

      RestResultDto dto = remoteService.remoteDevice(param);
      result = dto.getResult();
      data = dto.getData();
      msg = dto.getMsg();
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = false;
      msg = "远程控制命令下发失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  /**
   * @return void
   * @Title: pushMessageToClient
   * @Description: 远程控制返回结果
   */
  @ApiOperation(value = "远程控制回调", notes = "采集软件远程控制回调")
  @RequestMapping(value = "push", method = {RequestMethod.POST})
  public void pushMessageToClient(ControlRequest request) {
    String userId = deviceUserMap.get(request.getDeviceId());
    webSockerResponse = new WebSockerResponse();
    webSockerResponse.setInfoType(WebSocketTypeEnum.REMOTE_CONTROL.getKey());
    webSockerResponse.setData(request);
    WebSocketServer.pushMessageToUser(userId, webSockerResponse);

    DataSwitch switchStatus = new DataSwitch();
    switchStatus.setDeviceId(request.getDeviceId());
    switchStatus.setReadTime(new Date());
    switchStatus.setStatus(request.getType());
    // 控制之后，更新表格中的状态
    dataSwitchService.save(switchStatus);
  }

  /**
   * @return RestResultDto
   * @Title: getRealTimeData
   * @Description: 根据DeviceId抄读其实时数据
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "抄读实时数据", notes = "参数：deviceId")
  @RequestMapping(value = "getRealtimeData", method = {RequestMethod.POST})
  @SystemLog(module = "DEVICE", methods = "DEVICE_REMOTE_CONTROL")
  public RestResultDto getRealtimeData(@RequestBody ControlRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String deviceId = request.getDeviceId();
      if (StringUtil.isNullOrEmpty(deviceId)) {
        throw new Exception("必要参数不能为空！");
      }

      // 调用采集软件接口
      Map<String, Object> param = Maps.newHashMap();
      param.put("deviceId", deviceId);

      RestResultDto dto = remoteService.getRealtimeData(param);
      result = dto.getResult();
      data = dto.getData();
      msg = dto.getMsg();
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = false;
      msg = "抄读实时数据失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

}
