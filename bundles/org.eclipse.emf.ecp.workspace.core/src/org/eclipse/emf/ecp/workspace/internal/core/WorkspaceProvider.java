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

/*
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.ecp.workspace.internal.core;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPModelContextAdapter;
import org.eclipse.emf.ecp.spi.core.DefaultProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author Eike Stepper
 * @author Tobias Verhoeven
 */
public class WorkspaceProvider extends DefaultProvider {

	/** The Provider Name. */
	public static final String NAME = "org.eclipse.emf.ecp.workspace.provider"; //$NON-NLS-1$

	/** Root URI Property Name. */
	public static final String PROP_ROOT_URI = "rootURI"; //$NON-NLS-1$

	/** Constant which is used to indicated the the {@link #PROP_ROOT_URI root uri} is not existing yet. */
	public static final String VIRTUAL_ROOT_URI = "VIRTUAL_URI"; //$NON-NLS-1$

	/**
	 * The Workspace Provider Instance.
	 *
	 * @deprecated use ECPUtil.getECPProviderRegistry().getProvider(WorkspaceProvider.NAME) instead
	 *
	 */
	@Deprecated
	static WorkspaceProvider INSTANCE;

	/**
	 * Instantiates a new workspace provider.
	 */
	public WorkspaceProvider() {
		super(NAME);
		INSTANCE = this;
		// WORKSPACE.addResourceChangeListener(this);
	}

	/** {@inheritDoc} */
	@Override
	public void handleLifecycle(ECPContainer context, LifecycleEvent event) {
		switch (event) {
		case INIT:
			handleInit(context);
			break;
		case DISPOSE:
			handelDispose(context);
			break;
		case CREATE:
			handleCreate(context);
			break;
		case REMOVE:
			handleRemove(context);
			break;
		default:
			break;
		}
	}

	private void handleInit(ECPContainer context) {
		if (context instanceof InternalProject) {
			final InternalProject project = (InternalProject) context;
			final EditingDomain editingDomain = project.getEditingDomain();
			editingDomain.getResourceSet().eAdapters().add(new WorkspaceProjectObserver(project, this));
		}

	}

	private void handleRemove(ECPContainer context) {
		// TODO Auto-generated method stub

	}

	private void handleCreate(ECPContainer context) {
		// TODO Auto-generated method stub

	}

	private void handelDispose(ECPContainer context) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doDispose() {
		try {
			// WORKSPACE.removeResourceChangeListener(this);
			super.doDispose();
		} finally {
			INSTANCE = null;
		}
	}

	@Override
	public boolean hasCreateRepositorySupport() {
		return false;
	}

	@Override
	public void fillChildren(ECPContainer context, Object parent, InternalChildrenList childrenList) {
		if (parent instanceof ECPRepository) {
		} else if (parent instanceof ECPProject) {
			final ECPProject project = (ECPProject) parent;
			final String rootURI = project.getProperties().getValue(PROP_ROOT_URI);

			final ResourceSet resourceSet = project.getEditingDomain().getResourceSet();

			final URI uri = URI.createURI(rootURI);
			if (uri.hasFragment()) {
				final EObject eObject = resourceSet.getEObject(uri, true);
				super.fillChildren(context, eObject, childrenList);
			} else {
				final Resource resource = resourceSet.getResource(uri, true);
				childrenList.addChildren(resource.getContents());
			}

		} else {
			super.fillChildren(context, parent, childrenList);
		}
	}

	/** {@inheritDoc} */
	@Override
	public EList<? extends Object> getElements(InternalProject project) {
		boolean demandLoad = true;
		if (project.getProperties().getValue(PROP_ROOT_URI).equals(VIRTUAL_ROOT_URI)) {
			demandLoad = false;
		}
		final ResourceSet resourceSet = project.getEditingDomain().getResourceSet();
		return resourceSet.getResource(
			URI.createURI(project.getProperties().getValue(PROP_ROOT_URI)), demandLoad).getContents();
		// TODO: implement WorkspaceProvider.addRootElement(project, rootElement)
	}

	@Override
	public boolean contains(InternalProject project, Object object) {
		// TODO: optimize
		if (object instanceof EObject) {
			final EObject eObject = (EObject) object;
			final EObject root = EcoreUtil.getRootContainer(eObject);
			if (root == null || root.eResource() == null) {
				return false;
			}

			return root.eResource().equals(getRoot(project));
		}
		return false;
	}

