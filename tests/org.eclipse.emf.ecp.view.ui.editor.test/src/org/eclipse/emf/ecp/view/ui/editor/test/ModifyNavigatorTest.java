/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.ui.editor.test;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.ui.common.TreeViewerFactory;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBotTestCase;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author emueller
 * 
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class ModifyNavigatorTest extends SWTBotTestCase {

	private static final String PROJECTNAME = ModifyNavigatorTest.class.getName() + "Project";
	private Display display;
	private Shell shell;
	private TreeViewer viewer;
	private ECPProvider provider;
	private boolean failed;

	@Before
	public void init() {
		display = Display.getDefault();
		shell = UIThreadRunnable.syncExec(display, new Result<Shell>() {
			public Shell run() {
				final Shell shell = new Shell(display);
				shell.setLayout(new FillLayout());
				return shell;
			}
		});
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(PROJECTNAME);
		if (project != null) {
			project.delete();
		}
		provider = ECPUtil.getECPProviderRegistry().getProvider(EMFStoreProvider.NAME);
	}

	@After
	public void after() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				shell.dispose();
				shell.close();
			}
		});
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(PROJECTNAME);
		if (project != null) {
			project.delete();
		}
	}

	@Test
	public void test() throws ECPRendererException, InterruptedException {
		Realm.runWithDefault(SWTObservables.getRealm(display), new TestRunnable());
	}

	protected static Player createPlayer(String name) {
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		player.setName(name);
		return player;
	}

	private class TestRunnable implements Runnable {

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			final ECPProject[] project = new ECPProject[1];
			final Object monitor = new Object();
			try {
				project[0] = ECPUtil.getECPProjectManager().createProject(provider, PROJECTNAME);
			} catch (final ECPProjectWithNameExistsException ex) {
				fail(ex.getMessage());
			}
			UIThreadRunnable.syncExec(display, new VoidResult() {

				public void run() {
					viewer = TreeViewerFactory.createModelExplorerViewer(shell, false,
						PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator());
					shell.open();
				}
			});
			new Thread() {
				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						project[0].getContents().add(createPlayer("Player " + i));
					}
					synchronized (monitor) {
						monitor.notify();
					}
				}
			}.start();

			UIThreadRunnable.syncExec(new VoidResult() {
				public void run() {
					bot.tree().getTreeItem(PROJECTNAME).expand();
					if (project[0].getContents().size() != viewer.getTree().getItems()[0].getItems().length) {
						failed = true;
					}
				}
			});

			synchronized (monitor) {
				try {
					monitor.wait();
				} catch (final InterruptedException ex) {
					fail(ex.getMessage());
				}
			}

			UIThreadRunnable.syncExec(new VoidResult() {
				public void run() {
					shell.close();
				}
			});

			assertFalse(failed);
		}
	}
}
