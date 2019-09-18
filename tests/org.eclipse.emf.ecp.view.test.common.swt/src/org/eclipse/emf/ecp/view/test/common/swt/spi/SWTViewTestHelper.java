/**
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
 * Jonas Helming - initial API and implementation
 * Christian W. Damus - bug 548592
 */
package org.eclipse.emf.ecp.view.test.common.swt.spi;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Helper Class for SWT Tests.
 *
 * @author Jonas Helming
 *
 */
public final class SWTViewTestHelper {
	// private static SWTRendererFactory factory = new SWTRendererFactoryImpl();
	private static EMFFormsRendererFactory factory;

	/**
	 * Helper interface to return the renderer along with the rendered control.
	 */
	public interface RendererResult {

		/**
		 * Get the renderer which was used to render the control.
		 *
		 * @return the renderer
		 */
		AbstractSWTRenderer<VElement> getRenderer();

		/**
		 * Get the control which was rendered (if any).
		 *
		 * @return the control
		 */
		Optional<Control> getControl();

	}

	static {
		final BundleContext bundleContext = FrameworkUtil.getBundle(SWTViewTestHelper.class).getBundleContext();
		final ServiceReference<EMFFormsRendererFactory> serviceReference = bundleContext
			.getServiceReference(EMFFormsRendererFactory.class);
		factory = bundleContext.getService(serviceReference);
	}

	private SWTViewTestHelper() {

	}

	/**
	 *
	 * @return a new {@link Shell} with a {@link FillLayout}
	 */
	public static Shell createShell() {
		final Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		return shell;
	}

