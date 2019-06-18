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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.validation;

import org.eclipse.emfforms.common.spi.validation.Validator;

/**
 * The ValidationService calls the providers after the validation with EMF.
 * By providing an own provider, one can extend the EMF validation by providing additional validation rules.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
// TODO mark as deprecated
public interface ValidationProvider extends Validator {

}
