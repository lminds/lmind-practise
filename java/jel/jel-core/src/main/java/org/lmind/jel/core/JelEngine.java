package org.lmind.jel.core;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

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

	public JelObject eval(String script, ScriptContext context) {
		return eval(new StringReader(script), context);
	}

	public JelObject eval(Reader reader, ScriptContext context) {
		return compile(reader).eval(context);
	}

	public JelExpression compile(Reader reader) {
		Parser p = new Parser();
		JelNode node;
		try {
			node = p.parser(reader);
		} catch (ParseException e) {
			throw new ExpressionException(e);
		}
		return new JelExpressionImpl(this, node);
	}

	JelObject evalNode(JelNode node, ScriptContext context) {

		switch (node.getId()) {
		case JelParserTreeConstants.JJTEXPRESSION:
			return evalNode((JelNode) node.jjtGetChild(0), context);
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
			return division(node, context);
		case JelParserTreeConstants.JJTMODEXPRESSION:
			return mod(node, context);
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
		case JelParserTreeConstants.JJTUNARYEXPRESSIONNOT:
			return not(node, context);
			// 逻辑运算符 end

		case JelParserTreeConstants.JJTREFERENCE:
			return reference(node, context);
		case JelParserTreeConstants.JJTPROPERTYREADEXPRESSION:
			return propertyRead(node, context);
		case JelParserTreeConstants.JJTCALLEXPRESSION:
			return call(node, context);

			// 常量
		case JelParserTreeConstants.JJTSTRINGLITERAL:
			return string(node, context);
		case JelParserTreeConstants.JJTNUMBERLITERAL:
			return objectFactory.createNumber(Double.valueOf(node.getImage()));
		case JelParserTreeConstants.JJTBOOLEANLITERAL:
			return objectFactory.createBoolean(Boolean.valueOf(node.getImage()));
		case JelParserTreeConstants.JJTSETEXPRESSION:
			return setLiteral(node, context);
		}

		return null;
	}

	private JelObject ternary(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		if (condition(a)) {
			return evalNode((JelNode) node.jjtGetChild(1), context);
		} else {
			return evalNode((JelNode) node.jjtGetChild(2), context);
		}
	}

	private JelObject add(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.createNumber(((JelNumber) a).doubleValue()
						+ ((JelNumber) b).doubleValue());
			} else if (b instanceof JelString) {
				return objectFactory.createString(a.toString() + b.toString());
			}
		} else if (a instanceof JelString) {
			if (b instanceof JelNumber) {
				return objectFactory.createString(a.toString() + b.toString());
			} else if (b instanceof JelString) {
				return objectFactory.createString(a.toString() + b.toString());
			}
		}
		throw new ExpressionException("\"+\" unsupported");
	}

	private JelObject negate(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);

		if (a instanceof JelNumber) {
			return objectFactory.createNumber(-((JelNumber) a).doubleValue());
		}
		throw new ExpressionException("\"-\" unsupported ");
	}

	private JelObject subtract(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.createNumber(((JelNumber) a).doubleValue()
						- ((JelNumber) b).doubleValue());
			}
		}
		throw new ExpressionException("\"-\" unsupported ");
	}

	private JelObject multiply(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.createNumber(((JelNumber) a).doubleValue()
						* ((JelNumber) b).doubleValue());
			}
		}
		throw new ExpressionException("\"*\" unsupported ");
	}

	private JelObject division(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.createNumber(((JelNumber) a).doubleValue()
						/ ((JelNumber) b).doubleValue());
			}
		}
		throw new ExpressionException("\"/\" unsupported ");
	}

	private JelObject mod(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);

		if (a instanceof JelNumber) {
			if (b instanceof JelNumber) {
				return objectFactory.createNumber(((JelNumber) a).doubleValue()
						% ((JelNumber) b).doubleValue());
			}
		}
		throw new ExpressionException("\"/\" unsupported ");
	}

	private JelObject equals(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);

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
		return objectFactory.createBoolean(eq);
	}

	private JelObject notEquals(JelNode node, ScriptContext context) {
		JelObject r = equals(node, context);
		return objectFactory.createBoolean(!((JelBoolean) r).value());
	}

	private JelObject lesser(JelNode node, ScriptContext context) {
		return objectFactory.createBoolean(compare(node, context) < 0);
	}

	private JelObject greater(JelNode node, ScriptContext context) {
		return objectFactory.createBoolean(compare(node, context) > 0);
	}

	private JelObject lesserEquals(JelNode node, ScriptContext context) {
		return objectFactory.createBoolean(compare(node, context) <= 0);
	}

	private JelObject greaterEquals(JelNode node, ScriptContext context) {
		return objectFactory.createBoolean(compare(node, context) >= 0);
	}

	private JelObject and(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		if (condition(a)) {
			JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);
			if (condition(b)) {
				return objectFactory.createBoolean(true);
			}
		}

		return objectFactory.createBoolean(false);
	}

	private JelObject or(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		if (!condition(a)) {
			JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);
			if (!condition(b)) {
				return objectFactory.createBoolean(false);
			}
		}

		return objectFactory.createBoolean(true);
	}

	private JelObject not(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		return objectFactory.createBoolean(!condition(a));
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
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		return a.getProperty(((JelNode) node.jjtGetChild(1)).getImage());
	}

	private JelObject call(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		if (!(a instanceof JelCallable)) {
			throw new ExpressionException("object is not callable");
		}

		JelCallable call = (JelCallable) a;

		int c = node.jjtGetChild(1).jjtGetNumChildren();
		JelObject[] args = new JelObject[c];
		for (int i = 0; i < c; i++) {
			args[i] = evalNode((JelNode) node.jjtGetChild(1).jjtGetChild(i),
					context);
		}
		return call.call(args);
	}

	private JelString string(JelNode node, ScriptContext context) {
		return objectFactory.createString(JelUtils.unescape(node.getImage()
				.substring(1, node.getImage().length() - 1)));
	}

	private JelSet setLiteral(JelNode node, ScriptContext context) {
		int c = node.jjtGetNumChildren();
		int flag = 0;
		for (int i = 0; i < c; i++) {
			JelNode item = (JelNode) node.jjtGetChild(i);
			if (flag == 0) {
				flag = item.jjtGetNumChildren();
			} else if (flag != item.jjtGetNumChildren()) {
				throw new ExpressionException("table expr error");
			}
		}

		HashMap<String, JelObject> map = new HashMap<String, JelObject>();
		for (int i = 0; i < c; i++) {
			JelNode item = (JelNode) node.jjtGetChild(i);
			if (item.jjtGetNumChildren() == 1) {
				map.put(String.valueOf(i), evalNode((JelNode)item.jjtGetChild(0), context));
			} else if (item.jjtGetNumChildren() == 2) {
				String name = null;
				switch(item.jjtGetChild(0).getId()) {
				case JelParserTreeConstants.JJTIDENTIFIER:
					name = ((JelNode)item.jjtGetChild(0)).getImage();
					break;
				case JelParserTreeConstants.JJTSTRINGLITERAL:
					name = evalNode((JelNode)item.jjtGetChild(0), context).toString();
					break;
				}
				map.put(name, evalNode((JelNode)item.jjtGetChild(1), context));
			}
		}
		
		return objectFactory.createSet(map);
	}

	private boolean condition(JelObject a) {
		boolean r = false;
		if (a instanceof JelNumber) {
			r = ((JelNumber) a).doubleValue() != 0;
		} else if (a instanceof JelString) {
			r = a.toString().length() > 0;
		} else if (a instanceof JelBoolean) {
			r = ((JelBoolean) a).value();
		}
		return r;
	}

	private int compare(JelNode node, ScriptContext context) {
		JelObject a = evalNode((JelNode) node.jjtGetChild(0), context);
		JelObject b = evalNode((JelNode) node.jjtGetChild(1), context);

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
