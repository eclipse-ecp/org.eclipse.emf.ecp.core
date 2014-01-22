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
package org.eclipse.emf.ecp.view.editor.controls;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.edit.internal.swt.reference.LinkControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumn;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

public class TableColumnAttributeControl extends LinkControl {

	@Override
	protected int getNumButtons() {
		return 2;
	}

	@Override
	protected Button[] createButtons(Composite composite) {
		final Button[] buttons = new Button[2];
		final Setting setting = getFirstSetting();
		buttons[0] = createButtonForAction(new DeleteReferenceAction(getEditingDomain(setting), setting,
			getItemPropertyDescriptor(setting), getService(ReferenceService.class)), composite);
		buttons[1] = createButtonForAction(new FilteredReferenceAction(
			getEditingDomain(setting), setting,
			getItemPropertyDescriptor(setting), composite.getShell()), composite);
		return buttons;
	}

	private class FilteredReferenceAction extends
		AbstractFilteredReferenceAction {

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
					new FilteredReferenceCommand(
						getSetting().getEObject(),
						getShell()));
		}
	}

	private class FilteredReferenceCommand extends ChangeCommand {

		private final Shell shell;

		public FilteredReferenceCommand(Notifier notifier, Shell shell) {
			super(notifier);
			this.shell = shell;
		}

		@Override
		protected void doExecute() {

			final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(
				getComposedAdapterFactory());
			final EReference eref = (EReference) ((VFeaturePathDomainModelReference) ((VTableControl) getFirstSetting()
				.getEObject()
				.eContainer()).getDomainModelReference()).getDomainModelEFeature();
			final ListDialog ld = new ListDialog(shell);
			ld.setLabelProvider(labelProvider);
			ld.setContentProvider(ArrayContentProvider.getInstance());
			ld.setInput(eref.getEReferenceType().getEAllAttributes());
			ld.setTitle("Select Attribute for TableColumn");
			final int result = ld.open();
			if (Window.OK == result) {
				final Object selection = ld.getResult()[0];
				if (EAttribute.class.isInstance(selection)) {
					final EAttribute selectedFeature = (EAttribute) selection;

					((VTableColumn) getFirstSetting().getEObject()).setAttribute(selectedFeature);
				}
			}
			labelProvider.dispose();
		}
	}
}
