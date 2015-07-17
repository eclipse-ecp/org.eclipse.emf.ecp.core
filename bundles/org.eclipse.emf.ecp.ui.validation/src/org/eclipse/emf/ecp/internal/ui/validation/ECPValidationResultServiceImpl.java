/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.validation;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.validation.ECPValidationResultService;

/**
 * Default implementation of the {@link ECPValidationResultService}.
 *
 * @author jfaltermeier
 *
 */
public class ECPValidationResultServiceImpl implements ECPValidationResultService {

	private final Set<ECPValidationResultServiceListener> listener;
	private final Map<EClassifier, Set<ECPValidationResultServiceListener>> classifierToListenerMap;
	private final Map<ECPValidationResultServiceListener, Set<EClassifier>> listenerToClassifierMap;

	/**
	 * Constructor.
	 */
	public ECPValidationResultServiceImpl() {
		listener = new LinkedHashSet<ECPValidationResultService.ECPValidationResultServiceListener>();
		classifierToListenerMap = new LinkedHashMap<EClassifier, Set<ECPValidationResultServiceListener>>();
		listenerToClassifierMap = new LinkedHashMap<ECPValidationResultService.ECPValidationResultServiceListener, Set<EClassifier>>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.validation.ECPValidationResultService#setResult(org.eclipse.emf.common.util.Diagnostic[])
	 */
	@Override
	public void setResult(Diagnostic[] diagnostic) {
		final Set<EClassifier> classifiers = new LinkedHashSet<EClassifier>();
		for (final Diagnostic d : diagnostic) {
			extractClassifier(classifiers, d);
		}
		notifyListener(diagnostic, classifiers);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.validation.ECPValidationResultService#setResult(org.eclipse.emf.common.util.Diagnostic)
	 */
	@Override
	public void setResult(Diagnostic diagnostic) {
		final Set<EClassifier> classifiers = new LinkedHashSet<EClassifier>();
		extractClassifier(classifiers, diagnostic);
		notifyListener(diagnostic, classifiers);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.validation.ECPValidationResultService#setResult(java.util.List)
	 */
	@Override
	public void setResult(List<Diagnostic> diagnostic) {
		final Set<EClassifier> classifiers = new LinkedHashSet<EClassifier>();
		for (final Diagnostic d : diagnostic) {
			extractClassifier(classifiers, d);
		}
		notifyListener(diagnostic, classifiers);
	}

	/**
	 * @param classifiers
	 * @param d
	 */
	private void extractClassifier(final Set<EClassifier> classifiers, final Diagnostic d) {
		if (d.getData().size() == 0 || !(d.getData().get(0) instanceof EObject)) {
			return;
		}
		final EObject object = (EObject) d.getData().get(0);
		classifiers.add(object.eClass());
	}

	private void notifyListener(Object diagnostic, Set<EClassifier> classifiers) {
		notifyListenerFromSet(diagnostic, listener);

		for (final EClassifier c : classifiers) {
			final Set<ECPValidationResultServiceListener> set = classifierToListenerMap.get(c);
			if (set == null) {
				continue;
			}
			notifyListenerFromSet(diagnostic, set);
		}
	}

	private void notifyListenerFromSet(Object diagnostic, Set<ECPValidationResultServiceListener> listener) {
		final Iterator<ECPValidationResultServiceListener> iterator = listener.iterator();
		while (iterator.hasNext()) {
			iterator.next().resultChanged(diagnostic);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.validation.ECPValidationResultService#register(org.eclipse.emf.ecp.ui.validation.ECPValidationResultService.ECPValidationResultServiceListener)
	 */
	@Override
	public void register(ECPValidationResultServiceListener listener) {
		/* Register for all results. */
		this.listener.add(listener);
		removeFromSpecificMap(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.validation.ECPValidationResultService#register(org.eclipse.emf.ecp.ui.validation.ECPValidationResultService.ECPValidationResultServiceListener,
	 *      java.util.Set)
	 */
	@Override
	public void register(ECPValidationResultServiceListener listener, Set<EClassifier> classifiersOfInterest) {
		/* remove from list for all notifications */
		this.listener.remove(listener);

		/* Add to new */
		final Set<EClassifier> currentlyRegisteredClassifiers = listenerToClassifierMap.containsKey(listener) ? listenerToClassifierMap
			.get(listener)
			: new LinkedHashSet<EClassifier>();

		final Set<EClassifier> classifiersToRemove = new LinkedHashSet<EClassifier>();
		for (final EClassifier classifier : currentlyRegisteredClassifiers) {
			if (!classifiersOfInterest.contains(classifier)) {
				classifiersToRemove.add(classifier);
				classifierToListenerMap.get(classifier).remove(listener);
			}
		}
		currentlyRegisteredClassifiers.removeAll(classifiersToRemove);
		for (final EClassifier classifier : classifiersOfInterest) {
			currentlyRegisteredClassifiers.add(classifier);
			addToClassifierToListenerMap(classifier, listener);
		}
		listenerToClassifierMap.put(listener, currentlyRegisteredClassifiers);
	}

	private void addToClassifierToListenerMap(EClassifier classifier, ECPValidationResultServiceListener listener) {
		if (!classifierToListenerMap.containsKey(classifier)) {
			classifierToListenerMap.put(classifier, new LinkedHashSet<ECPValidationResultServiceListener>());
		}
		classifierToListenerMap.get(classifier).add(listener);
	}

	/**
	 * @param listener
	 */
	private void removeFromSpecificMap(ECPValidationResultServiceListener listener) {
		/* Check if listener was registered for some classifiers. If so unregister. */
		if (!listenerToClassifierMap.containsKey(listener)) {
			return;
		}
		final Set<EClassifier> classifiers = listenerToClassifierMap.get(listener);
		for (final EClassifier c : classifiers) {
			classifierToListenerMap.get(c).remove(listener);
		}
		listenerToClassifierMap.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.validation.ECPValidationResultService#deregister(org.eclipse.emf.ecp.ui.validation.ECPValidationResultService.ECPValidationResultServiceListener)
	 */
	@Override
	public void deregister(ECPValidationResultServiceListener listener) {
		this.listener.remove(listener);
		removeFromSpecificMap(listener);
	}

}
