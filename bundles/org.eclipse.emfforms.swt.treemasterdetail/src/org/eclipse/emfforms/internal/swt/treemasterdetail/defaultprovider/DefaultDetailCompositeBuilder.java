/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider;

import org.eclipse.emfforms.spi.swt.treemasterdetail.DetailCompositeBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * @author jfaltermeier
 *
 */
public final class DefaultDetailCompositeBuilder implements DetailCompositeBuilder {
	@Override
	public Composite createDetailComposite(Composite parent) {
		final ScrolledComposite detailScrollableComposite = new ScrolledComposite(parent,
			SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		detailScrollableComposite.setExpandHorizontal(true);
		detailScrollableComposite.setExpandVertical(true);
		return detailScrollableComposite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emfforms.spi.swt.treemasterdetail.DetailCompositeBuilder#enableVerticalCopy()
	 */
	@Override
	public boolean enableVerticalCopy() {
		return false;
	}
}