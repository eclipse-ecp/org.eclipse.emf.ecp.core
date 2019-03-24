/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi;

import java.lang.ref.ReferenceQueue;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Pool;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

/**
 * API for obtaining user-friendly labels for {@link EObject}s, intended especially for
 * {@link Object#toString() debug strings} of objects that encapsulate {@link EObject}s
 * but that could be appropriate in other end-user (such as UI) scenarios.
 *
 * @since 1.20
 */
public final class Labelizer {

	private static final Pool<Labelizer> POOL = new PoolImpl();

	private final EPackage ePackage;
	private AdapterFactory adapterFactory;

	/**
	 * Initializes me with the package that I support.
	 */
	private Labelizer(EPackage ePackage) {
		super();

		this.ePackage = ePackage;
	}

	/**
	 * Initialize my adapter factory.
	 *
	 * @return something to dispose when I am no longer needed
	 */
	IDisposable initialize() {
		final Collection<?> types = Arrays.asList(ePackage, IItemLabelProvider.class);
		final ComposedAdapterFactory.Descriptor desc = ComposedAdapterFactory.Descriptor.Registry.INSTANCE
			.getDescriptor(types);
		if (desc != null) {
			adapterFactory = desc.createAdapterFactory();
		}

		if (adapterFactory == null) {
			adapterFactory = new ReflectiveItemProviderAdapterFactory();
		}

		return adapterFactory instanceof IDisposable
			? (IDisposable) adapterFactory
			: Labelizer::pass;
	}

	private static void pass() {
		// Nothing to do
	}

	/**
	 * Get a labelizer for the given {@code object}.
	 *
	 * @param object the objectfor which to get a labelizer
	 * @return the labelizer
	 */
	public static Labelizer get(EObject object) {
		return get(object.eClass().getEPackage());
	}

	/**
	 * Get a labelizer for the given {@code ePackage}.
	 *
	 * @param ePackage the package for which to get a labelizer
	 * @return the labelizer
	 */
	public static Labelizer get(EPackage ePackage) {
		final Labelizer result = new Labelizer(ePackage);
		return POOL.intern(result);
	}

	/**
	 * Obtain a friendly label for the given {@code object}. Where possible,
	 * this will be based on the label provided by the model's EMF.Edit providers.
	 *
	 * @param object an object
	 * @return a best-effort friendly readable label for it
	 */
	public String getLabel(EObject object) {
		String result = null;

		final IItemLabelProvider labels = (IItemLabelProvider) adapterFactory.adapt(object, IItemLabelProvider.class);
		if (labels != null) {
			result = labels.getText(object);
		}

		if (result == null) {
			result = String.valueOf(object);
		}

		return result;
	}

	@Override
	public int hashCode() {
		return ePackage.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Labelizer && ((Labelizer) obj).ePackage.equals(ePackage);
	}

	//
	// Nested types
	//

	/**
	 * Interning labelizer pool implementation.
	 */
	@SuppressWarnings("serial")
	private static final class PoolImpl extends Pool<Labelizer> {

		PoolImpl() {
			super(8, null, null); // Default object access units and reference queue
		}

		@Override
		protected void putEntry(int index, Entry<Labelizer> entry) {
			// initialize the labelizer now
			((EntryImpl) entry).initialize();

			super.putEntry(index, entry);
		}

		@Override
		protected boolean removeEntry(int index, Entry<Labelizer> entry) {
			// dispose the labelizer
			((EntryImpl) entry).dispose();

			return super.removeEntry(index, entry);
		}

		@Override
		protected Entry<Labelizer> newExternalEntry(Labelizer object, int hashCode) {
			return new EntryImpl(this, object, hashCode, externalQueue);
		}

		//
		// Nested types
		//

		/**
		 * Custom pool entry that tracks the encapsulation of the disposing of the
		 * labelizer.
		 */
		private static final class EntryImpl extends PoolEntry<Labelizer> {
			private IDisposable disposer;

			EntryImpl(PoolImpl pool, Labelizer labelizer, int hashCode, ReferenceQueue<Object> externalQueue) {
				super(pool, labelizer, hashCode, externalQueue);
			}

			void initialize() {
				disposer = get().initialize();
			}

			void dispose() {
				if (disposer != null) {
					disposer.dispose();
					disposer = null;
				}
			}

		}

	}

}
