/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.Collections;
import java.util.List;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Vertically Compact implementation of the {@link TableViewerCompositeBuilder}.
 *
 * @author Stefan Dirix
 * @since 1.14
 *
 */
public class CompactVerticallyTableViewerCompositeBuilder implements TableViewerCompositeBuilder {

	private Label titleLabel;
	private Label validationLabel;
	private Composite buttonComposite;
	private Composite viewerComposite;
	private final boolean createTitleLabel;
	private final boolean createValidationLabel;

	/**
	 * Constructor.
	 *
	 * @param createTitleLabel indicates whether to create a title label.
	 * @param createValidationLabel indicates whether to create a validation label.
	 */
	public CompactVerticallyTableViewerCompositeBuilder(boolean createTitleLabel, boolean createValidationLabel) {
		this.createTitleLabel = createTitleLabel;
		this.createValidationLabel = createValidationLabel;
	}

	@Override
	public void createCompositeLayout(Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(parent);

		/* Overall Layout */
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		final int numberOfColumns = createTitleLabel || createValidationLabel ? 3 : 2;
		GridLayoutFactory.fillDefaults().numColumns(numberOfColumns).applyTo(composite);

		/* Title + Validation Composite */
		if (createTitleLabel || createValidationLabel) {
			final Composite frontComposite = new Composite(composite, SWT.NONE);
			GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.BEGINNING)
				.applyTo(frontComposite);
			final int numberOfFrontColumns = createTitleLabel && createValidationLabel ? 2 : 1;
			GridLayoutFactory.fillDefaults().numColumns(numberOfFrontColumns).equalWidth(false).applyTo(frontComposite);
			titleLabel = createTitleLabel(frontComposite, parent.getBackground());
			validationLabel = createValidationLabel(frontComposite);
		}

		/* Viewer composite */
		viewerComposite = createViewerComposite(composite);

		/* Button composite */
		buttonComposite = createButtonComposite(composite);
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
		GridDataFactory.fillDefaults().align(SWT.END, SWT.BEGINNING).grab(false, false)
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
		return Optional.ofNullable(titleLabel);
	}

	@Override
	public Optional<List<Control>> getValidationControls() {
		if (validationLabel != null) {
			return Optional.of(Collections.<Control> singletonList(validationLabel));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Composite> getButtonComposite() {
		return Optional.ofNullable(buttonComposite);
	}

	@Override
	public Composite getViewerComposite() {
		return viewerComposite;
	}

}
