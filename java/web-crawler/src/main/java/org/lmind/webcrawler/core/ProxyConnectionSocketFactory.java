package org.lmind.webcrawler.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import org.apache.http.HttpHost;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

public class ProxyConnectionSocketFactory implements ConnectionSocketFactory {
	
	private ConnectionSocketFactory origin;
	
	private String host;
	
	private int port;

	public ProxyConnectionSocketFactory(ConnectionSocketFactory origin,
			String host, int port) {
		super();
		this.origin = origin;
		this.host = host;
		this.port = port;
	}

	@Override
	public Socket connectSocket(int arg0, Socket arg1, HttpHost arg2,
			InetSocketAddress arg3, InetSocketAddress arg4, HttpContext arg5)
			throws IOException {
		return origin.connectSocket(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public Socket createSocket(HttpContext arg0) throws IOException {
		
		InetSocketAddress socksaddr = new InetSocketAddress(host, port);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
        return new Socket(proxy);
	}

}
