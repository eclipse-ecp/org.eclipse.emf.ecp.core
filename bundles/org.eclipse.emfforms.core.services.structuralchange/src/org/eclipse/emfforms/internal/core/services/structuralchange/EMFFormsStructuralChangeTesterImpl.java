/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.structuralchange;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Implementation of {@link EMFFormsStructuralChangeTester}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsStructuralChangeTesterImpl")
public class EMFFormsStructuralChangeTesterImpl implements EMFFormsStructuralChangeTester {

	private ReportService reportService;
	private final Set<StructuralChangeTesterInternal> structuralChangeTesters = new LinkedHashSet<StructuralChangeTesterInternal>();

	private static final RankingHelper<StructuralChangeTesterInternal> RANKING_HELPER = //
		new RankingHelper<StructuralChangeTesterInternal>(
			StructuralChangeTesterInternal.class,
			StructuralChangeTesterInternal.NOT_APPLICABLE,
			StructuralChangeTesterInternal.NOT_APPLICABLE);

	/**
	 * Sets the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Called by the framework to add a {@link StructuralChangeTesterInternal}.
	 *
	 * @param structuralChangeTester The {@link StructuralChangeTesterInternal} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addStructuralChangeTesterInternal(StructuralChangeTesterInternal structuralChangeTester) {
		structuralChangeTesters.add(structuralChangeTester);
	}

	/**
	 * Called by the framework to remove a {@link StructuralChangeTesterInternal}.
	 *
	 * @param structuralChangeTester The {@link StructuralChangeTesterInternal} to remove
	 */
	protected void removeStructuralChangeTesterInternal(StructuralChangeTesterInternal structuralChangeTester) {
		structuralChangeTesters.remove(structuralChangeTester);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 */
	@Override
	public boolean isStructureChanged(final VDomainModelReference reference, final EObject domainRootObject,
		ModelChangeNotification notification) {

		final StructuralChangeTesterInternal bestTester = RANKING_HELPER.getHighestRankingElement(
			structuralChangeTesters,
			new RankingHelper.RankTester<StructuralChangeTesterInternal>() {

				@Override
				public double getRank(final StructuralChangeTesterInternal tester) {
					return tester.isApplicable(reference);
				}

			});

		if (bestTester == null) {
			reportService.report(new AbstractReport("Warning: Structural changes of the DMR: " + reference //$NON-NLS-1$
				+ "could not be analyzed because no suitable StructuralChangeTesterInternal was available.")); //$NON-NLS-1$
			return false;
		}

		return bestTester.isStructureChanged(reference, domainRootObject, notification);
	}

}
