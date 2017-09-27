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

/**
 * A modified version of {@link MatchItemComboViewer} that allow free text entry.
 * Entered text is matched against the list of combo items, but this control also
 * allows for values that do not match any item.
 */
public class MatchFreeItemComboViewer extends ComboViewer {

	/**
	 * This search buffer is only used when the combo's popup has been opened.
	 */
	private final TimeBoundStringBuffer searchBuffer;
	private String initialText;

	/**
	 * Constructor.
	 *
	 * @param combo the {@link CCombo} to be wrapped
	 */
	public MatchFreeItemComboViewer(CCombo combo) {
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
		searchBuffer.reset();
		ComboUtil.setClosestMatch(getCCombo(), getCCombo().getText());
	}

	/**
	 * Callback that is called when the escape key is released.
	 * By default, this method does nothing, but note that at this
	 * point in time the text already has been reset to the initial text.
	 */
	protected void onEscape() {
		searchBuffer.reset();
	}

	/**
	 * Initializes this viewer.
	 */
	protected void setupListeners() {
		getCCombo().addKeyListener(new MatchItemKeyAdapter());
		getCCombo().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				initialText = getCCombo().getText();
				// limit visible items
				final CCombo control = getCCombo();
				if (control != null) {
					final int itemCount = control.getItemCount();
					if (itemCount == 0) {
						control.setVisibleItemCount(0);
					} else {
						control.setVisibleItemCount(itemCount <= 25 ? itemCount : 25);
					}
				}
			}
		});
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
				onEnter();
			} else {
				if (!Character.isISOControl(keyEvent.character)) {
					if (getCCombo().getListVisible()) {
						ComboUtil.setClosestMatch(getCCombo(), searchBuffer.asString());
					} else {
						ComboUtil.setClosestMatch(getCCombo(), getCCombo().getText());
					}
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent keyEvent) {

			// reset in case the buffer has timed out or the popup is not visible at all
			if (searchBuffer.timedOut() || !getCCombo().getListVisible()) {
				searchBuffer.reset();
			}

			if (keyEvent.keyCode == SWT.ESC) {
				// reset to initial text
				getCCombo().setText(initialText);
			}

			if (!Character.isISOControl(keyEvent.character)) {
				if (getCCombo().getListVisible()) {
					keyEvent.doit = false;
					searchBuffer.addLast(keyEvent.character);
				}
			}
		}
	}
}
