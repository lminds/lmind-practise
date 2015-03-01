package org.lmind.jel.core;

import javax.script.ScriptContext;

import org.lmind.jel.core.ast.JelNode;

public class JelExpressionImpl implements JelExpression {
	
	private JelEngine engine;
	
	private JelNode expression;

	public JelExpressionImpl(JelEngine engine, JelNode expression) {
		this.engine = engine;
		this.expression = expression;
	}

	@Override
	public JelObject eval(ScriptContext context) {
		return engine.evalNode(expression, context);
	}

}
