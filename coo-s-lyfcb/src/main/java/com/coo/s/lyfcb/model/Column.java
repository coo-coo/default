package com.coo.s.lyfcb.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
//指定目标, 必须包含方法
@Target( { ElementType.FIELD })
//设置保持性
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Column {
	
	/**
	 * 数据库字段名称，当为空,表示和字段一致
	 * @since 0.1.8.0
	 */
	public String name() default "";
	
	/**
	 * 字段注释，用于编程理解
	 * @since 0.1.8.0
	 */
	public String label() default "";
	
	/**
	 * 字段长度,备用字段
	 * @since 0.1.8.0
	 */
	public int length() default 0;
	
}