/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.viewproxy.resolver;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;

/**
 * The domain model reference merger prepends a given {@link VFeaturePathDomainModelReference} to any DMR.
 *
 * @author jfaltermeier
 *
 */
public interface ECPDomainModelReferenceMerger {

	/**
	 * Prepends the the proxy reference to the given reference.
	 *
	 * @param source the proxy reference
	 * @param target the target
	 * @return <code>true</code> if successful, <code>false</code> otherwise
	 */
	boolean merge(VFeaturePathDomainModelReference source, VDomainModelReference target);

}
