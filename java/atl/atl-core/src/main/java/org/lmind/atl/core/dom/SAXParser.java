package org.lmind.atl.core.dom;

import org.lmind.atl.core.ast.AtlNode;
import org.lmind.atl.core.ast.Parser;
import org.lmind.atl.core.ast.Token;

public class SAXParser {
	
	private AtlSaxHandler handler;

	private String source;

	private int index = 0;

	public SAXParser(String source) {
		this.source = source;
	}

	public void parse() {

		int start = index;
		StringBuilder sb = new StringBuilder();
		do {
			index = source.indexOf('@', index);
			
			if (index != -1) {
				sb.append(source.substring(start, index));
				int c = peek();
				switch (c) {
				case '@':
					index += 2;
					sb.append('@');
					break;
				case '#':
					index += 2;
					sb.append('#');
					break;
				default:
					handler.handleText(sb.toString());
					sb = new StringBuilder();
					enterTag();
				}
			} else {
				sb.append(source.substring(start));
			}
			
			start = index;
			
		} while (index != -1);
		System.out.println(sb.toString());
	}

	private void enterTag() {

		String s = source.substring(index);
		
		Parser p = new Parser();
		AtlNode n = p.parser(s);
		Token t = n.jjtGetLastToken();
		
		String fullName = ((AtlNode)n.jjtGetChild(0)).getImage();
		String body = ((AtlNode)n.jjtGetChild(1)).getImage();
		String[] arr = fullName.split(":");
		String domain = arr[0];
		String name = arr[1];
		
		handler.handleDirective(name, domain, body);
		
		index += t.endColumn;
	}

	private int peek() {
		if (source.length() == index + 1) {
			return -1;
		}
		
		
//		Character.isJavaIdentifierPart(ch)
		return source.charAt(index + 1);
	}
	
	public static void main(String[] args) {

		new SAXParser("hello@@@#@在2:好1 a+b #world").parse();
	}
}
