/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.util;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.ModelReferenceHelper;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.internal.swt.table.util.DMREditingSupport;
import org.eclipse.emfforms.spi.swt.table.CellEditorCreator;
import org.eclipse.emfforms.spi.swt.table.EditingSupportCreator;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

/**
 * Util class for creating {@link EditingSupport EditingSupports}.
 *
 * @author Johannes Faltermeier
 *
 */
public final class EditingSupportUtil {

	private EditingSupportUtil() {
	}

	/**
	 * Creates an editing support for the given DMR + {@link org.eclipse.jface.viewers.CellEditor CellEditor}.
	 *
	 * @param domainModelReference the {@link VDomainModelReference}
	 * @param parent the domain model
	 * @param cellEditor the editor
	 * @return the editing support creator
	 */
	public static EditingSupportCreator createEditingSupport(final VDomainModelReference domainModelReference,
		final EObject parent, final CellEditorCreator cellEditor) {
		return new EditingSupportCreator() {
			@Override
			public EditingSupport createEditingSupport(TableViewer columnViewer) {
				return new DMREditingSupport(columnViewer, cellEditor.createCellEditor(columnViewer),
					domainModelReference, parent);
			}
		};
	}

	/**
	 * Creates an editing support for the value at the given path + {@link org.eclipse.jface.viewers.CellEditor
	 * CellEditor}.
	 *
	 * @param feature the feature of the value
	 * @param path the path of the value
	 * @param parent the domain model
	 * @param cellEditor the editor
	 * @return the editing support creator
	 */
	public static EditingSupportCreator createEditingSupport(CellEditorCreator cellEditor, EObject parent,
		EStructuralFeature feature, EReference... path) {
		return createEditingSupport(ModelReferenceHelper.createDomainModelReference(feature, Arrays.asList(path)),
			parent, cellEditor);
	}

}
