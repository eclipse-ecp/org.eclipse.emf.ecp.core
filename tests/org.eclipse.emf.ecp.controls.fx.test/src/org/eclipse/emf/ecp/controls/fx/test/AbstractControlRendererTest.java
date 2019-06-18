/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.fx.test;

import javafx.embed.swing.JFXPanel;

import org.eclipse.emf.ecp.common.test.fx.DefaultRealm;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Lucas
 *
 */
public abstract class AbstractControlRendererTest {

	@SuppressWarnings("unused")
	private static JFXPanel jfx;
	private DefaultRealm realm;

	/**
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		jfx = new JFXPanel();
	}

	/**
	 *
	 */
	public AbstractControlRendererTest() {
		super();
	}

	/**
	 * Creates the default data binding realm.
	 */
	@Before
	public void setUpRealm() {
		realm = new DefaultRealm();
	}

	/**
	 * Removes the default realm.
	 */
	@After
	public void disposeRealm() {
		realm.dispose();
	}

}