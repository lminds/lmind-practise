package org.lmind.jel.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

import org.lmind.jel.core.ExpressionException;
import org.lmind.jel.core.JelObject;

public class SimpleJelObject implements JelObject {
	
	private Object value;

	public SimpleJelObject(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public JelObject propertyRead(String name) {
		
		try {
			BeanInfo info = Introspector.getBeanInfo(value.getClass());
			for (PropertyDescriptor prop : info.getPropertyDescriptors()) {
				if (prop.getName().equals(name)) {
					Object o = prop.getReadMethod().invoke(value);
					return SimpleJelObjectUtil.unwrap(o);
				}
			}
			
			for (MethodDescriptor m : info.getMethodDescriptors()) {
				if (m.getName().equals(name)) {
					return new SimpleJelCallable(value, m.getMethod());
				}
			}
			
			return null;
		} catch (Exception e) {
			throw new ExpressionException(e);
		}
	}

}
