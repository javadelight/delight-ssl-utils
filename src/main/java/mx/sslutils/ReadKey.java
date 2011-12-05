/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package mx.sslutils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An utility to read out a key file and represent it as a static array.
 * 
 * @author mroh004
 * 
 */
public class ReadKey {

	public static byte[] read(final File file) throws IOException {
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			final byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
			return ous.toByteArray();
		} finally {

			if (ous != null)
				ous.close();

			if (ios != null)
				ios.close();

		}

	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) throws Exception {
		final File f = new File("export.crt");
		assert f.exists();

		final byte[] data = read(f);
//		 for (int i=0; i<data.length; i++) {
//		 System.out.print(data[i]+", ");
//		 }

//		System.out.println(Arrays.equals(data, NxDefaultSSLCertificate.DATA));

	}

}
