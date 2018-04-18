package com.nhb.iot.platform.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nhb.utils.nhb_utils.common.StringUtil;

public class JsonUtil {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();

		// 设置FAIL_ON_EMPTY_BEANS属性，当序列化空对象不要抛异常
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		// 设置FAIL_ON_UNKNOWN_PROPERTIES属性，当JSON字符串中存在Java对象没有的属性，忽略
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * Convert Object to JsonString
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static String jsonObj2Sting(Object jsonObj) {
		String jsonString = null;

		try {
			jsonString = objectMapper.writeValueAsString(jsonObj);
		} catch (IOException e) {
			logger.error("pasre json Object[{}] to string failed.", jsonString);
		}

		return jsonString;
	}

	/**
	 * Convert JsonString to Simple Object
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T jsonString2SimpleObj(String jsonString, Class<T> cls) {
		T jsonObj = null;

		try {
			jsonObj = objectMapper.readValue(jsonString, cls);
		} catch (IOException e) {
			logger.error("pasre json Object[{}] to string failed.", jsonString);
		}

		return jsonObj;
	}

	/**
	 * Method that will convert object to the ObjectNode.
	 *
	 * @param value
	 *            the source data; if null, will return null.
	 * @return the ObjectNode data after converted.
	 * @throws JsonException
	 */
	public static <T> ObjectNode convertObject2ObjectNode(T object) throws Exception {
		if (null == object) {
			return null;
		}

		ObjectNode objectNode = null;

		if (object instanceof String) {
			objectNode = convertJsonStringToObject((String) object, ObjectNode.class);
		} else {
			objectNode = convertValue(object, ObjectNode.class);
		}

		return objectNode;
	}

	/**
	 * Method that will convert the json string to destination by the type(cls).
	 * 
	 * @param jsonString
	 *            the source json string; if null, will return null.
	 * @param cls
	 *            the destination data type.
	 * @return
	 * @throws JsonException
	 */
	public static <T> T convertJsonStringToObject(String jsonString, Class<T> cls) throws Exception {
		if (StringUtil.isNullOrEmpty(jsonString)) {
			return null;
		}

		try {
			T object = objectMapper.readValue(jsonString, cls);
			return object;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Method that will convert from given value into instance of given value
	 * type.
	 * 
	 * @param fromValue
	 * @param toValueType
	 * @return
	 * @throws JsonException
	 */
	public static <T> T convertValue(Object fromValue, Class<T> toValueType) throws Exception {
		try {
			return objectMapper.convertValue(fromValue, toValueType);
		} catch (IllegalArgumentException e) {
			throw new Exception(e);
		}
	}
	
}
