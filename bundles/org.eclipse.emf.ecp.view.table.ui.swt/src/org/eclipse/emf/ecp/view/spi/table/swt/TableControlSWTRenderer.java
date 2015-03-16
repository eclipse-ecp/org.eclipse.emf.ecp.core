/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Johannes Faltermeier - refactorings
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.edit.internal.swt.controls.ECPFocusCellDrawHighlighter;
import org.eclipse.emf.ecp.edit.internal.swt.controls.TableViewerColumnBuilder;
import org.eclipse.emf.ecp.edit.internal.swt.util.CellEditorFactory;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.internal.table.swt.CellReadOnlyTesterHelper;
import org.eclipse.emf.ecp.view.internal.table.swt.MessageKeys;
import org.eclipse.emf.ecp.view.internal.table.swt.TableConfigurationHelper;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.DiagnosticMessageExtractor;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapCellLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.osgi.framework.FrameworkUtil;

/**
 * SWT Renderer for Table Control.
 *
 * @author Eugen Neufeld
 *
 */
public class TableControlSWTRenderer extends AbstractControlSWTRenderer<VTableControl> {
	private SWTGridDescription rendererGridDescription;
	private static final String FIXED_COLUMNS = "org.eclipse.rap.rwt.fixedColumns"; //$NON-NLS-1$

	private static final String ICON_ADD = "icons/add.png"; //$NON-NLS-1$
	private static final String ICON_DELETE = "icons/delete.png"; //$NON-NLS-1$

	private TableViewer tableViewer;

	private Label validationIcon;
	private Button addButton;
	private Button removeButton;
	private final ImageRegistryService imageRegistryService;

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
	 */
	public TableControlSWTRenderer(VTableControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, ImageRegistryService imageRegistryService) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.imageRegistryService = imageRegistryService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(int, org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected Control renderControl(SWTGridCell gridCell, final Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		IObservableValue observableValue;
		try {
			observableValue = getEMFFormsDatabinding()
				.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new RenderingFailedReport(ex));
			return null;
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();

		final EClass clazz = ((EReference) structuralFeature).getEReferenceType();

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setBackground(parent.getBackground());

		final Composite titleComposite = new Composite(composite, SWT.NONE);
		titleComposite.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING)
			.applyTo(titleComposite);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(titleComposite);

		// TODO discuss
		// final Control label = createLabel(titleComposite);

		final Label label = new Label(titleComposite, SWT.NONE);
		label.setBackground(parent.getBackground());

		final EMFFormsLabelProvider labelService = getEMFFormsLabelProvider();
		final String labelText = labelService.getDisplayName(getVElement().getDomainModelReference(),
			getViewModelContext().getDomainModel());
		label.setText(labelText);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(label);

		// VALIDATION
		// final Label validationLabel = new Label(titleComposite, SWT.NONE);
		// validationLabel.setBackground(parent.getBackground());
		// // set the size of the label to the size of the image
		validationIcon = createValidationIcon(titleComposite);
		GridDataFactory.fillDefaults().hint(16, 17).grab(false, false).applyTo(validationIcon);

		Button addButton = null;
		Button removeButton = null;
		final Composite buttonComposite = new Composite(titleComposite, SWT.NONE);
		buttonComposite.setBackground(titleComposite.getBackground());
		GridDataFactory.fillDefaults().align(SWT.END, SWT.BEGINNING).grab(true, false).applyTo(buttonComposite);
		int numButtons = addButtonsToButtonBar(buttonComposite);
		if (!getVElement().isAddRemoveDisabled()) {
			// addButtons
			addButton = createAddRowButton(clazz, buttonComposite, eObject, structuralFeature);
			removeButton = createRemoveRowButton(clazz, buttonComposite, eObject, structuralFeature);
			numButtons = numButtons + 2;
		}

		GridLayoutFactory.fillDefaults().numColumns(numButtons).equalWidth(false).applyTo(buttonComposite);
		final Composite controlComposite = createControlComposite(composite);
		try {
			setTableViewer(createTableViewer(controlComposite, clazz));
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new RenderingFailedReport(ex));
			final Label errorLabel = new Label(parent, SWT.NONE);
			errorLabel.setText(ex.getMessage());
			return errorLabel;

		}

