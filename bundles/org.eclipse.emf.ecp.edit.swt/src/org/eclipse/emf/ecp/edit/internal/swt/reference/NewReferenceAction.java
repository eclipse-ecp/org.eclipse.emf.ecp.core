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
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.reference;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.actions.ECPSWTAction;
import org.eclipse.emf.ecp.edit.internal.swt.util.OverlayImageDescriptor;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;

/**
 * An Action for adding reference links to a model element. It is mainly used in the {@link ReferenceMultiControl}
 *
 * @author shterev
 * @author Eugen Neufeld
 */
public class NewReferenceAction extends ECPSWTAction {

	private final ReferenceService referenceService;

	/**
	 * The constructor for a new reference action.
	 *
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param itemPropertyDescriptor teh {@link IItemPropertyDescriptor} to use
	 * @param setting the {@link Setting} to use
	 * @param referenceService the {@link ReferenceService} to use
	 */
	public NewReferenceAction(EditingDomain editingDomain, Setting setting,
		IItemPropertyDescriptor itemPropertyDescriptor, ReferenceService referenceService) {
		super(editingDomain, setting);
		this.referenceService = referenceService;
		Object obj = null;
		final EReference eReference = (EReference) getSetting().getEStructuralFeature();
		// Only create a temporary object in order to get the correct icon from the label provider
		// the actual ME is created later on.
		if (!eReference.getEReferenceType().isAbstract()) {
			obj = eReference.getEReferenceType().getEPackage().getEFactoryInstance()
				.create(eReference.getEReferenceType());
		}
		final IItemLabelProvider labelProvider = itemPropertyDescriptor.getLabelProvider(
			getSetting().getEObject());
		Object labelProviderImageResult = labelProvider.getImage(obj);

		ImageData imageData = null;

		if (ComposedImage.class.isInstance(labelProviderImageResult)) {
			labelProviderImageResult = ((ComposedImage) labelProviderImageResult).getImages().get(0);
		}
		if (URI.class.isInstance(labelProviderImageResult)) {
			try {
				labelProviderImageResult = new URL(((URI) labelProviderImageResult).toString());
			} catch (final MalformedURLException ex) {
				Activator.logException(ex);
			}
		}
		if (URL.class.isInstance(labelProviderImageResult)) {
			imageData = Activator.getImageData((URL) labelProviderImageResult);
		}
		else {
			imageData = Activator.getImageData((URL) null);
		}

		final ImageDescriptor addOverlay = Activator.getImageDescriptor("icons/add_overlay.png");//$NON-NLS-1$
		final OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(imageData, addOverlay,
			OverlayImageDescriptor.LOWER_RIGHT);
		setImageDescriptor(imageDescriptor);

		String attribute = itemPropertyDescriptor.getDisplayName(eReference);
		// TODO language, same text as in addreference
		// make singular attribute labels
		if (attribute.endsWith("ies")) {//$NON-NLS-1$
			attribute = attribute.substring(0, attribute.length() - 3) + "y";//$NON-NLS-1$
		} else if (attribute.endsWith("s")) {//$NON-NLS-1$
			attribute = attribute.substring(0, attribute.length() - 1);
		}
		setToolTipText(ActionMessages.NewReferenceAction_CreateAndLinkNew + attribute);
	}

	/**
	 * Default constructor.
	 *
	 * @param modelElement
	 *            the source model element
	 * @param eReference
	 *            the target reference
	 * @param descriptor
	 *            the descriptor used to generate display content
	 * @param modelElementContext
	 *            the model element context
	 */
	// public NewReferenceAction(EObject modelElement, EReference eReference, IItemPropertyDescriptor descriptor,
	// EditModelElementContext modelElementContext, Shell shell, AdapterFactoryLabelProvider labelProvider) {
	// this.modelElement = modelElement;
	// this.eReference = eReference;
	// this.modelElementContext = modelElementContext;
	// this.shell = shell;
	//
	// Object obj = null;
	// // Only create a temporary object in order to get the correct icon from the label provider
	// // the actual ME is created later on.
	// if (!eReference.getEReferenceType().isAbstract()) {
	// obj = eReference.getEReferenceType().getEPackage().getEFactoryInstance()
	// .create(eReference.getEReferenceType());
	// }
	// Image image = labelProvider.getImage(obj);
	//
	// ImageDescriptor addOverlay = Activator.getImageDescriptor("icons/add_overlay.png");//$NON-NLS-1$
	// OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(image, addOverlay,
	// OverlayImageDescriptor.LOWER_RIGHT);
	// setImageDescriptor(imageDescriptor);
	//
	// String attribute = descriptor.getDisplayName(eReference);
	//
	// // make singular attribute labels
	// if (attribute.endsWith("ies")) {//$NON-NLS-1$
	// attribute = attribute.substring(0, attribute.length() - 3) + "y";//$NON-NLS-1$
	// } else if (attribute.endsWith("s")) {//$NON-NLS-1$
	// attribute = attribute.substring(0, attribute.length() - 1);
	// }
	// setToolTipText(ActionMessages.NewReferenceAction_CreateAndLinkNew + attribute);
	//
	// }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// checks if we try to create a container for ourself, this is not allowed
		final EReference eReference = (EReference) getSetting().getEStructuralFeature();
		referenceService.addNewModelElements(getSetting().getEObject(),
			eReference);
	}

}
