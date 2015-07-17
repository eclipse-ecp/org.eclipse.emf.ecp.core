/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model;

import java.util.Iterator;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;

/**
 * Provides the whole path for the a {@link Setting}. So by iterating over the path, each step through the domain model
 * is contained.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public interface SettingPath {

	/**
	 * The path to the bound {@link Setting}.
	 *
	 * @return the iterator for the path
	 */
	Iterator<Setting> getPath();
}
