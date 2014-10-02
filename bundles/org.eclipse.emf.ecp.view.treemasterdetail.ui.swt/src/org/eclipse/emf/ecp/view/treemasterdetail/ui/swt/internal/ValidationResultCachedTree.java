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
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal;

import java.util.Iterator;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.common.cachetree.AbstractCachedTree;
import org.eclipse.emf.ecp.common.cachetree.CachedTreeNode;
import org.eclipse.emf.ecp.common.cachetree.IExcludedObjectsCallback;

/**
 * {@link AbstractCachedTree} for propagating severities in the tree master detail.
 *
 * @author jfaltermeier
 *
 */
@SuppressWarnings("restriction")
public class ValidationResultCachedTree extends AbstractCachedTree<Integer> {

	/**
	 * Constructor.
	 *
	 * @param callback callback to exclude objects
	 */
	public ValidationResultCachedTree(IExcludedObjectsCallback callback) {
		super(callback);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.common.cachetree.AbstractCachedTree#getDefaultValue()
	 */
	@Override
	public Integer getDefaultValue() {
		return Diagnostic.OK;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.common.cachetree.AbstractCachedTree#createdCachedTreeNode(java.lang.Object)
	 */
	@Override
	protected CachedTreeNode<Integer> createdCachedTreeNode(Integer value) {
		return new ValidationResultCachedTreeNode(value);
	}

	/**
	 * Implementation of the tree node caching a severity.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class ValidationResultCachedTreeNode extends CachedTreeNode<Integer> {

		/**
		 * @param initialValue
		 */
		public ValidationResultCachedTreeNode(Integer initialValue) {
			super(initialValue);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.common.cachetree.CachedTreeNode#update()
		 */
		@Override
		protected void update() {
			final Iterator<Integer> iterator = values().iterator();
			int severity = getDefaultValue();
			while (iterator.hasNext()) {
				final Integer childSeverity = iterator.next();
				if (childSeverity > severity) {
					severity = childSeverity;
				}
			}
			setChildValue(severity);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.common.cachetree.CachedTreeNode#getDisplayValue()
		 */
		@Override
		public Integer getDisplayValue() {
			if (getChildValue() == null) {
				return getOwnValue();
			}
			return getOwnValue() > getChildValue() ? getOwnValue() : getChildValue();
		}

	}

}
