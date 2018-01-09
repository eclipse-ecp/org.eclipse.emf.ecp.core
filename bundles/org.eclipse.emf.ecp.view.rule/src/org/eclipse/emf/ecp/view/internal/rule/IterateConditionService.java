/*******************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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
import org.eclipse.emf.ecp.view.spi.rule.ConditionService;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Condition service for the {@link IterateCondition}.
 *
 * @author Christian W. Damus
 */
@Component(service = ConditionService.class)
public class IterateConditionService extends CompositeConditionService<IterateCondition> {

	private EMFFormsDatabindingEMF databindings;
	private ReportService reportService;

	/**
	 * Initializes me.
	 */
	public IterateConditionService() {
		super();
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
	public EClass getConditionType() {
		return RulePackage.Literals.ITERATE_CONDITION;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<? extends EObject> getTargets(IterateCondition condition, EObject domainModel) {
		try {
			return databindings.getObservableList(condition.getItemReference(), domainModel);
		} catch (final DatabindingFailedException e) {
			reportService.report(new DatabindingFailedReport(e));
			return Collections.emptyList();
		}
	}

	@Override
	protected Iterable<? extends Condition> components(IterateCondition condition) {
		final Condition result = condition.getItemCondition();
		if (result == null) {
			return Collections.emptyList();
		}
		return Collections.singletonList(result);
	}

	@Override
	public Set<UniqueSetting> getConditionSettings(IterateCondition condition, EObject domainModel) {
		final Set<UniqueSetting> result = super.getConditionSettings(condition, domainModel);

		// Also, the setting that is our reference
		try {
			result.add(UniqueSetting.createSetting(databindings.getSetting(condition.getItemReference(), domainModel)));
		} catch (final DatabindingFailedException e) {
			reportService.report(new DatabindingFailedReport(e));
		}

		return result;
	}
}
