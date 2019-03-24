/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.table.action;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.spi.swt.table.action.Action;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfiguration;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfigurationBuilder;
import org.eclipse.emfforms.spi.swt.table.action.ActionControlCreator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link KeyBindingManager}.
 * 
 * @author Lucas Koehler
 *
 */
public class KeyBindingManager_PTest {
	private ActionConfiguration actionConfiguration;
	private Shell shell;
	private Action action1;
	private Action action2;
	private KeyBindingManager keyBindingManager;

	@Before
	public void setUp() {
		final ActionConfigurationBuilder configurationBuilder = ActionConfigurationBuilder.usingDefaults();
		action1 = mock(Action.class);
		when(action1.getId()).thenReturn("action1"); //$NON-NLS-1$
		when(action1.canExecute()).thenReturn(true);
		action2 = mock(Action.class);
		when(action2.getId()).thenReturn("action2"); //$NON-NLS-1$
		when(action2.canExecute()).thenReturn(false);

		final ActionControlCreator<Button> creator1 = (composite, action) -> new Button(composite, SWT.PUSH);
		final ActionControlCreator<Label> creator2 = (composite, action) -> new Label(composite, SWT.PUSH);
		configurationBuilder.addAction(action1);
		configurationBuilder.addAction(action2);
		configurationBuilder.addControlFor(action1, creator1);
		configurationBuilder.addControlFor(action2, creator2);
		configurationBuilder.addKeySequenceFor(action1, "M1+a", "M2+b"); //$NON-NLS-1$ //$NON-NLS-2$
		configurationBuilder.addKeySequenceFor(action2, "M1+f"); //$NON-NLS-1$

		actionConfiguration = configurationBuilder.build();

		shell = new Shell(Display.getDefault());
		shell.setLayout(new FillLayout());
		shell.open();

		keyBindingManager = new KeyBindingManager();
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

	@Test
	public void testKeyBindingManager() {
		keyBindingManager.applyActionConfiguration(actionConfiguration);
		final TableViewer viewer = new TableViewer(shell);
		keyBindingManager.bindToViewer(viewer);

		SWTTestUtil.pressAndReleaseKey(viewer.getControl(), SWT.MOD1, 'a');
		verify(action1, times(1)).canExecute();
		verify(action1, times(1)).execute();
		SWTTestUtil.pressAndReleaseKey(viewer.getControl(), SWT.MOD2, 'b');
		verify(action1, times(2)).canExecute();
		verify(action1, times(2)).execute();

		// The former keybindings should not invoke unrelated actions
		verify(action2, never()).canExecute();
		verify(action2, never()).execute();

		SWTTestUtil.pressAndReleaseKey(viewer.getControl(), SWT.MOD1, 'f');
		verify(action2, times(1)).canExecute();
		// execute must not be invoked yet because canExecute returns false
		verify(action2, never()).execute();
		when(action2.canExecute()).thenReturn(true);
		SWTTestUtil.pressAndReleaseKey(viewer.getControl(), SWT.MOD1, 'f');
		verify(action2, times(2)).canExecute();
		// now execute must also be invoked
		verify(action2, times(1)).execute();

		// Verify that only shortcuts used on the viewer trigger the actions
		SWTTestUtil.pressAndReleaseKey(shell, SWT.MOD1, 'a');
		verify(action1, times(2)).canExecute();
		verify(action1, times(2)).execute();

		// Verify that unbinding the key binding manager works by checking that the actions are no longer invoked when
		// their combinations are pressed
		keyBindingManager.unbindFromViewer(viewer);
		SWTTestUtil.pressAndReleaseKey(viewer.getControl(), SWT.MOD1, 'a');
		SWTTestUtil.pressAndReleaseKey(viewer.getControl(), SWT.MOD1, 'f');
		verify(action1, times(2)).canExecute();
		verify(action1, times(2)).execute();
		verify(action2, times(2)).canExecute();
		verify(action2, times(1)).execute();
	}
}
