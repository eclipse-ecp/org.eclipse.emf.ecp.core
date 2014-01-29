/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.rap.util.internal.clientscripting;

import org.eclipse.rap.rwt.scripting.ClientListener;

/**
 * @author jfaltermeier
 * 
 */
public class NumericalListener extends ClientListener {

	private static final long serialVersionUID = -8673834381120467715L;

	/**
	 * Constructor.
	 */
	public NumericalListener() {
		super(ScriptUtil.getJavaScriptStringFromResource("numerical.js")); //$NON-NLS-1$
	}
}
