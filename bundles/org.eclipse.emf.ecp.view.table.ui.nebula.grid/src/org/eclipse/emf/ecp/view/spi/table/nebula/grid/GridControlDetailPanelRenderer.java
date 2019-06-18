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
 * Christian W. Damus - bug 545686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverterService;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;

/**
 * Render for a {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl VTableControl} with a detail editing
 * panel.
 *
 * @author jfaltermeier
 *
 */
// TODO: refactoring, this class is a copy of TableControlDetailPanelRenderer. See bug #527686.
public class GridControlDetailPanelRenderer extends GridControlSWTRenderer {

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
	 * @param converterService the {@link EStructuralFeatureValueConverterService}
	 * @param localizationService the {@link EMFFormsLocalizationService}
	 * @since 1.11
	 */
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public GridControlDetailPanelRenderer(VTableControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabindingEMF emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport, EStructuralFeatureValueConverterService converterService,
		EMFFormsLocalizationService localizationService) {
		// CHECKSTYLE.ON: ParameterNumber
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			imageRegistryService, emfFormsEditSupport, converterService, localizationService);
	}

	private ECPSWTView ecpView;
	private Composite detailPanel;
	private Composite border;
	private ScrolledComposite scrolledComposite;
	private VView currentDetailView;
	private boolean currentDetailViewOriginalReadonly;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer#createControlComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createControlComposite(Composite composite) {

		/* border */
		border = createBorderComposite(composite);

		final SashForm sashForm = createSash(border);

		/*
		 * Wrap the table composite in another composite because setting weights on the sash form overrides the layout
		 * data of its direct children. This must not happen on the table composite because the Table Control SWT
		 * Renderer needs the table composite's layout data to be GridData.
		 */
		final Composite tableCompositeWrapper = new Composite(sashForm, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(tableCompositeWrapper);
		final Composite tableComposite = createTableComposite(tableCompositeWrapper);

		/* scrolled composite */
		scrolledComposite = createScrolledDetail(sashForm);

		scrolledComposite.addListener(SWT.Resize,
			(Event event) -> scrolledComposite.setMinSize(detailPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT)));

		// As a default the table gets 1/3 of the space and the detail panel 2/3.
		sashForm.setWeights(new int[] { 1, 2 });

		return tableComposite;
	}

	/**
	 * Creates a composite with a border to surround the grid and detail panel.
	 *
	 * @param parent The parent Composite
	 * @return The border Composite
	 */
	protected Composite createBorderComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.BORDER);
		final GridLayout gridLayout = GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).create();
		composite.setLayout(gridLayout);
		final int totalHeight = getTableHeightHint() + getDetailPanelHeightHint() + gridLayout.verticalSpacing;
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(1, totalHeight)
			.applyTo(composite);
		return composite;
	}

	/**
	 * Creates the SashForm for the grid and the detail panel.
	 *
	 * @param parent the parent
	 * @return the SashForm
	 */
	protected SashForm createSash(Composite parent) {
		final SashForm sash = new SashForm(parent, SWT.VERTICAL);
		sash.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(sash);
		sash.setSashWidth(5);
		return sash;
	}

	/**
	 * Creates the Composite that will contain the grid.
	 *
	 * @param parent The parent Composite to create the grid composite on
	 * @return The grid Composite
	 */
	protected Composite createTableComposite(Composite parent) {
		final Composite tableComposite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(1, getTableHeightHint())
			.applyTo(tableComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(tableComposite);
		return tableComposite;
	}

	/**
	 * Creates a scrolled Composite that contains the detail panel.
	 *
	 * @param parent The parent Composite to create the scrolled composite on
	 * @return The ScrolledComposite containing the detail panel
	 */
	protected ScrolledComposite createScrolledDetail(Composite parent) {
		final ScrolledComposite scrolledComposite = new ScrolledComposite(parent,
			SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		scrolledComposite.setBackground(parent.getBackground());
		scrolledComposite.setLayout(GridLayoutFactory.fillDefaults().create());
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(scrolledComposite);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);

		/* detail panel */
		detailPanel = createDetailPanel(scrolledComposite);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(detailPanel);
		scrolledComposite.setContent(detailPanel);

		return scrolledComposite;
	}

	/**
	 * Returns the preferred height for the detail panel. This will be passed to the layout data.
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
		final Composite detail = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).margins(5, 5).applyTo(detail);
		return detail;
	}

	/**
	 * Returns a fresh copy of the {@link VView} used for detail editing based on the provided EObject.
	 *
	 * @param selectedEObject The selected EObject for which to provide the View
	 * @return the view
	 */
	protected VView getView(EObject selectedEObject) {
		VView detailView = getVElement().getDetailView();
		if (detailView == null) {

			final VElement viewModel = getViewModelContext().getViewModel();
			final VViewModelProperties properties = ViewModelPropertiesHelper
				.getInhertitedPropertiesOrEmpty(viewModel);
			detailView = ViewProviderHelper.getView(selectedEObject, properties);
		}

		currentDetailViewOriginalReadonly = detailView.isReadonly();
		return detailView;
	}

	@Override
	protected void applyEnable() {
		super.applyEnable();
		if (currentDetailView != null) {
			// Set the detail view to read only if this grid is disabled or read only. Use the detail view's original
			// read only state if this grid is enabled and not read only.
			currentDetailView.setReadonly(!getVElement().isEffectivelyEnabled() || getVElement().isEffectivelyReadonly()
				|| currentDetailViewOriginalReadonly);
		}
	}

	@Override
	protected void applyReadOnly() {
		super.applyReadOnly();
		if (currentDetailView != null) {
			// Set the detail view to read only if this grid is disabled or read only. Use the detail view's original
			// read only state if this grid is enabled and not read only.
			currentDetailView.setReadonly(!getVElement().isEffectivelyEnabled() || getVElement().isEffectivelyReadonly()
				|| currentDetailViewOriginalReadonly);
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
		// Did the selection actionally change? We may have stepped sideways in a row
		final EObject object = (EObject) selection.getFirstElement();
		if (ecpView != null && ecpView.getViewModelContext().getDomainModel() == object) {
			return;
		}

		disposeDetail();
		final Composite compositeToRenderOn = new Composite(detailPanel, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(compositeToRenderOn);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(compositeToRenderOn);

		renderSelectedObject(compositeToRenderOn, object);
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
		currentDetailView = getView(eObject);
		if (currentDetailView == null) {

			final Label label = new Label(composite, SWT.NONE);
			label.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_RED));
			label.setText("No Detail View found."); //$NON-NLS-1$

		} else {
			final ViewModelContext childContext = getViewModelContext().getChildContext(eObject, getVElement(),
				currentDetailView);
			currentDetailView = (VView) childContext.getViewModel();
			// Set the detail view to read only if this grid is read only or disabled
			currentDetailView.setReadonly(
				!getVElement().isEffectivelyEnabled() || getVElement().isEffectivelyReadonly()
					|| currentDetailViewOriginalReadonly);
			try {
				ecpView = ECPSWTViewRenderer.INSTANCE.render(composite, childContext);
			} catch (final ECPRendererException ex) {
				getReportService().report(new RenderingFailedReport(ex));
			}
		}
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
		if (ecpView != null) {
			ecpView.getSWTControl().dispose();
			ecpView = null;
		}
		for (final Control control : detailPanel.getChildren()) {
			control.dispose();
		}
	}

	@Override
	@Deprecated
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
