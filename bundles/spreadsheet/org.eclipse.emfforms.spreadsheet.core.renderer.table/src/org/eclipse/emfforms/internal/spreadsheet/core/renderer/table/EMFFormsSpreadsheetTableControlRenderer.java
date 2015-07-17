/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core.renderer.table;

import java.util.Collections;

import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.internal.spreadsheet.core.EMFFormsSpreadsheetViewModelContext;
import org.eclipse.emfforms.internal.spreadsheet.core.renderer.EMFFormsSpreadsheetControlRenderer;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsExportTableParent;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsIdProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterRegistry;

/**
 * Spreadsheet renderer for {@link VTableControl}.
 *
 * @author Eugen Neufeld
 */
@SuppressWarnings("restriction")
public class EMFFormsSpreadsheetTableControlRenderer extends EMFFormsAbstractSpreadsheetRenderer<VTableControl> {

	private final EMFFormsDatabinding emfformsDatabinding;
	private final EMFFormsLabelProvider emfformsLabelProvider;
	private final ReportService reportService;
	private final EMFFormsSpreadsheetRendererFactory rendererFactory;
	private final VTViewTemplateProvider vtViewTemplateProvider;
	private final EMFFormsIdProvider emfFormsIdProvider;
	private final EMFFormsSpreadsheetValueConverterRegistry converterRegistry;

	/**
	 * Default constructor.
	 *
	 * @param emfformsDatabinding The EMFFormsDatabinding to use
	 * @param emfformsLabelProvider The EMFFormsLabelProvider to use
	 * @param reportService The {@link ReportService}
	 * @param rendererFactory The EMFFormsSpreadsheetRendererFactory to use
	 * @param vtViewTemplateProvider The VTViewTemplateProvider to use
	 * @param emfFormsIdProvider The {@link EMFFormsIdProvider}
	 * @param converterRegistry the {@link EMFFormsSpreadsheetValueConverterRegistry}
	 */
	public EMFFormsSpreadsheetTableControlRenderer(
		EMFFormsDatabinding emfformsDatabinding,
		EMFFormsLabelProvider emfformsLabelProvider,
		ReportService reportService,
		EMFFormsSpreadsheetRendererFactory rendererFactory,
		VTViewTemplateProvider vtViewTemplateProvider,
		EMFFormsIdProvider emfFormsIdProvider,
		EMFFormsSpreadsheetValueConverterRegistry converterRegistry) {
		this.emfformsDatabinding = emfformsDatabinding;
		this.emfformsLabelProvider = emfformsLabelProvider;
		this.reportService = reportService;
		this.rendererFactory = rendererFactory;
		this.vtViewTemplateProvider = vtViewTemplateProvider;
		this.emfFormsIdProvider = emfFormsIdProvider;
		this.converterRegistry = converterRegistry;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer#render(org.apache.poi.ss.usermodel.Workbook,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext,
	 *      org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget)
	 */

