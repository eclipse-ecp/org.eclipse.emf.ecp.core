/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.editor.handler;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.internal.wizards.SelectModelElementWizard;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.DummyReferenceService;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Alexandra Buzila
 *
 */
@SuppressWarnings("restriction")
public class CreateDomainModelReferenceWizard extends SelectModelElementWizard {

	private CustomizeDomainModelReferencePage customizeDMRPage;
	private WizardPageExtension firstPage;
	private final EClass eclass;
	private final EditingDomain editingDomain;
	private final Setting setting;
	private final VDomainModelReference domainModelReference;

	/**
	 * A wizard used for creating a new DomainModelReference.
	 *
	 * @param setting - the setting to use
	 * @param editingDomain - the setting's editing domain
	 * @param eclass - the root EClass of the VView the setting belongs to
	 * @param windowTitle - title for the wizard window
	 * @param pageName - the name of the page
	 * @param pageTitle - the title of the page
	 * @param description - the description
	 * @param domainModelReference - the domain model reference
	 */
	public CreateDomainModelReferenceWizard(final Setting setting, final EditingDomain editingDomain,
		final EClass eclass, final String windowTitle,
		final String pageName, String pageTitle, String description, VDomainModelReference domainModelReference) {
		super(windowTitle, pageName, pageTitle, description);
		this.setting = setting;
		this.editingDomain = editingDomain;
		this.eclass = eclass;
		this.domainModelReference = domainModelReference;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public void addPages() {

		customizeDMRPage = new CustomizeDomainModelReferencePage(
			"New Domain Model Reference", "Select an EStructuralFeature", "Select a domain model EStructuralFeature for the domain model reference.", getDummyControl()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		if (domainModelReference == null) {
			firstPage = new WizardPageExtension(pageName);
			firstPage.setTitle(pageTitle);
			firstPage.setDescription(description);
			addPage(firstPage);
		} else {
			customizeDMRPage.setEClass(domainModelReference.eClass());
		}

		addPage(customizeDMRPage);
	}

	/**
	 * @return
	 */
	private VControl getDummyControl() {
		final VView domainModelView = VViewFactory.eINSTANCE.createView();
		domainModelView.setRootEClass(eclass);
		final VControl control = VViewFactory.eINSTANCE.createControl();
		domainModelView.getChildren().add(control);
		addDomainToResource(domainModelView);

		return control;
	}

	/**
	 * @param domainModelView
	 */
	private void addDomainToResource(VView domainModelView) {
		final ResourceSet rs = new ResourceSetImpl();
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
		resource.getContents().add(domainModelView);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == firstPage) {
			final Object[] selection = getCompositeProvider().getSelection();
			if (selection == null || selection.length == 0) {
				return null;
			}
			final EClass eClass = (EClass) selection[0];
			customizeDMRPage.setEClass(eClass);
			return customizeDMRPage;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.internal.wizards.SelectModelElementWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		Command command = null;
		if (setting.getEStructuralFeature().isMany()) {
			command = AddCommand.create(editingDomain, setting.getEObject(),
				setting.getEStructuralFeature(), customizeDMRPage.getvControl().getDomainModelReference());
		}
		else {
			command = SetCommand.create(editingDomain, setting.getEObject(),
				setting.getEStructuralFeature(), customizeDMRPage.getvControl().getDomainModelReference());
		}
		editingDomain.getCommandStack().execute(command);
		return super.performFinish();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		if (customizeDMRPage == null) {
			return false;
		}
		if (customizeDMRPage.getvControl() == null) {
			return false;
		}
		if (customizeDMRPage.getvControl().getDomainModelReference() == null) {
			return false;
		}
		if (customizeDMRPage.getvControl().getDomainModelReference().getEStructuralFeatureIterator() == null) {
			return false;
		}
		return super.canFinish();
	}

	/** Wizard page containing the control for setting a DomainModelReference. */
	private class CustomizeDomainModelReferencePage extends WizardPage {

		private EClass dmrEClass;
		private Composite composite;
		private final VControl vControl;

		/**
		 * @param pageName
		 * @param domainModelEClass
		 */
		protected CustomizeDomainModelReferencePage(String pageName, String pageTitle, String pageDescription,
			VControl vControl) {
			super(pageName);
			setTitle(pageTitle);
			setDescription(pageDescription);
			this.vControl = vControl;
		}

		public void setEClass(EClass dmrEClass) {
			this.dmrEClass = dmrEClass;
			if (isControlCreated()) {
				render();
			}
		}

		/**
		 *
		 */
		private void render() {
			clear();

			final VDomainModelReference dmr = (VDomainModelReference) EcoreUtil.create(dmrEClass);
			getvControl().setDomainModelReference(dmr);
			final VView view = ViewProviderHelper.getView(dmr);
			final ViewModelContext viewContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(view, dmr, new DummyReferenceService());
			try {
				ECPSWTViewRenderer.INSTANCE.render(composite, viewContext);
				composite.layout();
			} catch (final ECPRendererException ex) {
				ex.printStackTrace();
			}
		}

		/**
		 *
		 */
		private void clear() {
			if (composite != null && !composite.isDisposed()) {
				for (final Control c : composite.getChildren()) {
					c.dispose();
				}
			}

		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public void createControl(Composite parent) {
			composite = new Composite(parent, SWT.FILL);
			GridLayoutFactory.fillDefaults().applyTo(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(composite);
			setControl(composite);

			if (dmrEClass != null) {
				render();
			}

		}

		/**
		 * @return the vControl
		 */
		public VControl getvControl() {
			return vControl;
		}

	}

}
