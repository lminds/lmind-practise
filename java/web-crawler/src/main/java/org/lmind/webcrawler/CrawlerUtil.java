package org.lmind.webcrawler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderElement;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;

public class CrawlerUtil {
	
	public static String httpGet(String url, String charset)
			throws IOException, URISyntaxException {
		return httpGet(new URL(url), charset);
	}

	public static String httpGet(URL url, String charset)
			throws IOException, URISyntaxException {
		BasicCookieStore cookieStore = new BasicCookieStore();

		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultCookieStore(cookieStore);
		builder.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE);
		CloseableHttpClient client = builder.build();

		HttpGet req = new HttpGet();
		req.setURI(url.toURI());
		CloseableHttpResponse resp = client.execute(req);

		if (resp.getStatusLine().getStatusCode() >= 400) {
			throw new RuntimeException("status="
					+ resp.getStatusLine().getStatusCode());
		}

		String encoding = charset;
		if (encoding == null) {
			encoding = "utf-8";
		}
		if (resp.getEntity().getContentType() != null) {
			for (HeaderElement el : resp.getEntity().getContentType()
					.getElements()) {
				if ("charset".equalsIgnoreCase(el.getName())) {
					encoding = el.getValue();
				}
			}
		}

		return IOUtils.toString(resp.getEntity().getContent(), encoding);
	}
}
