package com.nhb.iot.platform.dataaccess.service.data;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhb.iot.platform.dataaccess.dao.data.DataSwitchDao;
import com.nhb.iot.platform.entity.data.DataSwitch;

@Service
public class DataSwitchService {

	@Autowired
	private DataSwitchDao dataSwitchDao;

	public DataSwitch save(DataSwitch dataSwitch) {
		return dataSwitchDao.save(dataSwitch);
	}

	public Page<DataSwitch> findByDeviceIdAndReadTimeBetween(String deviceId, Date startDate, Date endDate,
			Pageable pageable) {
		return dataSwitchDao.findByDeviceIdAndReadTimeBetween(deviceId, startDate, endDate, pageable);
	}

	public List<DataSwitch> findByDeviceIdIn(List<String> deviceIds) {
		return dataSwitchDao.findByDeviceIdIn(deviceIds);
	}

}
