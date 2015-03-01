package org.lmind.jel.core;

public interface JelCallable extends JelObject {

	JelObject call(JelObject[] args);
}
