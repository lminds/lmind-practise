package org.lmind.jel.core.ast;

public class TreeOptimizer implements JelParserTreeConstants {

	public JelNode optimize(JelNode node) {

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
		case JJTREFERENCE:
			if (1 == node.jjtGetNumChildren()) {
				return (JelNode) optimize((JelNode) node.jjtGetChild(0));
			}
			break;
			// 去除无意义的一元运算符
		case JJTUNARYEXPRESSIONNOT:
		case JJTUNARYEXPRESSIONSIGN:
			if (node.getImage() == null) {
				return (JelNode) optimize((JelNode) node.jjtGetChild(0));
			}
			break;
		}
		
		// 可以直接忽略的节点
		if (jjtNodeName[node.getId()].startsWith("Ignorable")) {
			return (JelNode) optimize((JelNode) node.jjtGetChild(0));
		}

		JelNode result = JelNodeFactory.jjtCreate(node.getId());
		result.jjtSetParent(node.jjtGetParent());
		result.setImage(node.getImage());
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			result.jjtAddChild(optimize((JelNode)node.jjtGetChild(i)), i);
		}
		return result;
	}
}
