/**
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
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bugs 543160, 545686
 */
package org.eclipse.emf.ecp.view.spi.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.DiagnosticMessageExtractor;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VDiagnostic</b></em>'.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VDiagnosticImpl#getDiagnostics <em>Diagnostics</em>}</li>
 *        </ul>
 *
 * @generated
 */
// TODO performance
public class VDiagnosticImpl extends EObjectImpl implements VDiagnostic {

	/**
	 * The cached value of the '{@link #getDiagnostics() <em>Diagnostics</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDiagnostics()
	 * @generated
	 * @ordered
	 */
	protected EList<Object> diagnostics;
	private final Map<EObject, Set<Diagnostic>> diagnosticMap = new LinkedHashMap<>();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VDiagnosticImpl() {
		super();
	}

	private void removeOldDiagnostic(Diagnostic diagnostic) {
		final EObject eObject = getDiagnosticSubject(diagnostic);
		if (eObject == null) {
			return;
		}

		EObject parent = eObject;
		while (parent != null) {
			final Set<Diagnostic> diagnostics = diagnosticMap.get(parent);
			if (diagnostics != null) {
				diagnostics.remove(diagnostic);
			}
			parent = parent.eContainer();
		}
	}

	private void addNewDiagnostic(Diagnostic diagnostic) {
		final EObject eObject = getDiagnosticSubject(diagnostic);
		if (eObject == null) {
			return;
		}

		EObject parent = eObject;
		while (parent != null) {
			Set<Diagnostic> diagnostics = diagnosticMap.get(parent);
			if (diagnostics == null) {
				diagnostics = new LinkedHashSet<>();
				diagnosticMap.put(parent, diagnostics);
			}
			diagnostics.add(diagnostic);
			parent = parent.eContainer();
		}
	}

