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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * This view service searches a view model for a control annotated with <code>showChildDomainModelReferences</code>.
 * If the legacy mode is used, the control's dmr is changed to point to the table dmr's list of column dmrs.
 *
 * @author Lucas Koehler
 *
 */
public class TableColumnsDmrViewService {

	/**
	 * Key of the annotation that defines that the child dmrs of a dmr's multi segment should be rendered.
	 */
	private static final String SHOW_CHILD_DOMAIN_MODEL_REFERENCES = "showChildDomainModelReferences"; //$NON-NLS-1$

	/**
	 * Creates the view service.
	 *
	 * @param viewContext The {@link EMFFormsViewContext} for which the service is instantiated
	 */
	public TableColumnsDmrViewService(EMFFormsViewContext viewContext) {

		final VElement view = viewContext.getViewModel();
		final List<VAnnotation> annotations = new LinkedList<>();
		view.eAllContents().forEachRemaining(object -> {
			if (object instanceof VAnnotation) {
				final String key = VAnnotation.class.cast(object).getKey();
				if (SHOW_CHILD_DOMAIN_MODEL_REFERENCES.equals(key)) {
					annotations.add((VAnnotation) object);
				}
			}
		});

		final List<VControl> controls = annotations.stream().map(EObject::eContainer).filter(VControl.class::isInstance)
			.map(VControl.class::cast).collect(Collectors.toList());
		for (final VControl control : controls) {
			final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
			dmr.getDomainModelEReferencePath().add(VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE);
			dmr.setDomainModelEFeature(
				VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES);
			control.setDomainModelReference(dmr);
		}
	}
}
