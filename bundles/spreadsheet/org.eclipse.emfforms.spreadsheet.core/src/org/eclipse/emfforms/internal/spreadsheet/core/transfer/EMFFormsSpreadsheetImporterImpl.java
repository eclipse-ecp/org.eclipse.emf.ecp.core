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
package org.eclipse.emfforms.internal.spreadsheet.core.transfer;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter.ReadableInputStream;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.spi.view.migrator.string.StringViewModelMigrator;
import org.eclipse.emf.ecp.spi.view.migrator.string.StringViewModelMigratorUtil;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrationException;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigratorUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.internal.spreadsheet.core.transfer.EMFFormsSpreadsheetImporterImpl.MigrationInformation.State;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsIdProvider;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsConverterException;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterRegistry;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult;
import org.eclipse.emfforms.spi.spreadsheet.core.transfer.EMFFormsSpreadsheetImporter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Implementation of the {@link EMFFormsSpreadsheetImporter}.
 *
 * @author Eugen Neufeld
 */
public class EMFFormsSpreadsheetImporterImpl implements EMFFormsSpreadsheetImporter {

	private static final String IGNORE_SHEET = "Ignore Sheet"; //$NON-NLS-1$
	private EMFFormsDomainExpander domainExpander;

	@Override
	public SpreadsheetImportResult importSpreadsheet(Workbook workbook, EClass eClass) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(EMFFormsSpreadsheetImporterImpl.class)
			.getBundleContext();
		final ServiceReference<EMFFormsDomainExpander> serviceReference = bundleContext
			.getServiceReference(EMFFormsDomainExpander.class);
		domainExpander = bundleContext.getService(serviceReference);

		final SpreadsheetImportResult result = readData(workbook, eClass);

		domainExpander = null;
		bundleContext.ungetService(serviceReference);

