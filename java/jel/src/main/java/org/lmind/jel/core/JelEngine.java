package org.lmind.jel.core;

import java.io.Reader;
import java.io.StringReader;

import javax.script.ScriptContext;

import org.lmind.jel.core.ast.JelParserTreeConstants;
import org.lmind.jel.core.ast.JelNode;
import org.lmind.jel.core.ast.ParseException;
import org.lmind.jel.core.ast.Parser;
import org.lmind.jel.core.util.SimpleJelObjectFactory;

public class JelEngine {

	private JelObjectFactory objectFactory = new SimpleJelObjectFactory();

	public JelObjectFactory getObjectFactory() {
		return objectFactory;
	}

	public void setObjectFactory(JelObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public JelObject evalute(String code, ScriptContext context) {
		return evalute(new StringReader(code), context);
	}

	public JelObject evalute(Reader reader, ScriptContext context) {
		Parser p = new Parser();
		JelNode node;
		try {
			node = p.parser(reader);
		} catch (ParseException e) {
			throw new ExpressionException(e);
		}
		return evalute(node, context);
	}

	private JelObject evalute(JelNode node, ScriptContext context) {

		switch (node.getId()) {
		case JelParserTreeConstants.JJTEXPRESSION:
			return evalute((JelNode) node.jjtGetChild(0), context);
		case JelParserTreeConstants.JJTTERNARYEXPRESSION:
			return ternary(node, context);

			// 算术运算符,加减乘除
		case JelParserTreeConstants.JJTADDEXPRESSION:
			return add(node, context);
		case JelParserTreeConstants.JJTSUBEXPRESSION:
			return subtract(node, context);
		case JelParserTreeConstants.JJTNEGATEEXPRESSION:
			return negate(node, context);
		case JelParserTreeConstants.JJTMULTIPLYEXPRESSION:
			return multiply(node, context);
		case JelParserTreeConstants.JJTDIVISIONEXPRESSION:
			return multiply(node, context);
		case JelParserTreeConstants.JJTMODEXPRESSION:
			return division(node, context);
			// 算术运算符 end

			// 关系运算符
		case JelParserTreeConstants.JJTEQUALSEXPRESSION:
			return equals(node, context);
		case JelParserTreeConstants.JJTNOTEQUALSEXPRESSION:
			return notEquals(node, context);
		case JelParserTreeConstants.JJTLESSEREXPRESSION:
			return lesser(node, context);
		case JelParserTreeConstants.JJTGREATEREXPRESSION:
			return greater(node, context);
		case JelParserTreeConstants.JJTLESSEREQUALSEXPRESSION:
			return lesserEquals(node, context);
		case JelParserTreeConstants.JJTGREATEREQUALSEXPRESSION:
			return greaterEquals(node, context);
			// 关系运算符 end

			// 逻辑运算符
		case JelParserTreeConstants.JJTANDEXPRESSION:
			return and(node, context);
		case JelParserTreeConstants.JJTOREXPRESSION:
			return or(node, context);
			// 逻辑运算符 end

		case JelParserTreeConstants.JJTREFERENCE:
			return reference(node, context);
		case JelParserTreeConstants.JJTPROPERTYREADEXPRESSION:
			return propertyRead(node, context);
		case JelParserTreeConstants.JJTCALLEXPRESSION:
			return call(node, context);

			// 常量
		case JelParserTreeConstants.JJTSTRINGLITERAL:
			return objectFactory.stringValue(node.getImage());
		case JelParserTreeConstants.JJTNUMBERLITERAL:
			return objectFactory.numberValue(Double.valueOf(node.getImage()));
		case JelParserTreeConstants.JJTBOOLEANLITERAL:
			return objectFactory.booleanValue(Boolean.valueOf(node.getImage()));

		}

		return null;
	}
	
	private JelObject ternary(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		if (condition(a)) {
			return evalute((JelNode) node.jjtGetChild(1), context);
		} else {
			return evalute((JelNode) node.jjtGetChild(2), context);
		}
	}

	private JelObject add(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalute((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.numberValue(((JelNumber) a).doubleValue()
						+ ((JelNumber) b).doubleValue());
			} else if (b instanceof JelString) {
				return objectFactory.stringValue(a.toString() + b.toString());
			}
		} else if (a instanceof JelString) {
			if (b instanceof JelNumber) {
				return objectFactory.stringValue(a.toString() + b.toString());
			} else if (b instanceof JelString) {
				return objectFactory.stringValue(a.toString() + b.toString());
			}
		}
		throw new ExpressionException("\"+\" unsupported");
	}

	private JelObject negate(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);

		if (a instanceof JelNumber) {
			return objectFactory.numberValue(-((JelNumber) a).doubleValue());
		}
		throw new ExpressionException("\"-\" unsupported ");
	}

