/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui;

import java.util.MissingResourceException;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Hodaie LabelProvider for TreeViewer that is shown on ModelTreePage
 */
public class MEClassLabelProvider extends AdapterFactoryLabelProvider {

	/**
	 * Constructor.
	 *
	 * @param adapterFactory the {@link AdapterFactory} to use
	 */
	public MEClassLabelProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);

	}

	/**
	 * . ({@inheritDoc}) If argument is instance of EClass then return its display name.
	 */
	@Override
	public String getText(Object object) {
		if (object instanceof EPackage) {
			return super.getText(object);
		}
		if (object instanceof EClass) {
			return ((EClass) object).getName();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * . ({@inheritDoc})
	 */
	@Override
	public Image getImage(Object object) {
		if (object instanceof EClass) {
			final EClass eClass = (EClass) object;
			final EPackage ePackage = eClass.getEPackage();
			if (!eClass.isAbstract() && !eClass.isInterface()) {
				// TODO: find a better way to retrieve images without instanciate classes
				final EObject newMEInstance = ePackage.getEFactoryInstance().create(eClass);
				try {
					return super.getImage(newMEInstance);
				} catch (final NullPointerException e) {
					return super.getImage(object);
				} catch (final MissingResourceException mre) {
					return super.getImage(object);
				}

			}
			return super.getImage(object);

		}
		return super.getImage(object);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getColumnImage(Object object, int columnIndex) {
		if (columnIndex == 0) {
			return getImage(object);
		}

		return super.getImage(object);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnText(Object object, int columnIndex) {
		if (columnIndex == 0) {
			return getText(object);
		}

		return super.getText(object);

	}

}
