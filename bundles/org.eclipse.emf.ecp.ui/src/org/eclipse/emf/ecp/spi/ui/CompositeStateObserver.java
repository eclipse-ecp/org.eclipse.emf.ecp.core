/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.spi.ui;

import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author Tobias Verhoeven
 * @since 1.1
 */
public interface CompositeStateObserver {

	/**
	 * This method is called to inform observers weather the according composites finished-state has changed.
	 *
	 * @param caller the composite
	 * @param complete true if complete, false if incomplete
	 * @param projectProperties the project properties
	 */
	void compositeChangedState(Composite caller, boolean complete, ECPProperties projectProperties);

}
