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
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelUtil;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.AbstractAdditionalSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

/**
 * The {@link ContainerSWTRenderer} is a super class for all Renderer which renders its contents vertically.
 *
 * @param <VELEMENT> the {@link VElement} of the renderer
 * @author Eugen Neufeld
 *
 */
public abstract class ContainerSWTRenderer<VELEMENT extends VElement> extends AbstractSWTRenderer<VELEMENT> {
	private final EMFFormsRendererFactory factory;
	private final EMFFormsDatabinding emfFormsDatabinding;

	/**
	 * The {@link EMFFormsRendererFactory} to use.
	 *
	 * @return the {@link EMFFormsRendererFactory}
	 * @since 1.6
	 */
	protected final EMFFormsRendererFactory getEMFFormsRendererFactory() {
		return factory;
	}

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param factory the {@link EMFFormsRendererFactory}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @since 1.6
	 */
	public ContainerSWTRenderer(VELEMENT vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory factory, EMFFormsDatabinding emfFormsDatabinding) {
		super(vElement, viewContext, reportService);
		this.factory = factory;
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	private SWTGridDescription rendererGridDescription;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(int, org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected Control renderControl(SWTGridCell gridCell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		if (gridCell.getColumn() != 0) {
			return null;
		}

		final Composite columnComposite = getComposite(parent);
		columnComposite.setData(CUSTOM_VARIANT, getCustomVariant());
		columnComposite.setBackground(parent.getBackground());

		final Map<VContainedElement, Collection<AbstractSWTRenderer<VElement>>> elementRendererMap = new LinkedHashMap<VContainedElement, Collection<AbstractSWTRenderer<VElement>>>();
		SWTGridDescription maximalGridDescription = null;
		final Map<VContainedElement, SWTGridDescription> rowGridDescription = new LinkedHashMap<VContainedElement, SWTGridDescription>();
		final Map<VContainedElement, SWTGridDescription> controlGridDescription = new LinkedHashMap<VContainedElement, SWTGridDescription>();

		for (final VContainedElement child : getChildren()) {

			if (!isValidElement(child)) {
				continue;
			}

			AbstractSWTRenderer<VElement> renderer;
			try {
				renderer = getEMFFormsRendererFactory().getRendererInstance(child,
					getViewModelContext());
			} catch (final EMFFormsNoRendererException ex) {
				getReportService().report(
					new StatusReport(
						new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
							"No Renderer for %s found.", child.eClass().getName()), ex))); //$NON-NLS-1$
				continue;
			}

			final Collection<AbstractAdditionalSWTRenderer<VElement>> additionalRenderers = getEMFFormsRendererFactory()
				.getAdditionalRendererInstances(child,
					getViewModelContext());
			SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
				.createEmptyGridDescription());
			controlGridDescription.put(child, gridDescription);

			for (final AbstractAdditionalSWTRenderer<VElement> additionalRenderer : additionalRenderers) {
				gridDescription = additionalRenderer.getGridDescription(gridDescription);
			}
			rowGridDescription.put(child, gridDescription);
			if (maximalGridDescription == null
				|| maximalGridDescription.getColumns() < gridDescription.getColumns())
			{
				maximalGridDescription = gridDescription;
			}
			final Set<AbstractSWTRenderer<VElement>> allRenderer = new LinkedHashSet<AbstractSWTRenderer<VElement>>();
			allRenderer.add(renderer);
			allRenderer.addAll(additionalRenderers);
			elementRendererMap.put(child, allRenderer);
		}
		if (maximalGridDescription == null) {
			return columnComposite;
		}
		columnComposite.setLayout(getLayout(maximalGridDescription.getColumns(), false));
		for (final VContainedElement child : getChildren()) {
			if (!isValidElement(child)) {
				continue;
			}
			final SWTGridDescription gridDescription = rowGridDescription.get(child);
			if (gridDescription == null) {
				continue;
			}
			for (final SWTGridCell childGridCell : gridDescription.getGrid()) {

				Control control = null;
				try {
					control = childGridCell.getRenderer().render(childGridCell,
						columnComposite);
				} catch (final NoRendererFoundException ex) {
					getReportService().report(new RenderingFailedReport(ex));
					if (ViewModelUtil.isDebugMode()) {
						control = renderDiagnoseControl(columnComposite, child);
					}

				} catch (final NoPropertyDescriptorFoundExeption ex) {
					getReportService().report(new RenderingFailedReport(ex));
					if (ViewModelUtil.isDebugMode()) {
						control = renderDiagnoseControl(columnComposite, child);
					}
				}

				// TODO who should apply the layout
				if (control == null) {
					continue;
				}

				// TODO possible layout issues?
				setLayoutDataForControl(childGridCell, controlGridDescription.get(child), gridDescription,
					maximalGridDescription,
					childGridCell.getRenderer().getVElement(), control);

			}
			for (final SWTGridCell childGridCell : gridDescription.getGrid()) {
				childGridCell.getRenderer().finalizeRendering(columnComposite);
			}
		}

