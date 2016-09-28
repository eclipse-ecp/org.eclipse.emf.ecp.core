/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Johannes Faltermeier - registry setting based instead of feature based
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.BidirectionalMap;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;

/**
 * Rule registry that maintains which {@link VElement}s
 * are affected if a setting is changed.
 *
 * @author emueller
 * @author jfaltermeier
 *
 * @param <T>
 *            the actual {@link Rule} type
 */
public class RuleRegistry<T extends Rule> {

	private final Map<UniqueSetting, BidirectionalMap<Condition, T>> settingToRules;
	private final BidirectionalMap<T, VElement> rulesToRenderables;
	private final Map<Condition, Set<UniqueSetting>> conditionToSettings;
	private final ViewModelContext context;
	private final Map<VDomainModelReference, Set<T>> dmrsToRules;
	private final DomainModelChangeListener domainModelChangeListener;
	private final ConditionServiceManager conditionServiceManager;

	/**
	 * Default constructor.
	 *
	 * @param context the view model context of the {@link RuleService} using this registry
	 *
	 */
	public RuleRegistry(ViewModelContext context) {
		this.context = context;
		settingToRules = new LinkedHashMap<UniqueSetting, BidirectionalMap<Condition, T>>();
		rulesToRenderables = new BidirectionalMap<T, VElement>();
		conditionToSettings = new LinkedHashMap<Condition, Set<UniqueSetting>>();
		dmrsToRules = new WeakHashMap<VDomainModelReference, Set<T>>();
		domainModelChangeListener = new DomainModelChangeListener();
		context.registerDomainChangeListener(domainModelChangeListener);
		conditionServiceManager = context.getService(ConditionServiceManager.class);
	}

	/**
	 * Creates a setting from the given {@link EObject} and the {@link Condition} and register it with the
	 * {@link VElement}.
	 *
	 * @param renderable
	 *            the {@link VElement} to be updated in case the condition changes
	 * @param rule
	 *            the parent rule holding the {@link Condition}
	 * @param condition
	 *            contains the attribute that the condition is depending on
	 * @param domainModel
	 *            the domain object that owns the attribute possibly being
	 *            changed
	 * @return the registered {@link UniqueSetting UniqueSettings}
	 */
	public Set<UniqueSetting> register(VElement renderable, T rule, Condition condition, EObject domainModel) {
		rulesToRenderables.put(rule, renderable);
		final Set<UniqueSetting> settings = conditionServiceManager.getConditionSettings(condition, domainModel);
		for (final UniqueSetting setting : settings) {
			mapSettingToRule(setting, condition, rule);
		}
		final Set<VDomainModelReference> domainModelReferences = conditionServiceManager
			.getDomainModelReferences(condition);
		mapDomainToDMRs(rule, domainModelReferences);
		return settings;
	}

	/**
	 * Removes the given rule from the registry.
	 *
	 * @param rule
	 *            the rule to be removed
	 * @return the {@link VElement} that belonged to the removed rule
	 */
	public VElement removeRule(T rule) {
		removeDomainModelChangeListener(rule);

		final Condition condition = rule.getCondition();
		if (condition != null) {
			return removeCondition(condition);
		}

		final Collection<BidirectionalMap<Condition, T>> values = settingToRules.values();
		for (final BidirectionalMap<Condition, T> bidirectionalMap : values) {
			if (bidirectionalMap.removeByValue(rule) != null) {
				break;
			}
		}

		final VElement renderable = rulesToRenderables.getValue(rule);
		rulesToRenderables.removeByKey(rule);
		return renderable;

	}

	/**
	 * Removes a rule from the dmrsToRules map. Consequently, the rule will no longer be re-registered when an
	 * associated {@link VDomainModelReference}'s structure changes.
	 *
	 * @param rule The rule to remove.
	 */
	private void removeDomainModelChangeListener(T rule) {
		final Set<VDomainModelReference> dmrs = new LinkedHashSet<VDomainModelReference>(dmrsToRules.keySet());
		for (final VDomainModelReference dmr : dmrs) {
			final Set<T> rules = dmrsToRules.get(dmr);
			if (rules != null) {
				rules.remove(rule);
			}
		}
	}

