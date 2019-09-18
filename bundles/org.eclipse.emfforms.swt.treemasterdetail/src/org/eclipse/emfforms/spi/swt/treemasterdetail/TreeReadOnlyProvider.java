/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.spi.swt.treemasterdetail;

/**
 * @author Lucas Koehler
 * @since 1.22
 *
 */
public interface TreeReadOnlyProvider {

	/**
	 * Returns whether the tree should be read-only.
	 * 
	 * @return <code>true</code> if the tree is read-only
	 */
	boolean isReadOnly();
}
