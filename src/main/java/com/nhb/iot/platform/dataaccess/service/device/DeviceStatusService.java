package com.nhb.iot.platform.dataaccess.service.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhb.iot.platform.api.device.DeviceStatus;
import com.nhb.iot.platform.dataaccess.dao.device.IDeviceStatusDao;

@Service
public class DeviceStatusService {

	@Autowired
	private IDeviceStatusDao deviceStatusDao;

	public DeviceStatus save(DeviceStatus deviceStatus) {
		return deviceStatusDao.save(deviceStatus);
	}

}
