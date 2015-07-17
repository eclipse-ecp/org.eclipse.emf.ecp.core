/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.internal.tooling.util;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.model.presentation.SelectEcorePage;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * Wizard for creating a {@link org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
 * VFeaturePathDomainModelReference} for a DomainModelReferenceSelector.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class DMRCreationWizard extends Wizard implements INewWizard {

	private static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.template.tooling"; //$NON-NLS-1$
	private Object selectedContainer;

	/**
	 * This caches an instance of the model package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VViewPackage viewPackage = VViewPackage.eINSTANCE;

	/**
	 * This caches an instance of the model factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VViewFactory viewFactory = viewPackage.getViewFactory();

	/**
	 * Remember the selection during initialization for populating the default
	 * container. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IStructuredSelection selection;

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * Remember the workbench during initialization. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IWorkbench workbench;

	/**
	 * Caches the names of the types that can be created as the root object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected List<String> initialObjectNames;

	private SelectEcorePage selectEcorePage;
	private SelectEStructuralFeatureWizardPage selectEClassPage;
	private EStructuralFeature selectedEStructuralFeature;

	/**
	 * This just records the information. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(Messages.DMRCreationWizard_Title);
		// setDefaultPageImageDescriptor();
	}

	/**
	 * The framework calls this to create the contents of the wizard. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void addPages() {

		selectEcorePage = new SelectEcorePage(PLUGIN_ID);
		addPage(selectEcorePage);
		selectEClassPage = new SelectEStructuralFeatureWizardPage();
		addPage(selectEClassPage);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#getStartingPage()
	 */
	@Override
	public IWizardPage getStartingPage() {
		if (selectedContainer == null)
		{
			return selectEcorePage;
		}

		return selectEClassPage;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	@Override
	public IWizardPage getNextPage(IWizardPage page) {

		if (page == selectEcorePage) {
			selectedContainer = selectEcorePage.getSelectedContainer();
			if (selectedContainer != null) {
				selectEClassPage.setSelectedEPackage(getEPackage());
				return selectEClassPage;
			}
			return null;
		} else if (page == selectEClassPage) {
			selectedEStructuralFeature = selectEClassPage.getEStructuralFeature();

		}

		return null;
	}

	/**
	 * @return
	 */
	private EPackage getEPackage() {
		EPackage ePackage = null;
		if (EPackage.class.isInstance(selectedContainer)) {
			ePackage = EPackage.class.cast(selectedContainer);
		}
		else if (IFile.class.isInstance(selectedContainer)) {
			final ResourceSetImpl resourceSet = new ResourceSetImpl();
			final String path = ((IFile) selectedContainer).getFullPath().toString();
			final URI uri = URI.createPlatformResourceURI(path, true);

			final Resource resource = resourceSet.getResource(uri, true);
			if (resource != null) {

				final EList<EObject> contents = resource.getContents();
				if (contents.size() != 1) {
					return null;
				}

				final EObject object = contents.get(0);
				if (!(object instanceof EPackage)) {
					return null;
				}

				ePackage = (EPackage) object;
			}
		}
		return ePackage;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#getPreviousPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {

		if (page == selectEClassPage) {
			return selectEcorePage;
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
		if (selectEClassPage != null) {
			return selectedEStructuralFeature != null;

		}
		return false;
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	public IFile getSelectedEcore() {
		if (IFile.class.isInstance(selectedContainer)) {
			return (IFile) selectedContainer;
		}
		return null;
	}

	public EStructuralFeature getSelectedEStructuralFeature() {
		return selectedEStructuralFeature;
	}

}
