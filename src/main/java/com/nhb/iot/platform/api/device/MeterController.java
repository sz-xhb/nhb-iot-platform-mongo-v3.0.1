package com.nhb.iot.platform.api.device;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.api.inface.SystemLog;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptDeviceService;
import com.nhb.iot.platform.dataaccess.service.device.ReceiptMeterService;
import com.nhb.iot.platform.dataaccess.service.device.UtilProtocolTypeService;
import com.nhb.iot.platform.entity.device.ReceiptDevice;
import com.nhb.iot.platform.entity.device.ReceiptMeter;
import com.nhb.iot.platform.request.device.ReceiptMeterRequest;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/meter")
public class MeterController {

  private final ReceiptMeterService receiptMeterService;

  private final ReceiptDeviceService receiptDeviceService;

  private final UtilProtocolTypeService utilProtocolTypeService;

  /**
   * 建议构造函数注入
   * @param receiptMeterService 电表服务
   * @param receiptDeviceService 支路（设备）服务
   * @param utilProtocolTypeService 协议服务
   */
  @Autowired
  public MeterController(ReceiptMeterService receiptMeterService,
      ReceiptDeviceService receiptDeviceService, UtilProtocolTypeService utilProtocolTypeService) {
    this.receiptMeterService = receiptMeterService;
    this.receiptDeviceService = receiptDeviceService;
    this.utilProtocolTypeService = utilProtocolTypeService;
  }

  @ApiOperation(value = "新增电表", notes = "新增电表")
  @RequestMapping(value = "save", method = {RequestMethod.POST})
  @SystemLog(module = "DEVICE", methods = "DEVICE_ADD_METER")
  public RestResultDto<Object> save(@RequestBody ReceiptMeter receiptMeter) {
    RestResultDto<Object> resultDto = new RestResultDto<>();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      if (receiptMeter.getCollectorId() == null) {
        throw new Exception("采集器id不能为空！");
      }
      receiptMeter = receiptMeterService.saveOrUpdate(receiptMeter);
      data = receiptMeter;
      msg = "电表保存成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "电表保存失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  @ApiOperation(value = "修改电表", notes = "修改电表")
  @RequestMapping(value = "update", method = {RequestMethod.POST})
  @SystemLog(module = "DEVICE", methods = "DEVICE_UPDATE_METER")
  public RestResultDto<Object> update(@RequestBody ReceiptMeter receiptMeter) {
    RestResultDto<Object> resultDto = new RestResultDto<>();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      if (receiptMeter.getCollectorId() == null) {
        throw new Exception("采集器id不能为空！");
      }
      ReceiptMeter meter = receiptMeterService.findById(receiptMeter.getId());
      if(null == meter){
    	  throw new Exception("电表不存在！");
      }
      
      receiptMeter = receiptMeterService.saveOrUpdate(receiptMeter);
      data = receiptMeter;
      msg = "电表修改成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "采集器修改失败！";
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
   * Title: findListByCollectorId
   * Description: 根据id查询用户
   */
  @ApiOperation(value = "根据采集器id查询下属电表", notes = "根据采集器id查询下属电表")
  @RequestMapping(value = "findListByCollectorId", method = {RequestMethod.POST})
  public RestResultDto<Object> findListByCollectorId(@RequestBody ReceiptMeterRequest request) {
    RestResultDto<Object> resultDto = new RestResultDto<>();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      Map<String, Object> returnValue = Maps.newHashMap();
      List<ReceiptMeter> meters = Lists.newArrayList();
      Long total = 0L;
      Integer totalPage = 0;
      Sort sort = new Sort(Direction.DESC, "meterNo");
      Pageable pageable = PageRequest.of(request.getPage() - 1, request.getRows(), sort);
      Page<ReceiptMeter> pageList =
          receiptMeterService.findByCollectorId(request.getCollectorId(), pageable);
      if (pageList != null) {
        meters = pageList.getContent();
        total = pageList.getTotalElements();
        totalPage = pageList.getTotalPages();
      }
      returnValue.put("total", total);
      returnValue.put("totalPage", totalPage);
      returnValue.put("currPage", request.getPage());
      returnValue.put("rows", meters);
      data = returnValue;
      msg = "电表查询成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "电表查询失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  @ApiOperation(value = "删除电表", notes = "删除电表")
  @RequestMapping(value = "delete", method = {RequestMethod.POST})
  @SystemLog(module = "DEVICE", methods = "DEVICE_DELETE_METER")
  public RestResultDto<Object> delete(@RequestBody ReceiptMeterRequest request) {
    RestResultDto<Object> resultDto = new RestResultDto<>();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String meterId = request.getId();
      //查找电表下属支路信息
      List<ReceiptDevice> devices = receiptDeviceService.findByMeterId(meterId, null).getContent();
      //删除电表下支路信息
      if (!CollectionUtils.isEmpty(devices)) {
        for (ReceiptDevice device : devices) {
          receiptDeviceService.delete(device.getId());
        }
      }
      //删除电表信息
      receiptMeterService.delete(request.getId());
      data = true;
      msg = "电表及下属支路删除成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = false;
      msg = "电表删除失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

  @ApiOperation(value = "获取协议类型", notes = "获取协议类型")
  @RequestMapping(value = "findProtocolTypeList", method = {RequestMethod.POST})
  public RestResultDto<Object> findProtocolTypeList() {
    RestResultDto<Object> resultDto = new RestResultDto<>();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      data = utilProtocolTypeService.findAll();
      msg = "获取协议类型成功！";
    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = false;
      msg = "获取协议类型失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

}
