/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.ui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.emf.ecp.ui.common.ECPCompositeFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

/**
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
@SuppressWarnings("restriction")
public class ECPReferenceServiceImpl implements ReferenceService {

	private ViewModelContext context;
	private ECPProject ecpProject;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		ecpProject = ECPUtil.getECPProjectManager().getProject(context.getDomainModel());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		ecpProject = null;
		context = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @since 1.5
	 * @deprecated
	 */
	@Deprecated
	@Override
	public void addNewModelElements(EObject eObject, EReference eReference) {
		addNewModelElements(eObject, eReference, true);
	}

	@Override
	public Optional<EObject> addNewModelElements(EObject eObject, EReference eReference, boolean openInNewContext) {
		if (eReference == null) {
			return Optional.empty();
		}
		EObject newModelElement = null;
		final Collection<EClass> classes = EMFUtils.getSubClasses(eReference.getEReferenceType());

		if (classes.size() > 1) {
			final SelectionComposite<TreeViewer> helper = ECPCompositeFactory.getSelectModelClassComposite(
				new HashSet<EPackage>(),
				new HashSet<EPackage>(), classes);

			newModelElement = SelectModelElementWizardFactory.openCreateNewModelElementDialog(helper);

		} else {
			newModelElement = eReference.getEReferenceType().getEPackage().getEFactoryInstance()
				.create(classes.iterator().next());

		}

		if (newModelElement == null) {
			return Optional.empty();

		}

		if (eReference.isContainer()) {
			// TODO language
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", //$NON-NLS-1$
				"Operation not permitted for container references!");//$NON-NLS-1$
			return Optional.empty();
		}
		ECPControlHelper.addModelElementInReference(eObject, newModelElement, eReference,
			ecpProject.getEditingDomain());
		if (openInNewContext) {
			openInNewContext(newModelElement);
		}

		return Optional.of(newModelElement);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @since 1.5
	 */
	@Override
	public void addExistingModelElements(EObject eObject, EReference eReference) {
		final Iterator<EObject> allElements = ((InternalProject) ecpProject).getReferenceCandidates(
			context.getDomainModel(),
			eReference);
		final Set<EObject> elements = new LinkedHashSet<EObject>();
		while (allElements.hasNext()) {
			elements.add(allElements.next());
		}

		ECPControlHelper.removeExistingReferences(eObject, eReference, elements);

		final Set<EObject> selectedElements = SelectModelElementWizardFactory
			.openModelElementSelectionDialog(elements, eReference.isMany());

		ECPControlHelper.addModelElementsInReference(eObject, selectedElements, eReference,
			ecpProject.getEditingDomain());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#openInNewContext(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void openInNewContext(EObject eObject) {
		ECPHandlerHelper.openModelElement(eObject, ecpProject);
	}

}
