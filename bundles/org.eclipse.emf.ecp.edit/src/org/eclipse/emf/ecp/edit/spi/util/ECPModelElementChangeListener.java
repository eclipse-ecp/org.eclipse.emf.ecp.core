/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas Helming - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.edit.spi.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * Listens to the changes of one modelelement.
 *
 * @author helming
 */
public abstract class ECPModelElementChangeListener extends AdapterImpl {

	private final EObject modelelement;

	/**
	 * Default constructor.
	 *
	 * @param modelelement
	 *            the modelelement to listen on
	 */
	public ECPModelElementChangeListener(EObject modelelement) {
		this.modelelement = modelelement;
		modelelement.eAdapters().add(this);
	}

	/**
	 * Handle changes to the model element.
	 *
	 * @param notification
	 *            the EMF notification, providing details on the change
	 *
	 */
	public abstract void onChange(Notification notification);

	/**
	 * Handle a runtime exception that occured in this listeners methods. NOTE: runtime exceptions of this method will
	 * be
	 * logged and silently dropped.
	 *
	 * @param exception
	 *            the exception
	 */
	void onRuntimeExceptionInListener(RuntimeException exception) {
		remove();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
		if (notification.isTouch()) {
			return;
		}
		// BEGIN SUPRESS CATCH EXCEPTION
		try {
			onChange(notification);
		} catch (final RuntimeException e) {
			onRuntimeExceptionInListener(e);
		}
		// END SUPRESS CATCH EXCEPTION
	}

	/**
	 * Removes the {@link ECPModelElementChangeListener}.
	 */
	public void remove() {
		modelelement.eAdapters().remove(this);
	}

}
