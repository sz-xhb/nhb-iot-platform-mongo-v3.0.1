package com.nhb.iot.platform.dataaccess.service.device;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhb.iot.platform.dataaccess.dao.device.IDeviceTypeDao;
import com.nhb.iot.platform.entity.device.DeviceType;

@Service
public class DeviceTypeService {

  @Autowired
  private IDeviceTypeDao deviceTypeDao;

  public DeviceType save(DeviceType deviceType) {
    return deviceTypeDao.save(deviceType);
  }

  public DeviceType findById(String id) {
    return deviceTypeDao.findById(id).get();
  }

  public List<DeviceType> findAll() {
    return deviceTypeDao.findAll();
  }

  public void deleteById(String id) {
    deviceTypeDao.deleteById(id);
  }

  public List<DeviceType> findByUserId(String userId) {
    return deviceTypeDao.findByUserId(userId);
  }

  public List<DeviceType> findByDeviceTypeCodeAndUserId(String deviceTypeCode, String userId) {
    return deviceTypeDao.findByDeviceTypeCodeAndUserId(deviceTypeCode, userId);
  }

}
