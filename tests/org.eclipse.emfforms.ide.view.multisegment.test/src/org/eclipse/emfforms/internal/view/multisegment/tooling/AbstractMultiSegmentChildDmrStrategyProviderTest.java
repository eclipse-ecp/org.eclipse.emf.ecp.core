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
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Abstract base class for unit tests for multi segment child dmr reference strategy providers.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public abstract class AbstractMultiSegmentChildDmrStrategyProviderTest<T extends ReferenceServiceCustomizationVendor<?> & TestableStrategyProvider> {

	/** Multi segment contained in a view model that's part of a resource with editing domain. */
	private VMultiDomainModelReferenceSegment multiSegment;
	/** DMR containing the multi segment */
	private VDomainModelReference parentDmr;
	private T strategyProvider;

	protected VMultiDomainModelReferenceSegment getMultiSegment() {
		return multiSegment;
	}

	protected void setMultiSegment(VMultiDomainModelReferenceSegment multiSegment) {
		this.multiSegment = multiSegment;
	}

	protected VDomainModelReference getParentDmr() {
		return parentDmr;
	}

	protected void setParentDmr(VDomainModelReference parentDmr) {
		this.parentDmr = parentDmr;
	}

	/** @return the tested strategy provider */
	protected T getStrategyProvider() {
		return strategyProvider;
	}

	/** @see #getStrategyProvider() */
	protected void setStrategyProvider(T strategyProvider) {
		this.strategyProvider = strategyProvider;
	}

	@Before
	public void setUp() {
		// Create view model with a multi segment
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(EcorePackage.Literals.ECLASS);
		final VTableControl tableControl = VTableFactory.eINSTANCE.createTableControl();
		parentDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		parentDmr.getSegments().add(multiSegment);
		tableControl.setDomainModelReference(parentDmr);
		view.getChildren().add(tableControl);

		// Add the view to a resource with editing domain
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
			adapterFactory, new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(editingDomain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
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
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		assertTrue(executeHandles(segment,
			VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES));
	}

	@Test
	public void handles_generationEnabled_incorrectReference() {
		strategyProvider.setSegmentToolingEnabled(true);
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		assertFalse(executeHandles(segment, EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES));
	}

	@Test
	public void handles_generationEnabled_incorrectSegment() {
		strategyProvider.setSegmentToolingEnabled(true);
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		assertFalse(executeHandles(segment,
			VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES));
	}

	@Test
	public void handles_generationEnabled_incorrectBoth() {
		strategyProvider.setSegmentToolingEnabled(true);
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		assertFalse(executeHandles(segment, EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES));
	}

	@Test
	public void handles_generationDisabled() {
		strategyProvider.setSegmentToolingEnabled(false);
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		assertFalse(executeHandles(segment,
			VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES));
	}
}
