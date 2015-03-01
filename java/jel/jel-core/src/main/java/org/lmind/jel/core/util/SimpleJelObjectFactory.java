package org.lmind.jel.core.util;

import org.lmind.jel.core.JelBoolean;
import org.lmind.jel.core.JelNumber;
import org.lmind.jel.core.JelObjectFactory;
import org.lmind.jel.core.JelString;

public class SimpleJelObjectFactory implements JelObjectFactory {
	
	public static final JelBoolean TRUE = new SimpleJelBoolean(true);
	
	public static final JelBoolean FALSE = new SimpleJelBoolean(false);

	@Override
	public JelNumber numberValue(double v) {
		return new SimpleJelNumber(v);
	}

	@Override
	public JelString stringValue(String s) {
		return new SimpleJelString(s);
	}

	@Override
	public JelBoolean booleanValue(boolean b) {
		return b ? TRUE : FALSE;
	}
}
