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
package org.eclipse.emf.ecp.view.spi.categorization.model.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationFactory;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.edit.command.CommandParameter;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link CategoryItemProvider}.
 *
 * @author Lucas Koehler
 *
 */
public class CategoryItemProvider_Test {

	private TestCategoryItemProvider provider;
	private ChildrenDescriptorCollector collector;
	private VElement parameterElement;

	@Before
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setUp() {
		provider = new TestCategoryItemProvider(mock(AdapterFactory.class));
		final Collection descriptors = new LinkedList();
		parameterElement = mock(VElement.class);
		final VAttachment attachment = mock(VAttachment.class);
		final CommandParameter parameter = new CommandParameter(null, VViewPackage.Literals.CONTAINER__CHILDREN,
			parameterElement);
		final CommandParameter filteredParameter = new CommandParameter(null,
			VViewPackage.Literals.ELEMENT__ATTACHMENTS, attachment);
		descriptors.add(parameter);
		descriptors.add(filteredParameter);
		collector = mock(ChildrenDescriptorCollector.class);
		when(collector.getDescriptors(any(EObject.class))).thenReturn(descriptors);
		provider.setChildrenDescriptorCollector(collector);

	}

	@Test
	public void testCollectNewChildDescriptors() {
		final List<Object> result = new LinkedList<Object>();
		final VCategory category = VCategorizationFactory.eINSTANCE.createCategory();
		provider.collectNewChildDescriptors(result, category);

		verify(collector).getDescriptors(isA(VContainer.class));
		assertTrue(result.size() >= 1);

		/*
		 * Verify that the result contains the command parameter retrieved by the collectContainerChildDecriptors().
		 * Cannot verify that #collectContainerChildDecriptors was called because using Mockito.spy does not work
		 * properly with the TestCategoryItemProvider.
		 */
		boolean resultContainsResult = false;
		for (final Object object : result) {
			if (object instanceof CommandParameter) {
				final CommandParameter p = (CommandParameter) object;
				if (parameterElement.equals(p.getValue())
					&& VCategorizationPackage.Literals.CATEGORY__COMPOSITE.equals(p.getFeature())) {
					resultContainsResult = true;
					break;
				}
			}
		}
		if (!resultContainsResult) {
			fail("The result did not contain the CommandParameter returned by #collectContainerChildDescriptors.");
		}
	}

	@Test
	public void testCollectContainerChildDecriptors() {
		final List<Object> result = new LinkedList<Object>();
		provider.collectContainerChildDecriptors(result, VCategorizationFactory.eINSTANCE.createCategory());

		// Verify that only a command parameter for VContainer's children reference was added and that it was correctly
		// adapted to the VCategory.
		assertEquals(1, result.size());
		assertTrue(result.get(0) instanceof CommandParameter);
		final CommandParameter p = (CommandParameter) result.get(0);
		assertEquals(parameterElement, p.getValue());
		assertEquals(VCategorizationPackage.Literals.CATEGORY__COMPOSITE, p.getFeature());
		// Need to get the child descriptors from the ChildrenDescriptorCollector for an implementation of VContainer.
		verify(collector).getDescriptors(isA(VContainer.class));

	}
}
