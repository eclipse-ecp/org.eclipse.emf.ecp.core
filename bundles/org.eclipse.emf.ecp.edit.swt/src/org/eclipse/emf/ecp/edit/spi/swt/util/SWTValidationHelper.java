/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Eugen Neufeld - VTViewTemplate implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationFactory;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Helper class for accessing icons and colours for validations.
 *
 * @author jfaltermeier
 * @since 1.5
 *
 */
public class SWTValidationHelper {

	/**
	 * The instance of the SWTValidationHelper.
	 */
	public static final SWTValidationHelper INSTANCE = new SWTValidationHelper();
	private final Map<String, Color> colorMap = new LinkedHashMap<String, Color>();
	private VTValidationStyleProperty defaultValidationStyle;

	/**
	 * Returns the background color for a control with the given validation severity, VElement
	 * and view model context, if applicable.
	 *
	 * @param severity severity the severity of the {@link Diagnostic}
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the color to be used as a background color
	 */
	public Color getValidationBackgroundColor(int severity, VElement vElement, ViewModelContext viewModelContext) {
		final VTValidationStyleProperty defaultStyle = getDefaultValidationStyle();
		String colorHex = null;

		switch (severity) {
		case Diagnostic.OK:
			colorHex = getOkColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.INFO:
			colorHex = getInfoColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.WARNING:
			colorHex = getWarningColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.ERROR:
			colorHex = getErrorColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.CANCEL:
			colorHex = getCancelColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		default:
			throw new IllegalArgumentException(
				"The specified severity value " + severity + " is invalid. See Diagnostic class."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (colorHex == null) {
			return null;
		}
		if (!colorMap.containsKey(colorHex)) {
			colorMap.put(colorHex, getColor(colorHex));
		}
		return colorMap.get(colorHex);
	}

	/**
	 * Returns the foreground color for a control with the given validation severity, VElement
	 * and view model context, if applicable.
	 *
	 * @param severity severity the severity of the {@link Diagnostic}
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the color to be used as a foreground color
	 * @since 1.10
	 */
	public Color getValidationForegroundColor(int severity, VElement vElement, ViewModelContext viewModelContext) {
		final VTValidationStyleProperty defaultStyle = getDefaultValidationStyle();
		String colorHex = null;

		switch (severity) {
		case Diagnostic.OK:
			colorHex = getOkForegroundColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.INFO:
			colorHex = getInfoForegroundColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.WARNING:
			colorHex = getWarningForegroundColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.ERROR:
			colorHex = getErrorForegroundColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.CANCEL:
			colorHex = getCancelForegroundColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		default:
			throw new IllegalArgumentException(
				"The specified severity value " + severity + " is invalid. See Diagnostic class."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (colorHex == null) {
			return null;
		}
		if (!colorMap.containsKey(colorHex)) {
			colorMap.put(colorHex, getColor(colorHex));
		}
		return colorMap.get(colorHex);
	}

	/**
	 * Returns the background color for a control with the given validation severity.
	 *
	 * @param severity severity the severity of the {@link Diagnostic}
	 * @return the color to be used as a background color
	 */
	public Color getValidationBackgroundColor(int severity) {
		return getValidationBackgroundColor(severity, null, null);
	}

	/**
	 * Returns the foreground color for a control with the given validation severity.
	 *
	 * @param severity severity the severity of the {@link Diagnostic}
	 * @return the color to be used as a foreground color
	 * @since 1.10
	 */
	public Color getValidationForegroundColor(int severity) {
		return getValidationForegroundColor(severity, null, null);
	}

	/**
	 * Returns the hex color for a control with the given validation severity, VElement
	 * and view model context, if applicable.
	 *
	 * @param severity severity the severity of the {@link Diagnostic}
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the hex value to be used
	 * @since 1.9
	 */
	public String getValidationColorHEX(int severity, VElement vElement, ViewModelContext viewModelContext) {
		final VTValidationStyleProperty defaultStyle = getDefaultValidationStyle();
		String colorHex = null;

		switch (severity) {
		case Diagnostic.OK:
			colorHex = getOkColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.INFO:
			colorHex = getInfoColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.WARNING:
			colorHex = getWarningColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.ERROR:
			colorHex = getErrorColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.CANCEL:
			colorHex = getCancelColorHEX(defaultStyle, vElement, viewModelContext);
			break;
		default:
			throw new IllegalArgumentException(
				"The specified severity value " + severity + " is invalid. See Diagnostic class."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return colorHex;
	}

	/**
	 * Returns the validation icon matching the given severity, VElement
	 * and view model context, if applicable.
	 *
	 * @param severity the severity of the {@link Diagnostic}
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the icon to be displayed, or <code>null</code> when no icon is to be displayed
	 */
	public Image getValidationIcon(int severity, VElement vElement, ViewModelContext viewModelContext) {
		final VTValidationStyleProperty defaultStyle = getDefaultValidationStyle();
		String imageUrl = null;

		switch (severity) {
		case Diagnostic.OK:
			imageUrl = getOkImageURL(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.INFO:
			imageUrl = getInfoImageURL(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.WARNING:
			imageUrl = getWarningImageURL(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.ERROR:
			imageUrl = getErrorImageURL(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.CANCEL:
			imageUrl = getCancelImageURL(defaultStyle, vElement, viewModelContext);
			break;
		default:
			throw new IllegalArgumentException(
				"The specified severity value " + severity + " is invalid. See Diagnostic class."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (imageUrl == null) {
			return null;
		}
		try {
			return Activator.getImage(new URL(imageUrl));
		} catch (final MalformedURLException ex) {
			return null;
		}
	}

	/**
	 * Returns the validation icon matching the given severity.
	 *
	 * @param severity the severity of the {@link Diagnostic}
	 * @return the icon to be displayed, or <code>null</code> when no icon is to be displayed
	 */
	public Image getValidationIcon(int severity) {
		return getValidationIcon(severity, null, null);
	}

	/**
	 * Returns the validation overlay icon matching the given severity, VElement
	 * and view model context, if applicable.
	 *
	 * @param severity the severity of the {@link Diagnostic}
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the icon to be displayed, or <code>null</code> when no icon is to be displayed
	 */
	public ImageDescriptor getValidationOverlayDescriptor(int severity, VElement vElement,
		ViewModelContext viewModelContext) {
		final VTValidationStyleProperty defaultStyle = getDefaultValidationStyle();
		String imageUrl = null;

		switch (severity) {
		case Diagnostic.OK:
			imageUrl = getOkOverlayURL(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.INFO:
			imageUrl = getInfoOverlayURL(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.WARNING:
			imageUrl = getWarningOverlayURL(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.ERROR:
			imageUrl = getErrorOverlayURL(defaultStyle, vElement, viewModelContext);
			break;
		case Diagnostic.CANCEL:
			imageUrl = getCancelOverlayURL(defaultStyle, vElement, viewModelContext);
			break;
		default:
			throw new IllegalArgumentException(
				"The specified severity value " + severity + " is invalid. See Diagnostic class."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (imageUrl == null) {
			return null;
		}
		try {
			return ImageDescriptor.createFromURL(new URL(imageUrl));
		} catch (final MalformedURLException ex) {
			return null;
		}
	}

	/**
	 * Returns an image descriptor which can be used as an overlay for validation icons.
	 *
	 * @param severity the severity of the validation
	 * @return the descriptor
	 */
	public ImageDescriptor getValidationOverlayDescriptor(int severity) {
		return getValidationOverlayDescriptor(severity, null, null);
	}

	private String getOkColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getOkColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getOkColorHEX();
		}
		return colorHex;
	}

	private String getOkForegroundColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getOkForegroundColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getOkForegroundColorHEX();
		}
		return colorHex;
	}

	private String getInfoColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getInfoColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getInfoColorHEX();
		}
		return colorHex;
	}

	private String getInfoForegroundColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getInfoForegroundColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getInfoForegroundColorHEX();
		}
		return colorHex;
	}

	private String getWarningColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getWarningColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getWarningColorHEX();
		}
		return colorHex;
	}

	private String getWarningForegroundColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getWarningForegroundColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getWarningForegroundColorHEX();
		}
		return colorHex;
	}

	private String getErrorColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getErrorColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getErrorColorHEX();
		}
		return colorHex;
	}

	private String getErrorForegroundColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getErrorForegroundColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getErrorForegroundColorHEX();
		}
		return colorHex;
	}

	private String getCancelColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getCancelColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getCancelColorHEX();
		}
		return colorHex;
	}

	private String getCancelForegroundColorHEX(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String colorHex = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			colorHex = defaultStyle.getCancelForegroundColorHEX();
		}
		if (validationStyleProperty != null) {
			colorHex = validationStyleProperty.getCancelForegroundColorHEX();
		}
		return colorHex;
	}

	private String getOkImageURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String imageURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			imageURL = defaultStyle.getOkImageURL();
		}
		if (validationStyleProperty != null) {
			imageURL = validationStyleProperty.getOkImageURL();
		}
		return imageURL;
	}

	private String getInfoImageURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String imageURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			imageURL = defaultStyle.getInfoImageURL();
		}
		if (validationStyleProperty != null) {
			imageURL = validationStyleProperty.getInfoImageURL();
		}
		return imageURL;
	}

	private String getWarningImageURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String imageURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			imageURL = defaultStyle.getWarningImageURL();
		}
		if (validationStyleProperty != null) {
			imageURL = validationStyleProperty.getWarningImageURL();
		}
		return imageURL;
	}

	private String getErrorImageURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String imageURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			imageURL = defaultStyle.getErrorImageURL();
		}
		if (validationStyleProperty != null) {
			imageURL = validationStyleProperty.getErrorImageURL();
		}
		return imageURL;
	}

	private String getCancelImageURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String imageURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			imageURL = defaultStyle.getCancelImageURL();
		}
		if (validationStyleProperty != null) {
			imageURL = validationStyleProperty.getCancelImageURL();
		}
		return imageURL;
	}

	private String getOkOverlayURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String overlayURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			overlayURL = defaultStyle.getOkOverlayURL();
		}
		if (validationStyleProperty != null) {
			overlayURL = validationStyleProperty.getOkOverlayURL();
		}
		return overlayURL;
	}

	private String getInfoOverlayURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String overlayURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			overlayURL = defaultStyle.getInfoOverlayURL();
		}
		if (validationStyleProperty != null) {
			overlayURL = validationStyleProperty.getInfoOverlayURL();
		}
		return overlayURL;
	}

	private String getWarningOverlayURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String overlayURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			overlayURL = defaultStyle.getWarningOverlayURL();
		}
		if (validationStyleProperty != null) {
			overlayURL = validationStyleProperty.getWarningOverlayURL();
		}
		return overlayURL;
	}

	private String getErrorOverlayURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String overlayURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			overlayURL = defaultStyle.getErrorOverlayURL();
		}
		if (validationStyleProperty != null) {
			overlayURL = validationStyleProperty.getErrorOverlayURL();
		}
		return overlayURL;
	}

	private String getCancelOverlayURL(VTValidationStyleProperty defaultStyle, VElement vElement,
		ViewModelContext viewModelContext) {
		String overlayURL = null;
		final VTValidationStyleProperty validationStyleProperty = getValidationStyleProperty(vElement,
			viewModelContext);
		if (defaultStyle != null) {
			overlayURL = defaultStyle.getCancelOverlayURL();
		}
		if (validationStyleProperty != null) {
			overlayURL = validationStyleProperty.getCancelOverlayURL();
		}
		return overlayURL;
	}

	private VTValidationStyleProperty getValidationStyleProperty(VElement vElement, ViewModelContext viewModelContext) {
		VTValidationStyleProperty validationStyleProperty = null;
		if (vElement != null && viewModelContext != null) {
			final VTViewTemplateProvider vtViewTemplateProvider = Activator.getDefault().getVTViewTemplateProvider();
			if (vtViewTemplateProvider == null) {
				return validationStyleProperty;
			}
			final Set<VTStyleProperty> styleProperties = vtViewTemplateProvider.getStyleProperties(vElement,
				viewModelContext);
			for (final VTStyleProperty styleProperty : styleProperties) {
				if (VTValidationStyleProperty.class.isInstance(styleProperty)) {
					validationStyleProperty = VTValidationStyleProperty.class
						.cast(styleProperty);
					break;
				}
			}
		}
		return validationStyleProperty;
	}

	/**
	 * @return The default validation style to apply if none is specified for a {@link VElement}.
	 */
	private VTValidationStyleProperty getDefaultValidationStyle() {
		if (defaultValidationStyle == null) {
			defaultValidationStyle = VTValidationFactory.eINSTANCE.createValidationStyleProperty();
			defaultValidationStyle.setOkColorHEX("ffffff"); //$NON-NLS-1$
			defaultValidationStyle.setErrorColorHEX("ff0000"); //$NON-NLS-1$
			defaultValidationStyle.setWarningColorHEX("FFD800");//$NON-NLS-1$
			defaultValidationStyle.setErrorImageURL(Activator.getDefault().getBundle()
				.getResource("icons/validation_error.png").toExternalForm()); //$NON-NLS-1$
			defaultValidationStyle.setErrorOverlayURL(Activator.getDefault().getBundle()
				.getResource("icons/error_decorate.png").toExternalForm()); //$NON-NLS-1$
			defaultValidationStyle.setWarningImageURL(Activator.getDefault().getBundle()
				.getResource("icons/validation_warning.png").toExternalForm()); //$NON-NLS-1$
			defaultValidationStyle.setWarningOverlayURL(Activator.getDefault().getBundle()
				.getResource("icons/warning_decorate.png").toExternalForm()); //$NON-NLS-1$
			defaultValidationStyle.setInfoOverlayURL(Activator.getDefault().getBundle()
				.getResource("icons/info_decorate.gif").toExternalForm()); //$NON-NLS-1$
		}
		return defaultValidationStyle;
	}

	/**
	 * @param colorHex
	 * @return
	 */
	private Color getColor(String colorHex) {
		final String redString = colorHex.substring(0, 2);
		final String greenString = colorHex.substring(2, 4);
		final String blueString = colorHex.substring(4, 6);
		final int red = Integer.parseInt(redString, 16);
		final int green = Integer.parseInt(greenString, 16);
		final int blue = Integer.parseInt(blueString, 16);
		return new Color(Display.getDefault(), red, green, blue);
	}

}
