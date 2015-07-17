/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.core.properties;

import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.net4j.util.properties.DefaultPropertyTester;
import org.eclipse.net4j.util.properties.IProperties;
import org.eclipse.net4j.util.properties.Properties;
import org.eclipse.net4j.util.properties.Property;

/**
 * @author Eugen Neufeld
 */
public final class ECPContainerProperties extends Properties<ECPContainer> {
	private static final String CAN_DELETE = "canDelete"; //$NON-NLS-1$
	private static final IProperties<ECPContainer> INSTANCE = new ECPContainerProperties();

	private ECPContainerProperties() {
		super(ECPContainer.class);

		add(new Property<ECPContainer>(CAN_DELETE) {
			@Override
			protected Object eval(ECPContainer deletable) {
				return deletable.canDelete();
			}
		});
	}

	/**
	 * @author Eike Stepper
	 */
	public static final class Tester extends DefaultPropertyTester<ECPContainer> {
		private static final String NAMESPACE = "org.eclipse.emf.ecp.core.container"; //$NON-NLS-1$

		/** The tester constructor. */
		public Tester() {
			super(NAMESPACE, INSTANCE);
		}
	}
}
