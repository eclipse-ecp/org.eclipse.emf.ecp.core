package org.eclipse.emf.ecp.controls.renderer.fx;

import java.text.DecimalFormat;
import java.util.Locale;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecp.controls.fx.util.ECPNumericalFieldToModelUpdateValueStrategy;
import org.eclipse.emf.ecp.controls.fx.util.NumericalHelper;
import org.eclipse.emf.ecp.view.spi.model.VControl;

public class NumericalRendererFX extends TextRendererFX {

	@Override
	protected Node createControl() {
		final Node textField = super.createControl();
		final Binding binding = (Binding) getDataBindingContext().getBindings().get(0);

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					binding.updateTargetToModel();
					binding.updateModelToTarget();
				}
			}
		});

		return textField;
	}

	@Override
	protected UpdateValueStrategy getModelToTargetStrategy(
		final VControl control) {
		return new EMFUpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				if (value == null || "".equals(value)) { //$NON-NLS-1$
					return null;
				}
				final DecimalFormat format = NumericalHelper.setupFormat(
					Locale.getDefault(), getInstanceClass(control));
				return format.format(value);
			}
		};
	}

	@Override
	protected UpdateValueStrategy getTargetToModelStrategy(VControl control) {
		return new ECPNumericalFieldToModelUpdateValueStrategy(
			getInstanceClass(control));
	}

	private Class<?> getInstanceClass(VControl control) {
		return control.getDomainModelReference()
			.getEStructuralFeatureIterator().next().getEType()
			.getInstanceClass();
	}
}
