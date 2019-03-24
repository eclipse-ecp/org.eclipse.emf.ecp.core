/*******************************************************************************
 * Copyright (c) 2019 EclipseSource Muenchen GmbH and others.
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

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.impl.VDiagnosticImpl;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Gender;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the custom code in the {@link VDiagnosticImpl} class.
 */
public class VDiagnostic_Test {

	private static final EStructuralFeature NAME = BowlingPackage.Literals.PLAYER__NAME;
	private static final EStructuralFeature DOB = BowlingPackage.Literals.PLAYER__DATE_OF_BIRTH;
	private static final EStructuralFeature PLAYERS = BowlingPackage.Literals.LEAGUE__PLAYERS;

	private final VDiagnostic fixture = VViewFactory.eINSTANCE.createDiagnostic();

	private League league;
	private Player alice;
	private Player brenda;
	private Player cathy;

	/**
	 * Test uniqueness of the diagnostic list.
	 */
	@Test
	public void getDiagnostics() {
		final List<Object> diagnostics = fixture.getDiagnostics();

		assertThat(diagnostics.size(), is(0));
		diagnostics.add(warning(alice, NAME, "first"));
		diagnostics.add(ok(brenda, NAME, "brenda"));

		assertThat(diagnostics.size(), is(2));

		assertThat(diagnostics.add(warning(cathy, NAME, "second")), is(true));
		assertThat(diagnostics.add(warning(cathy, NAME, "second")), is(false));

		diagnostics.addAll(Arrays.asList(
			warning(alice, NAME, "first"), // dupe
			warning(alice, NAME, "second"), // new
			ok(brenda, NAME, "brenda"), // dupe
			error(brenda, NAME, "brenda"), // new
			warning(cathy, DOB, "second") // new
		));

		assertThat(diagnostics.size(), is(6));

		assertThat(diagnostics, hasItems(
			warning(alice, NAME, "first"),
			ok(brenda, NAME, "brenda"),
			warning(cathy, NAME, "second"),
			warning(alice, NAME, "second"),
			error(brenda, NAME, "brenda"),
			warning(cathy, DOB, "second")));
	}

	/**
	 * Test severity query.
	 *
	 * @see VDiagnostic#getHighestSeverity()
	 */
	@Test
	public void getHighestSeverity() {
		final List<Object> diagnostics = fixture.getDiagnostics();

		assertThat(fixture.getHighestSeverity(), is(Diagnostic.OK));

		diagnostics.add(ok(brenda, NAME, "first"));
		assertThat(fixture.getHighestSeverity(), is(Diagnostic.OK));

		diagnostics.add(warning(alice, NAME, "first"));
		assertThat(fixture.getHighestSeverity(), is(Diagnostic.WARNING));

		diagnostics.add(error(cathy, NAME, "first"));
		assertThat(fixture.getHighestSeverity(), is(Diagnostic.ERROR));

		diagnostics.remove(2);
		assertThat(fixture.getHighestSeverity(), is(Diagnostic.WARNING));
	}

	/**
	 * Test message query.
	 *
	 * @see VDiagnostic#getMessage()
	 */
	@Test
	public void getMessage() {
		final List<Object> diagnostics = fixture.getDiagnostics();

		assertThat(fixture.getMessage(), is(""));

		diagnostics.add(ok(brenda, NAME, "first"));
		assertThat(fixture.getMessage(), is(""));

		diagnostics.add(warning(alice, NAME, "second"));
		assertThat(fixture.getMessage(), is("second"));

		diagnostics.add(error(cathy, NAME, "third"));
		assertThat(fixture.getMessage(), is("third\nsecond")); // Severity order
	}

