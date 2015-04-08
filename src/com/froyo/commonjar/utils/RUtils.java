package com.froyo.commonjar.utils;

import java.lang.reflect.Field;

import com.froyo.commonjar.R;

public  class RUtils {

	public static int getId(String name) {
		try {
			Class<R.id> cls = R.id.class;
			Field f = cls.getDeclaredField(name);
			f.setAccessible(true);
			return f.getInt(cls);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
