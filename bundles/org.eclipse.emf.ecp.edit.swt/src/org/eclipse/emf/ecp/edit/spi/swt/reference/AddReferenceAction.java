/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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

package org.eclipse.emf.ecp.edit.spi.swt.reference;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.reference.ReferenceMessageKeys;
import org.eclipse.emf.ecp.edit.internal.swt.util.OverlayImageDescriptor;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.ImageData;

/**
 * An Action for adding reference links to a model element. It is mainly used in the
 * {@link org.eclipse.emf.ecp.edit.internal.swt.controls.MultiControl MultiControl}
 *
 * @author shterev
 * @author Eugen Neufeld
 * @since 1.5
 */
public class AddReferenceAction extends ECPSWTAction {

	private final ReferenceService referenceService;

	/**
	 * The constructor for the add reference action.
	 *
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param setting the {@link Setting} to use
	 * @param itemPropertyDescriptor the property descriptor
	 * @param referenceService the {@link ReferenceService}
	 */
	public AddReferenceAction(EditingDomain editingDomain, Setting setting,
		IItemPropertyDescriptor itemPropertyDescriptor, ReferenceService referenceService) {
		super(editingDomain, setting);
		this.referenceService = referenceService;
		final EReference eReference = (EReference) setting.getEStructuralFeature();
		Object obj = null;
		if (!eReference.getEReferenceType().isAbstract()) {
			obj = eReference.getEReferenceType().getEPackage().getEFactoryInstance()
				.create(eReference.getEReferenceType());
		}
		final IItemLabelProvider labelProvider = itemPropertyDescriptor.getLabelProvider(setting.getEObject());
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
		} else {
			imageData = Activator.getImageData((URL) null);
		}
		String overlayString = "icons/link_overlay.png";//$NON-NLS-1$
		if (eReference.isContainment()) {
			overlayString = "icons/containment_overlay.png";//$NON-NLS-1$
		}
		final ImageDescriptor addOverlay = Activator.getImageDescriptor(overlayString);
		final OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(imageData, addOverlay,
			OverlayImageDescriptor.LOWER_RIGHT);
		setImageDescriptor(imageDescriptor);

		String attribute = itemPropertyDescriptor.getDisplayName(null);
		// make singular attribute labels
		// TODO language dependent
		if (attribute.endsWith("ies")) {//$NON-NLS-1$
			attribute = attribute.substring(0, attribute.length() - 3) + "y"; //$NON-NLS-1$
		} else if (attribute.endsWith("s")) {//$NON-NLS-1$
			attribute = attribute.substring(0, attribute.length() - 1);
		}
		setToolTipText(
			LocalizationServiceHelper.getString(getClass(), ReferenceMessageKeys.AddReferenceAction_Link) + attribute);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		final EReference eReference = (EReference) getSetting().getEStructuralFeature();
		if (eReference.isContainment()) {
			final MessageDialog dialog = getContainmentWarningDialog();
			new ECPDialogExecutor(dialog) {
				@Override
				public void handleResult(int codeResult) {
					if (codeResult == Window.OK) {
						addExistingElementsToReferenceService(getSetting().getEObject(), (EReference) getSetting()
							.getEStructuralFeature());
					}
				}
			}.execute();
		}
		addExistingElementsToReferenceService(getSetting().getEObject(), (EReference) getSetting()
			.getEStructuralFeature());
	}

	private void addExistingElementsToReferenceService(EObject eObject, EReference eReference) {
		if (referenceService == null) {
			return;
		}
		referenceService.addExistingModelElements(eObject, eReference);
	}

	private MessageDialog getContainmentWarningDialog() {
		return new MessageDialog(null,
			LocalizationServiceHelper.getString(AddReferenceAction.class,
				ReferenceMessageKeys.AddReferenceAction_Confirmation),
			null,
			LocalizationServiceHelper.getString(AddReferenceAction.class,
				ReferenceMessageKeys.AddReferenceAction_Warning),
			MessageDialog.WARNING,
			new String[] {
				LocalizationServiceHelper.getString(AddReferenceAction.class,
					ReferenceMessageKeys.AddReferenceAction_Yes),
				LocalizationServiceHelper.getString(AddReferenceAction.class,
					ReferenceMessageKeys.AddReferenceAction_No) },
			0);
	}
}
