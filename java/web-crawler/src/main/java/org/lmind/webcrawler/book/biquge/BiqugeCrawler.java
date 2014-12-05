package org.lmind.webcrawler.book.biquge;

import javax.annotation.Resource;

public class BiqugeCrawler {

	@Resource
	private Biquge biquge;
	
	public void run() throws Exception {
		biquge.doCrawl();
		
		long size = biquge.size();
		for (int i = 0; i < size; i++) {
			System.out.println(i);
			biquge.doContent();
		}
	}
}
