/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
