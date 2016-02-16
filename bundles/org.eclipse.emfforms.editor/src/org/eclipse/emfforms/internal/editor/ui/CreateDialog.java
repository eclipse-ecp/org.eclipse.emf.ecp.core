/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ui;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emfforms.swt.core.EMFFormsSWTConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class CreateDialog allows initializing newly created EObjects.
 * It also can be used to create an EObject and initialize it directly.
 *
 * @author Clemens Elflein
 */
public class CreateDialog extends Dialog {

	/** The new object. */
	private final EObject newObject;

	/**
	 * Instantiates a new dialog.
	 *
	 * @param parent the parent
	 * @param createdInstance an EObject to initialize
	 */
	public CreateDialog(Shell parent, EObject createdInstance) {
		super(parent);
		newObject = createdInstance;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Create new " + newObject.eClass().getName());
		newShell.setMinimumSize(300, 150);
		newShell.setBackground(new Color(newShell.getDisplay(), 255, 255, 255));
		newShell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		final GridData parentData = new GridData(SWT.FILL, SWT.FILL, true, true);
		parent.setLayout(new GridLayout(1, true));
		parent.setLayoutData(parentData);

		final ScrolledComposite wrapper = new ScrolledComposite(parent, SWT.V_SCROLL);
		wrapper.setExpandHorizontal(true);
		wrapper.setExpandVertical(true);
		final FillLayout wrapperLayout = new FillLayout();
		wrapperLayout.marginHeight = 10;
		wrapperLayout.marginWidth = 10;
		wrapper.setLayout(wrapperLayout);
		wrapper.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite emfFormsParent = new Composite(wrapper, SWT.NONE);
		wrapper.setContent(emfFormsParent);
		emfFormsParent.setLayout(new GridLayout());

		try {
			final VViewModelProperties properties = VViewFactory.eINSTANCE.createViewModelLoadingProperties();
			properties.addInheritableProperty(EMFFormsSWTConstants.USE_ON_MODIFY_DATABINDING_KEY,
				EMFFormsSWTConstants.USE_ON_MODIFY_DATABINDING_VALUE);
			ECPSWTViewRenderer.INSTANCE.render(emfFormsParent, newObject, properties);
		} catch (final ECPRendererException e) {
		}

		wrapper.setMinSize(wrapper.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return parent;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		getParentShell().forceFocus();
		final Diagnostic result = Diagnostician.INSTANCE.validate(newObject);
		if (result.getSeverity() == Diagnostic.OK) {
			super.okPressed();
		} else {
			// Get the error count and create an appropriate Error message:
			final int errorCount = result.getChildren().size();

			final StringBuilder sb = new StringBuilder();

			sb.append(errorCount);
			sb.append(" ");
			sb.append(errorCount == 1 ? "error" : "errors");
			sb.append(" occured while analyzing your inputs. The following errors were found:\r\n");

			int messageCount = 1;
			for (final Diagnostic d : result.getChildren()) {
				sb.append("\r\n");
				sb.append(messageCount++);
				sb.append(". ");
				sb.append(d.getMessage());
			}

			final String errorMessage = sb.toString();

			MessageDialog.open(MessageDialog.ERROR, getParentShell(), "Error", errorMessage, SWT.NONE);
		}
	}

	/**
	 * Gets the created instance or the updated one, if it was passed in the constructor.
	 * All fields are initialized with user inputs
	 *
	 * @return the created instance
	 */
	public EObject getCreatedInstance() {
		return newObject;
	}
}
