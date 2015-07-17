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

import org.eclipse.emf.ecp.core.util.ECPElement;
import org.eclipse.emf.ecp.internal.core.Activator;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable.DisposeListener;
import org.eclipse.emf.ecp.spi.core.util.InternalDescriptor;
import org.eclipse.emf.ecp.spi.core.util.InternalRegistryElement;

/**
 * @author Eike Stepper
 * @param <ELEMENT>
 */
public abstract class ElementDescriptor<ELEMENT extends ECPElement> extends Element implements
	InternalDescriptor<ELEMENT>, DisposeListener {
	private final Disposable disposable = new Disposable(this) {
		@Override
		protected void doDispose() {
			if (resolvedElement instanceof ECPDisposable) {
				try {
					((ECPDisposable) resolvedElement).dispose();
				} catch (final Exception ex) {
					Activator.log(ex);
				}
			}

			resolvedElement = null;
			ElementDescriptor.this.doDispose();
		}
	};

	private final ElementRegistry<ELEMENT, ?> registry;

	private String label;

	private String description;

	private ELEMENT resolvedElement;

	public ElementDescriptor(ElementRegistry<ELEMENT, ?> registry, String name) {
		super(name);
		this.registry = registry;
		label = name;
		description = ""; //$NON-NLS-1$
	}

	public final ElementRegistry<ELEMENT, ?> getRegistry() {
		return registry;
	}

	/** {@inheritDoc} */
	@Override
	public final String getLabel() {
		return label;
	}

	/** {@inheritDoc} */
	@Override
	public final void setLabel(String label) {
		this.label = label;
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription() {
		return description;
	}

	/** {@inheritDoc} */
	@Override
	public final void setDescription(String description) {
		this.description = description;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isResolved() {
		return resolvedElement != null;
	}

	/** {@inheritDoc} */
	@Override
	public final ELEMENT getResolvedElement() {
		boolean resolved = false;
		synchronized (this) {
			if (resolvedElement == null) {
				try {
					resolvedElement = resolve();
					if (resolvedElement instanceof InternalRegistryElement) {
						final InternalRegistryElement registryElement = (InternalRegistryElement) resolvedElement;
						registryElement.setLabel(getLabel());
						registryElement.setDescription(getDescription());
					}

					if (resolvedElement instanceof ECPDisposable) {
						final ECPDisposable disposable = (ECPDisposable) resolvedElement;
						disposable.addDisposeListener(this);
					}

					resolvedElement(resolvedElement);
				} catch (final Throwable t) {
					throw new Error("Unable to resolve " + this, t);
				}

				resolved = true;
			}
		}

		if (resolved) {
			registry.descriptorChanged(this, true);
		}

		return resolvedElement;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isDisposed() {
		return disposable.isDisposed();
	}

	/** {@inheritDoc} */
	@Override
	public final void dispose() {
		disposable.dispose();
	}

	/** {@inheritDoc} */
	@Override
	public final void addDisposeListener(DisposeListener listener) {
		disposable.addDisposeListener(listener);
	}

	/** {@inheritDoc} */
	@Override
	public final void removeDisposeListener(DisposeListener listener) {
		disposable.removeDisposeListener(listener);
	}

	/** {@inheritDoc} */
	@Override
	public final void disposed(ECPDisposable disposable) {
		if (resolvedElement == disposable) {
			resolvedElement = null;
		}

		registry.descriptorChanged(this, false);
	}

	protected void doDispose() {
		// Can be overridden in subclasses
	}

	protected void resolvedElement(ELEMENT element) {
		// Can be overridden in subclasses
	}

	protected abstract ELEMENT resolve() throws Exception;
}
