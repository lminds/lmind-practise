package org.lmind.jel.core;

import java.util.HashMap;
import java.util.Stack;

public class Context {

	private Stack<Object> stack = new Stack<Object>();
	
	private HashMap<String, Object> global = new HashMap<String, Object>();
	
	public void pushStack(Object o) {
		stack.push(o);
	}
	
	public Object popStack() {
		return stack.pop();
	}
	
	public void putObject(String name, Object obj) {
		global.put(name, obj);
	}
	
	public Object getObject(String name) {
		return global.get(name);
	}
}
