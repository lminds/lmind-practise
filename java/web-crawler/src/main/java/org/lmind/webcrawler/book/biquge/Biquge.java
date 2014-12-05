package org.lmind.webcrawler.book.biquge;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lmind.webcrawler.CrawlerUtil;
import org.lmind.webcrawler.entity.Book;
import org.lmind.webcrawler.entity.Chapter;
import org.lmind.webcrawler.entity.Epside;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class Biquge {

	@PersistenceContext
	private EntityManager entityManager;
	
	private String bookName = "庆余年";
	
	private String baseUrl = "http://www.biquge.la/book/113/";
	
	private String charset = "gbk";
	
	public Long size() {
		return entityManager
		.createQuery(
				"select count(o) from Chapter o where o.epside.book.name = ?1 and o.content is null",
				Long.class).setParameter(1, bookName).getSingleResult();
	}

	public void doContent() throws Exception {

		List<Chapter> chapters = entityManager
				.createQuery(
						"select o from Chapter o where o.epside.book.name = ?1 and o.content is null",
						Chapter.class).setParameter(1, bookName).setMaxResults(1)
				.getResultList();

		chapters.forEach(o -> {
			try {
				String html = CrawlerUtil.httpGet(baseUrl + o.getReference(), charset);
				Document doc = Jsoup.parse(html);
				StringBuilder sb = new StringBuilder();
				
				doc.select("#content").first().textNodes().forEach(o2 -> {
					String t = o2.text().replaceAll("\u00a0", " ");
					sb.append(t).append("\n");
				});
				o.setContent(sb.toString());
				entityManager.persist(o);
				System.out.println(o.getName());

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}

	public void doCrawl() throws Exception {
		
		if (getBook(bookName) != null) {
			return;
		}

		String s = CrawlerUtil.httpGet(baseUrl, charset);
		Document doc = Jsoup.parse(s);
		

		Book book = createBook(bookName);

		Elements els = doc.select("#list dt");
		AtomicLong seq = new AtomicLong(1);
		els.forEach(o -> {

			Epside ep = saveEpside(book, o, seq.incrementAndGet());
			Element dd = o;
			for (long i = 0; i < 10000; i++) {
				dd = dd.nextElementSibling();
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
						entityManager.persist(cp);
					}
				} else {
					System.out.println(dd.nodeName());
					break;
				}
			}
		});

		System.out.println("end");
	}

	private Epside saveEpside(Book book, Element o, long order) {
		TypedQuery<Epside> ep = entityManager.createQuery(
				"select o from Epside o where o.name = ?1 and o.book.id = ?2",
				Epside.class);
		try {
			return ep.setParameter(1, o.text()).setParameter(2, book.getId())
					.getSingleResult();
		} catch (NoResultException e) {
			Epside epside = new Epside();
			epside.setName(o.text());
			epside.setOrder(order);
			epside.setBook(book);
			book.getEpsides().add(epside);
			entityManager.persist(epside);
			return epside;
		}
	}
	
	private Book getBook(String bookName) {
		try {
			return entityManager
					.createQuery("select o from Book o where o.name = ?1",
							Book.class).setParameter(1, bookName)
					.getSingleResult();
		} catch (NoResultException e1) {
			return null;
		}
	}

	private Book createBook(String bookName) {
		Book book = null;
		try {
			book = entityManager
					.createQuery("select o from Book o where o.name = ?1",
							Book.class).setParameter(1, bookName)
					.getSingleResult();
		} catch (NoResultException e1) {
			book = new Book();
			book.setName(bookName);
			book.setEpsides(new HashSet<Epside>());
			entityManager.persist(book);
			return book;
		}
		return book;
	}
}
