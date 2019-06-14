/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author Alexandra Buzila
 *
 */
public class SelectAttributesWizard extends Wizard {

	private SelectAttributesWizardPage selectAttributesWizardPage;
	private final EClass rootClass;
	private final VView view;
	private Set<EStructuralFeature> selectedFeatures;

	/**
	 * Constructor.
	 * 
	 * @param view The {@link VView} the Controls are generated for.
	 */
	public SelectAttributesWizard(VView view) {
		this.view = view;
		rootClass = view.getRootEClass();
	}

	@Override
	public void addPages() {
		selectAttributesWizardPage = new SelectAttributesWizardPage();
		selectAttributesWizardPage
			.setDescription("Select the attributes for which controls should be generated."); //$NON-NLS-1$
		selectAttributesWizardPage.setTitle("Select Attributes"); //$NON-NLS-1$

		selectAttributesWizardPage.setRootClass(rootClass);
		selectAttributesWizardPage.setView(view);
		addPage(selectAttributesWizardPage);

	}

	@Override
	public boolean canFinish() {
		return !selectAttributesWizardPage.getSelectedFeatures().isEmpty();
	}

	@Override
	public boolean performFinish() {
		selectedFeatures = selectAttributesWizardPage.getSelectedFeatures();
		return true;
	}

	/**
	 *
	 * @return a {@link Set} of selected {@link EStructuralFeature}
	 */
	public Set<EStructuralFeature> getSelectedFeatures() {
		return selectedFeatures;
	}
}
