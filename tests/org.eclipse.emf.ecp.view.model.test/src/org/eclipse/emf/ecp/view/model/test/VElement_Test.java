/*******************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for the {@link VElement} custom code.
 *
 * @author Christian W. Damus
 */
@RunWith(Enclosed.class)
public abstract class VElement_Test {

	// BEGIN COMPLEX CODE - visibility for nested classes
	VElement fixture;
	VElement parent;
	VElement root;
	// END COMPLEX CODE

	@Before
	public void createFixture() {
		root = VViewFactory.eINSTANCE.createView();
		parent = VGroupFactory.eINSTANCE.createGroup();
		((VView) root).getChildren().add((VGroup) parent);
		fixture = VViewFactory.eINSTANCE.createControl();
		((VGroup) parent).getChildren().add((VControl) fixture);
	}

	//
	// Test partitions
	//

	@RunWith(JUnit4.class)
	public static class DefaultState extends VElement_Test {
		/**
		 * Initialies me.
		 */
		public DefaultState() {
			super();
		}

		@Test
		public void defaultState() {
			for (final VElement next : Arrays.asList(fixture, parent, root)) {
				assertThat(next.isEffectivelyEnabled(), is(true));
				assertThat(next.isEffectivelyVisible(), is(true));
				assertThat(next.isEffectivelyReadonly(), is(false));
			}
		}
	}

	@RunWith(Parameterized.class)
	public static class EffectiveState extends VElement_Test {
		private final Which which;
		private final What what;

		/**
		 * Initializes me with {@code which} element is to be made explicitly
		 * {@code what}.
		 *
		 * @param which which element is to be made
		 * @param what what
		 */
		public EffectiveState(Which which, What what) {
			super();

			this.which = which;
			this.what = what;
		}

		/**
		 * Asserts that the target fixture is effectively what it is explicitly set to.
		 */
		@Test
		public void explicitlyWhat() {
			assertThat(which.name() + " is not effectively " + what,
				what.isEffectivelyWhat(which.get(this)));
		}

		/**
		 * Asserts that the target fixture is effectively what some container is
		 * explicitly set to.
		 */
		@Test
		public void effectivelyWhat() {
			if (which == Which.FIXTURE) {
				// This is the explicit case, tested separately, so paranoiac check that
				// we don't somehow infer state down the tree from containers
				assertThat("root is effectively " + what,
					!what.isEffectivelyWhat(root));
			} else {
				assertThat("fixture is not effectively " + what,
					what.isEffectivelyWhat(fixture));
			}
		}

		//
		// Test framework
		//

		@Parameters(name = "{index}: {0}, {1}")
		public static Iterable<Object[]> parameters() {
			final List<Object[]> result = new ArrayList<Object[]>(Which.values().length * What.values().length);

			for (final Which which : Which.values()) {
				for (final What what : What.values()) {
					result.add(new Object[] { which, what });
				}
			}

			return result;
		}

		@Override
		@Before
		public void createFixture() {
			super.createFixture();

			what.setWhat(which.get(this));
		}

		enum Which {
			FIXTURE, PARENT, ROOT;

			VElement get(EffectiveState test) {
				switch (this) {
				case FIXTURE:
					return test.fixture;
				case PARENT:
					return test.parent;
				case ROOT:
					return test.root;
				default:
					throw new LinkageError("Missing case for " + this); //$NON-NLS-1$
				}
			}
		}

		enum What {
			READ_ONLY, DISABLED, INVISIBLE;

			void setWhat(VElement element) {
				switch (this) {
				case READ_ONLY:
					element.setReadonly(true);
					break;
				case INVISIBLE:
					element.setVisible(false);
					break;
				case DISABLED:
					element.setEnabled(false);
					break;
				default:
					throw new LinkageError("Missing case for " + this); //$NON-NLS-1$
				}
			}

			boolean isEffectivelyWhat(VElement element) {
				switch (this) {
				case READ_ONLY:
					return element.isEffectivelyReadonly();
				case INVISIBLE:
					return !element.isEffectivelyVisible();
				case DISABLED:
					return !element.isEffectivelyEnabled();
				default:
					throw new LinkageError("Missing case for " + this); //$NON-NLS-1$
				}
			}
		}
	}
}
