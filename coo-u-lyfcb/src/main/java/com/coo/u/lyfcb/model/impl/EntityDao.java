package com.coo.u.lyfcb.model.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

//@Repository
public class EntityDao<T> {

//	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 执行sql语句 insert|update|delete都可执行
	 */
	public boolean execSql(String sql, Object[] params) {
		int num = jdbcTemplate.update(sql, params);
		return num > 0 ? true : false;
	}

	/**
	 * 创建数据库表
	 */
	public boolean createTableBySQL(String sql) {
		boolean flag = true;
		try {
			jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 返回所有对象
	 */
	@SuppressWarnings("unchecked")
	public List<T> execQuery(String sql, final Object[] params, final Class<T> t) {
		ParameterizedRowMapper<T> mapper = (ParameterizedRowMapper<T>) EntityRowMapperFactory
				.getEntityRowMapper(t);
		return getJdbcTemplate().query(sql, params, mapper);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
