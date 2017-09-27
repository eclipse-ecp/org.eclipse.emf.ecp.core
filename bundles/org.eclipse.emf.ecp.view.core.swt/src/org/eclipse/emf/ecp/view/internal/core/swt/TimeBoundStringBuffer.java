/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt;

import java.util.concurrent.TimeUnit;

/**
 * A string buffer that is capable of buffering text based on a given time interval.
 *
 */
public class TimeBoundStringBuffer {

	private static final long DEFAULT_TIMEOUT = TimeUnit.MILLISECONDS.toMillis(1000);
	private final StringBuffer searchBuffer;
	private final long timeout;
	private long lastKeyPressMillis = -1;

	/**
	 * Default constructor.
	 */
	public TimeBoundStringBuffer() {
		this(DEFAULT_TIMEOUT);
	}

	/**
	 * Constructor that allows specifying the time interval in milliseconds.
	 *
	 * @param bound the time bound after which the buffer should be reset
	 */
	public TimeBoundStringBuffer(long bound) {
		searchBuffer = new StringBuffer();
		timeout = bound;
	}

	/**
	 * Append a character to the search buffer.
	 *
	 * @param character the {@link Character} to be appended
	 */
	public void addLast(Character character) {
		searchBuffer.append(Character.toString(character));
		resetKeyPressedTimeout();
	}

	/**
	 * Removes the last character from the search buffer.
	 */
	public void removeLast() {
		if (searchBuffer.length() > 0) {
			searchBuffer.deleteCharAt(searchBuffer.length() - 1);
			resetKeyPressedTimeout();
		}
	}

	/**
	 * Clears the search buffer.
	 */
	public void reset() {
		searchBuffer.setLength(0);
		resetKeyPressedTimer();
	}

	/**
	 * Reset the buffer in case the specified time interval has passed.
	 */
	public void resetIfTimedOut() {
		if (timedOut()) {
			reset();
		}
	}

	/**
	 * Whether the search buffer is empty.
	 *
	 * @return {@code true}, in case the buffer is empty, {@code false} otherwise
	 */
	public boolean isEmpty() {
		return searchBuffer.length() == 0;
	}

	/**
	 * Returns the search buffer as a string.
	 *
	 * @return the string representation of the buffer
	 */
	public String asString() {
		return searchBuffer.toString();
	}

	/**
	 * Whether the key press timeout has been hit.
	 *
	 * @return {@code true}, if the timeout has been hit, {@code false} otherwise
	 */
	public boolean timedOut() {
		long timeElapsed = -1;
		if (lastKeyPressMillis > 0) {
			timeElapsed = System.currentTimeMillis() - lastKeyPressMillis;
		}
		return timeElapsed != -1 && timeElapsed > timeout;
	}

	/**
	 * Reset the key pressed timer.
	 */
	protected void resetKeyPressedTimer() {
		lastKeyPressMillis = -1;
	}

	/**
	 * Reset the key pressed timeout.
	 */
	protected void resetKeyPressedTimeout() {
		lastKeyPressMillis = System.currentTimeMillis();
	}

}
