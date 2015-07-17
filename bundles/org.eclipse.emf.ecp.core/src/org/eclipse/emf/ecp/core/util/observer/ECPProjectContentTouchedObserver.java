/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core.util.observer;

import java.util.Collection;

import org.eclipse.emf.ecp.core.ECPProject;

/**
 * This Observer is called when objects of an project are touched. Touching means changed or affected indirectly by the
 * change. This implies that the objects might not have changed any of their attributes or references. Should be mainly
 * used to update UI correctly. This Observer is notified after the {@link ECPProjectContentChangedObserver}. It will
 * also get all Elements that where affected by the change.
 *
 * @author Eugen Neufeld
 *
 */
public interface ECPProjectContentTouchedObserver extends ECPObserver {
	/**
	 * This is called when objects of a project changed.
	 *
	 * @param project the project where the changed occurred.
	 * @param objects the objects that changed and the objects affected by the change
	 * @param structural whether the change was structural that is changes to containment references
	 */
	void contentTouched(ECPProject project, Collection<Object> objects, boolean structural);

}