	/**
	 * Get the {@link EObject} that is the subject of a {@code diagnostic}.
	 *
	 * @param diagnostic a purported {@link Diagnostic}
	 * @return the object that is the first {@linkplain Diagnostic#getData() data element}
	 *         of the {@code diagnostic}, or {@code null} if none
	 */
	private EObject getDiagnosticSubject(Object diagnostic) {
		EObject result = null;

		if (diagnostic instanceof Diagnostic) {
			final Diagnostic d = (Diagnostic) diagnostic;
			if (!d.getData().isEmpty()) {
				final Object first = d.getData().get(0);
				if (first instanceof EObject) {
					result = (EObject) first;
				}
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VViewPackage.Literals.DIAGNOSTIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public EList<Object> getDiagnostics() {
		if (diagnostics == null) {
			diagnostics = new DiagnosticEList();
		}
		return diagnostics;
	}

	/**
	 * Implementation of the list of diagnostics with hooks for tracking diagnostics in
	 * the per-object sets and leveraging those sets for efficient containment testing
	 * and duplicate filtering.
	 */
	@SuppressWarnings("serial")
	private final class DiagnosticEList extends EDataTypeUniqueEList<Object> {
		private DiagnosticEList() {
			super(Object.class, VDiagnosticImpl.this, VViewPackage.DIAGNOSTIC__DIAGNOSTICS);
		}

		@Override
		protected Collection<Object> getNonDuplicates(Collection<? extends Object> collection) {
			// Our implementation of 'contains' is constant-time
			final Collection<Object> result = new HashSet<>(collection.size() * 2, 0.5f);
			for (final Object next : collection) {
				if (!contains(next)) {
					result.add(next);
				}
			}
			return result;
		}

		@Override
		public boolean contains(Object object) {
			// We can leverage the efficiency of sets maintained already for
			// unique tracking per object
			final EObject subject = getDiagnosticSubject(object);
			final Set<Diagnostic> diagnostics = diagnosticMap.get(subject);
			if (diagnostics != null) {
				return diagnostics.contains(object);
			} else if (subject != null) {
				// If the diagnostic has a subject, and I have the diagnostic, then
				// it would be in the map. Ergo, I do not have this diagnostic
				return false;
			}

			// Okay, it's not a diagnostic or it doesn't have a subject.
			// Fall back to the default linear search
			return super.contains(object);
		}

		@Override
		protected void didAdd(int index, Object newObject) {
			addNewDiagnostic((Diagnostic) newObject);
		}

		@Override
		protected void didRemove(int index, Object oldObject) {
			removeOldDiagnostic((Diagnostic) oldObject);
		}

		@Override
		protected void didSet(int index, Object newObject, Object oldObject) {
			if (newObject != oldObject) {
				didRemove(index, oldObject);
				didAdd(index, newObject);
			}
		}
	}

	// end of custom code

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VViewPackage.DIAGNOSTIC__DIAGNOSTICS:
			return getDiagnostics();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VViewPackage.DIAGNOSTIC__DIAGNOSTICS:
			getDiagnostics().clear();
			getDiagnostics().addAll((Collection<? extends Object>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case VViewPackage.DIAGNOSTIC__DIAGNOSTICS:
			getDiagnostics().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case VViewPackage.DIAGNOSTIC__DIAGNOSTICS:
			return diagnostics != null && !diagnostics.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (diagnostics: "); //$NON-NLS-1$
		result.append(diagnostics);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getHighestSeverity()
	 */
	@Override
	public int getHighestSeverity() {
		int highestSeverity = Diagnostic.OK;
		if (getDiagnostics().size() > 0) {
			for (final Object o : getDiagnostics()) {
				final Diagnostic diagnostic = (Diagnostic) o;
				highestSeverity = highestSeverity >= diagnostic.getSeverity() ? highestSeverity
					: diagnostic
						.getSeverity();
			}
		}
		return highestSeverity;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getMessage()
	 */
	@Override
	public String getMessage() {
		final List<Diagnostic> diagnostics = new ArrayList<>(getDiagnostics().size());
		for (final Object o : getDiagnostics()) {
			final Diagnostic diagnostic = (Diagnostic) o;
			diagnostics.add(diagnostic);
		}
		return DiagnosticMessageExtractor.getMessage(diagnostics);
	}

	private void sortDiagnostics(final List<Diagnostic> diagnostics) {
		Collections.sort(diagnostics, new Comparator<Diagnostic>() {

			@Override
			public int compare(Diagnostic o1, Diagnostic o2) {
				if (o1.getSeverity() != o2.getSeverity()) {
					// highest first
					return o2.getSeverity() - o1.getSeverity();
				}
				return o1.getMessage().compareTo(o2.getMessage());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getDiagnostics(org.eclipse.emf.ecore.EObject)
	 * @since 1.3
	 */
	@Override
	public List<Diagnostic> getDiagnostics(EObject eObject) {
		final List<Diagnostic> result = new ArrayList<>();
		final Set<Diagnostic> set = diagnosticMap.get(eObject);
		if (set != null) {
			for (final Object objectDiagnostic : set) { // getDiagnostics()
				final Diagnostic diagnostic = (Diagnostic) objectDiagnostic;
				if (diagnostic.getSeverity() == Diagnostic.OK) {
					continue;
				}
				result.addAll(getDiagnostics(diagnostic, eObject));
			}
		}
		sortDiagnostics(result);
		return result;
	}

	private List<Diagnostic> getDiagnostics(Diagnostic diagnostic, EObject eObject) {
		final List<Diagnostic> result = new ArrayList<>();
		if (diagnostic.getData() != null && diagnostic.getData().size() != 0
			&& EcoreUtil.isAncestor(eObject, (EObject) diagnostic.getData().get(0))) {
			result.add(diagnostic);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getDiagnostic(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 * @since 1.3
	 */
	@Override
	public List<Diagnostic> getDiagnostic(EObject eObject, EStructuralFeature eStructuralFeature) {
		final EList<Diagnostic> result = new BasicEList<>();
		final Set<Diagnostic> set = diagnosticMap.get(eObject);
		if (set != null) {
			for (final Object objectDiagnostic : set) { // getDiagnostics()
				final Diagnostic diagnostic = (Diagnostic) objectDiagnostic;
				if (diagnostic.getSeverity() == Diagnostic.OK) {
					continue;
				}
				result.addAll(getDiagnostics(diagnostic, eObject, eStructuralFeature));
			}
		}
		sortDiagnostics(result);
		return result;
	}

	private List<Diagnostic> getDiagnostics(Diagnostic diagnostic, EObject eObject,
		EStructuralFeature eStructuralFeature) {
		final List<Diagnostic> result = new ArrayList<>();
		if (diagnostic.getData() != null && diagnostic.getData().size() > 1
			&& EcoreUtil.isAncestor(eObject, (EObject) diagnostic.getData().get(0))
			&& eStructuralFeature.equals(diagnostic.getData().get(1))) {
			if (diagnostic.getChildren() == null || diagnostic.getChildren().size() == 0) {
				result.add(diagnostic);
			} else {
				for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
					if (childDiagnostic.getSeverity() == Diagnostic.OK) {
						continue;
					}
					result.add(childDiagnostic);
				}
			}
		}
		return result;
	}

} // VDiagnosticImpl
