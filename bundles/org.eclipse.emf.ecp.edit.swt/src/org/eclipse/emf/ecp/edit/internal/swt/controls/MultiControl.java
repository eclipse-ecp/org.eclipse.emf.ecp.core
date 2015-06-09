/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPObservableValue;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlDescription;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester;
import org.eclipse.emf.ecp.edit.spi.util.ECPStaticApplicableTester;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * This control provides the necessary common functionality to create a multicontrol that are needed for
 * {@link org.eclipse.emf.ecore.EStructuralFeature EStructuralFeature}s that have multiple values.
 *
 * @author Eugen Neufeld
 *
 */
public abstract class MultiControl extends SWTControl {

	private static final String ICONS_ARROW_DOWN_PNG = "icons/arrow_down.png";//$NON-NLS-1$
	private static final String ICONS_ARROW_UP_PNG = "icons/arrow_up.png";//$NON-NLS-1$
	private static final String ICONS_UNSET_FEATURE = "icons/unset_feature.png"; //$NON-NLS-1$

	private IObservableList model;
	private IListChangeListener changeListener;
	// list of controls
	private final List<WidgetWrapper> widgetWrappers = new ArrayList<WidgetWrapper>();
	private final List<ECPSWTAction> toolBarActions = new ArrayList<ECPSWTAction>();
	private Composite mainComposite;
	private Composite sectionComposite;
	private ECPControlDescription controlDescription;
	private Class<?> supportedClassType;
	private ECPSWTAction[] actions;

	private Button unsetButton;
	private Label tooltipLabel;

	/**
	 * This returns the array of actions to display in the multi control.
	 *
	 * @return the array of action to add
	 */
	protected abstract ECPSWTAction[] instantiateActions();

	private void findControlDescription(Setting setting) {
		int bestPriority = -1;
		final ECPControlFactory controlFactory = Activator.getDefault().getECPControlFactory();
		if (controlFactory == null) {
			Activator.getDefault().ungetECPControlFactory();
			return;
		}
		for (final ECPControlDescription description : controlFactory.getControlDescriptors()) {
			for (final ECPApplicableTester tester : description.getTester()) {
				if (ECPStaticApplicableTester.class.isInstance(tester)) {
					final ECPStaticApplicableTester test = (ECPStaticApplicableTester) tester;
					final int priority = getTesterPriority(test, setting);
					if (bestPriority < priority) {
						bestPriority = priority;
						controlDescription = description;
						supportedClassType = test.getSupportedClassType();
					}
				} else {
					continue;
				}
			}
		}
		Activator.getDefault().ungetECPControlFactory();
	}

	/**
	 * Checks the priority of a tester.
	 *
	 * @param tester the {@link ECPStaticApplicableTester} to test
	 * @param setting the {@link Setting} to use
	 * @return the priority
	 */
	protected abstract int getTesterPriority(ECPStaticApplicableTester tester,
		Setting setting);

	@Override
	protected void fillControlComposite(Composite parent) {
		final Setting firstSetting = getFirstSetting();
		findControlDescription(firstSetting);
		actions = instantiateActions();

		mainComposite = new Composite(parent, SWT.BORDER);
		mainComposite.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(mainComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(mainComposite);

		// TOOLBAR
		createButtonBar();
		// SEPERATOR
		final Label seperator = new Label(mainComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).span(3, 1).applyTo(seperator);

		model = EMFEditObservables.observeList(getEditingDomain(firstSetting), firstSetting.getEObject(),
			firstSetting.getEStructuralFeature());

		final ScrolledComposite scrolledComposite = new ScrolledComposite(mainComposite, SWT.V_SCROLL);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).hint(SWT.DEFAULT, 150)
			.minSize(SWT.DEFAULT, 150).span(3, 1).applyTo(scrolledComposite);

		sectionComposite = new Composite(scrolledComposite, SWT.NONE);
		sectionComposite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).spacing(0, 5).applyTo(sectionComposite);

