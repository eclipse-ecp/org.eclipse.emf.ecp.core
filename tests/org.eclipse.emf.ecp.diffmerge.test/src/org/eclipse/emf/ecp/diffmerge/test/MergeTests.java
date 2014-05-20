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
package org.eclipse.emf.ecp.diffmerge.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.diffmerge.spi.context.DefaultMergeUtil;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.eclipse.emf.emfstore.bowling.Player;
import org.junit.Test;

/**
 * Merge Tests.
 * 
 * @author Eugen Neufeld
 * 
 */
public class MergeTests {

	private void addEobjectToResource(EObject eObject) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), resourceSet);
		resourceSet.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = resourceSet.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
		resource.getContents().add(eObject);
	}

	@Test
	public void testMergeSingleAttribute() {

		final VControl controlToCopyFrom = VViewFactory.eINSTANCE.createControl();
		final VControl controlToCopyTo = VViewFactory.eINSTANCE.createControl();

		final Player player1 = BowlingFactory.eINSTANCE.createPlayer();
		addEobjectToResource(player1);
		player1.setName("a"); //$NON-NLS-1$
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		controlToCopyFrom.setDomainModelReference(dmr);

		dmr.init(player1);

		final Player player2 = BowlingFactory.eINSTANCE.createPlayer();
		addEobjectToResource(player2);
		player2.setName("b"); //$NON-NLS-1$
		final VFeaturePathDomainModelReference dmr2 = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr2.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		controlToCopyTo.setDomainModelReference(dmr2);
		dmr2.init(player2);

		DefaultMergeUtil.copyValues(controlToCopyFrom, controlToCopyTo);

		assertEquals("a", player2.getName()); //$NON-NLS-1$
	}

	@Test
	public void testMergeMultiAttribute() {
		final VControl controlToCopyFrom = VViewFactory.eINSTANCE.createControl();
		final VControl controlToCopyTo = VViewFactory.eINSTANCE.createControl();

		final Player player1 = BowlingFactory.eINSTANCE.createPlayer();
		addEobjectToResource(player1);
		player1.getEMails().add("a"); //$NON-NLS-1$
		player1.getEMails().add("b"); //$NON-NLS-1$
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_EMails());
		controlToCopyFrom.setDomainModelReference(dmr);
		dmr.init(player1);

		final Player player2 = BowlingFactory.eINSTANCE.createPlayer();
		addEobjectToResource(player2);
		player2.getEMails().add("1"); //$NON-NLS-1$
		player2.getEMails().add("2"); //$NON-NLS-1$
		final VFeaturePathDomainModelReference dmr2 = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr2.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_EMails());
		controlToCopyTo.setDomainModelReference(dmr2);
		dmr2.init(player2);

		DefaultMergeUtil.copyValues(controlToCopyFrom, controlToCopyTo);

		assertEquals(2, player2.getEMails().size());
		assertEquals("a", player2.getEMails().get(0)); //$NON-NLS-1$
		assertEquals("b", player2.getEMails().get(1)); //$NON-NLS-1$

	}

	@Test
	public void testMergeSingleContainmentReference() {
		final VControl controlToCopyFrom = VViewFactory.eINSTANCE.createControl();
		final VControl controlToCopyTo = VViewFactory.eINSTANCE.createControl();

		final Fan fan1 = BowlingFactory.eINSTANCE.createFan();
		fan1.setFavouriteMerchandise(BowlingFactory.eINSTANCE.createMerchandise());
		fan1.getFavouriteMerchandise().setName("a"); //$NON-NLS-1$
		addEobjectToResource(fan1);
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());
		dmr.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise());
		controlToCopyFrom.setDomainModelReference(dmr);
		dmr.init(fan1);

		final Fan fan2 = BowlingFactory.eINSTANCE.createFan();
		fan2.setFavouriteMerchandise(BowlingFactory.eINSTANCE.createMerchandise());
		fan2.getFavouriteMerchandise().setName("b"); //$NON-NLS-1$
		addEobjectToResource(fan2);
		final VFeaturePathDomainModelReference dmr2 = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr2.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());
		dmr2.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise());
		controlToCopyTo.setDomainModelReference(dmr2);
		dmr2.init(fan2);

		DefaultMergeUtil.copyValues(controlToCopyFrom, controlToCopyTo);

		assertNotEquals(fan1.getFavouriteMerchandise(), fan2.getFavouriteMerchandise());
		assertEquals("a", fan2.getFavouriteMerchandise().getName()); //$NON-NLS-1$
	}

	@Test
	public void testMergeMultiContainmentReference() {
		final VTableControl controlToCopyFrom = VTableFactory.eINSTANCE.createTableControl();
		final VTableControl controlToCopyTo = VTableFactory.eINSTANCE.createTableControl();
		final VFeaturePathDomainModelReference vcFrom = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VFeaturePathDomainModelReference vcTo = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		vcFrom.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());
		vcTo.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());

		final Fan fan1 = BowlingFactory.eINSTANCE.createFan();
		{
			final Merchandise merchandise1 = BowlingFactory.eINSTANCE.createMerchandise();
			fan1.getFanMerchandise().add(merchandise1);
			merchandise1.setName("a"); //$NON-NLS-1$
			final Merchandise merchandise2 = BowlingFactory.eINSTANCE.createMerchandise();
			fan1.getFanMerchandise().add(merchandise2);
			merchandise2.setName("b"); //$NON-NLS-1$
			addEobjectToResource(fan1);
			final VTableDomainModelReference dmr = VTableFactory.eINSTANCE
				.createTableDomainModelReference();
			dmr.setDomainModelEFeature(BowlingPackage.eINSTANCE.getFan_FanMerchandise());
			controlToCopyFrom.setDomainModelReference(dmr);
			dmr.init(fan1);
		}

		final Fan fan2 = BowlingFactory.eINSTANCE.createFan();
		{
			final Merchandise merchandise1 = BowlingFactory.eINSTANCE.createMerchandise();
			fan2.getFanMerchandise().add(merchandise1);
			merchandise1.setName("1"); //$NON-NLS-1$
			final Merchandise merchandise2 = BowlingFactory.eINSTANCE.createMerchandise();
			fan2.getFanMerchandise().add(merchandise2);
			merchandise2.setName("2"); //$NON-NLS-1$
			addEobjectToResource(fan2);
			final VTableDomainModelReference dmr2 = VTableFactory.eINSTANCE
				.createTableDomainModelReference();
			dmr2.setDomainModelEFeature(BowlingPackage.eINSTANCE.getFan_FanMerchandise());
			controlToCopyTo.setDomainModelReference(dmr2);
			dmr2.init(fan2);
		}

		DefaultMergeUtil.copyValues(controlToCopyFrom, controlToCopyTo);

		assertEquals(fan1.getFanMerchandise().size(), fan2.getFanMerchandise().size());
		assertNotEquals(fan1.getFanMerchandise().get(0), fan2.getFanMerchandise().get(0));
		assertNotEquals(fan1.getFanMerchandise().get(1), fan2.getFanMerchandise().get(1));
		assertEquals("a", fan2.getFanMerchandise().get(0).getName()); //$NON-NLS-1$
		assertEquals("b", fan2.getFanMerchandise().get(1).getName()); //$NON-NLS-1$
	}

	@Test
	public void testMergeSingleNOTContainmentReference() {
		/*
		 * model:
		 * a
		 * |-b1 ref c1
		 * |-b2 ref c2
		 * |-c1
		 * |-c2
		 * a'
		 * |-b1' ref c1'
		 * |-b2' ref c2'
		 * |-c1'
		 * |-c2'
		 * what should a merge do? how do you identify the object to set the reference to?
		 */
	}

	@Test
	public void testMergeMultiNOTContainmentReference() {
		/*
		 * model:
		 * a
		 * |-b1 ref c1,c2
		 * |-c1
		 * |-c2
		 * a'
		 * |-b1' ref c1',c2'
		 * |-c1'
		 * |-c2'
		 * what should a merge do? how do you identify the object to set the reference to?
		 */
	}

}
