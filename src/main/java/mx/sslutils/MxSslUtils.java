/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package mx.sslutils;

import java.util.IdentityHashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import mx.sslutils.internal.SslContextFactory;

public class MxSslUtils {

    private static Map<SslKeyStoreData, SSLContext> cache;

    static {
        cache = new IdentityHashMap<SslKeyStoreData, SSLContext>();
    }

    /**
     * A generic certificate for a linnk.it node.
     * 
     * @return
     */
    public static SslKeyStoreData getLinnkItServerCertificate() {
        return new NxUncertifiedSslKeyStore();
    }

    public static SSLContext createContextForCertificate(
            final SslKeyStoreData keyStoreData) {
        if (cache.containsKey(keyStoreData)) {
            return cache.get(keyStoreData);
        }

        final SSLContext newContext = SslContextFactory
                .getServerContext(keyStoreData);

        cache.put(keyStoreData, newContext);

        return newContext;
    }

    public static SSLContext getClientContext() {
        SSLContext clientContext = null;
        try {
            clientContext = SSLContext.getInstance(SslContextFactory.PROTOCOL);
            clientContext.init(null, SslTrustManagerFactory.getTrustManagers(),
                    null);
        } catch (final Exception e) {
            throw new Error("Failed to initialize the client-side SSLContext",
                    e);
        }
        return clientContext;
    }

}
