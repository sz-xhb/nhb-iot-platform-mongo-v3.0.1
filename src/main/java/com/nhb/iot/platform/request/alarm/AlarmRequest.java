package com.nhb.iot.platform.request.alarm;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.nhb.iot.platform.base.page.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/11/2.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class AlarmRequest extends PageRequest {

  @XmlElement(name = "id")
  @ApiModelProperty(value = "id", required = true, example = "11")
  private List<String> ids;

  @XmlElement(name = "userId")
  @ApiModelProperty(value = "userId", required = true, example = "11")
  private String userId;

  @XmlElement(name = "dealStatus")
  @ApiModelProperty(value = "dealStatus", required = true, example = "0")
  private Integer dealStatus;

  @XmlElement(name = "handleReason")
  @ApiModelProperty(value = "handleReason", required = true, example = "非异常")
  private String handleReason;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getDealStatus() {
    return dealStatus;
  }

  public void setDealStatus(Integer dealStatus) {
    this.dealStatus = dealStatus;
  }

  public List<String> getIds() {
    return ids;
  }

  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  public String getHandleReason() {
    return handleReason;
  }

  public void setHandleReason(String handleReason) {
    this.handleReason = handleReason;
  }
}
