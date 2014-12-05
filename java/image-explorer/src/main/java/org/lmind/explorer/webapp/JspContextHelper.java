package org.lmind.explorer.webapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class JspContextHelper {

	public String encodeURIComponent(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20")
				.replaceAll("\\%21", "!").replaceAll("\\%27", "'")
				.replaceAll("\\%28", "(").replaceAll("\\%29", ")")
				.replaceAll("\\%7E", "~");
	}
	
}
