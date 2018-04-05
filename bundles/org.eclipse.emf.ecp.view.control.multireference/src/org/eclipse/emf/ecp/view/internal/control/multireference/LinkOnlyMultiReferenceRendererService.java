/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Martin Fleck - initial API and implementation
 * Lucas Koehler - Adapted for universal usage by using an annotation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.control.multireference;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the {@link LinkOnlyMultiReferenceRenderer} for multi references. The renderer is only used if the
 * corresponding control specifies an annotation with key {@value #ANNOTATION_KEY}.
 *
 * @author Martin Fleck
 * @author Lucas Koehler
 *
 */
@Component(name = "LinkOnlyMultiReferenceRendererService")
public class LinkOnlyMultiReferenceRendererService implements EMFFormsDIRendererService<VControl> {

	/**
	 * The annotation key specifying that this renderer is applicable for a multi reference.
	 */
	public static final String ANNOTATION_KEY = "hideAddNewButton"; //$NON-NLS-1$

	private EMFFormsDatabinding databindingService;
	private ReportService reportService;

	/**
	 * Called by the initializer to set the EMFFormsDatabinding.
	 *
	 * @param databindingService The EMFFormsDatabinding
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsDatabinding(EMFFormsDatabinding databindingService) {
		this.databindingService = databindingService;
	}

	/**
	 * Called by the initializer to set the ReportService.
	 *
	 * @param reportService The ReportService
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		if (control.getDomainModelReference() == null) {
			return NOT_APPLICABLE;
		}

		IValueProperty valueProperty;
		try {
			valueProperty = databindingService
				.getValueProperty(control.getDomainModelReference(), viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		if (!feature.isMany()) {
			return NOT_APPLICABLE;
		}
		if (EAttribute.class.isInstance(feature)) {
			return NOT_APPLICABLE;
		}

		// Check that the annotation is present; we do not care about the value
		if (isAnnotationPresent(vElement)) {
			return 6;
		}

		return NOT_APPLICABLE;
	}

	// Method by itself to reduce n-path complexity of #isApplicable
	private boolean isAnnotationPresent(VElement vElement) {
		for (final VAttachment vAttachment : vElement.getAttachments()) {
			if (VAnnotation.class.isInstance(vAttachment)
				&& ANNOTATION_KEY.equals(VAnnotation.class.cast(vAttachment).getKey())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VControl>> getRendererClass() {
		return LinkOnlyMultiReferenceRenderer.class;
	}

}
