/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.section.swt;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;

/**
 * Renderer for {@link VSection} with child items.
 *
 * @author jfaltermeier
 *
 */
public class SectionNodeSWTRenderer extends AbstractSectionSWTRenderer {

	private Set<AbstractSectionSWTRenderer> childRenderers;
	private SWTGridDescription rendererGridDescription;
	private ModelChangeListener listener;
	private ExpandableComposite expandableComposite;

	@Override
	protected void preInit() {
		super.preInit();
		listener = new ModelChangeListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getRawNotification().isTouch()) {
					return;
				}
				if (notification.getNotifier() != getVElement()) {
					return;
				}
				if (notification.getStructuralFeature() == VSectionPackage.eINSTANCE
					.getSection_Collapsed()) {
					handleCollapseState();
				}
			}
		};
		getViewModelContext().registerViewChangeListener(listener);
	}

	@Override
	public SWTGridDescription getGridDescription(
		SWTGridDescription gridDescription) {
		childRenderers = new LinkedHashSet<AbstractSectionSWTRenderer>();
		rendererGridDescription = new SWTGridDescription();
		rendererGridDescription.setColumns(4);
		final List<SWTGridCell> gridCells = new ArrayList<SWTGridCell>();

		/* add self */
		gridCells.add(createGridCell(0, 0, this));
		gridCells.add(createGridCell(0, 1, this));
		gridCells.add(createGridCell(0, 2, this));
		gridCells.add(createGridCell(0, 3, this));

		/* add children */
		int row = 1;
		for (final VSection item : getVElement().getChildItems()) {
			final AbstractSWTRenderer<?> itemRenderer = getSWTRendererFactory()
				.getRenderer(item, getViewModelContext());
			final SWTGridDescription itemGridDescription = itemRenderer
				.getGridDescription(GridDescriptionFactory.INSTANCE
					.createEmptyGridDescription());
			childRenderers.add((AbstractSectionSWTRenderer) itemRenderer);

			final int itemRows = itemGridDescription.getRows();
			for (final SWTGridCell itemCell : itemGridDescription.getGrid()) {
				gridCells.add(createGridCell(itemCell.getRow() + row,
					itemCell.getColumn(), itemCell.getRenderer()));
			}
			row = row + itemRows;
		}

		rendererGridDescription.setGrid(gridCells);
		return rendererGridDescription;
	}

	private SWTGridCell createGridCell(int row, int column,
		AbstractSWTRenderer<? extends VElement> renderer) {
		final SWTGridCell gridCell = new SWTGridCell(row, column, renderer);
		gridCell.setVerticalFill(false);
		gridCell.setVerticalGrab(false);
		if (column == 3) {
			gridCell.setHorizontalGrab(true);
		} else {
			gridCell.setHorizontalGrab(false);
		}
		return gridCell;

	}

	@Override
	protected Control createFirstColumn(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1)
			.extendedMargins(computeLeftMargin(), 0, 0, 0)
			.applyTo(composite);

		expandableComposite = new ExpandableComposite(
			composite, SWT.NONE, ExpandableComposite.TWISTIE);
		expandableComposite.setExpanded(!getVElement().isCollapsed());
		final String text = getVElement().getName() == null ? "" //$NON-NLS-1$
			: getVElement().getName();
		expandableComposite.setText(text);
		initExpandableComposite(expandableComposite);

		return composite;
	}

	private void initExpandableComposite(ExpandableComposite expandableComposite) {
		expandableComposite.addExpansionListener(new IExpansionListener() {

			@Override
			public void expansionStateChanging(ExpansionEvent e) {
			}

			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				getVElement().setCollapsed(!e.getState());
			}
		});
	}

	private int computeLeftMargin() {
		int numberOfParents = 0;
		EObject current = getVElement().eContainer();
		while (!VSectionedArea.class.isInstance(current)) {
			numberOfParents++;
			current = current.eContainer();
		}
		return (numberOfParents + 1) * 8;
	}

	@Override
	protected void adjustLayoutData(boolean vis) {
		super.adjustLayoutData(vis);
		for (final AbstractSectionSWTRenderer childRenderer : childRenderers) {
			boolean visible = vis;
			if (getVElement().isCollapsed()) {
				visible = false;
			}
			childRenderer.adjustLayoutData(visible);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#applyEnable()
	 */
	@Override
	protected void applyEnable() {
		expandableComposite.setEnabled(getVElement().isEnabled());
	}

	@Override
	protected void dispose() {
		getViewModelContext().unregisterViewChangeListener(listener);
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.section.swt.AbstractSectionSWTRenderer#initCollapseState()
	 */
	@Override
	protected void initCollapseState() {
		handleCollapseState();
	}

	private void handleCollapseState() {
		for (final AbstractSectionSWTRenderer childRenderer : childRenderers) {
			childRenderer.adjustLayoutData(!getVElement()
				.isCollapsed());
		}
		getControls().values().iterator().next().getParent()
			.layout(false);
		expandableComposite.setExpanded(!getVElement()
			.isCollapsed());
	}

}
