package org.lmind.jel.core;

import org.lmind.jel.core.ast.JelParserTreeConstants;
import org.lmind.jel.core.ast.JelNode;

public class Template {
	
	private ExpressionOperators oper = new ExpressionOperators();

	public void run(JelNode node, Context context) {

		switch (node.getId()) {
		case JelParserTreeConstants.JJTADDITIVEEXPRESSION:
			run((JelNode)node.jjtGetChild(0), context);
			run((JelNode)node.jjtGetChild(1), context);
			Object b = context.popStack();
			Object a = context.popStack();
			if ("+".equals(node.getImage())) {
				context.pushStack(oper.plus(a, b));
			} else {
				context.pushStack(oper.subtract(a, b));
			}
			
			break;
		case JelParserTreeConstants.JJTREFERENCE:
			
			break;
		case JelParserTreeConstants.JJTMEMBERACCESS:
			break;
		case JelParserTreeConstants.JJTSTRINGLITERAL:
			context.pushStack(node.getImage());
			break;
		case JelParserTreeConstants.JJTNUMBERLITERAL:
			context.pushStack(Double.valueOf(node.getImage()));
			break;
		case JelParserTreeConstants.JJTBOOLEANLITERAL:
			context.pushStack(Boolean.valueOf(node.getImage()));
			break;
		case JelParserTreeConstants.JJTEXPRESSION:
			run((JelNode)node.jjtGetChild(0), context);
			break;
		}
	}
}
