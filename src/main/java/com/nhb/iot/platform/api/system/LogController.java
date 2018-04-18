package com.nhb.iot.platform.api.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.dataaccess.service.system.LogEntityService;
import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
import com.nhb.iot.platform.entity.system.LogEntity;
import com.nhb.iot.platform.entity.system.SysUser;
import com.nhb.iot.platform.enums.SystemLogMethodsTypeEnum;
import com.nhb.iot.platform.enums.SystemLogMoudleTypeEnum;
import com.nhb.iot.platform.request.system.SysLogRequest;
import com.nhb.utils.nhb_utils.common.DateUtil;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;
import io.swagger.annotations.ApiOperation;

/**
 * @author XS guo
 * @ClassName: LogController
 * @Description: 操作日志查询
 * @date 2017年9月28日 上午9:54:24
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping("api/v1/sys/log")
public class LogController {

  @Autowired
  private LogEntityService logEntityService;

  @Autowired
  private SysUserService sysUserService;

  /**
   * @return RestResultDto
   * @Title: getLogType
   * @Description: 获取操作日志类型
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "获取操作日志类型", notes = "获取操作日志类型")
  @RequestMapping(value = "getLogType", method = {RequestMethod.POST})
  public RestResultDto getLogType() {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {

      Map<String, Object> returnValue = Maps.newHashMap();

      List<Object> listValue = Lists.newArrayList();
      Map<String, Object> mapValue = null;
      for (SystemLogMoudleTypeEnum value : SystemLogMoudleTypeEnum.values()) {
        mapValue = Maps.newHashMap();
        mapValue.put("key", value.getKey());
        mapValue.put("value", value.getValue());
        listValue.add(mapValue);
      }
      returnValue.put("moudle", listValue);
      listValue = Lists.newArrayList();
      for (SystemLogMethodsTypeEnum value : SystemLogMethodsTypeEnum.values()) {
        mapValue = Maps.newHashMap();
        mapValue.put("key", value.getKey());
        mapValue.put("value", value.getValue());
        listValue.add(mapValue);
      }
      returnValue.put("methods", listValue);
      data = returnValue;
      msg = "类型查询成功！";

    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = false;
      msg = "用户绑定区域失败！";
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
   * @Title: findLog
   * @Description: 查询日志
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "查询日志", notes = "查询日志")
  @RequestMapping(value = "findLog", method = {RequestMethod.POST})
  public RestResultDto findLog(@RequestBody SysLogRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {

      Map<String, Object> returnValue = Maps.newHashMap();
      List<LogEntity> list = Lists.newArrayList();
      Long total = 0L;
      Integer totalPage = 0;

      String userId = request.getUserId();
      if (StringUtil.isNullOrEmpty(userId)) {
        throw new Exception("用户id不能为空！");
      }
      String module = request.getModule();
      String methods = request.getMethods();
      Date startTime = DateUtil.parse(request.getStartTime());
      Date endTime = DateUtil.parse(request.getEndTime());
      String commit = request.getCommit();
      Sort sort = new Sort(Direction.DESC, "date");
      Pageable pageable = PageRequest.of(request.getPage() - 1, request.getRows(), sort);

      List<SysUser> sysUsers = sysUserService.findByParentId(userId);
      SysUser cUser = sysUserService.findById(userId);
      sysUsers.add(cUser);

      List<String> userIds = Lists.newArrayList();
      Map<String, String> userIdForName = Maps.newHashMap();
      if (!CollectionUtils.isEmpty(sysUsers)) {
        for (SysUser user : sysUsers) {
          userIds.add(user.getId());
          userIdForName.put(user.getId(), user.getName());
        }
      }
      userIds.add(userId);

      Page<LogEntity> pageList =
          logEntityService.findByUserIdInAndModuleAndMethodAndCommitAndDateBetween(userIds, module,
              methods, commit, startTime, endTime, pageable);

      if (pageList != null) {
        list = pageList.getContent();
        total = pageList.getTotalElements();
        totalPage = pageList.getTotalPages();
      }

      List<Object> listValue = Lists.newArrayList();
      Map<String, Object> mapValue = null;
      if (!CollectionUtils.isEmpty(list)) {
        for (LogEntity entity : list) {
          mapValue = Maps.newHashMap();
          mapValue.put("id", entity.getId());
          mapValue.put("userId", entity.getUserId());
          mapValue.put("userName", userIdForName.get(entity.getUserId()));
          mapValue.put("date", DateUtil.format(entity.getDate()));
          mapValue.put("commit", entity.getCommit());
          mapValue.put("module", entity.getModule());
          mapValue.put("moduleStr", SystemLogMoudleTypeEnum.getValueByKey(entity.getModule()));
          mapValue.put("method", entity.getMethod());
          mapValue.put("methodStr", SystemLogMethodsTypeEnum.getValueByKey(entity.getMethod()));
          mapValue.put("ip", entity.getIp());
          listValue.add(mapValue);
        }
      }

      returnValue.put("total", total);
      returnValue.put("totalPage", totalPage);
      returnValue.put("currPage", request.getPage());
      returnValue.put("rows", listValue);
      data = returnValue;
      msg = "查询日志成功！";

    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = false;
      msg = "查询日志失败！";
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
   * @Title: findLog
   * @Description: 查询日志
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "查询日志", notes = "查询日志")
  @RequestMapping(value = "find", method = {RequestMethod.POST})
  public RestResultDto find(@RequestBody SysLogRequest request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {

    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = false;
      msg = "查询日志失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

}
