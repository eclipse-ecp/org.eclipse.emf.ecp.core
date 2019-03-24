/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * Protocol for providers of marker helpers that should be registered as OSGi services.
 * The injection context includes:
 * <ul>
 * <li>the {@link IFile} for which markers are to be created</li>
 * </ul>
 */
// CHECKSTYLE.OFF: InterfaceIsType - Need distinct type for OSGi Service registration
public interface MarkerHelperProvider extends Vendor<MarkerHelper> {
	/**
	 * Provider of the default EMF marker helper.
	 */
	MarkerHelperProvider DEFAULT = new DefaultMarkerHelperProvider();
}
