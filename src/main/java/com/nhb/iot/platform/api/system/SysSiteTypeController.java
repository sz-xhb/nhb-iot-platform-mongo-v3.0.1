package com.nhb.iot.platform.api.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nhb.iot.platform.dataaccess.service.system.SysSiteTypeService;
import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
import com.nhb.iot.platform.entity.system.SysSiteType;
import com.nhb.iot.platform.entity.system.SysUser;
import com.nhb.iot.platform.enums.SysUserRoleEnum;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/sys/sitetype")
public class SysSiteTypeController {

  @Autowired
  private SysSiteTypeService sysSiteTypeService;

  @Autowired
  private SysUserService sysUserService;

  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "新增站址类型", notes = "站址类型")
  @RequestMapping(value = "save", method = {RequestMethod.POST})
  public RestResultDto save(@RequestBody SysSiteType sysSiteType) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String userId = sysSiteType.getUserId();
      String siteTypeCode = sysSiteType.getSiteCode();
      if (StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("用户id不能为空！");
      }
      SysUser sysUser = sysUserService.findById(userId);
      if (!sysUser.getRole().equals(SysUserRoleEnum.ADMIN.getKey())) {
        throw new Exception("您没有该权限！");
      }
      List<SysSiteType> siteTypes =
          sysSiteTypeService.findBySiteCodeAndUserId(siteTypeCode, userId);
      if (!CollectionUtils.isEmpty(siteTypes)) {
        throw new Exception("该编号已经存在！");
      }
      sysSiteType = sysSiteTypeService.save(sysSiteType);
      data = sysSiteType;
      msg = "站址类型保存成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "站址类型保存失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  /**
   * 
   * @Title: findById
   * @Description: 根据id查询
   * @return RestResultDto
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "根据id查询", notes = "根据id查询")
  @RequestMapping(value = "findById", method = {RequestMethod.POST})
  public RestResultDto findById(@RequestBody SysSiteType sysSiteType) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String id = sysSiteType.getId();
      sysSiteType = sysSiteTypeService.findById(id);
      data = sysSiteType;
      msg = "站址类型查询成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "站址类型查询失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  /**
   * 
   * @Title: deleteById
   * @Description: 根据id删除
   * @return RestResultDto
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "根据id删除", notes = "根据id删除")
  @RequestMapping(value = "deleteById", method = {RequestMethod.POST})
  public RestResultDto deleteById(@RequestBody SysSiteType sysSiteType) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String userId = sysSiteType.getUserId();
      String id = sysSiteType.getId();
      if (StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("用户id不能为空！");
      }
      if (StringUtil.isNullOrEmpty(id)) {
        throw new Exception("id不能为空！");
      }
      SysSiteType type = sysSiteTypeService.findById(id);
      if (!type.getUserId().equals(userId)) {
        throw new Exception("对不起，您没有权限进行该操作！");
      }
      sysSiteTypeService.deleteById(id);
      data = true;
      msg = "站址类型查询成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "站址类型查询失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  /**
   * 
   * @Title: findAll
   * @Description: 查询所有站址类型
   * @return RestResultDto
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "查询所有站址类型", notes = "userId")
  @RequestMapping(value = "findAll", method = {RequestMethod.POST})
  public RestResultDto findAll(@RequestBody SysSiteType sysSiteType) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String userId = sysSiteType.getUserId();
      if (StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("用户id不能未空！");
      }
      List<SysSiteType> sysSiteTypes = sysSiteTypeService.findByUserId(userId);
      data = sysSiteTypes;
      msg = "站址类型查询成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "站址类型查询失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

}
