package com.app.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			String[] split = exp.split(":");
			String operator = split[0];
			String value = split[1];
			Specification specification = createSpecification(operator, attribute, value);
			this.specification = this.specification == null ? specification : this.specification.and(specification);
		});
	}

	private Specification<T> createSpecification(String operator, String attribute, String value) {
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
				return cb.in(field).value(cb.literal(TypeCaster
						.castToReturnType(value.substring(1, value.length() - 1).split(","), field.getJavaType())));
			};
		case Operator.LIKE:

			break;
		case Operator.BETTWEEN:
			return (root, cq, cb) -> {
				String[] values = value.substring(1, value.length() - 1).split(",");
				Path field = root.get(attribute);
				return cb.between(field, (Expression) cb.literal(castToReturnType(values[0], field.getJavaType())),
						(Expression) cb.literal(castToReturnType(values[1], field.getJavaType())));
			};

		default:
			break;
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
}
