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

import java.util.List;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * <p>
 * A {@link TableViewerCompositeBuilder} is used to create the overall {@link Composite} hierarchy for the
 * {@link TableViewerComposite}. The layout will be created by a call to {@link #createCompositeLayout(Composite)}.
 * This method is supposed to create at least a parent composite for {@link org.eclipse.jface.viewers.TableViewer
 * TableViewer}.
 * </p>
 * <p>
 * Optionally this builder may create a {@link Label} which will show a title/description for the table.
 * </p>
 * <p>
 * Optionally this builder may create multiple controls which will show status/validation results. These controls will
 * be accessible.
 * </p>
 * <p>
 * Optionally this builder may create a parent {@link Composite} which will contain all
 * {@link org.eclipse.swt.widgets.Button Buttons} to
 * control/modify the table entries.
 * </p>
 *
 * @author Johannes Faltermeier
 *
 */
public interface TableViewerCompositeBuilder {

	/**
	 * Called to create the {@link Composite composites}.
	 *
	 * @param parent the parent
	 */
	void createCompositeLayout(Composite parent);

	/**
	 * Called after {@link #createCompositeLayout(Composite)}.
	 *
	 * @return the title {@link Label} if available
	 */
	Optional<Label> getTitleLabel();

	/**
	 * Called after {@link #createCompositeLayout(Composite)}.
	 *
	 * @return the list of validation {@link Control controls}.
	 */
	Optional<List<Control>> getValidationControls();

	/**
	 * Called after {@link #createCompositeLayout(Composite)}.
	 *
	 * @return the parent {@link Composite} for {@link org.eclipse.swt.widgets.Button control-buttons}.
	 */
	Optional<Composite> getButtonComposite();

	/**
	 * Called after {@link #createCompositeLayout(Composite)}.
	 *
	 * @return the parent {@link Composite} for the {@link org.eclipse.jface.viewers.AbstractTableViewer TableViewer}.
	 */
	Composite getViewerComposite();

}
