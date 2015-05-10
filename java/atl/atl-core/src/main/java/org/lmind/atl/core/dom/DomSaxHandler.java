package org.lmind.atl.core.dom;

public class DomSaxHandler implements AtlSaxHandler {
	
	private AtlElement root;
	
	private AtlElement current;

	public DomSaxHandler() {
		root = new AtlElement();
		current = root;
	}

	@Override
	public void handleText(String text) {
		AtlElement el = new AtlElement();
		el.setBody(text);
		current.append(el);
	}

	@Override
	public void handleDirective(String name, String domain, String body) {
		
		if ("end".equals(name)) {
			current = current.getParent();
		} else {
			DirectiveElement directive = new DirectiveElement();
			directive.setName(name);
			directive.setDomain(domain);
			directive.setBody(body);
			current.append(directive);
			current = directive;
		}
	}

}
