/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.spi;

/**
 * A Read Only EMF Observer has a handle notification method which receives a
 * {@link org.eclipse.emf.common.notify.Notification
 * Notification} from the {@link ChangeBroker}. As opposed to regular {@link ChangeObserver EMFObservers} it is
 * <b>not</b>
 * allowed to make
 * changes on the EMF model and therefore to trigger further notifications. This is not enforced but will lead to
 * unexpected behavior or circular updates between observers.
 *
 * @author jfaltermeier
 * @since 1.7
 *
 */
public interface ReadOnlyChangeObserver extends ChangeObserver {

}
