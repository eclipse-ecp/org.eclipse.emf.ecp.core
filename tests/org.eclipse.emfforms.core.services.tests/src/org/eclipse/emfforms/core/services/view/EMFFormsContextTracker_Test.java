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
package org.eclipse.emfforms.core.services.view;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.common.TriConsumer;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextTracker;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

/**
 * Test cases for the {@link EMFFormsContextTracker} class.
 */
@SuppressWarnings("nls")
public class EMFFormsContextTracker_Test {

	private final Map<EMFFormsViewContext, EMFFormsContextListener> contextListeners = new HashMap<>();
	private final Map<EMFFormsViewContext, RootDomainModelChangeListener> rootDomainListeners = new HashMap<>();

	private final EMFFormsViewContext root = mockContext("root");
	private final EMFFormsContextTracker fixture = new EMFFormsContextTracker(root);

	/**
	 * Initializes me.
	 */
	public EMFFormsContextTracker_Test() {
		super();
	}

	@Test
	public void onContextDisposed_root() {
		final Consumer<EMFFormsViewContext> handler = mockConsumer();

		fixture.onContextDisposed(handler);

		dispose(root);

		verify(handler).accept(root);
	}

	@Test
	public void onContextDisposed_child() {
		final EMFFormsViewContext child = mockContext("child");
		final VElement parentView = mock(VElement.class);
		addChild(root, parentView, child);

		final Consumer<EMFFormsViewContext> handler = mockConsumer();

		fixture.onContextDisposed(handler);

		dispose(child);

		verify(handler).accept(child);
		verify(handler, never()).accept(root);
	}

	@Test
	public void isRoot() {
		final EMFFormsViewContext child = mockContext("child");
		final VElement parentView = mock(VElement.class);
		addChild(root, parentView, child);

		assertThat("Root not root", fixture.isRoot(root), is(true));
		assertThat("Child is root", fixture.isRoot(child), is(false));
	}

	@Test
	public void onChildContextAdded() {
		final EMFFormsViewContext child = mockContext("child");
		final VElement parentView = mock(VElement.class, "parentView");

		final TriConsumer<EMFFormsViewContext, VElement, EMFFormsViewContext> handler = mockTriConsumer();

		fixture.onChildContextAdded(handler);

		addChild(root, parentView, child);

		verify(handler).accept(root, parentView, child);
	}

	@Test
	public void onContextInitialized_child() {
		final EMFFormsViewContext child = mockContext("child");
		final VElement parentView = mock(VElement.class, "parentView");

		final Consumer<EMFFormsViewContext> handler = mockConsumer();

		fixture.onContextInitialized(handler);

		addChild(root, parentView, child);

		verify(handler).accept(child);
		verify(handler, never()).accept(root);
	}

	@Test
	public void onChildContextRemoved() {
		final EMFFormsViewContext child = mockContext("child");
		final VElement parentView = mock(VElement.class, "parentView");
		addChild(root, parentView, child);

		final TriConsumer<EMFFormsViewContext, VElement, EMFFormsViewContext> handler = mockTriConsumer();

		fixture.onChildContextRemoved(handler);

		removeChild(root, child);

		verify(handler).accept(root, parentView, child);
	}

	@Test
	public void onDomainModelChanged_root() {
		final Consumer<EMFFormsViewContext> handler = mockConsumer();

		fixture.onDomainModelChanged(handler);

		changeDomainModel(root);

		verify(handler).accept(root);
	}

	@Test
	public void onDomainModelChanged_child() {
		final EMFFormsViewContext child = mockContext("child");
		final VElement parentView = mock(VElement.class, "parentView");
		addChild(root, parentView, child);

		final Consumer<EMFFormsViewContext> handler = mockConsumer();

		fixture.onDomainModelChanged(handler);

		changeDomainModel(child);

		verify(handler).accept(child);
		verify(handler, never()).accept(root);
	}

	//
	// Test framework
	//

	@Before
	public void open() {
		fixture.open();
	}

	@After
	public void close() {
		fixture.close();

		assertThat("Listeners not removed", contextListeners.keySet(), not(hasItem(anything())));
		assertThat("Listeners not removed", rootDomainListeners.keySet(), not(hasItem(anything())));
	}

	private EMFFormsViewContext mockContext(String name) {
		final EMFFormsViewContext result = mock(EMFFormsViewContext.class, name);

		doAnswer(interceptListenerRegistration(EMFFormsContextListener.class, contextListeners))
			.when(result).registerEMFFormsContextListener(any());
		doAnswer(interceptListenerRegistration(RootDomainModelChangeListener.class, rootDomainListeners))
			.when(result).registerRootDomainModelChangeListener(any());

		doAnswer(interceptListenerUnregistration(EMFFormsContextListener.class, contextListeners))
			.when(result).unregisterEMFFormsContextListener(any());
		doAnswer(interceptListenerUnregistration(RootDomainModelChangeListener.class, rootDomainListeners))
			.when(result).unregisterRootDomainModelChangeListener(any());

		return result;
	}

	private <T> Answer<T> interceptListenerRegistration(Class<T> type,
		Map<? super EMFFormsViewContext, ? super T> map) {
		return invocation -> {
			final T listener = type.cast(invocation.getArguments()[0]);
			map.put((EMFFormsViewContext) invocation.getMock(), listener);
			return null;
		};
	}

	private <T> Answer<T> interceptListenerUnregistration(Class<T> type,
		Map<? super EMFFormsViewContext, ? super T> map) {
		return invocation -> {
			final T listener = type.cast(invocation.getArguments()[0]);
			map.remove(invocation.getMock(), listener);
			return null;
		};
	}

	@SuppressWarnings("unchecked")
	<T> Consumer<T> mockConsumer() {
		return mock(Consumer.class);
	}

	@SuppressWarnings("unchecked")
	<T, U, V> TriConsumer<T, U, V> mockTriConsumer() {
		return mock(TriConsumer.class);
	}

	private EMFFormsContextListener requireContextListener(EMFFormsViewContext context) {
		final EMFFormsContextListener result = contextListeners.get(context);
		assertThat(String.format("No context listener for %s", context), result, notNullValue());
		return result;
	}

	private RootDomainModelChangeListener requireRootDomainListener(EMFFormsViewContext context) {
		final RootDomainModelChangeListener result = rootDomainListeners.get(context);
		assertThat(String.format("No root domain model listener for %s", context), result, notNullValue());
		return result;
	}

	void dispose(EMFFormsViewContext context) {
		requireContextListener(context).contextDispose();
	}

	void initialize(EMFFormsViewContext context) {
		requireContextListener(context).contextInitialised();
	}

	void addChild(EMFFormsViewContext parent, VElement parentView, EMFFormsViewContext child) {
		requireContextListener(parent).childContextAdded(parentView, child);
		initialize(child);
	}

	void removeChild(EMFFormsViewContext parent, EMFFormsViewContext child) {
		dispose(child);
		requireContextListener(parent).childContextDisposed(child);
	}

	void changeDomainModel(EMFFormsViewContext context) {
		requireRootDomainListener(context).notifyChange();
	}

}
