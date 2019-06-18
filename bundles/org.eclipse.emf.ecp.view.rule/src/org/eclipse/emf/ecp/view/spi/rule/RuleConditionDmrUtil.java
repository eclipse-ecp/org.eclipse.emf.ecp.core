/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;

/**
 * Utility class for common functionality needed for rule condition DMR tooling.
 *
 * @author Lucas Koehler
 * @since 1.21
 *
 */
public final class RuleConditionDmrUtil {

	private RuleConditionDmrUtil() {
		// do not instantiate utility class
	}

	/**
	 * Get the root EClass for the {@link VDomainModelReference} to create. If the rule {@link Condition} is contained
	 * in another Condition with a DMR, the root EClass is determined by resolving the container Condition(s) down from
	 * the domain root.
	 * Otherwise, the root EClass is the EClass of the domain root.
	 *
	 * @param databinding The {@link EMFFormsDatabindingEMF} used to resolve DMRs
	 * @param reportService The {@link ReportService} used to report databinding failures
	 * @param owner The condition containing the the DMR whose root EClass is needed
	 * @return The root EClass or nothing if it could not be determined
	 */
	public static Optional<EClass> getDmrRootEClass(EMFFormsDatabindingEMF databinding, ReportService reportService,
		EObject owner) {
		// We do not need to check the owner because the owner's dmr is the one we want to create
		EObject testObject = owner.eContainer();
		final List<IterateCondition> iterateConditions = new LinkedList<>();

		// Walk up the containment hierarchy up to the VView. Remember IterateConditions on the way
		while (!VView.class.isInstance(testObject) && testObject != null) {
			if (testObject instanceof IterateCondition) {
				iterateConditions.add((IterateCondition) testObject);
			}
			testObject = testObject.eContainer();
		}

		if (testObject instanceof VView) {
			// Reverse because we need to resolve top to bottom but added the elements bottom to top
			Collections.reverse(iterateConditions);
			EClass currentRoot = VView.class.cast(testObject).getRootEClass();
			// Resolve the nested iterate conditions from top (= view root EClass) to bottom
			for (final IterateCondition condition : iterateConditions) {
				final VDomainModelReference iterateDmr = condition.getItemReference();
				try {
					final IValueProperty<?, ?> valueProperty = databinding.getValueProperty(iterateDmr, currentRoot);
					if (valueProperty.getValueType() instanceof EReference) {
						currentRoot = EReference.class.cast(valueProperty.getValueType()).getEReferenceType();
					} else {
						return Optional.empty();
					}
				} catch (final DatabindingFailedException ex) {
					reportService.report(
						new AbstractReport(ex, "Could not determine root EClass for the DMR of Condition {0}.", owner)); //$NON-NLS-1$
					return Optional.empty();
				}
			}
			return Optional.of(currentRoot);
		}
		return Optional.empty();
	}

	/**
	 * Get the root EObjects for the owner's domain model reference. IF the owner is not nested in another Condition,
	 * the result list simply contains the given <code>domainRoot</code>. IF the owner is nested in one or more
	 * {@link IterateCondition IterateConditions}, the iterate conditions' dmrs are resolved and all possible root
	 * objects collected and returned.
	 *
	 * @param databinding The {@link EMFFormsDatabindingEMF} used to resolve dmrs
	 * @param reportService The {@link ReportService} to report databinding errors
	 * @param domainRoot The domain root (usually the view model context's domain model)
	 * @param owner The eObject containing the dmr (usually a {@link Condition})
	 * @return the roots of the owner's dmr. If the owner condition is not nested, the result list contains only the
	 *         given domain root. May return an empty list if no root could be determined but never returns
	 *         <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public static List<EObject> getDmrRootObject(EMFFormsDatabindingEMF databinding,
		ReportService reportService, EObject domainRoot, EObject owner) {

		// We do not need to check the owner because the owner's dmr is the one we want to create
		EObject testObject = owner.eContainer();
		final List<IterateCondition> iterateConditions = new LinkedList<>();

		// Walk up the containment hierarchy up to the VView. Remember IterateConditions on the way
		while (!VView.class.isInstance(testObject) && testObject != null) {
			if (testObject instanceof IterateCondition) {
				iterateConditions.add((IterateCondition) testObject);
			}
			testObject = testObject.eContainer();
		}

		Collections.reverse(iterateConditions);
		List<EObject> currentRoots = Collections.singletonList(domainRoot);

		// Resolve the nested iterate conditions from top to bottom
		// Thereby, an iterate condition usually points to a multi reference. The next dmr is resolved against each of
		// the reference's objects.
		for (final IterateCondition condition : iterateConditions) {
			final List<EObject> newRoots = new LinkedList<EObject>();
			for (final EObject currentRoot : currentRoots) {
				final VDomainModelReference iterateDmr = condition.getItemReference();
				try {
					final Setting setting = databinding.getSetting(iterateDmr, currentRoot);
					if (setting.getEStructuralFeature() instanceof EReference) {
						if (setting.getEStructuralFeature().isMany()) {
							newRoots.addAll((Collection<EObject>) setting.get(true));
						} else {
							newRoots.add((EObject) setting.get(true));
						}
					} else {
						reportService.report(new AbstractReport(
							"Could not determine root EObjects for the DMR of Condition {0} because the DMR {1} of a parent condition does not end in an EReference", //$NON-NLS-1$
							owner, iterateDmr));
						return Collections.emptyList();
					}
				} catch (final DatabindingFailedException ex) {
					reportService.report(new AbstractReport(ex,
						"Could not determine root EObjects for the DMR of Condition {0}.", owner)); //$NON-NLS-1$
					return Collections.emptyList();
				}
			}
			currentRoots = newRoots;
		}

		return currentRoots;

	}
}
