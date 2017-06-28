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

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * A {@link ComboViewer} that allows typed text to be matched against
 * the combo viewer's items and also allows setting the selection via arrow keys.
 *
 */
public class MatchItemComboViewer extends ComboViewer {

	private static final int TIMEOUT = 1000;
	private long lastKeyPressMillis = -1;
	private final StringBuffer searchBuffer;

	/**
	 * Constructor.
	 *
	 * @param combo the {@link CCombo} to be wrapped
	 */
	public MatchItemComboViewer(CCombo combo) {
		super(combo);
		searchBuffer = new StringBuffer();
		init();
	}

	/**
	 * Constructor.
	 *
	 * @param area the parent {@link Composite}
	 * @param style SWT style bits
	 */
	public MatchItemComboViewer(Composite area, int style) {
		super(area, style);
		searchBuffer = new StringBuffer();
		init();
	}

	/**
	 * Callback that is called when the enter key is released.
	 * By default, this method sets the selection to be the one
	 * that matches the text within the combo, ignoring the given
	 * parameter.
	 *
	 * @param selectedIndex the index of the item that is the closest match
	 */
	public void onEnter(int selectedIndex) {
		setClosestMatch(getCCombo().getText());
	}

	/**
	 * Initializes this viewer.
	 */
	protected void init() {
		getCCombo().addKeyListener(new MatchItemKeyAdapter());
		getCCombo().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// reset buffer when focus has been lost
				resetBuffer();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}

	/**
	 * Returns the index of the closest match.
	 *
	 * @param str the String to be matched
	 * @return the index of the closest match
	 */
	public int getClosestMatchIndex(String str) {
		final String[] cItems = getCCombo().getItems();
		// Find Item in Combo Items. If full match returns index
		for (int i = 0; i < cItems.length; i++) {
			if (cItems[i].toLowerCase().startsWith(str.toLowerCase())) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Set the closest match based on the given String.
	 *
	 * @param s the String the closest match should be selected by
	 */
	protected void setClosestMatch(String s) {
		final CCombo combo = getCCombo();
		final String[] comboItems = combo.getItems();
		final int index = getClosestMatchIndex(s);
		if (index != -1) {
			final String item = comboItems[index];
			final Point pt = combo.getSelection();
			combo.select(index);
			combo.setText(item);
			combo.setSelection(new Point(pt.x, item.length()));
		}
	}

	/**
	 * Whether the key press timeout has been hit.
	 *
	 * @return {@code true}, if the timeout has been hit, {@code false} otherwise
	 */
	protected boolean keyPressTimedOut() {
		long timeElapsed = -1;
		if (lastKeyPressMillis > 0) {
			timeElapsed = System.currentTimeMillis() - lastKeyPressMillis;
		}
		return timeElapsed != -1 && timeElapsed > TIMEOUT;
	}

	/**
	 * Reset the key pressed timer.
	 */
	public void resetKeyPressedTimer() {
		lastKeyPressMillis = -1;
	}

	/**
	 * Reset the key pressed timeout.
	 */
	protected void resetKeyPressedTimeout() {
		lastKeyPressMillis = System.currentTimeMillis();
	}

	/**
	 * Append a character to the search buffer.
	 *
	 * @param character the {@link Character} to be appended
	 */
	public void addToBuffer(Character character) {
		searchBuffer.append(Character.toString(character));
	}

	/**
	 * Removes the last character from the search buffer.
	 */
	private void removeLastFromBuffer() {
		searchBuffer.deleteCharAt(searchBuffer.length() - 1);
	}

	/**
	 * Clears the search buffer.
	 */
	public void resetBuffer() {
		searchBuffer.setLength(0);
	}

	/**
	 * Whether the search buffer is empty.
	 *
	 * @return {@code true}, in case the buffer is empty, {@code false} otherwise
	 */
	public boolean isEmptyBuffer() {
		return searchBuffer.length() == 0;
	}

	/**
	 * Returns the search buffer as a string.
	 *
	 * @return the string representation of the buffer
	 */
	private String bufferAsString() {
		return searchBuffer.toString();
	}

	/**
	 * Key adapter for matching typed in text and selecting the closest match.
	 *
	 */
	class MatchItemKeyAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent keyEvent) {
			if (keyEvent.keyCode == SWT.CR) {
				keyEvent.doit = true;
				final int selectedIndex = getClosestMatchIndex(bufferAsString());
				onEnter(selectedIndex);
				resetBuffer();
				resetKeyPressedTimer();
			} else if (keyEvent.keyCode == SWT.ARROW_DOWN || keyEvent.keyCode == SWT.ARROW_UP) {
				// enable skipping through the list item by item
				keyEvent.doit = true;
				resetBuffer();
			} else {
				// only update buffer in case it is a visible character
				keyEvent.doit = false;
				if (!Character.isISOControl(keyEvent.character) || keyEvent.keyCode == SWT.BS) {
					setClosestMatch(bufferAsString());
				} else if (keyEvent.keyCode != SWT.SHIFT) {
					resetBuffer();
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent keyEvent) {
			keyEvent.doit = false;
			if (keyPressTimedOut() && keyEvent.keyCode != SWT.CR) {
				resetBuffer();
			}

			if (!Character.isISOControl(keyEvent.character)) {
				addToBuffer(keyEvent.character);
				resetKeyPressedTimeout();
			} else if (keyEvent.keyCode == SWT.BS && !isEmptyBuffer()) {
				removeLastFromBuffer();
				resetKeyPressedTimeout();
			} else if (keyEvent.keyCode == SWT.ARROW_DOWN || keyEvent.keyCode == SWT.ARROW_UP) {
				// enable skipping through the list item by item
				keyEvent.doit = true;
			}
		}
	}
}
