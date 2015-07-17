/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.indexdmr.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.index.test.example.Child;
import org.eclipse.emf.ecp.view.index.test.example.Container;
import org.eclipse.emf.ecp.view.index.test.example.ExampleFactory;
import org.eclipse.emf.ecp.view.index.test.example.ExamplePackage;
import org.eclipse.emf.ecp.view.index.test.example.Intermediate;
import org.eclipse.emf.ecp.view.index.test.example.IntermediateTarget;
import org.eclipse.emf.ecp.view.index.test.example.Root;
import org.eclipse.emf.ecp.view.index.test.example.Target;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.junit.Test;

/**
 * Test for Index DMR.
 *
 */
public class IndexDMR_Test {

	/**
	 * Test a full model to get index 0.
	 */
	@Test
	public void testFullModelIndex0() {
		final Root root = ExampleFactory.eINSTANCE.createRoot();
		final Intermediate intermediate = ExampleFactory.eINSTANCE.createIntermediate();
		final Container container = ExampleFactory.eINSTANCE.createContainer();

		final Child child1 = ExampleFactory.eINSTANCE.createChild();
		final Child child2 = ExampleFactory.eINSTANCE.createChild();

		root.setIntermediate(intermediate);
		intermediate.setContainer(container);
		container.getChildren().add(child1);
		container.getChildren().add(child2);

		// child1
		final IntermediateTarget intermediateTarget1 = ExampleFactory.eINSTANCE.createIntermediateTarget();
		child1.setIntermediateTarget(intermediateTarget1);
		final Target target1 = ExampleFactory.eINSTANCE.createTarget();
		intermediateTarget1.setTarget(target1);
		target1.setName("name1"); //$NON-NLS-1$

		// child2
		final IntermediateTarget intermediateTarget2 = ExampleFactory.eINSTANCE.createIntermediateTarget();
		child2.setIntermediateTarget(intermediateTarget2);
		final Target target2 = ExampleFactory.eINSTANCE.createTarget();
		intermediateTarget2.setTarget(target2);
		target2.setName("name2"); //$NON-NLS-1$

		// Set DMR
		final VIndexDomainModelReference dmr = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReference();

		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getRoot_Intermediate());
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediate_Container());
		dmr.setDomainModelEFeature(ExamplePackage.eINSTANCE.getContainer_Children());

		final VFeaturePathDomainModelReference targetDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_IntermediateTarget());
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediateTarget_Target());
		targetDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getTarget_Name());
		dmr.setTargetDMR(targetDMR);

		assertTrue(dmr.init(root));

		assertEquals("name1", dmr.getIterator().next().get(true)); //$NON-NLS-1$
	}

	/**
	 * Test on full model with index 1.
	 */
	@Test
	public void testFullModelIndex1() {
		final Root root = ExampleFactory.eINSTANCE.createRoot();
		final Intermediate intermediate = ExampleFactory.eINSTANCE.createIntermediate();
		final Container container = ExampleFactory.eINSTANCE.createContainer();

		final Child child1 = ExampleFactory.eINSTANCE.createChild();
		final Child child2 = ExampleFactory.eINSTANCE.createChild();

		root.setIntermediate(intermediate);
		intermediate.setContainer(container);
		container.getChildren().add(child1);
		container.getChildren().add(child2);

		// child1
		final IntermediateTarget intermediateTarget1 = ExampleFactory.eINSTANCE.createIntermediateTarget();
		child1.setIntermediateTarget(intermediateTarget1);
		final Target target1 = ExampleFactory.eINSTANCE.createTarget();
		intermediateTarget1.setTarget(target1);
		target1.setName("name1"); //$NON-NLS-1$

		// child2
		final IntermediateTarget intermediateTarget2 = ExampleFactory.eINSTANCE.createIntermediateTarget();
		child2.setIntermediateTarget(intermediateTarget2);
		final Target target2 = ExampleFactory.eINSTANCE.createTarget();
		intermediateTarget2.setTarget(target2);
		target2.setName("name2"); //$NON-NLS-1$

		// Set DMR
		final VIndexDomainModelReference dmr = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReference();
		dmr.setIndex(1);

		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getRoot_Intermediate());
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediate_Container());
		dmr.setDomainModelEFeature(ExamplePackage.eINSTANCE.getContainer_Children());

		final VFeaturePathDomainModelReference targetDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_IntermediateTarget());
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediateTarget_Target());
		targetDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getTarget_Name());
		dmr.setTargetDMR(targetDMR);

		assertTrue(dmr.init(root));

		assertEquals("name2", dmr.getIterator().next().get(true)); //$NON-NLS-1$
	}

	/**
	 * Test on model without children and index 0.
	 */
	@Test
	public void testNoChildren() {
		final Root root = ExampleFactory.eINSTANCE.createRoot();
		final Intermediate intermediate = ExampleFactory.eINSTANCE.createIntermediate();
		final Container container = ExampleFactory.eINSTANCE.createContainer();

		root.setIntermediate(intermediate);
		intermediate.setContainer(container);

		// Set DMR
		final VIndexDomainModelReference dmr = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReference();

		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getRoot_Intermediate());
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediate_Container());
		dmr.setDomainModelEFeature(ExamplePackage.eINSTANCE.getContainer_Children());

		final VFeaturePathDomainModelReference targetDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_IntermediateTarget());
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediateTarget_Target());
		targetDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getTarget_Name());
		dmr.setTargetDMR(targetDMR);

		assertTrue(dmr.init(root));

		assertEquals(1, container.getChildren().size());
	}

	@Test
	public void testNoChildrenIndex1() {
		final Root root = ExampleFactory.eINSTANCE.createRoot();
		final Intermediate intermediate = ExampleFactory.eINSTANCE.createIntermediate();
		final Container container = ExampleFactory.eINSTANCE.createContainer();

		root.setIntermediate(intermediate);
		intermediate.setContainer(container);

		// Set DMR
		final VIndexDomainModelReference dmr = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReference();
		dmr.setIndex(1);
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getRoot_Intermediate());
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediate_Container());
		dmr.setDomainModelEFeature(ExamplePackage.eINSTANCE.getContainer_Children());

		final VFeaturePathDomainModelReference targetDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_IntermediateTarget());
		targetDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediateTarget_Target());
		targetDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getTarget_Name());
		dmr.setTargetDMR(targetDMR);

		assertFalse(dmr.init(root));

	}
}
