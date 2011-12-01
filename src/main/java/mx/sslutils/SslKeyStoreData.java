package mx.sslutils;

import java.io.InputStream;

public interface SslKeyStoreData {
	public  InputStream asInputStream() ;

	public  char[] getCertificatePassword() ;

	public  char[] getKeyStorePassword() ;
}
