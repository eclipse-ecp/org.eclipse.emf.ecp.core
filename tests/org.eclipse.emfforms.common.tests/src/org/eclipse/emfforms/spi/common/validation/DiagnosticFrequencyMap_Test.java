/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.common.validation;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test cases for the {@link DiagnosticFrequencyMap} type.
 */
@RunWith(Enclosed.class)
@SuppressWarnings("nls")
public class DiagnosticFrequencyMap_Test {

	private final DiagnosticFrequencyMap fixture;

	/**
	 * Initializes me with my fixture.
	 *
	 * @param fixture my fixture
	 */
	DiagnosticFrequencyMap_Test(DiagnosticFrequencyMap fixture) {
		super();

		this.fixture = fixture;
	}

	@RunWith(JUnit4.class)
	public static class Limited extends DiagnosticFrequencyMap_Test {

		/**
		 * Initializes me.
		 */
		public Limited() {
			super(DiagnosticFrequencyMap.limitedTo(5));
		}

		@Test
		public void isEmpty() {
			assertThat("should be empty", fixture().isEmpty(), is(true));
			charStream("abcdefghijklmnop").map(this::error).forEach(fixture()::add);
			assertThat("should not be empty", fixture().isEmpty(), is(false));
		}

		@Test
		public void isFull() {
			charStream("abcd").map(this::error).forEach(fixture()::add);
			assertThat("limited map should not be full", fixture().isFull(), is(false));
			charStream("efghijklmnop").map(this::error).forEach(fixture()::add);
			assertThat("limited map should be full", fixture().isFull(), is(true));
		}

		@Test
		public void size() {
			assertThat(fixture().size(), is(0));
			charStream("abcd").map(this::warning).forEach(fixture()::add);
			assertThat(fixture().size(), is(4));
			charStream("efghijklmnop").map(this::error).forEach(fixture()::add);
			assertThat(fixture().size(), is(5)); // 5 is the limit
		}

		@Test
		public void getDiscardedSeverity() {
			// Initial conditions
			assertThat(fixture().getDiscardedSeverity(), is(Diagnostic.OK));

			charStream("abcde").map(this::info).forEach(fixture()::add);
			assertThat("nothing should be discarded, yet", fixture().getDiscardedSeverity(), is(Diagnostic.OK));

			charStream("fghij").map(this::warning).forEach(fixture()::add);
			assertThat("infos should be discarded", fixture().getDiscardedSeverity(), is(Diagnostic.INFO));

			charStream("klmno").map(this::error).forEach(fixture()::add);
			assertThat("warnings should be discarded", fixture().getDiscardedSeverity(), is(Diagnostic.WARNING));

			charStream("p").map(this::error).forEach(fixture()::add);
			assertThat("an error should have been discarded", fixture().getDiscardedSeverity(), is(Diagnostic.ERROR));
		}

		@Test
		public void iterator() {
			// Initial conditions
			List<Diagnostic> diagnostics = iterate(fixture());
			assertThat(diagnostics, not(hasItem(anything())));

			fixture().addAll(charStream("abcde").map(this::info).collect(toList()));
			diagnostics = iterate(fixture());
			assertThat(diagnostics, everyItem(matches(Diagnostic.INFO, substringOf("abcde"))));

			fixture().addAll(charStream("fghij").map(this::warning).collect(toList()));
			diagnostics = iterate(fixture());
			assertThat(diagnostics, everyItem(matches(Diagnostic.WARNING, substringOf("fghij"))));

			fixture().addAll(charStream("klmno").map(this::error).collect(toList()));
			diagnostics = iterate(fixture());
			assertThat(diagnostics, everyItem(matches(Diagnostic.ERROR, substringOf("klmno"))));

			fixture().add(error("p"));
			diagnostics = iterate(fixture());
			assertThat("should have discarded new error", diagnostics,
				everyItem(matches(Diagnostic.ERROR, substringOf("klmno"))));
		}

		@Test
		public void clear() {
			charStream("abc").map(this::info).forEach(fixture()::add);
			charStream("def").map(this::warning).forEach(fixture()::add);
			charStream("ghi").map(this::error).forEach(fixture()::add);

			fixture().clear();
			assertThat("not empty", fixture().isEmpty(), is(true));
			assertThat("somehow full", fixture().isFull(), is(false));
			assertThat("has size", fixture().size(), is(0));
			assertThat("something discarded from nothing", fixture().getDiscardedSeverity(), is(Diagnostic.OK));
		}

