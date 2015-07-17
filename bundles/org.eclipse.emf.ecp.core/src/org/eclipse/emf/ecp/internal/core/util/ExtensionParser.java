/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.core.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable;
import org.eclipse.emf.ecp.spi.core.util.InternalRegistryElement;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.osgi.framework.Bundle;

/**
 * @author Eike Stepper
 * @param <ELEMENT>
 */
public abstract class ExtensionParser<ELEMENT extends InternalRegistryElement> extends Lifecycle implements
	IRegistryChangeListener {
	private final ElementRegistry<ELEMENT, ?> elementRegistry;

	private final String namespace;

	private final String name;

	public ExtensionParser(ElementRegistry<ELEMENT, ?> elementRegistry, String namespace, String name) {
		this.elementRegistry = elementRegistry;
		this.namespace = namespace;
		this.name = name;
	}

	public final ElementRegistry<ELEMENT, ?> getElementRegistry() {
		return elementRegistry;
	}

	public final String getNamespace() {
		return namespace;
	}

	public final String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public void registryChanged(IRegistryChangeEvent event) {
		final Set<String> remove = new HashSet<String>();
		final Set<ELEMENT> add = new HashSet<ELEMENT>();
		for (final IExtensionDelta delta : event.getExtensionDeltas(namespace, name)) {
			final IExtension extension = delta.getExtension();
			final int kind = delta.getKind();
			switch (kind) {
			case IExtensionDelta.ADDED:
				addExtension(extension, add);
				break;

			case IExtensionDelta.REMOVED:
				removeExtension(extension, remove);
				break;
			}
		}

		final Set<ELEMENT> removedElements = elementRegistry.doChangeElements(remove, add);
		if (removedElements != null) {
			for (final ELEMENT removedElement : removedElements) {
				if (removedElement instanceof ECPDisposable) {
					final ECPDisposable disposable = removedElement;
					disposable.dispose();
				}
			}
		}
	}

	@Override
	protected void doActivate() throws Exception {
		super.doActivate();

		final String extensionPointID = namespace + "." + name;
		final Set<ELEMENT> add = new HashSet<ELEMENT>();

		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(extensionPointID);
		for (final IExtension extension : extensionPoint.getExtensions()) {
			addExtension(extension, add);
		}

		elementRegistry.doChangeElements(null, add);
		Platform.getExtensionRegistry().addRegistryChangeListener(this, namespace);
	}

	@Override
	protected void doDeactivate() throws Exception {
		Platform.getExtensionRegistry().removeRegistryChangeListener(this);
		super.doDeactivate();
	}

	protected void addExtension(IExtension extension, Set<ELEMENT> result) {
		final String name = extension.getUniqueIdentifier();
		final IConfigurationElement configurationElement = extension.getConfigurationElements()[0];

		final ELEMENT element = createElement(name, configurationElement);
		element.setLabel(extension.getLabel());
		element.setDescription(configurationElement.getAttribute("description"));
		result.add(element);
	}

	protected void removeExtension(IExtension extension, Set<String> result) {
		final String name = extension.getUniqueIdentifier();
		result.add(name);
	}

	protected abstract ELEMENT createElement(String name, IConfigurationElement configurationElement);

	/**
	 * @author Eike Stepper
	 */
	public static class ExtensionDescriptor<ELEMENT extends InternalRegistryElement> extends ElementDescriptor<ELEMENT> {
		private final String type;

		private final IConfigurationElement configurationElement;

		public ExtensionDescriptor(ElementRegistry<ELEMENT, ?> registry, String name, String type,
			IConfigurationElement configurationElement) {
			super(registry, name);
			this.type = type;
			this.configurationElement = configurationElement;

			try {
				final String bundleName = configurationElement.getContributor().getName();
				final Bundle bundle = Platform.getBundle(bundleName);
				String location = bundle.getLocation();

				if (location.startsWith("initial@")) {
					location = location.substring("initial@".length());
				}

				final String prefix = "reference:file:";
				if (location.startsWith(prefix)) {
					location = location.substring(prefix.length());

					// TODO Trace properly
					System.out.println(getClass().getSimpleName() + ": " + bundleName + " [" + bundle.getBundleId()
						+ "] --> file:" + new File(location).getCanonicalPath());
				}
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		}

		/** {@inheritDoc} */
		@Override
		public String getType() {
			return type;
		}

		public final IConfigurationElement getConfigurationElement() {
			return configurationElement;
		}

		@Override
		@SuppressWarnings("unchecked")
		protected ELEMENT resolve() throws Exception {
			return (ELEMENT) configurationElement.createExecutableExtension(getClassAttributeName());
		}

		protected String getClassAttributeName() {
			return "class";
		}
	}
}
