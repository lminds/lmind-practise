package org.lmind.jel.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.lmind.jel.core.ast.AstParserTest;
import org.lmind.jel.core.ast.Func;

public class JelEngineTest {

	@Test
	public void test() throws Exception {
		exec("/relational-case.txt", null);
		exec("/logic-case.txt", null);
		exec("/integer-calculation-case.txt", null);
		exec("/ternary-case.txt", null);
		exec("/equality-case.txt", null);
	}
	
	private BufferedReader read(String file) {
		InputStream input = AstParserTest.class.getResourceAsStream(file);
		return new BufferedReader(new InputStreamReader(input, Charset.forName("utf-8")));
	}
	
	private Iterator<String> lines(String res) {
		Reader r = read(res);
		return IOUtils.lineIterator(r);
	}
	
	private JelObject eval(String script) {
		JelEngine engine = new JelEngine();
		ScriptContext z = new SimpleScriptContext();
		return engine.eval(script, z);
	}
	
	private void exec(String res, Func func) {
		Iterator<String> iter = lines(res);
		int n = 1;
		while (iter.hasNext()) {
			try {
				JelObject r = eval(iter.next());
				if (!(r instanceof JelBoolean)) {
					throw new RuntimeException("wrong type " + n);
				}
				boolean b = ((JelBoolean)r).value();
				if (!b) {
					throw new RuntimeException("wrong value " + n);
				}
			} catch (ExpressionException e) {
				e.printStackTrace();
				throw new RuntimeException("error at line " + n, e);
			}
			n++;
		}
	}
}
