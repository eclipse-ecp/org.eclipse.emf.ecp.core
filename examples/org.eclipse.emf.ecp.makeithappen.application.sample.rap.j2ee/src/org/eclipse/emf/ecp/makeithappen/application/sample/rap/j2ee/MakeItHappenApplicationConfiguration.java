/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.makeithappen.application.sample.rap.j2ee;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;

/**
 * The {@link ApplicationConfiguration} for the MakeItHappen RAP Demo.
 */
public class MakeItHappenApplicationConfiguration implements ApplicationConfiguration {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.rap.rwt.application.ApplicationConfiguration#configure(org.eclipse.rap.rwt.application.Application)
	 */
	@Override
	public void configure(Application application) {
		application.addEntryPoint("/makeithappen_j2ee", MakeItHappenEntryPoint.class, null); //$NON-NLS-1$
	}

}
