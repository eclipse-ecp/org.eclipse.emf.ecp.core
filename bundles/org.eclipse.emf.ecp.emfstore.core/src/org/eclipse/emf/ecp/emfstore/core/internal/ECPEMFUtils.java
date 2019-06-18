/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * neilmack - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.core.internal;

import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;

/**
 * @author neilmack
 *
 */
public abstract class ECPEMFUtils {

	/**
	 * Helper method to get the instance of the {@link ESWorkspaceProviderImpl}.
	 *
	 * @return the {@link ESWorkspaceProviderImpl}
	 * @since 1.5
	 */
	public static ESWorkspaceProviderImpl getESWorkspaceProviderInstance() {
		return Activator.getESWorkspaceProviderInstance();
	}

}
