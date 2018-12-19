/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * View service factory that creates {@link DmrToSegmentsViewService DmrToSegmentsViewServices}.
 *
 * @author Lucas Koehler
 */
@Component(name = "DmrToSegmentsViewServiceFactory")
public class DmrToSegmentsViewServiceFactory implements EMFFormsViewServiceFactory<DmrToSegmentsViewService> {

	/**
	 * This flag enables the automatic generation of segments from existing DMRs.
	 */
	public static final String SEGMENT_GENERATION = "-enableSegmentGeneration"; //$NON-NLS-1$

	private boolean segmentMode;

	private ReportService reportService;

	/**
	 * Activate this service and determine whether the application runs in segment generation mode.
	 */
	@Activate
	void activate() {
		final String[] applicationArgs = Platform.getApplicationArgs();
		Arrays.stream(applicationArgs).filter(SEGMENT_GENERATION::equals).findFirst()
			.ifPresent(s -> segmentMode = true);
		if (segmentMode) {
			reportService.report(new AbstractReport("Segment Generation for legacy DMRs is enabled.", IStatus.INFO)); //$NON-NLS-1$
		}
	}

	/**
	 * Set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		return EMFFormsViewServicePolicy.IMMEDIATE;
	}

	@Override
	public EMFFormsViewServiceScope getScope() {
		return EMFFormsViewServiceScope.LOCAL;
	}

	@Override
	public double getPriority() {
		return -1000d;
	}

	@Override
	public Class<DmrToSegmentsViewService> getType() {
		return DmrToSegmentsViewService.class;
	}

	@Override
	public DmrToSegmentsViewService createService(EMFFormsViewContext emfFormsViewContext) {
		if (segmentMode) {
			return new DmrToSegmentsViewService(emfFormsViewContext);
		}
		return null;
	}

}
