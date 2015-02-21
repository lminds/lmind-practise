package org.lmind.jel.core.ast;

public class JelNodeFactory {

	public static JelNode jjtCreate(int id) {
		return new JelNode(id);
	}
}
