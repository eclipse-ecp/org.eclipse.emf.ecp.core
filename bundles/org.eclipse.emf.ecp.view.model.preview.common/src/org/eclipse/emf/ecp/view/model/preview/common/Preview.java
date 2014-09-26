/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Alexandra Buzila- initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.preview.common;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.ErrorViewPart;

/** This class renders the contents of a {@link VView} in a {@link Composite}. */
public class Preview {

	private ECPSWTView render;
	private Adapter adapter;
	private VView view;
	private boolean updateAutomatic;

	private final Composite parent;
	private Composite composite;
	private EObject dummyData;

	/**
	 * The constructor.
	 * 
	 * @param parent - the {@link Composite} in which to render
	 * */
	public Preview(Composite parent) {
		this.parent = parent;
	}

	/**
	 * Render the contents of the {@link VView}.
	 * 
	 * @param view the {@link VView}
	 * @param sampleData the sample data to be displayed in the view
	 * */
	public void render(final VView view, EObject sampleData) {
		if (adapter != null) {
			removeAdapter();
		}
		this.view = view;
		if (sampleData != null) {
			dummyData = sampleData;
		}
		internalRender(view);
	}

	/** Adds adapter to listen to changes in the currently cached view model. */
	public void registerForViewModelChanges() {
		if (view == null) {
			return;
		}
		if (adapter != null) {
			removeAdapter();
		}
		adapter = new EContentAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(Notification notification) {
				super.notifyChanged(notification);
				if (!updateAutomatic) {
					return;
				}
				if (notification.isTouch()) {
					return;
				}

				if (EStructuralFeature.class.cast(notification.getFeature()).isTransient()) {
					return;
				}

				if (VViewPackage.eINSTANCE.getDomainModelReference_ChangeListener() == notification.getFeature()) {
					return;
				}

				internalRender(view);
			}
		};
		view.eAdapters().add(adapter);
	}

	private void internalRender(VView view) {
		try {
			clear();
			final EClass myPreviewEClass = view.getRootEClass();

			if (dummyData == null || dummyData.eClass() != myPreviewEClass) {
				dummyData = EcoreUtil.create(myPreviewEClass);
				final ResourceSet resourceSet = new ResourceSetImpl();
				final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
					new ComposedAdapterFactory(new AdapterFactory[] {
						new CustomReflectiveItemProviderAdapterFactory(),
						new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) }),
					new BasicCommandStack(), resourceSet);
				resourceSet.eAdapters().add(
					new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
				final Resource resource = resourceSet.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
				resource.getContents().add(dummyData);
			}

			final ReferenceService previewRefServ = new DefaultReferenceService();
			final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view,
				dummyData, previewRefServ);
			composite = createComposite(parent);
			render = ECPSWTViewRenderer.INSTANCE.render(composite, viewModelContext);
			composite.layout();
			parent.layout();
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			displayError(e);
		} catch (final ECPRendererException ex) {
			displayError(ex);
		}

	}

	// FIXME move to e3 PreviewView
	private void displayError(Exception e) {
		clear();
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		final IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
			"Rendering failed. Please re-check your model.", e); //$NON-NLS-1$
		final ErrorViewPart part = new ErrorViewPart(status);
		part.createPartControl(parent);
	}

	/**
	 * 
	 */
	public void removeAdapter() {
		if (view == null || adapter == null) {
			return;
		}
		for (final Adapter a : view.eAdapters()) {
			if (a == adapter) {
				view.eAdapters().remove(adapter);
				adapter = null;
				return;
			}
		}
	}

	/**
	 * Removes the previous rendering result from the parent Composite.
	 */
	public void clear() {
		if (render != null) {
			render.dispose();
		}
		if (composite != null) {
			composite.dispose();
			composite = null;
		}
		if (parent.isDisposed()) {
			return;
		}
		for (final Control c : parent.getChildren()) {
			c.dispose();
		}
		// clean previous view diagnostics
		final TreeIterator<EObject> eAllContents = view.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject next = eAllContents.next();
			if (VControl.class.isInstance(next)) {
				VControl.class.cast(next).setDiagnostic(null);
				next.eClass();
			}
		}
	}

	/** Removes the cached view. */
	public void removeView() {
		view = null;
		removeAdapter();
	}

	/**
	 * Creates the composite.
	 * 
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
		composite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(composite);
		return composite;
	}

	/**
	 * @return the canAutomaticallyRender
	 */
	public boolean isUpdateAutomatic() {
		return updateAutomatic;
	}

	/**
	 * @param canAutomaticallyRender the canAutomaticallyRender to set
	 */
	public void setUpdateAutomatic(boolean canAutomaticallyRender) {
		updateAutomatic = canAutomaticallyRender;
		if (updateAutomatic && view != null) {
			internalRender(view);
			parent.layout();
		}
	}

	/**
	 * 
	 */
	public void cleanSampleData() {
		dummyData = null;
	}
}
