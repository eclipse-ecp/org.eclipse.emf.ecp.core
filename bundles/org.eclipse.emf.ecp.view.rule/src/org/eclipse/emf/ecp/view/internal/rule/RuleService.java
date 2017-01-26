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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.EnableRule;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emf.ecp.view.spi.rule.model.ShowRule;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * Rule service that, once instantiated, maintains and synchronizes
 * the state of a rule with its {@link VElement}.
 *
 * @author emueller
 * @author jfaltermeier
 */
public class RuleService implements ViewModelService, EMFFormsContextListener {

	private static final String DOMAIN_MODEL_NULL_EXCEPTION = "Domain model must not be null."; //$NON-NLS-1$
	private static final String VIEW_MODEL_NULL_EXCEPTION = "View model must not be null."; //$NON-NLS-1$
	private ViewModelContext context;
	private ModelChangeAddRemoveListener domainChangeListener;
	private ModelChangeAddRemoveListener viewChangeListener;

	private RuleRegistry<EnableRule> enableRuleRegistry;
	private RuleRegistry<ShowRule> showRuleRegistry;
	private ConditionServiceManager conditionServiceManager;

	/**
	 * Instantiates the rule service.
	 */
	public RuleService() {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(final ViewModelContext context) {
		this.context = context;
		enableRuleRegistry = new RuleRegistry<EnableRule>(context);
		showRuleRegistry = new RuleRegistry<ShowRule>(context);
		context.registerEMFFormsContextListener(this);
		conditionServiceManager = context.getService(ConditionServiceManager.class);
	}

	private void resetToVisible(VElement renderable) {
		if (renderable == null) {
			return;
		}
		final Map<VElement, Boolean> maps = new LinkedHashMap<VElement, Boolean>();
		updateStateMap(maps, renderable, false, true, ShowRule.class);
		for (final VElement vElement : maps.keySet()) {
			vElement.setVisible(maps.get(vElement));
		}
	}

	private void resetToEnabled(VElement renderable) {
		if (renderable == null) {
			return;
		}
		final Map<VElement, Boolean> maps = new LinkedHashMap<VElement, Boolean>();
		updateStateMap(maps, renderable, false, true, EnableRule.class);
		for (final VElement vElement : maps.keySet()) {
			vElement.setEnabled(maps.get(vElement));
		}
	}

	private static Rule getRule(VElement renderable) {
		for (final VAttachment attachment : renderable.getAttachments()) {
			if (Rule.class.isInstance(attachment)) {
				final Rule rule = (Rule) attachment;
				return rule;
			}
		}

		return null;
	}

	private <T extends Rule> void updateStateMap(Map<VElement, Boolean> stateMap, VElement renderable,
		boolean isOpposite, boolean evalResult, Class<T> ruleType) {

		if (!stateMap.containsKey(renderable)) {
			boolean didUpdate = false;
			final Rule rule = getRule(renderable);
			if (rule != null && ruleApplies(rule, ruleType)) {
				final Condition condition = rule.getCondition();
				if (condition != null && canOverrideParent(evalResult, isOpposite)) {
					final boolean evaluate = conditionServiceManager.evaluate(condition, context.getDomainModel());
					stateMap.put(renderable, isOpposite(rule) ? !evaluate : evaluate);
					didUpdate = true;
				}
			}
			// use result of parent
			if (!didUpdate) {
				stateMap.put(renderable, isOpposite ? !evalResult : evalResult);
			}
		} else {
			final Boolean currentState = stateMap.get(renderable).booleanValue();
			if (currentState) {
				stateMap.put(renderable, isOpposite ? !evalResult : evalResult);
			}
		}

		for (final EObject childContent : renderable.eContents()) {
			if (childContent instanceof VElement) {
				updateStateMap(stateMap, (VElement) childContent, isOpposite, evalResult, ruleType);
			}
		}
	}

	private static boolean canOverrideParent(boolean evalResult, boolean isOpposite) {
		return evalResult && !isOpposite || !evalResult && isOpposite;
	}

	private static <T extends Rule> boolean ruleApplies(Rule rule, Class<T> ruleType) {
		return Arrays.asList(rule.getClass().getInterfaces()).contains(ruleType);
	}

	private static boolean isOpposite(Rule rule) {
		return isHideRule(rule) || isDisableRule(rule);
	}

	private static <T extends Rule> boolean hasRule(Class<T> ruleType, EObject eObject) {

		if (!VElement.class.isInstance(eObject)) {
			return false;
		}

		final VElement renderable = (VElement) eObject;
		final Rule rule = getRule(renderable);

		if (ruleType.isInstance(rule)) {
			return true;
		}

		return false;
	}

	private <T extends Rule> Map<VElement, Boolean> evalAffectedRenderables(RuleRegistry<T> registry,
		Class<T> ruleType, UniqueSetting setting, boolean isDryRun, Map<Setting, Object> possibleValues) {

		final Map<VElement, Boolean> map = new LinkedHashMap<VElement, Boolean>();

		for (final Map.Entry<T, VElement> ruleAndRenderable : registry.getAffectedRenderables(
			setting).entrySet()) {

			final Rule rule = ruleAndRenderable.getKey();

			final VElement renderable = ruleAndRenderable.getValue();
			// whether the value changed at all, if newValue has been provided
			boolean hasChanged = true;

			if (!ruleType.isInstance(rule)) {
				continue;
			}

			if (isDryRun) {

				hasChanged = checkDryRun(possibleValues);

			}

			boolean result = false;
			boolean updateMap = true;
			if (rule.getCondition() == null) {
				result = true;
			} else if (isDryRun && hasChanged) {
				result = conditionServiceManager.evaluateChangedValues(rule.getCondition(), context.getDomainModel(),
					possibleValues);
			} else if (!isDryRun) {
				result = conditionServiceManager.evaluate(rule.getCondition(), context.getDomainModel());
			} else {
				updateMap = false;
			}
			final boolean isOposite = isDisableRule(rule) || isHideRule(rule);
			updateMap &= propagateChanges(result, isOposite, rule, renderable);
			if (updateMap) {
				updateStateMap(map, renderable, isOposite, result, ruleType);
			}
		}

		return map;
	}

	private static boolean propagateChanges(boolean result, boolean isOposite, Rule rule, VElement renderable) {
		if (result && !isOposite || isOposite && !result) {
			if (ShowRule.class.isInstance(rule)) {
				if (isOposite && result != renderable.isVisible()) {
					return false;
				} else if (!isOposite && result == renderable.isVisible()) {
					return false;
				}
			} else if (EnableRule.class.isInstance(rule)) {
				if (isOposite && result != renderable.isEnabled()) {
					return false;
				} else if (!isOposite && result == renderable.isEnabled()) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean checkDryRun(Map<Setting, Object> possibleValues) {
		boolean hasChanged = true;
		for (final Setting setting : possibleValues.keySet()) {
			final EObject parent = setting.getEObject();
			final EStructuralFeature feature = setting.getEStructuralFeature();
			final EClass attributeClass = feature.getEContainingClass();
			if (!attributeClass.isInstance(parent)) {
				continue;
			}
			final Object actualValue = parent.eGet(feature);
			final Object newValue = possibleValues.get(setting);
			if (!feature.isMany()) {
				if (newValue == null) {
					hasChanged &= actualValue == null;
				} else {
					hasChanged &= !newValue.equals(actualValue);
				}
			} else {
				// EMF API
				@SuppressWarnings("unchecked")
				final List<Object> objects = (List<Object>) actualValue;
				@SuppressWarnings("unchecked")
				final List<Object> newValues = (List<Object>) newValue;
				if (objects.size() == newValues.size()) {
					boolean sameEntries = true;
					for (final Object newValueListEntry : newValues) {
						if (!objects.contains(newValueListEntry)) {
							sameEntries = false;
						}
					}
					hasChanged &= !sameEntries;
				} else {
					hasChanged = true;
				}
			}
		}
		return hasChanged;
	}

	private <T extends Rule> Map<VElement, Boolean> evalAffectedRenderables(RuleRegistry<T> registry,
		Class<T> ruleType, UniqueSetting setting, Map<Setting, Object> possibleValues) {
		return evalAffectedRenderables(registry, ruleType, setting, true, possibleValues);
	}

	private <T extends Rule> Map<VElement, Boolean> evalAffectedRenderables(RuleRegistry<T> registry,
		Class<T> ruleType, UniqueSetting setting) {
		final Map<Setting, Object> changedValues = Collections.emptyMap();
		return evalAffectedRenderables(registry, ruleType, setting, false, changedValues);
	}

	private static boolean isDisableRule(Rule rule) {
		if (isEnableRule(rule)) {
			final EnableRule enableRule = (EnableRule) rule;
			return enableRule.isDisable();
		}

		return false;
	}

	private static boolean isHideRule(Rule rule) {
		if (isShowRule(rule)) {
			final ShowRule showRule = (ShowRule) rule;
			return showRule.isHide();
		}

		return false;
	}

	private static boolean isShowRule(Rule rule) {
		return rule instanceof ShowRule;
	}

	private <T extends Rule> void evalShow(UniqueSetting setting) {

		final Map<VElement, Boolean> visibleMap = evalAffectedRenderables(showRuleRegistry, ShowRule.class,
			setting);
		final Set<VElement> toBeVisible = new LinkedHashSet<VElement>();
		final Set<VElement> toBeInvisible = new LinkedHashSet<VElement>();
		for (final Map.Entry<VElement, Boolean> e : visibleMap.entrySet()) {
			final Boolean isVisible = e.getValue();
			final VElement renderable = e.getKey();
			if (isVisible) {
				toBeVisible.add(renderable);
			} else {
				toBeInvisible.add(renderable);
			}
		}

		for (final VElement vElement : toBeInvisible) {
			vElement.setVisible(false);
		}
		for (final VElement vElement : toBeVisible) {
			vElement.setVisible(true);
		}
	}

	private <T extends Rule> void evalEnable(UniqueSetting setting) {

		final Map<VElement, Boolean> enabledMap = evalAffectedRenderables(enableRuleRegistry, EnableRule.class,
			setting);

		for (final Map.Entry<VElement, Boolean> e : enabledMap.entrySet()) {
			e.getKey().setEnabled(e.getValue());
		}
	}

	private <T extends Rule> Set<UniqueSetting> init(RuleRegistry<T> registry, Class<T> ruleType,
		EObject viewModel,
		EObject domainObject) {
		final TreeIterator<EObject> iterator = viewModel.eAllContents();
		final Set<UniqueSetting> relevantSettings = new LinkedHashSet<UniqueSetting>();
		relevantSettings.addAll(register(registry, ruleType, domainObject, viewModel));

		while (iterator.hasNext()) {
			final EObject content = iterator.next();
			relevantSettings.addAll(register(registry, ruleType, domainObject, content));
		}
		return relevantSettings;
	}

	private <T extends Rule> Set<UniqueSetting> register(RuleRegistry<T> registry, Class<T> ruleType,
		EObject domainObject,
		final EObject viewModel) {
		if (hasRule(ruleType, viewModel)) {
			final VElement renderable = (VElement) viewModel;
			@SuppressWarnings("unchecked")
			final T rule = (T) getRule(renderable);
			return registry.register(renderable, rule, rule.getCondition(), domainObject);
		}
		return Collections.emptySet();
	}

	/**
	 * Returns all {@link VElement}s, that would we disabled if {@code possibleValues} would be set for the given
	 * {@code setting}s.
	 *
	 * @param possibleValues
	 *            a mapping of settings to their would-be new value
	 * @param setting the changed setting
	 * @return the hidden {@link VElement}s and their new state if {@code possibleValues} would be set
	 */
	public Map<VElement, Boolean> getDisabledRenderables(Map<Setting, Object> possibleValues,
		UniqueSetting setting) {

		return evalAffectedRenderables(enableRuleRegistry,
			EnableRule.class, setting, possibleValues);
	}

	/**
	 * Returns all {@link VElement}s, that would we hidden if {@code possibleValues} would be set for the given
	 * {@code setting}s.
	 *
	 * @param possibleValues
	 *            a mapping of settings to their would-be new value
	 * @param setting the setting that was changed
	 * @return the hidden {@link VElement}s and their new state if {@code possibleValues} would be set
	 */
	public Map<VElement, Boolean> getHiddenRenderables(Map<Setting, Object> possibleValues, UniqueSetting setting) {

		return evalAffectedRenderables(showRuleRegistry,
			ShowRule.class, setting, possibleValues);
	}

	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		// dispose stuff
		context.unregisterEMFFormsContextListener(this);
		context.unregisterDomainChangeListener(domainChangeListener);
		context.unregisterViewChangeListener(viewChangeListener);
		enableRuleRegistry.dispose();
		showRuleRegistry.dispose();
	}

	private static boolean isEnableRule(Rule rule) {
		return EnableRule.class.isInstance(rule);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 1;
	}

	/**
	 * Lister of the rule service reacting to view model changes.
	 */
	private final class RuleServiceViewChangeListener implements ModelChangeAddRemoveListener {
		private final ViewModelContext context;

		/**
		 * @param context
		 */
		private RuleServiceViewChangeListener(ViewModelContext context) {
			this.context = context;
		}

		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (VFeaturePathDomainModelReference.class.isInstance(notification.getNotifier())) {
				final VDomainModelReference domainModelReference = VDomainModelReference.class.cast(notification
					.getNotifier());
				final EObject eContainer = domainModelReference.eContainer();
				if (!LeafCondition.class.isInstance(eContainer)) {
					return;
				}
				final Condition condition = Condition.class.cast(eContainer);
				enableRuleRegistry.removeCondition(condition);
				showRuleRegistry.removeCondition(condition);
				EObject parent = condition.eContainer();
				while (parent != null && !Rule.class.isInstance(parent)) {
					parent = parent.eContainer();
				}
				if (parent == null) {
					return;
				}
				if (!Rule.class.isInstance(parent)) {
					return;
				}
				final Rule rule = Rule.class.cast(parent);
				final VElement renderable = VElement.class.isInstance(rule.eContainer())
					? VElement.class.cast(rule.eContainer()) : null;

				if (renderable == null) {
					return;
				}
				reevaluateRule(condition, rule, renderable);
			}
		}

		private void reevaluateRule(final Condition condition, final Rule rule, final VElement renderable) {
			if (isEnableRule(rule)) {
				if (enableRuleRegistry.register(renderable, (EnableRule) rule, condition,
					context.getDomainModel()).isEmpty()) {
					renderable.setEnabled(true);
				} else {
					resetToEnabled(renderable);
				}
			}
			if (isShowRule(rule)) {
				if (showRuleRegistry.register(renderable, (ShowRule) rule, condition, context.getDomainModel())
					.isEmpty()) {
					renderable.setVisible(true);
				} else {
					resetToVisible(renderable);
				}
			}
		}

		@Override
		public void notifyAdd(Notifier notifier) {
			if (VElement.class.isInstance(notifier)) {
				register(enableRuleRegistry, EnableRule.class, context.getDomainModel(),
					VElement.class.cast(notifier));
				register(showRuleRegistry, ShowRule.class, context.getDomainModel(), VElement.class.cast(notifier));

				final Rule rule = getRule(VElement.class.cast(notifier));
				if (rule == null) {
					return;
				}

				final Set<UniqueSetting> settings = conditionServiceManager.getConditionSettings(rule.getCondition(),
					context.getDomainModel());
				for (final UniqueSetting setting : settings) {
					evalEnable(setting);
					evalShow(setting);
				}

			} else if (EnableRule.class.isInstance(notifier)) {
				final Set<UniqueSetting> register = register(enableRuleRegistry, EnableRule.class,
					context.getDomainModel(),
					EnableRule.class.cast(notifier).eContainer());

				for (final UniqueSetting setting : register) {
					evalEnable(setting);
				}

			} else if (ShowRule.class.isInstance(notifier)) {
				final Set<UniqueSetting> register = register(showRuleRegistry, ShowRule.class,
					context.getDomainModel(), ShowRule.class.cast(notifier)
						.eContainer());

				for (final UniqueSetting setting : register) {
					evalShow(setting);
				}

			}
		}

		@Override
		public void notifyRemove(Notifier notifier) {
			if (VElement.class.isInstance(notifier)) {
				final VElement renderable = VElement.class.cast(notifier);
				showRuleRegistry.removeRenderable(renderable);
				enableRuleRegistry.removeRenderable(renderable);
			} else if (Condition.class.isInstance(notifier)) {
				final Condition condition = Condition.class.cast(notifier);
				resetToVisible(showRuleRegistry.removeCondition(condition));

				resetToEnabled(enableRuleRegistry
					.removeCondition(condition));

			} else if (ShowRule.class.isInstance(notifier)) {
				final ShowRule showRule = ShowRule.class.cast(notifier);
				resetToVisible(showRuleRegistry.removeRule(showRule));

			} else if (EnableRule.class.isInstance(notifier)) {
				final EnableRule enableRule = EnableRule.class.cast(notifier);
				final VElement removeRule = enableRuleRegistry.removeRule(enableRule);
				resetToEnabled(removeRule);

			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextAdded(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextAdded(VElement parentElement, EMFFormsViewContext childContext) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextDisposed(org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextDisposed(EMFFormsViewContext childContext) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextInitialised()
	 */
	@Override
	public void contextInitialised() {
		final VElement view = context.getViewModel();
		domainChangeListener = new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getStructuralFeature() == null) {
					return;
				}
				// add && reference && !containment
				final Setting setting = ((InternalEObject) notification.getNotifier()).eSetting(notification
					.getStructuralFeature());
				evalShow(UniqueSetting.createSetting(setting));
				evalEnable(UniqueSetting.createSetting(setting));
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// no op
			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// no op
			}
		};
		context.registerDomainChangeListener(domainChangeListener);
		viewChangeListener = new RuleServiceViewChangeListener(context);
		context.registerViewChangeListener(viewChangeListener);

		if (view == null) {
			throw new IllegalStateException(VIEW_MODEL_NULL_EXCEPTION);
		}

		final EObject domainModel = context.getDomainModel();

		if (domainModel == null) {
			throw new IllegalStateException(DOMAIN_MODEL_NULL_EXCEPTION);
		}

		final Set<UniqueSetting> enableSettings = init(enableRuleRegistry, EnableRule.class, view, domainModel);
		final Set<UniqueSetting> showSettings = init(showRuleRegistry, ShowRule.class, view, domainModel);
		for (final UniqueSetting setting : enableSettings) {
			evalEnable(setting);
		}
		for (final UniqueSetting setting : showSettings) {
			evalShow(setting);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextDispose()
	 */
	@Override
	public void contextDispose() {
		// do nothing
	}
}
