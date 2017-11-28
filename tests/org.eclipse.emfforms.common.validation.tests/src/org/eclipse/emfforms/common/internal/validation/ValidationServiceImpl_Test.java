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
package org.eclipse.emfforms.common.internal.validation;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emfforms.common.spi.validation.exception.ValidationCanceledException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

/**
 * Test cases for the {@link ValidationServiceImpl} class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("nls")
@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceImpl_Test {
	private final ValidationServiceImpl fixture = new ValidationServiceImpl();

	/**
	 * Initializes me.
	 */
	public ValidationServiceImpl_Test() {
		super();
	}

	/**
	 * Verify that the service's {@link SubstitutionLabelProvider} is used for
	 * the construction of the "Diagnosis of ..." messages in the diagnostic chains.
	 *
	 * @see <a href="http://eclip.se/526224">bug 526224</a>
	 */
	@Test
	public void labelsInDiagnosticChains() {
		final SubstitutionLabelProvider labels = mock(SubstitutionLabelProvider.class);
		when(labels.getObjectLabel(any(EObject.class))).thenReturn("The league");

		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName("Narnian First Division");

		fixture.setSubstitutionLabelProvider(labels);
		final Diagnostic diagnostic = fixture.validate(league);
		assertThat("SubstitutionLabelProvider not used to create diagnostic chain message",
			diagnostic.getMessage(),
			containsString("The league"));
	}

	/**
	 * Verify that the service can create diagnostic chains even without
	 * a {@link SubstitutionLabelProvider}.
	 *
	 * @see <a href="http://eclip.se/526224">bug 526224</a>
	 */
	@Test
	public void diagnosticChainsNoSubstitutionLabelProvider() {
		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.setName("Narnian First Division");

		final Diagnostic diagnostic = fixture.validate(league);
		assertThat("Invalid diagnostic chain message",
			diagnostic.getMessage(),
			// The EcoreUtil::getIdentifier API was used
			both(containsString("#//")).and(containsString("LeagueImpl")));
	}

	/**
	 * Verify that the service uses a single shared validation context map when validating
	 * multiple objects.
	 *
	 * @see <a href="http://eclip.se/526224">bug 526224</a>
	 */
	@Test
	public void sharedValidationContext() throws ValidationCanceledException {
		final Set<Object> validationContexts = Collections.newSetFromMap(new IdentityHashMap<Object, Boolean>());

		// Fake up a validator that gathers validation context maps
		final EValidator validator = mock(EValidator.class);
		when(validator.validate(any(EClass.class), any(EObject.class), any(DiagnosticChain.class),
			anyMapOf(Object.class, Object.class))).then(new Answer<Boolean>() {
				@Override
				public Boolean answer(InvocationOnMock invocation) throws Throwable {
					validationContexts.add(invocation.getArguments()[3]);
					return false;
				}
			});

		// Add it to the registry
		final EValidator oldValidator = EValidator.Registry.INSTANCE.getEValidator(BowlingPackage.eINSTANCE);
		EValidator.Registry.INSTANCE.put(BowlingPackage.eINSTANCE, validator);

		try {
			final League league = BowlingFactory.eINSTANCE.createLeague();
			league.setName("Narnian First Division");

			final Player p1 = BowlingFactory.eINSTANCE.createPlayer();
			p1.setName("Mr Tumnus");
			league.getPlayers().add(p1);
			final Player p2 = BowlingFactory.eINSTANCE.createPlayer();
			p2.setName("Mr Tumnus");
			league.getPlayers().add(p2);

			// Validate the entire contents
			fixture.validate(EcoreUtil.<EObject> getAllContents(Collections.singleton(league)));
		} finally {
			// Restore the real validator
			EValidator.Registry.INSTANCE.put(BowlingPackage.eINSTANCE, oldValidator);
		}

		assumeThat("No validation occurred", validationContexts.size(), not(0));
		assertThat("Too many validation contexts", validationContexts.size(), is(1));
	}

}