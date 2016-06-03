/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * A wizard page for selecting a structural feature.
 *
 * @author Eugen Neufeld
 *
 */
public class SelectEStructuralFeatureWizardPage extends WizardPage {
	/**
	 * Content Provider for {@link EStructuralFeature}.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private static final class EStructuralFeatureContentProvider implements ITreeContentProvider {
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			viewer.refresh();
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
				return true;
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
			if (EPackage.class.isInstance(parentElement)) {
				final Set<Object> children = new LinkedHashSet<Object>();
				children.addAll(EPackage.class.cast(parentElement).getESubpackages());
				for (final EObject obj : EPackage.class.cast(parentElement).getEClassifiers()) {
					if (EClass.class.isInstance(obj)) {
						final EClass eClass = EClass.class.cast(obj);
						if (!eClass.isAbstract() && !eClass.isInterface()) {
							children.add(obj);
						}
					}
				}
				return children.toArray();
			}
			if (EClass.class.isInstance(parentElement)) {
				return EClass.class.cast(parentElement).getEAllStructuralFeatures().toArray();
			}
			return null;
		}
	}

	private Composite container;
	private EPackage selectedEPackage;
	private EStructuralFeature selectedEStructuralFeature;

	/**
	 * @param selectedEPackage the selectedEPackage to set
	 */
	public void setSelectedEPackage(EPackage selectedEPackage) {
		this.selectedEPackage = selectedEPackage;
		treeViewer.setInput(selectedEPackage);
	}

	private TreeViewer treeViewer;

	/**
	 * Constructor.
	 */
	public SelectEStructuralFeatureWizardPage() {
		super("Select EClass"); //$NON-NLS-1$
		setTitle("Select EClass"); //$NON-NLS-1$
		setDescription("Select an EClass for the current View Model."); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (treeViewer != null && selectedEPackage != null) {
			treeViewer.setInput(selectedEPackage);
			treeViewer.expandToLevel(2);
		}
	}

	@Override
	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 5;
		container.setLayout(layout);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(container);
		layout.numColumns = 1;

		final Label label1 = new Label(container, SWT.NONE);
		label1.setText("Select an EClass:"); //$NON-NLS-1$

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(adapterFactory);

		treeViewer = new TreeViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(getContentProvider(adapterFactory));
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.setInput(selectedEPackage);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					final IStructuredSelection selection = (IStructuredSelection) event.getSelection();

					final Object selectedItem = selection.getFirstElement();
					if (EStructuralFeature.class.isInstance(selectedItem)) {
						selectedEStructuralFeature = (EStructuralFeature) selectedItem;
						setPageComplete(true);
					} else {
						selectedEStructuralFeature = null;
						setPageComplete(false);
					}
				}
			}
		});
		treeViewer.expandToLevel(2);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).hint(SWT.DEFAULT, 200)
			.applyTo(treeViewer.getControl());
		container.layout(true);
		container.pack();

		setControl(container);
		setPageComplete(false);
	}

	private ITreeContentProvider getContentProvider(final AdapterFactory adapterFactory) {
		return new EStructuralFeatureContentProvider();
	}

	/**
	 * Returns the selected {@link EStructuralFeature}.
	 *
	 * @return the selected {@link EStructuralFeature}
	 */
	public EStructuralFeature getEStructuralFeature() {
		return selectedEStructuralFeature;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return selectedEStructuralFeature != null;
	}
}
