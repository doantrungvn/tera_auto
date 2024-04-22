package org.terasoluna.qp.app.message.translator.microsoft;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;

public class ProxyCommon {
	public static Proxy getSystemProxy() {
		Proxy proxy = null;
		try {

			System.setProperty("java.net.useSystemProxies", "true");
			List<Proxy> lProxy = ProxySelector.getDefault().select(new URI("http://www.yahoo.com/"));

			for (Proxy temp : lProxy) {
				System.out.println("proxy hostname : " + temp.type());

				InetSocketAddress addr = (InetSocketAddress) temp.address();
				if (addr == null) {
					System.out.println("No Proxy");
				} else {
					System.out.println("proxy hostname : " + addr.getHostName());

					System.out.println("proxy port : " + addr.getPort());

					proxy = temp;
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return proxy;
	}

	public static Proxy initProxySystemProxy(String proxyHost, int proxyPort) {
		SocketAddress sa = new InetSocketAddress(proxyHost, proxyPort);
		Proxy myProxy = new Proxy(Proxy.Type.HTTP, sa);
		return myProxy;
	}

	public static void main(String[] args) {
		ProxyCommon.getSystemProxy();
	}

}
