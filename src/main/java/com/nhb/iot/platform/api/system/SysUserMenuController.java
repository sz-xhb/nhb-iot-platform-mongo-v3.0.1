//package com.nhb.iot.platform.api.system;
//
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import com.google.common.collect.Lists;
//import com.nhb.iot.platform.dataaccess.service.system.SysUserMenuService;
//import com.nhb.iot.platform.entity.system.SysUserMenu;
//import com.nhb.utils.nhb_utils.common.RestResultDto;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("api/v1/sys/menu")
//@Api(value = "用户菜单权限")
//public class SysUserMenuController {
//
//  @Autowired
//  private SysUserMenuService sysUserMenuService;
//
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  @ApiOperation(value = "根据用户id获取菜单", notes = "传参：id")
//  @RequestMapping(value = "findByUserId", method = {RequestMethod.POST})
//  public RestResultDto findByUserId(@RequestBody SysUserMenu request) {
//    RestResultDto resultDto = new RestResultDto();
//    Integer result = RestResultDto.RESULT_SUCC;
//    String msg = null;
//    Object data = null;
//    String exception = null;
//    try {
//      List<Integer> returnValue = Lists.newArrayList();
//      SysUserMenu sysUserMenu = sysUserMenuService.findByUserId(request.getUserId());
//      if (sysUserMenu != null) {
//        returnValue = sysUserMenu.getMenu();
//      }
//      data = returnValue;
//      msg = "查询用户菜单权限成功";
//    } catch (Exception e) {
//      exception = e.getMessage();
//      result = RestResultDto.RESULT_FAIL;
//      msg = "查询用户菜单权限失败";
//    } finally {
//      resultDto.setData(data);
//      resultDto.setException(exception);
//      resultDto.setMsg(msg);
//      resultDto.setResult(result);
//    }
//    return resultDto;
//  }
//
//  @SuppressWarnings({"unchecked", "rawtypes"})
//  @ApiOperation(value = "保存用户菜单", notes = "传参：userId,menu")
//  @RequestMapping(value = "save", method = {RequestMethod.POST})
//  public RestResultDto save(@RequestBody SysUserMenu request) {
//    RestResultDto resultDto = new RestResultDto();
//    Integer result = RestResultDto.RESULT_SUCC;
//    String msg = null;
//    Object data = null;
//    String exception = null;
//    try {
//
//      SysUserMenu userMenu = sysUserMenuService.saveOrUpdate(request);
//      data = userMenu;
//      msg = "保存用户菜单权限成功";
//    } catch (Exception e) {
//      exception = e.getMessage();
//      result = RestResultDto.RESULT_FAIL;
//      msg = "保存用户菜单权限失败";
//    } finally {
//      resultDto.setData(data);
//      resultDto.setException(exception);
//      resultDto.setMsg(msg);
//      resultDto.setResult(result);
//    }
//    return resultDto;
//
//  }
//
//}
