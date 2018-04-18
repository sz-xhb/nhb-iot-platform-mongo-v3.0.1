package com.nhb.iot.platform.dataaccess.dao.system;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.system.LogEntity;

@Repository
public interface ILogEntityDao extends MongoRepository<LogEntity, Long> {

  Page<LogEntity> findByUserIdInAndModuleAndMethodAndCommitAndDateBetween(List<String> userIds,
      String module, String methods, String commit, Date startTime, Date endTime,
      Pageable pageable);

  Page<LogEntity> findByUserIdInAndDateBetween(List<String> userIds, Date startTime, Date endTime,
      Pageable pageable);
}
