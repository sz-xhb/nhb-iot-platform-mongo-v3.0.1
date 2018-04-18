package com.nhb.iot.platform.dataaccess.dao.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nhb.iot.platform.entity.data.DataSwitch;

@Repository
public interface DataSwitchDao extends MongoRepository<DataSwitch, String> {

	Page<DataSwitch> findByDeviceIdAndReadTimeBetween(String deviceId, Date startDate, Date endDate, Pageable pageable);

	List<DataSwitch> findByDeviceIdIn(List<String> deviceIds);

}
