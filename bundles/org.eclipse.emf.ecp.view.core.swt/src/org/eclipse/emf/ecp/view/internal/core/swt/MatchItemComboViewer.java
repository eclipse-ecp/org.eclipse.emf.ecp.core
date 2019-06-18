/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

/**
 * A {@link ComboViewer} that allows typed text to be matched against
 * the combo viewer's items and also allows setting the selection via arrow keys.
 * If the escape key pressed, the content of the editor is set back to its initial state.
 */
public class MatchItemComboViewer extends ComboViewer {

	private final TimeBoundStringBuffer searchBuffer;
	private String initialText;

	/**
	 * Constructor.
	 *
	 * @param combo the {@link CCombo} to be wrapped
	 */
	public MatchItemComboViewer(CCombo combo) {
		super(combo);
		searchBuffer = new TimeBoundStringBuffer();
		setupListeners();
	}

	/**
	 * Callback that is called when the enter key is released.
	 * By default, this method sets the selection to be the one
	 * that matches the text within the combo, ignoring the given
	 * parameter.
	 *
	 */
	public void onEnter() {
		setClosestMatch(getCCombo().getText());
	}

	/**
	 * Returns the search buffer used during matching.
	 *
	 * @return the {@link TimeBoundStringBuffer}
	 */
	public TimeBoundStringBuffer getBuffer() {
		return searchBuffer;
	}

	/**
	 * Match given text against items of combo and set selection, if applicable.
	 * If no match has been found, reset to initial state.
	 *
	 * @param text the string to be matched
	 */
	public void setClosestMatch(String text) {
		final boolean matchFound = ComboUtil.setClosestMatch(getCCombo(), text);
		// if no literal matches, reset to initial state
		if (!matchFound) {
			reset();
		}
	}

	/**
	 * Callback that is called when the escape key is released.
	 * By default, this method does nothing, but note that at this
	 * point in time the text already has been reset to the initial text.
	 */
	protected void onEscape() {

	}

	/**
	 * Initializes this viewer.
	 */
	protected void setupListeners() {
		getCCombo().addKeyListener(new MatchItemKeyAdapter());
		getCCombo().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// reset buffer when focus has been lost
				searchBuffer.reset();
			}

			@Override
			public void focusGained(FocusEvent e) {
				initialText = getCCombo().getText();
			}
		});
	}

	/**
	 * Reset to initial state, i.e. the initial text is restored and the search buffer is emptied.
	 */
	protected void reset() {
		getCCombo().setText(initialText);
		searchBuffer.reset();
	}

	/**
	 * Key adapter for matching typed in text and selecting the closest match.
	 *
	 */
	class MatchItemKeyAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent keyEvent) {
			if (keyEvent.keyCode == SWT.ESC) {
				onEscape();
			} else if (keyEvent.keyCode == SWT.CR) {
				keyEvent.doit = true;
				onEnter();
				searchBuffer.reset();
			} else if (keyEvent.keyCode == SWT.ARROW_DOWN || keyEvent.keyCode == SWT.ARROW_UP) {
				// enable skipping through the list item by item
				keyEvent.doit = true;
				searchBuffer.reset();
			} else {
				// only update buffer in case it is a visible character
				keyEvent.doit = false;
				if (!Character.isISOControl(keyEvent.character) || keyEvent.keyCode == SWT.BS) {
					setClosestMatch(searchBuffer.asString());
				} else if (keyEvent.keyCode != SWT.SHIFT) {
					searchBuffer.reset();
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent keyEvent) {
			keyEvent.doit = false;
			if (searchBuffer.timedOut() && keyEvent.keyCode != SWT.CR) {
				searchBuffer.reset();
			}

			if (keyEvent.keyCode == SWT.ESC) {
				// reset to initial text
				getCCombo().setText(initialText);
			} else if (!Character.isISOControl(keyEvent.character)) {
				searchBuffer.addLast(keyEvent.character);
			} else if (keyEvent.keyCode == SWT.BS) {
				searchBuffer.removeLast();
			} else if (keyEvent.keyCode == SWT.ARROW_DOWN || keyEvent.keyCode == SWT.ARROW_UP) {
				// enable skipping through the list item by item
				keyEvent.doit = true;
			}
		}
	}
}
