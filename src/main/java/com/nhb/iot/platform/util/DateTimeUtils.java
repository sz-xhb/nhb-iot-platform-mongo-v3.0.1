/**
 * Project Name:mbox-modules-biz File Name:DateTimeUtils.java Package
 * Name:com.movitech.mbox.common.utils Date:Mar 28, 20172:18:04 PM Copyright (c) 2017,
 * lorisun@live.com All Rights Reserved.
 * 
 */

package com.nhb.iot.platform.util;

import java.util.Calendar;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import com.google.common.collect.Maps;

/**
 * @ClassName:DateTimeUtils
 * @Function: ADD FUNCTION.
 * @Reason: ADD REASON.
 * @Date: Mar 28, 2017 2:18:04 PM
 * @author sunlei
 * @version
 * @since JDK 1.7
 * @see
 */
public class DateTimeUtils extends DateUtils {
  /**
   * getTime: 获取查询的时间条件
   * 
   * @author sunlei
   * @return
   * @since JDK 1.7
   */
  public static Map<String, String> getTimeRange() {
    String bTime = StringUtils.EMPTY;
    String eTime = StringUtils.EMPTY;
    Calendar cal = Calendar.getInstance();

    Calendar beginTime = (Calendar) cal.clone();
    Calendar endTime = (Calendar) cal.clone();

    beginTime.add(Calendar.HOUR_OF_DAY, -1);
    beginTime.set(Calendar.MINUTE, 0);
    beginTime.set(Calendar.SECOND, 0);

    endTime.add(Calendar.HOUR_OF_DAY, -1);
    endTime.set(Calendar.MINUTE, 59);
    endTime.set(Calendar.SECOND, 59);

    bTime = DateFormatUtils.format(beginTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    eTime = DateFormatUtils.format(endTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    Map<String, String> dateTime = Maps.newHashMap();
    dateTime.put("beginTime", bTime);
    dateTime.put("endTime", eTime);

    return dateTime;
  }

  /**
   * getDateRange: 获取昨天时间范围(yyyy-mm-dd 00:00:00 ~ yyyy-mm-dd 23:59:59
   * 
   * @author sunlei
   * @return
   * @since JDK 1.8
   */
  public static Map<String, String> getDateRange() {
    String bTime = StringUtils.EMPTY;
    String eTime = StringUtils.EMPTY;
    Calendar cal = Calendar.getInstance();

    Calendar beginTime = (Calendar) cal.clone();
    Calendar endTime = (Calendar) cal.clone();

    beginTime.add(Calendar.DATE, -1);
    beginTime.set(Calendar.HOUR_OF_DAY, 0);
    beginTime.set(Calendar.MINUTE, 0);
    beginTime.set(Calendar.SECOND, 0);

    endTime.add(Calendar.DATE, -1);
    endTime.set(Calendar.HOUR_OF_DAY, 23);
    endTime.set(Calendar.MINUTE, 59);
    endTime.set(Calendar.SECOND, 59);

    bTime = DateFormatUtils.format(beginTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    eTime = DateFormatUtils.format(endTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    Map<String, String> dateTime = Maps.newHashMap();
    dateTime.put("beginTime", bTime);
    dateTime.put("endTime", eTime);

    return dateTime;
  }

  /**
   * getDateRange: 获取昨天("yyyy-mm-dd")
   * 
   * @author sunlei
   * @return
   * @since JDK 1.8
   */
  public static String getBeforeDate() {
    String beforeDate = StringUtils.EMPTY;
    Calendar cal = Calendar.getInstance();

    Calendar date = (Calendar) cal.clone();

    date.add(Calendar.DATE, -1);
    date.set(Calendar.HOUR_OF_DAY, 0);
    date.set(Calendar.MINUTE, 0);
    date.set(Calendar.SECOND, 0);

    beforeDate = DateFormatUtils.format(date.getTime(), "yyyy-MM-dd");
    return beforeDate;
  }

  /**
   * getDateRange: 获取当月("yyyy-mm")
   * 
   * @author xuyahui
   * @return
   * @since JDK 1.8
   */
  public static Map<String, String> getMonthRange() {
    String bTime = StringUtils.EMPTY;
    String eTime = StringUtils.EMPTY;
    Calendar cal = Calendar.getInstance();

    Calendar beginTime = (Calendar) cal.clone();
    Calendar endTime = (Calendar) cal.clone();

    beginTime.set(Calendar.DAY_OF_MONTH, 1);
    beginTime.set(Calendar.HOUR_OF_DAY, 0);
    beginTime.set(Calendar.MINUTE, 0);
    beginTime.set(Calendar.SECOND, 0);

    endTime.set(Calendar.HOUR_OF_DAY, 23);
    endTime.set(Calendar.MINUTE, 59);
    endTime.set(Calendar.SECOND, 59);

    bTime = DateFormatUtils.format(beginTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    eTime = DateFormatUtils.format(endTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    Map<String, String> dateTime = Maps.newHashMap();
    dateTime.put("beginTime", bTime);
    dateTime.put("endTime", eTime);

    return dateTime;
  }

  /**
   * getDateRange: 获取上月("yyyy-mm")
   * 
   * @author xuyahui
   * @return
   * @since JDK 1.8
   */
  @SuppressWarnings("static-access")
  public static Map<String, String> getLastMonthRange() {
    String bTime = StringUtils.EMPTY;
    String eTime = StringUtils.EMPTY;
    Calendar cal = Calendar.getInstance();

    Calendar beginTime = (Calendar) cal.clone();
    Calendar endTime = (Calendar) cal.clone();

    beginTime.add(Calendar.MONTH, -1);
    beginTime.set(Calendar.DAY_OF_MONTH, 1);
    beginTime.set(Calendar.HOUR_OF_DAY, 0);
    beginTime.set(Calendar.MINUTE, 0);
    beginTime.set(Calendar.SECOND, 0);

    endTime.add(Calendar.MONTH, -1);
    endTime.set(Calendar.DAY_OF_MONTH, endTime.getActualMaximum(endTime.DAY_OF_MONTH));
    endTime.set(Calendar.HOUR_OF_DAY, 23);
    endTime.set(Calendar.MINUTE, 59);
    endTime.set(Calendar.SECOND, 59);

    bTime = DateFormatUtils.format(beginTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    eTime = DateFormatUtils.format(endTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    Map<String, String> dateTime = Maps.newHashMap();
    dateTime.put("beginTime", bTime);
    dateTime.put("endTime", eTime);

    return dateTime;
  }

  /**
   * getDateRange: 获取上月("yyyy-mm")
   * 
   * @author xuyahui
   * @return
   * @since JDK 1.8
   */
  @SuppressWarnings("static-access")
  public static Map<String, String> getPreviousMonthRange() {
    String bTime = StringUtils.EMPTY;
    String eTime = StringUtils.EMPTY;
    Calendar cal = Calendar.getInstance();

    Calendar beginTime = (Calendar) cal.clone();
    Calendar endTime = (Calendar) cal.clone();

    beginTime.add(Calendar.MONTH, -2);
    beginTime.set(Calendar.DAY_OF_MONTH, 1);
    beginTime.set(Calendar.HOUR_OF_DAY, 0);
    beginTime.set(Calendar.MINUTE, 0);
    beginTime.set(Calendar.SECOND, 0);

    endTime.add(Calendar.MONTH, -2);
    endTime.set(Calendar.DAY_OF_MONTH, endTime.getActualMaximum(endTime.DAY_OF_MONTH));
    endTime.set(Calendar.HOUR_OF_DAY, 23);
    endTime.set(Calendar.MINUTE, 59);
    endTime.set(Calendar.SECOND, 59);

    bTime = DateFormatUtils.format(beginTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    eTime = DateFormatUtils.format(endTime.getTime(), "yyyy-MM-dd HH:mm:ss");
    Map<String, String> dateTime = Maps.newHashMap();
    dateTime.put("beginTime", bTime);
    dateTime.put("endTime", eTime);

    return dateTime;
  }

}
