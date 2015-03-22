package org.lmind.jel.core.util;

import org.lmind.jel.core.JelBoolean;
import org.lmind.jel.core.JelObject;

public class SimpleJelBoolean implements JelBoolean {
	
	private boolean value;

	public SimpleJelBoolean(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public JelObject getProperty(String name) {
		return null;
	}

	@Override
	public boolean value() {
		return value;
	}

}
