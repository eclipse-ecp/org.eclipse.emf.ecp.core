/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.swt.internal.reference.table;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectModelElementCompositeImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.InvalidGridDescriptionReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.emfforms.spi.swt.table.ColumnConfigurationBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTBuilder;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * Implementation of a table selection composite that is described by a view model.
 */
public class TableSelectionCompositeImpl extends SelectModelElementCompositeImpl {

	/** Namespace URI of the dynamic selection extent package. */
	private static final String SELECTION_NS_URI = "http://www.eclipse.org/emf/ecp/view/reference/table/selection"; //$NON-NLS-1$

	/** The selection extent package. */
	private static final EPackage SELECTION_PACKAGE = EPackage.Registry.INSTANCE.getEPackage(SELECTION_NS_URI);

	/** The <em>Selection</em> class. */
	private static final EClass SELECTION_CLASS = (EClass) SELECTION_PACKAGE.getEClassifier("Selection"); //$NON-NLS-1$

	/** The <em>extent</em> reference of the <em>Selection</em> class. */
	private static final EStructuralFeature SELECTION_EXTENT = SELECTION_CLASS.getEStructuralFeature("extent"); //$NON-NLS-1$

	private final Collection<? extends EObject> extent;
	private final VTableControl tableControl;
	private final EObject owner;

	private ViewModelContext context;
	private VDomainModelReference realDMR;
	private ServiceRegistration<DomainModelReferenceConverterEMF> dmrConverter;

	/**
	 * Initializes me with the table view model to render.
	 *
	 * @param extent the collection of objects from which to make a selection
	 * @param tableControl the view model description of the table
	 * @param owner the owner of the {@code reference} being edited
	 * @param reference the reference being edited
	 */
	public TableSelectionCompositeImpl(Collection<? extends EObject> extent, VTableControl tableControl,
		EObject owner, EReference reference) {

		super(extent, reference.isMany());

		this.extent = extent;
		this.tableControl = tableControl;
		this.owner = owner;
	}

	@Override
	public void dispose() {
		if (context != null) {
			context.dispose();
			context = null;
		}

		if (dmrConverter != null) {
			dmrConverter.unregister();
		}

		super.dispose();
	}

	@Override
	protected TableViewer createViewer(Composite composite) {
		final EObject selectionExtent = createSelectionExtent();

		// Put the table in a view by itself
		final VView tableView = VViewFactory.eINSTANCE.createView();
		tableView.getChildren().add(tableControl);

		// We don't want anything in this view to be editable: it's only for selection
		tableView.setReadonly(true);

		// Set the extent DMR into the table model
		final VFeaturePathDomainModelReference extentDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		extentDMR.setDomainModelEFeature(selectionExtent.eClass().getEReferences().get(0));
		if (tableControl.getDomainModelReference() == null) {
			// So we have no column DMRs. What then is the point?
			realDMR = tableControl.getDomainModelReference();
			tableControl.setDomainModelReference(extentDMR);
		} else if (tableControl.getDomainModelReference() instanceof VTableDomainModelReference) {
			final VTableDomainModelReference dmr = (VTableDomainModelReference) tableControl.getDomainModelReference();
			realDMR = dmr.getDomainModelReference();
			dmr.setDomainModelReference(extentDMR);
		} else {
			throw new IllegalArgumentException("table model has no table DMR"); //$NON-NLS-1$
		}

		registerDMRConverter(selectionExtent, extentDMR);

		context = ViewModelContextFactory.INSTANCE.createViewModelContext(tableView, owner);

		final PrivateTableRenderer renderer = new PrivateTableRenderer(context, tableControl);

		final Control control = renderer.render(composite);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).minSize(0, 150).span(2, 1)
			.applyTo(control);

		final TableViewer result = renderer.getTableViewer();

