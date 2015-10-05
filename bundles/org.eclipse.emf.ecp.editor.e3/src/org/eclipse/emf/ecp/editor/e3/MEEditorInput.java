/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.editor.e3;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * The {@link IEditorInput} for the {@link org.eclipse.emf.ecp.editor.internal.e3.MEEditor MEEditor}.
 *
 * @author helming
 * @author shterev
 * @author naughton
 */
public class MEEditorInput implements IEditorInput {

	private EStructuralFeature problemFeature;
	private ECPEditorContext modelElementContext;

	/**
	 * Constructor to add a probleFeature.
	 *
	 * @param context context of the model element
	 * @param problemFeature the problem feature
	 */
	public MEEditorInput(ECPEditorContext context, EStructuralFeature problemFeature) {
		this(context);
		this.problemFeature = problemFeature;
	}

	/**
	 * Default constructor.
	 *
	 * @param context context of the modelelement
	 */
	public MEEditorInput(ECPEditorContext context) {
		super();
		modelElementContext = context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the problemFeature
	 */
	public EStructuralFeature getProblemFeature() {
		return problemFeature;
	}

	/**
	 * @param problemFeature the problemFeature to set
	 */
	public void setProblemFeature(EStructuralFeature problemFeature) {
		this.problemFeature = problemFeature;
	}

	/**
	 * Custom equals() for this class.
	 *
	 * @param obj the compared object.
	 * @return the boolean state. {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MEEditorInput) {
			final MEEditorInput other = (MEEditorInput) obj;
			if (modelElementContext == other.modelElementContext) {
				return true;
			}
			if (other.modelElementContext == null) {
				return false;
			}
			final boolean ret = modelElementContext.getDomainObject().equals(
				other.modelElementContext.getDomainObject());
			return ret;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class clazz) {

		if (clazz.equals(EObject.class) && modelElementContext != null) {
			return modelElementContext.getDomainObject();
		}
		return null;
	}

	/**
	 * Returns the {@link ECPEditorContext}.
	 *
	 * @return {@link ECPEditorContext}
	 */
	public ECPEditorContext getModelElementContext() {
		return modelElementContext;
	}

	/**
	 * Disposes the input.
	 */
	public void dispose() {
		modelElementContext.dispose();
		modelElementContext = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		if (modelElementContext == null) {
			return "Error"; //$NON-NLS-1$
		}
		return modelElementContext.getDomainObject().eClass().getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getToolTipText() {
		return getName();
	}
}
