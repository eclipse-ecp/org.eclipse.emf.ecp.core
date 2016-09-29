/*******************************************************************************
 * Copyright (c) 2015 EclipseSource Muenchen GmbH and others.
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

import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.internal.spreadsheet.core.renderer.EMFFormsSpreadsheetControlRenderer;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsExportTableParent;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsIdProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetFormatDescriptionProvider;
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

	private final EMFFormsDatabindingEMF emfformsDatabinding;
	private final EMFFormsLabelProvider emfformsLabelProvider;
	private final ReportService reportService;
	private final EMFFormsSpreadsheetRendererFactory rendererFactory;
	private final VTViewTemplateProvider vtViewTemplateProvider;
	private final EMFFormsIdProvider emfFormsIdProvider;
	private final EMFFormsSpreadsheetValueConverterRegistry converterRegistry;
	private final EMFFormsSpreadsheetFormatDescriptionProvider formatDescriptionProvider;
	private final EMFFormsDomainExpander domainExpander;

	/**
	 * Default constructor.
	 *
	 * @param emfformsDatabinding The EMFFormsDatabindingEMF to use
	 * @param emfformsLabelProvider The EMFFormsLabelProvider to use
	 * @param reportService The {@link ReportService}
	 * @param rendererFactory The EMFFormsSpreadsheetRendererFactory to use
	 * @param vtViewTemplateProvider The VTViewTemplateProvider to use
	 * @param emfFormsIdProvider The {@link EMFFormsIdProvider}
	 * @param converterRegistry The {@link EMFFormsSpreadsheetValueConverterRegistry}
	 * @param formatDescriptionProvider The {@link EMFFormsSpreadsheetFormatDescriptionProvider}
	 * @param domainExpander the {@link EMFFormsDomainExpander}
	 */
	// BEGIN COMPLEX CODE
	public EMFFormsSpreadsheetTableControlRenderer(
		EMFFormsDatabindingEMF emfformsDatabinding,
		EMFFormsLabelProvider emfformsLabelProvider,
		ReportService reportService,
		EMFFormsSpreadsheetRendererFactory rendererFactory,
		VTViewTemplateProvider vtViewTemplateProvider,
		EMFFormsIdProvider emfFormsIdProvider,
		EMFFormsSpreadsheetValueConverterRegistry converterRegistry,
		EMFFormsSpreadsheetFormatDescriptionProvider formatDescriptionProvider,
		EMFFormsDomainExpander domainExpander) {
		this.emfformsDatabinding = emfformsDatabinding;
		this.emfformsLabelProvider = emfformsLabelProvider;
		this.reportService = reportService;
		this.rendererFactory = rendererFactory;
		this.vtViewTemplateProvider = vtViewTemplateProvider;
		this.emfFormsIdProvider = emfFormsIdProvider;
		this.converterRegistry = converterRegistry;
		this.formatDescriptionProvider = formatDescriptionProvider;
		this.domainExpander = domainExpander;
	}
	// END COMPLEX CODE

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer#render(org.apache.poi.ss.usermodel.Workbook,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext,
	 *      org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget)
	 */
	// BEGIN COMPLEX CODE
	@Override
	public int render(Workbook workbook, VTableControl vElement, final ViewModelContext viewModelContext,
		EMFFormsSpreadsheetRenderTarget eMFFormsSpreadsheetRenderTarget) {
		final EMFFormsSpreadsheetControlRenderer controlRenderer = new EMFFormsSpreadsheetControlRenderer(
			emfformsDatabinding, emfformsLabelProvider, reportService, vtViewTemplateProvider, emfFormsIdProvider,
			converterRegistry, formatDescriptionProvider, domainExpander);
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

			try {
				domainExpander.prepareDomainObject(dmrToResolve, viewModelContext.getDomainModel());
			} catch (final EMFFormsExpandingFailedException ex) {
				reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
				return numColumns;
			}

			final Setting tableSetting = emfformsDatabinding.getSetting(
				dmrToResolve, viewModelContext.getDomainModel());
			@SuppressWarnings("unchecked")
			final EList<EObject> tableEntries = (EList<EObject>) tableSetting.get(true);
			final VTableDomainModelReference tableDomainModelReference = (VTableDomainModelReference) vElement
				.getDomainModelReference();

			for (int i = 0; i < getNumberOfExportElements(vElement, tableSetting); i++) {
				final String prefixName = getPrefixName(tableSetting, tableDomainModelReference,
					viewModelContext.getDomainModel().eClass(), i);

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
					final ViewModelContext subViewModelContext = viewModelContext.getChildContext(
						viewModelContext.getDomainModel(), vElement, null);
					subViewModelContext.putContextValue(EMFFormsExportTableParent.EXPORT_TABLE_PARENT, tableParent);

					numColumns += controlRenderer.render(workbook, vControl, subViewModelContext,
						new EMFFormsSpreadsheetRenderTarget(
							eMFFormsSpreadsheetRenderTarget.getSheetName(), eMFFormsSpreadsheetRenderTarget.getRow(),
							eMFFormsSpreadsheetRenderTarget.getColumn()
								+ numColumns));
				}

				if (vElement.getDetailEditing() != DetailEditing.NONE) {
					final EObject tableEntry = getTableEntry(tableEntries, i,
						(EReference) tableSetting.getEStructuralFeature());
					final VView viewModel = getView(vElement, tableEntry, viewModelContext);
					if (viewModel == null) {
						continue;
					}

					final ViewModelContext subViewModelContext = viewModelContext.getChildContext(
						viewModelContext.getDomainModel(), vElement, viewModel);

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

	// END COMPLEX CODE
	/**
	 * Returns the number of entries that should be exported.
	 *
	 * @param tableControl The VTableControl being exported
	 * @param tableSetting The Setting of the table being exported
	 * @return The number of entries to export
	 */
	protected int getNumberOfExportElements(VTableControl tableControl, Setting tableSetting) {
		return 3;
	}

	private String getPrefixName(final Setting tableSetting, final VTableDomainModelReference tableDomainModelReference,
		EClass dmrRootEClass, int index) throws NoLabelFoundException {
		String prefixName = (String) emfformsLabelProvider.getDisplayName(
			tableDomainModelReference.getDomainModelReference(), dmrRootEClass)
			.getValue();
		if (prefixName == null || prefixName.length() == 0) {
			prefixName = tableSetting.getEStructuralFeature()
				.getName();
		}
		return index + 1 + "_" + prefixName; //$NON-NLS-1$
	}

	private EObject getTableEntry(EList<EObject> tableEntries, int currentColumn, EReference tableEntryReference) {
		EObject tableEntry;
		if (tableEntries.size() > currentColumn) {
			tableEntry = tableEntries.get(currentColumn);
		} else {
			tableEntry = EcoreUtil.create(tableEntryReference
				.getEReferenceType());
		}
		return tableEntry;
	}

	private VView getView(VTableControl tableControl, EObject domainObject, ViewModelContext viewModelContext)
		throws DatabindingFailedException {
		VView detailView = tableControl.getDetailView();
		if (detailView == null && tableControl.getDetailEditing() != DetailEditing.WITH_DIALOG) {
			final VElement viewModel = viewModelContext.getViewModel();
			final VViewModelProperties properties = ViewModelPropertiesHelper.getInhertitedPropertiesOrEmpty(viewModel);
			detailView = ViewProviderHelper.getView(domainObject, properties);
		}
		return detailView;
	}
}
