/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.util.PreSetValidationStrategy;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Cell editor for string based cell editors that implements a custom
 * activate method that propagates changes on first key stroke.
 *
 * @since 1.14
 *
 */
public abstract class StringBasedCellEditor extends TextCellEditor implements ECPCellEditor {

	private String initialValue;

	/**
	 * Default constructor.
	 */
	public StringBasedCellEditor() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param parent the parent {@link Composite}
	 */
	public StringBasedCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * Constructor.
	 *
	 * @param parent the parent {@link Composite}
	 * @param style SWT styling bits
	 */
	public StringBasedCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#activate(org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent)
	 */
	@Override
	public void activate(ColumnViewerEditorActivationEvent event) {
		initialValue = text.getText();
		if (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED
			&& isPrintable(event.character)
			&& (getStyle() & SWT.READ_ONLY) == 0) {

			doSetValue(String.valueOf(event.character));
		}
		super.activate(event);
	}

	@Override
	protected void fireCancelEditor() {
		if (text != null && !text.isDisposed()
			&& text.getText() != null
			&& !text.getText().equals(initialValue)) {
			doSetValue(initialValue);
		}
		super.fireCancelEditor();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.TextCellEditor#doSetFocus()
	 */
	@Override
	protected void doSetFocus() {
		super.doSetFocus();
		if (text.getText() != null) {
			text.setSelection(text.getText().length());
		}
	}

	/**
	 * Create a {@link PreSetValidationStrategy}.
	 *
	 * @param feature the feature the cell editor is bound against
	 * @param delegate a delegate {@link UpdateValueStrategy}
	 *
	 * @return a {@link PreSetValidationStrategy}
	 *
	 */
	protected UpdateValueStrategy withPreSetValidation(EStructuralFeature feature, UpdateValueStrategy delegate) {
		return new PreSetValidationStrategy(null, feature, delegate);
	}

	/**
	 * Determines whether the given character is printable.
	 * Mimics behavior of Nebula's LetterOrDigitKeyEventMatcher.
	 *
	 *
	 * @param character the character to be checked
	 * @return {@code true}, if the character can be printed, {@code false} otherwise
	 */
	protected boolean isPrintable(char character) {
		return Character.isLetterOrDigit(character)
			|| Character
				.valueOf(character)
				.toString()
				.matches("[\\.:,;\\-_#\'+*~!?§$%&/()\\[\\]\\{\\}=\\\\\"]"); //$NON-NLS-1$
	}
}
