package com.nhb.iot.platform.dataaccess.service.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhb.iot.platform.dataaccess.dao.system.ISysSiteTypeDao;
import com.nhb.iot.platform.entity.system.SysSiteType;

@Service
public class SysSiteTypeService {

  @Autowired
  private ISysSiteTypeDao sysSiteTypeDao;

  public SysSiteType save(SysSiteType sysSiteType) {
    return sysSiteTypeDao.save(sysSiteType);
  }

  public SysSiteType findById(String id) {
    return sysSiteTypeDao.findById(id).get();
  }

  public void deleteById(String id) {
    sysSiteTypeDao.deleteById(id);
  }

  public List<SysSiteType> findAll() {
    return sysSiteTypeDao.findAll();
  }

  public List<SysSiteType> findByUserId(String userId) {
    return sysSiteTypeDao.findByUserId(userId);
  }

  public List<SysSiteType> findBySiteCodeAndUserId(String siteTypeCode, String userId) {
    return sysSiteTypeDao.findBySiteCodeAndUserId(siteTypeCode, userId);
  }

}
