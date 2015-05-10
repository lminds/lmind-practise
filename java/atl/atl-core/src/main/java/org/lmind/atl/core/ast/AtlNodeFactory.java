package org.lmind.atl.core.ast;

public class AtlNodeFactory {

	public static AtlNode jjtCreate(int id) {
		return new AtlNode(id);
	}
}