	/**
	 * Removes the given {@link VElement} from the registry.
	 *
	 * @param renderable
	 *            the renderable to be removed
	 */
	public void removeRenderable(VElement renderable) {
		final T rule = rulesToRenderables.removeByValue(renderable);
		removeDomainModelChangeListener(rule);
	}

	/**
	 * Removes the given condition from the registry.
	 *
	 * @param condition
	 *            the condition to be removed
	 * @return the {@link VElement} that belonged to the removed condition
	 */
	public VElement removeCondition(Condition condition) {
		VElement ret = null;
		T rule = null;
		final Set<UniqueSetting> settings = conditionToSettings.remove(condition);
		if (settings == null) {
			return ret;
		}
		for (final UniqueSetting setting : settings) {
			final BidirectionalMap<Condition, T> rules = settingToRules.get(setting);
			if (rules.keys().contains(condition)) {
				rule = rules.removeByKey(condition);
			}
			if (rules.keys().isEmpty()) {
				settingToRules.remove(setting);
			}
		}

		if (rule != null) {
			removeDomainModelChangeListener(rule);
			ret = rulesToRenderables.removeByKey(rule);
		}
		return ret;
	}

	private void mapSettingToRule(final UniqueSetting uniqueSetting, Condition condition, T rule) {
		if (!settingToRules.containsKey(uniqueSetting)) {
			settingToRules.put(uniqueSetting, new BidirectionalMap<Condition, T>());
		}
		settingToRules.get(uniqueSetting).put(condition, rule);
		if (!conditionToSettings.containsKey(condition)) {
			conditionToSettings.put(condition, new LinkedHashSet<UniqueSetting>());
		}
		conditionToSettings.get(condition).add(uniqueSetting);
	}

	/**
	 * Returns the settings of this registry.
	 *
	 * @return the settings of this registry.
	 */
	public Set<UniqueSetting> getSettings() {
		return settingToRules.keySet();
	}

	/**
	 * Returns all rules that would be affected if the value of given setting is changed.
	 *
	 * @param setting
	 *            the setting
	 * @return a list of {@link VElement}s that are affected of the setting change
	 */
	public Map<T, VElement> getAffectedRenderables(final UniqueSetting setting) {

		final Map<T, VElement> result = new LinkedHashMap<T, VElement>();
		final BidirectionalMap<Condition, T> bidirectionalMap = settingToRules.get(setting);

		if (bidirectionalMap == null) {
			return Collections.emptyMap();
		}

		for (final T rule : bidirectionalMap.values()) {
			final VElement renderable = rulesToRenderables.getValue(rule);
			result.put(rule, renderable);
		}

		return result;
	}

	private void mapDomainToDMRs(T rule, Set<VDomainModelReference> references) {
		for (final VDomainModelReference reference : references) {
			if (!dmrsToRules.containsKey(reference)) {
				dmrsToRules.put(reference, new LinkedHashSet<T>());
			}
			dmrsToRules.get(reference).add(rule);
		}
	}

	/**
	 * Disposes this {@link RuleRegistry}.
	 */
	public void dispose() {
		context.unregisterDomainChangeListener(domainModelChangeListener);
	}

	/**
	 * A model change listener that re-registers affected DMRs and rules when the domain model changes.
	 *
	 * @author Lucas Koehler
	 *
	 */
	private class DomainModelChangeListener implements ModelChangeListener {
		private final EMFFormsStructuralChangeTester structuralChangeTester;

		/**
		 * Creates a new {@link DomainModelChangeListener}.
		 */
		DomainModelChangeListener() {
			structuralChangeTester = context.getService(EMFFormsStructuralChangeTester.class);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.ModelChangeListener#notifyChange(org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
		 */
		@Override
		public void notifyChange(ModelChangeNotification notification) {
			final Set<VDomainModelReference> dmrs = new LinkedHashSet<VDomainModelReference>(dmrsToRules.keySet());
			for (final VDomainModelReference dmr : dmrs) {
				if (structuralChangeTester.isStructureChanged(dmr, context.getDomainModel(), notification)) {
					// dmr has changed => re-register every rule associated with it
					final Set<T> rules = dmrsToRules.remove(dmr);
					if (rules == null) {
						return;
					}

					for (final T rule : rules) {
						final VElement element = removeRule(rule);
						if (element == null) {
							return;
						}
						register(element, rule, rule.getCondition(), context.getDomainModel());
					}
				}
			}
		}

	}
}
