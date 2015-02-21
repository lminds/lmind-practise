package org.lmind.jel.core.ast;

import java.io.StringReader;

import org.junit.Test;

public class TreeOptimizerTest {
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTreeOptimizer() throws Exception {
		
		eval("a");
		
		eval("a + 2 - 3.4");
		
		eval("(a + b) + c");
	}
	
	private void eval(String s) throws Exception {
		AstParser parser = new AstParser(new StringReader(s));
		JelNode node = parser.parser();
		node = new TreeOptimizer().optimize(node);
		node.dump(">");
	}
}
