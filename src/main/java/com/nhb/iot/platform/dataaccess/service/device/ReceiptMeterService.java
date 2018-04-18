package com.nhb.iot.platform.dataaccess.service.device;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhb.iot.platform.dataaccess.dao.device.IReceiptMeterDao;
import com.nhb.iot.platform.entity.device.ReceiptMeter;

@Service
public class ReceiptMeterService {

  @Autowired
  private IReceiptMeterDao receiptMeterDao;

  /**
   * @return Page<ReceiptMeter>
   * @Title: findByCollectorId
   * @Description: 根据采集器id查询其下属所有的电表
   */
  public Page<ReceiptMeter> findByCollectorId(String collectorId, Pageable pageable) {
    return receiptMeterDao.findByCollectorId(collectorId, pageable);
  }
  
  public ReceiptMeter findById(String id){
	  if(receiptMeterDao.findById(id).equals(Optional.empty())){
		  return null;
	  }
	  return receiptMeterDao.findById(id).get();
  }

  public ReceiptMeter saveOrUpdate(ReceiptMeter receiptMeter) {
    return receiptMeterDao.save(receiptMeter);
  }

  public void delete(String id) {
    receiptMeterDao.deleteById(id);
  }

  public List<ReceiptMeter> findByCollectorIdIn(List<String> collectorIds) {
    return receiptMeterDao.findByCollectorIdIn(collectorIds);
  }
}
