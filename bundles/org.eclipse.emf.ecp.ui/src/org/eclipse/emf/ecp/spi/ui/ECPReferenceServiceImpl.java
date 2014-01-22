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
package org.eclipse.emf.ecp.spi.ui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.emf.ecp.internal.wizards.SelectModelElementWizard;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.ui.common.CompositeFactory;
import org.eclipse.emf.ecp.ui.common.SelectionComposite;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

/**
 * @author Eugen Neufeld
 * 
 */
@SuppressWarnings("restriction")
public class ECPReferenceServiceImpl implements ReferenceService {

	private ViewModelContext context;
	private ECPProject ecpProject;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	public void instantiate(ViewModelContext context) {
		this.context = context;
		ecpProject = ECPUtil.getECPProjectManager().getProject(context.getDomainModel());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	public void dispose() {
		ecpProject = null;
		context = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#addModelElement(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EReference)
	 */
	public void addModelElement(EObject eObject, EReference eReference) {
		if (eReference == null) {
			// TODO needed?
			ecpProject.getContents().add(eObject);
		}
		if (eReference.isContainer()) {
			// TODO language
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",//$NON-NLS-1$
				"Operation not permitted for container references!");//$NON-NLS-1$
			return;
		}
		ECPControlHelper.addModelElementInReference(context.getDomainModel(), eObject, eReference,
			ecpProject.getEditingDomain());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#getNewElementFor(org.eclipse.emf.ecore.EReference)
	 */
	public EObject getNewElementFor(EReference eReference) {
		final Collection<EClass> classes = ECPUtil.getSubClasses(eReference.getEReferenceType());

		final SelectModelElementWizard wizard = new SelectModelElementWizard("New Reference Element",
			Messages.NewModelElementWizard_WizardTitle_AddModelElement,
			Messages.NewModelElementWizard_PageTitle_AddModelElement,
			Messages.NewModelElementWizard_PageDescription_AddModelElement);

		final SelectionComposite<TreeViewer> helper = CompositeFactory.getSelectModelClassComposite(
			new HashSet<EPackage>(),
			new HashSet<EPackage>(), classes);
		wizard.setCompositeProvider(helper);

		final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
		// wizard.setWindowTitle("New Reference Element");
		EObject newMEInstance = null;
		final int result = wd.open();

		if (result == Window.OK) {
			final Object[] selection = helper.getSelection();
			if (selection == null || selection.length == 0) {
				return null;
			}
			final EClass eClasse = (EClass) selection[0];
			// 1.create ME
			final EPackage ePackage = eClasse.getEPackage();
			newMEInstance = ePackage.getEFactoryInstance().create(eClasse);
		}
		if (newMEInstance == null) {
			return null;

		}
		return newMEInstance;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#getExistingElementFor(org.eclipse.emf.ecore.EReference)
	 */
	public EObject getExistingElementFor(EReference eReference) {
		final Iterator<EObject> allElements = ((InternalProject) ecpProject).getReferenceCandidates(
			context.getDomainModel(),
			eReference);

		final Set<EObject> elements = new HashSet<EObject>();
		while (allElements.hasNext()) {
			elements.add(allElements.next());
		}

		final SelectModelElementWizard wizard = new SelectModelElementWizard("Select Elements",
			Messages.NewModelElementWizard_WizardTitle_AddModelElement,
			Messages.ModelelementSelectionDialog_DialogTitle,
			Messages.ModelelementSelectionDialog_DialogMessage_SearchPattern, EObject.class);

		final SelectionComposite<TableViewer> tableSelectionComposite = CompositeFactory
			.getTableSelectionComposite(elements.toArray());
		wizard.setCompositeProvider(tableSelectionComposite);

		final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
		EObject eObject = null;
		final int result = wd.open();
		if (result == Window.OK) {
			final Object[] selection = tableSelectionComposite.getSelection();
			if (selection == null || selection.length == 0) {
				return null;
			}
			eObject = (EObject) selection[0];

		}
		return eObject;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#openInNewContext(org.eclipse.emf.ecore.EObject)
	 */
	public void openInNewContext(EObject eObject) {
		ECPHandlerHelper.openModelElement(eObject, ecpProject);
	}

}
