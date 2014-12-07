package org.lmind.webcrawler.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.lmind.webcrawler.entity.Book;
import org.lmind.webcrawler.entity.Chapter;
import org.lmind.webcrawler.entity.Epside;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BookRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Book getBook(String name) {
		try {
			return entityManager
					.createQuery("select o from Book o where o.name = ?1",
							Book.class).setParameter(1, name)
					.getSingleResult();
		} catch (NoResultException e1) {
			return null;
		}
	}
	
	public Book newBook(Book book) {
		
		if (entityManager.contains(book)) {
			throw new RuntimeException("book实体已存在");
		}
		
		try {
			book = entityManager
					.createQuery("select o from Book o where o.name = ?1",
							Book.class).setParameter(1, book.getName())
					.getSingleResult();
			
			throw new RuntimeException("已存在同名的book:" + book.getName());
		} catch (NoResultException e1) {
			book.setId(null);
			entityManager.persist(book);
			return book;
		}
	}
	
	public Epside newEpside(Epside epside) {
		if (entityManager.contains(epside)) {
			throw new RuntimeException("实体已存在");
		}
		
		epside.setId(null);
		entityManager.persist(epside);
		return epside;
	}
	
	public Chapter newChapter(Chapter chapter) {
		if (entityManager.contains(chapter)) {
			throw new RuntimeException("实体已存在");
		}
		
		chapter.setId(null);
		entityManager.persist(chapter);
		return chapter;
	}
	
	public List<Chapter> getRemains(Book book) {
		return entityManager
				.createQuery(
						"select o from Chapter o where o.epside.book.id = ?1 and o.content is null",
						Chapter.class).setParameter(1, book.getId()).getResultList();
	}
	
	public void updateChapterContent(Chapter chapter, String content) {
		Chapter c = entityManager.find(Chapter.class, chapter.getId());
		c.setContent(content);
		entityManager.persist(c);
	}
}