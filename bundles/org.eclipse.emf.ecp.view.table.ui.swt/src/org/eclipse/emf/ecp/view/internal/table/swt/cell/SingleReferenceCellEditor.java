/**
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
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.internal.table.swt.cell;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.SWTImageHelper;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPElementAwareCellEditor;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Single reference cell editor implementation.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @author Eugen Neufeld <eneufeld@eclipsesource.com>
 * @since 1.22
 *
 */
@SuppressWarnings("restriction")
public class SingleReferenceCellEditor extends CellEditor implements ECPCellEditor, ECPElementAwareCellEditor {

	private EObject rowElement;
	private ReferenceService referenceService;
	private EReference eReference;

	private Composite composite;

	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;

	/**
	 * The constructor.
	 *
	 * @param parent the parent composite
	 */
	public SingleReferenceCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * Alternate constructor with SWT style bits.
	 *
	 * @param parent the parent composite
	 * @param style SWT style bits
	 */
	public SingleReferenceCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public SimpleValueProperty getValueProperty() {
		return new SimpleValueProperty<Object, String>() {

			@Override
			public Object getValueType() {
				return String.class;
			}

			@Override
			protected String doGetValue(Object source) {
				return (String) SingleReferenceCellEditor.this.doGetValue();
			}

			@Override
			protected void doSetValue(Object source, String value) {
				SingleReferenceCellEditor.this.doSetValue(value);
			}

			@Override
			public IObservableValue<String> observe(Object source) {
				if (source instanceof SingleReferenceCellEditor) {
					return observe(composite);
				}
				return super.observe(source);
			}

			@Override
			public INativePropertyListener adaptListener(ISimplePropertyListener listener) {
				return null;
			}
		};
	}

	@Override
	public void instantiate(EStructuralFeature feature, ViewModelContext viewModelContext) {
		referenceService = viewModelContext.getService(ReferenceService.class);
		eReference = (EReference) feature;
	}

	@Override
	public int getStyle() {
		return 0;
	}

	@Override
	public String getFormatedString(Object value) {
		if (value == null) {
			return ""; //$NON-NLS-1$
		}
		return adapterFactoryItemDelegator.getText(value);
	}

	@Override
	public Image getImage(Object value) {
		final Object image = adapterFactoryItemDelegator.getImage(value);
		return SWTImageHelper.getImage(image);
	}

	@Override
	public int getColumnWidthWeight() {
		return 0;
	}

	@Override
	public UpdateValueStrategy getTargetToModelStrategy(DataBindingContext databindingContext) {
		return null;
	}

	@Override
	public UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext) {
		return null;
	}

	@Override
	public void setEditable(boolean editable) {
	}

	@Override
	public int getMinWidth() {
		return 100;
	}

	@Override
	protected Control createControl(Composite parent) {

		composite = new Composite(parent, SWT.NONE);

		composite.addFocusListener(new FocusListener() {

			private boolean focused;

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (focused) {
					return;
				}
				focused = true;
				try {
					referenceService.addExistingModelElements(rowElement, eReference);
				} finally {
					deactivate();
					focused = false;
				}
			}
		});

		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		return composite;
	}

	@Override
	public void dispose() {
		super.dispose();
		composedAdapterFactory.dispose();
	}

	/**
	 * Get the {@link EditingDomain} for the given {@link EObject}.
	 *
	 * @param domainModel the eObject
	 * @return the {@link EditingDomain}
	 */
	protected final EditingDomain getEditingDomain(EObject domainModel) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(domainModel);
	}

	@Override
	protected Object doGetValue() {
		return null;
	}

	@Override
	protected void doSetFocus() {
		composite.setFocus();
	}

	@Override
	protected void doSetValue(Object value) {
	}

	@Override
	public void updateRowElement(Object element) {
		rowElement = (EObject) element;
	}

}
