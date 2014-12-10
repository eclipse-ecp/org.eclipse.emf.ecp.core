/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

/**
 * A ViewModelService which is instantiated only in the root context.
 * 
 * @author Eugen Neufeld
 * @since 1.5
 * 
 */
public interface GlobalViewModelService extends ViewModelService {

	void childViewModelContextAdded(ViewModelContext childContext);
}
