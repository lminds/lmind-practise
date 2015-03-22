package org.lmind.jel.core.util;

import org.lmind.jel.core.JelObject;
import org.lmind.jel.core.JelString;

public class SimpleJelString implements JelString {
	
	private String value;

	public SimpleJelString(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public JelObject getProperty(String name) {
		return null;
	}

}
