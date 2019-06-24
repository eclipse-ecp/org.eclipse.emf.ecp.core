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
package org.eclipse.emfforms.internal.core.services.reveal;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.InjectionException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.BidirectionalMap;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.spi.bazaar.BazaarUtil;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealService;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextTracker;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Implementation of the EMF Forms reveal service.
 *
 * @since 1.22
 */
public class EMFFormsRevealServiceImpl implements EMFFormsRevealService {

	private final Bazaar<RevealStep> stepBazaar = BazaarUtil.createBazaar(RevealStep.FAILED);

	/**
	 * Mapping of detail contexts into which reveal steps delegate drill-down.
	 * Keys are the view models of the detail contexts that are the values.
	 */
	private final BidirectionalMap<VElement, EMFFormsViewContext> detailContexts = new BidirectionalMap<>();

	private final EMFFormsViewContext viewContext;
	private IEclipseContext e4Context;

	/**
	 * Initializes me with my view context.
	 *
	 * @param viewContext my view context
	 */
	public EMFFormsRevealServiceImpl(EMFFormsViewContext viewContext) {
		super();

		this.viewContext = viewContext;

		new EMFFormsContextTracker(viewContext)
			.onChildContextAdded(this::childContextAdded)
			.onChildContextRemoved(this::childContextRemoved)
			.onContextDisposed(this::contextDisposed)
			.open();
	}

	private void contextDisposed(EMFFormsViewContext ctx) {
		if (ctx == viewContext && e4Context != null) {
			final IEclipseContext toDispose = e4Context;
			e4Context = null;
			toDispose.dispose();
		}
	}

	@Override
	public boolean reveal(EObject object) {
		final RevealStep step = reveal(object, null, viewContext.getViewModel());
		return perform(step);
	}

	@Override
	public boolean reveal(EObject object, EStructuralFeature feature) {
		RevealStep step = reveal(object, feature, viewContext.getViewModel());
		if (step.isFailed() && feature != null) {
			// Try revealing the object itself, then
			step = reveal(object, viewContext.getViewModel());
		}
		return perform(step);
	}

	/**
	 * Invoke a reveal step to reveal an object.
	 *
	 * @param revealStep the reveal step to perform
	 * @return whether the step revealed the object
	 */
	boolean perform(RevealStep revealStep) {
		RevealStep step = revealStep;

		while (!step.isDone()) {
			step = step.drillDown();
		}

		final boolean result = !step.isFailed();

		if (result) {
			step.reveal();
		}

		return result;
	}

	@Override
	public RevealStep reveal(EObject object, VElement scope) {
		return reveal(object, null, scope);
	}

	@Override
	public RevealStep reveal(EObject object, EStructuralFeature feature, VElement scope) {
		final IEclipseContext e4Context = createLocalContext("Reveal Service Bazaar", //$NON-NLS-1$
			scope, object, feature, null);

		try {
			final BazaarContext context = BazaarContext.Builder.empty()
				.put(IEclipseContext.class, e4Context)
				.build();

			// We have a default vendor but a vendor that wins the bid may
			// nonetheless create a null
			return Optional.ofNullable(stepBazaar.createProduct(context))
				.orElse(RevealStep.fail());
		} finally {
			e4Context.dispose();
		}
	}

	private IEclipseContext getEclipseContext() {
		if (e4Context == null) {
			e4Context = viewContext.getService(IEclipseContext.class);

			if (e4Context == null) {
				final Bundle bundle = FrameworkUtil.getBundle(EMFFormsRevealServiceImpl.class);
				e4Context = EclipseContextFactory.createServiceContext(bundle.getBundleContext());
			} else {
				// Create a private child context that we can dispose
				e4Context = e4Context.createChild("Reveal Service"); //$NON-NLS-1$
			}

			e4Context.set(EMFFormsRevealService.class, this);
			e4Context.set(EMFFormsRevealServiceImpl.class, this);
			e4Context.set(EMFFormsViewContext.class, viewContext);

			// Bind our helper implementation
			e4Context.set(RevealHelper.class.getName(), new ContextFunction() {
				@Override
				public Object compute(IEclipseContext context, String contextKey) {
					return ContextInjectionFactory.make(RevealHelperImpl.class, context);
				}
			});
		}
		return e4Context;
	}

	/**
	 * Evaluate an {@code annotation} computation in a temporary Eclipse context.
	 *
	 * @param <T> the type of {@code computation} to perform
	 * @param annotation the annotation of the method to invoke
	 * @param resultType the type of {@code computation} to perform
	 * @param element a view-model element to inject into the context
	 * @param object a domain-model element (being revealed) to inject into the context
	 * @param feature the structural feature (if any) to inject into the context
	 * @param computation the computation to perform
	 * @return the result of the {@code computation}
	 */
	final <T> Optional<T> evaluate(Class<? extends Annotation> annotation,
		Class<T> resultType, VElement element, EObject object, EStructuralFeature feature,
		Object computation) {

		return evaluate(annotation, resultType, element, object, feature, null, computation);
	}