		return columnComposite;
	}

	private boolean isValidElement(VContainedElement child) {
		if (VControl.class.isInstance(child)) {
			if (VControl.class.cast(child).getDomainModelReference() == null) {
				return false;
			}
			// TODO: define behaviour that defines when a control is valid
			// try {
			// getEMFFormsDatabinding()
			// .getValueProperty(VControl.class.cast(child).getDomainModelReference());
			// } catch (final DatabindingFailedException ex) {
			// getReportService().report(new RenderingFailedReport(ex));
			// return false;
			// }
		}
		return true;
	}

	/**
	 * Package visible method, to allow an easy replacement.
	 *
	 * @return The EMFFormsDatabinding
	 */
	private EMFFormsDatabinding getEMFFormsDatabinding() {
		// Method is eventually needed to check the validity of controls that are to be rendered.
		return emfFormsDatabinding;
	}

	// TODO: possible duplicate code
	private Control renderDiagnoseControl(Composite parent, VContainedElement child) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new FillLayout());
		final Label label = new Label(composite, SWT.NONE);
		label.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		label.setText("An error occurred while rendering " + child.getClass().getCanonicalName()); //$NON-NLS-1$

		return composite;
	}

	/**
	 * The custom variant to set for styling.
	 *
	 * @return the string used by rap for styling
	 */
	protected String getCustomVariant() {
		return "org_eclipse_emf_ecp_view_container"; //$NON-NLS-1$
	}

	/**
	 * The collection of children to iterate over.
	 *
	 * @return the collection of children to render
	 */
	protected Collection<VContainedElement> getChildren() {
		if (VViewPackage.eINSTANCE.getContainer().isInstance(getVElement())) {
			return VContainer.class.cast(getVElement()).getChildren();
		}
		return Collections.emptySet();
	}

	/**
	 * Allows to customize the composite which is used to render the children onto.
	 *
	 * @param parent the parent {@link Composite} to use as a parent
	 * @return the {@link Composite} or a subclass to use
	 */
	protected Composite getComposite(Composite parent) {
		return new Composite(parent, SWT.NONE);
	}

	/**
	 * Returns the layout to use.
	 *
	 * @param numControls number of columns to create
	 * @param equalWidth whether the columns should be equal
	 * @return the {@link Layout}
	 */
	protected Layout getLayout(int numControls, boolean equalWidth) {
		return LayoutProviderHelper.getColumnLayout(numControls, equalWidth);
	}

	/**
	 * Sets the LayoutData for the specified control.
	 *
	 * @param gridCell the {@link GridCell} used to render the control
	 * @param gridDescription the {@link GridDescription} of the parent which rendered the control
	 * @param currentRowGridDescription the {@link GridDescription} of the current row
	 * @param fullGridDescription the {@link GridDescription} of the whole container
	 * @param vElement the {@link VElement} to set the layoutData for
	 * @param control the control to set the layout to
	 * @since 1.6
	 */
	protected void setLayoutDataForControl(SWTGridCell gridCell, SWTGridDescription gridDescription,
		SWTGridDescription currentRowGridDescription, SWTGridDescription fullGridDescription, VElement vElement,
		Control control) {

		control.setLayoutData(LayoutProviderHelper.getLayoutData(gridCell, gridDescription, currentRowGridDescription,
			fullGridDescription, vElement, getViewModelContext().getDomainModel(), control));

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		super.dispose();
	}

}
