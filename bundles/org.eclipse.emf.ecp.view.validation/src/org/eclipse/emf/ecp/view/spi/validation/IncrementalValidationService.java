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
package org.eclipse.emf.ecp.view.spi.validation;

/**
 * Optional mix-in interface for a {@link ValidationService} that supports
 * reporting of {@linkplain ValidationUpdateListener incremental updates}.
 *
 * @since 1.22
 */
public interface IncrementalValidationService {

	/**
	 * Register a listener for incremental validation updates.
	 *
	 * @param listener the incremental validation listener
	 */
	void registerValidationUpdateListener(ValidationUpdateListener listener);

	/**
	 * De-register a listener for incremental validation updates.
	 *
	 * @param listener the incremental validation listener
	 */
	void deregisterValidationUpdateListener(ValidationUpdateListener listener);

}
