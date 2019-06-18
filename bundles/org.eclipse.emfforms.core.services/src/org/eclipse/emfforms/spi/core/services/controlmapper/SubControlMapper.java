/*******************************************************************************
 * Copyright (c) 2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.spi.core.services.controlmapper;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * An optional interface provided by an {@link EMFFormsSettingToControlMapper}
 * for queries specific to sub-trees of the view control hierarchy.
 *
 * @since 1.20
 */
public interface SubControlMapper {

	/**
	 * Obtain a collection of all objects that have a mapped setting in the
	 * hierarchy of the given view {@code element}.
	 *
	 * @param element the view sub-tree for which to get objects
	 * @return the objects presented in the view sub-tree that have settings
	 */
	Collection<EObject> getEObjectsWithSettings(VElement element);

}
