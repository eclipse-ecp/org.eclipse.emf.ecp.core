/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

/**
 * Utility for tests to create EMF.Edit-based factories of new children.
 *
 * @author Christian W. Damus
 */
public final class EMFEditNewChildFactoryBuilder {
	private final Map<Class<? extends EObject>, Map<EReference, EObject>> templates = new HashMap<Class<? extends EObject>, Map<EReference, EObject>>();

	/**
	 * Initializes me.
	 */
	public EMFEditNewChildFactoryBuilder() {
		super();
	}

	public AdapterFactory buildFactory() {
		final AdapterFactory result = mock(AdapterFactory.class,
			withSettings().extraInterfaces(ComposeableAdapterFactory.class));

		for (final Map.Entry<Class<? extends EObject>, Map<EReference, EObject>> next : templates.entrySet()) {
			final NewChildFactory itemProvider = new NewChildFactory(result, next.getValue());

			when(result.adapt(any(next.getKey()), same(IEditingDomainItemProvider.class)))
				.thenReturn(itemProvider);
			when(result.adapt((Object) any(next.getKey()), same(IEditingDomainItemProvider.class)))
				.thenReturn(itemProvider);
			when(result.adaptNew(any(next.getKey()), same(IEditingDomainItemProvider.class)))
				.thenReturn(itemProvider);
		}

		return result;
	}

	public EditingDomain buildEditingDomain() {
		return new AdapterFactoryEditingDomain(buildFactory(), new BasicCommandStack());
	}

	public Resource buildResource() {
		return buildEditingDomain().getResourceSet().createResource(URI.createURI("bogus://test/foo.xmi")); //$NON-NLS-1$
	}

	public EMFEditNewChildFactoryBuilder addTemplate(Class<? extends EObject> owner, EReference reference,
		EObject template) {
		return addTemplates(owner, Collections.singletonMap(reference, template));
	}

	public EMFEditNewChildFactoryBuilder addTemplates(Class<? extends EObject> owner,
		Map<EReference, EObject> templates) {
		Map<EReference, EObject> merge = this.templates.get(owner);
		if (merge == null) {
			merge = new HashMap<EReference, EObject>();
			this.templates.put(owner, merge);
		}

		merge.putAll(templates);
		return this;
	}

	//
	// Nested types
	//

	private static class NewChildFactory extends ItemProviderAdapter implements IEditingDomainItemProvider {
		private final Map<EReference, EObject> templates;

		NewChildFactory(AdapterFactory adapterFactory, Map<EReference, EObject> templates) {
			super(adapterFactory);

			this.templates = templates;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			// I am a fake EMF.Edit item provider adapter, so I should respond like one
			return type instanceof AdapterFactory || type == IEditingDomainItemProvider.class;
		}

		@Override
		protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
			for (final Map.Entry<EReference, EObject> next : templates.entrySet()) {
				newChildDescriptors.add(createChildParameter(next.getKey(), EcoreUtil.copy(next.getValue())));
			}
		}
	}

}
