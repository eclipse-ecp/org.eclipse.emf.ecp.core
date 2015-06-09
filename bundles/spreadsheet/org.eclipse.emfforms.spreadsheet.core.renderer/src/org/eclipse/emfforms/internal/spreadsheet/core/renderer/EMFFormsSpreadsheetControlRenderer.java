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
package org.eclipse.emfforms.internal.spreadsheet.core.renderer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsExportTableParent;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;

/**
 * Spreadsheet renderer for {@link VControl}.
 *
 * @author Eugen Neufeld
 */
public class EMFFormsSpreadsheetControlRenderer extends EMFFormsAbstractSpreadsheetRenderer<VControl> {

	private final EMFFormsDatabinding emfformsDatabinding;
	private final EMFFormsLabelProvider emfformsLabelProvider;
	private final ReportService reportService;
	private final VTViewTemplateProvider vtViewTemplateProvider;

	/**
	 * Default constructor.
	 *
	 * @param emfformsDatabinding The EMFFormsDatabinding to use
	 * @param emfformsLabelProvider The EMFFormsLabelProvider to use
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	public EMFFormsSpreadsheetControlRenderer(EMFFormsDatabinding emfformsDatabinding,
		EMFFormsLabelProvider emfformsLabelProvider, ReportService reportService,
		VTViewTemplateProvider vtViewTemplateProvider) {
		this.emfformsDatabinding = emfformsDatabinding;
		this.emfformsLabelProvider = emfformsLabelProvider;
		this.reportService = reportService;
		this.vtViewTemplateProvider = vtViewTemplateProvider;
	}

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

		Row valueRow = sheet.getRow(renderTarget.getRow() + 3);
		if (valueRow == null) {
			valueRow = sheet.createRow(renderTarget.getRow() + 3);
		}
		final Cell labelCell = labelRow.getCell(renderTarget.getColumn(),
			Row.CREATE_NULL_AS_BLANK);
		final Cell descriptionCell = descriptionRow.getCell(renderTarget.getColumn(),
			Row.CREATE_NULL_AS_BLANK);
		final Cell formatCell = formatRow.getCell(renderTarget.getColumn(),
			Row.CREATE_NULL_AS_BLANK);

		final Cell valueCell = valueRow.getCell(renderTarget.getColumn(),
			Row.CREATE_NULL_AS_BLANK);

		try {
			final EMFFormsExportTableParent exportTableParent = (EMFFormsExportTableParent) viewModelContext
				.getContextValue(EMFFormsExportTableParent.EXPORT_TABLE_PARENT);
			VDomainModelReference dmrToResolve = EcoreUtil.copy(vElement.getDomainModelReference());
			if (exportTableParent != null) {
				final VIndexDomainModelReference indexDMR = exportTableParent.getIndexDMRToExtend();
				indexDMR.setTargetDMR(dmrToResolve);

				dmrToResolve = exportTableParent.getIndexDMRToResolve();
			}

			final IObservableValue displayName = emfformsLabelProvider
				.getDisplayName(dmrToResolve, viewModelContext.getDomainModel());
			String labelValue = displayName.getValue().toString();
			if (exportTableParent != null) {
				labelValue = exportTableParent.getLabelPrefix() + "_" + labelValue; //$NON-NLS-1$
			}

			String extra = ""; //$NON-NLS-1$
			final VTMandatoryStyleProperty mandatoryStyle = getMandatoryStyle(vElement, viewModelContext);
			final EStructuralFeature structuralFeature = (EStructuralFeature) emfformsDatabinding.getValueProperty(
				dmrToResolve, viewModelContext.getDomainModel()).getValueType();
			if (mandatoryStyle.isHighliteMandatoryFields() && structuralFeature.getLowerBound() > 0) {
				extra = mandatoryStyle.getMandatoryMarker();
			}
			labelValue = labelValue + extra;

			labelCell.setCellValue(labelValue);
			displayName.dispose();

			final IObservableValue description = emfformsLabelProvider
				.getDescription(dmrToResolve, viewModelContext.getDomainModel());
			descriptionCell.setCellValue(description.getValue().toString());
			description.dispose();

			final String format = getFormatDescription(structuralFeature);
			formatCell.setCellValue(format);

			final IObservableValue observableValue = emfformsDatabinding
				.getObservableValue(dmrToResolve,
					viewModelContext.getDomainModel());
			valueCell.setCellValue(getValueString(observableValue.getValue(), observableValue.getValueType()));
			observableValue.dispose();

			final Comment comment = createComment(workbook, sheet, dmrToResolve,
				renderTarget.getRow(), renderTarget.getColumn());
			labelCell.setCellComment(comment);

			return 1;
		} catch (final DatabindingFailedException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		} catch (final NoLabelFoundException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		} catch (final IOException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}

		return 0;
	}

	private String getFormatDescription(final EStructuralFeature structuralFeature) {
		final StringBuilder sb = new StringBuilder();
		for (final EAnnotation eAnnotation : structuralFeature.getEAnnotations()) {
			final EMap<String, String> details = eAnnotation.getDetails();
			for (final String key : details.keySet()) {
				if ("http:///org/eclipse/emf/ecore/util/ExtendedMetaData".equals(eAnnotation.getSource())) { //$NON-NLS-1$
					if ("kind".equals(key)) { //$NON-NLS-1$
						continue;
					}
					if ("name".equals(key)) { //$NON-NLS-1$
						continue;
					}
					if ("baseType".equals(key)) { //$NON-NLS-1$
						continue;
					}
				}
				sb.append(key);
				sb.append("="); //$NON-NLS-1$
				sb.append(details.get(key));
				sb.append("\t"); //$NON-NLS-1$
			}
		}
		return sb.toString().trim();
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

	private String getValueString(Object fromObject, Object valueType) {
		if (EAttribute.class.isInstance(valueType)) {
			final EAttribute eAttribute = (EAttribute) valueType;
			final EDataType eDataType = eAttribute.getEAttributeType();
			final EFactory eFactory = eDataType.getEPackage().getEFactoryInstance();

			if (eAttribute.isMany())
			{
				final StringBuilder result = new StringBuilder();
				for (final Object value : (List<?>) fromObject)
				{
					if (result.length() == 0)
					{
						result.append(' ');
					}
					result.append(eFactory.convertToString(eDataType, value));
				}
				return result.toString();
			}
			return eFactory.convertToString(eDataType, fromObject);
		}
		if (fromObject == null) {
			return ""; //$NON-NLS-1$
		}
		return fromObject.toString();
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
