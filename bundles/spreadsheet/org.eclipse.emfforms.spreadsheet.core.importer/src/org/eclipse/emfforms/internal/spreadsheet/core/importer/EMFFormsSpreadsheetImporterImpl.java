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
package org.eclipse.emfforms.internal.spreadsheet.core.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter.ReadableInputStream;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsIdProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
import org.eclipse.emfforms.spi.spreadsheet.core.importer.EMFFormsSpreadsheetImporter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Implementation of the {@link EMFFormsSpreadsheetImporter}.
 *
 * @author Eugen Neufeld
 */
public class EMFFormsSpreadsheetImporterImpl implements EMFFormsSpreadsheetImporter {

	private final ReportService reportService;

	/**
	 * Default Constructor.
	 */
	public EMFFormsSpreadsheetImporterImpl() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ReportService> reportServiceReference = bundleContext
			.getServiceReference(ReportService.class);
		reportService = bundleContext.getService(reportServiceReference);
	}

	@Override
	public Collection<EObject> importSpreadsheet(String filePath, EClass eClass) {
		Workbook workbook;
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(filePath));
			workbook = new HSSFWorkbook(file);
			return readData(workbook, eClass);
		} catch (final IOException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		} finally {
			try {
				file.close();
			} catch (final IOException ex) {
				reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
			}
		}
		return null;
	}

	private Collection<EObject> readData(Workbook workbook, EClass eClass) {
		final ResourceSet rs = new ResourceSetImpl();
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$

		final List<EObject> result = new ArrayList<EObject>();

		final Map<String, Map<Integer, Integer>> mapIdToSheetIdWithRowId = parseIds(workbook);
		if (mapIdToSheetIdWithRowId == null) {
			for (int sheetId = 0; sheetId < workbook.getNumberOfSheets(); sheetId++) {
				final Sheet sheet = workbook.getSheetAt(sheetId);
				final Row labelRow = sheet.getRow(0);
				for (int rowId = 3; rowId <= sheet.getLastRowNum(); rowId++) {
					final Row row = sheet.getRow(rowId);
					final EObject eObject = EcoreUtil.create(eClass);
					resource.getContents().add(eObject);
					extractRowInformation(labelRow, row, eObject);
					result.add(eObject);

				}
			}
		}
		else {
			for (final String eObjectId : mapIdToSheetIdWithRowId.keySet()) {
				final Map<Integer, Integer> sheetIdToRowId = mapIdToSheetIdWithRowId.get(eObjectId);
				final EObject eObject = EcoreUtil.create(eClass);
				resource.getContents().add(eObject);
				for (final Integer sheetId : sheetIdToRowId.keySet()) {
					final Sheet sheet = workbook.getSheetAt(sheetId);
					final Row labelRow = sheet.getRow(0);
					final Row row = sheet.getRow(sheetIdToRowId.get(sheetId));
					extractRowInformation(labelRow, row, eObject);
				}
				result.add(eObject);
			}
		}
		return result;
	}

	private void extractRowInformation(final Row labelRow, final Row row, final EObject eObject) {
		for (int columnId = 0; columnId < row.getPhysicalNumberOfCells(); columnId++) {
			final Cell cell = labelRow.getCell(columnId);
			if (cell == null) {
				continue;
			}
			final Comment cellComment = cell.getCellComment();
			if (cellComment == null) {
				continue;
			}
			final String serializedDMR = cellComment.getString().getString();
			final String value = row.getCell(columnId).getStringCellValue();
			final VDomainModelReference dmr = deserializeDMR(serializedDMR);
			try {
				final IObservableValue observableValue = getObservableValue(dmr, eObject);
				resolveDMR(dmr, eObject);
				observableValue.setValue(getValue(value, observableValue.getValueType()));
			} catch (final DatabindingFailedException ex) {
				reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
			}
		}
	}

	private void resolveDMR(VDomainModelReference dmr, EObject eObject) {
		dmr.init(eObject);
	}

	private Map<String, Map<Integer, Integer>> parseIds(Workbook workbook) {
		final Map<String, Map<Integer, Integer>> result = new LinkedHashMap<String, Map<Integer, Integer>>();

		for (int sheetId = 0; sheetId < workbook.getNumberOfSheets(); sheetId++) {
			final Sheet sheet = workbook.getSheetAt(sheetId);
			final Row labelRow = sheet.getRow(0);
			if (!EMFFormsIdProvider.ID_COLUMN.equals(labelRow.getCell(0).getStringCellValue())) {
				return null;
			}
			for (int rowId = 3; rowId <= sheet.getLastRowNum(); rowId++) {
				final Row row = sheet.getRow(rowId);
				final String eObjectId = row.getCell(0).getStringCellValue();
				if (!result.containsKey(eObjectId)) {
					result.put(eObjectId, new LinkedHashMap<Integer, Integer>());
				}
				result.get(eObjectId).put(sheetId, rowId);
			}
		}
		return result;
	}

	private Object getValue(String value, Object valueType) {
		if (value == null || value.length() == 0) {
			return null;
		}
		if (EAttribute.class.isInstance(valueType)) {
			final EAttribute eAttribute = (EAttribute) valueType;
			final EDataType eDataType = eAttribute.getEAttributeType();
			final EFactory eFactory = eDataType.getEPackage().getEFactoryInstance();
			if (eAttribute.isMany())
			{
				final List<Object> result = new ArrayList<Object>();

				for (final String element : value.split(" ")) //$NON-NLS-1$
				{
					result.add(eFactory.createFromString(eDataType, element));
				}

				return result;
			}
			return eFactory.createFromString(eDataType, value);
		}
		else if (EReference.class.isInstance(valueType)) {
			if (EReference.class.cast(valueType).isMany()) {
				return ECollections.EMPTY_ELIST;
			}
			return null;
		}

		return value;
	}

	private IObservableValue getObservableValue(VDomainModelReference dmr, EObject eObject)
		throws DatabindingFailedException {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsDatabinding> serviceReference = bundleContext
			.getServiceReference(EMFFormsDatabinding.class);
		final EMFFormsDatabinding emfFormsDatabinding = bundleContext.getService(serviceReference);
		return emfFormsDatabinding.getObservableValue(dmr, eObject);
	}

	private VDomainModelReference deserializeDMR(String serializedDMR) {
		final ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$

		final ReadableInputStream is = new ReadableInputStream(serializedDMR, "UTF-8"); //$NON-NLS-1$
		try {
			resource.load(is, null);
		} catch (final IOException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}
		return (VDomainModelReference) resource.getContents().get(0);
	}
}
