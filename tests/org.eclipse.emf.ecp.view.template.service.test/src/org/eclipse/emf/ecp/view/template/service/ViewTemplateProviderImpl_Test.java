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
package org.eclipse.emf.ecp.view.template.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecp.spi.view.template.service.ViewTemplateSupplier;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTTemplateFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentFactory;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty;
import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundFactory;
import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundStyleProperty;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationFactory;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationPackage;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ViewTemplateProviderImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class ViewTemplateProviderImpl_Test {

	private ViewTemplateProviderImpl provider;
	private ViewTemplateSupplier supplier1;
	private ViewTemplateSupplier supplier2;
	private ReportService reportService;

	/**
	 * Set up the {@link ViewTemplateProviderImpl} with two basic suppliers for every test.
	 */
	@Before
	public void setUp() {
		provider = new ViewTemplateProviderImpl();
		reportService = mock(ReportService.class);
		provider.setReportService(reportService);
		supplier1 = mock(ViewTemplateSupplier.class);
		supplier2 = mock(ViewTemplateSupplier.class);
		provider.addViewTemplateSupplier(supplier1);
		provider.addViewTemplateSupplier(supplier2);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void hasControlValidationTemplate_True() {
		final VTViewTemplate viewTemplate1 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		final VTViewTemplate viewTemplate2 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		viewTemplate2.setControlValidationConfiguration(VTTemplateFactory.eINSTANCE.createControlValidationTemplate());
		when(supplier1.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate1));
		when(supplier2.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate2));

		assertTrue(provider.hasControlValidationTemplate());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void hasControlValidationTemplate_False() {
		final VTViewTemplate viewTemplate1 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		final VTViewTemplate viewTemplate2 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		when(supplier1.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate1));
		when(supplier2.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate2));

		assertFalse(provider.hasControlValidationTemplate());
	}

	/**
	 * Test that the {@link ViewTemplateProviderImpl} returns a merged view template containing all styles and
	 * referenced ecores from all view templates provided by its suppliers.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void getViewTemplate() {
		final VTViewTemplate viewTemplate1 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		final VTViewTemplate viewTemplate2 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		when(supplier1.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate1));
		when(supplier2.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate2));

		// Have one referenced ecore used by both to verify that the reference is not duplicated in the result
		viewTemplate1.getReferencedEcores().add("http://test/one"); //$NON-NLS-1$
		viewTemplate2.getReferencedEcores().add("http://test/one"); //$NON-NLS-1$
		viewTemplate2.getReferencedEcores().add("http://test/two"); //$NON-NLS-1$

		final VTControlValidationTemplate validationTemplate = VTTemplateFactory.eINSTANCE
			.createControlValidationTemplate();
		viewTemplate2.setControlValidationConfiguration(validationTemplate);

		// Cannot mock styles because otherwise the style cannot be added to the new view template in the tested method
		final VTStyle style1 = VTTemplateFactory.eINSTANCE.createStyle();
		// Cannot mock because otherwise adding it to the style will fail
		final VTAlignmentStyleProperty styleProperty1 = VTAlignmentFactory.eINSTANCE.createAlignmentStyleProperty();
		styleProperty1.setType(AlignmentType.LEFT);
		style1.getProperties().add(styleProperty1);

		final VTStyle style2 = VTTemplateFactory.eINSTANCE.createStyle();
		final VTAlignmentStyleProperty styleProperty2 = VTAlignmentFactory.eINSTANCE.createAlignmentStyleProperty();
		styleProperty2.setType(AlignmentType.RIGHT);
		style2.getProperties().add(styleProperty2);

		viewTemplate1.getStyles().add(style1);
		viewTemplate2.getStyles().add(style2);

		final VTViewTemplate result = provider.getViewTemplate();

		assertNotNull(result);
		assertEquals("The merged view template should all styles of the supplied view templates.", 2, //$NON-NLS-1$
			result.getStyles().size());
		assertTrue(result.getStyles().contains(style1));
		assertTrue(result.getStyles().contains(style2));
		assertEquals(validationTemplate, result.getControlValidationConfiguration());
		assertEquals("The merged view template should not contain duplicated referenced ecores.", 2, //$NON-NLS-1$
			result.getReferencedEcores().size());
		assertTrue(result.getReferencedEcores().contains("http://test/one")); //$NON-NLS-1$
		assertTrue(result.getReferencedEcores().contains("http://test/two")); //$NON-NLS-1$
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getViewTemplate_NoSuppliers() {
		provider.removeViewTemplateSupplier(supplier1);
		provider.removeViewTemplateSupplier(supplier2);

		final VTViewTemplate viewTemplate = provider.getViewTemplate();
		assertNotNull("The ViewTemplateProviderImpl should never " //$NON-NLS-1$
			+ "return null as a view template", viewTemplate); //$NON-NLS-1$
		assertTrue("The View Template should be empty", viewTemplate.getStyles().isEmpty()); //$NON-NLS-1$
		assertNull("The View Template should be empty", viewTemplate.getControlValidationConfiguration()); //$NON-NLS-1$
		assertTrue("The View Template should be empty", viewTemplate.getReferencedEcores().isEmpty()); //$NON-NLS-1$
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getViewTemplate_NoSuppliedTemplates() {
		when(supplier1.getViewTemplates()).thenReturn(Collections.<VTViewTemplate> emptySet());
		when(supplier2.getViewTemplates()).thenReturn(Collections.<VTViewTemplate> emptySet());

		final VTViewTemplate viewTemplate = provider.getViewTemplate();
		assertNotNull("The ViewTemplateProviderImpl should never " //$NON-NLS-1$
			+ "return null as a view template", viewTemplate); //$NON-NLS-1$
		assertTrue("The View Template should be empty", viewTemplate.getStyles().isEmpty()); //$NON-NLS-1$
		assertNull("The View Template should be empty", viewTemplate.getControlValidationConfiguration()); //$NON-NLS-1$
		assertTrue("The View Template should be empty", viewTemplate.getReferencedEcores().isEmpty()); //$NON-NLS-1$
	}

	/**
	 * Test that a legacy {@link VTControlValidationTemplate} is properly converted to a global
	 * {@link VTValidationStyleProperty}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void getStyleProperties_ControlValidationConfiguration() {
		final VTViewTemplate viewTemplate1 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		when(supplier1.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate1));
		when(supplier1.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(new LinkedHashMap<VTStyleProperty, Double>());
		final VTControlValidationTemplate validationTemplate1 = VTTemplateFactory.eINSTANCE
			.createControlValidationTemplate();
		viewTemplate1.setControlValidationConfiguration(validationTemplate1);
		validationTemplate1.setCancelColorHEX("#abcdef"); //$NON-NLS-1$

		final VTValidationStyleProperty validationProperty = VTValidationFactory.eINSTANCE
			.createValidationStyleProperty();
		when(supplier2.getViewTemplates()).thenReturn(Collections.<VTViewTemplate> emptySet());
		final HashMap<VTStyleProperty, Double> styleMap = new HashMap<VTStyleProperty, Double>();
		styleMap.put(validationProperty, 5d); // 5 equals the priority of a View Model Element selector
		when(supplier2.getStyleProperties(any(VElement.class), any(ViewModelContext.class))).thenReturn(styleMap);

		final Set<VTStyleProperty> result = provider.getStyleProperties(mock(VElement.class),
			mock(ViewModelContext.class));

		assertEquals(2, result.size());
		final Iterator<VTStyleProperty> iterator = result.iterator();
		// The configured validation style property must be returned before the generated one because generated
		// properties should be less applicable than style properties defined in a view template
		assertEquals(iterator.next(), validationProperty);
		final VTStyleProperty generatedProperty = iterator.next();
		assertEquals(VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY, generatedProperty.eClass());
		final VTValidationStyleProperty generatedValidationProperty = (VTValidationStyleProperty) generatedProperty;
		assertEquals("#abcdef", generatedValidationProperty.getCancelColorHEX()); //$NON-NLS-1$
		// Sample check that the generated property's values are not set to something "random"
		assertNull(generatedValidationProperty.getCancelForegroundColorHEX());
		assertNull(generatedValidationProperty.getErrorColorHEX());
	}

	/**
	 * Tests various aspects of the getStyleProperties method:
	 * <ul>
	 * <li>Style Properties from all Suppliers are collected</li>
	 * <li>For two applicable, <strong>equal</strong> styles with different specificities, the highest specificity is
	 * used</li>
	 * <li>For two applicable, <strong>unequal</strong> styles of the same type with equal specificities, a warning is
	 * logged because there is no specified behaviour which one of the styles is ranked higher.</li>
	 * <li>The result set is ordered by specificity in descending order</li>
	 * </ul>
	 */
	@Test
	public void getStyleProperties() {
		final VTViewTemplate viewTemplate1 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		when(supplier1.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate1));
		final VTViewTemplate viewTemplate2 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		when(supplier2.getViewTemplates()).thenReturn(Collections.singleton(viewTemplate2));

		final Map<VTStyleProperty, Double> propertiesMap1 = new LinkedHashMap<VTStyleProperty, Double>();
		when(supplier1.getStyleProperties(any(VElement.class), any(ViewModelContext.class))).thenReturn(propertiesMap1);
		final Map<VTStyleProperty, Double> propertiesMap2 = new LinkedHashMap<VTStyleProperty, Double>();
		when(supplier2.getStyleProperties(any(VElement.class), any(ViewModelContext.class))).thenReturn(propertiesMap2);

		final VTAlignmentStyleProperty alignmentLeft = VTAlignmentFactory.eINSTANCE.createAlignmentStyleProperty();
		alignmentLeft.setType(AlignmentType.LEFT);
		final VTValidationStyleProperty validationHigh = VTValidationFactory.eINSTANCE.createValidationStyleProperty();
		propertiesMap1.put(alignmentLeft, 5d);
		propertiesMap1.put(validationHigh, 20d);

		final VTValidationStyleProperty validationLow = VTValidationFactory.eINSTANCE.createValidationStyleProperty();
		final VTMandatoryStyleProperty priorityIntermediate = VTMandatoryFactory.eINSTANCE
			.createMandatoryStyleProperty();
		final VTBackgroundStyleProperty priorityLowest = VTBackgroundFactory.eINSTANCE.createBackgroundStyleProperty();
		final VTAlignmentStyleProperty alignmentRight = VTAlignmentFactory.eINSTANCE.createAlignmentStyleProperty();
		alignmentRight.setType(AlignmentType.RIGHT);
		propertiesMap2.put(validationLow, 2d);
		propertiesMap2.put(priorityIntermediate, 7d);
		propertiesMap2.put(alignmentRight, 5d);
		propertiesMap2.put(priorityLowest, 0.1d);

		final Set<VTStyleProperty> result = provider.getStyleProperties(mock(VElement.class),
			mock(ViewModelContext.class));

		assertEquals(5, result.size());

		final Iterator<VTStyleProperty> iterator = result.iterator();
		assertSame("The result set must be sorted in descending order according to the properties' specificities.", //$NON-NLS-1$
			validationHigh, iterator.next());
		assertSame("The result set must be sorted in descending order according to the properties' specificities.", //$NON-NLS-1$
			priorityIntermediate, iterator.next());

		// The conflicting properties are both present according to their specificities compared to the other
		// properties. However, their order is not specified. Therefore, a warning is logged additionally.
		final VTStyleProperty third = iterator.next();
		final VTStyleProperty forth = iterator.next();
		assertTrue(third == alignmentRight || third == alignmentLeft);
		assertTrue(forth == alignmentRight || forth == alignmentLeft);
		assertNotSame(third, forth);
		verify(reportService).report(any(AbstractReport.class));

		assertSame(priorityLowest, iterator.next());
	}
}
