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
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.spi.swt.table.TableViewerFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * A factory to create {@link GridTableViewerComposite GridTableViewerComposites}.
 *
 * @author Johannes Faltermeier
 *
 */
public final class GridTableViewerFactory extends TableViewerFactory {

	/** Default constructor. */
	public GridTableViewerFactory() {
		super();
	}

	/**
	 * Use this method if you want to customize any behavior of the {@link GridTableViewerComposite}. This will return
	 * a {@link GridTableViewerSWTBuilder} which allows to customize certain aspects.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style bits which will be passed to the {@link GridTableViewerComposite}
	 * @param input the input object
	 * @return the builder
	 */
	public static GridTableViewerSWTBuilder fillDefaults(Composite composite, int swtStyleBits, Object input) {
		return new GridTableViewerSWTBuilder(composite, swtStyleBits, input,
			Observables.constantObservableValue("", String.class), //$NON-NLS-1$
			Observables.constantObservableValue("", String.class)); //$NON-NLS-1$
	}

	/**
	 * Use this method if you want to customize any behavior of the {@link GridTableViewerComposite}. This will return
	 * a {@link GridTableViewerSWTBuilder} which allows to customize certain aspects.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style bits which will be passed to the {@link GridTableViewerComposite}
	 * @param input the input object
	 * @param title the title
	 * @param tooltip the tooltip
	 * @return the builder
	 */
	public static GridTableViewerSWTBuilder fillDefaults(Composite composite, int swtStyleBits, Object input,
		IObservableValue title, IObservableValue tooltip) {
		return new GridTableViewerSWTBuilder(composite, swtStyleBits, input, title, tooltip);
	}

	/**
	 * Use this method if you want to customize any behavior of the {@link GridTableViewerComposite}. This will return
	 * a {@link GridTableViewerSWTBuilder} which allows to customize certain aspects.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style bits which will be passed to the {@link GridTableViewerComposite}
	 * @param input the input object
	 * @param title the title
	 * @param tooltip the tooltip
	 * @return the builder
	 */
	public static GridTableViewerSWTBuilder fillDefaults(Composite composite, int swtStyleBits, Object input,
		String title, String tooltip) {
		return new GridTableViewerSWTBuilder(composite, swtStyleBits, input,
			Observables.constantObservableValue(title, String.class),
			Observables.constantObservableValue(tooltip, String.class));
	}
}
