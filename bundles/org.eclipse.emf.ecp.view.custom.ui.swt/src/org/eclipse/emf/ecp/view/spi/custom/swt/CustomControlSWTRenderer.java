/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.swt;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.internal.edit.EditMessages;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.Bundle;

/**
 * The renderer for custom control view models.
 *
 * @author Eugen Neufeld
 * @since 1.3
 */
@SuppressWarnings("restriction")
public class CustomControlSWTRenderer extends AbstractSWTRenderer<VCustomControl> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public CustomControlSWTRenderer(VCustomControl vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	private ECPAbstractCustomControlSWT swtCustomControl;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#preInit()
	 */
	@Override
	protected void preInit() {
		super.preInit();
		final VCustomControl customControl = getVElement();
		swtCustomControl = loadCustomControl(customControl);
		if (swtCustomControl == null) {
			// TODO
			throw new IllegalStateException(String.format("The  %1$s/%2$s cannot be loaded!", //$NON-NLS-1$
				customControl.getBundleName(), customControl.getClassName()));
		}
		swtCustomControl.init(getVElement(), getViewModelContext());
	}

	/**
	 * Loads and returns the {@link ECPAbstractCustomControlSWT} that is referenced by the {@link VCustomControl}.
	 *
	 * @param customControl the custom control view model
	 * @return the swt renderer
	 * @since 1.4
	 */
	protected ECPAbstractCustomControlSWT loadCustomControl(VCustomControl customControl) {
		String bundleName = customControl.getBundleName();
		String className = customControl.getClassName();
		if (customControl.getBundleName() != null) {
		}
		if (bundleName == null) {
			bundleName = ""; //$NON-NLS-1$
		}
		if (className == null) {
			className = ""; //$NON-NLS-1$
		}
		swtCustomControl = loadObject(bundleName, className);
		return swtCustomControl;
	}

	private static ECPAbstractCustomControlSWT loadObject(String bundleName, String clazz) {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			new ClassNotFoundException(clazz + EditMessages.CONTROLFACTROY_CANNOT_BE_LOADED
				+ bundleName
				+ EditMessages.CONTROLFACTORY_CANNOT_BE_RESOLVED);
			return null;
		}
		try {
			final Class<?> loadClass = bundle.loadClass(clazz);
			if (!ECPAbstractCustomControlSWT.class.isAssignableFrom(loadClass)) {
				return null;
			}
			return ECPAbstractCustomControlSWT.class.cast(loadClass.newInstance());
		} catch (final ClassNotFoundException ex) {
			return null;
		} catch (final InstantiationException ex) {
			return null;
		} catch (final IllegalAccessException ex) {
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		swtCustomControl.dispose();
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		final SWTGridDescription gd = swtCustomControl.getGridDescription();
		for (final SWTGridCell gridCell : gd.getGrid()) {
			gridCell.setRenderer(this);
		}
		return gd;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		return swtCustomControl.renderControl(cell, parent);
	}

	@Override
	protected void applyReadOnly() {
		swtCustomControl.applyReadOnly(getControls());
	}

	@Override
	protected void applyEnable() {
		swtCustomControl.applyEnable(getControls());
	}

	@Override
	protected void applyVisible() {
		for (final SWTGridCell gridCell : getControls().keySet()) {
			final Object layoutData = getControls().get(gridCell).getLayoutData();
			if (GridData.class.isInstance(layoutData)) {
				final GridData gridData = (GridData) layoutData;
				if (gridData != null) {
					gridData.exclude = false;
				}
			}
			getControls().get(gridCell).setVisible(getVElement().isVisible());
		}
	}

	/**
	 * Allows implementers to display the validation state of the control.
	 * The default implementation does nothing.
	 */
	@Override
	protected void applyValidation() {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				Label validationIcon = null;
				switch (getControls().size()) {
				case 3:
					validationIcon = Label.class.cast(getControls().get(
						new SWTGridCell(0, 1, CustomControlSWTRenderer.this)));
					break;
				default:
					break;
				}

				if (validationIcon != null && !validationIcon.isDisposed()) {
					validationIcon.setImage(getValidationIcon(getVElement().getDiagnostic().getHighestSeverity()));
					validationIcon.setToolTipText(getVElement().getDiagnostic().getMessage());
				}
				if (swtCustomControl != null) {
					swtCustomControl.applyValidation();
				}
			}
		});
	}

	private Image getValidationIcon(int severity) {
		return SWTValidationHelper.INSTANCE.getValidationIcon(severity, getVElement(), getViewModelContext());
	}
}
