package com.nhb.iot.platform.dataaccess.service.alarm;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nhb.iot.platform.dataaccess.dao.alarm.IAlarmDao;
import com.nhb.iot.platform.entity.alarm.Alarm;

/**
 * Created by Administrator on 2017/11/2.
 */
@Service
public class AlarmService {

  @Autowired
  private IAlarmDao alarmDao;

  public Alarm save(Alarm alarm) {
    return alarmDao.save(alarm);
  }

  public Page<Alarm> findByDeviceIdInAndDealStatusAndAlarmTimeBetween(List<String> deviceIds,
      Integer dealStatus, Date startTime, Date endTime, Pageable pageable) {
    return alarmDao.findByDeviceIdInAndDealStatusAndAlarmTimeBetween(deviceIds, dealStatus,
        startTime, endTime, pageable);
  }

  public Alarm findById(String alarmId) {
    if (alarmDao.findById(alarmId).equals(Optional.empty())) {
      return null;
    } else {
      return alarmDao.findById(alarmId).get();
    }
  }

  public long countByDeviceIdInAndDealStatusAndAlarmTimeBetween(List<String> deviceIds,
      int dealStatus, Date startTime, Date endTime) {
    return alarmDao.countByDeviceIdInAndDealStatusAndAlarmTimeBetween(deviceIds, dealStatus,
        startTime, endTime);
  }

  public long countByDeviceIdInAndAlarmTimeBetween(List<String> deviceIds, Date startTime,
      Date endTime) {
    return alarmDao.countByDeviceIdInAndAlarmTimeBetween(deviceIds, startTime, endTime);
  }
}
