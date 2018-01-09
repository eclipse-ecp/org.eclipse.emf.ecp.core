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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.ConditionService;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Partial implementation of a condition service for composite conditions.
 *
 * @param <C> the type of condition for which I provide service
 *
 * @author Christian W. Damus
 */
abstract class CompositeConditionService<C extends Condition> implements ConditionService<C> {

	private ServiceReference<ConditionServiceManager> conditionServiceManagerReference;
	private ConditionServiceManager conditionServiceManager;

	/**
	 * Initializes me.
	 */
	protected CompositeConditionService() {
		super();
	}

	/**
	 * Deactivates me.
	 */
	@Deactivate
	void deactivate() {
		conditionServiceManager = null;
		if (conditionServiceManagerReference != null) {
			getBundleContext().ungetService(conditionServiceManagerReference);
		}
	}

	private BundleContext getBundleContext() {
		return FrameworkUtil.getBundle(getClass()).getBundleContext();
	}

	@Override
	public Set<UniqueSetting> getConditionSettings(C condition, EObject domainModel) {
		final Set<UniqueSetting> result = new LinkedHashSet<UniqueSetting>();
		final ConditionServiceManager manager = getConditionServiceManager();
		final Iterable<? extends EObject> targets = getTargets(condition, domainModel);

		for (final Condition component : components(condition)) {
			for (final EObject next : targets) {
				result.addAll(manager.getConditionSettings(component, next));
			}
		}

		return result;
	}

	/**
	 * Obtains the objects on which a {@code condition} is evaluated.
	 *
	 * @param condition a composite condition of my type
	 * @param domainModel the source from which to navigate to obtain the targets for evaluation
	 *            of the {@code condition}
	 * @return the evaluation targets
	 */
	protected List<? extends EObject> getTargets(C condition, EObject domainModel) {
		return Collections.singletonList(domainModel);
	}

	/**
	 * Obtains the nested conditions in a composite {@code condition} of my type.
	 *
	 * @param condition the composite condition from which to get components
	 * @return the {@code condition}'s zero or more nested conditions that are its components
	 */
	protected abstract Iterable<? extends Condition> components(C condition);

	@Override
	public Set<VDomainModelReference> getDomainModelReferences(C condition) {
		final Set<VDomainModelReference> result = new LinkedHashSet<VDomainModelReference>();
		final ConditionServiceManager manager = getConditionServiceManager();

		for (final Condition next : components(condition)) {
			result.addAll(manager.getDomainModelReferences(next));
		}

		return result;
	}

	@Override
	public boolean evaluate(C condition, EObject domainModel) {
		return condition.evaluate(domainModel);
	}

	@Override
	public boolean evaluateChangedValues(C condition, EObject domainModel, Map<Setting, Object> possibleNewValues) {
		return condition.evaluateChangedValues(domainModel, possibleNewValues);
	}

	private ConditionServiceManager getConditionServiceManager() {
		if (conditionServiceManager == null) {
			final BundleContext context = getBundleContext();
			conditionServiceManagerReference = context.getServiceReference(ConditionServiceManager.class);
			if (conditionServiceManagerReference == null) {
				throw new IllegalStateException("No ConditionServiceManager"); //$NON-NLS-1$
			}
			conditionServiceManager = context.getService(conditionServiceManagerReference);
		}
		return conditionServiceManager;
	}

}
