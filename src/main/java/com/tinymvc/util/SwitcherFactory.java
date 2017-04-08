package com.tinymvc.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SwitcherFactory {
	
	private final Log log = LogFactory.getLog(getClass());

	private Map<Class<?>, Switcher<?>> map = new HashMap<Class<?>, Switcher<?>>();

	public SwitcherFactory() {
		loadParameters();
	}
	/*
     * 获取转换接口
     */
	private void loadParameters() {
		Switcher<?> switcher = null;
		switcher = new TypeConvertUtil.BooleanSwitcher();
		map.put(boolean.class, switcher);
		map.put(Boolean.class, switcher);

		switcher = new TypeConvertUtil.CharacterSwitcher();
		map.put(char.class, switcher);
		map.put(Character.class, switcher);

		switcher = new TypeConvertUtil.ByteSwitcher();
		map.put(byte.class, switcher);
		map.put(Byte.class, switcher);

		switcher = new TypeConvertUtil.ShortSwitcher();
		map.put(short.class, switcher);
		map.put(Short.class, switcher);

		switcher = new TypeConvertUtil.IntegerSwitcher();
		map.put(int.class, switcher);
		map.put(Integer.class, switcher);

		switcher = new TypeConvertUtil.LongSwitcher();
		map.put(long.class, switcher);
		map.put(Long.class, switcher);

		switcher = new TypeConvertUtil.FloatSwitcher();
		map.put(float.class, switcher);
		map.put(Float.class, switcher);

		switcher = new TypeConvertUtil.DoubleSwitcher();
		map.put(double.class, switcher);
		map.put(Double.class, switcher);
	}
    
	public Object switcher(Class<?> clazz, String s) throws Exception {
		Switcher<?> switcher = map.get(clazz);
		return switcher.switcher(s);
	}
}
