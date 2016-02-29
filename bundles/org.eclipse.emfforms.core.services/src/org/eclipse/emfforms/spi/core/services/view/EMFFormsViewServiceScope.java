/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.view;

/**
 * This describes in which context the service will be available, aka scope.
 *
 * @author Eugen Neufeld
 * @since 1.8
 */
public enum EMFFormsViewServiceScope {
	/**
	 * The service will be only available to the current context and its children. This also means that there might be
	 * multiple instances of the same service.
	 */
	LOCAL,
	/**
	 * The service will be available only once on the top context. There will be only one instance.
	 */
	GLOBAL
}
