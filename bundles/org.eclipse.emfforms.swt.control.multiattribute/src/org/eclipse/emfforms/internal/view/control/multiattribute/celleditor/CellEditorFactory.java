/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.view.control.multiattribute.celleditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.view.control.multiattribute.celleditor.MultiAttributeSWTRendererCellEditorTester;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Factory to created new {@link ECPCellEditor cell editors} for the
 * {@link org.eclipse.emfforms.spi.view.control.multiattribute.MultiAttributeSWTRenderer MultiAttributeSWTRenderer}.
 *
 * @author Johannes Faltermeier
 *
 */
public final class CellEditorFactory {

	/**
	 * The singleton instance.
	 */
	public static final CellEditorFactory INSTANCE = new CellEditorFactory();

	private static final String POINT_ID = "org.eclipse.emfforms.swt.control.multiattribute.cellEditor"; //$NON-NLS-1$
	private static final String CLAZZ = "class";//$NON-NLS-1$
	private static final String TESTER = "tester";//$NON-NLS-1$

	private final Set<CellEditorDescriptor> descriptors = new LinkedHashSet<CellEditorDescriptor>();

	private CellEditorFactory() {
		parseExtensionPoint();
	}

	private void parseExtensionPoint() {
		final IConfigurationElement[] editors = Platform.getExtensionRegistry().getConfigurationElementsFor(
			POINT_ID);
		for (final IConfigurationElement editor : editors) {
			try {
				final String clazz = editor.getAttribute(CLAZZ);
				final Class<? extends CellEditor> resolvedClass = loadClass(editor.getContributor().getName(), clazz);
				final MultiAttributeSWTRendererCellEditorTester tester = MultiAttributeSWTRendererCellEditorTester.class
					.cast(editor.createExecutableExtension(TESTER));
				descriptors.add(new CellEditorDescriptor(resolvedClass, tester));
			} catch (final ClassNotFoundException e) {
				reportException(e);
			} catch (final CoreException e) {
				reportException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String clazz) throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(MessageFormat
				.format("Class {0} cannot be loaded because bundle {1} cannot be resolved", clazz, bundleName)); //$NON-NLS-1$
		}
		return (Class<T>) bundle.loadClass(clazz);
	}

	/**
	 * Creates a new {@link CellEditor} for the given arguments. If no fitting {@link CellEditor} is found at the
	 * extension point or if there was an exception during the creation a {@link TextCellEditor} will be returned as a
	 * default.
	 *
	 * @param multiAttribute the feature which is displayed by the
	 *            {@link org.eclipse.emfforms.spi.view.control.multiattribute.MultiAttributeSWTRenderer
	 *            MultiAttributeSWTRenderer}
	 * @param eObject the {@link EObject}
	 * @param table the {@link Table}
	 * @param viewModelContext the {@link ViewModelContext}
	 * @return a {@link ECPCellEditor} or a {@link TextCellEditor} as a fallback
	 */
	public CellEditor createCellEditor(EAttribute multiAttribute, EObject eObject, Table table,
		ViewModelContext viewModelContext) {
		double bestPriority = -Double.MIN_VALUE;
		CellEditorDescriptor bestCandidate = null;
		for (final CellEditorDescriptor descriptor : descriptors) {
			final double priority = descriptor.getTester().isApplicable(eObject, multiAttribute, viewModelContext);
			if (Double.isNaN(priority)) {
				continue;
			}
			if (priority > bestPriority) {
				bestCandidate = descriptor;
				bestPriority = priority;
			}
		}
		CellEditor result = null;
		if (bestCandidate != null) {
			result = createCellEditor(multiAttribute, table, viewModelContext, bestCandidate, result);
		}
		if (result == null) {
			result = new TextCellEditor(table);
		}
		return result;
	}

	private CellEditor createCellEditor(EStructuralFeature eStructuralFeature, Table table,
		ViewModelContext viewModelContext, CellEditorDescriptor bestCandidate, CellEditor result) {
		try {
			final Constructor<? extends CellEditor> constructor = bestCandidate.getCellEditorClass()
				.getConstructor(
					Composite.class);
			result = constructor.newInstance(table);
			final ECPCellEditor ecpCellEditor = (ECPCellEditor) result;
			ecpCellEditor.instantiate(eStructuralFeature, viewModelContext);
		} catch (final SecurityException e) {
			reportException(e);
		} catch (final NoSuchMethodException e) {
			reportException(e);
		} catch (final IllegalArgumentException e) {
			reportException(e);
		} catch (final InstantiationException e) {
			reportException(e);
		} catch (final IllegalAccessException e) {
			reportException(e);
		} catch (final InvocationTargetException e) {
			reportException(e);
		} catch (final ClassCastException e) {
			reportException(e);
		}
		return result;
	}

	private void reportException(Throwable throwable) {
		final Bundle bundle = FrameworkUtil.getBundle(CellEditorFactory.class);
		final BundleContext bundleContext = bundle.getBundleContext();
		final ServiceReference<ReportService> serviceReference = bundleContext.getServiceReference(ReportService.class);
		final ReportService reportService = bundleContext.getService(serviceReference);
		reportService.report(new AbstractReport(throwable));
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Descriptor for the elements registered at the extension point.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private static final class CellEditorDescriptor {

		private final Class<? extends CellEditor> cellEditorClass;
		private final MultiAttributeSWTRendererCellEditorTester tester;

		private CellEditorDescriptor(Class<? extends CellEditor> cellEditorClass,
			MultiAttributeSWTRendererCellEditorTester tester) {
			this.cellEditorClass = cellEditorClass;
			this.tester = tester;
		}

		private Class<? extends CellEditor> getCellEditorClass() {
			return cellEditorClass;
		}

		private MultiAttributeSWTRendererCellEditorTester getTester() {
			return tester;
		}
	}

}
