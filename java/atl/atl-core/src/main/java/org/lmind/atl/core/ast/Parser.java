package org.lmind.atl.core.ast;

import java.io.Reader;
import java.io.StringReader;

public class Parser {
	
	public AtlNode parser(String s) {
		try {
			return parser(new StringReader(s));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public AtlNode parser(Reader r) throws ParseException {
		try {
			AtlNode node = new AtlParser(r).Directives();
			return node;
		} catch (TokenMgrError e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage());
		}
	}
}
