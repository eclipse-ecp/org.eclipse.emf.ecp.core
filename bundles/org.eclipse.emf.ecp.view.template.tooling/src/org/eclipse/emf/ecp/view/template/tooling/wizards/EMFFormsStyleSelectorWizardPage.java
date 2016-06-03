/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.tooling.wizards;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This page allows users whether to create DMR-Selector or a ViewModelElement-Selector.
 *
 * @author Johannes Faltermeier
 *
 */
public class EMFFormsStyleSelectorWizardPage extends WizardPage {

	private final boolean showControlSelectors;

	private EClass selectorEClass;
	private Boolean uuidAttribute;

	/**
	 * Default constructor.
	 *
	 * @param showControlSelectors whether to show control dmr selector option
	 */
	EMFFormsStyleSelectorWizardPage(boolean showControlSelectors) {
		super("styleSelector");//$NON-NLS-1$
		setTitle(Messages.EMFFormsStyleSelectorWizardPage_Title);
		setDescription(
			Messages.EMFFormsStyleSelectorWizardPage_Description);
		this.showControlSelectors = showControlSelectors;
	}

	@Override
	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);

		final Button dmrSelector;
		if (showControlSelectors) {
			dmrSelector = new Button(composite, SWT.RADIO);
			dmrSelector.setText(Messages.EMFFormsStyleSelectorWizardPage_DMRSelectorButton);
			selectorEClass = VTDomainmodelreferencePackage.eINSTANCE.getDomainModelReferenceSelector();
			dmrSelector.setSelection(true);

			final Label dmrSelectorLabel = new Label(composite, SWT.WRAP);
			dmrSelectorLabel.setForeground(dmrSelectorLabel.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
			dmrSelectorLabel.setText(
				Messages.EMFFormsStyleSelectorWizardPage_DMRSelectorLabel);
		} else {
			dmrSelector = null;
		}

		final Button elementSelector = new Button(composite, SWT.RADIO);
		elementSelector.setText(Messages.EMFFormsStyleSelectorWizardPage_ViewModelElementButton);

		final Label elementSelectorLabel = new Label(composite, SWT.WRAP);
		elementSelectorLabel.setForeground(elementSelectorLabel.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		elementSelectorLabel.setText(
			Messages.EMFFormsStyleSelectorWizardPage_ViewElementSelectorLabel);

		final Button uuidSelector = new Button(composite, SWT.RADIO);
		uuidSelector.setText(Messages.EMFFormsStyleSelectorWizardPage_UUIDButtonText);

		final Label uuidSelectorLabel = new Label(composite, SWT.WRAP);
		uuidSelectorLabel.setForeground(uuidSelectorLabel.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		uuidSelectorLabel.setText(
			Messages.EMFFormsStyleSelectorWizardPage_UUIDLabelText);

		final SelectionAdapter selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.widget == null) {
					return;
				}
				if (e.widget == dmrSelector) {
					selectorEClass = VTDomainmodelreferencePackage.eINSTANCE.getDomainModelReferenceSelector();
					uuidAttribute = null;
				} else if (e.widget == elementSelector) {
					selectorEClass = VTViewModelElementPackage.eINSTANCE.getViewModelElementSelector();
					uuidAttribute = false;
				} else if (e.widget == uuidSelector) {
					selectorEClass = VTViewModelElementPackage.eINSTANCE.getViewModelElementSelector();
					uuidAttribute = true;
				}
			}
		};

		if (dmrSelector != null) {
			dmrSelector.addSelectionListener(selectionListener);
		}
		elementSelector.addSelectionListener(selectionListener);
		uuidSelector.addSelectionListener(selectionListener);

		setPageComplete(true);
		setControl(composite);
	}

	/**
	 * Returns the EClass of the selector to create.
	 *
	 * @return the selector class
	 */
	public EClass getSelectorEClass() {
		return selectorEClass;
	}

	/**
	 * Whether to select the uuid in an selector.
	 *
	 * @return whether to use uuid attribute in selector
	 */
	public Optional<Boolean> getUseUUID() {
		return Optional.ofNullable(uuidAttribute);
	}

}
