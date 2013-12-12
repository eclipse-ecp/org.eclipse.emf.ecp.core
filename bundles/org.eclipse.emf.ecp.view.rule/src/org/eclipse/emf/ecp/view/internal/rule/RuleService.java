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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.EnableRule;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emf.ecp.view.spi.rule.model.ShowRule;

/**
 * Rule service that, once instantiated, maintains and synchronizes
 * the state of a rule with its {@link VElement}.
 * 
 * @author emueller
 */
public class RuleService implements ViewModelService {

	private static final String DOMAIN_MODEL_NULL_EXCEPTION = "Domain model must not be null."; //$NON-NLS-1$
	private static final String VIEW_MODEL_NULL_EXCEPTION = "View model must not be null."; //$NON-NLS-1$
	private ViewModelContext context;
	private ModelChangeListener domainChangeListener;
	private ModelChangeListener viewChangeListener;

	private final RuleRegistry<EnableRule> enableRuleRegistry;
	private final RuleRegistry<ShowRule> showRuleRegistry;

	/**
	 * Instantiates the rule service.
	 */
	public RuleService() {
		enableRuleRegistry = new RuleRegistry<EnableRule>();
		showRuleRegistry = new RuleRegistry<ShowRule>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	public void instantiate(ViewModelContext context) {
		this.context = context;
		final VElement view = context.getViewModel();
		domainChangeListener = new ModelChangeListener() {

			public void notifyChange(ModelChangeNotification notification) {
				if (isAttributeNotification(notification)) {
					final EAttribute attribute = (EAttribute) notification.getStructuralFeature();
					evalShow(attribute);
					evalEnable(attribute);
				} else if (isMultiRefNotification(notification)) {
					final EReference reference = (EReference) notification.getStructuralFeature();
					final EClass eReferenceType = reference.getEReferenceType();
					for (final EAttribute attribute : eReferenceType.getEAllAttributes()) {
						evalShow(attribute);
						evalEnable(attribute);
					}
				}
			}

			public void notifyAdd(Notifier notifier) {
			}

			public void notifyRemove(Notifier notifier) {
			}
		};
		context.registerDomainChangeListener(domainChangeListener);
		viewChangeListener = new ModelChangeListener() {
			public void notifyChange(ModelChangeNotification notification) {
				// do nothing for now, dynamic registration of rules isn't possible yet
			}

			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub
			}

			public void notifyRemove(Notifier notifier) {

				if (VElement.class.isInstance(notifier)) {
					final VElement renderable = VElement.class.cast(notifier);
					showRuleRegistry.removeRenderable(renderable);
					enableRuleRegistry.removeRenderable(renderable);
				} else if (Condition.class.isInstance(notifier)) {
					final Condition condition = Condition.class.cast(notifier);
					resetToVisible(showRuleRegistry.removeCondition(condition));
					resetToEnabled(enableRuleRegistry.removeCondition(condition));
				} else if (ShowRule.class.isInstance(notifier)) {
					final ShowRule showRule = ShowRule.class.cast(notifier);
					resetToVisible(showRuleRegistry.removeRule(showRule));
				} else if (EnableRule.class.isInstance(notifier)) {
					final EnableRule enableRule = EnableRule.class.cast(notifier);
					resetToEnabled(enableRuleRegistry.removeRule(enableRule));
				}
			}
		};
		context.registerViewChangeListener(viewChangeListener);

		if (view == null) {
			throw new IllegalStateException(VIEW_MODEL_NULL_EXCEPTION);
		}

		final EObject domainModel = context.getDomainModel();

		if (domainModel == null) {
			throw new IllegalStateException(DOMAIN_MODEL_NULL_EXCEPTION);
		}

		init(enableRuleRegistry, EnableRule.class, view, domainModel);
		init(showRuleRegistry, ShowRule.class, view, domainModel);

		evalEnable();
		evalShow();
	}

	private static void resetToVisible(VElement renderable) {
		if (renderable != null) {
			renderable.setVisible(true);
		}
	}

