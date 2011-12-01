package mx.sslutils.client;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public final class DefaultHostNameVerifier implements
		HostnameVerifier {
	@Override
	public boolean verify(final String arg0, final SSLSession arg1) {
		return true;
	}
}