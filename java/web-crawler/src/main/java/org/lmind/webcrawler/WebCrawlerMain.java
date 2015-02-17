package org.lmind.webcrawler;

import org.lmind.webcrawler.comic.Exhentai;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebCrawlerMain {

	public static void main(String[] args) throws BeansException, Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		//context.getBean(Mtmju.class).doCrawl();
		
		
		context.getBean(Exhentai.class).run();
		
//		TxtExporter e = context.getBean(TxtExporter.class);
//		e.export("暴风雨中的蝴蝶", new File("D:\\t.txt"));
		
	}

}
