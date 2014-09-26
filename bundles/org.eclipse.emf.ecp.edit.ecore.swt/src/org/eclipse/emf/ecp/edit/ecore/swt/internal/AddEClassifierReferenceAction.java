/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.ecore.swt.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.reference.AddReferenceAction;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizard;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

/**
 * An action to select an EClassifier from the registry.
 * 
 * @author jfaltermeier
 * 
 */
public class AddEClassifierReferenceAction extends AddReferenceAction {

	private final ReferenceService referenceService;
	private final Set<EPackage> ePackages;

	/**
	 * Constructor.
	 * 
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param setting the {@link Setting} to use
	 * @param itemPropertyDescriptor the {@link IItemPropertyDescriptor} to use
	 * @param referenceService the {@link ReferenceService} to use
	 * @param packages the {@link EPackage}s to use
	 */
	public AddEClassifierReferenceAction(EditingDomain editingDomain, Setting setting,
		IItemPropertyDescriptor itemPropertyDescriptor, ReferenceService referenceService, Set<EPackage> packages) {
		super(editingDomain, setting, itemPropertyDescriptor, referenceService);
		this.referenceService = referenceService;
		ePackages = packages;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		final EClassifier selectedClassifier = getExistingEClassifiers();
		if (selectedClassifier == null) {
			return;
		}
		referenceService.addNewModelElements(selectedClassifier, (EReference) getSetting().getEStructuralFeature());
	}

	private EClassifier getExistingEClassifiers() {
		final Set<EClassifier> elements = getEClassifiersFromRegistry(ePackages);

		final SelectModelElementWizard wizard = new SelectModelElementWizard("Select EClassifier", //$NON-NLS-1$
			Messages.NewModelElementWizard_WizardTitle_AddModelElement,
			Messages.ModelelementSelectionDialog_DialogTitle,
			Messages.ModelelementSelectionDialog_DialogMessage_SearchPattern, EObject.class);

		final SelectionComposite<TableViewer> tableSelectionComposite = CompositeFactory
			.getTableSelectionComposite(elements.toArray(), false);
		wizard.setCompositeProvider(tableSelectionComposite);

		final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
		EClassifier eClassifier = null;
		final int result = wd.open();
		if (result == Window.OK) {
			final Object[] selection = tableSelectionComposite.getSelection();
			if (selection == null || selection.length == 0) {
				return null;
			}
			eClassifier = (EClassifier) selection[0];

		}
		return eClassifier;
	}

	/**
	 * Extracts the {@link EClassifier}s from the registry.
	 * 
	 * @param ePackages the {@link EPackage}s to extract the classifiers from
	 * 
	 * @return the classifiers
	 */
	protected Set<EClassifier> getEClassifiersFromRegistry(Set<EPackage> ePackages) {
		final Set<EClassifier> elements = new HashSet<EClassifier>();
		for (final EPackage ePackage : ePackages) {
			elements.addAll(ePackage.getEClassifiers());
		}
		return elements;
	}

}
