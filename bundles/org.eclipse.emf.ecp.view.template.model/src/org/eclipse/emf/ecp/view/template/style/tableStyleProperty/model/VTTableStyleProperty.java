/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Style Property</b></em>'.
 *
 * @since 1.9
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
 *        <em>Minimum Height</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
 *        <em>Maximum Height</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#isShowValidationSummaryTooltip
 *        <em>Show Validation Summary Tooltip</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#isEnableSorting
 *        <em>Enable Sorting</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getVisibleLines
 *        <em>Visible Lines</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getRenderMode
 *        <em>Render Mode</em>}</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty()
 * @model
 * @generated
 */
public interface VTTableStyleProperty extends VTStyleProperty {
	/**
	 * Returns the value of the '<em><b>Minimum Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minimum Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Minimum Height</em>' attribute.
	 * @see #isSetMinimumHeight()
	 * @see #unsetMinimumHeight()
	 * @see #setMinimumHeight(int)
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty_MinimumHeight()
	 * @model unsettable="true"
	 * @generated
	 */
	int getMinimumHeight();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
	 * <em>Minimum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Minimum Height</em>' attribute.
	 * @see #isSetMinimumHeight()
	 * @see #unsetMinimumHeight()
	 * @see #getMinimumHeight()
	 * @generated
	 */
	void setMinimumHeight(int value);

	/**
	 * Unsets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
	 * <em>Minimum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetMinimumHeight()
	 * @see #getMinimumHeight()
	 * @see #setMinimumHeight(int)
	 * @generated
	 */
	void unsetMinimumHeight();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMinimumHeight
	 * <em>Minimum Height</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Minimum Height</em>' attribute is set.
	 * @see #unsetMinimumHeight()
	 * @see #getMinimumHeight()
	 * @see #setMinimumHeight(int)
	 * @generated
	 */
	boolean isSetMinimumHeight();

	/**
	 * Returns the value of the '<em><b>Maximum Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maximum Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Maximum Height</em>' attribute.
	 * @see #isSetMaximumHeight()
	 * @see #unsetMaximumHeight()
	 * @see #setMaximumHeight(int)
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty_MaximumHeight()
	 * @model unsettable="true"
	 * @generated
	 */
	int getMaximumHeight();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
	 * <em>Maximum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Maximum Height</em>' attribute.
	 * @see #isSetMaximumHeight()
	 * @see #unsetMaximumHeight()
	 * @see #getMaximumHeight()
	 * @generated
	 */
	void setMaximumHeight(int value);

	/**
	 * Unsets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
	 * <em>Maximum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSetMaximumHeight()
	 * @see #getMaximumHeight()
	 * @see #setMaximumHeight(int)
	 * @generated
	 */
	void unsetMaximumHeight();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getMaximumHeight
	 * <em>Maximum Height</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return whether the value of the '<em>Maximum Height</em>' attribute is set.
	 * @see #unsetMaximumHeight()
	 * @see #getMaximumHeight()
	 * @see #setMaximumHeight(int)
	 * @generated
	 */
	boolean isSetMaximumHeight();

	/**
	 * Returns the value of the '<em><b>Show Validation Summary Tooltip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Validation Summary Tooltip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Show Validation Summary Tooltip</em>' attribute.
	 * @see #setShowValidationSummaryTooltip(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty_ShowValidationSummaryTooltip()
	 * @model
	 * @generated
	 */
	boolean isShowValidationSummaryTooltip();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#isShowValidationSummaryTooltip
	 * <em>Show Validation Summary Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Validation Summary Tooltip</em>' attribute.
	 * @see #isShowValidationSummaryTooltip()
	 * @generated
	 */
	void setShowValidationSummaryTooltip(boolean value);

	/**
	 * Returns the value of the '<em><b>Enable Sorting</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Sorting</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Sorting</em>' attribute.
	 * @see #setEnableSorting(boolean)
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty_EnableSorting()
	 * @model default="true"
	 * @generated
	 */
	boolean isEnableSorting();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#isEnableSorting
	 * <em>Enable Sorting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.12
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Sorting</em>' attribute.
	 * @see #isEnableSorting()
	 * @generated
	 */
	void setEnableSorting(boolean value);

	/**
	 * Returns the value of the '<em><b>Visible Lines</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visible Lines</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Visible Lines</em>' attribute.
	 * @see #isSetVisibleLines()
	 * @see #unsetVisibleLines()
	 * @see #setVisibleLines(int)
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty_VisibleLines()
	 * @model unsettable="true"
	 * @generated
	 */
	int getVisibleLines();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getVisibleLines
	 * <em>Visible Lines</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visible Lines</em>' attribute.
	 * @see #isSetVisibleLines()
	 * @see #unsetVisibleLines()
	 * @see #getVisibleLines()
	 * @generated
	 */
	void setVisibleLines(int value);

	/**
	 * Unsets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getVisibleLines
	 * <em>Visible Lines</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @see #isSetVisibleLines()
	 * @see #getVisibleLines()
	 * @see #setVisibleLines(int)
	 * @generated
	 */
	void unsetVisibleLines();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getVisibleLines
	 * <em>Visible Lines</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @return whether the value of the '<em>Visible Lines</em>' attribute is set.
	 * @see #unsetVisibleLines()
	 * @see #getVisibleLines()
	 * @see #setVisibleLines(int)
	 * @generated
	 */
	boolean isSetVisibleLines();

	/**
	 * Returns the value of the '<em><b>Render Mode</b></em>' attribute.
	 * The literals are from the enumeration
	 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Render Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 *
	 * @since 1.14
	 *        <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Render Mode</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode
	 * @see #setRenderMode(RenderMode)
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage#getTableStyleProperty_RenderMode()
	 * @model
	 * @generated
	 */
	RenderMode getRenderMode();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty#getRenderMode
	 * <em>Render Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.14
	 *        <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Render Mode</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode
	 * @see #getRenderMode()
	 * @generated
	 */
	void setRenderMode(RenderMode value);

} // VTTableStyleProperty
