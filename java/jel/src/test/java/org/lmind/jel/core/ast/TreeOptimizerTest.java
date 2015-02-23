package org.lmind.jel.core.ast;

import javax.script.SimpleScriptContext;

import org.junit.Test;
import org.lmind.jel.core.JelEngine;
import org.lmind.jel.core.JelObject;
import org.lmind.jel.core.util.SimpleJelObject;

public class TreeOptimizerTest {
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTreeOptimizer() throws Exception {
		
//		eval("(a + b).b.c");
		
		String s = "a.name() ? 2 : a.b";
		eval(s);
		
		JelEngine engine = new JelEngine();
		SimpleScriptContext context = new SimpleScriptContext();
		context.setAttribute("a", new SimpleJelObject(new B()), SimpleScriptContext.ENGINE_SCOPE);
		JelObject r = engine.evalute(s, context);
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
	
	public static class B {
		
		private String b = "hello world";

		public String getB() {
			return b;
		}
		
		public String name() {
			return b;
		}
		
	}
}
