package org.lmind.atl.core.dom;

public class DirectiveElement extends AtlElement {

	private String name;
	
	private String domain;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
