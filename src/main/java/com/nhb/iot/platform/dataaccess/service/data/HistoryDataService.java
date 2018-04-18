package com.nhb.iot.platform.dataaccess.service.data;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhb.iot.platform.dataaccess.dao.data.HistoryDataDao;
import com.nhb.iot.platform.entity.data.HistoryData;

@Service
public class HistoryDataService {

	@Autowired
	private HistoryDataDao historyDataDao;

	public HistoryData save(HistoryData historyData) {
		return historyDataDao.save(historyData);
	}

	public Page<HistoryData> findByDeviceIdAndMeterTimeBetween(String deviceId, Date startDate, Date endDate,
			Pageable pageable) {
		return historyDataDao.findByDeviceIdAndMeterTimeBetween(deviceId, startDate, endDate, pageable);
	}

	public List<HistoryData> findByMeterTimeBetweenAndDeviceIdInOrderByMeterTimeDesc(Date startDate, Date endDate,
			List<String> deviceIds) {
		return historyDataDao.findByMeterTimeBetweenAndDeviceIdInOrderByMeterTimeDesc(startDate,endDate,deviceIds);
	}
	
	

}
