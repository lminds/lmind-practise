package org.lmind.webcrawler.book.biquge;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.lmind.webcrawler.entity.Chapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class BiqugeCrawler {

	@Resource
	private Biquge biquge;
	
	@Resource
	private ThreadPoolTaskExecutor schedulingTaskExecutor;
	
	public void run() throws Exception {
		biquge.doCrawl();
		
		List<Chapter> chapters = biquge.getRemains();
		chapters.forEach(o -> {
			
			schedulingTaskExecutor.execute(() -> {
				try {
					biquge.doContent(Arrays.asList(o));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		schedulingTaskExecutor.shutdown();
		
	}
}
