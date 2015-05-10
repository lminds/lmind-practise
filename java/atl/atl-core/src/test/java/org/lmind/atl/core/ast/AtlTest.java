package org.lmind.atl.core.ast;

import org.junit.Test;

public class AtlTest {

	@Test
	public void test() throws Exception {
		String s = "@中文1dadaw:kknd (aaa + b & \"@#$\\\"\"[]) #1212";
		Parser p = new Parser();
		AtlNode n = p.parser(s);
		Token t = n.jjtGetLastToken();
		n.dump("");
		System.out.println(s.substring(t.endColumn));
	}
}
