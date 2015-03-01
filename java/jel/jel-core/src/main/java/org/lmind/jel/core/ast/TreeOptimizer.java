package org.lmind.jel.core.ast;

public class TreeOptimizer implements JelParserTreeConstants {

	public JelNode optimize(JelNode node) {

		switch (node.getId()) {
			// 去除无意义的一元运算符
		case JJTUNARYEXPRESSIONNOT:
			if (node.getImage() == null) {
				return (JelNode) optimize((JelNode) node.jjtGetChild(0));
			}
			break;
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
