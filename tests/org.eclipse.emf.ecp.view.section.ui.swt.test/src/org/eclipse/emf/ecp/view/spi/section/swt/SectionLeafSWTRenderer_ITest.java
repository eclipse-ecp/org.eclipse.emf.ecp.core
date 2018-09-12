/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.section.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionFactory;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTControlLabelAlignmentStyleProperty;
import org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SectionLeafSWTRenderer_ITest {

	private DefaultRealm realm;
	private Shell shell;

	@Before
	public void before() {
		realm = new DefaultRealm();
		shell = new Shell();
	}

	@After
	public void after() {
		shell.dispose();
		realm.dispose();
	}

	@Test
	public void createFirstColumn() {
		/* setup */
		final VSectionedArea sectionedArea = VSectionFactory.eINSTANCE.createSectionedArea();
		final VSection section = VSectionFactory.eINSTANCE.createSection();
		sectionedArea.setRoot(section);

		final ViewModelContext viewModelContext = Mockito.mock(ViewModelContext.class);
		final ReportService reportService = Mockito.mock(ReportService.class);
		final VTViewTemplateProvider viewTemplateProvider = Mockito.mock(VTViewTemplateProvider.class);

		final VTLabelWrapStyleProperty wrapStyleProperty = Mockito.mock(VTLabelWrapStyleProperty.class);
		Mockito.doReturn(true).when(wrapStyleProperty).isWrapLabel();

		final VTControlLabelAlignmentStyleProperty labelAlignmentStyleProperty = Mockito
			.mock(VTControlLabelAlignmentStyleProperty.class);
		Mockito.doReturn(AlignmentType.RIGHT).when(labelAlignmentStyleProperty).getType();

		Mockito
			.doReturn(new LinkedHashSet<VTStyleProperty>(
				Arrays.asList(wrapStyleProperty, labelAlignmentStyleProperty)))
			.when(viewTemplateProvider)
			.getStyleProperties(section, viewModelContext);

		final SectionLeafSWTRenderer sectionLeafSWTRenderer = new SectionLeafSWTRenderer(section, viewModelContext,
			reportService, viewTemplateProvider);

		/* act */
		final Control firstColumn = sectionLeafSWTRenderer.createFirstColumn(shell);

		/* assert */
		final Label label = (Label) Composite.class.cast(firstColumn).getChildren()[0];
		assertNotEquals(0, label.getStyle() & SWT.WRAP);
		assertEquals(SWT.RIGHT, label.getAlignment());
	}

}
