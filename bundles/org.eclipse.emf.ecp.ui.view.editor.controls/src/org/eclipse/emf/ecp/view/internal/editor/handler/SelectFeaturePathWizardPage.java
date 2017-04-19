/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Lucas Koehler
 *
 */
public class SelectFeaturePathWizardPage extends WizardPage {
	private final VDomainModelReference domainModelReference;
	private final EClass rootEClass;
	private final ISelection firstSelection;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryLabelProvider labelProvider;
	private final boolean allowMultiReferencesInPath;
	private final SegmentGenerator segmentGenerator;
	private final EStructuralFeatureSelectionValidator selectionValidator;

	/**
	 *
	 * @param pageName
	 * @param pageTitle
	 * @param pageDescription
	 * @param rootEClass
	 * @param firstSelection
	 * @param segmentGenerator
	 * @param selectionValidator
	 * @param allowMultiReferencesInPath <code>true</code>: Multi references are allowed in the middle of a
	 *            reference path; <code>false</code>: they are only allowed as the last path segment
	 */
	public SelectFeaturePathWizardPage(String pageName, String pageTitle, String pageDescription,
		EClass rootEClass, ISelection firstSelection, SegmentGenerator segmentGenerator,
		EStructuralFeatureSelectionValidator selectionValidator, boolean allowMultiReferencesInPath) {
		super(pageName);
		setTitle(pageTitle);
		setDescription(pageDescription);
		this.rootEClass = rootEClass;
		this.firstSelection = firstSelection;
		domainModelReference = VViewFactory.eINSTANCE.createDomainModelReference();
		this.allowMultiReferencesInPath = allowMultiReferencesInPath;
		this.segmentGenerator = segmentGenerator;
		this.selectionValidator = selectionValidator;
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
			allowMultiReferencesInPath);

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

				// Validate that a valid structural feature was selected
				final EStructuralFeature structuralFeature = (EStructuralFeature) treeSelection.getFirstElement();
				final String errorMessage = selectionValidator.isValid(structuralFeature);
				setErrorMessage(errorMessage);
				if (errorMessage != null) {
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
	protected void configureSegments(List<EStructuralFeature> bottomUpPath) {
		if (!domainModelReference.getSegments().isEmpty()) {
			domainModelReference.getSegments().clear();
		}
		final List<VDomainModelReferenceSegment> segments = segmentGenerator.generateSegments(bottomUpPath);
		domainModelReference.getSegments().addAll(segments);
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
