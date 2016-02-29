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
package org.eclipse.emfforms.internal.swt.table;

import java.util.Collections;
import java.util.List;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
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
		titleLabel = new Label(topComposite, SWT.NONE);
		titleLabel.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(titleLabel);

		/* Validation icon label */
		validationLabel = createValidationLabel(topComposite);

		/* Button composite */
		buttonComposite = new Composite(topComposite, SWT.NONE);
		buttonComposite.setBackground(topComposite.getBackground());
		GridDataFactory.fillDefaults().align(SWT.END, SWT.BEGINNING).grab(true, false).applyTo(buttonComposite);

		/* Bottom composite */
		viewerComposite = createViewerComposite(composite);
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
