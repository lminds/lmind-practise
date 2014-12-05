package org.lmind.webcrawler.mtmju;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.lmind.webcrawler.CrawlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.gson.Gson;

public class Mtmju {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private TransactionTemplate transactionTemplate;
	
	public void doCrawl() throws Exception {

		for (int i = 530; i < 1000; i++) {
			final int index = i;
			transactionTemplate.execute(o -> {
				doCrawl(index);
				return null;
			});
			System.out.println("updated page:" + index);
		}
		
	}

	public void doCrawl(int i) {
		try {
			String s = CrawlerUtil.httpGet("http://mtmju.com/index.php?page=" + i, "utf-8");
			Document doc = Jsoup.parse(s);
			
			doc.select("div.toplist table tbody").forEach(o -> {
				
				MtmjuMetadata metadata = new MtmjuMetadata();
				metadata.setId(o.select("td.magTitle a").attr("rel"));
				metadata.setName(o.select("td.magTitle a").text());
				
				if (!StringUtils.isEmpty(metadata.getId())) {
					metadata.setMagnet(o.select("td.dow a.magDown").attr("href"));
					metadata.setEd2k(o.select("td.dow a.ed2kDown").attr("ed2k"));
					//metadata.setThunder(o.select("td.dow a.thunder").attr("thunderhref"));
					metadata.setSize(o.select("td.seed").first().text());
					
					update(metadata);
					
				}
				
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void update(MtmjuMetadata metadata) {
		
		if (jdbcTemplate.queryForList("select ID from RESOURCE where SOURCE=1 and SOURCE_ID = ?", metadata.getId()).size() > 0) {
			return;
		}
		
		String sql = "INSERT INTO RESOURCE (NAME, SOURCE, SOURCE_ID, MAGNET, ED2K, THUNDER, METADATA) VALUES (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, metadata.getName(), 1, metadata.getId(), metadata.getMagnet(), metadata.getEd2k(), metadata.getThunder(), new Gson().toJson(metadata));
	}
	
	public static class MtmjuMetadata {
		
		private String id;
		
		private String name;
		
		private String magnet;
		
		private String ed2k;
		
		private String thunder;
		
		private String size;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMagnet() {
			return magnet;
		}

		public void setMagnet(String magnet) {
			this.magnet = magnet;
		}

		public String getEd2k() {
			return ed2k;
		}

		public void setEd2k(String ed2k) {
			this.ed2k = ed2k;
		}

		public String getThunder() {
			return thunder;
		}

		public void setThunder(String thunder) {
			this.thunder = thunder;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}
	}
}
