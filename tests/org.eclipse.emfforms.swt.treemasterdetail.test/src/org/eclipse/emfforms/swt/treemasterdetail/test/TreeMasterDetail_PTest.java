/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.swt.treemasterdetail.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.spi.common.callback.ViewModelPropertiesUpdateCallback;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.EMFFormsViewService;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;
import org.eclipse.emf.ecp.view.test.common.swt.SWTTestUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.emfstore.bowling.Area;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emfforms.spi.swt.treemasterdetail.DetailCompositeBuilder;
import org.eclipse.emfforms.spi.swt.treemasterdetail.InitialSelectionProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailCache;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailSWTFactory;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

@SuppressWarnings("restriction")
@RunWith(SWTBotJunit4ClassRunner.class)
public class TreeMasterDetail_PTest {

	private static final String ALICE = "Alice"; //$NON-NLS-1$
	private static final String BOB = "Bob"; //$NON-NLS-1$
	private static final String LEAGUE = "Liga"; //$NON-NLS-1$

	private Shell shell;
	private Display display;
	private SWTBot bot;
	private DefaultRealm realm;

	private TreeMasterDetailComposite composite;

	@Before
	public void setUp() {
		realm = new DefaultRealm();
		display = Display.getDefault();
		shell = UIThreadRunnable.syncExec(display, new Result<Shell>() {
			@Override
			public Shell run() {
				final Shell shell = new Shell(display);
				GridDataFactory.fillDefaults().applyTo(shell);
				return shell;
			}
		});
		bot = new SWTBot(shell);
	}

