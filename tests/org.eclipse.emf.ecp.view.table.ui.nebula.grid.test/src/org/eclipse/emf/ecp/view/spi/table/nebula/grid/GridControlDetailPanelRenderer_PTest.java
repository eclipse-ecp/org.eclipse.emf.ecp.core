/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.table.test.common.TableControlHandle;
import org.eclipse.emf.ecp.view.table.test.common.TableTestUtil;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverterService;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for the {@link GridControlDetailPanelRenderer}.
 *
 * @author Lucas Koehler
 *
 */
public class GridControlDetailPanelRenderer_PTest {

	private static String log;
	private static PrintStream systemErr;
	private Shell shell;
	private EObject domainElement;
	private DefaultRealm realm;

	@BeforeClass
	public static void beforeClass() {
		systemErr = System.err;
		System.setErr(new PrintStreamWrapper(systemErr));
	}

	@AfterClass
	public static void afterClass() {
		System.setErr(systemErr);
	}

	@Before
	public void init() {
		realm = new DefaultRealm();
		log = "";
		shell = SWTViewTestHelper.createShell();

		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.getESuperTypes().add(EcorePackage.eINSTANCE.getEClass());
		domainElement = eClass;
	}

	@After
	public void after() {
		realm.dispose();
		if (!log.isEmpty()) {
			fail("Unexpected log to System.err: " + log);
		}
	}

	/**
	 * Tests that a sash form is used in order to allow resizing of the grid and detail panel composites.
	 */
	@Test
	public void testSashForm() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, EMFFormsNoRendererException {
		// setup model
		final TableControlHandle handle = TableTestUtil.createInitializedTableWithoutTableColumns();
		handle.getTableControl().setDetailEditing(DetailEditing.WITH_PANEL);
		//
		final Control render = SWTViewTestHelper.render(handle.getTableControl(), domainElement, shell);
		assertTrue(render instanceof Composite);
		final Composite border = getChild(getChild(render, Composite.class, 0), Composite.class, 1);
		assertEquals("The grid and detail should be surrounded by a common border", SWT.BORDER,
			border.getStyle() & SWT.BORDER);
		final SashForm sashForm = getChild(border, SashForm.class, 0);
		assertEquals("The SashForm should have exactly two child composites: one for the grid and one for the detail.",
			2, sashForm.getChildren().length);
		final int[] weights = sashForm.getWeights();
		assertEquals(2, weights.length);
		assertTrue("The table composite should have a lower relative weight in the sash than the detail",
			weights[0] < weights[1]);
		final Composite gridWrapperComposite = getChild(sashForm, Composite.class, 0);
		final Composite gridComposite = getChild(gridWrapperComposite, Composite.class, 0);
		getChild(gridComposite, Grid.class, 0);
		getChild(sashForm, Composite.class, 1);
	}

	@Test
	public void testGetView() {
		final GridControlDetailPanelRenderer renderer = new GridControlDetailPanelRenderer(mock(VTableControl.class),
			mock(ViewModelContext.class), mock(ReportService.class), mock(EMFFormsDatabindingEMF.class),
			mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			mock(EMFFormsEditSupport.class), mock(EStructuralFeatureValueConverterService.class),
			mock(EMFFormsLocalizationService.class));
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		final VView viewAttribute1 = renderer.getView(eAttribute);
		final VView viewReference = renderer.getView(eReference);
		assertFalse(EcoreUtil.equals(viewAttribute1, viewReference));
		final VView viewAttribute2 = renderer.getView(eAttribute);
		assertTrue(EcoreUtil.equals(viewAttribute1, viewAttribute2));
	}

	private <T> T getChild(Control parent, Class<T> type, int index) {
		assertTrue(MessageFormat.format("The Control {0} is not a composite => No children can be retrieved", parent),
			parent instanceof Composite);
		final Composite composite = (Composite) parent;
		final Control child = composite.getChildren()[index];
		assertTrue(MessageFormat.format("Child {0} is not of required type {1}", child, type.getName()),
			type.isInstance(child));
		return type.cast(child);
	}

	private static class PrintStreamWrapper extends PrintStream {

		private final PrintStream printStream;

		PrintStreamWrapper(PrintStream printStream) {
			super(new ByteArrayOutputStream());
			this.printStream = printStream;
		}

		@Override
		public void print(String s) {
			log = log.concat("\n" + s);
			printStream.print(s + "\n");
		}
	}
}
