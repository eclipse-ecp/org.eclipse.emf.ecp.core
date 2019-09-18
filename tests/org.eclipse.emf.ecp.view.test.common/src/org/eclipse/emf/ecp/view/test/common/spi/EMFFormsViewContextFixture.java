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

import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * A convenient fixture for JUnit testing with a mock {@link EMFFormsViewContext}.
 *
 * @param <T> the type of view context to mock up
 *
 * @since 1.22
 */
public class EMFFormsViewContextFixture<T extends EMFFormsViewContext> extends TestWatcher {

	private final Class<T> contextType;
	private final Supplier<? extends VElement> viewSupplier;
	private final Supplier<? extends EObject> domainModelSupplier;

	private T viewContext;

	private IEclipseContext e4Context;

	private final Map<Class<?>, Object> lazyServices = new HashMap<>();
	private final Set<T> childContexts = new HashSet<>();
	private final Set<EMFFormsContextListener> capturedListeners = new HashSet<>();

	/**
	 * Initializes me.
	 */
	protected EMFFormsViewContextFixture(Class<T> contextType,
		Supplier<? extends VElement> viewSupplier, Supplier<? extends EObject> domainModelSupplier) {

		super();

		this.contextType = contextType;
		this.viewSupplier = viewSupplier;
		this.domainModelSupplier = domainModelSupplier;
	}

	/**
	 * Initializes me.
	 */
	protected EMFFormsViewContextFixture(Class<T> contextType, Object owner) {
		this(contextType, fieldSupplier(owner, ViewModel.class, VElement.class),
			fieldSupplier(owner, DomainModel.class, EObject.class));
	}

	//
	// Creation
	//

