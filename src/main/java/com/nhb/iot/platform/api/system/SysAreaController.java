package com.nhb.iot.platform.api.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.nhb.iot.platform.api.inface.SystemLog;
import com.nhb.iot.platform.dataaccess.service.common.CommonService;
import com.nhb.iot.platform.dataaccess.service.system.SysAreaService;
import com.nhb.iot.platform.entity.system.SysArea;
import com.nhb.iot.platform.request.system.SysAreaRequest;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @author XS guo
 * @ClassName: SysAreaController
 * @Description: 区域信息Api
 * @date 2017年9月21日 下午8:47:56
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping("api/v1/sys/area")
public class SysAreaController {

	@Autowired
	private SysAreaService sysAreaService;

	@Autowired
	private CommonService commonService;

	/**
	 * @return RestResultDto
	 * @Title: save
	 * @Description: 保存区域信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "区域信息保存", notes = "区域信息保存")
	@RequestMapping(value = "save", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_ADD_AREA")
	public RestResultDto save(@RequestBody SysArea sysArea) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			Date nowTIme = new Date();
			sysArea.setCreateTime(nowTIme);
			SysArea area = sysAreaService.save(sysArea);
			msg = "区域信息保存成功！";
			data = area;
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "区域信息保存失败！";
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
	 * @Title: findList
	 * @Description: 根据用户id查询下属区域列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "查询用户名下顶级的区域列表", notes = "查询用户名下区域列表")
	@RequestMapping(value = "findList", method = { RequestMethod.POST })
	public RestResultDto findList(@RequestBody SysAreaRequest request) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			List<SysArea> sysAreas = Lists.newArrayList();
			String tenantId = request.getTenantId();
			if (StringUtil.isNullOrEmpty(tenantId)) {
				throw new Exception("租户id不能为空！");
			}
			List<String> tenantIds = commonService.findSubIdsByTenantId(tenantId);
			sysAreas = sysAreaService.findByTenantIdIn(tenantIds);
			data = sysAreas;
			msg = "查询区域列表成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "查询区域列表失败！";
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
	 * @Title: findList
	 * @Description: 根据用户id查询下属区域列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "根据父级节点查询下属区域列表", notes = "parentId")
	@RequestMapping(value = "findAreaByparentId", method = { RequestMethod.POST })
	public RestResultDto findAreaByparentId(@RequestBody SysAreaRequest request) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String parentId = request.getParentId();
			if (StringUtil.isNullOrEmpty(parentId)) {
				throw new Exception("parentId不能为空！");
			}
			List<SysArea> sysAreas = sysAreaService.findByTenantId(parentId);
			data = sysAreas;
			msg = "查询区域列表成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "查询区域列表失败！";
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
	 * @Title: delete
	 * @Description:删除区域
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "删除区域", notes = "删除区域")
	@RequestMapping(value = "delete", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_DELETE_AREA")
	public RestResultDto delete(@RequestBody SysAreaRequest request) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String areaId = request.getId();
			if (null == areaId) {
				throw new Exception("区域id不能为空！");
			}
			List<SysArea> sysAreas = sysAreaService.findByParentId(areaId);
			if (!CollectionUtils.isEmpty(sysAreas)) {
				throw new Exception("请先删除下属节点区域！");
			}

			sysAreaService.deleteById(areaId);
			data = true;
			msg = "删除成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = false;
			msg = "删除失败失败！";
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
	 * @Title: findAreaBasicInfo
	 * @Description: 查询区域的基本信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "查询区域的基本信息", notes = "参数：areaId")
	@RequestMapping(value = "findAreaBasicInfo", method = { RequestMethod.POST })
	public RestResultDto findAreaBasicInfo(@RequestBody SysAreaRequest request) {
		RestResultDto resultDto = new RestResultDto();
		Integer result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {

			String areaId = request.getId();
			if (!StringUtil.isNullOrEmpty(areaId)) {
				throw new Exception("要查询的区域循序不能未空");
			}

			SysArea sysArea = sysAreaService.findById(areaId);
			data = sysArea;
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

}
