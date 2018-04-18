package com.nhb.iot.platform.dataaccess.dao.alarm;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.alarm.Alarm;

/**
 * Created by Administrator on 2017/11/2.
 */
@Repository
public interface IAlarmDao extends MongoRepository<Alarm, String> {
  Page<Alarm> findByDeviceIdInAndDealStatusAndAlarmTimeBetween(List<String> deviceIds,
      Integer dealStatus, Date startTime, Date endTime, Pageable pageable);

  long countByDeviceIdInAndDealStatusAndAlarmTimeBetween(List<String> deviceIds, int dealStatus,
      Date startTime, Date endTime);

  long countByDeviceIdInAndAlarmTimeBetween(List<String> deviceIds, Date startTime, Date endTime);
}
