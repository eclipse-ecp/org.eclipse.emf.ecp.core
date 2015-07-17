package org.eclipse.emf.ecp.controls.renderer.fx;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

public class EnumRendererFX extends SimpleControlRendererFX {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Node createControl() {
		final VControl control = getVElement();
		final ChoiceBox choiceBox = new ChoiceBox();
		choiceBox.setItems(FXCollections.observableArrayList(control
			.getDomainModelReference().getEStructuralFeatureIterator()
			.next().getEType().getInstanceClass().getEnumConstants()));
		choiceBox.setMaxWidth(Double.MAX_VALUE);

		final IObservableValue targetValue = getTargetObservable(choiceBox, "value");
		final IObservableValue modelValue = getModelObservable(control
			.getDomainModelReference().getIterator().next());
		bindModelToTarget(targetValue, modelValue, null,
			null);

		control.eAdapters().add(new AdapterImpl() {

			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				if (msg.getFeature() == VViewPackage.eINSTANCE
					.getElement_Diagnostic()) {
					applyValidation(control, choiceBox);
				}
			}

		});

		applyValidation(control, choiceBox);

		return choiceBox;
	}

}
