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

import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.internal.ui.messages.Messages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Eike Stepper
 */
public class RepositoryPropertiesDialog extends PropertiesDialog {
	private final ECPRepository repository;

	private Text nameText;

	private Text labelText;

	private Text descriptionText;

	private Text providerText;

	public RepositoryPropertiesDialog(Shell parentShell, boolean editable, ECPRepository repository) {
		super(parentShell, repository.getLabel(), repository.getDescription(), editable, repository.getProperties());
		this.repository = repository;
	}

	public final ECPRepository getRepository() {
		return repository;
	}

	public final Text getNameText() {
		return nameText;
	}

	public final Text getLabelText() {
		return labelText;
	}

	public final Text getDescriptionText() {
		return descriptionText;
	}

	public final Text getProviderText() {
		return providerText;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.RepositoryPropertiesDialog_DialogTitle);
	}

	@Override
	protected void createSpecialProperties(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		providerText = addTextProperty(composite, Messages.RepositoryPropertiesDialog_RepositoryProvider, repository
			.getProvider().getLabel());
	}
}
