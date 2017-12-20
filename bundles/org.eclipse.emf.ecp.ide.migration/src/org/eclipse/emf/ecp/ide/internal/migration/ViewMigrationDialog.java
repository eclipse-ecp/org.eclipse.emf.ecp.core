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
package org.eclipse.emf.ecp.ide.internal.migration;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A dialog that enables the migration of view model files.
 *
 */
public class ViewMigrationDialog extends TitleAreaDialog {

	private Text updatedNamespaceText;
	private Text currentNamespaceText;
	private String oldNamespaceFragment;
	private String newNamespaceFragment;
	private Button showWarningsButton;
	private boolean showWarnings;

	/**
	 * Constructor.
	 *
	 * @param parentShell the parent shell of this dialog
	 */
	public ViewMigrationDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.ViewMigrationDialog_ShellTitle);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		setTitle(Messages.UpdateNSDialog_MigrationDialogTitle);
		setMessage(Messages.UpdateNSDialog_MigrationDialog_Message, IMessageProvider.INFORMATION);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		final Label label = new Label(container, SWT.NONE);
		label.setText(
			Messages.ViewMigrationDialog_LabelText);
		currentNamespaceText = createInput(container, Messages.ViewMigrationDialog_CurrentNSLabel);
		updatedNamespaceText = createInput(container, Messages.ViewMigrationDialog_UpdatedNSLabel);
		label.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).create());

		showWarningsButton = new Button(container, SWT.CHECK);
		showWarningsButton.setText(Messages.ViewMigrationDialog_ShowWarnings);
		showWarningsButton.setSelection(true);

		return area;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.TrayDialog#isHelpAvailable()
	 */
	@Override
	public boolean isHelpAvailable() {
		return false;
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

	private Text createInput(Composite container, String labelText) {
		final Label label = new Label(container, SWT.NONE);
		label.setText(labelText);
		final GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		final Text text = new Text(container, SWT.BORDER);
		text.setLayoutData(layoutData);
		return text;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		oldNamespaceFragment = currentNamespaceText.getText();
		newNamespaceFragment = updatedNamespaceText.getText();
		showWarnings = showWarningsButton.getSelection();
		super.okPressed();
	}

	/**
	 * Returns the namespace fragments to be replaced.
	 *
	 * @return the namespace fragments to be replaced
	 */
	public String getOldNamespaceFragment() {
		return oldNamespaceFragment;
	}

	/**
	 * Returns the new namespace fragments with which to replace the old ones.
	 *
	 * @return the new namespace fragments
	 */
	public String getNewNamespaceFragment() {
		return newNamespaceFragment;
	}

	/**
	 * Whether to show migration warnings.
	 *
	 * @return {@code true}, if migration warnings are to be shown
	 */
	public boolean shouldShowWarning() {
		return showWarnings;
	}
}
