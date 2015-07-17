/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.core;

import org.eclipse.osgi.util.NLS;

/**
 * @author Jonas
 * @generated
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.internal.core.messages"; //$NON-NLS-1$
	public static String ECPProjectProperties_HasProjectUnsavedChanges;
	public static String ECPProjectProperties_IsProjectDirty;
	public static String ECPProjectProperties_Name;
	public static String ECPProjectProperties_NameOfProject;
	public static String ECPProjectProperties_Provider;
	public static String ECPProjectProperties_ProviderOfProject;
	public static String ECPProjectProperties_Repository;
	public static String ECPProjectProperties_RepositoryOfProject;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
