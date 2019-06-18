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

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link IterateConditionDmrOpenInNewContextStrategyProvider}.
 *
 * @author Lucas Koehler
 */
public class IterateConditionDmrOpenInNewContextStrategyProvider_Test {

	private IterateCondition iterateCondition;
	private EditIterateConditionDmrTestProvider strategyProvider;

	@Before
	public void setUp() {
		iterateCondition = RuleFactory.eINSTANCE.createIterateCondition();
		strategyProvider = new EditIterateConditionDmrTestProvider();
	}

	@Test
	public void handles_generationEnabled_true() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertTrue(
			strategyProvider.handles(iterateCondition, RulePackage.Literals.ITERATE_CONDITION__ITEM_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectReference() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(
			strategyProvider.handles(iterateCondition, RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectParent() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(strategyProvider.handles(VViewFactory.eINSTANCE.createControl(),
			RulePackage.Literals.ITERATE_CONDITION__ITEM_REFERENCE));
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
			strategyProvider.handles(iterateCondition, RulePackage.Literals.ITERATE_CONDITION__ITEM_REFERENCE));
	}

	@Test
	public void getSelectionValidator_singleReference() {
		final String result = strategyProvider.getSelectionValidator()
			.isValid(EcorePackage.Literals.EATTRIBUTE__EATTRIBUTE_TYPE);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	public void getSelectionValidator_multiReference() {
		final String result = strategyProvider.getSelectionValidator()
			.isValid(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS);
		assertNull(result);
	}

	@Test
	public void getSelectionValidator_multiAttribute() {
		final String result = strategyProvider.getSelectionValidator()
			.isValid(EcorePackage.Literals.EANNOTATION__SOURCE);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	/** Allows to mock the segment tooling enabled flag without the need to provide a runtime parameter. */
	private class EditIterateConditionDmrTestProvider extends IterateConditionDmrOpenInNewContextStrategyProvider {
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
