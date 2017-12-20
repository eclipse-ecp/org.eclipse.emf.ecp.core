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
package org.eclipse.emf.ecp.ide.internal.migration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.internal.ui.validation.ValidationTreeViewerFactory;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog that show the result of migration.
 */
public class ViewMigrationResultDialog extends TitleAreaDialog {

	private final List<Diagnostic> errors;
	private final List<String> unresolvedViews;

	/**
	 * Constructor.
	 *
	 * @param parentShell the parent shell
	 * @param diagnostics the validation result of the migration
	 * @param showWarnings whether migration warnings should be shown
	 */
	public ViewMigrationResultDialog(Shell parentShell, Map<String, Optional<Diagnostic>> diagnostics,
		boolean showWarnings) {
		super(parentShell);
		errors = new LinkedList<Diagnostic>();
		unresolvedViews = new LinkedList<String>();

		if (showWarnings) {
			for (final Map.Entry<String, Optional<Diagnostic>> diagnostic : diagnostics.entrySet()) {
				final Optional<Diagnostic> diag = diagnostic.getValue();
				if (diag.isPresent() && diag.get().getSeverity() == Diagnostic.ERROR) {
					errors.add(diag.get());
				} else if (!diag.isPresent()) {
					unresolvedViews.add(diagnostic.getKey());
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.ViewMigrationResultDialog_ShellTitle);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		setTitle(Messages.ViewMigrationResultDialog_Title);
		setMessage(errors.isEmpty() ? Messages.ViewMigrationResultDialog_MigrationSuccessMessage
			: Messages.ViewMigrationResultDialog_MigrationFailureMessage);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		area.setLayout(new GridLayout(1, true));

		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);

		if (errors.isEmpty() && unresolvedViews.isEmpty()) {
			final Label label = new Label(container, SWT.NONE);
			label.setText(Messages.ViewMigrationResultDialog_MigrationSuccess);
			label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		if (!errors.isEmpty()) {
			final TreeViewer diagnosticView = ValidationTreeViewerFactory.createValidationViewer(container);
			diagnosticView.setInput(errors);
			diagnosticView.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		if (!unresolvedViews.isEmpty()) {
			final Label label = new Label(container, SWT.NONE);
			label.setText(Messages.ViewMigrationResultDialog_UnresolvedViews);
			label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			final ListViewer listViewer = new ListViewer(container);
			listViewer.getList().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			listViewer.setContentProvider(new ArrayContentProvider());
			listViewer.setInput(unresolvedViews);
		}

		return parent;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create only OK button
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
	}
}
