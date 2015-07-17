/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.alignmentgroup.internal.swt;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.group.model.GroupType;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;

/**
 * EmbeddedGroupSWTRendererService which provides the EmbeddedGroupSWTRenderer.
 *
 * @author Eugen Neufeld
 *
 */
public class EmbeddedGroupSWTRendererService implements EMFFormsDIRendererService<VGroup> {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(VElement,ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VGroup.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		if (VGroup.class.cast(vElement).getGroupType() == GroupType.EMBEDDED) {
			return 5;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#getRendererClass()
	 */
	@Override
	public Class<? extends AbstractSWTRenderer<VGroup>> getRendererClass() {
		return EmbeddedGroupSWTRenderer.class;
	}

}