		if (addButton != null && removeButton != null) {
			final Button finalAddButton = addButton;
			final Button finalRemoveButton = removeButton;
			addButton.addSelectionListener(new SelectionAdapter() {

				/*
				 * (non-Javadoc)
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					addRow(clazz, eObject, structuralFeature);

					final List<?> containments = (List<?>) eObject.eGet(structuralFeature, true);
					if (structuralFeature.getUpperBound() != -1
						&& containments.size() >= structuralFeature.getUpperBound()) {
						finalAddButton.setEnabled(false);
					}
					if (containments.size() > structuralFeature.getLowerBound()) {
						finalRemoveButton.setEnabled(true);
					}
				}
			});
			removeButton.addSelectionListener(new SelectionAdapter() {
				/*
				 * (non-Javadoc)
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();

					if (selection == null || selection.getFirstElement() == null) {
						return;
					}

					final List<EObject> deletionList = new ArrayList<EObject>();
					final Iterator<?> iterator = selection.iterator();

					while (iterator.hasNext()) {
						deletionList.add((EObject) iterator.next());
					}

					deleteRowUserConfirmDialog(deletionList, eObject, structuralFeature, finalAddButton,
						finalRemoveButton);

				}
			});
		}

		return composite;
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
	 * Returns the prefereed height for the table. This will be passed to the layoutdata.
	 *
	 * @return the height in px
	 */
	protected int getTableHeightHint() {
		return 200;
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

	private TableViewer createTableViewer(Composite composite, EClass clazz) throws DatabindingFailedException {

		final TableViewer tableViewer = new TableViewer(composite, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION
			| SWT.BORDER);
		tableViewer.getTable().setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_table"); //$NON-NLS-1$
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tableViewer,
			new ECPFocusCellDrawHighlighter(tableViewer));
		final ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
					|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
					|| event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR
					|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};

		TableViewerEditor.create(tableViewer, focusCellManager, actSupport, ColumnViewerEditor.TABBING_HORIZONTAL
			| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR | ColumnViewerEditor.TABBING_VERTICAL
			| ColumnViewerEditor.KEYBOARD_ACTIVATION);

		tableViewer.getTable().setData(FIXED_COLUMNS, new Integer(1));
		ColumnViewerToolTipSupport.enableFor(tableViewer);

		final ObservableListContentProvider cp = new ObservableListContentProvider();
		InternalEObject tempInstance = null;
		if (!clazz.isAbstract() && !clazz.isInterface()) {
			tempInstance = getInstanceOf(clazz);
		}
		final ECPTableViewerComparator comparator = new ECPTableViewerComparator();
		tableViewer.setComparator(comparator);
		int columnNumber = 0;

		// final Map<EStructuralFeature, Boolean> readOnlyConfig = createReadOnlyConfig(clazz,
		// tableControlConfiguration);
		// final List<EStructuralFeature> structuralFeatures = new ArrayList<EStructuralFeature>();
		// structuralFeatures.addAll(readOnlyConfig.keySet());
		if (!getVElement().isReadonly()) {
			createFixedValidationStatusColumn(tableViewer);
		}
		final VTableDomainModelReference tableDomainModelReference = VTableDomainModelReference.class.cast(
			getVElement().getDomainModelReference());
		for (final VDomainModelReference dmr : tableDomainModelReference.getColumnDomainModelReferences()) {
			if (dmr == null) {
				continue;
			}

			IValueProperty valueProperty;
			try {
				valueProperty = getEMFFormsDatabinding().getValueProperty(dmr);
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				continue;
			}
			final EStructuralFeature eStructuralFeature = (EStructuralFeature) valueProperty.getValueType();

			final EMFFormsLabelProvider labelService = getEMFFormsLabelProvider();
			final String text = labelService.getDisplayName(dmr);
			final String tooltipText = labelService.getDescription(dmr);

			final CellEditor cellEditor = createCellEditor(tempInstance, eStructuralFeature, tableViewer.getTable());
			final TableViewerColumn column = TableViewerColumnBuilder
				.create()
				.setText(text)
				.setToolTipText(tooltipText)
				.setResizable(true)
				.setMoveable(false)
				.setStyle(SWT.NONE)
				.setData("width", //$NON-NLS-1$
					ECPCellEditor.class.isInstance(cellEditor) ? ECPCellEditor.class.cast(cellEditor)
						.getColumnWidthWeight() : 100)
				.build(tableViewer);

			final IObservableMap observableMap = valueProperty.observeDetail(cp.getKnownElements());
			column.setLabelProvider(new ECPCellLabelProvider(eStructuralFeature, cellEditor, observableMap,
				getVElement(), dmr));
			column.getColumn().addSelectionListener(
				getSelectionAdapter(tableViewer, comparator, column.getColumn(), columnNumber));

			if (!TableConfigurationHelper.isReadOnly(getVElement(), dmr)) {
				// remove if no editing needed
				final EditingSupport observableSupport = new ECPTableEditingSupport(tableViewer, cellEditor,
					// eStructuralFeature,
					// itemPropertyDescriptor
					// null,
					getVElement(), dmr, valueProperty);
				column.setEditingSupport(observableSupport);
			}
			columnNumber++;
		}
		tableViewer.setContentProvider(cp);
		final IObservableList list = getEMFFormsDatabinding()
			.getObservableList(tableDomainModelReference, getViewModelContext().getDomainModel());
		tableViewer.setInput(list);

		// IMPORTANT:
		// - the minimumWidth and (non)resizable settings of the ColumnWeightData are not supported properly
		// - the layout stops resizing columns that have been resized manually by the user (this could be considered a
		// feature though)
		final TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);
		for (int i = 0; i < tableViewer.getTable().getColumns().length; i++) {
			final Integer storedValue = (Integer) tableViewer.getTable().getColumns()[i].getData("width"); //$NON-NLS-1$
			layout.setColumnData(tableViewer.getTable().getColumns()[i], new ColumnWeightData(storedValue == null ? 50
				: storedValue));
		}

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				viewerSelectionChanged(event);
			}
		});
		return tableViewer;
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
		if (event.getSelection().isEmpty()) {
			if (removeButton != null) {
				removeButton.setEnabled(false);
			}
		}
		else {
			if (removeButton != null) {
				removeButton.setEnabled(true);
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

	private void createFixedValidationStatusColumn(TableViewer tableViewer) {
		final VTTableValidationStyleProperty tableValidationStyleProperty = getTableValidationStyleProperty();
		final int columnWidth = tableValidationStyleProperty.getColumnWidth();
		final String columnName = tableValidationStyleProperty.getColumnName();
		final String imagePath = tableValidationStyleProperty.getImagePath();
		final TableViewerColumn column = TableViewerColumnBuilder.create()
			.setMoveable(false)
			.setText(columnName)
			.setData("width", columnWidth) //$NON-NLS-1$
			.build(tableViewer);

		if (imagePath != null && !imagePath.isEmpty()) {
			Image image = null;
			try {
				image = getImage(new File(imagePath).toURI().toURL());
			} catch (final MalformedURLException ex) {
				ex.printStackTrace();
			}
			if (image != null) {
				column.getColumn().setImage(image);
			}
		}
		column.setLabelProvider(new ValidationStatusCellLabelProvider(getVElement()));
	}

	/**
	 * Retrieve images from the {@link ImageRegistryService} using an {@link URL}.
	 *
	 * @param url The {@link URL} pointing to the image
	 * @return The retrieved Image
	 */
	protected Image getImage(URL url) {
		return imageRegistryService.getImage(url);
	}

	/**
	 * Retrieve images from the {@link ImageRegistryService} using a bundle relative path.
	 *
	 * @param path The bundle relative path pointing to the image
	 * @return The retrieved Image
	 */
	protected Image getImage(String path) {
		return imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()), path);
	}

	private VTTableValidationStyleProperty getTableValidationStyleProperty() {
		VTTableValidationStyleProperty tableValidationStyleProperties;
		final Set<VTStyleProperty> styleProperties = getVTViewTemplateProvider()
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTTableValidationStyleProperty.class.isInstance(styleProperty)) {
				tableValidationStyleProperties = VTTableValidationStyleProperty.class
					.cast(styleProperty);
				return tableValidationStyleProperties;
			}
		}

		tableValidationStyleProperties = getDefaultTableValidationStyleProperty();
		return tableValidationStyleProperties;
	}

	private VTTableValidationStyleProperty getDefaultTableValidationStyleProperty() {
		final VTTableValidationStyleProperty tableValidationProp = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();
		tableValidationProp.setColumnWidth(80);
		tableValidationProp.setColumnName(LocalizationServiceHelper.getString(getClass(),
			MessageKeys.TableControl_ValidationStatusColumn));
		tableValidationProp.setImagePath(null);
		return tableValidationProp;
	}

	private CellEditor createCellEditor(final EObject tempInstance, final EStructuralFeature feature, Table table) {
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
			LocalizationServiceHelper.getString(getClass(), MessageKeys.TableControl_RemoveSelected)
			, instanceName));

		final List<?> containments = (List<?>) eObject.eGet(structuralFeature, true);
		if (containments.size() <= structuralFeature.getLowerBound()) {
			removeButton.setEnabled(false);
		}
		return removeButton;
	}

	private Button createAddRowButton(final EClass clazz, final Composite buttonComposite, final EObject eObject,
		final EStructuralFeature structuralFeature) {
		addButton = new Button(buttonComposite, SWT.None);
		final Image image = getImage(ICON_ADD);
		addButton.setImage(image);
		final String instanceName = clazz.getInstanceClass() == null ? "" : clazz.getInstanceClass().getSimpleName(); //$NON-NLS-1$
		addButton.setToolTipText(String.format(
			LocalizationServiceHelper.getString(getClass(), MessageKeys.TableControl_AddInstanceOf), instanceName));

		final List<?> containments = (List<?>) eObject.eGet(structuralFeature, true);
		if (structuralFeature.getUpperBound() != -1 && containments.size() >= structuralFeature.getUpperBound()) {
			addButton.setEnabled(false);
		}
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
	 */
	protected void deleteRowUserConfirmDialog(final List<EObject> deletionList, final EObject eObject,
		final EStructuralFeature structuralFeature, final Button addButton, final Button removeButton) {
		final MessageDialog dialog = new MessageDialog(addButton.getShell(),
			LocalizationServiceHelper.getString(getClass(), MessageKeys.TableControl_Delete), null,
			LocalizationServiceHelper.getString(getClass(), MessageKeys.TableControl_DeleteAreYouSure),
			MessageDialog.CONFIRM, new String[] {
				JFaceResources.getString(IDialogLabelKeys.YES_LABEL_KEY),
				JFaceResources.getString(IDialogLabelKeys.NO_LABEL_KEY) }, 0);

		new ECPDialogExecutor(dialog) {

			@Override
			public void handleResult(int codeResult) {
				if (codeResult == IDialogConstants.CANCEL_ID) {
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
	 */
	protected void deleteRows(List<EObject> deletionList, final EObject eObject,
		final EStructuralFeature structuralFeature) {
		final EditingDomain editingDomain = getEditingDomain(eObject);
		editingDomain.getCommandStack().execute(
			RemoveCommand.create(editingDomain, eObject, structuralFeature, deletionList));
	}

	/**
	 * This method is called to add a new entry in the domain model and thus to create a new row in the table. The
	 * element to create is defined by the provided class.
	 * You can override this method but you have to call super nonetheless.
	 *
	 * @param clazz the {@link EClass} defining the EObject to create
	 * @param eObject The containment reference {@link EObject}
	 * @param structuralFeature The containment reference {@link EStructuralFeature}
	 */
	protected void addRow(EClass clazz, EObject eObject, EStructuralFeature structuralFeature) {
		if (clazz.isAbstract() || clazz.isInterface()) {
			getReportService().report(new StatusReport(
				new Status(IStatus.WARNING, "org.eclipse.emf.ecp.view.table.ui.swt", //$NON-NLS-1$
					String.format("The class %1$s is abstract or an interface.", clazz.getName())))); //$NON-NLS-1$
		}
		final EObject instance = clazz.getEPackage().getEFactoryInstance().create(clazz);
		final EditingDomain editingDomain = getEditingDomain(eObject);
		if (editingDomain == null) {
		}
		editingDomain.getCommandStack().execute(
			AddCommand.create(editingDomain, eObject, structuralFeature, instance));
	}

	@Override
	protected void applyValidation() {
		Display.getDefault().asyncExec(new Runnable() {

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
					observableValue = getEMFFormsDatabinding()
						.getObservableValue(getVElement().getDomainModelReference(),
							getViewModelContext().getDomainModel());
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
					return;
				}
				final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
				final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();

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
		});

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#applyEnable()
	 */
	@Override
	protected void applyEnable() {
		if (addButton != null) {
			addButton.setVisible(getVElement().isEnabled() && !getVElement().isReadonly());
		}
		if (removeButton != null) {
			removeButton.setVisible(getVElement().isEnabled() && !getVElement().isReadonly());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#applyReadOnly()
	 */
	@Override
	protected void applyReadOnly() {
		if (addButton != null) {
			addButton.setVisible(getVElement().isEnabled() && !getVElement().isReadonly());
		}
		if (removeButton != null) {
			removeButton.setVisible(getVElement().isEnabled() && !getVElement().isReadonly());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		super.dispose();
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

		public ECPTableViewerComparator() {
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
			if (direction == 0) {
				return 0;
			}
			int rc = 0;
			final EObject object1 = (EObject) e1;
			final EObject object2 = (EObject) e2;

			Object value1;
			Object value2;

			final VDomainModelReference dmr = ((VTableDomainModelReference) getVElement().getDomainModelReference())
				.getColumnDomainModelReferences().get(propertyIndex);
			final EMFFormsDatabinding emfFormsDatabinding = getEMFFormsDatabinding();

			try {
				final IObservableValue observableValue1 = emfFormsDatabinding.getObservableValue(dmr, object1);
				final EStructuralFeature structuralFeature1 = (EStructuralFeature) observableValue1.getValueType();
				final EObject observed1 = (EObject) ((IObserving) observableValue1).getObserved();
				value1 = observed1.eGet(structuralFeature1, true);
			} catch (final DatabindingFailedException ex) {
				value1 = null;
			}

			try {
				final IObservableValue observableValue2 = emfFormsDatabinding.getObservableValue(dmr, object2);
				final EStructuralFeature structuralFeature2 = (EStructuralFeature) observableValue2.getValueType();
				final EObject observed2 = (EObject) ((IObserving) observableValue2).getObserved();
				value2 = observed2.eGet(structuralFeature2, true);
			} catch (final DatabindingFailedException ex) {
				value2 = null;
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
		 */
		public ECPCellLabelProvider(EStructuralFeature feature, CellEditor cellEditor, IObservableMap attributeMap,
			VTableControl vTableControl, VDomainModelReference dmr) {
			super(attributeMap);
			this.vTableControl = vTableControl;
			this.feature = feature;
			this.cellEditor = cellEditor;
			this.dmr = dmr;
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

			cell.setBackground(getBackground(element));
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
		 */
		@Override
		public Color getForeground(Object element) {
			return null;
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

		/**
		 * @param viewer
		 */
		public ECPTableEditingSupport(ColumnViewer viewer, CellEditor cellEditor,
			VTableControl tableControl, VDomainModelReference domainModelReference, IValueProperty valueProperty) {
			super(viewer);
			this.cellEditor = cellEditor;
			this.tableControl = tableControl;
			this.valueProperty = valueProperty;
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

			editable &= getItemPropertyDescriptor(eObject, structuralFeature).canSetProperty(null);
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
					((ECPCellEditor) cellEditor).getTargetToModelStrategy(),
					((ECPCellEditor) cellEditor).getModelToTargetStrategy());
			}
			return getDataBindingContext().bindValue(target, model);
		}

		protected IObservableValue doCreateCellEditorObservable(CellEditor cellEditor) {
			if (ECPCellEditor.class.isInstance(cellEditor)) {
				return ((ECPCellEditor) cellEditor).getValueProperty().observe(cellEditor);
			}
			return SWTObservables.observeText(cellEditor.getControl(), SWT.FocusOut);
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
				// do nothing
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

		public ValidationStatusCellLabelProvider(
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
			final String message = DiagnosticMessageExtractor.getMessage(vDiagnostic.getDiagnostics((EObject) element));
			return ECPTooltipModifierHelper.modifyString(message, null);
		}
	}
}
