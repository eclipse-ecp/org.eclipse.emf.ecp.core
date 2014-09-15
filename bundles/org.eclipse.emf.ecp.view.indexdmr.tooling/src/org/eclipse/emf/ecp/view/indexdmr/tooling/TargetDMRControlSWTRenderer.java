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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.EMFUtils;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.view.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.editor.handler.CreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
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

	@Override
	protected void linkValue(Shell shell) {
		final Setting setting = getVElement().getDomainModelReference().getIterator().next();
		final VIndexDomainModelReference mappingDomainModelReference = VIndexDomainModelReference.class
			.cast(setting.getEObject());

		final EClass eclass = EReference.class.cast(mappingDomainModelReference.getDomainModelEFeature())
			.getEReferenceType();

		final Collection<EClass> classes = EMFUtils.getSubClasses(VViewPackage.eINSTANCE
			.getDomainModelReference());

		final CreateDomainModelReferenceWizard wizard = new CreateDomainModelReferenceWizard(
			setting, getEditingDomain(setting), eclass, "New Reference Element", //$NON-NLS-1$
			Messages.NewModelElementWizard_WizardTitle_AddModelElement,
			Messages.NewModelElementWizard_PageTitle_AddModelElement,
			Messages.NewModelElementWizard_PageDescription_AddModelElement,
			(VDomainModelReference) setting.get(true));

		final SelectionComposite<TreeViewer> helper = CompositeFactory.getSelectModelClassComposite(
			new HashSet<EPackage>(),
			new HashSet<EPackage>(), classes);
		wizard.setCompositeProvider(helper);

		final WizardDialog wd = new WizardDialog(shell, wizard);
		wd.open();

	}
}