		return result;
	}

	private SpreadsheetImportResult readData(Workbook workbook, EClass eClass) {
		final SpreadsheetImportResult result = ErrorFactory.eINSTANCE.createSpreadsheetImportResult();
		final ResourceSet rs = new ResourceSetImpl();
		final MigrationInformation information = new MigrationInformation();
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$

		final Map<String, Map<Integer, Integer>> mapIdToSheetIdWithRowId = parseIds(workbook, result);
		final Map<String, VDomainModelReference> sheetColumnToDMRMap = new LinkedHashMap<String, VDomainModelReference>();
		final Map<VDomainModelReference, EMFFormsSpreadsheetValueConverter> converter = new LinkedHashMap<VDomainModelReference, EMFFormsSpreadsheetValueConverter>();
		final List<EObject> importedEObjects = new ArrayList<EObject>(mapIdToSheetIdWithRowId.size());
		for (final String eObjectId : mapIdToSheetIdWithRowId.keySet()) {
			final Map<Integer, Integer> sheetIdToRowId = mapIdToSheetIdWithRowId.get(eObjectId);
			final EObject eObject = EcoreUtil.create(eClass);
			resource.getContents().add(eObject);
			for (final Integer sheetId : sheetIdToRowId.keySet()) {
				final Sheet sheet = workbook.getSheetAt(sheetId);
				final Row labelRow = sheet.getRow(0);
				final Row row = sheet.getRow(sheetIdToRowId.get(sheetId));
				extractRowInformation(labelRow, row, eObject, result, sheet.getSheetName(), sheetId,
					sheetColumnToDMRMap, converter, information);
			}
			importedEObjects.add(eObject);
		}
		result.getImportedEObjects().addAll(importedEObjects);
		return result;
	}

	/**
	 * Extracts the information from the row and sets the value on the given root EObject.
	 */
	// BEGIN COMPLEX CODE
	private void extractRowInformation(final Row dmrRow, final Row eObjectRow, final EObject eObject,
		SpreadsheetImportResult errorReports, String sheetname, int sheetId,
		Map<String, VDomainModelReference> sheetColumnToDMRMap,
		Map<VDomainModelReference, EMFFormsSpreadsheetValueConverter> converterMap,
		MigrationInformation information) {
		for (int columnId = 1; columnId < dmrRow.getLastCellNum(); columnId++) {
			final String sheetColId = sheetId + "_" + columnId; //$NON-NLS-1$
			final Cell cell = dmrRow.getCell(columnId);
			if (!sheetColumnToDMRMap.containsKey(sheetColId)) {
				final VDomainModelReference dmr = getDomainModelReference(cell, errorReports, eObject, sheetname,
					columnId, information);
				sheetColumnToDMRMap.put(sheetColId, dmr);
			}
			final VDomainModelReference dmr = sheetColumnToDMRMap.get(sheetColId);
			if (dmr == null) {
				continue;
			}

			/* resolve dmr */
			try {
				resolveDMR(dmr, eObject);
			} catch (final EMFFormsExpandingFailedException ex) {
				errorReports.reportError(
					Severity.ERROR, LocalizationServiceHelper.getString(getClass(), "ImportError_DMRResolvementFailed"), //$NON-NLS-1$
					ErrorFactory.eINSTANCE.createEMFLocation(eObject,
						ErrorFactory.eINSTANCE.createDMRLocation(dmr)),
					ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, 0,
						getStringCellValue(cell, errorReports, sheetname, columnId)));
				continue;
			}

			/* initiate databinding */
			Setting setting;
			try {
				setting = getSetting(dmr, eObject);
			} catch (final DatabindingFailedException ex) {
				errorReports.reportError(
					Severity.ERROR,
					LocalizationServiceHelper.getString(getClass(),
						MessageFormat.format("ImportError_DatabindingFailed", ex.getMessage())), //$NON-NLS-1$
					ErrorFactory.eINSTANCE.createEMFLocation(eObject,
						ErrorFactory.eINSTANCE.createDMRLocation(dmr)),
					ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, 0,
						getStringCellValue(cell, errorReports, sheetname, columnId)));
				continue;
			}

			/* access value converter */
			if (!converterMap.containsKey(dmr)) {
				try {
					final EMFFormsSpreadsheetValueConverter converter = getValueConverter(dmr, eObject);
					converterMap.put(dmr, converter);
				} catch (final EMFFormsConverterException ex) {
					errorReports.reportError(
						Severity.ERROR, LocalizationServiceHelper.getString(getClass(), "ImportError_NoValueConverter"), //$NON-NLS-1$
						ErrorFactory.eINSTANCE.createEMFLocation(eObject,
							ErrorFactory.eINSTANCE.createDMRLocation(dmr)),
						ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, 0,
							getStringCellValue(cell, errorReports, sheetname, columnId)));
					continue;
				}
			}
			final EMFFormsSpreadsheetValueConverter converter = converterMap.get(dmr);

			final EStructuralFeature feature = setting.getEStructuralFeature();

			/* access cell with value */
			Cell rowCell;
			if (feature.isUnsettable()) {
				rowCell = eObjectRow.getCell(columnId, Row.RETURN_NULL_AND_BLANK);
			} else {
				rowCell = eObjectRow.getCell(columnId, Row.CREATE_NULL_AS_BLANK);
			}

			if (rowCell == null) {
				/* no error -> unsettable feature */
				errorReports.getSettingToSheetMap()
					.add(ErrorFactory.eINSTANCE.createSettingToSheetMapping(
						createSettingLocation(setting),
						ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, eObjectRow.getRowNum(),
							getStringCellValue(cell, errorReports, sheetname, columnId))));
				continue;
			}

			/* convert value */
			Object convertedValue;
			try {
				convertedValue = converter.getCellValue(rowCell, feature);
			} catch (final EMFFormsConverterException ex) {
				errorReports.reportError(
					Severity.ERROR,
					MessageFormat.format(
						LocalizationServiceHelper.getString(getClass(), "ImportError_ValueConversionFailed"), //$NON-NLS-1$
						ex.getMessage()),
					ErrorFactory.eINSTANCE.createEMFLocation(eObject,
						createSettingLocation(setting),
						ErrorFactory.eINSTANCE.createDMRLocation(dmr)),
					ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, eObjectRow.getRowNum(),
						getStringCellValue(cell, errorReports, sheetname, columnId)));
				continue;
			}

			/* check converted value */
			if (convertedValue != null) {
				if (!checkTypes(feature, convertedValue)) {
					errorReports.reportError(
						Severity.ERROR,
						LocalizationServiceHelper.getString(getClass(), "ImportError_InvalidType"), //$NON-NLS-1$
						ErrorFactory.eINSTANCE.createEMFLocation(eObject,
							createSettingLocation(setting),
							ErrorFactory.eINSTANCE.createDMRLocation(dmr)),
						ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, eObjectRow.getRowNum(),
							getStringCellValue(cell, errorReports, sheetname, columnId)));
					continue;
				}
			}

			if (convertedValue == null && feature.isUnsettable()) {
				setting.unset();
			} else {
				/* set value */
				setting.set(convertedValue);
			}

			errorReports.getSettingToSheetMap()
				.add(ErrorFactory.eINSTANCE.createSettingToSheetMapping(
					createSettingLocation(setting),
					ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, eObjectRow.getRowNum(),
						getStringCellValue(cell, errorReports, sheetname, columnId))));
		}
	}

	private VDomainModelReference getDomainModelReference(Cell cell, SpreadsheetImportResult errorReports,
		EObject eObject, String sheetname, int columnId, MigrationInformation information) {
		/* get dmr comment */
		if (cell == null) {
			errorReports.reportError(
				Severity.ERROR, LocalizationServiceHelper.getString(getClass(), "ImportError_LabelCellDeleted"), //$NON-NLS-1$
				ErrorFactory.eINSTANCE.createEMFLocation(eObject),
				ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, 0, "NO CELL")); //$NON-NLS-1$
			return null;
		}
		final Comment cellComment = cell.getCellComment();
		if (cellComment == null) {
			errorReports.reportError(
				Severity.ERROR, LocalizationServiceHelper.getString(getClass(), "ImportError_CommentDeleted"), //$NON-NLS-1$
				ErrorFactory.eINSTANCE.createEMFLocation(eObject),
				ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, 0,
					getStringCellValue(cell, errorReports, sheetname, columnId)));
			return null;
		}
		final String serializedDMR = cellComment.getString().getString();
		if (serializedDMR == null || serializedDMR.isEmpty()) {
			errorReports.reportError(
				Severity.ERROR, LocalizationServiceHelper.getString(getClass(), "ImportError_CommentEmpty"), //$NON-NLS-1$
				ErrorFactory.eINSTANCE.createEMFLocation(eObject),
				ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, 0,
					getStringCellValue(cell, errorReports, sheetname, columnId)));
			return null;
		}

		/* deserialize dmr */

		try {
			return deserializeDMR(serializedDMR, information);
		} catch (final IOException ex1) {
			errorReports.reportError(
				Severity.ERROR,
				LocalizationServiceHelper.getString(getClass(), "ImportError_DMRDeserializationFailed"), //$NON-NLS-1$
				ErrorFactory.eINSTANCE.createEMFLocation(eObject),
				ErrorFactory.eINSTANCE.createSheetLocation(sheetname, columnId, 0,
					getStringCellValue(cell, errorReports, sheetname, columnId)));
			return null;
		}

	}

	// END COMPLEX CODE

	/**
	 * Checks whether the converted value can be set on the feature.
	 */
	private boolean checkTypes(final EStructuralFeature feature, final Object convertedValue) {
		final Class<?> featureType = feature.getEType().getInstanceClass();

		if (convertedValue == null) {
			return !featureType.isPrimitive();
		}

		final Class<? extends Object> valueType = convertedValue.getClass();

		if (feature.isMany()) {
			if (Collection.class.isInstance(convertedValue)) {
				final Collection<?> collection = Collection.class.cast(convertedValue);
				for (final Object object : collection) {
					if (!checkTypes(feature, object)) {
						return false;
					}
				}
				return true;
			}
			/* else continue with regular checks */
		}

		if (featureType.isPrimitive() && !valueType.isPrimitive()) {
			final Class<?> primitiveClass = getPrimitiveClass(valueType);
			if (primitiveClass == null) {
				return false;
			}
			return featureType.isAssignableFrom(primitiveClass);
		}

		if (!featureType.isPrimitive() && valueType.isPrimitive()) {
			final Class<?> primitiveClass = getPrimitiveClass(featureType);
			if (primitiveClass == null) {
				return false;
			}
			return primitiveClass.isAssignableFrom(valueType);
		}

		return featureType.isAssignableFrom(valueType);
	}

	private Class<?> getPrimitiveClass(Class<?> clazz) {
		try {
			return (Class<?>) clazz.getField("TYPE").get(null); //$NON-NLS-1$
		} catch (final IllegalArgumentException ex) {
		} catch (final IllegalAccessException ex) {
		} catch (final NoSuchFieldException ex) {
		} catch (final SecurityException ex) {
		}
		return null;
	}

	private SettingLocation createSettingLocation(Setting setting) {
		return ErrorFactory.eINSTANCE.createSettingLocation(setting.getEObject(), setting.getEStructuralFeature());
	}

	private void resolveDMR(VDomainModelReference dmr, EObject eObject) throws EMFFormsExpandingFailedException {
		domainExpander.prepareDomainObject(dmr, eObject);
	}

	/**
	 * Returns a Map from EObject-ID to Sheet-ID to Row-ID.
	 */
	private Map<String, Map<Integer, Integer>> parseIds(Workbook workbook, SpreadsheetImportResult errorReports) {
		final Map<String, Map<Integer, Integer>> result = new LinkedHashMap<String, Map<Integer, Integer>>();

		for (int sheetId = 0; sheetId < workbook.getNumberOfSheets(); sheetId++) {
			final Sheet sheet = workbook.getSheetAt(sheetId);
			final Row labelRow = sheet.getRow(0);
			if (labelRow == null) {
				errorReports.reportError(
					Severity.ERROR, MessageFormat.format(
						LocalizationServiceHelper.getString(getClass(), "ImportError_SheetEmpty"), //$NON-NLS-1$
						sheet.getSheetName()),
					ErrorFactory.eINSTANCE.createSheetLocation(workbook.getSheetName(sheetId), 0, 0, "NO CELL")); //$NON-NLS-1$
				continue;
			}
			final Cell idColumnLabelCell = labelRow.getCell(0, Row.CREATE_NULL_AS_BLANK);
			final Comment cellComment = idColumnLabelCell.getCellComment();
			if (cellComment != null && cellComment.getString() != null
				&& IGNORE_SHEET.equals(cellComment.getString().getString())) {
				continue;
			}
			final String idColumnLabel = getStringCellValue(idColumnLabelCell, errorReports,
				workbook.getSheetName(sheetId), 0);
			if (!EMFFormsIdProvider.ID_COLUMN.equals(idColumnLabel)) {
				/* ID Column is missing. We have to ignore this sheet */
				errorReports.reportError(
					Severity.ERROR, MessageFormat.format(
						LocalizationServiceHelper.getString(getClass(), "ImportError_FirstColumnWrong"), //$NON-NLS-1$
						EMFFormsIdProvider.ID_COLUMN, idColumnLabel),
					ErrorFactory.eINSTANCE.createSheetLocation(workbook.getSheetName(sheetId), 0, 0, "NO CELL")); //$NON-NLS-1$
				continue;
			}
			for (int rowId = 3; rowId <= sheet.getLastRowNum(); rowId++) {
				final Row row = sheet.getRow(rowId);
				if (row == null || isRowEmpty(row)) {
					errorReports.reportError(
						Severity.INFO, LocalizationServiceHelper.getString(getClass(), "ImportError_EmptyRow"), //$NON-NLS-1$
						ErrorFactory.eINSTANCE.createSheetLocation(workbook.getSheetName(sheetId), 0, rowId,
							EMFFormsIdProvider.ID_COLUMN));
					continue;
				}
				final String eObjectId = getStringCellValue(row.getCell(0, Row.CREATE_NULL_AS_BLANK), errorReports,
					workbook.getSheetName(sheetId), 0, rowId);
				if (eObjectId == null || eObjectId.isEmpty()) {
					/* EObject id deleted */
					errorReports.reportError(
						Severity.ERROR, LocalizationServiceHelper.getString(getClass(), "ImportError_NoEObjectID"), //$NON-NLS-1$
						ErrorFactory.eINSTANCE.createSheetLocation(workbook.getSheetName(sheetId), 0, rowId,
							EMFFormsIdProvider.ID_COLUMN));
					continue;
				}
				if (!result.containsKey(eObjectId)) {
					result.put(eObjectId, new LinkedHashMap<Integer, Integer>());
				}
				// each sheetid should only be mapped once to each eobjectid
				if (result.get(eObjectId).containsKey(sheetId)) {
					/* duplicate EObject ID */
					errorReports.reportError(
						Severity.ERROR,
						LocalizationServiceHelper.getString(getClass(), "ImportError_DuplicateEObjectID"), //$NON-NLS-1$
						ErrorFactory.eINSTANCE.createSheetLocation(workbook.getSheetName(sheetId), 0, rowId,
							EMFFormsIdProvider.ID_COLUMN));
					continue;
				}
				result.get(eObjectId).put(sheetId, rowId);
			}
		}
		return result;
	}

	private Setting getSetting(VDomainModelReference dmr, EObject eObject)
		throws DatabindingFailedException {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsDatabindingEMF> serviceReference = bundleContext
			.getServiceReference(EMFFormsDatabindingEMF.class);
		final EMFFormsDatabindingEMF emfFormsDatabinding = bundleContext.getService(serviceReference);
		return emfFormsDatabinding.getSetting(dmr, eObject);
	}

	private EMFFormsSpreadsheetValueConverter getValueConverter(VDomainModelReference dmr, EObject eObject)
		throws EMFFormsConverterException {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsSpreadsheetValueConverterRegistry> serviceReference = bundleContext
			.getServiceReference(EMFFormsSpreadsheetValueConverterRegistry.class);
		final EMFFormsSpreadsheetValueConverterRegistry emfFormsDatabinding = bundleContext
			.getService(serviceReference);
		return emfFormsDatabinding.getConverter(eObject, dmr);
	}

	private VDomainModelReference deserializeDMR(String serializedDMR, MigrationInformation information)
		throws IOException {
		if (ViewModelMigratorUtil.getStringViewModelMigrator() != null) {
			try {
				serializedDMR = migrateIfNeeded(serializedDMR, information);
			} catch (final ViewModelMigrationException ex) {
				throw new IOException(ex);
			}
		}
		final ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$

		final ReadableInputStream is = new ReadableInputStream(serializedDMR, "UTF-8"); //$NON-NLS-1$
		resource.load(is, null);
		return (VDomainModelReference) resource.getContents().get(0);
	}

	private String migrateIfNeeded(String serializedDMR, MigrationInformation information)
		throws ViewModelMigrationException {
		final List<String> namespaceURIs = StringViewModelMigratorUtil.getNamespaceURIs(serializedDMR);

		final MigrationInformation.State migrationState = information.isMigrationNeeded(namespaceURIs);

		if (migrationState == State.ok) {
			return serializedDMR;
		}

		final StringViewModelMigrator migrator = ViewModelMigratorUtil.getStringViewModelMigrator();

		if (migrationState == State.unknown) {
			if (migrator.checkMigration(serializedDMR)) {
				information.noMigrationNeeded(namespaceURIs);
				return serializedDMR;
			}
			information.migrationNeeded(namespaceURIs);
		}

		return migrator.performMigration(serializedDMR);
	}

	private String getStringCellValue(Cell cell, SpreadsheetImportResult errorReports, String sheetName, int columnId) {
		return getStringCellValue(cell, errorReports, sheetName, columnId, 0);
	}

	private String getStringCellValue(Cell cell, SpreadsheetImportResult errorReports, String sheetName, int columnId,
		int rowId) {
		try {
			return cell.getStringCellValue();
		} catch (final IllegalStateException ex) {
			errorReports.reportError(
				Severity.ERROR, ex.getMessage(),
				ErrorFactory.eINSTANCE.createEMFLocation(),
				ErrorFactory.eINSTANCE.createSheetLocation(sheetName, columnId, rowId, "")); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * Helper class which caches the required information of the migration state for the used NS-URIs.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	static class MigrationInformation {
		private final Set<Set<String>> migrationNeeded = new LinkedHashSet<Set<String>>();
		private final Set<Set<String>> noMigrationNeeded = new LinkedHashSet<Set<String>>();

		/**
		 * Whether a migration is needed for the given uris.
		 *
		 * @param namespaceURIs the uris
		 * @return the state
		 */
		State isMigrationNeeded(List<String> namespaceURIs) {
			final Set<String> set = new LinkedHashSet<String>(namespaceURIs);
			if (noMigrationNeeded.contains(set)) {
				return State.ok;
			}
			if (migrationNeeded.contains(set)) {
				return State.migrate;
			}
			return State.unknown;
		}

		/**
		 * Updates the cached migration information.
		 *
		 * @param namespaceURIs the given set of nsuris for which a migration is required
		 */
		void migrationNeeded(List<String> namespaceURIs) {
			final Set<String> set = Collections.unmodifiableSet(new LinkedHashSet<String>(namespaceURIs));
			migrationNeeded.add(set);
		}

		/**
		 * Updates the cached migration information.
		 *
		 * @param namespaceURIs the given set of nsuris for which no migration is required
		 */
		void noMigrationNeeded(List<String> namespaceURIs) {
			final Set<String> set = Collections.unmodifiableSet(new LinkedHashSet<String>(namespaceURIs));
			noMigrationNeeded.add(set);
		}

		/**
		 * Migration state.
		 *
		 * @author Johannes Faltermeier
		 *
		 */
		enum State {
			/**
			 * Based on the cached information a migration is needed.
			 */
			migrate,

			/**
			 * Based on the cached information no migration is needed.
			 */
			ok,

			/**
			 * No cached information about the migration state.
			 */
			unknown
		}

	}

	private static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			final Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
				return false;
			}
		}
		return true;
	}
}
