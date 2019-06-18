/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Jonas Helming - JavaDoc
 *******************************************************************************/
package org.eclipse.emf.ecp.spi.core.util;

import org.eclipse.emf.ecp.core.util.ECPElement;

/**
 * @author Eike Stepper
 * @param <ELEMENT>
 * @since 1.1
 */
public interface InternalElementRegistry<ELEMENT extends ECPElement> {
	void addResolveListener(ResolveListener<ELEMENT> listener);

	void removeResolveListener(ResolveListener<ELEMENT> listener);

	/**
	 * @author Eike Stepper
	 */
	public interface ResolveListener<ELEMENT extends ECPElement> {
		void descriptorChanged(InternalDescriptor<ELEMENT> descriptor, boolean resolved) throws Exception;
	}
}
