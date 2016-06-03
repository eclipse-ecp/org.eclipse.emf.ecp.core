/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

/**
 * Based on org.eclipse.draw2d.FigureUtilities .
 */
public final class FigureUtilities {

	private static GC gc;
	private static Font appliedFont;

	private FigureUtilities() {
		// util
	}

	/**
	 * Returns the GC used for various utilities. Advanced graphics must not be
	 * switched on by clients using this GC.
	 *
	 * @return the GC
	 */
	private static GC getGC() {
		if (gc == null) {
			gc = new GC(new Shell());
			appliedFont = gc.getFont();
		}
		return gc;
	}

	/**
	 * Returns the dimensions of the String <i>s</i> using the font <i>f</i>.
	 * Tab expansion and carriage return processing are performed.
	 *
	 * @param s
	 *            the string
	 * @param f
	 *            the font
	 * @return the text's dimensions
	 * @see GC#textExtent(String)
	 */
	public static org.eclipse.swt.graphics.Point getTextDimension(String s,
		Font f) {
		setFont(f);
		return getGC().textExtent(s);
	}

	/**
	 * Returns the width of <i>s</i> in Font <i>f</i>.
	 *
	 * @param s
	 *            the string
	 * @param f
	 *            the font
	 * @return the width
	 */
	public static int getTextWidth(String s, Font f) {
		return getTextDimension(s, f).x;
	}

	/**
	 * Sets Font to passed value.
	 *
	 * @param f
	 *            the new font
	 */
	private static void setFont(Font f) {
		if (appliedFont == f || f != null && f.equals(appliedFont)) {
			return;
		}
		getGC().setFont(f);
		appliedFont = f;
	}

}
