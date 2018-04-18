package com.nhb.iot.platform.dataaccess.dao.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nhb.iot.platform.entity.data.HistoryData;

@Repository
public interface HistoryDataDao extends MongoRepository<HistoryData, String> {

	Page<HistoryData> findByDeviceIdAndMeterTimeBetween(String deviceIds, Date startDate, Date endDate,
			Pageable pageable);

	List<HistoryData> findByMeterTimeBetweenAndDeviceIdInOrderByMeterTimeDesc(Date startDate, Date endDate,
			List<String> deviceIds);

}
