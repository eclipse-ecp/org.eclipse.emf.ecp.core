/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.mappingprovider.table;

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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
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
 * @author Lucas Koehler
 *
 */
public class EMFFormsMappingProviderTable_Test {

	private EMFFormsMappingProviderTable provider;
	private ReportService reportService;
	private EMFFormsDatabindingEMF databinding;

	@Before
	public void setUp() {
		provider = new EMFFormsMappingProviderTable();
		reportService = mock(ReportService.class);
		databinding = mock(EMFFormsDatabindingEMF.class);

		provider.setReportService(reportService);
		provider.setEMFFormsDatabinding(databinding);
	}

	/**
	 * Tests that the get mapping for method does not fail and does not report an error if the data binding of a column
	 * dmr fails.
	 *
	 * @throws DatabindingFailedException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetMappingFor_InvalidColumnDmr() throws DatabindingFailedException {
		final VTableDomainModelReference tableDmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
		final Setting tableSetting = mock(Setting.class);
		when(tableSetting.get(anyBoolean())).thenReturn(Collections.singletonList(mock(EObject.class)));
		final Setting columnValidSetting = mock(Setting.class);

		// Return mocked EObject's to avoid the created unique settings being equal.
		final EObject tableSettingEObject = mock(EObject.class);
		final EObject columSettingEObject = mock(EObject.class);
		when(tableSetting.getEObject()).thenReturn(tableSettingEObject);
		when(columnValidSetting.getEObject()).thenReturn(columSettingEObject);

		// Cannot mock dmrs because mocked dmrs cannot be added to an EList
		final VDomainModelReference columnInvalid = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReference columnValid = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		tableDmr.getColumnDomainModelReferences().add(columnInvalid);
		tableDmr.getColumnDomainModelReferences().add(columnValid);

		when(databinding.getSetting(same(columnInvalid), any(EObject.class)))
			.thenThrow(DatabindingFailedException.class);
		when(databinding.getSetting(same(columnValid), any(EObject.class))).thenReturn(columnValidSetting);
		when(databinding.getSetting(same(tableDmr), any(EObject.class))).thenReturn(tableSetting);

		final Set<UniqueSetting> result = provider.getMappingFor(tableDmr, mock(EObject.class));

		assertEquals(2, result.size());
		assertTrue("Result must contain the table setting.", containsSettingWithEObject(result, tableSettingEObject)); //$NON-NLS-1$
		assertTrue("Result must contain the valid column's setting.", //$NON-NLS-1$
			containsSettingWithEObject(result, columSettingEObject));
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
