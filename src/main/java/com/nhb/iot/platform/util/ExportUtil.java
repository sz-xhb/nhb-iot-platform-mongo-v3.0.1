/**
 * Project Name:PingTaiXiTong-ShanXiLianTongmongo File Name:ExportUtil.java Package
 * Name:nhb.system.platform.util Date:2018年2月8日上午10:47:57 Copyright (c) 2018, xyh@newhongbo.com All
 * Rights Reserved.
 * 
 */

package com.nhb.iot.platform.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @ClassName:ExportUtil
 * @Date: 2018年2月8日 上午10:47:57
 * @author Administrator
 * @version
 * @since JDK 1.8
 * @see
 */
public class ExportUtil {
  private List<String> tableName;
  private ArrayList<Map<String, String>> columnNameList;
  private List<Map<String, Object>> contentList;

  /**
   * 构造方法传入生成表格需要的参数
   * 
   * @param tableName
   * @param columnNameList
   * @param contentList
   */
  public ExportUtil(List<String> tableName, ArrayList<Map<String, String>> columnNameList,
      List<Map<String, Object>> contentList) {
    this.tableName = tableName;
    this.columnNameList = columnNameList;
    this.contentList = contentList;
  }

  /**
   * 生成2007版本的Excel文件
   * 
   * @return
   */
  public XSSFWorkbook doExportEXCEL07() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // byte[] content = null;
    XSSFWorkbook wb = new XSSFWorkbook();
    try {
      // 创建表格模版,调用createTable()
      createTable(wb);
      // wb.write(baos);
      // baos.flush();
      // content = baos.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (baos != null)
        try {
          baos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    return wb;
  }

  /**
   * 生成2003版本的Excel文件
   * 
   * @return
   */
  public byte[] doExportEXCEL03() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] content = null;
    try {
      // 创建表格模版,调用createTable()
      HSSFWorkbook wb = new HSSFWorkbook();
      createTable(wb);
      wb.write(baos);
      baos.flush();
      content = baos.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (baos != null)
        try {
          baos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    return content;
  }

  private void createTable(XSSFWorkbook wb)// 07设置表格格式与内容
  {
    int k = 0;
    for (int s = 0; s < columnNameList.size(); s++) {// 多张表情况考虑
      if (tableName.size() > 1) {
        k++;
      }
      XSSFSheet sheet = createSheet(wb, k);// 创建sheet，设置其格式
      XSSFCellStyle headStyle = createHeadStyle(wb);

      XSSFFont bodyFont = wb.createFont();// 设置内容样式与表头样式
      bodyFont.setBold(true);// 粗体显示
      XSSFCellStyle bodystyle = createBodyStyle(wb, bodyFont);

      createTitle(wb, sheet, headStyle, bodyFont);// 设置表头内容
      createColumnName(s, sheet, bodystyle);// 设置列名
      createBodyContent(s, sheet, bodystyle);// 设置表格内容
    }
  }

  private void createTable(HSSFWorkbook wb)// 03设置表格格式与内容
  {
    int k = 0;
    for (int s = 0; s < columnNameList.size(); s++) {// 多张表情况考虑
      if (tableName.size() > 1) {
        k++;
      }
      HSSFSheet sheet = createSheet(wb, k);// 创建sheet，设置其格式
      HSSFCellStyle headStyle = createHeadStyle(wb);

      HSSFFont bodyFont = wb.createFont();// 设置内容样式与表头样式
      bodyFont.setBold(true);// 粗体显示
      HSSFCellStyle bodystyle = createBodyStyle(wb, bodyFont);

      createTitle(wb, sheet, headStyle, bodyFont);// 设置表头内容
      createColumnName(s, sheet, bodystyle);// 设置列名
      createBodyContent(s, sheet, bodystyle);// 设置表格内容
    }
  }

  private void createBodyContent(int s, XSSFSheet sheet, XSSFCellStyle bodystyle) {
    XSSFRow row;
    int i = 4;// 表示第i行
    String cell_value;
    for (Map<String, Object> map : contentList) {// 设置表格内容，即表格中各个字段的数值
      row = sheet.createRow(i + 1);
      int j = 0;// 表示第j列
      Set<Entry<String, String>> entrySet2 = columnNameList.get(s).entrySet();
      for (Entry<String, String> title : entrySet2) {// 拿到表头Map中的value
        String key = title.getValue();
        if ("readTime".equals(key)) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
          cell_value = (sdf.format(map.get(key))).toString();
        } else {
          cell_value = !(map.get(key) == null) ? (map.get(key)).toString() : null;
        }
        XSSFCell cell = row.createCell(j);
        cell.setCellValue(cell_value);
        cell.setCellStyle(bodystyle);
        j++;
      }
      i++;
    }
  }

