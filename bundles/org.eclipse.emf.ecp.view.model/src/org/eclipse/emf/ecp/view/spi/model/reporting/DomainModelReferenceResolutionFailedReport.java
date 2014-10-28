/*******************************************************************************
 * Copyright (c) 2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model.reporting;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VResolvable;

/**
 * Indicates that a {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReference} could not be
 * resolved.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class DomainModelReferenceResolutionFailedReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param domainModelReference
	 *            the domain model reference that could not be resolved
	 * @param control
	 *            the control holding the domain model reference
	 */
	public DomainModelReferenceResolutionFailedReport(
		VDomainModelReference domainModelReference, VResolvable control) {
		super("Not resolved: " + domainModelReference //$NON-NLS-1$
			+ " on resolveable " + control, IStatus.WARNING); //$NON-NLS-1$
	}

}
