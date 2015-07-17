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

import java.util.Collections;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * This class defines a SWTCOntrol which is an abstract class defining an {@link ECPAbstractControl} for SWT.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public abstract class SWTControl extends ECPAbstractControl implements ECPControlSWT {

	/**
	 * RAP theming variable to set.
	 */
	protected static final String CUSTOM_VARIANT = "org.eclipse.rap.rwt.customVariant";//$NON-NLS-1$
	/**
	 * The icon for a validation error.
	 */
	protected static final String VALIDATION_ERROR_ICON = "icons/validation_error.png";//$NON-NLS-1$

	private static final String ICONS_UNSET_FEATURE = "icons/unset_feature.png"; //$NON-NLS-1$

	/**
	 * The label for the validation icon.
	 */
	protected Label validationLabel;

	private IObservableValue modelValue;
	private Binding binding;

	private Composite controlComposite;
	private Composite parentComposite;
	private StackLayout sl;
	private Label unsetLabel;

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPControlSWT#createControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public List<RenderingResultRow<Control>> createControls(final Composite parent) {
		final IItemPropertyDescriptor itemPropertyDescriptor = getItemPropertyDescriptor(getFirstSetting());
		if (itemPropertyDescriptor == null) {
			return null;
		}
		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
		createValidationIcon(parent);
		final List<RenderingResultRow<Control>> list = Collections.singletonList(SWTRenderingHelper.INSTANCE
			.getResultRowFactory().createRenderingResultRow(
				validationLabel, createControl(parent)));

		applyValidation(getControl().getDiagnostic());

		// TODO remove asap
		backwardCompatibleHandleValidation();

		return list;

	}

	/**
	 * This method is called to render the control on a parent.
	 *
	 * @param parent the {@link Composite} which is the parent
	 * @return the created {@link Composite}
	 */
	public Composite createControl(final Composite parent) {

		final Composite dataControl = createDataControl(parent);

		setHelpTooltips();

		// init
		setEditable(isEditable());

		binding = bindValue();

		// write initial values to model (if they differ from the default value of the model-element)
		if (!getFirstStructuralFeature().isUnsettable()
			&& !getFirstSetting().isSet()) {
			if (binding != null) {
				binding.updateTargetToModel();
			}
		}

		return dataControl;
	}

	private void createValidationIcon(Composite composite) {
		if (!isEmbedded()) {
			validationLabel = new Label(composite, SWT.NONE);
			validationLabel.setBackground(composite.getBackground());
			// set the size of the label to the size of the image
			// GridDataFactory.fillDefaults().hint(16, 17).applyTo(validationLabel);
		}
	}

	private Composite createDataControl(Composite composite) {
		final Composite innerComposite = new Composite(composite, SWT.NONE);
		innerComposite.setBackground(composite.getBackground());
		// GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(innerComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(innerComposite);
		createContentControl(innerComposite);
		return innerComposite;
	}

	private void setHelpTooltips() {
		final Control[] controls = getControlsForTooltip();
		if (controls != null) {
			for (final Control control : controls) {
				control.setToolTipText(getHelpText());
			}
		}
	}

	/**
	 * Method for retrieving all controls which should have the help text as their tooltip.
	 *
	 * @return the array of the controls to set a tooltip to
	 */
	protected abstract Control[] getControlsForTooltip();

	/**
	 * Helper for creating the unset stacklayout and creating the control's composite.
	 *
	 * @param composite the parent {@link Composite} to create the control onto
	 */
	protected void createContentControl(Composite composite) {
		parentComposite = new Composite(composite, SWT.NONE);
		parentComposite.setBackground(composite.getBackground());
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(parentComposite);
		sl = new StackLayout();
		parentComposite.setLayout(sl);
		controlComposite = new Composite(parentComposite, SWT.NONE);
		controlComposite.setBackground(parentComposite.getBackground());

		// 1 column for control, 1 for default unset button
		GridLayoutFactory.fillDefaults().numColumns(1).spacing(2, 0)
			.applyTo(controlComposite);

		unsetLabel = new Label(parentComposite, SWT.NONE);
		unsetLabel.setBackground(composite.getBackground());
		unsetLabel.setForeground(composite.getShell().getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		unsetLabel.setAlignment(SWT.CENTER);
		unsetLabel.setText(getUnsetLabelText());
		unsetLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				sl.topControl = controlComposite;
				parentComposite.layout(true);
				if (binding != null) {
					binding.updateTargetToModel();
				} else {
					// TODO editingdomain is missing
					final Setting firstSetting = getFirstSetting();
					final Object currentUnsetValue = firstSetting.get(true);
					firstSetting.set(currentUnsetValue);
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// nothing to do
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// nothing to do
			}
		});
		int numControls = 1;
		fillControlComposite(controlComposite);

		if (!isEmbedded() && getFirstStructuralFeature().isUnsettable()) {
			Button unsetButton = getCustomUnsetButton();
			if (unsetButton == null) {
				numControls++;
				unsetButton = new Button(controlComposite, SWT.PUSH);
				unsetButton.setToolTipText(getUnsetButtonTooltip());
				unsetButton.setImage(Activator.getImage(ICONS_UNSET_FEATURE));
			}
			unsetButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					final Setting firstSetting = getFirstSetting();
					final EditingDomain editingDomain = getEditingDomain(firstSetting);
					editingDomain.getCommandStack()
						.execute(
							new SetCommand(editingDomain, firstSetting.getEObject(), firstSetting
								.getEStructuralFeature(), SetCommand.UNSET_VALUE));

					showUnsetLabel();
				}
			});
			unsetButton.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_unset"); //$NON-NLS-1$
		}
		if (numControls != 1) {
			// 1 column for control, 1 for default unset button
			((GridLayout) controlComposite.getLayout()).numColumns = numControls;
		}

		// INFO margin needed for controlDecorator
		// .extendedMargins(10, 0, 0, 0)

		if (!getFirstStructuralFeature().isUnsettable()
			|| getFirstSetting().isSet()) {
			sl.topControl = controlComposite;
		} else {
			sl.topControl = unsetLabel;
		}

		parentComposite.layout();
	}

	/**
	 *
	 */
	protected void showUnsetLabel() {
		sl.topControl = unsetLabel;
		parentComposite.layout();
	}

	/**
	 * This method must be overridden by concrete classes. Here the widget displaying the data is added to the
	 * composite.
	 *
	 * @param controlComposite the {@link Composite} to add the widget to
	 */
	protected abstract void fillControlComposite(Composite controlComposite);

	/**
	 * The default unset button will be displayed to the right of the control's composite. Concrete classes may override
	 * this method to include an own unset button in their composite rather than using the default positioning.
	 *
	 * @return The custom unset button of the concrete class
	 */
	protected Button getCustomUnsetButton() {
		return null;
	}

	/**
	 * The model value used for databinding. It is either the set one or the calculated.
	 *
	 * @return the {@link IObservableValue}
	 */
	protected IObservableValue getModelValue() {
		if (modelValue != null) {
			return modelValue;
		}
		final Setting setting = getFirstSetting();
		modelValue = EMFEditObservables.observeValue(getEditingDomain(setting),
			setting.getEObject(), setting.getEStructuralFeature());
		return modelValue;
	}

	/**
	 * Allows the user to set the {@link IObservableValue} to use in the control during databinding.
	 *
	 * @param modelValue the set {@link IObservableValue}
	 */
	public void setObservableValue(IObservableValue modelValue) {
		this.modelValue = modelValue;
	}

	/**
	 * A helper method which creates a button for an action on a composite.
	 *
	 * @param action the action to create a button for
	 * @param composite the composite to create the button onto
	 * @return the created button
	 */
	protected Button createButtonForAction(final Action action, final Composite composite) {
		final Button selectButton = new Button(composite, SWT.PUSH);
		selectButton.setImage(Activator.getImage(action));
		selectButton.setEnabled(!getControl().isReadonly());
		selectButton.setToolTipText(action.getToolTipText());
		selectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.run();
				composite.layout();
			}

		});
		return selectButton;
	}

	/**
	 * Triggers the control to perform the databinding.
	 *
	 * @return The {@link Binding}
	 */
	protected abstract Binding bindValue();

	/**
	 * Returns the help information.
	 *
	 * @return The help text
	 */
	protected String getHelpText() {
		return getItemPropertyDescriptor(getFirstSetting()).getDescription(null);
	}

	/**
	 * Returns the validation icon matching the given severity.
	 *
	 * @param severity the severity of the {@link org.eclipse.emf.common.util.Diagnostic}
	 * @return the icon to be displayed, or <code>null</code> when no icon is to be displayed
	 */
	protected Image getValidationIcon(int severity) {
		return SWTValidationHelper.INSTANCE.getValidationIcon(severity);
	}

	/**
	 * Returns the background color for a control with the given validation severity.
	 *
	 * @param severity severity the severity of the {@link org.eclipse.emf.common.util.Diagnostic}
	 * @return the color to be used as a background color
	 */
	protected Color getValidationBackgroundColor(int severity) {
		return SWTValidationHelper.INSTANCE.getValidationBackgroundColor(severity);
	}

	/**
	 * Returns the string for the unset label.
	 *
	 * @return The unset label text
	 */
	protected abstract String getUnsetLabelText();

	/**
	 * Returns the string for the unset button tooltip.
	 *
	 * @return The unset button tooltip
	 */
	protected abstract String getUnsetButtonTooltip();

	/**
	 * Method for retrieving a {@link Color} based on the predefined SWT id.
	 *
	 * @param color the SWT id of the color
	 * @return the Color or black if the id was incorrect
	 */
	protected final Color getSystemColor(int color) {
		if (parentComposite == null) {
			return Display.getDefault().getSystemColor(color);
		}
		return parentComposite.getShell().getDisplay().getSystemColor(color);
	}

	/**
	 * Whether this control should be editable.
	 *
	 * @return true if the {@link IItemPropertyDescriptor#canSetProperty(Object)} returns true, false otherwise
	 */
	@Deprecated
	protected boolean isEditable() {
		final Setting firstSetting = getFirstSetting();
		return getItemPropertyDescriptor(firstSetting).canSetProperty(firstSetting.getEObject());
	}

	@Override
	public void dispose() {
		validationLabel = null;
		if (modelValue != null) {
			modelValue.dispose();
			modelValue = null;
		}
		if (binding != null) {
			binding.dispose();
			binding = null;
		}
		if (controlComposite != null) {
			controlComposite.dispose();
			controlComposite = null;
		}
		if (parentComposite != null) {
			parentComposite.dispose();
			parentComposite = null;
		}
		sl = null;
		if (unsetLabel != null) {
			unsetLabel.dispose();
			unsetLabel = null;
		}
		super.dispose();
	}

	/**
	 * Helper method to keep the old validation.
	 *
	 * @since 1.2
	 */
	@Override
	protected void backwardCompatibleHandleValidation() {
		final VDiagnostic diagnostic = getControl().getDiagnostic();
		if (diagnostic == null) {
			return;
		}
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (getControl() == null) {
					return;
				}
				resetValidation();
				for (final Object object : diagnostic.getDiagnostics()) {
					handleValidation((Diagnostic) object);
				}
			}
		});
	}
}
