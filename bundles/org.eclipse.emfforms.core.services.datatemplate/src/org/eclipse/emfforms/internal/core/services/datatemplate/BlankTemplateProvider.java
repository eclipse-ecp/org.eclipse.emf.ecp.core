/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bug 529138
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.ui.view.swt.reference.DefaultCreateNewModelElementStrategyProvider;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emfforms.core.services.datatemplate.TemplateProvider;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver.NoBundleFoundException;
import org.eclipse.emfforms.internal.core.services.label.BundleResolverImpl;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * {@link TemplateProvider} that provides a templates simply containing empty instances of the corresponding EClass and
 * its concrete sub classes.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
@Component(name = "BlankTemplateProvider")
public class BlankTemplateProvider implements TemplateProvider {
	private BundleResolver bundleResolver;
	private EMFFormsLocalizationService localizationService;

	private DefaultCreateNewModelElementStrategyProvider defaultNewElementStrategyProvider;

	/**
	 * Creates a new {@link BlankTemplateProvider} instance.
	 */
	public BlankTemplateProvider() {
		setBundleResolver(new BundleResolverImpl());
	}

	/**
	 * Sets the {@link EMFFormsLocalizationService}.
	 *
	 * @param localizationService The {@link EMFFormsLocalizationService}
	 */
	@Reference
	void setLocalizationService(EMFFormsLocalizationService localizationService) {
		this.localizationService = localizationService;
	}

	/**
	 * Sets the default new-element strategy provider for creation of "blank" template instances.
	 *
	 * @param defaultNewElementStrategyProvider the default new-element strategy provider
	 */
	@Reference
	void setDefaultNewElementStrategyProvider(
		DefaultCreateNewModelElementStrategyProvider defaultNewElementStrategyProvider) {

		this.defaultNewElementStrategyProvider = defaultNewElementStrategyProvider;
	}

	@Override
	public boolean canProvideTemplates(EObject owner, EReference reference) {
		// We can only provide a blank template if the type or at least one of its sub classes is concrete.
		for (final EClass subClass : EMFUtils.getSubClasses(reference.getEReferenceType())) {
			if (!subClass.isAbstract() && !subClass.isInterface()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Set<Template> provideTemplates(EObject owner, EReference reference) {
		return provideTemplates(owner, reference, EClassSelectionStrategy.NULL);
	}

	/**
	 * The actual method which creates the templates. This Method uses the provided {@link EClassSelectionStrategy} to
	 * filter possible templates.
	 *
	 * @param owner The {@link EObject} to which the templates should be added
	 * @param reference The {@link EReference} to which the templates should be added
	 * @param eClassSelectionStrategy The {@link EClassSelectionStrategy} to use for filtering
	 * @return The Set of Templates created by this {@link TemplateProvider}
	 */
	public Set<Template> provideTemplates(EObject owner, EReference reference,
		EClassSelectionStrategy eClassSelectionStrategy) {
		Map<EClass, EObject> descriptors = defaultNewElementStrategyProvider == null
			? Collections.<EClass, EObject> emptyMap()
			: defaultNewElementStrategyProvider.getNewObjectsByDescriptors(owner, reference);
		if (descriptors.isEmpty()) {
			// Just create actually blank instances of all possible subtypes
			descriptors = new HashMap<EClass, EObject>();
			final Collection<EClass> subClasses = EMFUtils.getSubClasses(reference.getEReferenceType());
			for (final EClass subClass : subClasses) {
				if (!subClass.isAbstract() && !subClass.isInterface()) {
					descriptors.put(subClass, EcoreUtil.create(subClass));
				}
			}
		}

		final Collection<EClass> allowedEClasses = eClassSelectionStrategy.collectEClasses(owner, reference,
			new LinkedHashSet<EClass>(descriptors.keySet()));
		descriptors.keySet().retainAll(allowedEClasses);
		final LinkedHashSet<Template> result = new LinkedHashSet<Template>();
		for (final Map.Entry<EClass, EObject> next : descriptors.entrySet()) {
			result.add(createTemplate(next.getKey(), next.getValue()));
		}
		return result;
	}

	/**
	 * Creates a blank template for the given type.
	 *
	 * @param type the type of the {@code instance} to wrap in a template
	 * @param instance the instance of the template {@code type}
	 * @return The blank {@link Template}
	 */
	protected Template createTemplate(EClass type, EObject instance) {
		final Template template = DataTemplateFactory.eINSTANCE.createTemplate();
		template.setInstance(instance);

		final String nameTemplate = localizationService.getString(BlankTemplateProvider.class,
			MessageKeys.BlankTemplateProvider_blankTemplateLabel);
		template.setName(NLS.bind(nameTemplate, getDisplayName(type)));

		return template;
	}

	/**
	 * Set the {@link BundleResolver}.
	 *
	 * @param bundleResolver The {@link BundleResolver}
	 */
	protected void setBundleResolver(BundleResolver bundleResolver) {
		this.bundleResolver = bundleResolver;
	}

	/**
	 * Fetch the display name for the given {@link EClass}.
	 *
	 * @param type the {@link EClass} to get the display name for
	 * @return the display name
	 */
	protected String getDisplayName(EClass type) {
		try {
			final Bundle editBundle = bundleResolver.getEditBundle(type);
			return localizationService.getString(editBundle, String.format("_UI_%s_type", type.getName())); //$NON-NLS-1$
		} catch (final NoBundleFoundException ex) {
			// Do nothing, the fall back of using the type's name will be used
		}

		return type.getName();

	}
}
