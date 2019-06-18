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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.test.common.MultiTry;
import org.eclipse.emf.ecp.test.common.MultiTryTestRule;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.view.internal.editor.handler.RuleConditionDmrOpenInNewContextStrategyProvider_PTest.EditRuleConditionDmrTestProvider;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for {@link LeafConditionDmrOpenInNewContextStrategyProvider}.
 *
 * @author Lucas Koehler
 */
public class RuleConditionDmrOpenInNewContextStrategyProvider_PTest
	extends AbstractRuleConditionDmrStrategyProviderTest<EditRuleConditionDmrTestProvider> {

	@Rule
	public final MultiTryTestRule multiTryRule = new MultiTryTestRule(2, false);

	@Test
	@MultiTry
	public void strategy() {
		final OpenInNewContextStrategy strategy = getStrategyProvider().createOpenInNewContextStrategy();
		assertNotNull(strategy);
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		getLeafCondition().setDomainModelReference(dmr);

		final Boolean[] result = new Boolean[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.openInNewContext(getLeafCondition(),
				RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE,
				dmr);
		});

		waitForShell("Edit Domain Model Reference"); //$NON-NLS-1$

		UIThreadRunnable.syncExec(() -> {
			final Shell wShell = Display.getDefault().getActiveShell();
			final Tree tree = SWTTestUtil.findControl(wShell, 0, Tree.class);
			SWTTestUtil.selectTreeItem(tree, 0);
			final Button finish = SWTTestUtil.findControl(wShell, 4, Button.class);
			SWTTestUtil.clickButton(finish);
		});

		final Boolean strategyResult = waitUntilNotNull(result);
		assertTrue(strategyResult);
		// strategy create a new dmr which replaces the old one
		assertNotSame(dmr, getLeafCondition().getDomainModelReference());
		assertNotNull(getLeafCondition().getDomainModelReference());
		assertEquals(1, getLeafCondition().getDomainModelReference().getSegments().size());
	}

	@Test
	@MultiTry
	public void strategy_cancel() {
		final OpenInNewContextStrategy strategy = getStrategyProvider().createOpenInNewContextStrategy();
		assertNotNull(strategy);
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		getLeafCondition().setDomainModelReference(dmr);

		final Boolean[] result = new Boolean[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.openInNewContext(getLeafCondition(),
				RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE,
				dmr);
		});

		waitForShell("Edit Domain Model Reference"); //$NON-NLS-1$

		UIThreadRunnable.syncExec(() -> {
			final Shell wShell = Display.getDefault().getActiveShell();
			final Tree tree = SWTTestUtil.findControl(wShell, 0, Tree.class);
			final Button cancel = SWTTestUtil.findControl(wShell, 3, Button.class);
			SWTTestUtil.selectTreeItem(tree, 1);
			SWTTestUtil.clickButton(cancel);
		});

		final Boolean strategyResult = waitUntilNotNull(result);
		assertTrue(strategyResult);
		assertSame(dmr, getLeafCondition().getDomainModelReference());
	}

	@Override
	protected void initStrategyProvider() {
		setStrategyProvider(new EditRuleConditionDmrTestProvider());
	}

	@Override
	protected boolean executeHandles(EObject owner, EReference reference) {
		return getStrategyProvider().handles(owner, reference);
	}

	/** Allows to mock the segment tooling enabled flag without the need to provide a runtime parameter. */
	class EditRuleConditionDmrTestProvider extends RuleConditionDmrOpenInNewContextStrategyProvider
		implements TestableStrategyProvider {
		private boolean segmentToolingEnabled;

		/**
		 * @param segmentToolingEnabled true to enable segment mode
		 */
		@Override
		public void setSegmentToolingEnabled(boolean segmentToolingEnabled) {
			this.segmentToolingEnabled = segmentToolingEnabled;
		}

		@Override
		protected boolean isSegmentToolingEnabled() {
			return segmentToolingEnabled;
		}
	}
}