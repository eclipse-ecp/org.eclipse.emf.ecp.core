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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.datatemplate.DataTemplatePackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.junit.Before;
import org.junit.Test;

public class TemplateInstanceRendererService_Test {

	private TemplateInstanceRendererService service;

	@Before
	public void setUp() throws Exception {
		service = new TemplateInstanceRendererService();
	}

	@Test
	public void testIsApplicableForView() {
		assertEquals(EMFFormsDIRendererService.NOT_APPLICABLE,
			service.isApplicable(VViewFactory.eINSTANCE.createView(), null), 0);
	}

	@Test
	public void testIsApplicableForEmptyControl() {
		assertEquals(EMFFormsDIRendererService.NOT_APPLICABLE,
			service.isApplicable(VViewFactory.eINSTANCE.createControl(), null), 0);
	}

	@Test
	public void testIsApplicableDatabindingException() throws DatabindingFailedException {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(dmr);
		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		final ReportService reportService = mock(ReportService.class);
		service.setEMFFormsDatabinding(databinding);
		service.setReportService(reportService);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final EObject domainModel = mock(EObject.class);
		when(vmc.getDomainModel()).thenReturn(domainModel);
		when(databinding.getValueProperty(dmr, domainModel)).thenThrow(new DatabindingFailedException("Test Error")); //$NON-NLS-1$
		assertEquals(EMFFormsDIRendererService.NOT_APPLICABLE, service.isApplicable(control, vmc), 0);
		inOrder(reportService).verify(reportService, times(1)).report(any(AbstractReport.class));
	}

	@Test
	public void testIsApplicableWrongFeature() throws DatabindingFailedException {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(dmr);
		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		service.setEMFFormsDatabinding(databinding);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final EObject domainModel = mock(EObject.class);
		when(vmc.getDomainModel()).thenReturn(domainModel);
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(EcorePackage.eINSTANCE.getENamedElement_Name());
		when(databinding.getValueProperty(dmr, domainModel)).thenReturn(property);
		assertEquals(EMFFormsDIRendererService.NOT_APPLICABLE, service.isApplicable(control, vmc), 0);
	}

	@Test
	public void testIsApplicableCorrectFeature() throws DatabindingFailedException {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(dmr);
		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		service.setEMFFormsDatabinding(databinding);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final EObject domainModel = mock(EObject.class);
		when(vmc.getDomainModel()).thenReturn(domainModel);
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(DataTemplatePackage.eINSTANCE.getTemplate_Instance());
		when(databinding.getValueProperty(dmr, domainModel)).thenReturn(property);
		assertEquals(10, service.isApplicable(control, vmc), 0);
	}

	@Test
	public void testGetRendererClass() {
		assertSame(TemplateInstanceRenderer.class, service.getRendererClass());
	}

}
