package com.nhb.iot.platform.api.inface;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: SystemLog
 * @Description: 自定义注解-用于记录操作日志
 * @author XS guo
 * @date 2017年9月26日 下午5:24:27
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

  String module() default "";

  String methods() default "";
}
