/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.controls.Activator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author Alexandra Buzila
 *
 */
public class SelectAttributesWizardPage extends WizardPage {

	private EClass rootClass;
	private AdapterFactoryLabelProvider labelProvider;
	private ComposedAdapterFactory composedAdapterFactory;
	private final Set<EStructuralFeature> selectedFeatures = new LinkedHashSet<EStructuralFeature>();
	private VView view;
	private Composite parent;
	private Composite composite;
	private CheckboxTableViewer tvAttributes;
	private Button bUnreferenced;

	/**
	 * Default constructor.
	 */
	protected SelectAttributesWizardPage() {
		super("Select Attributes"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		this.parent = parent;
		composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		setControl(composite);
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });

		labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);

		bUnreferenced = new Button(composite, SWT.CHECK);
		bUnreferenced.setText("Show only unreferenced Attributes?"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(2, 1)
			.applyTo(bUnreferenced);

		final ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.H_SCROLL | SWT.V_SCROLL
			| SWT.BORDER);
		scrolledComposite.setShowFocusedControl(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(scrolledComposite);
		GridLayoutFactory.fillDefaults().applyTo(scrolledComposite);

		final Composite tableComposite = new Composite(scrolledComposite, SWT.FILL);
		tableComposite.setLayout(GridLayoutFactory.fillDefaults().create());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(tableComposite);
		tableComposite.setBackground(scrolledComposite.getBackground());

		tvAttributes = CheckboxTableViewer.newCheckList(tableComposite, SWT.NONE);
		tvAttributes.setLabelProvider(labelProvider);
		tvAttributes.setContentProvider(ArrayContentProvider.getInstance());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).hint(SWT.DEFAULT, 200)
			.applyTo(tvAttributes.getControl());

		scrolledComposite.setContent(tableComposite);

		final Point point = tableComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledComposite.setMinSize(point);

		tvAttributes.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				final EStructuralFeature object = (EStructuralFeature) event.getElement();
				if (event.getChecked()) {
					selectedFeatures.add(object);

				} else {
					selectedFeatures.remove(object);
				}
				setPageComplete(!selectedFeatures.isEmpty());

			}
		});

		List<EStructuralFeature> attributes = null;
		if (rootClass != null) {
			if (!bUnreferenced.getSelection()) {
				attributes = rootClass.getEAllStructuralFeatures();
			} else {
				attributes = getUnreferencedSegmentAttributes(rootClass);
			}
		}
		tvAttributes.setInput(attributes);

		bUnreferenced.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				List<EStructuralFeature> attributes = null;
				if (rootClass != null) {
					if (!bUnreferenced.getSelection()) {
						attributes = rootClass.getEAllStructuralFeatures();
					} else {
						attributes = getUnreferencedSegmentAttributes(rootClass);
					}
				}
				tvAttributes.setInput(attributes);
			}

		});

		final Composite compositeButtons = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(compositeButtons);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(2, 1)
			.applyTo(compositeButtons);

		final Button bSelectAll = new Button(compositeButtons, SWT.PUSH);
		bSelectAll.setText("Select All"); //$NON-NLS-1$
		bSelectAll.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				@SuppressWarnings("unchecked")
				final List<EStructuralFeature> segments = (List<EStructuralFeature>) tvAttributes.getInput();
				tvAttributes.setAllChecked(true);
				selectedFeatures.addAll(segments);
				setPageComplete(!selectedFeatures.isEmpty());
			}

		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(bSelectAll);
		final Button bDeSelectAll = new Button(compositeButtons, SWT.PUSH);
		bDeSelectAll.setText("Deselect All"); //$NON-NLS-1$
		bDeSelectAll.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				@SuppressWarnings("unchecked")
				final List<EStructuralFeature> segments = (List<EStructuralFeature>) tvAttributes.getInput();
				tvAttributes.setAllChecked(false);
				selectedFeatures.removeAll(segments);
				setPageComplete(!selectedFeatures.isEmpty());
			}

		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(bDeSelectAll);
		parent.layout(true);
		scrolledComposite.layout(true);
	}

	public void onEnterPage() {
		/* if (isCurrentPage()) */ {
			// clear composite
			clear();
			composite = new Composite(parent, SWT.NONE);
			final GridLayout layout = new GridLayout();
			composite.setLayout(layout);
			layout.numColumns = 2;
			setControl(composite);
			composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new ReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });

			labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);

			final Button bUnreferenced = new Button(composite, SWT.CHECK);
			bUnreferenced.setText("Show only unreferenced Attributes?"); //$NON-NLS-1$
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(2, 1)
				.applyTo(bUnreferenced);

			final Label labelAttributes = new Label(composite, SWT.NONE);
			labelAttributes.setText("Select Attributes"); //$NON-NLS-1$

			final ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
			scrolledComposite.setShowFocusedControl(true);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setExpandHorizontal(true);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(scrolledComposite);
			GridLayoutFactory.fillDefaults().applyTo(scrolledComposite);

			final Composite tableComposite = new Composite(scrolledComposite, SWT.FILL);
			tableComposite.setLayout(GridLayoutFactory.fillDefaults().create());
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(tableComposite);
			tableComposite.setBackground(scrolledComposite.getBackground());

			final CheckboxTableViewer tvAttributes = CheckboxTableViewer.newCheckList(tableComposite, SWT.NONE);
			tvAttributes.setLabelProvider(labelProvider);
			tvAttributes.setContentProvider(ArrayContentProvider.getInstance());
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).hint(SWT.DEFAULT, 200)
				.applyTo(tvAttributes.getControl());

			scrolledComposite.setContent(tableComposite);

			final Point point = tableComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			scrolledComposite.setMinSize(point);

			tvAttributes.addCheckStateListener(new ICheckStateListener() {

				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					final EStructuralFeature object = (EStructuralFeature) event.getElement();
					if (event.getChecked()) {
						selectedFeatures.add(object);

					} else {
						selectedFeatures.remove(object);
					}
					setPageComplete(!selectedFeatures.isEmpty());

				}
			});

			List<EStructuralFeature> attributes = null;
			if (!bUnreferenced.getSelection()) {
				attributes = rootClass.getEAllStructuralFeatures();
			} else {
				attributes = getUnreferencedSegmentAttributes(rootClass);
			}
			tvAttributes.setInput(attributes);

			bUnreferenced.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					List<EStructuralFeature> attributes = null;
					if (!bUnreferenced.getSelection()) {
						attributes = rootClass.getEAllStructuralFeatures();
					} else {
						attributes = getUnreferencedSegmentAttributes(rootClass);
					}
					tvAttributes.setInput(attributes);
				}

			});

			final Composite compositeButtons = new Composite(composite, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(compositeButtons);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(2, 1)
				.applyTo(compositeButtons);

			final Button bSelectAll = new Button(compositeButtons, SWT.PUSH);
			bSelectAll.setText("Select All"); //$NON-NLS-1$
			bSelectAll.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					@SuppressWarnings("unchecked")
					final List<EStructuralFeature> segments = (List<EStructuralFeature>) tvAttributes.getInput();
					tvAttributes.setAllChecked(true);
					selectedFeatures.addAll(segments);
					setPageComplete(!selectedFeatures.isEmpty());
				}

			});
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(bSelectAll);
			final Button bDeSelectAll = new Button(compositeButtons, SWT.PUSH);
			bDeSelectAll.setText("Deselect All"); //$NON-NLS-1$
			bDeSelectAll.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					@SuppressWarnings("unchecked")
					final List<EStructuralFeature> segments = (List<EStructuralFeature>) tvAttributes.getInput();
					tvAttributes.setAllChecked(false);
					selectedFeatures.removeAll(segments);
					setPageComplete(!selectedFeatures.isEmpty());
				}

			});
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(bDeSelectAll);
			parent.layout(true);
			scrolledComposite.layout(true);

		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return super.isPageComplete();
	}

	/**
	 *
	 */
	private void clear() {
		if (parent != null && !parent.isDisposed()) {
			for (final Control control : parent.getChildren()) {
				control.dispose();
			}
		}
	}

	/**
	 * @param eClass the EClass to show attributes from
	 */
	public void setRootClass(EClass eClass) {
		rootClass = eClass;
		selectedFeatures.clear();
		if (tvAttributes != null) {
			List<EStructuralFeature> attributes = null;
			attributes = rootClass.getEAllStructuralFeatures();

			tvAttributes.setInput(attributes);
		}
	}

	private List<EStructuralFeature> getUnreferencedSegmentAttributes(EClass eClass) {
		final List<EStructuralFeature> result = new ArrayList<EStructuralFeature>();
		final List<EStructuralFeature> allStructuralFeatures = new ArrayList<EStructuralFeature>(
			eClass.getEAllStructuralFeatures());

		final TreeIterator<EObject> eAllContents = view.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			if (org.eclipse.emf.ecp.view.spi.model.VControl.class.isInstance(eObject)) {
				final org.eclipse.emf.ecp.view.spi.model.VControl control = (org.eclipse.emf.ecp.view.spi.model.VControl) eObject;
				final VDomainModelReference domainModelReference = control.getDomainModelReference();
				if (domainModelReference == null) {
					continue;
				}

				IValueProperty valueProperty;
				try {
					valueProperty = Activator.getDefault().getEMFFormsDatabinding()
						.getValueProperty(domainModelReference, eClass);
				} catch (final DatabindingFailedException ex) {
					Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
					continue;
				}
				final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
				if (feature != null && feature.getEContainingClass() != null
					&& feature.getEContainingClass().equals(eClass)) {
					result.add(feature);
				}
			}

		}

		allStructuralFeatures.removeAll(result);
		return allStructuralFeatures;
	}

	/** @return the set of features selected in the dialog, for which controls should be generated. */
	public Set<EStructuralFeature> getSelectedFeatures() {
		return selectedFeatures;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.WizardPage#getPreviousPage()
	 */
	@Override
	public IWizardPage getPreviousPage() {
		// TODO Auto-generated method stub
		return super.getPreviousPage();
	}

	/**
	 * @param view The {@link VView} to select an attribute for. Is used to filter attributes already shown in the view.
	 */
	public void setView(VView view) {
		this.view = view;
	}

	@Override
	public boolean isCurrentPage() {
		return super.isCurrentPage();
	}

	/**
	 *
	 */
	public void clearSelection() {
		getSelectedFeatures().clear();
		tvAttributes.setAllChecked(false);

	}
}
