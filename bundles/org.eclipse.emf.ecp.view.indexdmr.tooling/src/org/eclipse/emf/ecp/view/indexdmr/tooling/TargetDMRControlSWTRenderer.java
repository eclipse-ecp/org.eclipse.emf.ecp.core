/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.indexdmr.tooling;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Control for a {@link org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
 * VFeaturePathDomainModelReference} that is a child of a {@link VIndexDomainModelReference}.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class TargetDMRControlSWTRenderer extends
	EditableEReferenceLabelControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link ReportService}
	 */
	public TargetDMRControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService factory) {
		super(vElement, viewContext, factory);
	}

	@Override
	protected void linkValue(Shell shell) {
		final EMFFormsDatabinding emfFormsDatabinding = Activator.getDefault().getEMFFormsDatabinding();
		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(getVElement().getDomainModelReference(),
				getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			showLinkValueFailedMessageDialog(shell, ex);
			return;
		}
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		final VIndexDomainModelReference mappingDomainModelReference = VIndexDomainModelReference.class
			.cast(eObject);

		EStructuralFeature feature;
		if (mappingDomainModelReference.getDomainModelEFeature() != null) {
			feature = mappingDomainModelReference.getDomainModelEFeature();
		} else if (mappingDomainModelReference.getPrefixDMR() != null) {
			try {
				feature = (EStructuralFeature) emfFormsDatabinding
					.getValueProperty(mappingDomainModelReference.getPrefixDMR(), null).getValueType();
			} catch (final DatabindingFailedException ex) {
				showLinkValueFailedMessageDialog(shell, ex);
				return;
			}
		} else {
			showLinkValueFailedMessageDialog(shell, new IllegalStateException(
				"The provided IndexDomainModelReference doesn't have the prefix nor the feature set.")); //$NON-NLS-1$
			return;
		}

		final EClass eclass = EReference.class.cast(feature)
			.getEReferenceType();

		final Collection<EClass> classes = EMFUtils.getSubClasses(VViewPackage.eINSTANCE
			.getDomainModelReference());

		final CreateDomainModelReferenceWizard wizard = new CreateDomainModelReferenceWizard(
			eObject, structuralFeature, getEditingDomain(eObject), eclass, "New Reference Element", //$NON-NLS-1$
			Messages.NewModelElementWizard_WizardTitle_AddModelElement,
			Messages.NewModelElementWizard_PageTitle_AddModelElement,
			Messages.NewModelElementWizard_PageDescription_AddModelElement,
			(VDomainModelReference) eObject.eGet(structuralFeature, true));

		final SelectionComposite<TreeViewer> helper = CompositeFactory.getSelectModelClassComposite(
			new HashSet<EPackage>(),
			new HashSet<EPackage>(), classes);
		wizard.setCompositeProvider(helper);

		final WizardDialog wd = new WizardDialog(shell, wizard);
		wd.open();

	}
}
