/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.ecp.internal.ui.model;

import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.model.TreeContentProvider.ErrorElement;
import org.eclipse.emf.ecp.internal.ui.model.TreeContentProvider.SlowElement;
import org.eclipse.emf.ecp.spi.ui.UIProvider;
import org.eclipse.emf.ecp.spi.ui.UIProviderRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author Eike Stepper
 */
public class ECPLabelProvider extends LabelProvider implements ECPModelContextProvider, IECPLabelProvider {
	private ECPModelContextProvider modelContextProvider;

	public ECPLabelProvider(ECPModelContextProvider modelContextProvider) {
		this.modelContextProvider = modelContextProvider;
	}

	@Override
	public String getText(Object element) {
		final UIProvider uiProvider = getUIProvider(element);
		if (uiProvider != null) {
			final String text = uiProvider.getText(element);
			if (text != null) {
				return text;
			}
		}

		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof SlowElement) {
			return Activator.getImage("icons/pending.gif"); //$NON-NLS-1$
		}

		if (element instanceof ErrorElement) {
			return Activator.getImage("icons/error.gif"); //$NON-NLS-1$
		}

		final UIProvider uiProvider = getUIProvider(element);
		if (uiProvider != null) {
			final Image image = uiProvider.getImage(element);
			if (image != null) {
				return image;
			}
		}

		return super.getImage(element);
	}

	public UIProvider getUIProvider(Object element) {
		UIProvider uiProvider = UIProviderRegistry.INSTANCE.getUIProvider(element);
		if (uiProvider == null) {
			final ECPContainer modelContext = getModelContext(element);
			if (modelContext != null) {
				uiProvider = UIProviderRegistry.INSTANCE.getUIProvider(modelContext);
			}
		}

		return uiProvider;
	}

	/** {@inheritDoc} */
	@Override
	public ECPContainer getModelContext(Object element) {
		if (modelContextProvider != null) {
			return modelContextProvider.getModelContext(element);
		}

		return null;
	}

	protected final void fireEvent(final LabelProviderChangedEvent event) {
		final Display display = Display.getCurrent();
		if (display == null) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					fireLabelProviderChanged(event);
				}
			});
		} else {
			fireLabelProviderChanged(event);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.internal.ui.model.IECPLabelProvider#getModelContextProvider()
	 */
	@Override
	public ECPModelContextProvider getModelContextProvider() {
		return modelContextProvider;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.internal.ui.model.IECPLabelProvider#setModelContextProvider(org.eclipse.emf.ecp.core.util.ECPModelContextProvider)
	 */
	@Override
	public void setModelContextProvider(ECPModelContextProvider modelContextProvider) {
		this.modelContextProvider = modelContextProvider;
	}
}
