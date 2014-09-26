/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.EMFUtils;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * @author Eugen
 * @noreference This class is not intended to be referenced by clients.
 * @noextend This class is not intended to be subclassed by clients.
 * @since 1.4
 * 
 */
@SuppressWarnings("restriction")
public class DefaultReferenceService implements ReferenceService {

	private EditingDomain editingDomain;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(context.getDomainModel());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.5
	 */

	@Override
	public void addNewModelElements(EObject eObject, EReference eReference) {
		final Collection<EClass> classes = EMFUtils.getSubClasses(eReference.getEReferenceType());

		final SelectionComposite<TreeViewer> helper = CompositeFactory.getSelectModelClassComposite(
			new HashSet<EPackage>(),
			new HashSet<EPackage>(), classes);

		final EObject newMEInstance = SelectModelElementWizardFactory.openCreateNewModelElementDialog(helper);

		if (newMEInstance == null) {
			return;
		}

		if (eReference.isContainer()) {
			// TODO language
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",//$NON-NLS-1$
				"Operation not permitted for container references!");//$NON-NLS-1$
			return;
		}

		ECPControlHelper.addModelElementInReference(eObject, newMEInstance, eReference,
			editingDomain);
		openInNewContext(newMEInstance);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#openInNewContext(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void openInNewContext(final EObject eObject) {
		final Dialog dialog = new Dialog(Display.getDefault().getActiveShell()) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected Control createDialogArea(Composite parent) {
				final Composite composite = (Composite) super.createDialogArea(parent);
				final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(
					ViewProviderHelper.getView(eObject, null), eObject, new DefaultReferenceService());
				try {
					ECPSWTViewRenderer.INSTANCE.render(composite, vmc);
				} catch (final ECPRendererException ex) {
					Activator.log(ex);
				}

				return composite;
			}

		};
		new ECPDialogExecutor(dialog) {
			@Override
			public void handleResult(int codeResult) {

			}
		}.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#addExistingModelElements(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EReference)
	 * @since 1.5
	 */
	@Override
	public void addExistingModelElements(EObject eObject, EReference eReference) {
		final Iterator<EObject> allElements =
			ItemPropertyDescriptor.getReachableObjectsOfType(eObject, eReference.getEType())
				.iterator();

		final Set<EObject> elements = new LinkedHashSet<EObject>();
		while (allElements.hasNext()) {
			elements.add(allElements.next());
		}

		ECPControlHelper.removeExistingReferences(eObject, eReference, elements);

		final Set<EObject> addedElements = SelectModelElementWizardFactory
			.openModelElementSelectionDialog(elements, eReference.isMany());

		ECPControlHelper.addModelElementsInReference(eObject, addedElements, eReference,
			editingDomain);

	}
}
