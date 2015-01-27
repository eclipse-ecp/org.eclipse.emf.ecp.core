/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.edit.internal.swt.controls.ControlMessages;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapCellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author Eugen
 * @author Johannes
 */
public class TablePOJO {

	private static final String ICON_ADD = "icons/add.png"; //$NON-NLS-1$

	final LabelService labelService;
	final VTableControl tableControl;
	final TooltipModifier tooltipModifier;
	final DatabindingService databindingService;
	final TemplateService templateService;

	public TablePOJO(VTableControl tableControl, LabelService labelService, TooltipModifier tooltipModifier,
		DatabindingService databindingService, TemplateService templateService) {
		if (tableControl == null) {
			throw new IllegalArgumentException("TableControl may not be null"); //$NON-NLS-1$
		}
		this.tableControl = tableControl;
		if (labelService == null) {
			throw new IllegalArgumentException("LabelTextService may not be null"); //$NON-NLS-1$
		}
		this.labelService = labelService;
		if (tooltipModifier == null) {
			throw new IllegalArgumentException("TooltipModifier may not be null"); //$NON-NLS-1$
		}
		this.tooltipModifier = tooltipModifier;
		if (databindingService == null) {
			throw new IllegalArgumentException("DatabindingService must not be null!"); //$NON-NLS-1$
		}
		this.databindingService = databindingService;
		if (templateService == null) {
			throw new IllegalArgumentException("TemplateService may not be null"); //$NON-NLS-1$
		}
		this.templateService = templateService;
	}

	public SWTGridDescription getGridDescription(AbstractSWTRenderer<VTableControl> renderer) {
		if (renderer == null)
		{
			throw new IllegalArgumentException("Renderer must not be null!"); //$NON-NLS-1$
		}
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, renderer);
	}

	public Composite render(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		createTitle(composite);
		createTableComposite(composite);

		return composite;
	}

	void createTitle(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		createTitleLabel(composite);
		createValidationLabel(composite);
		createButtons(composite);
	}

	void createTitleLabel(Composite composite) {
		final Label label = new Label(composite, SWT.NONE);
		final String labelText = labelService.getLabelText(tableControl);
		label.setText(labelText);
	}

	void createValidationLabel(Composite composite) {
		final Label validation = new Label(composite, SWT.NONE);

		// tooltip binding
		final IObservableValue diagnosticObserve = createValidationTooltipModelObs();
		final IObservableValue tooltipObservable = createValidationTooltipTargetObs(validation);
		final EMFDataBindingContext bindingContext = new EMFDataBindingContext();
		bindValue(bindingContext, tooltipObservable, diagnosticObserve, null,
			new ValidationLabelTooltipModelToTargetStrategy(tooltipModifier));

		// TODO icon binding
	}

	// TODO move to micro service / helper class? asserts from tests can be moved
	IObservableValue createValidationTooltipModelObs() {
		return EMFObservables.observeValue(tableControl, VViewPackage.eINSTANCE.getElement_Diagnostic());
	}

	// TODO move to micro service / helper class? asserts from tests can be moved
	IObservableValue createValidationTooltipTargetObs(Label validation) {
		return SWTObservables.observeTooltipText(validation);
	}

	// TODO move to micro service / helper class? untested here
	void bindValue(DataBindingContext bindingContext, IObservableValue targetObservableValue,
		IObservableValue modelObservableValue, UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
		bindingContext.bindValue(targetObservableValue, modelObservableValue, targetToModel, modelToTarget);
	}

	void createButtons(Composite parent) {
		final Composite buttonComposite = new Composite(parent, SWT.NONE);
		createAddButton(buttonComposite);
		createRemoveButton(buttonComposite);
		addCustomButtons(buttonComposite);
	}

	void createAddButton(Composite buttonComposite) {
		final Button addButton = new Button(buttonComposite, SWT.PUSH);

		// add image
		final Image image = templateService.getAddIcon();
		addButton.setImage(image);

		// add tooltip
		final IObservableList observableList = databindingService.getObservableList(tableControl
			.getDomainModelReference());
		final EReference multiReference = EReference.class.cast(observableList.getElementType());
		final EClass eClass = multiReference.getEReferenceType();
		final String instanceName = eClass.getInstanceClass() == null ? "" : eClass.getInstanceClass().getSimpleName(); //$NON-NLS-1$
		addButton.setToolTipText(String.format(ControlMessages.TableControl_AddInstanceOf, instanceName));

		// check enablement //TODO this check also needed after add
		// if (multiReference.getUpperBound() != -1
		// && observableList.size() >= multiReference.getUpperBound()) {
		// addButton.setEnabled(false);
		// }

		// add listener
	}

	void createRemoveButton(Composite buttonComposite) {
		final Button removeButton = new Button(buttonComposite, SWT.PUSH);
	}

	/**
	 * Here you can add your own additional buttons to the buttons bar.
	 *
	 * @param parent The Composite to add your stuff to
	 */
	protected void addCustomButtons(Composite parent) {
		// empty implementation intentionally
	}

	void createTableComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		createTable(composite);
	}

	void createTable(Composite parent) {
		final TableViewer tableViewer = new TableViewer(parent, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION
			| SWT.BORDER);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		final ObservableListContentProvider cp = new ObservableListContentProvider();
		tableViewer.setContentProvider(cp);
		// TODO dmr null check where/who?
		final IObservableList list = databindingService.getObservableList(tableControl.getDomainModelReference());
		final EReference eReference = EReference.class.cast(list.getElementType());
		final VTableDomainModelReference tableDMR = (VTableDomainModelReference) tableControl.getDomainModelReference();
		for (final VDomainModelReference dmr : tableDMR.getColumnDomainModelReferences()) {

			final IValueProperty valueProperty = databindingService.getValueProperty(eReference, dmr);

			final TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
			column.getColumn().setText(labelService.getLabelText(eReference.getEReferenceType(), dmr));
			column.getColumn().setToolTipText(labelService.getDescriptionText(eReference.getEReferenceType(), dmr));
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(false);

			column.setLabelProvider(new ObservableMapCellLabelProvider(
				valueProperty.observeDetail(cp.getKnownElements())));

			final EditingSupport editingSupport = new InlineEditingSupport(tableViewer,
				databindingService.getDataBindingContext(), valueProperty);
			column.setEditingSupport(editingSupport);
		}
		tableViewer.setInput(list);
	}

	// TODO move to micro service / helper class? untested here
	final static class ValidationLabelTooltipModelToTargetStrategy extends UpdateValueStrategy {

		private final TooltipModifier tooltipModifier;

		public ValidationLabelTooltipModelToTargetStrategy(TooltipModifier tooltipModifier) {
			this.tooltipModifier = tooltipModifier;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
		 */
		@Override
		public Object convert(Object value) {
			value = super.convert(value);
			final VDiagnostic diagnostic = (VDiagnostic) value;
			final String message = diagnostic.getMessage();
			return tooltipModifier.modify(message);
		}
	}

}
