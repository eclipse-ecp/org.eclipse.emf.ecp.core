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
package org.eclipse.emfforms.internal.swt.categorization.expandbar;

import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

/**
 * CategorizationElementExpandBarRendererService which provides the ExpandBarCategorizationElementRenderer.
 *
 * @author Eugen Neufeld
 *
 */
@Component
public class CategorizationElementExpandBarRendererService
	implements EMFFormsDIRendererService<VCategorizationElement> {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(VElement,ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!FrameworkUtil.getBundle(Display.class).getSymbolicName()
			.contains(".rwt")) { //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (!VCategorizationElement.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		if (VCategorizationElement.class.isInstance(vElement)) {
			final VCategorizationElement categorizationElement = VCategorizationElement.class.cast(vElement);
			for (final VAbstractCategorization abstractCategorization : categorizationElement.getCategorizations()) {
				if (!VCategorization.class.isInstance(abstractCategorization)) {
					return NOT_APPLICABLE;
				}
				final VCategorization categorization = VCategorization.class.cast(abstractCategorization);
				for (final VAbstractCategorization innerAbstractCategorization : categorization.getCategorizations()) {
					if (!VCategory.class.isInstance(innerAbstractCategorization)) {
						return NOT_APPLICABLE;
					}
				}
			}
		}
		return 10d;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#getRendererClass()
	 */
	@Override
	public Class<? extends AbstractSWTRenderer<VCategorizationElement>> getRendererClass() {
		return ExpandBarCategorizationElementRenderer.class;
	}

}
