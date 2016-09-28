/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This builder is used to create buttons for the TableViewer.
 *
 * @author Johannes Faltermeier
 *
 */
public interface ButtonBarBuilder extends NewElementCreator<Object, Button> {

	/**
	 * This method is called to fill the given {@link Composite} with all required buttons.
	 *
	 * @param composite the button bar
	 * @param viewer the {@link AbstractTableViewer}
	 */
	void fillButtonComposite(Composite composite, AbstractTableViewer viewer);

	/**
	 * Gets called when a button is pressed which needs to create a new element which gets added to the list.
	 *
	 * @param button the button which was pressed
	 * @return the new element
	 */
	@Override
	Object createNewElement(Button button);

}