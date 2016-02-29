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

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.swt.widgets.Composite;

/**
 * A factory to create {@link TableViewerComposite TableViewerComposites}.
 *
 * @author Johannes Faltermeier
 *
 */
public final class TableViewerFactory {

	private TableViewerFactory() {
		// factory
	}

	/**
	 * Use this method if you want to customize any behavior of the {@link TableViewerComposite}. This will return
	 * a {@link TableViewerSWTBuilder} which allows to customize certain aspects.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style bits which will be passed to the {@link TableViewerComposite}
	 * @param input the input object
	 * @return the builder
	 */
	public static TableViewerSWTBuilder fillDefaults(Composite composite, int swtStyleBits, Object input) {
		return new TableViewerSWTBuilder(composite, swtStyleBits, input,
			Observables.constantObservableValue("", String.class), //$NON-NLS-1$
			Observables.constantObservableValue("", String.class)); //$NON-NLS-1$
	}

	/**
	 * Use this method if you want to customize any behavior of the {@link TableViewerComposite}. This will return
	 * a {@link TableViewerSWTBuilder} which allows to customize certain aspects.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style bits which will be passed to the {@link TableViewerComposite}
	 * @param input the input object
	 * @param title the title
	 * @param tooltip the tooltip
	 * @return the builder
	 */
	public static TableViewerSWTBuilder fillDefaults(Composite composite, int swtStyleBits, Object input,
		IObservableValue title, IObservableValue tooltip) {
		return new TableViewerSWTBuilder(composite, swtStyleBits, input, title, tooltip);
	}

	/**
	 * Use this method if you want to customize any behavior of the {@link TableViewerComposite}. This will return
	 * a {@link TableViewerSWTBuilder} which allows to customize certain aspects.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style bits which will be passed to the {@link TableViewerComposite}
	 * @param input the input object
	 * @param title the title
	 * @param tooltip the tooltip
	 * @return the builder
	 */
	public static TableViewerSWTBuilder fillDefaults(Composite composite, int swtStyleBits, Object input,
		String title, String tooltip) {
		return new TableViewerSWTBuilder(composite, swtStyleBits, input,
			Observables.constantObservableValue(title, String.class),
			Observables.constantObservableValue(tooltip, String.class));
	}
}
