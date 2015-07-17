/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ecore.editor.factory;

import org.eclipse.emf.ecp.ecore.editor.IEcoreGenModelLinker;
import org.eclipse.emf.ecp.ecore.editor.internal.EcoreGenModelLinkerImpl;

/**
 * Creates new instances of {@link IEcoreGenModelLinker}.
 */
public final class EcoreGenModelLinkerFactory {

	private EcoreGenModelLinkerFactory() {

	}

	/**
	 * Returns a new instance of {@link IEcoreGenModelLinker}.
	 *
	 * @return An instance of {@link IEcoreGenModelLinker}
	 */
	public static IEcoreGenModelLinker getEcoreGenModelLinker() {
		return new EcoreGenModelLinkerImpl();
	}

}