	private JelObject subtract(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalute((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.numberValue(((JelNumber) a).doubleValue()
						- ((JelNumber) b).doubleValue());
			}
		}
		throw new ExpressionException("\"-\" unsupported ");
	}

	private JelObject multiply(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalute((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.numberValue(((JelNumber) a).doubleValue()
						* ((JelNumber) b).doubleValue());
			}
		}
		throw new ExpressionException("\"*\" unsupported ");
	}

	private JelObject division(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalute((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.numberValue(((JelNumber) a).doubleValue()
						/ ((JelNumber) b).doubleValue());
			}
		}
		throw new ExpressionException("\"/\" unsupported ");
	}

	private JelObject equals(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalute((JelNode) node.jjtGetChild(1), context);

		boolean eq = false;
		if (a == b) {
			eq = true;
		} else if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				eq = ((JelNumber) a).doubleValue() == ((JelNumber) b)
						.doubleValue();
			}
		} else if (a instanceof JelString) {
			if (b instanceof JelString) {
				eq = a.toString().equals(b.toString());
			}
		} else if (a instanceof JelBoolean) {
			if (b instanceof JelBoolean) {
				eq = ((JelBoolean) a).value() == ((JelBoolean) b).value();
			}
		}
		return objectFactory.booleanValue(eq);
	}

	private JelObject notEquals(JelNode node, ScriptContext context) {
		JelObject r = equals(node, context);
		return objectFactory.booleanValue(((JelBoolean) r).value());
	}

	private JelObject lesser(JelNode node, ScriptContext context) {
		return objectFactory.booleanValue(compare(node, context) < 0);
	}

	private JelObject greater(JelNode node, ScriptContext context) {
		return objectFactory.booleanValue(compare(node, context) > 0);
	}

	private JelObject lesserEquals(JelNode node, ScriptContext context) {
		return objectFactory.booleanValue(compare(node, context) <= 0);
	}

	private JelObject greaterEquals(JelNode node, ScriptContext context) {
		return objectFactory.booleanValue(compare(node, context) >= 0);
	}

	private JelObject and(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		if (condition(a)) {
			JelObject b = evalute((JelNode) node.jjtGetChild(1), context);
			if (condition(b)) {
				return objectFactory.booleanValue(true);
			}
		}
		
		return objectFactory.booleanValue(false);
	}

	private JelObject or(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		if (!condition(a)) {
			JelObject b = evalute((JelNode) node.jjtGetChild(1), context);
			if (!condition(b)) {
				return objectFactory.booleanValue(false);
			}
		}
		
		return objectFactory.booleanValue(true);
	}
	
	private JelObject reference(JelNode node, ScriptContext context) {
		JelObject obj = (JelObject) context.getAttribute(node.getImage());
		if (obj == null) {
			throw new ExpressionException("null reference for name "
					+ node.getImage());
		}
		return obj;
	}

	private JelObject propertyRead(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		return a.propertyRead(((JelNode) node.jjtGetChild(1)).getImage());
	}

	private JelObject call(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		if (!(a instanceof JelCallable)) {
			throw new ExpressionException("object is not callable");
		}

		JelCallable call = (JelCallable) a;

		int c = node.jjtGetChild(1).jjtGetNumChildren();
		JelObject[] args = new JelObject[c];
		for (int i = 0; i < c; i++) {
			args[i] = evalute((JelNode) node.jjtGetChild(1).jjtGetChild(i),
					context);
		}
		return call.call(args);
	}
	
	private boolean condition(JelObject a) {
		boolean r = false;
		if (a instanceof JelNumber) {
			r = ((JelNumber)a).doubleValue() != 0;
		} else if (a instanceof JelString) {
			r = a.toString().length() > 0;
		} else if (a instanceof JelBoolean) {
			r = ((JelBoolean)a).value();
		}
		return r;
	}

	private int compare(JelNode node, ScriptContext context) {
		JelObject a = evalute((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalute((JelNode) node.jjtGetChild(1), context);

		int r = 0;
		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				r = Double.valueOf(((JelNumber) a).doubleValue()).compareTo(
						Double.valueOf(((JelNumber) b).doubleValue()));
			}
		} else if (a instanceof JelString) {
			if (b instanceof JelString) {
				r = a.toString().compareTo(b.toString());
			}
		}
		return r;
	}
}
