/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Jonas Helming - initial API and implementation
 */
package org.eclipse.emf.ecp.view.test.common.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.swt.SWTRendererFactoryImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("restriction")
public final class SWTViewTestHelper {
	private static SWTRendererFactory factory = new SWTRendererFactoryImpl();

	private SWTViewTestHelper() {

	}

	public static Shell createShell() {
		final Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		return shell;
	}

	public static Control render(VElement renderable, EObject input, Shell shell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final ViewModelContext viewContext = ViewModelContextFactory.INSTANCE.createViewModelContext(renderable, input);
		final AbstractSWTRenderer<VElement> renderer = factory
			.getRenderer(renderable, viewContext);
		final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
			.createEmptyGridDescription());
		final Control control = renderer.render(gridDescription.getGrid().get(gridDescription.getColumns() - 1), shell);
		renderer.finalizeRendering(shell);
		// TODO return resultRows
		if (control == null) {
			return null;
		}

		return control;

	}

	public static Control render(VElement renderable, Shell shell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		return render(renderable, VViewFactory.eINSTANCE.createView(), shell);
	}

	public static int getNumberofColumns(Composite composite) {
		final GridLayout gridLayout = (GridLayout) composite.getLayout();
		return gridLayout.numColumns;
	}

	public static int getHorizontalSpan(Composite composite) {
		final GridData gridData = (GridData) composite.getLayoutData();
		return gridData.horizontalSpan;
	}

	public static boolean checkIfThereIsATextControlWithLabel(Control control) {
		if (!(control instanceof Composite)) {
			return false;
		}
		final Composite controlComposite = (Composite) control;

		return checkIfThereIsATextControl(controlComposite.getChildren()[2]);
	}

	public static boolean checkIfThereIsATextControl(Control control) {
		if (Text.class.isInstance(control)) {
			return true;
		}
		Composite controlComposite = (Composite) control;
		controlComposite = (Composite) controlComposite.getChildren()[0];
		// controlComposite = (Composite) controlComposite.getChildren()[0];
		final Control textControl = controlComposite.getChildren()[0];

		return textControl instanceof Text;
	}

	public static List<Text> getAllTextControls(Control control) {
		final Composite controlComposite = (Composite) control;
		final List<Text> textFields = new ArrayList<Text>();
		for (final Control textControl : controlComposite.getChildren()) {
			if (textControl instanceof Text) {
				textFields.add((Text) textControl);
			}
			else if (textControl instanceof Composite) {
				textFields.addAll(getAllTextControls(textControl));
			}
		}
		return textFields;
	}
}
