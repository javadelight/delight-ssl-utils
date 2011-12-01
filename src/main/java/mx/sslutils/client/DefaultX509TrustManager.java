package mx.sslutils.client;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.X509TrustManager;


public final class DefaultX509TrustManager implements
		X509TrustManager {
	@Override
	public X509Certificate[] getAcceptedIssuers() {

		try {
			final InputStream inStream = NxDefaultSSLCertificate
					.asInputStream();
			final CertificateFactory cf = CertificateFactory
					.getInstance("X.509");
			final X509Certificate cert = (X509Certificate) cf
					.generateCertificate(inStream);
			inStream.close();
			return new X509Certificate[] { cert };
		} catch (final Throwable t) {
			throw new RuntimeException(t);
		}

	}

	@Override
	public void checkServerTrusted(
			final java.security.cert.X509Certificate[] certs,
			final String authType)
			throws java.security.cert.CertificateException {
		if (!Arrays.equals(certs, getAcceptedIssuers())) {
			throw new CertificateException("Invalid certificate "+certs[0]);
		}
		return;
	}

	@Override
	public void checkClientTrusted(
			final java.security.cert.X509Certificate[] certs,
			final String authType)
			throws java.security.cert.CertificateException {
		return;
	}
}