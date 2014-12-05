package org.lmind.explorer.core;

public class Mime {

	public static String getMimeType(String name) {
		String suffix = name.substring(name.length() - 4);
		if (".png".equalsIgnoreCase(suffix)) {
			return "image/png";
		} else if (".jpg".equalsIgnoreCase(suffix)) {
			return "image/jpeg";
		}  else if (".gif".equalsIgnoreCase(suffix)) {
			return "image/gif";
		} else {
			return "";
		}
	}
}