	/**
	 * Evaluate an {@code annotation} computation in a temporary Eclipse context.
	 *
	 * @param <T> the type of {@code computation} to perform
	 * @param annotation the annotation of the method to invoke
	 * @param resultType the type of {@code computation} to perform
	 * @param element a view-model element to inject into the context
	 * @param object a domain-model element (being revealed) to inject into the context
	 * @param feature the structural feature (if any) to inject into the context
	 * @param parameters additional variables to inject
	 * @param computation the computation to perform
	 * @return the result of the {@code computation}
	 */
	final <T> Optional<T> evaluate(Class<? extends Annotation> annotation,
		Class<T> resultType, VElement element, EObject object, EStructuralFeature feature,
		Map<Class<?>, ?> parameters, Object computation) {

		final IEclipseContext localContext = createLocalContext("invocation", //$NON-NLS-1$
			element, object, feature, parameters);

		try {
			return evaluate(annotation, resultType, localContext, computation);
		} finally {
			localContext.dispose();
		}
	}

	/**
	 * Evaluate an {@code annotation} computation in a temporary Eclipse context.
	 *
	 * @param <T> the type of {@code computation} to perform
	 * @param annotation the annotation of the method to invoke
	 * @param resultType the type of {@code computation} to perform
	 * @param context the Eclipse context for method injection
	 * @param computation the computation to perform
	 * @return the result of the {@code computation}
	 */
	final <T> Optional<T> evaluate(Class<? extends Annotation> annotation, Class<T> resultType,
		IEclipseContext context, Object computation) {

		Object result;
		try {
			result = ContextInjectionFactory.invoke(computation, annotation, context, null);
		} catch (final InjectionException e) {
			report(new AbstractReport(e.getCause(), e.getMessage()));
			result = null;
		}

		return Optional.ofNullable(result).filter(resultType::isInstance).map(resultType::cast);
	}

	/**
	 * Create a local context to overlay on our primary context for method invocation.
	 *
	 * @param debugLabel a debug label for the context
	 * @param viewModel the view-model element to inject
	 * @param domainModel the domain-model element to inject
	 * @param feature the structural feature to inject, or {@code null} if none
	 * @param parameters additional variables to inject
	 *
	 * @return the local (overlay) injection context
	 */
	private IEclipseContext createLocalContext(String debugLabel, VElement viewModel, EObject domainModel,
		EStructuralFeature feature, Map<Class<?>, ?> parameters) {

		final IEclipseContext result = getEclipseContext().createChild(debugLabel);

		fill(result, viewModel, domainModel, feature);

		if (parameters != null) {
			for (final Map.Entry<Class<?>, ?> entry : parameters.entrySet()) {
				final String key = entry.getKey().getName();
				if (!result.containsKey(key)) {
					result.set(key, entry.getValue());
				}
			}
		}

		return result;
	}

	private void fill(IEclipseContext context, VElement viewModel, EObject domainModel, EStructuralFeature feature) {
		final String eObjectClassName = EObject.class.getName();
		context.set(eObjectClassName, domainModel);
		if (feature != null) {
			context.set(EStructuralFeature.class, feature);
		}

		// It is more important for the view model to support specific types in the
		// injection because that is more likely to be what reveal providers filter
		// on (after all, the renderers employed depend on the view model, not the
		// domain model that they render). We cannot do the same for the domain
		// model because of the special case of the View Model Editor, in which the
		// domain model is an instance of the view model packages
		context.set(viewModel.eClass().getInstanceClassName(), viewModel);
		for (final EClass next : viewModel.eClass().getEAllSuperTypes()) {
			final String superclassName = next.getInstanceClassName();

			// In case EObject is an explicit superclass, don't hide the domain model
			if (!eObjectClassName.equals(superclassName)) {
				context.set(superclassName, viewModel);
			}
		}

		final EMFFormsViewContext viewContext = getViewContext(viewModel);
		if (viewContext != null) {
			context.set(EMFFormsViewContext.class, viewContext);
		}
	}

	private void report(AbstractReport report) {
		final ReportService service = viewContext.getService(ReportService.class);
		if (service != null) {
			service.report(report);
		} else if (report.hasException()) {
			report.getException().printStackTrace();
		}
	}

	/**
	 * Get the nearest context for a view model {@code element}. This is either some active
	 * detail context or the root context that owns me.
	 *
	 * @param element a view-model element
	 * @return its most specific applicable context
	 */
	protected EMFFormsViewContext getViewContext(VElement element) {
		EMFFormsViewContext result = null;

		// Don't just get the root container, but the container that is a view that
		// either is the root or is the detail view of (e.g.) a table or tree
		for (EObject parent = element; result == null && parent != null; parent = parent.eContainer()) {
			if (parent instanceof VElement) {
				result = detailContexts.getValue((VElement) parent);
			}
		}
		if (result == null) {
			result = viewContext;
		}
		return result;
	}

	@Override
	public void addRevealProvider(EMFFormsRevealProvider provider) {
		stepBazaar.addVendor(provider);
	}

	@Override
	public void removeRevealProvider(EMFFormsRevealProvider provider) {
		stepBazaar.removeVendor(provider);
	}

	private void childContextAdded(EMFFormsViewContext parentContext, VElement parentElement,
		EMFFormsViewContext childContext) {

		detailContexts.put(childContext.getViewModel(), childContext);
	}

	private void childContextRemoved(EMFFormsViewContext parentContext, VElement parentElement,
		EMFFormsViewContext childContext) {

		detailContexts.removeByValue(childContext);
	}

	/**
	 * Find the view model context that is the child context for details of the given
	 * master selection.
	 *
	 * @param masterSelection an object selected in the master control
	 * @return the detail context, or {@code null} if none
	 */
	EMFFormsViewContext getDetailContext(EObject masterSelection) {
		return detailContexts.values().stream()
			.filter(ctx -> ctx.getDomainModel() == masterSelection)
			.findAny().orElse(null);
	}

}
