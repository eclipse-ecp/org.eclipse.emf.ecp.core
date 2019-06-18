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
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.internal.view.multisegment.tooling.MultiDmrOpenInNewContextStrategyProvider_PTest.OpenMultiDmrTestProvider;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link MultiDmrOpenInNewContextStrategyProvider}.
 *
 * @author Lucas Koehler
 *
 */
public class MultiDmrOpenInNewContextStrategyProvider_PTest
	extends AbstractMultiDmrStrategyProviderTest<OpenMultiDmrTestProvider> {

	@Rule
	public final MultiTryTestRule multiTryRule = new MultiTryTestRule(3, false);

	@Override
	protected void initStrategyProvider() {
		setStrategyProvider(new OpenMultiDmrTestProvider());
	}

	@Override
	protected boolean executeHandles(EObject owner, EReference reference) {
		return getStrategyProvider().handles(owner, reference);
	}

	@Test
	@MultiTry
	public void strategy() throws DatabindingFailedException, InterruptedException {
		final OpenInNewContextStrategy strategy = getStrategyProvider().createOpenInNewContextStrategy();
		assertNotNull(strategy);

		final VDomainModelReference editDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		getTableControl().setDomainModelReference(editDmr);

		final Boolean[] result = new Boolean[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.openInNewContext(getTableControl(),
				VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE,
				editDmr);
		});

		StrategyTestUtil.waitForShell("Edit Multi Domain Model Reference"); //$NON-NLS-1$

		UIThreadRunnable.syncExec(() -> {
			final Shell wShell = Display.getDefault().getActiveShell();
			final Tree tree = SWTTestUtil.findControl(wShell, 0, Tree.class);
			SWTTestUtil.selectTreeItem(tree, 0);
			final Button finish = SWTTestUtil.findControl(wShell, 4, Button.class);
			SWTTestUtil.clickButton(finish);
		});

		final Boolean strategyResult = StrategyTestUtil.waitUntilNotNull(result);
		assertTrue(strategyResult);
		assertNotSame(getTableControl().getDomainModelReference(), editDmr);
		assertNotNull(getTableControl().getDomainModelReference());
		assertEquals("Number of created segments", 1, getTableControl().getDomainModelReference().getSegments().size()); //$NON-NLS-1$
	}

	@Test
	@MultiTry
	public void strategy_cancel() throws DatabindingFailedException, InterruptedException {
		final OpenInNewContextStrategy strategy = getStrategyProvider().createOpenInNewContextStrategy();
		assertNotNull(strategy);

		final VDomainModelReference editDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		getTableControl().setDomainModelReference(editDmr);

		final Boolean[] result = new Boolean[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.openInNewContext(getTableControl(),
				VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE,
				editDmr);
		});

		StrategyTestUtil.waitForShell("Edit Multi Domain Model Reference"); //$NON-NLS-1$

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

		final Boolean strategyResult = StrategyTestUtil.waitUntilNotNull(result);
		assertTrue(strategyResult);
		assertSame(getTableControl().getDomainModelReference(), editDmr);
		assertEquals(0, getTableControl().getDomainModelReference().getSegments().size());
	}

	/** Allows to mock the segment tooling enabled flag without the need to provide a runtime parameter. */
	class OpenMultiDmrTestProvider extends MultiDmrOpenInNewContextStrategyProvider
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
