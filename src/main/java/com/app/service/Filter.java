package com.app.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.app.config.constant.filter.Operator;
import com.app.helper.TypeCaster;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Getter
@Setter
public class Filter<T> {
	private Specification<T> specification;
	private Pageable pageable;

	public Filter(Map<String, String> filters) {
		filters.forEach((attribute, exp) -> {
			Specification specification = null;
			try {
				String[] split = exp.split(":");
				String operator = split[0];
				String value = split[1];
				specification = createSpecification(operator, attribute, value);
			} catch (Exception e) {
			}
			this.specification = this.specification == null ? specification : this.specification.and(specification);
		});
	}

	private Specification<T> createSpecification(String operator, String attribute, String value) throws Exception {
		try {
			switch (operator) {
			case Operator.GREATER_THAN:
				return (root, cq, cb) -> {
					Path field = root.get(attribute);
					return cb.greaterThan((Expression) field,
							(Expression) cb.literal(castToReturnType(value, field.getJavaType())));
				};
			case Operator.GREATER_THAN_EQUAL:
				return (root, cq, cb) -> {
					Path field = root.get(attribute);
					return cb.greaterThanOrEqualTo((Expression) field,
							(Expression) cb.literal(castToReturnType(value, field.getJavaType())));
				};
			case Operator.LESS_THAN:
				return (root, cq, cb) -> {
					Path field = root.get(attribute);
					return cb.lessThan((Expression) field,
							(Expression) cb.literal(castToReturnType(value, field.getJavaType())));
				};
			case Operator.LESS_THAN_EQUAL:
				return (root, cq, cb) -> {
					Path field = root.get(attribute);
					return cb.lessThanOrEqualTo((Expression) field,
							(Expression) cb.literal(castToReturnType(value, field.getJavaType())));
				};
			case Operator.EQUAL:
				// Date note
				return (root, cq, cb) -> {
					Path field = root.get(attribute);
					return cb.equal((Expression) field,
							(Expression) cb.literal(castToReturnType(value, field.getJavaType())));
				};
			case Operator.IN:
				return (root, cq, cb) -> {
					Path field = root.get(attribute);
					return cb.in(field)
							.value(cb.literal(TypeCaster.castToReturnType(value.split(","), field.getJavaType())));
				};
			case Operator.LIKE:
				return (root, cq, cb) -> {
					return cb.like(root.get(attribute), value);
				};
			case Operator.BETTWEEN:
				return (root, cq, cb) -> {
					String[] values = value.split(",");
					Path field = root.get(attribute);
					return cb.between(field, (Expression) cb.literal(castToReturnType(values[0], field.getJavaType())),
							(Expression) cb.literal(castToReturnType(values[1], field.getJavaType())));
				};

			case Operator.NOT_IN:
				return (root, cq, cb) -> {
					String[] inputs = value.split(",");
					List<Object> objects = castToReturnType(Arrays.asList(inputs), root.get(attribute).getJavaType());
					return root.get(attribute).in(objects).not();
				};

			default:
				break;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return null;
	}

	private Object castToReturnType(String value, Class<?> returnType) {
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
		if (returnType.isAssignableFrom(Long.class)) {
			return Long.parseLong(value);
		}
		if (returnType.isAssignableFrom(BigDecimal.class)) {
			return BigDecimal.valueOf(Double.parseDouble(value));
		}
		return value;
	}

	private List<Object> castToReturnType(List<String> values, Class<?> returnType) {
		List<Object> list = new ArrayList<>();
		for (String string : values) {
			list.add(castToReturnType(string, returnType));
		}
		return list;
	}
}
