/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.integrationtest;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.spi.separator.model.VSeparatorPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.Test;

public class ChildrenDescriptorExtensionTest {

	/**
	 * Needs to be adapted after refactoring
	 * These are the counts for a fully integrated model with all elements
	 */
	// showrule, enablerule, span
	private static final int ATTACHMENT_CHILD_COUNT = 3;
	private static final int RENDERABLE_CHILD_COUNT = 0 + ATTACHMENT_CHILD_COUNT;
	private static final int COMPOSITE_CHILD_COUNT = RENDERABLE_CHILD_COUNT;
	private static final int ABSTRACTCATEGORIZATION_CHILD_COUNT = RENDERABLE_CHILD_COUNT + 1;
	private static final int NUMBER_OF_MAIN_COMPOSITES = 1;
	// label, separator, table, vertical, horizontal, group, groupedgrid, categorizationElement
	private static final int NUMBER_OF_EXTERNAL_COMPOSITES = 8;
	private static final int NUMBER_OF_COMPOSITES = NUMBER_OF_MAIN_COMPOSITES + NUMBER_OF_EXTERNAL_COMPOSITES;
	// categorization, category
	private static final int NUMBER_OF_CATEGORIZATIONS = 2;
	private static final int CATEGORIZATION_CHILD_COUNT = ABSTRACTCATEGORIZATION_CHILD_COUNT
		+ NUMBER_OF_CATEGORIZATIONS;
	private static final int COMPOSITECOLLECTION_CHILD_COUNT = COMPOSITE_CHILD_COUNT + NUMBER_OF_COMPOSITES;
	private static final int VIEW_CHILD_COUNT = NUMBER_OF_COMPOSITES + RENDERABLE_CHILD_COUNT;
	private static final int SHOWRULE_CHILD_COUNT = 3;
	private static final int ENABLERULE_CHILD_COUNT = 3;
	private static final int LEAFCONDITION_CHILD_COUNT = 1;
	private static final int ORCONDITION_CHILD_COUNT = 3;
	private static final int ANDCONDITION_CHILD_COUNT = 3;
	// TODO: Should be not -10, labels, groupedgrid, span, 2 rules, vertical, horizontal, separator,table, group
	// are missing
	// TODO: upper hierarchy is missing, can't find children
	private static final int CATEGORY_CHILD_COUNT = NUMBER_OF_COMPOSITES + ABSTRACTCATEGORIZATION_CHILD_COUNT - 10 + 3;
	// VDomainModelReference -> VFeaturePathDR, VPredefinedDR, VTableDR
	private static final int CONTROL_CHILD_COUNT = COMPOSITE_CHILD_COUNT + 3;
	private static final int TABLECONTROL_CHILD_COUNT = CONTROL_CHILD_COUNT + 1;
	private static final int TABLECOLUMN_CHILD_COUNT = 0;
	private static final int SEPARATOR_CHILD_COUNT = RENDERABLE_CHILD_COUNT;
	private static final int COLUMNCOMPOSITE_CHILD_COUNT = COMPOSITECOLLECTION_CHILD_COUNT;
	private static final int COLUMN_CHILD_COUNT = COMPOSITECOLLECTION_CHILD_COUNT;
	private static final int GROUP_CHILD_COUNT = COMPOSITECOLLECTION_CHILD_COUNT;
	private static final int ACTION_CHILD_COUNT = 0;
	private static final int GROUPEDGRID_CHILD_COUNT = RENDERABLE_CHILD_COUNT + 1;
	private static final int GRIDEDGROUP_CHILD_COUNT = 1;
	// TODO: Should be not -7, labels, group, vertical, horizontal, separator ,table,categorizationelement are missing
	// TODO: upper hierarchy is missing, can't find children
	private static final int GRIDEDGROUPROW_CHILD_COUNT = NUMBER_OF_COMPOSITES - 7;
	private static final int GRIDEDGROUPSPAN_CHILD_COUNT = 0;

	private final ChildrenDescriptorCollector descriptorCollector = new ChildrenDescriptorCollector();

	@Test
	public void testGroupedGridChildDescriptors() {
		final int size = getChildrenSize(VGroupedGridPackage.eINSTANCE.getGroupedGrid());
		assertEquals(GROUPEDGRID_CHILD_COUNT, size);
	}

	@Test
	public void testGridedGroupChildDescriptors() {
		final int size = getChildrenSize(VGroupedGridPackage.eINSTANCE.getGroup());
		assertEquals(GRIDEDGROUP_CHILD_COUNT, size);
	}

	@Test
	public void testGridedGroupRowChildDescriptors() {
		final int size = getChildrenSize(VGroupedGridPackage.eINSTANCE.getRow());
		assertEquals(GRIDEDGROUPROW_CHILD_COUNT, size);
	}

