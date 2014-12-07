package org.lmind.webcrawler;

import org.lmind.webcrawler.book.qidianfree.QidianFreeCrawler;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebCrawlerMain {

	public static void main(String[] args) throws BeansException, Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		//context.getBean(Mtmju.class).doCrawl();
		
		
		QidianFreeCrawler c = context.getBean(QidianFreeCrawler.class);
		c.run();
		
	}

}
