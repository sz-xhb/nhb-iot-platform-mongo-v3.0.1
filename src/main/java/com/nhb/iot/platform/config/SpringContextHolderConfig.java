package com.nhb.iot.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.nhb.utils.nhb_utils.context.SpringContextHolder;

/**
 * 
 * @ClassName: SpringContextHolderConfig
 * @Description: 配置使用SpringContextHolder
 * @author XS guo
 * @date 2017年9月26日 上午10:39:08
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
public class SpringContextHolderConfig {
  @Bean
  public SpringContextHolder getSpringContextHolder() {
    SpringContextHolder springContextHolder = new SpringContextHolder();
    return springContextHolder;
  }
}
