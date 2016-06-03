/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
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
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The DefaultReferenceService provides a sample implementation for the ReferenceService.
 *
 * @author Eugen Neufeld
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
		final EObject newMEInstance = getNewModelElementInstance(eReference);

		if (newMEInstance == null) {
			return;
		}

		if (eReference.isContainer()) {
			// TODO language
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", //$NON-NLS-1$
				"Operation not permitted for container references!");//$NON-NLS-1$
			return;
		}

		if (!eReference.isContainment()) {
			addElementToModel(newMEInstance, eObject);
		}

		ECPControlHelper.addModelElementInReference(eObject, newMEInstance, eReference,
			editingDomain);
		openInNewContext(newMEInstance);
	}

	private EObject getNewModelElementInstance(EReference eReference) {
		final Collection<EClass> classes = EMFUtils.getSubClasses(eReference.getEReferenceType());
		if (classes.isEmpty()) {
			final String errorMessage = String.format("No concrete classes for the type %1$s were found!", //$NON-NLS-1$
				eReference.getEReferenceType().getName());
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", //$NON-NLS-1$
				errorMessage);
			Activator.getDefault().getReportService().report(new AbstractReport(errorMessage));
			return null;
		}
		if (classes.size() > 1) {
			return getModelElementInstanceFromList(classes);
		}
		return eReference.getEReferenceType().getEPackage().getEFactoryInstance()
			.create(classes.iterator().next());
	}

	private EObject getModelElementInstanceFromList(Collection<EClass> classes) {
		final SelectionComposite<TreeViewer> helper = CompositeFactory.getSelectModelClassComposite(
			new HashSet<EPackage>(),
			new HashSet<EPackage>(), classes);

		return SelectModelElementWizardFactory.openCreateNewModelElementDialog(helper);
	}

	/**
	 * Tries to add {@code newElement} recursively upwards starting from {@code eObject}. If no applicable
	 * {@link EObject} is found, the {@code newElement} will be added to {@code eObject}'s {@link Resource}.
	 *
	 * @param newElement
	 *            The {@link EObject} which is added to the model.
	 * @param eObject
	 *            The starting point from which the {@code newElement} is recursively tried to be added upwards.
	 */
	private void addElementToModel(EObject newElement, EObject eObject) {
		for (final EReference ref : eObject.eClass().getEAllContainments()) {
			if (ref.getEType().isInstance(newElement)) {
				ECPControlHelper.addModelElementInReference(eObject, newElement, ref, editingDomain);
				return;
			}
		}
		if (eObject.eContainer() != null) {
			addElementToModel(newElement, eObject.eContainer());
		} else {
			eObject.eResource().getContents().add(newElement);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#openInNewContext(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void openInNewContext(final EObject eObject) {

		final Dialog dialog = new CustomDialog(Display.getDefault().getActiveShell(), eObject);

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
		final Iterator<EObject> allElements = ItemPropertyDescriptor
			.getReachableObjectsOfType(eObject, eReference.getEType())
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

	/** Custom dialog used for displaying the provided EObject. */
	private class CustomDialog extends Dialog {

		private final EObject eObject;

		/**
		 * @param activeShell
		 * @param eObject
		 */
		CustomDialog(Shell activeShell, EObject eObject) {
			super(activeShell);
			this.eObject = eObject;
		}

		@Override
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setText(eObject.eClass().getName());
		}

		@Override
		protected boolean isResizable() {
			return true;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		protected Control createDialogArea(Composite parent) {
			final Composite composite = (Composite) super.createDialogArea(parent);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).hint(450, 250)
				.applyTo(composite);

			final ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.H_SCROLL
				| SWT.V_SCROLL);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(scrolledComposite);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setExpandHorizontal(true);

			final Composite content = new Composite(scrolledComposite, SWT.NONE);
			GridLayoutFactory.fillDefaults().applyTo(content);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(content);

			final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(
				ViewProviderHelper.getView(eObject, null), eObject, new DefaultReferenceService());
			try {
				ECPSWTViewRenderer.INSTANCE.render(content, vmc);
			} catch (final ECPRendererException ex) {
				Activator.log(ex);
			}

			scrolledComposite.setContent(content);
			final Point point = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			scrolledComposite.setMinSize(point);
			scrolledComposite.layout(true);

			return composite;
		}

	}
}