		@Test
		public void appendTo() {
			// Initial conditions
			final List<Diagnostic> diagnostics = new ArrayList<>();
			fixture().appendTo(diagnostics);
			assertThat(diagnostics, not(hasItem(anything())));

			fixture().addAll(charStream("abcde").map(this::info).collect(toList()));
			diagnostics.clear();
			fixture().appendTo(diagnostics);
			assertThat(diagnostics, everyItem(matches(Diagnostic.INFO, substringOf("abcde"))));

			fixture().addAll(charStream("fghij").map(this::warning).collect(toList()));
			diagnostics.clear();
			fixture().appendTo(diagnostics);
			assertThat(diagnostics, everyItem(matches(Diagnostic.WARNING, substringOf("fghij"))));

			fixture().addAll(charStream("klmno").map(this::error).collect(toList()));
			diagnostics.clear();
			fixture().appendTo(diagnostics);
			assertThat(diagnostics, everyItem(matches(Diagnostic.ERROR, substringOf("klmno"))));

			fixture().add(error("p"));
			diagnostics.clear();
			fixture().appendTo(diagnostics);
			assertThat("should have discarded new error", diagnostics,
				everyItem(matches(Diagnostic.ERROR, substringOf("klmno"))));
		}

		@SuppressWarnings("unchecked")
		@Test
		public void addDiagnosticFilter() {
			final Set<String> vowels = charStream("aeiou").collect(toSet());
			final Predicate<Diagnostic> isVowel = d -> vowels.contains(d.getMessage());
			final Predicate<Diagnostic> isProblem = d -> d.getSeverity() > Diagnostic.INFO;

			fixture().addDiagnosticFilter(isVowel);
			fixture().addDiagnosticFilter(isProblem);

			// initial conditions
			assumeThat(fixture().size(), is(0));
			assumeThat(fixture().getDiscardedSeverity(), is(Diagnostic.OK));

			charStream("abcd").map(this::info).forEach(fixture()::add);
			assertThat(fixture().size(), is(0));
			assertThat(fixture().getDiscardedSeverity(), is(Diagnostic.INFO));
			charStream("efghijklmnop").map(this::warning).forEach(fixture()::add);
			assertThat(fixture().size(), is(3));
			assertThat(fixture().getDiscardedSeverity(), is(Diagnostic.WARNING));
			charStream("qrstuvwxyz").map(this::error).forEach(fixture()::add);
			assertThat(fixture().size(), is(4));
			assertThat(fixture().getDiscardedSeverity(), is(Diagnostic.ERROR));

			final List<Diagnostic> collected = iterate(fixture());
			assertThat(collected, hasItems(
				matches(Diagnostic.WARNING, "e"),
				matches(Diagnostic.WARNING, "i"),
				matches(Diagnostic.WARNING, "o"),
				matches(Diagnostic.ERROR, "u")));
		}

	}

	@RunWith(JUnit4.class)
	public static class Unlimited extends DiagnosticFrequencyMap_Test {

		/**
		 * Initializes me.
		 */
		public Unlimited() {
			super(DiagnosticFrequencyMap.unlimited());
		}

		@Test
		public void isEmpty() {
			assertThat("should be empty", fixture().isEmpty(), is(true));
			charStream("abcdefghijklmnop").map(this::error).forEach(fixture()::add);
			assertThat("should not be empty", fixture().isEmpty(), is(false));
		}

		@Test
		public void isFull() {
			charStream("abcdefghijklmnop").map(this::error).forEach(fixture()::add);
			assertThat("unlimited map should never be full", fixture().isFull(), is(false));
		}

		@Test
		public void size() {
			assertThat(fixture().size(), is(0));
			charStream("abcdefghijklmnop").map(this::error).forEach(fixture()::add);
			assertThat(fixture().size(), is(16));
		}

