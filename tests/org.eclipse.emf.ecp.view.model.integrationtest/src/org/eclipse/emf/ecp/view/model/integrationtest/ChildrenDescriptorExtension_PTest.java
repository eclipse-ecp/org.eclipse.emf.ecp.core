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
 * Jonas - initial API and implementation
 * Christian W. Damus - bug 530900
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.integrationtest;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolPackage;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalPackage;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalPackage;
import org.eclipse.emf.ecp.view.spi.viewproxy.model.VViewproxyPackage;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailPackage;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

@SuppressWarnings("restriction")
public class ChildrenDescriptorExtension_PTest {

	/**
	 * Needs to be adapted after refactoring
	 * These are the counts for a fully integrated model with all elements
	 */
	// showrule, enablerule, span, datetimedisplay
	private static final int ATTACHMENT_CHILD_COUNT = 4;
	private static final int RENDERABLE_CHILD_COUNT = 0 + ATTACHMENT_CHILD_COUNT;
	private static final int COMPOSITE_CHILD_COUNT = RENDERABLE_CHILD_COUNT;
	private static final int ABSTRACTCATEGORIZATION_CHILD_COUNT = RENDERABLE_CHILD_COUNT + 1;
	private static final int NUMBER_OF_MAIN_COMPOSITES = 1;
	// label, table, vertical, horizontal, group, groupedgrid, categorizationElement, custom control, section,
	// stack,viewproxy, compoundcontrol, treemasterdetail
	private static final int NUMBER_OF_EXTERNAL_COMPOSITES = 13;
	// DMRs should not be creatable via child extender. Existing Dmrs: DMR, VFeaturePathDR, VPredefinedDR,
	// VTableDR,KEYDMR, MAPPINGDMR,IndexDMR
	private static final int DOMAINMODELREFERENCE_COUNT = 0;
	private static final int NUMBER_OF_COMPOSITES = NUMBER_OF_MAIN_COMPOSITES + NUMBER_OF_EXTERNAL_COMPOSITES;
	// categorization, category
	private static final int NUMBER_OF_CATEGORIZATIONS = 2;
	private static final int CATEGORIZATION_CHILD_COUNT = ABSTRACTCATEGORIZATION_CHILD_COUNT
		+ NUMBER_OF_CATEGORIZATIONS;
	private static final int COMPOSITECOLLECTION_CHILD_COUNT = COMPOSITE_CHILD_COUNT + NUMBER_OF_COMPOSITES;
	private static final int VIEW_CHILD_COUNT = NUMBER_OF_COMPOSITES + RENDERABLE_CHILD_COUNT;
	// Neither the main dmr nor the value dmr should be returned as children
	private static final int LEAFCONDITION_CHILD_COUNT = 0;
	private static final int CATEGORY_CHILD_COUNT = NUMBER_OF_COMPOSITES + ABSTRACTCATEGORIZATION_CHILD_COUNT;

	private static final int CONTROL_CHILD_COUNT = COMPOSITE_CHILD_COUNT + DOMAINMODELREFERENCE_COUNT;
	private static final int TABLECONTROL_CHILD_COUNT = CONTROL_CHILD_COUNT + 3;
	private static final int ACTION_CHILD_COUNT = 0;
	private static final int GROUPEDGRID_CHILD_COUNT = RENDERABLE_CHILD_COUNT + 1;
	private static final int GRIDEDGROUP_CHILD_COUNT = 1;
	// TODO: Should be not - NUMBER_OF_EXTERNAL_COMPOSITES are missing
	// TODO: upper hierarchy is missing, can't find children
	private static final int GRIDEDGROUPROW_CHILD_COUNT = NUMBER_OF_COMPOSITES - NUMBER_OF_EXTERNAL_COMPOSITES + 1;
	private static final int GRIDEDGROUPSPAN_CHILD_COUNT = 0;

