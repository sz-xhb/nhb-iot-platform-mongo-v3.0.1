package com.nhb.iot.platform.api.system;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nhb.iot.platform.api.inface.SystemLog;
import com.nhb.iot.platform.dataaccess.service.system.UnitRateService;
import com.nhb.iot.platform.entity.system.UnitRate;
import com.nhb.utils.nhb_utils.common.DateUtil;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/unit/rate")
public class UnitRateController {

  @Autowired
  private UnitRateService unitRateService;

  /**
   * 
   * @Title: save
   * @Description: 更新费率
   * @return RestResultDto
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "费率管理", notes = "费率管理")
  @RequestMapping(value = "save", method = {RequestMethod.POST})
  @SystemLog(module = "SYSTEM", methods = "SYSTEM_UPDATE_UNITRATE")
  public RestResultDto save(@RequestBody UnitRate request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {

      String areaId = request.getAreaId();
      Double electricity = request.getElectricity();
      Double water = request.getWater();
      Double gas = request.getGas();
      Double heat = request.getHeat();

      Date nowTime = new Date();
      // 当天
      String today = DateUtil.format(nowTime, DateUtil.DATE_FORMAT);
      // 昨天
      String lastDay = DateUtil.format(DateUtils.addDays(nowTime, -1), DateUtil.DATE_FORMAT);

      // 排序
      Sort sort = new Sort(Direction.DESC, "endDate");

      // 列表
      List<UnitRate> rates = unitRateService
          .findByAreaIdAndStartDateLessThanAndEndDateGreaterThan(areaId, today, today, sort);
      UnitRate rate = null;
      if (!CollectionUtils.isEmpty(rates)) {
        rate = rates.get(0);
      }

      String maxDate = "2099-12-31";

      if (null != rate) {
        rate.setEndDate(lastDay);
        unitRateService.saveOrUpdate(rate);
      }

      UnitRate newRate = new UnitRate();
      newRate.setAreaId(areaId);
      newRate.setElectricity(electricity);
      newRate.setWater(water);
      newRate.setGas(gas);
      newRate.setHeat(heat);
      newRate.setStartDate(today);
      newRate.setEndDate(maxDate);
      unitRateService.saveOrUpdate(newRate);

      data = true;
      msg = "设置成功！";

    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "设置失败！";
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
   * @Title: findRateByAreaId
   * @Description: 根据区域id获取费率列表
   * @return RestResultDto
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @ApiOperation(value = "根据区域id获取费率列表", notes = "根据区域id获取费率列表")
  @RequestMapping(value = "findRateByAreaId", method = {RequestMethod.POST})
  public RestResultDto findRateByAreaId(@RequestBody UnitRate request) {
    RestResultDto resultDto = new RestResultDto();
    Integer result = RestResultDto.RESULT_SUCC;
    String msg = null;
    Object data = null;
    String exception = null;
    try {
      String areaId = request.getAreaId();
      Sort sort = new Sort(Direction.DESC, "endTime");
      List<UnitRate> rates = unitRateService.findByAreaId(areaId, sort);
      data = rates;
      msg = "查询成功！";

    } catch (Exception e) {
      result = RestResultDto.RESULT_FAIL;
      exception = e.getMessage();
      data = null;
      msg = "获取失败！";
    } finally {
      resultDto.setData(data);
      resultDto.setException(exception);
      resultDto.setMsg(msg);
      resultDto.setResult(result);
    }
    return resultDto;
  }

}
