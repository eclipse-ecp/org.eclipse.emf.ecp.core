/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 * Christian W. Damus - bug 529138
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;

import java.util.LinkedHashSet;
import java.util.Set;

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
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
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
		for (final TemplateProvider provider : templateProviders) {
			if (!provider.canProvideTemplates(eObject, eReference)) {
				continue;
			}
			Set<Template> provideTemplates;
			if (provider instanceof BlankTemplateProvider) {
				final EClassSelectionStrategy eClassSelectionStrategy = ReferenceStrategyUtil
					.createDynamicEClassSelectionStrategy(eclassSelectionStrategyBazaar, context);
				provideTemplates = ((BlankTemplateProvider) provider).provideTemplates(eObject, eReference,
					eClassSelectionStrategy);
			} else {
				provideTemplates = provider.provideTemplates(eObject, eReference);
			}
			templates.addAll(provideTemplates);

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
	 * The actual {@link CreateNewModelElementStrategy strategy} that creates a new element based on a template selected
	 * by the user.
	 *
	 * @author Lucas Koehler
	 */
	private class Strategy implements CreateNewModelElementStrategy {
		private final EClassSelectionStrategy classSelectionStrategy;

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
}
