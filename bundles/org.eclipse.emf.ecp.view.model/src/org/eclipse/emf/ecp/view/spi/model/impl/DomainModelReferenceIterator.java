/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;

/**
 * Class for the DomainModelReferenceIterator which iterates over all found {@link Setting Settings}.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public class DomainModelReferenceIterator implements Iterator<EStructuralFeature.Setting> {
	/**
	 * Helper Class for tuple.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private class ReferenceCounter {
		private EReference eReference;
		private int position;
	}

	private final EStructuralFeature domainModelFeature;
	private final List<ReferenceCounter> references;
	private final EObject lastResolvedEObject;
	private boolean hasNext;

	/**
	 * Constructor for the {@link DomainModelReferenceIterator}.
	 *
	 * @param leftReferences the references which could not be used in the normal resolve process
	 * @param lastResolvedEObject the last {@link EObject} that was resolved
	 * @param domainModelFeature the {@link EStructuralFeature} which is referenced
	 */
	public DomainModelReferenceIterator(List<EReference> leftReferences, EObject lastResolvedEObject,
		EStructuralFeature domainModelFeature) {
		this.domainModelFeature = domainModelFeature;
		this.lastResolvedEObject = lastResolvedEObject;
		final List<ReferenceCounter> referenceCounters = new ArrayList<ReferenceCounter>(
			leftReferences.size());
		for (final EReference eReference : leftReferences) {
			final ReferenceCounter rc = new ReferenceCounter();
			rc.eReference = eReference;
			rc.position = 0;
			referenceCounters.add(rc);
		}
		references = referenceCounters;
		hasNext = resolveRefernces();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		// TODO do we need it?
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Setting next() {
		if (lastResolvedEObject == null) {
			return null;
		}
		EObject current = lastResolvedEObject;
		hasNext = references.size() != 0;
		for (int i = 0; i < references.size(); i++) {
			final ReferenceCounter referenceCounter = references.get(i);
			final EReference eReference = referenceCounter.eReference;
			EObject child;
			if (!current.eClass().getEAllStructuralFeatures().contains(eReference)) {
				return null;
			}
			if (!eReference.isMany()) {
				child = (EObject) current.eGet(eReference);
			}
			else {
				// EMF API
				@SuppressWarnings("unchecked")
				final List<EObject> children = (List<EObject>) current.eGet(eReference);
				if (children == null || children.size() == 0) {
					throw new IllegalStateException("The EReference " + eReference.getName() + " is wrong for " //$NON-NLS-1$ //$NON-NLS-2$
						+ current.eClass().getName() + "!"); //$NON-NLS-1$
				}
				child = children.get(referenceCounter.position);
				if (i + 1 == references.size()) {
					referenceCounter.position++;
					if (referenceCounter.position == children.size()) {
						increaseCounter(i - 1);
					}
				}
			}
			if (child == null) {
				throw new IllegalStateException("EObject in reference" + eReference.getName() + " of EObject " //$NON-NLS-1$ //$NON-NLS-2$
					+ current.eClass().getName() + " not set!"); //$NON-NLS-1$
			}
			current = child;

		}
		if (!current.eClass().getEAllStructuralFeatures().contains(domainModelFeature)) {
			return null;
		}
		return ((InternalEObject) current).eSetting(domainModelFeature);
	}

	private void increaseCounter(int currentPosition) {
		if (currentPosition < 0) {
			hasNext = false;
			return;
		}
		final ReferenceCounter previousCounter = references.get(currentPosition);
		if (!previousCounter.eReference.isMany()) {
			increaseCounter(currentPosition - 1);
		}
		else {
			previousCounter.position++;
			final int numElements = calculateSizeOfElementsOnPosition(currentPosition);
			if (previousCounter.position == numElements) {
				increaseCounter(currentPosition - 1);
			}
			else {
				for (int j = currentPosition + 1; j < references.size(); j++) {
					references.get(j).position = 0;
				}
			}
		}
	}

	private int calculateSizeOfElementsOnPosition(int position) {
		EObject current = lastResolvedEObject;
		for (int j = 0; j < position; j++) {
			final ReferenceCounter referenceCounter = references.get(j);
			final EReference eReference = referenceCounter.eReference;
			EObject child;

			if (!eReference.isMany()) {
				child = (EObject) current.eGet(eReference);
			}
			else {
				// EMF API
				@SuppressWarnings("unchecked")
				final List<EObject> children = (List<EObject>) current.eGet(eReference);
				final int id = referenceCounter.position;
				child = children.get(id);
			}
			current = child;
		}
		return ((List<?>) current.eGet(references.get(position).eReference)).size();
	}

	// FIXME: looks like duplicate code
	private boolean resolveRefernces() {
		EObject current = lastResolvedEObject;

		if (lastResolvedEObject == null) {
			return false;
		}

		for (int i = 0; i < references.size(); i++) {
			final ReferenceCounter referenceCounter = references.get(i);
			final EReference eReference = referenceCounter.eReference;
			EObject child;
			if (!current.eClass().getEAllStructuralFeatures().contains(eReference)) {
				return false;
			}
			if (!eReference.isMany()) {
				child = (EObject) current.eGet(eReference);
			}
			else {
				// EMF API
				@SuppressWarnings("unchecked")
				final List<EObject> children = (List<EObject>) current.eGet(eReference);
				if (children == null || children.size() == 0) {
					return false;
				}
				// FIXME
				child = children.get(0);
			}
			if (child == null) {
				return false;
			}
			current = child;
		}

		return current != null && current.eClass().getEAllStructuralFeatures().contains(domainModelFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return hasNext;
	}

}
