package org.lmind.jel.core.ast;

import java.io.Reader;
import java.io.StringReader;

public class Parser {
	
	public JelNode parser(String s) throws ParseException {
		return parser(new StringReader(s));
	}

	public JelNode parser(Reader r) throws ParseException {
		try {
			JelNode node = new JelParser(r).Expression();
//			return new TreeOptimizer().optimize(node);
			return node;
		} catch (TokenMgrError e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage());
		}
	}
}
