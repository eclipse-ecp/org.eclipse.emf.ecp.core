/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;

/**
 * Util for Grid Tests.
 *
 * @author Eugen Neufeld
 *
 */
public final class GridTestsUtil {

	private GridTestsUtil() {
	}

	public static GridTableViewer getTableViewerFromRenderer(AbstractSWTRenderer<VElement> renderer) {
		try {
			final Method method = TableControlSWTRenderer.class.getDeclaredMethod("getTableViewer");
			method.setAccessible(true);
			return (GridTableViewer) method.invoke(renderer);
		} catch (final NoSuchMethodException ex) {
			fail(ex.getMessage());
		} catch (final SecurityException ex) {
			fail(ex.getMessage());
		} catch (final IllegalAccessException ex) {
			fail(ex.getMessage());
		} catch (final IllegalArgumentException ex) {
			fail(ex.getMessage());
		} catch (final InvocationTargetException ex) {
			fail(ex.getMessage());
		}
		return null;
	}
}
