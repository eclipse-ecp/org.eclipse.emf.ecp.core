/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.controlmapper;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * Helper class to expand eobjects.
 *
 * @author Eugen Neufeld
 *
 */
public final class SettingToControlExpandHelper {

	private SettingToControlExpandHelper() {
	}

	/**
	 * Resolve all domain model references for a given resolvable and a given domain model root.
	 *
	 * @param resolvable The EObject to resolve all {@link VDomainModelReference domain model references} of.
	 * @param domainModelRoot the domain model used for the resolving.
	 * @param viewModelContext The {@link EMFFormsViewContext}
	 */
	public static void resolveDomainReferences(EObject resolvable, EObject domainModelRoot,
		EMFFormsViewContext viewModelContext) {
		// Get domain expander service
		final EMFFormsDomainExpander domainExpander = viewModelContext
			.getService(EMFFormsDomainExpander.class);
		if (domainExpander == null) {
			return;
		}
		expandAndInitDMR(domainModelRoot, domainExpander, resolvable, viewModelContext);
		// Iterate over all domain model references of the given EObject.
		final TreeIterator<EObject> eAllContents = resolvable.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			expandAndInitDMR(domainModelRoot, domainExpander, eObject, viewModelContext);
		}
	}

	@SuppressWarnings("deprecation")
	private static void expandAndInitDMR(EObject domainModelRoot, final EMFFormsDomainExpander domainExpander,
		final EObject eObject, EMFFormsViewContext viewModelContext) {
		if (VDomainModelReference.class.isInstance(eObject)
			&& !VDomainModelReference.class.isInstance(eObject.eContainer())) {
			final VDomainModelReference domainModelReference = VDomainModelReference.class.cast(eObject);
			// FIXME remove as soon as all users have moved
			domainModelReference.init(domainModelRoot);
			try {
				domainExpander.prepareDomainObject(domainModelReference, domainModelRoot);
			} catch (final EMFFormsExpandingFailedException ex) {
				viewModelContext.getService(ReportService.class)
					.report(new AbstractReport(ex));
			}
		}
	}
}