	/**
	 * Reloads the project.
	 *
	 * @param project the project to be reloaded.
	 */
	public void reload(InternalProject project) {
		final List<Resource> resources = project.getEditingDomain().getResourceSet().getResources();
		for (final Resource resource : resources) {
			if (resource.equals(getRoot(project)) && resource.isLoaded()) {
				resource.unload();

				try {
					resource.load(Collections.EMPTY_MAP);
				} catch (final IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void cloneProject(final InternalProject projectToClone, InternalProject targetProject) {
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	// FIXME
	@Override
	public Notifier getRoot(InternalProject project) {
		boolean demandLoad = true;
		if (project.getProperties().getValue(PROP_ROOT_URI).equals(VIRTUAL_ROOT_URI)) {
			demandLoad = false;
		}
		return project.getEditingDomain().getResourceSet()
			.getResource(URI.createURI(project.getProperties().getValue(PROP_ROOT_URI)), demandLoad);
	}

	@Override
	public void doSave(InternalProject project) {
		try {
			final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
			saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
			final List<Resource> resources = project.getEditingDomain().getResourceSet().getResources();
			for (final Resource resource : resources) {
				resource.save(saveOptions);
			}
			((BasicCommandStack) project.getEditingDomain().getCommandStack()).saveIsDone();
		} catch (final IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		super.doSave(project);
	}

	@Override
	public boolean isDirty(InternalProject project) {
		return ((BasicCommandStack) project.getEditingDomain().getCommandStack()).isSaveNeeded();
	}

	@Override
	public EditingDomain createEditingDomain(final InternalProject project) {

		final CommandStack commandStack = new BasicCommandStack();
		final EditingDomain editingDomain = new AdapterFactoryEditingDomain(InternalProvider.EMF_ADAPTER_FACTORY,
			commandStack);

		editingDomain.getResourceSet().eAdapters().add(new ECPModelContextAdapter(project));
		final URI uri = URI.createURI(project.getProperties().getValue(PROP_ROOT_URI));
		if (project.getProperties().getValue(PROP_ROOT_URI).equals(VIRTUAL_ROOT_URI)) {
			editingDomain.getResourceSet().createResource(uri);
		} else {
			try {
				editingDomain.getResourceSet().getResource(uri, true);
			} catch (final WrappedException we) {
				project.close();
			}
		}

		return editingDomain;
	}

	@Override
	public ECPContainer getModelContext(Object element) {
		return super.getModelContext(element);
	}

	@Override
	public boolean hasCreateProjectWithoutRepositorySupport() {
		return true;
	}

	/**
	 * Observes changes in a projects resource and notifies the project.
	 */
	private class WorkspaceProjectObserver extends EContentAdapter {

		private final InternalProject project;
		private final WorkspaceProvider provider;

		public WorkspaceProjectObserver(InternalProject project, WorkspaceProvider provider) {
			this.project = project;
			this.provider = provider;
		}

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);

			if (notification.getNotifier() instanceof EObject) {
				provider.notifyProviderChangeListeners(notification);
				final EObject eObject = (EObject) notification.getNotifier();
				project.notifyObjectsChanged((Collection) Collections.singleton(eObject), false);

				final Object feature = notification.getFeature();
				if (feature instanceof EReference) {
					final EReference eReference = (EReference) feature;

					if (eReference.isContainment() && notification.getNewValue() instanceof EObject) {
						project.notifyObjectsChanged(Collections.singleton(notification.getNewValue()), true);
					}

				}

			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.core.InternalProvider#isThreadSafe()
	 */
	@Override
	public boolean isThreadSafe() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.core.DefaultProvider#doDelete(org.eclipse.emf.ecp.spi.core.InternalProject,
	 *      java.util.Collection)
	 */
	@Override
	public void doDelete(InternalProject project, final Collection<Object> objects) {
		final Command deleteCommand = DeleteCommand.create(project.getEditingDomain(), objects);
		if (deleteCommand.canExecute()) {
			project.getEditingDomain().getCommandStack().execute(deleteCommand);
			return;
		}

		/*
		 * the default DeleteCommand cannot be executed for whatever reason.
		 * Wrap an EcoreUtil.delete in a change command for undo support.
		 */
		final Command changeCommand = new ChangeCommand(project.getEditingDomain().getResourceSet()) {
			@Override
			protected void doExecute() {
				for (final Object object : objects) {
					final Object unwrap = AdapterFactoryEditingDomain.unwrap(object);
					if (!EObject.class.isInstance(unwrap)) {
						continue;
					}
					EcoreUtil.delete(EObject.class.cast(unwrap), true);
				}
			}
		};
		if (changeCommand.canExecute()) {
			project.getEditingDomain().getCommandStack().execute(changeCommand);
			return;
		}

		// unexpected
		throw new IllegalStateException("Delete was not successful."); //$NON-NLS-1$

	}
}
