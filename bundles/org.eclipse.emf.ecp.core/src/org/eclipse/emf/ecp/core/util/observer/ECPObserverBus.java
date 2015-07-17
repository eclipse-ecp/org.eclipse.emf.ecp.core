/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.core.util.observer;

/**
 * Bus for application wide notifications on events of various types. Check the type hierarchy of {@link ECPObserver} to
 * discover possible types.<br/>
 * This is a universal observer bus. This class follows the publish/subscribe pattern, it is a central dispatcher for
 * observers and makes use of generics in order to allow type safety. It can be used as singleton or be injected through
 * DI.
 * Observers have to implement the {@link ECPObserver} interface, which is only used as a marker. Future use of
 * Annotations is possible.
 * by using {@link #notify(Class)} (e.g. <code>bus.notify(MyObserver.class).myObserverMethod()</code>) all registered
 * Observers are notified.
 * This is implemented by using the java {@link java.lang.reflect.Proxy Proxy} class. By calling {@link #notify(Class)}
 * a proxy is returned,
 * which then calls all registered observers.
 * The proxy can also be casted into {@link org.eclipse.emf.ecp.internal.core.util.observer.ECPObserverCall
 * ECPObserverCall}, which allows to access all results by the different
 * observers.
 *
 * <br/>
 * Example code:
 *
 * <pre>
 * // A is ECPObserver
 * A a = new A() {
 *
 * 	public void foo() {
 * 		System.out.println(&quot;A says: go!&quot;);
 * 	}
 * };
 *
 * // B extends A and is ECPObserver
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
 * ECPObserverBus.register(b);
 * ECPObserverBus.register(a);
 *
 * ECPObserverBus.notify(A.class).foo();
 *
 * ECPObserverBus.notify(B.class).say(&quot;w00t&quot;);
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
 * @author Eugen Neufeld
 *
 */
public interface ECPObserverBus {
	/**
	 * Registers an observer for all observer interfaces implemented by the object or its super classes.
	 *
	 * @param observer observer object
	 */
	void register(ECPObserver observer);

	/**
	 * Unregisters an observer for all observer interfaces implemented by the object or its super classes.
	 *
	 * @param observer observer object
	 */
	void unregister(ECPObserver observer);

	/**
	 * This method allows you to notify all observers of type <T>.
	 * It returns a proxy object of type T which allows you to call the specific methods of your observer. Calling any
	 * method will be delegated to all registered observers of the given type <T>.
	 *
	 * @param <T> type of observer
	 * @param clazz class of observer
	 * @return call object of type T
	 */
	<T extends ECPObserver> T notify(Class<T> clazz);
}
