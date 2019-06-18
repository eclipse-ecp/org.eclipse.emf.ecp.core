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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

/**
 * Test cases for the {@link CreateSegmentDmrWizard}.
 *
 * @author Lucas Koehler
 *
 */
public class CreateSegmentDmrWizard_PTest {
	// Wizard button labels
	private static final String BACK = "< Back";
	private static final String NEXT = "Next >";
	private static final String CANCEL = "Cancel";
	private static final String FINISH = "Finish";

	private static final String WINDOW_TITLE = "Test Window Title";

	private Collection<SegmentIdeDescriptor> descriptors;

	private SegmentGenerator segmentGenerator;
	private EStructuralFeatureSelectionValidator selectionValidator;

	private EClass testClass;
	private EReference testReference;
	private EReference subTestReference;
	private Shell shell;

	@Before
	public void setUp() {
		// Set up test model
		final EPackage testPackage = EcoreFactory.eINSTANCE.createEPackage();
		testPackage.setName("TestPackage");
		testPackage.setNsPrefix("TestPackage");
		testPackage.setNsURI("http://TestPackage");
		testClass = EcoreFactory.eINSTANCE.createEClass();
		testClass.setName("TestClass");
		testPackage.getEClassifiers().add(testClass);

		final EAttribute testAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		testAttribute.setName("TestAttribute");
		testAttribute.setEType(EcorePackage.Literals.ESTRING);

		final EClass refTestClass = EcoreFactory.eINSTANCE.createEClass();
		refTestClass.setName("RefTestClass");
		testPackage.getEClassifiers().add(refTestClass);
		subTestReference = EcoreFactory.eINSTANCE.createEReference();
		subTestReference.setName("SubTestReference");
		subTestReference.setEType(EcorePackage.Literals.ECLASSIFIER);
		refTestClass.getEStructuralFeatures().add(subTestReference);

		testReference = EcoreFactory.eINSTANCE.createEReference();
		testReference.setName("TestReference");
		testReference.setEType(refTestClass);
		testClass.getEStructuralFeatures().add(testAttribute);
		testClass.getEStructuralFeatures().add(testReference);

		selectionValidator = feature -> feature instanceof EReference ? null : "Illegal Selection";

		segmentGenerator = spy(new FeatureSegmentGenerator());
		descriptors = new LinkedList<>();

		shell = new Shell();
		shell.open();
	}

	@After
	public void disposeShell() {
		if (shell != null) {
			shell.dispose();
		}
	}

	@Test
	public void simpleMode() {
		final TestWizard testWizard = new TestWizard(testClass, WINDOW_TITLE, null, selectionValidator,
			segmentGenerator, null, true);
		final WizardDialog wizardDialog = openWizard(testWizard);

		assertEquals(WINDOW_TITLE, Display.getDefault().getActiveShell().getText());

		final SWTBot bot = new SWTBot(wizardDialog.getShell());
		assertFalse(bot.button(BACK).isEnabled());
		assertFalse(bot.button(NEXT).isEnabled());
		assertTrue(bot.button(CANCEL).isEnabled());
		assertFalse(bot.button(FINISH).isEnabled());

		SWTTestUtil.waitForUIThread();

		assertEquals(2, bot.tree().widget.getItemCount());

		// select invalid feature
		bot.tree().getTreeItem("TestAttribute : EString").select();
		assertFalse(bot.button(FINISH).isEnabled());

		final SWTBotTreeItem testRefItem = bot.tree().getTreeItem("TestReference : RefTestClass");
		testRefItem.select();
		assertTrue(bot.button(FINISH).isEnabled());
		assertEquals(1, testRefItem.getItems().length);
		testRefItem.expand();
		testRefItem.getItems()[0].select();
		assertTrue(bot.button(FINISH).isEnabled());

		bot.button(FINISH).click();

		assertTrue(testWizard.getRootEClass().isPresent());
		assertSame(testClass, testWizard.getRootEClass().get());
		// Verify that the segment generator was called once and the wizard handed in the correct list of features to
		// the segment generator
		verify(segmentGenerator, atLeastOnce())
			.generateSegments(argThat(new FeatureListMatcher(testReference, subTestReference)));
		assertTrue(testWizard.getDomainModelReference().isPresent());
		final VDomainModelReference dmr = testWizard.getDomainModelReference().get();
		assertEquals(2, dmr.getSegments().size());
	}