	/**
	 * Create a new fixture instance.
	 */
	public static EMFFormsViewContextFixture<EMFFormsViewContext> create() {
		return create(EMFFormsViewContext.class);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static <T extends EMFFormsViewContext> EMFFormsViewContextFixture<T> create(Class<T> contextType) {
		return create(contextType, () -> mock(VElement.class), () -> mock(EObject.class));
	}

	/**
	 * Create a new fixture instance.
	 */
	public static EMFFormsViewContextFixture<EMFFormsViewContext> create(
		Supplier<? extends VElement> viewSupplier, Supplier<? extends EObject> domainModelSupplier) {

		return create(EMFFormsViewContext.class, viewSupplier, domainModelSupplier);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static <T extends EMFFormsViewContext> EMFFormsViewContextFixture<T> create(Class<T> contextType,
		Supplier<? extends VElement> viewSupplier, Supplier<? extends EObject> domainModelSupplier) {

		return new EMFFormsViewContextFixture<>(contextType, viewSupplier, domainModelSupplier);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static EMFFormsViewContextFixture<EMFFormsViewContext> create(Object owner) {
		return create(EMFFormsViewContext.class, owner);
	}

	/**
	 * Create a new fixture instance.
	 */
	public static <T extends EMFFormsViewContext> EMFFormsViewContextFixture<T> create(Class<T> contextType,
		Object owner) {

		return new EMFFormsViewContextFixture<>(contextType, owner);
	}

	//
	// Test fixture protocol
	//

	public final VElement getViewModel() {
		return viewContext.getViewModel();
	}

	public final EObject getDomainModel() {
		return viewContext.getDomainModel();
	}

	public final T getViewContext() {
		return viewContext;
	}

	public final <S> S getService(Class<S> serviceType) {
		return viewContext.getService(serviceType);
	}

	public final <S> void putService(Class<S> serviceType, S service) {
		// This we get picked up by the mock view service
		e4Context.set(serviceType, service);

		initService(service);
	}

	private void initService(Object service) {
		// ViewModelService requires "instantiation"
		if (!instantiate(service, true)) {
			instantiate(service, false);
		}
	}

	private boolean instantiate(Object service, boolean withContext) {
		final Method instantiate;
		try {
			if (withContext) {
				instantiate = service.getClass().getMethod("instantiate", contextType);
				instantiate.invoke(service, viewContext);
				return true;
			}
			instantiate = service.getClass().getMethod("instantiate");
			instantiate.invoke(service);
			return true;
		} catch (NoSuchMethodException | IllegalAccessException e) {
			// These are normal when it's not a ViewModelService
		} catch (SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// These are not normal, but we've made the effort and will let the test proceed
			e4Context.get(ReportService.class).report(new AbstractReport(e));
		}
		return false;
	}

	public T createContext(String name, VElement viewModel, EObject domainModel) {
		final T result = mock(contextType, name);

		when(result.getService(ReportService.class)).thenReturn(mock(ReportService.class));
		when(result.getService(argThat(not(IEclipseContext.class)))).thenAnswer(
			invocation -> bestEffortService((Class<?>) invocation.getArguments()[0]));
		when(result.getService(IEclipseContext.class)).thenReturn(e4Context);

		when(result.getViewModel()).thenReturn(viewModel);
		when(result.getDomainModel()).thenReturn(domainModel);

		return result;
	}

	private <S> S bestEffortService(Class<S> serviceType) {
		S result = serviceType.cast(lazyServices.get(serviceType));

		if (result == null) {
			result = e4Context.get(serviceType);
			if (result != null) {
				initService(result);
				lazyServices.put(serviceType, result);
			}
		}

		if (result == null) {
			// Try the service manager
			final EMFFormsViewServiceManager mgr = getEMFFormsViewServiceManager();

			// These are de facto lazy and local to this mock
			org.eclipse.emfforms.common.Optional<S> service = mgr.createLocalLazyService(serviceType,
				viewContext);
			if (!service.isPresent()) {
				service = mgr.createLocalImmediateService(serviceType, viewContext);
			}
			if (service.isPresent()) {
				result = service.get();
				initService(result);
				lazyServices.put(serviceType, result);
			}
		}

		return result;
	}

	/**
	 * Get the view-model service manager, or a mock substitute, for delegation of
	 * services via the factory OSGi components.
	 *
	 * @return the best-effort service manager
	 */
	private EMFFormsViewServiceManager getEMFFormsViewServiceManager() {
		EMFFormsViewServiceManager result = (EMFFormsViewServiceManager) lazyServices
			.get(EMFFormsViewServiceManager.class);
		if (result == null) {
			result = e4Context.get(EMFFormsViewServiceManager.class);
			if (result != null) {
				initService(result);
			} else {
				result = mock(EMFFormsViewServiceManager.class);
			}

			// Cache the result to avoid unbounded recursion
			lazyServices.put(EMFFormsViewServiceManager.class, result);
		}

		return result;
	}

	/**
	 * Initialize the legacy services manager, which injects view-model services from
	 * the extension point as service-factory OSGi components.
	 */
	private void initializeLegacyServices() {
		final Object legacyServicesManager = e4Context
			.get("org.eclipse.emf.ecp.view.spi.context.EMFFormsLegacyServicesManager");
		if (legacyServicesManager != null) {
			initService(legacyServicesManager);
		}
	}

	public T createChildContext(VElement parentElement, String name,
		VElement viewModel, EObject domainModel) {

		final T result = createContext(name, viewModel, domainModel);

		if (childContexts.add(result)) {
			capturedListeners.forEach(safeRun(l -> l.childContextAdded(parentElement, result)));
		}

		return result;
	}

	private <V> Consumer<V> safeRun(Consumer<? super V> action) {
		return input -> SafeRunner.run(() -> action.accept(input));
	}

	public void disposeChildContext(T childContext) {
		if (childContexts.remove(childContext)) {
			capturedListeners.forEach(safeRun(l -> l.childContextDisposed(childContext)));
		}
	}

	private static <V> Supplier<V> fieldSupplier(Object owner,
		Class<? extends Annotation> annotationType, Class<V> type) {

		for (final Field next : owner.getClass().getDeclaredFields()) {
			if (next.isAnnotationPresent(annotationType) && type.isAssignableFrom(type)) {
				return () -> {
					final boolean wasAccessible = next.isAccessible();
					next.setAccessible(true);
					try {
						try {
							return type.cast(next.get(owner));
						} catch (final IllegalAccessException e) {
							return null;
						}
					} finally {
						next.setAccessible(wasAccessible);
					}
				};
			}
		}

		return () -> null;
	}

	//
	// Test lifecycle
	//

	@Override
	protected void starting(Description description) {
		final Bundle self = FrameworkUtil.getBundle(EMFFormsViewContextFixture.class);
		e4Context = EclipseContextFactory.createServiceContext(self.getBundleContext());

		viewContext = createContext("root", viewSupplier.get(), domainModelSupplier.get());

		// Capture the context listeners of the reveal service to feed them context lifecycle events
		doAnswer(invocation -> {
			capturedListeners.add((EMFFormsContextListener) invocation.getArguments()[0]);
			return null;
		}).when(viewContext).registerEMFFormsContextListener(any());

		initializeLegacyServices();
	}

	@Override
	protected void finished(Description description) {
		// Dispose any left-over child contexts
		new ArrayList<>(childContexts).forEach(this::disposeChildContext);

		// Dispose the root context. Defensive copy in case any removes itself
		new ArrayList<>(capturedListeners).forEach(safeRun(EMFFormsContextListener::contextDispose));
		capturedListeners.clear();

		lazyServices.clear();
		e4Context.dispose();

		viewContext = null;
	}

	//
	// Nested types
	//

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface ViewModel {
		// Empty annotation
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface DomainModel {
		// Empty annotation
	}

}
