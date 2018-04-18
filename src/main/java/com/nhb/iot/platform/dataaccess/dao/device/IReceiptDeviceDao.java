package com.nhb.iot.platform.dataaccess.dao.device;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nhb.iot.platform.entity.device.ReceiptDevice;

/**
 * 
 * @ClassName: IDeviceDao
 * @Description: 设备Dao接口
 * @author XS guo
 * @date 2017年9月20日 上午10:54:00
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Repository
public interface IReceiptDeviceDao extends MongoRepository<ReceiptDevice, String> {

	Page<ReceiptDevice> findByMeterId(String meterId, Pageable pageable);

	List<ReceiptDevice> findByMeterIdIn(List<String> meterIds);

	List<ReceiptDevice> findByCollectorNoIn(List<String> collectorNos);

	Page<ReceiptDevice> findByIsCtrlAndIdIn(boolean value, List<String> deviceIds,Pageable pageable);

}
