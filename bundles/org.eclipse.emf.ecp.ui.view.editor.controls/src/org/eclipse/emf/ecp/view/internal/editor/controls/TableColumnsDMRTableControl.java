/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Johannes Faltermeier - sorting + drag&drop
 * Lucas Koehler - adjusted to multi segment replacing table dmr
 *
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.handler.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.internal.editor.handler.FeatureSegmentGenerator;
import org.eclipse.emf.ecp.view.internal.editor.handler.SimpleCreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author Eugen
 *
 */
public class TableColumnsDMRTableControl extends SimpleControlSWTRenderer {

	private final EMFDataBindingContext viewModelDBC;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	@Inject
	public TableColumnsDMRTableControl(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		viewModelDBC = new EMFDataBindingContext();
	}

	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryLabelProvider labelProvider;
	private AdapterImpl adapter;
	private VTableControl tableControl;
	private TableViewer viewer;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(final Composite parent) throws DatabindingFailedException {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);
		final Composite titleComposite = new Composite(composite, SWT.NONE);
		titleComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(titleComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(titleComposite);

		final Label filler = new Label(titleComposite, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(filler);

		final Composite buttonComposite = new Composite(titleComposite, SWT.NONE);
		buttonComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(true).applyTo(buttonComposite);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.BEGINNING).grab(false, false).applyTo(buttonComposite);

		final Button buttonSort = new Button(buttonComposite, SWT.PUSH);
		buttonSort.setText("Sort"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(false, false).applyTo(buttonSort);

		final Button buttonAdd = new Button(buttonComposite, SWT.PUSH);
		buttonAdd.setText("Add"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(false, false).applyTo(buttonAdd);

		final Button buttonRemove = new Button(buttonComposite, SWT.PUSH);
		buttonRemove.setText("Remove"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(false, false).applyTo(buttonRemove);

		final Composite tableComposite = new Composite(composite, SWT.NONE);
		tableComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).hint(1, 100)
			.applyTo(tableComposite);
		final TableColumnLayout layout = new TableColumnLayout();
		tableComposite.setLayout(layout);

		viewer = new TableViewer(tableComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(viewer.getControl());

		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn tableColumn = column.getColumn();
		final EMFFormsLabelProvider emfFormsLabelProvider = Activator.getDefault().getEMFFormsLabelProvider();// TODO
		try {
			final IObservableValue labelText = emfFormsLabelProvider.getDisplayName(
				getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
			final IObservableValue tooltip = emfFormsLabelProvider.getDescription(
				getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
			viewModelDBC.bindValue(WidgetProperties.text().observe(tableColumn), labelText);
			viewModelDBC.bindValue(WidgetProperties.tooltipText().observe(tableColumn), tooltip);
		} catch (final NoLabelFoundException e) {
			// FIXME Expectations?
			getReportService().report(new RenderingFailedReport(e));
			tableColumn.setText(e.getMessage());
			tableColumn.setToolTipText(e.toString());
		}

		layout.setColumnData(column.getColumn(), new ColumnWeightData(1, true));

		viewer.setLabelProvider(labelProvider);
		viewer.setContentProvider(new ObservableListContentProvider());

		tableControl = (VTableControl) getViewModelContext().getDomainModel();

		/*
		 * The actual EObject on which the DND is executed is the list of child dmrs of the multi segment. But the multi
		 * segment is null when the table control's dmr is not set. It works by using the table control's editing domain
		 */
		addDragAndDropSupport(viewer, getEditingDomain(tableControl));

		viewer.setInput(getChildDmrsObservableList());

		adapter = new TableControlAdapter(composite, viewer);
		tableControl.eAdapters().add(adapter);

		buttonSort.addSelectionListener(new SortSelectionAdapter());

		buttonAdd.addSelectionListener(new AddSelectionAdapter(tableComposite));

		buttonRemove.addSelectionListener(new RemoveSelectionAdapter(viewer));

		return composite;
	}

	/**
	 * Returns an {@link IObservableList} that tracks all child dmrs of the table defined by this renderer's parent
	 * {@link VTableControl}. The child dmrs are stored in the last segment of the {@link VTableControl VTableControl's}
	 * segment. This segment is a multi segment.
	 * <p>
	 * <strong>IMPORTANT:</strong> Can only be used after the field <code>tableControl</code> has been set
	 *
	 * @return The observable list of child dmrs that define the table's columns
	 * @throws DatabindingFailedException If the databinding failed
	 */
	private IObservableList getChildDmrsObservableList() throws DatabindingFailedException {
		if (tableControl == null) {
			throw new DatabindingFailedException(
				"The TableControl was null. Therefore, the DMR that contains the multisegment cannot be accessed."); //$NON-NLS-1$
		}
		if (tableControl.getDomainModelReference() == null) {
			// throw new DatabindingFailedException(
			// "The TableControl's domain model reference was null. Therefore, the multi segment that contains the child
			// dmrs cannot be accessed."); //$NON-NLS-1$
			return Observables.emptyObservableList();
		}

		// The dmr referencing the child dmrs of the multi segment defining the table
		final VDomainModelReference childDmrsDMR = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		featureSegment.setDomainModelFeature(
			getChildDmrsEReference().getName());
		childDmrsDMR.getSegments().add(featureSegment);

		return Activator.getDefault().getEMFFormsDatabinding().getObservableList(childDmrsDMR, getMultiSegment());
	}

	/**
	 * @return The EReference describing the child dmr feature of the multi segment
	 */
	private EReference getChildDmrsEReference() {
		return VMultisegmentPackage.eINSTANCE.getMultiDomainModelReferenceSegment_ChildDomainModelReferences();
	}

	/**
	 * @return The {@link VMultiDomainModelReferenceSegment multi segment} of this renderer's {@link VTableControl} that
	 *         contains the child DMRs defining the columns of the table.
	 */
	private VMultiDomainModelReferenceSegment getMultiSegment() {
		// The table control's dmr's last segment must always be a multi segment. This multi segment contains the child
		// dmrs defining the table columns
		final EList<VDomainModelReferenceSegment> segments = tableControl.getDomainModelReference().getSegments();
		final VDomainModelReferenceSegment lastSegment = segments.get(segments.size() - 1);
		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) lastSegment;
		return multiSegment;
	}

	/**
	 * @param viewer
	 * @param editingDomain
	 */
	private void addDragAndDropSupport(TableViewer viewer, EditingDomain editingDomain) {
		final int dndOperations = DND.DROP_MOVE;
		final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		final EditingDomainViewerDropAdapter editingDomainViewerDropAdapter = new EditingDomainViewerDropAdapter(
			editingDomain, viewer);
		viewer.addDropSupport(dndOperations, transfers, editingDomainViewerDropAdapter);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#postInit()
	 */
	@Override
	protected void postInit() {
		super.postInit();
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		labelProvider.dispose();
		composedAdapterFactory.dispose();
		tableControl.eAdapters().remove(adapter);
		viewModelDBC.dispose();
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return "No columns set"; //$NON-NLS-1$
	}

	/**
	 * Adapter set on the {@link VTableControl}.
	 *
	 */
	private final class TableControlAdapter extends AdapterImpl {
		private final Composite parent;
		private final TableViewer viewer;

		/**
		 * @param parent
		 * @param viewer
		 */
		private TableControlAdapter(Composite parent, TableViewer viewer) {
			this.parent = parent;
			this.viewer = viewer;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);

			if (VTableControl.class.isInstance(notification.getNotifier())
				&& notification.getFeature() == VViewPackage.eINSTANCE.getControl_DomainModelReference()) {
				updateViewerInputObservableList();
				viewer.refresh();
				parent.layout();
			}
		}

		private void updateViewerInputObservableList() {
			IObservableList list;
			try {
				list = getChildDmrsObservableList();
			} catch (final DatabindingFailedException ex) {
				Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
				viewer.setInput(Observables.emptyObservableList());
				return;
			}
			viewer.setInput(list);
		}
	}

	/**
	 * Reacts on remove button clicks.
	 *
	 */
	private final class RemoveSelectionAdapter extends SelectionAdapter {
		private final TableViewer viewer;

		/**
		 * @param viewer
		 */
		private RemoveSelectionAdapter(TableViewer viewer) {
			this.viewer = viewer;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			final IStructuredSelection selection = IStructuredSelection.class.cast(viewer.getSelection());

			final EditingDomain editingDomain = getEditingDomain(getMultiSegment());
			editingDomain.getCommandStack().execute(
				RemoveCommand.create(editingDomain, getMultiSegment(), getChildDmrsEReference(), selection.toList()));
		}
	}

	/**
	 * Reacts on add button clicks.
	 *
	 */
	private final class AddSelectionAdapter extends SelectionAdapter {
		private final Composite tableComposite;

		/**
		 * @param tableComposite
		 * @param viewer
		 */
		private AddSelectionAdapter(Composite tableComposite) {
			this.tableComposite = tableComposite;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			if (tableControl.getDomainModelReference() == null) {
				Activator.getDefault().getReportService()
					.report(new AbstractReport("Cannot add column. The VTableControl's DMR is null.")); //$NON-NLS-1$
				return;
			}

			IValueProperty valueProperty;
			try {
				valueProperty = Activator
					.getDefault()
					.getEMFFormsDatabinding()
					.getValueProperty(tableControl.getDomainModelReference(),
						Helper.getRootEClass(getViewModelContext().getDomainModel()));
			} catch (final DatabindingFailedException ex) {
				Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
				return;
			}
			final EClass eclass = EReference.class.cast(valueProperty.getValueType()).getEReferenceType();

			final SimpleCreateDomainModelReferenceWizard wizard = new SimpleCreateDomainModelReferenceWizard(
				getMultiSegment(), getChildDmrsEReference(), getEditingDomain(getMultiSegment()), eclass,
				"New Column DMR", null, //$NON-NLS-1$
				new EStructuralFeatureSelectionValidator() {

					@Override
					public String isValid(EStructuralFeature structuralFeature) {
						// Every selection is valid
						return null;
					}
				}, new FeatureSegmentGenerator());

			final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			wd.open();
			tableComposite.layout();
		}
	}

	/**
	 * Reacts on sort button clicks.
	 *
	 * @author jfaltermeier
	 *
	 */
	private final class SortSelectionAdapter extends SelectionAdapter {
		private boolean down;

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			down = !down;
			// EMF API
			@SuppressWarnings("unchecked")
			final List<VDomainModelReference> list = new ArrayList<VDomainModelReference>(
				(List<VDomainModelReference>) getMultiSegment().eGet(getChildDmrsEReference(), true));
			Collections.sort(list, new Comparator<VDomainModelReference>() {
				@Override
				public int compare(VDomainModelReference o1, VDomainModelReference o2) {
					final String label1 = labelProvider.getText(o1);
					final String label2 = labelProvider.getText(o2);
					int result = label1.compareTo(label2);
					if (!down) {
						result *= -1;
					}
					return result;
				}
			});
			final EditingDomain editingDomain = getEditingDomain(getMultiSegment());
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, getMultiSegment(), getChildDmrsEReference(), list));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#rootDomainModelChanged()
	 */
	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {
		final IObservableList oldList = (IObservableList) viewer.getInput();
		oldList.dispose();

		final IObservableList list = getChildDmrsObservableList();
		// addRelayoutListenerIfNeeded(list, composite);
		viewer.setInput(list);
	}

}
