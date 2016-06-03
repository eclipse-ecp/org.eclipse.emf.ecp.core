/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.view;

/**
 * The listener interface to listen to changes of the domain model of a {@link EMFFormsViewContext}.
 *
 * @author Lucas Koehler
 * @since 1.9
 *
 */
public interface RootDomainModelChangeListener {
	/**
	 * Notifies this listener that the domain model of the {@link EMFFormsViewContext} has changed.
	 */
	void notifyChange();
}
