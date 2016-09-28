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
 * Johannes Faltermeier - refactorings
 * Lucas Koehler - use multi segment instead of table dmr
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.edit.internal.swt.util.CellEditorFactory;
import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorComparator;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPElementAwareCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.internal.table.swt.CellReadOnlyTesterHelper;
import org.eclipse.emf.ecp.view.internal.table.swt.MessageKeys;
import org.eclipse.emf.ecp.view.internal.table.swt.TableConfigurationHelper;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.DiagnosticMessageExtractor;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundFactory;
import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundStyleProperty;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesFactory;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.core.layout.EMFFormsSWTLayoutUtil;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.emfforms.spi.swt.table.ButtonBarBuilder;
import org.eclipse.emfforms.spi.swt.table.CellLabelProviderFactory;
import org.eclipse.emfforms.spi.swt.table.EditingSupportCreator;
import org.eclipse.emfforms.spi.swt.table.TableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerCreator;
import org.eclipse.emfforms.spi.swt.table.TableViewerFactory;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTBuilder;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapCellLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.osgi.framework.FrameworkUtil;

/**
 * SWT Renderer for Table Control.
 *
 * @author Eugen Neufeld
 * @author Johannes Faltermeier
 *
 */
public class TableControlSWTRenderer extends AbstractControlSWTRenderer<VTableControl> {
	private static final String FIXED_COLUMNS = "org.eclipse.rap.rwt.fixedColumns"; //$NON-NLS-1$
	private static final String TABLE_CUSTOM_VARIANT = "org_eclipse_emf_ecp_control_table"; //$NON-NLS-1$

	private static final String ICON_ADD = "icons/add.png"; //$NON-NLS-1$
	private static final String ICON_DELETE = "icons/delete.png"; //$NON-NLS-1$

	private final Map<Integer, ECPCellEditorComparator> columnIndexToComparatorMap = new LinkedHashMap<Integer, ECPCellEditorComparator>();

	private final ImageRegistryService imageRegistryService;
	private final EMFDataBindingContext viewModelDBC;
	private final EMFFormsEditSupport emfFormsEditSupport;

	private SWTGridDescription rendererGridDescription;

	private TableViewer tableViewer;

	private Label validationIcon;
	private Button addButton;
	private Button removeButton;

	private Optional<Integer> minimumHeight;
	private Optional<Integer> maximumHeight;
	private TableControlSWTRendererButtonBarBuilder tableControlSWTRendererButtonBarBuilder;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param imageRegistryService The {@link ImageRegistryService}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 * @since 1.8
	 */
	@Inject
	// BEGIN COMPLEX CODE
	public TableControlSWTRenderer(
		VTableControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabindingEMF emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider,
		ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport) {
		// END COMPLEX CODE

		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.imageRegistryService = imageRegistryService;
		this.emfFormsEditSupport = emfFormsEditSupport;
		viewModelDBC = new EMFDataBindingContext();
	}

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return rendererGridDescription;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#getEMFFormsDatabinding()
	 * @since 1.8
	 */
	@Override
	protected EMFFormsDatabindingEMF getEMFFormsDatabinding() {
		return (EMFFormsDatabindingEMF) super.getEMFFormsDatabinding();
	}

	@Override
	protected Control renderControl(SWTGridCell gridCell, final Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		try {
			/* get the list-setting which is displayed */
			final VDomainModelReference dmrToCheck = getDMRToMultiReference();

			/* get the observable list */
			final IObservableList list = getEMFFormsDatabinding().getObservableList(dmrToCheck,
				getViewModelContext().getDomainModel());

			/* get the label text/tooltip */
			final IObservableValue labelText = getLabelText(dmrToCheck, false);
			final IObservableValue labelTooltipText = getLabelTooltipText(dmrToCheck, false);

			/* content provider */
			final ObservableListContentProvider cp = new ObservableListContentProvider();

			final ECPTableViewerComparator comparator = new ECPTableViewerComparator();

			/* render */
			final TableViewerCompositeBuilder compositeBuilder = new TableControlSWTRendererCompositeBuilder();
			tableControlSWTRendererButtonBarBuilder = new TableControlSWTRendererButtonBarBuilder();
			final TableViewerSWTBuilder tableViewerSWTBuilder = TableViewerFactory
				.fillDefaults(parent, SWT.NONE, list, labelText, labelTooltipText)
				.customizeCompositeStructure(compositeBuilder)
				.customizeButtons(tableControlSWTRendererButtonBarBuilder)
				.customizeTableViewerCreation(new TableControlSWTRendererTableViewerCreator())
				.customizeContentProvider(cp)
				.customizeComparator(comparator);

			/* add columns */
			int regularColumnsStartIndex = 0;

			/* validation column */
			if (!getVElement().isReadonly()) {
				regularColumnsStartIndex++;
				createFixedValidationStatusColumn(tableViewerSWTBuilder);
			}
			regularColumnsStartIndex += addAdditionalColumns(tableViewerSWTBuilder);
			addColumns(tableViewerSWTBuilder, EReference.class.cast(list.getElementType()).getEReferenceType(), cp);

			initCompositeHeight();

			final TableViewerComposite tableViewerComposite = tableViewerSWTBuilder.create();

			/* setup selection changes listener */
			tableViewerComposite.getTableViewer().addSelectionChangedListener(new ViewerSelectionChangedListener());
			tableViewerComposite.getTableViewer().addDoubleClickListener(new DoubleClickListener());

			/* setup sorting via column selection */
			setupSorting(comparator, regularColumnsStartIndex, tableViewerComposite);

			/* get validation icon */
			setupValidation(tableViewerComposite);

			setTableViewer(tableViewerComposite.getTableViewer());

			SWTDataElementIdHelper.setElementIdDataForVControl(tableViewerComposite, getVElement(),
				getViewModelContext());

			// FIXME doesn't work with table with panel
			// setLayoutData(compositeBuilder.getViewerComposite());
			GridData.class
				.cast(compositeBuilder.getViewerComposite().getLayoutData()).heightHint = getTableHeightHint();

			addRelayoutListenerIfNeeded(list, compositeBuilder.getViewerComposite());

			addResizeListener(tableViewerComposite.getTableViewer().getTable(), regularColumnsStartIndex);

			return tableViewerComposite;

		} catch (final DatabindingFailedException ex) {
			getReportService().report(new RenderingFailedReport(ex));
			final Label errorLabel = new Label(parent, SWT.NONE);
			errorLabel.setText(ex.getMessage());
			return errorLabel;
		}
	}

	/**
	 * Override this method to add additional static columns at the beginning of the table.
	 *
	 * @param tableViewerSWTBuilder the builder
	 * @return the number of columns added
	 * @since 1.9
	 */
	protected int addAdditionalColumns(TableViewerSWTBuilder tableViewerSWTBuilder) {
		return 0;
	}

