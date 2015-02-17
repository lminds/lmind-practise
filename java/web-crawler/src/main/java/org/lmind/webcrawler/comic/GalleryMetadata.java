package org.lmind.webcrawler.comic;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="comic")
public class GalleryMetadata {

	private String title;
	
	private String originTitle;
	
	private String originUrl;
	
	@XmlElementWrapper(name="wallpapers")
	@XmlElement(name="a")
	private List<String> images;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginTitle() {
		return originTitle;
	}

	public void setOriginTitle(String originTitle) {
		this.originTitle = originTitle;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
	
	public void save(File file) {
		JAXB.marshal(this, file);
	}
	
	public static GalleryMetadata load(File file) {
		if (!file.isFile()) {
			return null;
		}
		return JAXB.unmarshal(file, GalleryMetadata.class);
	}
}
