/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog for selecting the attributes for which controls should be generated.
 *
 * @author Eugen Neufeld
 * @author Alexandra Buzila
 */
public class SelectAttributesDialog extends WizardDialog {

	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryLabelProvider labelProvider;
	private final VView view;
	private final Set<EStructuralFeature> selectedFeatures = new LinkedHashSet<EStructuralFeature>();
	private final EClass rootClass;
	private static SelectAttributesWizard wizard;

	/**
	 * Constructor.
	 *
	 * @param view for identifying the attributes which are not referenced yet
	 * @param rootClass the rootClass of the view
	 * @param parentShell the shell for creating the dialog
	 */
	public SelectAttributesDialog(SelectAttributesWizard wiz, VView view, EClass rootClass, Shell parentShell) {
		super(parentShell, wiz);
		wizard = wiz;
		this.view = view;
		this.rootClass = rootClass;
		wizard.setRootEClass(rootClass);
		wizard.setView(view);

		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	public boolean close() {
		if (labelProvider != null) {
			labelProvider.dispose();
		}
		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
		}
		return super.close();
	}

	/** @return the set of features selected in the dialog, for which controls should be generated. */
	public Set<EStructuralFeature> getSelectedFeatures() {
		return wizard.getSelectedFeatures();
	}

	/** @return the rootEClass the dialog is displaying the attributes for. */
	public EClass getRootClass() {
		return wizard.getRootEClass();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.WizardDialog#backPressed()
	 */
	@Override
	protected void backPressed() {

		wizard.backPressed();
		super.backPressed();
	}

}
