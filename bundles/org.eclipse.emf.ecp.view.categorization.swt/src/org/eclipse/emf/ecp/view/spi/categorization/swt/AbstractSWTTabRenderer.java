/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.internal.categorization.swt.Activator;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Abstract class for a tab renderer.
 *
 * @author Eugen Neufeld
 * @param <VELEMENT> the {@link VElement}
 */
public abstract class AbstractSWTTabRenderer<VELEMENT extends VElement> extends AbstractSWTRenderer<VELEMENT> {

	private final Map<CTabItem, VAbstractCategorization> itemToCategorizationMap = new LinkedHashMap<CTabItem, VAbstractCategorization>();
	private final Map<CTabItem, Composite> itemToCompositeMap = new LinkedHashMap<CTabItem, Composite>();

	private final EMFFormsRendererFactory emfFormsRendererFactory;
	private final EMFDataBindingContext dataBindingContext;
	private final VTViewTemplateProvider viewTemplateProvider;

	private EMFFormsRendererFactory getEMFFormsRendererFactory() {
		return emfFormsRendererFactory;
	}

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param emfFormsRendererFactory The {@link EMFFormsRendererFactory}
	 * @param viewTemplateProvider the {@link VTViewTemplateProvider}
	 * @since 1.8
	 */
	public AbstractSWTTabRenderer(
		VELEMENT vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsRendererFactory emfFormsRendererFactory,
		VTViewTemplateProvider viewTemplateProvider) {
		super(vElement, viewContext, reportService);
		this.emfFormsRendererFactory = emfFormsRendererFactory;
		this.viewTemplateProvider = viewTemplateProvider;
		dataBindingContext = new EMFDataBindingContext();
	}

	/**
	 * Returns the view template provider.
	 *
	 * @return the {@link VTViewTemplateProvider}
	 * @since 1.8
	 */
	protected final VTViewTemplateProvider getViewTemplateProvider() {
		return viewTemplateProvider;
	}

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final boolean useScrolledContent = useScrolledContent();

		final CTabFolder folder = new CTabFolder(parent, getTabFolderStyle());
		folder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				itemSelected(folder.getSelection());
			}
		});
		folder.setBackground(parent.getBackground());
		final EList<VAbstractCategorization> categorizations = getCategorizations();
		for (final VAbstractCategorization categorization : categorizations) {
			final CTabItem item = new CTabItem(folder, SWT.NULL);
			final Composite composite;
			if (useScrolledContent) {
				composite = new ScrolledComposite(folder, SWT.V_SCROLL | SWT.H_SCROLL);
			} else {
				composite = new Composite(folder, SWT.NONE);
				GridLayoutFactory.fillDefaults().applyTo(composite);
			}

			itemToCategorizationMap.put(item, categorization);
			itemToCompositeMap.put(item, composite);

			final IObservableValue modelValue = EMFEditObservables.observeValue(
				AdapterFactoryEditingDomain.getEditingDomainFor(categorization), categorization,
				VViewPackage.eINSTANCE.getElement_Label());
			final IObservableValue targetValue = WidgetProperties.text().observe(item);
			dataBindingContext.bindValue(targetValue, modelValue);

			if (!renderLazy()) {
				renderItem(item);
			}
			SWTDataElementIdHelper.setElementIdDataWithSubId(item, categorization, "tabitem", getViewModelContext()); //$NON-NLS-1$
			SWTDataElementIdHelper.setElementIdDataWithSubId(composite, categorization, "tabitem-composite", //$NON-NLS-1$
				getViewModelContext());
		}
		if (folder.getItemCount() > 0) {
			folder.setSelection(0);
			itemSelected(folder.getSelection());
		}
		SWTDataElementIdHelper.setElementIdDataWithSubId(folder, getVElement(), "tabfolder", getViewModelContext()); //$NON-NLS-1$
		return folder;
	}

	private void renderItem(final CTabItem item) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		if (!itemToCategorizationMap.containsKey(item)) {
			return;/* already rendered or invalid state */
		}

		/* remove from the maps on first rendering */
		final VAbstractCategorization categorization = itemToCategorizationMap.remove(item);
		final Composite composite = itemToCompositeMap.remove(item);
		final boolean useScrolledContent = useScrolledContent();

		AbstractSWTRenderer<VElement> renderer;
		try {
			renderer = getEMFFormsRendererFactory().getRendererInstance(categorization, getViewModelContext());
		} catch (final EMFFormsNoRendererException ex) {
			getReportService().report(
				new StatusReport(
					new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
						"No Renderer for %s found.", categorization.eClass().getName(), ex)))); //$NON-NLS-1$
			return;
		}
		final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
			.createEmptyGridDescription());
		for (final SWTGridCell gridCell : gridDescription.getGrid()) {
			final Control render = renderer.render(gridCell, composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true)
				.applyTo(render);
			if (useScrolledContent) {
				final ScrolledComposite scrolledComposite = ScrolledComposite.class.cast(composite);
				scrolledComposite.setExpandHorizontal(true);
				scrolledComposite.setExpandVertical(true);
				scrolledComposite.setContent(render);
				scrolledComposite.setMinSize(render.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
			item.setControl(composite);
		}
	}

	/**
	 * This method gets called when a tab item was selected. Subclasses may call this method when programmatic selection
	 * changes have been made.
	 *
	 * @param selection the selected item
	 *
	 * @since 1.9
	 */
	protected final void itemSelected(CTabItem selection) {
		try {
			renderItem(selection);
		} catch (final NoRendererFoundException ex) {
			getReportService().report(
				new StatusReport(
					new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
						"No Renderer for %s found.", selection.getText(), ex)))); //$NON-NLS-1$
		} catch (final NoPropertyDescriptorFoundExeption ex) {
			getReportService().report(
				new StatusReport(
					new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
						"No Renderer for %s found.", selection.getText(), ex)))); //$NON-NLS-1$
		}
	}

	/**
	 * Whether a {@link ScrolledComposite} should be used as the item's content or not.
	 *
	 * @return <code>true</code> if pane should be scrollable, <code>false</code> otherwise
	 * @since 1.9
	 */
	protected boolean useScrolledContent() {
		return true;
	}

	/**
	 * Whether to render all tab items immediately or on selection.
	 *
	 * @return <code>true</code> if the item UI will be rendered on first selection, <code>false</code> if all items
	 *         will be rendered immediately
	 * @since 1.9
	 */
	protected boolean renderLazy() {
		return false;
	}

	private int getTabFolderStyle() {
		if (getViewTemplateProvider() == null) {
			return getDefaultFolderStyle();
		}
		final Set<VTStyleProperty> styleProperties = getViewTemplateProvider()
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (!VTTabStyleProperty.class.isInstance(styleProperty)) {
				continue;
			}
			final VTTabStyleProperty style = VTTabStyleProperty.class.cast(styleProperty);
			switch (style.getType()) {
			case BOTTOM:
				return SWT.BOTTOM;
			case TOP:
				return SWT.TOP;
			default:
				return getDefaultFolderStyle();
			}
		}
		return getDefaultFolderStyle();
	}

	private int getDefaultFolderStyle() {
		return SWT.BOTTOM;
	}

	/**
	 * The list of categorizations to display in the tree.
	 *
	 * @return the list of {@link VAbstractCategorization}
	 */
	protected abstract EList<VAbstractCategorization> getCategorizations();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		dataBindingContext.dispose();
		super.dispose();
	}

}
