package com.nhb.iot.platform.dataaccess.service.system;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhb.iot.platform.dataaccess.dao.system.ISysUserMenuDao;
import com.nhb.iot.platform.entity.system.SysUserMenu;

@Service
public class SysUserMenuService {

  @Autowired
  private ISysUserMenuDao sysUserMenuDao;

  public SysUserMenu findByUserId(String userId) {
    if (sysUserMenuDao.findById(userId).equals(Optional.empty())) {
      return null;
    }
    return sysUserMenuDao.findById(userId).get();
  }

  public SysUserMenu saveOrUpdate(SysUserMenu sysUserMenu) {
    return sysUserMenuDao.save(sysUserMenu);
  }

}
