/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.structuralchange;

import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Lucas Koehler
 *
 */
class TestNotification extends NotificationImpl {
	private final EObject notifier;
	private final EStructuralFeature feature;

	TestNotification(int eventType, Object oldValue, Object newValue, EObject notifier,
		EStructuralFeature feature) {
		super(eventType, oldValue, newValue);
		this.notifier = notifier;
		this.feature = feature;
	}

	@Override
	public Object getNotifier() {
		return notifier;
	}

	@Override
	public Object getFeature() {
		return feature;
	}
}
