/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link LeafConditionDmrNewModelElementStrategyProvider}.
 *
 * @author Lucas Koehler
 *
 */
public class LeafConditionDmrNewModelElementStrategyProvider_Test {

	private CreateLeafConditionDmrTestProvider strategyProvider;
	private LeafCondition leafCondition;

	@Before
	public void setUp() {
		strategyProvider = new CreateLeafConditionDmrTestProvider();
		leafCondition = mock(LeafCondition.class);
	}

	@Test
	public void handles_generationEnabled_true() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertTrue(
			strategyProvider.handles(leafCondition, RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectReference() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(
			strategyProvider.handles(leafCondition, RulePackage.Literals.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectParent() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(strategyProvider.handles(VViewFactory.eINSTANCE.createControl(),
			RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectBoth() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(
			strategyProvider.handles(VViewFactory.eINSTANCE.createControl(),
				EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES));
	}

	@Test
	public void handles_generationDisabled() {
		strategyProvider.setSegmentToolingEnabled(false);
		assertFalse(
			strategyProvider.handles(leafCondition, RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void getSelectionValidator_eReference() {
		final String result = strategyProvider.getSelectionValidator()
			.isValid(EcorePackage.Literals.EATTRIBUTE__EATTRIBUTE_TYPE);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	public void getSelectionValidator_eAttribute() {
		final String result = strategyProvider.getSelectionValidator()
			.isValid(EcorePackage.Literals.EANNOTATION__SOURCE);
		assertNull(result);
	}

	/** Allows to mock the segment tooling enabled flag without the need to provide a runtime parameter. */
	private class CreateLeafConditionDmrTestProvider extends LeafConditionDmrNewModelElementStrategyProvider {
		private boolean segmentToolingEnabled;

		/**
		 * @param segmentToolingEnabled true to enable segment mode
		 */
		public void setSegmentToolingEnabled(boolean segmentToolingEnabled) {
			this.segmentToolingEnabled = segmentToolingEnabled;
		}

		@Override
		protected boolean isSegmentToolingEnabled() {
			return segmentToolingEnabled;
		}
	}
}
