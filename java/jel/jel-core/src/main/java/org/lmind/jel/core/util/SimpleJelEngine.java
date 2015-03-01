package org.lmind.jel.core.util;

import org.lmind.jel.core.JelEngine;

public class SimpleJelEngine extends JelEngine {

	public SimpleJelEngine() {
		setObjectFactory(new SimpleJelObjectFactory());
	}

}
