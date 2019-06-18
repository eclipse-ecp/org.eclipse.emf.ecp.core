/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.Viewer;

/**
 * A viewer action context class.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 * @param <V> the viewer type
 */
public interface ViewerActionContext<V extends Viewer> {

	/**
	 * Returns the setting of the containment reference.
	 *
	 * @return the setting
	 */
	Setting getSetting();

	/**
	 * Return the EMF editing domain.
	 *
	 * @return the editingDomain
	 */
	EditingDomain getEditingDomain();

	/**
	 * Returns the viewer instance.
	 *
	 * @return the viewer
	 */
	V getViewer();

}
