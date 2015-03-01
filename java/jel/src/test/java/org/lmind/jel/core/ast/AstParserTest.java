package org.lmind.jel.core.ast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.function.Consumer;

import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.lmind.jel.core.ExpressionException;
import org.lmind.jel.core.JelBoolean;
import org.lmind.jel.core.JelEngine;
import org.lmind.jel.core.JelObject;

public class AstParserTest {
	
	/**
	 * 比较运算符测试
	 * @throws Exception
	 */
	@Test
	public void testRelational() throws Exception {
		
		exec("/relational-case.txt", null);
		exec("/logic-case.txt", null);

	}
	
	
	@Test
	public void test() throws Exception {
		
		w("/sample.txt", line -> {
			Parser parser = new Parser();
			try {
				JelNode node = parser.parser(line);
				node.dump("");
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		});
		
	}
	
	private BufferedReader read(String file) {
		InputStream input = AstParserTest.class.getResourceAsStream(file);
		return new BufferedReader(new InputStreamReader(input, Charset.forName("utf-8")));
	}
	
	private Iterator<String> lines(String res) {
		Reader r = read(res);
		return IOUtils.lineIterator(r);
	}
	
	private void exec(String res, Func func) {
		JelEngine engine = new JelEngine();
		ScriptContext z = new SimpleScriptContext();
		
		Iterator<String> iter = lines(res);
		int n = 1;
		while (iter.hasNext()) {
			try {
				JelObject r = engine.eval(iter.next(), z);
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
	
	private void w(String file, Consumer<Reader> f) throws IOException {
		try (BufferedReader r = read(file)) {
			String line = null;
			int n = 1;
			while((line = r.readLine()) != null) {
				System.out.println(">" + file + " line" + n);
				try {
					f.accept(new StringReader(line));
				} catch (Throwable e) {
					e.printStackTrace();
					throw new RuntimeException("error at line " + n, e);
				}
				n++;
			}
		}
	}
}
