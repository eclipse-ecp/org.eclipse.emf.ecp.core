/*******************************************************************************
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
 * Lcuas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * <p>
 * A view service that converts all {@link VDomainModelReference domain model references} of a view context to
 * {@link VDomainModelReferenceSegment segments}. The generated segments are added to their respective DMRs.
 * </p>
 * <p>
 * <strong>Note:</strong> This behavior can be activated by setting the program argument
 * {@link org.eclipse.emfforms.spi.core.services.segments.RuntimeModeUtil#SEGMENT_GENERATION -enableSegmentGeneration}.
 * </p>
 *
 * @author Lucas Koehler
 *
 */
public class DmrToSegmentsViewService implements ModelChangeAddRemoveListener {

	private final EMFFormsSegmentGenerator segmentGenerator;

	/**
	 * Creates a new instance.
	 *
	 * @param viewContext The {@link EMFFormsViewContext} that created me
	 */
	public DmrToSegmentsViewService(EMFFormsViewContext viewContext) {
		viewContext.registerViewChangeListener(this);
		segmentGenerator = viewContext.getService(EMFFormsSegmentGenerator.class);

		final VElement view = viewContext.getViewModel();
		// We need to read the dmrs into a separate list because we add the segments in place and this might break the
		// iteration of the TreeIterator returned by view.eAllContents().
		final List<VDomainModelReference> allDmrs = new LinkedList<>();
		view.eAllContents().forEachRemaining(object -> {
			if (object instanceof VDomainModelReference) {
				allDmrs.add((VDomainModelReference) object);
			}
		});
		allDmrs.forEach(this::addSegmentsIfNecessary);
	}

	/**
	 * Checks whether a {@link VDomainModelReference} already uses {@link VDomainModelReferenceSegment segments}. If it
	 * does not, generate the segments and add them to the DMR.
	 *
	 * @param dmr The {@link VDomainModelReference DMR} to check and add the segments to if applicable
	 */
	private void addSegmentsIfNecessary(VDomainModelReference dmr) {
		if (!dmr.getSegments().isEmpty()) {
			// DMRs that already use segments must not be changed
			return;
		}
		final List<VDomainModelReferenceSegment> segments = segmentGenerator.generateSegments(dmr);
		dmr.getSegments().addAll(segments);
	}

	@Override
	public void notifyChange(ModelChangeNotification notification) {
		// Nothing to do
	}

	@Override
	public void notifyAdd(Notifier notifier) {
		if (notifier instanceof VDomainModelReference) {
			addSegmentsIfNecessary((VDomainModelReference) notifier);
		}
	}

	@Override
	public void notifyRemove(Notifier notifier) {
		// Nothing to do
	}
}
