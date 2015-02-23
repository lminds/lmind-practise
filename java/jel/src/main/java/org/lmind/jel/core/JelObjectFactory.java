package org.lmind.jel.core;

public interface JelObjectFactory {

	JelNumber numberValue(double v);
	
	JelString stringValue(String s);
	
	JelBoolean booleanValue(boolean b);
}
