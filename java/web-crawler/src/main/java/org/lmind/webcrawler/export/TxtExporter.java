package org.lmind.webcrawler.export;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.io.FileUtils;
import org.lmind.webcrawler.entity.Chapter;
import org.lmind.webcrawler.entity.Episode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TxtExporter {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void export(String bookName, File file) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		sb.append(bookName).append("\n\n");
		
		String jql = "select c from Episode c where c.book.name = ?1 order by c.order";
		TypedQuery<Episode> query = entityManager.createQuery(jql, Episode.class);
		query.setParameter(1, bookName).getResultList().forEach(episode -> {
			
			sb.append(episode.getName()).append("\n\n");
			
			String jql2 = "select c from Chapter c where c.episode.id = ?1 order by c.order";
			TypedQuery<Chapter> query2 = entityManager.createQuery(jql2, Chapter.class);
			query2.setParameter(1, episode.getId()).getResultList().forEach(c ->{
				
				sb.append(c.getName()).append("\n\n");
				sb.append(c.getContent());
			});
			
		});
		
		FileUtils.writeStringToFile(file, sb.toString(), "utf-8");
	}
}
