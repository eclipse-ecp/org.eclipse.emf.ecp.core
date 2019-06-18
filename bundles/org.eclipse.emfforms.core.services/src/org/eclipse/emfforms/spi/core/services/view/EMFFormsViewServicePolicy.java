/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.view;

/**
 * This describes when the service will be activated, aka policy.
 * 
 * @author Eugen Neufeld
 * @since 1.8
 */
public enum EMFFormsViewServicePolicy {
	/**
	 * The service will be activated on request.
	 */
	LAZY,
	/**
	 * The service will be activated immediately.
	 */
	IMMEDIATE
}
