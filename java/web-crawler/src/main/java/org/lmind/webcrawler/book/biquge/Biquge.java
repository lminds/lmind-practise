package org.lmind.webcrawler.book.biquge;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lmind.webcrawler.CrawlerUtil;
import org.lmind.webcrawler.dao.BookRepository;
import org.lmind.webcrawler.entity.Book;
import org.lmind.webcrawler.entity.Chapter;
import org.lmind.webcrawler.entity.Epside;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class Biquge {

	private String bookName = "希灵帝国";
	
	private String baseUrl = "http://www.biquge.la/book/122/";
	
	private String charset = "gbk";
	
	@Resource
	private BookRepository bookRepository;
	
	public List<Chapter> getRemains() {
		Book book = bookRepository.getBook(bookName);
		if (book == null) {
			return null;
		}
		
		return bookRepository.getRemains(book);
	}

	public void doContent(List<Chapter> chapters) throws Exception {

		chapters.forEach(o -> {
			try {
				
				System.out.println("begin " + o.getName());
				
				String html = CrawlerUtil.httpGet(baseUrl + o.getReference(), charset);
				Document doc = Jsoup.parse(html);
				StringBuilder sb = new StringBuilder();
				
				doc.select("#content").first().textNodes().forEach(o2 -> {
					String t = o2.text().replaceAll("\u00a0", " ");
					sb.append(t).append("\n");
				});
				
				bookRepository.updateChapterContent(o, sb.toString());
				
				System.out.println("end " + o.getName());

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}

	public void doCrawl() throws Exception {
		
		if (bookRepository.getBook(bookName) != null) {
			return;
		}

		String s = CrawlerUtil.httpGet(baseUrl, charset);
		Document doc = Jsoup.parse(s);
		

		Book book = createBook(bookName);

		Elements els = doc.select("#list dt");
		AtomicLong seq = new AtomicLong(1);
		if (els.size() > 0) {
			els.forEach(o -> {
				Epside ep = saveEpside(book, o.text(), seq.incrementAndGet());
				saveChapters(ep, o.nextElementSibling());
			});
		} else {
			Epside ep = saveEpside(book, bookName, seq.incrementAndGet());
			Element dd = doc.select("#list dd").first();
			saveChapters(ep, dd);
		}

		System.out.println("end");
	}

	private void saveChapters(Epside ep, Element dd) {
		for (long i = 0; i < 10000; i++) {
			if (dd == null) {
				break;
			}
			if ("dd".equalsIgnoreCase(dd.nodeName())) {
				Elements as = dd.select("a");
				if (as.size() > 0) {
					Chapter cp = new Chapter();
					cp.setEpside(ep);
					cp.setName(as.first().text());
					cp.setOrder(i);
					cp.setReference(as.first().attr("href"));
					bookRepository.newChapter(cp);
				}
			} else {
				break;
			}
			
			dd = dd.nextElementSibling();
		}
	}

	private Epside saveEpside(Book book, String epsideName, long order) {
		
		Epside epside = new Epside();
		epside.setName(epsideName);
		epside.setOrder(order);
		epside.setBook(book);
		return bookRepository.newEpside(epside);
	}
	
	private Book createBook(String bookName) {
		
		Book book = bookRepository.getBook(bookName);
		if (book == null) {
			book = new Book();
			book.setName(bookName);
			book = bookRepository.newBook(book);
		}
		return book;
	}
}