		changeListener = new IListChangeListener() {

			@Override
			public void handleListChange(ListChangeEvent event) {
				final ListDiff diff = event.diff;
				diff.accept(new ListDiffVisitor() {

					private int widthBeforeChange = -1; // initial negative value

					@Override
					public void handleRemove(int index, Object element) {
						updateIndicesAfterRemove(index);
						triggerScrollbarUpdate();
						updateTargets();
					}

					private void updateTargets() {
						for (final WidgetWrapper widgetWrapper : widgetWrappers) {
							widgetWrapper.widget.getDataBindingContext().updateTargets();
						}
					}

					@Override
					public void handleAdd(int index, Object element) {
						if (sectionComposite.isDisposed()) {
							return;
						}
						addControl();

						sectionComposite.layout();
						triggerScrollbarUpdate();
						updateTargets();
					}

					@Override
					public void handleMove(int oldIndex, int newIndex, Object element) {
						updateTargets();
					}

					@Override
					public void handleReplace(int index, Object oldElement, Object newElement) {
						widgetWrappers.get(index).widget.getDataBindingContext().updateTargets();
					}

					private void triggerScrollbarUpdate() {
						if (sectionComposite.isDisposed()) {
							return;
						}
						final int widthAfterChange = sectionComposite.getSize().x;
						if (widthBeforeChange != widthAfterChange) {
							scrolledComposite
								.setMinHeight(sectionComposite.computeSize(widthAfterChange, SWT.DEFAULT).y);
							widthBeforeChange = widthAfterChange;
						}
					}
				});
			}
		};
		model.addListChangeListener(changeListener);
		for (int i = 0; i < model.size(); i++) {
			addControl();
		}
		refreshSection();

		scrolledComposite.setMinSize(sectionComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite.setContent(sectionComposite);

	}

	@Override
	protected Button getCustomUnsetButton() {
		return unsetButton;
	}

	private void isFull() {
		final int upperBound = getFirstStructuralFeature().getUpperBound();
		final boolean full = model.size() >= upperBound && upperBound != -1;
		for (final Action action : toolBarActions) {
			action.setEnabled(!full);
		}
	}

	private void addControl() {
		if (sectionComposite.isDisposed()) {
			return;
		}

		final ECPObservableValue modelValue = new ECPObservableValue(model, widgetWrappers.size(), supportedClassType);
		final WidgetWrapper h = new WidgetWrapper(modelValue);
		h.createControl(sectionComposite, SWT.NONE);
		widgetWrappers.add(h);
	}

	/**
	 * Returns the {@link SWTControl}.
	 *
	 * @return the created {@link SWTControl}
	 */
	private SWTControl getSingleInstance() {
		try {
			// final Constructor<? extends ECPControl> widgetConstructor = controlDescription.getControlClass()
			// .getConstructor(boolean.class, IItemPropertyDescriptor.class, EStructuralFeature.class,
			// ECPControlContext.class, boolean.class);
			//
			// return (SWTControl) widgetConstructor.newInstance(false, getItemPropertyDescriptor(),
			// getStructuralFeature(), getModelElementContext(), true);
			final Constructor<? extends ECPAbstractControl> widgetConstructor = controlDescription.getControlClass()
				.getConstructor();

			final SWTControl control = (SWTControl) widgetConstructor.newInstance();
			control.init(getViewModelContext(), getControl());
			control.setEmbedded(true);
			return control;
		} catch (final IllegalArgumentException ex) {
			Activator.logException(ex);
		} catch (final InstantiationException ex) {
			Activator.logException(ex);
		} catch (final IllegalAccessException ex) {
			Activator.logException(ex);
		} catch (final InvocationTargetException ex) {
			Activator.logException(ex);
		} catch (final SecurityException ex) {
			Activator.logException(ex);
		} catch (final NoSuchMethodException ex) {
			Activator.logException(ex);
		}
		return null;
	}

