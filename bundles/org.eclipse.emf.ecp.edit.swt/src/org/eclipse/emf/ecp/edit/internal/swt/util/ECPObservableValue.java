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
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.edit.internal.swt.util;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;

/**
 * This implements an ObservableValue of a list. This class is used for binding.
 *
 * @author Eugen Neufeld
 */
public class ECPObservableValue extends AbstractObservableValue {

	private final IObservableList list;

	private int index;

	private final Object valueType;

	/**
	 * The Constructor to crate an observable value for a {@link IObservableList}, an index and a special value type.
	 *
	 * @param list the {@link IObservableList}
	 * @param index the index of this value
	 * @param valueType the type of the observed object
	 */
	public ECPObservableValue(IObservableList list, int index, Object valueType) {
		super();
		this.list = list;
		this.index = index;
		this.valueType = valueType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 * @generated
	 */
	@Override
	public Object getValueType() {
		return valueType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		return list.get(index);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(Object value) {
		list.set(index, value);
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
