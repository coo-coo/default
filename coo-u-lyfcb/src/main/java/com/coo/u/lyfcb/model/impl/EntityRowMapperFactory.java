package com.coo.u.lyfcb.model.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.kingstar.ngbf.s.util.GenericsUtil;
import com.kingstar.ngbf.s.util.PubString;

public class EntityRowMapperFactory<T> {
	private static final Logger logger = Logger
			.getLogger(EntityRowMapperFactory.class);
	@SuppressWarnings("rawtypes")
	private static final Map<String, ParameterizedRowMapper> map = new ConcurrentHashMap<String, ParameterizedRowMapper>();

	@SuppressWarnings("unchecked")
	public static <T> ParameterizedRowMapper<T> getEntityRowMapper(
			final Class<T> c) {
		String key = c.getSimpleName();
		if (map.get(key) == null) {
			final List<Field> fields = GenericsUtil.getClassSimpleFields(c,
					true);
			ParameterizedRowMapper<T> mapper = new ParameterizedRowMapper<T>() {
				public T mapRow(ResultSet rs, int rowNum) throws SQLException {
					T t = null;
					try {
						t = c.newInstance();
						for (Field field : fields) {
							EntityColumnMapper ano = field
									.getAnnotation(EntityColumnMapper.class);
							String column = null;
							logger.debug("EntityColumnMapper.ano=" + ano
									+ ",filed=" + field.getName());
							if (ano != null
									&& !PubString.isNullOrSpace(ano
											.columnName())) {
								column = ano.columnName();
							} else {
								column = field.getName();
							}
							String type = field.getType().getSimpleName();
							Class<?> tc = field.getType();
							Object value = null;
							if ("String".equals(type)) {
								value = rs.getString(column);
							} else if ("Long".equals(type)
									|| "long".equals(type)) {
								value = rs.getLong(column);
							} else if ("Integer".equals(type)
									|| "int".equals(type)) {
								value = rs.getInt(type);
							} else if (tc == BigDecimal.class) {
								value = rs.getBigDecimal(column);
							} else if (tc == Double.class || tc == double.class) {
								value = rs.getDouble(column);
							} else if (tc == Short.class || tc == short.class) {
								value = rs.getShort(column);
							} else if (tc == Byte.class || tc == byte.class) {
								value = rs.getByte(column);
							}
							PropertyUtils.setSimpleProperty(t, field.getName(),
									value);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return t;
				}
			};
			map.put(key, mapper);
		}
		return map.get(key);
	}
}
