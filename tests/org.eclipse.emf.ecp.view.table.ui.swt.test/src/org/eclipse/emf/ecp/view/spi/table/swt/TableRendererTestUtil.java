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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.jface.viewers.TableViewer;

/**
 * Util for Table Tests.
 *
 * @author Eugen Neufeld
 *
 */
public final class TableRendererTestUtil {

	private TableRendererTestUtil() {
	}

	public static TableViewer getTableViewerFromRenderer(AbstractSWTRenderer<VElement> renderer) {
		try {
			final Method method = TableControlSWTRenderer.class.getDeclaredMethod("getTableViewer");
			method.setAccessible(true);
			return (TableViewer) method.invoke(renderer);
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
