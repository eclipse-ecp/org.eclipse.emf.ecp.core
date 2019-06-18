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
 * Edgar Mueller - initial API and implementation
 * Christian W. Damus - bug 543190
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.scoped;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.internal.core.services.controlmapper.SettingToControlMapperImpl;
import org.eclipse.emfforms.spi.core.services.controlmapper.SubControlMapper;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class SettingToControlMapper_ITest {

	private static final int NR_UPDATES = 100;
	private static final int NR_RUNNABLES = 100;

	private SettingToControlMapperImpl mapper;
	private EObject domainObject;
	private VControl control;
	private FakeViewContext context;
	private EMFFormsMappingProviderManager mappingManager;

	@Before
	public void setup() {
		final VView view = VViewFactory.eINSTANCE.createView();
		control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		view.getChildren().add(control);
		domainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		context = new FakeViewContext(domainObject, view);

		mappingManager = mock(EMFFormsMappingProviderManager.class);
		when(mappingManager.getAllSettingsFor(any(VDomainModelReference.class), isA(EClass.class)))
			.then(new Answer<Set<UniqueSetting>>() {

				@Override
				public Set<UniqueSetting> answer(InvocationOnMock invocation) throws Throwable {
					final Set<UniqueSetting> settings = new LinkedHashSet<UniqueSetting>();
					final Object[] arguments = invocation.getArguments();
					settings.add(
						UniqueSetting.createSetting((EObject) arguments[1],
							EcorePackage.eINSTANCE.getEClass_EAttributes()));
					settings.add(
						UniqueSetting.createSetting((EObject) arguments[1],
							EcorePackage.eINSTANCE.getEClass_EReferences()));
					return settings;
				}
			});
		mapper = new SettingToControlMapperImpl(mappingManager, context);
	}

	@Test
	public void updateMappingsConcurrently() throws InterruptedException {
		// setup
		final CountDownLatch latch = new CountDownLatch(NR_RUNNABLES);

		final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
		final ArrayList<Runnable> runnables = new ArrayList<Runnable>();
		for (int i = 0; i < NR_RUNNABLES; i++) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					try {
						int j = 0;
						while (j < NR_UPDATES) {
							mapper.updateControlMapping(control);
							Thread.sleep(1);
							j += 1;
						}
						// BEGIN SUPRESS CATCH EXCEPTION
					} catch (final Exception exception) {
						exceptions.add(exception);
						// END SUPRESS CATCH EXCEPTION
					} finally {
						latch.countDown();
					}

				}
			});
		}
		final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(runnables.size());

		// act
		for (final Runnable runnable : runnables) {
			newFixedThreadPool.submit(runnable);
		}

		latch.await();

		// assert
		assertTrue(exceptions.isEmpty());
	}

	@Test
	public void getControlsForDirectControlAdd() {
		mapper.vControlAdded(control);
		final Set<VElement> controlsFor = mapper
			.getControlsFor(UniqueSetting.createSetting(domainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
		assertEquals(1, controlsFor.size());
		assertTrue(controlsFor.contains(control));
	}

	@Test
	public void getControlsForChildContextAdd() {
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EObject childDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);
		final Set<VElement> controlsFor = mapper
			.getControlsFor(
				UniqueSetting.createSetting(childDomainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
		assertEquals(2, controlsFor.size());
		assertTrue(controlsFor.contains(control));
		assertTrue(controlsFor.contains(childControl));
	}

	@Test
	public void childContextDisposed() {
		// Add parent control to later verify that it is not illegally removed during child context disposal
		mapper.vControlAdded(control);

		// Setup and add child context with one control and one setting
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EClass childDomainObject = EcoreFactory.eINSTANCE.createEClass();
		childDomainObject.setName("child"); //$NON-NLS-1$
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);

		final UniqueSetting childSettingAttributes = UniqueSetting.createSetting(childDomainObject,
			EcorePackage.eINSTANCE.getEClass_EAttributes());
		final UniqueSetting childSettingReferences = UniqueSetting.createSetting(childDomainObject,
			EcorePackage.eINSTANCE.getEClass_EReferences());
		assertEquals(2, mapper.getControlsFor(childSettingAttributes).size());
		assertEquals(2, mapper.getControlsFor(childSettingReferences).size());

		// Verification that add control and add child context work is done by other test cases.
		mapper.childContextDisposed(childContext);

		// Verify that the mapping for the child setting is empty
		final Set<UniqueSetting> settingsForControl = mapper.getSettingsForControl(childControl);
		assertEquals(0, settingsForControl.size());

		// Verify that after disposing the child context, the mapping from setting to parent control is also removed
		assertEquals(0, mapper.getControlsFor(childSettingAttributes).size());
		assertEquals(0, mapper.getControlsFor(childSettingReferences).size());

		final UniqueSetting parentSetting = UniqueSetting.createSetting(domainObject,
			EcorePackage.eINSTANCE.getEClass_EAttributes());

		// Verify that disposing a child context does not clear the mapping of the parent's control
		final Set<UniqueSetting> settingsForParentControl = mapper.getSettingsForControl(control);
		assertEquals(2, settingsForParentControl.size());
		assertTrue(settingsForParentControl.contains(parentSetting));

		// Verify that disposing a child context does not clear the mapping of the parent's setting
		final Set<VElement> controlsForParentSetting = mapper.getControlsFor(parentSetting);
		assertEquals(1, controlsForParentSetting.size());
		assertTrue(controlsForParentSetting.contains(control));
	}

	@Test
	public void childContextDisposed_NoSetting() {
		// Add parent control to later verify that it is not illegally removed during child context disposal
		mapper.vControlAdded(control);

		// Setup and add child context with one control and one setting
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EClass childDomainObject = EcoreFactory.eINSTANCE.createEClass();
		childDomainObject.setName("child"); //$NON-NLS-1$
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);

		mapper.vControlRemoved(childControl);
		mapper.childContextDisposed(childContext);

	}

	@Test
	public void getControlsForGrandChildContextAdd() {
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EObject childDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);

		final VView grandChildView = VViewFactory.eINSTANCE.createView();
		final VControl grandChildControl = VViewFactory.eINSTANCE.createControl();
		grandChildControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		grandChildView.getChildren().add(grandChildControl);
		final EObject grandChildDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final FakeViewContext grandChildContext = new FakeViewContext(grandChildDomainObject, grandChildView);
		childContext.addChildContext(childControl, grandChildContext);

		final Set<VElement> controlsFor = mapper
			.getControlsFor(
				UniqueSetting.createSetting(grandChildDomainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
		assertEquals(3, controlsFor.size());
		assertTrue(controlsFor.contains(control));
		assertTrue(controlsFor.contains(childControl));
		assertTrue(controlsFor.contains(grandChildControl));
	}

	@Test
	public void getControlsForChildContextPerformance() {

		for (int i = 0; i < 4999; i++) {
			final VView childView = VViewFactory.eINSTANCE.createView();
			final VControl childControl = VViewFactory.eINSTANCE.createControl();
			childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
			childView.getChildren().add(childControl);
			final EObject childDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
			final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
			context.addChildContext(control, childContext);
		}
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EObject childDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);
		final long startTime = System.nanoTime();
		final Set<VElement> controlsFor = mapper
			.getControlsFor(
				UniqueSetting.createSetting(childDomainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
		final long endTime = System.nanoTime();
		assertEquals(2, controlsFor.size());
		assertTrue(controlsFor.contains(control));
		assertTrue(controlsFor.contains(childControl));
		final long durationYs = (endTime - startTime) / 1000;
		// half a millisecond should be more then enough time
		assertTrue(MessageFormat.format("Duration was {0}.", durationYs), durationYs < 500); //$NON-NLS-1$
	}

	@Test
	public void childContextDisposed_533827_InfiniteLoop() {
		// Add parent control to later verify that it is not illegally removed during child context disposal
		mapper.vControlAdded(control);

		// Setup and add child context with one control and one setting
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EClass childDomainObject = EcoreFactory.eINSTANCE.createEClass();
		childDomainObject.setName("child"); //$NON-NLS-1$
		final Set<UniqueSetting> uniqueSettings = Collections.emptySet();
		when(mappingManager.getAllSettingsFor(same(childControl.getDomainModelReference()), any(EObject.class)))
			.thenReturn(uniqueSettings);
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);

		// Verification that add control and add child context work is done by other test cases.
		// mapper.vControlRemoved(childControl);
		mapper.childContextDisposed(childContext);

		final UniqueSetting parentSetting = UniqueSetting.createSetting(domainObject,
			EcorePackage.eINSTANCE.getEClass_EAttributes());

		// Verify that disposing a child context does not clear the mapping of the parent's control
		final Set<UniqueSetting> settingsForParentControl = mapper.getSettingsForControl(control);
		assertEquals(2, settingsForParentControl.size());
		assertTrue(settingsForParentControl.contains(parentSetting));

		// Verify that disposing a child context does not clear the mapping of the parent's setting
		final Set<VElement> controlsForParentSetting = mapper.getControlsFor(parentSetting);
		assertEquals(1, controlsForParentSetting.size());
		assertTrue(controlsForParentSetting.contains(control));
	}

	/**
	 * Test case for {@link SubControlMapper#getEObjectsWithSettings(VElement)} implementation.
	 */
	@Test
	@SuppressWarnings("nls")
	public void getEObjectsWithSettings_VElement() {
		// Create a child view for the EClass showing the name of its ID attribute
		// and the name of that ID attribute's type. These are two objects, one
		// contained and one not, that are presented by the child context
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl attributeControl = VViewFactory.eINSTANCE.createControl();
		attributeControl.setDomainModelReference(EcorePackage.Literals.ENAMED_ELEMENT__NAME);
		childView.getChildren().add(attributeControl);
		final VControl isIdControl = VViewFactory.eINSTANCE.createControl();
		isIdControl.setDomainModelReference(EcorePackage.Literals.EATTRIBUTE__ID);
		childView.getChildren().add(isIdControl); // This one doesn't add anything interesting
		final VControl typeNameControl = VViewFactory.eINSTANCE.createControl();
		typeNameControl.setDomainModelReference(EcorePackage.Literals.ENAMED_ELEMENT__NAME,
			Arrays.asList(EcorePackage.Literals.EATTRIBUTE__EATTRIBUTE_TYPE));
		childView.getChildren().add(typeNameControl);

		final EAttribute idAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		idAttribute.setName("id");
		idAttribute.setID(true);
		idAttribute.setEType(EcorePackage.Literals.ESTRING);
		((EClass) domainObject).getEStructuralFeatures().add(idAttribute);

		// Update mocking for the new DMRs
		when(mappingManager.getAllSettingsFor(attributeControl.getDomainModelReference(),
			idAttribute))
				.thenReturn(set(UniqueSetting.createSetting(idAttribute, EcorePackage.Literals.ENAMED_ELEMENT__NAME)));
		when(mappingManager.getAllSettingsFor(isIdControl.getDomainModelReference(),
			idAttribute))
				.thenReturn(
					set(UniqueSetting.createSetting(idAttribute, EcorePackage.Literals.EATTRIBUTE__EATTRIBUTE_TYPE)));
		when(mappingManager.getAllSettingsFor(typeNameControl.getDomainModelReference(),
			idAttribute))
				.thenReturn(set(UniqueSetting.createSetting(EcorePackage.Literals.ESTRING,
					EcorePackage.Literals.ENAMED_ELEMENT__NAME)));

		final FakeViewContext childContext = new FakeViewContext(idAttribute, childView);
		context.addChildContext(control, childContext);
		final Collection<EObject> objects = mapper.getEObjectsWithSettings(childView);
		assertThat("Wrong number of objects in child context", objects.size(), is(2));
		assertThat(objects, hasItems(idAttribute, EcorePackage.Literals.ESTRING));
	}

	@SafeVarargs
	static <T> Set<T> set(T... elements) {
		return new LinkedHashSet<>(Arrays.asList(elements));
	}

}
