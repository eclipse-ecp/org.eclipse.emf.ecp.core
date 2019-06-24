/*******************************************************************************
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
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 * Christian W. Damus - bugs 527686, 548592
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.ecp.view.model.common.AbstractRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.EMFFormsSWTLayoutUtil;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Common base class for all SWT specific renderer classes.
 *
 * A renderer using other renderers to render its contents must call this methods in this order:
 *
 * <pre>
 *  {@link #getGridDescription(SWTGridDescription)}
 *  for each SWTGridCell
 *  	{@link #render(SWTGridCell, Composite)}
 * {@link #finalizeRendering(Composite)}
 * </pre>
 *
 * If you don't call {@link #finalizeRendering(Composite)} after the rendering, the automatic disposing of the renderer
 * will not work, as well as the initial validation check.
 *
 * @author Eugen Neufeld
 *
 * @param <VELEMENT> the actual type of the {@link VElement} to be drawn
 * @since 1.2
 */
public abstract class AbstractSWTRenderer<VELEMENT extends VElement> extends AbstractRenderer<VELEMENT> {

	/**
	 * Variant constant for indicating RAP controls.
	 */
	protected static final String CUSTOM_VARIANT = "org.eclipse.rap.rwt.customVariant"; //$NON-NLS-1$
	private ModelChangeListener listener;
	private Map<SWTGridCell, Control> controls;
	private boolean renderingFinished;

