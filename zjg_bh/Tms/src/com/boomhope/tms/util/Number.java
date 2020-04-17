package com.boomhope.tms.util;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * 序列标记
 * @author Ben-Book
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Number {
	int id();
}
