package org.lmind.jel.core;


public class ExpressionOperators {

	public Object plus(Object a, Object b) {
		if (a instanceof Number) {
			if (b instanceof Number) {
				return ((Number)a).doubleValue() + ((Number)b).doubleValue();
			} else if (b instanceof String) {
				return a.toString() + (String)b;
			}
		} else if (a instanceof String) {
			if (b instanceof Number) {
				return (String)a + ((Number)b).doubleValue();
			} else if (b instanceof String) {
				return (String)a + (String)b;
			}
		}
		throw new ExpressionException("plus operator unsupported for " + className(a) + " and " + className(b));
	}
	
	public Object subtract(Object a, Object b) {
		
		if (a instanceof Number) {
			if (b instanceof Number) {
				return ((Number)a).doubleValue() - ((Number)b).doubleValue();
			}
		}
		throw new ExpressionException("plus operator unsupported for " + className(a) + " and " + className(b));
	}
	
	private String className(Object a) {
		return a == null ? "null" : a.getClass().getName();
	}
}
