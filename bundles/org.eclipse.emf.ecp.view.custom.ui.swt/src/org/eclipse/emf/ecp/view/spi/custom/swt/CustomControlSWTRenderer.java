/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Stefan Dirix - add ControlProcessorService
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.swt;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsControlProcessorService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.emfforms.spi.swt.core.ui.SWTValidationUiService;
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
public class CustomControlSWTRenderer extends AbstractSWTRenderer<VCustomControl> {

	private final SWTValidationUiService validationUiService;

	/**
	 * Legacy Constructor.
	 *
	 * @param vElement the view element to be rendered
	 * @param viewContext The view model context
	 * @param reportService the ReportService to use
	 * @since 1.6
	 */
	public CustomControlSWTRenderer(final VCustomControl vElement, final ViewModelContext viewContext,
		ReportService reportService) {
		this(vElement, viewContext, reportService, viewContext.getService(SWTValidationUiService.class));
	}

	/**
	 * Default Constructor.
	 *
	 * @param vElement the view element to be rendered
	 * @param viewContext The view model context
	 * @param reportService the ReportService to use
	 * @param validationUiService the {@link SWTValidationUiService} to use
	 * @since 1.23
	 */
	public CustomControlSWTRenderer(final VCustomControl vElement, final ViewModelContext viewContext,
		ReportService reportService, SWTValidationUiService validationUiService) {
		super(vElement, viewContext, reportService);
		this.validationUiService = validationUiService;
	}

	private ECPAbstractCustomControlSWT swtCustomControl;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#preInit()
	 */
	@Override
	protected void preInit() {
		super.preInit();
		final VCustomControl customControl = getVElement();
		swtCustomControl = loadCustomControl(customControl);
		if (swtCustomControl == null) {
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
			// why do we create a class not found exception without doing anything with it
			new ClassNotFoundException(
				String.format(LocalizationServiceHelper.getString(CustomControlSWTRenderer.class,
					"BundleNotFound_ExceptionMessage"), clazz, bundleName)); //$NON-NLS-1$
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
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		swtCustomControl.dispose();
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#render(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public Control render(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control control = super.render(cell, parent);

		if (control == null) {
			return null;
		}

		if (!canHandleControlProcessor()) {
			defaultHandleControlProcessor(control);
		}

		return control;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		return swtCustomControl.renderControl(cell, parent);
	}

	/**
	 * Indicates whether the {@link ECPAbstractCustomControlSWT} can handle a possibly existing
	 * {@link org.eclipse.emfforms.spi.swt.core.EMFFormsControlProcessorService EMFFormsControlProcessorService}
	 * itself.
	 *
	 * @return {@code true} if the {@link ECPAbstractCustomControlSWT} can handle a possibly existing
	 *         {@link org.eclipse.emfforms.spi.swt.core.EMFFormsControlProcessorService EMFFormsControlProcessorService}
	 *         itself, {@code false} otherwise.
	 * @since 1.8
	 */
	protected boolean canHandleControlProcessor() {
		return swtCustomControl.canHandleControlProcessor();
	}

	/**
	 * Calls a possibly existing {@link EMFFormsControlProcessorService} for the given {@code control}.
	 *
	 * @param control
	 *            The {@link Control} which is to be processed by the {@link EMFFormsControlProcessorService}.
	 * @since 1.8
	 */
	protected void defaultHandleControlProcessor(Control control) {
		if (getViewModelContext().hasService(EMFFormsControlProcessorService.class)) {
			final EMFFormsControlProcessorService service = getViewModelContext()
				.getService(EMFFormsControlProcessorService.class);
			service.process(control, getVElement(), getViewModelContext());
		}
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
				if (getControls().size() == 0 || getControls().values().iterator().next().isDisposed()) {
					return;
				}
				Label validationIcon = null;
				switch (getControls().size()) {
				case 3:
					validationIcon = Label.class.cast(getControls().get(
						new SWTGridCell(0, 1, CustomControlSWTRenderer.this)));
					break;
				default:
					break;
				}

				final VDiagnostic diag = getVElement().getDiagnostic();

				if (diag != null && validationIcon != null && !validationIcon.isDisposed()) {
					validationIcon.setImage(getValidationIcon());
					validationIcon.setToolTipText(diag.getMessage());
				}
				if (swtCustomControl != null) {
					swtCustomControl.applyValidation();
				}
			}
		});
	}

	private Image getValidationIcon() {
		return validationUiService.getValidationIcon(getVElement(), getViewModelContext());
	}
}
