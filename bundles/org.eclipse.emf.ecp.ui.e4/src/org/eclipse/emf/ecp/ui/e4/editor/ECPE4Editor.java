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
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.e4.editor;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryLabelProvider;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.ui.UIProvider;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Editor displaying one {@link EObject}.
 * 
 * @author Jonas
 * 
 */
public class ECPE4Editor {
	/**
	 * Key to set the input of the editor into the {@link org.eclipse.e4.core.contexts.IEclipseContext}.
	 */
	public static final java.lang.String INPUT = "ecpEditorInput"; //$NON-NLS-1$
	private MPart part;
	private EObject modelElement;
	private Adapter adapter;
	private final ScrolledComposite parent;

	/**
	 * Default constructor.
	 * 
	 * @param composite the parent composite.
	 * @param shell to retrieve the display from. Used to retrieve the system colors.
	 */
	@Inject
	public ECPE4Editor(Composite composite, Shell shell) {
		parent = new ScrolledComposite(composite, SWT.V_SCROLL
			| SWT.H_SCROLL);
		parent.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
	}

	@Inject
	public void setInput(@Optional @Named(INPUT) EObject modelElement, @Optional ECPProject ecpProject, MPart part) {
		if (modelElement == null || ecpProject == null) {
			return;
		}
		this.part = part;
		this.modelElement = modelElement;
		ECPSWTView render;
		try {
			render = ECPSWTViewRenderer.INSTANCE.render(parent, modelElement);

			parent.setExpandHorizontal(true);
			parent.setExpandVertical(true);
			parent.setContent(render.getSWTControl());
			parent.setMinSize(render.getSWTControl().computeSize(SWT.DEFAULT, SWT.DEFAULT));

		} catch (final ECPRendererException ex) {
			ex.printStackTrace();
		}

		updateImageAndText();
		adapter = new AdapterImpl() {

			/*
			 * (non-Javadoc)
			 * @see
			 * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(Notification msg) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						updateImageAndText();
					}
				});
			}

		};
		modelElement.eAdapters().add(adapter);
	}

	/**
	 * removes listener.
	 */
	@PreDestroy
	void dispose() {
		modelElement.eAdapters().remove(adapter);
	}

	private void updateImageAndText() {
		part.setLabel(UIProvider.EMF_LABEL_PROVIDER.getText(modelElement));
		part.setTooltip(UIProvider.EMF_LABEL_PROVIDER.getText(modelElement));

		final AdapterFactoryLabelProvider provider = new AdapterFactoryLabelProvider(
			InternalProvider.EMF_ADAPTER_FACTORY);
		final IItemLabelProvider itemLabelProvider = (IItemLabelProvider) provider.getAdapterFactory().adapt(
			modelElement, IItemLabelProvider.class);

		final Object image = itemLabelProvider.getImage(modelElement);
		if (URI.class.isInstance(image)) {
			final URI uri = (URI) image;
			part.setIconURI(uri.toString());
		}
	}

	/**
	 * Sets the focus to the parent composite.
	 */
	@Focus
	void setFocus() {
		if (parent != null) {
			parent.setFocus();
		}
	}
}
