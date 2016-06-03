/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Tester for Text Renderer.
 *
 * @author Eugen Neufeld
 *
 */
public class NumberControlRendererTester implements ECPRendererTester {

	private static Set<NumberTester> testers = new LinkedHashSet<NumberControlRendererTester.NumberTester>();

	static {
		testers.add(new NumberTester(Integer.class));
		testers.add(new NumberTester(Long.class));
		testers.add(new NumberTester(Float.class));
		testers.add(new NumberTester(Double.class));
		testers.add(new NumberTester(BigInteger.class));
		testers.add(new NumberTester(BigDecimal.class));
		testers.add(new NumberTester(Short.class));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.common.ECPRendererTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		int maxResult = NOT_APPLICABLE;
		for (final NumberTester tester : testers) {
			final int result = tester.isApplicable(vElement, viewModelContext);
			if (result > maxResult) {
				maxResult = result;
			}
		}
		return maxResult;
	}

	/**
	 * Inner class to encapsulate numerical tests.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private static class NumberTester extends SimpleControlRendererTester {
		private final Class<?> clazz;

		NumberTester(Class<?> clazz) {
			this.clazz = clazz;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.model.common.SimpleControlRendererTester#isSingleValue()
		 */
		@Override
		protected boolean isSingleValue() {
			return true;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.model.common.SimpleControlRendererTester#getPriority()
		 */
		@Override
		protected int getPriority() {
			return 2;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.model.common.SimpleControlRendererTester#getSupportedClassType()
		 */
		@Override
		protected Class<?> getSupportedClassType() {
			return clazz;
		}
	}

}
