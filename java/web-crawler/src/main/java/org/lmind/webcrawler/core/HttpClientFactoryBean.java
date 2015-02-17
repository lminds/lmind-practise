package org.lmind.webcrawler.core;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class HttpClientFactoryBean implements FactoryBean<CloseableHttpClient>, InitializingBean {
	
	private CloseableHttpClient closeableHttpClient;
	
	private String socksAddress;
	
	private int socksPort = 1080;

	public void setSocksAddress(String socksAddress) {
		this.socksAddress = socksAddress;
	}

	public void setSocksPort(int socksPort) {
		this.socksPort = socksPort;
	}

	@Override
	public CloseableHttpClient getObject() throws Exception {
		return closeableHttpClient;
	}

	@Override
	public Class<?> getObjectType() {
		return CloseableHttpClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		ConnectionSocketFactory http = PlainConnectionSocketFactory.INSTANCE;
		if (socksAddress != null) {
			http = new ProxyConnectionSocketFactory(http, socksAddress, socksPort);
		}
		
		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
		        .register("http", http)
//		        .register("https", new MyConnectionSocketFactory(SSLContexts.createSystemDefault()))
		        .build();
		HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);

		BasicCookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(cookie("ipb_member_id", "693068"));
		cookieStore.addCookie(cookie("ipb_pass_hash", "215f316469ae886d236cdfdda82a101d"));
		cookieStore.addCookie(cookie("igneous", "036a08e1be00b467f572d6e53667c2f0592aeec09c64b48c4a0d0f98acb1af9bb37a3bbfadd0fc806d86d0dfc7bd10b78d43207fcc49de0537cecd8308f317b1"));
		cookieStore.addCookie(cookie("lv", "1420084195-1420086338"));
		cookieStore.addCookie(cookie("uconfig", "tl_m-uh_y-rc_0-cats_0-xns_0-ts_m-tr_2-prn_y-dm_l-ar_0-rx_0-ry_0-ms_n-mt_n-cs_a-to_a-sa_y-oi_n-qb_n-tf_n-hp_-hk_-xl_"));
		
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultCookieStore(cookieStore);
		builder.setConnectionManager(cm);
		builder.setRedirectStrategy(DefaultRedirectStrategy.INSTANCE);
		
		closeableHttpClient = builder.build();
	}
	
	private Cookie cookie(String name, String value) {
		BasicClientCookie2 cookie = new BasicClientCookie2(name, value);
		cookie.setDomain("exhentai.org");
		return cookie;
	}

}
