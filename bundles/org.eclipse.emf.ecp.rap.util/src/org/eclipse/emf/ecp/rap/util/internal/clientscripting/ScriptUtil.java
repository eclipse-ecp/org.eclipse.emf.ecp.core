/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.rap.util.internal.clientscripting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Helper class for resolving java script snippets from a resource.
 * 
 * @author jfaltermeier
 * 
 */
public final class ScriptUtil {

	private ScriptUtil() {
		// util class
	}

	/**
	 * Returns the java script string for the given resource name.
	 * 
	 * @param resource the name of the resource which containts the js snippet.
	 * @return the java script snippet
	 */
	public static String getJavaScriptStringFromResource(String resource) {
		InputStream inputStream;
		try {
			final URL url = new URL("platform:/plugin/org.eclipse.emf.ecp.rap.util/script/" + resource); //$NON-NLS-1$
			inputStream = url.openConnection().getInputStream();
		} catch (final MalformedURLException ex1) {
			return ""; //$NON-NLS-1$
		} catch (final IOException ex1) {
			return ""; //$NON-NLS-1$
		}

		return readStringFromStream(inputStream);
	}

	private static String readStringFromStream(InputStream inputStream) {
		String result = ""; //$NON-NLS-1$
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line = reader.readLine();
			while (line != null) {
				result = result.concat(line + '\n');
				line = reader.readLine();
			}
		} catch (final IOException ex) {
			result = ""; //$NON-NLS-1$
		} finally {
			try {
				inputStream.close();
			} catch (final IOException ex) {
			}
		}
		return result;
	}
}
