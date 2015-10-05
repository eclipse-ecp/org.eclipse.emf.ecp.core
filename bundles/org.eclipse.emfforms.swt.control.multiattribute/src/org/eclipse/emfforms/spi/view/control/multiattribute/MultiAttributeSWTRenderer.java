/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.view.control.multiattribute;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.internal.view.control.multiattribute.Messages;
import org.eclipse.emfforms.internal.view.control.multiattribute.celleditor.CellEditorFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerRow;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.FrameworkUtil;

/**
 * Renderer for MultiReferenceControl.
 *
 * @author David Soto Setzke
 * @author Johannes Faltermeier
 *
 */
public class MultiAttributeSWTRenderer extends AbstractControlSWTRenderer<VControl> {

	private static final String ICON_ADD = "icons/add.png"; //$NON-NLS-1$
	private static final String ICON_DELETE = "icons/delete.png"; //$NON-NLS-1$
	private static final String ICONS_ARROW_DOWN_PNG = "icons/arrow_down.png";//$NON-NLS-1$
	private static final String ICONS_ARROW_UP_PNG = "icons/arrow_up.png";//$NON-NLS-1$

	private final ImageRegistryService imageRegistryService;

	private AdapterFactoryLabelProvider labelProvider;
	private ComposedAdapterFactory composedAdapterFactory;

	private Composite mainComposite;
	private TableViewer tableViewer;
	private Button removeButton;
	private Button addButton;

	private final EMFDataBindingContext viewModelDBC;
	private Label validationIcon;

