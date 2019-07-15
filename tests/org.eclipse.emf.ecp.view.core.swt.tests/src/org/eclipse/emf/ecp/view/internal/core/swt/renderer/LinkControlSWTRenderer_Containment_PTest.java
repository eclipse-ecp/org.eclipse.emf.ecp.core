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
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
 * Unit tests for the {@link LinkControlSWTRenderer} which use a containment {@link EReference}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class LinkControlSWTRenderer_Containment_PTest extends AbstractControl_PTest<VControl> {

	private DefaultRealm realm;
	private EMFFormsLocalizationService localizationService;
	private ImageRegistryService imageRegistryService;
	private ReportService reportService;
	private EReference eReference;
	private EObject eObject;
	private VTViewTemplateProvider templateProvider;

	@Before
	public void before() throws DatabindingFailedException, NoLabelFoundException {
		realm = new DefaultRealm();
		reportService = mock(ReportService.class);
		localizationService = mock(EMFFormsLocalizationService.class);
		when(localizationService.getString(any(Class.class), any(String.class))).thenReturn("TEST"); //$NON-NLS-1$
		when(localizationService.getString(any(Class.class), eq(MessageKeys.LinkControl_AddReference)))
			.thenReturn("Link "); //$NON-NLS-1$
		when(localizationService.getString(any(Class.class), eq(MessageKeys.LinkControl_NewReference)))
			.thenReturn("Create and link new "); //$NON-NLS-1$
		when(localizationService.getString(any(Class.class), eq(MessageKeys.LinkControl_DeleteReference)))
			.thenReturn("Delete"); //$NON-NLS-1$
		imageRegistryService = mock(ImageRegistryService.class);
		setDatabindingService(mock(EMFFormsDatabinding.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		templateProvider = mock(VTViewTemplateProvider.class);
		setTemplateProvider(templateProvider);
		setup();
		setRenderer(new LinkControlSWTRenderer(getvControl(), getContext(), reportService, getDatabindingService(),
			getLabelProvider(), getTemplateProvider(), localizationService, imageRegistryService));
		getRenderer().init();

		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(observableValue.getRealm()).thenReturn(realm);
		when(observableValue.getValueType()).thenReturn(eReference);
		when(observableValue.getObserved()).thenReturn(eObject);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(observableValue);

		final TestObservableValue labelObservable = mock(TestObservableValue.class);
		when(labelObservable.getValue()).thenReturn("Merchandise"); //$NON-NLS-1$
		when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(labelObservable);
	}

	@After
	public void tearDown() {
		realm.dispose();
		dispose();
	}

	@Override
	protected void mockControl() throws DatabindingFailedException {
		eObject = BowlingFactory.eINSTANCE.createFan();
		eReference = BowlingPackage.Literals.FAN__FAVOURITE_MERCHANDISE;
		super.mockControl(eObject, eReference);
	}

	@Test
	public void createAndLinkButton_noReferenceStyle()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button linkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertEquals("Link Merchandise", linkButton.getToolTipText()); //$NON-NLS-1$
		final Button createAndLinkButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertEquals("Create and link new Merchandise", createAndLinkButton.getToolTipText()); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 2, Button.class);
		assertEquals("Delete", deleteButton.getToolTipText()); //$NON-NLS-1$
	}

	/**
	 * For containment references, the 'create and link new' button must also be shown if the reference style property
	 * is set to false.
	 */
	@Test
	public void createAndLinkButton_referenceStyleFalse()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowCreateAndLinkButtonForCrossReferences(false);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button linkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertEquals("Link Merchandise", linkButton.getToolTipText()); //$NON-NLS-1$
		final Button createAndLinkButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertEquals("Create and link new Merchandise", createAndLinkButton.getToolTipText()); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 2, Button.class);
		assertEquals("Delete", deleteButton.getToolTipText()); //$NON-NLS-1$
	}

	/** For containment references, the 'link' button must be shown by default (:= reference style == true). */
	@Test
	public void linkExistingButton_noReferenceStyle()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button linkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertEquals("Link Merchandise", linkButton.getToolTipText()); //$NON-NLS-1$
		final Button createAndLinkButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertEquals("Create and link new Merchandise", createAndLinkButton.getToolTipText()); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 2, Button.class);
		assertEquals("Delete", deleteButton.getToolTipText()); //$NON-NLS-1$
	}

	@Test
	public void linkExistingButton_noReferenceStyle_readOnly()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		getvControl().setReadonly(true);
		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button linkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertFalse("Button visibility for readonly VControl.", linkButton.isVisible()); //$NON-NLS-1$
		final Button createAndLinkButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertFalse("Button visibility for readonly VControl.", createAndLinkButton.isVisible()); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 2, Button.class);
		assertFalse("Button visibility for readonly VControl.", deleteButton.isVisible()); //$NON-NLS-1$
	}

	/**
	 * For containment references, the 'link' button must not be shown if the reference style property
	 * is set to false.
	 */
	@Test
	public void linkButton_referenceStyleFalse()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(false);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		final Button createAndLinkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertEquals("Create and link new Merchandise", createAndLinkButton.getToolTipText()); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertEquals("Delete", deleteButton.getToolTipText()); //$NON-NLS-1$
		try {
			SWTTestUtil.findControl(renderControl, 2, Button.class);
			fail(
				"There must not be a third button for a containment reference with disabled 'link' button."); //$NON-NLS-1$
		} catch (final NoSuchElementException ex) {
			// This is what we expect => Test is successful
			// Cannot use expected in @Test annotation because the test must not succeed if the 'create and link' or the
			// delete button are not found.
		}
	}

	/**
	 * Test that buttons are still created but invisible when the VControl is set to readonly.
	 * This also implicitly tests that no null pointer exception is thrown if not all buttons are rendered.
	 */
	@Test
	public void linkButton_referenceStyleFalse_readOnly()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(false);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));
		getvControl().setReadonly(true);

		final Control renderControl = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());

		// Readonly => Buttons should be present but invisible
		final Button createAndLinkButton = SWTTestUtil.findControl(renderControl, 0, Button.class);
		assertEquals("Create and link new Merchandise", createAndLinkButton.getToolTipText()); //$NON-NLS-1$
		assertFalse("Button visibility for readonly VControl.", createAndLinkButton.isVisible()); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(renderControl, 1, Button.class);
		assertEquals("Delete", deleteButton.getToolTipText()); //$NON-NLS-1$
		assertFalse("Button visibility for readonly VControl.", deleteButton.isVisible()); //$NON-NLS-1$
		try {
			SWTTestUtil.findControl(renderControl, 2, Button.class);
			fail(
				"There must not be a third button for a containment reference with disabled 'link' button."); //$NON-NLS-1$
		} catch (final NoSuchElementException ex) {
			// This is what we expect => Test is successful
			// Cannot use expected in @Test annotation because the test must not succeed if the 'create and link' or the
			// delete button are not found.
		}
	}
}