	@Test
	public void simpleMode_cancel() {
		final TestWizard testWizard = new TestWizard(testClass, WINDOW_TITLE, null, selectionValidator,
			segmentGenerator, null, true);
		final WizardDialog wizardDialog = openWizard(testWizard);

		final SWTBot bot = new SWTBot(wizardDialog.getShell());
		final SWTBotTreeItem testRefItem = bot.tree().getTreeItem("TestReference : RefTestClass");
		testRefItem.select();
		assertTrue(bot.button(FINISH).isEnabled());
		bot.button(CANCEL).click();
		SWTTestUtil.waitForUIThread();

		assertFalse(testWizard.getDomainModelReference().isPresent());
		assertEquals(Window.CANCEL, wizardDialog.getReturnCode());
	}

	@Test
	public void simpleMode_initialSelection() {
		// Create initial Dmr with two segments
		final VDomainModelReference initialDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment initialFeatureSeg0 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		initialFeatureSeg0.setDomainModelFeature(testReference.getName());
		final VFeatureDomainModelReferenceSegment initialFeatureSeg1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		initialFeatureSeg1.setDomainModelFeature(subTestReference.getName());
		initialDmr.getSegments().add(initialFeatureSeg0);
		initialDmr.getSegments().add(initialFeatureSeg1);

		final TestWizard testWizard = new TestWizard(testClass, WINDOW_TITLE, initialDmr, selectionValidator,
			segmentGenerator, null,
			true);
		final WizardDialog dialog = openWizard(testWizard);
		final SWTBot bot = new SWTBot(dialog.getShell());

		final SWTBotTree botTree = bot.tree();
		assertEquals(1, botTree.widget.getSelectionCount());
		assertSame(subTestReference, botTree.widget.getSelection()[0].getData());

		bot.tree().getTreeItem("TestReference : RefTestClass").click();
		SWTTestUtil.waitForUIThread();

		bot.button(FINISH).click();
		SWTTestUtil.waitForUIThread();

		assertTrue(testWizard.getDomainModelReference().isPresent());

		final VDomainModelReference resultDmr = testWizard.getDomainModelReference().get();
		// The wizard must create a new dmr and not manipulate the input dmr
		assertNotSame(initialDmr, resultDmr);
		assertEquals(2, initialDmr.getSegments().size());

		assertEquals(1, resultDmr.getSegments().size());
	}

	/** Verifies that the switch to the advanced mode is disabled if there are no SegmentIdeDescriptors. */
	@Test
	public void advancedMode_noSegmentIdeDescriptors() {
		final TestWizard testWizard = new TestWizard(testClass, WINDOW_TITLE, null, selectionValidator,
			segmentGenerator, null, true);
		final WizardDialog wizardDialog = openWizard(testWizard);

		final SWTBot bot = new SWTBot(wizardDialog.getShell());
		final SWTBotButton advancedButton = bot.button("Switch To Advanced DMR Creation");
		assertFalse("The advanced mode button must not be enabled if there are no SegmentIdeDescriptors.",
			advancedButton.isEnabled());
		assertNotNull(advancedButton.getToolTipText());
		assertFalse("The advanced button must have a tooltip.", advancedButton.getToolTipText().isEmpty());
	}

