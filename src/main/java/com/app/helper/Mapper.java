package com.app.helper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapper {
	public static Map<String, Object> toMapValue(Object object, String... fields) {
		Map<String, Object> result = null;
		if (object != null) {
			result = new HashMap<>();
			Class<? extends Object> clazz = object.getClass();
			for (String field : fields) {
				String s = field.substring(0, 1).toUpperCase() + field.substring(1);
				try {
					result.put(field, clazz.getMethod("get" + s).invoke(object));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static List<Map<String, Object>> toMapValues(List<?> objects, String... fields) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (Object object : objects) {
			result.add(toMapValue(object, fields));
		}
		return result;
	}

	public static <T> T convert(T to, T from) {
		Class<? extends Object> clazz = to.getClass();
		Method[] declaredMethods = clazz.getMethods();
		for (Method method : declaredMethods) {
			String name = method.getName();
			boolean isCollection = Collection.class.isAssignableFrom(method.getReturnType());
			if (name.startsWith("get") && !isCollection && !name.equals("getClass")) {
				try {
					Object value = method.invoke(from);
					Method setter = clazz.getMethod(name.replace("get", "set"), method.getReturnType());
					if (value != null) {
						setter.invoke(to, value);
					}
				} catch (Exception e) {
				}
			}
		}

		return to;
	}

	@Deprecated
	public static <T> T convertIgnoreFields(T to, T from, String...ignoreFields) {
		T newFrom = toObjectIgnoreFields(from, ignoreFields);
		Class<? extends Object> clazz = to.getClass();
		Method[] declaredMethods = clazz.getMethods();
		for (Method method : declaredMethods) {
			String name = method.getName();
			boolean isCollection = Collection.class.isAssignableFrom(method.getReturnType());
			if (name.startsWith("get") && !isCollection && !name.equals("getClass")) {
				try {
					Object value = method.invoke(newFrom);
					Method setter = clazz.getMethod(name.replace("get", "set"), method.getReturnType());
					if (value != null) {
						setter.invoke(to, value);
					}
				} catch (Exception e) {
				}
			}
		}

		return to;
	}

	public static <T> T toObjectIgnoreFields(T object, String... ignoreFields) {
		Class<? extends Object> clazz = object.getClass();
		for (String ignoreField : ignoreFields) {
			String s = ignoreField.substring(0, 1).toUpperCase() + ignoreField.substring(1);
			try {
				Class<?> type = clazz.getDeclaredField(ignoreField).getType();
				Method method = clazz.getMethod("set" + s, type);
				method.invoke(object, (Object) null);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return object;
	}

	public static <T> List<T> toListIgnoreFields(List<T> objects, String... ignoreFields) {
		List<T> result = new ArrayList<>();
		objects.forEach(object -> {
			result.add(toObjectIgnoreFields(object, ignoreFields));
		});
		return result;
	}
}
