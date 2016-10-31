/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.util.SegmentResolvementUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard to create a new {@link VDomainModelReference} and select its {@link VDomainModelReferenceSegment
 * VDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
public class SimpleCreateDomainModelReferenceWizard extends Wizard {
	private SelectFeaturePathWizardPage selectFeaturePathPage;
	private final EditingDomain editingDomain;
	private final EStructuralFeature structuralFeature;
	private final EObject eObject;
	private final EClass rootEClass;
	private final VDomainModelReference existingDMR;

	/**
	 * A wizard used for creating and configuring a DomainModelReference.
	 *
	 * @param eObject The {@link EObject} containing a domain model reference
	 * @param structuralFeature The corresponding {@link EStructuralFeature}
	 * @param editingDomain The setting's editing domain
	 * @param rootEClass The root {@link EClass} of the VView the eObject belongs to
	 * @param windowTitle The title for the wizard window
	 * @param existingDMR The domain model reference to configure. May be null, then a new DMR is created
	 */
	public SimpleCreateDomainModelReferenceWizard(final EObject eObject, final EStructuralFeature structuralFeature,
		final EditingDomain editingDomain, final EClass rootEClass, final String windowTitle,
		VDomainModelReference existingDMR) {
		setWindowTitle(windowTitle);
		this.eObject = eObject;
		this.structuralFeature = structuralFeature;
		this.editingDomain = editingDomain;
		this.rootEClass = rootEClass;
		this.existingDMR = existingDMR;
	}

	/**
	 * @return The initial selection for the {@link SelectFeaturePathWizardPage SelectFeaturePathWizardPage's} tree
	 *         viewer. Returns an empty selection if there is no existing dmr or it does not contain any segments.
	 */
	private ISelection getInitialSelection() {
		if (existingDMR == null || existingDMR.getSegments().isEmpty()) {
			return TreeSelection.EMPTY;
		}

		final List<EStructuralFeature> pathList = SegmentResolvementUtil
			.resolveSegmentsToFeatureList(existingDMR.getSegments(), rootEClass);
		if (pathList.size() == existingDMR.getSegments().size()) {
			return new TreeSelection(new TreePath(pathList.toArray()));
		}
		return TreeSelection.EMPTY;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		if (!canFinish()) {
			return false;
		}
		Command command = null;
		if (structuralFeature.isMany()) {
			command = AddCommand.create(editingDomain, eObject,
				structuralFeature, selectFeaturePathPage.getDomainModelReference());
		} else {
			command = SetCommand.create(editingDomain, eObject,
				structuralFeature, selectFeaturePathPage.getDomainModelReference());
		}
		editingDomain.getCommandStack().execute(command);
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		return super.canFinish();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		selectFeaturePathPage = new SelectFeaturePathWizardPage("New Domain Model Reference", //$NON-NLS-1$
			"Select an EStructuralFeature", "Select a domain model EStructuralFeature for the domain model reference.", //$NON-NLS-1$//$NON-NLS-2$
			rootEClass, getInitialSelection());
		addPage(selectFeaturePathPage);
	}

	/**
	 * @return Whether the selection of multi references is allowed in the {@link SelectFeaturePathWizardPage}
	 */
	protected boolean isAllowMultiReferences() {
		// TODO configurable?
		return false;
	}

	private class SelectFeaturePathWizardPage extends WizardPage {
		private final VDomainModelReference domainModelReference;
		private final EClass rootEClass;
		private final ISelection firstSelection;
		private ComposedAdapterFactory composedAdapterFactory;
		private AdapterFactoryLabelProvider labelProvider;

		protected SelectFeaturePathWizardPage(String pageName, String pageTitle, String pageDescription,
			EClass rootEClass, ISelection firstSelection) {
			super(pageName);
			setTitle(pageTitle);
			setDescription(pageDescription);
			this.rootEClass = rootEClass;
			this.firstSelection = firstSelection;

			domainModelReference = VViewFactory.eINSTANCE.createDomainModelReference();
		}

		/**
		 * @return The {@link VDomainModelReference} which is configured in this page
		 */
		public VDomainModelReference getDomainModelReference() {
			return domainModelReference;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public void createControl(Composite parent) {
			final Composite composite = new Composite(parent, SWT.FILL);
			GridLayoutFactory.fillDefaults().applyTo(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(composite);
			final TreeViewer treeViewer = new TreeViewer(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(treeViewer.getControl());

			composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new ReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
			labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
			final EStructuralFeatureContentProvider contentProvider = new EStructuralFeatureContentProvider(
				isAllowMultiReferences());

			treeViewer.setContentProvider(contentProvider);
			treeViewer.setLabelProvider(labelProvider);
			treeViewer.setInput(rootEClass);
			treeViewer.addSelectionChangedListener(createSelectionChangedListener());
			treeViewer.setSelection(firstSelection, true);

			setControl(composite);
		}

		/**
		 * @return The {@link ISelectionChangedListener} for this page's {@link TreeViewer}.
		 */
		protected ISelectionChangedListener createSelectionChangedListener() {
			return new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					// Page is not complete until the opposite is proven
					setPageComplete(false);

					// Get the selected element and create an EReference path from its tree path
					final ITreeSelection treeSelection = (ITreeSelection) event.getSelection();
					if (treeSelection.getPaths().length < 1) {
						return;
					}
					final TreePath treePath = treeSelection.getPaths()[0];
					if (treePath.getSegmentCount() < 1) {
						return;
					}
					final List<EStructuralFeature> bottomUpPath = new LinkedList<EStructuralFeature>();

					for (int i = 0; i < treePath.getSegmentCount(); i++) {
						final Object o = treePath.getSegment(i);
						bottomUpPath.add((EStructuralFeature) o);
					}
					configureSegments(bottomUpPath);

					// TODO validation?
					if (!domainModelReference.getSegments().isEmpty()) {
						setPageComplete(true);
					}
				}
			};
		}

		/**
		 * Generates segments from the given path and set them in this page's domain model reference.
		 *
		 * @param bottomUpPath Path to the selected feature (including it). The selected feature is the last element in
		 *            the list.
		 */
		private void configureSegments(List<EStructuralFeature> bottomUpPath) {
			if (!domainModelReference.getSegments().isEmpty()) {
				domainModelReference.getSegments().clear();
			}
			for (final EStructuralFeature eStructuralFeature : bottomUpPath) {
				domainModelReference.getSegments().add(createDMRSegment(eStructuralFeature));
			}
		}

		/**
		 * Creates a {@link VDomainModelReferenceSegment} for the given {@link EStructuralFeature}.
		 *
		 * @param structuralFeature The {@link EStructuralFeature} that defines the path part represented by the created
		 *            segment
		 * @return The created {@link VDomainModelReference}
		 */
		private VDomainModelReferenceSegment createDMRSegment(final EStructuralFeature structuralFeature) {
			final VFeatureDomainModelReferenceSegment pathSegment = VViewFactory.eINSTANCE
				.createFeatureDomainModelReferenceSegment();
			pathSegment.setDomainModelFeature(structuralFeature.getName());
			return pathSegment;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
		 */
		@Override
		public void dispose() {
			composedAdapterFactory.dispose();
			labelProvider.dispose();
			super.dispose();
		}
	}

	/**
	 * An {@link ITreeContentProvider} that allows to select {@link EStructuralFeature EStructuralFeatures}. These are
	 * read from an {@link EClass}, an{@link EPackage}, or an already expanded {@link EReference}.
	 *
	 * @author Lucas Koehler
	 *
	 */
	private class EStructuralFeatureContentProvider implements ITreeContentProvider {
		private final boolean allowMultiReferences;

		/**
		 *
		 * @param allowMultiReferences Whether multi references can be selected
		 */
		protected EStructuralFeatureContentProvider(boolean allowMultiReferences) {
			this.allowMultiReferences = allowMultiReferences;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean hasChildren(Object element) {

			if (EPackage.class.isInstance(element)) {
				return true;
			}
			if (EClass.class.isInstance(element)) {
				final EClass eClass = (EClass) element;
				final boolean hasReferences = !eClass.getEAllReferences().isEmpty();
				final boolean hasAttributes = !eClass.getEAllAttributes().isEmpty();
				return hasReferences || hasAttributes;

			}
			if (EReference.class.isInstance(element)) {
				final EReference eReference = (EReference) element;

				return eReference.isMany() && !allowMultiReferences ? false : hasChildren(eReference
					.getEReferenceType());
			}
			return false;
		}

		@Override
		public Object getParent(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (EClass.class.isInstance(parentElement)) {
				final EClass eClass = (EClass) parentElement;
				final Set<Object> result = getElementsForEClass(eClass);
				return result.toArray();
			}
			if (EReference.class.isInstance(parentElement)) {
				final EReference eReference = (EReference) parentElement;
				final Set<Object> result = getElementsForEClass(eReference.getEReferenceType());
				return result.toArray();
			}
			if (EPackage.Registry.class.isInstance(parentElement)) {
				return EPackage.Registry.class.cast(parentElement).values().toArray();
			}
			if (EPackage.class.isInstance(parentElement)) {
				final Set<Object> children = new LinkedHashSet<Object>();
				children.addAll(EPackage.class.cast(parentElement).getESubpackages());
				children.addAll(EPackage.class.cast(parentElement).getEClassifiers());
				return children.toArray();
			}
			return null;
		}

		private Set<Object> getElementsForEClass(EClass eClass) {
			final Set<Object> result = new LinkedHashSet<Object>();
			if (eClass.isAbstract() || eClass.isInterface()) {
				// find eClasses which are not abstract
				for (final EClassifier eClassifier : eClass.getEPackage().getEClassifiers()) {
					if (eClass != eClassifier && EClass.class.isInstance(eClassifier)
						&& eClass.isSuperTypeOf((EClass) eClassifier)) {
						result.add(eClassifier);
					}
				}
			} else {
				result.addAll(eClass.getEAllReferences());
				result.addAll(eClass.getEAllAttributes());
			}
			return result;
		}
	}
}
