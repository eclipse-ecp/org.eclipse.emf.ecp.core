/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.dialogs;

import java.util.List;

import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Eike Stepper
 */
public class DeleteDialog extends TitleAreaDialog {
	private final List<ECPContainer> deletables;

	/**
	 * Default constructor.
	 * 
	 * @param parentShell The parent {@link Shell}
	 * @param deletables The {@link ECPContainer}s to be deleted
	 */
	public DeleteDialog(Shell parentShell, List<ECPContainer> deletables) {
		super(parentShell);
		this.deletables = deletables;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.DeleteDialog_DialogTitle);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final int count = deletables.size();

		setTitle(Messages.DeleteDialog_Title);
		setTitleImage(Activator.getImage("icons/delete_wiz.png")); //$NON-NLS-1$
		setMessage(Messages.DeleteDialog_Message_AreYouSure + count + Messages.DeleteDialog_Message_element
			+ (count == 1 ? "" : Messages.DeleteDialog_Message_element_plural) + "?");

		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout(1, false));

		// Button btnDontAskAgain = new Button(composite, SWT.CHECK);
		// btnDontAskAgain.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		// btnDontAskAgain.setText("Don't ask again");

		return area;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(381, 182);
	}
}
