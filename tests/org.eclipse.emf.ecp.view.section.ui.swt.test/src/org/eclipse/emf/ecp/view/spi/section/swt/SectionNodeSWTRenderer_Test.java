/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.section.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SectionNodeSWTRenderer_Test {

	private DefaultRealm realm;

	@Before
	public void before() {
		realm = new DefaultRealm();
	}

	@After
	public void after() {
		realm.dispose();
	}

	@Test
	public void getGridDescription() throws EMFFormsNoRendererException {
		/* setup */
		final VSection section = Create.section()
			.withName("S1")
			.withContent(
				Create.control("S1.C1"))
			.withChildren(
				Create.section()
					.withName("S1.1")
					.withContent(
						Create.control("S1.1.C1"),
						Create.control("S1.1.C2"))
					.withChildren(
						Create.section()
							.withName("S1.1.1")
							.withContent(
								Create.control("S1.1.1.C1"))),
				Create.section()
					.withName("S1.2")
					.withContent(
						Create.control("S1.2.C1"),
						Create.control("S1.2.C2"),
						Create.control("S1.2.C3")))
			.andReturn();

		final ViewModelContext viewModelContext = Mockito.mock(ViewModelContext.class);
		final ReportService reportService = Mockito.mock(ReportService.class);
		final VTViewTemplateProvider viewTemplateProvider = Mockito.mock(VTViewTemplateProvider.class);

		final EMFFormsRendererFactory rendererFactory = Mockito.mock(EMFFormsRendererFactory.class);
		Mockito.doReturn(rendererFactory).when(viewModelContext).getService(EMFFormsRendererFactory.class);

		final SectionNodeSWTRenderer s11 = new SectionNodeSWTRenderer((VSection) Get.byName(section, "S1.1"),
			viewModelContext, reportService, viewTemplateProvider);
		Mockito.doReturn(s11).when(rendererFactory).getRendererInstance(Get.byName(section, "S1.1"), viewModelContext);

		final SectionLeafSWTRenderer s111 = new SectionLeafSWTRenderer((VSection) Get.byName(section, "S1.1.1"),
			viewModelContext, reportService, viewTemplateProvider);
		Mockito.doReturn(s111).when(rendererFactory).getRendererInstance(Get.byName(section, "S1.1.1"),
			viewModelContext);

		final SectionLeafSWTRenderer s12 = new SectionLeafSWTRenderer((VSection) Get.byName(section, "S1.2"),
			viewModelContext, reportService, viewTemplateProvider);
		Mockito.doReturn(s12).when(rendererFactory).getRendererInstance(Get.byName(section, "S1.2"), viewModelContext);

		final VTLabelWidthStyleProperty labelWidthStyleProperty = Mockito.mock(VTLabelWidthStyleProperty.class);
		Mockito.doReturn(true).when(labelWidthStyleProperty).isSetWidth();
		Mockito.doReturn(137).when(labelWidthStyleProperty).getWidth();

		Mockito.doReturn(Collections.singleton(labelWidthStyleProperty)).when(viewTemplateProvider)
			.getStyleProperties(section, viewModelContext);
		Mockito.doReturn(Collections.singleton(labelWidthStyleProperty)).when(viewTemplateProvider)
			.getStyleProperties(Get.byName(section, "S1.1"), viewModelContext);
		Mockito.doReturn(Collections.singleton(labelWidthStyleProperty)).when(viewTemplateProvider)
			.getStyleProperties(Get.byName(section, "S1.1.1"), viewModelContext);
		Mockito.doReturn(Collections.singleton(labelWidthStyleProperty)).when(viewTemplateProvider)
			.getStyleProperties(Get.byName(section, "S1.2"), viewModelContext);

		final SectionNodeSWTRenderer sectionNodeSWTRenderer = new SectionNodeSWTRenderer(section, viewModelContext,
			reportService, viewTemplateProvider);

		/* act */
		final SWTGridDescription gridDescription = sectionNodeSWTRenderer.getGridDescription(null);

		/* assert */
		assertEquals(4, gridDescription.getColumns());
		assertEquals(4, gridDescription.getRows());
		for (final SWTGridCell gridCell : gridDescription.getGrid()) {
			if (gridCell.getColumn() == 0) {
				assertEquals(137, gridCell.getPreferredSize().x);
			} else {
				assertNull(gridCell.getPreferredSize());
			}
		}

	}

	static class Get {

		static VElement byName(EObject root, String name) {
			final TreeIterator<EObject> allContents = root.eAllContents();
			while (allContents.hasNext()) {
				final EObject next = allContents.next();
				if (VElement.class.isInstance(next)) {
					final VElement element = VElement.class.cast(next);
					if (name.equals(element.getName())) {
						return element;
					}
				}
			}
			throw new NoSuchElementException();
		}
	}

	static class Create {

		static SectionBuilder section() {
			return new SectionBuilder();
		}

		static VControl control(String name) {
			final VControl control = VViewFactory.eINSTANCE.createControl();
			control.setName(name);
			return control;
		}

		static class SectionBuilder {

			private final VSection result = VSectionFactory.eINSTANCE.createSection();

			VSection andReturn() {
				return result;
			}

			SectionBuilder withName(String name) {
				result.setName(name);
				return this;
			}

			SectionBuilder withContent(VContainedElement... element) {
				result.getChildren().addAll(Arrays.asList(element));
				return this;
			}

			SectionBuilder withChildren(SectionBuilder... section) {
				for (final SectionBuilder sectionBuilder : section) {
					result.getChildItems().add(sectionBuilder.andReturn());
				}
				return this;
			}

		}
	}

}
