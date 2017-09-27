/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;

/**
 * Combo helper class.
 *
 */
public final class ComboUtil {

	private ComboUtil() {
		// private ctor
	}

	/**
	 * Returns the index of the closest match.
	 *
	 * @param items an array of string to match the given string against
	 * @param str the string to be matched
	 *
	 * @return the index of the closest match
	 */
	public static int getClosestMatchIndex(String[] items, String str) {
		for (int i = 0; i < items.length; i++) {
			if (items[i].toLowerCase().startsWith(str.toLowerCase())) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Set the closest match based on the given String.
	 *
	 * @param combo the {@link CCombo} which should be updated in case the given string matches
	 * @param s the String the closest match should be selected by
	 *
	 * @return {@code true}, if the a match has been found and set, {@code false} otherwise
	 */
	public static boolean setClosestMatch(CCombo combo, String s) {
		final String[] comboItems = combo.getItems();
		final int index = getClosestMatchIndex(comboItems, s);
		if (index != -1) {
			final String item = comboItems[index];
			final Point pt = combo.getSelection();
			combo.select(index);
			combo.setText(item);
			combo.setSelection(new Point(pt.x, item.length()));
			return true;
		}
		return false;
	}
}
