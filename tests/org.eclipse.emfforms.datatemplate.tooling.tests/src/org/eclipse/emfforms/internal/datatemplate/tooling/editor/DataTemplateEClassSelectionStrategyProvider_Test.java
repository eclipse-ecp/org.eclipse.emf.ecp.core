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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskPackage;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emfforms.datatemplate.DataTemplatePackage;
import org.junit.Before;
import org.junit.Test;

public class DataTemplateEClassSelectionStrategyProvider_Test {

	private DataTemplateEClassSelectionStrategyProvider provider;

	@Before
	public void setUp() throws Exception {
		provider = new DataTemplateEClassSelectionStrategyProvider();
		final Registry registry = EPackage.Registry.INSTANCE;
		registry.put(TaskPackage.eNS_URI, TaskPackage.eINSTANCE);
	}

	@Test
	public void testBid() {
		assertNull(provider.bid(null));
		assertNull(provider.bid(EcorePackage.eINSTANCE.getEAnnotation_References()));
		assertEquals(10, provider.bid(DataTemplatePackage.eINSTANCE.getTemplate_Instance()), 0);
	}

	@Test
	public void testCreate() {
		final EClassSelectionStrategy strategy = provider.create();
		assertNotNull(strategy);
		final Collection<EClass> collectEClasses = strategy.collectEClasses(null,
			DataTemplatePackage.eINSTANCE.getTemplate_Instance(), null);
		final Collection<EClass> allFromRegistry = collectAllFromRegistry();
		assertEquals(allFromRegistry.size(), collectEClasses.size());
		for (final EClass eClass : allFromRegistry) {
			assertTrue(collectEClasses.contains(eClass));
		}
	}

	private Collection<EClass> collectAllFromRegistry() {
		final Registry registry = EPackage.Registry.INSTANCE;
		final Set<EClass> result = new LinkedHashSet<EClass>();
		for (final String uri : registry.keySet()) {
			final EPackage ePackage = registry.getEPackage(uri);
			for (final EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass && !((EClass) eClassifier).isAbstract()
					&& !((EClass) eClassifier).isInterface()) {
					result.add((EClass) eClassifier);
				}
			}
		}
		return result;
	}

}
