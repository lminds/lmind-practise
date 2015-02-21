package org.lmind.jel.core;

import java.util.Stack;

public class Context {

	private Stack<Object> stack = new Stack<Object>();
	
	public void pushStack(Object o) {
		stack.push(o);
	}
	
	public Object popStack() {
		return stack.pop();
	}
}
