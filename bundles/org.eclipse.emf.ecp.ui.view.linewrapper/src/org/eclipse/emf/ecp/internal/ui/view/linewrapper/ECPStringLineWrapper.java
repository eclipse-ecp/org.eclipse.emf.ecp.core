/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Johannes Faltermeier - Bug 470478, Bug 459998
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.view.linewrapper;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.provider.ECPStringModifier;

/**
 * An {@link ECPStringModifier} which wraps texts automatically after 80 chars.
 *
 * @author Eugen Neufeld
 * @author Johannes Faltermeier
 *
 */
public class ECPStringLineWrapper implements ECPStringModifier {

	private static final int MAX_LINE_LENGTH = 80;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.provider.ECPStringModifier#modifyString(java.lang.String,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	public String modifyString(String text, Setting setting) {
		final String[] textLines = text.split("\\r?\\n"); //$NON-NLS-1$

		final StringBuilder allLines = new StringBuilder();
		for (int j = 0; j < textLines.length; j++) {
			final String line = textLines[j];
			if (j != 0) {
				allLines.append("\n"); //$NON-NLS-1$
			}
			final String wrappedLine = wrapLine(line);
			allLines.append(wrappedLine);
		}

		return allLines.toString();
	}

	private String wrapLine(final String line) {
		final StringBuilder sb = new StringBuilder(line);
		int i = 0;
		while (i + MAX_LINE_LENGTH < sb.length()) {
			i = sb.lastIndexOf("\n", i + MAX_LINE_LENGTH); //$NON-NLS-1$
			if (i == -1) {
				i = 0;
			}
			i = sb.lastIndexOf(" ", i + MAX_LINE_LENGTH); //$NON-NLS-1$
			int multiplicator = 2;
			while (i == -1) {
				i = sb.lastIndexOf(" ", i + multiplicator * MAX_LINE_LENGTH); //$NON-NLS-1$
				multiplicator++;
				if (multiplicator * MAX_LINE_LENGTH > sb.length()) {
					break;
				}
			}
			if (i == -1) {
				break;
			}
			sb.replace(i, i + 1, "\n"); //$NON-NLS-1$
		}
		final String wrappedLine = sb.toString();
		return wrappedLine;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.provider.ECPStringModifier#getPriority()
	 */
	@Override
	public double getPriority() {
		return 0;
	}

}
