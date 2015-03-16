/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.internal.common.ui.MessageKeys;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

/**
 * @author Jonas
 * @since 1.5
 *
 */
public abstract class SelectModelElementWizardFactory {

	/**
	 * @param elements The elements to be selected
	 * @param isMany whether multi selection is allowed
	 * @return The selected elements
	 */
	public static Set<EObject> openModelElementSelectionDialog(
		final Set<EObject> elements, boolean isMany) {

		final SelectionComposite<TableViewer> tableSelectionComposite = CompositeFactory
			.getTableSelectionComposite(elements.toArray(), isMany);

		final SelectModelElementWizard wizard = new SelectModelElementWizard(
			LocalizationServiceHelper.getString(SelectModelElementWizardFactory.class,
				MessageKeys.SelectModelElementWizardFactory_ModelelementSelectionDialog_WindowTitle),
			LocalizationServiceHelper.getString(SelectModelElementWizardFactory.class,
				MessageKeys.NewModelElementWizard_WizardTitle_AddModelElement),
			LocalizationServiceHelper.getString(SelectModelElementWizardFactory.class,
				MessageKeys.ModelelementSelectionDialog_DialogTitle),
			LocalizationServiceHelper.getString(SelectModelElementWizardFactory.class,
				MessageKeys.ModelelementSelectionDialog_DialogMessage_SearchPattern), EObject.class);

		final HashSet<EObject> selectedElements = new HashSet<EObject>();
		wizard.setCompositeProvider(tableSelectionComposite);

		final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
		final int result = wd.open();
		if (result == Window.OK) {
			final Object[] selection = tableSelectionComposite.getSelection();
			if (selection == null || selection.length == 0) {
				return selectedElements;
			}

			for (final Object object : selection) {
				if (object instanceof EObject) {
					selectedElements.add((EObject) object);
				}
			}

		}
		return selectedElements;
	}

	/**
	 * @param selectionComposite the composite to select the Eclass on
	 * @return an new EObject or null, if canceled
	 */
	public static EObject openCreateNewModelElementDialog(final SelectionComposite<TreeViewer> selectionComposite) {
		final SelectModelElementWizard wizard = new SelectModelElementWizard("New Reference Element", //$NON-NLS-1$
			LocalizationServiceHelper.getString(SelectModelElementWizardFactory.class,
				MessageKeys.NewModelElementWizard_WizardTitle_AddModelElement),
			LocalizationServiceHelper
				.getString(SelectModelElementWizardFactory.class,
					MessageKeys.NewModelElementWizard_PageTitle_AddModelElement),
			LocalizationServiceHelper.getString(SelectModelElementWizardFactory.class,
				MessageKeys.NewModelElementWizard_PageDescription_AddModelElement));

		wizard.setCompositeProvider(selectionComposite);

		final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
		// wizard.setWindowTitle("New Reference Element");
		EObject newMEInstance = null;
		final int result = wd.open();

		if (result == Window.OK) {
			final Object[] selection = selectionComposite.getSelection();
			if (selection == null || selection.length == 0) {
				return null;
			}
			final EClass eClasse = (EClass) selection[0];
			// 1.create ME
			final EPackage ePackage = eClasse.getEPackage();
			newMEInstance = ePackage.getEFactoryInstance().create(eClasse);
		}
		return newMEInstance;
	}

}
