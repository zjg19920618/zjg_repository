package com.boomhope.tms.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 字段排序
 * @author Ben-Book
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Inherited
public @interface FieldSort {
	int NO();

	int length() default 0;
}
