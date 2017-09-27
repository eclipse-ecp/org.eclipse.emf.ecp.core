/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Neil Mackenzie - initial implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core.rap.concurrent.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.rap.sessionprovider.concurrent.test.MockConcurrentSessionProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.ECPEMFUtils;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.junit.Test;

import junit.framework.TestCase;

/**
 *
 * @author neil
 *         Tests concurrent Access to EMFStorePRovider.
 */
@SuppressWarnings("restriction")
public class RAPConcurrentAccess_PTest extends TestCase {

	/**
	 * counter used ito help make unique project names
	 */
	private static int testCounter;
	/**
	 * number of concurrent threads to run
	 */
	private final int numberOfThreads = 5;

	/**
	 * class which opens a creates a project, loads an XMI file with some tasks
	 * from the makeithappen model, and then reads the contents of th project.
	 *
	 * @author neil
	 *
	 */
	public class TestRun implements Runnable {
		private final int testcount;

		/**
		 * Constructor.
		 *
		 * @param counter
		 */
		public TestRun(int counter) {
			testcount = counter;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			final String fileName = "Task.xmi"; //$NON-NLS-1$

			final ECPProjectManager projectManager = ECPUtil.getECPProjectManager();
			ECPProject project1 = null;
			// perhaps delete project if It exists??
			try {
				project1 = projectManager.createProject(new EMFStoreProvider(),
					"concurrentTestProject" + testcount); //$NON-NLS-1$
			} catch (final ECPProjectWithNameExistsException ex) {
				ex.printStackTrace();
			}
			final Object parentObject = project1;
			final URI fileURI = URI.createFileURI(fileName);

			// create resource set and resource
			final ResourceSet resourceSet = new ResourceSetImpl();

			final Resource resource = resourceSet.getResource(fileURI, true);

			final EObject eObjectImport = resource.getContents().get(0);

			// add the items from the model to the project
			if (parentObject instanceof EObject) {

				for (final EReference ref : ((EObject) parentObject).eClass().getEAllContainments()) {
					if (ref.getEReferenceType().isInstance(eObjectImport)) {
						final EditingDomain editingDomain = AdapterFactoryEditingDomain
							.getEditingDomainFor(parentObject);
						if (ref.isMany()) {
							editingDomain.getCommandStack().execute(
								new AddCommand(editingDomain, (EObject) parentObject, ref, EcoreUtil
									.copy(eObjectImport)));
						} else {
							editingDomain.getCommandStack().execute(
								new SetCommand(editingDomain, (EObject) parentObject, ref, EcoreUtil
									.copy(eObjectImport)));
						}

						break;
					}
				}
			} else if (parentObject instanceof ECPProject) {
				final EditingDomain editingDomain = ((ECPProject) parentObject).getEditingDomain();
				editingDomain.getCommandStack().execute(new ChangeCommand(eObjectImport) {

					@Override
					protected void doExecute() {
						((ECPProject) parentObject).getContents().add(EcoreUtil.copy(eObjectImport));
					}
				});
			}

			final EList<Object> list = project1.getContents();

			final EObject root = (EObject) list.get(0);
			final TreeIterator<EObject> iter = EcoreUtil.getAllContents(root, true);

			// output the contents of the project.
			while (iter.hasNext()) {
				System.out.println(iter.next().toString());
			}

		}

	}

	/**
	 * Tests concurrent Access to EMFStorePRovider.
	 */
	@Test
	public final void testConcurrentAccess() {

		MockConcurrentSessionProvider.getInstance();
		// Initialisation of the EMFStore workspace is not currently threadsafe in
		// EMFStore client in EMFSTore 1.8, so we initialize it first in this thread.

		ECPEMFUtils.getESWorkspaceProviderInstance();

		final Collection<Thread> collection = new ArrayList<Thread>();

		// because we are using a service factory for the RAP implementation we must unget
		// the service so that the service factory is called again on each call. otherwise
		// the service factor will keep returning the same cached instance as the reference
		// count of the service will remain greater than zero

		for (int i = 0; i < numberOfThreads; i++) {
			final Runnable runnable = getNewTest();
			final Thread t = new Thread(runnable);
			t.setName("ECPConcurrentTestThread" + i); //$NON-NLS-1$
			// add the thread to the collection so that we can later
			// wait for all these threads to finish.
			collection.add(t);
			t.start();
		}
		// we wait for all the started threads to finish
		// this is because we don't want the main testing thread to finish
		// until the newly started threads have finished. It the main test
		// thread finished first then the other started threads throw errors
		// that some bundles appear to be not resolved.

		final Iterator<Thread> iter = collection.iterator();

		while (iter.hasNext()) {
			final Thread thread = iter.next();
			try {
				thread.join();
			} catch (final InterruptedException ex) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * create a test to run in a thread.
	 *
	 * @return a Runnable test
	 */
	private Runnable getNewTest() {
		testCounter++;
		return new TestRun(testCounter);

	}
}
