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
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Default implementation of {@link EMFFormsDomainExpander}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsDomainExpanderImpl")
public class EMFFormsDomainExpanderImpl implements EMFFormsDomainExpander {
	private final Set<EMFFormsDMRExpander> emfFormsDMRExpanders = new CopyOnWriteArraySet<EMFFormsDMRExpander>();

	/**
	 * Called by the framework to add an {@link EMFFormsDMRExpander} to the set of DMR expanders.
	 *
	 * @param emfFormsDMRExpander The {@link EMFFormsDMRExpander} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addEMFFormsDMRExpander(EMFFormsDMRExpander emfFormsDMRExpander) {
		emfFormsDMRExpanders.add(emfFormsDMRExpander);
	}

	/**
	 * Called by the framework to remove an {@link EMFFormsDMRExpander} from the set of DMR expanders.
	 *
	 * @param emfFormsDMRExpander The {@link EMFFormsDMRExpander} to remove
	 */
	protected void removeEMFFormsDMRExpander(EMFFormsDMRExpander emfFormsDMRExpander) {
		emfFormsDMRExpanders.remove(emfFormsDMRExpander);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void prepareDomainObject(VDomainModelReference domainModelReference, EObject domainObject)
		throws EMFFormsExpandingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(domainObject).notNull();

		EMFFormsDMRExpander bestDMRExpander = null;
		double bestScore = Double.NEGATIVE_INFINITY;
		for (final EMFFormsDMRExpander dmrExpander : emfFormsDMRExpanders) {
			if (dmrExpander.isApplicable(domainModelReference) > bestScore) {
				bestScore = dmrExpander.isApplicable(domainModelReference);
				bestDMRExpander = dmrExpander;
			}
		}

		if (bestDMRExpander == null) {
			throw new EMFFormsExpandingFailedException(
				"There is no suitable EMFFormsDMRExpander for the given domain model reference."); //$NON-NLS-1$
		}

		bestDMRExpander.prepareDomainObject(domainModelReference, domainObject);
	}
}
