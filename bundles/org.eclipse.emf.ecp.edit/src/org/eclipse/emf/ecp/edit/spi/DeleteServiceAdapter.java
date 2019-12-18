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
package org.eclipse.emf.ecp.edit.spi;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * An adapter for delete services that do not implement the {@link ConditionalDeleteService} protocol.
 */
final class DeleteServiceAdapter implements ConditionalDeleteService {

	/** An adapter for the {@code null} service (when there is no delete service at all). */
	static final ConditionalDeleteService NULL = new DeleteServiceAdapter(null);

	private final DeleteService delegate;

	/**
	 * Initializes me with the real delete service.
	 *
	 * @param delegate the real delete service
	 */
	DeleteServiceAdapter(DeleteService delegate) {
		super();

		this.delegate = delegate;
	}

	@Override
	public void deleteElements(Collection<Object> toDelete) {
		if (delegate != null) {
			delegate.deleteElements(toDelete);
		} else {
			throw new IllegalStateException("No deletion service available."); //$NON-NLS-1$
		}
	}

	@Override
	public void deleteElement(Object toDelete) {
		if (delegate != null) {
			delegate.deleteElement(toDelete);
		} else {
			throw new IllegalStateException("No deletion service available."); //$NON-NLS-1$
		}
	}

	@Override
	public void instantiate(ViewModelContext context) {
		if (delegate != null) {
			delegate.instantiate(context);
		}
	}

	@Override
	public void dispose() {
		if (delegate != null) {
			delegate.dispose();
		}
	}

	@Override
	public int getPriority() {
		return delegate == null ? Integer.MIN_VALUE : delegate.getPriority();
	}

	@Override
	public boolean canDelete(Iterable<?> objects) {
		// Cannot delete if we have no service to effect the deletion
		boolean result = delegate != null;

		if (result) {
			for (final Object next : objects) {
				// Cannot delete a null or a root object
				if (next == null || next instanceof EObject && ((EObject) next).eContainer() == null) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

}
