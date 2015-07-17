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
import java.util.List;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.makeithappen.model.task.Task;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskFactory;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter;
import org.junit.Before;
import org.junit.Test;

public class EMFFormsSpreadsheetMultiReferenceConverter_Test {

	private static final String LINE_SEP = System.getProperty("line.separator"); //$NON-NLS-1$
	private static final String EXPECTED = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEP + //$NON-NLS-1$
		"<org.eclipse.emf.ecp.makeithappen.model.task:Task xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:org.eclipse.emf.ecp.makeithappen.model.task=\"http://eclipse/org/emf/ecp/makeithappen/model/task\" description=\"1\"/>" //$NON-NLS-1$
		+ LINE_SEP +
		"\n" + //$NON-NLS-1$
		"\n" + //$NON-NLS-1$
		"\n" + //$NON-NLS-1$
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEP + //$NON-NLS-1$
		"<org.eclipse.emf.ecp.makeithappen.model.task:Task xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:org.eclipse.emf.ecp.makeithappen.model.task=\"http://eclipse/org/emf/ecp/makeithappen/model/task\" description=\"2\"/>" //$NON-NLS-1$
		+ LINE_SEP;

	private ReportService reportService;
	private EMFFormsDatabinding databinding;
	private EMFFormsSpreadsheetMultiReferenceConverter converter;
	private EObject domainObject;
	private VDomainModelReference dmr;

	@Before
	public void before() {
		converter = new EMFFormsSpreadsheetMultiReferenceConverter();
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
	public void testApplicableNoReference() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(TaskPackage.eINSTANCE.getUser_Active());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals(EMFFormsSpreadsheetValueConverter.NOT_APPLICABLE, converter.isApplicable(domainObject, dmr), 0d);
	}

	@Test
	public void testApplicableSingleReference() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(TaskPackage.eINSTANCE.getTask_Assignee());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals(EMFFormsSpreadsheetValueConverter.NOT_APPLICABLE, converter.isApplicable(domainObject, dmr), 0d);
	}

	@Test
	public void testApplicableMultiReference() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(TaskPackage.eINSTANCE.getTask_SubTasks());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals(0d, converter.isApplicable(domainObject, dmr), 0d);
	}

	@Test
	public void testToString() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(TaskPackage.eINSTANCE.getTask_SubTasks());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		assertEquals(EXPECTED, converter.convertValueToString(Arrays.asList(task("1"), task("2")), domainObject, dmr));//$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testFromString() throws DatabindingFailedException {
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(TaskPackage.eINSTANCE.getTask_SubTasks());
		when(databinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(property);
		converter.setDatabinding(databinding);
		converter.setReportService(reportService);
		@SuppressWarnings("unchecked")
		final List<Task> tasks = (List<Task>) converter.convertStringToValue(EXPECTED, domainObject, dmr);
		assertEquals(2, tasks.size());
		assertTrue(EcoreUtil.equals(task("1"), tasks.get(0))); //$NON-NLS-1$
		assertTrue(EcoreUtil.equals(task("2"), tasks.get(1))); //$NON-NLS-1$
	}

	private static Task task(String desc) {
		final Task task = TaskFactory.eINSTANCE.createTask();
		task.setDescription(desc);
		return task;

	}

}
