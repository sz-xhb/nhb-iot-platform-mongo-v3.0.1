package com.nhb.iot.platform.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.nhb.iot.platform.api.inface.SystemLog;
import com.nhb.iot.platform.dataaccess.service.system.LogEntityService;
import com.nhb.iot.platform.entity.system.LogEntity;
import com.nhb.iot.platform.enums.SystemLogMethodsTypeEnum;
import com.nhb.iot.platform.enums.SystemLogMoudleTypeEnum;
import com.nhb.utils.nhb_utils.common.RestResultDto;
import com.nhb.utils.nhb_utils.common.StringUtil;
import com.nhb.utils.nhb_utils.json.JsonMapper;

/**
 * aop切面记录操作日志
 */
@Aspect
@Component
public class WebLogAspect {

  @Autowired
  private LogEntityService logEntityService;

  LogEntity logEntity;

  long startTime;

  long endTime;

  String result;

  @Pointcut("@annotation(com.nhb.iot.platform.api.inface.SystemLog)")
  public void webLog() {}

  @SuppressWarnings("rawtypes")
  @Before("webLog()")
  public void doBefore(JoinPoint joinPoint) throws Throwable, Exception {
    // 接收到请求，记录请求内容
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    logEntity = new LogEntity();

    // ip
    String ip = request.getRemoteAddr();
    logEntity.setIp(ip);

    // 任务执行时间
    Date nowTime = new Date();
    logEntity.setDate(nowTime);

    // 方法通知前获取时间,为什么要记录这个时间呢？当然是用来计算模块执行时间的
    startTime = System.currentTimeMillis();
    // 拦截的实体类，就是当前正在执行的controller
    Object target = joinPoint.getTarget();
    // 拦截的方法名称。当前正在执行的方法
    String methodName = joinPoint.getSignature().getName();
    // 拦截的方法参数
    // Object[] args = joinPoint.getArgs();
    // Map<String, Object> mapValue = (Map<String, Object>)
    // JsonMapper.fromJsonString(JsonMapper.toJsonString(args[0]),
    // Map.class);
    String userId = request.getHeader("Authorization_Id");
    logEntity.setUserId(userId);
    // 拦截的放参数类型
    Signature sig = joinPoint.getSignature();
    MethodSignature msig = null;
    if (!(sig instanceof MethodSignature)) {
      throw new IllegalArgumentException("该注解只能用于方法");
    }
    msig = (MethodSignature) sig;
    Class[] parameterTypes = msig.getMethod().getParameterTypes();

    // 获得被拦截的方法
    Method method = target.getClass().getMethod(methodName, parameterTypes);
    if (null != method) {
      // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
      if (method.isAnnotationPresent(SystemLog.class)) {
        SystemLog systemlog = method.getAnnotation(SystemLog.class);
        logEntity.setModule(systemlog.module());
        logEntity.setMethod(systemlog.methods());
      }
    }

  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @AfterReturning(returning = "ret", pointcut = "webLog()")
  public void doAfterReturning(Object ret) throws Throwable {
    endTime = System.currentTimeMillis();
    logEntity.setResponseTime(String.valueOf(endTime - startTime));
    // 处理完请求，返回内容
    // 判断是否为1
    RestResultDto resultDto = (RestResultDto) ret;
    if (resultDto.getResult() == 0) {
      result = "SUCCESS";
    } else {
      result = "FAIL";
    }
    if (StringUtil.isNullOrEmpty(logEntity.getUserId())
        && logEntity.getModule().equals(SystemLogMoudleTypeEnum.SYSTEM.getKey())
        && logEntity.getMethod().equals(SystemLogMethodsTypeEnum.SYSTEM_LOGIN.getKey())
        && result == "SUCCESS") {
      // 登陆接口在登陆成功后，再存储userId
      Map<String, Object> map = (Map<String, Object>) JsonMapper
          .fromJsonString(JsonMapper.toJsonString(resultDto.getData()), Map.class);
      Map<String, Object> map2 = (Map<String, Object>) map.get("loginMsg");
      logEntity.setUserId(map2.get("uuid").toString());
    }
    logEntity.setCommit(result);
    logEntityService.save(logEntity);

  }
}
