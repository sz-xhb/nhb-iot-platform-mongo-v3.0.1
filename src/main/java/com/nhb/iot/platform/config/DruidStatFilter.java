//package com.nhb.iot.platform.config;
//
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebInitParam;
//
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.druid.support.http.WebStatFilter;
//
///**
// * Druid的StatFilter http://blog.csdn.net/catoop/
// */
////@Component
//@Order(value = 2) 
//@WebFilter(filterName = "druidWebStatFilter", 
//		   urlPatterns = "/*", 
//		   initParams = {@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
//})
//public class DruidStatFilter extends WebStatFilter {
//	
//	public void test(){
//		System.out.println("test method");
//	}
//	
//}
