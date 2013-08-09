package org.eclipse.emf.ecp.view.rule;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.rule.model.AndCondition;
import org.eclipse.emf.ecp.view.rule.model.Condition;
import org.eclipse.emf.ecp.view.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.rule.model.OrCondition;

public final class ConditionEvaluator {

	private ConditionEvaluator() {

	}

	public static boolean evaluate(EObject eObject, Condition condition) {

		if (AndCondition.class.isInstance(condition)) {
			return doEvaluate(eObject, (AndCondition) condition);
		}
		if (OrCondition.class.isInstance(condition)) {
			return doEvaluate(eObject, (OrCondition) condition);
		}
		if (LeafCondition.class.isInstance(condition)) {
			return doEvaluate(eObject, (LeafCondition) condition);
		}
		return false;
	}

	private static boolean doEvaluate(EObject eObject, AndCondition condition) {
		boolean result = true;
		for (final Condition innerCondition : condition.getConditions()) {
			result &= evaluate(eObject, innerCondition);
		}
		return result;
	}

	private static boolean doEvaluate(EObject eObject, OrCondition condition) {
		boolean result = false;
		for (final Condition innerCondition : condition.getConditions()) {
			result |= evaluate(eObject, innerCondition);
		}
		return result;
	}

	private static boolean doEvaluate(EObject eObject, LeafCondition condition) {
		final EClass attributeClass = condition.getAttribute().getEContainingClass();
		EObject parent = eObject;
		final List<EReference> referencePath = condition.getPathToAttribute();
		for (final EReference eReference : referencePath) {
			if (eReference.getEReferenceType().isInstance(parent)) {
				break;
			}
			final EObject child = (EObject) parent.eGet(eReference);
			if (child == null) {
				break;
			}
			parent = child;
		}
		if (!attributeClass.isInstance(parent)) {
			return false;
		}

		return condition.getExpectedValue().equals(parent.eGet(condition.getAttribute()));
	}

}
