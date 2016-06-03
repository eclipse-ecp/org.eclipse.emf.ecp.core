/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.common.spi.cachetree.AbstractCachedTree;
import org.eclipse.emf.ecp.common.spi.cachetree.CachedTreeNode;
import org.eclipse.emf.ecp.common.spi.cachetree.IExcludedObjectsCallback;

/**
 * Cache for diagnostic results.
 *
 * @author Johannes Faltermeier
 *
 */
public class DiagnosticCache extends AbstractCachedTree<Diagnostic> {

	/**
	 * Default constructor.
	 */
	public DiagnosticCache() {
		super(new IExcludedObjectsCallback() {

			@Override
			public boolean isExcluded(Object object) {
				return false;
			}
		});
	}

	@Override
	public Diagnostic getDefaultValue() {
		return Diagnostic.OK_INSTANCE;
	}

	@Override
	protected CachedTreeNode<Diagnostic> createdCachedTreeNode(Diagnostic value) {
		return new DiagnosticTreeNode(value);
	}

	/**
	 * Tree node for diagnostics.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private class DiagnosticTreeNode extends CachedTreeNode<Diagnostic> {

		private final Set<Diagnostic> diagnosticSet = new TreeSet<Diagnostic>(new Comparator<Diagnostic>() {

			@Override
			public int compare(Diagnostic o1, Diagnostic o2) {
				if (o1.getSeverity() == o2.getSeverity()) {
					if (o1 == o2) {
						return 0;
					}
					return 1;
				}
				return -1 * Integer.class.cast(o1.getSeverity()).compareTo(o2.getSeverity());
			}

		});

		DiagnosticTreeNode(Diagnostic initialValue) {
			super(initialValue);
		}

		@Override
		public void putIntoCache(Object key, Diagnostic value) {
			boolean updateRequired = true;

			if (getCache().containsKey(key)) {
				final Diagnostic diagnostic = getCache().get(key);
				if (diagnostic.getSeverity() == value.getSeverity()) {
					updateRequired = false;
				}
				diagnosticSet.remove(diagnostic);
			}
			getCache().put(key, value);
			diagnosticSet.add(value);

			if (updateRequired) {
				update();
			}
		}

		@Override
		public void update() {
			final Iterator<Diagnostic> iterator = diagnosticSet.iterator();
			if (iterator.hasNext()) {
				final Diagnostic mostSevereDiagnostic = iterator.next();
				setChildValue(mostSevereDiagnostic);
			} else {
				setChildValue(getDefaultValue());
			}
		}

		@Override
		public Diagnostic getDisplayValue() {
			if (getChildValue() == null) {
				return getOwnValue();
			}
			return getOwnValue().getSeverity() > getChildValue().getSeverity() ? getOwnValue() : getChildValue();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.common.spi.cachetree.CachedTreeNode#removeFromCache(java.lang.Object)
		 */
		@Override
		public void removeFromCache(Object key) {
			final Diagnostic diagnostic = getCache().remove(key);
			if (diagnostic != null) {
				diagnosticSet.remove(diagnostic);
			}
			update();
		}
	}

}
