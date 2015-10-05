/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
			SWT.V_SCROLL | SWT.BORDER);
		detailScrollableComposite.setExpandHorizontal(true);
		detailScrollableComposite.setExpandVertical(true);
		return detailScrollableComposite;
	}
}