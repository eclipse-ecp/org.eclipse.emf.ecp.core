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
 * Johannes Faltermeier - template pattern for extenders
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core.renderer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter.WriteableOutputStream;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
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
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetFormatDescriptionProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsCellStyleConstants;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsConverterException;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterRegistry;

/**
 * Spreadsheet renderer for {@link VControl}.
 *
 * @author Eugen Neufeld
 * @author Johannes Faltermeier
 */
public class EMFFormsSpreadsheetControlRenderer extends EMFFormsAbstractSpreadsheetRenderer<VControl> {

	private final EMFFormsDatabindingEMF emfformsDatabinding;
	private final EMFFormsLabelProvider emfformsLabelProvider;
	private final ReportService reportService;
	private final VTViewTemplateProvider vtViewTemplateProvider;
	private final EMFFormsIdProvider idProvider;
	private final EMFFormsSpreadsheetValueConverterRegistry converterRegistry;
	private final EMFFormsSpreadsheetFormatDescriptionProvider formatDescriptionProvider;
	private final EMFFormsDomainExpander domainExpander;

	/**
	 * Default constructor.
	 *
	 * @param emfformsDatabinding The EMFFormsDatabinding to use
	 * @param emfformsLabelProvider The EMFFormsLabelProvider to use
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param idProvider The {@link EMFFormsIdProvider}
	 * @param converterRegistry The {@link EMFFormsSpreadsheetValueConverterRegistry}
	 * @param formatDescriptionProvider The {@link EMFFormsSpreadsheetFormatDescriptionProvider}
	 * @param domainExpander The {@link EMFFormsDomainExpander}
	 */
	// BEGIN COMPLEX CODE
	public EMFFormsSpreadsheetControlRenderer(
		EMFFormsDatabindingEMF emfformsDatabinding,
		EMFFormsLabelProvider emfformsLabelProvider,
		ReportService reportService,
		VTViewTemplateProvider vtViewTemplateProvider,
		EMFFormsIdProvider idProvider,
		EMFFormsSpreadsheetValueConverterRegistry converterRegistry,
		EMFFormsSpreadsheetFormatDescriptionProvider formatDescriptionProvider,
		EMFFormsDomainExpander domainExpander) {
		this.emfformsDatabinding = emfformsDatabinding;
		this.emfformsLabelProvider = emfformsLabelProvider;
		this.reportService = reportService;
		this.vtViewTemplateProvider = vtViewTemplateProvider;
		this.idProvider = idProvider;
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
	@Override
	public int render(Workbook workbook, VControl vElement,
		ViewModelContext viewModelContext, EMFFormsSpreadsheetRenderTarget renderTarget) {
		Sheet sheet = workbook.getSheet(renderTarget.getSheetName());
		if (sheet == null) {
			sheet = workbook.createSheet(renderTarget.getSheetName());
			setupSheetFormat(sheet);
		}
		Row labelRow = sheet.getRow(0);
		if (labelRow == null) {
			labelRow = sheet.createRow(0);
		}
		Row descriptionRow = sheet.getRow(1);
		if (descriptionRow == null) {
			descriptionRow = sheet.createRow(1);
		}
		Row formatRow = sheet.getRow(2);
		if (formatRow == null) {
			formatRow = sheet.createRow(2);
		}

		final CellStyle readOnly = (CellStyle) viewModelContext.getContextValue(EMFFormsCellStyleConstants.LOCKED);
		final CellStyle readOnlyWrap = (CellStyle) viewModelContext
			.getContextValue(EMFFormsCellStyleConstants.LOCKED_AND_WRAPPED);

		final Cell idCell = labelRow.getCell(0, Row.CREATE_NULL_AS_BLANK);
		idCell.setCellValue(EMFFormsIdProvider.ID_COLUMN);
		idCell.setCellStyle(readOnly);

		final Cell labelCell = labelRow.getCell(renderTarget.getColumn() + 1,
			Row.CREATE_NULL_AS_BLANK);
		labelCell.setCellStyle(readOnlyWrap);

		final Cell descriptionCell = descriptionRow.getCell(renderTarget.getColumn() + 1,
			Row.CREATE_NULL_AS_BLANK);
		descriptionCell.setCellStyle(readOnlyWrap);

		final Cell formatCell = formatRow.getCell(renderTarget.getColumn() + 1,
			Row.CREATE_NULL_AS_BLANK);
		formatCell.setCellStyle(readOnlyWrap);

		try {
			final EMFFormsExportTableParent exportTableParent = (EMFFormsExportTableParent) viewModelContext
				.getContextValue(EMFFormsExportTableParent.EXPORT_TABLE_PARENT);
			VDomainModelReference dmrToResolve = EcoreUtil.copy(vElement.getDomainModelReference());
			if (exportTableParent != null) {
				final VIndexDomainModelReference indexDMR = exportTableParent.getIndexDMRToExtend();
				indexDMR.setTargetDMR(dmrToResolve);

				dmrToResolve = exportTableParent.getIndexDMRToResolve();
			}

			if (labelCell.getCellComment() == null) {
				final EStructuralFeature structuralFeature = emfformsDatabinding.getValueProperty(
					dmrToResolve, viewModelContext.getDomainModel()).getStructuralFeature();

				writeLabel(vElement, viewModelContext, labelCell, exportTableParent, dmrToResolve, structuralFeature);

				final Comment comment = createComment(workbook, sheet, dmrToResolve,
					renderTarget.getRow(), renderTarget.getColumn() + 1);
				labelCell.setCellComment(comment);

				writeDescription(viewModelContext, descriptionCell, dmrToResolve);

				writeFormatInformation(formatCell, structuralFeature);
			}
			if (viewModelContext.getDomainModel() != null) {
				writeValue(viewModelContext, renderTarget, sheet, dmrToResolve);
			}

			return 1;
		} catch (final DatabindingFailedException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		} catch (final NoLabelFoundException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		} catch (final IOException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		} catch (final EMFFormsConverterException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}

		return 0;
	}

	private void writeValue(ViewModelContext viewModelContext, EMFFormsSpreadsheetRenderTarget renderTarget,
		Sheet sheet, VDomainModelReference dmrToResolve) throws DatabindingFailedException, EMFFormsConverterException {
		Row valueRow = sheet.getRow(renderTarget.getRow() + 3);
		if (valueRow == null) {
			valueRow = sheet.createRow(renderTarget.getRow() + 3);
		}
		valueRow.getCell(0, Row.CREATE_NULL_AS_BLANK)
			.setCellValue(idProvider.getId(viewModelContext.getDomainModel()));

		try {
			expandDMR(dmrToResolve, viewModelContext.getDomainModel());
		} catch (final EMFFormsExpandingFailedException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
			return;
		}
		final Setting setting = emfformsDatabinding.getSetting(dmrToResolve, viewModelContext.getDomainModel());

		/* only create new cells for non-unsettable features and unsettable feature which are set */
		/*
		 * if the eObject is null, this means that the dmr could not be resolved correctly. in this case we want
		 * to create an empty cell
		 */
		if (setting.getEObject() == null || !setting.getEStructuralFeature().isUnsettable()
			|| setting.getEStructuralFeature().isUnsettable() && setting.isSet()) {
			final Object value = setting.get(true);
			final EMFFormsSpreadsheetValueConverter converter = converterRegistry
				.getConverter(viewModelContext.getDomainModel(), dmrToResolve);

			final Cell valueCell = valueRow.getCell(renderTarget.getColumn() + 1,
				Row.CREATE_NULL_AS_BLANK);
			converter.setCellValue(valueCell, value, setting.getEStructuralFeature(), viewModelContext);

		}
	}

	private void expandDMR(VDomainModelReference dmrToResolve, EObject domainModel)
		throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(dmrToResolve, domainModel);
	}

