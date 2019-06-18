/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Johannes Faltermeier - sorting + drag&drop
 * Lucas Koehler - adjusted to multi segment replacing table dmr
 *
 ******************************************************************************/
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.view.internal.control.multireference.MultiReferenceSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.view.spi.multisegment.model.MultiSegmentUtil;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Renderer to view and edit the child domain model references of a multisegment which is contained in a DMR.
 *
 * @author Lucas Koehler
 *
 */
public class MultiSegmentChildDmrsSWTRenderer extends MultiReferenceSWTRenderer {

	private AdapterImpl adapter;
	private VTableControl tableControl;
	private Button sortBtn;

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
	 * @param localizationService the localization service
	 */
	@Inject
	// BEGIN COMPLEX CODE
	public MultiSegmentChildDmrsSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider, VTViewTemplateProvider vtViewTemplateProvider,
		ImageRegistryService imageRegistryService, EMFFormsLocalizationService localizationService) {
		// END COMPLEX CODE
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			imageRegistryService, localizationService);
	}

	@Override
	protected void postInit() {
		tableControl = (VTableControl) getViewModelContext().getDomainModel();
		super.postInit();
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control renderControl = super.renderControl(cell, parent);

		adapter = new TableControlAdapter(parent, getTableViewer());
		tableControl.eAdapters().add(adapter);
		/*
		 * The actual EObject on which the DND is executed is the list of child dmrs of the multi segment. But the multi
		 * segment is null when the table control's dmr is not set. It works by using the table control's editing domain
		 */
		addDragAndDropSupport(getTableViewer(), getEditingDomain(tableControl));
		return renderControl;
	}

	@Override
	protected Composite createButtonComposite(Composite parent) throws DatabindingFailedException {
		final Composite buttonComposite = super.createButtonComposite(parent);
		sortBtn = new Button(buttonComposite, SWT.PUSH);
		sortBtn.setText("Sort"); //$NON-NLS-1$
		sortBtn.addSelectionListener(new SortSelectionAdapter());
		final int buttonCount = buttonComposite.getChildren().length;
		GridLayoutFactory.fillDefaults().numColumns(buttonCount).equalWidth(false).applyTo(buttonComposite);
		return buttonComposite;

	}

	@Override
	protected void updateButtonEnabling() {
		super.updateButtonEnabling();
		if (sortBtn != null) {
			sortBtn.setEnabled(getContainer().isPresent() && getVElement().isEffectivelyEnabled());
		}
	}

	@Override
	protected void updateButtonVisibility() {
		super.updateButtonVisibility();
		if (sortBtn != null) {
			sortBtn.setVisible(!getVElement().isEffectivelyReadonly());
		}
	}

	@Override
	protected Optional<EObject> getContainer() {
		return getMultiSegment().map(EObject.class::cast);
	}

	@Override
	protected EReference getEStructuralFeature() {
		return VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES;
	}

	@Override
	protected boolean showAddExistingButton() {
		return false;
	}

	@Override
	protected boolean showAddNewButton() {
		return true;
	}

	@Override
	protected boolean showDeleteButton() {
		return true;
	}

	@Override
	protected boolean showMoveUpButton() {
		return true;
	}

	@Override
	protected boolean showMoveDownButton() {
		return true;
	}

	@Override
	protected void handleAddNew(TableViewer tableViewer, EObject eObject, EStructuralFeature structuralFeature) {
		final ReferenceService referenceService = getViewModelContext().getService(ReferenceService.class);
		referenceService.addNewModelElements(eObject, (EReference) structuralFeature, false);
	}

	/**
	 * Returns an {@link IObservableList} that tracks all child dmrs of the table defined by this renderer's parent
	 * {@link VTableControl}. The child dmrs are stored in the last segment of the {@link VTableControl VTableControl's}
	 * segment. This segment is a multi segment.
	 * <p>
	 * <strong>IMPORTANT:</strong> Can only be used after the field <code>tableControl</code> has been set
	 *
	 * @see org.eclipse.emf.ecp.view.internal.control.multireference.MultiReferenceSWTRenderer#getReferencedElementsList()
	 */
	@Override
	protected IObservableList<?> getReferencedElementsList() throws DatabindingFailedException {
		if (tableControl == null) {
			throw new DatabindingFailedException(
				"The TableControl was null. Therefore, the DMR that contains the multisegment cannot be accessed."); //$NON-NLS-1$
		}
		final Optional<VMultiDomainModelReferenceSegment> multiSegment = getMultiSegment();
		if (tableControl.getDomainModelReference() == null || !multiSegment.isPresent()) {
			// throw new DatabindingFailedException(
			// "The TableControl's domain model reference was null. Therefore, the multi segment that contains the child
			// dmrs cannot be accessed."); //$NON-NLS-1$
			return Observables.emptyObservableList();
		}

		// The dmr referencing the child dmrs of the multi segment defining the table
		final VDomainModelReference childDmrsDMR = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		featureSegment.setDomainModelFeature(getEStructuralFeature().getName());
		childDmrsDMR.getSegments().add(featureSegment);

		return getEMFFormsDatabinding().getObservableList(childDmrsDMR, multiSegment.get());
	}

	/**
	 * @return The {@link VMultiDomainModelReferenceSegment multi segment} of this renderer's {@link VTableControl} that
	 *         contains the child DMRs defining the columns of the table.
	 */
	private Optional<VMultiDomainModelReferenceSegment> getMultiSegment() {
		return MultiSegmentUtil.getMultiSegment(tableControl.getDomainModelReference());
	}

	private void addDragAndDropSupport(TableViewer viewer, EditingDomain editingDomain) {
		final int dndOperations = DND.DROP_MOVE;
		final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		final EditingDomainViewerDropAdapter editingDomainViewerDropAdapter = new EditingDomainViewerDropAdapter(
			editingDomain, viewer);
		viewer.addDropSupport(dndOperations, transfers, editingDomainViewerDropAdapter);
	}

	@Override
	protected void dispose() {
		tableControl.eAdapters().remove(adapter);
		super.dispose();
	}

	@Override
	protected ILabelProvider createLabelProvider() {
		return new TableColumnsLabelProvider(getAdapterFactory());
	}

	/** Adapter set on the {@link VTableControl}. */
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

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);

			if (VTableControl.class.isInstance(notification.getNotifier())
				&& notification.getFeature() == VViewPackage.eINSTANCE.getControl_DomainModelReference()) {
				updateButtonEnabling();
				updateViewerInputObservableList();
				viewer.refresh();
				parent.layout();
			}
		}

		private void updateViewerInputObservableList() {
			try {
				updateTableViewerInputList();
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
				viewer.setInput(Observables.emptyObservableList());
				return;
			}
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

		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			final Optional<VMultiDomainModelReferenceSegment> multiSegment = getMultiSegment();
			if (!multiSegment.isPresent()) {
				return;
			}
			down = !down;
			// EMF API
			@SuppressWarnings("unchecked")
			final List<VDomainModelReference> list = new ArrayList<VDomainModelReference>(
				(List<VDomainModelReference>) multiSegment.get().eGet(getEStructuralFeature(), true));
			Collections.sort(list, new Comparator<VDomainModelReference>() {
				@Override
				public int compare(VDomainModelReference o1, VDomainModelReference o2) {
					final String label1 = getLabelProvider().getText(o1);
					final String label2 = getLabelProvider().getText(o2);
					int result = label1.compareTo(label2);
					if (!down) {
						result *= -1;
					}
					return result;
				}
			});
			final EditingDomain editingDomain = getEditingDomain(multiSegment.get());
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, multiSegment.get(), getEStructuralFeature(), list));
		}
	}

	/**
	 * The label provider.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class TableColumnsLabelProvider extends AdapterFactoryLabelProvider {

		private TableColumnsLabelProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		@Override
		public String getColumnText(Object object, int columnIndex) {
			final String text = super.getColumnText(object, columnIndex);
			if (columnIndex == 0 && VDomainModelReference.class.isInstance(object)) {
				for (final VTableColumnConfiguration configuration : tableControl.getColumnConfigurations()) {
					if (!VWidthConfiguration.class.isInstance(configuration)) {
						continue;
					}
					final VWidthConfiguration widthConfiguration = VWidthConfiguration.class.cast(configuration);
					if (widthConfiguration.getColumnDomainReference() != object) {
						continue;
					}
					return MessageFormat.format(
						"{0} [minWidth={1}, weight={2}]", //$NON-NLS-1$
						text,
						widthConfiguration.getMinWidth(),
						widthConfiguration.getWeight());
				}
			}
			return text;
		}
	}

}
