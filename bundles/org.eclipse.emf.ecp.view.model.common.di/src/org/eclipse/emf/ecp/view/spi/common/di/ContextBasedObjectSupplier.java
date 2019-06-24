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
package org.eclipse.emf.ecp.view.spi.common.di;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.di.IInjector;
import org.eclipse.e4.core.di.InjectionException;
import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.core.di.suppliers.IRequestor;
import org.eclipse.e4.core.di.suppliers.PrimaryObjectSupplier;

/**
 * A partial implementation of an object supplier that computes its results from
 * values in the {@linkplain IEclipseContext Eclipse context}.
 *
 * @param <A> my qualifier annotation type
 * @param <T> my value type
 */
public abstract class ContextBasedObjectSupplier<A extends Annotation, T> extends ExtendedObjectSupplier {

	private final Class<A> qualifierType;
	private final Class<T> valueType;

	/**
	 * Initializes me with the type of value that I supply and the
	 * qualifier annotation type that I key on.
	 *
	 * @param qualifierType the qualifier annotation type
	 * @param valueType the value type
	 */
	protected ContextBasedObjectSupplier(Class<A> qualifierType, Class<T> valueType) {
		super();

		this.qualifierType = qualifierType;
		this.valueType = valueType;
	}

	@Override
	public Object get(IObjectDescriptor descriptor, IRequestor requestor, boolean track, boolean group) {
		final Class<?> desiredType = getRawType(descriptor.getDesiredType());
		final A qualifier = descriptor.getQualifier(qualifierType);

		if (!valueType.isAssignableFrom(desiredType)) {
			return IInjector.NOT_A_VALUE;
		}
		final Class<? extends T> requestedType = desiredType.asSubclass(valueType);

		final IEclipseContext ctx = getContext(requestor);
		if (ctx == null) {
			return IInjector.NOT_A_VALUE;
		}

		if (!checkDependencies(qualifier, requestedType, ctx)) {
			return IInjector.NOT_A_VALUE;
		}

		final Object[] result = { null };
		final Runnable compute = () -> {
			final Optional<? extends T> computedResult = compute(qualifier, requestedType, ctx);
			if (computedResult == null) {
				throw new InjectionException(String.format(
					"null computation for @%s injection", qualifierType.getSimpleName())); //$NON-NLS-1$
			}
			result[0] = computedResult.map(Object.class::cast)
				.orElse(IInjector.NOT_A_VALUE);
		};
		if (track) {
			ctx.runAndTrack(new RunAndTrack() {

				@Override
				public boolean changed(IEclipseContext context) {
					final boolean valid = requestor.isValid();
					final boolean notify = result[0] != null && requestor.isValid();
					if (valid) {
						compute.run();
					}
					if (notify) {
						requestor.resolveArguments(false);
						requestor.execute();
					}
					return valid;
				}
			});
		} else {
			compute.run();
		}

		return result[0];
	}

	/**
	 * Check whether the Eclipse {@code context} has the dependencies required to
	 * compute my result. The default implementation just attempts to compute
	 * the result, which is useful for simple cases where the computation is not
	 * expensive and does not have undesired side-effects if it fails.
	 *
	 * @param qualifier the qualifier annotation, which may have attributes required for the computation
	 * @param requestedType the type requested for injection
	 * @param context the Eclipse context
	 * @return {@code true} if I can compute a value from this {@code context};
	 *         {@code false}, otherwise
	 *
	 * @see #compute(Annotation, IEclipseContext)
	 */
	protected boolean checkDependencies(A qualifier, Class<? extends T> requestedType, IEclipseContext context) {
		final Optional<? extends T> test = compute(qualifier, requestedType, context);
		return test != null && test.isPresent();
	}

	/**
	 * Compute my value from the Eclipse {@code context}. An empty result
	 * indicates that the value does not exist (the {@link IInjector#NOT_A_VALUE}
	 * special result for the object-supplier protocol). Injection of {@code null}
	 * values is not supported.
	 *
	 * @param qualifier the qualifier annotation, which may have attributes required for the computation
	 * @param requestedType the type requested for injection
	 * @param context the Eclipse context
	 * @return the result of the computation (possibly empty, but not {@code null})
	 */
	protected abstract Optional<? extends T> compute(A qualifier, Class<? extends T> requestedType,
		IEclipseContext context);

	private static Class<?> getRawType(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return getRawType(((ParameterizedType) type).getRawType());
		} else {
			return Object.class;
		}
	}

	@SuppressWarnings("restriction")
	private static IEclipseContext getContext(IRequestor requestor) {
		PrimaryObjectSupplier supplier = null;

		if (requestor instanceof org.eclipse.e4.core.internal.di.Requestor<?>) {
			supplier = ((org.eclipse.e4.core.internal.di.Requestor<?>) requestor).getPrimarySupplier();
		}

		if (supplier instanceof org.eclipse.e4.core.internal.contexts.ContextObjectSupplier) {
			return ((org.eclipse.e4.core.internal.contexts.ContextObjectSupplier) supplier).getContext();
		}

		return null;
	}

}