	private void refreshSection() {
		int widgetSize = 150;
		if (widgetWrappers.size() > 0) {
			widgetSize = widgetWrappers.size()
				* (widgetWrappers.get(0).composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);
		}
		sectionComposite.setSize(sectionComposite.getSize().x, widgetSize < 150 ? 150 : widgetSize);
		sectionComposite.layout();
		isFull();
	}

	private void createButtonBar() {
		final Composite toolbarComposite = new Composite(mainComposite, SWT.NONE);
		toolbarComposite.setBackground(mainComposite.getBackground());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(toolbarComposite);

		int colNum = actions.length + 1;
		if (!isEmbedded() && getFirstStructuralFeature().isUnsettable()) {
			colNum++;
		}

		GridLayoutFactory.fillDefaults().numColumns(colNum).equalWidth(false).applyTo(toolbarComposite);

		tooltipLabel = new Label(toolbarComposite, SWT.NONE);
		tooltipLabel.setBackground(mainComposite.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(tooltipLabel);

		for (final ECPSWTAction action : actions) {
			action.setEnabled(isEditable());
			createButtonForAction(action, toolbarComposite);
		}

		if (!isEmbedded() && getFirstStructuralFeature().isUnsettable()) {
			unsetButton = new Button(toolbarComposite, SWT.PUSH);
			unsetButton.setEnabled(!getControl().isReadonly());
			unsetButton.setToolTipText(getUnsetButtonTooltip());
			unsetButton.setImage(Activator.getImage(ICONS_UNSET_FEATURE));
		}
	}

	/**
	 * This class is the common wrapper for multi controls. It adds a remove, move up and move down button.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private final class WidgetWrapper {

		private ECPObservableValue modelValue;

		private Composite composite;

		private SWTControl widget;

		public WidgetWrapper(ECPObservableValue modelValue) {
			this.modelValue = modelValue;
		}

		private WidgetWrapper getThis() {
			return this;
		}

		void createControl(Composite parent, int style) {
			composite = new Composite(parent, style);
			composite.setBackgroundMode(SWT.INHERIT_FORCE);
			composite.setBackground(parent.getBackground());
			GridLayoutFactory.fillDefaults().numColumns(4).spacing(2, 0).applyTo(composite);
			GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(composite);

			widget = getSingleInstance();
			widget.setObservableValue(modelValue);
			final Composite createControl = widget.createControl(composite);

			widget.setEditable(!getControl().isReadonly());

			GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(createControl);

			createDeleteButton(composite);
			if (getFirstStructuralFeature().isOrdered()) {
				createUpDownButtons(composite);
			}

		}

		/**
		 * Initializes the delete button.
		 */
		private void createDeleteButton(Composite composite) {
			final Button delB = new Button(composite, SWT.PUSH);
			delB.setImage(Activator.getImage(ICONS_UNSET_FEATURE));
			delB.setEnabled(!getControl().isReadonly());
			delB.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				/*
				 * (non-Javadoc)
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					final Setting firstSetting = getFirstSetting();
					final EditingDomain editingDomain = getEditingDomain(firstSetting);

					editingDomain.getCommandStack()
						.execute(
							RemoveCommand.create(editingDomain, firstSetting.getEObject(),
								firstSetting.getEStructuralFeature(), modelValue.getValue()));
				}

			});
		}

		/**
		 * Initializes the up/down buttons.
		 */
		private void createUpDownButtons(Composite composite) {
			final Image up = Activator.getImage(ICONS_ARROW_UP_PNG);
			final Image down = Activator.getImage(ICONS_ARROW_DOWN_PNG);

			final Button upB = new Button(composite, SWT.PUSH);
			upB.setImage(up);
			upB.setEnabled(!getControl().isReadonly());
			upB.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				/*
				 * (non-Javadoc)
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (getThis().getModelValue().getIndex() == 0) {
						return;
					}
					final int currentIndex = getThis().getModelValue().getIndex();
					final Setting firstSetting = getFirstSetting();
					final EditingDomain editingDomain = getEditingDomain(firstSetting);

					editingDomain.getCommandStack()
						.execute(
							new MoveCommand(editingDomain, firstSetting.getEObject(), firstSetting
								.getEStructuralFeature(), currentIndex, currentIndex - 1));

				}
			});
			final Button downB = new Button(composite, SWT.PUSH);
			downB.setImage(down);
			downB.setEnabled(!getControl().isReadonly());
			downB.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				/*
				 * (non-Javadoc)
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (getThis().getModelValue().getIndex() + 1 == model.size()) {
						return;
					}
					final int currentIndex = getThis().getModelValue().getIndex();
					final Setting firstSetting = getFirstSetting();
					final EditingDomain editingDomain = getEditingDomain(firstSetting);
					editingDomain
						.getCommandStack()
						.execute(
							new MoveCommand(editingDomain, firstSetting.getEObject(), firstSetting
								.getEStructuralFeature(), currentIndex, currentIndex + 1));

				}
			});
		}

		/**
		 * @return the modelValue
		 */
		ECPObservableValue getModelValue() {
			return modelValue;
		}

		public void dispose() {
			composite.dispose();
			modelValue.dispose();
			widget.dispose();
			composite = null;
			modelValue = null;
			widget = null;
		}
	}

	private void updateIndicesAfterRemove(int indexRemoved) {
		if (widgetWrappers.size() == 0) {
			return;
		}
		final WidgetWrapper wrapper = widgetWrappers.remove(widgetWrappers.size() - 1);
		wrapper.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ECPAbstractControl#applyValidation(org.eclipse.emf.ecp.view.spi.model.VDiagnostic)
	 */
	@Override
	protected void applyValidation(final VDiagnostic diagnostic) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				final Diagnostic displayedDiagnostic = getMostSevereDiagnostic(diagnostic);
				if (displayedDiagnostic == null) {
					if (validationLabel == null || validationLabel.isDisposed()) {
						return;
					}
					updateValidationColor(null);
					validationLabel.setImage(null);
					validationLabel.setToolTipText(""); //$NON-NLS-1$
				} else {
					updateValidationColor(getValidationBackgroundColor(displayedDiagnostic.getSeverity()));
					if (validationLabel == null) {
						return;
					}
					final Image image = getValidationIcon(displayedDiagnostic.getSeverity());
					validationLabel.setImage(image);
					validationLabel.setToolTipText(displayedDiagnostic.getMessage());
				}
			}
		});
	}

	/**
	 * @param diagnostic
	 * @return
	 */
	private Diagnostic getMostSevereDiagnostic(VDiagnostic diagnostic) {
		int highestSeverity = -1;
		Diagnostic displayedDiagnostic = null;
		if (diagnostic == null) {
			return displayedDiagnostic;
		}
		final EList<Object> diagnostics = diagnostic.getDiagnostics();
		for (final Object object : diagnostics) {
			if (object == null) {
				continue;
			}
			final Diagnostic childDiagnostic = (Diagnostic) object;
			if (childDiagnostic.getSeverity() > highestSeverity) {
				highestSeverity = childDiagnostic.getSeverity();
				displayedDiagnostic = childDiagnostic;
			}
		}
		return displayedDiagnostic;
	}

	/**
	 * Allows controls to supply a second visual effect for controls on validation. The color to set is provided as the
	 * parameter.
	 *
	 * @param color the color to set, null if the default background color should be set
	 */
	protected void updateValidationColor(Color color) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		super.dispose();
		model.removeListChangeListener(changeListener);
		model.dispose();

		for (final WidgetWrapper helper : widgetWrappers) {
			helper.dispose();
		}
		mainComposite.dispose();
	}

	@Override
	public Binding bindValue() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated
	 */
	@Deprecated
	@Override
	public void setEditable(boolean isEditable) {
		for (final ECPSWTAction action : actions) {
			action.setEnabled(isEditable);
		}
	}

	@Override
	protected Control[] getControlsForTooltip() {
		return new Control[] { tooltipLabel };
	}

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated
	 */
	@Override
	@Deprecated
	public boolean showLabel() {
		return true;
	}

}