		@Test
		public void getDiscardedSeverity() {
			// Initial conditions
			assertThat(fixture().getDiscardedSeverity(), is(Diagnostic.OK));

			charStream("abcde").map(this::info).forEach(fixture()::add);
			assertThat("nothing should ever be discarded", fixture().getDiscardedSeverity(), is(Diagnostic.OK));

			charStream("fghij").map(this::warning).forEach(fixture()::add);
			assertThat("nothing should ever be discarded", fixture().getDiscardedSeverity(), is(Diagnostic.OK));

			charStream("klmno").map(this::error).forEach(fixture()::add);
			assertThat("nothing should ever be discarded", fixture().getDiscardedSeverity(), is(Diagnostic.OK));

			charStream("p").map(this::error).forEach(fixture()::add);
			assertThat("nothing should ever be discarded", fixture().getDiscardedSeverity(), is(Diagnostic.OK));
		}

		@Test
		public void appendTo() {
			// Initial conditions
			final List<Diagnostic> diagnostics = new ArrayList<>();
			fixture().appendTo(diagnostics);
			assertThat(diagnostics, not(hasItem(anything())));

			fixture().addAll(charStream("abcde").map(this::info).collect(toList()));
			diagnostics.clear();
			fixture().appendTo(diagnostics);
			assertThat(diagnostics, everyItem(matches(Diagnostic.INFO, substringOf("abcde"))));

			fixture().addAll(charStream("fghij").map(this::warning).collect(toList()));
			diagnostics.clear();
			fixture().appendTo(diagnostics);
			assertThat(diagnostics.subList(0, 5), everyItem(matches(Diagnostic.WARNING, substringOf("fghij"))));
			assertThat(diagnostics.subList(5, 10), everyItem(matches(Diagnostic.INFO, substringOf("abcde"))));

			fixture().addAll(charStream("klmno").map(this::error).collect(toList()));
			diagnostics.clear();
			fixture().appendTo(diagnostics);
			assertThat(diagnostics.subList(0, 5), everyItem(matches(Diagnostic.ERROR, substringOf("klmno"))));
			assertThat(diagnostics.subList(5, 10), everyItem(matches(Diagnostic.WARNING, substringOf("fghij"))));
			assertThat(diagnostics.subList(10, 15), everyItem(matches(Diagnostic.INFO, substringOf("abcde"))));

			fixture().add(error("p"));
			diagnostics.clear();
			fixture().appendTo(diagnostics);
			assertThat("Sixth diagnostic should be the error added to the earlier five",
				diagnostics.get(5), matches(Diagnostic.ERROR, "p"));
		}

		@Test
		public void iterator() {
			// Initial conditions
			List<Diagnostic> diagnostics = iterate(fixture());
			assertThat(diagnostics, not(hasItem(anything())));

			fixture().addAll(charStream("abcde").map(this::info).collect(toList()));
			diagnostics = iterate(fixture());
			assertThat(diagnostics, everyItem(matches(Diagnostic.INFO, substringOf("abcde"))));

			fixture().addAll(charStream("fghij").map(this::warning).collect(toList()));
			diagnostics = iterate(fixture());
			assertThat(diagnostics.subList(0, 5), everyItem(matches(Diagnostic.WARNING, substringOf("fghij"))));
			assertThat(diagnostics.subList(5, 10), everyItem(matches(Diagnostic.INFO, substringOf("abcde"))));

			fixture().addAll(charStream("klmno").map(this::error).collect(toList()));
			diagnostics = iterate(fixture());
			assertThat(diagnostics.subList(0, 5), everyItem(matches(Diagnostic.ERROR, substringOf("klmno"))));
			assertThat(diagnostics.subList(5, 10), everyItem(matches(Diagnostic.WARNING, substringOf("fghij"))));
			assertThat(diagnostics.subList(10, 15), everyItem(matches(Diagnostic.INFO, substringOf("abcde"))));

			fixture().add(error("p"));
			diagnostics = iterate(fixture());
			assertThat("Sixth diagnostic should be the error added to the earlier five",
				diagnostics.get(5), matches(Diagnostic.ERROR, "p"));
		}

		@Test
		public void clear() {
			charStream("abc").map(this::info).forEach(fixture()::add);
			charStream("def").map(this::warning).forEach(fixture()::add);
			charStream("ghi").map(this::error).forEach(fixture()::add);

			fixture().clear();
			assertThat("not empty", fixture().isEmpty(), is(true));
			assertThat("somehow full", fixture().isFull(), is(false));
			assertThat("has size", fixture().size(), is(0));
			assertThat("something discarded from nothing", fixture().getDiscardedSeverity(), is(Diagnostic.OK));
		}

