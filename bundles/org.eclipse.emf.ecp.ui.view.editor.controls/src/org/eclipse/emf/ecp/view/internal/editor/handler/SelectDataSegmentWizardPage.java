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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Alexandra Buzila
 *
 */
public class SelectDataSegmentWizardPage extends WizardPage {

	/**
	 * @author Jonas
	 *
	 */
	private final class DataSegmentTreeContentProvider implements ITreeContentProvider {
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

			if (VView.class.isInstance(element)) {
				return true;
			}
			if (EPackage.class.isInstance(element)) {
				return true;
			}
			if (EClass.class.isInstance(element)) {
				final EClass eClass = (EClass) element;
				final boolean hasReferences = !eClass.getEAllReferences().isEmpty();
				return hasReferences;

			}
			if (EReference.class.isInstance(element)) {
				final EReference eReference = (EReference) element;
				return eReference.isContainment() && !eReference.isMany()
					&& hasChildren(eReference.getEReferenceType());
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

			if (VView.class.isInstance(parentElement)) {
				final Object[] children = new Object[] { view.getRootEClass() };
				return children;
			}
			if (EPackage.class.isInstance(parentElement)) {
				final Set<Object> children = new LinkedHashSet<Object>();
				children.addAll(EPackage.class.cast(parentElement).getESubpackages());
				for (final EObject obj : EPackage.class.cast(parentElement).getEClassifiers()) {
					if (EClass.class.isInstance(obj)) {
						children.add(obj);
					}
				}
				return children.toArray();
			}
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
			}
			else {
				for (final EReference ref : eClass.getEAllReferences()) {
					if (!ref.isMany() && ref.isContainment()) {
						result.add(ref);
					}
				}
			}
			return result;
		}
	}

	private TreeViewer treeViewer;

	private EClass selectedDataSegment;

	/**
	 * @return the selectedDataSegment
	 */
	public EClass getSelectedDataSegment() {
		return selectedDataSegment;
	}

	private VView view;

	/**
	 * Default constructor.
	 */
	protected SelectDataSegmentWizardPage() {
		super("Select Data Segment");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {

		if (view != null) {

			final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new CustomReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
			final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(adapterFactory);

			treeViewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
			treeViewer.setContentProvider(getContentProvider(adapterFactory));
			treeViewer.setLabelProvider(labelProvider);
			treeViewer.setInput(view);
			treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					selectedDataSegment = null;
					if (((IStructuredSelection) event.getSelection()).getFirstElement() instanceof EClass) {
						selectedDataSegment = (EClass) ((IStructuredSelection) event.getSelection()).getFirstElement();
					} else {
						if (((IStructuredSelection) event.getSelection()).getFirstElement() instanceof EReference) {
							final EReference ref = (EReference) ((IStructuredSelection) event.getSelection())
								.getFirstElement();
							selectedDataSegment = ref.getEReferenceType();
						}
					}
					setPageComplete(selectedDataSegment != null);
				}
			});
			treeViewer.expandToLevel(2);
		}

		setControl(treeViewer.getControl());
		setPageComplete(false);
	}

	private ITreeContentProvider getContentProvider(final AdapterFactory adapterFactory) {
		return new DataSegmentTreeContentProvider();
	}

	/**
	 * @return the view
	 */
	public VView getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(VView view) {
		this.view = view;
	}
}
