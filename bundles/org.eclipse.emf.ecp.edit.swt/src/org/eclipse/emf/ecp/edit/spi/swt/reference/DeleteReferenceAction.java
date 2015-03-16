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
package org.eclipse.emf.ecp.edit.spi.swt.reference;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.reference.ReferenceMessageKeys;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

/**
 * This action unsets a reference.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public class DeleteReferenceAction extends ECPSWTAction {

	/**
	 * The constructor for a delete reference action.
	 *
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param itemPropertyDescriptor the {@link IItemPropertyDescriptor} to use
	 * @param setting the {@link Setting} to use
	 * @param referenceService the {@link ReferenceService} to use
	 */
	public DeleteReferenceAction(EditingDomain editingDomain, Setting setting,
		IItemPropertyDescriptor itemPropertyDescriptor, ReferenceService referenceService) {
		super(editingDomain, setting);
		// TODO remove PlatformUI

		setImageDescriptor(Activator.getImageDescriptor("icons/delete.png")); //$NON-NLS-1$
		setToolTipText(LocalizationServiceHelper.getString(DeleteReferenceAction.class,
			ReferenceMessageKeys.DeleteReferenceAction_DeleteReference));
	}

	@Override
	public void run() {
		super.run();
		// TODO: Reactivate
		if (DynamicEObjectImpl.class.isInstance(getSetting().getEObject())) {
			return;
		}
		final EReference reference = (EReference) getSetting().getEStructuralFeature();

		if (reference.isContainment()) // &&
		// context.getMetaModelElementContext().isNonDomainElement(opposite.eClass())||
		// context.getMetaModelElementContext().isAssociationClassElement(opposite)
		{
			if (askConfirmation((EObject) getSetting().get(true))) {
				delete(reference);
			}
		} else {
			delete(reference);
		}
	}

	private void delete(EReference reference) {
		Command removeCommand = null;

		if (reference.isMany()) {
			removeCommand = RemoveCommand.create(getEditingDomain(), getSetting().getEObject(), reference, getSetting()
				.get(true));
		} else {
			removeCommand = SetCommand.create(getEditingDomain(), getSetting().getEObject(), reference, null);
		}
		getEditingDomain().getCommandStack().execute(removeCommand);
	}

	private static boolean askConfirmation(EObject toBeDeleted) {
		String question = null;
		ComposedAdapterFactory adapterFactory = null;
		// if (toBeDeleted.size() == 1) {
		adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] { new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(adapterFactory);
		// AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
		final String modelElementName = adapterFactoryItemDelegator.getText(toBeDeleted);
		question = LocalizationServiceHelper.getString(DeleteReferenceAction.class,
			ReferenceMessageKeys.DeleteReferenceAction_DeleteModelQuestion)
			+ modelElementName
			+ LocalizationServiceHelper.getString(DeleteReferenceAction.class,
				ReferenceMessageKeys.DeleteReferenceAction_Questionmark);
		// } else {
		// question = "Do you really want to delete these " + toBeDeleted.size() + " model elements?";
		// }
		final MessageDialog dialog = new MessageDialog(
			null,
			LocalizationServiceHelper.getString(DeleteReferenceAction.class,
				ReferenceMessageKeys.DeleteReferenceAction_Confirmation),
			null,
			question,
			MessageDialog.QUESTION,
			new String[] {
				LocalizationServiceHelper.getString(DeleteReferenceAction.class, ReferenceMessageKeys.DeleteReferenceAction_Yes),
				LocalizationServiceHelper.getString(DeleteReferenceAction.class, ReferenceMessageKeys.DeleteReferenceAction_No) },
			0);

		boolean confirm = false;
		if (dialog.open() == Window.OK) {
			confirm = true;
		}

		// if (adapterFactory != null)
		// {
		// adapterFactory.dispose();
		// }
		adapterFactory.dispose();
		return confirm;
	}
}
