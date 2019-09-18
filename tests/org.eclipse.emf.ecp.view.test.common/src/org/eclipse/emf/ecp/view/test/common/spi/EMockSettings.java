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
package org.eclipse.emf.ecp.view.test.common.spi;

import java.io.Serializable;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.mockito.MockSettings;
import org.mockito.listeners.InvocationListener;
import org.mockito.stubbing.Answer;

/**
 * Analogue of the {@link MockSettings} interface that configures a mock of {@link EObject} type.
 * It ensures that all of the interfaces expected by the EMF run-time are
 * provided, so therefore it does not have the {@link MockSettings#extraInterfaces(Class...)}
 * settings as it would be unusual to mix in any other interfaces to an EMF model object.
 * Also, {@link EObject}s are never {@link Serializable}, so that option is not supported.
 *
 * @since 1.22
 * @see EMFMocking#eMock(Class, EMockSettings)
 * @see EMFMocking#withESettings()
 */
public interface EMockSettings {
	/**
	 * Set a name for the mock. <strong>Note</strong> that mocks injected into fields with
	 * the {@link EMock @EMock} annotation are automatically named according to the field name,
	 * or else a name specified in the annotation.
	 *
	 * @param name the mock name
	 * @return myself, for fluency
	 *
	 * @see MockSettings#name(String)
	 */
	EMockSettings name(String name);

	/**
	 * Configure a default answer for unstubbed methods.
	 *
	 * @param defaultAnswer the mock's default answer
	 * @return myself, for fluency
	 *
	 * @see MockSettings#defaultAnswer(Answer)
	 */
	EMockSettings defaultAnswer(Answer<?> defaultAnswer);

	/**
	 * Enable verbose logging of interactions.
	 *
	 * @return myself, for fluency
	 *
	 * @see MockSettings#verboseLogging()
	 */
	EMockSettings verboseLogging();

	/**
	 * Add listeners listener to method invocations on the mock.
	 *
	 * @param listeners the listeners to add (none may be {@code null})
	 * @return myself, for fluency
	 *
	 * @see MockSettings#invocationListeners(InvocationListener...)
	 */
	EMockSettings invocationListeners(InvocationListener... listeners);

	/**
	 * Set a contents list for the mock. This may be a writable list if the
	 * test needs that.
	 *
	 * @param eContents a list to return from {@link EObject#eContents()}
	 * @return myself, for fluency
	 */
	default EMockSettings eContents() {
		return eContents(ECollections.emptyEList());
	}

	/**
	 * Set an {@link EClass} list for the mock.
	 *
	 * @param eClass the {@link EClass} to return from {@link EObject#eClass()}
	 * @return myself, for fluency
	 */
	EMockSettings eClass(EClass eClass);

	/**
	 * Set a contents list for the mock. This may be a writable list if the
	 * test needs that.
	 *
	 * @param eContents a list to return from {@link EObject#eContents()}
	 * @return myself, for fluency
	 */
	EMockSettings eContents(EList<? extends EObject> eContents);

	/**
	 * Set a container for the mock.
	 *
	 * @param eContainer an object to return from {@link EObject#eContainer()}
	 * @return myself, for fluency
	 */
	EMockSettings eContainer(EObject eContainer);

}
