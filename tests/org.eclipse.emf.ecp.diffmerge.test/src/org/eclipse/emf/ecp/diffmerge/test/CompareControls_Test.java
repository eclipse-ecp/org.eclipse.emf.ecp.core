/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.diffmerge.internal.context.CompareControls;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Player;
import org.junit.Test;

/**
 * @author Jonas Helming
 *
 */
public class CompareControls_Test {

	private static final String EMPTY = ""; //$NON-NLS-1$
	private static final String STRING1 = "String1"; //$NON-NLS-1$

	@Test
	public void testValuesAreEqualNullValues() {
		final EAttribute eAttribute = BowlingPackage.eINSTANCE.getPlayer_Name();
		final boolean result = CompareControls.areValuesEqual(null, eAttribute, null, eAttribute);
		assertTrue(result);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emf.ecp.diffmerge.internal.context.CompareControls#areValuesEqual(java.lang.Object, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, org.eclipse.emf.ecore.EStructuralFeature)}.
	 */
	@Test
	public void testValuesAreEqualAllNull() {
		final boolean result = CompareControls.areValuesEqual(null, null, null, null);
		assertTrue(result);
	}

	@Test
	public void testValuesAreEqualFirstValueNull() {
		final boolean result = CompareControls.areValuesEqual(null, null, EMPTY, null);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualSecondValueNull() {
		final boolean result = CompareControls.areValuesEqual(EMPTY, null, null, null);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualSimpleValuesEqual() {
		final boolean result = CompareControls.areValuesEqual(EMPTY, null, EMPTY, null);
		assertTrue(result);
	}

	@Test
	public void testValuesAreEqualSimpleValuesNotEqual() {
		final EAttribute eAttribute = BowlingPackage.eINSTANCE.getPlayer_Name();
		final boolean result = CompareControls.areValuesEqual(EMPTY, eAttribute, STRING1, eAttribute);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualSingleReferenceEqual() {
		final EReference eReference = BowlingPackage.eINSTANCE.getGame_Player();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		player.setName(STRING1);
		final Player copy = EcoreUtil.copy(player);
		final boolean result = CompareControls.areValuesEqual(player, eReference, copy, eReference);
		assertTrue(result);
	}

	@Test
	public void testValuesAreEqualSingleReferenceNotEqual() {
		final EReference eReference = BowlingPackage.eINSTANCE.getGame_Player();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		final Player copy = EcoreUtil.copy(player);
		player.setName(STRING1);
		final boolean result = CompareControls.areValuesEqual(player, eReference, copy, eReference);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualSingleReferenceWithMulti() {
		final EReference eReference = BowlingPackage.eINSTANCE.getGame_Player();
		final EReference eReferenceMulti = BowlingPackage.eINSTANCE.getMatchup_Games();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		final Player copy = EcoreUtil.copy(player);
		player.setName(STRING1);
		final boolean result = CompareControls.areValuesEqual(player, eReference, copy, eReferenceMulti);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualSingleReferenceWithMultiTwisted() {
		final EReference eReference = BowlingPackage.eINSTANCE.getGame_Player();
		final EReference eReferenceMulti = BowlingPackage.eINSTANCE.getMatchup_Games();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		final Player copy = EcoreUtil.copy(player);
		player.setName(STRING1);
		final boolean result = CompareControls.areValuesEqual(player, eReferenceMulti, copy, eReference);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualMultiReferenceEqual() {
		final EReference eReferenceMulti = BowlingPackage.eINSTANCE.getMatchup_Games();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		final Player copy = EcoreUtil.copy(player);
		final List<EObject> left = new ArrayList<EObject>();
		left.add(player);
		final List<EObject> right = new ArrayList<EObject>();
		right.add(copy);
		final boolean result = CompareControls.areValuesEqual(left, eReferenceMulti,
			right, eReferenceMulti);
		assertTrue(result);
	}

	@Test
	public void testValuesAreEqualMultiReferenceNotEqual() {
		final EReference eReferenceMulti = BowlingPackage.eINSTANCE.getMatchup_Games();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		final Player copy = EcoreUtil.copy(player);
		final List<EObject> left = new ArrayList<EObject>();
		left.add(player);
		copy.setName(STRING1);
		final List<EObject> right = new ArrayList<EObject>();
		right.add(copy);
		final boolean result = CompareControls.areValuesEqual(left, eReferenceMulti,
			right, eReferenceMulti);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualMultiReferenceMultiAttribute() {
		final EReference eReferenceMulti = BowlingPackage.eINSTANCE.getMatchup_Games();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		final List<EObject> left = new ArrayList<EObject>();
		left.add(player);
		final EAttribute eAttributeMulti = BowlingPackage.eINSTANCE.getGame_Frames();
		final List<Integer> right = new ArrayList<Integer>();
		right.add(1);
		final boolean result = CompareControls.areValuesEqual(left, eReferenceMulti,
			right, eAttributeMulti);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualMultiReferenceMultiAttributeTwisted() {
		final EReference eReferenceMulti = BowlingPackage.eINSTANCE.getMatchup_Games();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		final List<EObject> left = new ArrayList<EObject>();
		left.add(player);
		final EAttribute eAttributeMulti = BowlingPackage.eINSTANCE.getGame_Frames();
		final List<Integer> right = new ArrayList<Integer>();
		right.add(1);
		final boolean result = CompareControls.areValuesEqual(right, eAttributeMulti,
			left, eReferenceMulti);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualMultiAttributeEqual() {
		final EAttribute eAttributeMulti = BowlingPackage.eINSTANCE.getGame_Frames();
		final List<Integer> left = new ArrayList<Integer>();
		left.add(1);
		final List<Integer> right = new ArrayList<Integer>();
		right.add(1);
		final boolean result = CompareControls.areValuesEqual(left, eAttributeMulti,
			right, eAttributeMulti);
		assertTrue(result);
	}

	@Test
	public void testValuesAreEqualMultiAttributeEqualTwoValues() {
		final EAttribute eAttributeMulti = BowlingPackage.eINSTANCE.getGame_Frames();
		final List<Integer> left = new ArrayList<Integer>();
		left.add(1);
		left.add(2);
		final List<Integer> right = new ArrayList<Integer>();
		right.add(1);
		right.add(2);
		final boolean result = CompareControls.areValuesEqual(left, eAttributeMulti,
			right, eAttributeMulti);
		assertTrue(result);
	}

	@Test
	public void testValuesAreEqualMultiAttributeNotEqualSize() {
		final EAttribute eAttributeMulti = BowlingPackage.eINSTANCE.getGame_Frames();
		final List<Integer> left = new ArrayList<Integer>();
		left.add(1);
		final List<Integer> right = new ArrayList<Integer>();
		final boolean result = CompareControls.areValuesEqual(left, eAttributeMulti,
			right, eAttributeMulti);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualMultiAttributeNoEqualValues() {
		final EAttribute eAttributeMulti = BowlingPackage.eINSTANCE.getGame_Frames();
		final List<Integer> left = new ArrayList<Integer>();
		left.add(1);
		final List<Integer> right = new ArrayList<Integer>();
		right.add(2);
		final boolean result = CompareControls.areValuesEqual(left, eAttributeMulti,
			right, eAttributeMulti);
		assertFalse(result);
	}

	@Test
	public void testValuesAreEqualMultiAttributeSomeEqualValues() {
		final EAttribute eAttributeMulti = BowlingPackage.eINSTANCE.getGame_Frames();
		final List<Integer> left = new ArrayList<Integer>();
		left.add(1);
		left.add(3);
		final List<Integer> right = new ArrayList<Integer>();
		right.add(1);
		right.add(2);
		final boolean result = CompareControls.areValuesEqual(left, eAttributeMulti,
			right, eAttributeMulti);
		assertFalse(result);
	}

}
