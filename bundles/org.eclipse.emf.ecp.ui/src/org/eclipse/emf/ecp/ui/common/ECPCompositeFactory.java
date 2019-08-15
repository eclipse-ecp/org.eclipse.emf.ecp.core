/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.internal.ui.composites.AddRepositoryCompositeImpl;
import org.eclipse.emf.ecp.internal.ui.composites.CheckoutProjectCompositeImpl;
import org.eclipse.emf.ecp.internal.ui.composites.CreateProjectCompositeImpl;
import org.eclipse.emf.ecp.internal.ui.composites.ECPProjectSelectionModelClassCompositeImpl;
import org.eclipse.emf.ecp.spi.common.ui.CheckedModelClassComposite;
import org.eclipse.emf.ecp.spi.common.ui.composites.CheckedEStructuralFeatureComposite;
import org.eclipse.emf.ecp.spi.common.ui.composites.CheckedEStructuralFeatureCompositeImpl;
import org.eclipse.emf.ecp.spi.common.ui.composites.CheckedSelectModelClassCompositeImpl;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectModelClassCompositeImpl;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * @author Eugen Neufeld
 *
 */
public final class ECPCompositeFactory {

	private ECPCompositeFactory() {
	}

	/**
	 *
	 * @return a {@link AddRepositoryComposite}
	 */
	public static AddRepositoryComposite getAddRepositoryComposite() {
		return new AddRepositoryCompositeImpl(null);
	}

	/**
	 *
	 * @param provider the provider to add a repository to.
	 * @return a {@link AddRepositoryComposite}
	 */
	public static AddRepositoryComposite getAddRepositoryComposite(ECPProvider provider) {
		return new AddRepositoryCompositeImpl(provider);
	}

	/**
	 *
	 * @param checkoutSource the source to check a project out from
	 * @return an {@link CheckoutProjectComposite} allowing to check out a project
	 */
	public static CheckoutProjectComposite getCheckoutProjectComposite(ECPCheckoutSource checkoutSource) {
		return new CheckoutProjectCompositeImpl(checkoutSource);
	}

	/**
	 * Util method to create a composite which allows to ceate an ECP project.
	 *
	 * @param providers the providers to be considered to create a new project
	 * @return A {@link CreateProjectComposite} allowing to create a new project
	 */
	public static CreateProjectComposite getCreateProjectComposite(List<ECPProvider> providers) {
		return new CreateProjectCompositeImpl(providers);
	}

	/**
	 * Util method to create a composite which allows to select Classes from {@link EPackage}s with a checkbox.
	 *
	 * @param ePackages The {@link EPackage}s to select from.
	 * @return The {@link CheckedModelClassComposite}
	 */
	public static CheckedModelClassComposite getCheckedModelClassComposite(Collection<EPackage> ePackages) {
		return new CheckedSelectModelClassCompositeImpl(new HashSet<EPackage>(), ePackages, new HashSet<EClass>());
	}

	/**
	 * Util method to create a composite which allows to select an {@link EClass} based on all known {@link EPackage}s
	 * in the registry by applying the provided filters.
	 *
	 * @param unsupportedEPackages The {@link EPackage}s that should not be available (blacklist)
	 * @param filteredEPackages The {@link EPackage}s that should be available (whitelist)
	 * @param filteredEClasses The {@link EClass}es that should be available (whitelist)
	 * @return The {@link SelectionComposite} based on a {@link TreeViewer}
	 */
	public static SelectionComposite<TreeViewer> getSelectModelClassComposite(
		Collection<EPackage> unsupportedEPackages, Collection<EPackage> filteredEPackages,
		Collection<EClass> filteredEClasses) {
		return new SelectModelClassCompositeImpl(unsupportedEPackages, filteredEPackages, filteredEClasses);
	}

	/**
	 * Util method to create a composite which allows to select an {@link EClass} based on all known {@link EPackage}s
	 * in the registry by applying the filters in the provided project.
	 * 
	 * @param project The {@link ECPProject} to use as a filter
	 * @return The {@link SelectionComposite} based on a {@link TreeViewer}
	 */
	public static SelectionComposite<TreeViewer> getSelectModelClassComposite(ECPProject project) {
		return new ECPProjectSelectionModelClassCompositeImpl(project);
	}

	/**
	 * Creates a {@link org.eclipse.emf.ecp.spi.common.ui.CompositeProvider CompositeProvider} for a composite
	 * displaying a table with checkboxes.
	 *
	 * @param rootObject the viewer input
	 * @return the composite provider
	 */
	public static CheckedEStructuralFeatureComposite getCheckedTableSelectionComposite(Object rootObject) {
		return new CheckedEStructuralFeatureCompositeImpl(rootObject);
	}

}
