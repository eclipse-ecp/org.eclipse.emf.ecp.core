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
package org.eclipse.emfforms.spi.core.services.domainexpander;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * This service offers the method {@link #prepareDomainObject(VDomainModelReference, EObject)} that allows to expand a
 * given {@link EObject domain object} for a {@link VDomainModelReference}. Thereby, missing references, which are
 * defined in the domain model reference but do not yet exist (= are null) in the domain model, are created.
 *
 * @author Lucas Koehler
 * @since 1.7
 *
 */
public interface EMFFormsDomainExpander {
	/**
	 * Expands a {@link EObject domain object} for the given {@link VDomainModelReference}. Thereby, the path defined
	 * by the {@link VDomainModelReference} is analyzed and missing objects in the domain model are created. The
	 * {@link VDomainModelReference} is not changed.
	 * <p>
	 * Example:<br/>
	 * DMR: A -> B -> x<br/>
	 * domain model is instance of A but does not reference an instance of B<br/>
	 * => An instance of B is created and referenced by the domain model.
	 *
	 * @param domainModelReference The {@link VDomainModelReference} for which the {@link EObject domain object} should
	 *            be prepared.
	 * @param domainObject The {@link EObject domain object} to prepare.
	 * @throws EMFFormsExpandingFailedException if the domain expansion fails.
	 */
	void prepareDomainObject(VDomainModelReference domainModelReference, EObject domainObject)
		throws EMFFormsExpandingFailedException;
}
