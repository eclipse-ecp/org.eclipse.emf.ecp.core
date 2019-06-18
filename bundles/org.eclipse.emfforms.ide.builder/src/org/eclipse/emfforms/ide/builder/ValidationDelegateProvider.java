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
package org.eclipse.emfforms.ide.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * Protocol for providers of validation delegates that should be registered as OSGi services.
 * The injection context includes:
 * <ul>
 * <li>the {@link IFile} to be validated</li>
 * </ul>
 */
// CHECKSTYLE.OFF: InterfaceIsType - Need distinct type for OSGi Service registration
public interface ValidationDelegateProvider extends Vendor<ValidationDelegate> {
	/**
	 * Provider of the {@link ValidationDelegate#NULL null validation delegate}.
	 */
	ValidationDelegateProvider NULL = new NullValidationDelegateProvider();
}
