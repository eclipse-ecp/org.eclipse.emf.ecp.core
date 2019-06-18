/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.spi.validation.exception;

/**
 * Exception which is thrown by ValidationService#validate(java.util.Iterator) in case
 * ValidationService#cancel() is invoked before the entire queue has been processed.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public class ValidationCanceledException extends Exception {

	private static final long serialVersionUID = 938755283756812085L;

}
