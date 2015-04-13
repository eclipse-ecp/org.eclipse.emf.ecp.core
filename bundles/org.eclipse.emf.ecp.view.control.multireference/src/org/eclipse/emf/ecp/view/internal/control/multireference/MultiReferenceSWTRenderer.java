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
package org.eclipse.emf.ecp.view.internal.control.multireference;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.controls.TableViewerColumnBuilder;
import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Renderer for MultiReferenceControl.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class MultiReferenceSWTRenderer extends AbstractControlSWTRenderer<VControl> {

	private Label validationIcon;
	private AdapterFactoryLabelProvider labelProvider;
	private ComposedAdapterFactory composedAdapterFactory;
	private TableViewer tableViewer;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell,
	 *      org.eclipse.emf.ecp.view.spi.swt.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		if (cell.getRow() != 0 || cell.getColumn() != 0 || cell.getRenderer() != this) {
			throw new IllegalArgumentException("Wrong parameter passed!"); //$NON-NLS-1$
		}

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);

		final Iterator<Setting> settings = getVElement().getDomainModelReference().getIterator();
		if (!settings.hasNext()) {
			return null;
		}

		final Setting mainSetting = settings.next();

		createTitleComposite(composite, mainSetting);

		createLabelProvider();

		final Composite controlComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL)
			.hint(1, 300)
			.applyTo(controlComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(controlComposite);
		createContent(controlComposite, mainSetting);

		return composite;
	}

	private void createLabelProvider() {
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
		labelProvider.setFireLabelUpdateNotifications(true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		composedAdapterFactory.dispose();
		labelProvider.dispose();
		super.dispose();
	}

	private void createTitleComposite(Composite composite, final Setting mainSetting)
		throws NoPropertyDescriptorFoundExeption {
		final Composite titleComposite = new Composite(composite, SWT.NONE);
		titleComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING)
			.applyTo(titleComposite);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(titleComposite);

		final Label filler = new Label(titleComposite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(filler);

		// VALIDATION
		// // set the size of the label to the size of the image
		validationIcon = createValidationIcon(titleComposite);
		GridDataFactory.fillDefaults().hint(16, 17).grab(false, false).applyTo(validationIcon);

		final Composite buttonComposite = new Composite(titleComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(true).applyTo(buttonComposite);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.END, SWT.FILL)
			.applyTo(buttonComposite);

		final Button btnAddExisting = new Button(buttonComposite, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(btnAddExisting);
		btnAddExisting.setImage(Activator.getImage("icons/link.png")); //$NON-NLS-1$
		btnAddExisting.setToolTipText(LocalizationServiceHelper.getString(getClass(),
			MessageKeys.MultiReferenceSWTRenderer_addExistingTooltip));
		btnAddExisting.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				handleAddExisting(tableViewer, mainSetting);
			}

		});

		final Button btnAddNew = new Button(buttonComposite, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(btnAddNew);
		btnAddNew.setImage(Activator.getImage("icons/link_add.png")); //$NON-NLS-1$
		btnAddNew.setToolTipText(LocalizationServiceHelper.getString(getClass(),
			MessageKeys.MultiReferenceSWTRenderer_addNewTooltip));
		btnAddNew.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				handleAddNew(tableViewer, mainSetting);
			}

		});

		final Button btnDelete = new Button(buttonComposite, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(btnDelete);
		btnDelete.setImage(Activator.getImage("icons/delete.png")); //$NON-NLS-1$
		btnDelete.setToolTipText(LocalizationServiceHelper.getString(getClass(),
			MessageKeys.MultiReferenceSWTRenderer_deleteTooltip));
		btnDelete.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				handleDelete(tableViewer, mainSetting);
			}

		});
		if (getVElement().isReadonly()) {
			btnAddExisting.setEnabled(false);
			btnAddNew.setEnabled(false);
			btnDelete.setEnabled(false);
		}

	}

	private void createContent(Composite composite, Setting mainSetting) {
		tableViewer = new TableViewer(composite, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION
			| SWT.BORDER);
		tableViewer.getTable().setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_multireference"); //$NON-NLS-1$
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

		TableViewerEditor.create(tableViewer, null, actSupport, ColumnViewerEditor.TABBING_HORIZONTAL
			| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR | ColumnViewerEditor.TABBING_VERTICAL
			| ColumnViewerEditor.KEYBOARD_ACTIVATION);
		ColumnViewerToolTipSupport.enableFor(tableViewer);

		final ECPTableViewerComparator comparator = new ECPTableViewerComparator();
		tableViewer.setComparator(comparator);

		final ObservableListContentProvider cp = new ObservableListContentProvider();

		final EStructuralFeature eStructuralFeature = mainSetting.getEStructuralFeature();
		String text = eStructuralFeature.getName();
		String tooltipText = eStructuralFeature.getName();
		final IItemPropertyDescriptor itemPropertyDescriptor = getItemPropertyDescriptor(mainSetting);
		if (itemPropertyDescriptor != null) {
			text = itemPropertyDescriptor.getDisplayName(null);
			tooltipText = itemPropertyDescriptor.getDescription(null);
		}

		final TableViewerColumn column = TableViewerColumnBuilder
			.create()
			.setText(text)
			.setToolTipText(tooltipText)
			.setResizable(false)
			.setMoveable(false)
			.setStyle(SWT.NONE)
			.build(tableViewer);

		column.getColumn().addSelectionListener(
			getSelectionAdapter(tableViewer, comparator, column.getColumn(), 0));

		tableViewer.setLabelProvider(labelProvider);
		tableViewer.setContentProvider(cp);
		final IObservableList list = EMFEditObservables.observeList(getEditingDomain(mainSetting),
			mainSetting.getEObject(), mainSetting.getEStructuralFeature());
		tableViewer.setInput(list);

		final TableColumnLayout layout = new TableColumnLayout();
		composite.setLayout(layout);
		layout.setColumnData(column.getColumn(), new ColumnWeightData(1, false));

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				final EObject selectedObject = (EObject) IStructuredSelection.class.cast(event.getSelection())
					.getFirstElement();
				handleDoubleClick(selectedObject);
			}

		});
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

	/**
	 * Method for handling a double click.
	 *
	 * @param selectedObject the selected {@link EObject}
	 */
	protected void handleDoubleClick(EObject selectedObject) {
		final ReferenceService referenceService = getViewModelContext().getService(ReferenceService.class);
		referenceService.openInNewContext(selectedObject);
	}

	/**
	 * Method for adding an existing element.
	 *
	 * @param tableViewer the {@link TableViewer}
	 * @param setting the {@link Setting} to add to
	 */
	protected void handleAddExisting(TableViewer tableViewer, Setting setting) {
		final ReferenceService referenceService = getViewModelContext().getService(ReferenceService.class);
		referenceService.addExistingModelElements(setting.getEObject(), (EReference) setting.getEStructuralFeature());
		referenceService.openInNewContext(setting.getEObject());
	}

	/**
	 * Method for adding a new element.
	 *
	 * @param tableViewer the {@link TableViewer}
	 * @param setting the {@link Setting} to add to
	 */
	protected void handleAddNew(TableViewer tableViewer, Setting setting) {
		final ReferenceService referenceService = getViewModelContext().getService(ReferenceService.class);
		referenceService.addNewModelElements(setting.getEObject(),
			(EReference) setting.getEStructuralFeature());

	}

	/**
	 * Method for deleting elements.
	 *
	 * @param tableViewer the {@link TableViewer}
	 * @param mainSetting the {@link Setting} to delete from
	 */
	protected void handleDelete(TableViewer tableViewer, Setting setting) {
		final DeleteService deleteService = getViewModelContext().getService(DeleteService.class);
		if (deleteService == null) {
			/*
			 * #getService(Class<?>) will report to the reportservice if it could not be found
			 * -> simply return here
			 */
			return;
		}
		@SuppressWarnings("unchecked")
		final List<Object> deletionList = IStructuredSelection.class.cast(tableViewer.getSelection()).toList();
		final EditingDomain editingDomain = getEditingDomain(setting);

		/* assured by #isApplicable */
		final EReference reference = EReference.class.cast(setting.getEStructuralFeature());

		if (reference.isContainment()) {
			deleteService.deleteElements(editingDomain, deletionList);
		} else {
			deleteService.removeElements(editingDomain, setting.getEObject(), reference, deletionList);
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
}
