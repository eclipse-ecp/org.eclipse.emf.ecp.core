/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import org.eclipse.emfforms.common.Optional;

/**
 * Provides an {@link ActionConfiguration} if possible.
 *
 * @author Lucas Koehler
 *
 */
public interface ActionConfigurationProvider {

	/**
	 * Returns the action configuration which will be used to register defined key bindings on the viewer.
	 *
	 * @return the {@link ActionConfiguration}, if any
	 */
	Optional<ActionConfiguration> getActionConfiguration();
}
