/*******************************************************************************
 * Copyright (c) 2019-2020 Christian W. Damus and others.
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
package org.eclipse.emf.ecp.edit.spi;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * An extension of the {@link DeleteService} protocol that supports conditional deletion,
 * respecting a model's edit providers when they provide unexecutable commands to veto
 * deletion. Implementations of this interface <strong>should be registered</strong> as
 * OSGi services providing {@code DeleteService} because the framework looks for that
 * service interface but will test whether the implementation also provides this extension.
 * Whether the extension interface is also declared to OSGi is left to the provider's discretion.
 *
 * @since 1.23
 */
public interface ConditionalDeleteService extends DeleteService {

	/**
	 * Queries whether an {@code object} can be deleted. By convention a {@code null} value
	 * cannot be deleted.
	 *
	 * @param object an object to be deleted
	 * @return {@code false} if the {@code object} cannot be deleted or is {@code null}; {@code true}, otherwise
	 */
	default boolean canDelete(Object object) {
		return canDelete(Collections.singleton(object));
	}

	/**
	 * Queries whether <em>all</em> of the given {@code objects} can be deleted.
	 *
	 * @param objects a number of objects to be deleted
	 * @return {@code false} if any of the objects cannot be deleted; {@code true} otherwise, including the case of no
	 *         {@code objects}
	 */
	boolean canDelete(Iterable<?> objects);

	/**
	 * Query whether an {@code object} in some {@code reference} feature of an {@code owner} of that
	 * feature can be removed from the {@code reference} feature. In the case of containment references,
	 * this amounts to {@linkplain #canDelete(Object) deletion} of the {@code object}.
	 *
	 * @param owner the owner of a {@code reference} to some {@code object}
	 * @param reference a reference feature of the {@code owner}
	 * @param object an object referenced by the {@code owner} from which it is to be removed
	 *
	 * @return whether the {@code object} may be removed
	 *
	 * @since 1.24
	 */
	default boolean canRemove(Object owner, Object reference, Object object) {
		return canRemove(owner, reference, Collections.singleton(object));
	}

	/**
	 * Query whether <em>all</em> of the given {@code objects} in some {@code reference} feature of an {@code owner} of
	 * that
	 * feature can be removed from the {@code reference} feature. In the case of containment references,
	 * this amounts to {@linkplain #canDelete(Iterable) deletion} of the {@code object}.
	 *
	 * @param owner the owner of a {@code reference} to some {@code object}
	 * @param reference a reference feature of the {@code owner}
	 * @param objects a group of objects referenced by the {@code owner} from which they are to be removed
	 *
	 * @return whether the {@code objects} may be removed
	 *
	 * @since 1.24
	 */
	default boolean canRemove(Object owner, Object reference, Iterable<?> objects) {
		return canDelete(objects);
	}

	/**
	 * Obtain a conditional delete service for a given {@code context}, if necessary
	 * {@linkplain #adapt(DeleteService) adapting} its service implementation.
	 *
	 * @param context a form context
	 * @return the delete service, or an adapter if the actual delete service implementation does not provide
	 *         the {@link ConditionalDeleteService} protocol or if there isn't a delete service at all (in which case
	 *         nothing can be deleted)
	 *
	 * @see #adapt(DeleteService)
	 */
	static ConditionalDeleteService getDeleteService(EMFFormsViewContext context) {
		return adapt(context == null ? null : context.getService(DeleteService.class));
	}

	/**
	 * Obtain a view of a given {@code service} as conditional delete service, if necessary adapting a service
	 * implementation that does not provide the {@link ConditionalDeleteService} protocol with a default deletion
	 * condition. In that case, an object is assumed to be deletable if it is not {@code null} and it is an
	 * {@link EObject} that has some container from which it can be removed (root elements not logically
	 * being deletable).
	 *
	 * @param service a delete service
	 * @return the delete service, or an adapter if the actual delete service implementation does not provide
	 *         the {@link ConditionalDeleteService} protocol or if the original {@code service} is {@code null}
	 */
	static ConditionalDeleteService adapt(DeleteService service) {
		return service == null
			? DeleteServiceAdapter.NULL
			: service instanceof ConditionalDeleteService
				? (ConditionalDeleteService) service
				: new DeleteServiceAdapter(service);
	}

}
