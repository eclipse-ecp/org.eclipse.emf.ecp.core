/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.control.multireference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotationFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link LinkOnlyMultiReferenceRendererService}.
 *
 * @author Lucas Koehler
 *
 */
public class LinkOnlyMultiReferenceRendererService_Test {

	private LinkOnlyMultiReferenceRendererService service;

	@Before
	public void setUp() {
		service = new LinkOnlyMultiReferenceRendererService();
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

	// TODO
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
	public void testIsApplicableMultiAttribute() throws DatabindingFailedException {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(dmr);
		final EAttribute attribute = mock(EAttribute.class);
		when(attribute.isMany()).thenReturn(true);
		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		service.setEMFFormsDatabinding(databinding);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final EObject domainModel = mock(EObject.class);
		when(vmc.getDomainModel()).thenReturn(domainModel);
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(attribute);
		when(databinding.getValueProperty(dmr, domainModel)).thenReturn(property);
		assertEquals(EMFFormsDIRendererService.NOT_APPLICABLE, service.isApplicable(control, vmc), 0);
	}

	// TODO no annotation
	@Test
	public void testIsApplicableNoAnnotation() throws DatabindingFailedException {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(dmr);

		final EReference reference = mock(EReference.class);
		when(reference.isMany()).thenReturn(true);

		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		service.setEMFFormsDatabinding(databinding);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final EObject domainModel = mock(EObject.class);
		when(vmc.getDomainModel()).thenReturn(domainModel);
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(reference);
		when(databinding.getValueProperty(dmr, domainModel)).thenReturn(property);
		assertEquals(EMFFormsDIRendererService.NOT_APPLICABLE, service.isApplicable(control, vmc), 0);
	}

	@Test
	public void testIsApplicableIncorrectAnnotation() throws DatabindingFailedException {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(dmr);
		final VAnnotation annotation = VAnnotationFactory.eINSTANCE.createAnnotation();
		annotation.setKey("incorrect"); //$NON-NLS-1$
		control.getAttachments().add(annotation);

		final EReference reference = mock(EReference.class);
		when(reference.isMany()).thenReturn(true);

		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		service.setEMFFormsDatabinding(databinding);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final EObject domainModel = mock(EObject.class);
		when(vmc.getDomainModel()).thenReturn(domainModel);
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(reference);
		when(databinding.getValueProperty(dmr, domainModel)).thenReturn(property);
		assertEquals(EMFFormsDIRendererService.NOT_APPLICABLE, service.isApplicable(control, vmc), 0);
	}

	@Test
	public void testIsApplicableCorrectAnnotation() throws DatabindingFailedException {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(dmr);
		final VAnnotation annotation = VAnnotationFactory.eINSTANCE.createAnnotation();
		annotation.setKey(LinkOnlyMultiReferenceRendererService.ANNOTATION_KEY);
		control.getAttachments().add(annotation);

		final EReference reference = mock(EReference.class);
		when(reference.isMany()).thenReturn(true);

		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		service.setEMFFormsDatabinding(databinding);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final EObject domainModel = mock(EObject.class);
		when(vmc.getDomainModel()).thenReturn(domainModel);
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(reference);
		when(databinding.getValueProperty(dmr, domainModel)).thenReturn(property);
		assertEquals(6, service.isApplicable(control, vmc), 0);
	}

	@Test
	public void testIsApplicableSingleReferenceWithCorrectAnnotation() throws DatabindingFailedException {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(dmr);
		final VAnnotation annotation = VAnnotationFactory.eINSTANCE.createAnnotation();
		annotation.setKey(LinkOnlyMultiReferenceRendererService.ANNOTATION_KEY);
		control.getAttachments().add(annotation);

		final EReference reference = mock(EReference.class);
		when(reference.isMany()).thenReturn(false);

		final EMFFormsDatabinding databinding = mock(EMFFormsDatabinding.class);
		service.setEMFFormsDatabinding(databinding);
		final ViewModelContext vmc = mock(ViewModelContext.class);
		final EObject domainModel = mock(EObject.class);
		when(vmc.getDomainModel()).thenReturn(domainModel);
		final IValueProperty property = mock(IValueProperty.class);
		when(property.getValueType()).thenReturn(reference);
		when(databinding.getValueProperty(dmr, domainModel)).thenReturn(property);
		assertEquals(EMFFormsDIRendererService.NOT_APPLICABLE, service.isApplicable(control, vmc), 0);
	}

	@Test
	public void testGetRendererClass() {
		assertSame(LinkOnlyMultiReferenceRenderer.class, service.getRendererClass());
	}
}
