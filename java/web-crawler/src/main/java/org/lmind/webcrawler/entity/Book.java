package org.lmind.webcrawler.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="BOOK")
public class Book {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@OneToMany(mappedBy="book")
	private Set<Epside> epsides;

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

	public Set<Epside> getEpsides() {
		return epsides;
	}

	public void setEpsides(Set<Epside> epsides) {
		this.epsides = epsides;
	}
}
