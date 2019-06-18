/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.common.callback;

import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;

/**
 * This interface allows clients to update the {@link VViewModelProperties}, usually before rendering.
 *
 * @author Alexandra Buzila
 * @since 1.11
 *
 */
public interface ViewModelPropertiesUpdateCallback {

	/**
	 * Gets called when the ViewModelProperties need to be updated, usually before rendering.
	 *
	 * @param properties the {@link VViewModelProperties}
	 */
	void updateViewModelProperties(VViewModelProperties properties);

}
