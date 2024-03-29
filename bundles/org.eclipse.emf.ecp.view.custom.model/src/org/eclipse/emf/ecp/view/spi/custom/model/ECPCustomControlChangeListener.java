/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model;

/**
 * {@link ECPCustomControlChangeListener} will get informed the value of a
 * {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReference} changes.
 *
 * @author eneufeld
 * @since 1.2
 *
 */
public interface ECPCustomControlChangeListener {

	/**
	 * When a change on the registered {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 * VDomainModelReference} is noticed listeners will be notified.
	 */
	void notifyChanged();
}