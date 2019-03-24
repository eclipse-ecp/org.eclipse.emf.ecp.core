/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.test.common.PerformanceClock;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.table.test.common.TableTestUtil;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Performance tests for the {@link TableControlSWTRenderer}.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(DatabindingClassRunner.class)
public class TableControlSWTRendererPerformance_PTest {

	private static final int SMALL_SCALE = 50;
	private static final int LARGE_SCALE = 2000;
	// sorting takes O(n*log n)
	private static final int ITERATIONS = 8;
	private static final double WORST_CASE_MULTIPLIER = 20.0;

	private Shell shell;
	private ComposedAdapterFactory adapterFactory;
	private Resource smallResource;
	private Resource largeResource;

	@Before
	public void init() {
		shell = SWTViewTestHelper.createShell();

		// Add domain element to resource with editing domain
		final ResourceSetImpl rs = new ResourceSetImpl();
		adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
			adapterFactory, new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(editingDomain));
		smallResource = rs.createResource(URI.createURI("VIRTUAL_SMALL"));
		largeResource = rs.createResource(URI.createURI("VIRTUAL_BIG"));
	}

	@After
	public void after() {
		adapterFactory.dispose();
		if (shell != null && !shell.isDisposed()) {
			shell.dispose();
		}
	}

	@Test
	public void tableSorting_autoSortOnEdit()
		throws EMFFormsNoRendererException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		// table control
		final VTableControl tableControl = TableTestUtil.createTableControl();
		final VTableDomainModelReference tableDMR = (VTableDomainModelReference) tableControl.getDomainModelReference();
		tableDMR.setDomainModelEFeature(EcorePackage.eINSTANCE.getEClass_EAttributes());
		tableDMR.getColumnDomainModelReferences().add(createDMR(EcorePackage.eINSTANCE.getENamedElement_Name()));

		// render
		shell.open();

		test(domain -> {
			Control control = null;
			try {
				control = SWTViewTestHelper.render(tableControl, domain, shell);
			} catch (NoRendererFoundException | NoPropertyDescriptorFoundExeption | EMFFormsNoRendererException ex) {
				fail("An exception occurred while rendering the table: " + ex.getMessage());
			}
			if (control == null) {
				fail("No control was rendered");
			}
			final Table table = SWTTestUtil.findControl(control, 0, Table.class);

			// column 0 is validation column
			// select column 1 (name) and ascending sorting
			SWTTestUtil.selectWidget(table.getColumns()[1]);
			SWTTestUtil.waitForUIThread();
			assertEquals(SWT.DOWN, table.getSortDirection()); // SWT.DOWN := ascending sorting

			final EAttribute start = (EAttribute) table.getItem(0).getData();
			start.setName("zzz");

			final EAttribute middle = (EAttribute) table.getItem(table.getItemCount() / 2).getData();
			middle.setName("aaa");
		});
	}

	private void test(Consumer<EClass> experiment) {
		final EClass smallInput = generateInput(SMALL_SCALE);
		smallResource.getContents().clear();
		smallResource.getContents().add(smallInput);
		final EClass largeInput = generateInput(LARGE_SCALE);
		largeResource.getContents().clear();
		largeResource.getContents().add(largeInput);
		PerformanceClock.test(ITERATIONS, WORST_CASE_MULTIPLIER, () -> smallInput, () -> largeInput, experiment);
	}

	private static EClass generateInput(int childCount) {
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		final Random random = new Random(System.nanoTime());
		final int nameLength = 10;
		for (int i = 0; i < childCount; i++) {
			final char[] result = new char[nameLength];
			for (int j = 0; j < nameLength; j++) {
				result[j] = (char) (random.nextInt('z' - 'A') + 'A');
			}
			final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
			attribute.setEType(EcorePackage.Literals.ESTRING);
			attribute.setName(new String(result));
			eClass.getEStructuralFeatures().add(attribute);
		}
		return eClass;
	}

	private static VFeaturePathDomainModelReference createDMR(EAttribute attribute, EReference... refs) {
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.setDomainModelEFeature(attribute);
		dmr.getDomainModelEReferencePath().addAll(Arrays.asList(refs));
		return dmr;
	}
}
