package org.lmind.jel.core.util;

import org.lmind.jel.core.JelNumber;
import org.lmind.jel.core.JelObject;

public class SimpleJelNumber implements JelNumber {
	
	private double value;

	public SimpleJelNumber(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (Math.round(value) == value) {
			return Long.valueOf((long)value).toString();
		}
		return String.valueOf(value);
	}

	@Override
	public JelObject propertyRead(String name) {
		return null;
	}

	@Override
	public double doubleValue() {
		return value;
	}

}
