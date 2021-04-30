/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
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
package org.eclipse.emfforms.swt.internal.reference.table;

import static java.util.Collections.singleton;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.ui.view.swt.reference.SelectionCompositeStrategy;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.view.spi.multisegment.model.MultiSegmentUtil;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provider of a selection composite strategy that builds a selection table viewer
 * from a {@linkplain VTableControl table control model}.
 *
 * @since 1.27
 */
@Component(name = "selectionTableCompositeStrategyProvider")
public class SelectionTableCompositeStrategyProvider implements SelectionCompositeStrategy.Provider {

	/**
	 * Filter key for view registration to apply the view to the context of the selection
	 * table composite specifically in the case that some other view is also available
	 * that is intended for the editor. The value of the filter is the name of the reference
	 * feature for which objects are to be selected.
	 */
	public static final String VIEW_FILTER_KEY = "selectionTableComposite"; //$NON-NLS-1$

	private static final Double BID = 1.0;

	private final Map<UniqueSetting, VTableControl> recentlyQueriedTables = new HashMap<>();

	private EMFFormsDatabindingEMF databinding;

	/**
	 * Initializes me.
	 */
	public SelectionTableCompositeStrategyProvider() {
		super();
	}

	/**
	 * Queries my bid on a selection table composite strategy for selection of objects to add
	 * to the given {@code reference} of an {@code owner} object.
	 *
	 * @param owner the owner of the reference to be edited
	 * @param reference the reference to which to add objects
	 *
	 * @return my bid, or {@code null} if I have nothing to offer
	 */
	@Bid
	public Double provides(EObject owner, EReference reference) {
		final UniqueSetting key = UniqueSetting.createSetting(owner, reference);
		final VTableControl table = getTableControl(key);
		if (table != null) {
			recentlyQueriedTables.put(key, table);
		}
		return table != null ? BID : null;
	}

	private VTableControl getTableControl(UniqueSetting key) {
		VTableControl result = recentlyQueriedTables.get(key);
		if (result == null) {
			result = getTableControl(key.getEObject(), key.getEStructuralFeature());
		}
		return result;
	}

	/**
	 * Obtain the view model for selecting objects to add the the {@code reference}
	 * of an {@code owner}.
	 *
	 * @param owner the owner of the {@code reference} being edited
	 * @param feature the reference feature being edited
	 * @return the view model, or {@code null} if there is none
	 */
	protected VTableControl getTableControl(EObject owner, EStructuralFeature feature) {
		VTableControl result = null;

		final VViewModelProperties loadingProperties = getLoadingProperties(owner, feature);

		// Require our filter property to be sure only to get views that are specifically
		// designed for usage in the selection composite
		final VView view = ViewProviderHelper.getView(owner, loadingProperties, singleton(VIEW_FILTER_KEY));

		if (view != null) {
			// It must have a table for our reference
			result = findTableControl(view, owner, feature);
		}

		return result;
	}

	/**
	 * Obtain the view model loading properties for filtering the applicable view models.
	 *
	 * @param owner the owner of the {@code reference} being edited
	 * @param feature the reference feature being edited
	 * @return the view model filter properties
	 */
	protected VViewModelProperties getLoadingProperties(EObject owner, EStructuralFeature feature) {
		final VViewModelLoadingProperties result = VViewFactory.eINSTANCE.createViewModelLoadingProperties();
		result.addNonInheritableProperty(VIEW_FILTER_KEY, feature.getName());
		return result;
	}

	/**
	 * Create my selection table composite strategy for selection of objects to add
	 * to the given {@code reference} of an {@code owner} object.
	 *
	 * @param owner the owner of the reference to be edited
	 * @param reference the reference to which to add objects
	 *
	 * @return my bid, or {@code null} if I have nothing to offer
	 */
	@Create
	public SelectionCompositeStrategy create(EObject owner, EReference reference) {
		SelectionCompositeStrategy result = null;
		final UniqueSetting key = UniqueSetting.createSetting(owner, reference);
		final VTableControl table = getTableControl(key);

		if (table != null) {
			// Don't keep the cache in case the next query would have a different result
			recentlyQueriedTables.remove(key);
			result = strategy(table);
		}

		return result;
	}

	private SelectionCompositeStrategy strategy(VTableControl table) {
		return (owner, reference, extent) -> new TableSelectionCompositeImpl(
			extent, table, owner, reference);
	}

	/**
	 * Search for a table control in the given {@code view} that edits a {@code feature}.
	 *
	 * @param view a view to search
	 * @param owner the owner of the {@code feature} being edited
	 * @param feature the feature to be edited
	 *
	 * @return a table editing the {@code feature}, or {@code null} if none
	 */
	private VTableControl findTableControl(VView view, EObject owner, EStructuralFeature feature) {
		VTableControl result = null;

		for (final Iterator<EObject> iter = view.eAllContents(); result == null && iter.hasNext();) {
			final EObject next = iter.next();
			if (next instanceof VTableControl) {
				final VTableControl table = (VTableControl) next;
				final VDomainModelReference dmr = table.getDomainModelReference();
				if (dmr == null) {
					continue;
				}

				if (dmr.getSegments().isEmpty() && dmr instanceof VTableDomainModelReference) {
					// The TableDMRConverter ignores single-valued references, but we
					// have to let them be specified anyways because it's the only
					// thing that can provide the column DMRs
					final VTableDomainModelReference tdmr = (VTableDomainModelReference) dmr;
					if (tdmr.getDomainModelEFeature() == feature
						|| resolvesTo(tdmr.getDomainModelReference(), owner, feature)) {

						// That's the one
						result = table;
					}
				} else if (!dmr.getSegments().isEmpty() && MultiSegmentUtil.getMultiSegment(dmr).isPresent()) {
					// The MultiSegmentConverter does not handle single-value reference, but we have to let them be
					// specified anyways because it's the only thing that can provide the column DMRs.
					// Copy the dmr and replace the multi segment with a feature segment in the copied version. See if
					// this can be resolved to our target feature.
					final VDomainModelReference copiedDmr = EcoreUtil.copy(dmr);
					final VMultiDomainModelReferenceSegment multiSegment = MultiSegmentUtil.getMultiSegment(copiedDmr)
						.get();
					copiedDmr.getSegments().remove(multiSegment);
					final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
						.createFeatureDomainModelReferenceSegment();
					segment.setDomainModelFeature(multiSegment.getDomainModelFeature());
					copiedDmr.getSegments().add(segment);
					if (resolvesTo(copiedDmr, owner, feature)) {
						result = table;
					}
				} else if (resolvesTo(dmr, owner, feature)) {
					// That's the one
					result = table;
				}
			}
		}

		return result;
	}

	private boolean resolvesTo(VDomainModelReference dmr, EObject owner, EStructuralFeature feature) {
		boolean result = false;

		EStructuralFeature.Setting setting;
		try {
			setting = databinding.getSetting(dmr, owner);
			result = setting != null && setting.getEStructuralFeature() == feature;
		} catch (final DatabindingFailedException e) {
			// This table is of no use to us
		}

		return result;
	}

	//
	// Component lifecycle and dependencies
	//

	/**
	 * Inject my data binding service dependency.
	 *
	 * @param databinding the data binding service to set
	 */
	@Reference(unbind = "-")
	void setDatabinding(EMFFormsDatabindingEMF databinding) {
		this.databinding = databinding;
	}

}
