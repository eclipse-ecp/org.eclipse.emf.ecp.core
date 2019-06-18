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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.test.common.MultiTry;
import org.eclipse.emf.ecp.test.common.MultiTryTestRule;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.internal.view.multisegment.tooling.MultiSegmentChildDmrOpenInNewContextStrategyProvider_PTest.OpenChildDmrTestProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link MultiSegmentChildDmrOpenInNewContextStrategyProvider}.
 *
 * @author Lucas Koehler
 */
public class MultiSegmentChildDmrOpenInNewContextStrategyProvider_PTest
	extends AbstractMultiSegmentChildDmrStrategyProviderTest<OpenChildDmrTestProvider> {

	@Rule
	public final MultiTryTestRule multiTryRule = new MultiTryTestRule(3, false);

	private ReportService reportService;
	private EMFFormsDatabindingEMF databinding;

	@Override
	protected void initStrategyProvider() {
		setStrategyProvider(new OpenChildDmrTestProvider());
		reportService = mock(ReportService.class);
		databinding = mock(EMFFormsDatabindingEMF.class);
		getStrategyProvider().setReportService(reportService);
		getStrategyProvider().setEMFFormsDatabinding(databinding);
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
		final EList<VDomainModelReference> childDmrs = getMultiSegment().getChildDomainModelReferences();
		childDmrs.add(editDmr);

		// mock databinding
		final IEMFValueProperty valueProperty = mock(IEMFValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		when(databinding.getValueProperty(getParentDmr(), EcorePackage.Literals.ECLASS))
			.thenReturn(valueProperty);

		final Boolean[] result = new Boolean[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.openInNewContext(getMultiSegment(),
				VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES,
				editDmr);
		});

		StrategyTestUtil.waitForShell("Edit Child Domain Model Reference"); //$NON-NLS-1$

		UIThreadRunnable.syncExec(() -> {
			final Shell wShell = Display.getDefault().getActiveShell();
			final Tree tree = SWTTestUtil.findControl(wShell, 0, Tree.class);
			SWTTestUtil.selectTreeItem(tree, 0);
			final Button finish = SWTTestUtil.findControl(wShell, 4, Button.class);
			SWTTestUtil.clickButton(finish);
		});

		final Boolean strategyResult = StrategyTestUtil.waitUntilNotNull(result);
		assertTrue(strategyResult);
		assertFalse(childDmrs.contains(editDmr));
		assertEquals(1, childDmrs.size());
		assertEquals("Number of created segments", 1, childDmrs.get(0).getSegments().size()); //$NON-NLS-1$
	}

	@Test
	@MultiTry
	public void strategy_cancel() throws DatabindingFailedException, InterruptedException {
		final OpenInNewContextStrategy strategy = getStrategyProvider().createOpenInNewContextStrategy();
		assertNotNull(strategy);

		final VDomainModelReference editDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final EList<VDomainModelReference> childDmrs = getMultiSegment().getChildDomainModelReferences();
		childDmrs.add(editDmr);

		// mock databinding
		final IEMFValueProperty valueProperty = mock(IEMFValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		when(databinding.getValueProperty(getParentDmr(), EcorePackage.Literals.ECLASS))
			.thenReturn(valueProperty);

		final Boolean[] result = new Boolean[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.openInNewContext(getMultiSegment(),
				VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES,
				editDmr);
		});

		StrategyTestUtil.waitForShell("Edit Child Domain Model Reference"); //$NON-NLS-1$

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
		assertTrue(childDmrs.contains(editDmr));
		assertEquals(1, childDmrs.size());
		assertEquals("Cancel should not change the dmr", 0, childDmrs.get(0).getSegments().size()); //$NON-NLS-1$
	}

	@Test
	@MultiTry
	public void strategy_databindingFailed() throws DatabindingFailedException, InterruptedException {
		final OpenInNewContextStrategy strategy = getStrategyProvider().createOpenInNewContextStrategy();
		assertNotNull(strategy);

		final VDomainModelReference editDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final EList<VDomainModelReference> childDmrs = getMultiSegment().getChildDomainModelReferences();
		childDmrs.add(editDmr);

		// mock databinding
		final IEMFValueProperty valueProperty = mock(IEMFValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		when(databinding.getValueProperty(getParentDmr(), EcorePackage.Literals.ECLASS))
			.thenThrow(new DatabindingFailedException("Test")); //$NON-NLS-1$

		final Boolean[] result = new Boolean[1];
		UIThreadRunnable.asyncExec(() -> {
			result[0] = strategy.openInNewContext(getMultiSegment(),
				VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES,
				editDmr);
		});

		final Boolean strategyResult = StrategyTestUtil.waitUntilNotNull(result);
		assertFalse(strategyResult);
		verify(reportService, times(1)).report(any(AbstractReport.class));
		assertTrue(childDmrs.contains(editDmr));
		assertEquals(1, childDmrs.size());
		assertEquals("Failed databinding should not change the dmr", 0, childDmrs.get(0).getSegments().size()); //$NON-NLS-1$
	}

	/** Allows to mock the segment tooling enabled flag without the need to provide a runtime parameter. */
	class OpenChildDmrTestProvider extends MultiSegmentChildDmrOpenInNewContextStrategyProvider
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
