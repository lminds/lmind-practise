package org.lmind.jel.core.util;

import org.lmind.jel.core.ExpressionException;
import org.lmind.jel.core.JelBoolean;
import org.lmind.jel.core.JelNumber;
import org.lmind.jel.core.JelObject;
import org.lmind.jel.core.JelString;

public class SimpleJelObjectUtil {

	public static Object wrap(JelObject arg) {
		Object r = null;
		if (arg instanceof JelNumber) {
			r = ((JelNumber)arg).doubleValue();
		} else if (arg instanceof JelBoolean) {
			r = ((JelBoolean)arg).value();
		} else if (arg instanceof JelString) {
			r = ((JelString)arg).toString();
		} else if (arg instanceof SimpleJelObject) {
			r = ((SimpleJelObject)arg).getValue();
		} else {
			r = arg;
		}
		return r;
	}
	
	public static JelObject unwrap(Object arg) {
		JelObject r = null;
		if (arg instanceof JelObject) {
			r = (JelObject)arg;
		} else if (arg instanceof String) {
			r = new SimpleJelString((String)arg);
		} else if (arg instanceof Boolean) {
			r = new SimpleJelBoolean((Boolean)arg);
		} else if (arg instanceof Number) {
			r = new SimpleJelNumber(((Number)arg).doubleValue());
		} else {
			throw new ExpressionException();
		}
		return r;
	}
}
