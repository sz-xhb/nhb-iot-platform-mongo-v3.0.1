/**
 * Project Name:controller File Name:WebSockerResponse.java Package
 * Name:com.newhongbo.iotdm.modules.websocket.domain Date:Aug 10, 201711:11:22 AM Copyright (c)
 * 2017, lorisun@live.com All Rights Reserved.
 * 
 */

package com.nhb.iot.platform.websocket.response;

/**
 * 
 * @ClassName: WebSockerResponse
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author XS guo
 * @date 2017年9月21日 下午10:28:26
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class WebSockerResponse {
  // 信息类型编码
  private String infoType;
  // 数据
  private Object data;

  public String getInfoType() {
    return infoType;
  }

  public void setInfoType(String infoType) {
    this.infoType = infoType;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
