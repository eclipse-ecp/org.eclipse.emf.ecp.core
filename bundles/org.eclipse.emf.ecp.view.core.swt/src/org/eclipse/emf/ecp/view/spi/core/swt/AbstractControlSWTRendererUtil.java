/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.util.Set;

import org.eclipse.emf.ecp.view.model.common.util.RendererUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTControlLabelAlignmentStyleProperty;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
import org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty;
import org.eclipse.swt.SWT;

/**
 * Holds some Util methods for the {@link AbstractControlSWTRendererUtil} which may be reused by renderers which cannot
 * inherit from {@link AbstractControlSWTRendererUtil} but want to reuse functionality.
 *
 * @author Johannes Faltermeier
 * @since 1.17
 *
 */
public final class AbstractControlSWTRendererUtil {

	private AbstractControlSWTRendererUtil() {
		/* util */
	}

	/**
	 * The style bits for a control label.
	 *
	 * @param viewTemplateProvider the {@link VTViewTemplateProvider}
	 * @param control the {@link VElement}
	 * @param viewModelContext the {@link ViewModelContext}
	 * @return the style bits
	 * @since 1.18
	 */
	public static int getLabelStyleBits(VTViewTemplateProvider viewTemplateProvider, VElement control,
		ViewModelContext viewModelContext) {
		final VTControlLabelAlignmentStyleProperty alignmentStyleProperty = RendererUtil.getStyleProperty(
			viewTemplateProvider,
			control,
			viewModelContext,
			VTControlLabelAlignmentStyleProperty.class);

		int bits = SWT.NONE;

		if (alignmentStyleProperty != null) {
			switch (alignmentStyleProperty.getType()) {
			case RIGHT:
				bits |= SWT.RIGHT;
				break;
			default:
				break;
			}
		}

		final VTLabelWrapStyleProperty wrapStyleProperty = RendererUtil.getStyleProperty(
			viewTemplateProvider,
			control,
			viewModelContext,
			VTLabelWrapStyleProperty.class);

		if (wrapStyleProperty != null) {
			if (VTLabelWrapStyleProperty.class.cast(wrapStyleProperty).isWrapLabel()) {
				bits |= SWT.WRAP;
			}
		}

		return bits;
	}

	/**
	 * The {@link VTMandatoryStyleProperty} property.
	 *
	 * @param vtViewTemplateProvider the {@link VTViewTemplateProvider}
	 * @param control the {@link VControl}
	 * @param viewModelContext the {@link ViewModelContext}
	 * @return the property
	 */
	public static VTMandatoryStyleProperty getMandatoryStyle(
		VTViewTemplateProvider vtViewTemplateProvider,
		VControl control,
		ViewModelContext viewModelContext) {

		if (vtViewTemplateProvider == null) {
			return getDefaultMandatoryStyle();
		}
		final Set<VTStyleProperty> styleProperties = vtViewTemplateProvider
			.getStyleProperties(control, viewModelContext);
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTMandatoryStyleProperty.class.isInstance(styleProperty)) {
				return (VTMandatoryStyleProperty) styleProperty;
			}
		}
		return getDefaultMandatoryStyle();
	}

	private static VTMandatoryStyleProperty getDefaultMandatoryStyle() {
		return VTMandatoryFactory.eINSTANCE.createMandatoryStyleProperty();
	}

}
