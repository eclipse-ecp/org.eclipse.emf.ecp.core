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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.EObjectObservableMap;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.edit.internal.swt.controls.ControlMessages;
import org.eclipse.emf.ecp.edit.internal.swt.controls.ECPFocusCellDrawHighlighter;
import org.eclipse.emf.ecp.edit.internal.swt.controls.TableViewerColumnBuilder;
import org.eclipse.emf.ecp.edit.internal.swt.table.TableColumnConfiguration;
import org.eclipse.emf.ecp.edit.internal.swt.table.TableControlConfiguration;
import org.eclipse.emf.ecp.edit.internal.swt.util.CellEditorFactory;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.internal.table.swt.CellReadOnlyTesterHelper;
import org.eclipse.emf.ecp.view.internal.table.swt.TableConfigurationHelper;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapCellLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

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
	private Button detailEditButton;

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
		final Iterator<Setting> settings = getVElement().getDomainModelReference().getIterator();
		if (!settings.hasNext()) {
			return null;
		}

		final Setting mainSetting = settings.next();

		final EClass clazz = ((EReference) mainSetting.getEStructuralFeature()).getEReferenceType();

		final TableControlConfiguration tableControlConfiguration = new TableControlConfiguration();
		tableControlConfiguration.setAddRemoveDisabled(getVElement().isAddRemoveDisabled());

		for (final VDomainModelReference column : VTableDomainModelReference.class.cast(
			getVElement().getDomainModelReference()).getColumnDomainModelReferences()) {
			final boolean readOnly = TableConfigurationHelper.isReadOnly(getVElement(), column);
			tableControlConfiguration.getColumns().add(
				new TableColumnConfiguration(readOnly, (EAttribute) column.getEStructuralFeatureIterator().next()));
		}

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
		final IItemPropertyDescriptor propDescriptor = getItemPropertyDescriptor(mainSetting);
		String labelText = ""; //$NON-NLS-1$
		if (propDescriptor != null) {
			labelText = propDescriptor.getDisplayName(null);
		}
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
		int numButtons = 2;
		if (getVElement().isEnableDetailEditingDialog()) {
			numButtons++;
			createDetailEditButton(buttonComposite);
		}
		if (!tableControlConfiguration.isAddRemoveDisabled()) {
			// addButtons
			addButton = createAddRowButton(clazz, buttonComposite, mainSetting);
			removeButton = createRemoveRowButton(clazz, buttonComposite, mainSetting);
		}
		else {
			numButtons -= 2;
		}
		GridLayoutFactory.fillDefaults().numColumns(numButtons).equalWidth(false).applyTo(buttonComposite);
		final Composite controlComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL)
			.hint(1, 200)
			.applyTo(controlComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(controlComposite);
		tableViewer = createTableViewer(controlComposite, clazz,
			mainSetting, tableControlConfiguration);

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
					addRow(clazz, mainSetting);

					final List<?> containments = (List<?>) mainSetting.get(true);
					if (mainSetting.getEStructuralFeature().getUpperBound() != -1
						&& containments.size() >= mainSetting.getEStructuralFeature().getUpperBound()) {
						finalAddButton.setEnabled(false);
					}
					if (containments.size() > mainSetting.getEStructuralFeature().getLowerBound()) {
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

					deleteRowUserConfirmDialog(deletionList, mainSetting, finalAddButton, finalRemoveButton);

				}
			});
		}

		return composite;
	}

	private void createDetailEditButton(final Composite buttonComposite) {
		detailEditButton = new Button(buttonComposite, SWT.PUSH);
		// detailEditButton.setText("Edit in Detail");
		detailEditButton.setImage(Activator.getImage("icons/detailEdit.png")); //$NON-NLS-1$
		detailEditButton.setEnabled(false);
		detailEditButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				if (tableViewer.getSelection().isEmpty()) {
					final MessageDialog dialog = new MessageDialog(buttonComposite.getShell(),
						"No Table Selection", null, //$NON-NLS-1$
						"You must select one element in order to edit it.", MessageDialog.WARNING, new String[] { //$NON-NLS-1$
						JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);

					new ECPDialogExecutor(dialog) {

						@Override
						public void handleResult(int codeResult) {

						}
					}.execute();
				} else {
					openDetailEditDialog(buttonComposite.getShell());
				}
			}

		});
	}

	private void openDetailEditDialog(Shell shell) {
		final Dialog dialog = new DetailDialog(shell, (EObject) IStructuredSelection.class.cast(
			tableViewer.getSelection()).getFirstElement(), getVElement());

		new ECPDialogExecutor(dialog) {

			@Override
			public void handleResult(int codeResult) {

			}
		}.execute();

	}

	private TableViewer createTableViewer(Composite composite, EClass clazz,
		Setting mainSetting,
		TableControlConfiguration tableControlConfiguration) {

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
		final InternalEObject tempInstance = getInstanceOf(clazz);
		final ECPTableViewerComparator comparator = new ECPTableViewerComparator();
		tableViewer.setComparator(comparator);
		int columnNumber = 0;
		final Map<EStructuralFeature, Boolean> readOnlyConfig = createReadOnlyConfig(clazz, tableControlConfiguration);
		final List<EStructuralFeature> structuralFeatures = new ArrayList<EStructuralFeature>();
		structuralFeatures.addAll(readOnlyConfig.keySet());
		if (!getVElement().isReadonly()) {
			createFixedValidationStatusColumn(tableViewer);
		}

		for (final EStructuralFeature feature : structuralFeatures) {
			if (feature == null) {
				continue;
			}
			final IItemPropertyDescriptor itemPropertyDescriptor = getItemPropertyDescriptor(tempInstance
				.eSetting(feature));
			if (itemPropertyDescriptor == null) {
				// if we can't render because no edit information is available, do nothing
				continue;
			}

			final CellEditor cellEditor = createCellEditor(tempInstance, feature, tableViewer.getTable());
			final TableViewerColumn column = TableViewerColumnBuilder
				.create()
				.setText(itemPropertyDescriptor.getDisplayName(null))
				.setToolTipText(itemPropertyDescriptor.getDescription(null))
				.setResizable(true)
				.setMoveable(false)
				.setStyle(SWT.NONE)
				.setData("width", //$NON-NLS-1$
					ECPCellEditor.class.isInstance(cellEditor) ? ECPCellEditor.class.cast(cellEditor)
						.getColumnWidthWeight() : 100)
				.build(tableViewer);

			column.setLabelProvider(new ECPCellLabelProvider(feature, cellEditor, feature.isMany() ?
				new EObjectObservableMap(cp.getKnownElements(), feature) :
				EMFProperties.value(feature).observeDetail(cp.getKnownElements()), getVElement()));
			column.getColumn().addSelectionListener(
				getSelectionAdapter(tableViewer, comparator, column.getColumn(), columnNumber));

			if (!isReadOnlyFeature(readOnlyConfig, feature)) {
				// remove if no editing needed
				final EditingSupport observableSupport = new ECPTableEditingSupport(tableViewer, cellEditor, feature,
					itemPropertyDescriptor, getVElement());
				column.setEditingSupport(observableSupport);
			}
			columnNumber++;
		}
		tableViewer.setContentProvider(cp);
		final IObservableList list = EMFEditObservables.observeList(getEditingDomain(mainSetting),
			mainSetting.getEObject(), mainSetting.getEStructuralFeature());
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
				if (event.getSelection().isEmpty()) {
					if (detailEditButton != null) {
						detailEditButton.setEnabled(false);
					}
					if (removeButton != null) {
						removeButton.setEnabled(false);
					}
				}
				else {
					if (detailEditButton != null && IStructuredSelection.class.cast(event.getSelection()).size() == 1) {
						detailEditButton.setEnabled(true);
					}
					if (removeButton != null) {
						removeButton.setEnabled(true);
					}
				}
			}
		});
		return tableViewer;
	}

	private static boolean isReadOnlyFeature(Map<EStructuralFeature, Boolean> readOnlyConfig, EStructuralFeature feature) {
		if (readOnlyConfig != null) {
			final Boolean isReadOnly = readOnlyConfig.get(feature);
			if (isReadOnly != null) {
				return isReadOnly;
			}
		}
		return false;
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
		final TableViewerColumn column = TableViewerColumnBuilder.create()
			.setMoveable(false)
			.setText(ControlMessages.TableControl_ValidationStatusColumn)
			.setWidth(80)
			.build(tableViewer);

		column.setLabelProvider(new ValidationStatusCellLabelProvider(getVElement()));
	}

	private CellEditor createCellEditor(final EObject tempInstance, final EStructuralFeature feature, Table table) {
		return CellEditorFactory.INSTANCE.getCellEditor(feature,
			tempInstance, table, getViewModelContext());
	}

	private InternalEObject getInstanceOf(EClass clazz) {
		return InternalEObject.class.cast(clazz.getEPackage().getEFactoryInstance().create(clazz));
	}

	private Map<EStructuralFeature, Boolean> createReadOnlyConfig(EClass clazz,
		TableControlConfiguration tableControlConfiguration) {
		final Map<EStructuralFeature, Boolean> readOnlyConfig = new LinkedHashMap<EStructuralFeature, Boolean>();

		for (final TableColumnConfiguration tcc : tableControlConfiguration.getColumns()) {
			readOnlyConfig.put(tcc.getColumnAttribute(), tcc.isReadOnly());
		}

		return readOnlyConfig;
	}

	private Button createRemoveRowButton(EClass clazz, final Composite buttonComposite, Setting mainSetting) {
		removeButton = new Button(buttonComposite, SWT.None);
		final Image image = Activator.getImage(ICON_DELETE);
		removeButton.setImage(image);
		removeButton.setEnabled(false);
		final String instanceName = clazz.getInstanceClass() == null ? "" : clazz.getInstanceClass().getSimpleName(); //$NON-NLS-1$
		removeButton.setToolTipText(String.format(ControlMessages.TableControl_RemoveSelected
			, instanceName));

		final List<?> containments = (List<?>) mainSetting.get(true);
		if (containments.size() <= mainSetting.getEStructuralFeature().getLowerBound()) {
			removeButton.setEnabled(false);
		}
		return removeButton;
	}

	private Button createAddRowButton(final EClass clazz, final Composite buttonComposite, final Setting mainSetting) {
		addButton = new Button(buttonComposite, SWT.None);
		final Image image = Activator.getImage(ICON_ADD);
		addButton.setImage(image);
		final String instanceName = clazz.getInstanceClass() == null ? "" : clazz.getInstanceClass().getSimpleName(); //$NON-NLS-1$
		addButton.setToolTipText(String.format(ControlMessages.TableControl_AddInstanceOf, instanceName));

		final List<?> containments = (List<?>) mainSetting.get(true);
		if (mainSetting.getEStructuralFeature().getUpperBound() != -1
			&& containments.size() >= mainSetting.getEStructuralFeature().getUpperBound()) {
			addButton.setEnabled(false);
		}
		return addButton;
	}

	/**
	 * This method shows a user confirmation dialog when the user attempts to delete a row in the table.
	 * 
	 * @param deletionList the list of selected EObjects to delete
	 * @param mainSetting the containment reference setting
	 * @param addButton the add button
	 * @param removeButton the remove button
	 */
	protected void deleteRowUserConfirmDialog(final List<EObject> deletionList, final Setting mainSetting,
		final Button addButton, final Button removeButton) {
		final MessageDialog dialog = new MessageDialog(addButton.getShell(),
			ControlMessages.TableControl_Delete, null,
			ControlMessages.TableControl_DeleteAreYouSure, MessageDialog.CONFIRM, new String[] {
				JFaceResources.getString(IDialogLabelKeys.YES_LABEL_KEY),
				JFaceResources.getString(IDialogLabelKeys.NO_LABEL_KEY) }, 0);

		new ECPDialogExecutor(dialog) {

			@Override
			public void handleResult(int codeResult) {
				if (codeResult == IDialogConstants.CANCEL_ID) {
					return;
				}

				deleteRows(deletionList, mainSetting);

				final List<?> containments = (List<?>) mainSetting.get(true);
				if (containments.size() < mainSetting.getEStructuralFeature().getUpperBound()) {
					addButton.setEnabled(true);
				}
				if (containments.size() <= mainSetting.getEStructuralFeature().getLowerBound()) {
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
	 * @param mainSetting the containment reference setting
	 */
	protected void deleteRows(List<EObject> deletionList, Setting mainSetting) {
		final EObject modelElement = mainSetting.getEObject();
		final EditingDomain editingDomain = getEditingDomain(mainSetting);
		editingDomain.getCommandStack().execute(
			RemoveCommand.create(editingDomain, modelElement, mainSetting.getEStructuralFeature(), deletionList));
	}

	/**
	 * This method is called to add a new entry in the domain model and thus to create a new row in the table. The
	 * element to create is defined by the provided class.
	 * You can override this method but you have to call super nonetheless.
	 * 
	 * @param clazz the {@link EClass} defining the EObject to create
	 * @param mainSetting the containment reference setting
	 */
	protected void addRow(EClass clazz, Setting mainSetting) {
		final EObject modelElement = mainSetting.getEObject();
		final EObject instance = clazz.getEPackage().getEFactoryInstance().create(clazz);
		final EditingDomain editingDomain = getEditingDomain(mainSetting);
		if (editingDomain == null) {
			return;
		}
		editingDomain.getCommandStack().execute(
			AddCommand.create(editingDomain, modelElement, mainSetting.getEStructuralFeature(), instance));

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
				validationIcon.setImage(getValidationIcon(getVElement().getDiagnostic().getHighestSeverity()));
				validationIcon.setToolTipText(getVElement().getDiagnostic().getMessage());
				final Setting mainSetting = getVElement().getDomainModelReference().getIterator().next();
				final Collection<?> collection = (Collection<?>) mainSetting.get(true);
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
			final EStructuralFeature feat1 = object1.eClass().getEAllStructuralFeatures().get(propertyIndex);
			final EStructuralFeature feat2 = object2.eClass().getEAllStructuralFeatures().get(propertyIndex);

			final Object value1 = object1.eGet(feat1);
			final Object value2 = object2.eGet(feat2);

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
		 */
		public ECPCellLabelProvider(EStructuralFeature feature, CellEditor cellEditor, IObservableMap attributeMap,
			VTableControl vTableControl) {
			super(attributeMap);
			this.vTableControl = vTableControl;
			this.feature = feature;
			this.cellEditor = cellEditor;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
		 */
		@Override
		public String getToolTipText(Object element) {
			final EObject domainObject = (EObject) element;
			final Setting setting = InternalEObject.class.cast(domainObject).eSetting(feature);
			final StringBuffer tooltip = new StringBuffer();
			final VDiagnostic vDiagnostic = vTableControl.getDiagnostic();
			final List<Diagnostic> diagnostics = vDiagnostic.getDiagnostic(domainObject, feature);
			for (final Diagnostic diagnostic : diagnostics) {
				if (tooltip.length() > 0) {
					tooltip.append("\n"); //$NON-NLS-1$
				}
				tooltip.append(diagnostic.getMessage());
			}
			if (tooltip.length() != 0) {
				return ECPTooltipModifierHelper.modifyString(tooltip.toString(), setting);
			}
			final Object value = ((EObject) element).eGet(feature);
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

		private final EStructuralFeature cellFeature;

		private final IItemPropertyDescriptor itemPropertyDescriptor;

		private final VTableControl tableControl;

		/**
		 * @param viewer
		 */
		public ECPTableEditingSupport(ColumnViewer viewer, CellEditor cellEditor, EStructuralFeature feature,
			IItemPropertyDescriptor itemPropertyDescriptor, VTableControl tableControl) {
			super(viewer);
			this.cellEditor = cellEditor;
			cellFeature = feature;
			this.itemPropertyDescriptor = itemPropertyDescriptor;
			this.tableControl = tableControl;
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
			boolean editable = itemPropertyDescriptor.canSetProperty(null) && tableControl.isEnabled()
				&& !tableControl.isReadonly();

			editable &= !CellReadOnlyTesterHelper.getInstance().isReadOnly(getVElement(),
				InternalEObject.class.cast(element).eSetting(cellFeature));

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

			final IObservableValue model = doCreateElementObservable(cell.getElement(), cell);
			Assert.isNotNull(model, "doCreateElementObservable(...) did not return an observable"); //$NON-NLS-1$

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

		protected IObservableValue doCreateElementObservable(Object element, ViewerCell cell) {
			return EMFEditObservables.observeValue(
				getEditingDomain(InternalEObject.class.cast(element).eSetting(cellFeature)),
				(EObject) element, cellFeature);
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
			final StringBuffer tooltip = new StringBuffer();
			final VDiagnostic vDiagnostic = vTableControl.getDiagnostic();

			final List<Diagnostic> diagnostics = vDiagnostic.getDiagnostics((EObject) element);
			for (final Diagnostic diagnostic : diagnostics) {
				if (tooltip.length() > 0) {
					tooltip.append("\n"); //$NON-NLS-1$
				}
				tooltip.append(diagnostic.getMessage());
			}

			return tooltip.toString();
		}
	}
}
