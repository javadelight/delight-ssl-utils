/*
 * Copyright 2009 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package mx.sslutils.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import de.mxro.sslutils.SslKeyStoreData;
import mx.gwtutils.Base64Coder;

public class SslContextFactory {

    public static final String PROTOCOL = "TLS";

    // <!-- one.download
    // https://u1.linnk.it/qc8sbw/usr/apps/textsync/files/fragements-stream-to-string
    // -->
    public static String toString(final InputStream inputStream)
            throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return new String(baos.toByteArray());
    }

    // <!-- one.end -->

    public static SSLContext getServerContext(final SslKeyStoreData keyStoreData) {

        String algorithm = Security
                .getProperty("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }

        SSLContext serverContext = null;

        if (keyStoreData.encoding().equals("CUSTOMBASE64")) {
            try {

                final KeyStore ks = KeyStore.getInstance("JKS");
                ks.load(new ByteArrayInputStream(Base64Coder
                        .decode(toString(keyStoreData.asInputStream()))),
                        keyStoreData.getKeyStorePassword());
                final KeyManagerFactory kmf = KeyManagerFactory
                        .getInstance(algorithm);
                kmf.init(ks, keyStoreData.getCertificatePassword());

                serverContext = SSLContext.getInstance(PROTOCOL);
                serverContext.init(kmf.getKeyManagers(), null, null);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        } else

        if (keyStoreData.encoding().equals("BYTE")) {
            try {
                final KeyStore ks = KeyStore.getInstance("JKS");
                ks.load(keyStoreData.asInputStream(),
                        keyStoreData.getKeyStorePassword());

                // Set up key manager factory to use our key store
                final KeyManagerFactory kmf = KeyManagerFactory
                        .getInstance(algorithm);
                kmf.init(ks, keyStoreData.getCertificatePassword());

                // Initialize the SSLContext to work with our key managers.
                serverContext = SSLContext.getInstance(PROTOCOL);
                serverContext.init(kmf.getKeyManagers(), null, null);
            } catch (final Exception e) {
                throw new Error(
                        "Failed to initialize the server-side SSLContext", e);
            }
        } else if (keyStoreData.encoding().equals("BASE64")) {
            try {
                serverContext = SSLContext.getInstance("TLS");
                final KeyStore ks = KeyStore.getInstance("PKCS12");
                ks.load(new ByteArrayInputStream(Base64Coder
                        .decode(toString(keyStoreData.asInputStream()))),
                        keyStoreData.getKeyStorePassword());
                final KeyManagerFactory kmf = KeyManagerFactory
                        .getInstance(algorithm);
                kmf.init(ks, keyStoreData.getCertificatePassword());
                serverContext.init(kmf.getKeyManagers(), null, null);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Keystore Encoding not supported: "
                    + keyStoreData.encoding());
        }

        return serverContext;
    }
}
