/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 * Lucas Koehler - Refactoring to delegate to customizable suppliers
 */
package org.eclipse.emf.ecp.view.template.service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.spi.view.template.service.ViewTemplateSupplier;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTTemplateFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationFactory;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Implementation of the VTViewTemplateProvider. This implementations uses {@link ViewTemplateSupplier} to offer an
 * extensible mechanism of gathering {@link VTStyleProperty VTStyleProperties} from different sources.
 *
 * @author Eugen Neufeld
 *
 */
@Component(name = "viewTemplate", service = VTViewTemplateProvider.class)
public class ViewTemplateProviderImpl implements VTViewTemplateProvider {

	private final List<ViewTemplateSupplier> templateSuppliers = new LinkedList<ViewTemplateSupplier>();
	private ReportService reportService;

	/**
	 * Adds a {@link ViewTemplateSupplier}.
	 *
	 * @param viewTemplateSupplier The {@link ViewTemplateSupplier}
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.STATIC)
	void addViewTemplateSupplier(ViewTemplateSupplier viewTemplateSupplier) {
		templateSuppliers.add(viewTemplateSupplier);
	}

	/**
	 * Removes a {@link ViewTemplateSupplier}.
	 *
	 * @param viewTemplateSupplier The {@link ViewTemplateSupplier}
	 */
	void removeViewTemplateSupplier(ViewTemplateSupplier viewTemplateSupplier) {
		templateSuppliers.remove(viewTemplateSupplier);
	}

	/**
	 * Set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation returns a new {@link VTViewTemplate} merged from all view templates known to the
	 * {@link ViewTemplateSupplier ViewTemplateSuppliers}. If multiple view templates provide a
	 * {@link VTControlValidationTemplate}, it is not specified which one is used.
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider#getViewTemplate()
	 * @return The merged {@link VTViewTemplate} which might be empty but never <code>null</code>.
	 */
	@Deprecated
	@Override
	public VTViewTemplate getViewTemplate() {
		final VTViewTemplate resultTemplate = VTTemplateFactory.eINSTANCE.createViewTemplate();
		final Set<String> referencedEcores = new HashSet<String>();
		for (final ViewTemplateSupplier supplier : templateSuppliers) {
			for (final VTViewTemplate viewTemplate : supplier.getViewTemplates()) {
				referencedEcores.addAll(viewTemplate.getReferencedEcores());
				// last set control validation configuration wins if there are multiple
				if (viewTemplate.getControlValidationConfiguration() != null) {
					resultTemplate.setControlValidationConfiguration(viewTemplate.getControlValidationConfiguration());
				}
				resultTemplate.getStyles().addAll(viewTemplate.getStyles());
			}
		}
		resultTemplate.getReferencedEcores().addAll(referencedEcores);
		return resultTemplate;
	}

	@Override
	public Set<VTStyleProperty> getStyleProperties(VElement vElement, ViewModelContext viewModelContext) {
		final Map<VTStyleProperty, Double> properties = new LinkedHashMap<VTStyleProperty, Double>();
		for (final ViewTemplateSupplier supplier : templateSuppliers) {
			final Map<VTStyleProperty, Double> supplierProperties = supplier.getStyleProperties(vElement,
				viewModelContext);
			/*
			 * Legacy support: Generate Validation Style Properties for Control Validation Templates and add them with a
			 * lower specificity than a View Model Element Selector => If there is any applicable validation style
			 * property, it is higher ranked than any legacy one.
			 */
			for (final VTStyleProperty generated : generateValidationStyleProperties(supplier.getViewTemplates())) {
				supplierProperties.put(generated, 1d);
			}

			for (final VTStyleProperty styleProperty : supplierProperties.keySet()) {
				final double specificity = supplierProperties.get(styleProperty);
				// a new property -> add it
				final VTStyleProperty savedStyleProperty = getSavedStyleProperty(styleProperty, properties.keySet());
				if (savedStyleProperty == null) {
					reportConflictingProperties(styleProperty, specificity, properties);
					properties.put(styleProperty, specificity);
					continue;
				}
				// the old property is less specific as the new -> remove it and add the higher rated one
				if (properties.get(savedStyleProperty) < specificity) {
					properties.remove(savedStyleProperty);
					properties.put(styleProperty, specificity);
				}
			}
		}

		// Returned properties should be ordered by specificity
		final Set<VTStyleProperty> treeSet = new TreeSet<VTStyleProperty>(new Comparator<VTStyleProperty>() {
			@Override
			public int compare(VTStyleProperty o1, VTStyleProperty o2) {
				final Double specificity1 = properties.get(o1);
				final Double specificity2 = properties.get(o2);

				/* higher value comes before lower value */
				final int result = specificity2.compareTo(specificity1);
				if (result != 0) {
					return result;
				}
				return o1.toString().compareTo(o2.toString());
			}
		});
		treeSet.addAll(properties.keySet());
		return treeSet;
	}

