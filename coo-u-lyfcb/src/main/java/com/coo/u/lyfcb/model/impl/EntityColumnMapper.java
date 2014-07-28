package com.coo.u.lyfcb.model.impl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
// 指定目标, 必须包含方法
@Target({ ElementType.FIELD })
// 设置保持性
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EntityColumnMapper {
	public String columnName() default "";

}