		@SuppressWarnings("unchecked")
		@Test
		public void addDiagnosticFilter() {
			final Set<String> vowels = charStream("aeiou").collect(toSet());
			final Predicate<Diagnostic> isVowel = d -> vowels.contains(d.getMessage());
			final Predicate<Diagnostic> isProblem = d -> d.getSeverity() > Diagnostic.INFO;

			fixture().addDiagnosticFilter(isVowel);
			fixture().addDiagnosticFilter(isProblem);

			// initial conditions
			assumeThat(fixture().size(), is(0));
			assumeThat(fixture().getDiscardedSeverity(), is(Diagnostic.OK));

			charStream("abcd").map(this::info).forEach(fixture()::add);
			assertThat(fixture().size(), is(0));
			assertThat(fixture().getDiscardedSeverity(), is(Diagnostic.INFO));
			charStream("efghijklmnop").map(this::warning).forEach(fixture()::add);
			assertThat(fixture().size(), is(3));
			assertThat(fixture().getDiscardedSeverity(), is(Diagnostic.WARNING));
			charStream("qrstuvwxyz").map(this::error).forEach(fixture()::add);
			assertThat(fixture().size(), is(4));
			assertThat(fixture().getDiscardedSeverity(), is(Diagnostic.ERROR));

			final List<Diagnostic> collected = iterate(fixture());
			assertThat(collected, hasItems(
				matches(Diagnostic.WARNING, "e"),
				matches(Diagnostic.WARNING, "i"),
				matches(Diagnostic.WARNING, "o"),
				matches(Diagnostic.ERROR, "u")));
		}

	}

	//
	// Common test framework
	//

	final DiagnosticFrequencyMap fixture() {
		return fixture;
	}

	/**
	 * Iterate a diagnostic {@code freq}uency map to collect its diagnostics.
	 *
	 * @param freq the map to {@linkplain Iterable#iterator() iterate}
	 * @return the diagnostics gathered from the iterator
	 */
	final List<Diagnostic> iterate(DiagnosticFrequencyMap freq) {
		final List<Diagnostic> result = new ArrayList<>(freq.size());
		for (final Diagnostic next : freq) {
			result.add(next);
		}
		return result;
	}

	final Stream<String> charStream(String chars) {
		return chars.chars().mapToObj(Character::toChars).map(String::new);
	}

	final Diagnostic diagnostic(int severity, String message) {
		return new BasicDiagnostic(severity, "org.eclipse.emfforms.common.tests", 0, message, null);
	}

	final Diagnostic error(String message) {
		return diagnostic(Diagnostic.ERROR, message);
	}

	final Diagnostic warning(String message) {
		return diagnostic(Diagnostic.WARNING, message);
	}

	final Diagnostic info(String message) {
		return diagnostic(Diagnostic.INFO, message);
	}

	final Matcher<Diagnostic> matches(int severityMask, Matcher<String> message) {
		return new TypeSafeDiagnosingMatcher<Diagnostic>() {
			@Override
			protected boolean matchesSafely(Diagnostic item, Description mismatchDescription) {
				if ((severityMask & item.getSeverity()) != item.getSeverity()) {
					mismatchDescription.appendText(String.format("severity does not match %x mask", severityMask)); //$NON-NLS-1$
					return false;
				}

				if (!message.matches(item.getMessage())) {
					message.describeMismatch(item.getMessage(), mismatchDescription);
					return false;
				}

				return true;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(
					String.format("diagnostic of severity matching %x mask with message that ", severityMask)); //$NON-NLS-1$
				description.appendDescriptionOf(message);
			}
		};
	}

	final Matcher<Diagnostic> matches(int severityMask, String message) {
		return matches(severityMask, is(message));
	}

	/**
	 * Obtain a matcher that matches strings that are sub-strings of some target string.
	 *
	 * @param s the target string
	 * @return a matcher of strings that are substrings of {@code s}
	 */
	final Matcher<String> substringOf(String s) {
		return new TypeSafeDiagnosingMatcher<String>() {
			@Override
			protected boolean matchesSafely(String item, Description mismatchDescription) {
				final boolean result = s.contains(item);

				if (!result) {
					mismatchDescription.appendText(String.format("\"%s\" is not contained in \"%s\"", item, s)); //$NON-NLS-1$
				}

				return result;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(String.format("string contained in \"%s\"", s)); //$NON-NLS-1$
			}
		};
	}
}
