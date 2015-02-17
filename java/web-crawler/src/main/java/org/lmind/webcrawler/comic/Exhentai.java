package org.lmind.webcrawler.comic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.lmind.webcrawler.CrawlerUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class Exhentai {

	@Resource
	private CloseableHttpClient closeableHttpClient;

	@Resource
	private ThreadPoolTaskExecutor schedulingTaskExecutor;

	private String start = "http://exhentai.org/g/741683/790e6ec72d/";

	public void run() throws Exception {

		GalleryMetadata meta = loadMeta();

		for (int i = 0; i < meta.getImages().size(); i++) {
			int index = i;

			boolean exist = Arrays.asList(new File(".").listFiles()).stream()
					.anyMatch(o -> o.getName().startsWith(fillZero(index + 1)));
			if (!exist) {
				schedulingTaskExecutor.execute(() -> {
					doImage(meta, index);
				});
				Thread.sleep(5000);
			} else {
				// System.out.println("skip" + i);
			}

		}

		schedulingTaskExecutor.shutdown();
	}

	private GalleryMetadata loadMeta() throws Exception {
		File metaFile = new File("meta.xml");

		GalleryMetadata meta = GalleryMetadata.load(metaFile);
		if (meta == null) {
			meta = processMeta();
			meta.save(metaFile);
		}
		return meta;
	}

	private void doImage(GalleryMetadata meta, int i) {
		try {
			String src = getSrc(meta.getImages().get(i));
			System.out.println("start " + meta.getImages().get(i));
			downloadAndSave(i, src);
			System.out.println("end " + meta.getImages().get(i));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getSrc(String page) throws Exception {
		String html = CrawlerUtil.httpGet(closeableHttpClient, new URL(page),
				"utf-8");
		Document doc = Jsoup.parse(html);
		return doc.select("#img").first().attr("src");
	}

	private void downloadAndSave(int i, String src) throws Exception,
			IOException, ClientProtocolException {
		HttpGet get = new HttpGet(src);
		try (CloseableHttpResponse resp = closeableHttpClient.execute(get)) {

			File f = new File(toFileName(i + 1, src));
			if (!f.isFile()) {
				byte[] data = checkStatusAndLength(resp);
				if (data == null) {
					return;
				}
				FileUtils.writeByteArrayToFile(f, data);
			}
		}
	}

	private byte[] checkStatusAndLength(CloseableHttpResponse resp)
			throws Exception {
		if (200 != resp.getStatusLine().getStatusCode()) {
			return null;
		}
		byte[] data = IOUtils.toByteArray(resp.getEntity().getContent());
		return data;
	}

	private String fillZero(int index) {
		String n = String.valueOf(index);
		if (index < 10) {
			n = "00" + n;
		} else if (index < 100) {
			n = "0" + n;
		} else if (index < 1000) {
			n = "" + n;
		}
		return n;
	}

	private String toFileName(int index, String url) {
		String suffix = url.substring(url.length() - 3);
		return fillZero(index) + "." + suffix.toLowerCase();
	}

	public GalleryMetadata processMeta() throws Exception {

		String html = CrawlerUtil.httpGet(closeableHttpClient, new URL(start),
				"utf-8");
		Document doc = Jsoup.parse(html);

		GalleryMetadata meta = new GalleryMetadata();
		meta.setTitle(doc.select("#gn").text());
		meta.setOriginTitle(doc.select("#gj").text());

		ArrayList<String> list = getWallPagers(doc);

		meta.setImages(list);
		meta.save(new File("meta.xml"));
		return meta;
	}

	private ArrayList<String> getWallPagers(Document doc) throws IOException,
			URISyntaxException, MalformedURLException, InterruptedException {
		String html;
		Optional<Element> r = doc.select("#gdd td").stream().filter(o -> {
			System.out.println(o.text());
			return "Images:".equals(o.text());
		}).findFirst();
		if (!r.isPresent()) {
			System.out.println("not found total size");
		}
		int total = Integer.parseInt(r.get().nextElementSibling().text()
				.split("@")[0].trim());
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < 100; i++) {

			if (i > 0) {
				html = CrawlerUtil.httpGet(closeableHttpClient, new URL(start
						+ "?p=" + i), "utf-8");
				doc = Jsoup.parse(html);
			}

			doc.select("#gdt .gdtm a").forEach(o -> {
				list.add(o.attr("href"));
			});

			if (list.size() >= total) {
				break;
			}

			Thread.sleep(5000);
		}
		return list;
	}

	public static void main(String[] args) {
		String a = "8 @ 1.37 MB".split("@")[0];
		System.out.println(a.trim());
	}
}
