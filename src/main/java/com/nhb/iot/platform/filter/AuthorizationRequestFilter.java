package com.nhb.iot.platform.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import com.nhb.iot.platform.util.RedisUtils;
import com.nhb.utils.nhb_utils.common.StringUtil;

@WebFilter(filterName = "authorizationRequestFilter", urlPatterns = "/api/v1/*")
public class AuthorizationRequestFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String url = httpServletRequest.getServletPath();

    // 过滤登录、登录出接口、swagger-ui接口
    if ("/api/v1/sys/user/login".equals(url) || "/api/v1/sys/user/logout".equals(url)
    		|| "/api/v1/data/report/oneAreaEnergy".equals(url)
    		|| "/api/v1/data/report/totalEnergy".equals(url)
    		|| "/api/v1/remote/getRealtimeData".equals(url)
    		|| "/api/v1/device/findListByMeterId".equals(url)
    		|| "/api/v1/device/save".equals(url)
    		|| "/api/v1/device/update".equals(url)
    		|| "/api/v1/remote/push".equals(url)
    		|| "/api/v1/devicetype/save".equals(url)
    		|| "/api/v1/remote/findRemoteDevices".equals(url)
    		|| "/api/v1/remote/control".equals(url)
    		|| "/api/v1/devicetype/findAll".equals(url)
    		|| "/api/v1/data/energy/handlerCalcEnergy".equals(url)
    		) {
      chain.doFilter(request, response);
      return;
    } else {
      String id = httpServletRequest.getHeader("Authorization_Id");
      String token = httpServletRequest.getHeader("Authorization_Token");

      if (StringUtil.isNullOrEmpty(token)) {
        throw new ServletException("没有权限！");
      }
      if (RedisUtils.getStr(id).equals(token)) {
        // 请求正确
        chain.doFilter(request, response);
        return;
      }
    }
  }

  @Override
  public void destroy() {

  }

}
