package com.nhb.iot.platform.dataaccess.dao.system;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.system.SysUser;

/**
 * 
 * @ClassName: ISysUserDao
 * @Description: User Dao层
 * @author XS guo
 * @date 2017年9月15日 下午4:43:14
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Repository
public interface ISysUserDao extends MongoRepository<SysUser, String> {

	Page<SysUser> findByName(String name, Pageable pageable);

	List<SysUser> findByParentId(String userId);

	SysUser findByLoginName(String loginName);

	List<SysUser> findByParentIdIn(List<String> childTenantIds);

	Page<SysUser> findByParentId(String parentId, Pageable pageable);

}
