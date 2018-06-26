/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.data;

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.swt.widgets.Widget;

/**
 * This service is used by renderers to set data on SWT controls.
 *
 * @author Johannes Faltermeier
 * @since 1.18
 *
 */
public interface EMFFormsSWTControlDataService {

	/**
	 * Returns the string which is set as data.
	 *
	 * @param element the {@link VElement}
	 * @param widget the {@link Widget}
	 * @param id the main id of the {@link VElement}
	 * @param subId the sub-id for the {@link Widget}
	 * @return the data
	 */
	String getData(VElement element, Widget widget, String id, String subId);

}