	private void writeLabel(VControl vControl, ViewModelContext viewModelContext, final Cell labelCell,
		final EMFFormsExportTableParent exportTableParent, VDomainModelReference dmrToResolve,
		final EStructuralFeature structuralFeature) throws NoLabelFoundException {
		IObservableValue displayName;
		if (viewModelContext.getDomainModel() != null) {
			displayName = emfformsLabelProvider.getDisplayName(dmrToResolve, viewModelContext.getDomainModel());
		} else {
			displayName = emfformsLabelProvider.getDisplayName(dmrToResolve);
		}
		String labelValue = displayName.getValue().toString();
		if (exportTableParent != null) {
			labelValue = exportTableParent.getLabelPrefix() + "_" + labelValue; //$NON-NLS-1$
		}

		String extra = ""; //$NON-NLS-1$
		final VTMandatoryStyleProperty mandatoryStyle = getMandatoryStyle(vControl, viewModelContext);
		if (mandatoryStyle.isHighliteMandatoryFields() && structuralFeature.getLowerBound() > 0) {
			extra = mandatoryStyle.getMandatoryMarker();
		}
		labelValue = labelValue + extra;

		labelCell.setCellValue(labelValue);
		displayName.dispose();
	}

	private void writeDescription(ViewModelContext viewModelContext, final Cell descriptionCell,
		VDomainModelReference dmrToResolve) throws NoLabelFoundException {
		IObservableValue description;
		if (viewModelContext.getDomainModel() != null) {
			description = emfformsLabelProvider.getDescription(dmrToResolve, viewModelContext.getDomainModel());
		} else {
			description = emfformsLabelProvider.getDescription(dmrToResolve);
		}
		descriptionCell.setCellValue(description.getValue().toString());
		description.dispose();
	}

