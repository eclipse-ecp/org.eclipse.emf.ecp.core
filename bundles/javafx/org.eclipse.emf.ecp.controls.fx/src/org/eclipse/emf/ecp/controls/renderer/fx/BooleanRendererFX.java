package org.eclipse.emf.ecp.controls.renderer.fx;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;

public class BooleanRendererFX extends SimpleControlRendererFX {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public BooleanRendererFX(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	@Override
	protected Node createControl() {
		final VControl control = getVElement();
		final CheckBox checkBox = new CheckBox();
		final IObservableValue targetValue = getTargetObservable(checkBox, "selected");
		final IObservableValue modelValue = getModelObservable(control
			.getDomainModelReference().getIterator().next());
		bindModelToTarget(targetValue, modelValue, null,
			null);
		return checkBox;
	}

}
