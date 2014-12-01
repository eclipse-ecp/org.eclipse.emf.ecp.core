/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;

/**
 * Iterator that returns all settings of the condition's {@link LeafCondition#getDomainModelReference()} and
 * {@link LeafCondition#getValueDomainModelReference()}.
 *
 * @author jfaltermeier
 * @since 1.5
 *
 */
public class LeafConditionSettingIterator implements Iterator<Setting> {

	/**
	 * Map from a setting iterator to the children of the last returned setting of the iterator.
	 */
	private final Map<Iterator<Setting>, Iterator<EObject>> iteratorToCurrentChildrenMap;
	private int expectedStackSize;
	private final Stack<Iterator<Setting>> iterators;
	private final boolean includeNonValueRelatedSettings;
	private final Set<VDomainModelReference> dmrs;

	private final List<Setting> nonValueRelSettings = new LinkedList<EStructuralFeature.Setting>();
	private final LeafCondition condition;

	/**
	 * Constructs a new {@link LeafConditionSettingIterator}.
	 *
	 * @param condition the leaf condition
	 * @param includeNonValueRelatedSettings whether to include non value related settings
	 */
	public LeafConditionSettingIterator(LeafCondition condition, boolean includeNonValueRelatedSettings) {
		this.condition = condition;
		dmrs = new LinkedHashSet<VDomainModelReference>();
		this.includeNonValueRelatedSettings = includeNonValueRelatedSettings;
		iteratorToCurrentChildrenMap = new LinkedHashMap<Iterator<Setting>, Iterator<EObject>>();
		expectedStackSize = 1;
		if (condition.getValueDomainModelReference() != null) {
			expectedStackSize = expectedStackSize + 1;
		}
		iterators = new Stack<Iterator<Setting>>();
		iterators.push(condition.getDomainModelReference().getIterator());
		organizeStack();
	}

	private void organizeStack() {
		if (iterators.size() == 0) {
			// end
			return;
		}

		if (iterators.size() == expectedStackSize && iterators.peek().hasNext()) {
			// all is ok
			return;
		}

		if (iterators.size() == expectedStackSize) {
			// top iterator has reached end.
			// since top iterator does not have entries in iteratorToCurrentChildrenMap,
			// this is treated as a special case
			iterators.pop();
			organizeStack();
			return;
		}

		// iterators.size() != expectedStackSize
		final Iterator<EObject> domainIterator = iteratorToCurrentChildrenMap.get(iterators.peek());
		if (domainIterator == null) {
			// move on to next setting
			if (iterators.peek().hasNext()) {
				final Setting setting = iterators.peek().next();
				if (includeNonValueRelatedSettings) {
					nonValueRelSettings.add(setting);
				}
				final Iterator<EObject> childIterator = getChildIterator(setting);
				iteratorToCurrentChildrenMap.put(iterators.peek(), childIterator);
				organizeStack();
				return;
			}

			// top iterator at end, pop
			iterators.pop();
			organizeStack();
			return;
		}

		if (domainIterator.hasNext()) {
			final EObject nextDomain = domainIterator.next();
			final VDomainModelReference valueDMR = EcoreUtil.copy(condition.getValueDomainModelReference());
			valueDMR.init(nextDomain);
			dmrs.add(valueDMR);
			iterators.push(valueDMR.getIterator());
			organizeStack();
			return;
		}

		// domain iterator at end
		// remove from map
		iteratorToCurrentChildrenMap.remove(iterators.peek());
		organizeStack();
		return;
	}

	@SuppressWarnings("unchecked")
	private Iterator<EObject> getChildIterator(Setting setting) {
		final List<EObject> children = new ArrayList<EObject>();
		if (setting.getEStructuralFeature().isMany()) {
			children.addAll((Collection<? extends EObject>) setting.get(true));
		} else {
			children.add((EObject) setting.get(true));
		}
		final Iterator<EObject> iterator = children.iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == null) {
				iterator.remove();
			}
		}
		return children.iterator();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (includeNonValueRelatedSettings && !nonValueRelSettings.isEmpty()) {
			return true;
		}
		if (iterators.isEmpty()) {
			return false;
		}
		return iterators.peek().hasNext();

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Setting next() {
		if (includeNonValueRelatedSettings && !nonValueRelSettings.isEmpty()) {
			return nonValueRelSettings.remove(0);
		}
		final Setting result = iterators.peek().next();
		organizeStack();
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * <br>
	 * Returns the used {@link VDomainModelReference VDomainModelReferences}.</br>
	 * <b>This map will contain all entries when the iterator has reached its end.</b>
	 *
	 * @return the map
	 */
	public Set<VDomainModelReference> getUsedValueDomainModelReferences() {
		return dmrs;
	}

}