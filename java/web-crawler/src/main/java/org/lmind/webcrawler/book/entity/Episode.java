package org.lmind.webcrawler.book.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="EPISODE")
public class Episode {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="BOOK_ID")
	private Book book;
	
	@Column(name="ORDER_VALUE")
	private Long order;
	
	@OneToMany(mappedBy="episode")
	private Set<Chapter> chapters;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public Set<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}
}
