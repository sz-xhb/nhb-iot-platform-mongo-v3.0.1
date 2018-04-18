package com.nhb.iot.platform.api.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
import com.nhb.iot.platform.api.inface.SystemLog;
import com.nhb.iot.platform.dataaccess.service.system.SysUserService;
import com.nhb.iot.platform.entity.system.SysUser;
import com.nhb.iot.platform.enums.SysUserRoleEnum;
import com.nhb.iot.platform.request.system.SysUserRequest;
import com.nhb.iot.platform.util.EncryptionUtils;
import com.nhb.iot.platform.util.RedisUtils;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/sys/user")
@Api(value = "系统用户")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * @return RestResultDto Title: save Description: 保存用户信息
	 */
	@ApiOperation(value = "登出系统", notes = "用户名，密码")
	@RequestMapping(value = "logout", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_LOGOUT")
	public RestResultDto<Object> logout(@RequestBody SysUser sysUser) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {

			data = true;
			msg = "退出成功！";

		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "登陆失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto Title: save Description: 保存用户信息
	 */
	@ApiOperation(value = "登陆系统", notes = "用户名，密码")
	@RequestMapping(value = "login", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_LOGIN")
	public RestResultDto<Object> login(@RequestBody SysUser sysUser) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String loginName = sysUser.getLoginName();
			String password = sysUser.getPassword();
			if (StringUtil.isNullOrEmpty(loginName) || StringUtil.isNullOrEmpty(password)) {
				throw new Exception("用户名或者密码不能为空！");
			}
			SysUser user = sysUserService.findByLoginName(loginName);
			if (null == user || !user.getPassword().equals(EncryptionUtils.md5Hex(password))) {
				throw new Exception("用户名或者密码错误！");
			}
			// 更新登陆信息
			Date nowTime = new Date();
			user.setLastLoginTime(nowTime);
			user = sysUserService.saveOrUpdate(user);
			Map<String, Object> mapValue = Maps.newHashMap();
			// 保存token
			saveTokenInfo(user, mapValue);
			data = mapValue;
			msg = "登陆成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = false;
			msg = "登陆失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return void Title: saveTokenInfo Description: 设置TOKEN信息
	 */
	private void saveTokenInfo(SysUser user, Map<String, Object> mapValue) {
		String encryptMsg = "nhb" + user.getId() + user.getPassword();
		String token = EncryptionUtils.md5Hex(encryptMsg);
		RedisUtils.set(user.getId(), token, 0);
		RedisUtils.set("Authorization_Id", user.getId(), 0);
		Map<String, Object> loginMap = Maps.newHashMap();
		loginMap.put("uuid", user.getId());
		loginMap.put("token", token);
		loginMap.put("name", user.getName());
		loginMap.put("role", user.getRole());
		loginMap.put("loginName", user.getLoginName());
		loginMap.put("image", user.getImage());
		mapValue.put("loginMsg", loginMap);
	}

	/**
	 * @return RestResultDto Title: save Description: 保存用户信息
	 */
	@ApiOperation(value = "新增用户", notes = "必传：parentId")
	@RequestMapping(value = "save", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_ADD_USER")
	public RestResultDto<Object> save(@RequestBody SysUser sysUser) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			SysUser users = sysUserService.findByLoginName(sysUser.getLoginName());
			if (null != users) {
				throw new Exception("用户名已存在！");
			}
			// 查询其父节点
			SysUser parent = null;
			if (!StringUtil.isNullOrEmpty(sysUser.getParentId())) {
				parent = sysUserService.findById(sysUser.getParentId());
				if (parent.getRole().equals(SysUserRoleEnum.SUPERADMIN.getKey())) {
					sysUser.setRole(SysUserRoleEnum.ADMIN.getKey());
				} else {
					sysUser.setRole(SysUserRoleEnum.USER.getKey());
					sysUser.setImage(parent.getImage());
				}
			}
			Date nowTime = new Date();
			sysUser.setId(UUID.randomUUID().toString());
			// 初始密码123456
			sysUser.setPassword(EncryptionUtils.md5Hex("123456"));
			sysUser.setCreateTime(nowTime);
			// 设置权限
			if (null == parent) {
				sysUser.setParentId("0");
			}
			SysUser user = sysUserService.saveOrUpdate(sysUser);
			msg = "用户保存成功！";
			data = user;
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "用户保存失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto Title: resetPassword Description: 重置用户密码
	 */
	@ApiOperation(value = "重置用户密码", notes = "必传参数：id 和 parentId")
	@RequestMapping(value = "resetPassword", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_RESET_PASSWORD")
	public RestResultDto<Object> resetPassword(@RequestBody SysUserRequest request) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String parentId = request.getParentId();
			String id = request.getId();
			if (StringUtil.isNullOrEmpty(id) || StringUtil.isNullOrEmpty(parentId)) {
				throw new Exception("用户id不能为空");
			}
			SysUser parent = sysUserService.findById(parentId);
			SysUser son = sysUserService.findById(id);
			if (!parent.getId().equals(son.getParentId())) {
				throw new Exception("您没有权限重置该用户密码！");
			}
			son.setPassword(EncryptionUtils.md5Hex("123456"));
			sysUserService.saveOrUpdate(son);
			msg = "密码重置成功！";
			data = true;
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "密码重置失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto Title: updatePassword Description: 修改密码信息
	 */
	@ApiOperation(value = "修改密码信息", notes = "修改密码信息")
	@RequestMapping(value = "updatePassword", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_MODIFY_PASSWORD")
	public RestResultDto<Object> updatePassword(@RequestBody SysUserRequest request) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String userId = request.getId();
			String oldPass = request.getOldPass();
			String newPass = request.getNewPass();
			if (StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(newPass)
					|| StringUtil.isNullOrEmpty(oldPass)) {
				throw new Exception("必要参数不能为空！");
			}
			SysUser sysUser = sysUserService.findById(userId);
			if (!sysUser.getPassword().equals(EncryptionUtils.md5Hex(oldPass))) {
				throw new Exception("原密码错误！");
			}
			sysUser.setPassword(EncryptionUtils.md5Hex(newPass));
			Date nowTime = new Date();
			sysUser.setUpdateTime(nowTime);
			sysUser = sysUserService.saveOrUpdate(sysUser);
			msg = "密码修改成功！";
			data = sysUser;
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "密码修改失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto Title: update Description: 保存用户信息
	 */
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	@RequestMapping(value = "update", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_MODIFY_USER")
	public RestResultDto<Object> update(@RequestBody SysUser sysUser) {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			Date nowTime = new Date();
			SysUser user = sysUserService.findById(sysUser.getId());
			sysUser.setPassword(user.getPassword());
			sysUser.setUpdateTime(nowTime);
			sysUser.setCreateTime(user.getCreateTime());
			sysUser = sysUserService.saveOrUpdate(sysUser);
			// 判断是否需要更新下属子用户的图片
			if (!user.getImage().equals(sysUser.getImage())) {
				if (user.getRole().equals(SysUserRoleEnum.ADMIN.getKey())) {
					List<SysUser> childUsers = sysUserService.findByParentId(user.getId());
					for (SysUser chuser : childUsers) {
						chuser.setImage(sysUser.getImage());
						sysUserService.saveOrUpdate(chuser);
					}
				}
			}
			msg = "用户修改成功！";
			data = sysUser;
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "用户修改失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto Title: findList Description: 条件查询用户列表
	 */
	@ApiOperation(value = "查询用户列表", notes = "查询用户列表")
	@RequestMapping(value = "findList", method = { RequestMethod.POST })
	public RestResultDto<Object> findList(@RequestBody SysUserRequest sysUserRequest) throws Exception {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			String tenantId = sysUserRequest.getId();
			if (StringUtil.isNullOrEmpty(tenantId)) {
				throw new Exception("用户id不能为空！");
			}
			SysUser sysUser = sysUserService.findById(tenantId);
			Map<String, Object> returnValue = Maps.newHashMap();
			long total = 0L;
			int totalPage = 0;
			List<SysUser> users = Lists.newArrayList();

			Sort sort = new Sort(Direction.DESC, "createTime");
			Pageable pageable = PageRequest.of(sysUserRequest.getPage() - 1, sysUserRequest.getRows(), sort);
			// 查询下属用户的列表信息
			Page<SysUser> pageList = sysUserService.findByParentId(sysUser.getId(), pageable);

			if (pageList != null) {
				users = pageList.getContent();
				total = pageList.getTotalElements();
				totalPage = pageList.getTotalPages();
			}
			returnValue.put("total", total);
			returnValue.put("totalPage", totalPage);
			returnValue.put("currPage", sysUserRequest.getPage());
			returnValue.put("rows", users);
			data = returnValue;
			msg = "查询用户列表成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "查询用户列表失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto Title: findById Description: 根据id查询用户
	 */
	@ApiOperation(value = "根据用户id获取用户信息", notes = "传参：id")
	@RequestMapping(value = "findById", method = { RequestMethod.POST })
	public RestResultDto<Object> findById(@RequestBody SysUserRequest sysUserRequest) throws Exception {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {
			SysUser sysUser = sysUserService.findById(sysUserRequest.getId());
			data = sysUser;
			msg = "根据id查询用户成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = null;
			msg = "根据id查询用户失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

	/**
	 * @return RestResultDto Title: delete Description: 删除
	 */
	@ApiOperation(value = "删除用户", notes = "根据id删除用户信息")
	@RequestMapping(value = "delete", method = { RequestMethod.POST })
	@SystemLog(module = "SYSTEM", methods = "SYSTEM_DELETE_USER")
	public RestResultDto<Object> delete(@RequestBody SysUserRequest sysUserRequest) throws Exception {
		RestResultDto<Object> resultDto = new RestResultDto<>();
		int result = RestResultDto.RESULT_SUCC;
		String msg = null;
		Object data = null;
		String exception = null;
		try {

			String userId = sysUserRequest.getId();
			if (StringUtil.isNullOrEmpty(userId)) {
				throw new Exception("要删除的id不能未空！");
			}

			sysUserService.deleteById(userId);
			data = true;
			msg = "删除用户成功！";
		} catch (Exception e) {
			result = RestResultDto.RESULT_FAIL;
			exception = e.getMessage();
			data = false;
			msg = "删除用户失败！";
		} finally {
			resultDto.setData(data);
			resultDto.setException(exception);
			resultDto.setMsg(msg);
			resultDto.setResult(result);
		}
		return resultDto;
	}

}
