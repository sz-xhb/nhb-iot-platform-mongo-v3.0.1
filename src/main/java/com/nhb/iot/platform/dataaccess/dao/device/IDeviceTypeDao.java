package com.nhb.iot.platform.dataaccess.dao.device;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.device.DeviceType;

@Repository
public interface IDeviceTypeDao extends MongoRepository<DeviceType, String> {

  List<DeviceType> findByUserId(String userId);

  List<DeviceType> findByDeviceTypeCodeAndUserId(String deviceTypeCode, String userId);

}
