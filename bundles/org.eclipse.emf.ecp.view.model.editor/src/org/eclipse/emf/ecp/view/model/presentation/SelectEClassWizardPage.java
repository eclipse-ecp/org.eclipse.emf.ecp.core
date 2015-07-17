/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author Alexandra
 *
 */
public class SelectEClassWizardPage extends WizardPage {
	private Composite container;
	private Button generateViewModelChkBox;
	private EPackage selectedEPackage;
	private List<EClass> selectedEClasses;

	/**
	 * @return the selectedEcore
	 */
	public EPackage getSelectedEPackage() {
		return selectedEPackage;
	}

	/**
	 * @param selectedEPackage the selectedEPackage to set
	 */
	public void setSelectedEPackage(EPackage selectedEPackage) {
		this.selectedEPackage = selectedEPackage;
	}

	private TreeViewer treeViewer;

	/**
	 * @param selectedEClasses the selectedEClasses to set
	 */
	public void setSelectedEClasses(List<EClass> selectedEClasses) {
		this.selectedEClasses = selectedEClasses;
	}

	public SelectEClassWizardPage() {
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

		if (selectedEPackage != null) {

			final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new CustomReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
			final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(adapterFactory);

			selectedEClasses = new ArrayList<EClass>();

			treeViewer = new TreeViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			treeViewer.setContentProvider(getContentProvider(adapterFactory));
			treeViewer.setLabelProvider(labelProvider);
			treeViewer.setInput(selectedEPackage);
			treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					selectedEClasses = new ArrayList<EClass>();
					if (event.getSelection() instanceof IStructuredSelection) {
						final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
						for (final Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
							final Object selectedItem = iterator.next();
							if (EClass.class.isInstance(selectedItem)) {
								selectedEClasses.add((EClass) selectedItem);
								setPageComplete(true);
							}
						}
					}
					if (selectedEClasses.isEmpty()) {
						setPageComplete(false);
					}
				}
			});
			treeViewer.expandToLevel(2);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).hint(SWT.DEFAULT, 200)
				.applyTo(treeViewer.getControl());
			container.layout(true);
			container.pack();

		}

		generateViewModelChkBox = new Button(container, SWT.CHECK);
		generateViewModelChkBox.setText("Fill view model with default layout"); //$NON-NLS-1$
		generateViewModelChkBox.setSelection(true);

		setControl(container);
		setPageComplete(false);
	}

	private ITreeContentProvider getContentProvider(final AdapterFactory adapterFactory) {
		return new ITreeContentProvider() {

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

			private final ResourceSetImpl resourceSet = new ResourceSetImpl();

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
				if (IFile.class.isInstance(parentElement))
				{
					final String path = ((IFile) parentElement).getFullPath().toString();
					final URI uri = URI.createPlatformResourceURI(path, true);
					parentElement = resourceSet.getResource(uri, true);

					final ITreeItemContentProvider treeItemContentProvider =
						(ITreeItemContentProvider) adapterFactory.adapt(parentElement, ITreeItemContentProvider.class);
					return (treeItemContentProvider != null ?
						treeItemContentProvider.getChildren(parentElement) :
						Collections.EMPTY_LIST).toArray();
				}
				return null;
			}
		};
	}

	public boolean isGenerateViewModelOptionSelected() {
		return generateViewModelChkBox.getSelection();
	}

	public List<EClass> getSelectedEClasses() {
		return selectedEClasses;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return selectedEClasses != null && !selectedEClasses.isEmpty();
	}
}
