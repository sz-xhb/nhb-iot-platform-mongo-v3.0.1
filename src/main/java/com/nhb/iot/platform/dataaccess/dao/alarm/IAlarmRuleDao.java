package com.nhb.iot.platform.dataaccess.dao.alarm;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.alarm.AlarmRule;

/**
 * Created by Administrator on 2017/11/7.
 */
@Repository
public interface IAlarmRuleDao extends MongoRepository<AlarmRule, String> {
}
