/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeContextFactory;
import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalFactory;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Gender;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.junit.Test;

/**
 * Diff Tests.
 * 
 * @author Eugen Neufeld
 * 
 */
public class DiffTests {

	@Test
	public void testSingleControlViewWithDiff() {

		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		vControl.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(vControl);

		final Player left = BowlingFactory.eINSTANCE.createPlayer();
		left.setName("a"); //$NON-NLS-1$
		final Player right = BowlingFactory.eINSTANCE.createPlayer();
		left.setName("b"); //$NON-NLS-1$
		final Player target = BowlingFactory.eINSTANCE.createPlayer();
		target.setName("c"); //$NON-NLS-1$

		final DiffMergeModelContext context = DiffMergeContextFactory.INSTANCE.createViewModelContext(view, target,
			left, right);
		assertEquals(1, context.getTotalNumberOfDiffs());
	}

	@Test
	public void testTableControlViewWithDiff() {

		final VView view = VViewFactory.eINSTANCE.createView();
		final VTableControl vControl = VTableFactory.eINSTANCE.createTableControl();
		final VTableDomainModelReference tableDomainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		tableDomainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getLeague_Players());
		vControl.setDomainModelReference(tableDomainModelReference);
		final VFeaturePathDomainModelReference col = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		col.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		VTableDomainModelReference.class.cast(vControl.getDomainModelReference()).getColumnDomainModelReferences()
			.add(col);
		view.getChildren().add(vControl);

		final League left = BowlingFactory.eINSTANCE.createLeague();
		final Player left1 = BowlingFactory.eINSTANCE.createPlayer();
		left1.setName("a"); //$NON-NLS-1$
		left.getPlayers().add(left1);
		final League right = BowlingFactory.eINSTANCE.createLeague();
		final Player right1 = BowlingFactory.eINSTANCE.createPlayer();
		left.setName("b"); //$NON-NLS-1$
		right.getPlayers().add(right1);
		final League target = BowlingFactory.eINSTANCE.createLeague();
		final Player target1 = BowlingFactory.eINSTANCE.createPlayer();
		target1.setName("c"); //$NON-NLS-1$
		target.getPlayers().add(target1);

		final DiffMergeModelContext context = DiffMergeContextFactory.INSTANCE.createViewModelContext(view, target,
			left, right);
		assertEquals(1, context.getTotalNumberOfDiffs());
	}

	@Test
	public void testSingleControlViewWithoutDiff() {

		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		vControl.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(vControl);

		final Player left = BowlingFactory.eINSTANCE.createPlayer();
		left.setName("a"); //$NON-NLS-1$
		final Player right = BowlingFactory.eINSTANCE.createPlayer();
		right.setName("a"); //$NON-NLS-1$
		final Player target = BowlingFactory.eINSTANCE.createPlayer();
		target.setName("b"); //$NON-NLS-1$

		final DiffMergeModelContext context = DiffMergeContextFactory.INSTANCE.createViewModelContext(view, target,
			left, right);
		assertEquals(0, context.getTotalNumberOfDiffs());
	}

	@Test
	public void testTableControlViewWithoutDiff() {

		final VView view = VViewFactory.eINSTANCE.createView();
		final VTableControl vControl = VTableFactory.eINSTANCE.createTableControl();
		final VTableDomainModelReference tableDomainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		tableDomainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getLeague_Players());
		vControl.setDomainModelReference(tableDomainModelReference);
		final VFeaturePathDomainModelReference col = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		col.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		VTableDomainModelReference.class.cast(vControl.getDomainModelReference()).getColumnDomainModelReferences()
			.add(col);
		view.getChildren().add(vControl);

		final League left = BowlingFactory.eINSTANCE.createLeague();
		final Player left1 = BowlingFactory.eINSTANCE.createPlayer();
		left1.setName("a"); //$NON-NLS-1$
		left.getPlayers().add(left1);
		final League right = BowlingFactory.eINSTANCE.createLeague();
		final Player right1 = BowlingFactory.eINSTANCE.createPlayer();
		right1.setName("a"); //$NON-NLS-1$
		right.getPlayers().add(right1);
		final League target = BowlingFactory.eINSTANCE.createLeague();
		final Player target1 = BowlingFactory.eINSTANCE.createPlayer();
		target1.setName("b"); //$NON-NLS-1$
		target.getPlayers().add(target1);

		final DiffMergeModelContext context = DiffMergeContextFactory.INSTANCE.createViewModelContext(view, target,
			left, right);
		assertEquals(0, context.getTotalNumberOfDiffs());
	}

	@Test
	public void testSingleControlContainedViewWithDiff() {

		final VView view = VViewFactory.eINSTANCE.createView();
		final VHorizontalLayout layout1 = VHorizontalFactory.eINSTANCE.createHorizontalLayout();
		final VHorizontalLayout layout2 = VHorizontalFactory.eINSTANCE.createHorizontalLayout();
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		vControl.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(layout1);
		layout1.getChildren().add(layout2);
		layout2.getChildren().add(vControl);

		final Player left = BowlingFactory.eINSTANCE.createPlayer();
		left.setName("a"); //$NON-NLS-1$
		final Player right = BowlingFactory.eINSTANCE.createPlayer();
		left.setName("b"); //$NON-NLS-1$
		final Player target = BowlingFactory.eINSTANCE.createPlayer();
		target.setName("c"); //$NON-NLS-1$

		final DiffMergeModelContext context = DiffMergeContextFactory.INSTANCE.createViewModelContext(view, target,
			left, right);
		assertEquals(1, context.getTotalNumberOfDiffs());
	}

	@Test
	public void testMultipleControlsWithAndWithoutDiff() {

		final VView view = VViewFactory.eINSTANCE.createView();

		final VControl vControlName = VViewFactory.eINSTANCE.createControl();
		vControlName.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(vControlName);

		final VControl vControlGender = VViewFactory.eINSTANCE.createControl();
		vControlGender.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Gender());
		view.getChildren().add(vControlGender);

		final VControl vControlHeight = VViewFactory.eINSTANCE.createControl();
		vControlHeight.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Height());
		view.getChildren().add(vControlHeight);

		final VControl vControlProfessional = VViewFactory.eINSTANCE.createControl();
		vControlProfessional.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_IsProfessional());
		view.getChildren().add(vControlProfessional);

		final VControl vControlNumberOfVictories = VViewFactory.eINSTANCE.createControl();
		vControlNumberOfVictories.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_NumberOfVictories());
		view.getChildren().add(vControlNumberOfVictories);

		final Player left = BowlingFactory.eINSTANCE.createPlayer();
		left.setName("a"); //$NON-NLS-1$
		left.setGender(Gender.MALE);
		left.setHeight(1);
		left.setIsProfessional(true);
		left.setNumberOfVictories(1);
		final Player right = BowlingFactory.eINSTANCE.createPlayer();
		right.setName("a"); //$NON-NLS-1$
		right.setGender(Gender.FEMALE);
		right.setHeight(2);
		right.setIsProfessional(false);
		right.setNumberOfVictories(1);
		final Player target = BowlingFactory.eINSTANCE.createPlayer();
		target.setName("b"); //$NON-NLS-1$
		target.setGender(Gender.MALE);
		target.setHeight(3);
		target.setIsProfessional(true);
		target.setNumberOfVictories(1);

		final DiffMergeModelContext context = DiffMergeContextFactory.INSTANCE.createViewModelContext(view, target,
			left, right);
		assertEquals(3, context.getTotalNumberOfDiffs());
	}
}
