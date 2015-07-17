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
 * Johannes Faltermeier - registry setting based instead of feature based
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.BidirectionalMap;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.internal.rule.reporting.LeafConditionDMRResolutionFailedReport;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.SettingPath;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.rule.model.AndCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.OrCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionSettingIterator;

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
	private final Map<Rule, Set<VDomainModelReference>> rulesToDMRs;

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
		rulesToDMRs = new LinkedHashMap<Rule, Set<VDomainModelReference>>();
	}

	/**
	 * Creates a setting from the given {@link EObject} and the {@link LeafCondition} and register it with the
	 * {@link VElement}.
	 *
	 * @param renderable
	 *            the {@link VElement} to be updated in case the condition changes
	 * @param rule
	 *            the parent rule holding the {@link LeafCondition}
	 * @param condition
	 *            contains the attribute that the condition is depending on
	 * @param domainModel
	 *            the domain object that owns the attribute possibly being
	 *            changed
	 * @return the registered {@link UniqueSetting UniqueSettings}
	 */
	public Set<UniqueSetting> register(VElement renderable, T rule, Condition condition, EObject domainModel) {

		final Set<UniqueSetting> registeredSettings = new LinkedHashSet<UniqueSetting>();
		if (condition instanceof LeafCondition) {
			final LeafCondition leafCondition = (LeafCondition) condition;

			final VDomainModelReference domainModelReference = leafCondition.getDomainModelReference();
			if (domainModelReference == null) {
				return registeredSettings;
			}

			final boolean initSuccessful = domainModelReference.init(domainModel);
			mapDomainToDMRs(rule, Collections.singleton(domainModelReference));
			if (!initSuccessful) {
				if (org.eclipse.emf.ecp.view.spi.model.impl.Activator.getDefault() != null) {
					org.eclipse.emf.ecp.view.spi.model.impl.Activator.getDefault().getReportService()
						.report(new LeafConditionDMRResolutionFailedReport(leafCondition, false));
				}

				return registerLegacySupport(renderable, rule, registeredSettings, leafCondition, domainModelReference);
			}

			final LeafConditionSettingIterator iterator = new LeafConditionSettingIterator(leafCondition, true);
			while (iterator.hasNext()) {
				final Setting setting = iterator.next();
				final UniqueSetting uniqueSetting = UniqueSetting.createSetting(setting);
				mapSettingToRule(uniqueSetting, leafCondition, rule);
				registeredSettings.add(uniqueSetting);
			}
			mapDomainToDMRs(rule, iterator.getUsedValueDomainModelReferences());
			rulesToRenderables.put(rule, renderable);

		} else if (condition instanceof OrCondition) {
			final OrCondition orCondition = (OrCondition) condition;
			for (final Condition cond : orCondition.getConditions()) {
				registeredSettings.addAll(register(renderable, rule, cond, domainModel));
			}
		} else if (condition instanceof AndCondition) {
			final AndCondition andCondition = (AndCondition) condition;
			for (final Condition cond : andCondition.getConditions()) {
				registeredSettings.addAll(register(renderable, rule, cond, domainModel));
			}
		}

		return registeredSettings;
	}

	/**
	 * For legacy reasons leaf conditions with an unresolvable domain model reference will still be registered.
	 */
	private Set<UniqueSetting> registerLegacySupport(VElement renderable, T rule,
		final Set<UniqueSetting> registeredSettings, final LeafCondition leafCondition,
		final VDomainModelReference domainModelReference) {
		final Iterator<SettingPath> fullPathIterator = domainModelReference.getFullPathIterator();
		while (fullPathIterator.hasNext()) {
			final SettingPath path = fullPathIterator.next();
			final Iterator<Setting> pathIterator = path.getPath();
			final boolean validIterator = pathIterator.hasNext();
			while (pathIterator.hasNext()) {
				final Setting setting = pathIterator.next();
				final UniqueSetting uniqueSetting = UniqueSetting.createSetting(setting);
				mapSettingToRule(uniqueSetting, leafCondition, rule);
				registeredSettings.add(uniqueSetting);
			}
			if (validIterator) {
				rulesToRenderables.put(rule, renderable);
			}
		}
		return registeredSettings;
	}

	/**
	 * Removes the given rule from the registry.
	 *
	 * @param rule
	 *            the rule to be removed
	 * @return the {@link VElement} that belonged to the removed rule
	 */
	public VElement removeRule(T rule) {
		removeDMRListeners(rule);

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

	private void removeDMRListeners(T rule) {
		final Set<VDomainModelReference> dmrs = rulesToDMRs.remove(rule);
		if (dmrs != null) {
			for (final VDomainModelReference dmr : dmrs) {
				context.unregisterDomainChangeListener(dmr);
				final Iterator<DomainModelReferenceChangeListener> iterator = dmr.getChangeListener().iterator();
				while (iterator.hasNext()) {
					final DomainModelReferenceChangeListener next = iterator.next();
					if (DMRChangeListener.class.isInstance(next)) {
						iterator.remove();
					}
				}
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
		removeDMRListeners(rule);
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
		// we only have to care about leaf conditions since or/and conditions aren't even registered
		if (LeafCondition.class.isInstance(condition)) {
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
		}

		conditionToSettings.remove(condition);

		if (rule != null) {
			removeDMRListeners(rule);
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
		if (!rulesToDMRs.containsKey(rule)) {
			rulesToDMRs.put(rule, new LinkedHashSet<VDomainModelReference>());
		}
		rulesToDMRs.get(rule).addAll(references);

		for (final VDomainModelReference reference : references) {
			reference.getChangeListener().add(new DMRChangeListener(rule));
			context.registerDomainChangeListener(reference);
		}
	}

	/**
	 * DMR change listener that will reregister the affected settings for the condition.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class DMRChangeListener implements DomainModelReferenceChangeListener {

		private final T rule;

		public DMRChangeListener(T rule) {
			this.rule = rule;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener#notifyChange()
		 */
		@Override
		public void notifyChange() {
			// dmr has changed register every associated with it
			final Set<VDomainModelReference> dmrs = rulesToDMRs.remove(rule);
			if (dmrs == null) {
				return;
			}
			for (final VDomainModelReference dmr : dmrs) {
				context.unregisterDomainChangeListener(dmr);
				dmr.getChangeListener().remove(this);
			}
			final VElement element = removeRule(rule);
			if (element == null) {
				return;
			}
			register(element, rule, rule.getCondition(), context.getDomainModel());
		}

	}

}