	/**
	 * Test mappings of diagnostics {@linkplain VDiagnostic#getDiagnostics(EObject) by object}.
	 *
	 * @see VDiagnostic#getDiagnostics(EObject)
	 */
	@Test
	public void getDiagnostics_EObject() {
		final List<Object> diagnostics = fixture.getDiagnostics();

		assertThat(fixture.getDiagnostics(alice), is(emptyList()));
		assertThat(fixture.getDiagnostics(league), is(emptyList()));

		// OKs are filtered out
		diagnostics.add(ok(alice, NAME, "first"));
		assertThat(fixture.getDiagnostics(alice), is(emptyList()));
		assertThat(fixture.getDiagnostics(league), is(emptyList()));

		diagnostics.add(warning(alice, DOB, "second"));
		diagnostics.add(error(alice, NAME, "third"));
		assertThat(fixture.getDiagnostics(alice), hasInOrder(
			error(alice, NAME, "third"), warning(alice, DOB, "second")));
		assertThat(fixture.getDiagnostics(league), hasInOrder(
			error(alice, NAME, "third"), warning(alice, DOB, "second")));

		diagnostics.add(warning(cathy, DOB, "third"));
		assertThat(fixture.getDiagnostics(alice), hasInOrder(
			error(alice, NAME, "third"), warning(alice, DOB, "second")));
		assertThat(fixture.getDiagnostics(alice), not(hasItem(isAbout(cathy))));
		assertThat(fixture.getDiagnostics(cathy), hasItem(warning(cathy, DOB, "third")));
		assertThat(fixture.getDiagnostics(cathy), not(hasItem(isAbout(alice))));
		assertThat(fixture.getDiagnostics(league), hasInOrder(
			// Warnings are alphabetical by message
			error(alice, NAME, "third"), warning(alice, DOB, "second"), warning(cathy, DOB, "third")));

		diagnostics.add(error(league, PLAYERS, "too few"));
		assertThat(fixture.getDiagnostics(league), hasItem(isAbout(league)));
		assertThat(fixture.getDiagnostics(alice), not(hasItem(isAbout(league))));
		assertThat(fixture.getDiagnostics(cathy), not(hasItem(isAbout(league))));

		// Now remove the error(alice, NAME, "third")
		diagnostics.remove(2);
		assertThat(fixture.getDiagnostics(alice), not(hasItem(error(alice, NAME, "third"))));
		assertThat(fixture.getDiagnostics(league), not(hasItem(error(alice, NAME, "third"))));

		// And replace warning(alice, DOB, "second")
		diagnostics.set(1, error(brenda, NAME, "new"));
		assertThat(fixture.getDiagnostics(alice), not(hasItem(warning(alice, DOB, "second"))));
		assertThat(fixture.getDiagnostics(league), both(hasItem(error(brenda, NAME, "new"))).and(
			not(hasItem(warning(alice, DOB, "second")))));
	}

	/**
	 * Test mappings of diagnostics {@linkplain VDiagnostic#getDiagnostics(EObject) by object and feature}.
	 *
	 * @see VDiagnostic#getDiagnostic(EObject, EStructuralFeature)
	 */
	@Test
	public void getDiagnostic_EObject_EStructuralFeature() {
		final List<Object> diagnostics = fixture.getDiagnostics();

		assertThat(fixture.getDiagnostic(cathy, NAME), is(emptyList()));
		assertThat(fixture.getDiagnostic(league, NAME), is(emptyList()));

		// OKs are filtered out
		diagnostics.add(ok(cathy, NAME, "first"));
		assertThat(fixture.getDiagnostic(cathy, NAME), is(emptyList()));
		assertThat(fixture.getDiagnostic(league, NAME), is(emptyList()));

		diagnostics.add(warning(cathy, NAME, "second"));
		diagnostics.add(error(cathy, DOB, "third"));
		assertThat(fixture.getDiagnostic(cathy, NAME), hasItem(
			warning(cathy, NAME, "second")));
		assertThat(fixture.getDiagnostic(cathy, NAME), not(hasItem(hasFeature(DOB))));
		assertThat(fixture.getDiagnostic(league, NAME), hasItem(
			warning(cathy, NAME, "second")));
		assertThat(fixture.getDiagnostic(league, NAME), not(hasItem(hasFeature(DOB))));

		diagnostics.add(error(cathy, NAME, "third"));
		assertThat(fixture.getDiagnostic(cathy, NAME), hasInOrder(
			error(cathy, NAME, "third"), warning(cathy, NAME, "second")));
	}

	//
	// Test framework
	//

	@Before
	public void createModel() {
		league = BowlingFactory.eINSTANCE.createLeague();
		league.setName("Bantam A");

		alice = player("Alice");
		brenda = player("Brenda");
		cathy = player("Cathy");
	}

