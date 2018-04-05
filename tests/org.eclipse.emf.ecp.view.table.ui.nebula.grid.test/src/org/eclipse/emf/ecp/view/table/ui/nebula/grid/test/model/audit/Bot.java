/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot#getExecutionIntervalSeconds
 * <em>Execution Interval Seconds</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getBot()
 * @model
 * @generated
 */
public interface Bot extends Member {
	/**
	 * Returns the value of the '<em><b>Execution Interval Seconds</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execution Interval Seconds</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Execution Interval Seconds</em>' attribute.
	 * @see #setExecutionIntervalSeconds(int)
	 * @see org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage#getBot_ExecutionIntervalSeconds()
	 * @model
	 * @generated
	 */
	int getExecutionIntervalSeconds();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Bot#getExecutionIntervalSeconds
	 * <em>Execution Interval Seconds</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Execution Interval Seconds</em>' attribute.
	 * @see #getExecutionIntervalSeconds()
	 * @generated
	 */
	void setExecutionIntervalSeconds(int value);

} // Bot
