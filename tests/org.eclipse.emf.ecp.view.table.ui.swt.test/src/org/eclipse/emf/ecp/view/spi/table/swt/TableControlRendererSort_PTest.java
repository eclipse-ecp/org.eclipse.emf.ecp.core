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
 * eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.table.test.common.TableTestUtil;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author eugen
 *
 */
@RunWith(DatabindingClassRunner.class)
public class TableControlRendererSort_PTest {

	private static class TableControlSWTRendererSort extends TableControlSWTRenderer {

		@Inject
		// CHECKSTYLE.OFF: ParameterNumber
		TableControlSWTRendererSort(VTableControl vElement, ViewModelContext viewContext,
			ReportService reportService,
			EMFFormsDatabindingEMF emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider, ImageRegistryService imageRegistryService,
			EMFFormsEditSupport emfFormsEditSupport) {
			// CHECKSTYLE.ON: ParameterNumber
			super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider,
				vtViewTemplateProvider,
				imageRegistryService, emfFormsEditSupport);
		}

		@Override
		protected TableControlComparator createTableViewerComparator() {
			// CHECKSTYLE.OFF: AnonInnerLength
			return new TableControlComparator() {
				private int propertyIndex;
				private int direction;

				@Override
				public int getDirection() {
					switch (direction) {
					case 0:
						return SWT.DOWN; // ascending
					case 1:
						return SWT.UP; // descending
					default:
						return SWT.DOWN;
					}
				}

				@Override
				public void setColumn(int column) {
					if (column == propertyIndex) {
						// Same column as last sort; toggle the direction
						direction = (direction + 1) % 2;
					} else {
						// New column; do an ascending sort
						propertyIndex = column;
						direction = 0;
					}
					// columnFeatures starts at index 0 with the first regular column
					setSortColumnFeature(getColumnFeature(propertyIndex));
				}

				@Override
				public int compare(Viewer viewer, Object e1, Object e2) {
					return TableControlSWTRendererSort.this.compare(viewer, e1, e2, direction + 1, propertyIndex);
				}
			};
			// CHECKSTYLE.ON: AnonInnerLength
		}

		@Override
		protected Control renderTableControl(SWTGridCell gridCell, Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
			final Control result = super.renderTableControl(gridCell, parent);
			getTableViewerComposite().setCompareColumn(1);
			return result;
		}

	}

	private static class TableControlSWTRendererSortService implements EMFFormsDIRendererService<VTableControl> {

		@Override
		public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
			if (!VTableControl.class.isInstance(vElement)) {
				return NOT_APPLICABLE;
			}
			final VTableControl tableControl = (VTableControl) vElement;
			final VTableDomainModelReference domainRef = (VTableDomainModelReference) tableControl
				.getDomainModelReference();
			if (domainRef.getDomainModelEFeature() == EcorePackage.eINSTANCE.getEClass_EStructuralFeatures()) {
				return Double.MAX_VALUE;
			}
			return NOT_APPLICABLE;
		}

		@Override
		public Class<? extends AbstractSWTRenderer<VTableControl>> getRendererClass() {
			return TableControlSWTRendererSort.class;
		}

	}

	private static ServiceRegistration<EMFFormsDIRendererService> serviceRegistration;
	private Shell shell;

	@BeforeClass
	public static void beforeClass() {
		final Bundle bundle = FrameworkUtil.getBundle(TableControlRendererSort_PTest.class);
		final BundleContext bundleContext = bundle.getBundleContext();
		final Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put(Constants.SERVICE_RANKING, Double.MAX_VALUE);
		serviceRegistration = bundleContext.registerService(EMFFormsDIRendererService.class,
			new TableControlSWTRendererSortService(), properties);
	}

	@AfterClass
	public static void afterClass() {
		serviceRegistration.unregister();
	}

	@Before
	public void init() {
		shell = SWTViewTestHelper.createShell();
	}

	@After
	public void after() {
		if (shell != null && !shell.isDisposed()) {
			shell.dispose();
		}
	}

	@Test
	public void tableCustomSort()
		throws EMFFormsNoRendererException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		// table control
		final VTableControl tableControl = TableTestUtil.createTableControl();
		final VTableDomainModelReference tableDMR = (VTableDomainModelReference) tableControl.getDomainModelReference();
		tableDMR.setDomainModelEFeature(EcorePackage.eINSTANCE.getEClass_EStructuralFeatures());
		tableDMR.getColumnDomainModelReferences().add(createDMR(EcorePackage.eINSTANCE.getENamedElement_Name()));

		// render
		shell.open();

		final EClass domain = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute att1 = EcoreFactory.eINSTANCE.createEAttribute();
		att1.setName("zzz");
		final EAttribute att2 = EcoreFactory.eINSTANCE.createEAttribute();
		att2.setName("aaa");
		domain.getEStructuralFeatures().add(att1);
		domain.getEStructuralFeatures().add(att2);

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

		SWTTestUtil.waitForUIThread();
		assertEquals(SWT.DOWN, table.getSortDirection()); // SWT.DOWN := ascending sorting

		final EAttribute first = (EAttribute) table.getItem(0).getData();
		assertEquals("aaa", first.getName());

		final EAttribute second = (EAttribute) table.getItem(1).getData();
		assertEquals("zzz", second.getName());
	}

	private static VFeaturePathDomainModelReference createDMR(EAttribute attribute, EReference... refs) {
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.setDomainModelEFeature(attribute);
		dmr.getDomainModelEReferencePath().addAll(Arrays.asList(refs));
		return dmr;
	}
}
