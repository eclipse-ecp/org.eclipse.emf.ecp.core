/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.handler.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.internal.editor.handler.MultiSegmentGenerator;
import org.eclipse.emf.ecp.view.internal.editor.handler.SegmentGenerator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;

/**
 * Renderer for domain model references that end in a multi segment (for table controls).
 *
 * @author Lucas Koehler
 *
 */
public class MultiDomainModelReferenceControlSWTRenderer extends DomainModelReferenceControlSWTRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 */
	@Inject
	public MultiDomainModelReferenceControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider, VTViewTemplateProvider vtViewTemplateProvider,
		EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			emfFormsEditSupport);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.DomainModelReferenceControlSWTRenderer#getSegmentGenerator()
	 */
	@Override
	protected SegmentGenerator getSegmentGenerator() {
		return new MultiSegmentGenerator();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.DomainModelReferenceControlSWTRenderer#getSelectionValidator()
	 */
	@Override
	protected EStructuralFeatureSelectionValidator getSelectionValidator() {
		return new EStructuralFeatureSelectionValidator() {
			@Override
			public String isValid(EStructuralFeature structuralFeature) {
				if (structuralFeature != null && EReference.class.isInstance(structuralFeature)
					&& structuralFeature.isMany()) {
					return null;
				}
				return "A multi reference must be selected."; //$NON-NLS-1$
			}
		};
	}

}
