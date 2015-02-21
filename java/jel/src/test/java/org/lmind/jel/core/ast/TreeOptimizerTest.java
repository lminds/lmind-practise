package org.lmind.jel.core.ast;

import java.io.StringReader;

import org.junit.Test;
import org.lmind.jel.core.Context;
import org.lmind.jel.core.Template;

public class TreeOptimizerTest {
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTreeOptimizer() throws Exception {
		
		eval("a + b + c");
		
		Template t = new Template();
		JelNode n = compile("1 + 2.1 - 3");
		Context context = new Context();
		t.run(n, context);
		Object r = context.popStack();
		System.out.println(r);
	}
	
	private JelNode compile(String s) throws Exception {
		Parser p = new Parser();
		return p.parser(s);
	}
	
	private void eval(String s) throws Exception {
		JelNode node = compile(s);
		node.dump(">");
	}
}
