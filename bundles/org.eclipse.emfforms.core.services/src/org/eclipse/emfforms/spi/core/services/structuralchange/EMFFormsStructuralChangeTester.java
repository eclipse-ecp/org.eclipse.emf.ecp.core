/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.structuralchange;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * A service that checks whether the domain structure of a {@link VDomainModelReference} has changed.
 *
 * @author Lucas Koehler
 * @since 1.8
 *
 */
public interface EMFFormsStructuralChangeTester {

	/**
	 * Checks whether the domain structure of the given {@link VDomainModelReference} has changed for the changes
	 * indicated by the given {@link ModelChangeNotification}.
	 *
	 * @param reference The {@link VDomainModelReference}
	 * @param domainRootObject The root object of the {@link VDomainModelReference}.
	 * @param notification The {@link ModelChangeNotification}
	 * @return true if the domain structure has changed, false otherwise
	 */
	boolean isStructureChanged(VDomainModelReference reference, EObject domainRootObject,
		ModelChangeNotification notification);
}