	/**
	 * Default Constructor.
	 *
	 * @param vElement the view element to be rendered
	 * @param viewContext The view model context
	 * @param reportService the ReportService to use
	 * @since 1.6
	 */
	public AbstractSWTRenderer(final VELEMENT vElement, final ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	/**
	 * Returns the GridDescription for this Renderer.
	 *
	 * @param gridDescription the current {@link SWTGridDescription}
	 * @return the number of controls per row
	 * @since 1.3
	 */
	public abstract SWTGridDescription getGridDescription(SWTGridDescription gridDescription);

	/**
	 * Initializes the {@link AbstractSWTRenderer}.
	 *
	 * @since 1.6
	 */
	public final void init() {
		preInit();
		controls = new LinkedHashMap<SWTGridCell, Control>();
		if (getViewModelContext() != null) {
			listener = new ViewChangeListener();
			getViewModelContext().registerViewChangeListener(listener);
		}
		getViewModelContext().addContextUser(this);

		postInit();
	}

	/**
	 * Returns a copy of the {@link GridCell} to {@link Control} map.
	 *
	 * @return a copy of the controls map
	 * @since 1.3
	 */
	protected final Map<SWTGridCell, Control> getControls() {
		if (controls == null) {
			return Collections.emptyMap();
		}
		return new LinkedHashMap<SWTGridCell, Control>(controls);
	}

	/**
	 * Use this method to initialize objects which are needed already before rendering.
	 *
	 * @since 1.3
	 */
	protected void preInit() {

	}

	/**
	 * Use this method to initialize objects which are needed during rendering.
	 *
	 * @since 1.3
	 */
	protected void postInit() {

	}

	/**
	 * Disposes all resources used by the renderer.
	 * Don't forget to call super.dispose if overwriting this method.
	 *
	 * @since 1.3
	 */
	@Override
	protected void dispose() {
		if (getViewModelContext() != null) {
			getViewModelContext().unregisterViewChangeListener(listener);
		}
		listener = null;
		controls = null;
		getViewModelContext().removeContextUser(this);

		super.dispose();
	}

	/**
	 * Renders the passed {@link VElement}.
	 *
	 * @param cell the {@link SWTGridCell} of the control to render
	 * @param parent the {@link Composite} to render on
	 * @return the rendered {@link Control}
	 * @throws NoRendererFoundException this is thrown when a renderer cannot be found
	 * @throws NoPropertyDescriptorFoundExeption this is thrown when no property descriptor can be found
	 * @since 1.3
	 */
	public Control render(final SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		Control control = controls.get(cell);
		if (control != null) {
			return control;
		}

		control = renderControl(cell, parent);
		if (control == null) {
			// something went wrong, log
			return null;
		}
		controls.put(cell, control);

		// register dispose listener to rerender if disposed
		control.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (controls != null) {
					controls.remove(cell);
				}
			}
		});

		return control;
	}

	/**
	 * Called by the framework to initialize listener.
	 *
	 * @param parent the parent used during render
	 * @since 1.3
	 */
	public void finalizeRendering(Composite parent) {
		if (renderingFinished) {
			return;
		}
		renderingFinished = true;

		// Apply visibility, enablement, and validation decorations
		if (!getVElement().isVisible()) {
			/* convention is to render visible, so only call apply if we are invisible */
			applyVisible();
		}
		applyReadOnly();
		if (!ignoreEnableOnReadOnly()) {
			applyEnable();
		}
		applyValidation();

		// When some control is disposed, dispose me. Don't worry about
		// my parent composite because I could be a detail view that is cached
		// independently of it in a master-detail situation
		if (!controls.isEmpty()) {
			controls.values().iterator().next().addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent event) {
					dispose();
				}
			});
		}
	}

	/**
	 * Query whether rendering has completed and I am ready for user interaction.
	 *
	 * @return whether rendering has finished
	 *
	 * @since 1.22
	 */
	protected boolean isRenderingFinished() {
		return renderingFinished;
	}

	/**
	 * Returns <code>true</code> when read only will always force control to be disabled.
	 *
	 * @return <code>true</code> when read only will always force control to be disabled.
	 */
	protected boolean ignoreEnableOnReadOnly() {
		return getVElement().isEffectivelyReadonly();
	}

	/**
	 * Renders the passed {@link VElement}.
	 *
	 * @param cell the {@link GridCell} of the control to render
	 * @param parent the {@link Composite} to render on
	 * @return the rendered {@link Control}
	 * @throws NoRendererFoundException this is thrown when a renderer cannot be found
	 * @throws NoPropertyDescriptorFoundExeption this is thrown when no property descriptor can be found
	 * @since 1.3
	 */
	protected abstract Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption;

	/**
	 * Marks a controls as readonly.
	 *
	 * @since 1.3
	 *
	 */
	protected void applyReadOnly() {
		// Do nothing, implement behavior in implementing class if needed
	}

	/**
	 * Allows implementers to set a control to enabled.
	 *
	 * @since 1.3
	 *
	 */
	protected void applyEnable() {
		// Do nothing, implement behavior in implementing class if needed
	}

	/**
	 * Wraps the call to enable/disable a control.
	 *
	 * @param gridCell the {@link SWTGridCell} to enable/disable
	 * @param control the {@link Control} to enable/disable
	 * @param enabled true if control should be enabled, false otherwise
	 * @since 1.3
	 */
	protected void setControlEnabled(SWTGridCell gridCell, Control control, boolean enabled) {
		control.setEnabled(enabled);
	}

	/**
	 * Allows implementers to check and set the visibility on the whole result row.
	 *
	 * @since 1.3
	 *
	 */
	protected void applyVisible() {
		final boolean visible = getVElement().isVisible();
		/* avoid multiple layout calls by saving the parents which need to be relayouted */
		final Set<Composite> parents = new LinkedHashSet<Composite>();
		for (final SWTGridCell gridCell : controls.keySet()) {
			final Object layoutData = controls.get(gridCell).getLayoutData();
			if (GridData.class.isInstance(layoutData)) {
				final GridData gridData = (GridData) layoutData;
				if (gridData != null) {
					gridData.exclude = !visible;
				}
			}
			controls.get(gridCell).setVisible(visible);
			parents.add(controls.get(gridCell).getParent());
		}
		for (final Composite composite : parents) {
			EMFFormsSWTLayoutUtil.adjustParentSize(composite.getChildren()[0]);
		}
	}

	/**
	 * Allows implementers to display the validation state of the control.
	 * The default implementation does nothing.
	 *
	 * @since 1.3
	 */
	protected void applyValidation() {

	}

	/**
	 * Called before the {@link #applyValidation()}. This method allows to create a diff between the old diagnostic and
	 * the new diagnostic and thus improve the performance of the overlay apply by triggering it only on the relevant
	 * elements.
	 *
	 * @param oldDiagnostic The previous {@link VDiagnostic}
	 * @param newDiagnostic The current {@link VDiagnostic}
	 * @since 1.14
	 */
	protected void applyValidation(VDiagnostic oldDiagnostic, VDiagnostic newDiagnostic) {

	}

	/**
	 * @return String the default font name on the system.
	 * @param control The control to derive the default font name from
	 *
	 * @since 1.5
	 */
	protected String getDefaultFontName(Control control) {
		return control.getDisplay().getSystemFont().getFontData()[0].getName();
	}

	/**
	 * If my control is rendered within a scrolled composite, scroll that composite to reveal me.
	 *
	 * @since 1.22
	 */
	public void scrollToReveal() {
		if (controls != null && !controls.isEmpty()) {
			@SuppressWarnings("serial")
			final Iterator<Control> iter = new AbstractTreeIterator<Control>(controls.values(), false) {
				@Override
				protected Iterator<? extends Control> getChildren(Object object) {
					if (object instanceof Composite) {
						return Arrays.asList(((Composite) object).getChildren()).iterator();
					}
					if (object instanceof Collection<?>) {
						@SuppressWarnings("unchecked")
						final Collection<? extends Control> collection = (Collection<? extends Control>) object;
						return collection.iterator();
					}
					return Collections.emptyIterator();
				}
			};

			while (iter.hasNext()) {
				final Control next = iter.next();
				if (next instanceof Composite) {
					// Don't attempt to reveal composites
					continue;
				}

				if (scrollToReveal(next)) {
					break;
				}
			}
		}
	}

	/**
	 * Scroll composites as necessary to reveal the given {@code control} and
	 * then request focus.
	 *
	 * @param control the control to reveal and focus
	 * @return whether the focus was successfully set (usually because the control
	 *         is one that can receive input focus)
	 *
	 * @since 1.22
	 */
	protected boolean scrollToReveal(final Control control) {
		for (Composite parent = control.getParent(); parent != null; parent = parent.getParent()) {
			if (parent instanceof ScrolledComposite) {
				final ScrolledComposite scrolled = (ScrolledComposite) parent;
				scrolled.showControl(control);
			}
		}

		// Try to request focus
		return control.setFocus();
	}

	/**
	 * Query whether a given {@code control} can plausibly be revealed.
	 *
	 * @param control a control to be revealed
	 * @return whether it reasonably can be revealed
	 *
	 * @since 1.22
	 */
	protected boolean canReveal(Control control) {
		return control != null && !control.isDisposed() && control.isVisible();
	}

	//
	// Nested types
	//

	/**
	 * A listener that reacts to view-model changes to update the UI accordingly.
	 * Examples include updating enablement/visibility state and diagnostic decorations.
	 */
	private final class ViewChangeListener implements ModelChangeListener {
		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (!renderingFinished) {
				return;
			}

			// We ned to handle touch events for the diagnostic if in the case of
			// re-use of the view model we update diagnostics in situ
			if (notification.getRawNotification().isTouch() &&
				notification.getStructuralFeature() != VViewPackage.Literals.ELEMENT__DIAGNOSTIC) {
				return;
			}
			// Always apply enable and read-only if it changed because it might have changed for a parent
			if (notification.getStructuralFeature() == VViewPackage.Literals.ELEMENT__ENABLED) {
				if (!ignoreEnableOnReadOnly()) {
					applyEnable();
				}
			} else if (notification.getStructuralFeature() == VViewPackage.Literals.ELEMENT__READONLY) {
				applyReadOnly();
			}
			if (notification.getNotifier() != getVElement()) {
				return;
			}
			if (notification.getStructuralFeature() == VViewPackage.Literals.ELEMENT__VISIBLE) {
				applyVisible();
			} else if (notification.getStructuralFeature() == VViewPackage.Literals.ELEMENT__DIAGNOSTIC) {
				final VDiagnostic newDia = (VDiagnostic) notification.getRawNotification().getNewValue();
				final VDiagnostic oldDia = (VDiagnostic) notification.getRawNotification().getOldValue();
				applyValidation(oldDia, newDia);
				applyValidation();
			}
		}
	}

}