	/**
	 * Generates a {@link VTValidationStyleProperty} for every {@link VTControlValidationTemplate} contained in a given
	 * {@link VTViewTemplate}. Thereby, all configured validation styles are copied to the newly created style property.
	 *
	 * @param viewTemplates The {@link VTViewTemplate VTViewTemplates} potentially containing a
	 *            {@link VTControlValidationTemplate}.
	 * @return The set of generated {@link VTValidationStyleProperty VTValidationStyleProperties} or an empty set; never
	 *         <code>null</code>
	 */
	@SuppressWarnings("deprecation")
	private Set<VTStyleProperty> generateValidationStyleProperties(Collection<VTViewTemplate> viewTemplates) {
		final LinkedHashSet<VTStyleProperty> result = new LinkedHashSet<VTStyleProperty>();
		for (final VTViewTemplate template : viewTemplates) {
			if (template.getControlValidationConfiguration() == null) {
				continue;
			}
			final VTValidationStyleProperty validationProperty = VTValidationFactory.eINSTANCE
				.createValidationStyleProperty();
			final VTControlValidationTemplate validationConfig = template.getControlValidationConfiguration();
			// Copy the values of all attributes configuring the validation styling
			for (final EStructuralFeature configFeature : validationConfig.eClass().getEAttributes()) {
				final EStructuralFeature propertyFeature = validationProperty.eClass()
					.getEStructuralFeature(configFeature.getName());
				validationProperty.eSet(propertyFeature, validationConfig.eGet(configFeature));
			}
			result.add(validationProperty);
		}
		return result;
	}

	/**
	 * Iterate over all given properties and report a conflict for every style which has the same type and
	 * specificity but is not equal to the given current style property.
	 *
	 * @param property The {@link VTStyleProperty} to report conflicts for
	 * @param currentSpecificity The specificity of the given style property
	 * @param properties The map of style properties potentially containing conflicting style properties
	 */
	private void reportConflictingProperties(VTStyleProperty property, double currentSpecificity,
		Map<VTStyleProperty, Double> properties) {
		if (property == null) {
			return;
		}
		if (properties == null) {
			return;
		}
		if (properties.isEmpty()) {
			return;
		}
		for (final VTStyleProperty other : properties.keySet()) {
			if (property.eClass().equals(other.eClass()) && !property.equalStyles(other)
				&& currentSpecificity == properties.get(other).doubleValue()) {
				reportService.report(new AbstractReport(MessageFormat.format(
					"The VTStyleProperty '{0}' with specificity {1} is conflicting with property '{2}'. " //$NON-NLS-1$
						+ "Therefore, it is not determind which of the two properties will be applied.", //$NON-NLS-1$
					property, currentSpecificity, other), IStatus.WARNING));
			}
		}
	}

	private VTStyleProperty getSavedStyleProperty(VTStyleProperty style, Set<VTStyleProperty> properties) {
		if (style == null) {
			return null;
		}
		if (properties == null) {
			return null;
		}
		if (properties.isEmpty()) {
			return null;
		}
		for (final VTStyleProperty styleProperty : properties) {
			if (!styleProperty.getClass().equals(style.getClass())) {
				continue;
			}
			if (styleProperty.equalStyles(style)) {
				return styleProperty;
			}
		}
		return null;
	}

	@Deprecated
	@Override
	public boolean hasControlValidationTemplate() {
		for (final ViewTemplateSupplier supplier : templateSuppliers) {
			for (final VTViewTemplate viewTemplate : supplier.getViewTemplates()) {
				if (viewTemplate.getControlValidationConfiguration() != null) {
					return true;
				}
			}
		}
		return false;
	}

}
