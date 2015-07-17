/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.spi.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.util.AdapterProvider;
import org.eclipse.emf.ecp.spi.core.util.InternalRegistryElement;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * @author Eike Stepper
 * @since 1.1
 */
public interface UIProvider extends InternalRegistryElement, IAdaptable, AdapterProvider {
	/** The Type of the Element. **/
	String TYPE = "UIProvider";
	/** The LabelProvider to use in UIProviders. **/
	ILabelProvider EMF_LABEL_PROVIDER = new AdapterFactoryLabelProvider(InternalProvider.EMF_ADAPTER_FACTORY);

	/**
	 * Returns the corresponding Provider for this UI Provider.
	 *
	 * @return the corresponding {@link InternalProvider}
	 */
	InternalProvider getProvider();

	/**
	 * Returns the name for an element.
	 *
	 * @param element the object to return the name for
	 * @return the name of this element
	 */
	String getText(Object element);

	/**
	 * Returns the image for an element.
	 *
	 * @param element the object to return the image for
	 * @return the name of this element
	 */
	Image getImage(Object element);

	/**
	 * Allows the UIProvider to fill the context menu specifically.
	 *
	 * @param manager the {@link IMenuManager} to fill
	 * @param context the current selected {@link ECPContainer}
	 * @param elements the selected elements
	 */
	void fillContextMenu(IMenuManager manager, ECPContainer context, Object[] elements);

	/**
	 * The UIProvider can return its provider specific UI to allow the user to fill in provider specific data during the
	 * creation of an Repository.
	 *
	 * @param parent the {@link Composite} to fill
	 * @param repositoryProperties the {@link ECPProperties} of the repository to create
	 * @param repositoryNameText the {@link Text} widget handling the repository name
	 * @param repositoryLabelText the {@link Text} widget handling the repository label
	 * @param repositoryDescriptionText the {@link Text} widget handling the repository description
	 * @return the created control
	 */
	Control createAddRepositoryUI(Composite parent, ECPProperties repositoryProperties, Text repositoryNameText,
		Text repositoryLabelText, Text repositoryDescriptionText);

	/**
	 * The UIProvider can return a provider specific UI to allow the user to fill in provider specific data for a
	 * checkout.
	 *
	 * @param parent the {@link Composite} to fill
	 * @param checkoutSource the Object to checkout
	 * @param projectProperties the {@link ECPProperties} of the project to create
	 * @return the created control
	 */
	Control createCheckoutUI(Composite parent, ECPCheckoutSource checkoutSource, ECPProperties projectProperties);

	/**
	 * The UIProvider can return a provider specific UI to allow the user to fill in provider specific data for the
	 * creation of a new project.
	 *
	 * @param parent the {@link Composite} to fill
	 * @param observer the observer
	 * @param projectProperties the {@link ECPProperties} of the project to create
	 * @return the created control
	 */
	Control createNewProjectUI(Composite parent, CompositeStateObserver observer, ECPProperties projectProperties);
}
