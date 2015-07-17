/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.rule.RuleServiceHelper;

/**
 * @author emueller
 *
 */
public class RuleServiceHelperImpl implements ViewModelService, RuleServiceHelper {

	/**
	 * A predicate that is used to determine the set of {@link VElement}s
	 * if {@link RuleServiceHelperImpl#getInvolvedEObjects(Setting, Object, Class)} is called.
	 */
	private interface RenderablePredicate {
		boolean checkCurrentState(VElement renderable);
	}

	private ViewModelContext context;

	/**
	 * @return
	 */
	private RenderablePredicate createDisabledRenderablePredicate() {
		return new RenderablePredicate() {
			@Override
			public boolean checkCurrentState(VElement renderable) {
				return renderable.isEnabled();
			}
		};
	}

	/**
	 * @return
	 */
	private RenderablePredicate createHiddenRenderablePredicate() {
		return new RenderablePredicate() {
			@Override
			public boolean checkCurrentState(VElement renderable) {
				return renderable.isVisible();
			}

		};
	}

	@SuppressWarnings("unchecked")
	private <T extends VElement> Set<T> collectFalseValues(Class<T> cls,
		final Map<VElement, Boolean> renderableToStateMapping, RenderablePredicate renderablePredicate) {

		final Set<T> result = new LinkedHashSet<T>();

		for (final Map.Entry<VElement, Boolean> entry : renderableToStateMapping.entrySet()) {

			final VElement renderable = entry.getKey();
			final Boolean newState = entry.getValue();
			if (newState) {
				continue;
			}
			if (renderablePredicate.checkCurrentState(renderable) == newState) {
				continue;
			}
			if (cls.isInstance(renderable)) {
				result.add((T) renderable);
			}
			else {
				EObject parent = renderable.eContainer();
				while (parent != null) {
					if (cls.isInstance(parent)) {
						result.add((T) parent);
						break;
					}
					parent = parent.eContainer();
				}
			}

		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.RuleServiceHelper#getInvolvedEObjects(org.eclipse.emf.ecore.EStructuralFeature.Setting,
	 *      java.lang.Object, java.lang.Class)
	 */
	@Override
	public <T extends VElement> Set<T> getInvolvedEObjects(Setting setting, Object newValue, Class<T> renderableClass) {
		final Map<Setting, Object> newValues = new LinkedHashMap<EStructuralFeature.Setting, Object>();
		newValues.put(setting, newValue);

		final Map<VElement, Boolean> disabledRenderables = context.getService(RuleService.class)
			.getDisabledRenderables(newValues, UniqueSetting.createSetting(setting));
		final Map<VElement, Boolean> hiddenRenderables = context.getService(RuleService.class)
			.getHiddenRenderables(newValues, UniqueSetting.createSetting(setting));

		final Set<T> result = new LinkedHashSet<T>();
		result.addAll(collectFalseValues(renderableClass, disabledRenderables, createDisabledRenderablePredicate()));
		result.addAll(collectFalseValues(renderableClass, hiddenRenderables, createHiddenRenderablePredicate()));
		return result;
	}
}
