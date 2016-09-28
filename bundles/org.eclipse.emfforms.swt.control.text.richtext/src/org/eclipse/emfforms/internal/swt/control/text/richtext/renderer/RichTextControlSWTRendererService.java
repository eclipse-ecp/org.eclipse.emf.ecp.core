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
package org.eclipse.emfforms.internal.swt.control.text.richtext.renderer;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.swt.control.text.richtext.renderer.RichTextControlSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * {@link EMFFormsDIRendererService Renderer service} for the {@link RichTextControlSWTRenderer}.
 *
 * @author jfaltermeier
 *
 */
@Component
public class RichTextControlSWTRendererService implements EMFFormsDIRendererService<VControl> {

	private static final String ANNOTATION_SOURCE = "org.eclipse.emfforms"; //$NON-NLS-1$
	private static final String ANNOTATION_KEY = "autocomplete"; //$NON-NLS-1$
	private static final String ANNOTATION_VALUE = "true"; //$NON-NLS-1$

	private EMFFormsDatabinding databinding;
	private EMFFormsEditSupport emfFormsEditSupport;

	/**
	 * Sets the {@link EMFFormsDatabinding} service.
	 *
	 * @param databinding service
	 */
	@Reference(unbind = "-")
	public void setDatabinding(EMFFormsDatabinding databinding) {
		this.databinding = databinding;
	}

	/**
	 * Sets the {@link EMFFormsEditSupport}.
	 *
	 * @param emfFormsEditSupport {@link EMFFormsEditSupport}
	 */
	@Reference(unbind = "-")
	public void setEMFFormsEditSupport(EMFFormsEditSupport emfFormsEditSupport) {
		this.emfFormsEditSupport = emfFormsEditSupport;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		try {
			if (!VControl.class.isInstance(vElement)) {
				return NOT_APPLICABLE;
			}

			final VControl control = VControl.class.cast(vElement);

			if (control.getDomainModelReference() == null) {
				return NOT_APPLICABLE;
			}

			@SuppressWarnings("rawtypes")
			final IValueProperty valueProperty = databinding.getValueProperty(control.getDomainModelReference(),
				viewModelContext.getDomainModel());
			final EStructuralFeature feature = EStructuralFeature.class.cast(valueProperty.getValueType());

			if (feature.isMany()) {
				return NOT_APPLICABLE;
			}

			if (!EAttribute.class.isInstance(feature)) {
				return NOT_APPLICABLE;
			}

			final EAttribute attribute = EAttribute.class.cast(feature);
			final Class<?> instanceClass = attribute.getEAttributeType().getInstanceClass();
			if (instanceClass == null) {
				return NOT_APPLICABLE;
			}

			if (!String.class.isAssignableFrom(instanceClass)) {
				return NOT_APPLICABLE;
			}

			if (emfFormsEditSupport.isMultiLine(control.getDomainModelReference(), viewModelContext.getDomainModel())) {
				return 10;
			}

		} catch (final DatabindingFailedException ex) {
			return NOT_APPLICABLE;
		}

		return NOT_APPLICABLE;
	}

	/**
	 * Checks whether the given feature has an autocomplete annotation which is set to true.
	 *
	 * @param feature the feature to check
	 * @return <code>true</code> if autocomplete should be used, <code>false</code> othwise
	 */
	boolean hasAutoCompleteAnnotation(EStructuralFeature feature) {
		final String annotation = EcoreUtil.getAnnotation(feature, ANNOTATION_SOURCE, ANNOTATION_KEY);
		if (annotation == null) {
			return false;
		}
		return ANNOTATION_VALUE.equalsIgnoreCase(annotation);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#getRendererClass()
	 */
	@Override
	public Class<? extends AbstractSWTRenderer<VControl>> getRendererClass() {
		return RichTextControlSWTRenderer.class;
	}

}
