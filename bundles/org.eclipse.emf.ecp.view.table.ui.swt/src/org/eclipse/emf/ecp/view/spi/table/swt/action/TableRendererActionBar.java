/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfiguration;
import org.eclipse.emfforms.spi.swt.table.action.TableActionBar;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A customized action bar for the table renderer.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public class TableRendererActionBar extends TableActionBar<AbstractTableViewer> {

	/**
	 * A map of historic element IDs used for UI testing.
	 */
	private static final Map<String, String> LEGACY_ELEMENT_IDS;
	static {
		final Map<String, String> tmp = new LinkedHashMap<String, String>();
		tmp.put(AddRowAction.ACTION_ID, "table_add"); //$NON-NLS-1$
		tmp.put(RemoveRowAction.ACTION_ID, "table_remove"); //$NON-NLS-1$
		tmp.put(MoveRowUpAction.ACTION_ID, "table_moveUp"); //$NON-NLS-1$
		tmp.put(MoveRowDownAction.ACTION_ID, "table_moveDown"); //$NON-NLS-1$
		LEGACY_ELEMENT_IDS = Collections.unmodifiableMap(tmp);
	}

	private final ViewModelContext viewModelContext;
	private final TableRendererViewerActionContext context;

	/**
	 * The constructor.
	 *
	 * @param context the {@link ViewerActionContext} to use
	 * @param configuration the {@link ActionConfiguration} to use
	 * @param viewModelContext the {@link ViewModelContext}
	 */
	public TableRendererActionBar(
		TableRendererViewerActionContext context, ActionConfiguration configuration,
		ViewModelContext viewModelContext) {
		super(configuration);
		this.context = context;
		this.viewModelContext = viewModelContext;
	}

	@Override
	public void fillComposite(Composite composite, AbstractTableViewer viewer) {
		super.fillComposite(composite, viewer);
		addLegacyElementData();
	}

	@Override
	public void addControl(String id, Control control) {
		super.addControl(id, control);
		addElementData(id, control);
	}

	private void addElementData(String actionId, Control control) {
		SWTDataElementIdHelper.setElementIdDataWithSubId(
			control, context.getVElement(), actionId, viewModelContext);
	}

	/**
	 * Add legacy non-qualified SWT element data objects to maintain backward compatibility.
	 */
	private void addLegacyElementData() {
		for (final Entry<String, String> legacyMapping : LEGACY_ELEMENT_IDS.entrySet()) {
			final Optional<Control> control = getControlById(legacyMapping.getKey());
			if (control.isPresent()) {
				addElementData(legacyMapping.getValue(), control.get());
			}
		}
	}

}
