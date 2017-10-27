/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Lucas Koehler - adapted to segments
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.controls;

import java.io.IOException;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.internal.tooling.util.DMRCreationWizard;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Control for setting the DomainModelReference in the DomainModelReferenceSelector.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class DMRSelectorControlSWTRenderer extends EditableEReferenceLabelControlSWTRenderer {

	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	public DMRSelectorControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createSWTControl(Composite parent) throws DatabindingFailedException {
		final Control control = super.createSWTControl(parent);

		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		return control;
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

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		featureSegment.setDomainModelFeature(featureToSet.getName());
		dmr.getSegments().add(featureSegment);

		final EditingDomain editingDomain = getEditingDomain(eObject);
		final EReference rootEClassFeature = VTDomainmodelreferencePackage.eINSTANCE
			.getDomainModelReferenceSelector_DmrRootEClass();
		if (eObject.eClass().getEAllReferences().contains(rootEClassFeature)) {
			final Command rootEClassCommand = SetCommand.create(editingDomain, eObject, rootEClassFeature,
				featureToSet.eContainer());
			editingDomain.getCommandStack().execute(rootEClassCommand);
		}

		final Command dmrCommand = SetCommand.create(editingDomain, eObject, structuralFeature,
			dmr);
		editingDomain.getCommandStack().execute(dmrCommand);
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

	@Override
	protected Object getText(Object value) {
		final VDomainModelReference modelReference = (VDomainModelReference) value;
		if (modelReference == null || modelReference.getSegments().isEmpty()) {
			return null;
		}
		final VTDomainModelReferenceSelector selector = VTDomainModelReferenceSelector.class
			.cast(getViewModelContext().getDomainModel());
		final EClass rootEClass = selector.getDmrRootEClass();
		final EList<VDomainModelReferenceSegment> segments = modelReference.getSegments();

		String attributeType = null;
		try {
			final IEMFValueProperty valueProperty = Activator.getDefault().getEMFFormsDatabinding().getValueProperty(
				modelReference, rootEClass);
			attributeType = valueProperty.getStructuralFeature().getEType().getName();
		} catch (final DatabindingFailedException ex) {
			// TODO handle?
		}

		String attributeName = " -> " + adapterFactoryItemDelegator.getText(segments.get(segments.size() - 1)); //$NON-NLS-1$
		if (attributeType != null && !attributeType.isEmpty()) {
			attributeName += " : " + attributeType; //$NON-NLS-1$
		}
		String referencePath = ""; //$NON-NLS-1$

		for (int i = 0; i < segments.size() - 1; i++) {
			referencePath = referencePath + " -> " //$NON-NLS-1$
				+ adapterFactoryItemDelegator.getText(segments.get(i));
		}

		final String linkText = rootEClass.getName() + referencePath + attributeName;
		if (linkText.equals(" -> ")) { //$NON-NLS-1$
			return null;
		}
		return linkText;
	}
}