	/**
	 * Default constructor.
	 *
	 * @param vElement
	 *            the view model element to be rendered
	 * @param viewContext
	 *            the view context
	 * @param emfFormsDatabinding
	 *            The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider
	 *            The {@link EMFFormsLabelProvider}
	 * @param reportService
	 *            The {@link ReportService}
	 * @param vtViewTemplateProvider
	 *            The {@link VTViewTemplateProvider}
	 * @param imageRegistryService
	 *            The {@link ImageRegistryService}
	 */
	@Inject
	public MultiAttributeSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, ImageRegistryService imageRegistryService) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.imageRegistryService = imageRegistryService;
		viewModelDBC = new EMFDataBindingContext();
	}

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
	}

	/**
	 * Returns the height for the table that will be created.
	 *
	 * @return the height hint
	 */
	protected int getTableHeightHint() {
		return 200;
	}

	/**
	 * Gives access to the tableViewer used to display the attributes.
	 *
	 * @return the viewer
	 */
	protected TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * Creates the composite which will be the parent for the table.
	 *
	 * @param composite
	 *            the parent composite
	 * @return the table composite
	 */
	protected Composite createControlComposite(Composite composite) {
		final Composite controlComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(1, getTableHeightHint())
			.applyTo(controlComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(controlComposite);
		return controlComposite;
	}

	/**
	 * Returns an {@link Image} from the image registry.
	 *
	 * @param path
	 *            the path to the image
	 * @return the image
	 */
	protected Image getImage(String path) {
		return imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()), path);
	}

	private Button createRemoveRowButton(final Composite buttonComposite, IObservableList list, EAttribute attribute) {
		removeButton = new Button(buttonComposite, SWT.None);
		final Image image = getImage(ICON_DELETE);
		removeButton.setImage(image);
		removeButton.setEnabled(!getVElement().isReadonly());
		if (list.size() <= attribute.getLowerBound()) {
			removeButton.setEnabled(false);
		}
		return removeButton;
	}

	private Button createAddRowButton(final Composite buttonComposite, IObservableList list, EAttribute attribute) {
		addButton = new Button(buttonComposite, SWT.None);
		final Image image = getImage(ICON_ADD);
		addButton.setImage(image);
		if (attribute.getUpperBound() != -1 && list.size() >= attribute.getUpperBound()) {
			addButton.setEnabled(false);
		}
		return addButton;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setBackground(parent.getBackground());

		IObservableList list;
		try {
			list = getEMFFormsDatabinding().getObservableList(getVElement().getDomainModelReference(),
				getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex1) {
			getReportService().report(new RenderingFailedReport(ex1));
			return composite;
		}

		final EAttribute attribute = EAttribute.class.cast(list.getElementType());

		final Composite titleComposite = new Composite(composite, SWT.NONE);
		titleComposite.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(titleComposite);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(titleComposite);
		createLabelProvider();

		validationIcon = createValidationIcon(titleComposite);
		GridDataFactory.fillDefaults().hint(16, 17).grab(false, false).applyTo(validationIcon);

		Button addButton = null;
		Button removeButton = null;
		final Composite buttonComposite = new Composite(titleComposite, SWT.NONE);
		buttonComposite.setBackground(titleComposite.getBackground());
		GridDataFactory.fillDefaults().align(SWT.END, SWT.BEGINNING).grab(true, false).applyTo(buttonComposite);
		int numButtons = 0;
		if (attribute.isOrdered()) {
			createUpDownButtons(buttonComposite, list);
			numButtons += 2;
		}

		addButton = createAddRowButton(buttonComposite, list, attribute);
		removeButton = createRemoveRowButton(buttonComposite, list, attribute);
		numButtons += 2;

		GridLayoutFactory.fillDefaults().numColumns(numButtons).equalWidth(false).applyTo(buttonComposite);
		final Composite controlComposite = createControlComposite(composite);
		createContent(controlComposite, attribute, list);

		initAddButton(addButton, list);
		initRemoveButton(removeButton, list);

		getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				MultiAttributeSWTRenderer.this.removeButton.setEnabled(!event.getSelection().isEmpty());
			}
		});

		return composite;
	}

	private void createLabelProvider() {
		composedAdapterFactory = new ComposedAdapterFactory(
			new AdapterFactory[] { new CustomReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
		labelProvider.setFireLabelUpdateNotifications(true);
	}

	private void initAddButton(Button addButton, IObservableList list) {
		if (addButton == null) {
			return;
		}
		addButton.addSelectionListener(new AddButtonSelectionAdapter(list));
	}

	private void initRemoveButton(Button removeButton, IObservableList list) {
		if (removeButton == null) {
			return;
		}
		removeButton.addSelectionListener(new RemoveButtonSelectionAdapter(list));
	}

	private void createUpDownButtons(Composite composite, IObservableList list) {
		final Image up = getImage(ICONS_ARROW_UP_PNG);
		final Image down = getImage(ICONS_ARROW_DOWN_PNG);

		final Button upB = new Button(composite, SWT.PUSH);
		upB.setImage(up);
		upB.setEnabled(!getVElement().isReadonly());
		upB.addSelectionListener(new UpButtonSelectionAdapter(list));
		final Button downB = new Button(composite, SWT.PUSH);
		downB.setImage(down);
		downB.setEnabled(!getVElement().isReadonly());
		downB.addSelectionListener(new DownButtonSelectionAdapter(list));
	}

	private InternalEObject getInstanceOf(EClass clazz) {
		return InternalEObject.class.cast(clazz.getEPackage().getEFactoryInstance().create(clazz));
	}

	private void createContent(Composite composite, EAttribute attribute, IObservableList list) {
		tableViewer = new TableViewer(composite, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tableViewer.getTable().setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_multiattribute"); //$NON-NLS-1$
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		final ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
					|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
					|| event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR
					|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};

		TableViewerEditor.create(tableViewer, null, actSupport,
			ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
				| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
		ColumnViewerToolTipSupport.enableFor(tableViewer);

		final ObservableListContentProvider cp = new ObservableListContentProvider();

		final EMFFormsLabelProvider labelService = getEMFFormsLabelProvider();

		final TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setResizable(false);
		column.getColumn().setMoveable(false);

		final EClass clazz = attribute.getEContainingClass();
		InternalEObject tempInstance = null;
		if (!clazz.isAbstract() && !clazz.isInterface()) {
			tempInstance = getInstanceOf(clazz);
		}

		final CellEditor cellEditor = createCellEditor(tempInstance, attribute, tableViewer.getTable());

		@SuppressWarnings("deprecation")
		final IObservableValue textObservableValue = org.eclipse.jface.databinding.swt.SWTObservables
			.observeText(column.getColumn());
		@SuppressWarnings("deprecation")
		final IObservableValue tooltipObservableValue = org.eclipse.jface.databinding.swt.SWTObservables
			.observeTooltipText(column.getColumn());
		try {
			viewModelDBC.bindValue(textObservableValue, labelService
				.getDisplayName(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel()));

			viewModelDBC.bindValue(tooltipObservableValue, labelService
				.getDescription(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel()));
		} catch (final NoLabelFoundException e) {
			getReportService().report(new RenderingFailedReport(e));
		}

		tableViewer.setLabelProvider(labelProvider);
		tableViewer.setContentProvider(cp);
		tableViewer.setInput(list);

		final TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);
		layout.setColumnData(column.getColumn(), new ColumnWeightData(1, false));

		final VDomainModelReference dmr = getVElement().getDomainModelReference();
		final EditingSupport observableSupport = new ECPListEditingSupport(tableViewer, cellEditor, getVElement(), dmr,
			list);
		column.setEditingSupport(observableSupport);

	}

	private CellEditor createCellEditor(final EObject tempInstance, final EAttribute attribute, Table table) {
		return CellEditorFactory.INSTANCE.createCellEditor(attribute, tempInstance, table, getViewModelContext());
	}

	@Override
	protected void applyValidation() {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (validationIcon == null) {
					return;
				}
				if (validationIcon.isDisposed()) {
					return;
				}
				if (getVElement().getDiagnostic() == null) {
					return;
				}
				validationIcon.setImage(getValidationIcon(getVElement().getDiagnostic().getHighestSeverity()));
				validationIcon.setToolTipText(ECPTooltipModifierHelper.modifyString(getVElement().getDiagnostic()
					.getMessage(), null));
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		if (mainComposite != null) {
			mainComposite.dispose();
		}
		if (labelProvider != null) {
			labelProvider.dispose();
		}
		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
		}
		super.dispose();
	}

	/**
	 * Listener for the down button.
	 *
	 * @author David Soto Setzke
	 * @author Johannes Faltermeier
	 *
	 */
	private final class DownButtonSelectionAdapter extends SelectionAdapter {

		private final IObservableList list;

		public DownButtonSelectionAdapter(IObservableList list) {
			this.list = list;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			final IObserving observing = IObserving.class.cast(list);
			final EObject eObject = EObject.class.cast(observing.getObserved());
			final EAttribute attribute = EAttribute.class.cast(list.getElementType());

			final int currentIndex = tableViewer.getTable().getSelectionIndex();
			if (currentIndex + 1 < tableViewer.getTable().getItems().length) {
				final EditingDomain editingDomain = getEditingDomain(eObject);
				editingDomain.getCommandStack()
					.execute(new MoveCommand(editingDomain, eObject, attribute, currentIndex, currentIndex + 1));
				tableViewer.refresh();
			}
		}
	}

	/**
	 * Listener for the up button.
	 *
	 * @author David Soto Setzke
	 * @author Johannes Faltermeier
	 *
	 */
	private final class UpButtonSelectionAdapter extends SelectionAdapter {

		private final IObservableList list;

		public UpButtonSelectionAdapter(IObservableList list) {
			this.list = list;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			final IObserving observing = IObserving.class.cast(list);
			final EObject eObject = EObject.class.cast(observing.getObserved());
			final EAttribute attribute = EAttribute.class.cast(list.getElementType());

			final int currentIndex = tableViewer.getTable().getSelectionIndex();
			if (currentIndex != 0) {
				final EditingDomain editingDomain = getEditingDomain(eObject);

				editingDomain.getCommandStack()
					.execute(new MoveCommand(editingDomain, eObject, attribute, currentIndex, currentIndex - 1));
				tableViewer.refresh();
			}
		}
	}

	/**
	 * Listener for the remove button.
	 *
	 * @author David Soto Setzke
	 * @author Johannes Faltermeier
	 *
	 */
	private final class RemoveButtonSelectionAdapter extends SelectionAdapter {

		private final IObservableList list;

		public RemoveButtonSelectionAdapter(IObservableList list) {
			this.list = list;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			final IObserving observing = IObserving.class.cast(list);
			final EObject eObject = EObject.class.cast(observing.getObserved());
			final EAttribute attribute = EAttribute.class.cast(list.getElementType());

			final EditingDomain editingDomain = getEditingDomain(eObject);
			final int currentIndex = tableViewer.getTable().getSelectionIndex();
			if (currentIndex >= 0) {
				editingDomain.getCommandStack().execute(RemoveCommand.create(editingDomain, eObject, attribute,
					tableViewer.getElementAt(currentIndex)));
			}
		}
	}

	/**
	 * Listener for the add button.
	 *
	 * @author David Soto Setzke
	 * @author Johannes Faltermeier
	 *
	 */
	private final class AddButtonSelectionAdapter extends SelectionAdapter {

		private final IObservableList list;

		public AddButtonSelectionAdapter(IObservableList list) {
			this.list = list;
		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			try {
				final IObserving observing = IObserving.class.cast(list);
				final EObject eObject = EObject.class.cast(observing.getObserved());
				final EAttribute attribute = EAttribute.class.cast(list.getElementType());

				Object defaultValue = attribute.getEType().getDefaultValue();
				if (defaultValue == null) {
					defaultValue = attribute.getEType().getInstanceClass().getConstructor().newInstance();

				}
				final EditingDomain editingDomain = getEditingDomain(getViewModelContext().getDomainModel());
				editingDomain.getCommandStack()
					.execute(AddCommand.create(editingDomain, eObject, attribute, defaultValue));
				tableViewer.refresh();
			} catch (final InstantiationException ex) {
				getReportService().report(new AbstractReport(ex, Messages.MultiAttributeSWTRenderer_AddFailed));
			} catch (final IllegalAccessException ex) {
				getReportService().report(new AbstractReport(ex, Messages.MultiAttributeSWTRenderer_AddFailed));
			} catch (final IllegalArgumentException ex) {
				getReportService().report(new AbstractReport(ex, Messages.MultiAttributeSWTRenderer_AddFailed));
			} catch (final InvocationTargetException ex) {
				getReportService().report(new AbstractReport(ex, Messages.MultiAttributeSWTRenderer_AddFailed));
			} catch (final NoSuchMethodException ex) {
				getReportService().report(new AbstractReport(ex, Messages.MultiAttributeSWTRenderer_AddFailed));
			} catch (final SecurityException ex) {
				getReportService().report(new AbstractReport(ex, Messages.MultiAttributeSWTRenderer_AddFailed));
			}
		}
	}

	/**
	 * Editing support for a single element in a multi {@link EAttribute}.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class ECPListEditingSupport extends EditingSupport {

		private final CellEditor cellEditor;

		private final VControl control;

		private final IObservableList valueProperty;

		public ECPListEditingSupport(ColumnViewer viewer, CellEditor cellEditor, VControl control,
			VDomainModelReference domainModelReference, IObservableList valueProperty) {
			super(viewer);
			this.cellEditor = cellEditor;
			this.control = control;
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

			final boolean editable = control.isEnabled() && !control.isReadonly();

			if (ECPCellEditor.class.isInstance(cellEditor)) {
				ECPCellEditor.class.cast(cellEditor).setEditable(editable);
				return true;
			}
			return editable;
		}

		/**
		 * Default implementation always returns <code>null</code> as this will
		 * be handled by the Binding.
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
		 * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object,
		 *      java.lang.Object)
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
			final TableViewerRow viewerRow = (TableViewerRow) cell.getViewerRow();
			final TableItem item = (TableItem) viewerRow.getItem();
			final int index = item.getParent().indexOf(item);
			@SuppressWarnings("restriction")
			final IObservableValue model = new org.eclipse.emf.ecp.edit.internal.swt.util.ECPObservableValue(
				valueProperty, index, String.class);

			final Binding binding = createBinding(target, model);

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

		@SuppressWarnings("deprecation")
		protected IObservableValue doCreateCellEditorObservable(CellEditor cellEditor) {
			if (ECPCellEditor.class.isInstance(cellEditor)) {
				return ((ECPCellEditor) cellEditor).getValueProperty().observe(cellEditor);
			}
			return org.eclipse.jface.databinding.swt.SWTObservables.observeText(cellEditor.getControl(), SWT.FocusOut);
		}

		@Override
		protected final void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
			editingState.binding.updateTargetToModel();
		}

		/**
		 * A ColumnViewerEditorActivationListener to reset the cells after focus
		 * lost.
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
		 * Maintains references to objects that only live for the length of the
		 * edit cycle.
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
