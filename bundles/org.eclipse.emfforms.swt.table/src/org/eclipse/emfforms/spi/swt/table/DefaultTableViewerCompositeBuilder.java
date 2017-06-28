/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.Collections;
import java.util.List;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Default implementation of the {@link TableViewerCompositeBuilder}.
 *
 * @author Johannes Faltermeier
 *
 */
public class DefaultTableViewerCompositeBuilder implements TableViewerCompositeBuilder {

	private Label titleLabel;
	private Label validationLabel;
	private Composite buttonComposite;
	private Composite viewerComposite;

	@Override
	public void createCompositeLayout(Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(parent);
		/* Overall Layout */
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		composite.setLayout(new GridLayout(1, false));

		/* Top composite */
		final Composite topComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(topComposite);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(topComposite);

		/* Title label */
		titleLabel = createTitleLabel(topComposite, parent.getBackground());

		/* Validation icon label */
		validationLabel = createValidationLabel(topComposite);

		/* Button composite */
		buttonComposite = createButtonComposite(topComposite);

		/* Bottom composite */
		viewerComposite = createViewerComposite(composite);
	}

	/**
	 * Returns the {@link Label} displaying the table viewer's label. Can be overwritten to customize the label's
	 * appearance.
	 *
	 * @param parentComposite The parent composite of the created label
	 * @param background The background color of the label
	 *
	 * @return The title label
	 */
	protected Label createTitleLabel(final Composite parentComposite, Color background) {
		final Label titleLabel = new Label(parentComposite, SWT.NONE);
		titleLabel.setBackground(background);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(titleLabel);
		return titleLabel;
	}

	/**
	 * Creates and returns the button composite used by this table viewer. Can be overwritten to customize the
	 * composite's appearance and placement.
	 *
	 * @param parentComposite The composite that will contain the button composite
	 * @return The button composite
	 */
	protected Composite createButtonComposite(final Composite parentComposite) {
		final Composite buttonComposite = new Composite(parentComposite, SWT.NONE);
		buttonComposite.setBackground(parentComposite.getBackground());
		GridDataFactory.fillDefaults().align(SWT.END, SWT.BEGINNING).grab(true, false)
			.applyTo(buttonComposite);
		return buttonComposite;
	}

	/**
	 * Called to create the composite for the table viewer.
	 *
	 * @param composite the parent
	 * @return the composite
	 */
	protected Composite createViewerComposite(final Composite composite) {
		final Composite viewerComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(1, 200)
			.applyTo(viewerComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(viewerComposite);
		return viewerComposite;
	}

	/**
	 * Called to create the validation label.
	 *
	 * @param topComposite the parent
	 * @return the label
	 */
	protected Label createValidationLabel(final Composite topComposite) {
		final Label validationLabel = new Label(topComposite, SWT.NONE);
		validationLabel.setBackground(topComposite.getBackground());
		GridDataFactory.fillDefaults().hint(16, 17).grab(false, false).applyTo(validationLabel);
		return validationLabel;
	}

	@Override
	public Optional<Label> getTitleLabel() {
		return Optional.of(titleLabel);
	}

	@Override
	public Optional<List<Control>> getValidationControls() {
		return Optional.of(Collections.<Control> singletonList(validationLabel));
	}

	@Override
	public Optional<Composite> getButtonComposite() {
		return Optional.of(buttonComposite);
	}

	@Override
	public Composite getViewerComposite() {
		return viewerComposite;
	}

}
