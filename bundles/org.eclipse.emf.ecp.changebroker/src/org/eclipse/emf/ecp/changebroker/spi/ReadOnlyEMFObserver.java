/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.spi;

/**
 * A Read Only EMF Observer has a handle notification method which receives a
 * {@link org.eclipse.emf.common.notify.Notification
 * Notification} from the {@link ChangeBroker}. As opposed to regular {@link EMFObserver EMFObservers} it is <b>not</b>
 * allowed to make
 * changes on the EMF model and therefore to trigger further notifications. This is not enforced but will lead to
 * unexpected behavior or circular updates between observers.
 *
 * @author jfaltermeier
 *
 */
public interface ReadOnlyEMFObserver extends EMFObserver {

}
