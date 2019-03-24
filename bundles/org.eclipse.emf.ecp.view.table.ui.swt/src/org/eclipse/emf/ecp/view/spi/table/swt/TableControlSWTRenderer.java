/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Johannes Faltermeier - refactorings
 * Christian W. Damus - bugs 544116, 544537
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import static java.util.stream.Collectors.toCollection;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorComparator;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCustomUpdateCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPElementAwareCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPViewerAwareCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.internal.table.swt.Activator;
import org.eclipse.emf.ecp.view.internal.table.swt.CellReadOnlyTesterHelper;
import org.eclipse.emf.ecp.view.internal.table.swt.MessageKeys;
import org.eclipse.emf.ecp.view.internal.table.swt.RunnableManager;
import org.eclipse.emf.ecp.view.internal.table.swt.TableConfigurationHelper;
import org.eclipse.emf.ecp.view.model.common.util.RendererUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRendererUtil;
import org.eclipse.emf.ecp.view.spi.model.DiagnosticMessageExtractor;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.spi.table.model.VEnablementConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.swt.action.AddRowAction;
import org.eclipse.emf.ecp.view.spi.table.swt.action.DuplicateRowAction;
import org.eclipse.emf.ecp.view.spi.table.swt.action.MoveRowDownAction;
import org.eclipse.emf.ecp.view.spi.table.swt.action.MoveRowUpAction;
import org.eclipse.emf.ecp.view.spi.table.swt.action.RemoveRowAction;
import org.eclipse.emf.ecp.view.spi.table.swt.action.TableActionIconButton;
import org.eclipse.emf.ecp.view.spi.table.swt.action.TableRendererActionBar;
import org.eclipse.emf.ecp.view.spi.table.swt.action.TableRendererViewerActionContext;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundFactory;
import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundStyleProperty;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesFactory;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBindings;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.sort.NumberAwareStringComparator;
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
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.CellLabelProviderFactory;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.emfforms.spi.swt.table.ColumnConfigurationBuilder;
import org.eclipse.emfforms.spi.swt.table.DNDProvider;
import org.eclipse.emfforms.spi.swt.table.EditingSupportCreator;
import org.eclipse.emfforms.spi.swt.table.TableConfiguration;
import org.eclipse.emfforms.spi.swt.table.TableConfigurationBuilder;
import org.eclipse.emfforms.spi.swt.table.TableControl;
import org.eclipse.emfforms.spi.swt.table.TableViewerComparator;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerCreator;
import org.eclipse.emfforms.spi.swt.table.TableViewerFactory;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization;
import org.eclipse.emfforms.spi.swt.table.action.ActionBar;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfiguration;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfigurationBuilder;
import org.eclipse.emfforms.spi.swt.table.action.TableActionBar;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;
import org.eclipse.emfforms.view.spi.multisegment.model.MultiSegmentUtil;
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
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;
import org.osgi.framework.FrameworkUtil;

/**
 * SWT Renderer for Table Control.
 *
 * @author Eugen Neufeld
 * @author Johannes Faltermeier
 *
 */
public class TableControlSWTRenderer extends AbstractControlSWTRenderer<VTableControl> {

	/**
	 * @since 1.10
	 */
	protected static final String FIXED_COLUMNS = "org.eclipse.rap.rwt.fixedColumns"; //$NON-NLS-1$
	/**
	 * @since 1.10
	 */
	protected static final String TABLE_CUSTOM_VARIANT = "org_eclipse_emf_ecp_control_table"; //$NON-NLS-1$
	/**
	 * @since 1.17
	 */
	protected static final Point VALIDATION_PREFERRED_SIZE = new Point(16, 17);

	private final Map<Integer, ECPCellEditorComparator> columnIndexToComparatorMap = new LinkedHashMap<Integer, ECPCellEditorComparator>();

	private final ImageRegistryService imageRegistryService;
	private final EMFDataBindingContext viewModelDBC;
	private final EMFFormsEditSupport emfFormsEditSupport;

	private SWTGridDescription rendererGridDescription;

	private AbstractTableViewer tableViewer;

	private Label validationIcon;
	private boolean showValidationSummaryTooltip;

	private Optional<Integer> minimumHeight;
	private Optional<Integer> maximumHeight;
	private Optional<Integer> visibleLines;
	private AbstractTableViewerComposite<? extends AbstractTableViewer> tableViewerComposite;
	private int regularColumnsStartIndex;
	private boolean isDisposing;
	private IObservableList<?> list;
	private final RunnableManager runnableManager = new RunnableManager(Display.getDefault());

	private TableViewerSWTCustomization<?> customization;

