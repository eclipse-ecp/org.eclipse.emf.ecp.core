/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 * Johannes Falterimeier - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.edit.internal.swt.util.DoubleColumnRow;
import org.eclipse.emf.ecp.edit.internal.swt.util.SingleColumnRow;
import org.eclipse.emf.ecp.edit.internal.swt.util.ThreeColumnRow;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * The Class SWTViewRenderer.
 */
public class SWTViewRenderer extends AbstractSWTRenderer<VView> {

	/** The Constant INSTANCE. */
	public static final SWTViewRenderer INSTANCE = new SWTViewRenderer();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, final VView vView,
		final ViewModelContext viewContext)
		throws NoPropertyDescriptorFoundExeption, NoRendererFoundException {

		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setBackground(parent.getBackground());
		// Gridlayout does not have an overflow as other Layouts might have.
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(columnComposite);

		for (final VContainedElement child : vView.getChildren()) {

			List<RenderingResultRow<Control>> resultRows;
			try {
				resultRows = SWTRendererFactory.INSTANCE.render(columnComposite, child, viewContext);
			} catch (final NoPropertyDescriptorFoundExeption ex) {
				Activator.getDefault().getLog().log(new Status(IStatus.INFO, Activator.PLUGIN_ID, ex.getMessage(), ex));
				continue;
			} catch (final NoRendererFoundException ex) {
				Activator.getDefault().getLog().log(new Status(IStatus.INFO, Activator.PLUGIN_ID, ex.getMessage(), ex));
				continue;
			}

			// TOOD; when does this case apply?
			if (resultRows == null) {
				continue;
			}

			setLayoutDataForResultRows(resultRows);
		}
		return createResult(columnComposite);

	}

	@Override
	protected void setLayoutDataForResultRows(final List<RenderingResultRow<Control>> resultRows) {
		for (final RenderingResultRow<Control> row : resultRows) {
			if (SingleColumnRow.class.isInstance(row)) {
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).span(3, 1)
					.applyTo(((SingleColumnRow) row).getControl());
			}
			else if (DoubleColumnRow.class.isInstance(row)) {
				GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false)
					.applyTo(((DoubleColumnRow) row).getLeftControl());
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(2, 1)
					.applyTo(((DoubleColumnRow) row).getRightControl());
			}
			else if (ThreeColumnRow.class.isInstance(row)) {
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(false, false)
					.applyTo(((ThreeColumnRow) row).getLeftControl());
				GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(false, false).hint(16, 17)
					.applyTo(((ThreeColumnRow) row).getMiddleControl());
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false)
					.applyTo(((ThreeColumnRow) row).getRightControl());
			}
		}
	}

}
