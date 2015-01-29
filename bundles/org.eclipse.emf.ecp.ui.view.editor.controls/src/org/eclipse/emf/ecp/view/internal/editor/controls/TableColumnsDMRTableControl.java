/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Johannes Faltermeier - sorting + drag&drop
 *
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
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

/**
 * @author Eugen
 *
 */
@SuppressWarnings("restriction")
public class TableColumnsDMRTableControl extends SimpleControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public TableColumnsDMRTableControl(VControl vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryLabelProvider labelProvider;
	private AdapterImpl adapter;
	private VTableControl tableControl;
	private Setting setting;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(final Composite parent) {
		setting = getVElement().getDomainModelReference().getIterator().next();
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

		final TableViewer viewer = new TableViewer(tableComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(viewer.getControl());

		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		final IItemPropertyDescriptor propertyDescriptor = getItemPropertyDescriptor(setting);
		column.getColumn().setText(propertyDescriptor.getDisplayName(null));
		column.getColumn().setToolTipText(propertyDescriptor.getDescription(null));
		layout.setColumnData(column.getColumn(), new ColumnWeightData(1, true));

		viewer.setLabelProvider(labelProvider);
		viewer.setContentProvider(new ObservableListContentProvider());
		addDragAndDropSupport(viewer, getEditingDomain(setting));

		final IObservableList list = EMFEditObservables.observeList(getEditingDomain(setting),
			setting.getEObject(), setting.getEStructuralFeature());
		viewer.setInput(list);

		tableControl = (VTableControl) getViewModelContext().getDomainModel();
		adapter = new TableControlAdapter(parent, viewer);
		tableControl.eAdapters().add(adapter);

		buttonSort.addSelectionListener(new SortSelectionAdapter());

		buttonAdd.addSelectionListener(new AddSelectionAdapter(tableComposite, viewer));

		buttonRemove.addSelectionListener(new RemoveSelectionAdapter(viewer));

		return composite;
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
			if (notification.getFeature() == VTablePackage.eINSTANCE
				.getTableDomainModelReference_ColumnDomainModelReferences()) {
				viewer.refresh();
				parent.layout();
			}
			if (VTableDomainModelReference.class.isInstance(notification.getNotifier())) {
				updateSetting();
				viewer.refresh();
				parent.layout();
			}

			if (VTableControl.class.isInstance(notification.getNotifier())
				&& (VTableDomainModelReference.class.isInstance(notification.getNewValue()) || VTableDomainModelReference.class
					.isInstance(notification.getOldValue()))) {
				updateSetting();
				viewer.refresh();
				parent.layout();
			}
		}

		private void updateSetting() {
			final Iterator<Setting> iterator = getVElement().getDomainModelReference().getIterator();
			if (iterator.hasNext()) {
				setting = iterator.next();
				final IObservableList list = EMFEditObservables.observeList(getEditingDomain(setting),
					setting.getEObject(), setting.getEStructuralFeature());
				viewer.setInput(list);
			}
			else {
				viewer.setInput(Observables.emptyObservableList());
			}

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

			final EditingDomain editingDomain = getEditingDomain(setting);
			editingDomain.getCommandStack().execute(RemoveCommand.create(editingDomain, setting.getEObject(),
				setting.getEStructuralFeature(), selection.toList()));
		}
	}

	/**
	 * Reacts on add button clicks.
	 *
	 */
	private final class AddSelectionAdapter extends SelectionAdapter {
		private final Composite tableComposite;
		private final TableViewer viewer;

		/**
		 * @param tableComposite
		 * @param viewer
		 */
		private AddSelectionAdapter(Composite tableComposite, TableViewer viewer) {
			this.tableComposite = tableComposite;
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
			final VTableDomainModelReference tableDomainModelReference = VTableDomainModelReference.class
				.cast(setting.getEObject());

			final EClass eclass = EReference.class.cast(
				tableDomainModelReference.getEStructuralFeatureIterator().next()).getEReferenceType();

			final Collection<EClass> classes = ECPUtil.getSubClasses(VViewPackage.eINSTANCE
				.getDomainModelReference());

			final CreateDomainModelReferenceWizard wizard = new CreateDomainModelReferenceWizard(
				setting, getEditingDomain(setting), eclass, "New Reference Element", //$NON-NLS-1$
				Messages.NewModelElementWizard_WizardTitle_AddModelElement,
				Messages.NewModelElementWizard_PageTitle_AddModelElement,
				Messages.NewModelElementWizard_PageDescription_AddModelElement,
				(VDomainModelReference) IStructuredSelection.class.cast(viewer.getSelection()).getFirstElement());

			final SelectionComposite<TreeViewer> helper = CompositeFactory.getSelectModelClassComposite(
				new HashSet<EPackage>(),
				new HashSet<EPackage>(), classes);
			wizard.setCompositeProvider(helper);

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
				(List<VDomainModelReference>) setting.get(true));
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
			final EditingDomain editingDomain = getEditingDomain(setting);
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, setting.getEObject(), setting.getEStructuralFeature(), list));
		}
	}

}
