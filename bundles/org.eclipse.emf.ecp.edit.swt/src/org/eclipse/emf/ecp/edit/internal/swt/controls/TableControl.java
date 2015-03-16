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
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.EObjectObservableMap;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.table.TableColumnConfiguration;
import org.eclipse.emf.ecp.edit.internal.swt.table.TableControlConfiguration;
import org.eclipse.emf.ecp.edit.internal.swt.util.CellEditorFactory;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTRenderingHelper;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

/**
 * The class describing a table control.
 *
 * @author Eugen Neufeld
 * @author emueller
 */
// this class is not serialized
public class TableControl extends SWTControl {

	private static final String FIXED_COLUMNS = "org.eclipse.rap.rwt.fixedColumns"; //$NON-NLS-1$

	private static final String ICON_ADD = "icons/add.png"; //$NON-NLS-1$

	private TableViewer tableViewer;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private Button unsetButton;
	private EClass clazz;
	private TableControlConfiguration tableControlConfiguration;
	private boolean editable = true;

	private EReference getTableReference() {
		return (EReference) getFirstStructuralFeature();
	}

	/**
	 *
	 * @param tableControlConfiguration the {@link TableControlConfiguration} to use when creating the table
	 */
	public final void setTableControlConfiguration(TableControlConfiguration tableControlConfiguration) {
		this.tableControlConfiguration = tableControlConfiguration;
	}

	@Override
	protected Binding bindValue() {
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPControlSWT#createControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public List<RenderingResultRow<Control>> createControls(final Composite parent) {
		final IItemPropertyDescriptor itemPropertyDescriptor = getItemPropertyDescriptor(getFirstSetting());
		if (itemPropertyDescriptor == null) {
			return null;
		}
		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
		final List<RenderingResultRow<Control>> list = Collections.singletonList(SWTRenderingHelper.INSTANCE
			.getResultRowFactory().createRenderingResultRow(
				createControl(parent)));

		applyValidation(getControl().getDiagnostic());
		return list;

	}

