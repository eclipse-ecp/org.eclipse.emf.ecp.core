/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.table.ui.swt.persistedstate;

import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.common.Optional;

/**
 * A service which persists the state of a table.
 *
 * @author Johannes Faltermeier
 *
 */
public interface PersistTableStateService {

	/**
	 * <p>
	 * Use this method if a non {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl VTableControl}
	 * {@link VControl} renders a table and wishes to persist the state.
	 * The service will look for an {@link VAttachment} with the {@link PersistTableStateServiceVAttachment} marker
	 * interface and will persist this attachment for the given control.
	 * <p/>
	 * <p>
	 * This method may be called multiple times.
	 * </p>
	 *
	 * @param controls the additional controls to registers.
	 */
	void registerAdditionalControls(VControl... controls);

	/**
	 * This methods gives access to previsouly persisted state attachments.
	 *
	 * @param control the control
	 * @return the attachment, if present
	 */
	Optional<VAttachment> getPersistedState(VControl control);

	/**
	 * Marker interface required by the {@link PersistTableStateService#registerAdditionalControls(VControl...)} method.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	interface PersistTableStateServiceVAttachment {

	}

}