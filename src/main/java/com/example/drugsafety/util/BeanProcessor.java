package com.example.drugsafety.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.drugsafety.controller.UserController;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class BeanProcessor {

    private static final Logger logger = LoggerFactory.getLogger(BeanProcessor.class);

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

    public static HashMap<String, Object[]> diff(Object source, Object target) {
        HashMap<String, Object[]> changedMap = new HashMap<>();
        if (source.getClass() != target.getClass()) {
            return changedMap;
        }
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                Object sourceValue = field.get(source);
                Object targetValue = field.get(source);
                if (targetValue != null && !targetValue.equals(sourceValue) && field.getType().isPrimitive()) {
                    changedMap.put(field.getName(), new Object[]{source, target});
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
        return changedMap;
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }

    public static Object stringToWrapper(Class clazz, String value) {
        if (Boolean.class == clazz) {
            return Boolean.parseBoolean(value);
        }
        if (Byte.class == clazz) {
            return Byte.parseByte(value);
        }
        if (Short.class == clazz) {
            return Short.parseShort(value);
        }
        if (Integer.class == clazz) {
            return Integer.parseInt(value);
        }
        if (Long.class == clazz) {
            return Long.parseLong(value);
        }
        if (Float.class == clazz) {
            return Float.parseFloat(value);
        }
        if (Double.class == clazz) {
            return Double.parseDouble(value);
        }
        return value;
    }
}
