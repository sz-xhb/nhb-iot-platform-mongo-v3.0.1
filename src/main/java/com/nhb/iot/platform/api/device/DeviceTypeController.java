package com.nhb.iot.platform.api.device;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nhb.iot.platform.dataaccess.service.device.DeviceTypeService;
import com.nhb.iot.platform.entity.device.DeviceType;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/devicetype")
public class DeviceTypeController {

  @Autowired
  private DeviceTypeService deviceTypeService;

  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "新增设备类型", notes = "设备类型")
  @RequestMapping(value = "save", method = {RequestMethod.POST})
  public RestResultDto save(@RequestBody DeviceType deviceType) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String userId = deviceType.getUserId();
//      String deviceTypeCode = deviceType.getDeviceTypeCode();
      if (StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("用户id不能为空！");
      }
//      SysUser sysUser = sysUserService.findById(userId);
//      if (SysUserRoleEnum.USER.getKey().equals(sysUser.getRole())) {
//        throw new Exception("您没有该权限！");
//      }
//      List<DeviceType> types =
//          deviceTypeService.findByDeviceTypeCodeAndUserId(deviceTypeCode, userId);
//      if (!CollectionUtils.isEmpty(types)) {
//        throw new Exception("该设备型号已经存在！");
//      }

      deviceType = deviceTypeService.save(deviceType);
      data = deviceType;
      msg = "设备类型保存成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "设备类型保存失败！";
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
   * @Title: findById
   * @Description: 根据id查询
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "根据id查询", notes = "根据id查询")
  @RequestMapping(value = "findById", method = {RequestMethod.POST})
  public RestResultDto findById(@RequestBody DeviceType deviceType) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String id = deviceType.getId();
      deviceType = deviceTypeService.findById(id);
      data = deviceType;
      msg = "设备类型查询成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "设备类型查询失败！";
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
   * @Title: deleteById
   * @Description: 根据id删除
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "根据id删除", notes = "根据id删除")
  @RequestMapping(value = "deleteById", method = {RequestMethod.POST})
  public RestResultDto deleteById(@RequestBody DeviceType deviceType) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String userId = deviceType.getUserId();
      String id = deviceType.getId();
      if (StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("用户id不能为空！");
      }
      if (StringUtil.isNullOrEmpty(id)) {
        throw new Exception("id不能为空！");
      }
      DeviceType type = deviceTypeService.findById(id);
      if (!type.getUserId().equals(userId)) {
        throw new Exception("对不起，您没有权限进行该操作！");
      }
      deviceTypeService.deleteById(id);
      data = true;
      msg = "删除成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "删除失败！";
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
   * @Title: findAll
   * @Description: 查询所有设备类型
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "查询所有设备类型", notes = "查询所有设备类型")
  @RequestMapping(value = "findAll", method = {RequestMethod.POST})
  public RestResultDto findAll(@RequestBody DeviceType deviceType) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {

      String userId = deviceType.getUserId();
      if (StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("用户id不能未空！");
      }

      // 如果不是管理员，可以查看到父节点账号的设备类型
//      SysUser sysUser = sysUserService.findById(userId);
//      if (!sysUser.getRole().equals(SysUserRoleEnum.ADMIN.getKey())) {
//        userId = sysUser.getParentId();
//      }

      List<DeviceType> deviceTypes = deviceTypeService.findAll();
      data = deviceTypes;
      msg = "设备类型查询成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "设备类型查询失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

}
