/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.ide.view.segments.ToolingModeUtil;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * DI renderer service for {@link TableColumnsDMRTableControl}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "TableColumnsDMRTableControlService")
public class TableColumnsDMRTableControlService implements EMFFormsDIRendererService<VControl> {
	/**
	 * Key of the annotation that defines that the child dmrs of a dmr's multi segment should be rendered.
	 */
	private static final String SHOW_CHILD_DOMAIN_MODEL_REFERENCES = "showChildDomainModelReferences"; //$NON-NLS-1$

	private EMFFormsDatabinding databindingService;
	private ReportService reportService;

	/**
	 * Called by the framework to set the {@link EMFFormsDatabinding}.
	 *
	 * @param databindingService The {@link EMFFormsDatabinding}
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsDatabinding(EMFFormsDatabinding databindingService) {
		this.databindingService = databindingService;
	}

	/**
	 * Called by the framework to set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (ToolingModeUtil.isSegmentToolingEnabled()) {
			return NOT_APPLICABLE;
		}
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		if (control.getDomainModelReference() == null) {
			return NOT_APPLICABLE;
		}
		if (viewModelContext.getDomainModel().eClass() != VTablePackage.Literals.TABLE_CONTROL) {
			return NOT_APPLICABLE;
		}

		// Check that the correct annotation is set
		if (!checkAnnotation(control)) {
			return NOT_APPLICABLE;
		}

		IValueProperty<?, ?> valueProperty;
		try {
			valueProperty = databindingService.getValueProperty(control.getDomainModelReference(),
				viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();

		if (VViewPackage.eINSTANCE.getControl_DomainModelReference() != feature
			&& VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES != feature) {
			return NOT_APPLICABLE;
		}
		return 10d;
	}

	/**
	 * Checks whether the control has the annotation which states that the columns should be rendered.
	 *
	 * @param control
	 * @return <code>true</code> if the annotation is present
	 */
	private boolean checkAnnotation(final VControl control) {
		boolean showChildDmrs = false;
		for (final VAttachment attachment : control.getAttachments()) {
			if (VAnnotation.class.isInstance(attachment)) {
				final VAnnotation annotation = (VAnnotation) attachment;
				if (SHOW_CHILD_DOMAIN_MODEL_REFERENCES.equals(annotation.getKey())) {
					showChildDmrs = true;
					break;
				}
			}
		}
		return showChildDmrs;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VControl>> getRendererClass() {
		return TableColumnsDMRTableControl.class;
	}

}
