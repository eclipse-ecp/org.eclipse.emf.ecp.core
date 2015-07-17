/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter;
import org.junit.Before;
import org.junit.Test;

public class EMFFormsSpreadsheetMultiAttributeConverter_Test {

	private ReportService reportService;
	private EMFFormsDatabinding databinding;
	private EMFFormsSpreadsheetMultiAttributeConverter converter;
	private EObject domainObject;
	private VDomainModelReference dmr;

	@Before
	public void before() {
		converter = new EMFFormsSpreadsheetMultiAttributeConverter();
		reportService = mock(ReportService.class);
		databinding = mock(EMFFormsDatabinding.class);
		domainObject = mock(EObject.class);
		dmr = mock(VDomainModelReference.class);
	}

	@Test
	public void testApplicableNoFeature() throws DatabindingFailedException {
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenThrow(new DatabindingFailedException("")); //$NON-NLS-1$
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals(EMFFormsSpreadsheetValueConverter.NOT_APPLICABLE, converter.isApplicable(domainObject, dmr), 0d);
	}

	@Test
	public void testApplicableNoEAttribute() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(TaskPackage.eINSTANCE.getUser_Tasks());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals(EMFFormsSpreadsheetValueConverter.NOT_APPLICABLE, converter.isApplicable(domainObject, dmr), 0d);
	}

	@Test
	public void testApplicableSingleEAttribute() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(TaskPackage.eINSTANCE.getUser_Active());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals(EMFFormsSpreadsheetValueConverter.NOT_APPLICABLE, converter.isApplicable(domainObject, dmr), 0d);
	}

	@Test
	public void testApplicableMultiEAttribute() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(BowlingPackage.eINSTANCE.getPlayer_EMails());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals(0d, converter.isApplicable(domainObject, dmr), 0d);
	}

	@Test
	public void testToStringEmptyList() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(BowlingPackage.eINSTANCE.getPlayer_EMails());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals("", converter.convertValueToString(Collections.<String> emptyList(), domainObject, dmr)); //$NON-NLS-1$
	}

	@Test
	public void testToStringNonEmptyList() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(BowlingPackage.eINSTANCE.getPlayer_EMails());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals("foo@bar.org foo@bar.com", //$NON-NLS-1$
			converter.convertValueToString(Arrays.asList("foo@bar.org", "foo@bar.com"), domainObject, dmr)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testFromStringEmpty() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(BowlingPackage.eINSTANCE.getPlayer_EMails());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		final Object value = converter.convertStringToValue("", domainObject, dmr); //$NON-NLS-1$
		final List<?> list = List.class.cast(value);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testFromStringNull() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(BowlingPackage.eINSTANCE.getPlayer_EMails());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		final Object value = converter.convertStringToValue(null, domainObject, dmr);
		final List<?> list = List.class.cast(value);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testFromString() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(BowlingPackage.eINSTANCE.getPlayer_EMails());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		final Object value = converter.convertStringToValue("foo@bar.org foo@bar.com", domainObject, dmr); //$NON-NLS-1$
		assertTrue(List.class.isInstance(value));
		final List<?> list = List.class.cast(value);
		assertEquals(2, list.size());
		assertEquals("foo@bar.org", list.get(0)); //$NON-NLS-1$
		assertEquals("foo@bar.com", list.get(1)); //$NON-NLS-1$
	}

}
