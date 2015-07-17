package org.eclipse.emf.ecp.view.model.test;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.model.SettingPath;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.junit.Before;
import org.junit.Test;

public class DomainModelReference_Test {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testFeaturePathDomainModelReferencePathSimpleIterator() {
		final Fan createFan = BowlingFactory.eINSTANCE.createFan();
		createFan.setFavouriteMerchandise(BowlingFactory.eINSTANCE.createMerchandise());

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());
		domainModelReference.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise());

		domainModelReference.init(createFan);

		int pathCount = 0;
		final Iterator<SettingPath> fullPathIterator = domainModelReference.getFullPathIterator();
		while (fullPathIterator.hasNext()) {
			pathCount++;
			final SettingPath next = fullPathIterator.next();
			final Iterator<Setting> pathIterator = next.getPath();
			int pathSegments = 0;
			while (pathIterator.hasNext()) {
				pathSegments++;
				final Setting setting = pathIterator.next();
				if (pathSegments == 1) {
					assertEquals(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise(),
						setting.getEStructuralFeature());
					assertEquals(createFan, setting.getEObject());
				}
				if (pathSegments == 2) {
					assertEquals(BowlingPackage.eINSTANCE.getMerchandise_Name(), setting.getEStructuralFeature());
				}
			}
			assertEquals(2, pathSegments);
		}
		assertEquals(1, pathCount);
	}

	@Test
	public void testFeaturePathDomainModelReferenceSimpleIterator() {
		final Fan createFan = BowlingFactory.eINSTANCE.createFan();
		createFan.setFavouriteMerchandise(BowlingFactory.eINSTANCE.createMerchandise());

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());
		domainModelReference.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise());

		domainModelReference.init(createFan);

		final Iterator<Setting> iterator = domainModelReference.getIterator();
		int pathSegments = 0;
		while (iterator.hasNext()) {
			pathSegments++;
			final Setting setting = iterator.next();
			if (pathSegments == 1) {
				assertEquals(BowlingPackage.eINSTANCE.getMerchandise_Name(), setting.getEStructuralFeature());
			}
		}
		assertEquals(1, pathSegments);
	}

	@Test
	public void testFeaturePathDomainModelReferencePathWithNonUniqueReferences() {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		final EReference ref1 = EcoreFactory.eINSTANCE.createEReference();
		final EReference ref2 = EcoreFactory.eINSTANCE.createEReference();
		referencePath.add(ref1);
		referencePath.add(ref2);
		referencePath.add(ref1);
		final EReference feature = EcoreFactory.eINSTANCE.createEReference();
		control.setDomainModelReference(feature, referencePath);
		assertEquals(3, referencePath.size());
		assertEquals(3, ((VFeaturePathDomainModelReference) control.getDomainModelReference())
			.getDomainModelEReferencePath().size());

	}

}