	@Override
	public Composite createControl(final Composite parent) {
		mainSetting = getFirstSetting();
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });

		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		clazz = getTableReference().getEReferenceType();

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setBackground(parent.getBackground());

		final Composite titleComposite = new Composite(composite, SWT.NONE);
		titleComposite.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING)
			.applyTo(titleComposite);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(titleComposite);

		final Label label = new Label(titleComposite, SWT.NONE);
		label.setBackground(parent.getBackground());
		label.setText(getItemPropertyDescriptor(mainSetting).getDisplayName(null));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(label);

		// VALIDATION
		validationLabel = new Label(titleComposite, SWT.NONE);
		validationLabel.setBackground(parent.getBackground());
		// set the size of the label to the size of the image
		GridDataFactory.fillDefaults().hint(16, 17).applyTo(validationLabel);

		boolean createButtons = true;
		if (tableControlConfiguration != null) {
			createButtons = !tableControlConfiguration.isAddRemoveDisabled();
		}
		if (createButtons) {
			// addButtons
			final Composite buttonComposite = new Composite(titleComposite, SWT.NONE);
			GridDataFactory.fillDefaults().align(SWT.END, SWT.BEGINNING).grab(true, false).applyTo(buttonComposite);
			int numButtons = 2;

			createAddRowButton(clazz, buttonComposite);
			createRemoveRowButton(clazz, buttonComposite);
			if (!isEmbedded() && getTableReference().isUnsettable()) {
				unsetButton = new Button(buttonComposite, SWT.PUSH);
				unsetButton.setToolTipText(getUnsetButtonTooltip());
				unsetButton.setImage(Activator.getImage("icons/delete.png")); //$NON-NLS-1$
				numButtons++;
			}
			GridLayoutFactory.fillDefaults().numColumns(numButtons).equalWidth(true).applyTo(buttonComposite);
		}
		final Composite controlComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL,
			SWT.FILL).applyTo(controlComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(controlComposite);

		// delegate to super class
		createContentControl(controlComposite);

		return composite;
	}

	private TableViewer createTableViewer(Composite parent) {
		final TableViewer tableViewer = new TableViewer(parent, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION
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

		return tableViewer;
	}

	private void createFixedValidationStatusColumn(TableViewer tableViewer,
		final List<EStructuralFeature> structuralFeatures) {
		final TableViewerColumn column = TableViewerColumnBuilder
			.create()
			.setMoveable(false)
			.setText(
				LocalizationServiceHelper.getString(getClass(),
					DepricatedControlMessageKeys.TableControl_ValidationStatusColumn))
			.setWidth(80)
			.build(tableViewer);

		column.setLabelProvider(new ValidationStatusCellLabelProvider(structuralFeatures));
	}

	private EObject getInstanceOf(EClass clazz) {
		return clazz.getEPackage().getEFactoryInstance().create(clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#fillControlComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void fillControlComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(1, 200).applyTo(composite);
		tableViewer = createTableViewer(composite);

		final ObservableListContentProvider cp = new ObservableListContentProvider();
		final EObject tempInstance = getInstanceOf(clazz);
		final ECPTableViewerComparator comparator = new ECPTableViewerComparator();
		tableViewer.setComparator(comparator);
		int columnNumber = 0;
		final Map<EStructuralFeature, Boolean> readOnlyConfig = createReadOnlyConfig(clazz);
		final List<EStructuralFeature> structuralFeatures = new ArrayList<EStructuralFeature>();
		structuralFeatures.addAll(readOnlyConfig.keySet());
		if (!getControl().isReadonly()) {
			createFixedValidationStatusColumn(tableViewer, structuralFeatures);
		}

		for (final EStructuralFeature feature : structuralFeatures) {
			final IItemPropertyDescriptor itemPropertyDescriptor = adapterFactoryItemDelegator.getPropertyDescriptor(
				tempInstance, feature);
			if (itemPropertyDescriptor == null) {
				// if we can't render because no edit information is available, do nothing
				continue;
			}

			final CellEditor cellEditor = createCellEditor(tempInstance, feature);
			final TableViewerColumn column = TableViewerColumnBuilder
				.create()
				.setText(itemPropertyDescriptor.getDisplayName(null))
				.setToolTipText(itemPropertyDescriptor.getDescription(null))
				.setResizable(true)
				.setMoveable(false)
				.setStyle(noStyle())
				.setData("width", //$NON-NLS-1$
					ECPCellEditor.class.isInstance(cellEditor) ? ECPCellEditor.class.cast(cellEditor)
						.getColumnWidthWeight() : 100)
				.build(tableViewer);

			column.setLabelProvider(new ECPCellLabelProvider(feature, cellEditor, feature.isMany() ?
				new EObjectObservableMap(cp.getKnownElements(), feature) :
				EMFProperties.value(feature).observeDetail(cp.getKnownElements())));
			column.getColumn().addSelectionListener(getSelectionAdapter(comparator, column.getColumn(), columnNumber));

			if (!isReadOnlyFeature(readOnlyConfig, feature)) {
				// remove if no editing needed
				final EditingSupport observableSupport = new ECPTableEditingSupport(tableViewer, cellEditor, feature);
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
	}

	private CellEditor createCellEditor(final EObject tempInstance, final EStructuralFeature feature) {
		return CellEditorFactory.INSTANCE.getCellEditor(feature,
			tempInstance, tableViewer.getTable(), getViewModelContext());
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

	private Map<EStructuralFeature, Boolean> createReadOnlyConfig(EClass clazz) {
		final Map<EStructuralFeature, Boolean> readOnlyConfig = new LinkedHashMap<EStructuralFeature, Boolean>();
		if (tableControlConfiguration != null) {
			for (final TableColumnConfiguration tcc : tableControlConfiguration.getColumns()) {
				readOnlyConfig.put(tcc.getColumnAttribute(), tcc.isReadOnly());
			}
		} else {
			for (final EStructuralFeature feature : clazz.getEStructuralFeatures()) {
				readOnlyConfig.put(feature, Boolean.FALSE);
			}
		}
		return readOnlyConfig;
	}

	private int noStyle() {
		return SWT.NONE;
	}

	@Override
	protected Button getCustomUnsetButton() {
		return unsetButton;
	}

	private SelectionAdapter getSelectionAdapter(final ECPTableViewerComparator comparator, final TableColumn column,
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

	private Button removeButton;

	private void createRemoveRowButton(EClass clazz, final Composite buttonComposite) {
		removeButton = new Button(buttonComposite, SWT.None);
		final Image image = Activator.getImage("icons/delete.png"); //$NON-NLS-1$
		removeButton.setImage(image);
		removeButton.setToolTipText(LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.TableControl_RemoveSelected)
			+ clazz.getInstanceClass().getSimpleName());
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

				deleteRowUserConfirmDialog(deletionList);

			}
		});

		final List<?> containments = (List<?>) mainSetting.get(true);
		if (containments.size() <= getTableReference().getLowerBound()) {
			removeButton.setEnabled(false);
		}
	}

	/**
	 * This method shows a user confirmation dialog when the user attempts to delete a row in the table.
	 *
	 * @param deletionList the list of selected EObjects to delete
	 */
	protected void deleteRowUserConfirmDialog(final List<EObject> deletionList) {
		final MessageDialog dialog = new MessageDialog(
			tableViewer.getTable().getShell(),
			LocalizationServiceHelper.getString(getClass(), DepricatedControlMessageKeys.TableControl_Delete),
			null,
			LocalizationServiceHelper.getString(getClass(), DepricatedControlMessageKeys.TableControl_DeleteAreYouSure),
			MessageDialog.CONFIRM, new String[] {
				JFaceResources.getString(IDialogLabelKeys.YES_LABEL_KEY),
				JFaceResources.getString(IDialogLabelKeys.NO_LABEL_KEY) }, 0);

		new ECPDialogExecutor(dialog) {

			@Override
			public void handleResult(int codeResult) {
				if (codeResult == IDialogConstants.CANCEL_ID) {
					return;
				}

				deleteRows(deletionList);

				final List<?> containments = (List<?>) mainSetting.get(true);
				if (containments.size() < getTableReference().getUpperBound()) {
					addButton.setEnabled(true);
				}
				if (containments.size() <= getTableReference().getLowerBound()) {
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
	 */
	protected void deleteRows(List<EObject> deletionList) {
		final EditingDomain editingDomain = getEditingDomain(mainSetting);
		final EObject modelElement = mainSetting.getEObject();
		editingDomain.getCommandStack().execute(
			RemoveCommand.create(editingDomain, modelElement, getTableReference(), deletionList));
	}

	/**
	 * This method is called to add a new entry in the domain model and thus to create a new row in the table. The
	 * element to create is defined by the provided class.
	 * You can override this method but you have to call super nonetheless.
	 *
	 * @param clazz the {@link EClass} defining the EObject to create
	 */
	protected void addRow(EClass clazz) {
		final EObject modelElement = mainSetting.getEObject();
		final EObject instance = clazz.getEPackage().getEFactoryInstance().create(clazz);

		final EditingDomain editingDomain = getEditingDomain(mainSetting);
		editingDomain.getCommandStack().execute(
			AddCommand.create(editingDomain, modelElement, getTableReference(), instance));

	}

	private Button addButton;
	private Setting mainSetting;

	private boolean isDisposing;

	private void createAddRowButton(final EClass clazz, final Composite buttonComposite) {
		addButton = new Button(buttonComposite, SWT.None);
		final Image image = Activator.getImage(ICON_ADD);
		addButton.setImage(image);
		addButton.setToolTipText(LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.TableControl_AddInstanceOf) + clazz.getInstanceClass().getSimpleName());
		addButton.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				addRow(clazz);

				final List<?> containments = (List<?>) mainSetting.get(true);
				if (getTableReference().getUpperBound() != -1
					&& containments.size() >= getTableReference().getUpperBound()) {
					addButton.setEnabled(false);
				}
				if (containments.size() > getTableReference().getLowerBound()) {
					removeButton.setEnabled(true);
				}
			}
		});

		final List<?> containments = (List<?>) mainSetting.get(true);
		if (getTableReference().getUpperBound() != -1
			&& containments.size() >= getTableReference().getUpperBound()) {
			addButton.setEnabled(false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		isDisposing = true;
		super.dispose();
		composedAdapterFactory.dispose();
		mainSetting = null;
		adapterFactoryItemDelegator = null;
		composedAdapterFactory = null;
		isDisposing = false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ECPAbstractControl#applyValidation(org.eclipse.emf.ecp.view.spi.model.VDiagnostic)
	 */
	@Override
	protected void applyValidation(VDiagnostic diagnostic) {

		if (validationLabel == null || validationLabel.isDisposed()) {
			return;
		}
		if (diagnostic != null) {
			final Image image = getValidationIcon(diagnostic.getHighestSeverity());
			validationLabel.setImage(image);
			validationLabel.setToolTipText(diagnostic.getMessage());
			for (final Object object : (Collection<?>) mainSetting.get(true)) {
				tableViewer.update(object, null);
			}
		}
	}

	// /**
	// * {@inheritDoc}
	// *
	// * @deprecated
	// */
	// @Deprecated
	// @Override
	// public void resetValidation() {
	// if (validationLabel == null || validationLabel.isDisposed()) {
	// return;
	// }
	//		validationLabel.setToolTipText(""); //$NON-NLS-1$
	// validationLabel.setImage(null);
	// tableViewer.refresh();
	// }
	// /**
	// * {@inheritDoc}
	// *
	// * @deprecated
	// */
	// @Deprecated
	// @Override
	// public void handleValidation(Diagnostic diagnostic) {
	// if (diagnostic.getData().isEmpty()) {
	// return;
	// }
	// final Image image = getValidationIcon(diagnostic.getSeverity());
	// validationLabel.setImage(image);
	// validationLabel.setToolTipText(getTableTooltipMessage(diagnostic));
	// final EObject object = (EObject) diagnostic.getData().get(0);
	// tableViewer.update(object, null);
	// }

	/**
	 * Returns the message of the validation tool tip shown in the table header.
	 *
	 * @param diagnostic the {@link Diagnostic} to extract the message from
	 * @return the message
	 */
	protected String getTableTooltipMessage(Diagnostic diagnostic) {
		return diagnostic.getMessage();
	}

	/**
	 * Returns the message of the validation tool tip shown in the row.
	 *
	 * @param vDiagnostic the {@link VDiagnostic} to get the message from
	 * @return the message
	 */
	protected String getRowTooltipMessage(VDiagnostic vDiagnostic) {
		return vDiagnostic.getMessage();
	}

	/**
	 * Returns the message of the validation tool tip shown in the cell.
	 *
	 * @param vDiagnostic the {@link VDiagnostic} to get the message from
	 * @return the message
	 */
	protected String getCellTooltipMessage(VDiagnostic vDiagnostic) {
		if (vDiagnostic == null) {
			return null;
		}
		if (vDiagnostic.getDiagnostics().size() == 0) {
			return vDiagnostic.getMessage();
		}
		final Diagnostic diagnostic = (Diagnostic) vDiagnostic.getDiagnostics().get(0);
		Diagnostic reason = diagnostic;
		if (diagnostic.getChildren() != null && diagnostic.getChildren().size() != 0) {
			reason = diagnostic.getChildren().get(0);
		}
		return reason.getMessage();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated
	 */
	@Deprecated
	@Override
	public void setEditable(boolean isEditable) {
		if (addButton != null) {
			addButton.setVisible(isEditable);
		}
		if (removeButton != null) {
			removeButton.setVisible(isEditable);
		}
		editable = isEditable;
	}

	/**
	 * @author Jonas
	 *
	 */
	private final class ValidationStatusCellLabelProvider extends CellLabelProvider {
		private final List<EStructuralFeature> structuralFeatures;

		/**
		 * @param structuralFeatures
		 */
		private ValidationStatusCellLabelProvider(List<EStructuralFeature> structuralFeatures) {
			this.structuralFeatures = structuralFeatures;
		}

		@Override
		public void update(ViewerCell cell) {
			Integer mostSevere = Diagnostic.OK;
			final VDiagnostic vDiagnostic = getControl().getDiagnostic();
			// for (final Object diagObject : vDiagnostic.getDiagnostics()) {
			// final Diagnostic diagnostic = (Diagnostic) diagObject;
			// if (diagnostic.getData().size() == 0) {
			// continue;
			// }
			// if (diagnostic.getData().get(0).equals(cell.getElement())) {
			// final int currentSeverity = diagnostic.getSeverity();
			// if (currentSeverity > mostSevere) {
			// mostSevere = currentSeverity;
			// }
			// }
			// }
			final List<Diagnostic> diagnostics = vDiagnostic.getDiagnostics((EObject) cell.getElement());
			if (diagnostics.size() != 0) {
				mostSevere = diagnostics.get(0).getSeverity();
			}
			cell.setImage(getValidationIcon(mostSevere));
		}

		@Override
		public String getToolTipText(Object element) {
			final StringBuffer tooltip = new StringBuffer();
			final VDiagnostic vDiagnostic = getControl().getDiagnostic();
			// for (final Object diagObject : vDiagnostic.getDiagnostics()) {
			// final Diagnostic diagnostic = (Diagnostic) diagObject;
			// if (diagnostic.getData().size() < 2) {
			// continue;
			// }
			// if (diagnostic.getSeverity() == Diagnostic.OK) {
			// continue;
			// }
			// if (diagnostic.getData().get(0).equals(element)
			// && structuralFeatures.contains(diagnostic.getData().get(1))) {
			// if (tooltip.length() > 0) {
			//						tooltip.append("\n"); //$NON-NLS-1$
			// }
			// tooltip.append(diagnostic.getMessage());
			// }
			// }

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

		/**
		 * Constructor.
		 *
		 * @param feature
		 *            the {@link EStructuralFeature} the cell is bound to
		 * @param cellEditor
		 *            the {@link CellEditor} instance
		 * @param attributeMap
		 *            an {@link IObservableMap} instance that is passed to the {@link ObservableMapCellLabelProvider}
		 */
		public ECPCellLabelProvider(EStructuralFeature feature, CellEditor cellEditor, IObservableMap attributeMap) {
			super(attributeMap);
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

			final StringBuffer tooltip = new StringBuffer();
			final VDiagnostic vDiagnostic = getControl().getDiagnostic();
			final List<Diagnostic> diagnostics = vDiagnostic.getDiagnostic(domainObject, feature);
			for (final Diagnostic diagnostic : diagnostics) {
				if (tooltip.length() > 0) {
					tooltip.append("\n"); //$NON-NLS-1$
				}
				tooltip.append(diagnostic.getMessage());
			}
			return tooltip.toString();

			// final VDiagnostic vDiagnostic = getControl().getDiagnostic();
			// for (final Object diagObject : vDiagnostic.getDiagnostics()) {
			// final Diagnostic diagnostic = (Diagnostic) diagObject;
			// if (diagnostic.getData().size() < 2) {
			// continue;
			// }
			// if (diagnostic.getData().get(0).equals(element) && diagnostic.getData().get(1).equals(feature)) {
			//
			// if (diagnostic.getChildren() != null && diagnostic.getChildren().size() != 0) {
			// boolean childrenUsefull = false;
			// for (final Diagnostic diagnostic2 : diagnostic.getChildren()) {
			// if (diagnostic2.getSeverity() != Diagnostic.OK) {
			// if (tooltip.length() > 0) {
			//									tooltip.append("\n"); //$NON-NLS-1$
			// }
			// tooltip.append(diagnostic2.getMessage());
			// childrenUsefull = true;
			// }
			// }
			// if (!childrenUsefull) {
			// if (tooltip.length() > 0) {
			//								tooltip.append("\n"); //$NON-NLS-1$
			// }
			// tooltip.append(diagnostic.getMessage());
			// }
			// } else {
			// if (tooltip.length() > 0) {
			//							tooltip.append("\n"); //$NON-NLS-1$
			// }
			// tooltip.append(diagnostic.getMessage());
			// }
			// }
			// }
			// if (tooltip.length() != 0) {
			// return tooltip.toString();
			// }
			// final Object value = ((EObject) element).eGet(feature);
			// if (value == null) {
			// return null;
			// }
			// return String.valueOf(value);
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
			if (isDisposing) {
				return null;
			}

			final Integer mostSevere = Diagnostic.OK;
			final VDiagnostic vDiagnostic = getControl().getDiagnostic();
			// for (final Object diagObject : vDiagnostic.getDiagnostics()) {
			// final Diagnostic diagnostic = (Diagnostic) diagObject;
			// if (diagnostic.getData().size() < 2) {
			// continue;
			// }
			// if (diagnostic.getData().get(0).equals(element) && diagnostic.getData().get(1).equals(feature)) {
			// final int currentSeverity = diagnostic.getSeverity();
			// if (currentSeverity > mostSevere) {
			// mostSevere = currentSeverity;
			// }
			// }
			// }
			final List<Diagnostic> diagnostic = vDiagnostic.getDiagnostic((EObject) element, feature);
			return getValidationBackgroundColor(diagnostic.size() == 0 ? Diagnostic.OK : diagnostic.get(0)
				.getSeverity());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.TableControl_NotSetClickToSet);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper.getString(getClass(), DepricatedControlMessageKeys.TableControl_Unset);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getControlForTooltip()
	 */
	@Override
	protected Control[] getControlsForTooltip() {
		// return new Control[] { tableViewer.getControl() };
		return new Control[0];
	}

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated
	 */
	@Override
	@Deprecated
	public boolean showLabel() {
		return false;
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

		/**
		 * @param viewer
		 */
		public ECPTableEditingSupport(ColumnViewer viewer, CellEditor cellEditor, EStructuralFeature feature) {
			super(viewer);
			this.cellEditor = cellEditor;
			cellFeature = feature;
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
			return EMFEditObservables.observeValue(getEditingDomain(),
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

		class ColumnViewerEditorActivationListenerHelper extends ColumnViewerEditorActivationListener {

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

}
