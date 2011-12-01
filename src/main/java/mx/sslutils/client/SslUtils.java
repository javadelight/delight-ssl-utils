package mx.sslutils.client;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SslUtils {

	private static SSLSocketFactory sslSocketFactory = null;
	private static HostnameVerifier hostnameVerifier = new DefaultHostNameVerifier();
	
	public static SSLSocketFactory getSSLSocketFactory() {
		if (sslSocketFactory == null) {
			final TrustManager[] trustNxCerts = new TrustManager[] { new DefaultX509TrustManager() };

			try {
				final SSLContext sc = SSLContext.getInstance("TLS");

				sc.init(null, trustNxCerts, new java.security.SecureRandom());

				sslSocketFactory = sc.getSocketFactory();
			} catch (final KeyManagementException e) {
				throw new RuntimeException(e);
			} catch (final NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}

		return sslSocketFactory;

	}

	public static final void defaultNxCertificateChecking(
			final HttpsURLConnection connection) {

		connection.setSSLSocketFactory(getSSLSocketFactory());
		connection.setHostnameVerifier(hostnameVerifier);

	}

}
