package com.nhb.iot.platform.dataaccess.dao.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nhb.iot.platform.entity.data.RealtimeData;

@Repository
public interface RealtimeDataDao extends MongoRepository<RealtimeData, String> {

	List<RealtimeData> findByDeviceIdIn(List<String> deviceIds);

}
