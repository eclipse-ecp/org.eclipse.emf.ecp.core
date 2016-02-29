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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emfforms.internal.spreadsheet.core.EMFFormsSpreadsheetViewModelContext;
import org.eclipse.emfforms.internal.spreadsheet.core.converter.NumberFormatHelper;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsCellStyleConstants;
import org.eclipse.emfforms.spi.spreadsheet.core.transfer.EMFFormsSpreadsheetExporter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Implementation of the {@link EMFFormsSpreadsheetExporter}.
 *
 * @author Eugen Neufeld
 */
public class EMFFormsSpreadsheetExporterImpl implements EMFFormsSpreadsheetExporter {

	private final ReportService reportService;
	private final ViewProvider viewProvider;

	/**
	 * Default Constructor.
	 */
	public EMFFormsSpreadsheetExporterImpl() {
		this(new ViewProvider() {
			@Override
			public VView getViewModel(EObject viewEobject, VViewModelProperties properties) {
				return ViewProviderHelper.getView(viewEobject, properties);
			}
		});
	}

	/**
	 * Constructor which offers a way to influence the view model retrieval.
	 *
	 * @param viewProvider the provider which will be used to find the view model that will be rendered
	 */
	public EMFFormsSpreadsheetExporterImpl(ViewProvider viewProvider) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ReportService> reportServiceReference = bundleContext
			.getServiceReference(ReportService.class);
		reportService = bundleContext.getService(reportServiceReference);
		this.viewProvider = viewProvider;

	}

	@Override
	public Workbook render(final Collection<? extends EObject> domainObjects, EObject viewEobject,
		VViewModelProperties properties) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsSpreadsheetRendererFactory> serviceReference = bundleContext
			.getServiceReference(EMFFormsSpreadsheetRendererFactory.class);
		final EMFFormsSpreadsheetRendererFactory emfFormsSpreadsheetRendererFactory = bundleContext
			.getService(serviceReference);

		final Workbook workbook = new HSSFWorkbook();

		final VView viewModel = viewProvider.getViewModel(viewEobject, properties);
		if (viewModel == null) {
			reportService.report(
				new EMFFormsSpreadsheetReport("No view model could be found for the given EObject/ViewModelProperties", //$NON-NLS-1$
					EMFFormsSpreadsheetReport.ERROR));
			return workbook;
		}

		// analyze and retrieve
		final Set<String> retrievedFormats = retrieveAllFormats(viewModel.getRootEClass());
		final Map<String, CellStyle> keyStyleMap = new LinkedHashMap<String, CellStyle>(retrievedFormats.size());
		addDefaultCellStyles(workbook, keyStyleMap);
		prepareNumberCellStyles(workbook, retrievedFormats, keyStyleMap);
		if (domainObjects == null) {
			try {
				final ViewModelContext viewModelContext = new EMFFormsSpreadsheetViewModelContext(viewModel, null);
				prepareViewContext(keyStyleMap, viewModelContext);

				final EMFFormsAbstractSpreadsheetRenderer<VElement> renderer = emfFormsSpreadsheetRendererFactory
					.getRendererInstance(
						viewModelContext.getViewModel(), viewModelContext);
				renderer.render(workbook, viewModelContext.getViewModel(), viewModelContext,
					new EMFFormsSpreadsheetRenderTarget("root", 0, 0)); //$NON-NLS-1$
				viewModelContext.dispose();
			} catch (final EMFFormsNoRendererException ex) {
				reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
			}
		} else {
			int i = 0;
			for (final EObject domainObject : domainObjects) {
				if (!viewModel.getRootEClass().isInstance(domainObject)) {
					reportService
						.report(new EMFFormsSpreadsheetReport(
							String
								.format(
									"The provided view %1$s doesn't fit for the passed EObject %2$s", viewModel, //$NON-NLS-1$
									domainObject),
							EMFFormsSpreadsheetReport.ERROR));
					continue;
				}
				try {
					final ViewModelContext viewModelContext = new EMFFormsSpreadsheetViewModelContext(viewModel,
						domainObject);
					prepareViewContext(keyStyleMap, viewModelContext);
					final EMFFormsAbstractSpreadsheetRenderer<VElement> renderer = emfFormsSpreadsheetRendererFactory
						.getRendererInstance(
							viewModelContext.getViewModel(), viewModelContext);
					renderer.render(workbook, viewModelContext.getViewModel(), viewModelContext,
						new EMFFormsSpreadsheetRenderTarget("root", //$NON-NLS-1$
							i++, 0));
					viewModelContext.dispose();
				} catch (final EMFFormsNoRendererException ex) {
					reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
				}
			}
		}
		return workbook;
	}

	private void addDefaultCellStyles(Workbook workbook, Map<String, CellStyle> keyStyleMap) {
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setLocked(true);
		keyStyleMap.put(EMFFormsCellStyleConstants.LOCKED, cellStyle);
		final CellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setLocked(true);
		cellStyle2.setWrapText(true);
		keyStyleMap.put(EMFFormsCellStyleConstants.LOCKED_AND_WRAPPED, cellStyle2);
		final CellStyle cellStyle3 = workbook.createCellStyle();
		cellStyle3.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text")); //$NON-NLS-1$
		keyStyleMap.put(EMFFormsCellStyleConstants.TEXT, cellStyle3);
		final CellStyle cellStyle4 = workbook.createCellStyle();
		cellStyle4.setDataFormat((short) BuiltinFormats.getBuiltinFormat("m/d/yy")); //$NON-NLS-1$
		keyStyleMap.put(EMFFormsCellStyleConstants.DATE, cellStyle4);
	}

	private void prepareNumberCellStyles(Workbook workbook, Set<String> retrievedFormats,
		Map<String, CellStyle> keyStyleMap) {
		final DataFormat dataFormat = workbook.createDataFormat();
		for (final String format : retrievedFormats) {
			final CellStyle cellStyleNumberFormat = workbook.createCellStyle();
			cellStyleNumberFormat.setDataFormat(dataFormat.getFormat(format));
			keyStyleMap.put(format, cellStyleNumberFormat);
		}
	}

	private void prepareViewContext(Map<String, CellStyle> formatStyleMap, ViewModelContext viewModelContext) {
		for (final String key : formatStyleMap.keySet()) {
			viewModelContext.putContextValue(key, formatStyleMap.get(key));
		}
	}

	private Set<String> retrieveAllFormats(EClass eClass) {
		final Set<String> formats = new LinkedHashSet<String>();
		final Set<EClass> checkedClasses = new LinkedHashSet<EClass>();
		final Queue<EClass> uncheckedClasses = new LinkedList<EClass>();
		uncheckedClasses.add(eClass);
		while (!uncheckedClasses.isEmpty()) {
			retrieveAllFormats(uncheckedClasses.poll(), formats, checkedClasses, uncheckedClasses);
		}
		return formats;
	}

	private void retrieveAllFormats(EClass eClass, Set<String> formats, Set<EClass> checkedClasses,
		Queue<EClass> uncheckedClasses) {
		if (checkedClasses.contains(eClass)) {
			return;
		}
		for (final EReference eReference : eClass.getEAllReferences()) {
			uncheckedClasses.offer(eReference.getEReferenceType());
		}
		for (final EAttribute eAttribute : eClass.getEAllAttributes()) {
			final String format = NumberFormatHelper.getNumberFormat(eAttribute);
			if (format == null) {
				continue;
			}
			formats.add(format);
		}
		checkedClasses.add(eClass);
	}

	/**
	 * Interface which encapsulates the call to get a viewmodel for EObject + properties.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	public interface ViewProvider {

		/**
		 * Returns the view model for the given EObject/properties.
		 *
		 * @param viewEobject the object
		 * @param properties the {@link VViewModelProperties properties}
		 * @return the view
		 */
		VView getViewModel(EObject viewEobject, VViewModelProperties properties);
	}
}
