/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.mappingprovider.table.panel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.EMFFormsViewService;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link EMFFormsMappingProviderTable}.
 *
 * @author Eugen Neufeld
 *
 */
public class EMFFormsMappingProviderTable_Test {

	private EMFFormsMappingProviderTable provider;
	private ReportService reportService;
	private EMFFormsDatabindingEMF databinding;
	private EMFFormsViewService viewService;

	@Before
	public void setUp() {
		provider = new EMFFormsMappingProviderTable();
		reportService = mock(ReportService.class);
		databinding = mock(EMFFormsDatabindingEMF.class);
		viewService = mock(EMFFormsViewService.class);

		provider.setReportService(reportService);
		provider.setEMFFormsDatabinding(databinding);
		provider.setEMFFormsViewService(viewService);
	}

	/**
	 * Tests that the get mapping for method does not fail and does not report an error if the data binding of a column
	 * dmr fails.
	 *
	 * @throws DatabindingFailedException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetMappingFor() throws DatabindingFailedException {
		final VTableDomainModelReference tableDmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
		final VTableControl table = VTableFactory.eINSTANCE.createTableControl();
		table.setDomainModelReference(tableDmr);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.getChildren().add(table);

		final Setting tableSetting = mock(Setting.class);
		final EObject tableEntry = mock(EObject.class);
		final EClass tableEntryClass = mock(EClass.class);
		when(tableEntry.eClass()).thenReturn(tableEntryClass);
		final EList<EStructuralFeature> allFeatures = new BasicEList<EStructuralFeature>();
		when(tableEntryClass.getEAllStructuralFeatures()).thenReturn(allFeatures);
		allFeatures.add(mock(EStructuralFeature.class));
		when(tableSetting.get(anyBoolean())).thenReturn(Collections.singletonList(tableEntry));

		// Return mocked EObject's to avoid the created unique settings being equal.
		final EObject tableSettingEObject = mock(EObject.class);
		when(tableSetting.getEObject()).thenReturn(tableSettingEObject);

		// Cannot mock dmrs because mocked dmrs cannot be added to an EList
		final VDomainModelReference columnInvalid = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		tableDmr.getColumnDomainModelReferences().add(columnInvalid);

		final VView detailView = VViewFactory.eINSTANCE.createView();
		final VControl innerControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference innerControlDMR = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		innerControl.setDomainModelReference(innerControlDMR);
		detailView.getChildren().add(innerControl);

		when(viewService.getView(same(tableEntry), any(VViewModelProperties.class))).thenReturn(detailView);

		when(databinding.getSetting(same(columnInvalid), any(EObject.class)))
			.thenThrow(DatabindingFailedException.class);
		when(databinding.getSetting(same(tableDmr), any(EObject.class))).thenReturn(tableSetting);

		final Setting panelSetting = mock(Setting.class);
		when(panelSetting.getEObject()).thenReturn(tableEntry);
		when(databinding.getSetting(same(innerControlDMR), same(tableEntry))).thenReturn(panelSetting);

		final Set<UniqueSetting> result = provider.getMappingFor(tableDmr, mock(EObject.class));

		assertEquals(2, result.size());
		assertTrue("Result must contain the table setting.", containsSettingWithEObject(result, tableSettingEObject)); //$NON-NLS-1$
		assertTrue("Result must contain the setting of table entries.", containsSettingWithEObject(result, tableEntry)); //$NON-NLS-1$
		verify(reportService, times(0)).report(any(AbstractReport.class));
	}

	private boolean containsSettingWithEObject(Set<UniqueSetting> set, EObject eObject) {
		for (final UniqueSetting unique : set) {
			if (unique.getEObject().equals(eObject)) {
				return true;
			}
		}
		return false;
	}
}
