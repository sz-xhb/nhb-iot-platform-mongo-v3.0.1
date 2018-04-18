package com.nhb.iot.platform.dataaccess.service.system;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhb.iot.platform.dataaccess.dao.system.ISysUserDao;
import com.nhb.iot.platform.entity.system.SysUser;

/**
 * @author XS guo
 * @ClassName: SysUserService
 * @Description: 用户实现Service
 * @date 2017年9月15日 下午4:42:50
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class SysUserService {

	@Autowired
	private ISysUserDao sysUserDao;

	/**
	 * @return SysUser
	 * @Title: saveOrUpdate
	 * @Description: 保存或修改
	 */
	public SysUser saveOrUpdate(SysUser sysUser) {
		return sysUserDao.save(sysUser);
	}

	public void deleteById(String id) {
		sysUserDao.deleteById(id);
	}

	/**
	 * @return SysUser
	 * @Title: findById
	 * @Description: 根据id获取用户详细信息
	 */
	public SysUser findById(String id) {
		if (sysUserDao.findById(id).equals(Optional.empty())) {
			return null;
		}
		return sysUserDao.findById(id).get();
	}

	public List<SysUser> findByParentId(String userId) {
		return sysUserDao.findByParentId(userId);
	}

	public SysUser findByLoginName(String loginName) {
		return sysUserDao.findByLoginName(loginName);
	}

	public List<SysUser> findAll() {
		return sysUserDao.findAll();
	}

	public List<SysUser> findByParentIdIn(List<String> childTenantIds) {
		return sysUserDao.findByParentIdIn(childTenantIds);
	}

	public Page<SysUser> findByParentId(String parentId, Pageable pageable) {
		return sysUserDao.findByParentId(parentId, pageable);
	}
}
