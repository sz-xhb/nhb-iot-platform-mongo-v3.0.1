package com.nhb.iot.platform.dataaccess.service.system;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nhb.iot.platform.dataaccess.dao.system.ILogEntityDao;
import com.nhb.iot.platform.entity.system.LogEntity;

@Service
public class LogEntityService {

  @Autowired
  private ILogEntityDao logEntityDao;

  public LogEntity save(LogEntity entity) {
    return logEntityDao.save(entity);
  }

  public Page<LogEntity> findByUserIdInAndModuleAndMethodAndCommitAndDateBetween(
      List<String> userIds, String module, String methods, String commit, Date startTime,
      Date endTime, Pageable pageable) {
    return logEntityDao.findByUserIdInAndModuleAndMethodAndCommitAndDateBetween(userIds, module,
        methods, commit, startTime, endTime, pageable);
  }

  public Page<LogEntity> findByUserIdInAndDateBetween(List<String> userIds, Date startTime,
      Date endTime, Pageable pageable) {
    return logEntityDao.findByUserIdInAndDateBetween(userIds, startTime, endTime, pageable);
  }
}
