package org.lmind.jel.core.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import org.lmind.jel.core.JelObject;
import org.lmind.jel.core.JelSet;

public class SimpleJelSetObject implements JelSet {
	
	private Map<String, JelObject> value;

	public SimpleJelSetObject(Map<String, JelObject> value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public JelObject getProperty(String name) {
		return value.get(name);
	}

	@Override
	public Enumeration<String> getKeys() {
		final Iterator<String> iter = value.keySet().iterator();
		return new Enumeration<String>() {
			public boolean hasMoreElements() {
				return iter.hasNext();
			}
			public String nextElement() {
				return iter.next();
			}
		};
	}

}
