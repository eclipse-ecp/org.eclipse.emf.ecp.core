/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.categorization.expandbar;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.widgets.MarkupValidator;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Table;

/**
 * A VCategorizationElement renderer that renders the categories as expandbar items.
 * This renderer does only work if there are only VCategorization items on the root and each VCategorization item
 * contains only VCategory items.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class ExpandBarCategorizationElementRenderer extends AbstractSWTRenderer<VCategorizationElement> {

	private static final String VALIDATION_ERRORS = "validationErrors"; //$NON-NLS-1$
	private SWTGridDescription gridDescription;
	private final EMFFormsRendererFactory emfFormsRendererFactory;
	private ScrolledComposite editorComposite;
	private AdapterFactoryLabelProvider labelProvider;
	private ExpandBar expandBar;

	private final EMFDataBindingContext dbc;
	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryContentProvider contentProvider;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param emfFormsRendererFactory The {@link EMFFormsRendererFactory}
	 * @since 1.9
	 */
	@Inject
	public ExpandBarCategorizationElementRenderer(VCategorizationElement vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsRendererFactory emfFormsRendererFactory) {
		super(vElement, viewContext, reportService);
		this.emfFormsRendererFactory = emfFormsRendererFactory;
		dbc = new EMFDataBindingContext();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#getGridDescription(org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (this.gridDescription == null) {
			this.gridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return this.gridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		final EList<VAbstractCategorization> categorizations = getVElement().getCategorizations();

		if (categorizations.size() == 1 && categorizations.get(0) instanceof VCategory) {
			final VElement child = categorizations.get(0);
			AbstractSWTRenderer<VElement> renderer;
			try {
				renderer = getEMFFormsRendererFactory().getRendererInstance(child,
					getViewModelContext());
			} catch (final EMFFormsNoRendererException ex) {
				getReportService().report(new AbstractReport(ex));
				return null;
			}
			final Control render = renderer.render(cell, parent);
			renderer.finalizeRendering(parent);
			SWTDataElementIdHelper.setElementIdDataWithSubId(render, getVElement(), "vcategory", getViewModelContext()); //$NON-NLS-1$
			return render;

		}

		Composite expandBar;
		final Object detailPane = getViewModelContext().getContextValue("detailPane"); //$NON-NLS-1$
		if (detailPane != null && Composite.class.isInstance(detailPane)) {
			expandBar = createExpandBarMaster(parent);

			editorComposite = createdEditorPane(Composite.class.cast(detailPane));

			Composite.class.cast(detailPane).layout();

			selectFirstEntry();

			return expandBar;
		}

		final SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		SWTDataElementIdHelper.setElementIdDataWithSubId(sashForm, getVElement(), "sash", getViewModelContext()); //$NON-NLS-1$
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(sashForm);

		expandBar = createExpandBarMaster(sashForm);

		editorComposite = createdEditorPane(sashForm);

		sashForm.setWeights(new int[] { 1, 3 });

		selectFirstEntry();

		return sashForm;
	}

	private void selectFirstEntry() {
		if (expandBar.getItemCount() != 0) {
			final TableViewer tableViewer = TableViewer.class
				.cast(expandBar.getItem(0).getData("tableViewer")); //$NON-NLS-1$
			tableViewer.setSelection(new StructuredSelection(tableViewer.getElementAt(0)));
		}
	}

	private Composite createExpandBarMaster(final Composite parent) {

		labelProvider = new AdapterFactoryLabelProvider(adapterFactory) {

			@Override
			public Image getImage(Object object) {
				return null;
			}

			@Override
			public Image getColumnImage(Object object, int columnIndex) {
				return null;
			}

			@Override
			public String getColumnText(Object object, int columnIndex) {
				String result = super.getColumnText(object, columnIndex);
				final VDiagnostic diagnostic = VCategory.class.cast(object).getDiagnostic();
				if (diagnostic != null && diagnostic.getHighestSeverity() > 0) {

					result += String.format(
						" <span style='background-color:#%2$s;border-radius: 1em;color:#%3$s;height:1.25em;width:1.25em;display:inline-block;text-align:center'>%1$s</span>", //$NON-NLS-1$
						diagnostic.getDiagnostics().size(), SWTValidationHelper.INSTANCE.getValidationColorHEX(
							diagnostic.getHighestSeverity(), getVElement(), getViewModelContext()),
						getFontColor());
				}
				return String.format("<span style='display:inline-block;margin-left:1.25em;'>%1$s</span>", result); //$NON-NLS-1$
			}

		};

		final Composite expandBarComposite = new Composite(parent, SWT.BORDER);
		SWTDataElementIdHelper.setElementIdDataWithSubId(expandBarComposite, getVElement(), "expandBarComposite", //$NON-NLS-1$
			getViewModelContext());
		expandBarComposite.setLayout(new GridLayout());

		expandBar = new ExpandBar(expandBarComposite, SWT.V_SCROLL);
		SWTDataElementIdHelper.setElementIdDataWithSubId(expandBar, getVElement(), "expandBar", getViewModelContext()); //$NON-NLS-1$
		expandBar.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		int destroyedItems = 0;
		for (final VAbstractCategorization categorization : getVElement().getCategorizations()) {

			final ExpandItem item = createExpandItem(adapterFactory, categorization, destroyedItems);
			item.setExpanded(true);

			categorization.eAdapters().add(new CategorizationVisibilityAdapter(categorization, item));
			if (!categorization.isVisible()) {
				item.dispose();
				destroyedItems++;
			}
		}

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true)
			.hint(SWT.DEFAULT, 5000) // arbitrary high height that won't be reached
			.applyTo(expandBar);

		return expandBarComposite;
	}

	private ExpandItem createExpandItem(final ComposedAdapterFactory adapterFactory,
		final VAbstractCategorization categorization, int destroyedItems) {
		final ExpandItem item = new ExpandItem(expandBar, SWT.NONE,
			getVElement().getCategorizations().indexOf(categorization) - destroyedItems);
		SWTDataElementIdHelper.setElementIdDataWithSubId(item, categorization, "expandItem", getViewModelContext()); //$NON-NLS-1$
		item.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);

		final ISWTObservableValue target = WidgetProperties.text().observe(item);
		final IObservableValue modelValue = EMFEditObservables.observeValue(
			AdapterFactoryEditingDomain.getEditingDomainFor(categorization), categorization,
			VViewPackage.eINSTANCE.getElement_Label());
		dbc.bindValue(target, modelValue);

		item.setImage(labelProvider.getImage(categorization));

		final TableViewer tv = new TableViewer(expandBar, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tv.getTable().setHeaderVisible(false);
		tv.setLabelProvider(labelProvider);
		tv.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return VCategory.class.isInstance(element) && VCategory.class.cast(element).isVisible();
			}
		});

		contentProvider = new AdapterFactoryContentProvider(adapterFactory);
		tv.setContentProvider(contentProvider);
		tv.setInput(VCategorization.class.cast(categorization));
		tv.addSelectionChangedListener(new CustomTableSelectionAdapter());
		tv.getTable().setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		tv.getTable().setData(MarkupValidator.MARKUP_VALIDATION_DISABLED, Boolean.TRUE);
		item.setControl(tv.getControl());
		SWTDataElementIdHelper.setElementIdDataWithSubId(tv.getControl(), categorization, "expandItemContent", //$NON-NLS-1$
			getViewModelContext());
		item.setHeight(computeHeight(item.getControl()));
		if (categorization.getDiagnostic() != null && categorization.getDiagnostic().getHighestSeverity() > 0) {
			item.setData(RWT.CUSTOM_VARIANT, getValidationKey(categorization));
		}
		item.setData("tableViewer", tv); //$NON-NLS-1$
		return item;
	}

	private String getValidationKey(final VAbstractCategorization categorization) {
		switch (categorization.getDiagnostic().getHighestSeverity()) {
		case Diagnostic.INFO:
			return VALIDATION_ERRORS + "_info"; //$NON-NLS-1$
		case Diagnostic.WARNING:
			return VALIDATION_ERRORS + "_warning"; //$NON-NLS-1$
		case Diagnostic.ERROR:
			return VALIDATION_ERRORS + "_error"; //$NON-NLS-1$
		case Diagnostic.CANCEL:
			return VALIDATION_ERRORS + "_cancel"; //$NON-NLS-1$
		default:
			return VALIDATION_ERRORS;
		}
	}

	private String getFontColor() {
		return "ffffff"; //$NON-NLS-1$
	}

	private int computeHeight(Control control) {
		return control.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 1;
	}

	private Composite createComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getBackground());

		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).margins(7, 7).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(composite);
		return composite;
	}

	/**
	 * Created editor pane.
	 *
	 * @param composite the composite
	 * @return the created editor composite
	 */
	protected ScrolledComposite createdEditorPane(Composite composite) {
		final ScrolledComposite editorComposite = createScrolledComposite(composite);
		editorComposite.setExpandHorizontal(true);
		editorComposite.setExpandVertical(true);
		editorComposite.setShowFocusedControl(true);
		SWTDataElementIdHelper.setElementIdDataWithSubId(editorComposite, getVElement(), "editorComposite", //$NON-NLS-1$
			getViewModelContext());

		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(editorComposite);

		return editorComposite;
	}

	/**
	 * Creates the scrolled composite.
	 *
	 * @param parent the parent
	 * @return the scrolled composite
	 */
	private ScrolledComposite createScrolledComposite(Composite parent) {
		final ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL
			| SWT.BORDER);
		scrolledComposite.setShowFocusedControl(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setBackground(parent.getBackground());

		return scrolledComposite;
	}

	private EMFFormsRendererFactory getEMFFormsRendererFactory() {
		return emfFormsRendererFactory;
	}

	/**
	 * Adapter to check for visibility changes on categorizations.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private final class CategorizationVisibilityAdapter extends AdapterImpl {
		private final VAbstractCategorization categorization;
		private ExpandItem catItem;

		private CategorizationVisibilityAdapter(VAbstractCategorization categorization, ExpandItem item) {
			this.categorization = categorization;
			catItem = item;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		@Override
		public void notifyChanged(Notification msg) {
			super.notifyChanged(msg);
			if (msg.getFeature() != VViewPackage.eINSTANCE.getElement_Visible()) {
				return;
			}
			if (!categorization.isVisible()) {
				if (catItem.getControl().isFocusControl()) {
					expandBar.forceFocus();
				}
				catItem.dispose();
			} else {
				int index=Math.min(expandBar.getChildren().length, getVElement().getCategorizations().indexOf(categorization));
				catItem = createExpandItem(adapterFactory, categorization,index );
			}
		}
	}

	/**
	 * A custom adapter that resets the selection in the other table viewers and triggers the rendering of the selected
	 * category.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private class CustomTableSelectionAdapter implements ISelectionChangedListener {
		private Composite childComposite;

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			for (int i = 0; i < expandBar.getItemCount(); i++) {
				final ExpandItem expandItem = expandBar.getItem(i);
				final Table table = (Table) expandItem.getControl();
				if (TableViewer.class.cast(event.getSource()).getTable() != table) {
					table.deselectAll();
				}
			}
			final Object selection = IStructuredSelection.class.cast(event.getSelection()).getFirstElement();

			if (childComposite != null) {
				childComposite.dispose();
				childComposite = null;
			}
			if (selection == null) {
				return;
			}
			childComposite = createComposite(editorComposite);

			childComposite.setBackground(editorComposite.getBackground());
			editorComposite.setContent(childComposite);

			final VElement child = (VElement) selection;
			try {

				AbstractSWTRenderer<VElement> renderer;
				try {
					renderer = getEMFFormsRendererFactory().getRendererInstance(child, getViewModelContext());
				} catch (final EMFFormsNoRendererException ex) {
					getReportService().report(new AbstractReport(ex));
					return;
				}
				final Control render = renderer.render(
					renderer.getGridDescription(
						GridDescriptionFactory.INSTANCE.createEmptyGridDescription()).getGrid().get(0),
					childComposite);
				renderer.finalizeRendering(childComposite);
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true)
					.minSize(SWT.DEFAULT, 200)
					.applyTo(render);
				getVElement().setCurrentSelection((VCategorizableElement) child);
			} catch (final NoRendererFoundException e) {
				getReportService().report(new RenderingFailedReport(e));
			} catch (final NoPropertyDescriptorFoundExeption e) {
				getReportService().report(new RenderingFailedReport(e));
			}

			childComposite.layout();
			final Point point = childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			editorComposite.setMinSize(point);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#applyValidation()
	 */
	@Override
	protected void applyValidation() {
		super.applyValidation();
		labelProvider.fireLabelProviderChanged();
		for (int i = 0; i < expandBar.getItemCount(); i++) {
			final ExpandItem expandItem = expandBar.getItem(i);
			final VAbstractCategorization categorization = getVElement().getCategorizations().get(i);
			expandItem.setImage(labelProvider.getImage(categorization));
			if (categorization.getDiagnostic() != null && categorization.getDiagnostic().getHighestSeverity() > 0) {
				expandItem.setData(RWT.CUSTOM_VARIANT,
					getValidationKey(categorization));
			} else {
				expandItem.setData(RWT.CUSTOM_VARIANT, null);
			}
		}
	}

	@Override
	protected void dispose() {
		if (dbc != null) {
			dbc.dispose();
		}
		if (adapterFactory != null) {
			adapterFactory.dispose();
		}
		if (labelProvider != null) {
			labelProvider.dispose();
		}
		if (contentProvider != null) {
			contentProvider.dispose();
		}
		for (final VAbstractCategorization categorization : getVElement().getCategorizations()) {
			final Set<Adapter> toRemove = new LinkedHashSet<Adapter>();
			for (final Adapter adapter : categorization.eAdapters()) {
				if (CategorizationVisibilityAdapter.class.isInstance(adapter)) {
					toRemove.add(adapter);
				}
			}
			categorization.eAdapters().removeAll(toRemove);
		}
		super.dispose();
	}
}