	@Test
	public void advancedMode() {
		final EClass segmentTypeA = createSegmentType("A");
		final SegmentIdeDescriptor descriptorA = mockSimpleDescriptor(segmentTypeA);
		final EClass segmentTypeB = createSegmentType("B");
		final SegmentIdeDescriptor descriptorB = mockSimpleDescriptor(segmentTypeB);

		descriptors.add(descriptorA);
		descriptors.add(descriptorB);
		final TestWizard testWizard = new TestWizard(testClass, WINDOW_TITLE, null, selectionValidator,
			segmentGenerator, null, true);
		final WizardDialog wizardDialog = openWizard(testWizard);

		final SWTBot bot = new SWTBot(wizardDialog.getShell());
		final SWTBotButton advancedButton = bot.button("Switch To Advanced DMR Creation");
		assertTrue("The advanced mode button must be enabled if there are SegmentIdeDescriptors.",
			advancedButton.isEnabled());
		assertNotNull(advancedButton.getToolTipText());
		assertFalse("The advanced button must have a tooltip.", advancedButton.getToolTipText().isEmpty());

		advancedButton.click();
		SWTTestUtil.waitForUIThread();

		assertTrue(bot.button(BACK).isEnabled());
		assertFalse(bot.button(NEXT).isEnabled());
		assertTrue(bot.button(CANCEL).isEnabled());
		assertFalse(bot.button(FINISH).isEnabled());

		// Advanced page 1; The root EClass for this page is TestClass
		{
			final SWTBotCombo combo = bot.comboBox();
			assertTrue(combo.isEnabled());
			assertEquals("One combo box entry for every segment ide descriptor.", 2, combo.itemCount());
			assertEquals("A", combo.items()[0]);
			assertEquals("B", combo.items()[1]);
			combo.setSelection(0); // Select descriptor A
			SWTTestUtil.waitForUIThread();
			final String textBeforeFeatureSelect = bot
				.widget(new WithClassAndData<Text>(Text.class, SWTDataElementIdHelper.ELEMENT_ID_KEY))
				.getText();
			assertTrue(textBeforeFeatureSelect == null || textBeforeFeatureSelect.isEmpty());
			final SWTBotTable table = bot.table();
			assertEquals("One entry for every feature in the current segment's root EClass.", 2,
				table.widget.getItemCount());

			table.select(0);
			SWTTestUtil.waitForUIThread();
			assertFalse(bot.button(NEXT).isEnabled()); // EAttribute selected => next not possible

			table.select(1);
			SWTTestUtil.waitForUIThread();
			final String textAfterFeatureSelect = bot
				.widget(new WithClassAndData<Text>(Text.class, SWTDataElementIdHelper.ELEMENT_ID_KEY))
				.getText();
			assertEquals(testReference.getName(), textAfterFeatureSelect);
			assertTrue(bot.button(NEXT).isEnabled());
			assertTrue(bot.button(FINISH).isEnabled());
		}

		bot.button(NEXT).click();

		// Advanced page 2; The root EClass for this page is RefTestClass
		{
			final SWTBotCombo combo = bot.comboBox();
			assertEquals("One combo box entry for every segment ide descriptor.", 2, combo.itemCount());
			combo.setSelection(1); // Select descriptor B
			SWTTestUtil.waitForUIThread();

			final String textBeforeFeatureSelect = bot
				.widget(new WithClassAndData<Text>(Text.class, SWTDataElementIdHelper.ELEMENT_ID_KEY))
				.getText();
			assertTrue(textBeforeFeatureSelect == null || textBeforeFeatureSelect.isEmpty());
			final SWTBotTable table = bot.table();
			assertEquals("One entry for every feature in the current segment's root EClass.", 1,
				table.widget.getItemCount());
			table.select(0);
			SWTTestUtil.waitForUIThread();
			final String textAfterFeatureSelect = bot
				.widget(new WithClassAndData<Text>(Text.class, SWTDataElementIdHelper.ELEMENT_ID_KEY))
				.getText();
			assertEquals(subTestReference.getName(), textAfterFeatureSelect);
			assertTrue(bot.button(NEXT).isEnabled());
			assertTrue(bot.button(FINISH).isEnabled());

			bot.button(FINISH).click();
			SWTTestUtil.waitForUIThread();
		}

		// Verify results of advanced mode
		assertEquals(Window.OK, wizardDialog.getReturnCode());
		final Optional<VDomainModelReference> dmr = testWizard.getDomainModelReference();
		assertTrue(dmr.isPresent());
		assertEquals(dmr.get().getSegments().size(), 2);
		final VDomainModelReferenceSegment segment0 = dmr.get().getSegments().get(0);
		final VDomainModelReferenceSegment segment1 = dmr.get().getSegments().get(1);
		assertSame(segmentTypeA, segment0.eClass());
		assertSame(segmentTypeB, segment1.eClass());
		assertEquals(testReference.getName(),
			segment0.eGet(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE));
		assertEquals(subTestReference.getName(),
			segment1.eGet(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE));
		assertTrue(testWizard.getRootEClass().isPresent());
		assertSame(testClass, testWizard.getRootEClass().get());
	}

