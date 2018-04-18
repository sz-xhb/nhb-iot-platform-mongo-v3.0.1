package com.nhb.iot.platform.dataaccess.service.system;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhb.iot.platform.dataaccess.dao.system.ISysAreaDao;
import com.nhb.iot.platform.entity.system.SysArea;

@Service
public class SysAreaService {

	@Autowired
	private ISysAreaDao sysAreaDao;

	/**
	 * @return SysArea
	 * @Title: save
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public SysArea save(SysArea sysArea) {
		return sysAreaDao.save(sysArea);
	}

	/**
	 * @return SysArea
	 * @Title: findById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	public SysArea findById(String areaId) {
		if (sysAreaDao.findById(areaId).equals(Optional.empty())) {
			return null;
		}
		return sysAreaDao.findById(areaId).get();
	}

	public List<SysArea> findByParentId(String areaId) {
		return sysAreaDao.findByParentId(areaId);
	}

	public void deleteById(String areaId) {
		sysAreaDao.deleteById(areaId);
	}

	public List<SysArea> findAll() {
		return sysAreaDao.findAll();
	}

	public List<SysArea> findByTenantId(String tenantId) {
		return sysAreaDao.findByTenantId(tenantId);
	}

	public SysArea get(String id) {
		return sysAreaDao.findById(id).get();
	}

	public Page<SysArea> findByIdInAndSiteType(List<String> areaIds, String siteType, Pageable pageable) {
		return sysAreaDao.findByIdInAndSiteType(areaIds, siteType, pageable);
	}

	public List<SysArea> findByTenantIdInAndParentId(List<String> tenantIds, String parentId) {
		return sysAreaDao.findByTenantIdInAndParentId(tenantIds, parentId);
	}

	public List<SysArea> findByTenantIdIn(List<String> tenantIds) {
		return sysAreaDao.findByTenantIdIn(tenantIds);
	}

	public List<SysArea> findByIds(List<String> areaIds) {
		return (List<SysArea>) sysAreaDao.findAllById(areaIds);
	}

	public Page<SysArea> findByTenantIdAndSiteType(String ownerId, String deviceType, Pageable pageable) {
		return sysAreaDao.findByTenantIdAndSiteType(ownerId,deviceType,pageable);
	}

}