	private static void resetToEnabled(VElement renderable) {
		if (renderable != null) {
			renderable.setEnabled(true);
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

	private static <T extends Rule> void updateStateMap(Map<VElement, Boolean> stateMap, VElement renderable,
		boolean isOpposite, boolean evalResult, Class<T> ruleType) {

		if (!stateMap.containsKey(renderable)) {
			boolean didUpdate = false;
			final Rule rule = getRule(renderable);
			if (rule != null && ruleApplies(rule, ruleType)) {
				final Condition condition = rule.getCondition();
				if (condition != null && canOverrideParent(evalResult, isOpposite)) {
					final boolean evaluate = condition.evaluate();
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

	private static <T extends Rule> Map<VElement, Boolean> evalAffectedRenderables(RuleRegistry<T> registry,
		Class<T> ruleType, EStructuralFeature attribute, boolean isDryRun, Map<Setting, Object> possibleValues) {

		final Map<VElement, Boolean> map = new LinkedHashMap<VElement, Boolean>();

		for (final Map.Entry<T, VElement> ruleAndRenderable : registry.getAffectedRenderables(
			attribute).entrySet()) {

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
				result = false;
			} else if (isDryRun && hasChanged) {
				result = rule.getCondition().evaluateChangedValues(possibleValues);
			} else if (!isDryRun) {
				result = rule.getCondition().evaluate();
			}
			else {
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
			}
			else {
				// EMF API
				@SuppressWarnings("unchecked")
				final List<Object> objects = (List<Object>) actualValue;
				hasChanged &= !objects.contains(newValue);
			}
		}
		return hasChanged;
	}

	private static <T extends Rule> Map<VElement, Boolean> evalAffectedRenderables(RuleRegistry<T> registry,
		Class<T> ruleType, EStructuralFeature attribute, Map<Setting, Object> possibleValues) {
		return evalAffectedRenderables(registry, ruleType, attribute, true, possibleValues);
	}

	private static <T extends Rule> Map<VElement, Boolean> evalAffectedRenderables(RuleRegistry<T> registry,
		Class<T> ruleType, EStructuralFeature attribute) {
		final Map<Setting, Object> changedValues = Collections.emptyMap();
		return evalAffectedRenderables(registry, ruleType, attribute, false, changedValues);
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

	private <T extends Rule> void evalShow(EStructuralFeature attribute) {

		final Map<VElement, Boolean> visibleMap = evalAffectedRenderables(showRuleRegistry, ShowRule.class,
			attribute);
		for (final Map.Entry<VElement, Boolean> e : visibleMap.entrySet()) {
			final Boolean isVisible = e.getValue();
			final VElement renderable = e.getKey();
			renderable.setVisible(isVisible);
		}
	}

	private <T extends Rule> void evalEnable(EStructuralFeature attribute) {

		final Map<VElement, Boolean> enabledMap = evalAffectedRenderables(enableRuleRegistry, EnableRule.class,
			attribute);

		for (final Map.Entry<VElement, Boolean> e : enabledMap.entrySet()) {
			e.getKey().setEnabled(e.getValue());
		}
	}

	private <T extends Rule> void evalShow() {
		for (final EStructuralFeature feature : showRuleRegistry.getAttributes()) {
			evalShow(feature);
		}
	}

	private <T extends Rule> void evalEnable() {
		for (final EStructuralFeature feature : enableRuleRegistry.getAttributes()) {
			evalEnable(feature);
		}
	}

	private <T extends Rule> void init(RuleRegistry<T> registry, Class<T> ruleType, EObject viewModel,
		EObject domainObject) {
		final TreeIterator<EObject> iterator = viewModel.eAllContents();

		register(registry, ruleType, domainObject, viewModel);

		while (iterator.hasNext()) {
			final EObject content = iterator.next();
			register(registry, ruleType, domainObject, content);
		}
	}

	private <T extends Rule> void register(RuleRegistry<T> registry, Class<T> ruleType, EObject domainObject,
		final EObject viewModel) {
		if (hasRule(ruleType, viewModel)) {
			final VElement renderable = (VElement) viewModel;
			@SuppressWarnings("unchecked")
			final T rule = (T) getRule(renderable);
			registry.register(renderable, rule, rule.getCondition(), domainObject);
		}
	}

	/**
	 * Returns all {@link VElement}s, that would we disabled if {@code possibleValues} would be set for the given
	 * {@code setting}s.
	 * 
	 * @param possibleValues
	 *            a mapping of settings to their would-be new value
	 * @param changedAttribute the changed attribute
	 * @return the hidden {@link VElement}s and their new state if {@code possibleValues} would be set
	 */
	public Map<VElement, Boolean> getDisabledRenderables(Map<Setting, Object> possibleValues,
		EAttribute changedAttribute) {

		return evalAffectedRenderables(enableRuleRegistry,
			EnableRule.class, changedAttribute, possibleValues);
	}

	/**
	 * Returns all {@link VElement}s, that would we hidden if {@code possibleValues} would be set for the given
	 * {@code setting}s.
	 * 
	 * @param possibleValues
	 *            a mapping of settings to their would-be new value
	 * @param changedAttribute the attribute that was changed
	 * @return the hidden {@link VElement}s and their new state if {@code possibleValues} would be set
	 */
	public Map<VElement, Boolean> getHiddenRenderables(Map<Setting, Object> possibleValues, EAttribute changedAttribute) {

		return evalAffectedRenderables(showRuleRegistry,
			ShowRule.class, changedAttribute, possibleValues);
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		// dispose stuff
		context.unregisterDomainChangeListener(domainChangeListener);
		context.unregisterViewChangeListener(viewChangeListener);
	}

	private static boolean isEnableRule(Rule rule) {
		return EnableRule.class.isInstance(rule);
	}

	private static boolean isAttributeNotification(ModelChangeNotification notification) {
		if (notification.getStructuralFeature() instanceof EAttribute) {
			return true;
		}

		return false;
	}

	private boolean isMultiRefNotification(ModelChangeNotification notification) {
		if (EReference.class.isInstance(notification.getStructuralFeature())) {
			final EReference reference = (EReference) notification.getStructuralFeature();
			return reference.getUpperBound() < 0 || reference.getUpperBound() > 1;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	public int getPriority() {
		return 1;
	}
}
