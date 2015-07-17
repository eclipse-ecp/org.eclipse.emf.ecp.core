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
package org.eclipse.emf.ecp.view.spi.custom.model;

import java.util.Set;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * A class implementing {@link ECPHardcodedReferences} indicates, that it provides the set of the
 * {@link VDomainModelReference VDomainModelReferences} needed for rendering itself.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public interface ECPHardcodedReferences {
	/**
	 * Returns a list of all {@link VDomainModelReference VDomainModelReferences} which will be used by this control.
	 *
	 * @return a {@link Set} of {@link VDomainModelReference VDomainModelReferences} to be used by the control
	 */
	Set<VDomainModelReference> getNeededDomainModelReferences();

}