	private void addResizeListener(final Table table, final int regularColumnsStartIndex) {
		final ControlAdapter controlAdapter = new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				updateTableColumnWidths(table, regularColumnsStartIndex);
			}
		};
		table.addControlListener(controlAdapter);
		for (final TableColumn tableColumn : table.getColumns()) {
			tableColumn.addControlListener(controlAdapter);
		}
	}

	private void updateTableColumnWidths(Table table, int regularColumnsStartIndex) {
		final VTableControl tableControl = getVElement();
		final Table swtTable = getTableViewer().getTable();
		final TableColumn[] allColumns = swtTable.getColumns();
		for (int i = regularColumnsStartIndex; i < allColumns.length; i++) {
			final TableColumn tableColumn = allColumns[i];
			final VDomainModelReference columnDMR = getColumnDomainModelReferences().get(i - regularColumnsStartIndex);
			TableConfigurationHelper.updateWidthConfiguration(tableControl, columnDMR, swtTable, tableColumn);
		}
	}

	private void initCompositeHeight() {
		final VTTableStyleProperty styleProperty = getTableStyleProperty();
		minimumHeight = styleProperty.isSetMinimumHeight() ? Optional.of(styleProperty.getMinimumHeight())
			: Optional.<Integer> empty();
		maximumHeight = styleProperty.isSetMaximumHeight() ? Optional.of(styleProperty.getMaximumHeight())
			: Optional.<Integer> empty();
	}

	private void addRelayoutListenerIfNeeded(IObservableList list, final Composite composite) {
		if (list == null) {
			return;
		}

		// relayout is only needed if min height != max height
		if (!minimumHeight.isPresent() && !maximumHeight.isPresent()) {
			return;
		}
		if (minimumHeight.isPresent() && maximumHeight.isPresent() && minimumHeight.get() == maximumHeight.get()) {
			return;
		}

		final GridData gridData = GridData.class.cast(composite.getLayoutData());
		list.addListChangeListener(new IListChangeListener() {
			@Override
			public void handleListChange(ListChangeEvent event) {
				gridData.heightHint = getTableHeightHint();
				EMFFormsSWTLayoutUtil.adjustParentSize(composite);
			}
		});
	}

	/**
	 * Adds the table columns to the {@link TableViewerSWTBuilder}.
	 *
	 * @param tableViewerSWTBuilder the builder
	 * @param clazz the {@EClass} of the rendered object
	 * @param cp the content provider
	 *
	 */
	private void addColumns(TableViewerSWTBuilder tableViewerSWTBuilder, EClass clazz,
		ObservableListContentProvider cp) {
		InternalEObject tempInstance = null;
		if (!clazz.isAbstract() && !clazz.isInterface()) {
			tempInstance = getInstanceOf(clazz);
		}

		/* regular columns */
		for (final VDomainModelReference dmr : getColumnDomainModelReferences()) {
			try {
				if (dmr == null) {
					continue;
				}

				final IObservableValue text = getLabelText(dmr, true);
				final IObservableValue tooltip = getLabelTooltipText(dmr, true);

				final IValueProperty valueProperty = getEMFFormsDatabinding().getValueProperty(dmr,
					getViewModelContext().getDomainModel());
				final EStructuralFeature eStructuralFeature = (EStructuralFeature) valueProperty.getValueType();

				final IObservableMap observableMap = valueProperty.observeDetail(cp.getKnownElements());

				final TableControlEditingSupportAndLabelProvider labelProvider = new TableControlEditingSupportAndLabelProvider(
					tempInstance, eStructuralFeature, dmr, valueProperty, observableMap,
					getColumnDomainModelReferences().indexOf(dmr));
				final EditingSupportCreator editingSupportCreator = TableConfigurationHelper
					.isReadOnly(getVElement(), dmr) ? null : labelProvider;

				final Optional<Integer> weightConfig = TableConfigurationHelper.getColumnWeight(getVElement(), dmr);
				final Optional<Integer> widthConfig = TableConfigurationHelper.getColumnWidth(getVElement(), dmr);

				int weight;
				int minWidth;
				if (weightConfig.isPresent()) {
					minWidth = widthConfig.get();
					weight = weightConfig.get();
				} else {
					// TODO ugly: we need this temporary cell editor so early just to get size information
					final CellEditor tempCellEditor = createCellEditor(tempInstance, eStructuralFeature,
						new Table(new Shell(), SWT.NONE));
					weight = ECPCellEditor.class.isInstance(tempCellEditor)
						? ECPCellEditor.class.cast(tempCellEditor).getColumnWidthWeight() : 100;
					minWidth = ECPCellEditor.class.isInstance(tempCellEditor)
						? ECPCellEditor.class.cast(tempCellEditor).getMinWidth() : 10;
				}

				tableViewerSWTBuilder.addColumn(true, false, SWT.NONE, weight, minWidth, text, tooltip,
					labelProvider, editingSupportCreator, null);

			} catch (final DatabindingFailedException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				continue;
			}
		}

	}

	private void setupValidation(final TableViewerComposite tableViewerComposite) {
		if (tableViewerComposite.getValidationControls().isPresent()) {
			final List<Control> validationControls = tableViewerComposite.getValidationControls().get();
			if (validationControls.size() == 1 && Label.class.isInstance(validationControls.get(0))) {
				validationIcon = (Label) validationControls.get(0);
			}
		}
	}

	private void setupSorting(final ECPTableViewerComparator comparator, int regularColumnsStartIndex,
		final TableViewerComposite tableViewerComposite) {
		for (int i = regularColumnsStartIndex; i < tableViewerComposite.getTableViewer().getTable()
			.getColumns().length; i++) {
			final TableColumn tableColumn = tableViewerComposite.getTableViewer().getTable().getColumns()[i];
			tableColumn.addSelectionListener(getSelectionAdapter(tableViewerComposite.getTableViewer(), comparator,
				tableColumn, i - regularColumnsStartIndex));
		}
	}

	private IObservableValue getLabelText(VDomainModelReference dmrToCheck, boolean forColumn) {
		final EMFFormsLabelProvider labelService = getEMFFormsLabelProvider();
		if (forColumn) {
			try {
				return labelService.getDisplayName(dmrToCheck);
			} catch (final NoLabelFoundException e) {
				// FIXME Expectation?
				getReportService().report(new RenderingFailedReport(e));
				return Observables.constantObservableValue(e.getMessage(), String.class);
			}
		}
		switch (getVElement().getLabelAlignment()) {
		case NONE:
			return Observables.constantObservableValue("", String.class); //$NON-NLS-1$
		default:
			try {
				return labelService.getDisplayName(dmrToCheck, getViewModelContext().getDomainModel());
			} catch (final NoLabelFoundException e) {
				// FIXME Expectation?
				getReportService().report(new RenderingFailedReport(e));
				return Observables.constantObservableValue(e.getMessage(), String.class);
			}
		}
	}

	private IObservableValue getLabelTooltipText(VDomainModelReference dmrToCheck, boolean forColumn) {
		final EMFFormsLabelProvider labelService = getEMFFormsLabelProvider();
		try {
			if (forColumn) {
				return labelService.getDescription(dmrToCheck);
			}
		} catch (final NoLabelFoundException e) {
			// FIXME Expectation?
			getReportService().report(new RenderingFailedReport(e));
			return Observables.constantObservableValue(e.toString(), String.class);
		}
		switch (getVElement().getLabelAlignment()) {
		case NONE:
			return Observables.constantObservableValue("", String.class); //$NON-NLS-1$
		default:
			try {
				return labelService.getDescription(dmrToCheck, getViewModelContext().getDomainModel());
			} catch (final NoLabelFoundException e) {
				// FIXME Expectation?
				getReportService().report(new RenderingFailedReport(e));
				return Observables.constantObservableValue(e.toString(), String.class);
			}
		}
	}

	/**
	 * @return the {@link VDomainModelReference} which ends at the table setting
	 */
	private VDomainModelReference getDMRToMultiReference() {
		return getVElement().getDomainModelReference();
	}

	/**
	 * Allows to add additional buttons to the button bar of the table control.
	 * <p>
	 * The default implementation does not add additional buttons.
	 * </p>
	 *
	 * @param buttonComposite the composite where the buttons are added
	 * @return the total number of buttons added
	 */
	protected int addButtonsToButtonBar(Composite buttonComposite) {
		return 0;
	}

	/**
	 * Creates and returns the composite which will be the parent for the table viewer.
	 *
	 * @param composite the parent composite including the title/button bar
	 * @return the parent for the table viewer
	 */
	protected Composite createControlComposite(Composite composite) {
		final Composite controlComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(1, getTableHeightHint())
			.applyTo(controlComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(controlComposite);
		return controlComposite;
	}

	/**
	 * Returns the preferred height for the table. This will be passed to the layoutdata.
	 *
	 * @return the height in px
	 */
	protected int getTableHeightHint() {
		/* if neither min nor max is set we use a fixed height */
		if (!minimumHeight.isPresent() && !maximumHeight.isPresent()) {
			return 200;
		}

		if (minimumHeight.isPresent() && maximumHeight.isPresent() && minimumHeight.get() == maximumHeight.get()) {
			return minimumHeight.get();
		}

		final int requiredHeight = computeRequiredHeight();

		if (minimumHeight.isPresent() && !maximumHeight.isPresent()) {
			return requiredHeight < minimumHeight.get() ? minimumHeight.get() : requiredHeight;
		}

		if (!minimumHeight.isPresent() && maximumHeight.isPresent()) {
			return requiredHeight > maximumHeight.get() ? maximumHeight.get() : requiredHeight;
		}

		if (requiredHeight < minimumHeight.get()) {
			return minimumHeight.get();
		}

		if (requiredHeight > maximumHeight.get()) {
			return maximumHeight.get();
		}

		return requiredHeight;
	}

	private int computeRequiredHeight() {
		if (tableViewer == null) {
			return SWT.DEFAULT;
		}
		final Table table = tableViewer.getTable();
		if (table == null) {
			return SWT.DEFAULT;
		}
		if (table.isDisposed()) {
			return SWT.DEFAULT;
		}
		final int itemHeight = table.getItemHeight();
		// show one empty row if table does not contain any items
		final int itemCount = Math.max(table.getItemCount(), 1);
		final int headerHeight = table.getHeaderVisible() ? table.getHeaderHeight() : 0;
		// 4px needed as a buffer to avoid scrollbars
		final int tableHeight = itemHeight * itemCount + headerHeight + 4;
		return tableHeight;
	}

	/**
	 * Returns the table viewer.
	 *
	 * @return the viewer
	 */
	protected TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * Sets the table viewer.
	 *
	 * @param tableViewer the viewer
	 */
	protected void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

	// FIXME needed?
	// /**
	// * Applies the layout data to the given composite.
	// *
	// * @param composite the composite to which the layout data is applied
	// *
	// */
	// private void setLayoutData(Composite composite) {
	// GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(1, getTableHeightHint())
	// .applyTo(composite);
	// }

	/**
	 * This method gets called when the selection on the {@link TableViewer} (see {@link #getTableViewer()}) has
	 * changed.
	 * <p>
	 * If you override this method make sure to call super.
	 * </p>
	 *
	 * @param event the {@link SelectionChangedEvent}
	 */
	protected void viewerSelectionChanged(SelectionChangedEvent event) {
		if (event.getSelection().isEmpty()) {
			if (getRemoveButton() != null) {
				getRemoveButton().setEnabled(false);
			}
		} else {
			if (getRemoveButton() != null) {
				getRemoveButton().setEnabled(true);
			}
		}
	}

	private SelectionAdapter getSelectionAdapter(final TableViewer tableViewer,
		final ECPTableViewerComparator comparator, final TableColumn column,
		final int index) {
		final SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				final int dir = comparator.getDirection();
				tableViewer.getTable().setSortDirection(dir);
				tableViewer.getTable().setSortColumn(column);
				tableViewer.refresh();
			}
		};
		return selectionAdapter;
	}

	private void createFixedValidationStatusColumn(TableViewerSWTBuilder tableViewerSWTBuilder) {
		final VTTableValidationStyleProperty tableValidationStyleProperty = getTableValidationStyleProperty();
		final int columnWidth = tableValidationStyleProperty.getColumnWidth();
		final String columnName = tableValidationStyleProperty.getColumnName();
		final String imagePath = tableValidationStyleProperty.getImagePath();
		Image image = null;
		if (imagePath != null && !imagePath.isEmpty()) {
			try {
				image = getImage(new File(imagePath).toURI().toURL());
			} catch (final MalformedURLException ex) {
				getReportService().report(new AbstractReport(ex));
			}
		}

		tableViewerSWTBuilder.addColumn(
			true,
			false,
			SWT.NONE,
			0,
			columnWidth,
			columnName,
			columnName,
			new ValidationStatusCellLabelProvider(getVElement()),
			null,
			image);

	}

	/**
	 * Retrieve images from the {@link ImageRegistryService} using an {@link URL}.
	 *
	 * @param url The {@link URL} pointing to the image
	 * @return The retrieved Image
	 * @since 1.6
	 */
	protected Image getImage(URL url) {
		return imageRegistryService.getImage(url);
	}

	/**
	 * Retrieve images from the {@link ImageRegistryService} using a bundle relative path.
	 *
	 * @param path The bundle relative path pointing to the image
	 * @return The retrieved Image
	 * @since 1.6
	 */
	protected Image getImage(String path) {
		return imageRegistryService.getImage(FrameworkUtil.getBundle(TableControlSWTRenderer.class), path);
	}

	private VTTableValidationStyleProperty getTableValidationStyleProperty() {
		VTTableValidationStyleProperty tableValidationStyleProperties = getStyleProperty(
			VTTableValidationStyleProperty.class);
		if (tableValidationStyleProperties == null) {
			tableValidationStyleProperties = getDefaultTableValidationStyleProperty();
		}
		return tableValidationStyleProperties;
	}

	private VTTableValidationStyleProperty getDefaultTableValidationStyleProperty() {
		final VTTableValidationStyleProperty tableValidationProp = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();
		tableValidationProp.setColumnWidth(80);
		tableValidationProp.setColumnName(LocalizationServiceHelper.getString(TableControlSWTRenderer.class,
			MessageKeys.TableControl_ValidationStatusColumn));
		tableValidationProp.setImagePath(null);
		return tableValidationProp;
	}

	private VTBackgroundStyleProperty getBackgroundStyleProperty() {
		VTBackgroundStyleProperty styleProperty = getStyleProperty(VTBackgroundStyleProperty.class);
		if (styleProperty == null) {
			styleProperty = getDefaultBackgroundStyleProperty();
		}
		return styleProperty;
	}

	private VTBackgroundStyleProperty getDefaultBackgroundStyleProperty() {
		return VTBackgroundFactory.eINSTANCE.createBackgroundStyleProperty();
	}

	private VTTableStyleProperty getTableStyleProperty() {
		VTTableStyleProperty styleProperty = getStyleProperty(VTTableStyleProperty.class);
		if (styleProperty == null) {
			styleProperty = getDefaultTableStylProperty();
		}
		return styleProperty;
	}

	private VTTableStyleProperty getDefaultTableStylProperty() {
		final VTTableStyleProperty tableStyleProperty = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();
		tableStyleProperty.setMaximumHeight(200);
		if (!getVElement().isReadonly()) {
			tableStyleProperty.setMinimumHeight(200);
		}
		return tableStyleProperty;
	}

	private VTFontPropertiesStyleProperty getFontPropertiesStyleProperty() {
		VTFontPropertiesStyleProperty styleProperty = getStyleProperty(VTFontPropertiesStyleProperty.class);
		if (styleProperty == null) {
			styleProperty = getDefaultFontPropertiesStyleProperty();
		}
		return styleProperty;
	}

	private VTFontPropertiesStyleProperty getDefaultFontPropertiesStyleProperty() {
		final VTFontPropertiesStyleProperty property = VTFontPropertiesFactory.eINSTANCE
			.createFontPropertiesStyleProperty();
		property.setColorHEX("000000"); //$NON-NLS-1$
		return property;
	}

	/**
	 * Returns a {@link VTStyleProperty} of the given class or <code>null</code> if none was found.
	 *
	 * @param stylePropertyClass the style property class
	 * @return the property or <code>null</code>
	 */
	private <SP extends VTStyleProperty> SP getStyleProperty(Class<SP> stylePropertyClass) {
		final Set<VTStyleProperty> styleProperties = getVTViewTemplateProvider()
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (stylePropertyClass.isInstance(styleProperty)) {
				return stylePropertyClass.cast(styleProperty);
			}
		}
		return null;
	}

	private Color getSWTColor(String colorHex) {
		final String redString = colorHex.substring(0, 2);
		final String greenString = colorHex.substring(2, 4);
		final String blueString = colorHex.substring(4, 6);
		final int red = Integer.parseInt(redString, 16);
		final int green = Integer.parseInt(greenString, 16);
		final int blue = Integer.parseInt(blueString, 16);
		return new Color(Display.getDefault(), red, green, blue);
	}

	/**
	 * @return The multi segment of the {@link VDomainModelReference} of this renderer's {@link VControl}.
	 */
	private VMultiDomainModelReferenceSegment getMultiSegment() {
		final EList<VDomainModelReferenceSegment> segments = getVElement().getDomainModelReference().getSegments();
		// The multi segment is the dmr's last segment
		return (VMultiDomainModelReferenceSegment) segments.get(segments.size() - 1);
	}

	/**
	 * @return The domain model references defining the columns of this table.
	 */
	private EList<VDomainModelReference> getColumnDomainModelReferences() {
		return getMultiSegment().getChildDomainModelReferences();
	}

	/**
	 * This is called in order to setup the editing support for a table column.
	 *
	 * @param tempInstance the temporary input instance of the table
	 * @param feature the feature of the column
	 * @param table the table/parent
	 * @return the cell editor
	 * @since 1.8
	 */
	protected CellEditor createCellEditor(final EObject tempInstance, final EStructuralFeature feature, Table table) {
		return CellEditorFactory.INSTANCE.getCellEditor(feature,
			tempInstance, table, getViewModelContext());
	}

	private InternalEObject getInstanceOf(EClass clazz) {
		return InternalEObject.class.cast(clazz.getEPackage().getEFactoryInstance().create(clazz));
	}

	private Button createRemoveRowButton(EClass clazz, final Composite buttonComposite, final EObject eObject,
		final EStructuralFeature structuralFeature) {
		removeButton = new Button(buttonComposite, SWT.None);
		final Image image = getImage(ICON_DELETE);
		removeButton.setImage(image);
		removeButton.setEnabled(false);
		final String instanceName = clazz.getInstanceClass() == null ? "" : clazz.getInstanceClass().getSimpleName(); //$NON-NLS-1$
		removeButton.setToolTipText(String.format(
			LocalizationServiceHelper.getString(TableControlSWTRenderer.class, MessageKeys.TableControl_RemoveSelected),
			instanceName));

		final List<?> containments = (List<?>) eObject.eGet(structuralFeature, true);
		if (containments.size() <= structuralFeature.getLowerBound()) {
			removeButton.setEnabled(false);
		}
		SWTDataElementIdHelper.setElementIdDataWithSubId(removeButton, getVElement(), "table_remove", //$NON-NLS-1$
			getViewModelContext());
		return removeButton;
	}

	private Button createAddRowButton(final EClass clazz, final Composite buttonComposite, final EObject eObject,
		final EStructuralFeature structuralFeature) {
		addButton = new Button(buttonComposite, SWT.None);
		final Image image = getImage(ICON_ADD);
		addButton.setImage(image);
		final String instanceName = clazz.getInstanceClass() == null ? "" : clazz.getInstanceClass().getSimpleName(); //$NON-NLS-1$
		addButton.setToolTipText(String.format(
			LocalizationServiceHelper.getString(TableControlSWTRenderer.class, MessageKeys.TableControl_AddInstanceOf),
			instanceName));

		final List<?> containments = (List<?>) eObject.eGet(structuralFeature, true);
		if (structuralFeature.getUpperBound() != -1 && containments.size() >= structuralFeature.getUpperBound()) {
			addButton.setEnabled(false);
		}
		SWTDataElementIdHelper.setElementIdDataWithSubId(addButton, getVElement(), "table_add", getViewModelContext()); //$NON-NLS-1$
		return addButton;
	}

	/**
	 * This method shows a user confirmation dialog when the user attempts to delete a row in the table.
	 *
	 * @param deletionList the list of selected EObjects to delete
	 * @param eObject The containment reference {@link EObject}
	 * @param structuralFeature The containment reference {@link EStructuralFeature}
	 * @param addButton the add button
	 * @param removeButton the remove button
	 * @since 1.6
	 */
	protected void deleteRowUserConfirmDialog(final List<EObject> deletionList, final EObject eObject,
		final EStructuralFeature structuralFeature, final Button addButton, final Button removeButton) {
		final MessageDialog dialog = new MessageDialog(addButton.getShell(),
			LocalizationServiceHelper.getString(TableControlSWTRenderer.class, MessageKeys.TableControl_Delete), null,
			LocalizationServiceHelper.getString(TableControlSWTRenderer.class,
				MessageKeys.TableControl_DeleteAreYouSure),
			MessageDialog.CONFIRM, new String[] {
				JFaceResources.getString(IDialogLabelKeys.YES_LABEL_KEY),
				JFaceResources.getString(IDialogLabelKeys.NO_LABEL_KEY) },
			0);

		new ECPDialogExecutor(dialog) {

			@Override
			public void handleResult(int codeResult) {
				if (codeResult == IDialogConstants.CANCEL_ID
					|| codeResult == SWT.DEFAULT) { // SWT.DEFAULT is return by closing a message dialog
					return;
				}

				deleteRows(deletionList, eObject, structuralFeature);

				final List<?> containments = (List<?>) eObject.eGet(structuralFeature, true);
				if (containments.size() < structuralFeature.getUpperBound()) {
					addButton.setEnabled(true);
				}
				if (containments.size() <= structuralFeature.getLowerBound()) {
					removeButton.setEnabled(false);
				}
			}
		}.execute();
	}

	/**
	 * This is called by {@link #deleteRowUserConfirmDialog(List)} after the user confirmed to delete the selected
	 * elements.
	 *
	 * @param deletionList the list of {@link EObject EObjects} to delete
	 * @param eObject The containment reference {@link EObject}
	 * @param structuralFeature The containment reference {@link EStructuralFeature}
	 * @since 1.6
	 */
	protected void deleteRows(List<EObject> deletionList, final EObject eObject,
		final EStructuralFeature structuralFeature) {

		final EditingDomain editingDomain = getEditingDomain(eObject);

		/* assured by #isApplicable */
		final EReference reference = EReference.class.cast(structuralFeature);
		final List<Object> toDelete = new ArrayList<Object>(deletionList);
		if (reference.isContainment()) {
			DeleteService deleteService = getViewModelContext().getService(DeleteService.class);
			if (deleteService == null) {
				/*
				 * #getService(Class<?>) will report to the reportservice if it could not be found
				 * Use Default
				 */
				deleteService = new EMFDeleteServiceImpl();
			}
			deleteService.deleteElements(toDelete);
		} else {
			removeElements(editingDomain, eObject, reference, toDelete);
		}
	}

	private void removeElements(EditingDomain editingDomain, Object source, EStructuralFeature feature,
		Collection<Object> toRemove) {
		final Command removeCommand = RemoveCommand.create(editingDomain, source, feature, toRemove);
		if (removeCommand.canExecute()) {
			if (editingDomain.getCommandStack() == null) {
				removeCommand.execute();
			} else {
				editingDomain.getCommandStack().execute(removeCommand);
			}
		}
	}

	/**
	 * This method is called to add a new entry in the domain model and thus to create a new row in the table. The
	 * element to create is defined by the provided class.
	 * You can override this method but you have to call super nonetheless.
	 *
	 * @param clazz the {@link EClass} defining the EObject to create
	 * @param eObject The containment reference {@link EObject}
	 * @param structuralFeature The containment reference {@link EStructuralFeature}
	 * @since 1.6
	 */
	protected void addRow(EClass clazz, EObject eObject, EStructuralFeature structuralFeature) {
		final TableControlService tableService = getViewModelContext()
			.getService(TableControlService.class);
		Optional<EObject> eObjectToAdd;

		/* no table service available, fall back to default */
		if (tableService == null) {
			if (clazz.isAbstract() || clazz.isInterface()) {
				getReportService().report(new StatusReport(
					new Status(IStatus.WARNING, "org.eclipse.emf.ecp.view.table.ui.swt", //$NON-NLS-1$
						String.format("The class %1$s is abstract or an interface.", clazz.getName())))); //$NON-NLS-1$
				eObjectToAdd = Optional.empty();
			} else {
				eObjectToAdd = Optional.of(clazz.getEPackage().getEFactoryInstance().create(clazz));
			}
		}
		/* table service available */
		else {
			eObjectToAdd = tableService.createNewElement(clazz, eObject, structuralFeature);
		}

		if (!eObjectToAdd.isPresent()) {
			return;
		}

		final EObject instance = eObjectToAdd.get();
		final EditingDomain editingDomain = getEditingDomain(eObject);
		if (editingDomain == null) {
		}
		editingDomain.getCommandStack().execute(
			AddCommand.create(editingDomain, eObject, structuralFeature, instance));
		tableViewer.setSelection(new StructuredSelection(eObjectToAdd.get()), true);
	}

	@Override
	protected void applyValidation() {
		Display.getDefault().asyncExec(new ApplyValidationRunnable());

	}

	/**
	 * Returns the add button created by the framework.
	 *
	 * @return the addButton
	 * @since 1.6
	 */
	protected Button getAddButton() {
		return addButton;
	}

	/**
	 * Returns the remove button created by the framework.
	 *
	 * @return the removeButton
	 * @since 1.6
	 */
	protected Button getRemoveButton() {
		return removeButton;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#applyEnable()
	 */
	@Override
	protected void applyEnable() {
		if (getAddButton() != null) {
			getAddButton().setVisible(getVElement().isEnabled() && !getVElement().isReadonly());
		}
		if (getRemoveButton() != null) {
			getRemoveButton().setVisible(getVElement().isEnabled() && !getVElement().isReadonly());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#applyReadOnly()
	 */
	@Override
	protected void applyReadOnly() {
		if (getAddButton() != null) {
			getAddButton().setVisible(getVElement().isEnabled() && !getVElement().isReadonly());
		}
		if (getRemoveButton() != null) {
			getRemoveButton().setVisible(getVElement().isEnabled() && !getVElement().isReadonly());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		viewModelDBC.dispose();
		super.dispose();
	}

	/**
	 * Get called by the {@link ECPTableViewerComparator} in order to compare the given objects.
	 *
	 * @param viewer the tavle viewer
	 * @param e1 the first object of the comparison
	 * @param e2 the second object of the comparison
	 * @param propertyIndex index of the selection column. the index is aligned with the index of the associated column
	 *            domain model reference
	 * @param direction {@link SWT#NONE}, {@link SWT#UP} or {@link SWT#DOWN} according to the indication displayed at
	 *            the table column.
	 * @return a negative number if the first element is less than the
	 *         second element; the value <code>0</code> if the first element is
	 *         equal to the second element; and a positive number if the first
	 *         element is greater than the second element
	 * @since 1.8
	 */
	protected int compare(Viewer viewer, Object e1, Object e2, int direction, int propertyIndex) {
		if (direction == 0) {
			return 0;
		}
		int rc = 0;
		final EObject object1 = (EObject) e1;
		final EObject object2 = (EObject) e2;

		Object value1;
		Object value2;

		final VDomainModelReference dmr = getColumnDomainModelReferences().get(propertyIndex);
		final EMFFormsDatabinding emfFormsDatabinding = getEMFFormsDatabinding();

		try {
			final IObservableValue observableValue1 = emfFormsDatabinding.getObservableValue(dmr, object1);
			final EStructuralFeature structuralFeature1 = (EStructuralFeature) observableValue1.getValueType();
			final EObject observed1 = (EObject) ((IObserving) observableValue1).getObserved();
			value1 = observed1.eGet(structuralFeature1, true);
			observableValue1.dispose();
		} catch (final DatabindingFailedException ex) {
			value1 = null;
		}

		try {
			final IObservableValue observableValue2 = emfFormsDatabinding.getObservableValue(dmr, object2);
			final EStructuralFeature structuralFeature2 = (EStructuralFeature) observableValue2.getValueType();
			final EObject observed2 = (EObject) ((IObserving) observableValue2).getObserved();
			value2 = observed2.eGet(structuralFeature2, true);
			observableValue2.dispose();
		} catch (final DatabindingFailedException ex) {
			value2 = null;
		}

		if (columnIndexToComparatorMap.containsKey(propertyIndex)) {
			return columnIndexToComparatorMap.get(propertyIndex).compare(value1, value2, direction);
		}

		if (value1 == null) {
			rc = 1;
		} else if (value2 == null) {
			rc = -1;
		} else {
			rc = value1.toString().compareTo(value2.toString());
		}
		// If descending order, flip the direction
		if (direction == 2) {
			rc = -rc;
		}
		return rc;
	}

	/**
	 * Double click listener.
	 *
	 */
	private final class DoubleClickListener implements IDoubleClickListener {
		@Override
		public void doubleClick(DoubleClickEvent event) {
			final TableControlService tableService = getViewModelContext()
				.getService(TableControlService.class);
			if (tableService == null) {
				return;
			}
			final ISelection selection = event.getSelection();
			if (!StructuredSelection.class.isInstance(selection)) {
				return;
			}
			tableService.doubleClick(getVElement(),
				(EObject) StructuredSelection.class.cast(selection).getFirstElement());
		}
	}

	/**
	 * The Listener which is set on the table viewer to inform the renderer about selection changes.
	 *
	 */
	private final class ViewerSelectionChangedListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			viewerSelectionChanged(event);
		}
	}

	/**
	 * Runnable which is called by {@link TableControlSWTRenderer#applyValidation() applyValidation}.
	 *
	 */
	private final class ApplyValidationRunnable implements Runnable {
		@Override
		public void run() {
			// triggered due to another validation rule before this control is rendered
			if (validationIcon == null) {
				return;
			}
			// validation rule triggered after the control was disposed
			if (validationIcon.isDisposed()) {
				return;
			}
			// no diagnostic set
			if (getVElement().getDiagnostic() == null) {
				return;
			}

			IObservableValue observableValue;
			try {
				observableValue = getEMFFormsDatabinding().getObservableValue(getVElement().getDomainModelReference(),
					getViewModelContext().getDomainModel());
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
				return;
			}
			final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
			final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
			observableValue.dispose();

			validationIcon.setImage(getValidationIcon(getVElement().getDiagnostic().getHighestSeverity()));

			validationIcon.setToolTipText(ECPTooltipModifierHelper.modifyString(getVElement().getDiagnostic()
				.getMessage(), null));
			final Collection<?> collection = (Collection<?>) eObject.eGet(structuralFeature, true);
			if (!collection.isEmpty()) {
				for (final Object object : collection) {
					tableViewer.update(object, null);
				}
			}
		}
	}

	/**
	 * Implements {@link EditingSupportCreator} and {@link CellLabelProviderFactory} for the table control swt renderer.
	 *
	 * This allows us to access the actual cell editor from the cell label provider.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class TableControlEditingSupportAndLabelProvider
		implements EditingSupportCreator, CellLabelProviderFactory {
		private final InternalEObject tempInstance;
		private final EStructuralFeature eStructuralFeature;
		private final VDomainModelReference dmr;
		private final IValueProperty valueProperty;
		private final IObservableMap observableMap;

		private CellEditor cellEditor;
		private ECPTableEditingSupport observableSupport;

		private boolean initialized;
		private final int indexOfColumn;

		private TableControlEditingSupportAndLabelProvider(InternalEObject tempInstance,
			EStructuralFeature eStructuralFeature, VDomainModelReference dmr,
			IValueProperty valueProperty, IObservableMap observableMap, int indexOfColumn) {
			this.tempInstance = tempInstance;
			this.eStructuralFeature = eStructuralFeature;
			this.dmr = dmr;
			this.valueProperty = valueProperty;
			this.observableMap = observableMap;
			this.indexOfColumn = indexOfColumn;
		}

		@Override
		public EditingSupport createEditingSupport(TableViewer tableViewer) {
			if (!initialized) {
				init(tableViewer);
			}
			return observableSupport;
		}

		private void init(TableViewer tableViewer) {
			cellEditor = createCellEditor(tempInstance, eStructuralFeature,
				tableViewer.getTable());
			if (ECPCellEditorComparator.class.isInstance(cellEditor)) {
				columnIndexToComparatorMap.put(indexOfColumn, ECPCellEditorComparator.class.cast(cellEditor));
			}
			observableSupport = new ECPTableEditingSupport(tableViewer, cellEditor,
				getVElement(), dmr, valueProperty);
			initialized = true;
		}

		@Override
		public CellLabelProvider createCellLabelProvider(TableViewer table) {
			if (!initialized) {
				init(table);
			}
			return new ECPCellLabelProvider(eStructuralFeature, cellEditor, observableMap,
				getVElement(), dmr, table.getTable());
		}
	}

	/**
	 * {@link TableViewerCreator} for the table control swt renderer. It will create a TableViewer with the expected
	 * custom variant data and the correct style properties as defined in the template model.
	 *
	 */
	private final class TableControlSWTRendererTableViewerCreator implements TableViewerCreator {

		@Override
		public TableViewer createTableViewer(Composite parent) {
			tableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
			tableViewer.getTable().setData(CUSTOM_VARIANT, TABLE_CUSTOM_VARIANT);
			tableViewer.getTable().setHeaderVisible(true);
			tableViewer.getTable().setLinesVisible(true);

			/* Set background color */
			final VTBackgroundStyleProperty backgroundStyleProperty = getBackgroundStyleProperty();
			if (backgroundStyleProperty.getColor() != null) {
				tableViewer.getTable().setBackground(getSWTColor(backgroundStyleProperty.getColor()));
			}

			/* Set foreground color */
			final VTFontPropertiesStyleProperty fontPropertiesStyleProperty = getFontPropertiesStyleProperty();
			if (fontPropertiesStyleProperty.getColorHEX() != null) {
				tableViewer.getTable()
					.setForeground(getSWTColor(fontPropertiesStyleProperty.getColorHEX()));
			}

			tableViewer.getTable().setData(FIXED_COLUMNS, new Integer(1));
			return tableViewer;
		}
	}

	/**
	 * {@link ButtonBarBuilder} for the table control swt renderer. It will call the existing template methods which
	 * allows subclasses to change the buttons.
	 *
	 */
	private final class TableControlSWTRendererButtonBarBuilder implements ButtonBarBuilder {
		private EStructuralFeature structuralFeature;
		private EClass clazz;
		private EObject eObject;

		private TableControlSWTRendererButtonBarBuilder() throws DatabindingFailedException {
			setValues();
		}

		public void updateValues() throws DatabindingFailedException {
			setValues();
		}

		private void setValues() throws DatabindingFailedException {
			final Setting setting = getEMFFormsDatabinding().getSetting(getDMRToMultiReference(),
				getViewModelContext().getDomainModel());
			eObject = setting.getEObject();
			structuralFeature = setting.getEStructuralFeature();
			clazz = ((EReference) structuralFeature).getEReferenceType();
		}

		@Override
		public void fillButtonComposite(Composite buttonComposite, TableViewer viewer) {
			int numButtons = addButtonsToButtonBar(buttonComposite);
			if (!getVElement().isAddRemoveDisabled()) {
				addButton = createAddRowButton(
					clazz, buttonComposite, eObject, structuralFeature);
				removeButton = createRemoveRowButton(
					clazz, buttonComposite, eObject, structuralFeature);

				numButtons = numButtons + 2;

				initButtons(addButton, removeButton, viewer);
			}
			GridLayoutFactory.fillDefaults().numColumns(numButtons).equalWidth(false)
				.applyTo(buttonComposite);
		}

		private void initButtons(final Button addButton, final Button removeButton, final TableViewer viewer) {
			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					addRow(clazz, eObject, structuralFeature);

					final List<?> containments = (List<?>) eObject.eGet(structuralFeature, true);
					if (structuralFeature.getUpperBound() != -1
						&& containments.size() >= structuralFeature.getUpperBound()) {
						addButton.setEnabled(false);
					}
					if (containments.size() > structuralFeature.getLowerBound()) {
						addButton.setEnabled(true);
					}
				}
			});
			removeButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					final IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

					if (selection == null || selection.getFirstElement() == null) {
						return;
					}

					final List<EObject> deletionList = new ArrayList<EObject>();
					final Iterator<?> iterator = selection.iterator();

					while (iterator.hasNext()) {
						deletionList.add((EObject) iterator.next());
					}

					deleteRowUserConfirmDialog(deletionList, eObject,
						structuralFeature, addButton, removeButton);
				}
			});
		}

		@Override
		public Object createNewElement(Button button) {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * {@link org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder TableViewerCompositeBuilder} which calls
	 * the existing template method to create the validation label.
	 *
	 */
	@SuppressWarnings("restriction")
	private final class TableControlSWTRendererCompositeBuilder
		extends org.eclipse.emfforms.internal.swt.table.DefaultTableViewerCompositeBuilder {
		@Override
		protected Label createValidationLabel(Composite topComposite) {
			final Label validationLabel = createValidationIcon(topComposite);
			GridDataFactory.fillDefaults().hint(16, 17).grab(false, false).applyTo(validationLabel);
			return validationLabel;
		}

		@Override
		protected Composite createViewerComposite(Composite composite) {
			return createControlComposite(composite);
		}
	}

	/**
	 * The {@link ViewerComparator} for this table which allows 3 states for sort order:
	 * none, up and down.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private class ECPTableViewerComparator extends ViewerComparator {
		private int propertyIndex;
		private static final int NONE = 0;
		private int direction = NONE;

		ECPTableViewerComparator() {
			propertyIndex = 0;
			direction = NONE;
		}

		public int getDirection() {
			switch (direction) {
			case 0:
				return SWT.NONE;
			case 1:
				return SWT.UP;
			case 2:
				return SWT.DOWN;
			default:
				return SWT.NONE;
			}

		}

		public void setColumn(int column) {
			if (column == propertyIndex) {
				// Same column as last sort; toggle the direction
				direction = (direction + 1) % 3;
			} else {
				// New column; do an ascending sort
				propertyIndex = column;
				direction = 1;
			}
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			return TableControlSWTRenderer.this.compare(viewer, e1, e2, direction, propertyIndex);
		}
	}

	/**
	 * ECP specficic cell label provider that does also implement {@link IColorProvider} in
	 * order to correctly.
	 *
	 * @author emueller
	 *
	 */
	public class ECPCellLabelProvider extends ObservableMapCellLabelProvider implements IColorProvider {

		private final EStructuralFeature feature;
		private final CellEditor cellEditor;
		private final VTableControl vTableControl;
		private final VDomainModelReference dmr;
		private final Table table;

		/**
		 * Constructor.
		 *
		 * @param feature
		 *            the {@link EStructuralFeature} the cell is bound to
		 * @param cellEditor
		 *            the {@link CellEditor} instance
		 * @param attributeMap
		 *            an {@link IObservableMap} instance that is passed to the {@link ObservableMapCellLabelProvider}
		 * @param vTableControl the {@link VTableControl}
		 * @param dmr the {@link VDomainModelReference} for this cell
		 * @param table the swt table
		 * @since 1.6
		 */
		public ECPCellLabelProvider(EStructuralFeature feature, CellEditor cellEditor, IObservableMap attributeMap,
			VTableControl vTableControl, VDomainModelReference dmr, Table table) {
			super(attributeMap);
			this.vTableControl = vTableControl;
			this.feature = feature;
			this.cellEditor = cellEditor;
			this.dmr = dmr;
			this.table = table;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
		 */
		@Override
		public String getToolTipText(Object element) {
			final EObject domainObject = (EObject) element;
			IObservableValue observableValue;
			try {
				observableValue = getEMFFormsDatabinding()
					.getObservableValue(dmr, domainObject);
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
				return null;
			}
			final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
			final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
			final Setting setting = ((InternalEObject) eObject).eSetting(structuralFeature);
			observableValue.dispose();

			final VDiagnostic vDiagnostic = vTableControl.getDiagnostic();
			if (vDiagnostic != null) {
				final String message = DiagnosticMessageExtractor.getMessage(vDiagnostic.getDiagnostic(domainObject,
					feature));
				if (message != null && !message.isEmpty()) {
					return ECPTooltipModifierHelper.modifyString(message, setting);
				}
			}
			final Object value = eObject.eGet(structuralFeature, true);
			if (value == null) {
				return null;
			}
			return ECPTooltipModifierHelper.modifyString(String.valueOf(value), setting);
		}

		@Override
		public void update(ViewerCell cell) {
			final EObject element = (EObject) cell.getElement();
			final Object value = attributeMaps[0].get(element);

			if (ECPCellEditor.class.isInstance(cellEditor)) {
				final ECPCellEditor ecpCellEditor = (ECPCellEditor) cellEditor;
				final String text = ecpCellEditor.getFormatedString(value);
				cell.setText(text == null ? "" : text); //$NON-NLS-1$
				cell.setImage(ecpCellEditor.getImage(value));
			} else {
				cell.setText(value == null ? "" : value.toString()); //$NON-NLS-1$
				cell.getControl().setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_edit_cellEditor_string"); //$NON-NLS-1$
			}

			cell.setForeground(getForeground(element));
			cell.setBackground(getBackground(element));
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
		 */
		@Override
		public Color getForeground(Object element) {
			return table.getForeground();
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
		 */
		@Override
		public Color getBackground(Object element) {
			final VDiagnostic vDiagnostic = vTableControl.getDiagnostic();
			if (vDiagnostic == null) {
				return getValidationBackgroundColor(Diagnostic.OK);
			}
			final List<Diagnostic> diagnostic = vDiagnostic.getDiagnostic((EObject) element, feature);
			return getValidationBackgroundColor(diagnostic.size() == 0 ? Diagnostic.OK : diagnostic.get(0)
				.getSeverity());
		}
	}

	/**
	 * Implementation of the {@link EditingSupport} for the generic ECP Table.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private class ECPTableEditingSupport extends EditingSupport {

		private final CellEditor cellEditor;

		private final VTableControl tableControl;

		private final IValueProperty valueProperty;

		private final VDomainModelReference domainModelReference;

		/**
		 * @param viewer
		 */
		ECPTableEditingSupport(ColumnViewer viewer, CellEditor cellEditor,
			VTableControl tableControl, VDomainModelReference domainModelReference, IValueProperty valueProperty) {
			super(viewer);
			this.cellEditor = cellEditor;
			this.tableControl = tableControl;
			this.valueProperty = valueProperty;
			this.domainModelReference = domainModelReference;
		}

		private EditingState editingState;

		private final ColumnViewerEditorActivationListenerHelper activationListener = new ColumnViewerEditorActivationListenerHelper();

		/**
		 * Default implementation always returns <code>true</code>.
		 *
		 * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
		 */
		@Override
		protected boolean canEdit(Object element) {

			boolean editable = tableControl.isEnabled()
				&& !tableControl.isReadonly();

			final IObservableValue observableValue = valueProperty.observe(element);
			final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
			final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
			final Setting setting = ((InternalEObject) eObject).eSetting(structuralFeature);

			editable &= emfFormsEditSupport.canSetProperty(domainModelReference, (EObject) element);
			editable &= !CellReadOnlyTesterHelper.getInstance().isReadOnly(getVElement(), setting);

			if (ECPCellEditor.class.isInstance(cellEditor)) {
				ECPCellEditor.class.cast(cellEditor).setEditable(editable);
				return true;
			}
			return editable;
		}

		/**
		 * Default implementation always returns <code>null</code> as this will be
		 * handled by the Binding.
		 *
		 * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
		 */
		@Override
		protected Object getValue(Object element) {
			// no op
			return null;
		}

		/**
		 * Default implementation does nothing as this will be handled by the
		 * Binding.
		 *
		 * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
		 */
		@Override
		protected void setValue(Object element, Object value) {
			// no op
		}

		/**
		 * Creates a {@link Binding} between the editor and the element to be
		 * edited. Invokes {@link #doCreateCellEditorObservable(CellEditor)},
		 * {@link #doCreateElementObservable(Object, ViewerCell)}, and then
		 * {@link #createBinding(IObservableValue, IObservableValue)}.
		 */
		@Override
		protected void initializeCellEditorValue(CellEditor cellEditor, ViewerCell cell) {

			if (ECPElementAwareCellEditor.class.isInstance(cellEditor)) {
				ECPElementAwareCellEditor.class.cast(cellEditor).updateRowElement(cell.getElement());
			}

			final IObservableValue target = doCreateCellEditorObservable(cellEditor);
			Assert.isNotNull(target, "doCreateCellEditorObservable(...) did not return an observable"); //$NON-NLS-1$

			final IObservableValue model = valueProperty.observe(cell.getElement());
			Assert.isNotNull(model, "The databinding service did not return an observable"); //$NON-NLS-1$

			final Binding binding = createBinding(target, model);

			Assert.isNotNull(binding, "createBinding(...) did not return a binding"); //$NON-NLS-1$

			editingState = new EditingState(binding, target, model);

			getViewer().getColumnViewerEditor().addEditorActivationListener(activationListener);
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return cellEditor;
		}

		protected Binding createBinding(IObservableValue target, IObservableValue model) {
			if (ECPCellEditor.class.isInstance(cellEditor)) {
				return getDataBindingContext().bindValue(target, model,
					((ECPCellEditor) cellEditor).getTargetToModelStrategy(getDataBindingContext()),
					((ECPCellEditor) cellEditor).getModelToTargetStrategy(getDataBindingContext()));
			}
			return getDataBindingContext().bindValue(target, model);
		}

		protected IObservableValue doCreateCellEditorObservable(CellEditor cellEditor) {
			if (ECPCellEditor.class.isInstance(cellEditor)) {
				return ((ECPCellEditor) cellEditor).getValueProperty().observe(cellEditor);
			}
			return WidgetProperties.text(SWT.FocusOut).observe(cellEditor.getControl());
		}

		@Override
		protected final void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
			editingState.binding.updateTargetToModel();
		}

		/**
		 * A ColumnViewerEditorActivationListener to reset the cells after focus lost.
		 *
		 * @author Eugen Neufeld
		 *
		 */
		private class ColumnViewerEditorActivationListenerHelper extends ColumnViewerEditorActivationListener {

			@Override
			public void afterEditorActivated(ColumnViewerEditorActivationEvent event) {
				// set colors for cell editor
				final Control control = cellEditor.getControl();
				if (control == null || control.isDisposed()) {
					return;
				}
				control.setBackground(getViewer().getControl().getBackground());
				control.setForeground(getViewer().getControl().getForeground());
			}

			@Override
			public void afterEditorDeactivated(ColumnViewerEditorDeactivationEvent event) {
				editingState.dispose();
				editingState = null;

				getViewer().getColumnViewerEditor().removeEditorActivationListener(this);
				final ViewerCell focusCell = getViewer().getColumnViewerEditor().getFocusCell();
				if (focusCell != null) {
					getViewer().update(focusCell.getElement(), null);
				}
			}

			@Override
			public void beforeEditorActivated(ColumnViewerEditorActivationEvent event) {
				// do nothing
			}

			@Override
			public void beforeEditorDeactivated(ColumnViewerEditorDeactivationEvent event) {
				// do nothing
			}
		}

		/**
		 * Maintains references to objects that only live for the length of the edit
		 * cycle.
		 */
		class EditingState {
			private final IObservableValue target;

			private final IObservableValue model;

			private final Binding binding;

			EditingState(Binding binding, IObservableValue target, IObservableValue model) {
				this.binding = binding;
				this.target = target;
				this.model = model;
			}

			void dispose() {
				binding.dispose();
				target.dispose();
				model.dispose();
			}
		}
	}

	/**
	 * The {@link CellLabelProvider} to update the validation status on the cells.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private class ValidationStatusCellLabelProvider extends CellLabelProvider {
		private final VTableControl vTableControl;

		ValidationStatusCellLabelProvider(
			VTableControl vTableControl) {
			this.vTableControl = vTableControl;
		}

		@Override
		public void update(ViewerCell cell) {
			Integer mostSevere = Diagnostic.OK;
			final VDiagnostic vDiagnostic = vTableControl.getDiagnostic();
			if (vDiagnostic == null) {
				return;
			}
			final List<Diagnostic> diagnostics = vDiagnostic.getDiagnostics((EObject) cell.getElement());
			if (diagnostics.size() != 0) {
				mostSevere = diagnostics.get(0).getSeverity();
			}
			cell.setImage(getValidationIcon(mostSevere));
		}

		@Override
		public String getToolTipText(Object element) {
			final VDiagnostic vDiagnostic = vTableControl.getDiagnostic();
			if (vDiagnostic == null) {
				return null;
			}
			final String message = DiagnosticMessageExtractor.getMessage(vDiagnostic.getDiagnostics((EObject) element));
			return ECPTooltipModifierHelper.modifyString(message, null);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#rootDomainModelChanged()
	 */
	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {
		// TODO rebind tooltip and text?

		final IObservableList oldList = (IObservableList) getTableViewer().getInput();
		oldList.dispose();

		final IObservableList list = getEMFFormsDatabinding().getObservableList(getDMRToMultiReference(),
			getViewModelContext().getDomainModel());
		// addRelayoutListenerIfNeeded(list, composite);
		getTableViewer().setInput(list);

		tableControlSWTRendererButtonBarBuilder.updateValues();
	}
}
