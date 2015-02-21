package org.lmind.jel.core.ast;

public class TreeOptimizer implements AstParserTreeConstants {

	public JelNode optimize(Node node) {

		switch (node.getId()) {
		// 去除无意义的三元或二元运算符等节点
		case JJTTERNARYEXPRESSION:
		case JJTCONDITIONALOREXPRESSION:
		case JJTCONDITIONALANDEXPRESSION:
		case JJTEQUALITYEXPRESSION:
		case JJTRELATIONALEXPRESSION:
		case JJTADDITIVEEXPRESSION:
		case JJTMULTIPLICATIVEEXPRESSION:
		case JJTINVOCATIONEXPRESSION:
			if (1 == node.jjtGetNumChildren()) {
				return (JelNode) optimize((JelNode) node.jjtGetChild(0));
			}
			break;
		}

		JelNode result = JelNodeFactory.jjtCreate(node.getId());
		result.jjtSetParent(node.jjtGetParent());
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			result.jjtAddChild(optimize(node.jjtGetChild(i)), i);
		}
		return result;
	}
}
