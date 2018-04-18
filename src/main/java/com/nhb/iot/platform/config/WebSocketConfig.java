package com.nhb.iot.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 
 * @ClassName: WebSocketConfig
 * @Description: WebSocket 配置文件
 * @author XS guo
 * @date 2017年9月26日 上午10:39:43
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
public class WebSocketConfig {
  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }
}
