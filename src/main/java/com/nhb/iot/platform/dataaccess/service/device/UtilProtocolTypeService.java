package com.nhb.iot.platform.dataaccess.service.device;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhb.iot.platform.dataaccess.dao.device.IUtilProtocolTypeDao;
import com.nhb.iot.platform.entity.device.UtilProtocolType;

@Service
public class UtilProtocolTypeService {

  @Autowired
  private IUtilProtocolTypeDao utilProtocolTypeDao;

  public List<UtilProtocolType> findAll() {
    return utilProtocolTypeDao.findAll();
  }

}
