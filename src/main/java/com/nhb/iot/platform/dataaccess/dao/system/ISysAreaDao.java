package com.nhb.iot.platform.dataaccess.dao.system;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.system.SysArea;

/**
 * 
 * @ClassName: ISysAreaDao
 * @Description: 区域Dao接口
 * @author XS guo
 * @date 2017年9月20日 上午10:51:04
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Repository
public interface ISysAreaDao extends MongoRepository<SysArea, String> {

	List<SysArea> findByParentId(String areaId);

	Page<SysArea> findByTenantIdAndSiteType(String ownerId, String siteType, Pageable pageable);

	Page<SysArea> findByIdInAndSiteType(List<String> areaIds, String siteType, Pageable pageable);

	List<SysArea> findByTenantId(String tenantId);

	List<SysArea> findByTenantIdInAndParentId(List<String> tenantIds, String parentId);

	List<SysArea> findByTenantIdIn(List<String> tenantIds);
}
