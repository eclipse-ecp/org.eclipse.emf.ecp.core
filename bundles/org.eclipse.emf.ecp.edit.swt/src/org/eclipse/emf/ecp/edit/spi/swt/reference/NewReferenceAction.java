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
package org.eclipse.emf.ecp.edit.spi.swt.reference;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.reference.ReferenceMessageKeys;
import org.eclipse.emf.ecp.edit.internal.swt.util.OverlayImageDescriptor;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.ImageData;

/**
 * An Action for adding reference links to a model element. It is mainly used in the
 * {@link org.eclipse.emf.ecp.edit.internal.swt.reference.ReferenceMultiControl ReferenceMultiControl}
 *
 * @author shterev
 * @author Eugen Neufeld
 * @since 1.5
 */
public class NewReferenceAction extends ECPSWTAction {

	private final ReferenceService referenceService;

	/**
	 * The constructor for a new reference action.
	 *
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param setting the {@link Setting} to use
	 * @param editSupport the {@link EMFFormsEditSupport} to use
	 * @param labelProvider the {@link EMFFormsLabelProvider} to use
	 * @param referenceService the {@link ReferenceService} to use
	 * @param reportService the {@link ReportService} to use
	 * @param domainModelReference the {@link VDomainModelReference} to use
	 * @param domainModel the domain model of the given {@link VDomainModelReference}
	 * @since 1.6
	 *
	 */
	public NewReferenceAction(EditingDomain editingDomain, Setting setting, EMFFormsEditSupport editSupport,
		EMFFormsLabelProvider labelProvider, ReferenceService referenceService, ReportService reportService,
		VDomainModelReference domainModelReference, EObject domainModel) {
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
		Object labelProviderImageResult = editSupport.getImage(domainModelReference, domainModel, obj);

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

		final ImageDescriptor addOverlay = Activator.getImageDescriptor("icons/add_overlay.png");//$NON-NLS-1$
		final OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(imageData, addOverlay,
			OverlayImageDescriptor.LOWER_RIGHT);
		setImageDescriptor(imageDescriptor);

		String attribute;
		try {
			final IObservableValue displayName = labelProvider.getDisplayName(domainModelReference, domainModel);
			attribute = (String) displayName.getValue();
			displayName.dispose();
		} catch (final NoLabelFoundException ex) {
			reportService.report(new AbstractReport(ex));
			setToolTipText(ex.getMessage());
			return;
		}
		// TODO language, same text as in addreference
		// make singular attribute labels
		if (attribute.endsWith("ies")) {//$NON-NLS-1$
			attribute = attribute.substring(0, attribute.length() - 3) + "y";//$NON-NLS-1$
		} else if (attribute.endsWith("s")) {//$NON-NLS-1$
			attribute = attribute.substring(0, attribute.length() - 1);
		}
		setToolTipText(LocalizationServiceHelper.getString(getClass(),
			ReferenceMessageKeys.NewReferenceAction_CreateAndLinkNew)
			+ attribute);
	}

	/**
	 * The constructor for a new reference action.
	 *
	 * @param editingDomain The {@link EditingDomain} to use
	 * @param eObject The {@link EObject} to use
	 * @param structuralFeature The {@link EStructuralFeature} defining which feature of the {@link EObject} is used
	 * @param editSupport The {@link EMFFormsEditSupport} to use
	 * @param labelProvider the {@link EMFFormsLabelProvider} to use
	 * @param referenceService The {@link ReferenceService} to use
	 * @param reportService The {@link ReportService} to use
	 * @param domainModelReference the {@link VDomainModelReference} to use
	 * @param domainModel the domain model of the given {@link VDomainModelReference}
	 * @since 1.6
	 */
	public NewReferenceAction(EditingDomain editingDomain, EObject eObject, EStructuralFeature structuralFeature,
		EMFFormsEditSupport editSupport, EMFFormsLabelProvider labelProvider, ReferenceService referenceService,
		ReportService reportService, VDomainModelReference domainModelReference, EObject domainModel) {
		this(editingDomain, ((InternalEObject) eObject).eSetting(structuralFeature), editSupport, labelProvider,
			referenceService, reportService, domainModelReference, domainModel);
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
		if (eReference.isContainment() && getSetting().getEObject().eIsSet(eReference)) {
			final MessageDialog dialog = getContainmentWarningDialog();
			new ECPDialogExecutor(dialog) {
				@Override
				public void handleResult(int codeResult) {
					if (codeResult == Window.OK) {
						addNewElementsToReferenceService(getSetting().getEObject(), eReference);
					}
				}
			}.execute();
		}
		addNewElementsToReferenceService(getSetting().getEObject(), eReference);
	}

	private void addNewElementsToReferenceService(EObject eObject, EReference eReference) {
		if (referenceService == null) {
			return;
		}
		referenceService.addNewModelElements(eObject, eReference);
	}

	private MessageDialog getContainmentWarningDialog() {
		return new MessageDialog(null,
			LocalizationServiceHelper.getString(NewReferenceAction.class,
				ReferenceMessageKeys.NewReferenceAction_Confirmation),
			null,
			LocalizationServiceHelper.getString(NewReferenceAction.class,
				ReferenceMessageKeys.NewReferenceAction_Warning),
			MessageDialog.WARNING,
			new String[] {
				LocalizationServiceHelper.getString(NewReferenceAction.class,
					ReferenceMessageKeys.NewReferenceAction_Yes),
				LocalizationServiceHelper.getString(NewReferenceAction.class,
					ReferenceMessageKeys.NewReferenceAction_No) },
			0);
	}
}
