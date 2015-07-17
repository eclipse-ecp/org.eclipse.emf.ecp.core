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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

/**
 * @author Eugen Neufeld
 *
 */
public class ValidationNotification implements Notification {

	private final EObject toValidate;

	/**
	 * Creates a {@link ValidationNotification} which is used to trigger a validation of a specific EObject.
	 *
	 * @param toValidate the {@link EObject} to validate
	 */
	public ValidationNotification(EObject toValidate) {
		this.toValidate = toValidate;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNotifier()
	 */
	@Override
	public Object getNotifier() {
		return toValidate;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getEventType()
	 */
	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getFeatureID(java.lang.Class)
	 */
	@Override
	public int getFeatureID(Class<?> expectedClass) {
		return NO_FEATURE_ID;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getFeature()
	 */
	@Override
	public Object getFeature() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldValue()
	 */
	@Override
	public Object getOldValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewValue()
	 */
	@Override
	public Object getNewValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#wasSet()
	 */
	@Override
	public boolean wasSet() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#isTouch()
	 */
	@Override
	public boolean isTouch() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#isReset()
	 */
	@Override
	public boolean isReset() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getPosition()
	 */
	@Override
	public int getPosition() {
		return NO_INDEX;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#merge(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public boolean merge(Notification notification) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldBooleanValue()
	 */
	@Override
	public boolean getOldBooleanValue() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewBooleanValue()
	 */
	@Override
	public boolean getNewBooleanValue() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldByteValue()
	 */
	@Override
	public byte getOldByteValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewByteValue()
	 */
	@Override
	public byte getNewByteValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldCharValue()
	 */
	@Override
	public char getOldCharValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewCharValue()
	 */
	@Override
	public char getNewCharValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldDoubleValue()
	 */
	@Override
	public double getOldDoubleValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewDoubleValue()
	 */
	@Override
	public double getNewDoubleValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldFloatValue()
	 */
	@Override
	public float getOldFloatValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewFloatValue()
	 */
	@Override
	public float getNewFloatValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldIntValue()
	 */
	@Override
	public int getOldIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewIntValue()
	 */
	@Override
	public int getNewIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldLongValue()
	 */
	@Override
	public long getOldLongValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewLongValue()
	 */
	@Override
	public long getNewLongValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldShortValue()
	 */
	@Override
	public short getOldShortValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewShortValue()
	 */
	@Override
	public short getNewShortValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getOldStringValue()
	 */
	@Override
	public String getOldStringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.notify.Notification#getNewStringValue()
	 */
	@Override
	public String getNewStringValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
