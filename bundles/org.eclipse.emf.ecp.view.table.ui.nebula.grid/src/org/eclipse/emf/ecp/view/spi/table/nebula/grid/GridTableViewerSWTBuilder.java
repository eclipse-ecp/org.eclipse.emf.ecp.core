/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTBuilder;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Jonas Helming
 *
 */
public class GridTableViewerSWTBuilder extends TableViewerSWTBuilder {

	/**
	 * Contructs a new instance.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style to apply
	 * @param input the input object
	 * @param title the title of the table viewer
	 * @param tooltip the tooltip
	 */
	public GridTableViewerSWTBuilder(Composite composite, int swtStyleBits, Object input,
		IObservableValue title,
		IObservableValue tooltip) {
		super(composite, swtStyleBits, input, title, tooltip);
	}

	/**
	 * Call this method after all desired customizations have been passed to the builder. The will create a new
	 * {@link GridTableViewerComposite} with the desired customizations.
	 *
	 * @return the {@link GridTableViewerComposite}
	 */
	@Override
	public GridTableViewerComposite build() {
		return new GridTableViewerComposite(getComposite(), getSwtStyleBits(), getInput(), getCustomization(),
			getTitle(), getTooltip());
	}
}
