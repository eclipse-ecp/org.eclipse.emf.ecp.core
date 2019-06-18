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
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.test.common.TestUtil;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract base classes for the tests for {@link LeafConditionDmrNewModelElementStrategyProvider} and
 * {@link LeafConditionDmrOpenInNewContextStrategyProvider}.
 *
 * @author Lucas Koehler
 */
public abstract class AbstractRuleConditionDmrStrategyProviderTest<T extends ReferenceServiceCustomizationVendor<?> & TestableStrategyProvider> {

	private T strategyProvider;
	private EClass testEClass;
	// Use LeafCondition as the condition instance for these tests
	private LeafCondition leafCondition;

	/** @return the tested strategy provider */
	protected T getStrategyProvider() {
		return strategyProvider;
	}

	/** @see #getStrategyProvider() */
	protected void setStrategyProvider(T strategyProvider) {
		this.strategyProvider = strategyProvider;
	}

	/** The {@link LeafCondition} to edit */
	protected LeafCondition getLeafCondition() {
		return leafCondition;
	}

	@Before
	public void setUp() {
		testEClass = EcoreFactory.eINSTANCE.createEClass();
		testEClass.setName("TestClass"); //$NON-NLS-1$
		// ePackage.getEClassifiers().add(eClass);
		final EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		eReference.setUpperBound(-1);
		eReference.setName("TestReference"); //$NON-NLS-1$
		eReference.setEType(EcorePackage.Literals.ECLASS);
		testEClass.getEStructuralFeatures().add(eReference);
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		eAttribute.setName("TestAttribute");
		eAttribute.setEType(EcorePackage.Literals.ESTRING);
		testEClass.getEStructuralFeatures().add(eAttribute);

		// Create view model with a leaf condition
		leafCondition = RuleFactory.eINSTANCE.createLeafCondition();
		final Rule rule = RuleFactory.eINSTANCE.createEnableRule();
		rule.setCondition(leafCondition);
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.getAttachments().add(rule);
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(testEClass);
		view.getChildren().add(control);

		// Add the view to a resource with editing domain
		final Resource resource = TestUtil.createResourceWithEditingDomain();
		resource.getContents().add(view);

		initStrategyProvider();

		// Wait for the UI harness to be ready before starting the test execution
		UIThreadRunnable.syncExec(SWTTestUtil::waitForUIThread);
	}

	@After
	public void tearDown() {
		// Shell 0 and 1 must not be disposed. They belong to the UI harness around the tests
		UIThreadRunnable.syncExec(Display.getDefault(), () -> {
			final Shell[] shells = Display.getDefault().getShells();
			for (int i = 2; i < shells.length; i++) {
				if (shells[i] != null && !shells[i].isDisposed()) {
					shells[i].dispose();
				}
			}
		});
	}

	protected abstract void initStrategyProvider();

	protected abstract boolean executeHandles(EObject owner, EReference reference);

	@Test
	public void handles_generationEnabled_true() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertTrue(executeHandles(leafCondition, RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectReference() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(executeHandles(leafCondition, RulePackage.Literals.RULE__CONDITION));
	}

	@Test
	public void handles_generationEnabled_incorrectParent() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(executeHandles(VViewFactory.eINSTANCE.createControl(),
			RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectBoth() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(
			executeHandles(VViewFactory.eINSTANCE.createControl(), EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES));
	}

	@Test
	public void handles_generationDisabled() {
		strategyProvider.setSegmentToolingEnabled(false);
		assertFalse(executeHandles(leafCondition, RulePackage.Literals.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE));
	}

	//
	// Test utility
	/**
	 * Waits until the {@link Shell} with the given title is active. The active shell is checked every 100 milli seconds
	 * and the wait times out after 10 seconds.
	 *
	 * @param title The title of the {@link Shell}.
	 */
	static void waitForShell(String title) {
		new SWTBot().waitUntil(Conditions.shellIsActive(title), 10000, 100);
	}

	/**
	 * Waits until the element at index 0 of the given array is not null. The check is repeated every 100 milli seconds
	 * and the wait times out after 20 seconds.
	 *
	 * @param array The array which will contain the wanted element at index 0
	 * @return the result if the wait succeeds
	 */
	static <T> T waitUntilNotNull(T[] array) {
		new SWTBot().waitUntil(new DefaultCondition() {

			@Override
			public boolean test() throws Exception {
				return array[0] != null;
			}

			@Override
			public String getFailureMessage() {
				return "Strategy did not return!"; //$NON-NLS-1$
			}
		}, 20000, 100);
		return array[0];
	}
}
