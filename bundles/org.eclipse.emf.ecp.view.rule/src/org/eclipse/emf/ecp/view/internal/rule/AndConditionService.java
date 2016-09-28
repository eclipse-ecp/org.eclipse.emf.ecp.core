/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.ConditionService;
import org.eclipse.emf.ecp.view.spi.rule.model.AndCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/** {@link ConditionService} for conditions of type {@link AndCondition}. */
@Component
public class AndConditionService implements ConditionService<AndCondition> {

	private BundleContext bundleContext;
	private ServiceReference<ConditionServiceManager> conditionServiceManagerReference;
	private ConditionServiceManager conditionServiceManager;

	@Override
	public EClass getConditionType() {
		return RulePackage.eINSTANCE.getAndCondition();
	}

	@Override
	public Set<UniqueSetting> getConditionSettings(AndCondition condition, EObject domainModel) {
		final Set<UniqueSetting> registeredSettings = new LinkedHashSet<UniqueSetting>();
		final ConditionServiceManager conditionService = getConditionServiceManager();
		for (final Condition cond : condition.getConditions()) {
			registeredSettings.addAll(conditionService.getConditionSettings(cond, domainModel));
		}
		return registeredSettings;
	}

	@Override
	public boolean evaluate(AndCondition condition, EObject domainModel) {
		if (condition == null) {
			return false;
		}
		return condition.evaluate(domainModel);
	}

	@Override
	public boolean evaluateChangedValues(AndCondition condition, EObject domainModel,
		Map<Setting, Object> possibleNewValues) {
		if (condition == null) {
			return false;
		}
		return condition.evaluateChangedValues(domainModel, possibleNewValues);
	}

	@Override
	public Set<VDomainModelReference> getDomainModelReferences(AndCondition condition) {
		final Set<VDomainModelReference> references = new HashSet<VDomainModelReference>();
		final ConditionServiceManager conditionService = getConditionServiceManager();
		for (final Condition cond : condition.getConditions()) {
			references.addAll(conditionService.getDomainModelReferences(cond));
		}
		return references;
	}

	/**
	 * Called by the framework when the component gets activated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Activate
	protected void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * Called by the framework when the component gets deactivated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		if (conditionServiceManagerReference != null) {
			bundleContext.ungetService(conditionServiceManagerReference);
			conditionServiceManager = null;
		}
	}

	private ConditionServiceManager getConditionServiceManager() {
		if (conditionServiceManager == null) {
			conditionServiceManagerReference = bundleContext
				.getServiceReference(ConditionServiceManager.class);
			if (conditionServiceManagerReference == null) {
				throw new IllegalStateException("No ConditionServiceManager available!"); //$NON-NLS-1$
			}
			conditionServiceManager = bundleContext.getService(conditionServiceManagerReference);
		}
		return conditionServiceManager;
	}

}
