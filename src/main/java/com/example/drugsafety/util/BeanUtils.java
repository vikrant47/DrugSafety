package com.example.drugsafety.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.drugsafety.controller.UserController;

public class BeanUtils {
	private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
	public static <T> T merge(T source, T target) {
		Class<?> clazz = source.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(target);
				if (value != null) {
					field.set(source, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}

		}
		return source;
	}

	public static <T> T update(T source, Map<String, Object> data) {
		Class<?> clazz = source.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Object value = data.get(field.getName());
			try {
				if (value != null) {
					field.set(source, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		return source;
	}
}
