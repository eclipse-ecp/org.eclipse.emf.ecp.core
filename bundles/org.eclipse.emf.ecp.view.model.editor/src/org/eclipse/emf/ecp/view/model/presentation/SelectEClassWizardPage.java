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
 * Wizard page allowing the selection of an {@link EClass} from a given container.
 *
 */
public class SelectEClassWizardPage extends WizardPage {
	private Composite container;
	private Button generateViewModelChkBox;
	private EPackage selectedEPackage;
	private List<EClass> selectedEClasses;
	private TreeViewer treeViewer;

	/** Creates a new EClass selection wizard page. */
	public SelectEClassWizardPage() {
		super(ViewEditorPlugin.INSTANCE.getString("_UI_SelectEClassWizardPage_page_name")); //$NON-NLS-1$
		setTitle(ViewEditorPlugin.INSTANCE.getString("_UI_SelectEClassWizardPage_page_title")); //$NON-NLS-1$
		setDescription(ViewEditorPlugin.INSTANCE.getString("_UI_SelectEClassWizardPage_page_description")); //$NON-NLS-1$
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
								setPageComplete(getErrorMessage() == null);
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

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (treeViewer != null && selectedEPackage != null) {
			treeViewer.setInput(selectedEPackage);
			treeViewer.expandToLevel(2);
		}
	}

	private ITreeContentProvider getContentProvider(final AdapterFactory adapterFactory) {
		return new CustomTreeContentProvider(adapterFactory);
	}

	/**
	 * Returns whether the option to generate the controls for the created view model is selected.
	 *
	 * @return <code>true</code> if the option is selected
	 */
	public boolean isGenerateViewModelOptionSelected() {
		return generateViewModelChkBox.getSelection();
	}

	/**
	 * Returns the {@link EPackage} container providing the EClasses displayed in the wizard page.
	 *
	 * @return the EPackage
	 */
	public EPackage getSelectedEPackage() {
		return selectedEPackage;
	}

	/**
	 * Sets the EPackage container from which the EClasses will be selected in the wizard page.
	 *
	 * @param selectedEPackage the ePackage to set
	 */
	public void setSelectedEPackage(EPackage selectedEPackage) {
		this.selectedEPackage = selectedEPackage;
		if (selectedEPackage == null || selectedEPackage.getNsURI() == null || selectedEPackage.getNsURI().isEmpty()) {
			setErrorMessage(ViewEditorPlugin.INSTANCE.getString("_UI_SelectEClassWizardPage_invalid_package_URI")); //$NON-NLS-1$
		} else {
			setErrorMessage(null);
		}
	}

	/**
	 * Sets the EClasses selected by this wizard page.
	 *
	 * @param selectedEClasses the selectedEClasses to set
	 */
	public void setSelectedEClasses(List<EClass> selectedEClasses) {
		this.selectedEClasses = selectedEClasses;
	}

	/**
	 * Returns the EClasses selected by the user in the wizard page.
	 *
	 * @return the selected EClasses as a list
	 */
	public List<EClass> getSelectedEClasses() {
		return selectedEClasses;
	}

	@Override
	public boolean isPageComplete() {
		return selectedEClasses != null && !selectedEClasses.isEmpty();
	}

	/** Custom {@link ITreeContentProvider} for the {@link SelectEClassWizardPage}. */
	private class CustomTreeContentProvider implements ITreeContentProvider {

		private final ResourceSetImpl resourceSet = new ResourceSetImpl();
		private final AdapterFactory adapterFactory;

		CustomTreeContentProvider(AdapterFactory adapterFactory) {
			this.adapterFactory = adapterFactory;
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
						children.add(obj);
					}
				}
				return children.toArray();
			}
			if (IFile.class.isInstance(parentElement)) {
				final String path = ((IFile) parentElement).getFullPath().toString();
				final URI uri = URI.createPlatformResourceURI(path, true);
				parentElement = resourceSet.getResource(uri, true);

				final ITreeItemContentProvider treeItemContentProvider = (ITreeItemContentProvider) adapterFactory
					.adapt(parentElement, ITreeItemContentProvider.class);
				return (treeItemContentProvider != null ? treeItemContentProvider.getChildren(parentElement)
					: Collections.EMPTY_LIST).toArray();
			}
			return null;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// do nothing
		}

		@Override
		public void dispose() {
			// do nothing
		}
	}
}
