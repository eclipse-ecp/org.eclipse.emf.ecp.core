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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;

/**
 * Factory to created new {@link ECPCellEditor cell editors}.
 *
 * @since 1.10
 */
public final class CellEditorFactory {
	private static final String CONTROL_EXTENSION = "org.eclipse.emf.ecp.edit.swt.cellEditor"; //$NON-NLS-1$

	private static final String CLASS_ATTRIBUTE = "class";//$NON-NLS-1$
	private static final String ID = "id";//$NON-NLS-1$
	private static final String TESTER = "tester";//$NON-NLS-1$

	private final Set<CellDescriptor> descriptors = new HashSet<CellEditorFactory.CellDescriptor>();
	/** CellEditorFactory instance. */
	public static final CellEditorFactory INSTANCE = new CellEditorFactory();

	private CellEditorFactory() {
		parseExtensionPoint();
	}

	private void parseExtensionPoint() {
		final IConfigurationElement[] controls = Platform.getExtensionRegistry().getConfigurationElementsFor(
			CONTROL_EXTENSION);
		for (final IConfigurationElement e : controls) {
			try {
				final String id = e.getAttribute(ID);
				final String clazz = e.getAttribute(CLASS_ATTRIBUTE);
				final Class<? extends CellEditor> resolvedClass = loadClass(e.getContributor().getName(), clazz);
				final ECPCellEditorTester tester = (ECPCellEditorTester) e.createExecutableExtension(TESTER);
				descriptors.add(new CellDescriptor(id, resolvedClass, tester));
			} catch (final ClassNotFoundException e1) {
				Activator.logException(e1);
			} catch (final CoreException e1) {
				Activator.logException(e1);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String clazz) throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(
			// TODO Grid
			// clazz
			// + LocalizationServiceHelper.getString(CellEditorFactory.class,
			// UtilMessageKeys.CellEditorFactory_CannotBeLoadedBecauseBundle)
			// + bundleName
			// + LocalizationServiceHelper.getString(CellEditorFactory.class,
			// UtilMessageKeys.CellEditorFactory_CannotBeResolved)
			);
		}
		return (Class<T>) bundle.loadClass(clazz);

	}

	/**
	 * Returns a new instance of the {@link CellEditor} for the given object.
	 *
	 * @param eStructuralFeature the {@link EStructuralFeature} displayed in the cell editor
	 * @param eObject the {@link EObject}
	 * @param table the parent composite
	 * @param viewModelContext the {@link ViewModelContext} used for the current view
	 * @return the cell editor
	 */
	@SuppressWarnings("restriction")
	public CellEditor getCellEditor(EStructuralFeature eStructuralFeature, EObject eObject, Composite table,
		ViewModelContext viewModelContext) {
		int bestPriority = -1;
		CellDescriptor bestCandidate = null;
		for (final CellDescriptor descriptor : descriptors) {
			final int priority = descriptor.getTester().isApplicable(eObject, eStructuralFeature, viewModelContext);
			if (priority > bestPriority) {
				bestCandidate = descriptor;
				bestPriority = priority;
			}
		}
		CellEditor result = null;
		if (bestCandidate != null) {
			try {
				final Constructor<? extends CellEditor> constructor = bestCandidate.getCellEditorClass()
					.getConstructor(
						Composite.class);
				result = constructor.newInstance(table);
				final ECPCellEditor ecpCellEditor = (ECPCellEditor) result;
				ecpCellEditor.instantiate(eStructuralFeature, viewModelContext);
			} catch (final SecurityException e) {
				Activator.logException(e);
			} catch (final NoSuchMethodException e) {
				Activator.logException(e);
			} catch (final IllegalArgumentException e) {
				Activator.logException(e);
			} catch (final InstantiationException e) {
				Activator.logException(e);
			} catch (final IllegalAccessException e) {
				Activator.logException(e);
			} catch (final InvocationTargetException e) {
				Activator.logException(e);
			} catch (final ClassCastException e) {
				Activator.logException(e);
			}
		}
		if (result == null) {
			result = new TextCellEditor(table);
		}
		return result;
	}

	/**
	 * Descriptor encapsulating the contributions to the <code>org.eclipse.emf.ecp.edit.swt.cellEditor</code> extension
	 * point.
	 */
	private class CellDescriptor {
		private final String id;
		private final Class<? extends CellEditor> cellEditorClass;
		private final ECPCellEditorTester tester;

		CellDescriptor(String id, Class<? extends CellEditor> cellEditorClass, ECPCellEditorTester tester) {
			super();
			this.id = id;
			this.cellEditorClass = cellEditorClass;
			this.tester = tester;
		}

		String getId() {
			return id;
		}

		Class<? extends CellEditor> getCellEditorClass() {
			return cellEditorClass;
		}

		ECPCellEditorTester getTester() {
			return tester;
		}
	}
}