	/** Tests that using the back button in advanced mode works properly. */
	@Test
	public void advancedMode_back() {
		final EClass segmentTypeA = createSegmentType("A");
		final SegmentIdeDescriptor descriptorA = mockSimpleDescriptor(segmentTypeA);

		descriptors.add(descriptorA);
		final TestWizard testWizard = new TestWizard(testClass, WINDOW_TITLE, null, selectionValidator,
			segmentGenerator, null, true);
		final WizardDialog wizardDialog = openWizard(testWizard);

		final SWTBot bot = new SWTBot(wizardDialog.getShell());
		final SWTBotButton advancedButton = bot.button("Switch To Advanced DMR Creation");

		advancedButton.click();
		SWTTestUtil.waitForUIThread();

		assertTrue(bot.button(BACK).isEnabled());
		assertFalse(bot.button(NEXT).isEnabled());
		assertTrue(bot.button(CANCEL).isEnabled());
		assertFalse(bot.button(FINISH).isEnabled());

		// Advanced page 1; The root EClass for this page is TestClass
		{
			final SWTBotCombo combo = bot.comboBox();
			assertEquals("A", combo.items()[0]);
			combo.setSelection(0); // Select descriptor A
			SWTTestUtil.waitForUIThread();
			bot.table().select(1);
			SWTTestUtil.waitForUIThread();
			bot.button(NEXT).click();
		}

		// Advanced page 2; The root EClass for this page is RefTestClass
		{
			final SWTBotCombo combo = bot.comboBox();
			combo.setSelection(0); // Select descriptor A
			SWTTestUtil.waitForUIThread();
			bot.table().select(0);
			SWTTestUtil.waitForUIThread();
			assertTrue(bot.button(BACK).isEnabled());
			bot.button(BACK).click();
			SWTTestUtil.waitForUIThread();
		}

		// Back at page one the finish button should still be activated
		assertTrue(bot.button(FINISH).isEnabled());
		bot.button(FINISH).click();
		SWTTestUtil.waitForUIThread();

		// Verify results of advanced mode
		assertEquals(Window.OK, wizardDialog.getReturnCode());
		final Optional<VDomainModelReference> dmr = testWizard.getDomainModelReference();
		assertTrue(dmr.isPresent());
		// No segment must have been created for the page we backed out of.
		assertEquals(dmr.get().getSegments().size(), 1);
		final VDomainModelReferenceSegment segment = dmr.get().getSegments().get(0);
		assertSame(segmentTypeA, segment.eClass());
		assertEquals(testReference.getName(),
			segment.eGet(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE));
		assertTrue(testWizard.getRootEClass().isPresent());
		assertSame(testClass, testWizard.getRootEClass().get());
	}

	/** Tests that the advanced mode only allows to finish if the last segment's type matches a configured one. */
	@Test
	public void advancedMode_lastSegmentType() {
		final EClass segmentTypeA = createSegmentType("A");
		final SegmentIdeDescriptor descriptorA = mockSimpleDescriptor(segmentTypeA);
		final EClass segmentTypeB = createSegmentType("B");
		final SegmentIdeDescriptor descriptorB = mockSimpleDescriptor(segmentTypeB);

		descriptors.add(descriptorA);
		descriptors.add(descriptorB);
		// Require segmentTypeB as last segment
		final TestWizard testWizard = new TestWizard(testClass, WINDOW_TITLE, null, selectionValidator,
			segmentGenerator, segmentTypeB,
			true);
		final WizardDialog wizardDialog = openWizard(testWizard);

		final SWTBot bot = new SWTBot(wizardDialog.getShell());
		final SWTBotButton advancedButton = bot.button("Switch To Advanced DMR Creation");
		assertTrue("The advanced mode button must be enabled if there are SegmentIdeDescriptors.",
			advancedButton.isEnabled());

		advancedButton.click();
		SWTTestUtil.waitForUIThread();

		// Advanced page 1; The root EClass for this page is TestClass
		{
			final SWTBotCombo combo = bot.comboBox();
			assertTrue(combo.isEnabled());
			assertEquals("One combo box entry for every segment ide descriptor.", 2, combo.itemCount());
			assertEquals("A", combo.items()[0]);
			assertEquals("B", combo.items()[1]);
			combo.setSelection(0); // Select descriptor A
			SWTTestUtil.waitForUIThread();

			final SWTBotTable table = bot.table();
			table.select(1);
			SWTTestUtil.waitForUIThread();
			assertTrue(bot.button(NEXT).isEnabled());
			assertFalse(bot.button(FINISH).isEnabled());

			combo.setSelection(1); // Select descriptor B
			SWTTestUtil.waitForUIThread();
			assertTrue(bot.button(NEXT).isEnabled());
			assertTrue(bot.button(FINISH).isEnabled());

		}
		bot.button(FINISH).click();

		// Verify results of advanced mode
		assertEquals(Window.OK, wizardDialog.getReturnCode());
		final Optional<VDomainModelReference> dmr = testWizard.getDomainModelReference();
		assertTrue(dmr.isPresent());
		assertEquals(dmr.get().getSegments().size(), 1);
		final VDomainModelReferenceSegment segment = dmr.get().getSegments().get(0);
		assertSame(segmentTypeB, segment.eClass());
		assertEquals(testReference.getName(),
			segment.eGet(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE));
		assertTrue(testWizard.getRootEClass().isPresent());
		assertSame(testClass, testWizard.getRootEClass().get());
	}

