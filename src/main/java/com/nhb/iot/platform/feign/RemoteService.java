package com.nhb.iot.platform.feign;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.nhb.utils.nhb_utils.common.RestResultDto;

@FeignClient(value = "collector-tcp")
public interface RemoteService {

  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/remote/ctrl/turnswitch", method = RequestMethod.POST)
  RestResultDto remoteDevice(@RequestBody Map<String, Object> params);
  
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/remote/ctrl/getRealtimeData", method = RequestMethod.POST)
  RestResultDto getRealtimeData(@RequestBody Map<String, Object> params);

}