	/** The EReference describing the list shown by the table. */
	private EReference tableEReference;
	private EStructuralFeature[] columnFeatures;
	/** The feature of the column which is currently used for sorting. */
	private java.util.Optional<EStructuralFeature> sortColumnFeature = java.util.Optional.empty();
	private ModelChangeListener autoSortModelChangeListener;

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
			if (getTableStyleProperty().getRenderMode() == RenderMode.COMPACT_VERTICALLY) {
				final boolean includeLabel = getVElement().getLabelAlignment() != LabelAlignment.NONE;
				if (getVElement().getLabelAlignment() == LabelAlignment.TOP) {
					Activator.getInstance().log(IStatus.WARNING, MessageFormat.format(
						Messages.TableControlSWTRenderer_LabelAlignmentTopNotSupportForRenderModeCompactVertically,
						getVElement().getName()));
				}
				rendererGridDescription = GridDescriptionFactory.INSTANCE.createCompactGrid(includeLabel, true, this);
			} else {
				rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
			}

		}
		return rendererGridDescription;
	}

	@Override
	protected EMFFormsDatabindingEMF getEMFFormsDatabinding() {
		return (EMFFormsDatabindingEMF) super.getEMFFormsDatabinding();
	}

	@Override
	protected Control renderControl(SWTGridCell gridCell, final Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		// compact label
		if (gridCell.getColumn() == 0 && rendererGridDescription.getColumns() == 3) {
			return createLabel(parent);
		}
		// compact validation
		if (gridCell.getColumn() == 0 && rendererGridDescription.getColumns() == 2
			|| gridCell.getColumn() == 1 && rendererGridDescription.getColumns() == 3) {
			validationIcon = createValidationIcon(parent);
			return validationIcon;
		}
		// Default
		return renderTableControl(gridCell, parent);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Control createLabel(final Composite parent) {
		final VDomainModelReference dmrToCheck = getDMRToMultiReference();
		final IObservableValue<?> labelText = getLabelText(dmrToCheck);
		final IObservableValue<?> labelTooltipText = getLabelTooltipText(dmrToCheck);

		final Label titleLabel = new Label(parent, AbstractControlSWTRendererUtil
			.getLabelStyleBits(getVTViewTemplateProvider(), getVElement(), getViewModelContext()));
		titleLabel.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
		titleLabel.setBackground(parent.getBackground());

		viewModelDBC.bindValue(
			WidgetProperties.text().observe(titleLabel),
			labelText);
		viewModelDBC.bindValue(
			WidgetProperties.tooltipText().observe(titleLabel),
			labelTooltipText);

		return titleLabel;
	}

	/**
	 * Renders the Table Control.
	 *
	 * Renders the Table Control including title and validation when {@link RenderMode} is set to
	 * {@link RenderMode#DEFAULT}. Only renders the
	 * Table Control without title and validation when renderMode is set to {@link RenderMode#COMPACT_VERTICALLY}.
	 *
	 * @param gridCell the {@link SWTGridCell}.
	 * @param parent the {@link Composite}.
	 * @return the rendered {@link Control}.
	 * @throws NoRendererFoundException the {@link NoRendererFoundException}.
	 * @throws NoPropertyDescriptorFoundExeption the {@link NoPropertyDescriptorFoundExeption}.
	 * @since 1.14
	 */
	protected Control renderTableControl(SWTGridCell gridCell, final Composite parent)
		throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		try {
			/* get the list-setting which is displayed */
			final VDomainModelReference dmrToCheck = getDMRToMultiReference();

			/* get the observable list */
			list = getEMFFormsDatabinding().getObservableList(dmrToCheck,
				getViewModelContext().getDomainModel());

			/* get the EReference describing the list shown by the table */
			tableEReference = (EReference) list.getElementType();

			final TableRendererViewerActionContext actionContext = createViewerActionContext();
			final ActionConfiguration actionConfiguration = configureActions(actionContext);
			final TableActionBar<? extends AbstractTableViewer> actionBar = createActionBar(actionContext,
				actionConfiguration);

			/* get the label text/tooltip */
			final IObservableValue<?> labelText = getLabelText(dmrToCheck);
			final IObservableValue<?> labelTooltipText = getLabelTooltipText(dmrToCheck);

			/* content provider */
			final ObservableListContentProvider cp = new ObservableListContentProvider();

			final ECPTableViewerComparator comparator = getVElement().isMoveUpDownDisabled()
				? new ECPTableViewerComparator()
				: null;

			/* render */
			final TableViewerCompositeBuilder compositeBuilder = createTableViewerCompositeBuilder();

			final TableViewerSWTBuilder tableViewerSWTBuilder = createTableViewerSWTBuilder(parent, list, labelText,
				labelTooltipText, compositeBuilder, cp, comparator, actionBar);
			tableViewerSWTBuilder.customizeActionConfiguration(actionConfiguration);

			tableViewerSWTBuilder
				.configureTable(TableConfigurationBuilder.usingDefaults()
					.inheritFeatures(tableViewerSWTBuilder.getEnabledFeatures())
					.dataMapEntry(TableConfiguration.DMR, dmrToCheck)
					.build());

			regularColumnsStartIndex = 0;

			/* validation column */
			if (!getVElement().isEffectivelyReadonly()) {
				regularColumnsStartIndex++;
				createFixedValidationStatusColumn(tableViewerSWTBuilder);
			}
			regularColumnsStartIndex += addAdditionalColumns(tableViewerSWTBuilder);
			addColumns(tableViewerSWTBuilder, EReference.class.cast(list.getElementType()).getEReferenceType(), cp);

			initCompositeHeight();

			tableViewerComposite = tableViewerSWTBuilder.build();

			/* setup selection changes listener */
			tableViewerComposite.getTableViewer().addSelectionChangedListener(new ViewerSelectionChangedListener());
			tableViewerComposite.getTableViewer().addDoubleClickListener(new DoubleClickListener());

			/* setup sorting via column selection */
			if (getVElement().isMoveUpDownDisabled()) {
				setupSorting(comparator, regularColumnsStartIndex, tableViewerComposite);
			}

			/* get validation icon */
			setupValidation(tableViewerComposite);

			/* create the table viewer editor */
			setTableViewer(tableViewerComposite.getTableViewer());

			SWTDataElementIdHelper.setElementIdDataForVControl(tableViewerComposite, getVElement(),
				getViewModelContext());

			// FIXME doesn't work with table with panel
			// setLayoutData(compositeBuilder.getViewerComposite());
			GridData.class
				.cast(compositeBuilder.getViewerComposite().getLayoutData()).heightHint = getTableHeightHint();

			addRelayoutListenerIfNeeded(list, compositeBuilder.getViewerComposite());

			addResizeListener(tableViewerComposite.getTableViewer().getControl(), regularColumnsStartIndex);

			customization = tableViewerSWTBuilder.getCustomization();

			autoSortModelChangeListener = new AutoSortModelChangeListener();
			getViewModelContext().registerDomainChangeListener(autoSortModelChangeListener);

			return tableViewerComposite;

		} catch (final DatabindingFailedException ex) {
			getReportService().report(new RenderingFailedReport(ex));
			final Label errorLabel = new Label(parent, SWT.NONE);
			errorLabel.setText(ex.getMessage());
			return errorLabel;
		}
	}

	/**
	 * Create the {@link ViewerActionContext} for the table viewer.
	 *
	 * @return the {@link TableRendererViewerActionContext}
	 * @throws DatabindingFailedException
	 * @since 1.18
	 */
	protected TableRendererViewerActionContext createViewerActionContext() {
		return new TableRendererViewerActionContext() {

			@Override
			public VTableControl getVElement() {
				return TableControlSWTRenderer.this.getVElement();
			}

			@Override
			public Setting getSetting() {
				try {
					return getEMFFormsDatabinding().getSetting(
						getDMRToMultiReference(), getViewModelContext().getDomainModel());
				} catch (final DatabindingFailedException ex) {
					return null; // this should never happen
				}
			}

			@Override
			public EditingDomain getEditingDomain() {
				return TableControlSWTRenderer.this.getEditingDomain(getViewModelContext().getDomainModel());
			}

			@Override
			public AbstractTableViewer getViewer() {
				return TableControlSWTRenderer.this.getTableViewer();
			}
		};
	}

	/**
	 * Configure the actions applicable to this table viewer.
	 *
	 * @param actionContext the action context
	 * @return an {@link ActionConfigurationImpl} built using the {@link ActionConfigurationBuilder}
	 *
	 * @since 1.18
	 */
	protected ActionConfiguration configureActions(TableRendererViewerActionContext actionContext) {
		final ActionConfigurationBuilder actionConfigBuilder = //
			ActionConfigurationBuilder.usingDefaults();

		/**
		 * Note: EMF Forms distinguishes between read-only and enabled.
		 * Read-only is a declarative state defined by the view model and cannot
		 * be overwritten during runtime whereas the enabled state can.
		 * Therefore, if the view element is marked as read-only we must not configure any buttons.
		 *
		 * @see @{link TableRenderAction#isTableDisabled()}
		 */
		if (getVElement().isEffectivelyReadonly()) {
			return actionConfigBuilder.build();
		}

		final Setting setting = actionContext.getSetting();
		final EClass eClass = ((EReference) setting.getEStructuralFeature()).getEReferenceType();

		if (!getVElement().isMoveUpDownDisabled()) {
			final MoveRowUpAction moveRowUpAction = new MoveRowUpAction(actionContext);
			final MoveRowDownAction moveRowDownAction = new MoveRowDownAction(actionContext);

			actionConfigBuilder
				.addAction(moveRowUpAction)
				.addControlFor(moveRowUpAction, new TableActionIconButton(
					formatTooltipText(eClass, MessageKeys.TableControl_MoveUp), getImage("icons/move_up.png"))) //$NON-NLS-1$
				.addKeySequenceFor(moveRowUpAction,
					getKeyBindingsForAction(MoveRowUpAction.ACTION_ID))
				//
				.addAction(moveRowDownAction)
				.addControlFor(moveRowDownAction, new TableActionIconButton(
					formatTooltipText(eClass, MessageKeys.TableControl_MoveDown), getImage("icons/move_down.png"))) //$NON-NLS-1$
				.addKeySequenceFor(moveRowDownAction,
					getKeyBindingsForAction(MoveRowDownAction.ACTION_ID));
		}

		if (!getVElement().isAddRemoveDisabled()) {
			final AddRowAction addRowAction = new AddRowAction(actionContext) {
				@Override
				public void addRowLegacy(
					final EClass eClass, final EStructuralFeature eStructuralFeature, final EObject eObject) {
					addRow(eClass, eObject, eStructuralFeature);
				}
			};
			final RemoveRowAction removeRowAction = new RemoveRowAction(actionContext) {
				@Override
				public void removeRowLegacy(List<EObject> deletionList, EObject eObject,
					EStructuralFeature eStructuralFeature) {
					deleteRowUserConfirmDialog(deletionList, eObject, eStructuralFeature, getAddButton(),
						getRemoveButton());
				}
			};

			actionConfigBuilder
				.addAction(addRowAction)
				.addControlFor(addRowAction, new TableActionIconButton(
					formatTooltipText(eClass, MessageKeys.TableControl_AddInstanceOf), getImage("icons/add.png"))) //$NON-NLS-1$
				.addKeySequenceFor(addRowAction,
					getKeyBindingsForAction(AddRowAction.ACTION_ID));
			actionConfigBuilder
				.addAction(removeRowAction)
				.addControlFor(removeRowAction, new TableActionIconButton(
					formatTooltipText(eClass, MessageKeys.TableControl_RemoveSelected), getImage("icons/delete.png"))) //$NON-NLS-1$
				.addKeySequenceFor(removeRowAction,
					getKeyBindingsForAction(RemoveRowAction.ACTION_ID));
		}

		if (!getVElement().isDuplicateDisabled()) {
			final DuplicateRowAction duplicateRow = new DuplicateRowAction(actionContext);

			actionConfigBuilder
				.addAction(duplicateRow)
				.addControlFor(duplicateRow, new TableActionIconButton(
					formatTooltipText(eClass, MessageKeys.TableControl_Duplicate), getImage("icons/duplicate.png"))) //$NON-NLS-1$
				.addKeySequenceFor(duplicateRow,
					getKeyBindingsForAction(DuplicateRowAction.ACTION_ID));
		}

		return actionConfigBuilder.build();
	}

	/**
	 * Helper to extract the configured key bindings form the view template model.
	 *
	 * @param actionId the ID of the action to extract the key bindings for
	 * @param defaultKeybindings the default key bindings to use in case there are no bindings configured
	 * @return an array of key bindings
	 *
	 * @since 1.18
	 */
	protected String[] getKeyBindingsForAction(String actionId, String... defaultKeybindings) {
		final VTKeyBindings bindings = getStyleProperty(VTKeyBindings.class);
		if (bindings == null) {
			return defaultKeybindings;
		}
		final Set<String> ret = new LinkedHashSet<String>();
		for (final VTKeyBinding binding : bindings.getBindings()) {
			if (actionId.equals(binding.getId()) && binding.getKeySequence() != null
				&& !binding.getKeySequence().isEmpty()) {
				ret.add(binding.getKeySequence());
			}
		}
		return ret.toArray(new String[] {});
	}

	/**
	 * Creates an action bar.
	 *
	 * @param actionContext the {@link ViewerActionContext} to use
	 * @param actionConfiguration the {@link ActionConfiguration} to use
	 * @return a action bar builder
	 *
	 * @since 1.18
	 */
	protected TableActionBar<? extends AbstractTableViewer> createActionBar(
		TableRendererViewerActionContext actionContext, ActionConfiguration actionConfiguration) {
		return new TableRendererActionBar(actionContext, actionConfiguration, getViewModelContext());
	}

	/**
	 * Creates the {@link TableViewerCompositeBuilder} used to get Composite hierarchy for this table renderer. This
	 * method can be overwritten by sub classes to customize the layout.
	 *
	 * @return The {@link TableViewerCompositeBuilder}
	 * @since 1.13
	 */
	protected TableViewerCompositeBuilder createTableViewerCompositeBuilder() {
		if (getTableStyleProperty().getRenderMode() == RenderMode.COMPACT_VERTICALLY) {
			return new CompactVerticallyTableControlSWTRendererCompositeBuilder(false, false);
		}
		return new TableControlSWTRendererCompositeBuilder();
	}

	/**
	 * Creates a new {@link TableViewerSWTBuilder}.
	 *
	 * @param parent the parent {@link Composite}
	 * @param list the input object
	 * @param labelText the title
	 * @param labelTooltipText the tooltip
	 * @param compositeBuilder the {@link TableViewerCompositeBuilder}
	 * @param cp the content provider
	 * @param comparator the {@link ViewerComparator}; has no effect if move up/down
	 *            functionality is enabled
	 * @param actionBar the {@link ActionBar}
	 * @return the {@link TableViewerSWTBuilder}
	 * @since 1.18
	 *
	 */
	// CHECKSTYLE.OFF: ParameterNumber
	protected TableViewerSWTBuilder createTableViewerSWTBuilder(Composite parent,
		@SuppressWarnings("rawtypes") IObservableList list,
		@SuppressWarnings("rawtypes") IObservableValue labelText,
		@SuppressWarnings("rawtypes") IObservableValue labelTooltipText, TableViewerCompositeBuilder compositeBuilder,
		ObservableListContentProvider cp, ECPTableViewerComparator comparator,
		TableActionBar<? extends AbstractTableViewer> actionBar) {
		// CHECKSTYLE.ON: ParameterNumber
		return TableViewerFactory.fillDefaults(parent, SWT.NONE, list, labelText, labelTooltipText)
			.customizeCompositeStructure(compositeBuilder)
			.customizeActionBar(actionBar)
			.customizeTableViewerCreation(getTableViewerCreator())
			.customizeContentProvider(cp)
			.customizeComparator(comparator)
			.customizeDragAndDrop(new TableControlSWTRendererDragAndDrop());

	}

	/**
	 * Creates a new instance of the {@link TableViewerCreator} to be used.
	 *
	 * @return the {@link TableViewerCreator}
	 * @since 1.10
	 */
	protected TableViewerCreator<? extends AbstractTableViewer> getTableViewerCreator() {
		return new TableControlSWTRendererTableViewerCreator();
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

	/**
	 * Returns the zero-relative index of the item which is currently selected in the receiver, or -1 if no item is
	 * selected.
	 *
	 * @return the index of the selected item
	 * @since 1.10
	 */
	protected int getSelectionIndex() {
		return ((TableViewer) tableViewer).getTable().getSelectionIndex();
	}

	/**
	 * Returns an array of {@link Item items} which are the columns in the table.
	 *
	 * @return the columns of the table
	 * @since 1.10
	 */
	protected Item[] getColumns() {
		return ((TableViewer) tableViewer).getTable().getColumns();
	}

	/**
	 * Returns the receiver's horizontal scroll bar if it has one, and null if it does not.
	 *
	 * @return the horizontal scroll bar (or null)
	 * @since 1.10
	 */
	protected ScrollBar getHorizontalBar() {
		return ((TableViewer) tableViewer).getTable().getHorizontalBar();
	}

	/**
	 * Returns the receiver's vertical scroll bar if it has one, and null if it does not.
	 *
	 * @return the vertical scroll bar (or null)
	 * @since 1.10
	 */
	protected ScrollBar getVerticalBar() {
		return ((TableViewer) tableViewer).getTable().getVerticalBar();
	}

	private void addResizeListener(final Control control, final int regularColumnsStartIndex) {
		final ControlAdapter controlAdapter = new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				updateTableColumnWidths(control, regularColumnsStartIndex);
			}
		};
		control.addControlListener(controlAdapter);

		tableViewerComposite.addColumnListener(controlAdapter);
	}

	private void updateTableColumnWidths(Control table, int regularColumnsStartIndex) {
		if (isDisposing) {
			return;
		}
		final VTableControl tableControl = getVElement();
		final Widget[] allColumns = tableViewerComposite.getColumns();
		for (int i = regularColumnsStartIndex; i < allColumns.length; i++) {
			final Widget tableColumn = allColumns[i];
			final VDomainModelReference columnDMR = getColumnDomainModelReferences().get(i - regularColumnsStartIndex);
			TableConfigurationHelper.updateWidthConfiguration(tableControl, columnDMR, tableColumn);
		}
	}

	private void initCompositeHeight() {
		final VTTableStyleProperty styleProperty = getTableStyleProperty();
		minimumHeight = styleProperty.isSetMinimumHeight() ? Optional.of(styleProperty.getMinimumHeight())
			: Optional.<Integer> empty();
		maximumHeight = styleProperty.isSetMaximumHeight() ? Optional.of(styleProperty.getMaximumHeight())
			: Optional.<Integer> empty();
		visibleLines = styleProperty.isSetVisibleLines() ? Optional.of(styleProperty.getVisibleLines())
			: Optional.<Integer> empty();
	}

	private <T> void addRelayoutListenerIfNeeded(IObservableList<T> list, final Composite composite) {
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
		list.addListChangeListener(new IListChangeListener<T>() {
			@Override
			public void handleListChange(ListChangeEvent<? extends T> event) {
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
	 * @param tableConfiguration
	 *
	 */
	private void addColumns(TableViewerSWTBuilder tableViewerSWTBuilder, EClass clazz,
		ObservableListContentProvider cp) {
		InternalEObject tempInstance = null;
		if (!clazz.isAbstract() && !clazz.isInterface()) {
			tempInstance = getInstanceOf(clazz);
		}

		final List<VDomainModelReference> columns = getColumnDomainModelReferences().stream().filter(Objects::nonNull)
			.collect(Collectors.toList());
		/* regular columns */
		columnFeatures = new EStructuralFeature[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			try {
				final VDomainModelReference dmr = columns.get(i);
				final IObservableValue<?> text = getLabelTextForColumn(dmr, clazz);
				final IObservableValue<?> tooltip = getLabelTooltipTextForColumn(dmr, clazz);

				// Use the same editing domain for the columns as for the view's domain object
				final EditingDomain editingDomain = getEditingDomain(getViewModelContext().getDomainModel());
				final IValueProperty<?, ?> valueProperty = getEMFFormsDatabinding().getValueProperty(dmr, clazz,
					editingDomain);
				final EStructuralFeature eStructuralFeature = (EStructuralFeature) valueProperty.getValueType();
				columnFeatures[i] = eStructuralFeature;

				@SuppressWarnings("unchecked")
				final IObservableMap<?, ?> observableMap = valueProperty.observeDetail(cp.getKnownElements());

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
					final Shell tempShell = new Shell();
					final CellEditor tempCellEditor = createCellEditor(tempInstance, eStructuralFeature,
						new Table(tempShell, SWT.NONE));
					weight = ECPCellEditor.class.isInstance(tempCellEditor)
						? ECPCellEditor.class.cast(tempCellEditor).getColumnWidthWeight()
						: 100;
					minWidth = ECPCellEditor.class.isInstance(tempCellEditor)
						? ECPCellEditor.class.cast(tempCellEditor).getMinWidth()
						: 10;
					tempShell.dispose();
				}

				tableViewerSWTBuilder.addColumn(
					ColumnConfigurationBuilder.usingDefaults()
						.inheritFeatures(tableViewerSWTBuilder.getEnabledFeatures())
						.weight(weight)
						.minWidth(minWidth)
						.text(text)
						.tooltip(tooltip)
						.labelProviderFactory(labelProvider)
						.editingSupportCreator(editingSupportCreator)
						.dataMapEntry(ColumnConfiguration.DMR, dmr)
						.build());

			} catch (final DatabindingFailedException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				continue;
			}
		}

	}

	private void setupValidation(
		final AbstractTableViewerComposite<? extends AbstractTableViewer> tableViewerComposite) {
		if (tableViewerComposite.getValidationControls().isPresent()) {
			final List<Control> validationControls = tableViewerComposite.getValidationControls().get();
			if (validationControls.size() == 1 && Label.class.isInstance(validationControls.get(0))) {
				validationIcon = (Label) validationControls.get(0);
			}

			final VTTableStyleProperty tableStyleProperty = getTableStyleProperty();
			showValidationSummaryTooltip = tableStyleProperty.isShowValidationSummaryTooltip();
		}
	}

	private void setupSorting(final ECPTableViewerComparator comparator, int regularColumnsStartIndex,
		final AbstractTableViewerComposite<? extends AbstractTableViewer> tableViewerComposite) {

		final VTTableStyleProperty tableStyleProperty = getTableStyleProperty();
		if (!tableStyleProperty.isEnableSorting()) {
			return;
		}
		final int length = tableViewerComposite.getColumns().length;
		final List<Integer> sortableColumns = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			if (i >= regularColumnsStartIndex) {
				sortableColumns.add(i);
			}
		}
		tableViewerComposite.setComparator(comparator, sortableColumns);
	}

	private IObservableValue<?> getLabelText(VDomainModelReference dmrToCheck) {
		switch (getVElement().getLabelAlignment()) {
		case NONE:
			return Observables.constantObservableValue("", String.class); //$NON-NLS-1$
		default:
			try {
				return getEMFFormsLabelProvider().getDisplayName(dmrToCheck, getViewModelContext().getDomainModel());
			} catch (final NoLabelFoundException e) {
				// FIXME Expectation?
				getReportService().report(new RenderingFailedReport(e));
				return Observables.constantObservableValue(e.getMessage(), String.class);
			}
		}
	}

	private IObservableValue<?> getLabelTextForColumn(VDomainModelReference dmrToCheck, EClass dmrRootEClass) {
		try {
			return getEMFFormsLabelProvider().getDisplayName(dmrToCheck, dmrRootEClass);
		} catch (final NoLabelFoundException e) {
			// FIXME Expectation?
			getReportService().report(new RenderingFailedReport(e));
			return Observables.constantObservableValue(e.getMessage(), String.class);
		}
	}

	private IObservableValue<?> getLabelTooltipText(VDomainModelReference dmrToCheck) {
		switch (getVElement().getLabelAlignment()) {
		case NONE:
			return Observables.constantObservableValue("", String.class); //$NON-NLS-1$
		default:
			try {
				return getEMFFormsLabelProvider().getDescription(dmrToCheck, getViewModelContext().getDomainModel());
			} catch (final NoLabelFoundException e) {
				// FIXME Expectation?
				getReportService().report(new RenderingFailedReport(e));
				return Observables.constantObservableValue(e.toString(), String.class);
			}
		}
	}

	private IObservableValue<?> getLabelTooltipTextForColumn(VDomainModelReference dmrToCheck, EClass dmrRootEClass) {
		try {
			return getEMFFormsLabelProvider().getDescription(dmrToCheck, dmrRootEClass);
		} catch (final NoLabelFoundException e) {
			// FIXME Expectation?
			getReportService().report(new RenderingFailedReport(e));
			return Observables.constantObservableValue(e.toString(), String.class);
		}
	}

	/**
	 * @return the {@link VDomainModelReference} which ends at the table setting
	 * @since 1.11
	 */
	protected VDomainModelReference getDMRToMultiReference() {
		final VTableDomainModelReference tableDomainModelReference = (VTableDomainModelReference) getVElement()
			.getDomainModelReference();
		final VDomainModelReference dmrToCheck = tableDomainModelReference.getDomainModelReference() == null
			? tableDomainModelReference
			: tableDomainModelReference.getDomainModelReference();
		return dmrToCheck;
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
	// BEGIN COMPLEX CODE
	protected int getTableHeightHint() {
		/* if neither min nor max is set we use a fixed height */
		if (!minimumHeight.isPresent() && !maximumHeight.isPresent() && !visibleLines.isPresent()) {
			return 200;
		}
		// if the visible lines attribute is present, it takes precedence over the minimum & maximum height hints
		if (visibleLines.isPresent()) {
			return computeRequiredHeight(visibleLines.get());
		}
		if (minimumHeight.isPresent() && maximumHeight.isPresent() && minimumHeight.get() == maximumHeight.get()) {
			return minimumHeight.get();
		}

		final int requiredHeight = computeRequiredHeight(null);

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
	// END COMPLEX CODE

	/**
	 * Returns the height in pixels required to display the given number of table items. If the visible items are not
	 * specified, the height required to display all the table items is returned.
	 *
	 * @param visibleLines the number of visible table items
	 * @return the required height
	 * @since 1.13
	 */
	protected int computeRequiredHeight(Integer visibleLines) {
		if (tableViewer == null) {
			return SWT.DEFAULT;
		}
		final TableControl table = tableViewerComposite.getTableControl();
		if (table == null) {
			return SWT.DEFAULT;
		}
		if (table.isDisposed()) {
			return SWT.DEFAULT;
		}
		final int itemHeight = table.getItemHeight();
		// show one empty row if table does not contain any items or visibleLines < 1
		int itemCount;
		if (visibleLines != null) {
			itemCount = Math.max(visibleLines, 1);
		} else {
			itemCount = Math.max(table.getItemCount(), 1);
		}
		final int headerHeight = table.getHeaderVisible() ? table.getHeaderHeight() : 0;
		// 4px needed as a buffer to avoid scrollbars
		final int tableHeight = itemHeight * itemCount + headerHeight + 4;
		return tableHeight;
	}

	/**
	 * Returns the table viewer.
	 *
	 * @return the viewer
	 * @since 1.10
	 */
	protected AbstractTableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * Returns the {@link AbstractTableViewerComposite}.
	 *
	 * @return the table viewer composite
	 * @since 1.13
	 */
	@SuppressWarnings("rawtypes")
	protected AbstractTableViewerComposite getTableViewerComposite() {
		return tableViewerComposite;
	}

	/**
	 * Sets the table viewer.
	 *
	 * @param tableViewer the viewer
	 * @since 1.10
	 */
	protected void setTableViewer(AbstractTableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

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
		// The default implementation does not need to do anything
	}

	private void createFixedValidationStatusColumn(TableViewerSWTBuilder tableViewerSWTBuilder) {
		final VTTableValidationStyleProperty tableValidationStyleProperty = getTableValidationStyleProperty();
		final int columnWidth = tableValidationStyleProperty.getColumnWidth();
		final String columnName = tableValidationStyleProperty.getColumnName();
		final String imagePath = tableValidationStyleProperty.getImagePath();
		Image image = null;
		if (imagePath != null && !imagePath.isEmpty()) {
			try {
				image = getImage(new URL(imagePath));
			} catch (final MalformedURLException ex) {
				getReportService().report(new AbstractReport(ex));
			}
		}

		tableViewerSWTBuilder.addColumn(
			ColumnConfigurationBuilder.usingDefaults()
				.minWidth(columnWidth)
				.text(columnName)
				.tooltip(columnName)
				.labelProvider(new ValidationStatusCellLabelProvider(getVElement()))
				.image(image)
				.build());

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
		Image result = imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()), path);
		if (result == null && getClass() != TableControlSWTRenderer.class) {
			result = imageRegistryService.getImage(FrameworkUtil.getBundle(TableControlSWTRenderer.class), path);
		}
		return result;
	}

	/**
	 * Returns the {@link VTTableValidationStyleProperty}.
	 *
	 * @return the {@link VTTableValidationStyleProperty}
	 * @since 1.14
	 */
	protected VTTableValidationStyleProperty getTableValidationStyleProperty() {
		VTTableValidationStyleProperty tableValidationStyleProperties = getStyleProperty(
			VTTableValidationStyleProperty.class);
		if (tableValidationStyleProperties == null) {
			tableValidationStyleProperties = createDefaultTableValidationStyleProperty();
		}
		return tableValidationStyleProperties;
	}

	/**
	 * Creates the default {@link VTTableValidationStyleProperty}.
	 *
	 * @return the default {@link VTTableValidationStyleProperty}
	 * @since 1.14
	 */
	protected VTTableValidationStyleProperty createDefaultTableValidationStyleProperty() {
		final VTTableValidationStyleProperty tableValidationProp = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();
		tableValidationProp.setColumnWidth(80);
		tableValidationProp.setColumnName(LocalizationServiceHelper.getString(TableControlSWTRenderer.class,
			MessageKeys.TableControl_ValidationStatusColumn));
		tableValidationProp.setImagePath(null);
		return tableValidationProp;
	}

	/**
	 * Returns the {@link VTBackgroundStyleProperty}.
	 *
	 * @return the {@link VTBackgroundStyleProperty}
	 *
	 * @since 1.10
	 */
	protected VTBackgroundStyleProperty getBackgroundStyleProperty() {
		VTBackgroundStyleProperty styleProperty = getStyleProperty(VTBackgroundStyleProperty.class);
		if (styleProperty == null) {
			styleProperty = createDefaultBackgroundStyleProperty();
		}
		return styleProperty;
	}

	/**
	 * Creates the default {@link VTBackgroundStyleProperty}.
	 *
	 * @return the default {@link VTBackgroundStyleProperty}
	 * @since 1.14
	 */
	protected VTBackgroundStyleProperty createDefaultBackgroundStyleProperty() {
		return VTBackgroundFactory.eINSTANCE.createBackgroundStyleProperty();
	}

	/**
	 * Returns the {@link VTTableStyleProperty}.
	 *
	 * @return the {@link VTTableStyleProperty}
	 * @since 1.14
	 */
	protected VTTableStyleProperty getTableStyleProperty() {
		VTTableStyleProperty styleProperty = getStyleProperty(VTTableStyleProperty.class);
		if (styleProperty == null) {
			styleProperty = createDefaultTableStyleProperty();
		}
		return styleProperty;
	}

	/**
	 * Creates the default {@link VTTableStyleProperty}.
	 *
	 * @return the default {@link VTTableStyleProperty}
	 * @since 1.14
	 */
	protected VTTableStyleProperty createDefaultTableStyleProperty() {
		final VTTableStyleProperty tableStyleProperty = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();
		tableStyleProperty.setMaximumHeight(200);
		if (!getVElement().isEffectivelyReadonly()) {
			tableStyleProperty.setMinimumHeight(200);
		}
		return tableStyleProperty;
	}

	/**
	 * Returns the {@link VTFontPropertiesStyleProperty}.
	 *
	 * @return the {@link VTFontPropertiesStyleProperty}
	 * @since 1.10
	 */
	protected VTFontPropertiesStyleProperty getFontPropertiesStyleProperty() {
		VTFontPropertiesStyleProperty styleProperty = getStyleProperty(VTFontPropertiesStyleProperty.class);
		if (styleProperty == null) {
			styleProperty = createDefaultFontPropertiesStyleProperty();
		}
		return styleProperty;
	}

	/**
	 * Creates the default {@link VTFontPropertiesStyleProperty}.
	 *
	 * @return the default {@link VTFontPropertiesStyleProperty}
	 * @since 1.14
	 */
	protected VTFontPropertiesStyleProperty createDefaultFontPropertiesStyleProperty() {
		final VTFontPropertiesStyleProperty property = VTFontPropertiesFactory.eINSTANCE
			.createFontPropertiesStyleProperty();
		property.setColorHEX("000000"); //$NON-NLS-1$
		return property;
	}

	private <SP extends VTStyleProperty> SP getStyleProperty(Class<SP> stylePropertyClass) {
		return RendererUtil.getStyleProperty(getVTViewTemplateProvider(), getVElement(), getViewModelContext(),
			stylePropertyClass);
	}

	/**
	 * Returns the {@link Color} specified by the provided String.
	 *
	 * @param colorHex the Hex String describing the color
	 * @return the {@link Color}
	 * @since 1.10
	 *
	 */
	protected Color getSWTColor(String colorHex) {
		final String redString = colorHex.substring(0, 2);
		final String greenString = colorHex.substring(2, 4);
		final String blueString = colorHex.substring(4, 6);
		final int red = Integer.parseInt(redString, 16);
		final int green = Integer.parseInt(greenString, 16);
		final int blue = Integer.parseInt(blueString, 16);
		return new Color(Display.getDefault(), red, green, blue);
	}

	/**
	 * Retrieves this table's column DMRs from the table DMR.
	 *
	 * @return The domain model references defining the columns of this table.
	 * @since 1.19
	 */
	protected EList<VDomainModelReference> getColumnDomainModelReferences() {
		final VDomainModelReference dmr = getVElement().getDomainModelReference();
		final java.util.Optional<VMultiDomainModelReferenceSegment> multiSegment = MultiSegmentUtil
			.getMultiSegment(dmr);
		if (multiSegment.isPresent()) {
			return multiSegment.get().getChildDomainModelReferences();
		}
		return VTableDomainModelReference.class.cast(dmr).getColumnDomainModelReferences();
	}

	/**
	 * This is called in order to setup the editing support for a table column.
	 *
	 * @param tempInstance the temporary input instance of the table
	 * @param feature the feature of the column
	 * @param table the table/parent
	 * @return the cell editor
	 * @since 1.10
	 */
	protected CellEditor createCellEditor(final EObject tempInstance, final EStructuralFeature feature,
		Composite table) {
		return CellEditorFactory.INSTANCE.getCellEditor(feature,
			tempInstance, table, getViewModelContext());
	}

	private InternalEObject getInstanceOf(EClass clazz) {
		return InternalEObject.class.cast(clazz.getEPackage().getEFactoryInstance().create(clazz));
	}

	/**
	 * This method shows a user confirmation dialog when the user attempts to delete a row in the table.
	 *
	 * @deprecated this method will be moved to {@link RemoveRowAction} in the future
	 * @param deletionList the list of selected EObjects to delete
	 * @param eObject The containment reference {@link EObject}
	 * @param structuralFeature The containment reference {@link EStructuralFeature}
	 * @param addButton the add button
	 * @param removeButton the remove button
	 * @since 1.6
	 */
	@Deprecated
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
	 * @deprecated this method will be moved to {@link RemoveRowAction} in the future
	 * @param deletionList the list of {@link EObject EObjects} to delete
	 * @param eObject The containment reference {@link EObject}
	 * @param structuralFeature The containment reference {@link EStructuralFeature}
	 * @since 1.6
	 */
	@Deprecated
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
	 * @deprecated this method will be move to {@link AddRowAction} in the future
	 * @param clazz the {@link EClass} defining the EObject to create
	 * @param eObject The containment reference {@link EObject}
	 * @param structuralFeature The containment reference {@link EStructuralFeature}
	 * @since 1.6
	 */
	@Deprecated
	protected void addRow(EClass clazz, EObject eObject, EStructuralFeature structuralFeature) {
		Optional<EObject> eObjectToAdd = null;

		/* table service available => use specific behavior to create row */
		if (getViewModelContext().hasService(TableControlService.class)) {
			final TableControlService tableService = getViewModelContext()
				.getService(TableControlService.class);
			eObjectToAdd = tableService.createNewElement(clazz, eObject, structuralFeature);
		}
		/* no table service available, fall back to default */
		if (eObjectToAdd == null) {
			final ReferenceService referenceService = getViewModelContext().getService(ReferenceService.class);
			eObjectToAdd = referenceService.addNewModelElements(eObject, EReference.class.cast(structuralFeature),
				false);
		}

		if (!eObjectToAdd.isPresent()) {
			return;
		}

		final EObject instance = eObjectToAdd.get();
		final EditingDomain editingDomain = getEditingDomain(eObject);
		if (editingDomain == null) {
			return;
		}
		editingDomain.getCommandStack().execute(
			AddCommand.create(editingDomain, eObject, structuralFeature, instance));
		tableViewer.setSelection(new StructuredSelection(eObjectToAdd.get()), true);

	}

	@Override
	protected void applyValidation(VDiagnostic oldDiagnostic, VDiagnostic newDiagnostic) {
		final Stream<Object> oldDiagnostics = oldDiagnostic == null ? Stream.empty()
			: oldDiagnostic.getDiagnostics().stream();
		final Stream<Object> newDiagnostics = newDiagnostic == null ? Stream.empty()
			: newDiagnostic.getDiagnostics().stream();

		final Set<Object> updates = Stream.concat(oldDiagnostics, newDiagnostics)
			.map(this::getSubject).filter(Objects::nonNull).collect(toCollection(LinkedHashSet::new));

		runnableManager.executeAsync(new ApplyValidationRunnable(updates));
	}

	private Object getSubject(Object diagnostic) {
		Object result = null;

		if (diagnostic instanceof Diagnostic) {
			final List<?> data = ((Diagnostic) diagnostic).getData();
			result = data.isEmpty() ? null : data.get(0);
		}

		return result;
	}

	@Override
	protected void applyValidation() {
		if (!runnableManager.isRunning()) {
			runnableManager.executeAsync(new ApplyValidationRunnable());
		}
	}

	/**
	 * Returns the add button created by the framework.
	 *
	 * @deprecated use {@link #getControlForAction(String)} instead
	 * @return the addButton
	 * @since 1.6
	 */
	@Deprecated
	protected Button getAddButton() {
		final Optional<Control> control = getControlForAction(AddRowAction.ACTION_ID);
		if (control.isPresent()) {
			return (Button) control.get();
		}
		return null; // happens when add/remove has been disabled in the view model
	}

	/**
	 * Returns the remove button created by the framework.
	 *
	 * @deprecated use {@link #getControlForAction(String)} instead
	 * @return the removeButton
	 * @since 1.6
	 */
	@Deprecated
	protected Button getRemoveButton() {
		final Optional<Control> control = getControlForAction(RemoveRowAction.ACTION_ID);
		if (control.isPresent()) {
			return (Button) control.get();
		}
		return null; // happens when add/remove has been disabled in the view model
	}

	/**
	 * Returns the control created to trigger a certain action.
	 *
	 * @param actionId the action ID of the action that is bound to the control
	 * @return an optional for the control of the given action ID
	 * @since 1.18
	 */
	public Optional<Control> getControlForAction(String actionId) {
		if (tableViewerComposite.getActionBar().isPresent()) {
			return tableViewerComposite.getActionBar().get().getControlById(actionId);
		}
		return Optional.empty();
	}

	/**
	 * Helper method which uses an EMFForms observable value to get the setting for the given
	 * {@link VDomainModelReference}.
	 *
	 * @param dmr the {@link VDomainModelReference}
	 * @param eObject the {@link EObject} to get the setting for
	 * @return an Optional<Setting>
	 *
	 * @since 1.17
	 */
	protected Optional<Setting> getSettingFromObservable(VDomainModelReference dmr, EObject eObject) {
		@SuppressWarnings("rawtypes")
		IObservableValue observableValue = null;
		try {
			observableValue = getEMFFormsDatabinding().getObservableValue(dmr, eObject);

			final EStructuralFeature feature = (EStructuralFeature) observableValue.getValueType();
			final EObject observed = (EObject) ((IObserving) observableValue).getObserved();

			// Given EClass has no such feature
			if (observed == null || observed.eClass().getFeatureID(feature) == -1) {
				return Optional.empty();
			}

			return Optional.of(((InternalEObject) observed).eSetting(feature));

		} catch (final DatabindingFailedException ex) {
			getReportService().report(new DatabindingFailedReport(ex));
			return Optional.empty();
		} finally {
			if (observableValue != null) {
				observableValue.dispose();
			}
		}

	}

	@Override
	protected void applyEnable() {
		updateActionBar();
	}

	@Override
	protected void applyReadOnly() {
		updateActionBar();
	}

	@Override
	protected void applyUnchangeableFeature() {
		getVElement().setAddRemoveDisabled(true);
		getVElement().setDuplicateDisabled(true);
		getVElement().setMoveUpDownDisabled(true);
		updateActionBar();
	}

	/**
	 * Update this table control's action bar if it is present.
	 *
	 * @since 1.20
	 */
	protected void updateActionBar() {
		if (tableViewerComposite != null && tableViewerComposite.getActionBar().isPresent()) {
			tableViewerComposite.getActionBar().get().updateActionBar();
		}
	}

	@Override
	protected void dispose() {
		isDisposing = true;
		rendererGridDescription = null;
		viewModelDBC.dispose();
		if (list != null) {
			list.dispose();
		}
		if (columnIndexToComparatorMap != null) {
			for (final ECPCellEditorComparator value : columnIndexToComparatorMap.values()) {
				if (value instanceof CellEditor) {
					((CellEditor) value).dispose();
				}
			}
			columnIndexToComparatorMap.clear();
		}
		if (tableViewerComposite != null) {
			tableViewerComposite.dispose();
			tableViewerComposite = null;
		}
		if (tableViewer != null) {
			tableViewer.getControl().dispose();
			tableViewer = null;
		}

		if (customization != null) {
			for (final ColumnConfiguration columnConfig : customization.getColumnConfigurations()) {
				columnConfig.visible().dispose();
				columnConfig.dispose();
			}
			customization.getTableConfiguration().dispose();
		}

		if (autoSortModelChangeListener != null) {
			getViewModelContext().unregisterDomainChangeListener(autoSortModelChangeListener);
			autoSortModelChangeListener = null;
		}
		super.dispose();
	}

	/**
	 * Get called by the {@link ECPTableViewerComparator} in order to compare the given objects.
	 *
	 * @param viewer the tavle viewer
	 * @param left the first object of the comparison
	 * @param right the second object of the comparison
	 * @param propertyIndex index of the selection column. the index is aligned with the index of the associated column
	 *            domain model reference
	 * @param direction 0 (no sorting = insertion order := {@link SWT#NONE}), 1 (ascending := {@link SWT#DOWN}) or 2
	 *            (descending := {@link SWT#UP}) according to the indication displayed at
	 *            the table column.
	 * @return a negative number if the first element is less than the
	 *         second element; the value <code>0</code> if the first element is
	 *         equal to the second element; and a positive number if the first
	 *         element is greater than the second element
	 * @since 1.8
	 */
	@SuppressWarnings("unchecked")
	protected int compare(Viewer viewer, Object left, Object right, int direction, int propertyIndex) {
		if (direction == 0) {
			return 0;
		}

		// We might have ignored columns at the beginning
		propertyIndex = propertyIndex - regularColumnsStartIndex;
		int rc = 0;

		final VDomainModelReference dmr = getColumnDomainModelReferences().get(propertyIndex);

		final Optional<Setting> leftSetting = getSettingFromObservable(dmr, (EObject) left);
		final Optional<Setting> rightSetting = getSettingFromObservable(dmr, (EObject) right);

		final Object leftValue = leftSetting.isPresent() ? leftSetting.get().get(true) : null;
		final Object rightValue = rightSetting.isPresent() ? rightSetting.get().get(true) : null;

		if (columnIndexToComparatorMap.containsKey(propertyIndex)) {
			return columnIndexToComparatorMap.get(propertyIndex).compare(leftValue, rightValue, direction);
		}

		if (leftValue == null) {
			if (rightValue == null) {
				rc = 0;
			} else {
				rc = 1;
			}
		} else if (rightValue == null) {
			rc = -1;
		} else {
			if (!(leftValue instanceof String) && leftValue instanceof Comparable
				&& leftValue.getClass().isInstance(rightValue)) {
				rc = Comparable.class.cast(leftValue).compareTo(rightValue);
			} else {
				rc = NumberAwareStringComparator.getInstance().compare(leftValue.toString(), rightValue.toString());
			}
		}
		// If descending order, flip the direction
		if (direction == 2) {
			rc = -rc;
		}
		return rc;
	}

	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {

		final IObservableList<?> oldList = (IObservableList<?>) getTableViewer().getInput();
		oldList.dispose();

		final EObject domainModel = getViewModelContext().getDomainModel();
		final IObservableList<?> list = getEMFFormsDatabinding().getObservableList(getDMRToMultiReference(),
			domainModel);
		getTableViewer().setInput(list);

		if (tableViewerComposite.getActionBar().isPresent()) {
			tableViewerComposite.getActionBar().get().updateActionBar();
		}
	}

	/**
	 * Checks whether an element is editable or not.
	 *
	 * @param element The list entry to be checked
	 * @return True if the element can be edited, false otherwise
	 *
	 * @since 1.11
	 */
	protected boolean canEditObject(Object element) {
		return true;
	}

	/**
	 * Defined whether a cell editor should be created or not.
	 *
	 * @param element The table entry to be checked
	 * @return True if a CellEditor should be created, false otherwise
	 * @since 1.12
	 */
	protected boolean shouldCreateCellEditor(Object element) {
		final boolean isObjectEditable = canEditObject(element);
		if (!isObjectEditable) {
			return false;
		}
		final boolean editable = getVElement().isEffectivelyEnabled()
			&& !getVElement().isEffectivelyReadonly();
		return editable;
	}

	private boolean isDisabled(EObject eObject, VDomainModelReference columnDmr) {

		final Optional<VEnablementConfiguration> enablmentConf = TableConfigurationHelper
			.findEnablementConfiguration(getVElement(), columnDmr);

		if (enablmentConf.isPresent()) {
			return !enablmentConf.get().isEnabled();
		}

		return false;
	}

	/**
	 * Called by the {@link TableControlEditingSupportAndLabelProvider}.
	 *
	 * @param feature the feature of the column
	 * @param cellEditor the cell editor for the column
	 * @param attributeMap the attribute map displayed in the table
	 * @param vTableControl the table view model element
	 * @param dmr the domain model reference of the column
	 * @param table the table control
	 * @return the {@link CellLabelProvider} of the column
	 * @since 1.12
	 */
	protected CellLabelProvider createCellLabelProvider(
		EStructuralFeature feature,
		CellEditor cellEditor,
		@SuppressWarnings("rawtypes") IObservableMap attributeMap,
		VTableControl vTableControl,
		VDomainModelReference dmr,
		Control table) {
		return new ECPCellLabelProvider(
			feature,
			cellEditor,
			attributeMap,
			getVElement(),
			dmr,
			table);
	}

	/**
	 * The {@link DNDProvider} for this renderer.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class TableControlSWTRendererDragAndDrop implements DNDProvider {

		/**
		 * The drop adapter.
		 */
		private final class TableControlDropAdapter extends EditingDomainViewerDropAdapter {

			private final AbstractTableViewer tableViewer;
			private EObject eObject;
			private EStructuralFeature eStructuralFeature;
			private List<Object> list;

			@SuppressWarnings("unchecked")
			TableControlDropAdapter(EditingDomain domain, Viewer viewer, AbstractTableViewer tableViewer) {
				super(domain, viewer);
				this.tableViewer = tableViewer;
				try {
					final Setting setting = getEMFFormsDatabinding().getSetting(getDMRToMultiReference(),
						getViewModelContext().getDomainModel());
					eObject = setting.getEObject();
					eStructuralFeature = setting.getEStructuralFeature();
					list = (List<Object>) setting.get(true);
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new AbstractReport(ex));
				}
			}

			@Override
			protected void helper(DropTargetEvent event) {
				final Object target = extractDropTarget(event.item);
				final Collection<?> dragSource = getDragSource(event);

				if (dragSource == null) {
					/* possible on non-win32 platforms */
					/* in this case we will just wait until the data is available without setting a detail */
					return;
				}

				if (target == null || dragSource.contains(target)) {
					event.detail = DND.DROP_NONE;
					return;
				}

				event.detail = DND.DROP_MOVE;
			}

			@Override
			public void drop(DropTargetEvent event) {

				final Collection<?> dragSource = getDragSource(event);
				final Object target = extractDropTarget(event.item);
				final float location = getLocation(event);

				final List<Command> commands = new ArrayList<Command>();
				final boolean insertAfter = location >= 0.5;

				for (final Object toMove : dragSource) {
					final int indexTarget = list.indexOf(target);
					final int indexToMove = list.indexOf(toMove);

					if (indexTarget == -1 || indexToMove == -1) {
						return;
					}

					final boolean moveIsLocatedBeforeTarget = indexToMove < indexTarget;

					int index;
					if (insertAfter) {
						if (moveIsLocatedBeforeTarget) {
							index = indexTarget;
						} else {
							index = indexTarget + 1;
						}
					} else {
						/* insert Before Target */
						if (moveIsLocatedBeforeTarget) {
							index = indexTarget - 1;
						} else {
							index = indexTarget;
						}
					}

					commands.add(MoveCommand.create(domain, eObject, eStructuralFeature, toMove, index));
				}

				final Command command = new CompoundCommand(commands);

				if (!command.canExecute()) {
					return;
				}
				domain.getCommandStack().execute(command);

				tableViewer.refresh();
			}
		}

		@Override
		public int getDragOperations() {
			return getDNDOperations();
		}

		@Override
		public Transfer[] getDragTransferTypes() {
			return getDNDTransferTypes();
		}

		@Override
		public DragSourceListener getDragListener(AbstractTableViewer tableViewer) {
			return new ViewerDragAdapter(tableViewer);
		}

		@Override
		public int getDropOperations() {
			return getDNDOperations();
		}

		@Override
		public Transfer[] getDropTransferTypes() {
			return getDNDTransferTypes();
		}

		@Override
		public DropTargetListener getDropListener(final AbstractTableViewer tableViewer) {
			return new TableControlDropAdapter(getEditingDomain(getViewModelContext().getDomainModel()), tableViewer,
				tableViewer);
		}

		private int getDNDOperations() {
			return DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		}

		private Transfer[] getDNDTransferTypes() {
			return new Transfer[] { LocalTransfer.getInstance() };
		}

		@Override
		public boolean hasDND() {
			return true;
		}
	}

	/**
	 * Double click listener.
	 *
	 */
	private final class DoubleClickListener implements IDoubleClickListener {
		@Override
		public void doubleClick(DoubleClickEvent event) {
			if (!getViewModelContext().hasService(TableControlService.class)) {
				return;
			}
			final ISelection selection = event.getSelection();
			if (!StructuredSelection.class.isInstance(selection)) {
				return;
			}
			final TableControlService tableService = getViewModelContext()
				.getService(TableControlService.class);
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
		private Collection<?> updates;

		ApplyValidationRunnable() {
			super();
		}

		ApplyValidationRunnable(Collection<?> updates) {
			super();

			this.updates = updates;
		}

		@Override
		public void run() {
			if (isDisposing) {
				return;
			}
			// triggered due to another validation rule before this control is rendered
			// validation rule triggered after the control was disposed
			if (validationIcon == null || validationIcon.isDisposed()) {
				return;
			}

			// no diagnostic set
			if (getVElement().getDiagnostic() == null) {
				return;
			}

			final VDomainModelReference dmr = getDMRToMultiReference();
			final Optional<Setting> setting = getSettingFromObservable(dmr, getViewModelContext().getDomainModel());

			if (!setting.isPresent()) {
				return;
			}

			validationIcon.setImage(getValidationIcon(getVElement().getDiagnostic().getHighestSeverity()));
			showValidationSummaryTooltip(setting.get(), showValidationSummaryTooltip);

			if (updates != null) {
				// Update these specific objects
				getTableViewer().update(updates.toArray(), null);
			} else {
				// Just refresh everything
				getTableViewer().refresh();
			}
		}

		// extracted in order to avoid checkstyle complexity warning
		private void showValidationSummaryTooltip(Setting tableSetting, boolean doShow) {
			if (doShow) {
				validationIcon.setToolTipText(ECPTooltipModifierHelper.modifyString(getVElement().getDiagnostic()
					.getMessage(), null));
				return;
			}

			// Even if the display of a validation summary tooltip is disabled, we still show validation errors directly
			// related to the table feature (e.g. multiplicity errors)
			final StringBuilder builder = new StringBuilder();
			final List<Diagnostic> tableDiagnostics = getVElement().getDiagnostic()
				.getDiagnostic(tableSetting.getEObject(), tableSetting.getEStructuralFeature());
			for (final Diagnostic diagnostic : tableDiagnostics) {
				builder.append(diagnostic.getMessage());
				builder.append("\n"); //$NON-NLS-1$
			}
			final String toolTipText = ECPTooltipModifierHelper.modifyString(builder.toString().trim(), null);
			validationIcon.setToolTipText(toolTipText);
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
	protected final class TableControlEditingSupportAndLabelProvider
		implements EditingSupportCreator, CellLabelProviderFactory {
		private final InternalEObject tempInstance;
		private final EStructuralFeature eStructuralFeature;
		private final VDomainModelReference dmr;
		private final IValueProperty<?, ?> valueProperty;
		private final IObservableMap<?, ?> observableMap;

		private CellEditor cellEditor;
		private ECPTableEditingSupport observableSupport;

		private boolean initialized;
		private final int indexOfColumn;

		private TableControlEditingSupportAndLabelProvider(InternalEObject tempInstance,
			EStructuralFeature eStructuralFeature, VDomainModelReference dmr,
			IValueProperty<?, ?> valueProperty, IObservableMap<?, ?> observableMap, int indexOfColumn) {
			this.tempInstance = tempInstance;
			this.eStructuralFeature = eStructuralFeature;
			this.dmr = dmr;
			this.valueProperty = valueProperty;
			this.observableMap = observableMap;
			this.indexOfColumn = indexOfColumn;
		}

		@Override
		public EditingSupport createEditingSupport(AbstractTableViewer tableViewer) {
			if (!initialized) {
				init(tableViewer);
			}
			return observableSupport;
		}

		private void init(AbstractTableViewer tableViewer) {
			cellEditor = createCellEditor(tempInstance, eStructuralFeature,
				(Composite) tableViewer.getControl());
			if (cellEditor instanceof ECPViewerAwareCellEditor) {
				((ECPViewerAwareCellEditor) cellEditor).setTableViewer(tableViewer);
				((ECPViewerAwareCellEditor) cellEditor).setTableFeature((EReference) list.getElementType());
			}
			tableViewer.getControl().addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent arg0) {
					cellEditor.dispose();
				}
			});
			if (ECPCellEditorComparator.class.isInstance(cellEditor)) {
				columnIndexToComparatorMap.put(indexOfColumn, ECPCellEditorComparator.class.cast(cellEditor));
			}
			observableSupport = new ECPTableEditingSupport(tableViewer, cellEditor, dmr, valueProperty);
			initialized = true;
		}

		@Override
		public CellLabelProvider createCellLabelProvider(AbstractTableViewer table) {
			if (!initialized) {
				init(table);
			}
			return TableControlSWTRenderer.this.createCellLabelProvider(eStructuralFeature, cellEditor, observableMap,
				getVElement(), dmr, table.getControl());
		}
	}

	/**
	 * {@link TableViewerCreator} for the table control swt renderer. It will create a GridTableViewer with the expected
	 * custom variant data and the correct style properties as defined in the template model.
	 *
	 * @since 1.10
	 *
	 */
	protected class TableControlSWTRendererTableViewerCreator implements TableViewerCreator<TableViewer> {

		@Override
		public TableViewer createTableViewer(Composite parent) {
			final TableViewer tableViewer = new TableViewer(parent,
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

			/* manage editing support activation */
			createTableViewerEditor(tableViewer);
			return tableViewer;
		}

		/**
		 * This method creates and initialises a {@link TableViewerEditor} for the given {@link TableViewer}.
		 *
		 * @param tableViewer the table viewer
		 */
		protected void createTableViewerEditor(final TableViewer tableViewer) {
			final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tableViewer,
				new org.eclipse.emf.ecp.edit.internal.swt.controls.ECPFocusCellDrawHighlighter(tableViewer));
			final ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(
				tableViewer) {
				@Override
				protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
					return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
						|| event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
				}
			};
			TableViewerEditor.create(
				tableViewer,
				focusCellManager,
				actSupport,
				ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
					| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
		}
	}

	/**
	 * {@link org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder TableViewerCompositeBuilder} which calls
	 * the existing template method to create the validation label.
	 *
	 * @since 1.13
	 *
	 */
	protected class TableControlSWTRendererCompositeBuilder
		extends org.eclipse.emfforms.spi.swt.table.DefaultTableViewerCompositeBuilder {
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
	 * {@link org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder TableViewerCompositeBuilder} which calls
	 * the existing template method to create the validation label.
	 *
	 * @since 1.14
	 *
	 */
	protected class CompactVerticallyTableControlSWTRendererCompositeBuilder
		extends org.eclipse.emfforms.spi.swt.table.CompactVerticallyTableViewerCompositeBuilder {
		/**
		 * Constructor.
		 *
		 * @param createTitleLabel indicates whether to create a title label.
		 * @param createValidationLabel indicates whether to create a validation label.
		 */
		public CompactVerticallyTableControlSWTRendererCompositeBuilder(
			boolean createTitleLabel, boolean createValidationLabel) {
			super(createTitleLabel, createValidationLabel);
		}

		@Override
		protected Label createValidationLabel(Composite composite) {
			final Label validationLabel = createValidationIcon(composite);
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
	 * @since 1.10
	 *
	 */
	protected class ECPTableViewerComparator extends ViewerComparator implements TableViewerComparator {
		private int propertyIndex;
		private static final int NONE = 0;
		private int direction = NONE;

		/** Constructs a new instance. */
		ECPTableViewerComparator() {
			propertyIndex = 0;
			direction = NONE;
		}

		@Override
		public int getDirection() {
			switch (direction) {
			case 0:
				return SWT.NONE;
			case 1:
				return SWT.DOWN; // ascending
			case 2:
				return SWT.UP; // descending
			default:
				return SWT.NONE;
			}

		}

		@Override
		public void setColumn(int column) {
			if (column == propertyIndex) {
				// Same column as last sort; toggle the direction
				direction = (direction + 1) % 3;
			} else {
				// New column; do an ascending sort
				propertyIndex = column;
				direction = 1;
			}
			// No sorting is the same as no column being selected for sorting
			if (direction == NONE) {
				sortColumnFeature = java.util.Optional.empty();
			} else {
				// columnFeatures starts at index 0 with the first regular column
				sortColumnFeature = java.util.Optional.of(columnFeatures[propertyIndex - regularColumnsStartIndex]);
			}
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			return TableControlSWTRenderer.this.compare(viewer, e1, e2, direction, propertyIndex);
		}
	}

	/**
	 * Domain model change listener that re-sorts the table and reveals the added resp. changed object.
	 *
	 * @since 1.20
	 */
	protected class AutoSortModelChangeListener implements ModelChangeListener {

		@Override
		public void notifyChange(ModelChangeNotification notification) {
			final int event = notification.getRawNotification().getEventType();
			if (notification.getStructuralFeature() == tableEReference
				&& sortColumnFeature.isPresent()
				&& (event == Notification.ADD || event == Notification.ADD_MANY)) {
				sortAndReveal(notification.getNewEObjects());
			} else if (sortColumnFeature.isPresent()
				&& notification.getStructuralFeature() == sortColumnFeature.get()) {
				sortAndReveal(notification.getNotifier());
			}
		}

		private void sortAndReveal(Object toReveal) {
			Display.getDefault().asyncExec(() -> {
				getTableViewer().refresh();
				getTableViewer().reveal(toReveal);
			});
		}
	}

	/**
	 * ECP specific cell label provider that does also implement {@link IColorProvider} in
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
		private final Control table;

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
		 * @since 1.10
		 */
		public ECPCellLabelProvider(EStructuralFeature feature, CellEditor cellEditor,
			@SuppressWarnings("rawtypes") IObservableMap attributeMap,
			VTableControl vTableControl, VDomainModelReference dmr, Control table) {
			super(attributeMap);
			this.vTableControl = vTableControl;
			this.feature = feature;
			this.cellEditor = cellEditor;
			this.dmr = dmr;
			this.table = table;
		}

		@Override
		public String getToolTipText(Object element) {
			final EObject domainObject = (EObject) element;

			final Optional<Setting> setting = getSettingFromObservable(dmr, domainObject);
			if (!setting.isPresent()) {
				return null;
			}

			final VDiagnostic vDiagnostic = vTableControl.getDiagnostic();
			if (vDiagnostic != null) {
				final String message = DiagnosticMessageExtractor.getMessage(vDiagnostic.getDiagnostic(domainObject,
					feature));
				if (message != null && !message.isEmpty()) {
					return ECPTooltipModifierHelper.modifyString(message, setting.get());
				}
			}
			final Object value = setting.get().get(true);
			if (value == null) {
				return null;
			}
			final String tooltip = ECPTooltipModifierHelper.modifyString(String.valueOf(value), setting.get());
			if (tooltip == null || tooltip.isEmpty()) {
				return null;
			}
			return tooltip;

		}

		@Override
		public void update(ViewerCell cell) {
			final EObject element = (EObject) cell.getElement();
			final Object value = attributeMaps[0].get(element);
			if (ECPCustomUpdateCellEditor.class.isInstance(cellEditor)) {
				((ECPCustomUpdateCellEditor) cellEditor).updateCell(cell, value);
			} else if (ECPCellEditor.class.isInstance(cellEditor)) {
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
			return getValidationBackgroundColor(diagnostic.size() == 0 ? Diagnostic.OK
				: diagnostic.get(0)
					.getSeverity());
		}

		/**
		 * @return the cellEditor
		 */
		protected CellEditor getCellEditor() {
			return cellEditor;
		}

		/**
		 * @return the feature
		 */
		protected EStructuralFeature getFeature() {
			return feature;
		}

		/**
		 * @return the dmr
		 */
		protected VDomainModelReference getDmr() {
			return dmr;
		}
	}

	/**
	 * Implementation of the {@link EditingSupport} for the generic ECP Table.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	class ECPTableEditingSupport extends EditingSupport {

		private final CellEditor cellEditor;

		@SuppressWarnings("rawtypes")
		private final IValueProperty valueProperty;

		private final VDomainModelReference domainModelReference;

		/**
		 * Initializes me.
		 *
		 * @param viewer the viewer to edit
		 * @param cellEditor the cell editor used to edit the {@code viewer}
		 * @param domainModelReference the model reference that populates the {@code viewer}
		 * @param valueProperty the value property that supplies the {@code viewer}
		 */
		ECPTableEditingSupport(ColumnViewer viewer, CellEditor cellEditor, VDomainModelReference domainModelReference,
			IValueProperty<?, ?> valueProperty) {
			super(viewer);
			this.cellEditor = cellEditor;
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
			if (!shouldCreateCellEditor(element)) {
				return false;
			}

			// TODO: use getSettingFromObservable(dmr, eObject) instead?
			@SuppressWarnings("unchecked")
			final IObservableValue<?> observableValue = valueProperty.observe(element);
			final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();

			final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();

			// Given EClass has no such feature
			if (eObject == null || eObject.eClass().getFeatureID(structuralFeature) == -1) {
				return false;
			}

			final Setting setting = ((InternalEObject) eObject).eSetting(structuralFeature);

			if (isDisabled(eObject, domainModelReference)
				|| CellReadOnlyTesterHelper.getInstance().isReadOnly(getVElement(), setting)) {
				return false;
			}

			final boolean editable = emfFormsEditSupport.canSetProperty(domainModelReference, (EObject) element);

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

			final IObservableValue<?> target = doCreateCellEditorObservable(cellEditor);
			Assert.isNotNull(target, "doCreateCellEditorObservable(...) did not return an observable"); //$NON-NLS-1$

			@SuppressWarnings("unchecked")
			final IObservableValue<?> model = valueProperty.observe(cell.getElement());
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

		/**
		 * Bind a {@code model} value to a {@code target} value.
		 *
		 * @param target the binding target
		 * @param model the binding source
		 * @return the binding
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		protected Binding createBinding(IObservableValue target, IObservableValue model) {
			if (ECPCellEditor.class.isInstance(cellEditor)) {
				return getDataBindingContext().bindValue(target, model,
					((ECPCellEditor) cellEditor).getTargetToModelStrategy(getDataBindingContext()),
					((ECPCellEditor) cellEditor).getModelToTargetStrategy(getDataBindingContext()));
			}
			return getDataBindingContext().bindValue(target, model);
		}

		/**
		 * Create an observable value to supply the cells edited by a cell editor.
		 *
		 * @param cellEditor a cell editor
		 * @return the observable value to supply it with data to edit
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		protected IObservableValue doCreateCellEditorObservable(CellEditor cellEditor) {
			if (ECPCellEditor.class.isInstance(cellEditor)) {
				return ((ECPCellEditor) cellEditor).getValueProperty().observe(cellEditor);
			}
			return WidgetProperties.text(SWT.FocusOut).observe(cellEditor.getControl());
		}

		@Override
		protected final void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
			if (editingState.isUpdateNeeded()) {
				editingState.binding.updateTargetToModel();
			}
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

			private final IObservableValue<?> target;
			private final IObservableValue<?> model;
			private final Binding binding;

			/**
			 * Initializes me with the data binding details involved in editing.
			 *
			 * @param binding the data binding
			 * @param target the bound target value
			 * @param model the bound model value
			 */
			EditingState(Binding binding, IObservableValue<?> target, IObservableValue<?> model) {
				this.binding = binding;
				this.target = target;
				this.model = model;
			}

			/**
			 * Dispose the data binding that I maintain.
			 */
			void dispose() {
				binding.dispose();
				target.dispose();
				model.dispose();
			}

			/**
			 * Checks if an update is really needed.
			 *
			 * @return <code>true</code> if update is really needed, <code>false</code> otherwise.
			 */
			boolean isUpdateNeeded() {
				final Object targetValue = target.getValue();
				final Object modelValue = model.getValue();

				if (targetValue == null) {
					return modelValue != null;
				}
				return !targetValue.equals(modelValue);
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

	private String formatTooltipText(EClass eClass, String messageKey) {
		// final EClass clazz = ((EReference) setting.getEStructuralFeature()).getEReferenceType();
		final String instanceName = eClass.getInstanceClass() == null ? "" : eClass.getInstanceClass().getSimpleName(); //$NON-NLS-1$
		return String.format(LocalizationServiceHelper.getString(
			TableControlSWTRenderer.class, messageKey),
			instanceName);
	}

}
