/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eclipse Modeling Project - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.application.e4.fx;

import java.net.URL;

import javafx.scene.layout.BorderPane;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.model.fx.ECPFXView;
import org.eclipse.emf.ecp.view.model.fx.ECPFXViewRenderer;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.fx.util.EmfStoreUtil;

/**
 * @author Eugen Neufeld
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class EditorPart {
	/**
	 * Key to set the input of the editor into the {@link org.eclipse.e4.core.contexts.IEclipseContext}.
	 */
	public static final String INPUT = "ecpEditorInput"; //$NON-NLS-1$
	private static final String MODEL_ELEMENT_ID_PERSIST_KEY = "modelElementId"; //$NON-NLS-1$
	private static final String PROJECT_ID_PERSIST_KEY = "projectId"; //$NON-NLS-1$
	private final MPart part;
	private final BorderPane parent;
	private EObject modelElement;
	private Adapter adapter;

	@Inject
	public EditorPart(BorderPane parent, MPart mPart) {
		this.parent = parent;
		part = mPart;
		if (part.getContext().get(INPUT) == null) {
			restorePersistedState();
			render();
		}
	}

	@PostConstruct
	public void postConstruct() {

	}

	@Inject
	void setSelection(
		@Optional @Named(INPUT) EObject person) {

		if (person != null) {
			modelElement = person;
			adapter = new AdapterImpl() {
				/*
				 * (non-Javadoc)
				 * @see
				 * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification
				 * )
				 */
				@Override
				public void notifyChanged(Notification msg) {
					// TODO Asynchronous execution?
					updateImageAndText();
				}
			};
			modelElement.eAdapters().add(adapter);
			persistState();
			render();
		}
	}

	/**
	 * Renders the content of this editor part.
	 * Note: The modelElement must be set in order to render something.
	 */
	private void render() {
		if (modelElement != null) {

			final ReferenceServiceFX referenceService = new ReferenceServiceFX();
			final ECPFXView ecpfxView = ECPFXViewRenderer.INSTANCE.render(
				modelElement, referenceService);
			parent.setCenter(ecpfxView.getFXNode());
			updateImageAndText();
		}
	}

	private void restorePersistedState() {
		final String projectId = part.getPersistedState().get(PROJECT_ID_PERSIST_KEY);
		final ESLocalProject project = EmfStoreUtil.getLocalProjectForId(projectId);
		if (project != null) {
			final String modelElementId = part.getPersistedState().get(MODEL_ELEMENT_ID_PERSIST_KEY);
			modelElement = EmfStoreUtil.getModelObjectForId(project, modelElementId);
			if (modelElement != null) {
				part.getContext().set(INPUT, modelElement);
			}
		}
	}

	/**
	 * removes listener.
	 */
	@PreDestroy
	void dispose() {
		if (modelElement != null) {
			modelElement.eAdapters().remove(adapter);
		}
	}

	/**
	 * Persists the IDs of the model object and the local emf store project before this part is destroyed.
	 * This allows to correctly recreated this part on the next startup of the Editor.
	 */
	// @PersistState
	void persistState() {
		final ESLocalProject project = ESWorkspaceProvider.INSTANCE.getWorkspace().getLocalProject(modelElement);
		final String modelElementId = project.getModelElementId(modelElement).getId();
		final String projectId = project.getLocalProjectId().getId();
		part.getPersistedState().put(MODEL_ELEMENT_ID_PERSIST_KEY, modelElementId);
		part.getPersistedState().put(PROJECT_ID_PERSIST_KEY, projectId);
	}

	private void updateImageAndText() {
		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final IItemLabelProvider itemLabelProvider = (IItemLabelProvider) composedAdapterFactory.adapt(
			modelElement, IItemLabelProvider.class);
		composedAdapterFactory.dispose();

		part.setLabel(itemLabelProvider.getText(modelElement));
		part.setTooltip(itemLabelProvider.getText(modelElement));

		Object image = itemLabelProvider.getImage(modelElement);
		String iconUri = null;
		if (ComposedImage.class.isInstance(image)) {
			final ComposedImage composedImage = (ComposedImage) image;
			image = composedImage.getImages().get(0);
		}
		if (URI.class.isInstance(image)) {
			final URI uri = (URI) image;
			iconUri = uri.toString();
		}
		if (URL.class.isInstance(image)) {
			final URL uri = (URL) image;
			iconUri = uri.toString();
		}

		part.setIconURI(iconUri);
	}

	@Focus
	public void onFocus() {
		// TODO Your code here
	}

}