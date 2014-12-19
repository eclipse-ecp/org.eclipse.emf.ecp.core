/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.makeithappen.application.sample.rap.j2ee;

import org.eclipse.core.databinding.observable.Realm;

/**
 * A {@link PhaseListener} to set the default realm.
 */

@SuppressWarnings({ "deprecation", "serial" })
public class DataBindingPhaseListener implements PhaseListener {

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.PROCESS_ACTION;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		final Realm realm = (Realm) RWT.getUISession().getAttribute("realm"); //$NON-NLS-1$
		RealmSetter.setRealm(realm);
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		RealmSetter.setRealm(null);
	}
}
