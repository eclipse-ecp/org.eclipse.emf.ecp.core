/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.editor.controls;

import java.net.URL;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.util.OverlayImageDescriptor;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * A {@link ECPSWTAction} allowing to set a reference.
 *
 * @since 1.5
 *
 */
public abstract class AbstractFilteredReferenceAction extends ECPSWTAction {
	private final Shell shell;

	/**
	 * Returns the used {@link Shell}.
	 *
	 * @return the shell
	 */
	protected Shell getShell() {
		return shell;
	}

	/**
	 * Constructor.
	 *
	 * @param editingDomain the editing domain to use
	 * @param setting the setting to use
	 * @param descriptor the property descriptor
	 * @param shell the shell
	 */
	public AbstractFilteredReferenceAction(EditingDomain editingDomain, Setting setting,
		IItemPropertyDescriptor descriptor, Shell shell) {
		super(editingDomain, setting);
		this.shell = shell;

		final EReference eReference = (EReference) setting.getEStructuralFeature();
		Object obj = null;
		if (!eReference.getEReferenceType().isAbstract()) {
			obj = eReference.getEReferenceType().getEPackage().getEFactoryInstance()
				.create(eReference.getEReferenceType());
		}
		final IItemLabelProvider labelProvider = descriptor.getLabelProvider(obj);
		Object labelProviderImageResult = labelProvider.getImage(obj);
		if (ComposedImage.class.isInstance(labelProviderImageResult)) {
			labelProviderImageResult = ((ComposedImage) labelProviderImageResult).getImages()
				.get(0);
		}

		final Image image = Activator.getImage(obj == null ? null : (URL) labelProviderImageResult);
		String overlayString = "icons/link_overlay.png"; //$NON-NLS-1$
		if (eReference.isContainment()) {
			overlayString = "icons/containment_overlay.png"; //$NON-NLS-1$
		}
		final ImageDescriptor addOverlay = Activator.getImageDescriptor(overlayString);
		final OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(image, addOverlay,
			OverlayImageDescriptor.LOWER_RIGHT);
		setImageDescriptor(imageDescriptor);

		String attribute = descriptor.getDisplayName(eReference);
		// make singular attribute labels
		if (attribute.endsWith("ies")) { //$NON-NLS-1$
			attribute = attribute.substring(0, attribute.length() - 3) + "y"; //$NON-NLS-1$
		} else if (attribute.endsWith("s")) { //$NON-NLS-1$
			attribute = attribute.substring(0, attribute.length() - 1);
		}

		setToolTipText("Link " + attribute); //$NON-NLS-1$

	}
}
