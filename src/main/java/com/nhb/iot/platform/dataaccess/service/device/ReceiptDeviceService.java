package com.nhb.iot.platform.dataaccess.service.device;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.nhb.iot.platform.dataaccess.dao.device.IReceiptDeviceDao;
import com.nhb.iot.platform.entity.device.ReceiptDevice;

@Service
public class ReceiptDeviceService {

	@Autowired
	private IReceiptDeviceDao deviceDao;
	
	public ReceiptDevice findById(String id){
		if(deviceDao.findById(id).equals(Optional.empty())){
			return null;
		}
		return deviceDao.findById(id).get();
	}

	public ReceiptDevice saveOrUpdate(ReceiptDevice device) {
		return deviceDao.save(device);
	}

	public Page<ReceiptDevice> findByMeterId(String meterId, Pageable pageable) {
		return deviceDao.findByMeterId(meterId, pageable);
	}

	public void delete(String id) {
		deviceDao.deleteById(id);
	}

	public List<ReceiptDevice> findByIds(List<String> deviceIds) {
		return Lists.newArrayList(deviceDao.findAllById(deviceIds));
	}

	public List<ReceiptDevice> findByMeterIdIn(List<String> meterIds) {
		return deviceDao.findByMeterIdIn(meterIds);
	}

	public List<ReceiptDevice> findAll() {
		return deviceDao.findAll();
	}

	public List<ReceiptDevice> findByCollectorNoIn(List<String> collectorNos) {
		return deviceDao.findByCollectorNoIn(collectorNos);
	}

	public Page<ReceiptDevice> findByIsCtrlAndIdIn(boolean value, List<String> deviceIds,Pageable pageable) {
		return deviceDao.findByIsCtrlAndIdIn(value,deviceIds,pageable);
	}

}
