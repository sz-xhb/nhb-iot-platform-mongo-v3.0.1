package com.nhb.iot.platform.dataaccess.dao.system;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.system.SysUserMenu;

@Repository
public interface ISysUserMenuDao extends MongoRepository<SysUserMenu, String> {

}
