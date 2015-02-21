package org.lmind.jel.core.ast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.function.Consumer;
import java.util.function.Function;


import org.junit.Test;

public class AstParserTest {
	
	
	@Test
	public void test() throws Exception {
		
		w("/sample.txt", line -> {
			AstParser parser = new AstParser(line);
			try {
				SimpleNode node = parser.parser();
				node.dump("");
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		});
		
	}
	
	private BufferedReader read(String file) throws UnsupportedEncodingException {
		InputStream input = AstParserTest.class.getResourceAsStream(file);
		return new BufferedReader(new InputStreamReader(input, "utf-8"));
	}
	
	private void w(String file, Consumer<Reader> f) throws IOException {
		try (BufferedReader r = read("/sample.txt")) {
			String line = null;
			int n = 1;
			while((line = r.readLine()) != null) {
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
