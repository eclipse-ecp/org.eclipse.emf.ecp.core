/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Lucas Koehler - adapted to segments
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.controls;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateSegmentDmrWizard;
import org.eclipse.emf.ecp.view.internal.editor.handler.FeatureSegmentGenerator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Control for setting a segment based DomainModelReference in the DomainModelReferenceSelector.
 *
 * @author Eugen Neufeld
 * @author Lucas Koehler
 *
 */
public class DmrSelectorSegmentDmrControlSWTRenderer extends EditableEReferenceLabelControlSWTRenderer {

	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private final EMFFormsDatabindingEMF databindingEMF;
	private ComposedAdapterFactory composedAdapterFactory;

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param databindingEMF the {@link EMFFormsDatabindingEMF}
	 */
	@Inject
	public DmrSelectorSegmentDmrControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabindingEMF databindingEMF) {
		super(vElement, viewContext, reportService);
		this.databindingEMF = databindingEMF;
	}

	@Override
	protected Control createSWTControl(Composite parent) throws DatabindingFailedException {
		final Control control = super.createSWTControl(parent);

		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		return control;
	}

	@Override
	protected void linkValue(Shell shell) {
		final CreateSegmentDmrWizard dmrWizard = new CreateSegmentDmrWizard("Create Selector Domain Model Reference", //$NON-NLS-1$
			sf -> null, new FeatureSegmentGenerator(), null, false);
		final WizardDialog wd = new WizardDialog(shell, dmrWizard);
		wd.setBlockOnOpen(true);
		final int open = wd.open();
		if (Window.CANCEL == open) {
			return;
		}

		IObservableValue<?> observableValue;
		try {
			observableValue = getModelValue();
		} catch (final DatabindingFailedException ex) {
			showLinkValueFailedMessageDialog(shell, ex);
			return;
		}
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		final VDomainModelReference dmr = dmrWizard.getDomainModelReference().get();

		EClass rootEClass = dmrWizard.getRootEClass().get();

		/*
		 * If the ecore of the selected root EClass is loaded from the workspace, load and register the model, add it
		 * to the template's ecore paths, and resolve the root EClass in the registered version of the EPackage. The
		 * latter is necessary so that the EClass is properly referenced in the template model.
		 */
		final Optional<IFile> selectedEcore = dmrWizard.getSelectedEcore();
		if (selectedEcore.isPresent()) {
			final String ecorePath = selectedEcore.get().getFullPath().toString();
			try {
				EcoreHelper.registerEcore(ecorePath);
				addEcorePathToTemplate(ecorePath);
				final String nsUri = rootEClass.getEPackage().getNsURI();
				final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsUri);
				rootEClass = (EClass) ePackage.getEClassifier(rootEClass.getName());
			} catch (final IOException ex) {
				getReportService().report(new AbstractReport(ex,
					String.format("Could not add ecore path \"%s\" to the view template.", ecorePath))); //$NON-NLS-1$
			}
		}

		// Wrap setting the root EClass and the dmr in commands and execute
		setDmrAndRootEClass(eObject, dmr, rootEClass);
	}

	/**
	 * Package visible to allow unit testing.
	 *
	 * @param eObject The {@link VTDomainModelReferenceSelector} to set the dmr and root EClass in.
	 * @param dmr The {@link VDomainModelReference} to set
	 * @param rootEClass The root {@link EClass} to set
	 */
	/* private */void setDmrAndRootEClass(final EObject eObject, final VDomainModelReference dmr,
		final EClass rootEClass) {
		final List<Command> commands = new LinkedList<>();
		final EditingDomain editingDomain = getEditingDomain(eObject);
		final EReference rootEClassFeature = VTDomainmodelreferencePackage.Literals.DOMAIN_MODEL_REFERENCE_SELECTOR__ROOT_ECLASS;
		if (eObject.eClass().getEAllReferences().contains(rootEClassFeature)) {
			commands.add(SetCommand.create(editingDomain, eObject, rootEClassFeature, rootEClass));
		}
		final EReference dmrFeature = VTDomainmodelreferencePackage.Literals.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE;
		commands.add(SetCommand.create(editingDomain, eObject, dmrFeature, dmr));
		final CompoundCommand compoundCommand = new CompoundCommand(commands);
		editingDomain.getCommandStack().execute(compoundCommand);
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
		final EClass rootEClass = selector.getRootEClass();
		final EList<VDomainModelReferenceSegment> segments = modelReference.getSegments();

		String attributeType = null;
		try {
			final IEMFValueProperty valueProperty = databindingEMF.getValueProperty(modelReference, rootEClass);
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

	@Override
	public void dispose() {
		super.dispose();
		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
		}
	}

}
