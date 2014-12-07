package org.lmind.webcrawler;

import java.io.File;

import org.lmind.webcrawler.book.qidianfree.QidianFreeCrawler;
import org.lmind.webcrawler.export.TxtExporter;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebCrawlerMain {

	public static void main(String[] args) throws BeansException, Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		//context.getBean(Mtmju.class).doCrawl();
		
		
//		QidianFreeCrawler c = context.getBean(QidianFreeCrawler.class);
//		c.run();
		
		TxtExporter e = context.getBean(TxtExporter.class);
		e.export("暴风雨中的蝴蝶", new File("D:\\t.txt"));
		
	}

}
