package com.nhb.iot.platform.dataaccess.service.device;

import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.nhb.iot.platform.dataaccess.dao.device.IReceiptCollectorDao;
import com.nhb.iot.platform.entity.device.ReceiptCollector;

@Service
public class ReceiptCollectorService {

	@Autowired
	private IReceiptCollectorDao receiptCollectorDao;

	/**
	 * @return ReceiptCollector
	 * @Title: save
	 * @Description: 新增或修改
	 */
	public ReceiptCollector saveOrUpdate(ReceiptCollector collector) {
		return receiptCollectorDao.save(collector);
	}

	public ReceiptCollector findById(String id){
		if(receiptCollectorDao.findById(id).equals(Optional.empty())){
			return null;
		}
		return receiptCollectorDao.findById(id).get();
	}
	
	/**
	 * @return void
	 * @Title: delete
	 * @Description: 删除
	 */
	public void delete(String collectorId) {
		receiptCollectorDao.deleteById(collectorId);
	}

	/**
	 * @return Page<ReceiptCollector>
	 * @Title: findByUserId
	 * @Description: 根据userId查询其下属所有的采集器
	 */
	public Page<ReceiptCollector> findByTenantId(String tenantId, Pageable pageable) {
		return receiptCollectorDao.findByTenantId(tenantId, pageable);
	}

	public Page<ReceiptCollector> findByTenantIds(List<String> tenantIds, Pageable pageable) {
		return receiptCollectorDao.findByTenantIdIn(tenantIds, pageable);
	}

	public ReceiptCollector findByCollectorNo(String collectorNo) {
		return receiptCollectorDao.findByCollectorNo(collectorNo);
	}

	public List<ReceiptCollector> findByIds(List<String> collectorIds) {
		return Lists.newArrayList(receiptCollectorDao.findAllById(collectorIds));
	}

	public Page<ReceiptCollector> findAll(Pageable pageable) {
		return receiptCollectorDao.findAll(pageable);
	}

	public List<ReceiptCollector> findAll() {
		return receiptCollectorDao.findAll();
	}

	public List<ReceiptCollector> findByAreaId(String areaId) {
		return receiptCollectorDao.findByAreaId(areaId);
	}

	public List<ReceiptCollector> findByAreaId(Map<String, String> areaId) {
		return receiptCollectorDao.findByAreaId(areaId.get("areaId"));
	}

	public long totalCounts() {
		return receiptCollectorDao.count();
	}

	public List<ReceiptCollector> findByTenantIds(List<String> tenantIds) {
		return receiptCollectorDao.findByTenantIdIn(tenantIds);
	}

	public List<ReceiptCollector> findByAreaIdIn(List<String> areaIds) {
		return receiptCollectorDao.findByAreaIdIn(areaIds);
	}

}
