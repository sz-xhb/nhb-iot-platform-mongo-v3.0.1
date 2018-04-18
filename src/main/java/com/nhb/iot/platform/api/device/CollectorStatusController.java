//package com.nhb.iot.platform.api.device;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.nhb.iot.platform.dataaccess.service.device.CollectorStatusService;
//import com.nhb.iot.platform.dataaccess.service.device.ReceiptCollectorService;
//import com.nhb.iot.platform.dataaccess.service.system.SysUserCollectorService;
//import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
//import com.nhb.iot.platform.entity.device.CollectorStatus;
//import com.nhb.iot.platform.entity.device.ReceiptCollector;
//import com.nhb.iot.platform.entity.system.SysUser;
//import com.nhb.iot.platform.entity.system.SysUserCollector;
//import com.nhb.iot.platform.enums.SysUserRoleEnum;
//import com.nhb.iot.platform.request.device.ReceiptCollectorRequest;
//import com.nhb.utils.nhb_utils.common.RestResultDto;
//import com.nhb.utils.nhb_utils.common.StringUtil;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("api/v1/collector/status")
//public class CollectorStatusController {
//
//  @Autowired
//  private CollectorStatusService collectorStatusService;
//  @Autowired
//  private ReceiptCollectorService receiptCollectorService;
//  @Autowired
//  private SysUserService sysUserService;
//  @Autowired
//  private SysUserCollectorService sysUserCollectorService;
//
//  @SuppressWarnings({"rawtypes", "unchecked"})
//  @ApiOperation(value = "根据用户查询下属采集器在线状态", notes = "根据用户查询下属采集器在线状态")
//  @RequestMapping(value = "findCollectorStatusList", method = {RequestMethod.POST})
//  public RestResultDto findCollectorStatusList(@RequestBody ReceiptCollectorRequest request) {
//    RestResultDto resultDto = new RestResultDto();
//    Integer result = RestResultDto.RESULT_SUCC;
//    String msg = null;
//    Object data = null;
//    String exception = null;
//    try {
//      String ownerId = request.getOwnerId();
//      if (StringUtil.isNullOrEmpty(ownerId)) {
//        throw new Exception("用户id不能为空！");
//      }
//      SysUser sysUser = sysUserService.findById(ownerId);
//      if (null == sysUser) {
//        throw new Exception("当前用户不存在！");
//      }
//      Pageable pageable = PageRequest.of(request.getPage() - 1, request.getRows());
//      List<ReceiptCollector> collectors = Lists.newArrayList();
//      Long total = 0L;
//      Integer totalPage = 0;
//      if (sysUser.getRole().equals(SysUserRoleEnum.SUPERADMIN.getKey())) {
//        Page<ReceiptCollector> pageList = receiptCollectorService.findAll(pageable);
//        if (null != pageList) {
//          collectors = pageList.getContent();
//          total = pageList.getTotalElements();
//          totalPage = pageList.getTotalPages();
//        }
//      } else if (sysUser.getRole().equals(SysUserRoleEnum.ADMIN.getKey())) {
//        Page<ReceiptCollector> pageList =
//            receiptCollectorService.findByManagerId(ownerId, pageable);
//        if (null != pageList) {
//          collectors = pageList.getContent();
//          total = pageList.getTotalElements();
//          totalPage = pageList.getTotalPages();
//        }
//      } else {
//        Page<SysUserCollector> userCollectors =
//            sysUserCollectorService.findByUserId(ownerId, pageable);
//        List<String> collectorIds = Lists.newArrayList();
//        if (null != userCollectors) {
//          for (SysUserCollector collector : userCollectors) {
//            collectorIds.add(collector.getCollectorId());
//          }
//          collectors = receiptCollectorService.findByIds(collectorIds);
//          total = userCollectors.getTotalElements();
//          totalPage = userCollectors.getTotalPages();
//        }
//      }
//      Map<String, Object> returnValue = Maps.newHashMap();
//      List<Object> listvalue = Lists.newArrayList();
//      Map<String, Object> mapValue = null;
//      if (!CollectionUtils.isEmpty(collectors)) {
//        String[] arrayIds = new String[collectors.size()];
//        for (int i = 0; i < collectors.size(); i++) {
//          arrayIds[i] = collectors.get(i).getId();
//        }
//        List<CollectorStatus> statusList = collectorStatusService.findByCollectorIdIn(arrayIds);
//        for (ReceiptCollector collector : collectors) {
//          mapValue = Maps.newHashMap();
//          mapValue.put("collectorId", collector.getId());
//          mapValue.put("collectorNo", collector.getCollectorNo());
//          mapValue.put("name", collector.getName());
//          if (!CollectionUtils.isEmpty(statusList)) {
//            for (CollectorStatus status : statusList) {
//              if (status.getCollectorId().equals(collector.getId())) {
//                mapValue.put("status",
//                    new Date().getTime() - status.getActiveTime().getTime() > 10 * 60 * 1000 ? "离线"
//                        : "在线");
//              }
//            }
//          } else {
//            mapValue.put("status", "未上线");
//          }
//          listvalue.add(mapValue);
//        }
//
//      }
//      returnValue.put("total", total);
//      returnValue.put("totalPage", totalPage);
//      returnValue.put("currPage", request.getPage());
//      returnValue.put("rows", listvalue);
//
//      data = returnValue;
//      msg = "采集器查询成功！";
//    } catch (Exception e) {
//      result = RestResultDto.RESULT_FAIL;
//      exception = e.getMessage();
//      data = null;
//      msg = "采集器查询失败！";
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
