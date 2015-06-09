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
package org.eclipse.emf.ecp.view.internal.rule.reporting;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * Indicates that a {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReference} of a
 * {@link LeafCondition} could not be resolved.
 *
 * @author jfaltermeier
 * @since 1.5
 *
 */
public class LeafConditionDMRResolutionFailedReport extends AbstractReport {

	/**
	 * Constructs a new {@link LeafConditionDMRResolutionFailedReport}.
	 *
	 * @param condition the condition
	 * @param valueDMR
	 *            <code>true</code> if resolution for {@link LeafCondition#getValueDomainModelReference()} failed <br>
	 *            <code>false</code> if resolution for {@link LeafCondition#getDomainModelReference()} failed</br>
	 */
	public LeafConditionDMRResolutionFailedReport(LeafCondition condition, boolean valueDMR) {
		super("Not resolved: " //$NON-NLS-1$
			+ (valueDMR ? condition.getValueDomainModelReference() : condition.getDomainModelReference())
			+ " on leaf condition " + condition, IStatus.WARNING); //$NON-NLS-1$
	}

}
