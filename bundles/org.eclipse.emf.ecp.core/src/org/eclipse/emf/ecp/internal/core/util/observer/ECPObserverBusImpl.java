/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * wesendon - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.internal.core.util.observer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecp.core.util.observer.ECPObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPObserverBus;

/**
 * This is a universal observer bus. This class follows the publish/subscribe pattern, it is a central dispatcher for
 * observers and makes use of generics in order to allow type safety. It can be used as singleton or be injected through
 * DI.
 * Observers have to implement the {@link ECPObserver} interface, which is only used as a marker. Future use of
 * Annotations is possible.
 * by using {@link #notify(Class)} (e.g. <code>bus.notify(MyObserver.class).myObserverMethod()</code>) all registered
 * Observers are notified.
 * This is implemented by using the java {@link Proxy} class. By calling {@link #notify(Class)} a proxy is returned,
 * which then calls all registered observers.
 * The proxy can also be casted into {@link ECPObserverCall}, which allows to access all results by the different
 * observers.
 *
 *
 * Example code:
 *
 * <pre>
 * // A is ESObserver
 * A a = new A() {
 *
 * 	public void foo() {
 * 		System.out.println(&quot;A says: go!&quot;);
 * 	}
 * };
 *
 * // B extends A and is IObserver
 * B b = new B() {
 *
 * 	public void say(String ja) {
 * 		System.out.println(&quot;B says: &quot; + ja);
 * 	}
 *
 * 	public void foo() {
 * 		System.out.println(&quot;B says: h??&quot;);
 * 	}
 * };
 *
 * // B is registered first
 * ObserverBus.register(b);
 * ObserverBus.register(a);
 *
 * ObserverBus.notify(A.class).foo();
 *
 * ObserverBus.notify(B.class).say(&quot;w00t&quot;);
 *
 * // Output:
 *
 * // B says: h??
 * // A says: go!
 * //
 * // B says: w00t
 *
 * </pre>
 *
 * @author wesendon
 */
public class ECPObserverBusImpl implements ECPObserverBus {

	private final HashMap<Class<? extends ECPObserver>, List<ECPObserver>> observerMap;

	/**
	 * Default constructor.
	 */
	public ECPObserverBusImpl() {
		observerMap = new HashMap<Class<? extends ECPObserver>, List<ECPObserver>>();
	}

	/**
	 * This method allows you to notify all observers.
	 *
	 * @param <T> class of observer
	 * @param clazz class of observer
	 * @return call object
	 */
	@Override
	public <T extends ECPObserver> T notify(Class<T> clazz) {
		return notify(clazz, false);
	}

	/**
	 * This method allows you to notify all observers.
	 *
	 * @param <T> class of observer
	 * @param clazz class of observer
	 * @param prioritized sort observer after {@link ECPPrioritizedIObserver}
	 *
	 * @return call object
	 */
	public <T extends ECPObserver> T notify(Class<T> clazz, boolean prioritized) {
		if (clazz == null) {
			return null;
		}
		return createProxy(clazz, false);
	}

	/**
	 * Registers an observer for all observer interfaces implemented by the object or its super classes.
	 *
	 * @param observer observer object
	 */
	@Override
	public void register(ECPObserver observer) {
		register(observer, getObserverInterfaces(observer));
	}

	/**
	 * Registers an observer for the specified observer interfaces.
	 *
	 * @param observer observer object
	 * @param classes set of classes
	 */
	public void register(ECPObserver observer, Class<? extends ECPObserver>... classes) {
		for (final Class<? extends ECPObserver> iface : classes) {
			if (iface.isInstance(observer)) {
				addObserver(observer, iface);
			}
		}
	}

	/**
	 * Unregisters an observer for all observer interfaces implemented by the object or its super classes.
	 *
	 * @param observer observer object
	 */
	@Override
	public void unregister(ECPObserver observer) {
		unregister(observer, getObserverInterfaces(observer));
	}

	/**
	 * Unregisters an observer for the specified observer interfaces.
	 *
	 * @param observer observer object
	 * @param classes set of classes
	 */
	public void unregister(ECPObserver observer, Class<? extends ECPObserver>... classes) {
		for (final Class<? extends ECPObserver> iface : classes) {
			if (iface.isInstance(observer)) {
				removeObserver(observer, iface);
			}
		}
	}

	private void addObserver(ECPObserver observer, Class<? extends ECPObserver> iface) {
		final List<ECPObserver> observers = initObserverList(iface);
		observers.add(observer);
	}

	private void removeObserver(ECPObserver observer, Class<? extends ECPObserver> iface) {
		final List<ECPObserver> observers = initObserverList(iface);
		observers.remove(observer);
	}

	private List<ECPObserver> initObserverList(Class<? extends ECPObserver> iface) {
		List<ECPObserver> list = observerMap.get(iface);
		if (list == null) {
			list = new ArrayList<ECPObserver>();
			observerMap.put(iface, list);
		}
		return list;
	}

	private List<ECPObserver> getObserverByClass(Class<? extends ECPObserver> clazz) {
		List<ECPObserver> list = observerMap.get(clazz);
		if (list == null) {
			list = Collections.emptyList();
		}
		return new ArrayList<ECPObserver>(list);
	}

