/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.internal.preview;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Dialog for managing additional views for the preview.
 *
 * @author Eugen Neufeld
 *
 */
public class ManageAdditionalViewsDialog extends Dialog {

	private final Set<IPath> knownViews;

	/**
	 * Default constructor.
	 *
	 * @param parentShell The Shell to create the dialog on
	 * @param knownViews The Set of known views
	 */
	public ManageAdditionalViewsDialog(Shell parentShell, Set<IPath> knownViews) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.knownViews = knownViews;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.ManageAdditionalViewsDialog_Title);
		shell.setMinimumSize(300, 300);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(1, false));
		final Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.ManageAdditionalViewsDialog_LoadedView);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(label);
		final ListViewer listViewer = new ListViewer(composite, SWT.SINGLE | SWT.BORDER);
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new LabelProvider() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(Object element) {
				if (IPath.class.isInstance(element)) {
					return IPath.class.cast(element).toOSString();
				}
				return super.getText(element);
			}

		});
		listViewer.setInput(knownViews);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(listViewer.getControl());

		final Composite buttons = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(buttons);
		buttons.setLayout(new FillLayout(SWT.HORIZONTAL));
		final Button addView = new Button(buttons, SWT.PUSH);
		addView.setText(Messages.ManageAdditionalViewsDialog_AddView);
		addView.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				final WorkspaceResourceDialog d = new WorkspaceResourceDialog(getShell(), new WorkbenchLabelProvider(),
					new WorkbenchContentProvider());
				d.setAllowMultiple(false);
				d.setValidator(new ISelectionStatusValidator() {
					@Override
					public IStatus validate(Object[] selection) {
						if (selection == null || selection.length != 1 || !IFile.class.isInstance(selection[0])) {
							return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
								Messages.ManageAdditionalViewsDialog_NoFileSelected);
						}
						if (IFile.class.cast(selection[0]).getFileExtension().equals("view")) { //$NON-NLS-1$
							return Status.OK_STATUS;
						}
						return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							Messages.ManageAdditionalViewsDialog_NoViewModelSelected);
					}
				});
				d.loadContents();
				final int result = d.open();
				if (result == Window.OK) {
					knownViews.add(d.getSelectedFiles()[0].getFullPath());
					listViewer.refresh();
				}
			}
		});

		final Button removeView = new Button(buttons, SWT.PUSH);
		removeView.setText(Messages.ManageAdditionalViewsDialog_RemoveView);
		removeView.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				knownViews.remove(listViewer.getStructuredSelection().getFirstElement());
				listViewer.refresh();
			}
		});

		return composite;
	}

}
