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
package org.eclipse.emf.ecp.edit.internal.swt.table;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.internal.XMLCalendar;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;

public class XmlDateCellEditor extends CellEditor implements ECPCellEditor {

	private DateTime dateWidget;
	private final DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM);

	public XmlDateCellEditor() {
		// TODO Auto-generated constructor stub
	}

	public XmlDateCellEditor(Composite parent) {
		super(parent);
	}

	public XmlDateCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	public IValueProperty getValueProperty() {
		return new WidgetValueProperty() {

			public Object getValueType() {
				return XMLGregorianCalendar.class;
			}

			@Override
			protected Object doGetValue(Object source) {
				return XmlDateCellEditor.this.doGetValue();
			}

			@Override
			protected void doSetValue(Object source, Object value) {
				XmlDateCellEditor.this.doSetValue(value);
			}

			@Override
			public IObservableValue observe(Object source) {
				if (source instanceof XmlDateCellEditor) {
					return observe(dateWidget);
				}
				return super.observe(source);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#instantiate(org.eclipse.emf.ecore.EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	public void instantiate(EStructuralFeature feature, ViewModelContext viewModelContext) {

	}

	@Override
	protected Control createControl(Composite parent) {
		dateWidget = new DateTime(parent, SWT.DATE | SWT.DROP_DOWN);
		dateWidget.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_edit_cellEditor_xmlDate"); //$NON-NLS-1$
		dateWidget.addKeyListener(new KeyAdapter() {
			// hook key pressed - see PR 14201
			@Override
			public void keyPressed(KeyEvent e) {
				keyReleaseOccured(e);
			}
		});
		dateWidget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				applyEditorValueAndDeactivate();
			}

		});

		dateWidget.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ESCAPE || e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
				}
			}
		});

		dateWidget.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				XmlDateCellEditor.this.focusLost();
			}
		});
		return dateWidget;
	}

	@Override
	protected Object doGetValue() {

		try {
			final XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			cal.setYear(dateWidget.getYear());
			cal.setMonth(dateWidget.getMonth() + 1);
			cal.setDay(dateWidget.getDay());

			cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

			return cal;
		} catch (final DatatypeConfigurationException ex) {
			// Activator.logException(ex);
		}
		final Calendar selectedCalendarDate = Calendar.getInstance();
		selectedCalendarDate.set(Calendar.YEAR, dateWidget.getYear());
		selectedCalendarDate.set(Calendar.MONTH, dateWidget.getMonth());
		selectedCalendarDate.set(Calendar.DAY_OF_MONTH, dateWidget.getDay());
		return new XMLCalendar(selectedCalendarDate.getTime(), XMLCalendar.DATE);

	}

	@Override
	protected void doSetFocus() {
		dateWidget.setFocus();
	}

	@Override
	protected void doSetValue(Object value) {
		final XMLGregorianCalendar cal = (XMLGregorianCalendar) value;
		final GregorianCalendar gregCal = cal.toGregorianCalendar();
		dateWidget.setDate(gregCal.get(Calendar.YEAR), gregCal.get(Calendar.MONTH), gregCal.get(Calendar.DAY_OF_MONTH));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellEditor#focusLost()
	 */
	@Override
	protected void focusLost() {
		if (isActivated()) {
			applyEditorValueAndDeactivate();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.CellEditor#keyReleaseOccured(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		super.keyReleaseOccured(keyEvent);
		if (keyEvent.character == '\u001b') { // Escape character
			fireCancelEditor();
		} else if (keyEvent.character == '\t') { // tab key
			applyEditorValueAndDeactivate();
		}
	}

	/**
	 * Applies the currently selected value and deactiavates the cell editor.
	 */
	void applyEditorValueAndDeactivate() {
		// must set the selection before getting value

		final Object newValue = doGetValue();
		markDirty();
		final boolean isValid = isCorrect(newValue);
		setValueValid(isValid);

		if (!isValid) {
			MessageFormat.format(getErrorMessage(), new Object[] { newValue });
		}

		fireApplyEditorValue();
		deactivate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#getFormatedString(java.lang.Object)
	 */
	public String getFormatedString(Object value) {
		final XMLGregorianCalendar cal = (XMLGregorianCalendar) value;
		if (value == null) {
			return ""; //$NON-NLS-1$
		}
		return dateFormat.format(cal.toGregorianCalendar().getTime());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#getColumnWidthWeight()
	 */
	public int getColumnWidthWeight() {
		return 75;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#getTargetToModelStrategy()
	 */
	public UpdateValueStrategy getTargetToModelStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#getModelToTargetStrategy()
	 */
	public UpdateValueStrategy getModelToTargetStrategy() {
		// TODO Auto-generated method stub
		return null;
	}
}
