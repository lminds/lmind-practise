package org.lmind.atl.core.dom;

public interface AtlSaxHandler {

	void handleText(String text);
	
	void handleDirective(String name, String domain, String body);
}
