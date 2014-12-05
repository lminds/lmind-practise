package org.lmind.webcrawler.kan7;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.lmind.webcrawler.CrawlerUtil;
import org.lmind.webcrawler.entity.Book;
import org.lmind.webcrawler.entity.Epside;

public class Kan7Crawler {
	
	@PersistenceContext
	private EntityManager entityManager;

	public void doCrawl() throws Exception {

		String s = CrawlerUtil.httpGet(
				"http://read.qidian.com/BookReader/2643379.aspx", "utf-8");
		Document doc = Jsoup.parse(s);

		Elements els = doc.select("#content div.box_title b");

		Book book = new Book();
		book.setName("奥术神座");
		
		ArrayList<Epside> list = new ArrayList<Epside>();
		els.forEach(o -> {
			try {
				Epside epside = new Epside();
				String s2 = o.textNodes().get(0).text();
				if (s2.endsWith("[")) {
					String s3 = s2.substring(2, s2.length() - 2);
					epside.setName(s3);
				} else {
					String s3 = s2.substring(1, s2.length() - 1);
					epside.setName(s3);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}
}
