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
package org.eclipse.emf.ecp.view.template.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTTemplateFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test cases for the {@link ViewTemplateSupplierImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class ViewTemplateSupplierImpl_Test {

	private ViewTemplateSupplierImpl templateSupplier;

	@Before
	public void setup() {
		templateSupplier = new ViewTemplateSupplierImpl();
	}

	@Test
	public void testGetTemplateModels() {
		final VTViewTemplate template1 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		final VTViewTemplate template2 = VTTemplateFactory.eINSTANCE.createViewTemplate();
		final VTStyle style1 = VTTemplateFactory.eINSTANCE.createStyle();
		template1.getStyles().add(style1);
		final LinkedHashSet<VTViewTemplate> templates = new LinkedHashSet<VTViewTemplate>();
		templates.add(template1);
		templates.add(template2);

		templateSupplier.setViewTemplates(templates);

		final Iterator<VTViewTemplate> iterator = templateSupplier.getViewTemplates().iterator();
		final VTViewTemplate copy1 = iterator.next();
		assertNotSame(template1, copy1);
		assertTrue(EcoreUtil.equals(template1, copy1));
		final VTViewTemplate copy2 = iterator.next();
		assertNotSame(template2, copy2);
		assertTrue(EcoreUtil.equals(template2, copy2));
	}

	@Test
	public void testNoTemplateModel() {
		final Map<VTStyleProperty, Double> styleProperties = templateSupplier.getStyleProperties(mock(VElement.class),
			mock(ViewModelContext.class));
		assertTrue(styleProperties.isEmpty());
	}

	@Test
	public void testVElementIsNull() {
		final List<VTViewTemplate> templates = createListWithTwoTemplates();
		templates.get(0).getStyles().add(mockStyle(1d));
		templates.get(1).getStyles().add(mockStyle(1d));
		templateSupplier.setViewTemplates(new LinkedHashSet<VTViewTemplate>(templates));

		final Map<VTStyleProperty, Double> styleProperties = templateSupplier.getStyleProperties(null,
			mock(ViewModelContext.class));
		assertTrue(styleProperties.isEmpty());
	}

	@Test
	public void testViewModelContextIsNull() {
		final List<VTViewTemplate> templates = createListWithTwoTemplates();
		templates.get(0).getStyles().add(mockStyle(1d));
		templates.get(1).getStyles().add(mockStyle(1d));
		templateSupplier.setViewTemplates(new LinkedHashSet<VTViewTemplate>(templates));

		final Map<VTStyleProperty, Double> styleProperties = templateSupplier.getStyleProperties(mock(VElement.class),
			null);
		assertTrue(styleProperties.isEmpty());
	}

	/**
	 * Test that only applicable style properties are returned.
	 */
	@Test
	public void testApplicableStyleProperties() {
		final List<VTViewTemplate> templates = createListWithTwoTemplates();
		final VTStyle style1 = mockStyle(1d);
		final VTStyleProperty styleProperty1 = style1.getProperties().get(0);
		final VTStyle style2 = mockStyle(2d);
		final VTStyleProperty styleProperty2 = style2.getProperties().get(0);
		templates.get(0).getStyles().add(style1);
		templates.get(0).getStyles().add(mockStyle(VTStyleSelector.NOT_APPLICABLE));
		templates.get(1).getStyles().add(style2);

		final VControl vElement = mock(VControl.class);
		final VDomainModelReference dmr = mock(VDomainModelReference.class);
		when(vElement.getDomainModelReference()).thenReturn(dmr);
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		templateSupplier.setViewTemplates(new LinkedHashSet<VTViewTemplate>(templates));

		final Map<VTStyleProperty, Double> styleProperties = templateSupplier.getStyleProperties(vElement,
			viewModelContext);

		assertEquals(2, styleProperties.size());
		assertTrue(styleProperties.containsKey(styleProperty1));
		assertTrue(styleProperties.containsKey(styleProperty2));
	}

	/** Test that style properties are associated with their correct specificities. */
	@Test
	public void testSpecifities() {
		final List<VTViewTemplate> templates = createListWithTwoTemplates();
		final VTStyle style1 = mockStyle(3d);
		final VTStyleProperty styleProperty1 = style1.getProperties().get(0);
		final VTStyle style2 = mockStyle(1d);
		final VTStyleProperty styleProperty2 = style2.getProperties().get(0);
		templates.get(0).getStyles().add(style1);
		templates.get(1).getStyles().add(style2);

		when(styleProperty1.equalStyles(styleProperty2)).thenReturn(true);
		when(styleProperty2.equalStyles(styleProperty1)).thenReturn(true);

		templateSupplier.setViewTemplates(new LinkedHashSet<VTViewTemplate>(templates));

		final VControl vElement = mock(VControl.class);
		final VDomainModelReference dmr = mock(VDomainModelReference.class);
		when(vElement.getDomainModelReference()).thenReturn(dmr);
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		final Map<VTStyleProperty, Double> styleProperties = templateSupplier.getStyleProperties(vElement,
			viewModelContext);

		assertEquals(2, styleProperties.size());
		assertTrue(styleProperties.containsKey(styleProperty1));
		assertEquals(new Double(3d), styleProperties.get(styleProperty1));
		assertTrue(styleProperties.containsKey(styleProperty2));
		assertEquals(new Double(1d), styleProperties.get(styleProperty2));
	}

	private static VTStyle mockStyle(double specificity) {
		final VTStyle style = mock(VTStyle.class);
		final VTStyleSelector styleSelector = mock(VTStyleSelector.class);
		when(style.getSelector()).thenReturn(styleSelector);

		when(styleSelector.isApplicable(isNull(VElement.class), isNull(ViewModelContext.class))).thenReturn(
			VTStyleSelector.NOT_APPLICABLE);
		when(styleSelector.isApplicable(isNull(VElement.class), notNull(ViewModelContext.class))).thenReturn(
			VTStyleSelector.NOT_APPLICABLE);
		when(styleSelector.isApplicable(notNull(VElement.class), isNull(ViewModelContext.class))).thenReturn(
			VTStyleSelector.NOT_APPLICABLE);
		when(styleSelector.isApplicable(notNull(VElement.class), notNull(ViewModelContext.class))).thenReturn(
			specificity);

		final VTStyleProperty styleProperty = mock(VTStyleProperty.class);
		final EList<VTStyleProperty> properties = new BasicEList<VTStyleProperty>();
		properties.add(styleProperty);

		when(style.getProperties()).thenReturn(properties);

		return style;
	}

	private static List<VTViewTemplate> createListWithTwoTemplates() {
		final VTViewTemplate viewTemplate1 = mock(VTViewTemplate.class);
		final EList<VTStyle> styles1 = new BasicEList<VTStyle>();
		when(viewTemplate1.getStyles()).thenReturn(styles1);
		final VTViewTemplate viewTemplate2 = mock(VTViewTemplate.class);
		final EList<VTStyle> styles2 = new BasicEList<VTStyle>();
		when(viewTemplate2.getStyles()).thenReturn(styles2);

		final ArrayList<VTViewTemplate> list = new ArrayList<VTViewTemplate>();
		list.add(viewTemplate1);
		list.add(viewTemplate2);
		return list;
	}
}
