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

		add(new Property<ECPRepository>("name", "Name", "The name of this repository.") {
			@Override
			protected Object eval(ECPRepository repository) {
				return repository.getName();
			}
		});

		add(new Property<ECPRepository>("repositoryLabel", "Repository", "The repository of this project.") {
			@Override
			protected Object eval(ECPRepository repository) {
				return repository.getLabel();
			}
		});

		add(new Property<ECPRepository>("providerName") {
			@Override
			protected Object eval(ECPRepository repository) {
				final ECPProvider provider = repository.getProvider();
				if (provider != null) {
					return provider.getName();
				}

				return "<unknown provider>";
			}
		});
	}

	/**
	 * @author Eike Stepper
	 */
	public static final class Tester extends DefaultPropertyTester<ECPRepository> {
		private static final String NAMESPACE = "org.eclipse.emf.ecp.core.repository";

		/** The tester constructor. */
		public Tester() {
			super(NAMESPACE, INSTANCE);
		}
	}
}