	private static final ChildrenDescriptorCollector DESCRIPTOR_COLLECTOR = new ChildrenDescriptorCollector();

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
		assertThat(getChildDescriptors(RulePackage.Literals.SHOW_RULE),
			hasItem(isChildDescriptor(RulePackage.Literals.IS_PROXY_CONDITION)));
	}

	@Test
	public void testEnableRuleChildDescriptors() {
		assertThat(getChildDescriptors(RulePackage.Literals.ENABLE_RULE),
			hasItem(isChildDescriptor(RulePackage.Literals.IS_PROXY_CONDITION)));
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
		assertThat(getChildDescriptors(RulePackage.Literals.OR_CONDITION),
			hasItem(isChildDescriptor(RulePackage.Literals.IS_PROXY_CONDITION)));
	}

	@Test
	public void testAndConditionChildDescriptors() {
		assertThat(getChildDescriptors(RulePackage.Literals.AND_CONDITION),
			hasItem(isChildDescriptor(RulePackage.Literals.IS_PROXY_CONDITION)));
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
		assertEquals(COMPOSITECOLLECTION_CHILD_COUNT, size);
	}

	@Test
	public void testHorizontalDescriptors() {
		final int size = getChildrenSize(VHorizontalPackage.eINSTANCE.getHorizontalLayout());
		assertEquals(COMPOSITECOLLECTION_CHILD_COUNT, size);
	}

	@Test
	public void testVerticalDescriptors() {
		final int size = getChildrenSize(VVerticalPackage.eINSTANCE.getVerticalLayout());
		assertEquals(COMPOSITECOLLECTION_CHILD_COUNT, size);
	}

	@Test
	public void testLabelDescriptors() {
		final int size = getChildrenSize(VLabelPackage.eINSTANCE.getLabel());
		assertEquals(ATTACHMENT_CHILD_COUNT, size);
	}

	@Test
	public void testCustomControlDescriptors() {
		final int size = getChildrenSize(VCustomPackage.eINSTANCE.getCustomControl());
		assertEquals(CONTROL_CHILD_COUNT, size);
	}

	@Test
	public void testSectionDescriptors() {
		final int size = getChildrenSize(VSectionPackage.eINSTANCE.getSection());
		assertEquals(COMPOSITECOLLECTION_CHILD_COUNT, size);
	}

	@Test
	public void testSectionAreaDescriptors() {
		final int size = getChildrenSize(VSectionPackage.eINSTANCE.getSectionedArea());
		// +1 because of section
		assertEquals(ATTACHMENT_CHILD_COUNT + 1, size);
	}

	@Test
	public void testStackItemDescriptors() {
		final int size = getChildrenSize(VStackPackage.eINSTANCE.getStackItem());
		// -1 because of treemasterdetail as it only can be added to containedContainer
		assertEquals(COMPOSITECOLLECTION_CHILD_COUNT - 1, size);
	}

	@Test
	public void testStackLayoutDescriptors() {
		final int size = getChildrenSize(VStackPackage.eINSTANCE.getStackLayout());
		// +1 for stack items
		assertEquals(ATTACHMENT_CHILD_COUNT + 1, size);
	}

	@Test
	public void testViewProxyDescriptors() {
		final int size = getChildrenSize(VViewproxyPackage.eINSTANCE.getViewProxy());
		assertEquals(ATTACHMENT_CHILD_COUNT, size);
	}

	@Test
	public void testCompoundControlDescriptors() {
		final int size = getChildrenSize(VCompoundcontrolPackage.eINSTANCE.getCompoundControl());
		// +1 because of vcontrol TODO table and custom control is missing
		assertEquals(ATTACHMENT_CHILD_COUNT + DOMAINMODELREFERENCE_COUNT + 1, size);
	}

	@Test
	public void testTreeMasterDetailDescriptors() {
		final int size = getChildrenSize(VTreeMasterDetailPackage.eINSTANCE.getTreeMasterDetail());
		// +1 because of VView
		assertEquals(ATTACHMENT_CHILD_COUNT + 1, size);
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
	static int getChildrenSize(EClass eClass) {
		final EObject eObject = getEObjectWithResource(eClass);
		return DESCRIPTOR_COLLECTOR.getDescriptors(eObject).size();
	}

	static Collection<CommandParameter> getChildDescriptors(EClass eClass) {
		final EObject eObject = getEObjectWithResource(eClass);
		final Collection<?> collected = DESCRIPTOR_COLLECTOR.getDescriptors(eObject);
		final List<CommandParameter> result = new ArrayList<CommandParameter>(collected.size());
		for (final Object next : collected) {
			if (next instanceof CommandParameter) {
				result.add((CommandParameter) next);
			}
		}
		return result;
	}

	private static EObject getEObjectWithResource(EClass eClass) {
		final EObject eObject = EcoreUtil.create(eClass);
		final AdapterFactoryEditingDomain adapterFactoryEditingDomain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack());
		final ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();
		final Resource virtualResource = resourceSet.createResource(URI.createURI("VIRTUAL_URI"));
		virtualResource.getContents().add(eObject);
		return eObject;
	}

	/**
	 * Obtain a matcher for {@link CommandParameter}s creating children of the given class.
	 *
	 * @param childClass the child class to match
	 * @return the matcher
	 */
	Matcher<CommandParameter> isChildDescriptor(final EClass childClass) {
		return new CustomTypeSafeMatcher<CommandParameter>("child of type " + childClass.getName()) {
			@Override
			public boolean matchesSafely(CommandParameter item) {
				return childClass.isInstance(item.getEValue());
			}
		};
	}
}
