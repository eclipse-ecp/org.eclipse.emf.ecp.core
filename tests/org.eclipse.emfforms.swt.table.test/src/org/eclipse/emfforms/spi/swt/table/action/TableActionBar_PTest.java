/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link TableActionBar}.
 *
 * @author Lucas Koehler
 *
 */
public class TableActionBar_PTest {

	private ActionConfiguration actionConfiguration;
	private Shell shell;
	private TableActionBar<AbstractTableViewer> tableActionBar;
	private Action action1;
	private Action action2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
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

		actionConfiguration = configurationBuilder.build();

		shell = new Shell(Display.getDefault());
		shell.setLayout(new FillLayout());

		tableActionBar = new TableActionBar<AbstractTableViewer>(actionConfiguration);
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

	@Test
	public void test() {
		final Composite composite = new Composite(shell, SWT.NONE);
		final TableViewer tableViewer = new TableViewer(shell);
		tableActionBar.fillComposite(composite, tableViewer);

		assertEquals(2, composite.getChildren().length);
		assertEquals(Button.class, composite.getChildren()[0].getClass());
		assertEquals(Label.class, composite.getChildren()[1].getClass());

		assertEquals(2, tableActionBar.getActionCount());
		assertEquals(2, tableActionBar.getControlCount());
		assertEquals(Button.class, tableActionBar.getControlById("action1").get().getClass()); //$NON-NLS-1$
		assertEquals(Label.class, tableActionBar.getControlById("action2").get().getClass()); //$NON-NLS-1$
	}

	@Test
	public void updateActionBar() {
		final Composite composite = new Composite(shell, SWT.NONE);
		final TableViewer tableViewer = new TableViewer(shell);
		tableActionBar.fillComposite(composite, tableViewer);

		tableActionBar.updateActionBar();
		// initial state
		assertTrue(composite.getChildren()[0].isEnabled());
		assertFalse(composite.getChildren()[1].isEnabled());

		when(action2.canExecute()).thenReturn(true);
		tableActionBar.updateActionBar();
		// The control for action2 should now be enabled
		assertTrue(composite.getChildren()[0].isEnabled());
		assertTrue(composite.getChildren()[1].isEnabled());
	}

}
