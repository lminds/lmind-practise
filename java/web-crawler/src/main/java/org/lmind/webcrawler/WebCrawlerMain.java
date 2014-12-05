package org.lmind.webcrawler;

import org.lmind.webcrawler.book.biquge.BiqugeCrawler;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebCrawlerMain {

	public static void main(String[] args) throws BeansException, Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		//context.getBean(Mtmju.class).doCrawl();
		
		
		BiqugeCrawler c = context.getBean(BiqugeCrawler.class);
		c.run();
		
	}

}
