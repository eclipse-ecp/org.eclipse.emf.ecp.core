/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * TableControlSWTRendererService which provides the TableControlSWTRenderer.
 *
 * @author Eugen Neufeld
 *
 */
public class TableControlSWTRendererService implements EMFFormsDIRendererService<VTableControl> {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(VElement,ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VTableControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VTableControl tableControl = VTableControl.class.cast(vElement);
		final EList<VDomainModelReferenceSegment> segments = tableControl.getDomainModelReference().getSegments();
		final VDomainModelReferenceSegment lastSegment = segments.get(segments.size() - 1);
		if (segments.isEmpty() || !VMultiDomainModelReferenceSegment.class.isInstance(lastSegment)) {
			return NOT_APPLICABLE;
		}
		if (DetailEditing.NONE == tableControl.getDetailEditing()) {
			return 10;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#getRendererClass()
	 */
	@Override
	public Class<? extends AbstractSWTRenderer<VTableControl>> getRendererClass() {
		return TableControlSWTRenderer.class;
	}

}
