package com.nhb.iot.platform.dataaccess.dao.device;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.device.ReceiptCollector;

/**
 * 
 * @ClassName: IReceiptCollectorDao
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author XS guo
 * @date 2017年9月19日 下午2:14:39
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Repository
public interface IReceiptCollectorDao extends MongoRepository<ReceiptCollector, String> {

	Page<ReceiptCollector> findByTenantId(String tenantId, Pageable pageable);

	Page<ReceiptCollector> findByTenantIdIn(List<String> tenantIds, Pageable pageable);

	List<ReceiptCollector> findByIdIn(Long[] ids);

	ReceiptCollector findByCollectorNo(String collectorNo);

	List<ReceiptCollector> findByAreaId(String areaId);

	List<ReceiptCollector> findByTenantIdIn(List<String> tenantIds);

	List<ReceiptCollector> findByAreaIdIn(List<String> areaIds);

}
