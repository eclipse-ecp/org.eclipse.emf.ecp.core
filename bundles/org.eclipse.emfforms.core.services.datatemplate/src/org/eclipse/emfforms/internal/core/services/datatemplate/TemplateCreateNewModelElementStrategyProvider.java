/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * lucas - initial API and implementation
 * Christian W. Damus - bugs 529138, 543461, 546974
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategyUtil;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.core.services.datatemplate.TemplateFilterService;
import org.eclipse.emfforms.core.services.datatemplate.TemplateProvider;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.spi.bazaar.BazaarUtil;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * Provides a strategy to the {@link org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService}
 * that allows creating new model elements based on a template with pre-defined values.
 *
 * @author Lucas Koehler
 *
 */
// Ranking as it was for the TemplateReferenceService
@Component(name = "TemplateCreateNewModelElementStrategyProvider", property = "service.ranking:Integer=10")
public class TemplateCreateNewModelElementStrategyProvider
	extends ReferenceServiceCustomizationVendor<CreateNewModelElementStrategy> implements Provider {

	private final Set<TemplateProvider> templateProviders = new LinkedHashSet<TemplateProvider>();
	private EMFFormsLocalizationService localizationService;

	private final Bazaar<EClassSelectionStrategy> eclassSelectionStrategyBazaar = BazaarUtil.createBazaar(
		EClassSelectionStrategy.NULL);
	private final Bazaar<TemplateFilterService> templateFilterBazaar = BazaarUtil.createBazaar(
		TemplateFilterService.NULL);
	private ComponentContext context;

	/**
	 * Activates me.
	 *
	 * @param context my component context
	 */
	@Activate
	void activate(ComponentContext context) {
		this.context = context;
	}

	/**
	 * Deactivates me.
	 */
	@Deactivate
	void deactivate() {
		context = null;
	}

	/**
	 * Add an {@code EClass} selection strategy provider.
	 *
	 * @param provider the provider to add
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	public void addEClassSelectionStrategyProvider(EClassSelectionStrategy.Provider provider) {
		eclassSelectionStrategyBazaar.addVendor(provider);
	}

	/**
	 * Remove an {@code EClass} selection strategy provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeEClassSelectionStrategyProvider(EClassSelectionStrategy.Provider provider) {
		eclassSelectionStrategyBazaar.removeVendor(provider);
	}

	/**
	 * Register a template provider implementation.
	 *
	 * @param templateProvider the {@link TemplateProvider} to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE)
	void registerTemplateProvider(TemplateProvider templateProvider) {
		templateProviders.add(templateProvider);
	}

	/**
	 * Unregister a template provider registration.
	 *
	 * @param templateProvider the {@link TemplateProvider} to remove
	 */
	void unregisterTemplateProvider(TemplateProvider templateProvider) {
		templateProviders.remove(templateProvider);
	}

	/**
	 * Add a template filter service provider.
	 *
	 * @param provider the provider to add
	 *
	 * @since 1.21
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	public void addFilterServiceProvider(TemplateFilterService.Provider provider) {
		templateFilterBazaar.addVendor(provider);
	}

	/**
	 * Remove a template filter service provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeFilterServiceProvider(TemplateFilterService.Provider provider) {
		templateFilterBazaar.removeVendor(provider);
	}

	/**
	 * Called by the framework to set the {@link EMFFormsLocalizationService}.
	 *
	 * @param localizationService The {@link EMFFormsLocalizationService}
	 */
	@Reference
	void setLocalizationService(EMFFormsLocalizationService localizationService) {
		this.localizationService = localizationService;
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		for (final TemplateProvider provider : templateProviders) {
			if (provider.canProvideTemplates(owner, reference)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Collect a list of available templates for the given {@link EReference}.
	 *
	 * @param eObject the parent {@link EObject}
	 * @param eReference the {@link EReference} to find templates for
	 * @return list of available templates
	 */
	protected Set<Template> collectAvailableTemplates(EObject eObject, EReference eReference) {
		final Set<Template> templates = new LinkedHashSet<Template>();

		final Predicate<? super Template> filter = getFilter(eObject, eReference);

		for (final TemplateProvider provider : templateProviders) {
			if (!provider.canProvideTemplates(eObject, eReference)) {
				continue;
			}

			Set<Template> providedTemplates;
			if (provider instanceof BlankTemplateProvider) {
				final EClassSelectionStrategy eClassSelectionStrategy = ReferenceStrategyUtil
					.createDynamicEClassSelectionStrategy(eclassSelectionStrategyBazaar, context);
				providedTemplates = ((BlankTemplateProvider) provider).provideTemplates(eObject, eReference,
					eClassSelectionStrategy);
			} else {
				providedTemplates = provider.provideTemplates(eObject, eReference);
			}

			if (filter == null) {
				templates.addAll(providedTemplates);
			} else {
				providedTemplates.stream().filter(filter).forEach(templates::add);
			}
		}

		return templates;
	}

	/**
	 * Creates the {@link CreateNewModelElementStrategy}.
	 *
	 * @return The created {@link CreateNewModelElementStrategy}
	 */
	@Create
	public CreateNewModelElementStrategy createCreateNewModelElementStrategy() {
		final EClassSelectionStrategy eClassSelectionStrategy = ReferenceStrategyUtil
			.createDynamicEClassSelectionStrategy(eclassSelectionStrategyBazaar, context);
		return new Strategy(eClassSelectionStrategy);
	}

	/**
	 * Obtain a filter predicate for the templates provided for the given {@code reference}
	 * of an {@code owner} object in the editor.
	 *
	 * @param owner the {@link EObject} in the model
	 * @param reference the {@link EReference} in which templates are to be instantiated
	 *
	 * @return the filter, or {@code null} if none
	 *
	 * @since 1.21
	 */
	protected Predicate<? super Template> getFilter(EObject owner, EReference reference) {
		final BazaarContext bazaarContext = ReferenceStrategyUtil.createBazaarContext(context, owner, reference);
		return templateFilterBazaar.createProducts(bazaarContext).stream()
			.map(service -> service.getTemplateFilter(owner, reference))
			.filter(Objects::nonNull)
			.reduce(Predicate::and)
			.orElse(null);
	}

	/**
	 * The actual {@link CreateNewModelElementStrategy strategy} that creates a new element based on a template selected
	 * by the user.
	 *
	 * @author Lucas Koehler
	 */
	class Strategy implements CreateNewModelElementStrategy {
		private final EClassSelectionStrategy classSelectionStrategy;

		/**
		 * Initializes me with a strategy for selecting classes permitted in the particular form context.
		 *
		 * @param classSelectionStrategy my class selection strategy
		 */
		Strategy(final EClassSelectionStrategy classSelectionStrategy) {
			super();
			this.classSelectionStrategy = classSelectionStrategy;
		}

		/**
		 * {@inheritDoc}
		 * <p>
		 * Create the new model element based on a template.
		 */
		@Override
		public Optional<EObject> createNewModelElement(EObject owner, EReference reference) {
			final Set<Template> availableTemplates = collectAvailableTemplates(owner, reference);
			if (availableTemplates.isEmpty()) {
				// This should not happen because in case of no available templates, the provider should not bid during
				// the strategy selection
				return Optional.empty();
			}

			final Set<EClass> availableClasses = new LinkedHashSet<EClass>(
				EMFUtils.getSubClasses(reference.getEReferenceType()));
			final Set<EClass> subClasses = new LinkedHashSet<EClass>(
				classSelectionStrategy.collectEClasses(owner, reference, availableClasses));

			Template selected = availableTemplates.iterator().next();
			if (availableTemplates.size() > 1) {
				// Don't show classes for which we don't have templates (bug 543461)
				final Set<EClass> templateClasses = availableTemplates.stream()
					.map(Template::getInstance).filter(Objects::nonNull).map(ignoreException(EObject::eClass))
					.filter(Objects::nonNull)
					.collect(Collectors.toSet());
				subClasses.retainAll(templateClasses);

				final Optional<Template> selectedElement = showSelectModelInstancesDialog(subClasses,
					availableTemplates);

				if (selectedElement.isPresent()) {
					selected = selectedElement.get();
				} else {
					return Optional.empty();
				}
			}

			return Optional.of(EcoreUtil.copy(selected.getInstance()));
		}

		/**
		 * Show a model instance selection dialog to first select a sub class and then a fitting template.
		 *
		 * @param subClasses the sub classes to choose from
		 * @param availableInstances the instances to choose from
		 * @return the instances selected by the user
		 */
		protected Optional<Template> showSelectModelInstancesDialog(Set<EClass> subClasses,
			Set<Template> availableInstances) {
			final SelectSubclassAndTemplateWizard wizard = new SelectSubclassAndTemplateWizard(
				localizationService.getString(TemplateCreateNewModelElementStrategyProvider.class,
					MessageKeys.TemplateCreateNewModelElementStrategyProvider_wizardTitle),
				subClasses, availableInstances, localizationService);

			final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			wd.open();

			return wizard.getSelectedTemplate();
		}
	}

	// CHECKSTYLE.OFF: IllegalCatch
	private static <T, R> Function<T, R> ignoreException(Function<T, R> function) {
		return t -> {
			try {
				return function.apply(t);
			} catch (final Exception e) {
				// ignore
				return null;
			}
		};
	}
	// CHECKSTYLE.ON: IllegalCatch
}
