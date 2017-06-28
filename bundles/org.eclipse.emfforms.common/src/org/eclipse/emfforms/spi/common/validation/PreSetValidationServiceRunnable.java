/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.common.validation;

/**
 * Function that receives the {@link PreSetValidationService} as an input parameter.
 *
 * @since 1.13
 */
public interface PreSetValidationServiceRunnable {

	/**
	 * Execute this function.
	 *
	 * @param service the {@link PreSetValidationService}
	 */
	void run(PreSetValidationService service);

}
