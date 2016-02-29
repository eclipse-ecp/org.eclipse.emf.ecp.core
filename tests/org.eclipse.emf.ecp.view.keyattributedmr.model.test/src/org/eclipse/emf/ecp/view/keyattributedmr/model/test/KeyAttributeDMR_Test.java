package org.eclipse.emf.ecp.view.keyattributedmr.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.keyattribute.test.example.Child;
import org.eclipse.emf.ecp.view.keyattribute.test.example.Container;
import org.eclipse.emf.ecp.view.keyattribute.test.example.ExampleFactory;
import org.eclipse.emf.ecp.view.keyattribute.test.example.ExamplePackage;
import org.eclipse.emf.ecp.view.keyattribute.test.example.Intermediate;
import org.eclipse.emf.ecp.view.keyattribute.test.example.IntermediateTarget;
import org.eclipse.emf.ecp.view.keyattribute.test.example.KeyContainer;
import org.eclipse.emf.ecp.view.keyattribute.test.example.Root;
import org.eclipse.emf.ecp.view.keyattribute.test.example.Target;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.junit.Before;
import org.junit.Test;

public class KeyAttributeDMR_Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFullModel() {
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
		final KeyContainer keyContainer1 = ExampleFactory.eINSTANCE.createKeyContainer();
		keyContainer1.setKey("key1"); //$NON-NLS-1$
		child1.setKey(keyContainer1);
		final IntermediateTarget intermediateTarget1 = ExampleFactory.eINSTANCE.createIntermediateTarget();
		child1.setIntermediateTarget(intermediateTarget1);
		final Target target1 = ExampleFactory.eINSTANCE.createTarget();
		intermediateTarget1.setTarget(target1);
		target1.setName("name1"); //$NON-NLS-1$

		// child2
		final KeyContainer keyContainer2 = ExampleFactory.eINSTANCE.createKeyContainer();
		keyContainer2.setKey("key2"); //$NON-NLS-1$
		child2.setKey(keyContainer2);
		final IntermediateTarget intermediateTarget2 = ExampleFactory.eINSTANCE.createIntermediateTarget();
		child2.setIntermediateTarget(intermediateTarget2);
		final Target target2 = ExampleFactory.eINSTANCE.createTarget();
		intermediateTarget2.setTarget(target2);
		target2.setName("name2"); //$NON-NLS-1$

		// Set DMR
		final VKeyAttributeDomainModelReference dmr = VKeyattributedmrFactory.eINSTANCE
			.createKeyAttributeDomainModelReference();

		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getRoot_Intermediate());
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediate_Container());
		dmr.setDomainModelEFeature(ExamplePackage.eINSTANCE.getContainer_Children());

		final VFeaturePathDomainModelReference keyDMR = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		keyDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_Key());
		keyDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getKeyContainer_Key());

		dmr.setKeyDMR(keyDMR);
		dmr.setKeyValue("key2"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference valueDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		valueDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_IntermediateTarget());
		valueDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediateTarget_Target());
		valueDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getTarget_Name());
		dmr.setValueDMR(valueDMR);

		assertTrue(dmr.init(root));

		assertEquals("name2", dmr.getIterator().next().get(true)); //$NON-NLS-1$
	}

	@Test
	public void testNoChildren() {
		final Root root = ExampleFactory.eINSTANCE.createRoot();
		final Intermediate intermediate = ExampleFactory.eINSTANCE.createIntermediate();
		final Container container = ExampleFactory.eINSTANCE.createContainer();

		root.setIntermediate(intermediate);
		intermediate.setContainer(container);

		// Set DMR
		final VKeyAttributeDomainModelReference dmr = VKeyattributedmrFactory.eINSTANCE
			.createKeyAttributeDomainModelReference();

		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getRoot_Intermediate());
		dmr.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediate_Container());
		dmr.setDomainModelEFeature(ExamplePackage.eINSTANCE.getContainer_Children());

		final VFeaturePathDomainModelReference keyDMR = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		keyDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_Key());
		keyDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getKeyContainer_Key());

		dmr.setKeyDMR(keyDMR);
		dmr.setKeyValue("key"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference valueDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		valueDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getChild_IntermediateTarget());
		valueDMR.getDomainModelEReferencePath().add(ExamplePackage.eINSTANCE.getIntermediateTarget_Target());
		valueDMR.setDomainModelEFeature(ExamplePackage.eINSTANCE.getTarget_Name());
		dmr.setValueDMR(valueDMR);

		assertTrue(dmr.init(root));

		assertEquals(1, container.getChildren().size());
		final Child child = container.getChildren().get(0);
		assertEquals("key", child.getKey().getKey()); //$NON-NLS-1$
	}
}
