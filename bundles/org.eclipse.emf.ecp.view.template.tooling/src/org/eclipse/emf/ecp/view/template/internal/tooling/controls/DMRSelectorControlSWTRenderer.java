/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.controls;

import java.io.IOException;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.internal.tooling.util.DMRCreationWizard;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Control for setting the DomainModelReference in the DomainModelReferenceSelector.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class DMRSelectorControlSWTRenderer extends EditableEReferenceLabelControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	public DMRSelectorControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	@Override
	protected void linkValue(Shell shell) {
		final DMRCreationWizard dmrWizard = new DMRCreationWizard();
		final WizardDialog wd = new WizardDialog(shell, dmrWizard);
		final int open = wd.open();
		if (Window.CANCEL == open) {
			return;
		}

		final IFile selectedEcore = dmrWizard.getSelectedEcore();
		EStructuralFeature featureToSet = dmrWizard.getSelectedEStructuralFeature();
		if (selectedEcore != null) {
			try {
				final String ecorePath = selectedEcore.getFullPath().toString();
				EcoreHelper.registerEcore(ecorePath);
				addEcorePathToTemplate(ecorePath);
				final EPackage ePackage = (EPackage) featureToSet.eResource().getContents().get(0);
				final EPackage registeredPackage = (EPackage) EPackage.Registry.INSTANCE.get(ePackage.getNsURI());
				final EClass eClass = (EClass) registeredPackage.getEClassifier(featureToSet.getEContainingClass()
					.getName());
				featureToSet = eClass.getEStructuralFeature(featureToSet.getFeatureID());
			} catch (final IOException ex) {
				Activator.log(ex);
			}
		}

		IObservableValue observableValue;
		try {
			observableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			showLinkValueFailedMessageDialog(shell, ex);
			return;
		}
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		final VFeaturePathDomainModelReference value = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		value.setDomainModelEFeature(featureToSet);

		final EditingDomain editingDomain = getEditingDomain(eObject);
		final Command command = SetCommand.create(editingDomain, eObject, structuralFeature,
			value);
		editingDomain.getCommandStack().execute(command);

	}

	/**
	 * @param ecorePath
	 */
	private void addEcorePathToTemplate(String ecorePath) {
		EObject domain = getViewModelContext().getDomainModel();
		while (!VTViewTemplate.class.isInstance(domain)) {
			domain = domain.eContainer();
		}
		if (!VTViewTemplate.class.isInstance(domain)) {
			return;
		}
		VTViewTemplate.class.cast(domain).getReferencedEcores().add(ecorePath);
	}
}
