/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package mx.sslutils;

import java.io.InputStream;

public interface SslKeyStoreData {

    public String encoding();

    public InputStream asInputStream();

    public char[] getCertificatePassword();

    public char[] getKeyStorePassword();
}
