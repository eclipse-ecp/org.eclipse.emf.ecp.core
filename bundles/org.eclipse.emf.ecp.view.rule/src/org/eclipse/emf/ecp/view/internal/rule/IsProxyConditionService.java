/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.ConditionService;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * A condition service for the {@link IsProxyCondition}.
 *
 * @author Christian W. Damus
 */
@Component(service = ConditionService.class)
public class IsProxyConditionService extends CompositeConditionService<IsProxyCondition> {

	private EMFFormsDatabindingEMF databindings;
	private ReportService reportService;

	/**
	 * Initializes me.
	 */
	public IsProxyConditionService() {
		super();
	}

	@Override
	public EClass getConditionType() {
		return RulePackage.Literals.IS_PROXY_CONDITION;
	}

	/**
	 * Sets my data bindings provider dependency.
	 *
	 * @param databindings a provider of data bindings
	 */
	@Reference
	void setDatabindings(EMFFormsDatabindingEMF databindings) {
		this.databindings = databindings;
	}

	/**
	 * Sets my reporting service dependency.
	 *
	 * @param reportService the reporting service
	 */
	@Reference
	void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	protected Iterable<? extends Condition> components(IsProxyCondition condition) {
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<? extends EObject> getTargets(IsProxyCondition condition, EObject domainModel) {
		final VDomainModelReference dmr = condition.getDomainModelReference();
		if (dmr == null) {
			return Collections.emptyList();
		}

		try {
			return databindings.getObservableList(dmr, domainModel);
		} catch (final DatabindingFailedException e) {
			reportService.report(new DatabindingFailedReport(e));
			return Collections.emptyList();
		}
	}

	@Override
	public Set<VDomainModelReference> getDomainModelReferences(IsProxyCondition condition) {
		final VDomainModelReference dmr = condition.getDomainModelReference();
		if (dmr == null) {
			return Collections.emptySet();
		}
		return Collections.singleton(dmr);
	}

	@Override
	public Set<UniqueSetting> getConditionSettings(IsProxyCondition condition, EObject domainModel) {
		Set<UniqueSetting> result;

		final VDomainModelReference dmr = condition.getDomainModelReference();
		if (dmr == null) {
			// The only setting is the domain model, itself
			result = super.getConditionSettings(condition, domainModel);
		} else {
			// The setting that is our reference
			try {
				result = Collections.singleton(UniqueSetting.createSetting(databindings.getSetting(dmr, domainModel)));
			} catch (final DatabindingFailedException e) {
				result = Collections.emptySet();
				reportService.report(new DatabindingFailedReport(e));
			}
		}

		return result;
	}

}
