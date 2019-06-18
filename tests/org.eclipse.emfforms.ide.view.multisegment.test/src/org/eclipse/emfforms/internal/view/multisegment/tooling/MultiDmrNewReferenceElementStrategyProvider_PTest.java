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
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.test.common.MultiTry;
import org.eclipse.emf.ecp.test.common.MultiTryTestRule;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.internal.view.multisegment.tooling.MultiDmrNewReferenceElementStrategyProvider_PTest.CreateMultiDmrTestProvider;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link MultiDmrNewReferenceElementStrategyProvider}.
 *
 * @author Lucas Koehler
 *
 */
public class MultiDmrNewReferenceElementStrategyProvider_PTest
	extends AbstractMultiDmrStrategyProviderTest<CreateMultiDmrTestProvider> {

	@Rule
	public final MultiTryTestRule multiTryRule = new MultiTryTestRule(3, false);

	@Override
	protected void initStrategyProvider() {
		setStrategyProvider(new CreateMultiDmrTestProvider());
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
			result[0] = strategy.createNewModelElement(getTableControl(),
				VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE);
		});

		StrategyTestUtil.waitForShell("New Multi Domain Model Reference"); //$NON-NLS-1$

		UIThreadRunnable.syncExec(() -> {
			final Shell wShell = Display.getDefault().getActiveShell();
			final Tree tree = SWTTestUtil.findControl(wShell, 0, Tree.class);
			SWTTestUtil.selectTreeItem(tree, 0);
			final Button finish = SWTTestUtil.findControl(wShell, 4, Button.class);
			SWTTestUtil.clickButton(finish);
		});

		final Optional<EObject> strategyResult = StrategyTestUtil.waitUntilNotNull(result);
		assertTrue(strategyResult.isPresent());
		assertTrue("Result must be a VDomainModelReference.", result[0].get() instanceof VDomainModelReference); //$NON-NLS-1$
		// strategy should not add the dmr to the table control.
		assertNull(getTableControl().getDomainModelReference());
	}

	@Test
	@MultiTry
	public void strategy_cancel() {
		final CreateNewModelElementStrategy strategy = getStrategyProvider().createCreateNewModelElementStrategy();
		assertNotNull(strategy);

		@SuppressWarnings("unchecked")
		final Optional<EObject>[] result = new Optional[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.createNewModelElement(getTableControl(),
				VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE);
		});

		StrategyTestUtil.waitForShell("New Multi Domain Model Reference"); //$NON-NLS-1$

		UIThreadRunnable.syncExec(() -> {
			final SWTBot bot = new SWTBot();
			final Shell wShell = bot.activeShell().widget;
			final Tree tree = SWTTestUtil.findControl(wShell, 0, Tree.class);
			SWTTestUtil.selectTreeItem(tree, 0);
			bot.button("Cancel").click(); //$NON-NLS-1$
			if (!wShell.isDisposed()) {
				wShell.dispose();
			}
		});

		final Optional<EObject> strategyResult = StrategyTestUtil.waitUntilNotNull(result);
		assertFalse(strategyResult.isPresent());
		assertNull(getTableControl().getDomainModelReference());
	}

	/** Allows to mock the segment tooling enabled flag without the need to provide a runtime parameter. */
	class CreateMultiDmrTestProvider extends MultiDmrNewReferenceElementStrategyProvider
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
		boolean isSegmentToolingEnabled() {
			return segmentToolingEnabled;
		}
	}
}
