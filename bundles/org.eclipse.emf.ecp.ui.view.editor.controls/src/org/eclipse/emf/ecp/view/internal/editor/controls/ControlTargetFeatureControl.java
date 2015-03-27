/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.reference.LinkControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.view.spi.editor.controls.AbstractFilteredReferenceAction;
import org.eclipse.emf.ecp.view.spi.editor.controls.AbstractFilteredReferenceCommand;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * A Link Control for creating VFeaturePathDomainReference Objects.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("deprecation")
public class ControlTargetFeatureControl extends LinkControl {

	@Override
	protected int getNumButtons() {
		return 2;
	}

	@Override
	protected Object getLinkText(Object value) {

		final VFeaturePathDomainModelReference modelReference =
			(VFeaturePathDomainModelReference) getFirstSetting().getEObject();

		String className = ""; //$NON-NLS-1$
		final String attributeName = " -> " + getAdapterFactoryItemDelegator().getText(value); //$NON-NLS-1$
		String referencePath = ""; //$NON-NLS-1$

		for (final EReference ref : modelReference.getDomainModelEReferencePath()) {
			if (className.isEmpty()) {
				className = ref.getEContainingClass().getName();
			}
			referencePath = referencePath + " -> " + getAdapterFactoryItemDelegator().getText(ref); //$NON-NLS-1$
		}
		if (className.isEmpty() && modelReference.getDomainModelEFeature() != null
			&& modelReference.getDomainModelEFeature().getEContainingClass() != null) {

			className = modelReference.getDomainModelEFeature().getEContainingClass().getName();
		}

		final String linkText = className + referencePath + attributeName;
		return linkText;
	}

	@Override
	protected Button[] createButtons(Composite composite) {
		final Button[] buttons = new Button[2];
		final Setting setting = getFirstSetting();
		buttons[0] = createButtonForAction(new DeleteReferenceAction(getEditingDomain(setting), setting,
			getService(ReferenceService.class)) {

			@Override
			public void run() {
				super.run();
				final VFeaturePathDomainModelReference modelReference =
					(VFeaturePathDomainModelReference) setting.getEObject();
				modelReference.getDomainModelEReferencePath().clear();
				modelReference.setDomainModelEFeature(null);
			}

		}, composite);
		buttons[1] = createButtonForAction(new FilteredReferenceAction(getEditingDomain(setting), setting,
			getItemPropertyDescriptor(setting), composite.getShell()), composite);
		return buttons;
	}

	/**
	 * Whether the selection of many features are allowed or not.
	 *
	 * @return true if the selection of many attributes is allowed, false otherwise
	 */
	protected boolean allowMultiSelection() {
		return false;
	}

	/**
	 * Private class for setting a reference.
	 *
	 */
	private class FilteredReferenceAction extends AbstractFilteredReferenceAction {

		public FilteredReferenceAction(EditingDomain editingDomain, Setting setting,
			IItemPropertyDescriptor descriptor, Shell shell) {
			super(editingDomain, setting, descriptor, shell);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			getEditingDomain()
				.getCommandStack()
				.execute(
					new FilteredReferenceCommand(getSetting().getEObject(), getComposedAdapterFactory(),
						getShell()));
		}
	}

	/**
	 * The command for changing the reference.
	 *
	 */
	private class FilteredReferenceCommand extends AbstractFilteredReferenceCommand<EStructuralFeature> {

		public FilteredReferenceCommand(final Notifier notifier, ComposedAdapterFactory composedAdapterFactory,
			Shell shell) {

			super(notifier, composedAdapterFactory, shell, Helper.getRootEClass((EObject) notifier),
				new ECPSelectionStatusValidator() {

					@Override
					public IStatus validate(Object[] selection) {

						if (selection.length != 0 && EStructuralFeature.class.isInstance(selection[0])) {
							final TreePath treePath = getTreePath();
							if (!Helper
								.hasFeaturePropertyDescriptor(EStructuralFeature.class.cast(selection[0])
									.getEContainingClass(), treePath)) {
								// FIXME Hack, for allowing the selection of EStructuralFeatures w/o property
								// descriptors. Should return error.
								return new Status(IStatus.WARNING,
									org.eclipse.emf.ecp.view.internal.editor.controls.Activator.PLUGIN_ID,
									"The selected " + EStructuralFeature.class.getSimpleName() //$NON-NLS-1$
										+ " has no PropertyDescriptor."); //$NON-NLS-1$
							}
							return Status.OK_STATUS;
						}
						return new Status(IStatus.ERROR,
							org.eclipse.emf.ecp.view.internal.editor.controls.Activator.PLUGIN_ID,
							"This is not an " + EStructuralFeature.class.getSimpleName() + "."); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}, allowMultiSelection());
		}

		@Override
		protected void setSelectedValues(EStructuralFeature selectedFeature, List<EReference> bottomUpPath) {
			final VFeaturePathDomainModelReference modelReference = (VFeaturePathDomainModelReference) getFirstSetting()
				.getEObject();
			modelReference.setDomainModelEFeature(selectedFeature);
			modelReference.getDomainModelEReferencePath().clear();
			modelReference.getDomainModelEReferencePath().addAll(bottomUpPath);
		}

	}
}
