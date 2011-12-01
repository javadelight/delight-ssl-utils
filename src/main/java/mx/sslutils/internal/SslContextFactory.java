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

import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import mx.sslutils.SslKeyStoreData;

public class SslContextFactory {

	public static final String PROTOCOL = "TLS";

	
	public static SSLContext getServerContext(final SslKeyStoreData keyStoreData) {

		String algorithm = Security
				.getProperty("ssl.KeyManagerFactory.algorithm");
		if (algorithm == null) {
			algorithm = "SunX509";
		}

		SSLContext serverContext = null;

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
			throw new Error("Failed to initialize the server-side SSLContext",
					e);
		}

		return serverContext;
	}
}
