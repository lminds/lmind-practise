package org.lmind.jel.core;

import javax.script.ScriptContext;

public interface JelExpression {

	JelObject eval(ScriptContext context);
}
