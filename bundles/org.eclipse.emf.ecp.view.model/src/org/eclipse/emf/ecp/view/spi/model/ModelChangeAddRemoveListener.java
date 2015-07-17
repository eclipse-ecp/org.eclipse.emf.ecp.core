/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model;

import org.eclipse.emf.common.notify.Notifier;

/**
 * The listener interface for receiving modelChange events.
 * The class that is interested in processing a modelChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>registerViewChangeListener</code> or <code>registerDomainChangeListener</code> method. When
 * the modelChange event occurs, that object's appropriate
 * method is invoked.
 *
 * @since 1.3
 *
 */
public interface ModelChangeAddRemoveListener extends ModelChangeListener {

	/**
	 * @param notifier the notifier
	 */
	void notifyAdd(Notifier notifier);

	/**
	 * @param notifier the notifier
	 */
	void notifyRemove(Notifier notifier);
}