		return result;
	}

	/**
	 * Create a column label provider that delegates to another label provider.
	 *
	 * @param delegate the label provider delegate
	 * @return the column label provider
	 */
	protected CellLabelProvider createColumnLabelProvider(ILabelProvider delegate) {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return delegate.getText(element);
			}

			@Override
			public Image getImage(Object element) {
				return delegate.getImage(element);
			}
		};
	}

	private EObject createSelectionExtent() {
		final EObject result = SELECTION_PACKAGE.getEFactoryInstance().create(SELECTION_CLASS);
		@SuppressWarnings("unchecked")
		final EList<EObject> selectionExtent = (EList<EObject>) result.eGet(SELECTION_EXTENT);
		selectionExtent.addAll(extent);

		return result;
	}

	private void registerDMRConverter(EObject source, VFeaturePathDomainModelReference dmr) {
		final DelegatingDomainModelReferenceConverter delegator = new DelegatingDomainModelReferenceConverter(dmr,
			__ -> source);
		final BundleContext ctx = FrameworkUtil.getBundle(TableSelectionCompositeImpl.class).getBundleContext();

		final Hashtable<String, Object> properties = new Hashtable<>();
		properties.put(Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		dmrConverter = ctx.registerService(DomainModelReferenceConverterEMF.class, delegator, properties);
	}

	//
	// Nested types
	//

	/**
	 * Private subclass of the table renderer to access some protected API and customize
	 * its presentation for our use case.
	 */
	private final class PrivateTableRenderer extends TableControlSWTRenderer {

		private final EMFFormsLocalizationService l10n;
		private final EMFFormsLabelProvider labels;

		/**
		 * Initializes me.
		 */
		PrivateTableRenderer(ViewModelContext context, VTableControl vTable) {
			super(vTable, context, context.getService(ReportService.class),
				context.getService(EMFFormsDatabindingEMF.class),
				context.getService(EMFFormsLabelProvider.class),
				context.getService(VTViewTemplateProvider.class),
				context.getService(ImageRegistryService.class),
				context.getService(EMFFormsEditSupport.class));

			l10n = context.getService(EMFFormsLocalizationService.class);
			labels = context.getService(EMFFormsLabelProvider.class);
		}

		@Override
		protected TableViewer getTableViewer() {
			return (TableViewer) super.getTableViewer();
		}

		@Override
		protected TableViewerCompositeBuilder createTableViewerCompositeBuilder() {
			return new MinimalCompositeBuilder();
		}

		@Override
		protected int addAdditionalColumns(TableViewerSWTBuilder tableViewerSWTBuilder) {
			// Inject the selection column
			final ColumnConfigurationBuilder columnBuilder = ColumnConfigurationBuilder.usingDefaults()
				.labelProvider(createColumnLabelProvider(getLabelProvider()))
				.minWidth(50).weight(100).resizable(true)
				.text(getSelectionColumnTitle())
				.tooltip(getSelectionColumnTooltip());
			tableViewerSWTBuilder.addColumn(columnBuilder.build());

			return 1;
		}

		private IObservableValue<String> getSelectionColumnTitle() {
			final String result = l10n.getString(TableSelectionCompositeImpl.class, "selectionColumn"); //$NON-NLS-1$
			return Observables.constantObservableValue(Realm.getDefault(), result, String.class);
		}

		private IObservableValue<String> getSelectionColumnTooltip() {
			final IObservableValue<String> featureName = getRealDMRDisplayName();
			return ComputedValue
				.create(() -> NLS.bind(l10n.getString(TableSelectionCompositeImpl.class, "selectionTooltip"), //$NON-NLS-1$
					featureName.getValue()));
		}

		@SuppressWarnings("unchecked")
		private IObservableValue<String> getRealDMRDisplayName() {
			IObservableValue<String> result;
			try {
				result = labels.getDisplayName(realDMR, owner);
			} catch (final NoLabelFoundException e) {
				result = Observables.constantObservableValue(Realm.getDefault(),
					l10n.getString(TableSelectionCompositeImpl.class, "reference"), //$NON-NLS-1$
					String.class);
			}
			return result;
		}

		Control render(Composite parent) {
			Control result = null;

			init();

			final SWTGridDescription grid = getGridDescription(
				GridDescriptionFactory.INSTANCE.createEmptyGridDescription());
			if (grid.getGrid().isEmpty()) {
				getReportService().report(
					new InvalidGridDescriptionReport("grid has no cells")); //$NON-NLS-1$
			}

			try {
				result = renderTableControl(grid.getGrid().get(0), parent);
				finalizeRendering(parent);
			} catch (final NoRendererFoundException | NoPropertyDescriptorFoundExeption e) {
				getReportService().report(new RenderingFailedReport(e));
			}

			return result;
		}

		//
		// Nested types
		//

		/**
		 * A composite builder that leaves the top composite empty.
		 */
		private final class MinimalCompositeBuilder extends TableControlSWTRendererCompositeBuilder {

			@Override
			protected Label createTitleLabel(Composite parentComposite, Color background) {
				return null;
			}

			@Override
			protected Label createValidationLabel(Composite topComposite) {
				return null;
			}

			@Override
			protected Composite createButtonComposite(Composite parentComposite) {
				return null;
			}

			@Override
			public Optional<Label> getTitleLabel() {
				return Optional.empty();
			}

			@Override
			public Optional<List<Control>> getValidationControls() {
				return Optional.empty();
			}

			@Override
			public Optional<Composite> getButtonComposite() {
				return Optional.empty();
			}

		}

	}

}
