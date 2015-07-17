/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.core.ECPProviderRegistryImpl;
import org.eclipse.emf.ecp.internal.core.util.ElementDescriptor;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.util.InternalDescriptor;
import org.eclipse.emf.ecp.spi.core.util.InternalElementRegistry.ResolveListener;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author Eike Stepper
 */
public class ProvidersLabelProvider extends ECPLabelProvider implements IColorProvider,
ResolveListener<InternalProvider> {
	private static final Image PROVIDER = Activator.getImage("icons/provider.gif"); //$NON-NLS-1$

	private static final Image PROVIDER_DISABLED = Activator.getImage("icons/provider_disabled.gif"); //$NON-NLS-1$

	private static final Color GRAY = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);

	public ProvidersLabelProvider() {
		super(null);
		((ECPProviderRegistryImpl) ECPUtil.getECPProviderRegistry()).addResolveListener(this);
	}

	@Override
	public void dispose() {
		((ECPProviderRegistryImpl) ECPUtil.getECPProviderRegistry()).removeResolveListener(this);
		super.dispose();
	}

	/** {@inheritDoc} */
	@Override
	public void descriptorChanged(InternalDescriptor<InternalProvider> descriptor, boolean resolved) throws Exception {
		fireEvent(new LabelProviderChangedEvent(this, descriptor));
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ECPProvider) {
			final ECPProvider provider = (ECPProvider) element;
			return provider.getLabel();
		}

		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof ECPProvider) {
			if (element instanceof ElementDescriptor) {
				final ElementDescriptor<?> descriptor = (ElementDescriptor<?>) element;
				if (!descriptor.isResolved()) {
					return PROVIDER_DISABLED;
				}
			}

			return PROVIDER;
		}

		return super.getImage(element);
	}

	/** {@inheritDoc} */
	@Override
	public Color getForeground(Object element) {
		if (element instanceof ElementDescriptor) {
			final ElementDescriptor<?> descriptor = (ElementDescriptor<?>) element;
			if (!descriptor.isResolved()) {
				return GRAY;
			}
		}

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Color getBackground(Object element) {
		return null;
	}
}
