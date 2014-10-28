/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.edapt.internal.migration.execution.IClassLoader;
import org.osgi.framework.Bundle;

/**
 * @author jfaltermeier
 *
 */
@SuppressWarnings("restriction")
public class CustomMigrationClassLoader implements IClassLoader {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<T> load(String name) throws ClassNotFoundException {
		final String bundleNameForClass = CustomMigrationHelper.getInstance().getBundleNameForClass(name);
		if (bundleNameForClass == null) {
			throw new ClassNotFoundException(name);
		}
		final Bundle bundle = Platform.getBundle(bundleNameForClass);
		if (bundle == null) {
			throw new ClassNotFoundException(name);
		}
		return (Class<T>) bundle.loadClass(name);
	}

}
