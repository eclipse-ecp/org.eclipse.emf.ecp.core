/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.test.common.spi;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealService;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStepKind;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.runner.Description;

/**
 * A convenient fixture for JUnit testing with the {@link EMFFormsRevealService}.
 *
 * @param <T> the type of view context to mock up
 *
 * @since 1.22
 */
public class EMFFormsRevealServiceFixture<T extends EMFFormsViewContext> extends EMFFormsViewContextFixture<T>
	implements EMFFormsRevealService {

	private EMFFormsRevealService service;

	//
	// Creation
	//

	/**
	 * Initializes me.
	 */
	protected EMFFormsRevealServiceFixture(Class<T> contextType,
		Supplier<? extends VElement> viewSupplier, Supplier<? extends EObject> domainModelSupplier) {

		super(contextType, viewSupplier, domainModelSupplier);
	}

	/**
	 * Initializes me.
	 */
	protected EMFFormsRevealServiceFixture(Class<T> contextType, Object owner) {
		super(contextType, owner);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static EMFFormsRevealServiceFixture<EMFFormsViewContext> create() {
		return create(EMFFormsViewContext.class);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static <T extends EMFFormsViewContext> EMFFormsRevealServiceFixture<T> create(Class<T> contextType) {
		return create(contextType, () -> mock(VElement.class), () -> mock(EObject.class));
	}

	/**
	 * Create a new fixture instance.
	 */
	public static EMFFormsRevealServiceFixture<EMFFormsViewContext> create(
		Supplier<? extends VElement> viewSupplier, Supplier<? extends EObject> domainModelSupplier) {

		return create(EMFFormsViewContext.class, viewSupplier, domainModelSupplier);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static <T extends EMFFormsViewContext> EMFFormsRevealServiceFixture<T> create(Class<T> contextType,
		Supplier<? extends VElement> viewSupplier, Supplier<? extends EObject> domainModelSupplier) {

		return new EMFFormsRevealServiceFixture<>(contextType, viewSupplier, domainModelSupplier);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static EMFFormsRevealServiceFixture<EMFFormsViewContext> create(Object owner) {
		return create(EMFFormsViewContext.class, owner);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static <T extends EMFFormsViewContext> EMFFormsRevealServiceFixture<T> create(Class<T> contextType,
		Object owner) {

		return new EMFFormsRevealServiceFixture<>(contextType, owner);
	}

	//
	// Test fixture protocol
	//

	public final EMFFormsRevealService getService() {
		return service;
	}

	//
	// Test lifecycle
	//

	@Override
	protected void starting(Description description) {
		super.starting(description);

		final EMFFormsViewServiceManager serviceManager = getService(EMFFormsViewServiceManager.class);
		service = serviceManager.createGlobalImmediateService(EMFFormsRevealService.class, getViewContext()).get();
	}

	@Override
	protected void finished(Description description) {
		super.finished(description);

		service = null;
	}

	//
	// Service protocol delegation
	//

	@Override
	public boolean reveal(EObject object) {
		return service.reveal(object);
	}

	@Override
	public boolean reveal(EObject object, EStructuralFeature feature) {
		return service.reveal(object, feature);
	}

	@Override
	public RevealStep reveal(EObject object, VElement scope) {
		return service.reveal(object, scope);
	}

	@Override
	public RevealStep reveal(EObject object, EStructuralFeature feature, VElement scope) {
		return reveal(object, feature, scope);
	}

	@Override
	public void addRevealProvider(EMFFormsRevealProvider provider) {
		service.addRevealProvider(provider);
	}

	@Override
	public void removeRevealProvider(EMFFormsRevealProvider provider) {
		service.removeRevealProvider(provider);
	}

	//
	// Test framework
	//

	public static Matcher<RevealStep> isA(RevealStepKind stepKind) {
		return new FeatureMatcher<RevealStep, RevealStepKind>(is(stepKind), "isA", "stepKind") {

			@Override
			protected RevealStepKind featureValueOf(RevealStep actual) {
				return actual.getType();
			}
		};
	}

	@SafeVarargs
	public static <T> Matcher<List<? super T>> isListOf(T... items) {
		return is(Arrays.asList(items));
	}

	public static Runnable pass() {
		return EMFFormsRevealServiceFixture::pass;
	}

	//
	// Nested types
	//

	public static class TestProvider implements EMFFormsRevealProvider {

		private final Double bid;

		/**
		 * Initializes me.
		 */
		public TestProvider() {
			this(1.0);
		}

		/**
		 * Initializes me.
		 */
		public TestProvider(Double bid) {
			super();

			this.bid = bid;
		}

		@Bid
		public Double bid() {
			return bid;
		}

	}

}
