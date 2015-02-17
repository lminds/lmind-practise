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

		return httpGet(client, url, charset);
	}
	
	public static String httpGet(CloseableHttpClient client, URL url, String charset)
			throws IOException, URISyntaxException {
		
		HttpGet req = new HttpGet();
		req.setURI(url.toURI());
//		req.setHeader("Cookie", "ipb_member_id=693068; ipb_pass_hash=215f316469ae886d236cdfdda82a101d; igneous=036a08e1be00b467f572d6e53667c2f0592aeec09c64b48c4a0d0f98acb1af9bb37a3bbfadd0fc806d86d0dfc7bd10b78d43207fcc49de0537cecd8308f317b1; lv=1420084195-1420086338; uconfig=tl_m-uh_y-rc_0-cats_0-xns_0-ts_m-tr_2-prn_y-dm_l-ar_0-rx_0-ry_0-ms_n-mt_n-cs_a-to_a-sa_y-oi_n-qb_n-tf_n-hp_-hk_-xl_");

		try (CloseableHttpResponse resp = client.execute(req)) {
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
}
