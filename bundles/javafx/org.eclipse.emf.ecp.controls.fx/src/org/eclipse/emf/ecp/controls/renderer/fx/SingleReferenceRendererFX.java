/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.renderer.fx;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.controls.internal.fx.Activator;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * This renderer renders a single reference to another element.
 *
 * @author Lucas Koehler
 *
 */
public class SingleReferenceRendererFX extends SimpleControlRendererFX {

	private ReferenceService referenceService;

	// FIXME At the moment there is no way to remove the adapter from the last reference before "closing" this
	// control because there isn't any dispose mechanism in the FX renderer yet.
	private Adapter adapter;

	@Override
	public void init(final VControl control, ViewModelContext viewModelContext) {
		super.init(control, viewModelContext);
		referenceService = viewModelContext.getService(ReferenceService.class);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX#createControl()
	 */
	@Override
	protected Node createControl() {
		final VControl control = getVElement();
		final HBox hBox = new HBox();
		final Hyperlink link = new Hyperlink();
		final ImageView linkImage = new ImageView();
		final IObservableValue modelValue = getModelObservable(control.getDomainModelReference().getIterator().next());

		control.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				if (msg.getFeature() == VViewPackage.eINSTANCE.getElement_Diagnostic()) {
					applyValidation(control, hBox);
				}
			}

		});

		// bind link
		final IObservableValue targetValue = getTargetObservable(link, "text"); //$NON-NLS-1$
		final Binding linkBinding = bindModelToTarget(targetValue, modelValue, new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return modelValue.getValue();
			}
		}, new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return getLinkText(value);
			}
		});

		// Create adapter to track changes of the referenced object.
		adapter = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				if (!msg.isTouch()) {
					linkBinding.updateModelToTarget();
				}
			}
		};

		final EObject currentReference = getReferencedEObject();
		if (currentReference != null) {
			currentReference.eAdapters().add(adapter);
		}

		// Add "click listener" to the hyperlink
		link.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				referenceService.openInNewContext((EObject) modelValue.getValue());
			}
		});

		// bind link image
		final IObservableValue imageTargetValue = getTargetObservable(linkImage, "image"); //$NON-NLS-1$
		getDataBindingContext().bindValue(imageTargetValue, modelValue, new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return modelValue.getValue();
			}
		}, new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return getImage(value);
			}
		});

		hBox.getChildren().add(linkImage);
		hBox.getChildren().add(link);
		hBox.getChildren().addAll(createButtons());
		return hBox;
	}

	/**
	 * @param path the relative path to the image in this bundle.
	 * @return the imageView
	 */
	protected ImageView getImageView(String path) {
		return new ImageView(Activator.getImage(path));
	}

	/**
	 * @return the Buttons used by this renderer.
	 */
	protected Button[] createButtons() {
		final Button deleteRefButton = new Button();
		deleteRefButton.setGraphic(getImageView("icons/delete.png")); //$NON-NLS-1$
		deleteRefButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				removeAdapterFromReferencedEObject();
				final Setting setting = getVElement().getDomainModelReference().getIterator().next();
				final EditingDomain ed = getEditingDomain(setting);
				final EReference reference = (EReference) setting.getEStructuralFeature();
				final Command removeCommand = SetCommand.create(ed, setting.getEObject(),
					reference, null);
				ed.getCommandStack().execute(removeCommand);

			}
		});

		final Button addExistingRefButton = new Button();
		addExistingRefButton.setGraphic(getImageView("icons/link_overlay.png")); //$NON-NLS-1$
		addExistingRefButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeAdapterFromReferencedEObject();
				final Setting setting = getVElement().getDomainModelReference().getIterator().next();
				final EReference reference = (EReference) setting.getEStructuralFeature();
				referenceService.addExistingModelElements(setting.getEObject(), reference);
				final EObject newElement = (EObject) setting.get(true);
				if (newElement != null) {
					newElement.eAdapters().add(adapter);
				}
			}
		});

		final Button addNewRefButton = new Button();
		addNewRefButton.setGraphic(getImageView("icons/add.png")); //$NON-NLS-1$
		addNewRefButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeAdapterFromReferencedEObject();
				final Setting setting = getVElement().getDomainModelReference().getIterator().next();
				final EReference reference = (EReference) setting.getEStructuralFeature();
				referenceService.addNewModelElements(setting.getEObject(), reference);
				final EObject newElement = (EObject) setting.get(true);
				if (newElement != null) {
					newElement.eAdapters().add(adapter);
				}
			}
		});

		final Button[] buttons = new Button[] { deleteRefButton, addExistingRefButton, addNewRefButton };
		return buttons;
	}

	/**
	 * @param value the model object
	 * @return the title of the model object to be displayed as the text of the hyperlink.
	 */
	protected String getLinkText(Object value) {
		final String linkName = getAdapterFactoryItemDelegator().getText(value);
		return linkName == null ? "" : linkName; //$NON-NLS-1$
	}

	/**
	 * @param value the model object
	 * @return the image/icon of the given model object, <code>null</code> if the parameter value is <code>null</code>.
	 */
	protected Image getImage(Object value) {
		if (value == null) {
			return null;
		}
		final String urlString = ((URL) getAdapterFactoryItemDelegator().getImage(value)).toString();
		return new Image(urlString);
	}

	/**
	 * @return the currently referenced EObject.
	 */
	private EObject getReferencedEObject() {
		return EObject.class.cast(getVElement().getDomainModelReference().getIterator().next().get(true));
	}

	/**
	 * Removes the adapter from the currently referenced EObject.
	 */
	private void removeAdapterFromReferencedEObject() {
		final EObject currentReference = getReferencedEObject();
		if (currentReference != null) {
			currentReference.eAdapters().remove(adapter);
		}
	}
}