	//
	// Test Framework
	//

	/**
	 * @return the opened {@link WizardDialog} showing the wizard
	 */
	private WizardDialog openWizard(Wizard wizard) {
		final WizardDialog wizardDialog = new WizardDialog(shell, wizard);
		wizardDialog.setBlockOnOpen(false);
		wizardDialog.open();
		SWTTestUtil.waitForUIThread();
		return wizardDialog;
	}

	private static EClass createSegmentType(String name) {
		final EClass segmentClass = EcoreFactory.eINSTANCE.createEClass();
		segmentClass.setName(name);
		segmentClass.getESuperTypes().add(VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT);

		final EPackage segmentPackage = EcoreFactory.eINSTANCE.createEPackage();
		segmentPackage.setName("package-" + name);
		segmentPackage.setNsPrefix("package-" + name);
		segmentPackage.setNsURI("http://package-" + name);
		segmentPackage.getEClassifiers().add(segmentClass);

		return segmentClass;
	}

	private static SegmentIdeDescriptor mockSimpleDescriptor(EClass segmentType) {
		final SegmentIdeDescriptor descriptor = mock(SegmentIdeDescriptor.class);
		when(descriptor.getEStructuralFeatureSelectionValidator()).thenReturn(feature -> null); // always valid
		when(descriptor.getReferenceTypeResolver()).thenReturn((ref, seg) -> ref.getEReferenceType());
		when(descriptor.getSegmentType()).thenReturn(segmentType);
		when(descriptor.isAllowedAsLastElementInPath()).thenReturn(true);
		when(descriptor.isAvailableInIde()).thenReturn(true);
		when(descriptor.isLastElementInPath()).thenReturn(false);
		return descriptor;
	}

	/**
	 * Argument matcher that validates that a list of structural features contains the specified features in the correct
	 * order.
	 */
	private static class FeatureListMatcher extends ArgumentMatcher<List<EStructuralFeature>> {
		private final EStructuralFeature[] features;

		FeatureListMatcher(EStructuralFeature... features) {
			this.features = features;
		}

		@Override
		public boolean matches(Object argument) {
			@SuppressWarnings("unchecked")
			final List<EStructuralFeature> list = (List<EStructuralFeature>) argument;
			if (features.length != list.size()) {
				return false;
			}
			for (int i = 0; i < features.length; i++) {
				if (features[i] != list.get(i)) {
					return false;
				}
			}
			return true;
		}
	}

	/** Extend to avoid ide descriptor collection via OSGI */
	private class TestWizard extends CreateSegmentDmrWizard {

		TestWizard(EClass rootEClass, String windowTitle, VDomainModelReference existingDMR,
			EStructuralFeatureSelectionValidator selectionValidator, SegmentGenerator segmentGenerator,
			EClass lastSegmentType, boolean ignoreSegmentIdeRestriction) {
			super(rootEClass, windowTitle, existingDMR, selectionValidator, segmentGenerator, lastSegmentType,
				ignoreSegmentIdeRestriction);
		}

		@Override
		protected Collection<SegmentIdeDescriptor> collectSegmentIdeDescriptors(boolean ignoreSegmentIdeRestriction) {
			return descriptors;
		}
	}

	/** Matches a widget of a given class which has data for the given key. */
	private static class WithClassAndData<T extends Widget> extends AbstractMatcher<T> {
		private final String dataKey;
		private final Class<T> clazz;

		WithClassAndData(Class<T> clazz, String dataKey) {
			this.clazz = clazz;
			this.dataKey = dataKey;
		}

		@Override
		public void describeTo(Description description) {
			description
				.appendText(
					MessageFormat.format("with class ''{0}'' and data for key ''{1}''.", clazz.getName(), dataKey));
		}

		@Override
		protected boolean doMatch(Object item) {
			if (!clazz.isInstance(item)) {
				return false;
			}
			final Widget widget = (Widget) item;
			return widget.getData(dataKey) != null;
		}

	}
}
