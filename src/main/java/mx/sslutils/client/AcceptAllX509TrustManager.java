package mx.sslutils.client;

import javax.net.ssl.X509TrustManager;

public class AcceptAllX509TrustManager implements X509TrustManager {

	@Override
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	@Override
	public void checkClientTrusted(
			final java.security.cert.X509Certificate[] certs,
			final String authType) {
	}

	@Override
	public void checkServerTrusted(
			final java.security.cert.X509Certificate[] certs,
			final String authType) {
	}

}