	@Override
	public int render(Workbook workbook, VTableControl vElement, final ViewModelContext viewModelContext,
		EMFFormsSpreadsheetRenderTarget eMFFormsSpreadsheetRenderTarget) {
		final EMFFormsSpreadsheetControlRenderer controlRenderer = new EMFFormsSpreadsheetControlRenderer(
			emfformsDatabinding,
			emfformsLabelProvider, reportService, vtViewTemplateProvider, emfFormsIdProvider, converterRegistry);
		int numColumns = 0;
		try {
			final EMFFormsExportTableParent exportTableParent = (EMFFormsExportTableParent) viewModelContext
				.getContextValue(EMFFormsExportTableParent.EXPORT_TABLE_PARENT);

			VDomainModelReference dmrToResolve = EcoreUtil.copy(vElement.getDomainModelReference());
			if (exportTableParent != null) {
				final VIndexDomainModelReference indexDMR = exportTableParent.getIndexDMRToExtend();
				indexDMR.setTargetDMR(dmrToResolve);

				dmrToResolve = exportTableParent.getIndexDMRToResolve();
			}

			final IObservableList observableList = emfformsDatabinding.getObservableList(
				dmrToResolve, viewModelContext.getDomainModel());

			final VTableDomainModelReference tableDomainModelReference = (VTableDomainModelReference) vElement
				.getDomainModelReference();

			for (int i = 0; i < Math.max(observableList.size(), 3); i++) {
				String prefixName = (String) emfformsLabelProvider.getDisplayName(
					tableDomainModelReference.getDomainModelReference())
					.getValue();
				if (prefixName == null || prefixName.length() == 0) {
					try {
						prefixName = EStructuralFeature.class.cast(
							emfformsDatabinding.getValueProperty(
								tableDomainModelReference.getDomainModelReference(),
								viewModelContext.getDomainModel()).getValueType())
							.getName();
					} catch (final DatabindingFailedException ex) {
						reportService
							.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
					}
				}

				final VIndexDomainModelReference indexDMR = VIndexdmrFactory.eINSTANCE
					.createIndexDomainModelReference();
				indexDMR.setPrefixDMR(EcoreUtil.copy(tableDomainModelReference.getDomainModelReference()));
				indexDMR.setIndex(i);

				EMFFormsExportTableParent tableParent;
				if (exportTableParent == null) {
					tableParent = new EMFFormsExportTableParent(indexDMR, indexDMR,
						prefixName);
				} else {
					final VIndexDomainModelReference wrapper = exportTableParent.getIndexDMRToExtend();
					wrapper.setTargetDMR(indexDMR);
					tableParent = new EMFFormsExportTableParent(indexDMR, exportTableParent.getIndexDMRToResolve(),
						exportTableParent.getLabelPrefix()
							+ "_" + prefixName); //$NON-NLS-1$
				}
				for (final VDomainModelReference domainModelReference : tableDomainModelReference
					.getColumnDomainModelReferences()) {

					final VControl vControl = VViewFactory.eINSTANCE.createControl();

					vControl.setDomainModelReference(EcoreUtil.copy(domainModelReference));
					// if (exportTableParent != null) {
					// indexDMR.setTargetDMR(EcoreUtil.copy(domainModelReference));
					// vControl.setDomainModelReference(EcoreUtil.copy(indexDMR));
					// }
					final ViewModelContext subViewModelContext = new EMFFormsSpreadsheetViewModelContext(
						(VView) viewModelContext.getViewModel(),
						viewModelContext.getDomainModel());
					subViewModelContext.putContextValue(EMFFormsExportTableParent.EXPORT_TABLE_PARENT, tableParent);

					numColumns += controlRenderer.render(workbook, vControl, subViewModelContext,
						new EMFFormsSpreadsheetRenderTarget(
							eMFFormsSpreadsheetRenderTarget.getSheetName(), eMFFormsSpreadsheetRenderTarget.getRow(),
							eMFFormsSpreadsheetRenderTarget.getColumn()
								+ numColumns));
				}

				if (vElement.getDetailEditing() != DetailEditing.NONE) {
					EObject tableEntry;
					if (observableList.size() > i) {
						tableEntry = (EObject) observableList.get(i);
					} else {
						tableEntry = EcoreUtil.create(EReference.class.cast(observableList.getElementType())
							.getEReferenceType());
					}
					final VView viewModel = getView(vElement, tableEntry);

					final ViewModelContext subViewModelContext = new EMFFormsSpreadsheetViewModelContext(viewModel,
						viewModelContext.getDomainModel());

					subViewModelContext.putContextValue(EMFFormsExportTableParent.EXPORT_TABLE_PARENT, tableParent);
					try {
						final EMFFormsAbstractSpreadsheetRenderer<VElement> renderer = rendererFactory
							.getRendererInstance(viewModel, subViewModelContext);
						final int renderedColumns = renderer.render(workbook,
							viewModel, subViewModelContext, new EMFFormsSpreadsheetRenderTarget(
								eMFFormsSpreadsheetRenderTarget.getSheetName(),
								eMFFormsSpreadsheetRenderTarget.getRow(), eMFFormsSpreadsheetRenderTarget.getColumn()
									+ numColumns));

						numColumns += renderedColumns;
					} catch (final EMFFormsNoRendererException ex) {
						reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
					}
				}
			}

		} catch (final DatabindingFailedException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		} catch (final NoLabelFoundException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}
		return numColumns;
	}

	private VView getView(VTableControl tableControl, EObject domainObject) throws DatabindingFailedException {
		VView detailView = tableControl.getDetailView();
		if (detailView == null) {
			detailView = ViewProviderHelper.getView(domainObject, Collections.<String, Object> emptyMap());
		}
		return detailView;
	}
}
