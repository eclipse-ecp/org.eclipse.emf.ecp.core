/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * This {@link ILabelProvider} wrapps a {@link IBaseLabelProvider} and delegates to the base label provider as far as
 * possible.
 *
 * @author Johannes Faltermeier
 *
 */
public final class BaseLabelProviderWrapper implements ILabelProvider {
	private final IBaseLabelProvider labelProvider;

	/**
	 * @param labelProvider the wrapped label provider
	 */
	public BaseLabelProviderWrapper(IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		labelProvider.removeListener(listener);
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return labelProvider.isLabelProperty(element, property);
	}

	@Override
	public void dispose() {
		labelProvider.dispose();
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		labelProvider.addListener(listener);
	}

	@Override
	public String getText(Object element) {
		/* not supported by IBaseLabelProvider */
		return "";
	}

	@Override
	public Image getImage(Object element) {
		/* not supported by IBaseLabelProvider */
		return null;
	}
}