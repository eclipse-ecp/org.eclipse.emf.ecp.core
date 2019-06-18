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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.test.common.MultiTry;
import org.eclipse.emf.ecp.test.common.MultiTryTestRule;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.view.internal.editor.handler.RuleConditionDmrNewModelElementStrategyProvider_PTest.CreateRuleConditionDmrTestProvider;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link RuleConditionDmrNewModelElementStrategyProvider}.
 *
 * @author Lucas Koehler
 */
public class RuleConditionDmrNewModelElementStrategyProvider_PTest
	extends AbstractRuleConditionDmrStrategyProviderTest<CreateRuleConditionDmrTestProvider> {

	@Rule
	public final MultiTryTestRule multiTryRule = new MultiTryTestRule(2, false);

	@Override
	protected void initStrategyProvider() {
		setStrategyProvider(new CreateRuleConditionDmrTestProvider());
	}

	@Override
	protected boolean executeHandles(EObject owner, EReference reference) {
		return getStrategyProvider().handles(owner, reference);
	}

	@Test
	@MultiTry
	public void strategy() {
		final CreateNewModelElementStrategy strategy = getStrategyProvider().createCreateNewModelElementStrategy();
		assertNotNull(strategy);

		@SuppressWarnings("unchecked")
		final Optional<EObject>[] result = new Optional[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.createNewModelElement(getLeafCondition(),
				RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE);
		});

		waitForShell("New Domain Model Reference"); //$NON-NLS-1$

		UIThreadRunnable.syncExec(() -> {
			final Shell wShell = Display.getDefault().getActiveShell();
			final Tree tree = SWTTestUtil.findControl(wShell, 0, Tree.class);
			SWTTestUtil.selectTreeItem(tree, 0);
			final Button finish = SWTTestUtil.findControl(wShell, 4, Button.class);
			SWTTestUtil.clickButton(finish);
		});

		final Optional<EObject> strategyResult = waitUntilNotNull(result);
		assertTrue(strategyResult.isPresent());
		assertTrue("Result must be a VDomainModelReference.", result[0].get() instanceof VDomainModelReference); //$NON-NLS-1$
		// strategy should not add the dmr to the table control.
		assertNull(getLeafCondition().getDomainModelReference());
	}

	@Test
	@MultiTry
	public void strategy_cancel() {
		final CreateNewModelElementStrategy strategy = getStrategyProvider().createCreateNewModelElementStrategy();
		assertNotNull(strategy);

		@SuppressWarnings("unchecked")
		final Optional<EObject>[] result = new Optional[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.createNewModelElement(getLeafCondition(),
				RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE);
		});

		waitForShell("New Domain Model Reference"); //$NON-NLS-1$

		UIThreadRunnable.syncExec(() -> {
			final Shell wShell = Display.getDefault().getActiveShell();
			final Tree tree = SWTTestUtil.findControl(wShell, 0, Tree.class);
			final Button cancel = SWTTestUtil.findControl(wShell, 3, Button.class);
			SWTTestUtil.selectTreeItem(tree, 1);
			SWTTestUtil.clickButton(cancel);
		});

		final Optional<EObject> strategyResult = waitUntilNotNull(result);
		assertFalse(strategyResult.isPresent());
		assertNull(getLeafCondition().getDomainModelReference());
	}

	/** Allows to mock the segment tooling enabled flag without the need to provide a runtime parameter. */
	class CreateRuleConditionDmrTestProvider extends RuleConditionDmrNewModelElementStrategyProvider
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
