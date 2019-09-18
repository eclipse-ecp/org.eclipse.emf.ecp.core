/*******************************************************************************
 * Copyright (c) 2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - Copied when updating from Nebula 2.1 to 2.2
 ******************************************************************************/
// REUSED CLASS
/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * chris.gross@us.ibm.com - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import org.eclipse.swt.graphics.GC;

/**
 * Utility class to provide common operations on strings not supported by the
 * base java API.
 *
 * @author chris.gross@us.ibm.com
 * @author Mirko Paturzo <mirko.paturzo@exeura.eu>
 * @author gongguangyong@live.cn
 *
 *         Mirko modified the pivot calculation for improve short text provider performance.
 *         The pivot number is calculate starting from the size of the cell provided
 *
 * @since 2.0.0
 */
public class TextUtils {

	/**
	 * Shortens a supplied string so that it fits within the area specified by
	 * the width argument. Strings that have been shorted have an "..." attached
	 * to the end of the string. The width is computed using the
	 * {@link GC#textExtent(String)}.
	 *
	 * @param gc GC used to perform calculation.
	 * @param t text to modify.
	 * @param width Pixels to display.
	 * @return shortened string that fits in area specified.
	 * @deprecated when text is large, performance is poor, possible occur OOM exception. suggest use
	 *             {@link #getShortStr(GC, String, int)}
	 */
	@Deprecated
	public static String getShortText(GC gc, String t, int width) {
		if (t == null) {
			return null;
		}

		if (t.equals("")) { //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

		if (width >= gc.textExtent(t).x) {
			return t;
		}

		final int w = gc.textExtent("...").x; //$NON-NLS-1$
		String text = t;
		final int l = text.length();
		final int pivot = l / 2;
		int s = pivot;
		int e = pivot + 1;

		while (s >= 0 && e < l) {
			final String s1 = text.substring(0, s);
			final String s2 = text.substring(e, l);
			final int l1 = gc.textExtent(s1).x;
			final int l2 = gc.textExtent(s2).x;
			if (l1 + w + l2 < width) {
				text = s1 + "..." + s2; //$NON-NLS-1$
				break;
			}
			s--;
			e++;
		}

		if (s == 0 || e == l) {
			text = text.substring(0, 1) + "..." + text.substring(l - 1, l); //$NON-NLS-1$
		}

		return text;
	}

	/**
	 * Shortens a supplied string so that it fits within the area specified by
	 * the width argument. Strings that have been shorted have an "..." attached
	 * to the end of the string. The width is computed using the
	 * {@link GC#stringExtent(String)}.
	 *
	 * @param gc GC used to perform calculation.
	 * @param t text to modify.
	 * @param width Pixels to display.
	 * @return shortened string that fits in area specified.
	 * @deprecated when text is large, performance is poor, possible occur OOM exception. suggest use
	 *             {@link #getShortStr(GC, String, int)}
	 */
	@Deprecated
	public static String getShortString(GC gc, String t, int width) {
		if (t == null) {
			return null;
		}

		if (t.equals("")) { //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

		final int textWidth = gc.stringExtent(t).x;
		if (width >= textWidth) {
			return t;
		}
		String text = t;
		final int l = text.length();
		final int w = gc.stringExtent("...").x; //$NON-NLS-1$
		final double midChar = (double) textWidth / l;
		final int pivot = (int) (width / midChar / 2);
		int s = pivot;
		int e = l - pivot + 1;

		while (s >= 0 && e < l) {
			final String s1 = text.substring(0, s);
			final String s2 = text.substring(e, l);
			final int l1 = gc.stringExtent(s1).x;
			final int l2 = gc.stringExtent(s2).x;
			if (l1 + w + l2 < width) {
				text = s1 + "..." + s2; //$NON-NLS-1$
				break;
			}
			s--;
			e++;
		}

		if (s == 0 || e == l) {
			text = text.substring(0, 1) + "..." + text.substring(l - 1, l); //$NON-NLS-1$
		}

		return text;
	}

	/**
	 * Shortens a supplied string so that it fits within the area specified by
	 * the width argument. Strings that have been shorted have an "..." attached
	 * to the end of the string. The width is computed using the
	 * {@link GC#getCharWidth(char)}.
	 *
	 * @param gc GC used to perform calculation.
	 * @param t text to modify.
	 * @param width Pixels to display.
	 * @return shortened string that fits in area specified.
	 */
	public static String getShortStr(GC gc, String t, int width) {
		if (t == null || t.equals("")) { //$NON-NLS-1$
			return t;
		}

		if (width >= gc.stringExtent(t).x) {
			return t;
		}

		final char[] chars = t.toCharArray();
		final int length = chars.length;
		int left = 0;
		int right = length - 1;
		int calcWidth = gc.stringExtent("...").x; //$NON-NLS-1$

		while (left < right) {
			int step = gc.getCharWidth(chars[left]);
			calcWidth += step;
			if (calcWidth >= width) {
				break;
			}
			left++;

			step = gc.getCharWidth(chars[right]);
			calcWidth += step;
			if (calcWidth >= width) {
				break;
			}
			right--;
		}
		if (left >= right) {
			return t;
		}
		final StringBuilder builder = new StringBuilder(left + length - right + 4);
		if (left == 0 || right == length - 1) {
			builder.append(chars[0]).append("...").append(chars[length - 1]); //$NON-NLS-1$
		} else {
			final int leftLen = left == 1 ? left : left - 1;
			builder.append(chars, 0, leftLen).append("...").append(chars, right + 1, length - right - 1); //$NON-NLS-1$
		}
		return builder.toString();
	}

	/**
	 * private constructor to prevent instantiation.
	 */
	private TextUtils() {
		// is empty
	}
}