	private Resource createResource() {
		final Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
		final Map<String, Object> extToFactoryMap = registry
			.getExtensionToFactoryMap();
		extToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
			new ResourceFactoryImpl());
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI,
			EcorePackage.eINSTANCE);

		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), resourceSet);
		resourceSet.eAdapters().add(
			new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = resourceSet
			.createResource(URI.createURI("VIRTUAL")); //$NON-NLS-1$
		return resource;

	}

	@After
	public void after() {
		realm.dispose();
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (composite != null) {
					composite.dispose();
					composite = null;
				}
				shell.close();
				shell.dispose();
			}
		});
	}

	@Test
	public void renderInitialEObject() {
		// arrange
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);

		final Object input = league;

		// act
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory.createTreeMasterDetail(
					shell,
					SWT.NONE,
					input);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		// assert, league hidden, no selection
		assertEquals(1, bot.tree().getAllItems().length);
		assertEquals("Player Alice", bot.tree().getAllItems()[0].getText()); //$NON-NLS-1$
		assertEquals(0, bot.tree().selection().columnCount());
		assertEquals(0, bot.tree().selection().rowCount());
		assertEquals("Select a node in the tree to edit it", bot.label().getText()); //$NON-NLS-1$
	}

	@Test
	public void renderInitialRootobject() {
		// arrange
		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName(LEAGUE);
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);

		final Object input = new RootObject(league);

		// act
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory.createTreeMasterDetail(
					shell,
					SWT.NONE,
					input);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		// assert, league shown, no selection
		assertEquals(1, bot.tree().getAllItems().length);
		assertEquals("League Liga", bot.tree().getAllItems()[0].getText()); //$NON-NLS-1$
		assertEquals(0, bot.tree().selection().columnCount());
		assertEquals(0, bot.tree().selection().rowCount());
		assertEquals("Select a node in the tree to edit it", bot.label().getText()); //$NON-NLS-1$
	}

	@Test
	public void renderInitialResource() {
		// arrange
		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName(LEAGUE);
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);

		final Resource resource = createResource();
		resource.getContents().add(league);

		final Object input = resource;

		// act
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory.createTreeMasterDetail(
					shell,
					SWT.NONE,
					input);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		// assert, league shown, selection on league
		assertEquals(1, bot.tree().getAllItems().length);
		assertEquals("League Liga", bot.tree().getAllItems()[0].getText()); //$NON-NLS-1$
		assertEquals("League Liga", bot.tree().selection().get(0, 0)); //$NON-NLS-1$
		assertEquals(LEAGUE, bot.text().getText());
	}

	@Test
	public void updateDetailBySelectingAnotherPlayer() throws InterruptedException {

		// arrange
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		bob.setName(BOB);
		league.getPlayers().add(alice);
		league.getPlayers().add(bob);

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.createTreeMasterDetail(shell, SWT.NONE, league);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});

		SWTTestUtil.waitForUIThread();

		/* act */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				bot.tree().getTreeItem("Player Bob").doubleClick(); //$NON-NLS-1$
			}
		});

		SWTTestUtil.waitForUIThread();

		// assert
		assertEquals(BOB, bot.text().getText());
	}

	@Test
	public void initSelection() throws InterruptedException {

		// arrange
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		bob.setName(BOB);
		league.getPlayers().add(alice);
		league.getPlayers().add(bob);

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, league)
					.customizeInitialSelection(new InitialSelectionProvider() {
						@Override
						public EObject getInitialSelection(Object input) {
							return bob;
						}
					})
					.create();
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

				// act
				shell.open();
			}
		});

		SWTTestUtil.waitForUIThread();

		// assert
		assertEquals("Player Bob", bot.tree().selection().get(0, 0)); //$NON-NLS-1$
		assertEquals(BOB, bot.text().getText());
	}

	@Test
	public void renderDelaySingleClick() {
		/* setup */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName(LEAGUE);
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		bob.setName(BOB);
		league.getPlayers().add(bob);
		final Object input = league;
		final int renderDelay = 2500;
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, input)
					.customizeInitialSelection(new InitialSelectionProvider() {
						@Override
						public EObject getInitialSelection(Object input) {
							return alice;
						}
					})
					.customizeUpdateDelay(renderDelay)
					.create();
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();
		final String text = bot.text().getText();
		assertEquals(ALICE, text);

		/* select */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				bot.tree().getTreeItem("Player Bob").click(); //$NON-NLS-1$
			}
		});

		/* unchanged after click */
		SWTTestUtil.waitForUIThread();
		assertEquals(text, bot.text().getText());

		/* changed after delay */
		bot.waitUntil(new ICondition() {

			@Override
			public boolean test() throws Exception {
				SWTTestUtil.waitForUIThread();
				return BOB.equals(bot.text().getText());
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Delay over but still showing first player"; //$NON-NLS-1$
			}
		}, renderDelay * 2);

		/* no focus change expected */
		assertSame(bot.tree().widget, bot.getFocusedWidget());
	}

	@Test
	public void renderDelayDoubleClick() {
		/* setup */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName(LEAGUE);
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		bob.setName(BOB);
		league.getPlayers().add(bob);
		final Object input = league;
		final int renderDelay = 2500;
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, input)
					.customizeInitialSelection(new InitialSelectionProvider() {
						@Override
						public EObject getInitialSelection(Object input) {
							return alice;
						}
					})
					.customizeUpdateDelay(renderDelay)
					.create();
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();
		final String text = bot.text().getText();
		assertEquals(ALICE, text);

		/* double click */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				bot.tree().getTreeItem("Player Bob").doubleClick(); //$NON-NLS-1$
			}
		});

		/* double click should update immediately, no delay expected */
		SWTTestUtil.waitForUIThread();
		assertEquals(BOB, bot.text().getText());
		/* focus change expected */
		assertSame(bot.text().widget, bot.getFocusedWidget());
	}

	@Test
	public void renderDelayKeyboardEnter() {
		/* setup */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName(LEAGUE);
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		bob.setName(BOB);
		league.getPlayers().add(bob);
		final Object input = league;
		final int renderDelay = 2500;
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, input)
					.customizeInitialSelection(new InitialSelectionProvider() {
						@Override
						public EObject getInitialSelection(Object input) {
							return alice;
						}
					})
					.customizeUpdateDelay(renderDelay)
					.create();
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();
		final String text = bot.text().getText();
		assertEquals(ALICE, text);
		assertEquals("Player Alice", bot.tree().selection().get(0, 0)); //$NON-NLS-1$

		/* press down, selection expected to change */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					bot.tree().pressShortcut(KeyStroke.getInstance(IKeyLookup.ARROW_DOWN_NAME));
				} catch (final ParseException ex) {
					throw new RuntimeException(ex);
				}
			}
		});

		/* detail unchanged after down, but selection changed already */
		SWTTestUtil.waitForUIThread();
		assertEquals(text, bot.text().getText());
		assertEquals("Player Bob", bot.tree().selection().get(0, 0)); //$NON-NLS-1$

		/* press enter */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					bot.tree().pressShortcut(KeyStroke.getInstance(IKeyLookup.ENTER_NAME));
				} catch (final ParseException ex) {
					throw new RuntimeException(ex);
				}
			}
		});

		/* enter should update immediately, no delay expected */
		SWTTestUtil.waitForUIThread();
		assertEquals(BOB, bot.text().getText());
		/* focus change expected */
		assertSame(bot.text().widget, bot.getFocusedWidget());
	}

	@Test
	public void cacheNotCached() {
		/* setup render */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		bob.setName(BOB);
		league.getPlayers().add(bob);

		final Object input = league;

		final AtomicReference<TreeMasterDetailComposite> tmdComposite = new AtomicReference<TreeMasterDetailComposite>();

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, input)
					.create();
				tmdComposite.set(composite);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		/* setup cache */
		final AtomicReference<EObject> isCachedCalledWith = new AtomicReference<EObject>();
		tmdComposite.get().setCache(new TreeMasterDetailCache() {

			@Override
			public boolean isChached(EObject selection) {
				if (isCachedCalledWith.get() != null) {
					/* called multiple times */
					fail();
				}
				assertSame(bob, selection);
				isCachedCalledWith.set(selection);
				return false;
			}

			@Override
			public ECPSWTView getCachedView(EObject selection) {
				fail();
				return null;
			}

			@Override
			public void cache(ECPSWTView ecpView) {
			}
		});

		/* act select something */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				bot.tree().getTreeItem("Player Bob").doubleClick(); //$NON-NLS-1$
			}
		});

		/* assert */
		assertSame(bob, isCachedCalledWith.get());
	}

	@Test
	public void cacheCacheAndRerender() {
		/* setup render and select something */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		bob.setName(BOB);
		league.getPlayers().add(bob);

		final Object input = league;

		final AtomicReference<TreeMasterDetailComposite> tmdComposite = new AtomicReference<TreeMasterDetailComposite>();

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, input)
					.create();
				tmdComposite.set(composite);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				bot.tree().getTreeItem("Player Bob").doubleClick(); //$NON-NLS-1$
			}
		});
		SWTTestUtil.waitForUIThread();
		assertEquals(BOB, bot.text().getText());
		final Text nameWidget = bot.text().widget;

		/* setup cache */
		tmdComposite.get().setCache(new TreeMasterDetailCache() {

			private ECPSWTView ecpView;

			@Override
			public boolean isChached(EObject selection) {
				assertSame(alice, selection);
				return true;
			}

			@Override
			public ECPSWTView getCachedView(EObject selection) {
				assertSame(alice, selection);
				return ecpView;
			}

			@Override
			public void cache(ECPSWTView ecpView) {
				assertSame(bob, ecpView.getViewModelContext().getDomainModel());
				if (this.ecpView != null) {
					fail();
				}
				this.ecpView = ecpView;
			}
		});

		/* act select something else */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				bot.tree().getTreeItem("Player Alice").doubleClick(); //$NON-NLS-1$
			}
		});

		/* assert updated text, same widget in use */
		SWTTestUtil.waitForUIThread();
		assertEquals(ALICE, bot.text().getText());
		assertSame(nameWidget, bot.text().widget);
	}

	@Test
	public void initialTreeWidth() {
		/* setup domain */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		bob.setName(BOB);
		league.getPlayers().add(bob);

		/* act render */
		final int initialTreeWidth = 123;
		final Object input = league;
		final AtomicReference<TreeMasterDetailComposite> tmdComposite = new AtomicReference<TreeMasterDetailComposite>();
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, input)
					.customizeInitialTreeWidth(initialTreeWidth)
					.create();
				tmdComposite.set(composite);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		/* assert */
		final TreeMasterDetailComposite treeMasterDetailComposite = tmdComposite.get();
		final Sash sash = (Sash) treeMasterDetailComposite.getChildren()[0];
		final FormData formData = (FormData) sash.getLayoutData();
		assertEquals(initialTreeWidth, formData.left.offset);
	}

	@Test
	public void detailComposite() {
		/* setup domain */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		bob.setName(BOB);
		league.getPlayers().add(bob);

		/* act render */
		final Object input = league;
		final AtomicReference<TreeMasterDetailComposite> tmdComposite = new AtomicReference<TreeMasterDetailComposite>();
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, input)
					.customizeDetailComposite(new DetailCompositeBuilder() {

						@Override
						public boolean enableVerticalCopy() {
							return false;
						}

						@Override
						public Composite createDetailComposite(Composite parent) {
							final Composite comp = new Composite(parent, SWT.NONE);
							comp.setData(TreeMasterDetail_PTest.class.getName(),
								TreeMasterDetail_PTest.class.getName());
							return comp;
						}
					})
					.create();
				tmdComposite.set(composite);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		final TreeMasterDetailComposite treeMasterDetailComposite = tmdComposite.get();
		final Composite comp = (Composite) treeMasterDetailComposite.getChildren()[2];
		assertEquals(TreeMasterDetail_PTest.class.getName(), comp.getData(TreeMasterDetail_PTest.class.getName()));
	}

	@Test
	public void setInput() {
		/* setup */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);

		final Object input = league;
		final AtomicReference<TreeMasterDetailComposite> tmdComposite = new AtomicReference<TreeMasterDetailComposite>();

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory.createTreeMasterDetail(
					shell,
					SWT.NONE,
					input);
				tmdComposite.set(composite);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		/* act */
		final League league2 = BowlingFactory.eINSTANCE.createLeague();
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		bob.setName(BOB);
		league2.getPlayers().add(bob);
		tmdComposite.get().setInput(league2);
		SWTTestUtil.waitForUIThread();

		// assert, league hidden, no selection
		assertEquals(1, bot.tree().getAllItems().length);
		assertEquals("Player Bob", bot.tree().getAllItems()[0].getText()); //$NON-NLS-1$
		assertEquals(0, bot.tree().selection().columnCount());
		assertEquals(0, bot.tree().selection().rowCount());
		assertEquals("Select a node in the tree to edit it", bot.label().getText()); //$NON-NLS-1$

	}

	@Test
	public void testUpdateViewModelProperties() {
		/* setup */
		final BundleContext bundleContext = FrameworkUtil.getBundle(EMFFormsViewService.class).getBundleContext();
		final ServiceReference<EMFFormsViewService> serviceReference = bundleContext
			.getServiceReference(EMFFormsViewService.class);
		final EMFFormsViewService viewService = bundleContext.getService(serviceReference);

		final String myPropertyKey = "IAmPropertyKey"; //$NON-NLS-1$

		final AtomicBoolean called = new AtomicBoolean(false);

		final IViewProvider viewProvider = Mockito.mock(IViewProvider.class);
		Mockito.doAnswer(new Answer<Double>() {

			@Override
			public Double answer(InvocationOnMock invocation) throws Throwable {
				final VViewModelProperties viewModelProperties = VViewModelProperties.class
					.cast(invocation.getArguments()[1]);
				assertTrue(viewModelProperties.containsKey(myPropertyKey));
				called.set(true);
				return IViewProvider.NOT_APPLICABLE;
			}
		}).when(viewProvider).canProvideViewModel(
			Matchers.any(EObject.class),
			Matchers.any(VViewModelProperties.class));

		try {
			viewService.addProvider(viewProvider);

			final League league = BowlingFactory.eINSTANCE.createLeague();
			final Player alice = BowlingFactory.eINSTANCE.createPlayer();
			final Player bob = BowlingFactory.eINSTANCE.createPlayer();
			alice.setName(ALICE);
			bob.setName(BOB);
			league.getPlayers().add(alice);
			league.getPlayers().add(bob);

			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					composite = TreeMasterDetailSWTFactory
						.createTreeMasterDetail(shell, SWT.NONE, league);
					GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
					composite.addViewModelPropertiesUpdateCallback(new ViewModelPropertiesUpdateCallback() {
						@Override
						public void updateViewModelProperties(VViewModelProperties properties) {
							properties.addInheritableProperty(myPropertyKey, "IAmValue"); //$NON-NLS-1$
						}
					});
					shell.open();
				}
			});

			SWTTestUtil.waitForUIThread();

			/* act */
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					bot.tree().getTreeItem("Player Bob").doubleClick(); //$NON-NLS-1$
				}
			});

			/* assert */
			assertTrue(called.get());
		}

		/* cleanup */
		finally {
			viewService.removeProvider(viewProvider);
			bundleContext.ungetService(serviceReference);
		}
	}

	@Test
	public void testDispose() {
		final int shellsBefore = Display.getCurrent().getShells().length;

		/* setup */
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		league.getPlayers().add(alice);

		final Object input = league;

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory.createTreeMasterDetail(
					shell,
					SWT.NONE,
					input);
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});
		SWTTestUtil.waitForUIThread();

		/* act */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite.dispose();
			}
		});
		SWTTestUtil.waitForUIThread();

		/* assert */
		assertEquals(0, shell.getChildren().length);
		// limbo shell is diposed -> no increase in
		// count of shells
		assertEquals(shellsBefore, Display.getCurrent().getShells().length);
	}

	// TODO test set selection programmatically
	// TODO test get editing domain
	// TODO test rendering finished callback

	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void createElement() throws InterruptedException {

		// arrange
		// create an area as it already has child descriptors
		final Area area = BowlingFactory.eINSTANCE.createArea();
		final Tournament t1 = BowlingFactory.eINSTANCE.createTournament();
		final Tournament t2 = BowlingFactory.eINSTANCE.createTournament();

		area.getTournaments().add(t1);
		area.getTournaments().add(t2);

		// necessary in order to generate menu items of child descriptors
		final Resource resource = createResource();
		resource.getContents().add(area);

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, area)
					.customizeCildCreation(new CreateElementCallback() {

						@Override
						public void initElement(EObject parent, EReference reference, EObject newObject) {
						}

						@Override
						public boolean beforeCreateElement(Object newElement) {
							return true;
						}

						@Override
						public void afterCreateElement(Object newElement) {
							Matchup.class.cast(newElement).setNrSpectators(BigInteger.TEN);
						}
					})
					.create();
				GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
				shell.open();
			}
		});

		SWTTestUtil.waitForUIThread();

		/* act */
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				bot.tree().select(0).contextMenu("Matchup").click(); //$NON-NLS-1$
			}
		});

		SWTTestUtil.waitForUIThread();

		// assert
		final EList<Matchup> matchups = area.getTournaments().get(0).getMatchups();
		assertEquals(1, matchups.size());
		assertEquals(BigInteger.TEN, matchups.get(0).getNrSpectators());
	}

	@Test
	public void labelDecorator() throws InterruptedException {

		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player alice = BowlingFactory.eINSTANCE.createPlayer();
		final Player bob = BowlingFactory.eINSTANCE.createPlayer();
		alice.setName(ALICE);
		bob.setName(BOB);
		league.getPlayers().add(alice);
		league.getPlayers().add(bob);

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				composite = TreeMasterDetailSWTFactory
					.fillDefaults(shell, SWT.NONE, league)
					.customizeLabelDecorator(new MyLabelDecorator())
					.create();
				shell.open();
			}
		});

		// act
		bot.tree().getTreeItem(ALICE);
	}

	class MyLabelDecorator implements ILabelDecorator {

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public String decorateText(String text, Object element) {
			return text.replace("Player", "").trim(); //$NON-NLS-1$ //$NON-NLS-2$
		}

		@Override
		public Image decorateImage(Image image, Object element) {
			return null;
		}
	}

}
