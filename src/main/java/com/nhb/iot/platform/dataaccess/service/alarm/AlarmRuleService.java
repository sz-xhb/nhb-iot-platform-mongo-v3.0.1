package com.nhb.iot.platform.dataaccess.service.alarm;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhb.iot.platform.dataaccess.dao.alarm.IAlarmRuleDao;
import com.nhb.iot.platform.entity.alarm.AlarmRule;

/**
 * Created by Administrator on 2017/11/7.
 */
@Service
public class AlarmRuleService {

  @Autowired
  private IAlarmRuleDao alarmRuleDao;

  public AlarmRule save(AlarmRule rule) {
    return alarmRuleDao.save(rule);
  }

  public AlarmRule findById(String deviceId) {
    if (alarmRuleDao.findById(deviceId).equals(Optional.empty())) {
      return null;
    } else {
      return alarmRuleDao.findById(deviceId).get();
    }
  }

  public List<AlarmRule> findAll() {
    return alarmRuleDao.findAll();
  }
}
