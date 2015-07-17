/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * @author Alexandra Buzila
 *
 */
public class SelectAttributesWizard extends Wizard {

	private SelectDataSegmentWizardPage selectDataSegmentWizardPage;
	private SelectAttributesWizardPage selectAttributesWizardPage;
	private EClass rootClass;
	private VView view;
	private Set<EStructuralFeature> selectedFeatures;

	@Override
	public void addPages() {
		selectDataSegmentWizardPage = new SelectDataSegmentWizardPage();
		selectDataSegmentWizardPage.setTitle("Select Data Segment"); //$NON-NLS-1$
		selectDataSegmentWizardPage.setDescription("Select an EClass."); //$NON-NLS-1$
		selectDataSegmentWizardPage.setView(view);
		addPage(selectDataSegmentWizardPage);

		selectAttributesWizardPage = new SelectAttributesWizardPage();
		selectAttributesWizardPage
			.setDescription("Select the attributes for which controls should be generated."); //$NON-NLS-1$
		selectAttributesWizardPage.setTitle("Select Attributes"); //$NON-NLS-1$
		addPage(selectAttributesWizardPage);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (SelectDataSegmentWizardPage.class.isInstance(page)) {
			final SelectDataSegmentWizardPage selectPage = (SelectDataSegmentWizardPage) page;
			if (selectPage != null) {
				if (selectAttributesWizardPage == null) {
					selectAttributesWizardPage = new SelectAttributesWizardPage();
					selectAttributesWizardPage
						.setDescription("Select the attributes for which controls should be generated."); //$NON-NLS-1$
					selectAttributesWizardPage.setTitle("Select Attributes"); //$NON-NLS-1$
					addPage(selectAttributesWizardPage);
				}
				rootClass = selectPage.getSelectedDataSegment();
				selectAttributesWizardPage.setRootClass(rootClass);
				selectAttributesWizardPage.setView(view);
			}
			return selectAttributesWizardPage;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		return selectAttributesWizardPage != null && !selectAttributesWizardPage.getSelectedFeatures().isEmpty();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
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

	/**
	 * @param rootClass the {@link EClass} to select attributes from
	 */
	protected void setRootEClass(EClass rootClass) {
		this.rootClass = rootClass;

	}

	/**
	 * @return rootClass
	 */
	protected EClass getRootEClass() {
		return rootClass;

	}

	/**
	 * @param view
	 */
	protected void setView(VView view) {
		this.view = view;

	}

	/**
	 *
	 */
	public void backPressed() {
		if (selectAttributesWizardPage != null && selectAttributesWizardPage.isCurrentPage()) {
			selectAttributesWizardPage.clearSelection();
		}
	}
}