	private Player player(String name) {
		final Player result = BowlingFactory.eINSTANCE.createPlayer();
		result.setName(name);
		result.setGender(Gender.FEMALE);
		result.setDateOfBirth(
			java.util.Date.from(LocalDate.now().minusYears(23).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		league.getPlayers().add(result);
		return result;
	}

	Diagnostic error(EObject subject, EStructuralFeature feature, String message) {
		return problem(Diagnostic.ERROR, subject, feature, message);
	}

	Diagnostic warning(EObject subject, EStructuralFeature feature, String message) {
		return problem(Diagnostic.WARNING, subject, feature, message);
	}

	Diagnostic ok(EObject subject, EStructuralFeature feature, String message) {
		return problem(Diagnostic.OK, subject, feature, message);
	}

	Diagnostic problem(int severity, EObject subject, EStructuralFeature feature, String message) {
		return new BasicDiagnostic(severity, "source", 0, message, new Object[] { subject, feature }) {
			// Implement equals() and hashcode() for convenience of testing uniqueness constraints

			@Override
			public int hashCode() {
				return Objects.hash(getSeverity(), getSource(), getMessage(), getData());
			}

			@Override
			public boolean equals(Object obj) {
				if (obj == null || obj.getClass() != getClass()) {
					return false;
				}
				final Diagnostic other = (Diagnostic) obj;
				return getSeverity() == other.getSeverity()
					&& Objects.equals(getSource(), other.getSource())
					&& Objects.equals(getMessage(), other.getMessage())
					&& Objects.equals(getData(), other.getData());
			}
		};
	}

	static Matcher<Diagnostic> isAbout(Matcher<? super EObject> subjectMatcher) {
		return new FeatureMatcher<Diagnostic, EObject>(subjectMatcher, "data[0] as EObject", "subject") {
			@Override
			protected EObject featureValueOf(Diagnostic actual) {
				final List<?> data = actual.getData();
				return data.isEmpty() || !(data.get(0) instanceof EObject)
					? null
					: (EObject) data.get(0);
			}
		};
	}

	static Matcher<Diagnostic> isAbout(EObject subject) {
		return isAbout(is(subject));
	}

	static Matcher<Diagnostic> hasFeature(Matcher<? super EStructuralFeature> featureMatcher) {
		return new FeatureMatcher<Diagnostic, EStructuralFeature>(featureMatcher, "data[1] as EStructuralFeature",
			"feature") {
			@Override
			protected EStructuralFeature featureValueOf(Diagnostic actual) {
				final List<?> data = actual.getData();
				return data.size() < 2 || !(data.get(1) instanceof EStructuralFeature)
					? null
					: (EStructuralFeature) data.get(1);
			}
		};
	}

	static Matcher<Diagnostic> hasFeature(EStructuralFeature feature) {
		return hasFeature(is(feature));
	}

	@SafeVarargs
	static <T> Matcher<Iterable<T>> hasInOrder(Matcher<? super T>... matchers) {
		return new InOrderMatcher<>(matchers);
	}

	private static final class InOrderMatcher<T> extends TypeSafeMatcher<Iterable<T>> {
		private final List<Matcher<? super T>> matchers;

		@SafeVarargs
		InOrderMatcher(Matcher<? super T>... matchers) {
			super();

			this.matchers = Arrays.asList(matchers);
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("matches in order ");
			matchers.forEach(description::appendDescriptionOf);
		}

		@Override
		protected boolean matchesSafely(Iterable<T> item) {
			boolean result = true;
			final Iterator<T> iter = item.iterator();
			final Iterator<Matcher<? super T>> mIter = matchers.iterator();
			Matcher<? super T> m = null;

			while (iter.hasNext()) {
				if (m == null) {
					if (!mIter.hasNext()) {
						break; // All matched
					}
					m = mIter.next();
				}

				final T next = iter.next();
				if (m.matches(next)) {
					// Match the next
					m = null;
				}
			}

			if (mIter.hasNext()) {
				// Not all matched
				result = false;
			}

			return result;
		}
	}

	@SuppressWarnings("unchecked")
	static <T> Matcher<Iterable<T>> hasInOrder(T... items) {
		return hasInOrder(Stream.of(items).map(CoreMatchers::is).toArray(Matcher[]::new));
	}

}
