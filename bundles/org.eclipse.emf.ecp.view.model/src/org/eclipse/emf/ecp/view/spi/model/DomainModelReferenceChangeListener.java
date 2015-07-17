/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model;

/**
 * The {@link DomainModelReferenceChangeListener} allows to subscribe to {@link VDomainModelReference
 * DomainModelReferences} and thus be notified when the {@link VDomainModelReference} changes.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public interface DomainModelReferenceChangeListener {

	/**
	 * Gets called in order to notify the listener, that the {@link VDomainModelReference} has changed.
	 */
	void notifyChange();

}
