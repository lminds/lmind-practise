package org.lmind.webcrawler.kan7;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TestBean {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void t() {
		Query q = entityManager.createQuery("select o from Book o");
		q.getResultList();
	}
}
