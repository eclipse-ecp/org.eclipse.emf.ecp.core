/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * A wizard page that allows to create a segment based domain model reference by selecting the DMR's target feature in a
 * tree view. The page automatically generates the segment path to the target feature.
 *
 * @author Lucas Koehler
 */
public class SelectFeaturePathWizardPage extends WizardPage {
	private final VDomainModelReference domainModelReference;
	private EClass rootEClass;
	private final ISelection firstSelection;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryLabelProvider labelProvider;
	private final boolean allowMultiReferencesInPath;
	private final SegmentGenerator segmentGenerator;
	private final EStructuralFeatureSelectionValidator selectionValidator;
	private TreeViewer treeViewer;

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
	// BEGIN COMPLEX CODE
	public SelectFeaturePathWizardPage(String pageName, String pageTitle, String pageDescription,
		EClass rootEClass, ISelection firstSelection, SegmentGenerator segmentGenerator,
		EStructuralFeatureSelectionValidator selectionValidator, boolean allowMultiReferencesInPath) {
		// END COMPLEX CODE
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

	@Override
	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.FILL);
		GridLayoutFactory.fillDefaults().applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(composite);
		treeViewer = createTreeViewer(composite);
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
	 * Creates the tree viewer of this wizard page. Overwrite this if you want to use custom style flags for the
	 * {@link TreeViewer}.
	 * <p>
	 * <strong>Note:</strong> This method should only create the viewer but not configure anymore stuff like the label
	 * provider.
	 *
	 * @param composite The {@link Composite} which will contain the tree viewer
	 * @return The created {@link TreeViewer}
	 */
	protected TreeViewer createTreeViewer(Composite composite) {
		return new TreeViewer(composite);
	}

	/**
	 * (Re-)sets the root {@link EClass} of this wizard page. This clears the current selection.
	 *
	 * @param rootEClass The new root {@link EClass}
	 */
	public void setRootEClass(EClass rootEClass) {
		this.rootEClass = rootEClass;
		domainModelReference.getSegments().clear();
		setPageComplete(false);
		treeViewer.setInput(rootEClass);
	}

	/**
	 * @return The {@link ISelectionChangedListener} for this page's {@link TreeViewer}.
	 */
	protected ISelectionChangedListener createSelectionChangedListener() {
		return event -> {
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

	@Override
	public void dispose() {
		composedAdapterFactory.dispose();
		labelProvider.dispose();
		super.dispose();
	}
}
