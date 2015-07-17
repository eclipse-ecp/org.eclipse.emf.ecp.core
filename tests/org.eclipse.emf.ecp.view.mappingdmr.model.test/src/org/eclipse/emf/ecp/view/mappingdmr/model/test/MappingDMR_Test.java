/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.mappingdmr.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.mapping.test.example.Child;
import org.eclipse.emf.ecp.view.mapping.test.example.Container;
import org.eclipse.emf.ecp.view.mapping.test.example.ExampleFactory;
import org.eclipse.emf.ecp.view.mapping.test.example.ExamplePackage;
import org.eclipse.emf.ecp.view.mapping.test.example.Intermediate;
import org.eclipse.emf.ecp.view.mapping.test.example.IntermediateTarget;
import org.eclipse.emf.ecp.view.mapping.test.example.Root;
import org.eclipse.emf.ecp.view.mapping.test.example.Target;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.junit.Test;

/**
 * @author jfaltermeier
 *
 */
public class MappingDMR_Test {

	private static final String NAME = "name"; //$NON-NLS-1$

	@Test
	public void testFullModel() {
		final Root root = ExampleFactory.eINSTANCE.createRoot();
		final Intermediate intermediate = ExampleFactory.eINSTANCE.createIntermediate();
		root.setIntermediate(intermediate);
		final Container container = ExampleFactory.eINSTANCE.createContainer();
		intermediate.setContainer(container);
		final Child child = ExampleFactory.eINSTANCE.createChild();
		container.getChildren().put(ExamplePackage.eINSTANCE.getChild(), child);
		final IntermediateTarget intermediateTarget = ExampleFactory.eINSTANCE.createIntermediateTarget();
		child.setIntermediateTarget(intermediateTarget);
		final Target target = ExampleFactory.eINSTANCE.createTarget();
		intermediateTarget.setTarget(target);
		target.setName(NAME);

		final VMappingDomainModelReference dmr = createDMR();

		assertTrue(dmr.init(root));
		final Iterator<Setting> iterator = dmr.getIterator();
		assertTrue(iterator.hasNext());
		assertEquals(NAME, iterator.next().get(true));
	}

	@Test
	public void testNoMapEntry() {
		final Root root = ExampleFactory.eINSTANCE.createRoot();
		final Intermediate intermediate = ExampleFactory.eINSTANCE.createIntermediate();
		root.setIntermediate(intermediate);
		final Container container = ExampleFactory.eINSTANCE.createContainer();
		intermediate.setContainer(container);

		final VMappingDomainModelReference dmr = createDMR();

		assertTrue(dmr.init(root));
		assertTrue(container.getChildren().containsKey(ExamplePackage.eINSTANCE.getChild()));
		assertSame(ExamplePackage.eINSTANCE.getChild(), container.getChildren()
			.get(ExamplePackage.eINSTANCE.getChild()).eClass());
		final Iterator<Setting> iterator = dmr.getIterator();
		assertTrue(iterator.hasNext());
		assertNull(iterator.next().get(true));
	}

	private VMappingDomainModelReference createDMR() {
		final VMappingDomainModelReference dmr = VMappingdmrFactory.eINSTANCE.createMappingDomainModelReference();
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getRoot_Intermediate());
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediate_Container());
		dmr.setDomainModelEFeature(ExamplePackage.eINSTANCE.getContainer_Children());

		dmr.setMappedClass(ExamplePackage.eINSTANCE.getChild());

		final VFeaturePathDomainModelReference targetDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		dmr.setDomainModelReference(targetDMR);
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_IntermediateTarget());
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediateTarget_Target());
		targetDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getTarget_Name());
		return dmr;
	}
}
