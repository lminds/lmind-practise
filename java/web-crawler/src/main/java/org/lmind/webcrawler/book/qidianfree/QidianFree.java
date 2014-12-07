package org.lmind.webcrawler.book.qidianfree;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;







import javax.annotation.Resource;







import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.lmind.webcrawler.CrawlerUtil;
import org.lmind.webcrawler.dao.BookRepository;
import org.lmind.webcrawler.entity.Book;
import org.lmind.webcrawler.entity.Chapter;
import org.lmind.webcrawler.entity.Episode;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class QidianFree {
	
	private String baseUrl = "http://free.qidian.com/Free/ChapterList.aspx?bookId=22468";
	
	private String charset = "utf-8";
	
	private String bookName = "暴风雨中的蝴蝶";
	
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
				String s = CrawlerUtil.httpGet("http://free.qidian.com/" + o.getReference(), charset);
				Document doc = Jsoup.parse(s);

				String href = doc.select("#content script").last().attr("src");
				
				String s2 = CrawlerUtil.httpGet(href, "gbk");
				s2 = s2.substring(16, s2.length() - 5);
				
				s2 = "<div>" + s2 + "</div>";
				
				StringBuilder sb = new StringBuilder();
				Jsoup.parseBodyFragment(s2).select("p").forEach(p -> {
					sb.append(p.text()).append("\n");
				});
				
				bookRepository.updateChapterContent(o, sb.toString());
				System.out.println(o.getName());
				
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
		
		Book book = new Book();
		book.setName(bookName);
		bookRepository.newBook(book);
		
		AtomicLong seq = new AtomicLong();
		doc.select(".tabs").forEach(o -> {
			
			Episode episode = new Episode();
			episode.setName(o.text());
			episode.setBook(book);
			episode.setOrder(seq.incrementAndGet());
			bookRepository.newEpisode(episode);
			
			saveChapter(o.nextElementSibling(), episode);
			
		});
	}

	private void saveChapter(Element section, Episode episode) {
		AtomicLong seq = new AtomicLong();
		section.select("li a").forEach(link -> {
			
			Chapter chapter = new Chapter();
			chapter.setName(link.text());
			chapter.setEpisode(episode);
			chapter.setReference(link.attr("href"));
			chapter.setOrder(seq.incrementAndGet());
			bookRepository.newChapter(chapter);
		});
	}
}
