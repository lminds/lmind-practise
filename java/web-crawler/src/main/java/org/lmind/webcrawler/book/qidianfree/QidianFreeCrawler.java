package org.lmind.webcrawler.book.qidianfree;

import java.util.List;

import javax.annotation.Resource;

import org.lmind.webcrawler.book.entity.Chapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class QidianFreeCrawler {
	
	@Resource
	private QidianFree qidian;
	
	@Resource
	private ThreadPoolTaskExecutor schedulingTaskExecutor;
	
	public void run() throws Exception {
		qidian.doCrawl();
		
		List<Chapter> chapters = qidian.getRemains();
		qidian.doContent(chapters);
	}
}
