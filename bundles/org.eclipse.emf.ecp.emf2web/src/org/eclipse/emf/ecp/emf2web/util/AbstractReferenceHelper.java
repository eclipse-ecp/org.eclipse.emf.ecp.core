/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.emf2web.Activator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * An abstract implementation of {@link ReferenceHelper} using {@link EMFFormsDatabinding}.
 *
 * @author Stefan Dirix
 *
 */
public abstract class AbstractReferenceHelper implements ReferenceHelper {

	private final EMFFormsDatabindingEMF dataBinding;

	/**
	 * Constructor.
	 */
	public AbstractReferenceHelper() {
		dataBinding = Activator.getDefault().getEMFFormsDatabinding();
	}

	/**
	 * Returns the {@link EStructuralFeature} for the given {@link VDomainModelReference}.
	 *
	 * @param reference
	 *            The {@link VDomainModelReference} for which the {@link EStructuralFeature} is to be determined.
	 * @return
	 * 		The determined {@link EStructuralFeature}.
	 */
	protected EStructuralFeature getEStructuralFeature(VDomainModelReference reference) {
		try {
			final IValueProperty<?, ?> valueProperty = dataBinding.getValueProperty(reference,
				getRootEClass(reference));

			if (valueProperty != null) {
				return (EStructuralFeature) valueProperty.getValueType();
			}
		} catch (final DatabindingFailedException ex) {
			handleDatabindingFailedException(ex);
		}
		return null;
	}

	/**
	 * Get the root {@link EClass} by walking up the containment hierarchy of the given EObject (typically a DMR or
	 * control).
	 *
	 * @param eObject The {@link EObject} whose containment hierarchy is searched for a {@link VView}
	 * @return The root EClass or <code>null</code> if the given EObject is not contained in a {@link VView}
	 */
	private static EClass getRootEClass(EObject eObject) {
		EObject parent = eObject.eContainer();
		while (parent != null) {
			if (parent instanceof VView) {
				return ((VView) parent).getRootEClass();
			}
			parent = parent.eContainer();
		}
		return null;
	}

	/**
	 * Handle {@link DatabindingFailedException}s by reporting them to the ECP ReportService.
	 *
	 * @param exception
	 *            The handled {@link DatabindingFailedException}.
	 */
	protected void handleDatabindingFailedException(DatabindingFailedException exception) {
		Activator.getDefault().getReportService().report(new DatabindingFailedReport(exception));
	}

	private static final String DISPLAY_NAME = "_UI_%1$s_%2$s_feature"; //$NON-NLS-1$

	@Override
	public String getLabel(VDomainModelReference reference) {
		final Set<String> paths = getEcorePaths();
		if (paths == null || paths.isEmpty()) {
			try {
				final BundleContext bundleContext = Activator.getDefault().getBundleContext();
				final ServiceReference<EMFFormsLabelProvider> serviceReference = bundleContext
					.getServiceReference(EMFFormsLabelProvider.class);
				final EMFFormsLabelProvider labelProvider = bundleContext.getService(serviceReference);
				final IObservableValue<?> observableValue = labelProvider.getDisplayName(reference,
					getRootEClass(reference));
				final String result = (String) observableValue.getValue();
				observableValue.dispose();
				bundleContext.ungetService(serviceReference);
				return result;
			} catch (final NoLabelFoundException ex) {
				Activator.getDefault().getReportService().report(new AbstractReport(ex));
				return ""; //$NON-NLS-1$
			}
		}
		for (final String path : paths) {
			// try to find edit bundle
			final String firstPath = path.split("/")[1]; //$NON-NLS-1$
			final String editPath = firstPath + ".edit/plugin.properties"; //$NON-NLS-1$
			final IResource member = ResourcesPlugin.getWorkspace().getRoot().findMember(editPath);
			if (member != null && member.exists()) {
				final File file = member.getLocation().toFile();
				final Properties p = new Properties();
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					p.load(fis);
				} catch (final FileNotFoundException ex) {
					Activator.getDefault().getReportService().report(new AbstractReport(ex));
				} catch (final IOException ex) {
					Activator.getDefault().getReportService().report(new AbstractReport(ex));
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (final IOException ex) {
							Activator.getDefault().getReportService().report(new AbstractReport(ex));
						}
					}
				}

				final EStructuralFeature feature = getEStructuralFeature(reference);
				if (feature == null) {
					continue;
				}
				final EClass eClass = feature.getEContainingClass();
				final String key = String.format(DISPLAY_NAME, eClass.getName(), feature.getName());
				final String result = p.getProperty(key);
				if (result == null) {
					return feature.getName();
				}
				return result;
			}
		}
		final EStructuralFeature feature = getEStructuralFeature(reference);
		return feature.getName();
	}

	/**
	 * Returns the ecore paths of the current view model.
	 *
	 * @return The paths to the ecores of the current view
	 */
	protected abstract Set<String> getEcorePaths();
}
