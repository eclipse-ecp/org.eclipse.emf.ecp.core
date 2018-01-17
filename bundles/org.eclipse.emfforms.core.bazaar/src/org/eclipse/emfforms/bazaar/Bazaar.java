/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar;

import java.security.AllPermission;
import java.util.List;

/**
 * A Bazaar is a flexible registry for factories to create specific objects of type T, called "Ware". To create a ware,
 * an arbitrary number of {@link Vendor} is queried. {@link Vendor} request certain parameters to create a ware.
 * This is queried from the {@link BazaarContext}. If a {@link AllPermission} parameters requested by a
 * {@link Vendor} are present in the {@link BazaarContext}, a {@link Vendor} will do a {@link Bid}. The
 * {@link Vendor} with the highest {@link Bid} will finally create a ware.
 *
 * If two or more {@link Vendor} do the same bid, the registered {@link PriorityOverlapCallBack} will be notified,
 * an arbitrary {@link Vendor} with the same {@link Bid} will create the ware in this case.
 *
 * To transform parameter in the {@link BazaarContext} into parameters {@link Vendor}s expect, you can register
 * {@link BazaarContextFunction}s at the {@link Bazaar}.
 *
 * @param <T> the type of ware create by this Bazaar
 * @author jonas
 *
 */
public interface Bazaar<T> {

	/**
	 * Adds a {@link Vendor} to the bazaar. Will be queried if a ware is requested, the best fitting will create the
	 * Ware.
	 *
	 * @param vendor the {@link Vendor}
	 */
	void addVendor(Vendor<T> vendor);

	/**
	 * Adds a {@link BazaarContextFunction} to this {@link Bazaar} to exchange existing parameters to a parameter
	 * requested by a {@link Vendor}.
	 *
	 * @param key the key of a requested parameter, which can be exchanged from other available parameters in the
	 *            {@link BazaarContext} by this {@link BazaarContextFunction}
	 * @param contextFunction the {@link BazaarContextFunction} being able to exchange to the requested parameter
	 */
	void addContextFunction(String key, BazaarContextFunction contextFunction);

	/**
	 * Creates a ware of type T, provided by the {@link Vendor} with the highest {@link Bid} and which is statisfied
	 * by the parameters in the {@link BazaarContext}.
	 *
	 * @param context the {@link BazaarContext}, which is used to provide requested parameters for {@link Vendor}
	 * @return the ware provided by the best {@link Vendor}
	 */
	T createWare(BazaarContext context);

	/**
	 * Creates a list of wares of type T, provided by {@link Vendor}s which are statisfied by the parameters in the
	 * {@link BazaarContext}, ordered by their {@link Bid}.
	 *
	 * @param context the {@link BazaarContext}, which is used to provide requested parameters for {@link Vendor}
	 * @return a list of wares ordered by the highest {@link Bid}
	 */
	List<T> createWares(BazaarContext context);

	/**
	 * If two or more {@link Vendor} do the same bid, the registered
	 * {@link org.eclipse.emfforms.bazaar.Bazaar.PriorityOverlapCallBack} will be
	 * notified,
	 * an arbitrary {@link Vendor} with the same {@link Bid} will create the ware in this case.
	 *
	 * @param <T> the type of ware create by this Bazaar
	 * @author jonas
	 *
	 */
	public interface PriorityOverlapCallBack<T> {
		/**
		 * Will be called if two {@link Vendor}s do the same bid.
		 *
		 * @param winner The {@link Vendor} who will win and create the ware
		 * @param overlapping Another {@link Vendor} with the same bid, but who will not create the ware
		 */

		void priorityOverlap(Vendor<T> winner, Vendor<T> overlapping);

	}

	/**
	 * Adds a {@link PriorityOverlapCallBack}, see {@link PriorityOverlapCallBack}.
	 *
	 * @param priorityOverlapCallBack a PriorityOverlapCallBack
	 */
	void setPriorityOverlapCallBack(PriorityOverlapCallBack<T> priorityOverlapCallBack);

}
