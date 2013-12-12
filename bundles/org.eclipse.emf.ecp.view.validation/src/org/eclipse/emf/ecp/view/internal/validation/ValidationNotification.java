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
	public Object getNotifier() {
		return toValidate;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getEventType()
	 */
	public int getEventType() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getFeatureID(java.lang.Class)
	 */
	public int getFeatureID(Class<?> expectedClass) {
		return NO_FEATURE_ID;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getFeature()
	 */
	public Object getFeature() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldValue()
	 */
	public Object getOldValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewValue()
	 */
	public Object getNewValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#wasSet()
	 */
	public boolean wasSet() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#isTouch()
	 */
	public boolean isTouch() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#isReset()
	 */
	public boolean isReset() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getPosition()
	 */
	public int getPosition() {
		return NO_INDEX;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#merge(org.eclipse.emf.common.notify.Notification)
	 */
	public boolean merge(Notification notification) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldBooleanValue()
	 */
	public boolean getOldBooleanValue() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewBooleanValue()
	 */
	public boolean getNewBooleanValue() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldByteValue()
	 */
	public byte getOldByteValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewByteValue()
	 */
	public byte getNewByteValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldCharValue()
	 */
	public char getOldCharValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewCharValue()
	 */
	public char getNewCharValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldDoubleValue()
	 */
	public double getOldDoubleValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewDoubleValue()
	 */
	public double getNewDoubleValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldFloatValue()
	 */
	public float getOldFloatValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewFloatValue()
	 */
	public float getNewFloatValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldIntValue()
	 */
	public int getOldIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewIntValue()
	 */
	public int getNewIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldLongValue()
	 */
	public long getOldLongValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewLongValue()
	 */
	public long getNewLongValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldShortValue()
	 */
	public short getOldShortValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewShortValue()
	 */
	public short getNewShortValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getOldStringValue()
	 */
	public String getOldStringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.Notification#getNewStringValue()
	 */
	public String getNewStringValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
