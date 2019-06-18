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
package org.eclipse.emf.ecp.spi.view.migrator;

/**
 * An exception that might occur during template model migration.
 *
 * @author Lucas Koehler
 * @since 1.17
 *
 */
public class TemplateModelMigrationException extends Exception {
	private static final long serialVersionUID = 81393242066915618L;

	/**
	 * Default constructor.
	 *
	 * @param ex the throwable to wrap
	 */
	public TemplateModelMigrationException(Throwable ex) {
		super(ex);
	}
}
