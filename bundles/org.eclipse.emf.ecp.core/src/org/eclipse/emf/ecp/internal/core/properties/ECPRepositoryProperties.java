/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.core.properties;

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.net4j.util.properties.DefaultPropertyTester;
import org.eclipse.net4j.util.properties.IProperties;
import org.eclipse.net4j.util.properties.Properties;
import org.eclipse.net4j.util.properties.Property;

/**
 * @author Eike Stepper
 */
public final class ECPRepositoryProperties extends Properties<ECPRepository> {
	private static final IProperties<ECPRepository> INSTANCE = new ECPRepositoryProperties();

	private ECPRepositoryProperties() {
		super(ECPRepository.class);

		add(new Property<ECPRepository>("name", "Name", "The name of this repository.") { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			@Override
			protected Object eval(ECPRepository repository) {
				return repository.getName();
			}
		});

		add(new Property<ECPRepository>("repositoryLabel", "Repository", "The repository of this project.") { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			@Override
			protected Object eval(ECPRepository repository) {
				return repository.getLabel();
			}
		});

		add(new Property<ECPRepository>("providerName") { //$NON-NLS-1$
			@Override
			protected Object eval(ECPRepository repository) {
				final ECPProvider provider = repository.getProvider();
				if (provider != null) {
					return provider.getName();
				}

				return "<unknown provider>"; //$NON-NLS-1$
			}
		});
	}

	/**
	 * @author Eike Stepper
	 */
	public static final class Tester extends DefaultPropertyTester<ECPRepository> {
		private static final String NAMESPACE = "org.eclipse.emf.ecp.core.repository"; //$NON-NLS-1$

		/** The tester constructor. */
		public Tester() {
			super(NAMESPACE, INSTANCE);
		}
	}
}