	private void writeFormatInformation(final Cell formatCell, final EStructuralFeature structuralFeature) {
		String format = ""; //$NON-NLS-1$
		if (formatDescriptionProvider != null) {
			format = formatDescriptionProvider.getFormatDescription(structuralFeature);
		}
		formatCell.setCellValue(format);
	}

	private static void setupSheetFormat(final Sheet sheet) {
		sheet.setDefaultColumnWidth(30);
		// do not scroll the first column (id) and the three top rows (label+info)
		sheet.createFreezePane(1, 3);
	}

	private VTMandatoryStyleProperty getMandatoryStyle(VElement vElement, ViewModelContext viewModelContext) {
		if (vtViewTemplateProvider == null) {
			return getDefaultStyle();
		}
		final Set<VTStyleProperty> styleProperties = vtViewTemplateProvider
			.getStyleProperties(vElement, viewModelContext);
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTMandatoryStyleProperty.class.isInstance(styleProperty)) {
				return (VTMandatoryStyleProperty) styleProperty;
			}
		}
		return getDefaultStyle();
	}

	private VTMandatoryStyleProperty getDefaultStyle() {
		return VTMandatoryFactory.eINSTANCE.createMandatoryStyleProperty();
	}

	private Comment createComment(Workbook workbook, Sheet sheet, VDomainModelReference domainModelReference, int row,
		int column) throws IOException {
		final CreationHelper factory = workbook.getCreationHelper();

		// When the comment box is visible, have it show in a 1x3 space
		final ClientAnchor anchor = factory.createClientAnchor();
		anchor.setCol1(column);
		anchor.setCol2(column + 1);
		anchor.setRow1(row);
		anchor.setRow2(row + 1);

		final Drawing drawing = sheet.createDrawingPatriarch();
		final Comment comment = drawing.createCellComment(anchor);

		comment.setAuthor("EMFForms Spreadsheet Renderer"); //$NON-NLS-1$
		comment.setVisible(false);
		comment.setString(factory.createRichTextString(getSerializedDMR(domainModelReference)));
		return comment;
	}

	private String getSerializedDMR(VDomainModelReference domainModelReference) throws IOException {
		final ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$
		resource.getContents().add(EcoreUtil.copy(domainModelReference));
		final StringWriter sw = new StringWriter();
		final WriteableOutputStream os = new WriteableOutputStream(sw, "UTF-8"); //$NON-NLS-1$
		resource.save(os, null);
		final String value = sw.getBuffer().toString();
		return value;
	}

}
