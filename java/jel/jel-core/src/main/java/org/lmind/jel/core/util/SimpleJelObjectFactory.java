package org.lmind.jel.core.util;

import java.util.Map;

import org.lmind.jel.core.JelBoolean;
import org.lmind.jel.core.JelNumber;
import org.lmind.jel.core.JelObject;
import org.lmind.jel.core.JelObjectFactory;
import org.lmind.jel.core.JelSet;
import org.lmind.jel.core.JelString;

public class SimpleJelObjectFactory implements JelObjectFactory {
	
	public static final JelBoolean TRUE = new SimpleJelBoolean(true);
	
	public static final JelBoolean FALSE = new SimpleJelBoolean(false);

	@Override
	public JelNumber createNumber(double v) {
		return new SimpleJelNumber(v);
	}

	@Override
	public JelString createString(String s) {
		return new SimpleJelString(s);
	}

	@Override
	public JelBoolean createBoolean(boolean b) {
		return b ? TRUE : FALSE;
	}

	@Override
	public JelSet createSet(Map<String, JelObject> v) {
		return new SimpleJelSetObject(v);
	}
}
