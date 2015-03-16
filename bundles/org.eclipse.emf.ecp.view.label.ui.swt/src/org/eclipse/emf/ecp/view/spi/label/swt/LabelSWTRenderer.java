/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.label.swt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesFactory;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Renders an {@link VLabel} to a SWT {@link Label}.
 *
 * @since 1.5
 *
 */
public class LabelSWTRenderer extends AbstractSWTRenderer<VLabel> {
	private SWTGridDescription rendererGridDescription;
	private Font font;
	private org.eclipse.swt.graphics.Color labelColor;

	private Map<VLabelStyle, VTFontPropertiesStyleProperty> defaultStyles;
	private Composite parent;
	private EMFDataBindingContext dataBindingContext;
	private final EMFDataBindingContext dbc;

	public LabelSWTRenderer(){
		super();
		dbc = new EMFDataBindingContext();
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		if (font != null) {
			font.dispose();
		}
		if (labelColor != null) {
			labelColor.dispose();
		}
		if (dataBindingContext != null) {
			dataBindingContext.dispose();
			dataBindingContext = null;
		}
		dbc.dispose();
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
			final SWTGridCell gridCell = rendererGridDescription.getGrid().get(0);
			gridCell.setVerticalGrab(false);
			gridCell.setVerticalFill(false);
			gridCell.setHorizontalFill(true);
			gridCell.setHorizontalGrab(true);
		}
		return rendererGridDescription;
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		this.parent = parent;
		final Label label;
		if (getVElement().getStyle() == VLabelStyle.SEPARATOR) {
			label = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		} else {
			label = new Label(parent, SWT.NONE);
			setText(label);
			applyStyle(label);
		}
		return label;
	}

	private void setText(Label label) {
		if (getVElement().getDomainModelReference() != null) {
			final Iterator<Setting> iterator = getVElement().getDomainModelReference().getIterator();
			if (iterator.hasNext()) {
				final Setting setting = iterator.next();

				final ISWTObservableValue observeText = SWTObservables.observeText(label);
				final IObservableValue observeValue = EMFObservables.observeValue(setting.getEObject(),
					setting.getEStructuralFeature());
				final Binding binding = getDataBindingContext().bindValue(observeText, observeValue);

				label.addDisposeListener(new DisposeListener() {
					@Override
					public void widgetDisposed(DisposeEvent e) {
						binding.dispose();
					}
				});
			}
		} else {
			final IObservableValue modelValue = EMFEditObservables.observeValue(
				AdapterFactoryEditingDomain.getEditingDomainFor(getVElement()), getVElement(),
				VViewPackage.eINSTANCE.getElement_Label());
			final IObservableValue targetValue = SWTObservables.observeText(label);

			dbc.bindValue(targetValue, modelValue);
		}
	}

	private DataBindingContext getDataBindingContext() {
		if (dataBindingContext == null) {
			dataBindingContext = new EMFDataBindingContext();
		}
		return dataBindingContext;
	}

	/**
	 * Applies the style defined in the StyleProperty.
	 *
	 * @param label the {@link Label} to apply the styles onto
	 */
	protected void applyStyle(Label label) {
		final VTFontPropertiesStyleProperty fontProperties = getFontProperty();
		if (fontProperties == null) {
			return;
		}
		final FontData[] fD = label.getFont().getFontData();

		int style = SWT.NORMAL;
		if (fontProperties.isBold()) {
			style = style | SWT.BOLD;
		}
		if (fontProperties.isItalic()) {
			style = style | SWT.ITALIC;
		}

		fD[0].setStyle(style);

		fD[0].setHeight(fontProperties.getHeight());

		if (font != null) {
			font.dispose();
		}
		if (labelColor != null) {
			labelColor.dispose();
		}
		String fontName = fontProperties.getFontName();
		if (fontName == null) {
			fontName = getDefaultFontName(label);
		}
		font = new Font(label.getDisplay(), fontName, fontProperties.getHeight(), style);
		label.setFont(font);
		labelColor = getColor(fontProperties.getColorHEX());
		label.setForeground(labelColor);

	}

	private Color getColor(String colorHex) {
		final String redString = colorHex.substring(0, 2);
		final String greenString = colorHex.substring(2, 4);
		final String blueString = colorHex.substring(4, 6);
		final int red = Integer.parseInt(redString, 16);
		final int green = Integer.parseInt(greenString, 16);
		final int blue = Integer.parseInt(blueString, 16);
		return new Color(Display.getDefault(), red, green, blue);
	}

	private VTFontPropertiesStyleProperty getFontProperty() {
		VTFontPropertiesStyleProperty fontProperties;
		final Set<VTStyleProperty> styleProperties = Activator.getDefault().getVTViewTemplateProvider()
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTFontPropertiesStyleProperty.class.isInstance(styleProperty)) {
				fontProperties = VTFontPropertiesStyleProperty.class
					.cast(styleProperty);
				return fontProperties;
			}
		}

		fontProperties = getDefaultFontProperties().get(getVElement().getStyle());
		return fontProperties;
	}

	private Map<VLabelStyle, VTFontPropertiesStyleProperty> getDefaultFontProperties() {
		if (defaultStyles == null) {
			defaultStyles = new HashMap<VLabelStyle, VTFontPropertiesStyleProperty>();
			final String defaultFontName = parent.getDisplay().getSystemFont().getFontData()[0].getName();
			defaultStyles.put(VLabelStyle.H0, createStyle(false, false, "000000", defaultFontName, 10)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H1, createStyle(false, false, "000000", defaultFontName, 12)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H2, createStyle(false, false, "000000", defaultFontName, 14)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H3, createStyle(true, true, "000000", defaultFontName, 10)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H4, createStyle(true, false, "000000", defaultFontName, 10)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H5, createStyle(true, false, "000000", defaultFontName, 12)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H6, createStyle(true, false, "000000", defaultFontName, 14)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H7, createStyle(false, true, "000000", defaultFontName, 10)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H8, createStyle(false, true, "000000", defaultFontName, 12)); //$NON-NLS-1$
			defaultStyles.put(VLabelStyle.H9, createStyle(false, true, "000000", defaultFontName, 14)); //$NON-NLS-1$
		}
		return defaultStyles;
	}

	private VTFontPropertiesStyleProperty createStyle(boolean bold, boolean italic, String colorHex, String fontName,
		int fontHeight) {

		final VTFontPropertiesStyleProperty fontProp = VTFontPropertiesFactory.eINSTANCE
			.createFontPropertiesStyleProperty();

		fontProp.setBold(bold);
		fontProp.setColorHEX(colorHex); // "ff0000"
		fontProp.setFontName(fontName);
		fontProp.setHeight(fontHeight);
		fontProp.setItalic(italic);

		return fontProp;

	}
}
