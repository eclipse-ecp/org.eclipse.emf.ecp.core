/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * wesendon - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.internal.core.util.observer;

import org.eclipse.emf.ecp.core.util.observer.ECPObserver;

/**
 * An observer with a priority. The higher the number the more important is the observer.
 *
 * @author wesendon
 */
public interface ECPPrioritizedIObserver extends ECPObserver {

	/**
	 * Returns the priority of this observer. The higher the number returned
	 * by this method, the more likely it is that this observer is notified before
	 * others.
	 *
	 * @return the priority of this observer
	 */
	int getPriority();

}