	@Test
	public void testGridedSpanChildDescriptors() {
		final int size = getChildrenSize(VGroupedGridPackage.eINSTANCE.getSpan());
		assertEquals(GRIDEDGROUPSPAN_CHILD_COUNT, size);
	}

	@Test
	public void testViewChildDescriptors() {
		final int size = getChildrenSize(VViewPackage.eINSTANCE.getView());
		assertEquals(VIEW_CHILD_COUNT, size);
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAbstractCategorizationChildDescriptors() {
		getChildrenSize(VCategorizationPackage.eINSTANCE.getAbstractCategorization());
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRuleChildDescriptors() {
		getChildrenSize(RulePackage.eINSTANCE.getRule());
	}

	@Test
	public void testShowRuleChildDescriptors() {
		final int size = getChildrenSize(RulePackage.eINSTANCE.getShowRule());
		assertEquals(SHOWRULE_CHILD_COUNT, size);
	}

	@Test
	public void testEnableRuleChildDescriptors() {
		final int size = getChildrenSize(RulePackage.eINSTANCE.getEnableRule());
		assertEquals(ENABLERULE_CHILD_COUNT, size);
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConditionChildDescriptors() {
		getChildrenSize(RulePackage.eINSTANCE.getCondition());
	}

	@Test
	public void testLeafConditionChildDescriptors() {
		final int size = getChildrenSize(RulePackage.eINSTANCE.getLeafCondition());
		assertEquals(LEAFCONDITION_CHILD_COUNT, size);
	}

	@Test
	public void testOrConditionChildDescriptors() {
		final int size = getChildrenSize(RulePackage.eINSTANCE.getOrCondition());
		assertEquals(ORCONDITION_CHILD_COUNT, size);
	}

	@Test
	public void testAndConditionChildDescriptors() {
		final int size = getChildrenSize(RulePackage.eINSTANCE.getAndCondition());
		assertEquals(ANDCONDITION_CHILD_COUNT, size);
	}

	@Test
	public void testCategorizationChildDescriptors() {
		final int size = getChildrenSize(VCategorizationPackage.eINSTANCE.getCategorization());
		assertEquals(CATEGORIZATION_CHILD_COUNT, size);
	}

	@Test
	public void testCategoryChildDescriptors() {
		final int size = getChildrenSize(VCategorizationPackage.eINSTANCE.getCategory());
		assertEquals(CATEGORY_CHILD_COUNT, size);
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompositeChildDescriptors() {
		getChildrenSize(VViewPackage.eINSTANCE.getContainedElement());
	}

	@Test
	public void testControlChildDescriptors() {
		final int size = getChildrenSize(VViewPackage.eINSTANCE.getControl());
		assertEquals(CONTROL_CHILD_COUNT, size);
	}

	@Test
	public void testTableControlChildDescriptors() {
		final int size = getChildrenSize(VTablePackage.eINSTANCE.getTableControl());
		assertEquals(TABLECONTROL_CHILD_COUNT, size);
	}

	@Test
	public void testTableColumnChildDescriptors() {
		final int size = getChildrenSize(VTablePackage.eINSTANCE.getTableColumn());
		assertEquals(TABLECOLUMN_CHILD_COUNT, size);
	}

	@Test
	public void testSeparatorChildDescriptors() {
		final int size = getChildrenSize(VSeparatorPackage.eINSTANCE.getSeparator());
		assertEquals(SEPARATOR_CHILD_COUNT, size);
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCompositeCollectionDescriptors() {
		getChildrenSize(VViewPackage.eINSTANCE.getContainer());
	}

	@Test
	public void testGroupDescriptors() {
		final int size = getChildrenSize(VGroupPackage.eINSTANCE.getGroup());
		assertEquals(GROUP_CHILD_COUNT, size);
	}

	/**
	 * Class is abstract, Exception expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRenderableDescriptors() {
		getChildrenSize(VViewPackage.eINSTANCE.getElement());
	}

	@Test
	public void testActionDescriptors() {
		final int size = getChildrenSize(VCategorizationPackage.eINSTANCE.getAction());
		assertEquals(ACTION_CHILD_COUNT, size);
	}

	/**
	 * @param category
	 * @return
	 */
	private int getChildrenSize(EClass eClass) {
		final EObject eObject = getEObjectWithResource(eClass);
		return descriptorCollector.getDescriptors(eObject).size();
	}

	private EObject getEObjectWithResource(EClass eClass) {
		final EObject eObject = EcoreUtil.create(eClass);
		final AdapterFactoryEditingDomain adapterFactoryEditingDomain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack());
		final ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();
		final Resource virtualResource = resourceSet.createResource(URI.createURI("VIRTUAL_URI"));
		virtualResource.getContents().add(eObject);
		return eObject;
	}

}
