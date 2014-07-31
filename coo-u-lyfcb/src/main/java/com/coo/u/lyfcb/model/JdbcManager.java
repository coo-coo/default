package com.coo.u.lyfcb.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.coo.s.lyfcb.model.Column;
import com.kingstar.ngbf.s.util.GenericsUtil;
import com.kingstar.ngbf.s.util.NgbfRuntimeException;
import com.kingstar.ngbf.s.util.PubString;
import com.kingstar.ngbf.s.util.SpringContextFactory;

public class JdbcManager {

	private static Logger logger = Logger.getLogger(JdbcManager.class);

	/**
	 * 查询获得SQL全部数据
	 */
	public static List<?> find(String sql, Object[] params, Class<?> clz) {
		List<Object> list = new ArrayList<Object>();
		// 如果出错,打印日志信息,但是不报错

		// 获得List结果集
		Iterator<?> it = getTemplate().queryForList(sql, params).iterator();
		// 迭代循环，返回对象
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) it.next();
			Object instance = merge(map, clz);
			if (instance != null) {
				list.add(instance);
			}
		}
		return list;
	}

	/**
	 * 将对象合并成一个类
	 */
	public static Object merge(Map<String, Object> map, Class<?> clz) {
		try {
			Object instance = clz.newInstance();
			// 获得所有的字段,该字段可能对应有数据库的Column
			List<Field> fields = GenericsUtil.getClassSimpleFields(clz, true);
			for (Field field : fields) {
				// 获得field上的注解
				Column col = field.getAnnotation(Column.class);
				if (col != null) {
					// 定义数据库队列名称,缺省是字段名稱
					String colName = field.getName();
					if (!PubString.isNullOrSpace(col.name())) {
						colName = col.name();
					}
					// 获得值
					Object value = map.get(colName);
					if (value != null) {
						PropertyUtils.setSimpleProperty(instance,
								field.getName(), value);
					}
				}
			}
			return instance;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	/**
	 * 执行SQL语句
	 */
	public static void execute(String sql, Object[] params)
			throws NgbfRuntimeException {
		try {
			jdbcTemplate.update(sql, params);
		} catch (DataAccessException e) {
			throw new NgbfRuntimeException(e.getMessage(), e);
		}
	}

	private static JdbcTemplate jdbcTemplate;

	/**
	 * 获取JdbcTemplate
	 * 
	 * @return
	 */
	private static JdbcTemplate getTemplate() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextFactory
					.getSpringBean("jdbcTemplate");
		}
		return jdbcTemplate;
	}

}
