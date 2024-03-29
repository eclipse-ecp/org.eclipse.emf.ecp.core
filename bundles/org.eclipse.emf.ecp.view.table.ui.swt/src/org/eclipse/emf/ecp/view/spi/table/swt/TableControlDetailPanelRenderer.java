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
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailViewCache;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailViewManager;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * Render for a {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl VTableControl} with a detail editing
 * panel.
 *
 * @author jfaltermeier
 *
 */
public class TableControlDetailPanelRenderer extends TableControlSWTRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param emfFormsDatabinding The {@link EMFFormsDatabindingEMF}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param imageRegistryService The {@link ImageRegistryService}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 * @since 1.8
	 */
	// BEGIN COMPLEX CODE
	@Inject
	public TableControlDetailPanelRenderer(
		VTableControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabindingEMF emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider,
		ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport) {
		// END COMPLEX CODE
		super(
			vElement,
			viewContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			vtViewTemplateProvider,
			imageRegistryService,
			emfFormsEditSupport);
	}

	private Composite detailPanel;
	private Composite border;
	private ScrolledComposite scrolledComposite;
	private DetailViewManager detailManager;

	@Override
	protected void dispose() {
		if (detailManager != null) {
			detailManager.dispose();
		}

		super.dispose();
	}

	@Override
	protected Composite createControlComposite(Composite composite) {
		/* border */
		border = new Composite(composite, SWT.BORDER);
		final GridLayout gridLayout = GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).create();
		border.setLayout(gridLayout);
		final int totalHeight = getTableHeightHint() + getDetailPanelHeightHint() + gridLayout.verticalSpacing;
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(1, totalHeight).applyTo(border);

		/* table composite */
		final Composite tableComposite = new Composite(border, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).hint(1, getTableHeightHint())
			.applyTo(tableComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(border);

		/* scrolled composite */
		scrolledComposite = new ScrolledComposite(border, SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledComposite.setBackground(composite.getBackground());
		scrolledComposite.setLayout(GridLayoutFactory.fillDefaults().create());
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(scrolledComposite);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);

		/* detail panel */
		detailPanel = createDetailPanel(scrolledComposite);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(detailPanel);
		scrolledComposite.setContent(detailPanel);

		createDetailManager(detailPanel);
		detailManager.cacheCurrentDetail();

		scrolledComposite.addListener(SWT.Resize,
			(Event event) -> scrolledComposite.setMinSize(detailPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT)));

		return tableComposite;
	}

	/**
	 * Create the detail manager in the given {@code parent}.
	 *
	 * @param parent the parent composite in which to present details
	 * @since 1.27
	 */
	protected void createDetailManager(Composite parent) {
		detailManager = new DetailViewManager(parent, __ -> getVElement().getDetailView());
		detailManager.setCache(DetailViewCache.createCache(getViewModelContext()));
		detailManager.layoutDetailParent(parent);
	}

	/**
	 * Returns the prefereed height for the detail panel. This will be passed to the layoutdata.
	 *
	 * @return the height in px
	 */
	protected int getDetailPanelHeightHint() {
		return 400;
	}

	/**
	 * Creates the detail panel.
	 *
	 * @param composite the parent
	 * @return the detail panel
	 */
	protected Composite createDetailPanel(ScrolledComposite composite) {
		final Composite detail = new Composite(scrolledComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(detail);
		return detail;
	}

	/**
	 * Returns a fresh copy of the {@link VView} used for detail editing.
	 *
	 * @return the view
	 */
	@Deprecated
	protected VView getView() {
		IValueProperty<?, ?> valueProperty;
		try {
			valueProperty = getEMFFormsDatabinding()
				.getValueProperty(getVElement().getDomainModelReference(),
					getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new DatabindingFailedReport(ex));
			return null; // possible because the only caller is null safe.
		}
		final EReference reference = (EReference) valueProperty.getValueType();
		return getView(EcoreUtil.create(reference.getEReferenceType()));
	}

	/**
	 * Returns a fresh copy of the {@link VView} used for detail editing based on the provided EObject.
	 *
	 * @param selectedEObject The selected EObject for which to provide the View
	 * @return the view
	 */
	protected VView getView(EObject selectedEObject) {
		return detailManager.getDetailView(selectedEObject);
	}

	@Override
	protected void applyEnable() {
		super.applyEnable();
		if (detailManager != null) {
			detailManager
				.setDetailReadOnly(!getVElement().isEffectivelyEnabled() || getVElement().isEffectivelyReadonly());
		}
	}

	@Override
	protected void applyReadOnly() {
		super.applyReadOnly();
		if (detailManager != null) {
			detailManager
				.setDetailReadOnly(!getVElement().isEffectivelyEnabled() || getVElement().isEffectivelyReadonly());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer#viewerSelectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	protected void viewerSelectionChanged(SelectionChangedEvent event) {
		if (event.getSelection().isEmpty()) {
			handleEmptySelection();
		} else if (IStructuredSelection.class.cast(event.getSelection()).size() != 1) {
			handleMultiSelection((IStructuredSelection) event.getSelection());
		} else {
			handleSingleSelection((IStructuredSelection) event.getSelection());
		}
		super.viewerSelectionChanged(event);
	}

	/**
	 * Handle a single selection.
	 *
	 * @param selection the selection
	 */
	protected void handleSingleSelection(IStructuredSelection selection) {
		disposeDetail();
		final EObject object = (EObject) selection.getFirstElement();
		renderSelectedObject((Composite) detailManager.getDetailContainer(), object);
		border.layout(true, true);
		scrolledComposite.setMinSize(detailPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * Called in order to render the selectedObject onto the created detail pane.
	 *
	 * @param composite The {@link Composite} to render on
	 * @param eObject The selected {@link EObject} to render
	 * @since 1.9
	 */
	protected void renderSelectedObject(final Composite composite, final EObject eObject) {
		if (detailManager == null) {
			// For testability only
			createDetailManager(composite);
		}
		detailManager.render(getViewModelContext(), getVElement(), eObject);
	}

	/**
	 * Handle multi selection.
	 *
	 * @param selection the selection
	 */
	protected void handleMultiSelection(IStructuredSelection selection) {
		disposeDetail();
	}

	/**
	 * Handle empty selection.
	 */
	protected void handleEmptySelection() {
		disposeDetail();
	}

	private void disposeDetail() {
		detailManager.cacheCurrentDetail();
	}

	/**
	 * @since 1.6
	 * @deprecated
	 */
	@Deprecated
	@Override
	protected void deleteRows(List<EObject> deletionList, final EObject eObject,
		final EStructuralFeature structuralFeature) {
		super.deleteRows(deletionList, eObject, structuralFeature);
		final Set<Diagnostic> toDelete = new LinkedHashSet<Diagnostic>();
		final VDiagnostic diagnostic = getVElement().getDiagnostic();
		if (diagnostic == null) {
			return;
		}
		for (final EObject deleteObject : deletionList) {
			toDelete.addAll(diagnostic.getDiagnostics(deleteObject));
			final TreeIterator<EObject> eAllContents = deleteObject.eAllContents();
			while (eAllContents.hasNext()) {
				toDelete.addAll(diagnostic.getDiagnostics(eAllContents.next()));
			}
		}
		diagnostic.getDiagnostics().removeAll(toDelete);
	}
}
