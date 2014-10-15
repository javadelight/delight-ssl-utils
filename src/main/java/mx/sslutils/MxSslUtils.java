/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package mx.sslutils;

import javax.net.ssl.SSLContext;

import de.mxro.sslutils.SslKeyStoreData;
import de.mxro.sslutils.internal.SslContextFactory;

public class MxSslUtils {

    /**
     * A generic certificate for a linnk.it node.
     * 
     * @return
     */
    public static SslKeyStoreData getLinnkItServerCertificate() {
        return new NxUncertifiedSslKeyStore();
    }

    public static SSLContext getClientContext() {
        SSLContext clientContext = null;
        try {
            clientContext = SSLContext.getInstance(SslContextFactory.PROTOCOL);
            clientContext.init(null, SslTrustManagerFactory.getTrustManagers(), null);
        } catch (final Exception e) {
            throw new Error("Failed to initialize the client-side SSLContext", e);
        }
        return clientContext;
    }

}
