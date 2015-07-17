/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.renderer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;

/**
 * Exception indicating, that an {@link org.eclipse.emf.edit.provider.ItemPropertyDescriptor ItemPropertyDescriptor}
 * could not be found for an {@link EObject} and its {@link EStructuralFeature}.
 *
 * @author Eugen Neufeld
 *
 * @noextend This class is not intended to be subclassed by clients.
 * @since 1.2
 */
public class NoPropertyDescriptorFoundExeption extends ECPRendererException {

	private static final String NO_PROPERTY_DESCRIPTOR_FOUND = "No Property descriptor was found for the feature \"%s\" of \"%s\". Make sure, the corresponing edit bundle is started."; //$NON-NLS-1$

	private static final long serialVersionUID = -4450264762772550298L;

	private final EObject modelElement;
	private final EStructuralFeature targetFeature;

	/**
	 * Constructor for an {@link Exception} which indicates, that no
	 * {@link org.eclipse.emf.edit.provider.ItemPropertyDescriptor ItemPropertyDescriptor} was found.
	 *
	 * @param modelElement the {@link EObject} the {@link org.eclipse.emf.edit.provider.ItemPropertyDescriptor
	 *            ItemPropertyDescriptor} was not found for
	 * @param targetFeature the {@link EStructuralFeature} the
	 *            {@link org.eclipse.emf.edit.provider.ItemPropertyDescriptor ItemPropertyDescriptor} was not found for
	 */
	public NoPropertyDescriptorFoundExeption(EObject modelElement,
		EStructuralFeature targetFeature) {
		super(String.format(NO_PROPERTY_DESCRIPTOR_FOUND, targetFeature.getName(), modelElement.eClass().getName()));
		this.modelElement = modelElement;
		this.targetFeature = targetFeature;
	}

	/**
	 * The {@link EObject} which has a missing {@link org.eclipse.emf.edit.provider.ItemPropertyDescriptor
	 * ItemPropertyDescriptor}.
	 *
	 * @return the {@link EObject}
	 */
	public EObject getModelElement() {
		return modelElement;
	}

	/**
	 * The {@link EStructuralFeature} which misses a {@link org.eclipse.emf.edit.provider.ItemPropertyDescriptor
	 * ItemPropertyDescriptor}.
	 *
	 * @return the {@link EStructuralFeature}
	 */
	public EStructuralFeature getTargetFeature() {
		return targetFeature;
	}
}
