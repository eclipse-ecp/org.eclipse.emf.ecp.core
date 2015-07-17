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
 * The listener interface for receiving modelChange events.
 * The class that is interested in processing a modelChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>registerViewChangeListener</code> or <code>registerDomainChangeListener</code> method. When
 * the modelChange event occurs, that object's appropriate
 * method is invoked.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public interface ModelChangeListener {
	/**
	 * Notify about a change.
	 *
	 * @param notification the {@link ModelChangeNotification}
	 */
	void notifyChange(ModelChangeNotification notification);
}
