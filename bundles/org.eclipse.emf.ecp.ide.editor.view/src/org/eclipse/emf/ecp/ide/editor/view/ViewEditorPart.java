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
package org.eclipse.emf.ecp.ide.editor.view;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.ide.view.service.ViewModelEditorCallback;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The IDE ViewModel EditorPart.
 * 
 * @author Eugen Neufeld
 * 
 */
public class ViewEditorPart extends EditorPart implements
	ViewModelEditorCallback {

	private Resource resource;
	private BasicCommandStack basicCommandStack;
	private Composite parent;
	private ECPSWTView render;

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			resource.save(null);
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
		throws PartInitException {
		super.setSite(site);
		super.setInput(input);
		basicCommandStack = new BasicCommandStack();
	}

	private ResourceSet createResourceSet() {
		final ResourceSet resourceSet = new ResourceSetImpl();

		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			basicCommandStack, resourceSet);
		resourceSet.eAdapters().add(
			new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		return resourceSet;
	}

	@Override
	public boolean isDirty() {
		return basicCommandStack.isSaveNeeded();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;

		loadAndShowView();
		final VView view = (VView) resource.getContents().get(0);
		Activator.getViewModelRegistry().registerViewModelEditor(view, this);
		if (view.getRootEClass() != null) {
			Activator.getViewModelRegistry().register(view.getRootEClass().eResource().getURI().toString(), view);
		}

	}

	private void loadAndShowView() {
		try {
			final FileEditorInput fei = (FileEditorInput) getEditorInput();

			final ResourceSet resourceSet = createResourceSet();
			resource = resourceSet.getResource(
				URI.createURI(fei.getURI().toURL().toExternalForm()), true);
			resource.load(null);
			final VView view = (VView) resource.getContents().get(0);

			render = ECPSWTViewRenderer.INSTANCE
				.render(parent, view);
		} catch (final ECPRendererException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	@Override
	public void setFocus() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ide.view.service.ViewModelEditorCallback#reloadViewModel()
	 */
	public void reloadViewModel() {
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				if (render != null) {
					render.dispose();
					render.getSWTControl().dispose();
				}
				loadAndShowView();
			}
		});
	}

	@Override
	public void dispose() {
		final VView view = (VView) resource.getContents().get(0);
		Activator.getViewModelRegistry().unregisterViewModelEditor(view, this);
		super.dispose();
	}

}
