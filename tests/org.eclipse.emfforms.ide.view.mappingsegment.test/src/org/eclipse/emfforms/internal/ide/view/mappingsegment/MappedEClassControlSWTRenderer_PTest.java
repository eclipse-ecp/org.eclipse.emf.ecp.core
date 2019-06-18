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
package org.eclipse.emfforms.internal.ide.view.mappingsegment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.test.common.MultiTryTestRule;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.editor.controls.SelectedFeatureViewService;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentFactory;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for {@link MappedEClassControlSWTRenderer}.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class MappedEClassControlSWTRenderer_PTest {

	// Allow two tries because Jenkins CI might fail on the first run of the first test case
	@Rule
	public final MultiTryTestRule multiTryRule = new MultiTryTestRule(2);

	private MappedEClassControlSWTRenderer renderer;
	private SelectedFeatureViewService featureService;
	private VMappingDomainModelReferenceSegment domainObject;

	private EClass valueType;

	private EClass subValueType;

	private Control renderControl;

	private DefaultRealm realm;

	@Before
	public void setUp() {
		realm = new DefaultRealm();
		final VControl vControl = mock(VControl.class);

		// We need real objects because the MappedEClassControlSWTRenderer extends the
		// EditableEReferenceLabelControlSWTRenderer which doesn't use proper DI. Consequently, we cannot mock the
		// databinding.
		domainObject = VMappingsegmentFactory.eINSTANCE.createMappingDomainModelReferenceSegment();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(
			VMappingsegmentPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS.getName());
		dmr.getSegments().add(segment);

		when(vControl.getDomainModelReference()).thenReturn(dmr);
		when(vControl.isVisible()).thenReturn(true);
		when(vControl.isEffectivelyEnabled()).thenReturn(true);
		when(vControl.isEffectivelyReadonly()).thenReturn(false);
		when(vControl.getLabelAlignment()).thenReturn(LabelAlignment.DEFAULT);
		final ViewModelContext viewContext = mock(ViewModelContext.class);
		when(viewContext.getDomainModel()).thenReturn(domainObject);
		final ReportService reportService = mock(ReportService.class);
		featureService = mock(SelectedFeatureViewService.class);
		when(viewContext.getService(SelectedFeatureViewService.class)).thenReturn(featureService);

		// Mock example package and classes
		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("mappingSegmentTest"); //$NON-NLS-1$
		ePackage.setNsPrefix("mappingSegmentTest"); //$NON-NLS-1$
		ePackage.setNsURI("mappingSegmentTest"); //$NON-NLS-1$
		valueType = EcoreFactory.eINSTANCE.createEClass();
		valueType.setName("ValueType"); //$NON-NLS-1$
		subValueType = EcoreFactory.eINSTANCE.createEClass();
		subValueType.setName("SubValueType"); //$NON-NLS-1$
		subValueType.getESuperTypes().add(valueType);
		ePackage.getEClassifiers().add(valueType);
		ePackage.getEClassifiers().add(subValueType);
		Registry.INSTANCE.put("mappingSegmentTest", ePackage); //$NON-NLS-1$

		final EReference mappingERef = TestUtil.mockMapReference(valueType);
		when(featureService.getFeature()).thenReturn(mappingERef);

		renderer = new MappedEClassControlSWTRenderer(vControl, viewContext, reportService);

		// Wait for the UI harness to be ready before starting the test execution
		final Shell baseShell = UIThreadRunnable.syncExec(() -> {
			final Shell s = new Shell(Display.getDefault());
			s.setLayout(new FillLayout());
			s.setSize(400, 400);
			s.setLocation(100, 100);
			s.open();
			return s;
		});

		// Render
		renderControl = UIThreadRunnable.syncExec(() -> {
			renderer.init();
			try {
				final Control result = renderer.render(new SWTGridCell(0, 2, renderer), baseShell);
				renderer.finalizeRendering(baseShell);
				return result;
			} catch (NoRendererFoundException | NoPropertyDescriptorFoundExeption ex) {
				ex.printStackTrace();
				return null;
			}
		});

		if (renderControl == null) {
			fail("Rendering failed!"); //$NON-NLS-1$
		}

		UIThreadRunnable.syncExec(SWTTestUtil::waitForUIThread);
		UIThreadRunnable.syncExec(() -> baseShell.layout(true, true));
		UIThreadRunnable.syncExec(SWTTestUtil::waitForUIThread);
	}

	@After
	public void disposeRealm() {
		realm.dispose();
	}

	@After
	public void disposeShells() {
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

	@Test
	public void render() {
		final Control c = UIThreadRunnable.syncExec(() -> {
			final Composite renderComposite = Composite.class.cast(renderControl);
			return renderComposite.getChildren()[1];
		});
		assertTrue("Must be a button: " + c, c instanceof Button); //$NON-NLS-1$
		final Button linkButton = (Button) c;
		assertEquals("Link Mapped Class", UIThreadRunnable.syncExec(linkButton::getText)); //$NON-NLS-1$
		UIThreadRunnable.asyncExec(() -> {
			SWTTestUtil.clickButton(linkButton);
		});

		waitForShell("Select an EClass"); //$NON-NLS-1$
		final Shell dialogShell = UIThreadRunnable.syncExec(Display.getDefault()::getActiveShell);
		final SWTBot dialogBot = UIThreadRunnable.syncExec(() -> new SWTBot(dialogShell));

		// tree view should show ValueType as root node and SubValueType as its child
		final SWTBotTreeItem[] treeItems = UIThreadRunnable.syncExec(dialogBot.tree()::getAllItems);
		assertEquals(1, treeItems.length);
		assertTrue(treeItems[0].getText().contains("ValueType")); //$NON-NLS-1$

		treeItems[0].expand();
		final SWTBotTreeItem[] subItems = treeItems[0].getItems();
		assertEquals(1, subItems.length);

		// Select SubValueType and click OK
		subItems[0].select();
		dialogBot.button("OK").click(); //$NON-NLS-1$
		UIThreadRunnable.syncExec(() -> {
			if (!dialogShell.isDisposed()) {
				dialogShell.dispose();
			}
		});

		UIThreadRunnable.syncExec(SWTTestUtil::waitForUIThread);
		assertSame(subValueType, domainObject.getMappedClass());
	}

	@Test
	public void render_cancelDialog() {
		UIThreadRunnable.asyncExec(() -> {
			final Composite renderComposite = Composite.class.cast(renderControl);
			// click 'Link Mapped Class' Button
			SWTTestUtil.clickButton((Button) renderComposite.getChildren()[1]);
		});

		waitForShell("Select an EClass"); //$NON-NLS-1$
		final Shell dialogShell = UIThreadRunnable.syncExec(Display.getDefault()::getActiveShell);
		final SWTBot dialogBot = UIThreadRunnable.syncExec(() -> new SWTBot(dialogShell));

		final SWTBotTreeItem[] treeItems = UIThreadRunnable.syncExec(dialogBot.tree()::getAllItems);
		assertEquals(1, treeItems.length);
		assertTrue(treeItems[0].getText().contains("ValueType")); //$NON-NLS-1$

		treeItems[0].expand();
		final SWTBotTreeItem[] subItems = treeItems[0].getItems();
		assertEquals(1, subItems.length);

		subItems[0].select();
		dialogBot.button("Cancel").click(); //$NON-NLS-1$
		UIThreadRunnable.syncExec(() -> {
			if (!dialogShell.isDisposed()) {
				dialogShell.dispose();
			}
		});

		UIThreadRunnable.syncExec(SWTTestUtil::waitForUIThread);
		assertEquals(null, domainObject.getMappedClass());
	}

	//
	// Test Framework
	//

	private static void waitForShell(String title) {
		new SWTBot().waitUntil(Conditions.shellIsActive(title), 10000, 100);
	}
}
