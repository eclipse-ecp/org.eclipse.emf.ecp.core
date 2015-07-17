/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.quickfix.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.quickfix.ModelQuickFix;
import org.eclipse.emf.ecp.quickfix.ModelQuickFixRegistry;

/**
 * @author Alexandra Buzila
 *
 */
public class ModelQuickFixRegistryImpl implements ModelQuickFixRegistry {

	private final ArrayList<ModelQuickFix> registeredQuickFixes = new ArrayList<ModelQuickFix>();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.quickfix.ModelQuickFixRegistry#registerModelQuickFix(org.eclipse.emf.ecp.quickfix.ModelQuickFix)
	 */
	@Override
	public void registerModelQuickFix(ModelQuickFix modelQuickFix) {
		if (modelQuickFix == null) {
			return;
		}
		registeredQuickFixes.add(modelQuickFix);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.quickfix.ModelQuickFixRegistry#getApplicableModelQuickFixes(org.eclipse.emf.common.util.Diagnostic)
	 */
	@Override
	public List<ModelQuickFix> getApplicableModelQuickFixes(Diagnostic diagnostic) {
		final ArrayList<Pair<Double, ModelQuickFix>> applicableQuickFixes = new ArrayList<Pair<Double, ModelQuickFix>>();
		for (final ModelQuickFix fix : getAllModelQuickFixes()) {
			final double priority = fix.isApplicable(diagnostic);
			if (priority != ModelQuickFix.NOT_APPLICABLE) {
				addQuickFixToList(applicableQuickFixes, fix, priority);
			}
		}
		return getQuickFixes(applicableQuickFixes);
	}

	/**
	 * @param applicableQuickFixes
	 * @return all quick fixes in the array
	 */
	private List<ModelQuickFix> getQuickFixes(ArrayList<Pair<Double, ModelQuickFix>> applicableQuickFixes) {
		final List<ModelQuickFix> quickFixes = new ArrayList<ModelQuickFix>();
		for (final Pair<Double, ModelQuickFix> item : applicableQuickFixes) {
			quickFixes.add(item.y);
		}
		return quickFixes;
	}

	/**
	 * @param applicableQuickFixes - the array in which the {@code fix} will be added
	 * @param fix - the model quick fix to add
	 * @param priority - the priority of the {@code fix}
	 */
	private void addQuickFixToList(ArrayList<Pair<Double, ModelQuickFix>> applicableQuickFixes, ModelQuickFix fix,
		double priority) {
		int i = 0;
		for (; i < applicableQuickFixes.size(); i++) {
			if (applicableQuickFixes.get(i).x < priority) {
				break;
			}
		}
		applicableQuickFixes.add(i, new Pair<Double, ModelQuickFix>(priority, fix));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.quickfix.ModelQuickFixRegistry#getAllModelQuickFixes()
	 */
	@Override
	public List<ModelQuickFix> getAllModelQuickFixes() {
		return registeredQuickFixes;
	}

	/** Util class for pairs of objects. */
	private class Pair<X, Y> {
		private final X x;
		private final Y y;

		Pair(X x, Y y) {
			this.x = x;
			this.y = y;
		}
	}
}
