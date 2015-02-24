package org.lmind.jel.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JelUtils {

	private static final Pattern p = Pattern.compile(String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s",
			Pattern.quote("\\\n"), Pattern.quote("\\\t"), Pattern.quote("\\\b"),
			Pattern.quote("\\\r"), Pattern.quote("\\\f"), Pattern.quote("\\\\"),
			Pattern.quote("\\\""), Pattern.quote("\\\t"), "(\\\\u[0-9][0-9]*)"));
	
	public static String unescape(String s) {
		Matcher m = p.matcher(s);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		if (result) {
            do {
            	String g = m.group();
            	if ("\\\n".equals(g)) {
            		m.appendReplacement(sb, "\n");
            	} else if ("\\\t".equals(g)) {
            		m.appendReplacement(sb, "\t");
            	} else if ("\\\b".equals(g)) {
            		m.appendReplacement(sb, "\b");
            	} else if ("\\\r".equals(g)) {
            		m.appendReplacement(sb, "\r");
            	} else if ("\\\f".equals(g)) {
            		m.appendReplacement(sb, "\f");
            	} else if ("\\\\".equals(g)) {
            		m.appendReplacement(sb, "\\");
            	} else if ("\\\"".equals(g)) {
            		m.appendReplacement(sb, "\"");
            	} else if ("\\\t".equals(g)) {
            		m.appendReplacement(sb, "\t");
            	} else if (g.startsWith("\\u")) {
            		char[] ch = Character.toChars(Integer.valueOf(g.substring(2)));
            		m.appendReplacement(sb, new String(ch));
            	}
 
                result = m.find();
            } while (result);
            m.appendTail(sb);
            return sb.toString();
        }

		return s;
	}
	
	public static void main(String[] args) {
		String s = null;
		
		s = unescape("\\u49");
		System.out.println(s);
	}
}
