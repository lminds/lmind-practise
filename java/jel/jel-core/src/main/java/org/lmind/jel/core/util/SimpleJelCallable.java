package org.lmind.jel.core.util;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.lmind.jel.core.ExpressionException;
import org.lmind.jel.core.JelCallable;
import org.lmind.jel.core.JelObject;

public class SimpleJelCallable implements JelCallable {
	
	private Object object;
	
	private Method method;

	public SimpleJelCallable(Object object, Method method) {
		super();
		this.object = object;
		this.method = method;
	}

	@Override
	public JelObject propertyRead(String name) {
		return null;
	}

	@Override
	public JelObject call(JelObject[] args) {
		
		ArrayList<Object> list = new ArrayList<Object>();
		for (JelObject arg : args) {
			list.add(SimpleJelObjectUtil.wrap(arg));
		}
		
		Object r;
		try {
			r = method.invoke(object, list.toArray());
		} catch (Exception e) {
			throw new ExpressionException(e);
		}
		return SimpleJelObjectUtil.unwrap(r);
	}

}
