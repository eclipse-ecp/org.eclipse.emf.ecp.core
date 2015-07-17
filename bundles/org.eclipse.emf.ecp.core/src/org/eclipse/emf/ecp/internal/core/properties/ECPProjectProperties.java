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

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.internal.core.Messages;
import org.eclipse.net4j.util.properties.DefaultPropertyTester;
import org.eclipse.net4j.util.properties.IProperties;
import org.eclipse.net4j.util.properties.Properties;
import org.eclipse.net4j.util.properties.Property;

/**
 * @author Eike Stepper
 */
public final class ECPProjectProperties extends Properties<ECPProject> {
	private static final String UNKNOWN_PROVIDER = "<unknown provider>"; //$NON-NLS-1$
	private static final IProperties<ECPProject> INSTANCE = new ECPProjectProperties();

	private ECPProjectProperties() {
		super(ECPProject.class);

		add(new Property<ECPProject>(
			"name", Messages.ECPProjectProperties_Name, Messages.ECPProjectProperties_NameOfProject) { //$NON-NLS-1$
			@Override
			protected Object eval(ECPProject project) {
				return project.getName();
			}
		});

		add(new Property<ECPProject>("repositoryName") { //$NON-NLS-1$
			@Override
			protected Object eval(ECPProject project) {
				return project.getRepository().getName();
			}
		});

		add(new Property<ECPProject>(
			"repositoryLabel", Messages.ECPProjectProperties_Repository, Messages.ECPProjectProperties_RepositoryOfProject) { //$NON-NLS-1$
			@Override
			protected Object eval(ECPProject project) {
				return project.getRepository().getLabel();
			}
		});

		add(new Property<ECPProject>("providerName") { //$NON-NLS-1$
			@Override
			protected Object eval(ECPProject project) {
				final ECPProvider provider = project.getProvider();
				if (provider != null) {
					return provider.getName();
				}

				return UNKNOWN_PROVIDER;
			}
		});

		add(new Property<ECPProject>(
			"providerLabel", Messages.ECPProjectProperties_Provider, Messages.ECPProjectProperties_ProviderOfProject) { //$NON-NLS-1$
			@Override
			protected Object eval(ECPProject project) {
				final ECPProvider provider = project.getProvider();
				if (provider != null) {
					return provider.getLabel();
				}

				return UNKNOWN_PROVIDER;
			}
		});
		add(new Property<ECPProject>(
			"isDirty", Messages.ECPProjectProperties_IsProjectDirty, Messages.ECPProjectProperties_HasProjectUnsavedChanges) { //$NON-NLS-1$
			@Override
			protected Object eval(ECPProject project) {
				return project.hasDirtyContents();
			}
		});
		add(new Property<ECPProject>("open") { //$NON-NLS-1$
			@Override
			protected Object eval(ECPProject closeable) {
				return closeable.isOpen();
			}
		});

		add(new Property<ECPProject>("closed") { //$NON-NLS-1$
			@Override
			protected Object eval(ECPProject closeable) {
				return !closeable.isOpen();
			}
		});
	}

	/**
	 * @author Eike Stepper
	 */
	public static final class Tester extends DefaultPropertyTester<ECPProject> {
		private static final String NAMESPACE = "org.eclipse.emf.ecp.core.project"; //$NON-NLS-1$

		/** The tester constructor. */
		public Tester() {
			super(NAMESPACE, INSTANCE);
		}
	}
}
