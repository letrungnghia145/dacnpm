package com.app.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TypeCaster {
	public static Object castToReturnType(String value, Class<?> returnType) {
		if (returnType.isAssignableFrom(Date.class)) {
			try {
				return new SimpleDateFormat("dd-MM-yyyy").parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (returnType.isAssignableFrom(Integer.class)) {
			return Integer.parseInt(value);
		}
		if (returnType.isAssignableFrom(BigDecimal.class)) {
			return BigDecimal.valueOf(Double.parseDouble(value));
		}
		return value;
	}

	public static List<Object> castToReturnType(String[] values, Class<?> returnType) {
		List<Object> objects = new ArrayList<>();
		for (String value : values) {
			objects.add(castToReturnType(value, returnType));
		}
		return objects;
	}
}
