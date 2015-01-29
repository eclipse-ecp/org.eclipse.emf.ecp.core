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
package org.eclipse.emf.ecp.view.keyattributedmr.tooling;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Control for a {@link org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
 * VFeaturePathDomainModelReference} that is a child of a {@link VKeyAttributeDomainModelReference}.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class FeaturePathDMRControlSWTRenderer extends
	EditableEReferenceLabelControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public FeaturePathDMRControlSWTRenderer(VControl vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void linkValue(Shell shell) {
		final Setting setting = getVElement().getDomainModelReference().getIterator().next();
		final VKeyAttributeDomainModelReference mappingDomainModelReference = VKeyAttributeDomainModelReference.class
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
