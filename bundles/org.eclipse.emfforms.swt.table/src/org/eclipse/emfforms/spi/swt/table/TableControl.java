/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

/**
 * @author jonas
 *
 */
public interface TableControl {

	/**
	 * Returns <code>true</code> if the widget has been disposed,
	 * and <code>false</code> otherwise.
	 *
	 * @return <code>true</code> when the widget is disposed and <code>false</code> otherwise
	 */
	boolean isDisposed();

	/**
	 * Returns the height of the area which would be used to
	 * display <em>one</em> of the items in the receiver.
	 *
	 * @return the height of one item
	 */
	int getItemHeight();

	/**
	 * Returns <code>true</code> if the receiver's header is visible,
	 * and <code>false</code> otherwise.
	 *
	 * @return the receiver's header's visibility state
	 */
	boolean getHeaderVisible();

	/**
	 * Returns the height of the receiver's header.
	 *
	 * @return the height of the header
	 */
	int getHeaderHeight();

}
