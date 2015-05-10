package org.lmind.atl.core.dom;

import java.util.ArrayList;

public class AtlElement {
	
	private AtlElement parent;
	
	private String body;
	
	private ArrayList<AtlElement> children;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public AtlElement getParent() {
		return parent;
	}
	
	public int getChildrenNum() {
		if (children == null)
			return 0;
		return children.size();
	}
	
	public void append(AtlElement child, int index) {
		if (children == null) {
			children = new ArrayList<AtlElement>();
		}
		children.add(index, child);
		child.parent = this;
	}
	
	public void append(AtlElement child) {
		append(child, getChildrenNum());
	}
}
