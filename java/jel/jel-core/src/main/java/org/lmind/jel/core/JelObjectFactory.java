package org.lmind.jel.core;

import java.util.Map;

public interface JelObjectFactory {

	JelNumber createNumber(double v);
	
	JelString createString(String s);
	
	JelBoolean createBoolean(boolean b);
	
	JelSet createSet(Map<String, JelObject> v);
}