	private boolean isPrioritizedObserver(Class<?> clazz, Method method) {
		// Only prioritize if requested class extends PrioritizedIObserver and method is part of this class and not part
		// of some super class
		if (!clazz.equals(method.getDeclaringClass())) {
			return false;
		}
		for (final Class<?> interfaceClass : clazz.getInterfaces()) {
			if (ECPPrioritizedIObserver.class.equals(interfaceClass)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private <T extends ECPObserver> T createProxy(Class<T> clazz, boolean prioritized) {
		final ProxyHandler handler = new ProxyHandler((Class<ECPObserver>) clazz, prioritized);
		return (T) Proxy
			.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz, ECPObserverCall.class }, handler);

	}

	/**
	 * Proxyobserver which notifies all observers.
	 *
	 * @author wesendon
	 */
	private final class ProxyHandler implements InvocationHandler, ECPObserverCall {

		private final Class<ECPObserver> clazz;
		private List<ECPObserverCall.Result> lastResults;
		private final boolean prioritized;

		ProxyHandler(Class<ECPObserver> clazz, boolean prioritized) {
			this.clazz = clazz;
			this.prioritized = prioritized;
			lastResults = new ArrayList<ECPObserverCall.Result>();
		}

		// BEGIN SUPRESS CATCH EXCEPTION
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// END SUPRESS CATCH EXCEPTION
			// fork for calls to ObserverCall.class
			if (ECPObserverCall.class.equals(method.getDeclaringClass())) {
				return accessObserverCall(method, args);
			}

			final List<ECPObserver> observers = getObserverByClass(clazz);

			if (prioritized && isPrioritizedObserver(clazz, method)) {
				sortObservers(observers);
			}

			// return default value if no observers are registered
			if (observers.size() == 0) {
				lastResults = new ArrayList<ECPObserverCall.Result>();
				return Result.getDefaultValue(method);
			}

			lastResults = notifiyObservers(observers, method, args);
			final List<Object> result = new ArrayList<Object>();
			for (final Result resultObject : lastResults) {
				final Object res = resultObject.getResultOrDefaultValue();
				if (res instanceof Object[]) {
					final Object[] arrayRes = (Object[]) res;
					result.addAll(Arrays.asList(arrayRes));
				} else if (res instanceof Collection) {
					result.addAll((Collection) res);
				} else {
					result.add(res);
				}

			}
			return result;
		}

		private Object accessObserverCall(Method method, Object[] args) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
			return method.invoke(this, args);

		}

		private List<ECPObserverCall.Result> notifiyObservers(List<ECPObserver> observers, Method method,
			Object[] args) {
			final List<ECPObserverCall.Result> results = new ArrayList<ECPObserverCall.Result>(observers.size());
			for (final ECPObserver observer : observers) {
				try {
					results.add(new Result(observer, method, method.invoke(observer, args)));
					// BEGIN SUPRESS CATCH EXCEPTION
				} catch (final Throwable e) {
					// END SUPRESS CATCH EXCEPTION
					results.add(new Result(observer, e, method));
				}
			}
			return results;
		}

		@Override
		public List<Result> getObserverCallResults() {
			return lastResults;
		}

		// END SUPRESS CATCH EXCEPTION
	}

	/**
	 * Sorts Observers. Make sure they are {@link ECPPrioritizedIObserver}!!
	 *
	 * @param observers list of observers
	 */
	private void sortObservers(List<ECPObserver> observers) {
		Collections.sort(observers, new Comparator<ECPObserver>() {
			@Override
			public int compare(ECPObserver o1, ECPObserver o2) {
				final int prio1 = ((ECPPrioritizedIObserver) o1).getPriority();
				final int prio2 = ((ECPPrioritizedIObserver) o2).getPriority();
				if (prio1 == prio2) {
					return 0;
				}
				return prio1 > prio2 ? 1 : -1;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private Class<? extends ECPObserver>[] getObserverInterfaces(ECPObserver observer) {
		final HashSet<Class<? extends ECPObserver>> observerInterfacsFound = new HashSet<Class<? extends ECPObserver>>();
		getClasses(observer.getClass(), observerInterfacsFound);
		return observerInterfacsFound.toArray(new Class[observerInterfacsFound.size()]);
	}

	@SuppressWarnings("unchecked")
	private boolean getClasses(Class<?> clazz, HashSet<Class<? extends ECPObserver>> result) {
		for (final Class<?> iface : getAllInterfaces(clazz, new HashSet<Class<?>>())) {
			if (iface.equals(ECPObserver.class) && clazz.isInterface()) {
				result.add((Class<? extends ECPObserver>) clazz);
				return true;
			}
			if (getClasses(iface, result) && clazz.isInterface()) {
				result.add((Class<? extends ECPObserver>) clazz);
			}

		}
		return false;
	}

	private Set<Class<?>> getAllInterfaces(final Class<?> clazz, final Set<Class<?>> interfacesFound) {

		for (final Class<?> iface : clazz.getInterfaces()) {
			interfacesFound.add(iface);
		}

		if (clazz.getSuperclass() == null) {
			return interfacesFound;
		}

		return getAllInterfaces(clazz.getSuperclass(), interfacesFound);
	}

}
