package com.nhb.iot.platform.dataaccess.dao.device;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nhb.iot.platform.api.device.DeviceStatus;

@Repository
public interface IDeviceStatusDao extends MongoRepository<DeviceStatus, String> {

}
