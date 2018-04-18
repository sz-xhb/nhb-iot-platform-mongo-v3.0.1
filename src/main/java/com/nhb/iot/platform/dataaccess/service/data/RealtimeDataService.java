package com.nhb.iot.platform.dataaccess.service.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhb.iot.platform.dataaccess.dao.data.RealtimeDataDao;
import com.nhb.iot.platform.entity.data.RealtimeData;

@Service
public class RealtimeDataService {

	@Autowired
	private RealtimeDataDao realtimeDataDao;

	public RealtimeData save(RealtimeData realtimeData) {
		return realtimeDataDao.save(realtimeData);
	}

	public List<RealtimeData> findByDeviceIdIn(List<String> deviceIds) {
		return realtimeDataDao.findByDeviceIdIn(deviceIds);
	}

}