  private void createBodyContent(int s, HSSFSheet sheet, HSSFCellStyle bodystyle) {
    HSSFRow row;
    int i = 4;// 表示第i行
    String cell_value;
    for (Map<String, Object> map : contentList) {// 设置表格内容，即表格中各个字段的数值
      row = sheet.createRow(i + 1);
      int j = 0;// 表示第j列
      Set<Entry<String, String>> entrySet2 = columnNameList.get(s).entrySet();
      for (Entry<String, String> title : entrySet2) {// 拿到表头Map中的value
        String key = title.getValue();
        if ("readTime".equals(key)) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
          cell_value = (sdf.format(map.get(key))).toString();
        } else {
          cell_value = !(map.get(key) == null) ? (map.get(key)).toString() : null;
        }
        HSSFCell cell = row.createCell(j);
        cell.setCellValue(cell_value);
        cell.setCellStyle(bodystyle);
        j++;
      }
      i++;
    }
  }

  private void createColumnName(int s, XSSFSheet sheet, XSSFCellStyle bodystyle) {
    int c = 0;
    XSSFRow row = sheet.createRow(c + 2);
    Set<Entry<String, String>> entrySet = columnNameList.get(s).entrySet();// 设置第三行内容，即列名
    for (Entry<String, String> title : entrySet) {
      XSSFCell cell = row.createCell(c);
      cell.setCellValue(title.getKey());
      cell.setCellStyle(bodystyle);
      c++;
    }
  }

  private void createColumnName(int s, HSSFSheet sheet, HSSFCellStyle bodystyle) {
    int c = 0;
    HSSFRow row = sheet.createRow(c + 2);
    Set<Entry<String, String>> entrySet = columnNameList.get(s).entrySet();// 设置第三行内容，即列名
    for (Entry<String, String> title : entrySet) {
      HSSFCell cell = row.createCell(c);
      cell.setCellValue(title.getKey());
      cell.setCellStyle(bodystyle);
      c++;
    }
  }

  private void createTitle(XSSFWorkbook wb, XSSFSheet sheet, XSSFCellStyle headStyle,
      XSSFFont bodyFont) {
    String title1 = tableName.get(0);// 解析表头
    String title2 = "报告生产时间 :" + DateUtils.formatDateTime(new Date());

    XSSFCellStyle titleStyle = wb.createCellStyle();// 设置表头样式
    titleStyle.setAlignment(HorizontalAlignment.LEFT);
    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    titleStyle.setFont(bodyFont);

    // 设置表格内容（重点）
    XSSFRow row = sheet.createRow((int) 0);// 设置第一行内容
    row.setHeight((short) (20 * 20));
    XSSFCell cell0 = row.createCell(2);
    cell0.setCellValue(title1);
    cell0.setCellStyle(headStyle);
    row = sheet.createRow((int) 1);// 设置第二行内容
    row.setHeight((short) (20 * 20));
    XSSFCell cell1 = row.createCell(0);
    cell1.setCellValue(title2);
    cell1.setCellStyle(titleStyle);
  }

  private void createTitle(HSSFWorkbook wb, HSSFSheet sheet, HSSFCellStyle headStyle,
      HSSFFont bodyFont) {
    String title1 = tableName.get(0);// 解析表头
    String title2 = "报告生产时间 :" + DateUtils.formatDateTime(new Date());

    HSSFCellStyle titleStyle = wb.createCellStyle();// 设置表头样式
    titleStyle.setAlignment(HorizontalAlignment.LEFT);
    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    titleStyle.setFont(bodyFont);

    // 设置表格内容（重点）
    HSSFRow row = sheet.createRow((int) 0);// 设置第一行内容
    row.setHeight((short) (20 * 20));
    HSSFCell cell0 = row.createCell(2);
    cell0.setCellValue(title1);
    cell0.setCellStyle(headStyle);
    row = sheet.createRow((int) 1);// 设置第二行内容
    row.setHeight((short) (20 * 20));
    HSSFCell cell1 = row.createCell(0);
    cell1.setCellValue(title2);
    cell1.setCellStyle(titleStyle);
  }

  private XSSFSheet createSheet(XSSFWorkbook wb, int k)// 07
  {
    String sheetName = tableName.get(k);
    XSSFSheet sheet = wb.createSheet(sheetName);// 创建一个sheet
    sheet.setDefaultColumnWidth(12);
    sheet.setDefaultRowHeight((short) 5);
    sheet.setDisplayGridlines(false);
    return sheet;
  }

  private HSSFSheet createSheet(HSSFWorkbook wb, int k)// 03
  {
    String sheetName = tableName.get(k);
    HSSFSheet sheet = wb.createSheet(sheetName);// 创建一个sheet
    sheet.setDefaultColumnWidth(12);
    sheet.setDefaultRowHeight((short) 5);
    sheet.setDisplayGridlines(false);
    return sheet;
  }

  private XSSFCellStyle createBodyStyle(XSSFWorkbook wb, XSSFFont bodyFont) {
    XSSFCellStyle bodystyle = wb.createCellStyle();// 设置内容样式
    bodystyle.setFont(bodyFont);
    bodystyle.setAlignment(HorizontalAlignment.CENTER);
    bodystyle.setVerticalAlignment(VerticalAlignment.CENTER);
    bodystyle.setBorderBottom(BorderStyle.THIN); // 下边框
    bodystyle.setBorderLeft(BorderStyle.THIN);// 左边框
    bodystyle.setBorderTop(BorderStyle.THIN);// 上边框
    bodystyle.setBorderRight(BorderStyle.THIN);// 右边框
    return bodystyle;
  }

  private HSSFCellStyle createBodyStyle(HSSFWorkbook wb, HSSFFont bodyFont) {
    HSSFCellStyle bodystyle = wb.createCellStyle();// 设置内容样式
    bodystyle.setFont(bodyFont);
    bodystyle.setAlignment(HorizontalAlignment.CENTER);
    bodystyle.setVerticalAlignment(VerticalAlignment.CENTER);
    bodystyle.setBorderBottom(BorderStyle.THIN); // 下边框
    bodystyle.setBorderLeft(BorderStyle.THIN);// 左边框
    bodystyle.setBorderTop(BorderStyle.THIN);// 上边框
    bodystyle.setBorderRight(BorderStyle.THIN);// 右边框
    return bodystyle;
  }

  private XSSFCellStyle createHeadStyle(XSSFWorkbook wb) {
    // 07-首行字体样式
    XSSFCellStyle headStyle = wb.createCellStyle();
    headStyle.setAlignment(HorizontalAlignment.CENTER);
    headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    XSSFFont headFont = wb.createFont();
    headFont.setBold(true);// 粗体显示
    headFont.setFontHeightInPoints((short) 15);// 设置字体大小
    headStyle.setFont(headFont);
    return headStyle;
  }

  private HSSFCellStyle createHeadStyle(HSSFWorkbook wb) {
    // 03-首行字体样式
    HSSFCellStyle headStyle = wb.createCellStyle();
    headStyle.setAlignment(HorizontalAlignment.CENTER);
    headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    HSSFFont headFont = wb.createFont();
    headFont.setBold(true);// 粗体显示
    headFont.setFontHeightInPoints((short) 15);// 设置字体大小
    headStyle.setFont(headFont);
    return headStyle;
  }


}
