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
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceFactory;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty;
import org.eclipse.emf.ecp.view.test.common.swt.SWTTestUtil;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link LinkControlSWTRenderer} which use a non-containment {@link EReference}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class LinkControlSWTRenderer_CrossReference_PTest extends AbstractControl_PTest<VControl> {

	private DefaultRealm realm;
	private EReference eReference;
	private EObject eObject;
	private VTViewTemplateProvider templateProvider;

	@Before
	public void before() throws DatabindingFailedException, NoLabelFoundException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);
		final EMFFormsLocalizationService localizationService = mock(EMFFormsLocalizationService.class);
		when(localizationService.getString(any(Class.class), any(String.class))).thenReturn("TEST");
		when(localizationService.getString(any(Class.class), eq(MessageKeys.LinkControl_AddReference)))
			.thenReturn("Link ");
		when(localizationService.getString(any(Class.class), eq(MessageKeys.LinkControl_NewReference)))
			.thenReturn("Create and link new ");
		when(localizationService.getString(any(Class.class), eq(MessageKeys.LinkControl_DeleteReference)))
			.thenReturn("Delete");
		final ImageRegistryService imageRegistryService = mock(ImageRegistryService.class);
		final EMFFormsEditSupport editSupport = mock(EMFFormsEditSupport.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		templateProvider = mock(VTViewTemplateProvider.class);
		setTemplateProvider(templateProvider);
		setup();
		setRenderer(new LinkControlSWTRenderer(getvControl(), getContext(), reportService, getDatabindingService(),
			getLabelProvider(), getTemplateProvider(), localizationService, imageRegistryService, editSupport));
		getRenderer().init();

		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(observableValue.getRealm()).thenReturn(realm);
		when(observableValue.getValueType()).thenReturn(eReference);
		when(observableValue.getObserved()).thenReturn(eObject);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(observableValue);

		final TestObservableValue labelObservable = mock(TestObservableValue.class);
		when(labelObservable.getValue()).thenReturn("Player");
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(labelObservable);

	}

	@After
	public void tearDown() {
		realm.dispose();
		dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.swt.common.test.AbstractControl_PTest#mockControl()
	 */
	@Override
	protected void mockControl() throws DatabindingFailedException {
		eObject = BowlingFactory.eINSTANCE.createFan();
		eReference = BowlingPackage.Literals.FAN__FAVOURITE_PLAYER;
		super.mockControl(eObject, eReference);
	}

	/**
	 * By default, the 'create and link new' button should be shown for cross references (legacy behavior).
	 */
	@Test
	public void createAndLinkButton_noReferenceStyleProperty()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button linkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertEquals("Link Player", linkButton.getToolTipText());
		final Button createAndLinkButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertEquals("Create and link new Player", createAndLinkButton.getToolTipText());
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 2, Button.class);
		assertEquals("Delete", deleteButton.getToolTipText());
	}

	@Test
	public void createAndLinkButton_referenceStylePropertyTrue()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowCreateAndLinkButtonForCrossReferences(true);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button linkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertEquals("Link Player", linkButton.getToolTipText());
		final Button createAndLinkButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertEquals("Create and link new Player", createAndLinkButton.getToolTipText());
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 2, Button.class);
		assertEquals("Delete", deleteButton.getToolTipText());
	}

	@Test
	public void createAndLinkButton_referenceStylePropertyFalse()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowCreateAndLinkButtonForCrossReferences(false);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button linkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertEquals("Link Player", linkButton.getToolTipText());
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertEquals("Delete", deleteButton.getToolTipText());
		try {
			SWTTestUtil.findControl(renderControl, 2, Button.class);
			fail(
				"There must not be a third button for a non-containment reference with disabled 'create and link' button."); //$NON-NLS-1$
		} catch (final NoSuchElementException ex) {
			// This is what we expect => Test is successful
			// Cannot use expected in @Test annotation because the test must not succeed if the link or the delete
			// button are not found.
		}
	}
}