	/**
	 * Renders the given {@link VElement} on the given {@link Shell}. The method will create a dummy domain model object
	 * as an input.
	 *
	 * @param renderable the {@link VElement} to be rendered
	 * @param shell The {@link Shell} to render on
	 * @return the rendered {@link Control}
	 * @throws NoRendererFoundException If a required sub renderer is not found
	 * @throws NoPropertyDescriptorFoundExeption If no PropertyDescriptor was found for the domain model instance
	 * @throws EMFFormsNoRendererException If the renderer for the given {@link VElement} is not found
	 */
	public static Control render(VElement renderable, Shell shell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, EMFFormsNoRendererException {
		return render(renderable, VViewFactory.eINSTANCE.createView(), shell);
	}

	/**
	 * Renders the given {@link VElement} on the given {@link Shell} and uses the given {@link EObject} as an input.
	 *
	 * @param renderable the {@link VElement} to be rendered
	 * @param input The input {@link EObject} (domain model instance)
	 * @param shell The {@link Shell} to render on
	 * @return the rendered {@link Control}
	 * @throws NoRendererFoundException If a required sub renderer is not found
	 * @throws NoPropertyDescriptorFoundExeption If no PropertyDescriptor was found for the domain model instance
	 * @throws EMFFormsNoRendererException If the renderer for the given {@link VElement} is not found
	 */
	public static Control render(VElement renderable, EObject input, Shell shell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, EMFFormsNoRendererException {

		final RendererResult result = renderControl(renderable, input, shell);
		if (result.getControl().isPresent()) {
			return result.getControl().get();
		}
		return null;
	}

	/**
	 * Renders the given {@link VElement} on the given {@link Shell} and uses the given {@link EObject} as an input.
	 *
	 * @param renderable the {@link VElement} to be rendered
	 * @param input The input {@link EObject} (domain model instance)
	 * @param shell The {@link Shell} to render on
	 * @return a {@link RendererResult}
	 * @throws NoRendererFoundException If a required sub renderer is not found
	 * @throws NoPropertyDescriptorFoundExeption If no PropertyDescriptor was found for the domain model instance
	 * @throws EMFFormsNoRendererException If the renderer for the given {@link VElement} is not found
	 */
	public static RendererResult renderControl(VElement renderable, EObject input, Shell shell)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, EMFFormsNoRendererException {

		final ViewModelContext viewContext = ViewModelContextFactory.INSTANCE.createViewModelContext(renderable, input);
		return renderControl(viewContext, shell);
	}

	/**
	 * Render the given {@code context} on the given {@code composite}.
	 *
	 * @param context view-model context to render
	 * @param composite the composite to render on
	 * @return a {@link RendererResult}
	 *
	 * @throws NoRendererFoundException If a required sub renderer is not found
	 * @throws NoPropertyDescriptorFoundExeption If no PropertyDescriptor was found for the domain model instance
	 * @throws EMFFormsNoRendererException If the renderer for the given {@link VElement} is not found
	 *
	 * @since 1.22
	 */
	public static RendererResult renderControl(ViewModelContext context, Composite composite)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, EMFFormsNoRendererException {

		final AbstractSWTRenderer<VElement> renderer = factory
			.getRendererInstance(context.getViewModel(), context);
		final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
			.createEmptyGridDescription());
		final Control control = renderer.render(gridDescription.getGrid().get(gridDescription.getColumns() - 1),
			composite);
		renderer.finalizeRendering(composite);

		return new RendererResult() {
			@Override
			public AbstractSWTRenderer<VElement> getRenderer() {
				return renderer;
			}

			@Override
			public Optional<Control> getControl() {
				return Optional.ofNullable(control);
			}
		};
	}

	/**
	 * Render the given {@code context} on the given {@code composite}.
	 *
	 * @param context view-model context to render
	 * @param composite the composite to render on
	 * @return the rendered SWT control
	 *
	 * @since 1.22
	 */
	public static Control render(ViewModelContext context, Composite composite) {
		try {
			final Optional<Control> result = renderControl(context, composite).getControl();
			return result.isPresent()
				? result.get()
				: null;
		} catch (NoRendererFoundException | NoPropertyDescriptorFoundExeption | EMFFormsNoRendererException e) {
			e.printStackTrace();
			fail("Failed to render: " + e.getMessage());
			return null; // Unreachable
		}
	}

	/**
	 *
	 * @param composite a {@link Composite} with a {@link GridLayout}
	 * @return the number of columns
	 */
	public static int getNumberofColumns(Composite composite) {
		final GridLayout gridLayout = (GridLayout) composite.getLayout();
		return gridLayout.numColumns;
	}

	/**
	 *
	 * @param control a control with a Horitontal Span specified
	 * @return the horizontal span
	 */
	public static int getHorizontalSpan(Control control) {
		final GridData gridData = (GridData) control.getLayoutData();
		return gridData.horizontalSpan;
	}

	/**
	 * Checks whether there is a text control.
	 *
	 * @param control the control to check its children
	 * @return if there is a text control
	 */
	public static boolean checkIfThereIsATextControlWithLabel(Control control) {
		if (!(control instanceof Composite)) {
			return false;
		}
		final Composite controlComposite = (Composite) control;

		return checkIfThereIsATextControl(controlComposite.getChildren()[2]);
	}

	/**
	 * Checks whether there is at least one text control in the children of a control (recursivly). The method will only
	 * check the first child on every level.
	 *
	 * @param control control the control to check its children
	 * @return if there is a text control
	 */
	public static boolean checkIfThereIsATextControl(Control control) {
		if (Text.class.isInstance(control)) {
			return true;
		} else if (Composite.class.isInstance(control)) {
			return checkIfThereIsATextControl(Composite.class.cast(control).getChildren()[0]);
		}
		return false;
	}

	/**
	 * Retrieve all text controls, which are children on a given control.
	 *
	 * @param control the control to check its children
	 * @return a {@link List} of {@link Text}
	 */
	public static List<Text> getAllTextControls(Control control) {
		final Composite controlComposite = (Composite) control;
		final List<Text> textFields = new ArrayList<Text>();
		for (final Control textControl : controlComposite.getChildren()) {
			if (textControl instanceof Text) {
				textFields.add((Text) textControl);
			} else if (textControl instanceof Composite) {
				textFields.addAll(getAllTextControls(textControl));
			}
		}
		return textFields;
	}
}
